//@formatter:off
/**
 ******************************************************************************
 * @file        TransferRouteModel.java
 * @brief       搬送経路マスタメンテナンス(一覧画面)用モデルクラス
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
public class TransferRouteModel {
    private String srcKey;
    private String srcElementId;
    private String dstKey;
    private String dstElementId;
    private String patternId;
}