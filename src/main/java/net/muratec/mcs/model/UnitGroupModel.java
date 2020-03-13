//@formatter:off
/**
 ******************************************************************************
 * @file        OhbPortGroupModel.java
 * @brief       OHBポートグループマスタメンテナンス 一覧取得用モデル
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
 * 2017/12/20 0.6         Step4phase2リリース                               CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief    OHBポートグループマスタメンテナンス 一覧取得用モデル
 * @par
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
public class UnitGroupModel {

    private String groupId;
    private String description;
    private String member;
}
