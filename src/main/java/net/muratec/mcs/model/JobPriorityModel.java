//@formatter:off
/**
 ******************************************************************************
 * @file        JobPriorityModel.java
 * @brief       搬送Job優先順位しきい値設定 一覧連携用モデルクラス
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

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送Job優先順位しきい値設定 一覧連携用モデル
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
public class JobPriorityModel {

    public Short thresholdPriority;
    public Boolean useAccelerator;
    public Short limitPriority;
    public Integer timeLimit;
    public Long overtimeAlarmId;
    public Integer ultimateTime;
    public String description;
    public String hotlotCategory;
}
