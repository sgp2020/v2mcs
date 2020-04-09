//@formatter:off
/**
 ******************************************************************************
 * @file        ActivityHistoryAjaxController.java
 * @brief       ActivityHistoryAjaxController画面関連のajaxコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                   AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/07 v1.0.0      初版作成                                          SGP
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
import net.muratec.mcs.entity.hist.ReqGetActivityHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetActivityHistoryListValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ResGetActivityHistoryListEntity;
import net.muratec.mcs.entity.hist.ResGetAtomicActivityHistListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.ActivityHistoryService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ActivityHistory画面関連のajaxコントローラクラス
 * @par       機能:
 *              getActivityHistory (ActivityHistory一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.               DESCRIPTION                            AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 	    ActivityHistoryAjaxController			SGP天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class ActivityHistoryAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(ActivityHistoryAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private ActivityHistoryService activityHistoryService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getActivityHistoryList（ActivityHistoryList一覧検索）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      一覧検索条件
     * @param     errors         エラー情報（Framworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/ActivityHistory/GetActivityHistoryList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.GET, number = 2L)
    public ResGetActivityHistoryListEntity getActivityHistoryList(HttpSession session,
            @Valid @RequestBody ReqGetActivityHistoryListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetActivityHistoryEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetActivityHistoryEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetActivityHistoryListEntity resEntity = mcsDataTablesService.createResEntity(ResGetActivityHistoryListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
   
            // データ取得、設定
            resEntity.body = activityHistoryService.getActivityHistoryList(reqEntity);
            
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = activityHistoryService.getCountActivityHistory(reqEntity);
            
        }
        return resEntity;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getAtomicTransferLogList
     * @param     session     セッション情報（Frameworkより付加）
     * @param     reqEntity   検索条件
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      ポートリストの取得を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/ActivityHistory/GetAtomicTransferLogList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetAtomicActivityHistListEntity getAtomicTransferLogList(HttpSession session,
            @Valid @RequestBody ReqGetAtomicActivityHistEntity reqEntity, Errors errors, Locale locale, Model model)
                    throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
    	ResGetAtomicActivityHistListEntity resEntity = new ResGetAtomicActivityHistListEntity();

        // ------------------------------------
        // ポートリスト取得
        // ------------------------------------
        resEntity.body = activityHistoryService.getAtomicTransferLogList(reqEntity.commandId);
        
        //データに応じたセルの背景色の設定を行います。
        //List<String> rowColorList = new ArrayList<String>();
        //List<OhbPortRltModel> ohbPortRltList = resEntity.ohbPortRltList;
        //for (OhbPortRltModel ohbPortRltModel : ohbPortRltList) {
        //	rowColorList.add(getRowColorList(ohbPortRltModel));
		//}
        //resEntity.rowColor = rowColorList;
        /*
        List<String> rowColor = new ArrayList<String>();
        rowColor.add("#00FF00");
        rowColor.add("#808080");
        rowColor.add("#6E78FF");
        resEntity.rowColor = rowColor;*/

        return resEntity;
    }

  //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvActivityHistoryList（CSV保存）機能
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
    @RequestMapping(value = "/ActivityHistory/SetCsvActivityHistoryList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvActivityHistoryList(@Valid @RequestBody ReqGetActivityHistoryListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetActivityHistoryEntity reqEntity = bc.convert(reqStrEntity, ReqGetActivityHistoryEntity.class);

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

        String sessionKey = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
