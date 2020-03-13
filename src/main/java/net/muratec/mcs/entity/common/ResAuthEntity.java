//@formatter:off
/**
 ******************************************************************************
 * @file        ResAuthEntity.java
 * @brief       ユーザ名取得用のレスポンスエンティティ
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
 * @brief     ユーザ名取得用のレスポンスエンティティ
 * @par      ユーザ名取得用のレスポンスエンティティクラス。
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResAuthEntity extends AjaxResBaseEntity {

    public AuthenticationEntity body = new AuthenticationEntity();

}
