//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetCarrierExeAddEntity.java
 * @brief       キャリア情報表示画面のキャリア追加用レスポンスエンティティ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報表示画面のキャリア追加用レスポンスエンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetCarrierExeAddEntity extends AjaxDataTablesResBaseEntity {

    // ------------------------------------
    // データ本文
    // ------------------------------------
    public CarrierEntity body = new CarrierEntity();
}
