//@formatter:off
/**
 ******************************************************************************
 * @file        OhbPortRltModel.java
 * @brief       アラーム情報表示画面用モデル
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 201620 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE           VER.        DESCRIPTION                 AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/16     2                                         SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     OhbPortRlt情報表示画面用モデルクラス
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
public class OhbPortRltModel {
	public String ohbId;
    public String portId;
    public String carrierId;
    public String storedTime;
    public String portMode;
    public String available;
    public String ibsemAvail;
}
