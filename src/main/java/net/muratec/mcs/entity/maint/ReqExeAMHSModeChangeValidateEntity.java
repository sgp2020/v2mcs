//@formatter:off
/**
 ******************************************************************************
 * @file        ReqExeAMHSModeChangeValidateEntity.java
 * @brief       AMHSモード変更関連の外部プロセス実行条件Validateエンティティ
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.maint;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSモード変更関連の外部プロセス実行条件Validateエンティティクラス
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
public class ReqExeAMHSModeChangeValidateEntity extends AjaxReqBaseEntity {

    @NotBlank @FieldNameKey(key = "IM-010-01-001") public String controllerType = null;
    @NotBlank @FieldNameKey(key = "IM-010-01-003") public String controller = null;
}
