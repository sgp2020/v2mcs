//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleStatisticsHistoryEntity.java
 * @brief       空FOUP管理関連のエンティティ
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/15   v1.0.0      初版作成                                          SGP
 ******************************************************************************
 */
//@formatter:on

package net.muratec.mcs.entity.hist;

import java.math.BigDecimal;

//@formatter:off
/**
 ******************************************************************************
 * @brief    空FOUP管理関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.                 DESCRIPTION                                 AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 			VehicleStatisticsHistoryEntity			    SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
public class VehicleStatisticsHistoryEntity {

	public int rn;
	public String time;
	public String tscId;
	public String tscName;
	public String vehicleId;
	public String assignWaitMaxTime;
	public String assignWaitMinTime;
	public String assignWaitAveTime;
	public String activeWaitMaxTime;
	public String activeWaitMinTime;
	public String activeWaitAveTime;
	public String activeTotalTime;
	public String idleTime;
	public String totalUpTime;
	//public BigDecimal downTime;
	public String downTime;
	public BigDecimal opeRate;
	public String transferCount;
	public String errorCount;
	public String mCBF;
	
	public long activeTotalTime1;
	public long transferCount1;
	public long errorCount1;
	public long mCBF1;
	public long totalUpTime1;
	public long idleTime1;
}
