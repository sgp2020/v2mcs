//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorTransferJob.java
 * @brief       個別モニタ_搬送Job画面用モデル
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
 * @brief     個別モニタ_搬送Job画面用モデルクラス
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
public class IndividualMonitorTransferJob {

    private Timestamp receivedTime = null;
    private String jobOwner = null;
    private String carrierId = null;
    private Short priority = null;
    private String carrierJobState = null;
    private String currentLoc = null;
    private String srcAmhsId = null;
    private String srcLoc = null;
    private String dstAmhsId = null;
    private String dstLoc = null;
    private String microFrom = null;
    private String microTo = null;
    private String amhsId = null;
    private Short hideCarrierJobState = null;
    private String waitInTime = null;
}
