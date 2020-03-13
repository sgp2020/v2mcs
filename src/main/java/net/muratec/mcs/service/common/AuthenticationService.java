//@formatter:off
/**
 ******************************************************************************
 * @file        AuthenticationService.java
 * @brief       ユーザ認証関連のサービス
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComPassword;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiFuncGroupRltMapper;
import net.muratec.mcs.mapper.UserAccountMapper;
import net.muratec.mcs.model.GuiFuncGroupRltExample;
import net.muratec.mcs.model.GuiFuncGroupRltKey;
import net.muratec.mcs.model.McsConsts;
import net.muratec.mcs.model.UserAccount;
import net.muratec.mcs.model.UserAccountExample;
import net.muratec.mcs.service.maint.SystemParameterService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ユーザ認証関連のサービスクラス
 * @par       機能:
 *              accountCheck（アカウント情報のチェック処理）
 *              getLoginUserInfo（ログインユーザー情報取得処理）
 *              getNoLoginUserInfo（未ログインユーザー情報取得処理）
 *              getFunctionIdList（ファンクションID一覧取得処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class AuthenticationService extends BaseService {

    /** DBマッパー */
    @Autowired private UserAccountMapper userAccMapper;

    @Autowired private GuiFuncGroupRltMapper guiFuncGrRltMapper;

    /** サービス */
    @Autowired private SystemParameterService spService;

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アカウント情報チェック処理
     * @param     userName        ユーザ名
     * @param     password        パスワード
     * @return    アカウント情報
     * @retval
     * @attention
     * @note      引数のユーザ名、パスワードにてアカウント情報をチェックする
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(rollbackFor = Exception.class, noRollbackFor = McsException.class)
    public AuthenticationEntity accountCheck(String userName, String password) throws McsException {

        // **********************************************************************
        // ユーザ名入力チェック
        // **********************************************************************
        if (userName == null || userName.length() == 0) {
            // 入力されたユーザまたはパスワードはアカウントと一致しません。
            throw new McsException(
                    messageSource.getMessage("ERR0004", null, "ERR0004", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // NOLOGINチェック
        // **********************************************************************
        if (ComConst.ConstUserId.NOLOGIN.equals(userName)) {
            // NOLOGINでのログインはできません。
            throw new McsException(
                    messageSource.getMessage("ERR0007", null, "ERR0007", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // システムパラメータよりログイン失敗最大回数を取得する
        // **********************************************************************
        McsConsts mcsConsts = spService.getMcsConsts(ComConst.McsConstsKey.LOGIN_FAIL.getCategory(),
                ComConst.McsConstsKey.LOGIN_FAIL.getSection(), ComConst.McsConstsKey.LOGIN_FAIL.getKey());
        short loginFailMaxTimes = Short.parseShort(mcsConsts.getValue());

        // **********************************************************************
        // システムパラメータより自動ログアウト時間を取得する
        // **********************************************************************
        mcsConsts = spService.getMcsConsts(ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getCategory(),
                ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getSection(),
                ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getKey());
        int autoLogoutTime = Integer.parseInt(mcsConsts.getValue());

        // **********************************************************************
        // ユーザ名でユーザアカウントテーブルを取得
        // **********************************************************************
        UserAccountExample uaExample = new UserAccountExample();
        uaExample.createCriteria().andUserNameEqualTo(userName);
        List<UserAccount> userAccList = userAccMapper.selectByExample(uaExample);

        // **********************************************************************
        // 取得ユーザ不在チェック
        // **********************************************************************
        if (userAccList == null || userAccList.size() == 0) {
            // 入力されたユーザまたはパスワードはアカウントと一致しません。
            throw new McsException(
                    messageSource.getMessage("ERR0004", null, "ERR0004", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // 取得ユーザ複数チェック（通常はありえないはず）
        // **********************************************************************
        if (userAccList.size() >= 2) {
            // 入力されたユーザがシステムに複数存在してます。
            throw new McsException(
                    messageSource.getMessage("ERR0005", null, "ERR0005", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // ユーザアカウントを取得
        // **********************************************************************
        UserAccount userAcc = userAccList.get(0);
        short errCount = 0;
        short accState = ComConst.AccountState.UNLOCK;
        // エラーカウントを取得
        if (userAcc.getPassErrorCount() != null) {
            errCount = userAcc.getPassErrorCount().shortValue();
        }
        // アカウント状態を取得
        if (userAcc.getAccountState() != null) {
            accState = userAcc.getAccountState().shortValue();
        }

        // **********************************************************************
        // アカウント状態チェック
        // ロック中ならロックである旨のエラーを返却
        // **********************************************************************
        if (accState == ComConst.AccountState.LOCK) {
            // 入力されたユーザはロックされております。
            throw new McsException(
                    messageSource.getMessage("ERR0006", null, "ERR0006", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // パスワードチェック
        // **********************************************************************
        if (!ComPassword.isAuthUser(userName, password, userAcc.getPassword())) {

            // パスワード不一致の場合
            // エラーカウントを＋１
            errCount += 1;

            // エラーカウントをセット
            userAcc.setPassErrorCount(Short.valueOf(errCount));

            // 失敗の最大回数が０以外かつ、その回数を超えていたらロックする（0設定時は無効とする）
            if (loginFailMaxTimes != 0 && errCount > loginFailMaxTimes) {
                userAcc.setAccountState(Short.valueOf(ComConst.AccountState.LOCK));
            }

            userAcc.setUserName(null);
            userAcc.setDescription(null);
            userAcc.setGuiGroupId(null);
            userAcc.setPassword(null);
            userAcc.setLastLogin(null);

            // ユーザアカウントを更新
            userAccMapper.updateByPrimaryKeySelective(userAcc);

            // ログイン失敗として例外を返却
            // 入力されたユーザまたはパスワードはアカウントと一致しません。
            throw new McsException(
                    messageSource.getMessage("ERR0004", null, "ERR0004", LocaleContextHolder.getLocale()));
        }

        // **********************************************************************
        // ユーザアカウントを更新
        // パスワード失敗を０クリアし、ログイン日時を現在日時に更新
        // **********************************************************************
        userAcc.setPassErrorCount(Short.valueOf((short) 0));
        Timestamp tsNowDatetime = new Timestamp(System.currentTimeMillis());
        userAcc.setLastLogin(tsNowDatetime);
        int updateCount = userAccMapper.updateByPrimaryKey(userAcc);

        // 更新件数チェック（0件ならエラー）。
        if (updateCount == 0) {
            throw new McsException(
                    messageSource.getMessage("ERR0020", null, "ERR0020", LocaleContextHolder.getLocale()));
        }

        AuthenticationEntity authEntity = new AuthenticationEntity();
        authEntity.userId = userAcc.getUserId();
        authEntity.userName = userAcc.getUserName();
        authEntity.description = userAcc.getDescription();
        authEntity.autoLogoutTime = autoLogoutTime;
        authEntity.functionIdList = getFunctionIdList(userAcc.getGuiGroupId());

        return authEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログインユーザー情報取得処理処理
     * @param     userName        ユーザ名
     * @return    アカウント情報
     * @retval
     * @attention
     * @note      引数で指定されたユーザー情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public AuthenticationEntity getLoginUserInfo(String userName) throws McsException {

        // **********************************************************************
        // システムパラメータより自動ログアウト時間を取得する
        // **********************************************************************
        McsConsts mcsConsts = spService.getMcsConsts(ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getCategory(),
                ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getSection(),
                ComConst.McsConstsKey.LOGOUT_AUTO_LOGOUT.getKey());
        int autoLogoutTime = Integer.parseInt(mcsConsts.getValue());

        // **********************************************************************
        // ユーザ名でユーザアカウントテーブルを取得
        // **********************************************************************
        UserAccountExample uaExample = new UserAccountExample();
        uaExample.createCriteria().andUserNameEqualTo(userName);
        List<UserAccount> userAccList = userAccMapper.selectByExample(uaExample);

        // **********************************************************************
        // 取得ユーザ不在チェック
        // **********************************************************************
        if (userAccList == null || userAccList.size() == 0) {
            // ログイン中のユーザ[{0}]は、削除もしくはロックされております。
            // エラーメッセージ生成し例外発生
            String[] args = { userName };
            String message = messageSource.getMessage("ERR0037", args, "ERR0037", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        // **********************************************************************
        // 取得ユーザ複数チェック（通常はありえないはず）
        // **********************************************************************
        if (userAccList.size() >= 2) {
            // 入力されたユーザがシステムに複数存在してます。
            String message = messageSource.getMessage("ERR0010", null, "ERR0010", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        // **********************************************************************
        // ユーザアカウントを取得
        // **********************************************************************
        UserAccount userAcc = userAccList.get(0);

        // **********************************************************************
        // アカウント状態チェック
        // ロック中ならロックである旨のエラーを返却
        // **********************************************************************
        short accState = ComConst.AccountState.UNLOCK;
        // アカウント状態を取得
        if (userAcc.getAccountState() != null) {
            accState = userAcc.getAccountState().shortValue();
        }
        // アカウント状態チェック
        if (accState == ComConst.AccountState.LOCK) {
            // ログイン中のユーザ[{0}]は、削除もしくはロックされております。
            // エラーメッセージ生成し例外発生
            String[] args = { userName };
            String message = messageSource.getMessage("ERR0037", args, "ERR0037", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        AuthenticationEntity authEntity = new AuthenticationEntity();
        authEntity.userId = userAcc.getUserId();
        authEntity.userName = userAcc.getUserName();
        authEntity.description = userAcc.getDescription();
        authEntity.autoLogoutTime = autoLogoutTime;
        authEntity.functionIdList = getFunctionIdList(userAcc.getGuiGroupId());

        return authEntity;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     未ログインユーザー情報取得処理
     * @param
     * @return    アカウント情報
     * @retval
     * @attention
     * @note      未ログインユーザー情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public AuthenticationEntity getNoLoginUserInfo() throws McsException {

        // **********************************************************************
        // 未ログインユーザの自動ログアウト時間はないため-1を設定する。
        // **********************************************************************
        int autoLogoutTime = -1;

        // **********************************************************************
        // ユーザ名でユーザアカウントテーブルを取得
        // **********************************************************************
        UserAccountExample uaExample = new UserAccountExample();
        uaExample.createCriteria().andUserNameEqualTo(ComConst.ConstUserId.NOLOGIN);
        List<UserAccount> userAccList = userAccMapper.selectByExample(uaExample);

        // **********************************************************************
        // 取得ユーザ不在チェック
        // **********************************************************************
        if (userAccList == null || userAccList.size() == 0) {
            // 未ログインユーザの情報が不正です。
            String message = messageSource.getMessage("ERR0009", null, "ERR0009", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        // **********************************************************************
        // 取得ユーザ複数チェック（通常はありえないはず）
        // **********************************************************************
        if (userAccList.size() >= 2) {
            // 未ログインユーザの情報が不正です。
            String message = messageSource.getMessage("ERR0009", null, "ERR0009", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        // **********************************************************************
        // ユーザアカウントを取得
        // **********************************************************************
        UserAccount userAcc = userAccList.get(0);

        // **********************************************************************
        // アカウント状態チェック
        // ロック中ならロックである旨のエラーを返却
        // **********************************************************************
        short accState = ComConst.AccountState.UNLOCK;
        // アカウント状態を取得
        if (userAcc.getAccountState() != null) {
            accState = userAcc.getAccountState().shortValue();
        }
        // アカウント状態チェック
        if (accState == ComConst.AccountState.LOCK) {
            // 未ログインユーザの情報が不正です。
            String message = messageSource.getMessage("ERR0009", null, "ERR0009", LocaleContextHolder.getLocale());
            throw new McsException(message);
        }

        AuthenticationEntity authEntity = new AuthenticationEntity();
        authEntity.userId = userAcc.getUserId();
        authEntity.userName = userAcc.getUserName();
        authEntity.description = userAcc.getDescription();
        authEntity.autoLogoutTime = autoLogoutTime;
        authEntity.functionIdList = getFunctionIdList(userAcc.getGuiGroupId());

        return authEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ファンクションID一覧取得処理
     * @param     guiGroupId            GUIグループID
     * @return    ファンクションID一覧
     * @retval
     * @attention
     * @note      GUIグループIDを元にファンクションIDを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String> getFunctionIdList(String guiGroupId) throws McsException {

        List<String> funcIdList = new ArrayList<String>();

        // **********************************************************************
        // 引数「GUIグループID」がNullか空文字なら空リストを返却する
        // **********************************************************************
        if (guiGroupId == null || guiGroupId.length() == 0) {
            return funcIdList;
        }

        // **********************************************************************
        // 引数「GUIグループID」よりFuncIdを取得し戻り値に入れる。
        // **********************************************************************
        GuiFuncGroupRltExample guiFuncGrRltExample = new GuiFuncGroupRltExample();
        guiFuncGrRltExample.createCriteria().andGuiGroupIdEqualTo(guiGroupId);
        List<GuiFuncGroupRltKey> guiFuncGroupList = guiFuncGrRltMapper.selectByExample(guiFuncGrRltExample);
        // 戻り値のListにAddしていく
        if (guiFuncGroupList != null) {
            for (GuiFuncGroupRltKey guiFuncGroup : guiFuncGroupList) {
                funcIdList.add(guiFuncGroup.getFuncId());
            }
        }

        return funcIdList;
    }

}
