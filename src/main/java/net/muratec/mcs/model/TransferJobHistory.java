//@formatter:off
/**
 ******************************************************************************
 * @file        TransferJobHistory.java
 * @brief       搬送Job来歴用モデル
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
 * 2018/10/23 MACS4#0020  HostZoneIDの表示(GUI)                       T.Iga/CSC
 * 2018/11/08 MACS4#0034  PriorityChange(GUI)対応                     T.Iga/CSC
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
 * @brief     搬送Job来歴用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0020  HostZoneIDの表示(GUI)                                  T.Iga/CSC
 * MACS4#0034  PriorityChange(GUI)対応                                T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class TransferJobHistory {

    private String carrierJobId;
    private Timestamp time;
    private String result;
    private String jobId;
    private String entityId;
    private String carrierId;
    private String macroSrcAmhsId;
    private String macroSrcLoc;
    private String macroSrcZoneId;
    private String macroDstGroupId;
    private String macroDstAmhsId;
    private String macroDstLoc;
    private String macroDstZoneId;
    private String orgMacroDstGroupId;
    private String orgMacroDstAmhsId;
    private String orgMacroDstLoc;
    private String orgMacroDstZoneId;
    private Short orgPriority;
    private Timestamp rcvTime;
    private Timestamp compTime;
    private Timestamp eta;
    private Timestamp pta;
    private Timestamp ptd;
    private String jobOwner;
    private String jobType;
    private String cancelReason;
    private Timestamp cancelTime;
    private String lastRerouteReason;
    private Timestamp lastRerouteTime;
    private String orgCarrierId;
    private short  transferOrder;
    private String carrierJobType;
    private String transGrpId;
    private String productId;
    private String lotId;
    private String hostZoneId;  // MACS4#0020 Add
    private String reqPriority; // MACS4#0034 Add
    private String reqToLoc;    // MACS4#0034 Add

}
