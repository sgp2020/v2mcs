//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierController.java
 * @brief       キャリア情報表示関連のコントローラ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 * 2018/10/23 MACS4#0021  LotID,ProductID表示                         T.Iga/CSC
 * 2019/02/21 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
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
import net.muratec.mcs.entity.info.CarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.info.CarrierService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報表示関連のコントローラクラス
 * @par       機能:
 *              carrier（初期状態表示）
 *              formatSelectBox（セレクトボックスの設定を行う）
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
public class CarrierController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private CarrierService CarrierService;

    @Autowired private SelectBoxService selBoxService;

    private static final Logger logger = LoggerFactory.getLogger(CarrierController.class);

    public static Logger getLogger() {

        return logger;
    }

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     carrier（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      画面項目情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    キャリア情報表示一覧以外の検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.GET, number = 1L)
    public String carrier(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getRefAuthFuncId());

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

        List<String[]> tscIDBoxList = selBoxService.getTscIdBox();
        tscIDBoxList.add(0, allTerms);
        model.addAttribute("v2II_002_02_004", super.objectToJson(tscIDBoxList));
        
        List<String[]> carrierStateBoxList = selBoxService.getCarrierStateBox();
        carrierStateBoxList.add(0, allTerms);
        model.addAttribute("v2II_002_02_005", super.objectToJson(carrierStateBoxList));
        
        List<String[]> ohbBoxList = selBoxService.getOhbIdBox();
        ohbBoxList.add(0, allTerms);
        model.addAttribute("v2II_002_02_001", super.objectToJson(ohbBoxList));
        
        List<String[]> portBoxList = selBoxService.getPortIdBox();
        model.addAttribute("v2II_002_02_002", super.objectToJson(portBoxList));
        
        List<String[]> stkBoxList = selBoxService.getStkIdBox();
        model.addAttribute("v2II_002_02_003", super.objectToJson(stkBoxList));
        
        // バージョン情報付与
        ComFunction.setVersion(model);

        return "info/Carrier";
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
    @RequestMapping(value = { "/Carrier/SaveCsvCarrierList" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.CSV_OUT, number = 6L)
    public void getCsvFile(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.INFO_CARRIER.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetCarrierListEntity reqEntity = super.getSessionAttribute(session, sessionKey, ReqGetCarrierListEntity.class);
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
        List<CarrierListEntity> CarrierList = CarrierService.getCarrierList(reqEntity);

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
        ComCsvOut<CarrierListEntity> csvOut = new ComCsvOut<CarrierListEntity>();
        csvOut.csvOut(res, messageSource, locale, "CarrierInformation.csv", csvHeader, listCsvItem, CarrierList);
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
    private String createCsvHeaderRecords(ReqGetCarrierListEntity reqEntity, Locale locale) {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage("II-002-01-001", null, locale);
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
            sbHeader.append(messageSource.getMessage("v2II-002-02-001", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.currentTscId.toString()); // 比較値
        }

        // ######################
        // キャリアID
        // ######################
        if (reqEntity.carrierId != null && reqEntity.carrierId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("v2II-002-02-003", null, locale)); // 項目名
            sbHeader.append(" LIKE "); // 比較演算子
            sbHeader.append(reqEntity.carrierId); // 比較値
        }

        // ######################
        // STATE
        // ######################
        if (reqEntity.state != null && reqEntity.state.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("v2II-002-02-005", null, locale)); // 項目名
            sbHeader.append(" = "); // 比較演算子
            sbHeader.append(reqEntity.state.toString()); // 比較値
        }

        // ######################
        // 在庫日時（開始）
        // ######################
        if (reqEntity.sfrom != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(ComFunction.dateToString(reqEntity.sfrom)); // 比較値
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(messageSource.getMessage("v2II-002-02-007", null, locale)); // 項目名
        }

        // ######################
        // 在庫日時（終了）
        // ######################
        if (reqEntity.sto != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            sbHeader.append(messageSource.getMessage("v2II-002-02-007", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(ComFunction.dateToString(reqEntity.sto)); // 比較値
        }
        
        // ######################
        // 在庫日時（開始）
        // ######################
        if (reqEntity.ifrom != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(ComFunction.dateToString(reqEntity.ifrom)); // 比較値
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(messageSource.getMessage("v2II-002-02-007", null, locale)); // 項目名
        }

        // ######################
        // 在庫日時（終了）
        // ######################
        if (reqEntity.ito != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            sbHeader.append(messageSource.getMessage("v2II-002-02-011", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(ComFunction.dateToString(reqEntity.ito)); // 比較値
        }

        // ######################
        // 改行コード
        // ######################
        sbHeader.append(ComConst.BR);

        return sbHeader.toString();
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
        listCsvItem.add(createCsvItem("v2II-002-01-003", "carrierId", false));
        listCsvItem.add(createCsvItem("v2II-002-01-004", "status", false));
        listCsvItem.add(createCsvItem("v2II-002-01-005", "carrierState", false));
        listCsvItem.add(createCsvItem("v2II-002-01-006", "currentTscId", false));
        listCsvItem.add(createCsvItem("v2II-002-01-007", "currentLocation", false));
        listCsvItem.add(createCsvItem("v2II-002-01-008", "currentZone", false));
        listCsvItem.add(createCsvItem("v2II-002-01-009", "lastStoredTime", false));
        listCsvItem.add(createCsvItem("v2II-002-01-010", "block", false));
        listCsvItem.add(createCsvItem("v2II-002-01-011", "insystemCarrierID", false));
        listCsvItem.add(createCsvItem("v2II-002-01-012", "lastInsystemTime", false));
        listCsvItem.add(createCsvItem("v2II-002-01-013", "insystemLocation", false));
        listCsvItem.add(createCsvItem("v2II-002-01-014", "nextDestination", false));
        listCsvItem.add(createCsvItem("v2II-002-01-016", "priority", false));
        listCsvItem.add(createCsvItem("v2II-002-01-017", "offline", false));

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
}
