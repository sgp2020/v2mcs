//@formatter:off
/**
 ******************************************************************************
 * @file        McsDeleteTaskManagerTableEntity.java
 * @brief      McsDataTables 削除テーブルエンティティ
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

import java.util.HashMap;
import java.util.Map;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables 削除テーブルエンティティ
 * @par      McsDataTables 削除テーブルエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsDeleteTaskManagerTableEntity {

    // ------------------------------------
    // テーブル名
    // ------------------------------------
    public String tableName;

    // ------------------------------------
    // where句となるカラム名と値のマップ
    // ------------------------------------
    public Map<String, Object> whereMap = new HashMap<>();
}
