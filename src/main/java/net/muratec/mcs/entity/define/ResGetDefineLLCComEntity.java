//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetDefineLLCComEntity.java
 * @brief       comconf画面用レスポンスエンティティ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			MACSV2											天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.define;


import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    ResGetDefineLLCComEntity画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.18 			ResGetDefineLLCComEntity							董 天津村研
 ******************************************************************************
 */
//@formatter:on
public class ResGetDefineLLCComEntity extends AjaxDataTablesResBaseEntity {
    public List<DefineLLCComEntity> body = new ArrayList<DefineLLCComEntity>();
}
