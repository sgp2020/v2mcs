//@formatter:off
/**
 ******************************************************************************
 * @file        OperationHistoryModel.java
 * @brief       操作ログ登録用モデル
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
 * 2019/02/27 MACS4#0109  HistSvr機能修正(GUI)(初版作成)              T.Iga/CSC
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
 * @brief     操作ログ登録用モデルクラス
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
public class OperationHistoryModel {

    private Timestamp time;
    private String userId;
    private Long logCode;
    private String logText;
    private String client;
    private String targetTable;
}
