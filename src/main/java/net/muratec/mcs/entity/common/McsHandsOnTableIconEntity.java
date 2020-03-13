//@formatter:off
/**
 ******************************************************************************
 * @file        McsHandsOnTableIconEntity.java
 * @brief       McsHandsOnTable用のアイコン情報エンティティ
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
 * @brief     McsHandsOnTable用のアイコン情報エンティティ
 * @par       McsHandsOnTable用のアイコン情報エンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2019/11/07 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
public class McsHandsOnTableIconEntity {

    /**
     * amhsId
     */
    public String amhsId = null;

    /**
     * amhsType
     */
    public Short amhsType = null;

    /**
     * アイコン種別
     */
    public Short iconType = null;

    /**
     * アイコンのDOM文字列
     */
    public String iconDomStr = null;
    
    
    // MACS4#MACSV2 Add start
    public String displayName = null;
    public String displayId = null;
    public String memberGroup;
    public Short groupNumber;
    public String tscMode;
    public Short rownum;
    // MACS4#MACSV2 Add end

}
