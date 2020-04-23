//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleStatisticsHistoryController.java
 * @brief       
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
 * 2020/04/15 v1.0.0      初版作成                                         SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.text.ParseException;
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

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComCsvItem;
import net.muratec.mcs.common.ComCsvOut;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.hist.AtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.AtomicActivityHistService;
import net.muratec.mcs.service.hist.VehicleStatisticsHistoryService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     VehicleStatisticsHistory画面
 * @par       機能:
 *              VehicleStatisticsHistory (VehicleStatisticsHistory画面を表示)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.           DESCRIPTION                                     AUTHOR
 * ----------------------------------------------------------------------------
 *  20200415      VehicleStatisticsHistory画面                                    SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class VehicleStatisticsHistoryController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private AtomicActivityHistService atomicActivityHistService;
    
    @Autowired private VehicleStatisticsHistoryService vehicleStatisticsHistoryService;
    
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
     * @brief     VehicleStatisticsHistory   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(VehicleStatisticsHistory一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      VehicleStatisticsHistory画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     * 20200415   VehicleStatisticsHistory画面                                     SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/VehicleStatisticsHistory", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String vehicleStatisticsHistory(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_VEHICLESTATISTICSHISTORY.getRefAuthFuncId());

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;
        
        String[] unitHourTerms = new String[2];
        unitHourTerms[0] = "0";
        unitHourTerms[1] = "Hour";
        
        String[] unitDayTerms = new String[2];
        unitDayTerms[0] = "1";
        unitDayTerms[1] = "Day";

        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> tscIdBoxList = vehicleStatisticsHistoryService.getTscIdBox();
        List<String[]> vehicleBoxList = vehicleStatisticsHistoryService.getVehicleIdBox("");
        List<String[]> unitBoxList = new ArrayList<String[]>();
       
        //vehicleBoxはAllを初期化表示する
        vehicleBoxList.add(0, allTerms);
        //Unitは「Hour」と「Day」を初期化表示する
        unitBoxList.add(0, unitHourTerms);
        unitBoxList.add(1, unitDayTerms);

        // セレクトボックス要素をJSON化
       String tscIdJson = super.objectToJson(tscIdBoxList);
       String vehicleJson = super.objectToJson(vehicleBoxList);
       String unitJson	= super.objectToJson(unitBoxList);
       
       model.addAttribute("IH_004_01_001", tscIdJson);
       model.addAttribute("IH_004_01_002", vehicleJson);
       model.addAttribute("IH_004_01_003", unitJson);
       
        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "hist/VehicleStatisticsHistory";
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
    
    /*
    //@formatter:on
    @RequestMapping(value = { "/JobStatisticsHistory/SaveCsvJobStatisticsHistoryList" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY, logOperationType = ComConst.LogOperationType.CSV_OUT, number = 6L)
    public void getCsvFile(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.HIST_JOBSTATISTICSHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetAtomicActivityHistEntity reqEntity = super.getSessionAttribute(session, sessionKey, ReqGetAtomicActivityHistEntity.class);
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
        List<AtomicActivityHistListEntity> atomicTransferLog = atomicActivityHistService.getAtomicActivityHistList(reqEntity);
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
        ComCsvOut<AtomicActivityHistListEntity> csvOut = new ComCsvOut<AtomicActivityHistListEntity>();
        csvOut.csvOut(res, messageSource, locale, "JobStatisticsHistory.csv", csvHeader, listCsvItem, atomicTransferLog);
    }
    */
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
     * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
     * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
     ******************************************************************************
     */
    /*
    //@formatter:on
    private List<ComCsvItem> createCsvTitleRecords() {

        List<ComCsvItem> listCsvItem = new ArrayList<ComCsvItem>();

        // ######################
        // CSV項目を生成し、ListにADD
        // ######################
        listCsvItem.add(createCsvItem("IH-002-01-004", "rum", false));
        listCsvItem.add(createCsvItem("IH-002-01-005", "time", false));
        listCsvItem.add(createCsvItem("IH-002-01-006", "carrierId", false));
        listCsvItem.add(createCsvItem("IH-002-01-007", "tscAbbreviation", false));
        listCsvItem.add(createCsvItem("IH-002-01-008", "source", false));
        listCsvItem.add(createCsvItem("IH-002-01-009", "destination", false));
        listCsvItem.add(createCsvItem("IH-002-01-010", "statusStr", false));
        listCsvItem.add(createCsvItem("IH-002-01-011", "priority", false));
        listCsvItem.add(createCsvItem("IH-002-01-012", "routeNo", false));
        listCsvItem.add(createCsvItem("IH-002-01-013", "queuedTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-014", "leadTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-015", "totalTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-016", "vehicleId", false));
        listCsvItem.add(createCsvItem("IH-002-01-017", "commandId", false));
        listCsvItem.add(createCsvItem("IH-002-01-018", "atomicRequestTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-019", "atomicAnswerTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-020", "atomicInitiateTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-021", "atomicAcquiredTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-022", "atomicCompleteTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-023", "abortRequestTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-024", "abortAnswerTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-025", "abortInitiateTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-026", "abortCompleteTime", false));
        listCsvItem.add(createCsvItem("IH-002-01-027", "abortReason", false));
        
        return listCsvItem;
    } 
    */
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
    /*
    //@formatter:on
    private ComCsvItem createCsvItem(String csvOutName, String entityName, boolean addSingleQuotes) {

        ComCsvItem csvItem = new ComCsvItem();
        csvItem.csvOutName = csvOutName;
        csvItem.entityName = entityName;
        csvItem.addSingleQuotes = addSingleQuotes;
        return csvItem;
    }
    */
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
    /*
    //@formatter:on
    private String createCsvHeaderRecords(ReqGetAtomicActivityHistEntity reqEntity, Locale locale) {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage("IH-002-01-001", null, locale);
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
        // TSCID
        // ######################
        if (reqEntity.tscId != null && reqEntity.tscId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-002-03-001", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.tscId.toString()); // 比較値
        }
        
        // ######################
        // Source
        // ######################
        if (reqEntity.source != null && reqEntity.source.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IH-002-03-002", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-002-03-003", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-002-03-004", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-002-03-005", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-002-03-011", null, locale)); // 項目名
        }

        // ######################
        // 在庫日時（終了）
        // ######################
        if (reqEntity.dateTo != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            sbHeader.append(messageSource.getMessage("IH-002-03-012", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-002-03-007", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(reqEntity.maxRecords.toString()); // 比較値
        }
        
        // ######################
        // 改行コード
        // ######################
        sbHeader.append(ComConst.BR);

        return sbHeader.toString();
    }
    */
}
