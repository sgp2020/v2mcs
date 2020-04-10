//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetStockerStatisticsHistEntity.java
 * @brief       StockerStatisticsHist面用レスポンスエンティティ
 * @par
 * @author      ZHANGDONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			MACSV2											ZHANGDONG
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
 * @brief    StockerStatisticsHistory画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetStockerStatisticsHistEntity extends AjaxDataTablesResBaseEntity {
    public List<StockerStatisticsHistEntity> body = new ArrayList<StockerStatisticsHistEntity>();
}
