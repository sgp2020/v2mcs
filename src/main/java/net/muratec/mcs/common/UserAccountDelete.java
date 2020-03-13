//@formatter:off
/**
 ******************************************************************************
 * @file        UserAccountDelete.java
 * @brief       未ログイン期間に応じてユーザのロック/削除を行う
 * @par
 * @author      CSC
 * $Id:         $
 * @attention   SpringFramework外で動作するため、DB操作等を自前で実装
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.muratec.mcs.entity.common.DbConfigEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ユーザのロック/削除を行うクラス
 * @par       機能:
 *              UserAccountDelete（コンストラクタ）
 *              waitSec（Wait処理）
 *              userAccountLockDel（ユーザのロック/削除処理）
 *              stopRunning（スレッド停止処理）
 *              isRunning（スレッド動作確認処理）
 *              dbConnect（DB接続処理）
 *              dbDisConnect（DB切断処理）
 *              setParameter（システムパラメータ読み込み処理）
 *              run（スレッド処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class UserAccountDelete extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountDelete.class);

    // ! 実行SQL
    private static final String SELECT_MCS_CONSTS = "SELECT VALUE FROM MCS_CONSTS WHERE CATEGORY=? AND SECTION=? AND KEY=?";
    private static final String UPDATE_USER_ACCOUNT = "UPDATE USER_ACCOUNT SET ACCOUNT_STATE = ? WHERE USER_NAME NOT IN(?, ?, ?) AND ACCOUNT_STATE = ? AND to_char((LAST_LOGIN),'yyyymmddhh24missff2') < to_char((sysdate - ?),'yyyymmddhh24miss') || '00'";
    private static final String DELETE_USER_ACCOUNT = "DELETE FROM USER_ACCOUNT WHERE USER_NAME NOT IN(?, ?, ?) AND to_char((LAST_LOGIN),'yyyymmddhh24missff2') < to_char((sysdate - ?),'yyyymmddhh24miss') || '00'";

    // ! スリープタイム
    private int sleepTime = 60;
    // ! ユーザロック日時
    private int lockDay = 0;
    // ! 削除日時
    private int delDay = 0;

    private boolean loopFlag = true;
    private DbConfigEntity dbConfig = null;
    private Connection conn = null;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コンストラクタ
     * @param     dbConfig      DB接続情報
     * @return
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public UserAccountDelete(DbConfigEntity dbConfig) {
        this.dbConfig = dbConfig;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定された秒数Waitする
     * @param     execTime      処理実行時間
     * @return
     * @retval
     * @attention
     * @note      1秒ごとにWaitしループフラグの確認を行う。
     *            waitする時間は引数の処理時間を考慮する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void waitSec(int execTime) {

        int waitTime = sleepTime;
        if ((execTime > 0) && (waitTime > execTime)) {
            waitTime = waitTime - execTime;
        }

        for (int i = waitTime; i > 0; i--) {
            if (!loopFlag) {
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Sleep Interrupted Exception.", e);
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザーアカウントのロックと削除処理
     * @param
     * @return
     * @retval
     * @attention
     * @note      ロック対象のユーザーアカウントのロック（UPDATE）を行う。
     *            削除対象のユーザーアカウントの削除（DELETE）を行う。
     *            処理成功時にはcommitを行う。
     *            処理失敗時にはrollbackを行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void userAccountLockDel() {

        // 現在日時取得
        Calendar c = Calendar.getInstance();

        // フォーマットを指定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String procDate = sdf.format(c.getTime());

        logger.debug("############ UserAccount Lock & Delete!!! #######[" + procDate + "]");

        if (conn == null) {
            logger.warn("DB DisConnect.");
            return;
        }

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        try {
            conn.setAutoCommit(false);

            // 自動ロック処理
            if (lockDay >= 1) {
                pstmt1 = conn.prepareStatement(UPDATE_USER_ACCOUNT);
                pstmt1.setInt(1, ComConst.AccountState.LOCK);               // SET区(AccountState)
                pstmt1.setString(2, ComConst.ConstUserId.NOLOGIN);          // NOT IN区1
                pstmt1.setString(3, ComConst.ConstUserId.ADMIN);            // NOT IN区2
                pstmt1.setString(4, ComConst.ConstUserId.ADMINISTRATOR);    // NOT IN区3
                pstmt1.setInt(5, ComConst.AccountState.UNLOCK);             // WHERE区(AccountState)
                pstmt1.setInt(6, lockDay);                                  // WHERE区(sysdate-xxx)
                int count = pstmt1.executeUpdate();
                logger.debug("############ UserAccount Lock [" + count + "]user. ###");
            } else {
                logger.debug("############ UserAccount Lock Skip(lockDay=" + lockDay + ") . ###");
            }

            // 自動削除処理
            if (delDay >= 1) {
                pstmt2 = conn.prepareStatement(DELETE_USER_ACCOUNT);
                pstmt2.setString(1, ComConst.ConstUserId.NOLOGIN);          // NOT IN区1
                pstmt2.setString(2, ComConst.ConstUserId.ADMIN);            // NOT IN区2
                pstmt2.setString(3, ComConst.ConstUserId.ADMINISTRATOR);    // NOT IN区3
                pstmt2.setInt(4, delDay);                                   // WHERE区(sysdate-xxx)
                int count = pstmt2.executeUpdate();
                logger.debug("############ UserAccount Delete [" + count + "]user. ###");
            } else {
                logger.debug("############ UserAccount Delete Skip(delDay=" + delDay + ") . ###");
            }

            // コミット
            conn.commit();

        } catch (SQLException e) {
            try {
                logger.error("DB Accsess Error and Rollback.", e);
                conn.rollback();
            } catch (SQLException e1) {
                logger.warn("DB Rollback Error.", e1);
            }
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                    pstmt1 = null;
                } catch (SQLException e) {
                    logger.warn("PreparedStatement1 Close ERROR.", e);
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                    pstmt2 = null;
                } catch (SQLException e) {
                    logger.warn("PreparedStatement2 Close ERROR.", e);
                }
            }
        }

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     スレッドを停止させる
     * @param
     * @return
     * @retval
     * @attention
     * @note      スレッドを停止させるため、ループフラグをFlaseにする。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void stopRunning() {

        loopFlag = false;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     スレッドの稼働状態を確認する
     * @param
     * @return    スレッドの稼働状態
     * @retval    true  稼働中
     * @retval    false 停止中
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isRunning() {

        return loopFlag;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief    DBへ接続する
     * @param
     * @return
     * @retval
     * @attention
     * @note      DB接続情報を元に、DBに接続する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void dbConnect() {

        if (this.dbConfig.url != null && this.dbConfig.username != null && this.dbConfig.password != null
                && this.dbConfig.driverClassName != null) {
            try {
                Class.forName(this.dbConfig.driverClassName);
                conn = DriverManager.getConnection(this.dbConfig.url, this.dbConfig.username, this.dbConfig.password);
            } catch (ClassNotFoundException | SQLException e) {
                logger.error("DB Connection Error.", e);
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DBを切断する
     * @param
     * @return
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void dbDisConnect() {

        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                logger.error("DB DisConnection Error.", e);
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SystemParameter取得
     * @param
     * @return
     * @retval
     * @attention DBに接続している必要がある
     * @note      処理に必要なパラメータをSystemParameterより取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setParameter() {

        if (conn == null) {
            logger.warn("DB DisConnect.");
            return;
        }

        PreparedStatement pstmt = null;
        ResultSet result = null;
        try {
            pstmt = conn.prepareStatement(SELECT_MCS_CONSTS);

            // 自動ロック日の取得
            pstmt.clearParameters();
            pstmt.setString(1, ComConst.McsConstsKey.LOGIN_LOCK.getCategory());
            pstmt.setString(2, ComConst.McsConstsKey.LOGIN_LOCK.getSection());
            pstmt.setString(3, ComConst.McsConstsKey.LOGIN_LOCK.getKey());
            result = pstmt.executeQuery();
            String strValue = null;
            if (result.next()) {
                strValue = result.getString(1);
                try {
                    lockDay = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    logger.warn("NumberFormatException(lockDay=[" + strValue + "]).", e);
                    lockDay = 0;
                }
            }
            if (result != null) {
                try {
                    result.close();
                    result = null;
                } catch (SQLException e) {
                    logger.warn("ResultSet Close ERROR.", e);
                }
            }

            // 自動削除日の取得
            pstmt.clearParameters();
            pstmt.setString(1, ComConst.McsConstsKey.LOGIN_DELETE.getCategory());
            pstmt.setString(2, ComConst.McsConstsKey.LOGIN_DELETE.getSection());
            pstmt.setString(3, ComConst.McsConstsKey.LOGIN_DELETE.getKey());
            result = pstmt.executeQuery();
            strValue = null;
            if (result.next()) {
                strValue = result.getString(1);
                try {
                    delDay = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    logger.warn("NumberFormatException(delDay=[" + strValue + "]).", e);
                    delDay = 0;
                }
            }
            if (result != null) {
                try {
                    result.close();
                    result = null;
                } catch (SQLException e) {
                    logger.warn("ResultSet Close ERROR.", e);
                }
            }

            // 自動削除＆ロック処理インターバル（分）の取得
            pstmt.clearParameters();
            pstmt.setString(1, ComConst.McsConstsKey.AUTO_DEL_INTERVAL.getCategory());
            pstmt.setString(2, ComConst.McsConstsKey.AUTO_DEL_INTERVAL.getSection());
            pstmt.setString(3, ComConst.McsConstsKey.AUTO_DEL_INTERVAL.getKey());
            result = pstmt.executeQuery();
            strValue = null;
            if (result.next()) {
                strValue = result.getString(1);
                try {
                    sleepTime = Integer.parseInt(strValue) * 60; // 分から秒に変換
                } catch (NumberFormatException e) {
                    logger.warn("NumberFormatException(sleepTime=[" + strValue + "]).", e);
                    sleepTime = 60; // 60分を設定
                }
            }
            if (result != null) {
                try {
                    result.close();
                    result = null;
                } catch (SQLException e) {
                    logger.warn("ResultSet Close ERROR.", e);
                }
            }

        } catch (SQLException e) {
            logger.error("SQL SELECT ERROR.", e);
        } finally {
            logger.info("Param = [sleepTime=" + sleepTime + "][delDay=" + delDay + "][lockDay=" + lockDay + "]");
            if (result != null) {
                try {
                    result.close();
                    result = null;
                } catch (SQLException e) {
                    logger.warn("ResultSet Close ERROR.", e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (SQLException e) {
                    logger.warn("PreparedStatement Close ERROR.", e);
                }
            }
        }

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     スレッド実行
     * @param
     * @return
     * @retval
     * @attention
     * @note      スレッドで実行する処理を記載
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void run() {

        // ユーザアカウント削除処理を実行
        while (loopFlag) {
            if (conn == null) {
                logger.warn("DB DisConnect.Try DB Connect.");
                dbConnect();
            }
            long beforeTime = System.currentTimeMillis();
            setParameter();
            userAccountLockDel();
            long afterTime = System.currentTimeMillis();
            int execTime = (int) ((afterTime - beforeTime) / 1000);
            waitSec(execTime);
        }

    }

}
