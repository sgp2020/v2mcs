//@formatter:off
/**
 ******************************************************************************
 * @file        SpringContext.java
 * @brief       SpringFrameworkよりApplicationContextの設定・取得を行う
 * @par
 * @author      CSC
 * $Id:         $
 * @attention   SpringFrameworkの設定で本クラスを利用するよう設定が必要
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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ApplicationContextの設定・取得
 * @par       機能:
 *              SpringContext（デフォルトコンストラクタ）
 *              getApplicationContext（ApplicationContextゲッター）
 *              setApplicationContext（ApplicationContextセッター）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     デフォルトコンストラクタ
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
    public SpringContext() {
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ApplicationContextゲッター
     * @param
     * @return    ApplicationContext
     * @retval
     * @attention
     * @note      SpringFrameworkが介在しApplicationContextをゲットする
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static final ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ApplicationContextセッター
     * @param     applicationContext セットしたいapplicationContext
     * @return
     * @retval
     * @attention
     * @note      SpringFrameworkが介在しApplicationContextをセットする
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        // FindBugsでエラーが出るが、SpringFrameworkの仕様のため無視する。
        SpringContext.applicationContext = applicationContext;
    }

}
