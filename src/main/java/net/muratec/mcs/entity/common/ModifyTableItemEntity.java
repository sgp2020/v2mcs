//@formatter:off
/**
 ******************************************************************************
 * @file        ModifyTableItemEntity.java
 * @brief      モディファイテーブルエンティティ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

import net.muratec.mcs.common.ComConst;

//@formatter:off
/**
 ******************************************************************************
 * @brief     モディファイテーブルエンティティ
 * @par      モディファイテーブルエンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ModifyTableItemEntity {

    // ------------------------------------
    // 項目ID
    // ------------------------------------
    public String itemId = "";

    // ------------------------------------
    // 項目名
    // ------------------------------------
    public String itemName = "";

    // ------------------------------------
    // 表示/非表
    // ------------------------------------
    public int itemDisplay = ComConst.ColumnDisplay.DISP;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ModifyTableEntity ");
        sb.append(" itemId=").append(itemId);
        sb.append(" itemName=").append(itemName);
        sb.append(" itemDisplay=").append(itemDisplay);
        return sb.toString();
    }
}
