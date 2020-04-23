//@formatter:off
/**
 ******************************************************************************
 * @file        ResScMonitorPortListEntity.java
 * @brief       SCモニタポート画面用レスポンスエンティティ
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    SCモニタポート画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetMacroDataListEntity extends AjaxResBaseEntity {
//public class ResGetMacroDataListEntity extends AjaxDataTablesResBaseEntity {

    public List<MacroDataListEntity> body = new ArrayList<MacroDataListEntity>();

}
