//@formatter:off
/**
 ******************************************************************************
 * @file        AmhsModel.java
 * @brief       AMHSマスタメンテナンス用モデル
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

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSマスタメンテナンス用モデルクラス
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
public class AmhsModel {

    private String amhsId;

    private String driverServiceId;

    private String amhsName;

    private String amhsModel;

    private String amhsType;

    private Short displayFlag;

    private Short timeSyncFlag;

    private String nicAddress;

    private String connectAddress;

    private Integer connectPort;

    private Integer hsmsDeviceId;

    private Short hsmsT3;

    private Short hsmsT5;

    private Short hsmsT6;

    private Short hsmsT7;

    private Short hsmsT8;

    private String hsmsMode;

    private Short linktestTimer;

    private String amhsGrpId;

    private String defaultTransGrpId;

    private Short dummyFlag;

    private String totalShelves;

    private Short microCommandLimit;

    private String availability;

    private String systemState;

    private String alarmState;

    private String communicationState;

    private String controlState;

    private String amhsLState;

    private Timestamp setTime;

    private String setAuthor;

    private String shapeId;

    private Short funcOpt;
}
