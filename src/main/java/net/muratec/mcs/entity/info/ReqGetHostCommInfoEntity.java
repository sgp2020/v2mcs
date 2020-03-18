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
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    空FOUP管理関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetHostCommInfoEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "II-009-04-001") public String hostName = null;
    @FieldNameKey(key = "II-009-04-002") public String commState = null;
}
