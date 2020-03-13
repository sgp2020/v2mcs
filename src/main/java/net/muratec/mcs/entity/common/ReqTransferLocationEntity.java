//@formatter:off
/**
 ******************************************************************************
 * @file       ReqTransferLocationEntity.java
 * @brief      搬送Locationリストリクエストエンティティ
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
 * 2018/01/31 0.8         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送Locationリストリクエストエンティティ
 * @par       搬送Locationリストリクエストエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqTransferLocationEntity extends AjaxReqBaseEntity {
    
    public Short srcDstFlg = 0; //源与目的列表区分 1源 2目的
    public String con = null;   //下级条件
}
