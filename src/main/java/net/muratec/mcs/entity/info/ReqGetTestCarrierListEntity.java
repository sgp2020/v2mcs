//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetTestCarrierInfoListEntity.java
 * @brief       テストキャリア情報表示画面の一覧取得リクエストエンティティ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  1                                          天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     一覧取得リクエストエンティティクラス
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
public class ReqGetTestCarrierListEntity extends AjaxDataTablesReqBaseEntity {
	@FieldNameKey(key = "II-007-02-001") public String status = null;
	@FieldNameKey(key = "II-007-04-001") public String currentTscId = null;
	@FieldNameKey(key = "II-007-04-002") public String carrierId = null;
}
