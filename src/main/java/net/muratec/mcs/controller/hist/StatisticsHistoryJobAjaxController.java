//@formatter:off
/**
 ******************************************************************************
 * @file        StatisticsHistoryJobAjaxController.java
 * @brief       StatisticsHistoryJob画面関連のajaxコントローラ
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
import net.muratec.mcs.entity.hist.ReqGeStatisticsHistoryJobListValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetStatisticsHistoryJobEntity;
import net.muratec.mcs.entity.hist.ResGetStatisticsHistoryJobListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.StatisticsHistoryJobService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     StatisticsHistoryJob画面関連のajaxコントローラクラス
 * @par       機能:
 *              getStatisticsHistoryJobList (StockerInformation一覧の取得)
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
public class StatisticsHistoryJobAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(StatisticsHistoryJobAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private StatisticsHistoryJobService statisticsHistoryJobService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStatisticsHistoryJobList（Statistics History(Job)一覧検索）機能
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
    @RequestMapping(value = "/StatisticsHistoryJob/GetStatisticsHistoryJobList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_STATISTICSHISTORYJOB, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetStatisticsHistoryJobListEntity getStatisticsHistoryJobList(HttpSession session,
            @Valid @RequestBody ReqGeStatisticsHistoryJobListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_STATISTICSHISTORYJOB.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetStatisticsHistoryJobEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetStatisticsHistoryJobEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetStatisticsHistoryJobListEntity resEntity = mcsDataTablesService.createResEntity(ResGetStatisticsHistoryJobListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
            resEntity.body = statisticsHistoryJobService.getStatisticsHistoryJobList(reqEntity);
            
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
    @RequestMapping(value = "/StatisticsHistoryJob/SetCsvStatisticsHistoryJobList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_STATISTICSHISTORYJOB, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvStatisticsHistoryJobList(@Valid @RequestBody ReqGeStatisticsHistoryJobListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_STATISTICSHISTORYJOB.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetStatisticsHistoryJobEntity reqEntity = bc.convert(reqStrEntity, ReqGetStatisticsHistoryJobEntity.class);
        
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

        String sessionKey = ComConst.ScreenInfo.HIST_STATISTICSHISTORYJOB.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

}