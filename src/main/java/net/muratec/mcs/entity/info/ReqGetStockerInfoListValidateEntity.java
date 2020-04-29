//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetStockerInfoListValidateEntity.java
 * @brief       Stocker IDリスト取得用リクエストエンティティ
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/12  v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    STOCKER IDリスト取得用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/12 	ReqGetStockerInfoListValidateEntity 					天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetStockerInfoListValidateEntity extends AjaxDataTablesReqBaseEntity {

//    public String controllerId;
    public String tscId;
}
