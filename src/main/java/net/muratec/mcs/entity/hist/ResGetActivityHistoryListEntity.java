//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetActivityHistoryListEntity.java
 * @brief       SCモニタ状態画面用レスポンスエンティティ
 * @par
 * @author      SGP 天津村研
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE            VER.        DESCRIPTION            AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 	   									  SGP 天津村研
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
 * @brief    ActivityHistory画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.                 DESCRIPTION                         AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.07 			ActivityHistoryBody				   SGP 天津村研
 ******************************************************************************
 */
//@formatter:on
public class ResGetActivityHistoryListEntity extends AjaxDataTablesResBaseEntity {
    public List<ActivityHistoryListEntity> body = new ArrayList<ActivityHistoryListEntity>();
}
