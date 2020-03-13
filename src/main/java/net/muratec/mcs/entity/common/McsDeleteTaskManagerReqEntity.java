//@formatter:off
/**
 ******************************************************************************
 * @file        McsDeleteTaskManagerReqEntity.java
 * @brief      McsDataTables 削除リクエストエンティティ
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

import java.util.List;
import java.util.Map;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables 削除リクエストエンティティ
 * @par      McsDataTables 削除リクエストエンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsDeleteTaskManagerReqEntity {

    // ------------------------------------
    // テーブル名
    // ------------------------------------
    public String tableName;

    // ------------------------------------
    // where句となるカラム名と値のマップ
    // ------------------------------------
    public List<Map<String, Object>> whereMapList;

}
