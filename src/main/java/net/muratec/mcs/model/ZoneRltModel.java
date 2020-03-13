//@formatter:off
/**
 ******************************************************************************
 * @file        ZoneRltModel.java
 * @brief       ゾーンリレーショナル構成マスタメンテ 一覧連携用モデル
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
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
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
 * @brief     ゾーンリレーショナル構成マスタメンテ 一覧連携用モデルクラス
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
public class ZoneRltModel {

    private String key;
    private String zoneId;
    private String zoneName;
    private Short zoneCapacity;
    private Short zoneOccupied;
    private Short zoneLEmpty;
    private BigDecimal zoneUtility;
    private Short zoneHwm;
    private Short zoneLwm;
    private String zoneFull;
}
