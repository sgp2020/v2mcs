//@formatter:off
/**
 ******************************************************************************
 * @file        StockerInfoListEntity.java
 * @brief       
 * @par
 * @author       天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/11 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on

package net.muratec.mcs.entity.system;

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
 * 2020/03/11  StokerZoneRltEntity							            董 天津村研
 ******************************************************************************
 */
//@formatter:on
public class ModeSetTSCListEntity {

	public int rum;
	public String tscName;
	public String zoneId;
	public int lOccupancy;
	public int occupancy;
	public int usedCell;
	public int totalShelves;
	public int lowWaterMark;
	public int highWaterMark;
	public String tscAbbreviation;

}
