//@formatter:off
/**
 ******************************************************************************
 * @file        TransferJobDetailTable.java
 * @brief       搬送Job（DetailTable）用モデル
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
 * 2019/02/01 MACS4#0095  TransferJobのDetailにVehicleイベント進捗表示 T.Iga/CSC
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
 * @brief     搬送Job（DetailTable）用モデル
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0095  TransferJobのDetailにVehicleイベント進捗表示           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class TransferJobDetailTable {

    private Short tableNo;
    private Long routeNo;
    private Short seqNo;
    private String amhsName;
    private String srcLoc;
    private String dstLoc;
    private Short priority;
    private Date reqTime;
    private Date ansTime;
    private Date iniTime;
    private Date assignedTime;
    private Date acqStartTime;
    private Date acqCompTime;
    private Date srcArrivedTime;
    private Date departedTime;      // MACS4#0095 Add
    private Date dstArrivedTime;    // MACS4#0095 Add
    private Date depoStartTime;
    private Date depoCompTime;
    private Date unassignedTime;
    private Date craneActiveTime;
    private Date craneIdleTime;
    private Date compTime;
    private Date abortReqTime;
    private Date abortAnsTime;
    private Date abortInitTime;
    private Date abortCompTime;
    private String abortReason;
    private Boolean uncompFlag;
    private String machineId;
    private Date estPassTime;
    private Date passedTime;
    private String carrierJobState;
    private String commandId;
    private Date waitInTime;
    private String jobType;
    
}
