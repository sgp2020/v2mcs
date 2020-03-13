//@formatter:off
/**
 ******************************************************************************
 * @file        JobstateReasonModel.java
 * @brief       搬送Job（DetailJobReasonTable）用モデル
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
 * 2017/10/31 0.6         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送Job（DetailJobReasonTable）用モデル
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
public class JobstateReasonModel {

    private String carrierJobId;
    private Date time;
    private String carrierId;
    private String carrierJobState;
    private String amhsId;
    private String locationId;
    private String jobOwner;
    private String reason;
}
