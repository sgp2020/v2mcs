//@formatter:off
/**
 ******************************************************************************
 * @file        ReqIndividualMonitorOhbValidateEntity.java
 * @brief       OHBモニタ情報取得用Validateエンティティ
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
 * @brief    OHBモニタ情報取得用Validateエンティティクラス
 * @par
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
public class ReqIndividualMonitorOhbValidateEntity extends AjaxReqBaseEntity {

//    @NotBlank @FieldNameKey(key = "IT-001-20-002") public String amhsId;//20191225 DQY DEL
    @NotBlank @FieldNameKey(key = "IT-001-20-002") public String displayId;//20191225 DQY ADD
    @NotBlank @FieldNameKey(key = "IT-001-20-016") public String ohbId;
    public String ohbChgFlag;
}
