//@formatter:off
/**
 ******************************************************************************
 ******************************************************************************
 * @file        TestCarrierController.java
 * @brief       テストキャリア情報表示関連のコントローラ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  2                                    天津／張東江                             
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.muratec.mcs.entity.info.ReqGetTestCarrierListEntity;
import net.muratec.mcs.entity.info.TestCarrierListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.info.TestCarrierService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     テストキャリア情報表示関連のコントローラクラス
 * @par       機能:
 *              testCarrierList（初期状態表示）
 *              getCsvFile（CSV出力）
 *              createCsvHeaderRecords（CSVヘッダー情報生成）
 *              createCsvTitleRecords（CSVTitle情報生成）
 *              createCsvItem（CSV項目オブジェクト生成）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class TestCarrierController extends BaseController {

    //** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private TestCarrierService testCarrierService;

    private static final Logger logger = LoggerFactory.getLogger(TestCarrierController.class);

    public static Logger getLogger() {

        return logger;
    }

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     testCarrierList（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(アラーム情報表示)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/testCarrierList", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST, logOperationType = ComConst.LogOperationType.GET, number = 1L)
    public String testCarrierList(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getRefAuthFuncId());

        // ----------------------------------------------
        // 自動更新機能の有効化
        // ----------------------------------------------
        autoReload.setInterval(model);
        
        // セレクトボックスの全件要素作成
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;

        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        List<String[]> testCarrierStateBoxList = testCarrierService.getTestCarrierStateBox();
        List<String[]> currentTscIdBoxList = testCarrierService.getCurrentTscIdBox();
       
        //tscIdBoxはAllを初期化表示する
        testCarrierStateBoxList.add(0, allTerms);
        currentTscIdBoxList.add(0, allTerms);

       
        String testCarrierStateJson = super.objectToJson(testCarrierStateBoxList);
        String currentTscIdJson = super.objectToJson(currentTscIdBoxList);
        model.addAttribute("II_007_01_001", testCarrierStateJson);
        model.addAttribute("II_007_01_002", currentTscIdJson);


        // バージョン情報付与
        ComFunction.setVersion(model);

        return "info/TestCarrierList";
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
    @RequestMapping(value = { "/TestCarrierList/SaveCsvTestCarrierList" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST, logOperationType = ComConst.LogOperationType.CSV_OUT, number = 6L)
    public void getCsvFile(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetTestCarrierListEntity reqEntity = super.getSessionAttribute(session, sessionKey, ReqGetTestCarrierListEntity.class);
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
        List<TestCarrierListEntity> stockerOpeLog = testCarrierService.getTestCarrierList(reqEntity);
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
        ComCsvOut<TestCarrierListEntity> csvOut = new ComCsvOut<TestCarrierListEntity>();
        csvOut.csvOut(res, messageSource, locale, "TestCarrierList.csv", csvHeader, listCsvItem, stockerOpeLog);
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
     * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
     * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    private List<ComCsvItem> createCsvTitleRecords() {

        List<ComCsvItem> listCsvItem = new ArrayList<ComCsvItem>();

        // ######################
        // CSV項目を生成し、ListにADD
        // ######################
        listCsvItem.add(createCsvItem("II-007-01-013", "rn", false));
        listCsvItem.add(createCsvItem("II-007-01-003", "testCarrierId", false));
        listCsvItem.add(createCsvItem("II-007-01-004", "currentTscId", false));
        listCsvItem.add(createCsvItem("II-007-01-005", "currentLoc", false));
        listCsvItem.add(createCsvItem("II-007-01-006", "currentZoneId", false));
        listCsvItem.add(createCsvItem("II-007-01-007", "atomicTscId", false));
        listCsvItem.add(createCsvItem("II-007-01-008", "atomicSource", false));
        listCsvItem.add(createCsvItem("II-007-01-009", "atomicDestination", false));
        listCsvItem.add(createCsvItem("II-007-01-010", "transferDestination", false));
        listCsvItem.add(createCsvItem("II-007-01-011", "testStartTime", false));
        listCsvItem.add(createCsvItem("II-007-01-012", "testEndTime", false));
            
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
    private String createCsvHeaderRecords(ReqGetTestCarrierListEntity reqEntity, Locale locale) {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage("II-007-01-001", null, locale);
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
        if (reqEntity.currentTscId != null && reqEntity.currentTscId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("II-007-04-002", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.currentTscName.toString()); // 比較値
        }
        
        // ######################
        // unit
        // ######################
        if (reqEntity.carrierId != null && reqEntity.carrierId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("II-007-04-001", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.carrierId.toString()); // 比較値
        }
             
        // ######################
        // 改行コード
        // ######################
        sbHeader.append(ComConst.BR);

        return sbHeader.toString();
    }

    
}
