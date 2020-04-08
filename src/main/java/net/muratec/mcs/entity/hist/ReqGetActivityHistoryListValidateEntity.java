//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetActivityHistoryListValidateEntity.java
 * @brief       
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION             AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07   Ver2.0	   							SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.DateTime;

//@formatter:off
/**
 ******************************************************************************
 * @brief    
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.          DESCRIPTION                                   AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 	ReqGetActivityHistoryListValidateEntity 	   SGP天津村研
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetActivityHistoryListValidateEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-001-03-001") public String source;
    @FieldNameKey(key = "IH-001-03-002") public String destination;
    @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IH-001-03-003") public String carrierId;
    @FieldNameKey(key = "IH-001-03-004") public String commandId;
    @DateTime @FieldNameKey(key = "IH-001-03-012, IH-001-03-010") public String dateFrom;
    @DateTime @FieldNameKey(key = "IH-001-03-012, IH-001-03-011") public String dateTo;
    @FieldNameKey(key = "IH-001-03-006") public String maxRecords;
}
