//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetVehicleStatisticsHistoryValidateEntity.java
 * @brief       Stocker IDリスト取得用リクエストエンティティ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE          VER.         DESCRIPTION                       AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15     Ver1.0	  MCSV2　GUI開発   					SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

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
 * @brief    tscId,vehicleId リスト取得用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 	MACSV2                          				SGP
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetVehicleStatisticsHistoryValidateEntity extends AjaxDataTablesReqBaseEntity {

	@FieldNameKey(key = "IH-004-03-001") public String tscId;
	@FieldNameKey(key = "IH-004-03-002") public String vehicleId;
	@FieldNameKey(key = "IH-004-03-003") public String unit;
    @DateTime @NotBlank @FieldNameKey(key = "IH-004-03-004, IH-003-03-008") public String dateFrom;
    @DateTime @NotBlank @FieldNameKey(key = "IH-004-03-004, IH-003-03-009") public String dateTo;
    public String tscName ;
    public String unitName;
}
