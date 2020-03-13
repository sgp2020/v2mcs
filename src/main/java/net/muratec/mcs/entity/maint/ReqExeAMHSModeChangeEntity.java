//@formatter:off
/**
 ******************************************************************************
 * @file        ReqExeAMHSModeChangeEntity.java
 * @brief       AMHSモード変更関連の外部プロセス実行条件エンティティ
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

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSモード変更関連の外部プロセス実行条件エンティティクラス
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
public class ReqExeAMHSModeChangeEntity extends AjaxReqBaseEntity {

    public Short controllerType = null;
    public String controller = null;
}
