//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetEmptyCarrierSelectBoxEntity.java
 * @brief       空FOUPマスタメンテナンス 一覧画面 情報取得用エンティティ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 *****************************************************************************
 * @brief    空FOUPマスタメンテナンス関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetStockerInfoSelectBoxEntity extends AjaxDataTablesResBaseEntity {

//  public List<String[]> controllerIdList = new ArrayList<String[]>();
  public List<String[]> tscIdList = new ArrayList<String[]>();
}
