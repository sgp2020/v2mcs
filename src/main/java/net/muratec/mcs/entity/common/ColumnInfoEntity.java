//@formatter:off
/**
 ******************************************************************************
 * @file        ColumnInfoEntity.java
 * @brief       McsDataTables用のカラム情報エンティティ
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
 * @brief     McsDataTables用のカラム情報エンティティ
 * @par      McsDataTables用のカラム情報エンティティの基底クラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ColumnInfoEntity {

    // ------------------------------------
    // カラムID
    // ------------------------------------
    public String columnId = "";

    // ------------------------------------
    // カラム名
    // ------------------------------------
    public String columnName = "";

    // ------------------------------------
    // 表示/非表示/隠しカラム
    // ------------------------------------
    public int columnDisplay = ComConst.ColumnDisplay.DISP;

    // ------------------------------------
    // カラムの文字寄せ位置
    // ------------------------------------
    public String columnAlign = ComConst.ColumnAlign.LEFT;

    // ------------------------------------
    // カラムが主キーかどうか（主キーならtrue）
    // ------------------------------------
    public boolean isUniqueKey = false;

    // ------------------------------------
    // カラムの並び順（1～）
    // ------------------------------------
    public Integer columnOrder = 0;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("ColumnInfoEntity ");
        sb.append(" columnId=").append(columnId);
        sb.append(" columnName=").append(columnName);
        sb.append(" columnDisplay=").append(columnDisplay);
        sb.append(" columnAlign=").append(columnAlign);
        sb.append(" isUniqueKey=").append(isUniqueKey);
        sb.append(" columnOrder=").append(columnOrder);
        return sb.toString();
    }
}