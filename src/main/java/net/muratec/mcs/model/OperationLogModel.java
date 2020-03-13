//@formatter:off
/**
 ******************************************************************************
 * @file        OperationLogModel.java
 * @brief       操作ログ表示用モデル
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

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ表示用モデルクラス
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
public class OperationLogModel {

    private Timestamp time; // 日時
    private String userId; // ユーザ
    private String logCode; // コード
    private String logName; // コード名
    private String logText; // テキスト
    private String client; // クライアント
}
