//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableCellDataElemEntity.java
 * @brief       McsHandsOnTable用のセルデータ要素エンティティ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsHandsOnTable用のセルデータ要素エンティティ
 * @par       McsHandsOnTable用のセルデータ要素エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableCellDataElemEntity {

    /**
     * 行インデックス
     */
    public Integer row = null;

    /**
     * 列インデックス
     */
    public Integer col = null;

    /**
     * 優先順位
     */
    public Integer priority = null;

    /**
     * ラベル
     */
    public String label = null;

    /**
     * アイコン
     */
    public McsHandsOnTableIconEntity icon = null;

}
