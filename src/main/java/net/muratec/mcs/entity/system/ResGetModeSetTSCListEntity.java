//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetModeSetTSCListEntity.java
 * @brief       
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/05/08 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.system;


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
 * 2020/3/11 	ResGetModeSetTSCListEntity  							天津村研　董
 ******************************************************************************
 */
//@formatter:on
public class ResGetModeSetTSCListEntity extends AjaxDataTablesResBaseEntity {
    public List<ModeSetTSCListEntity> body = new ArrayList<ModeSetTSCListEntity>();
}
