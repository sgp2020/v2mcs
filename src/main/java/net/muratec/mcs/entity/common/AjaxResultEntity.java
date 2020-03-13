//@formatter:off
/**
 ******************************************************************************
 * @file        AjaxResultEntity.java
 * @brief       McsDataTables用のリザルトエンティティ
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

import net.muratec.mcs.common.ComConst;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables用のリザルトエンティティ
 * @par      McsDataTablesへのリザルトの基底クラス。
 *           入力値エラーチェックのクラスはこのクラスを継承する。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class AjaxResultEntity {

    public String status = ComConst.AjaxStatus.SUCCESS;
    public String message = "";
    public String value = "";

    public List<ErrorInfo> errorInfoList = new ArrayList<ErrorInfo>();

   //@formatter:off
    /**
    ******************************************************************************
    * @brief     入力値エラーチェックのクラス
    * @par      入力値エラーチェックのクラス。
    *           共通関数の入力値エラーチェックでこのクラスを継承する。
    * @attention 
    * @note      
    * ----------------------------------------------------------------------------
    * VER.        DESCRIPTION                                               AUTHOR
    * 0.1         Step1リリース                                             CSC
    * ----------------------------------------------------------------------------
    ******************************************************************************
    */
    //@formatter:on
    public class ErrorInfo {

        public String id = "";
        public String errorMessage = "";
        public String errorValue = "";
    }
}
