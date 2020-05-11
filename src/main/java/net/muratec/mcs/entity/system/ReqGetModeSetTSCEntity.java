//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetModeSetTSCEntity.java
 * @brief       
 * * @par
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
 * 2020/05/08 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.system;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0	   ReqGetModeSetTSCEntity                                   天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetModeSetTSCEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IS-001-01-003") public String tscType = null;
}
