//@formatter:off
/**
 ******************************************************************************
 * @file        ReqEmptyCarrierEntity.java
 * @brief       空FOUP管理関連のエンティティ
 * * @par
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
 * 2019/02/19 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    AtomicActivityHist関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.04.09	 	ReqGetStatisticsHistoryJobEntity 					DONG
 ****************************************************************************** ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetStatisticsHistoryJobEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-005-03-001") public String tscId ;
    @FieldNameKey(key = "IH-005-03-002") public String source;
    @FieldNameKey(key = "IH-005-03-003") public String destination;
    @FieldNameKey(key = "IH-005-03-004") public String unit;
    @FieldNameKey(key = "IH-005-03-006, IH-005-03-011") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-005-03-006, IH-005-03-012") public Timestamp dateTo;
}
