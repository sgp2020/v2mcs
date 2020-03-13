//@formatter:off
/**
 ******************************************************************************
 * @file        McsBaseController.java
 * @brief       MCSコントローラ基底クラス
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
 * 2018/11/22 MACS4#0047  GUI要望分                                   T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AuthenticationService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     MCSコントローラ基底クラス
 * @par       機能:
 *              getSessionAttribute（オブジェクト取得処理）
 *              setSessionAttribute（オブジェクト保存処理）
 *              objectToJson（JSON変換処理）
 *              getUserInfo（ユーザ情報取得処理）
 *              setUserInfoProc（アクセス制御処理（共通））
 *              setUserInfo（アクセス制御処理（通常画面））
 *              setUserInfoTop（アクセス制御処理（TOP画面））
 *              setUserInfoAjax（アクセス制御処理(通常画面 Ajax用)）
 *              setUserInfoTopAjax（アクセス制御処理(TOP画面 Ajax用)）
 *              removeSessionUserInfo（ユーザ情報削除処理）
 * @attention
 * @note      セッションアクセス用関数などの基本機能を保持したクラス
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class McsBaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private AuthenticationService authService;

    /**
     * GSONインスタンス
     */
    protected static final Gson gson = new Gson();

    /** ログ出力 */
    private static final Logger logger = LoggerFactory.getLogger(McsBaseController.class);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     オブジェクト取得処理
     * @param     session        セッション情報
     * @param     key            情報取得キー
     * @param     clazz          保存先クラス型
     * @return    キーに紐付くセッション保存情報
     * @retval
     * @attention
     * @note      セッションから任意のキーで取得し、指定したクラス型で返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected <T> T getSessionAttribute(HttpSession session, String key, Class<T> clazz) {

        String sessionAttribute = (String) session.getAttribute(key);
        T target = (T) gson.fromJson(sessionAttribute, clazz);

        return target;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     オブジェクト保存処理
     * @param     session        セッション情報
     * @param     key            情報保存キー
     * @param     objValue       保存するオブジェクト
     * @return
     * @retval
     * @attention
     * @note      指定されたオブジェクトをセッションに保存
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void setSessionAttribute(HttpSession session, String key, Object objValue) {

        String strJson = gson.toJson(objValue);
        session.setAttribute(key, strJson);

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     JSON変換処理
     * @param     objValue       JSON化したいオブジェクト
     * @return    JSON化した文字列
     * @retval
     * @attention GSONライブラリを利用
     * @note      指定されたオブジェクトをJson化する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected String objectToJson(Object objValue) {

        if (objValue == null) {
            return "{}";
        }

        String strJson = gson.toJson(objValue);
        strJson = strJson.replace("\\", "\\\\");
        return strJson;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザ情報取得処理
     * @param     session        セッション情報
     * @return    ユーザ情報
     * @retval
     * @attention
     * @note      ユーザ情報をセッションから取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected AuthenticationEntity getUserInfo(HttpSession session) {

        return getSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO, AuthenticationEntity.class);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アクセス制御処理
     * @param     session        セッション情報
     * @param     model          画面連携情報
     * @param     locale         ロケール
     * @param     screenFuncId   画面ファンクションID
     * @param     isTop          TOP画面フラグ
     * @return    AuthenticationEntity ユーザ情報
     * @retval
     * @attention 画面連携情報がNullの場合はユーザ情報やアクセス可否情報は設定されない
     * @note      現在ログイン中のユーザのアクセス権をチェック
     *            画面連携情報へユーザ情報並びにボタンに対するアクセス可否の情報を設定
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0047  GUI要望分                                              T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    private AuthenticationEntity setUserInfoProc(HttpSession session, Model model, Locale locale, String screenFuncId,
            boolean isTop) throws McsException {

        // *************************************************************************************
        // セッションよりユーザ情報を取得
        // *************************************************************************************
        AuthenticationEntity userInfo = this.getUserInfo(session);

        // *************************************************************************************
        // セッションにユーザ情報が存在しなければ、
        // ・TOP画面 ： NOLOGINユーザ情報を取得しセッションに保存し処理を継続
        // ・TOP画面以外 ： セッション無しエラーで処理を中断
        // セッションにユーザが存在する場合はそのユーザアカウントの生死チェック
        // *************************************************************************************
        if (userInfo == null) {
            if (isTop) {
                AuthenticationEntity noLoginUser = authService.getNoLoginUserInfo();
                // 認証情報をセッションに保存＆変数に再格納
                this.setSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO, noLoginUser);
                userInfo = this.getSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO,
                        AuthenticationEntity.class);
            } else {
                throw new McsException(messageSource.getMessage("ERR0036", null, "ERR0036", locale));
            }
        } else {
            // セッションのユーザ情報が正常かどうかユーザアカウントを再取得＆チェック
            if (ComConst.ConstUserId.NOLOGIN.equals(userInfo.userName)) {
                // NOLOGINユーザ時
                userInfo = authService.getNoLoginUserInfo();
            } else {
                // その他ユーザ時
                userInfo = authService.getLoginUserInfo(userInfo.userName);
            }

        }

        // *************************************************************************************
        // ユーザ情報が取得できればmodelにユーザ情報をセット
        // *************************************************************************************
        if (model != null) {
            model.addAttribute("loginUserId", userInfo.userId);
            model.addAttribute("loginUserName", userInfo.userName);
            model.addAttribute("loginUserDescription", userInfo.description);
            model.addAttribute("autoLogoutTime", userInfo.autoLogoutTime);

            ComConst.ScreenInfo screenInfoList[] = ComConst.ScreenInfo.values();
            for (ComConst.ScreenInfo scrInfo : screenInfoList) {
                String refAuthFuncId = scrInfo.getRefAuthFuncId();
                if (refAuthFuncId != null && refAuthFuncId.length() > 0) {
                    if (userInfo.functionIdList.indexOf(refAuthFuncId) >= 0) {
                        model.addAttribute(refAuthFuncId, true);
//                      logger.debug(refAuthFuncId + " = true");    // MACS4#0047 Del
                    } else {
                        model.addAttribute(refAuthFuncId, false);
//                      logger.debug(refAuthFuncId + " = false");   // MACS4#0047 Del
                    }
                }

                String chgAuthFuncId = scrInfo.getChgAuthFuncId();
                if (chgAuthFuncId != null && chgAuthFuncId.length() > 0) {
                    if (userInfo.functionIdList.indexOf(chgAuthFuncId) >= 0) {
                        model.addAttribute(chgAuthFuncId, true);
//                      logger.debug(chgAuthFuncId + " = true");    // MACS4#0047 Del
                    } else {
                        model.addAttribute(chgAuthFuncId, false);
//                      logger.debug(chgAuthFuncId + " = false");   // MACS4#0047 Del
                    }
                }
            }

            // MACS4#0047 Add Start
            // 子メニューの権限がすべて無効になっている場合、
            // 親メニューも無効にする
            ComConst.PariMenu.PairFuncId pairList[] = ComConst.PariMenu.PairFuncId.values();
            for (ComConst.PariMenu.PairFuncId pair : pairList) {
                String[] childFuncIdList = pair.getChildFuncIdList();
                boolean bEnabled = false;
                for (String childFuncId : childFuncIdList) {
                    if (userInfo.functionIdList.indexOf(childFuncId) >= 0) {
                        bEnabled = true;
                        break;
                    }
                }
                model.addAttribute(pair.getParentFuncId(), bEnabled);
            }
            // MACS4#0047 Add End
        }

        // *****************************************************
        // 取得したユーザ情報.ファンクションIDリストの中に
        // 引数のファンクションIDが存在チェック。存在しなければ権限なしとする。
        // 引数のファンクションIDがない場合はアクセスフリーとし、ノーチェックとする。
        // *****************************************************
        if (screenFuncId != null && screenFuncId.length() > 0) {
            if (userInfo.functionIdList.indexOf(screenFuncId) == -1) {
                throw new McsException(messageSource.getMessage("ERR0008", null, "ERR0008", locale));
            }
        }

        return userInfo;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アクセス制御処理（通常版 TOP画面以外）
     * @param     session        セッション情報
     * @param     model          画面連携情報
     * @param     locale         ロケール
     * @param     screenFuncId   画面ファンクションID
     * @return
     * @retval
     * @attention 画面連携情報がNullの場合はユーザ情報やアクセス可否情報は設定されない
     * @note      現在ログイン中のユーザのアクセス権をチェック
     *            画面連携情報へユーザ情報並びにボタンに対するアクセス可否の情報を設定
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void setUserInfo(HttpSession session, Model model, Locale locale, String screenFuncId)
            throws McsException {

        // 処理呼び出し
        setUserInfoProc(session, model, locale, screenFuncId, false);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アクセス制御処理（通常版 TOP画面）
     * @param     session        セッション情報
     * @param     model          画面連携情報
     * @param     locale         ロケール
     * @param     screenFuncId   画面ファンクションID
     * @return
     * @retval
     * @attention 画面連携情報がNullの場合はユーザ情報やアクセス可否情報は設定されない
     * @note      現在ログイン中のユーザのアクセス権をチェック
     *            画面連携情報へユーザ情報並びにボタンに対するアクセス可否の情報を設定
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void setUserInfoTop(HttpSession session, Model model, Locale locale, String screenFuncId)
            throws McsException {

        // 処理呼び出し
        setUserInfoProc(session, model, locale, screenFuncId, true);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アクセス制御処理(Ajax用)
     * @param     session        セッション情報
     * @param     locale         ロケール
     * @param     screenFuncId   画面ファンクションID
     * @return
     * @retval
     * @attention Ajaxからのアクセスはこちらを利用する
     * @note      アクセス制御処理を画面連携情報Nullで呼び出す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void setUserInfoAjax(HttpSession session, Locale locale, String screenFuncId) throws McsException {

        // 処理呼び出し
        setUserInfoProc(session, null, locale, screenFuncId, false);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     アクセス制御処理(Ajax用 TOP画面)
     * @param     session        セッション情報
     * @param     locale         ロケール
     * @param     screenFuncId   画面ファンクションID
     * @return
     * @retval
     * @attention Ajaxからのアクセスはこちらを利用する
     * @note      アクセス制御処理を画面連携情報Nullで呼び出す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void setUserInfoTopAjax(HttpSession session, Locale locale, String screenFuncId) throws McsException {

        // 処理呼び出し
        setUserInfoProc(session, null, locale, screenFuncId, true);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザ情報削除処理
     * @param     session        セッション情報
     * @return
     * @retval
     * @attention
     * @note      セッションよりユーザ情報を削除
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected void removeSessionUserInfo(HttpSession session) {

        session.removeAttribute(ComConst.SessionKey.LOGIN_USER_INFO);
    }

}
