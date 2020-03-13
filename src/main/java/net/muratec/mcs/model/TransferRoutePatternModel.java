//@formatter:off
/**
 ******************************************************************************
 * @file        TransferTestModel.java
 * @brief       搬送テストマスタメンテナンス(一覧画面)用モデル
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送経路マスタメンテナンス(一覧画面)用モデルクラス
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
public class TransferRoutePatternModel {
    private String tableNoString;
    private Short tableNo;
    private String patternId;
    private Long routeNo;
    private String funcOptString;
    private Short funcOpt;
    private Short enableFlag;
}