//@formatter:off
/**
 ******************************************************************************
 * @file        IdListSelectGroupEntity.java
 * @brief       AmhsGroupIdリストエンティティ
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     AmhsGroupIdリストエンティティ
 * @par      AmhsGroupIdリストエンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class IdListSelectGroupEntity {

    /** プロセスグループID */
    public Short processGroupId = null;
    /** ノード名リスト */
    public String nodeNameList = null;
}
