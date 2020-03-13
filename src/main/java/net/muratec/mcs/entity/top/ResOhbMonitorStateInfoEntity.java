//@formatter:off
/**
 ******************************************************************************
 * @file        ResOhbMonitorStateInfoEntity.java
 * @brief       OHBモニタ状態画面用レスポンスエンティティ
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

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    OHBモニタ状態画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResOhbMonitorStateInfoEntity extends AjaxResBaseEntity {

    public IndividualMonitorStateEntity body = new IndividualMonitorStateEntity();
}
