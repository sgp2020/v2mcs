//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetStockerInfoListValidateEntity.java
 * @brief       Stocker IDリスト取得用リクエストエンティティ
 * @par
 * @author      DONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.10   Ver2.0	  MCSV4　GUI開発   							董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.DateTime;

//@formatter:off
/**
 ******************************************************************************
 * @brief    HOST_NAME,COMM_STATE リスト取得用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.26 	MACSV2                          					   ZHANGDONG
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetStockerStatisticsHistValidateEntity extends AjaxDataTablesReqBaseEntity {

	@FieldNameKey(key = "IH-003-03-001") public String tscId;
	@FieldNameKey(key = "IH-003-03-002") public String unit;
    @DateTime @NotBlank @FieldNameKey(key = "IH-003-03-003, IH-003-03-006") public String dateFrom;
    @DateTime @NotBlank @FieldNameKey(key = "IH-003-03-003, IH-003-03-007") public String dateTo;
    public String tscName ;
    public String unitName;
}
