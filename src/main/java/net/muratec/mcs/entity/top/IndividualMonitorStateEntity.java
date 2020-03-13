//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorStateEntity.java
 * @brief       個別モニタ状態画面用共通エンティティ
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

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ状態画面用共通エンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class IndividualMonitorStateEntity extends AjaxResBaseEntity {

    public IndividualMonitorStateInfoEntity state = new IndividualMonitorStateInfoEntity();
    public List<IndividualMonitorAlarmInfoEntity> alarmList = new ArrayList<IndividualMonitorAlarmInfoEntity>();
}
