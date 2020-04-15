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
 * 2020.03.25 			AtomicActivityHistListEntity				       DONG
 ******************************************************************************
 */
//@formatter:on
public class SystemLogEntity {

	public int rum;
	public String time;
	public String carrierId;
	public String tscAbbreviation;
	public String source;
	public String destination;
	public String statusStr;
	public int priority;
	public int routeNo;
	public String queuedTime;
	public String leadTime;
	public String totalTime;
	public String vehicleId;
	public String commandId;
	public String atomicRequestTime;
	public String atomicAnswerTime;
	public String atomicInitiateTime;
	public String atomicAcquiredTime;
	public String atomicCompleteTime;
	public String abortRequestTime;
	public String abortAnswerTime;
	public String abortInitiateTime;
	public String abortCompleteTime;
	public String abortReason;
	
	public String atomicTime;
	public String tscId;
	public String srcLoc;
	public String dstLoc;
	public String originator;
	public String loadedTime;
	

}
