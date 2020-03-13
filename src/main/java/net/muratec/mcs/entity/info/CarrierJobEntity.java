//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierJobEntity.java
 * @brief       キャリアJOB作成用エンティティ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリアJOB作成用エンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class CarrierJobEntity {

    public String carrierJobId;

    public String entityJob;

    public Short priority;

    public String fromControllerId;

    public String fromPortId;

    public String toControllerId;

    public String toPortId;

    public String carrierId;

    public String zoneId;

}
