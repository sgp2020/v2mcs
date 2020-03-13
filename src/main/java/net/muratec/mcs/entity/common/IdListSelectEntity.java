//@formatter:off
/**
 ******************************************************************************
 * @file        IdListSelectEntity.java
 * @brief       AmhsIdリストエンティティ
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
 * @brief     AmhsIdリストエンティティ
 * @par      AmhsIdリストエンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class IdListSelectEntity {

    /** プロセスID（裏で持っておく値） */
    public String serviceId = null;
    /** プロセスID（テキストとして出す値） */
    public String processName = null;
    /** AMHS数 */
    public Integer amhsNum = null;
    /** AMHS名リスト */
    public String amhsNameList = null;
}
