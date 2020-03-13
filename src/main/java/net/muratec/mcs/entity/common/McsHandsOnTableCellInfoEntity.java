//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableCellInfoEntity.java
 * @brief       McsHandsOnTable設定用のエンティティ
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

import java.util.ArrayList;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsHandsOnTable設定用のエンティティ
 * @par       McsHandsOnTable設定用のエンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableCellInfoEntity {

    /**
     * セルデータ要素リスト
     */
    public List<McsHandsOnTableCellDataElemEntity> data = new ArrayList<McsHandsOnTableCellDataElemEntity>();

    /**
     * マージ情報
     */
    public List<McsHandsOnTableMergeInfoEntity> mergeCells = new ArrayList<McsHandsOnTableMergeInfoEntity>();

    /**
     * ボーダー情報
     */
    public List<McsHandsOnTableBorderInfoEntity> customBorders = new ArrayList<McsHandsOnTableBorderInfoEntity>();

    /**
     * セルの縦幅
     */
    public List<Integer> heights = new ArrayList<Integer>();

    /**
     * セルの横幅
     */
    public List<Integer> widths = new ArrayList<Integer>();
}
