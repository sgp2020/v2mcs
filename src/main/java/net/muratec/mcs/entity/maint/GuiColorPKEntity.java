//@formatter:off
/**
 ******************************************************************************
 * @file        GuiColorPKEntity.java
 * @brief       システムパラメータマスタメンテ GUI_COLORテーブルPKエンティティ
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
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.validator.annotation.ByteRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ GUI_COLORテーブルPKエンティティクラス
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
public class GuiColorPKEntity {

    @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-05-003") public String section = "";
    @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-05-005") public String key = "";

}
