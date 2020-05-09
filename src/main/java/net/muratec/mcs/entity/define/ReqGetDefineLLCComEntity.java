//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetDefineLLCComEntity.java
 * @brief       comconf関連のエンティティ
 * * @par
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
 * 2020/04/01 v1.0.0      初版作成                                          天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.define;
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
 * 2020.03.26 	 									天津／張東江
 ****************************************************************************** ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetDefineLLCComEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "ID-003-03-001") public String sid ;
    @FieldNameKey(key = "ID-003-04-003") public String name;
    @FieldNameKey(key = "ID-003-03-002") public String hostName;
    @FieldNameKey(key = "ID-003-03-003") public String portNo;
    @FieldNameKey(key = "ID-003-04-001") public String connectMode;
    @FieldNameKey(key = "ID-003-04-002") public String linkTest;
    @FieldNameKey(key = "ID-003-04-004") public String t3;
    @FieldNameKey(key = "ID-003-04-005") public String t5;
    @FieldNameKey(key = "ID-003-04-006") public String t6;
    @FieldNameKey(key = "ID-003-04-007") public String t7;
    @FieldNameKey(key = "ID-003-04-008") public String t8;
}
