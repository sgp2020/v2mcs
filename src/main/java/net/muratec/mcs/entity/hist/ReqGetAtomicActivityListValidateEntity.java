//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetStockerInfoListValidateEntity.java
 * @brief       Stocker IDリスト取得用リクエストエンティティ
 * @par
 * @author      DONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/10  v1.0.0                     初版作成                                							          天津村研　董
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
 * @brief    HOST_NAME,COMM_STATE リスト取得用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/26 	ReqGetHostCommListValidateEntity 					天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetAtomicActivityListValidateEntity extends AjaxDataTablesReqBaseEntity {

	@FieldNameKey(key = "IH-002-03-001") public String tscId;
    @FieldNameKey(key = "IH-002-03-002") public String source;
    @FieldNameKey(key = "IH-002-03-003") public String destination;
    @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IH-002-03-004") public String carrierId;
    @FieldNameKey(key = "IH-002-03-005") public String commandId;
    @DateTime @FieldNameKey(key = "IH-002-03-006, IH-002-03-011") public String dateFrom;
    @DateTime @FieldNameKey(key = "IH-002-03-006, IH-002-03-012") public String dateTo;
    @FieldNameKey(key = "IH-002-03-007") public String maxRecords;
    public String tscName ;
}
