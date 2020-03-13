//@formatter:off
/**
 ******************************************************************************
 * @file        AMHSModeChangeAjaxController.java
 * @brief       AMHSモード変更関連のajaxコントローラ
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.maint;

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
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.ResSelectBoxEntity;
import net.muratec.mcs.entity.maint.ReqExeAMHSModeChangeEntity;
import net.muratec.mcs.entity.maint.ReqExeAMHSModeChangeValidateEntity;
import net.muratec.mcs.entity.maint.ReqGetAMHSModeChangeSelListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.maint.AMHSModeChangeService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSモード変更関連のajaxコントローラクラス
 * @par       機能:
 *              getController（コントローラの検索を行う）
 *              exeControlModeOnline（制御モードのオンラインを要求する）
 *              exeControlModeOffline（制御モードのオフラインを要求する）
 *              exeSemStatePaused（SEM状態のポーズを要求する）
 *              exeSemStateAuto（SEM状態のオートを要求する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class AMHSModeChangeAjaxController extends BaseAjaxController {

    // キャリア同期用サービス
    @Autowired private AMHSModeChangeService AMHSModeChangeService;

    // セレクトボックス用サービス
    @Autowired private SelectBoxService selectBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コントローラの検索を行う機能
     * @param     session     セッション情報（Frameworkより付加）
     * @param     reqEntity   検索条件
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      セレクトボックスの要素を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AMHSModeChange/GetController", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSelectBoxEntity getController(HttpSession session,
            @RequestBody ReqGetAMHSModeChangeSelListEntity reqEntity, Errors errors, Locale locale, Model model)
            throws McsException {

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResSelectBoxEntity resEntity = new ResSelectBoxEntity();

        // ------------------------------------
        // コントローラ要素取得
        // ------------------------------------
        resEntity.body = selectBoxService.getControllerByType(reqEntity.controllerType);

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     制御モードのオンラインを要求する機能
     * @param     reqValidate リクエストエンティティ
     * @param     session     セッション情報（Frameworkより付加）
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AMHSModeChange/ExeControlModeOnline", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.MAINT_AMHSMODECHANGE, logOperationType = ComConst.LogOperationType.EXECUTE,
            number = 1L)
    public AjaxResBaseEntity exeControlModeOnline(HttpSession session,
            @Valid @RequestBody ReqExeAMHSModeChangeValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfoAjax(session, locale, ComConst.ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqExeAMHSModeChangeEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqExeAMHSModeChangeEntity.class);

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 外部プロセス連携
        // ------------------------------------
        AMHSModeChangeService.exeControlModeOnline(reqEntity);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     制御モードのオフラインを要求する機能
     * @param     reqValidate リクエストエンティティ
     * @param     session     セッション情報（Frameworkより付加）
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AMHSModeChange/ExeControlModeOffline", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.MAINT_AMHSMODECHANGE, logOperationType = ComConst.LogOperationType.EXECUTE,
            number = 2L)
    public AjaxResBaseEntity exeControlModeOffline(HttpSession session,
            @Valid @RequestBody ReqExeAMHSModeChangeValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfoAjax(session, locale, ComConst.ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqExeAMHSModeChangeEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqExeAMHSModeChangeEntity.class);

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 外部プロセス連携
        // ------------------------------------
        AMHSModeChangeService.exeControlModeOffline(reqEntity);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SEM状態のポーズを要求する機能
     * @param     reqValidate リクエストエンティティ
     * @param     session     セッション情報（Frameworkより付加）
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AMHSModeChange/ExeSemStatePaused", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.MAINT_AMHSMODECHANGE, logOperationType = ComConst.LogOperationType.EXECUTE,
            number = 3L)
    public AjaxResBaseEntity exeSemStatePaused(HttpSession session,
            @Valid @RequestBody ReqExeAMHSModeChangeValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfoAjax(session, locale, ComConst.ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqExeAMHSModeChangeEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqExeAMHSModeChangeEntity.class);

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 外部プロセス連携
        // ------------------------------------
        AMHSModeChangeService.exeSemStatePaused(reqEntity);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SEM状態のオートを要求する機能
     * @param     reqValidate リクエストエンティティ
     * @param     session     セッション情報（Frameworkより付加）
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AMHSModeChange/ExeSemStateAuto", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.MAINT_AMHSMODECHANGE, logOperationType = ComConst.LogOperationType.EXECUTE,
            number = 4L)
    public AjaxResBaseEntity exeSemStateAuto(HttpSession session,
            @Valid @RequestBody ReqExeAMHSModeChangeValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfoAjax(session, locale, ComConst.ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqExeAMHSModeChangeEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqExeAMHSModeChangeEntity.class);

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 外部プロセス連携
        // ------------------------------------
        AMHSModeChangeService.exeSemStateAuto(reqEntity);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
