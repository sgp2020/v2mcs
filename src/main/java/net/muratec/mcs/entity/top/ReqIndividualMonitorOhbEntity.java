//@formatter:off
/**
 ******************************************************************************
 * @file        ReqIndividualMonitorOhbEntity.java
 * @brief       OHBモニタ情報取得用リクエストエンティティ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.top;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    OHBモニタ情報取得用リクエストエンティティクラス
 * @par
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
public class ReqIndividualMonitorOhbEntity extends AjaxReqBaseEntity {

//    public String amhsId = null;//20191225 DQY DEL
    public Integer displayId = null;//20191225 DQY ADD
    public String ohbId = null;
    public boolean ohbChgFlag = false;
    public Integer pieceId;	 //20200121 Song Add
}
