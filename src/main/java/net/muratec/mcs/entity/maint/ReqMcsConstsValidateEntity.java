//@formatter:off
/**
 ******************************************************************************
 * @file        ReqMcsConstsValidateEntity.java
 * @brief       システムパラメータマスタメンテ SysPara修正画面連携用バリデート
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ SysPara修正画面連携用バリデートクラス
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
public class ReqMcsConstsValidateEntity {

    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-02-003") public String category = "";
    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-02-005") public String section = "";
    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "IM-009-02-007") public String key = "";
    @NotBlank @ByteRange(min = 0, max = 128) @FieldNameKey(key = "IM-009-02-009") public String value = "";
    @ByteRange(min = 0, max = 256) @FieldNameKey(key = "IM-009-02-011") public String description = "";
    @ByteRange(min = 0, max = 16) @FieldNameKey(key = "IM-009-02-013") public String unit = "";
    @FieldNameKey(key = "IM-009-02-015") public String lowerLimit = "";
    @FieldNameKey(key = "IM-009-02-017") public String upperLimit = "";

}
