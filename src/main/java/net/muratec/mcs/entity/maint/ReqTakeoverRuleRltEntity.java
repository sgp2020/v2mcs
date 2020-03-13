//@formatter:off
/**
 ******************************************************************************
 * @file        ReqTakeoverRuleRltEntity.java
 * @brief       テイクオーバールール構成マスタメンテ 追加/修正連携用エンティティ
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
 * @brief     テイクオーバールール構成マスタメンテ 追加/修正連携用エンティティクラス
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
public class ReqTakeoverRuleRltEntity {

    public Integer tableIndex = 0;
    // Step4 2017/08/29
    @NotBlank @NumRange(min = 1, max = 99) @FieldNameKey(key = "IM-022-02-010") public String no = "";
    @NotBlank @ByteRange(min = 0, max = 32) @FieldNameKey(key = "IM-022-02-011") public String serviceId = "";
    @NotBlank @NumRange(min = 0, max = 999) @FieldNameKey(key = "IM-022-02-012") public String nodeId = "";
    @NotBlank @NumRange(min = 0, max = 999999) @FieldNameKey(key = "IM-022-02-013") public String systemId = "";
    @FieldNameKey(key = "IM-022-02-014") public Boolean onOffFlag;
}
