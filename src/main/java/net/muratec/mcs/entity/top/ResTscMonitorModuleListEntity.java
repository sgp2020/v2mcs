//@formatter:off
/**
 ******************************************************************************
 * @file     ResTscMonitorModuleListEntity.java
 * @brief    TSCモニタモジュール画面用レスポンスエンティティ
 * @par
 * @author   T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/01/31 0.8         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.top;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    TSCモニタモジュール画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResTscMonitorModuleListEntity extends AjaxResBaseEntity {

    public List<IndividualMonitorModuleEntity> body = new ArrayList<IndividualMonitorModuleEntity>();
    public List<String> rowColorList = null;
}
