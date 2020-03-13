//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGuiColorValidateEntity.java
 * @brief       システムパラメータマスタメンテ GUIカラー修正画面連携用バリデート
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

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.NumRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ GUIカラー修正画面連携用バリデートクラス
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
public class ReqGuiColorValidateEntity {

    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-05-003") public String section = "";
    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-05-005") public String key = "";
    @NotBlank @ByteRange(min = 7, max = 7) @FieldNameKey(key = "IM-009-05-007") public String color = "";
    @NumRange(min = 0, max = 99) @FieldNameKey(key = "IM-009-05-009") public String object = "";
    @ByteRange(min = 0, max = 128) @FieldNameKey(key = "IM-009-05-011") public String description = "";

}
