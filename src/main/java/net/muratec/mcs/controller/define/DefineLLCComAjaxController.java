//@formatter:off
/**
 ******************************************************************************
 * @file        DefineLLCComAjaxController.java
 * @brief       
 * @par
 * @author       天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/01 v1.0.0      初版作成                                           天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.define;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComBeanConv;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.define.ReqGetDefineLLCComValidateEntity;
import net.muratec.mcs.entity.define.ReqGetDefineLLCComEntity;
import net.muratec.mcs.entity.define.ResGetDefineLLCComEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorValidateEntity;
import net.muratec.mcs.entity.top.ResScMonitorPortListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.define.DefineLLCComService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     comconf画面関連のajaxコントローラクラス
 * @par       機能:
 *              getComConf (ComConf一覧の取得)
 *              getComConfSelectBoxList (ComConf一覧セレクトボックスリストの取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class DefineLLCComAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(DefineLLCComAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private DefineLLCComService defineLLCComService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoList（StockerInfo一覧検索）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      一覧検索条件
     * @param     errors         エラー情報（Framworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      EMPTY_CARRIERテーブルより、指定された開始～終了行の一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/DefineLLCCom/GetDefineLLCCom", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.DEF_LLCCOM, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetDefineLLCComEntity getDefineLLCCom(HttpSession session,
            @Valid @RequestBody ReqGetDefineLLCComValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.DEF_LLCCOM.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetDefineLLCComEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetDefineLLCComEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetDefineLLCComEntity resEntity = mcsDataTablesService.createResEntity(ResGetDefineLLCComEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
            resEntity.body = defineLLCComService.getDefineLLCCom(reqEntity);
            
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = defineLLCComService.getDefineLLCComCount(reqEntity);
            
           
        }
        return resEntity;
    }
 
}
