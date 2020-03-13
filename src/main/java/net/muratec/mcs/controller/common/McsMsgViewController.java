//@formatter:off
/**
 ******************************************************************************
 * @file        McsMsgViewController.java
 * @brief       メッセージ表示コントローラクラス
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
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.common;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.entity.common.McsMsgViewReqEntity;
import net.muratec.mcs.entity.common.McsMsgViewResEntity;
import net.muratec.mcs.entity.common.MsgDataEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;
import net.muratec.mcs.service.common.McsMsgViewService;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ表示コントローラ
 * @par       機能:
 *              getLogger (logger取得)
 *              getRecvID (受信者ID取得機能)
 *              getMsgData (メッセージデータ取得機能)
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
@RequestMapping(value = "/Com/McsMsgView")
public class McsMsgViewController extends BaseAjaxController {

    // メッセージ送受信サービス
    @Autowired McsMsgViewService mcsMsgViewService;

    // ロガー
    public static final Logger logger = LoggerFactory.getLogger(McsMsgViewController.class);

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

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     受信者ID取得機能
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    受信者ID
     * @retval    JSON形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetRecvID", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public McsMsgViewResEntity getRecvID(Locale locale, Model model) throws AjaxAurgumentException {

        McsMsgViewResEntity resEntity = new McsMsgViewResEntity();

        String strRecvID = mcsMsgViewService.setRecvID();

        // 受信者IDの設定
        resEntity.setRecvId(strRecvID);

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     メッセージデータ取得機能
     * @param     reqEntity      画面からのリクエスト
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    受信者ID
     * @retval    JSON形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetMsgDataList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public McsMsgViewResEntity getMsgData(@RequestBody McsMsgViewReqEntity reqEntity, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException {

        McsMsgViewResEntity resEntity = new McsMsgViewResEntity();

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ComFunction.ajaxAurgumentCheck(errors, logger, locale);

        MsgResult result = new MsgResult();

        // 受信者IDが正しく設定されているか判定
        if (reqEntity.recvId != null && !(reqEntity.recvId.isEmpty())) {
            // メッセージ取得処理を実施
            resEntity.recvId = reqEntity.recvId;

            // メッセージ取得処理 - 複数件取得
            result = mcsMsgViewService.getMsgList(reqEntity.recvId);

            if (result.getResult() == MsgResult.k_nResultOK) {
                for (MsgData msgData : result.getMsgDataList()) {
                    // データの設定
                    MsgDataEntity data = new MsgDataEntity();
                    Date date = ComFunction.stringToDateByMicro((String) msgData.getVal(ComMsgDef.k_strKeyDateTime));
                    data.date = ComFunction.dateToString(date);
                    data.message = (String) msgData.getVal(ComMsgDef.k_strKeyMessage);
                    resEntity.msgData.add(data);
                }
            } else {
                // メッセージの取得処理に失敗した場合
                if (resEntity.getMsgData().size() == 0) {
                    // Queueが削除された可能性を考慮し、受信者IDの更新
                    resEntity.recvId = mcsMsgViewService.setRecvID();
                }
            }
        } else {
            // 受信者ID登録を実施
            resEntity.recvId = mcsMsgViewService.setRecvID();
        }

        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
