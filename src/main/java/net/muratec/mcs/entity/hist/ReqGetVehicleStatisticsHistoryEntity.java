//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetVehicleStatisticsHistoryEntity.java
 * @brief       VehicleStatisticsHistoryのエンティティ
 * * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
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
 * @brief    VehicleStatisticsHistory関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.            DESCRIPTION                                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 	  ReqGetVehicleStatisticsHistoryEntity 			SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetVehicleStatisticsHistoryEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-004-03-001") public String tscId ;
    @FieldNameKey(key = "IH-004-03-002") public String vehicleId ;
    @FieldNameKey(key = "IH-004-03-003") public String unit;
    @FieldNameKey(key = "IH-004-03-004, IH-003-03-008") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-004-03-004, IH-003-03-009") public Timestamp dateTo;
    public String dateFromForDownTime;
    public String dateToForDownTime;
}
