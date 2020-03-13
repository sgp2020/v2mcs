//@formatter:off
/**
 ******************************************************************************
 * @file        ReqIndividualMonitorValidateEntity.java
 * @brief       個別モニタ情報取得用Validateエンティティ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.top;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ情報取得用Validateエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 *  2019/12/18 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqIndividualMonitorValidateEntity extends AjaxReqBaseEntity {

//    @NotBlank @FieldNameKey(key = "IT-001-11-002") public String amhsId;//20191220 DQY DEL
//    @NotBlank @FieldNameKey(key = "IT-001-11-002") public String displayName;//20191220 DQY ADD
    @NotBlank @FieldNameKey(key = "IT-001-11-002") public String displayId;//20191220 DQY ADD
    public String ctrlChgFlag;
}
