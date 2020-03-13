//@formatter:off
/**
 ******************************************************************************
 * @file        ResSystemMonitorEntity.java
 * @brief      システムモニタ表示画面ステータス情報取得エンティティ
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on

package net.muratec.mcs.entity.top;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムモニタ表示画面ステータス情報取得クラス
 * @par       メソッド無
 *
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on

public class ResSystemMonitorEntity extends AjaxResBaseEntity {

    public SystemMonitorEntity body = new SystemMonitorEntity();

}
