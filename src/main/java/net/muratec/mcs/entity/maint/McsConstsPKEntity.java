//@formatter:off
/**
 ******************************************************************************
 * @file        McsConstsPKEntity.java
 * @brief       システムパラメータマスタメンテ MCS_CONSTSテーブルPKエンティティ
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ MCS_CONSTSテーブルPKエンティティクラス
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
public class McsConstsPKEntity {

    @FieldNameKey(key = "IM-009-02-007") public String key = "";

}
