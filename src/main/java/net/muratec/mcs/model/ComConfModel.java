//@formatter:off
/**
 ******************************************************************************
 * @file        ComConfModel.java
 * @brief       ComConf用モデル
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/26 1         Step1リリース                                    天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ComConf用モデルクラス
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
public class ComConfModel {

    private String rn;
    private String sid;   
    private String usedTsc;
    private String portNo;   
    private String connectMode;
    private String hostIP;   
    private String deviceID;

}
