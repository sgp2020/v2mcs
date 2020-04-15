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
 * @brief    JobStatisticsHistory
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.09 			JobStatisticsHistoryListEntity				       DONG
 ******************************************************************************
 */
//@formatter:on
public class JobStatisticsHistoryListEntity {

	public int rum;
	public String time;
	public String tscId;
	public String sourceLoc;
	public String destLoc;
	public String maxTime;
	public String minTime;
	public String avgTime;
	public String totalTime;
	public int opeCount;
	public int errCount;
	public int mcbf;
}
