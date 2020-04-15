//@formatter:off
/**
 ******************************************************************************
 * @file        JobStatisticsHistoryAjaxController.java
 * @brief       JobStatisticsHistory画面関連のajaxコントローラ
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
 * 2020.04.10 			StockerInformationAjaxController			          董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.sql.Timestamp;
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
import net.muratec.mcs.common.ComBeanConv;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.common.ResSelectBoxEntity;
import net.muratec.mcs.entity.hist.ReqGetJobStatisticsHistoryListValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetJobStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetJobStatisticsHistorySelListEntity;
import net.muratec.mcs.entity.hist.ResGetJobStatisticsHistoryListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.JobStatisticsHistoryService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     JobStatisticsHistory画面関連のajaxコントローラクラス
 * @par       機能:
 *              getJobStatisticsHistoryList (StockerInformation一覧の取得)
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
public class JobStatisticsHistoryAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(JobStatisticsHistoryAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private JobStatisticsHistoryService jobStatisticsHistoryService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getJobStatisticsHistoryList（Statistics History(Job)一覧検索）機能
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
     * 20200409		Statistics History(Job)									DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/JobStatisticsHistory/GetJobStatisticsHistoryList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetJobStatisticsHistoryListEntity getJobStatisticsHistoryList(HttpSession session,
            @Valid @RequestBody ReqGetJobStatisticsHistoryListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetJobStatisticsHistoryEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetJobStatisticsHistoryEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetJobStatisticsHistoryListEntity resEntity = mcsDataTablesService.createResEntity(ResGetJobStatisticsHistoryListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
            resEntity.body = jobStatisticsHistoryService.getJobStatisticsHistoryList(reqEntity);
            
            // 全体レコード数取得、設定
//            resEntity.pageInfo.totalRecords = statisticsHistoryJobService.getgetStatisticsHistoryJobCount(reqEntity);
        }
        return resEntity;
    }

  //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvAtomicActivityHistList（CSV保存）機能
     * @param     reqValidate    画面より入力された情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    成功、または失敗
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリアのCSV出力を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200331		DownLoad												DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/JobStatisticsHistory/SetCsvJobStatisticsHistoryList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvJobStatisticsHistoryList(@Valid @RequestBody ReqGetJobStatisticsHistoryListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetJobStatisticsHistoryEntity reqEntity = bc.convert(reqStrEntity, ReqGetJobStatisticsHistoryEntity.class);
        
        // 日付の大小関係を確認（修正）
        if (!ComFunction.checkFromTo(reqEntity.dateFrom, reqEntity.dateTo)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp sbeforeFrom = reqEntity.dateFrom;
            Timestamp sbeforeTo = reqEntity.dateTo;
            // FromとToを入れ替え
            reqEntity.dateFrom = sbeforeTo;
            reqEntity.dateTo = sbeforeFrom;
        }

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     tscIdのセレクトボックス要素を生成する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity    画面より入力された検索情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    tscIdのセレクトボックス要素
     * @retval    JSON形式で返却
     * @attention
     * @note      指定されたtscIdのSourceをセレクトボックス要素を生成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     *  20200413	getSourceSelList   										 DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/JobStatisticsHistory/GetSourceSelList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSelectBoxEntity getSourceSelList(HttpSession session, @RequestBody ReqGetJobStatisticsHistorySelListEntity reqEntity,
            Errors errors, Locale locale, Model model) throws McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        super.setUserInfoAjax(session, locale, ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getRefAuthFuncId());

        ResSelectBoxEntity resEntity = new ResSelectBoxEntity();
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;
        
        if (reqEntity.tscId != null) {
        	List<String[]> portData = jobStatisticsHistoryService.getPortData(reqEntity.tscId);
            List<String[]> zoneData = jobStatisticsHistoryService.getZoneData(reqEntity.tscId);
            if(portData!=null) {
            	resEntity.body = portData;
            }
            if(zoneData!=null) {
            	resEntity.body.addAll(zoneData);
            }
            //sourceBoxはAllを初期化表示する
            resEntity.body.add(0, allTerms);
        } else {
        	//sourceBoxはAllを初期化表示する
            resEntity.body.add(0, allTerms);
        }
        return resEntity;
    }
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     tscIdのセレクトボックス要素を生成する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity    画面より入力された検索情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    tscIdのセレクトボックス要素
     * @retval    JSON形式で返却
     * @attention
     * @note      指定されたtscIdのSourceをセレクトボックス要素を生成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     *  20200413	getDestinationSelList   										 DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/JobStatisticsHistory/GetDestinationSelList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSelectBoxEntity getDestinationSelList(HttpSession session, @RequestBody ReqGetJobStatisticsHistorySelListEntity reqEntity,
            Errors errors, Locale locale, Model model) throws McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        super.setUserInfoAjax(session, locale, ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getRefAuthFuncId());

        ResSelectBoxEntity resEntity = new ResSelectBoxEntity();
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;
        
        if (reqEntity.tscId != null) {
        	List<String[]> portData = jobStatisticsHistoryService.getPortData(reqEntity.tscId);
            List<String[]> zoneData = jobStatisticsHistoryService.getZoneData(reqEntity.tscId);
            if(portData!=null) {
            	resEntity.body = portData;
            }
            if(zoneData!=null) {
            	resEntity.body.addAll(zoneData);
            }
            //DestinationBoxはAllを初期化表示する
            resEntity.body.add(0, allTerms);
        } else {
        	//DestinationBoxはAllを初期化表示する
            resEntity.body.add(0, allTerms);
        }
        return resEntity;
    }

}
