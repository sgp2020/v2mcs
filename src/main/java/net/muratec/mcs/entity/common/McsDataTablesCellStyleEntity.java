//@formatter:off
/**
 ******************************************************************************
 * @file        McsDataTablesCellStyleEntity.java
 * @brief       McsDataTablesでセルのスタイルを設定する場合のエンティティ
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
package net.muratec.mcs.entity.common;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTablesでセルのスタイルを設定する場合のエンティティ
 * @par       エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsDataTablesCellStyleEntity {

    /**
     * McsDataTablesに対し、スタイル設定処理を明示するためのフラグ この値は変更しないこと。
     */
    public final String type = "style";

    /** セルの背景色。#RRGGBB または rgb(r,g,b) の形式。nullならなにも表示しない。 */
    public String bgcolor = null;

    /** セルに表示するテキスト。nullなら何も表示しない。 */
    public String text = null;

}
