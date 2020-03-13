//@formatter:off
/**
 ******************************************************************************
 * @file        SiteMapInfoEntity.java
 * @brief       サイトマップ情報用のエンティティ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.top;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     サイトマップ情報用のエンティティ
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class SiteMapInfoEntity {

    /**
     * キー情報（第一要素）
     */
    public int key1 = 0;

    /**
     * キー情報（第二要素）
     */
    public int key2 = 0;

    /**
     * キー情報（第三要素）
     */
    public int key3 = 0;

    /**
     * Value1
     */
    public String value1 = null;

    /**
     * Value2
     */
    public String value2 = null;

    /**
     * Value3
     */
    public String value3 = null;

    /**
     * Value4
     */
    public String value4 = null;

    /**
     * Value5
     */
    public String value5 = null;
}
