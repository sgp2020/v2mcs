//@formatter:off
/**
 ******************************************************************************
 * @file        MultiSelBoxStringEntity.java
 * @brief       マルチセレクトボックス(Stringのみ)取得エンティティ
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
 * 2017/10/31 0.6         Step4リリース                               T.Iga/CSC
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

//@formatter:off
/**
 ******************************************************************************
 * @brief     マルチセレクトボックス(Stringのみ)取得クラス
 * @par       メソッド無
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class MultiSelBoxStringEntity {

    public boolean disp = true;
    public boolean selected = true;
    public MultiSelBoxStringDataEntity data = new MultiSelBoxStringDataEntity();
}
