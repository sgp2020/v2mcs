//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetVehicleStatisticsHistoryEntity.java
 * @brief       VehicleStatisticsHistory面用レスポンスエンティティ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION                      AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.15 			       							     SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;


import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    VehicleStatisticsHistory画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetVehicleStatisticsHistoryEntity extends AjaxDataTablesResBaseEntity {
    public List<VehicleStatisticsHistoryEntity> body = new ArrayList<VehicleStatisticsHistoryEntity>();
}
