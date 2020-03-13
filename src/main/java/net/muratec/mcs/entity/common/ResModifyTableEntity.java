//@formatter:off
/**
 ******************************************************************************
 * @file        ResModifyTableEntity.java
 * @brief      Mcsモディファイテーブルレスポンスエンティティ
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
 * @brief     Mcsモディファイテーブルレスポンスエンティティ
 * @par      Mcsモディファイテーブルレスポンスエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResModifyTableEntity extends AjaxResBaseEntity {

    public List<ModifyTableItemEntity> body = new ArrayList<ModifyTableItemEntity>();

}
