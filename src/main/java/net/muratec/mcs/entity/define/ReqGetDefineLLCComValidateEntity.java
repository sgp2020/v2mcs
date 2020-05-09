//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetDefineLLCComValidateEntity.java
 * @brief       comconfリスト取得用リクエストエンティティ
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
package net.muratec.mcs.entity.define;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.NumRange;

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
 * 2020.03.26 	ReqGetDefineLLCComValidateEntity 					天津／張東江
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetDefineLLCComValidateEntity extends AjaxDataTablesReqBaseEntity {

    @NumRange(min = 0,max = 999999) @NotBlank @FieldNameKey(key = "ID-003-03-001") public Long sid ;
    @ByteRange(min = 0,max = 64) @NotBlank @FieldNameKey(key = "ID-003-04-003") public String name;
    @ByteRange(min = 0,max = 64) @FieldNameKey(key = "ID-003-03-002") public String hostName;
    @NumRange(min = 0,max = 999999) @FieldNameKey(key = "ID-003-03-003") public String portNo;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-001") public String connectMode;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-002") public String linkTest;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-004") public String t3;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-005") public String t5;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-006") public String t6;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-007") public String t7;
    @NumRange(min = 0,max = 99999) @FieldNameKey(key = "ID-003-04-008") public String t8;
}
