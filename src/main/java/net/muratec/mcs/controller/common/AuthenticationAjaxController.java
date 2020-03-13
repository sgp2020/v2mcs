//@formatter:off
/**
 ******************************************************************************
 * @file        AuthenticationAjaxController.java
 * @brief       ユーザ認証関連のコントローラ
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
package net.muratec.mcs.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.common.ReqLoginEntity;
import net.muratec.mcs.entity.common.ReqLoginValidateEntity;
import net.muratec.mcs.entity.common.ResAuthEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AuthenticationService;
import net.muratec.mcs.service.common.OpeLogService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ユーザ認証関連のコントローラクラス
 * @par       機能:
 *              login（ログインを行う）
 *              logout（ログアウトを行う）
 *              getAuthInfo（ログイン中のユーザ情報取得）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class AuthenticationAjaxController extends BaseAjaxController {

    /** サービス */
    @Autowired private AuthenticationService authService;

    /** 操作ログサービス */
    @Autowired private OpeLogService opeLogService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログイン機能
     * @param     reqValidate    画面より入力されたユーザ情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ログイン結果
     * @retval    JSON形式で返却
     * @attention
     * @note      指定されたユーザ名/パスワードでログイン処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    // OpLog 操作ログ出力用アノテーション
    // screenInfo = どの画面かを選択 logOperationType = どの操作かを選択
    // number = 画面内で一意となる連番（1L～99L）
    @OpLog(screenInfo = ComConst.ScreenInfo.SYS_LOGIN, logOperationType = ComConst.LogOperationType.SYS, number = 1L)
    public ResAuthEntity login(@Valid @RequestBody ReqLoginValidateEntity reqValidate, HttpSession session,
            Errors errors, Locale locale, Model model) throws AjaxAurgumentException, McsException {

        // 戻り値宣言
        ResAuthEntity resEntity = new ResAuthEntity();

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ReqLoginEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqLoginEntity.class);

        // 認証確認 エラーなら例外 OKなら認証情報を取得し後続処理へ
        resEntity.body = authService.accountCheck(reqEntity.userName, reqEntity.password);

        // 認証情報をセッションに保存
        super.setSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO, resEntity.body);

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログアウト機能
     * @param     reqValidate    ユーザ情報（未使用）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ログアウト結果
     * @retval    JSON形式で返却
     * @attention
     * @note      セッションに保存されているユーザ情報を削除
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity logout(@RequestBody ReqLoginValidateEntity reqValidate, HttpSession session, Errors errors,
            Locale locale, Model model) throws AjaxAurgumentException, McsException {

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 操作ログ情報設定（アノテーション記載情報を転記）
        // ------------------------------------
        OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.SYS_LOGOUT,
                ComConst.LogOperationType.SYS, 1L);
        // 正常時に操作ログ出力
        // パラメータ文字列化
        String param = ComFunction.toStringMcs(reqValidate);
        opeLogService.getOpeLog(opeLogInfo.logCode, param, opeLogInfo.userName, opeLogInfo.ipAddress);

        // セッション削除
        super.removeSessionUserInfo(session);

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザ情報取得機能
     * @param     reqEntity      ユーザ情報（未使用）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ユーザ情報エンティティ
     * @retval    JSON形式で返却
     * @attention
     * @note      セッションに保存されているユーザ情報を返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetAuthInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    // OpLog 操作ログ出力用アノテーション
    // screenInfo = どの画面かを選択 logOperationType = どの操作かを選択
    // number = 画面内で一意となる連番（1L～99L）
    @OpLog(screenInfo = ComConst.ScreenInfo.SYS_AUTH, logOperationType = ComConst.LogOperationType.SYS, number = 1L)
    public ResAuthEntity getAuthInfo(@RequestBody AjaxReqBaseEntity reqEntity, HttpSession session, Errors errors,
            Locale locale, Model model) throws AjaxAurgumentException {

        // 戻り値宣言
        ResAuthEntity resEntity = new ResAuthEntity();

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";
        resEntity.body = super.getUserInfo(session);

        return resEntity;
    }

}
