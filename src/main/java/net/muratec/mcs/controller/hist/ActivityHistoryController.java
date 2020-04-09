//@formatter:off
/**
 ******************************************************************************
 * @file        ActivityHistoryController.java
 * @brief       空FOUP画面関連のコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/03 v1.0.0      初版作成                                        SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComCsvItem;
import net.muratec.mcs.common.ComCsvOut;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.hist.ActivityHistoryListEntity;
import net.muratec.mcs.entity.hist.ReqGetActivityHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.ActivityHistoryService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ActivityHistController画面
 * @par       機能:
 *              ActivityHistController (ActivityHistController画面を表示)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.          DESCRIPTION                            AUTHOR
 * ----------------------------------------------------------------------------
 *  20200403    ActivityHistory画面                                      天津村研 SGP
 ******************************************************************************
 */
//@formatter:on
@Controller
public class ActivityHistoryController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private ActivityHistoryService activityHistoryService;
    
    @Autowired private SelectBoxService selBoxService;
    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;
/*    public static final Logger logger = LoggerFactory.getLogger(AMHSConfController.class);

    public static Logger getLogger() {

        return logger;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ActivityHistory   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(ActivityHistory一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      ActivityHistory画面を表示する
     * ----------------------------------------------------------------------------
     * VER.         DESCRIPTION                           AUTHOR
     * ----------------------------------------------------------------------------
     * 20200403   ActivityHistory画面                                     天津村研 SGP
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/ActivityHistory", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String ActivityHistory(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getRefAuthFuncId());

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;

        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> stkData = activityHistoryService.getStkData();
        List<String[]> ohbData = activityHistoryService.getOhbData();
        List<String[]> portData = activityHistoryService.getPortData();
        
        List<String[]> sourceBoxList = null;
        if(stkData!=null) {
        	sourceBoxList = stkData;
        }
        if(ohbData!=null) {
        	sourceBoxList.addAll(ohbData);
        }
        if(portData!=null) {
        	sourceBoxList.addAll(portData);
        }
       
       //sourceBoxListはAllを初期化表示する
        sourceBoxList.add(0, allTerms);

        // セレクトボックス要素をJSON化
       String sourceJson = super.objectToJson(sourceBoxList);
       String destinationJson = super.objectToJson(sourceBoxList);
       
       model.addAttribute("IH_001_01_001", sourceJson);
       model.addAttribute("IH_001_01_002", destinationJson);

        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "hist/ActivityHistory";
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCsvFile（CSV出力を行う）機能
     * @param     res            CSV返却のレスポンス情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return
     * @retval
     * @attention
     * @note      指定された検索条件でのCSV出力処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = { "/ActivityHistory/SaveCsvActivityHistoryList" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.CSV_OUT, number = 6L)
    public void getCsvFile(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.HIST_ACTIVITYHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetActivityHistoryEntity reqEntity = super.getSessionAttribute(session, sessionKey, ReqGetActivityHistoryEntity.class);
        if (reqEntity == null) {
            // sessionに存在しない例外
            String[] args = { sessionKey };
            String errMessage = messageSource.getMessage("ERR0025", args, "ERR0025", locale);
            throw new McsException(errMessage);
        }

        // エスケープ処理前に出力条件生成
        // #########################################
        // ヘッダ部
        // #########################################
        String csvHeader = createCsvHeaderRecords(reqEntity, locale);

        // Csvファイル用のヘッダ情報（ComCsvItem）設定
        // ----------------------------------------------
        // (3)SQL生成 ～ (4)SQL実行（Service呼び出し）
        // ----------------------------------------------
        reqEntity.fromRecordNum = null;
        reqEntity.toRecordNum = null;
        List<ActivityHistoryListEntity> macroTransferLog = activityHistoryService.getActivityHistoryList(reqEntity);
        // ----------------------------------------------
        // CSV形成
        // ----------------------------------------------
        // #########################################
        // CSVタイトル部
        // #########################################
        List<ComCsvItem> listCsvItem = createCsvTitleRecords();

        // #########################################
        // CSV出力
        // #########################################
        ComCsvOut<ActivityHistoryListEntity> csvOut = new ComCsvOut<ActivityHistoryListEntity>();
        csvOut.csvOut(res, messageSource, locale, "ActivityHist.csv", csvHeader, listCsvItem, macroTransferLog);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     createCsvTitleRecords（CSVTitle情報生成）機能
     * @param
     * @return    CSV項目リスト
     * @retval
     * @attention
     * @note      CSV項目リストを作成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private List<ComCsvItem> createCsvTitleRecords() {

        List<ComCsvItem> listCsvItem = new ArrayList<ComCsvItem>();

        // ######################
        // CSV項目を生成し、ListにADD
        // ######################
        listCsvItem.add(createCsvItem("IH-001-01-004", "rum", false));
        listCsvItem.add(createCsvItem("IH-001-01-005", "rcvTime", false));
        listCsvItem.add(createCsvItem("IH-001-01-006", "carrierId", false));
        listCsvItem.add(createCsvItem("IH-001-01-007", "totalTime", false));
        listCsvItem.add(createCsvItem("IH-001-01-008", "srcTscId", false));
        listCsvItem.add(createCsvItem("IH-001-01-009", "srcLoc", false));
        listCsvItem.add(createCsvItem("IH-001-01-010", "compTscId", false));
        listCsvItem.add(createCsvItem("IH-001-01-011", "compLoc", false));
        listCsvItem.add(createCsvItem("IH-001-01-012", "status", false));
        listCsvItem.add(createCsvItem("IH-001-01-013", "startTime", false));
        listCsvItem.add(createCsvItem("IH-001-01-014", "cmpTime", false));
        listCsvItem.add(createCsvItem("IH-001-01-015", "dstGroup", false));
        listCsvItem.add(createCsvItem("IH-001-01-016", "dstTscId", false));
        listCsvItem.add(createCsvItem("IH-001-01-017", "dstLoc", false));
        listCsvItem.add(createCsvItem("IH-001-01-018", "altTscId", false));
        listCsvItem.add(createCsvItem("IH-001-01-019", "altLoc", false));
        listCsvItem.add(createCsvItem("IH-001-01-020", "priority", false));
        listCsvItem.add(createCsvItem("IH-001-01-021", "nextDestination", false));
        listCsvItem.add(createCsvItem("IH-001-01-022", "cancelReq", false));
        listCsvItem.add(createCsvItem("IH-001-01-023", "hostCommandId", false));
        listCsvItem.add(createCsvItem("IH-001-01-024", "commandId", false));
        listCsvItem.add(createCsvItem("IH-001-01-025", "originator", false));
        listCsvItem.add(createCsvItem("IH-001-01-026", "rerouteReq", false));
        
        return listCsvItem;
    } 
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     createCsvItem（CSV項目オブジェクト生成）機能
     * @param     csvOutName       CSV出力される項目名
     * @param     entityName       DBより返されるEntityの変数名
     * @param     addSingleQuotes  項目データの先頭にシングルクォーテーションを付与
     * @return    CSV項目オブジェクト
     * @retval
     * @attention
     * @note      CSV項目オブジェクトを生成し、返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private ComCsvItem createCsvItem(String csvOutName, String entityName, boolean addSingleQuotes) {

        ComCsvItem csvItem = new ComCsvItem();
        csvItem.csvOutName = csvOutName;
        csvItem.entityName = entityName;
        csvItem.addSingleQuotes = addSingleQuotes;
        return csvItem;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     createCsvHeaderRecords（CSVヘッダー情報生成）機能
     * @param     reqEntity      画面より入力された検索条件
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @return    CSVヘッダー
     * @retval
     * @attention
     * @note      CSV出力のヘッダー（1～4行目）を作成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String createCsvHeaderRecords(ReqGetActivityHistoryEntity reqEntity, Locale locale) {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage("IH-001-01-001", null, locale);
        sbHeader.append(ComConst.CSV_TITLE + ComConst.CSV_SEPARATOR + csvTitle + ComConst.BR);

        // ######################
        // 2行目：出力日
        // ######################
        sbHeader.append(
                ComConst.CSV_DATE + ComConst.CSV_SEPARATOR + ComFunction.dateToString(new Date()) + ComConst.BR);

        // ######################
        // 3行目：検索条件
        // ######################
        boolean searchCondFlag = false;
        sbHeader.append(ComConst.CSV_SEARCH_COND + ComConst.CSV_SEPARATOR);
        
        
        // ######################
        // Source
        // ######################
        if (reqEntity.source != null && reqEntity.source.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-001-03-001", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.source.toString()); // 比較値
        }
        
        // ######################
        // Destination
        // ######################
        if (reqEntity.destination != null && reqEntity.destination.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-001-03-002", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.destination.toString()); // 比較値
        }

        // ######################
        // キャリアID
        // ######################
        if (reqEntity.carrierId != null && reqEntity.carrierId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-001-03-003", null, locale)); // 項目名
            sbHeader.append(" LIKE "); // 比較演算子
            sbHeader.append(reqEntity.carrierId); // 比較値
        }

        // ######################
        // Command ID
        // ######################
        if (reqEntity.commandId != null && reqEntity.commandId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-001-03-004", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.commandId.toString()); // 比較値
        }

        // ######################
        // 在庫日時（開始）
        // ######################
        if (reqEntity.dateFrom != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(ComFunction.dateToString(reqEntity.dateFrom)); // 比較値
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(messageSource.getMessage("IH-001-03-010", null, locale)); // 項目名
        }

        // ######################
        // 在庫日時（終了）
        // ######################
        if (reqEntity.dateTo != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            sbHeader.append(messageSource.getMessage("IH-001-03-011", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(ComFunction.dateToString(reqEntity.dateTo)); // 比較値
        }
        
        // ######################
        // Max Records
        // ######################
        if (reqEntity.maxRecords != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-001-03-006", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(reqEntity.maxRecords.toString()); // 比較値
        }
        
        // ######################
        // 改行コード
        // ######################
        sbHeader.append(ComConst.BR);

        return sbHeader.toString();
    }
   
}
