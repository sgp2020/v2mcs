//@formatter:off
/**
 ******************************************************************************
 * @file        MultiSelectBoxItemEntity.java
 * @brief       マルチセレクトボックス要素エンティティ
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

import net.muratec.mcs.model.StringMaster;

//@formatter:off
/**
 ******************************************************************************
 * @brief     マルチセレクトボックス要素エンティティ
 * @par      マルチセレクトボックス要素エンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class MultiSelectBoxItemEntity {

    public Boolean disp = null;
    public Boolean selected = null;
    public StringMaster data = null;
}
