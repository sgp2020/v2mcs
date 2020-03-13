//@formatter:off
/**
 ******************************************************************************
 * @file        ReqMcsConstsEntity.java
 * @brief       システムパラメータマスタメンテ SysPara修正画面連携用エンティティ
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ SysPara修正画面連携用エンティティクラス
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
public class ReqMcsConstsEntity {

    public String category = "";
    public String section = "";
    public String key = "";
    public String value = "";
    public String description = "";
    public String unit = "";
    public Long lowerLimit = 0L;
    public Long upperLimit = 0L;

}
