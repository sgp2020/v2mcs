//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetStockerInfoListEntity.java
 * @brief       SCモニタ状態画面用レスポンスエンティティ
 * @par
 * @author      董 天津村研
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			MACSV2											董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;


import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    StockerInformation画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.18 			HostCommInformationBody							董 天津村研
 ******************************************************************************
 */
//@formatter:on
public class ResGetHostCommInfoListEntity extends AjaxDataTablesResBaseEntity {
    public List<HostCommInfoListEntity> body = new ArrayList<HostCommInfoListEntity>();
}