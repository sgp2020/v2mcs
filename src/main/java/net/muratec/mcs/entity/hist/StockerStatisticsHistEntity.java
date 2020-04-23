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
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.25 			AtomicActivityHistListEntity							          董 天津村研
 ******************************************************************************
 */
//@formatter:on
public class StockerStatisticsHistEntity {

	public int rn;
	public String time;
	public String tscId;
	public String assignWaitMaxTime;
	public String assignWaitMinTime;
	public String assignWaitAveTime;
	public String activeWaitMaxTime;
	public String activeWaitMinTime;
	public String activeWaitAveTime;
	public String activeTotalTime;
	public String idleTime;
	public String totalUpTime;
	public String downTime;
	public String opeRate;
	public String transferCount;
	public String errorCount;
	public String mCBF;
}
