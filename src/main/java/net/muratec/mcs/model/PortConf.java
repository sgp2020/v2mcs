//@formatter:off
/**
 ******************************************************************************
 * @file        PortConf.java
 * @brief       ポート構成用モデル
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ポート構成用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class PortConf {

    private String portId;
    private String portType;
    private String idrwType;
    private String idrwMode;
    private Boolean dummyFlag;
    private String amhsId;
    private String pgvMode;
    private String samePortId;
    private String ioMode;
    private String phase;
    private String passingMode;
    private String connCtrl;
    private String carrierShape;
    private String portLState;
    private Timestamp portLStateSetTime;
    private String portLStateSetAuthor;
    private String portAvail;
    private String ibsemAvail;
    private String idrwAvail;
    private BigDecimal idErrorCount;
    private BigDecimal dplErrorCount;
    private BigDecimal incorrectCount;
    private BigDecimal e84ErrorCount;
    private Short expectedduration;
    private Short noblockingtime;
    private Short waittimeout;

}
