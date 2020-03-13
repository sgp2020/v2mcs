//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableCellInfoResEntity.java
 * @brief       McsHandsOnTable用のレスポンスエンティティ
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
 * @brief     McsHandsOnTable用のレスポンスエンティティ
 * @par       McsHandsOnTable用のレスポンスエンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableCellInfoResEntity extends AjaxResBaseEntity {

    /**
     * HandsOnTableの設定値
     */
    public McsHandsOnTableCellInfoEntity body = new McsHandsOnTableCellInfoEntity();
}
