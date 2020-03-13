//@formatter:off
/**
 ******************************************************************************
 * @file        IdListSelectModel.java
 * @brief       IDリスト選択用モデル
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     IDリスト選択用モデル
 * @par       idSelect用モデルクラス
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
public class IdListSelectModel {

    /** PROCESS.SERVICE_ID */
    private String serviceId;
    /** AMHS.AMHS_NAME（カンマ区切り） */
    private String amhsNameList;
    /** AMHSのレコード数 */
    private Integer amhsNum;
}
