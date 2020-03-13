//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorStateAlarm.java
 * @brief       個別モニタ_状態画面(アラーム一覧)用モデル
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

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ_状態画面(アラーム一覧)用モデルクラス
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
public class IndividualMonitorStateAlarm {

    private Timestamp setTime = null;
    private String alarmId = null;
    private String alarmText = null;
}
