//@formatter:off
/**
 ******************************************************************************
 * @file        OperationLogListEntity.java
 * @brief       操作ログ表示画面連携用エンティティ
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
package net.muratec.mcs.entity.log;

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ表示画面連携用エンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class OperationLogListEntity {

    public String time; // 日時
    public String userId; // ユーザ
    public String logCode; // コード
    public String logName; // コード名
    public String logText; // テキスト
    public String client; // クライアント

}
