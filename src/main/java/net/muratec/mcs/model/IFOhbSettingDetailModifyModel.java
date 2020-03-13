//@formatter:off
/**
 ******************************************************************************
 * @file        IFOhbSettingDetailModifyModel.java
 * @brief       IFOHB搬送詳細 修正連携用モデル
 * @par
 * @author      T.Iga/CSC
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
 * 2019/01/28 MACS4#0092  IFOHB追加機能(GUI)                          T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     IFOHB搬送詳細 修正連携用モデルクラス
 * @par       機能:
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
public class IFOhbSettingDetailModifyModel {

    private Short rowNumber;
    private String ifOhbGrp;
    private Short priority;
    private Short selected;
    private String ioMode;      // MACS4#0092 Add
}
