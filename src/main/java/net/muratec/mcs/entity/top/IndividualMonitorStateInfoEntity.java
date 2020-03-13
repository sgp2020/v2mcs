//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorStateInfoEntity.java
 * @brief       個別モニタ_状態画面(テキスト部)用共通データエンティティ
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
 * @brief     個別モニタ_状態画面(テキスト部)用共通データエンティティクラス
 * @par       機能:
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
public class IndividualMonitorStateInfoEntity extends AjaxResBaseEntity {

    public String commState = null;
    public String controlState = null;
    public String systemState = null;
    public String available = null;
    public Short zoneOccupied = null;
    public Short zoneCapacity = null;
//    public Short zoneEmpty = null;//20191225 DQY DEL
    public String zoneUtility = null;
    public String amhsLState = null;
    
    //20191223 DQY ADD START FOR MCSV2 STATE
    public String alarmState = null;
    public String tscMode = null;
    public String pieceMode = null;
    public String pieceAvailable = null;
    //20191223 DQY ADD START FOR MCSV2 END
}
