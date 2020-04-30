//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetSystemLogValidateEntity.java
 * @brief       Stocker IDリスト取得用リクエストエンティティ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.10   Ver2.0	  MCSV4　GUI開発   							天津／張東江
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
import net.muratec.mcs.entity.validator.annotation.NumRange;
import net.muratec.mcs.entity.validator.annotation.NumRangeString;

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
 * 2020.03.26 	ReqGetHostCommListValidateEntity 					董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetSystemLogValidateEntity extends AjaxDataTablesReqBaseEntity {

	@FieldNameKey(key = "IH-007-03-002") public Boolean debug ;
    @FieldNameKey(key = "IH-007-03-003") public Boolean information;
    @FieldNameKey(key = "IH-007-03-004") public Boolean warning;
    @FieldNameKey(key = "IH-007-03-005") public Boolean error;
    @FieldNameKey(key = "IH-007-03-006") public Boolean performance;
    @DateTime @NotBlank @FieldNameKey(key = "IH-007-03-007, IH-007-03-012") public String dateFrom;
    @DateTime @NotBlank @FieldNameKey(key = "IH-007-03-007, IH-007-03-013") public String dateTo;
    @NumRange(min = 1,max = 10000) @FieldNameKey(key = "IH-007-03-008") public String maxRecords;

}
