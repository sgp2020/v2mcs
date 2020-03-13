//@formatter:off
/**
 ******************************************************************************
 * @file        ReqAmhsConfigurationEntity.java
 * @brief       AMHSマスタメンテナンス関連のエンティティ
 * * @par
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
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    AMHSマスタメンテナンス関連のエンティティクラス
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
public class ReqAmhsConfigurationEntity extends AjaxReqBaseEntity {

    public String amhsId = null;
    public String driverServiceId = null;
    public String amhsName = null;
    public Short amhsModel = null;
    public Short amhsType = null;
    public Boolean displayFlag = null;
    public Boolean timeSyncFlag = null;
    public String nicAddress = null;
    public String connectAddress = null;
    public Integer connectPort = null;
    public Integer hsmsDeviceId = null;
    public Short hsmsT3 = null;
    public Short hsmsT5 = null;
    public Short hsmsT6 = null;
    public Short hsmsT7 = null;
    public Short hsmsT8 = null;
    public Short hsmsMode = null;
    public Short linktestTimer = null;
    public String amhsGrpId = null;
    public String defaultTransGrpId;
    public Boolean dummyFlag;
    public Short microCommandLimit = null;
    public Short totalShelves = null;
    public Short amhsLState = null;
    public Short shapeId = null;
    public Boolean priUpdateSendSetting = null;
}
