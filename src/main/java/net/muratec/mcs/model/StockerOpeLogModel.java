//@formatter:off
/**
 ******************************************************************************
 * @file        AmhsModel.java
 * @brief       AMHSマスタメンテナンス用モデル
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSマスタメンテナンス用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class StockerOpeLogModel {

	public int rn;
	public String time;
	public String tscId;
	public int assignWaitMaxTime;
	public int assignWaitMinTime;
	public int assignWaitAveTime;
	public int activeWaitMaxTime;
	public int activeWaitMinTime;
	public int activeWaitAveTime;
	public int activeTotalTime;
	public long idleTime;
	public long totalUpTime;
	public long downTime;
	public long opeRate;
	public int transferCount;
	public int errorCount;
	public int mCBF;
}
