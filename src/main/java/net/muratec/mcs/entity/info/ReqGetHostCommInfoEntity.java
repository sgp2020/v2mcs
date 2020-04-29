//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetHostCommInfoEntity.java
 * @brief       
 * * @par
 * @author       天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/11 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    空FOUP管理関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/11 	ReqGetHostCommInfoEntity							           天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetHostCommInfoEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "II-009-04-001") public String hostName ;
    @FieldNameKey(key = "II-009-04-002") public String commState;
}
