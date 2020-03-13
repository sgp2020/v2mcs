//@formatter:off
/**
 ******************************************************************************
 * @file        ResGuiEntity.java
 * @brief       システムパラメータマスタメンテ GUIカラー情報取得用エンティティ
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
package net.muratec.mcs.entity.maint;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ GUIカラー情報取得のレスポンスエンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGuiEntity extends AjaxDataTablesResBaseEntity {

    // ------------------------------------
    // データ本文
    // ------------------------------------
    public GuiColorEntity body = new GuiColorEntity();

}
