//@formatter:off
/**
 ******************************************************************************
 * @file        OpeLogService.java
 * @brief       操作ログ関連のサービス
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
 * 2019/02/26 MACS4#0109  HistSvr機能修正(GUI)                        T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.common.LogCodeMasterEntity;
import net.muratec.mcs.mapper.LogCodeMasterMapper;
import net.muratec.mcs.mapper.OperationHistoryMapper;   // MACS4#0109 Add
//import net.muratec.mcs.mapper.OperationLogMapper;     // MACS4#0109 Del
import net.muratec.mcs.model.LogCodeMaster;
import net.muratec.mcs.model.OperationHistoryModel;     // MACS4#0109 Add
//import net.muratec.mcs.model.OperationLog;            // MACS4#0109 Del

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ関連のサービスクラス
 * @par       機能:
 *              getOpeLog（操作ログ格納処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0109  HistSvr機能修正(GUI)                                   T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Service
public class OpeLogService extends BaseService {

    @Autowired private LogCodeMasterMapper logCodeMasterMapper;
//  @Autowired private OperationLogMapper opeLogMapper;             // MACS4#0109 Del
    @Autowired private OperationHistoryMapper opeHistMapper;        // MACS4#0109 Add
    @Autowired private PlatformTransactionManager transactionManager;

    private static final Logger logger = LoggerFactory.getLogger(OpeLogService.class);
//    private static final String TABLE_NAME = "OPERATION_HISTORY_";  // MACS4#0109 Add

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     操作ログ格納処理
     * @param     opeLogCode      ログコード
     * @param     strMcsArg       画面からの引数
     * @param     userId          ユーザID
     * @param     ipAddress       クライアントIPアドレス
     * @return    アカウント情報
     * @retval
     * @attention
     * @note      LOG_CODE_MASTERからログのcodeとテキストのフォーマットを取得し変換。
     *             OperationLogテーブルに格納する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0109  HistSvr機能修正(GUI)                                   T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public LogCodeMasterEntity getOpeLog(Long opeLogCode, String strMcsArg, String userId, String ipAddress) {

        logger.debug("LogCode[" + opeLogCode + "] Args[" + strMcsArg + "] UserId[" + userId + "] IpAddress[" + ipAddress
                + "].");

        LogCodeMaster logCodeMasterRec = null;
        LogCodeMasterEntity retRec = new LogCodeMasterEntity();

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try {

            // LOG_CODE_MASTERからlogCodeの値を取得する
            logCodeMasterRec = logCodeMasterMapper.selectByPrimaryKey(opeLogCode);

            if (logCodeMasterRec == null) {
                logger.error("LogCode[" + opeLogCode + "] not found[LogCodeMaster].");
                transactionManager.rollback(status);
                return null;
            }

            // EntityにDBから取得した値を格納する
            retRec.logCode = logCodeMasterRec.getLogCode();
            retRec.logName = logCodeMasterRec.getLogName();
            retRec.logTextFormat = logCodeMasterRec.getLogTextFormat();

            // LOG_TEXT_FORMAT値の[%]を変換し、引数を追加する
            String re_logTextFormat = String.format(logCodeMasterRec.getLogTextFormat(), strMcsArg);
            retRec.logTextFormat = re_logTextFormat;

//          OperationLog opeLog = new OperationLog();   // MACS4#0109 Del
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            List<String> log = ComFunction.makeLogStringList(retRec.logTextFormat);
            // MACS4#0109 Add Start
            //SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            //String targetTable = TABLE_NAME + sdf.format(timestamp);
            OperationHistoryModel opeHist = new OperationHistoryModel();
            // MACS4#0109 Add End

            // 256byteずつDBに格納する
            for (int i = 0; i < log.size(); i++) {
                String s_LogText = (String) log.get(i);
                // MACS4#0109 Del Start
//              opeLog.setTime(timestamp);
//              opeLog.setUserId(userId);
//              opeLog.setLogCode(opeLogCode);
//              opeLog.setLogText(s_LogText);
//              opeLog.setClient(ipAddress);
//              opeLogMapper.insert(opeLog);
                // MACS4#0109 Del End
                // MACS4#0109 Add Start
                opeHist.setTime(timestamp);
                opeHist.setUserId(userId);
                opeHist.setLogCode(opeLogCode);
                opeHist.setLogText(s_LogText);
                opeHist.setClient(ipAddress);
                //opeHist.setTargetTable(targetTable);
                //opeHistMapper.insertTargetDate(opeHist);
                opeHistMapper.insertTargetDatev2(opeHist);
                // MACS4#0109 Add End
            }

            // コミット
            transactionManager.commit(status);
        } catch (Throwable e) {
            logger.error("OpeLogService.getOpeLog Error.", e);

            // ロールバック
            transactionManager.rollback(status);
        }

        return retRec;
    }

}
