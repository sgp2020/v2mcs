//@formatter:off
/**
 ******************************************************************************
 * @file        OperationLogService.java
 * @brief       操作ログ表示画面のサービス
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
 * 2019/02/27 MACS4#0109  HistSvr機能修正(GUI)                        T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.log.OperationLogListEntity;
import net.muratec.mcs.entity.log.ReqGetOperationLogListEntity;
import net.muratec.mcs.mapper.OperationHistoryMapper;       // MACS4#0109 Add
//import net.muratec.mcs.mapper.OperationLogMapper;         // MACS4#0109 Del
import net.muratec.mcs.model.OperationLogModel;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ表示画面のサービスクラス
 * @par       機能:
 *              getCount（操作ログ表示一覧の全レコード数を取得）
 *              escapeSearchCond（あいまい検索用に検索条件をエスケープする）
 *              getOperationLogList（操作ログ表示一覧の取得）
 *              createCaption（キャプションの値を取得）
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
public class OperationLogService extends BaseService {

    // ------------------------------------
    // 操作ログ表示マッパー
    // ------------------------------------
//  @Autowired private OperationLogMapper operationlogMapper;           // MACS4#0109 Del
    @Autowired private OperationHistoryMapper operationlogMapper;       // MACS4#0109 Add

    // ------------------------------------
    // メッセージリソース
    // ------------------------------------
    @Autowired private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(OperationLogService.class);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount（操作ログ表示一覧の全レコード数を取得）機能
     * @param     reqEntity      リクエスト（検索条件）
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致した操作ログ表示一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetOperationLogListEntity reqEntity) {

        return operationlogMapper.getCount(reqEntity);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     あいまい検索用に検索条件をエスケープする機能
     * @param     reqEntity      画面項目情報
     * @return
     * @retval
     * @attention
     * @note      *をあいまいにする。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void escapeSearchCond(ReqGetOperationLogListEntity reqEntity) {

        reqEntity.userId = ComFunction.escapeForLike(reqEntity.userId);
        reqEntity.logCode = ComFunction.escapeForLike(reqEntity.logCode);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOperationLogList（操作ログ表示一覧の取得）機能
     * @param     reqEntity      リクエスト（検索条件）
     * @return    操作ログ表示一覧
     * @retval    操作ログ表示のLIST形式で返却
     * @attention
     * @note      検索条件に一致した操作ログ表示一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<OperationLogListEntity> getOperationLogList(ReqGetOperationLogListEntity reqEntity) {

        List<OperationLogModel> recList = null;
        recList = operationlogMapper.selectList(reqEntity);

        if (recList == null) {
            return null;
        }

        List<OperationLogListEntity> retRecList = new ArrayList<OperationLogListEntity>();
        for (OperationLogModel operationlog : recList) {
            OperationLogListEntity entity = new OperationLogListEntity();
            entity.time = ComFunction.dateToString(operationlog.getTime());
            entity.userId = operationlog.getUserId();
            entity.logCode = operationlog.getLogCode();
            entity.logName = operationlog.getLogName();
            entity.logText = operationlog.getLogText();
            entity.client = operationlog.getClient();

            retRecList.add(entity);
        }

        return retRecList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     createCaption（操作ログ表示一覧のキャプション生成）機能
     * @param     recList      取得一覧レコード
     * @return    キャプションの値
     * @retval    String形式で返却
     * @attention
     * @note      取得した一覧レコードから最大登録時刻と最小登録時刻を求め、
     *             キャプションの形式で返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String createCaption(List<OperationLogListEntity> recList) {

        if (recList == null || recList.size() == 0) {
            return null;
        }

        // データの最大登録時刻と最小登録時刻を求める。
        Date maxTime = ComFunction.stringToDate(recList.get(0).time);
        Date minTime = ComFunction.stringToDate(recList.get(0).time);
        for (int i = 1; i < recList.size(); i++) {
            Date time = ComFunction.stringToDate(recList.get(i).time);
            if (time.after(maxTime)) {
                maxTime = time;
            }
            if (time.before(minTime)) {
                minTime = time;
            }
        }
        return ComFunction.dateToString(minTime) + " ---- " + ComFunction.dateToString(maxTime);
    }
}
