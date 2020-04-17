//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetTestCarrierInfoListValidateEntity.java
 * @brief       テストキャリア情報表示画面の一覧リクエストエンティティ（検証）
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE        VER.        DESCRIPTION                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12   1                                         天津／張東江
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
 * @brief     一覧リクエストエンティティ（検証）クラス
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
public class ReqGetTestCarrierListValidateEntity extends AjaxDataTablesReqBaseEntity {
	
	@FieldNameKey(key = "II-007-02-001") String status;
	@FieldNameKey(key = "II-007-04-001") String carrierId;
	@FieldNameKey(key = "II-007-04-002") String currentTscId;
	String currentTscName;
}
