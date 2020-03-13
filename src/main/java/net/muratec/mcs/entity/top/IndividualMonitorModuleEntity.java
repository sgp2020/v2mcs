//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorModuleEntity.java
 * @brief       個別モニタ_モジュール画面用共通データエンティティ
 * @par
 * @author      T.Iga/CSC
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

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ_モジュール画面用共通データエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class IndividualMonitorModuleEntity extends AjaxResBaseEntity {

    public String moduleId = null;
    public String moduleName = null;
    public String available = null;
    public Short availableCode = null;
}
