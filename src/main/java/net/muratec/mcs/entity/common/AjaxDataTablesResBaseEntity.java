//@formatter:off
/**
 ******************************************************************************
 * @file        AjaxDataTablesResBaseEntity.java
 * @brief       McsDataTables用のレスポンスエンティティ
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
 * @brief     McsDataTables用のレスポンスエンティティ
 * @par      McsDataTablesへのレスポンスの基底クラス。
 *           McsDataTables用のレスポンスエンティティはこのクラスを継承する。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * 0.1         Step1リリース                                             CSC
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public abstract class AjaxDataTablesResBaseEntity extends AjaxResBaseEntity {

    // ------------------------------------
    // ページ情報
    // ------------------------------------
    public PageInfoEntity pageInfo = new PageInfoEntity();

    // ------------------------------------
    // ヘッダ情報（カラム情報）
    // ------------------------------------
    public HeaderEntity header = new HeaderEntity();

    // ------------------------------------
    // 行の色情報
    // ------------------------------------
    public List<String> rowColorList = new ArrayList<String>();
}
