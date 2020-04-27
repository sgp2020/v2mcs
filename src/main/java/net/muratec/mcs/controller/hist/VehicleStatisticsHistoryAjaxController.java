//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleStatisticsHistoryAjaxController.java
 * @brief       VehicleStatisticsHistoryAjaxController画面関連のajaxコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                          AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 			初版作成			  						SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.muratec.mcs.entity.hist.ReqGetVehicleStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetVehicleStatisticsHistoryValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetStockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.ReqGetStockerStatisticsHistValidateEntity;
import net.muratec.mcs.entity.hist.ResGetStockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.ResGetVehicleStatisticsHistoryEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListValidateEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.StockerStatisticsHistService;
import net.muratec.mcs.service.hist.VehicleStatisticsHistoryService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     VehicleStatisticsHistory画面関連のajaxコントローラクラス
 * @par       機能:
 *              getVehicleStatisticsHistory (VehicleStatisticsHistory一覧の取得)
 *              getStockerStatisticsHistSelectBoxList (StockerStatisticsHist一覧TSCIDセレクトボックスリストの取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                            AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 			VehicleStatisticsHistory			          SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class VehicleStatisticsHistoryAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(VehicleStatisticsHistoryAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private VehicleStatisticsHistoryService vehicleStatisticsHistoryService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GetVehicleStatisticsHistory（VehicleStatisticsHistory一覧検索）機能
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
    @RequestMapping(value = "/VehicleStatisticsHistory/GetVehicleStatisticsHistory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetVehicleStatisticsHistoryEntity getVehicleStatisticsHistory(HttpSession session,
            @Valid @RequestBody ReqGetVehicleStatisticsHistoryValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY.getRefAuthFuncId());
        
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetVehicleStatisticsHistoryEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetVehicleStatisticsHistoryEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetVehicleStatisticsHistoryEntity resEntity = mcsDataTablesService.createResEntity(ResGetVehicleStatisticsHistoryEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // ------------------------------------
        // 日付の大小関係を確認（修正）
        // ------------------------------------
        if (!ComFunction.checkFromTo(reqEntity.dateFrom, reqEntity.dateTo)) {
            // 大小関係が入れ替わっている場合

            // 現在の値を格納
            Timestamp beforeFrom = reqEntity.dateFrom;
            Timestamp beforeTo = reqEntity.dateTo;

            // FromとToを入れ替え
            reqEntity.dateFrom = beforeTo;
            reqEntity.dateTo = beforeFrom;
        }

        //增加一天
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
        	String substring = reqEntity.dateTo.toString().substring(0, 10);
        	c.setTime(d.parse(reqEntity.dateTo.toString().substring(0, 10)));
        }catch(ParseException e) {
			 	e.printStackTrace();
        }
        c.set(Calendar.DATE, c.get(Calendar.DATE)+1);
        reqEntity.dateFrom1 = reqEntity.dateFrom.toString().substring(0, 10);
        reqEntity.dateTo1 = d.format(c.getTime());
        
        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/

            // データ取得、設定
            resEntity.body = vehicleStatisticsHistoryService.getVehicleStatisticsHistory(reqEntity);
            
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = vehicleStatisticsHistoryService.getVehicleStatisticsHistoryCount(reqEntity);
            
        }
        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvVehicleStatisticsHistory（CSV保存）機能
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
     * VER.        DESCRIPTION                                     AUTHOR
     * ----------------------------------------------------------------------------
     * 20200424		DownLoad										SGP
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/VehicleStatisticsHistory/SetCsvVehicleStatisticsHistoryList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvStockerStatisticsHist(@Valid @RequestBody ReqGetStockerStatisticsHistValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetVehicleStatisticsHistoryEntity reqEntity = bc.convert(reqStrEntity, ReqGetVehicleStatisticsHistoryEntity.class);

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
        
        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
        	c.setTime(d.parse(reqEntity.dateTo.toString().substring(0, 10)));
        }catch(ParseException e) {
			 	e.printStackTrace();
        }
        c.set(Calendar.DATE, c.get(Calendar.DATE)+1);
        String t = d.format(c.getTime());
        reqEntity.dateFrom1 = reqEntity.dateFrom.toString().substring(0, 10);
        reqEntity.dateTo1 = d.format(c.getTime());

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
