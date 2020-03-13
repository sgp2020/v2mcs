//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableBorderInfoEntity.java
 * @brief       McsHandsOnTable用のボーダー情報エンティティ
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
 * @brief    McsHandsOnTable用のボーダー情報エンティティ
 * @par      McsHandsOnTable用のボーダー情報エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableBorderInfoEntity {

    /**
     * 行インデックス
     */
    public Integer row = null;

    /**
     * 列インデックス
     */
    public Integer col = null;

    /**
     * 上の線
     */
    public McsHandsOnTableLineInfoEntity top = null;

    /**
     * 左の線
     */
    public McsHandsOnTableLineInfoEntity left = null;

    /**
     * 下の線
     */
    public McsHandsOnTableLineInfoEntity bottom = null;

    /**
     * 右の線
     */
    public McsHandsOnTableLineInfoEntity right = null;

}
