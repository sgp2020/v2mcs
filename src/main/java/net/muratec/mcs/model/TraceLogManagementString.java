//@formatter:off
/**
 ******************************************************************************
 * @file        TraceLogManagementString.java
 * @brief       トレースログ管理マスタメンテナンス （logLevelの型変換用モデル）
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
 * 2017/12/20 0.6         Step4phase2リリース                               CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

public class TraceLogManagementString {

    /**
     * This field corresponds to the database column MCS_STEP2_1.TRACE_LOG_LEVEL.PROC_NAME
     */
    private String processName;
    /**
     * This field corresponds to the database column MCS_STEP2_1.TRACE_LOG_LEVEL.LOG_LEVEL
     */
    private String logLevel;
    /**
     * This field corresponds to the database column MCS_STEP2_1.TRACE_LOG_LEVEL.INTERVAL
     */
    private Short interval;
    /**
     * This field corresponds to the database column MCS_STEP2_1.TRACE_LOG_LEVEL.DIRECTORY
     */
    private String outputDirectory;

    /**
     * This method returns the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.PROC_NAME
     * @return the value of MCS_STEP2_1.TRACE_LOG_LEVEL.PROC_NAME
     */
    public String getProcessName() {

        return processName;
    }

    /**
     * This method sets the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.PROC_NAME
     * @param processName the value for MCS_STEP2_1.TRACE_LOG_LEVEL.PROC_NAME
     */
    public void setProcessName(String processName) {

        this.processName = processName;
    }

    /**
     * This method returns the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.LOG_LEVEL
     * @return the value of MCS_STEP2_1.TRACE_LOG_LEVEL.LOG_LEVEL
     */
    public String getLogLevel() {

        return logLevel;
    }

    /**
     * This method sets the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.LOG_LEVEL
     * @param logLevel the value for MCS_STEP2_1.TRACE_LOG_LEVEL.LOG_LEVEL
     */
    public void setLogLevel(String logLevel) {

        this.logLevel = logLevel;
    }

    /**
     * This method returns the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.INTERVAL
     * @return the value of MCS_STEP2_1.TRACE_LOG_LEVEL.INTERVAL
     */
    public Short getInterval() {

        return interval;
    }

    /**
     * This method sets the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.INTERVAL
     * @param interval the value for MCS_STEP2_1.TRACE_LOG_LEVEL.INTERVAL
     */
    public void setInterval(Short interval) {

        this.interval = interval;
    }

    /**
     * This method returns the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.DIRECTORY
     * @return the value of MCS_STEP2_1.TRACE_LOG_LEVEL.DIRECTORY
     */
    public String getOutputDirectory() {

        return outputDirectory;
    }

    /**
     * This method sets the value of the database column MCS_STEP2_1.TRACE_LOG_LEVEL.DIRECTORY
     * @param outputDirectory the value for MCS_STEP2_1.TRACE_LOG_LEVEL.DIRECTORY
     */
    public void setOutputDirectory(String outputDirectory) {

        this.outputDirectory = outputDirectory;
    }

}
