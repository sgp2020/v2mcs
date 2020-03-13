//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierSynchronizeAjaxController.java
 * @brief       キャリア同期関連のajaxコントローラ
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.maint;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import net.muratec.mcs.entity.maint.ReqExeCarrierSynchronizeEntity;
import net.muratec.mcs.entity.maint.ReqExeCarrierSynchronizeValidateEntity;
import net.muratec.mcs.entity.maint.ReqGetCarrierSynchronizeEntity;
import net.muratec.mcs.entity.maint.ResGetCarrierSynchronizeEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.maint.CarrierSynchronizeService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア同期関連のajaxコントローラクラス
 * @par       機能:
 *              getCarrierSynchronize（コントローラの検索を行う）
 *              exeCarrierSynchronize（キャリア同期を行う）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class CarrierSynchronizeAjaxController extends BaseAjaxController {

    // キャリア同期用サービス
    @Autowired private CarrierSynchronizeService carrierSynchronizeService;

    // メッセージリソース
    @Autowired private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(CarrierSynchronizeAjaxController.class);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCarrierSynchronize（コントローラの検索を行う）
     * @param     session     セッション情報（Frameworkより付加）
     * @param     reqEntity   検索条件
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      コントローラの検索を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/CarrierSynchronize/GetCarrierSynchronize", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetCarrierSynchronizeEntity getCarrierSynchronize(HttpSession session,
            @Valid @RequestBody ReqGetCarrierSynchronizeEntity reqEntity, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResGetCarrierSynchronizeEntity resEntity = new ResGetCarrierSynchronizeEntity();

        // ------------------------------------
        // コントローラ要素取得
        // ------------------------------------
        resEntity.body = carrierSynchronizeService.getCarrierSynchronize(reqEntity);

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeCarrierSynchronize（キャリア同期を行う）
     * @param     reqValidate 同期内容
     * @param     session     セッション情報（Frameworkより付加）
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリア同期を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/CarrierSynchronize/ExeCarrierSynchronize", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.MAINT_CARRIERSYNCHRONIZE,
            logOperationType = ComConst.LogOperationType.EXECUTE, number = 1L)
    public AjaxResBaseEntity exeCarrierSynchronize(HttpSession session,
            @Valid @RequestBody ReqExeCarrierSynchronizeValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfoAjax(session, locale, ComConst.ScreenInfo.MAINT_CARRIERSYNCHRONIZE.getChgAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqExeCarrierSynchronizeEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqExeCarrierSynchronizeEntity.class);

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        if (carrierSynchronizeService.exeCarrierSynchronize(reqEntity)) {
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
            resEntity.result.message = "";
        } else {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            resEntity.result.message = messageSource.getMessage("IM-011-01.002", null, "IM-011-01.002", locale);
        }

        return resEntity;
    }
}
