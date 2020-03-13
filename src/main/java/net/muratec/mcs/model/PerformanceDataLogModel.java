//@formatter:off
/**
 ******************************************************************************
 * @file        PerformanceDataLogModel.java
 * @brief       パフォーマンスデータログ表示用モデル
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

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     パフォーマンスデータログ表示用モデルクラス
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
public class PerformanceDataLogModel {

    private Timestamp time; // 日時
    private String key1; // key1
    private String key2; // key2
    private String key3; // key3
    private long value; // 値

}
