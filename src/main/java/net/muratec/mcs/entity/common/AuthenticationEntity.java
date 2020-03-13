//@formatter:off
/**
 ******************************************************************************
 * @file        AuthenticationEntity.java
 * @brief       ユーザ名取得用のエンティティ
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

import java.io.Serializable;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ユーザ名取得用のエンティティ
 * @par      ユーザ名取得用の基底クラス。
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class AuthenticationEntity implements Serializable {

    public Short userId = null;
    public String userName = null;
    public String description = null;
    public int autoLogoutTime = -1;
    public List<String> functionIdList = null;
}
