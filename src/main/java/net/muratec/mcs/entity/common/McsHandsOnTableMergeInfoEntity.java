//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableMergeInfoEntity.java
 * @brief       McsHandsOnTable用のセル結合情報エンティティ
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
 * @brief     McsHandsOnTable用のセル結合情報エンティティ
 * @par       McsHandsOnTable用のセル結合情報エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableMergeInfoEntity {

    /**
     * 行インデックス
     */
    public Integer row = null;

    /**
     * 列インデックス
     */
    public Integer col = null;

    /**
     * 行の結合セル数
     */
    public Integer rowspan = null;

    /**
     * 列の結合セル数
     */
    public Integer colspan = null;
}
