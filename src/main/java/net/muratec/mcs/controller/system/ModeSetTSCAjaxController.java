﻿//@formatter:off
/**
 ******************************************************************************
 * @file        StockerInformationAjaxController.java
 * @brief       StockerInformation画面関連のajaxコントローラ
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/05/08  v1.0.0  	       初版作成                                       								天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.system;

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

import net.muratec.mcs.annotation.OpLog;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseAjaxController;

import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.info.ReqGetStockerInfoListValidateEntity;
import net.muratec.mcs.entity.info.ResGetStockerInfoListEntity;
import net.muratec.mcs.entity.info.ResGetStockerInfoSelectBoxEntity;
import net.muratec.mcs.entity.system.ReqGetModeSetTSCEntity;
import net.muratec.mcs.entity.system.ResGetModeSetTSCListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.info.StockerInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ModeSetTSC画面関連のajaxコントローラクラス
 * @par       機能:
 *              getModeSetTSCList (ModeSetTSC一覧の取得)
 *              getModeSetTSCSelectBoxList (ModeSetTSC一覧TSCTYPEセレクトボックスリストの取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0 	   ModeSetTSCAjaxController					   	           天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Controller
public class ModeSetTSCAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(ModeSetTSCAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private StockerInfoService stockerInfoService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

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
    @RequestMapping(value = "/ModeSetTSC/GetModeSetTSCList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.SYST_MODESETTSC, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetModeSetTSCListEntity getModeSetTSCList(HttpSession session,
            @Valid @RequestBody ReqGetStockerInfoListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.SYST_MODESETTSC.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetModeSetTSCEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetModeSetTSCEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetModeSetTSCListEntity resEntity = mcsDataTablesService.createResEntity(ResGetModeSetTSCListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
//            resEntity.body = ecService.getEmptyCarrierList(reqEntity);
/*            resEntity.body = stockerInfoService.getStockerInfoList(reqEntity);
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = stockerInfoService.getStockerInfoCount(reqEntity);
            
	        // STD 2020.03.27 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
            //Rowを色へ変更する
            resEntity.rowColorList = stockerInfoService.getRowColor(reqEntity);
            // END 2020.03.27 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
*/
        }
        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoSelectBoxList(空FOUP管理一覧 コントローラIDセレクトボックスリストの取得)機能
     * @param     session        セッション情報(Frameworkより付加)
     * @param     reqEntity      一覧検索条件
     * @param     errors         エラー情報(Framworkより付加)
     * @param     locale         ロケーション情報(Frameworkより付加)
     * @param     model          モデル情報(Frameworkより付加)
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      セレクトボックスリスト情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/ModeSetTSC/GetTSCTypeSelectBoxList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetStockerInfoSelectBoxEntity getTSCTypeSelectBoxList(HttpSession session,
            @RequestBody AjaxReqBaseEntity reqEntity, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        super.setUserInfoAjax(session, locale, ComConst.ScreenInfo.SYST_MODESETTSC.getRefAuthFuncId());

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        ResGetStockerInfoSelectBoxEntity resEntity = new ResGetStockerInfoSelectBoxEntity();

        // ------------------------------------
        // パターンリスト取得
        // ------------------------------------
        resEntity.tscIdList = stockerInfoService.getStockerInfoIdBox();

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
