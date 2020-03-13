//@formatter:off
/**
 ******************************************************************************
 * @file        ResGuiListEntity.java
 * @brief       システムパラメータマスタメンテ GUIカラー一覧画面連携用エンティティ
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

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ GUIカラー一覧取得のレスポンスエンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGuiListEntity extends AjaxDataTablesResBaseEntity {

    // ------------------------------------
    // データ本文
    // ------------------------------------
    public List<GuiColorEntity> body = new ArrayList<GuiColorEntity>();

}
