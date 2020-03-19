//@formatter:off
/**
 ******************************************************************************
 * @file        StockerInformationAjaxController.java
 * @brief       StockerInformation画面関連のajaxコントローラ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 * 2020.03.11 			StockerInformationAjaxController			          董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

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

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseAjaxController;

import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.info.ReqGetHostCommInfoEntity;
import net.muratec.mcs.entity.info.ReqGetHostCommListValidateEntity;
import net.muratec.mcs.entity.info.ResGetHostCommInfoListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.info.HostCommInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     StockerInformation画面関連のajaxコントローラクラス
 * @par       機能:
 *              getStockerInfoList (StockerInformation一覧の取得)
 *              getStockerInfoSelectBoxList (StockerInformation一覧TSCIDセレクトボックスリストの取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			StockerInformationAjaxController			          董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class HostCommInfoAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(HostCommInfoAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private HostCommInfoService hostCommInfoService;

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
    @RequestMapping(value = "/HostCommInfo/GetHostCommInfoList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_HOSTCOMMINFO, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetHostCommInfoListEntity getStockerInfoList(HttpSession session,
            @Valid @RequestBody ReqGetHostCommListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_HOSTCOMMINFO.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetHostCommInfoEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetHostCommInfoEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetHostCommInfoListEntity resEntity = mcsDataTablesService.createResEntity(ResGetHostCommInfoListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
            resEntity.body = hostCommInfoService.getHostCommInfoList(reqEntity);
            
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = hostCommInfoService.getHostommInfoCount(reqEntity);
            
            //異常Rowを色へ変更する
            List<String> color = new ArrayList<String>();
            int rowSize = resEntity.body.size();
            for(int i = 0;i<rowSize; i++) {
            	String commState = resEntity.body.get(i).commState; 
            	if(commState!=null && !State.HOST_STATE_COMMUNICATING.equals(commState) ) 
        		{
            		// Selected/Communicating以外は異常とする.
            		color.add("#08336B");
        		}
            	resEntity.rowColorList = color;
            }

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
/*    @RequestMapping(value = "/StockerInfo/GetStockerSelectBoxList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetStockerInfoSelectBoxEntity getStockerInfoSelectBoxList(HttpSession session,
            @RequestBody AjaxReqBaseEntity reqEntity, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        super.setUserInfoAjax(session, locale, ComConst.ScreenInfo.INFO_HOSTCOMMINFO.getRefAuthFuncId());

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
    }*/
}
