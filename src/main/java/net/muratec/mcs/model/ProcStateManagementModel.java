//@formatter:off
/**
 ******************************************************************************
 * @file        ProcStateManagementModel.java
 * @brief       プロセス状態管理用モデルクラス
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
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
* @brief     プロセス状態管理用モデルクラス
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
public class ProcStateManagementModel {

    private Integer pid;
    private Integer systemId;
    private String serviceId;
    private String name;
    private String node;
    private String procType;
    private Integer telnetPort;
    private Short ruleNo;
    private Short useFlag;
    private String procState;
    private String availability;
    private String handleProcGroup;
    private Timestamp startTime;
}
