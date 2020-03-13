//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorTransferJobEntity.java
 * @brief       個別モニタ搬送Job画面用共通データエンティティ
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
package net.muratec.mcs.entity.top;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ搬送Job画面用共通データエンティティクラス
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
public class IndividualMonitorTransferJobEntity {

    public String receivedTime = null;
    public String jobOwner = null;
    public String carrierId = null;
    public Short priority = null;
    public String carrierJobState = null;
    public String currentLoc = null;
    public String srcAmhsId = null;
    public String srcLoc = null;
    public String dstAmhsId = null;
    public String dstLoc = null;
    public String microFrom = null;
    public String microTo = null;
    public String amhsId = null;
    public Short hideCarrierJobState = null;
    public String waitInTime = null;

}
