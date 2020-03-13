//@formatter:off
/**
 ******************************************************************************
 * @file        McsDataTablesColInfoReqEntity.java
 * @brief      McsDataTables カラム順保存のリクエストエンティティ
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables カラム順保存のリクエストエンティティ
 * @par      McsDataTables カラム順保存のリクエストエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsDataTablesColInfoReqEntity extends AjaxReqBaseEntity {

    // ------------------------------------
    // テーブルコンポーネントID
    // ------------------------------------
    public String tableCompId;

    // ------------------------------------
    // カラム順のリスト
    // ------------------------------------
    public List<Integer> columnOrderList;

    // ------------------------------------
    // カラム表示非表示のリスト
    // ------------------------------------
    public List<Integer> columnDisplayList;
    public int pageRecords;
}
