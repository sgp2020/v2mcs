//@formatter:off
/**
******************************************************************************
* @file        ResGetCarrierPortListEntity.java
* @brief       キャリア情報画面 追加画面用レスポンスエンティティ
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
 * 2018/10/01 v1.0.0      初版作成                                         CSC
******************************************************************************
*/
//@formatter:on
package net.muratec.mcs.entity.info;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
******************************************************************************
* @brief     キャリア情報画面 追加画面用レスポンスエンティティクラス
* @par       機能:
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
public class ResGetCarrierPortListEntity extends AjaxDataTablesResBaseEntity {

  // ------------------------------------
  // データ本文
  // ------------------------------------
  public List<String[]> body = new ArrayList<String[]>();

}
