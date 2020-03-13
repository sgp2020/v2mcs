//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierListEntity.java
 * @brief       キャリア情報画面連携用エンティティ
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
 * 2018/10/23 MACS4#0021  LotID,ProductID表示                         T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報画面連携用エンティティクラス
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
public class CarrierListEntity {

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
