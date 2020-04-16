//@formatter:off
/**
 ******************************************************************************
 * @file        EmptyCarrierController.java
 * @brief       空FOUP画面関連のコントローラ
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
 * 2019/02/19 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import net.muratec.mcs.common.ComCsvItem;
import net.muratec.mcs.common.ComCsvOut;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.hist.SystemLogEntity;
import net.muratec.mcs.entity.hist.ReqGetSystemLogValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetSystemLogEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ErrorLog;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.SystemLogService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     HostCommInfo画面
 * @par       機能:
 *              HostCommInfo (HostCommInfo画面を表示)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 *  20200318  Ver2.0→Ver4.0 SystemLogory画面                                     董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class SystemLogController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private SystemLogService systemLogService;
    
    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SystemLogory   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(SystemLogory一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      SystemLogory画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200318  Ver2.0→Ver4.0 SystemLogory画面                                     董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/SystemLog", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_SYSTEMLOG, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String SystemLog(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_SYSTEMLOG.getRefAuthFuncId());

        
        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "hist/SystemLog";
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
    @RequestMapping(value = { "/SystemLog/SaveCsvSystemLog" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_SYSTEMLOG, logOperationType = ComConst.LogOperationType.CSV_OUT, number = 6L)
    public void getCsvFile(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_SYSTEMLOG.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.LOG_SYSTEMLOG.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetSystemLogEntity reqEntity = super.getSessionAttribute(session, sessionKey, ReqGetSystemLogEntity.class);
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
        List<SystemLogEntity> systemLog = systemLogService.getSystemLog(reqEntity);
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
        ComCsvOut<SystemLogEntity> csvOut = new ComCsvOut<SystemLogEntity>();
        csvOut.csvOut(res, messageSource, locale, "SystemLog.csv", csvHeader, listCsvItem, systemLog);
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
        listCsvItem.add(createCsvItem("IH-007-01-005", "rn", false));
        listCsvItem.add(createCsvItem("IH-007-01-003", "time", false));
        listCsvItem.add(createCsvItem("IH-007-01-004", "description", false));

        
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
    private String createCsvHeaderRecords(ReqGetSystemLogEntity reqEntity, Locale locale) {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage("IH-007-01-001", null, locale);
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
        
        if (reqEntity.debug == true || reqEntity.information == true || reqEntity.warning == true || reqEntity.error == true || reqEntity.performance == true)
		{
            sbHeader.append(" Error Level: ");
            searchCondFlag = true;
		}
        // ######################
        // Debug
        // ######################
        if (reqEntity.debug != null && reqEntity.debug == true) {
            sbHeader.append(messageSource.getMessage("IH-007-03-002", null, locale)); // 項目名
        	if (searchCondFlag) {
                sbHeader.append(" ");
            }
            searchCondFlag = true;
        }
        
        // ######################
        // information
        // ######################
        if (reqEntity.information != null && reqEntity.information == true) {
            sbHeader.append(messageSource.getMessage("IH-007-03-003", null, locale)); // 項目名
        	if (searchCondFlag) {
                sbHeader.append(" ");
            }
            searchCondFlag = true;
        }
        
        // ######################
        // Warning
        // ######################
        if (reqEntity.warning != null && reqEntity.warning == true) {
            sbHeader.append(messageSource.getMessage("IH-007-03-004", null, locale)); // 項目名
        	if (searchCondFlag) {
                sbHeader.append(" ");
            }
            searchCondFlag = true;
       }

        // ######################
        // Error
        // ######################
        if (reqEntity.error != null && reqEntity.error == true) {
            sbHeader.append(messageSource.getMessage("IH-007-03-005", null, locale)); // 項目名
        	if (searchCondFlag) {
                sbHeader.append(" ");
            }
            searchCondFlag = true;
        }

        // ######################
        // Performance
        // ######################
        if (reqEntity.performance != null && reqEntity.performance == true) {
            sbHeader.append(messageSource.getMessage("IH-007-03-006", null, locale)); // 項目名
        	if (searchCondFlag) {
                sbHeader.append(" ");
            }
            searchCondFlag = true;
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
            sbHeader.append(messageSource.getMessage("IH-007-03-012", null, locale)); // 項目名
        }

        // ######################
        // 在庫日時（終了）
        // ######################
        if (reqEntity.dateTo != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            sbHeader.append(messageSource.getMessage("IH-007-03-013", null, locale)); // 項目名
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
            sbHeader.append(messageSource.getMessage("IH-007-03-008", null, locale)); // 項目名
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
