//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorPortEntity.java
 * @brief       個別モニタ_ポート画面用共通データエンティティ
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

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ_ポート画面用共通データエンティティクラス
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
public class IndividualMonitorPortEntity {

    public String portId = null;
    public String portType = null;
    public String portIo = null;
    public String portAvail = null;
    public String portLState = null;
    public String ohbPriority = null;
    public String carrierId = null;
    public Short hidePortAvail = null;
    
    //20191227 DQY ADD START
    public String portAbbreviation = null;
    public String portMode = null;
    public String Available = null;
    public String ibsemAavail = null;
    public String managementTscId = null;
    public String tscId = null;
    //20191227 DQY ADD END

}
