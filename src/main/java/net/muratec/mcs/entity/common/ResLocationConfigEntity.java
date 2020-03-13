//@formatter:off
/**
 ******************************************************************************
 * @file        ResLocationConfigEntity.java
 * @brief      Locationリストレスポンスエンティティ
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

import java.util.ArrayList;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     Locationリストレスポンスエンティティ
 * @par      Locationリストレスポンスエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResLocationConfigEntity extends AjaxResBaseEntity {

    public List<LocationEntity> body = new ArrayList<LocationEntity>();
    public Short location = 1;
}
