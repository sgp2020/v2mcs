//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorMicroCmdEntity.java
 * @brief       個別モニタ_Microコマンド画面用共通データエンティティ
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
package net.muratec.mcs.entity.top;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ_Microコマンド画面用共通データエンティティクラス
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
public class IndividualMonitorMicroCmdEntity extends AjaxResBaseEntity {

    public String commandId = null;
    public String priority = null;
    public String carrierId = null;
    public String srcLoc = null;
    public String dstLoc = null;
    public String carrierJobState = null;
    public Short hideCarrierJobState = null;
    public String waitInTime = null;
}
