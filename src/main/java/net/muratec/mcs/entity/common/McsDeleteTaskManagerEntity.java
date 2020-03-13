//@formatter:off
/**
 ******************************************************************************
 * @file        McsDeleteTaskManagerEntity.java
 * @brief       McsDataTables 削除エンティティ
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
 * @brief     McsDataTables 削除エンティティ
 * @par       McsDataTables 削除エンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * 0.1         Step1リリース                                             CSC
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsDeleteTaskManagerEntity {

    // ------------------------------------
    // 削除テーブルリスト
    // ------------------------------------
    public List<McsDeleteTaskManagerTableEntity> deleteTableList = new ArrayList<McsDeleteTaskManagerTableEntity>();

}
