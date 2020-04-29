//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetAtomicActivityHistEntity.java
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
 * 2020/03/26 v1.0.0                     初版作成                                							          天津村研　董
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
 * @brief    AtomicActivityHist関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/26 	ReqGetcEntity 											天津村研　董
 ****************************************************************************** 
 */
//@formatter:on
@Getter
@Setter
public class ReqGetAtomicActivityHistEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-002-03-001") public String tscId ;
    @FieldNameKey(key = "IH-002-03-001") public String tscName ;
    @FieldNameKey(key = "IH-002-03-002") public String source;
    @FieldNameKey(key = "IH-002-03-003") public String destination;
    @FieldNameKey(key = "IH-002-03-004") public String carrierId;
    @FieldNameKey(key = "IH-002-03-005") public String commandId;
    @FieldNameKey(key = "IH-002-03-006, IH-002-03-011") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-002-03-006, IH-002-03-012") public Timestamp dateTo;
    @FieldNameKey(key = "IH-002-03-007") public String maxRecords;
}
