//@formatter:off
/**
 ******************************************************************************
 * @file        WebSessionListener.java
 * @brief       セッション作成・破棄時に実行させたい処理
 * @par
 * @author      CSC
 * $Id:         $
 * @attention   web.xmlにListenerの登録が必要
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/12/31 0.7         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//@formatter:off
/**
 ******************************************************************************
 * @brief     セッション作成/破棄時処理
 * @par       機能:
 *              sessionCreated（セッション作成処理）
 *              sessionDestroyed（セッション破棄処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class WebSessionListener implements HttpSessionListener{
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     セッション作成時処理
     * @param     arg0       セッションイベント
     * @return
     * @retval
     * @attention
     * @note      セッション作成時に実施する処理
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        // セッション作成時には特に動作なし
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     セッション破棄時処理
     * @param     arg0       セッションイベント
     * @return
     * @retval
     * @attention
     * @note      セッション破棄時に実施する処理
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        HttpSession session = arg0.getSession();

       // AMHS通信ログの出力データ削除
        @SuppressWarnings("unchecked")
        List<String> amhsLogList = (List<String>) session.getAttribute(ComConst.SessionKey.AMHS_LOG_FILE_PATH);
        if (amhsLogList != null && amhsLogList.size() > 0) {
            for (String strTarget : amhsLogList) {

                File file = new File(strTarget);
                file.delete();
            }
        }

        // HOST通信ログの出力データ削除
        @SuppressWarnings("unchecked")
        List<String> hostLogList = (List<String>) session.getAttribute(ComConst.SessionKey.HOST_LOG_FILE_PATH);
        if (hostLogList != null && hostLogList.size() > 0) {
            for (String strTarget : hostLogList) {

                File file = new File(strTarget);
                file.delete();
            }
        }
    }
}
