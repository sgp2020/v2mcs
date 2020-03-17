//@formatter:off
/**
 ******************************************************************************
 * @file        EmptyCarrierModel.java
 * @brief       空FOUP管理用モデル
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
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     空FOUP管理用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class StockerInfoModel  {

    private int num = 0;
    private int tscId = 0;
    private String zoneId = "";
    private String tscAbbreviation = "";
    private int lOccupancy = 0;
    private int occupancy = 0;
    private int usedCell = 0;
    private int totalShelves = 0;
    private int lowWaterMark = 0;
    private int highWaterMark = 0;


}
