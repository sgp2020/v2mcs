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
package net.muratec.mcs.entity.hist;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
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
 *  20200401   MacroData                    						     DONG
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetMacroDataValidateEntity extends AjaxDataTablesReqBaseEntity {

    @NotBlank @FieldNameKey(key = "IH-002-01-017") public String commandId;
//    public String ctrlChgFlag;
}
