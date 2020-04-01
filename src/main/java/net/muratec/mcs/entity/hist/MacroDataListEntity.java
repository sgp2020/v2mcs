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
public class MacroDataListEntity {

	public int rum;
	public String orgCarrierId;
	public String orgRcvTime;
	public String orgStartTime;
	public String orgCmpTime;
	public int orgSrcTscId;
	public String orgSrcLoc;
	public int orgDstTscId;
	public String orgDstLoc;
	public int altTscId;
	public String altLoc;
	public int status;
	public int orgPriority;
	public int cancelFlg;
	public String orgDstGroup;
	public String time;
	public String orgHostCommandId;
	public String orgCommandId;
	public String orgOriginator;
	public int rerouteReq;

}
