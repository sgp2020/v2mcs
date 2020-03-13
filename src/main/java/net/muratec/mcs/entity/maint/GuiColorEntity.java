//@formatter:off
/**
 ******************************************************************************
 * @file        GuiColorEntity.java
 * @brief       システムパラメータマスタメンテ Guiカラー一覧画面連携用エンティティ
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

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.McsDataTablesCellStyleEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ Guiカラー一覧画面連携用エンティティクラス
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
public class GuiColorEntity {

    public String section = "";
    public String key = "";

    public McsDataTablesCellStyleEntity color = new McsDataTablesCellStyleEntity();
    public String object = "";
    public String description = "";

}
