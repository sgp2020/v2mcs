//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetActivityHistoryEntity.java
 * @brief       空FOUP管理関連のエンティティ
 * * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION             AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/07 v1.0.0      初版作成                                SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    ActivityHistory関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.          DESCRIPTION                                   AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 	ReqGetActivityHistoryEntity 				SGP 天津村研
 ****************************************************************************** ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetActivityHistoryEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-001-03-001") public String source;
    @FieldNameKey(key = "IH-001-03-002") public String destination;
    @FieldNameKey(key = "IH-001-03-003") public String carrierId;
    @FieldNameKey(key = "IH-001-03-004") public String commandId;
    @FieldNameKey(key = "IH-001-03-012, IH-002-03-010") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-001-03-012, IH-002-03-011") public Timestamp dateTo;
    @FieldNameKey(key = "IH-001-03-006") public String maxRecords;
}
