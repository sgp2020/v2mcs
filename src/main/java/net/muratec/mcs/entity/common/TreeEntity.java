//@formatter:off
/**
 ******************************************************************************
 * @file        TreeEntity.java
 * @brief       ツリー用エンティティ
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
 * @brief    ツリー用エンティティ
 * @par       ツリー用エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class TreeEntity {

    public String id = "";
    public String parent = "";
    public String text = "";
    public TreeStateEntity state = new TreeStateEntity();
}
