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
 * 2020.03.10   Ver2.0	  MCSV4　GUI開発   							董 天津村研
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
 * @brief    TSC_ID,SRC_LOC,DST_LOC,TIME リスト取得用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.10 	ReqGeStatisticsHistoryJobListValidateEntity 			DONG
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGeStatisticsHistoryJobListValidateEntity extends AjaxDataTablesReqBaseEntity {

	@FieldNameKey(key = "IH-005-03-001") public String tscId;
    @FieldNameKey(key = "IH-005-03-002") public String source;
    @FieldNameKey(key = "IH-005-03-003") public String destination;
    @FieldNameKey(key = "IH-005-03-004") public String unit;
    @DateTime @FieldNameKey(key = "IH-005-03-006, IH-005-03-011") public String dateFrom;
    @DateTime @FieldNameKey(key = "IH-005-03-006, IH-005-03-012") public String dateTo;
}
