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
 * 2019/01/23 MACS4#0092  IFOHB追加機能(GUI)                          T.Iga/CSC
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
 * @brief    OHBポートグループマスタメンテナンス 一覧取得用モデル
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0092  IFOHB追加機能(GUI)                                     T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on

@Getter
@Setter
public class OhbPortGroupModel {

    private String ohbId;
    private String ohbName;
    private String ohbType;
    private String ioMode;      // MACS4#0092 Add
    private String amhsId;
    private String ohbLState;
    private Timestamp setTime;
    private String setAuthor;
    private String shapeId;
    private String member;
}
