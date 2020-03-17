//@formatter:off
/**
 ******************************************************************************
 * @file        EmptyCarrierListEntity.java
 * @brief       空FOUP管理関連のエンティティ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 * 2019/02/19 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on

package net.muratec.mcs.entity.info;

//@formatter:off
/**
 ******************************************************************************
 * @brief    空FOUP管理関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			StokerZoneRltEntity							          董 天津村研
 ******************************************************************************
 */
//@formatter:on
public class StockerInfoListEntity {

	public int rum;
	public int tscId;
	public String zoneId;
	public int lOccupancy;
	public int occupancy;
	public int usedCell;
//	public int totalCell;
	public int totalShelves;
	public int lowWaterMark;
	public int highWaterMark;
	public String tscAbbreviation;

}
