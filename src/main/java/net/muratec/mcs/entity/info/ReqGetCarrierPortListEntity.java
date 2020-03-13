//@formatter:off
/**
******************************************************************************
* @file        ReqGetCarrierPortListEntity.java
* @brief       キャリア情報画面 追加画面用エンティティ
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
* 2016/12/26 0.1         Step1リリース                                  CSC
* ----------------------------------------------------------------------------
******************************************************************************
*/
//@formatter:on
package net.muratec.mcs.entity.info;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
******************************************************************************
* @brief    キャリア情報画面 追加画面用エンティティクラス
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
public class ReqGetCarrierPortListEntity extends AjaxReqBaseEntity {

  @NotNull @NotBlank public String ohbId = null;

}
