//@formatter:off
/**
 ******************************************************************************
 * @file        TestCarrierModel.java
 * @brief       テストキャリアマスタメンテナンス一覧連携用モデル
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

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     テストキャリアマスタメンテナンス一覧連携用モデルクラス
 * @par
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
public class TestCarrierModel {

    public String carrierId;

    public String description;

    public String carrierState;

    public String prevCarrierState;

    public String carrierTypeId;

    public String carrierShapeId;

    public String currentAmhsId;

    public String currentLoc;

    public String hostZoneId;

    public String shelfNo;

    public String prevAmhsId;

    public String prevCarrierLoc;

    public Timestamp storedTime;

    public String carrierEmpty;

    public Timestamp waitInTime;

    public Timestamp waitOutTime;

    public String insystemCarrierId;

    public Timestamp insystemTime;

    public String carrierAlarm;

    public String passingMode;

    public String carrierActionCtrl;

    public Timestamp carrierActionTime;

    public Short cleanRequest;

    public String contamiId;

    public String puroposeType;

    public String mesMessege;

    public Timestamp setTime;
}
