//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierDispListEntity.java
 * @brief       キャリア情報画面表示用連携用エンティティ
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
 * 2017/10/31 0.6         Step4リリース                               T.Iga/CSC
 * 2018/10/23 MACS4#0021  LotID,ProductID表示                         T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報画面表示用連携用エンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
public class CarrierDispListEntity {

    public String carrierId;
    public String status;
    public String carrierState;
    public String currentTscId;
    public String currentLocation;
    public String currentZone;
    public String lastStoredTime;
    public String block;
    public String insystemCarrierID;
    public String lastInsystemTime;
    public String insystemLocation;
    public String nextDestination;
    public String priority;
    public String offline;
    
}
