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
 * @brief    SystemLog関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.26 	ReqGetcEntity 					董 天津村研
 ****************************************************************************** ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetSystemLogEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-007-03-002") public String debug ;
    @FieldNameKey(key = "IH-007-03-003") public String information;
    @FieldNameKey(key = "IH-007-03-004") public String warning;
    @FieldNameKey(key = "IH-007-03-005") public String error;
    @FieldNameKey(key = "IH-007-03-006") public String performance;
    @FieldNameKey(key = "IH-007-03-007, IH-007-03-012") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-007-03-007, IH-007-03-013") public Timestamp dateTo;
    @FieldNameKey(key = "IH-007-03-008") public String maxRecords;
}
