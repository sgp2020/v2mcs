//@formatter:off
/**
 ******************************************************************************
 * @file        RouteSearchModel.java
 * @brief       ルート検索マスタメンテナンス 一覧連携用モデル
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
 * @brief     ルート検索マスタメンテナンス 一覧連携用モデルクラス
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
public class RouteSearchModel {

    public String amhsId;
    public String srcLoc;
    public String dstLoc;
    public Long passedCount;
    public BigDecimal waitTimeAve;
    public BigDecimal waitTimeStd;
    public BigDecimal waitTimeSqr;
    public BigDecimal execTimeAve;
    public BigDecimal execTimeStd;
    public BigDecimal execTimeSqr;
    public Long execTimeMin;
    public Long execTimeMax;
    public Long defTimeMin;
    public Long defTimeMax;
    public String modifyFlag;
    public Timestamp lastModifiedTime;
    public String srcLocType;
    public String dstLocType;
}
