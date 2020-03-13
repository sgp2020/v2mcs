//@formatter:off
/**
 ******************************************************************************
 * @file        TransferJob.java
 * @brief       搬送Job用モデル
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
 * 2018/10/23 MACS4#0020  HostZoneIDの表示(GUI)                       T.Iga/CSC
 * 2018/11/07 MACS4#0034  PriorityChange(GUI)対応                     T.Iga/CSC
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
 * @brief     搬送Job用モデルクラス
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
public class TransferJob {

    private Date receivedTime;
    private String jobOwner;
    private String jobState;
    private String jobId;
    private Boolean compFlag;
    private String carrierId;
    private String currentLoc;
    private String currentAmhsId;
    private String carrierJobId;
    private String transGrpId;
    private String hostGrpId;
    private String hostDrvSrvId;
    private Short priority;
    private String carrierJobState;
    private String lastNormCjobState;
    private String activity;
    private String srcAmhsId;
    private String srcLoc;
    private String dstAmhsId;
    private String dstLoc;
    private Short tableNo;
    private Long routeNo;
    private Short seqNo;
    private String machineId;
    private String stageCmdId;
    private String stageCmdAmhsId;
    private String macroSrcAmhsId;
    private String macroSrcLoc;
    private String macroDstGroupId;
    private String macroDstAmhsId;
    private String macroDstLoc;
    private String orgDstGroupId;
    private String orgDstAmhsId;
    private String orgDstLoc;
    private Short orgPriority;
    private Date macroStartTime;
    private Date eta;
    private Date pta;
    private Date ptd;
    private Boolean estFlag;
    private Boolean erFlag;
    private String logTable;
    private String commandId;
    private Short jobActionCtrl;
    private Date jobActionTime;
    private Short priorityCJP;
    private String srcLocCJP;
    private String dstLocCJP;
    private String hideMacroDstAmhsId;
    private Boolean etaFlag;
    private String carrierJobIdJT;
    private Short hideCarrierJobState;
    private Date waitInTime;
    private String alias;
    private String jobType;
    private String productId;
    private String lotId;
    private String hostZoneId;  // MACS4#0020 Add
    private String reqPriority; // MACS4#0034 Add
    private String reqToLoc;    // MACS4#0034 Add
}
