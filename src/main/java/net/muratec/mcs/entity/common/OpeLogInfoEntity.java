﻿//@formatter:off
/**
 ******************************************************************************
 * @file        OpeLogInfoEntity.java
 * @brief      オペレーションログ情報エンティティ
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
 * @brief     オペレーションログ情報エンティティ
 * @par      オペレーションログ情報エンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class OpeLogInfoEntity {

    // ------------------------------------
    // ログコード
    // ------------------------------------
    public long logCode;

    // ------------------------------------
    // IPアドレス
    // ------------------------------------
    public String ipAddress;

    // ------------------------------------
    // ユーザ名
    // ------------------------------------
    public String userName;
}
