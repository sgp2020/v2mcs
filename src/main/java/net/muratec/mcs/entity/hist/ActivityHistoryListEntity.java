//@formatter:off
/**
 ******************************************************************************
 * @file        ActivityHistoryListEntity.java
 * @brief       
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/07 v1.0.0      初版作成                                         SGP
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
 * VER.             DESCRIPTION                              AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 	     ActivityHistListEntity				      SGP
 ******************************************************************************
 */
//@formatter:on
public class ActivityHistoryListEntity {

	public int rum;
	public String rcvTime;
	public String carrierId;
	public String totalTime;
	public String srcTscId;
	public String srcLoc;
	public String compTscId;
	public String compLoc;
	public String status;
	public String startTime;
	public String cmpTime;
	public String dstGroup;
	public String dstTscId;
	public String dstLoc;
	public String altTscId;
	public String altLoc;
	public int priority;
	public String nextDestination;
	public String cancelReq;
	public String hostCommandId;
	public String commandId;
	public String originator;
	public String rerouteReq;
}
