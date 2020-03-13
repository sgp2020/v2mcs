//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorStateInfo.java
 * @brief       個別モニタ_状態画面(状態)用モデル
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
package net.muratec.mcs.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ_状態画面(状態)用モデルクラス
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
public class IndividualMonitorStateInfo {

    private String commState = null;
    private String controlState = null;
    private String systemState = null;
    private String available = null;
    private Short zoneOccupied = null;
    private Short zoneCapacity = null;
    private BigDecimal zoneUtility = null;
    private String amhsLState = null;
}
