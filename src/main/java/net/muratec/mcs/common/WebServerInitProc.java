//@formatter:off
/**
 ******************************************************************************
 * @file        WebServerInitProc.java
 * @brief       tomcatの起動/停止に実行させたい処理
 * @par
 * @author      CSC
 * $Id:         $
 * @attention   サーブレットの設定ファイルに本処理を記載しておく必要がある
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.muratec.mcs.entity.common.DbConfigEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     tomcat起動/停止時処理
 * @par       機能:
 *              getDbConfig（DB設定ファイルより、DB接続情報を取得）
 *              contextInitialized（Tomcat起動時処理）
 *              contextDestroyed（Tomcat停止時処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class WebServerInitProc implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(WebServerInitProc.class);

    private UserAccountDelete userDel = null;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DB設定ファイルより、DB接続情報を取得し返却
     * @param     context       サーブレットのContext
     * @return    DB接続情報
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private DbConfigEntity getDbConfig(ServletContext context) {

        Properties properties = new Properties();
        String file = context.getRealPath("/WEB-INF/spring/DbConfig.properties");

        try {
            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception ex) {
            logger.error("File[DbConfig.properties] load error.", ex);
            return null;
        }

        // 値の取得
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String driverClassName = properties.getProperty("db.driverClassName");
        String strAutoCommit = properties.getProperty("db.defaultAutoCommit");
        boolean autoCommit = true;
        if (strAutoCommit != null && strAutoCommit.toLowerCase().equals("false")) {
            autoCommit = false;
        }

        DbConfigEntity retConfig = new DbConfigEntity();
        retConfig.url = url;
        retConfig.username = username;
        retConfig.password = password;
        retConfig.driverClassName = driverClassName;
        retConfig.autoCommit = autoCommit;

        return retConfig;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Tomcat起動時処理
     * @param     event        サーブレットのContextEvent
     * @return
     * @retval
     * @attention
     * @note      Tomcat起動に合わせてユーザ自動ロック＆削除処理のスレッドを起動
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void contextInitialized(ServletContextEvent event) {

        // Tomcat起動時処理
        logger.info("################ User Account Lock & Delete Proc Start ##############");
        ServletContext context = event.getServletContext();
        DbConfigEntity dbConfig = this.getDbConfig(context);

        userDel = new UserAccountDelete(dbConfig);
        userDel.dbConnect();
        userDel.start();

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Tomcat停止時処理
     * @param     event        サーブレットのContextEvent
     * @return
     * @retval
     * @attention
     * @note      Tomcat停止に合わせてユーザ自動ロック＆削除処理のスレッドを停止
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        // Tomcatシャットダウン時処理
        logger.info("################ User Account Lock & Delete Proc Shutdown ##############");
        try {
            if (userDel.isRunning()) {
                userDel.stopRunning();
                userDel.join();
            }
            userDel.dbDisConnect();
        } catch (InterruptedException e) {
            logger.error("Thread Stop Error.", e);
        }

        // JDBCドライバリリース
        Collections.list(DriverManager.getDrivers()).forEach(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (final Exception e) {
                logger.error("Deregister Driver Error.", e);
            }
        });

        logger.info("################ User Account Lock & Delete Proc End ##############");
    }

}
