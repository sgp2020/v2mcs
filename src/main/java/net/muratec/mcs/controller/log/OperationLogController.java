//@formatter:off
/**
 ******************************************************************************
 * @file        OperationLogController.java
 * @brief       操作ログ表示関連のコントローラ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.log;

import java.text.ParseException;
import java.util.ArrayList;
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
import net.muratec.mcs.entity.log.OperationLogListEntity;
import net.muratec.mcs.entity.log.ReqGetOperationLogListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.log.OperationLogService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ表示関連のコントローラクラス
 * @par       機能:
 *              operationLog（初期状態表示）
 *              saveCsvOperationLog(CSV出力）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class OperationLogController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private OperationLogService OperationLogService;

    private static final Logger logger = LoggerFactory.getLogger(OperationLogController.class);

    public static Logger getLogger() {

        return logger;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     operationLog（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(操作ログ表示)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/OperationLog", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_OPERATIONLOG, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String operationLog(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_OPERATIONLOG.getRefAuthFuncId());

        // バージョン情報付与
        ComFunction.setVersion(model);

        return "/log/OperationLog";
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     saveCsvOperationLog(CSV出力を行う）機能
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
    @RequestMapping(value = { "/OperationLog/SaveCsvOperationLog" }, method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_OPERATIONLOG, logOperationType = ComConst.LogOperationType.CSV_OUT,
            number = 4L)
    public void saveCsvOperationLog(HttpServletResponse res, HttpSession session, Locale locale, Model model)
            throws ParseException, McsException {

        // ----------------------------------------------
        // (1)アクセス権チェック(GET版)
        // ----------------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_OPERATIONLOG.getRefAuthFuncId());

        // ----------------------------------------------
        // (2)セッション取得
        // ----------------------------------------------
        String sessionKey = ComConst.ScreenInfo.LOG_OPERATIONLOG.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        ReqGetOperationLogListEntity reqEntity = super.getSessionAttribute(session, sessionKey,
                ReqGetOperationLogListEntity.class);
        if (reqEntity == null) {
            // sessionに存在しない例外
            String[] args = { sessionKey };
            String errMessage = messageSource.getMessage("ERR0025", args, "ERR0025", locale);
            throw new McsException(errMessage);
        }
        // エスケープ処理前に出力条件生成
        // ==============================================
        // 検索条件
        // ==============================================
        StringBuilder sbHeader = new StringBuilder();
        boolean searchCondFlag = false;
        // Step4 2017_08_09：#SearchConditionの二重出力を削除
        // sbHeader.append(ComConst.CSV_SEARCH_COND + ComConst.CSV_SEPARATOR);

        // ######################
        // 日時（開始）
        // ######################
        if (reqEntity.from != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(ComFunction.dateToString(reqEntity.from)); // 比較値
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(messageSource.getMessage("IL-006-02-001", null, locale)); // 項目名
        }

        // ######################
        // 日時（終了）
        // ######################
        if (reqEntity.to != null) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IL-006-02-001", null, locale)); // 項目名
            sbHeader.append(" <= "); // 比較演算子
            sbHeader.append(ComFunction.dateToString(reqEntity.to)); // 比較値
        }

        // ######################
        // ユーザ
        // ######################
        if (reqEntity.userId != null && reqEntity.userId.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IL-006-02-004", null, locale)); // 項目名
            sbHeader.append(" LIKE "); // 比較演算子
            sbHeader.append(reqEntity.userId.toString()); // 比較値
        }

        // ######################
        // コード
        // ######################
        if (reqEntity.logCode != null && reqEntity.logCode.length() != 0) {
            if (searchCondFlag) {
                sbHeader.append(" AND ");
            }
            searchCondFlag = true;
            sbHeader.append(messageSource.getMessage("IL-006-02-006", null, locale)); // 項目名
            sbHeader.append(" LIKE "); // 比較演算子
            sbHeader.append(reqEntity.logCode); // 比較値
        }

        // Step4 2017_08_30：コードを*で検索したときにCSVに出力されるようにした
        // あいまい検索用に特殊文字をエスケープ
        OperationLogService.escapeSearchCond(reqEntity);

        // ----------------------------------------------
        // (3)SQL生成 ～ (4)SQL実行（Service呼び出し）
        // ----------------------------------------------
        reqEntity.fromRecordNum = null;
        reqEntity.toRecordNum = null;
        List<OperationLogListEntity> OperationLogList = OperationLogService.getOperationLogList(reqEntity);

        // Csvファイル用のヘッダ情報（ComCsvItem）設定
        // ==============================================
        // カラム設定
        // ==============================================
        List<ComCsvItem> listCsvItem = new ArrayList<ComCsvItem>();
        listCsvItem.add(new ComCsvItem("IL-006-01-003", "time", false));
        listCsvItem.add(new ComCsvItem("IL-006-01-004", "userId", false));
        listCsvItem.add(new ComCsvItem("IL-006-01-005", "logCode", true));
        listCsvItem.add(new ComCsvItem("IL-006-01-006", "logName", false));
        listCsvItem.add(new ComCsvItem("IL-006-01-007", "logText", false));
        // Step4 2017_08_24：#シングルクォーテーションをつけないためtrueをfalseに修正
        listCsvItem.add(new ComCsvItem("IL-006-01-008", "client", false));

        // #########################################
        // CSV出力
        // #########################################
        ComCsvOut<OperationLogListEntity> csvOut = new ComCsvOut<OperationLogListEntity>();

        String fileName = "OperationLog.csv";
        String titleKey = "IL-006-01-001"; // 画面名
        String searchCond = sbHeader.toString();
        csvOut.csvOut(res, messageSource, locale, fileName, titleKey, searchCond, listCsvItem, OperationLogList);
    }
}
