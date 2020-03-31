//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleInfoListEntity.java
 * @brief       ビックル情報画面連携用エンティティ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION                AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12   1                                      天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ビックル情報画面連携用エンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class VehicleInfoListEntity {

    public int rn;
    public String vehicleId;
    public String vehicleState;
    public String currentTscId;
    public String currentLoc;
    public String assignedCarrierId;
    public String assignedCommandId;
    public String sourceLoc;
    public String destLoc;
    public String totalCount;
}
