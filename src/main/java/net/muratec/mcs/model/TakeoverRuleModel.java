//@formatter:off
/**
 ******************************************************************************
 * @file        TakeoverRuleModel.java
 * @brief       テイクオーバールール構成マスタメンテ 一覧連携用モデル
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
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     テイクオーバールール構成マスタメンテ 一覧連携用モデルクラス
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
public class TakeoverRuleModel {

    private Short ruleNo = 0;
    private String description = "";
    private String onOffFlag = "";
    private String member = "";

}
