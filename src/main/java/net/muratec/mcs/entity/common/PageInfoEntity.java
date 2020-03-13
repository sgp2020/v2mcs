//@formatter:off
/**
 ******************************************************************************
 * @file        PageInfoEntity.java
 * @brief       McsDataTables用のページ情報エンティティ
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
 * @brief     McsDataTables用のページ情報エンティティ
 * @par       McsDataTables用のページ情報エンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class PageInfoEntity {

    /**
     * 全件数
     */
    // public int totalNumber = 0;
    public int totalRecords = 0;

    /**
     * 全ページ数
     */
    // public int totalPages = 0;

    /**
     * 現ページ
     */
    public int currentPage = 0;

    /**
     * １ページあたりの件数
     */
    public int pageRecords = 100;
}
