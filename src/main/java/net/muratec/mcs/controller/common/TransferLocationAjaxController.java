//@formatter:off
/**
 ******************************************************************************
 * @file        TransferLocationAjaxController.java
 * @brief       画面部品搬送Location選択用コントローラ
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/01/31 0.8         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.common;

import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.common.ReqTransferLocationEntity;
import net.muratec.mcs.entity.common.ResTransferLocationConfigEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.service.common.TransferLocationService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     画面部品Location選択用コントローラクラス
 * @par       機能:
 *              getLogger（logger取得）
 *              getTransferLocationData（ロケーション情報取得）
 *              getTransferControllerData(コントローラ情報取得)
 *              getTransferAliasData(エイリアス情報取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@RestController
@RequestMapping(value = "/Com/McsTransferLocation")
public class TransferLocationAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(TransferLocationAjaxController.class);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     logger取得
     * @param
     * @return    logger
     * @retval
     * @attention
     * @note      本クラスで生成したloggerを返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static Logger getLogger() {

        return logger;
    }

    @Autowired private TransferLocationService transferLocationService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送ロケーション情報取得機能
     * @param     reqEntity      ロケーション選択情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ロケーション取得結果
     * @retval    JSON形式で返却
     * @attention
     * @note      ロケーションタイプごとのロケーション情報を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    
    @RequestMapping(value = "/GetTransferEqpTypeData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResTransferLocationConfigEntity getTransferEqpTypeData(@Valid @RequestBody ReqTransferLocationEntity reqEntity, Errors errors,
            Locale locale, Model model) throws AjaxAurgumentException {
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ComFunction.ajaxAurgumentCheck(errors, logger, locale);
        
        ResTransferLocationConfigEntity resEntity = new ResTransferLocationConfigEntity();
        resEntity.body = transferLocationService.getEqpTypeBox(reqEntity.getSrcDstFlg());   //参数reqEntity.location, reqEntity.ioMode

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
    
    @RequestMapping(value = "/GetTransferLocationData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResTransferLocationConfigEntity getTransferLocationData(@Valid @RequestBody ReqTransferLocationEntity reqEntity, Errors errors,
            Locale locale, Model model) throws AjaxAurgumentException {

        ResTransferLocationConfigEntity resEntity = new ResTransferLocationConfigEntity();

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ComFunction.ajaxAurgumentCheck(errors, logger, locale);

        resEntity.body = transferLocationService.getTransferLocationList(reqEntity.getSrcDstFlg(),reqEntity.getCon());

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送ロケーションポート情報取得機能
     * @param     reqEntity      ロケーション選択情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ロケーション取得結果
     * @retval    JSON形式で返却
     * @attention
     * @note      搬送ロケーション選択のセレクトボックスで選択したIDに紐付くポート情報を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetTransferAliasData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResTransferLocationConfigEntity getLocationAliasData(@Valid @RequestBody ReqTransferLocationEntity reqEntity, Errors errors,
            Locale locale, Model model) throws AjaxAurgumentException {

        ResTransferLocationConfigEntity resEntity = new ResTransferLocationConfigEntity();

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ComFunction.ajaxAurgumentCheck(errors, logger, locale);

        resEntity.body = transferLocationService.getAliasList(reqEntity.getSrcDstFlg(),reqEntity.getCon());

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
