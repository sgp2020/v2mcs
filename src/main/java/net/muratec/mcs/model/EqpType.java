//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierModel.java
 * @brief       キャリア情報用モデル
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 * 2018/10/23 MACS4#0021  LotID,ProductID表示                         T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class EqpType {

    private String eqpValue;
    private String eqpTxt;   
}
