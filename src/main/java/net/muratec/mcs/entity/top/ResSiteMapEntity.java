//@formatter:off
/**
 ******************************************************************************
 * @file        ResSiteMapEntity.java
 * @brief       サイトマップ用のレスポンスエンティティ
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
package net.muratec.mcs.entity.top;

import net.muratec.mcs.entity.common.AjaxResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     サイトマップ用のレスポンスエンティティ
 * @par       サイトマップ用のレスポンスエンティティクラス
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResSiteMapEntity extends AjaxResBaseEntity {

  /**
   * サイトマップの設定値
   */
  public SiteMapEntity body = new SiteMapEntity();
}
