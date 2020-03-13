//@formatter:off
/**
 ******************************************************************************
 * @file        ResScMonitorTransferJobListEntity.java
 * @brief       SCモニタ搬送Job画面用レスポンスエンティティ
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

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    SCモニタ搬送Job画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResScMonitorTransferJobListEntity extends AjaxResBaseEntity {

    public List<IndividualMonitorTransferJobEntity> body = new ArrayList<IndividualMonitorTransferJobEntity>();

    public List<String> rowColorList = new ArrayList<String>();
}
