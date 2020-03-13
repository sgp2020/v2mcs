//@formatter:off
/**
 ******************************************************************************
 * @file        OhbModel.java
 * @brief       アラーム情報表示画面用モデル
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE        VER.        DESCRIPTION                 AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12   2                                       SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示画面用モデルクラス
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
public class OhbModel {
    public String  ohbId;
    public Integer occupancy;
    public Integer used;
    public Integer total;
}
