//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetRouteInfoListEntity.java
 * @brief       アラーム情報表示画面の一覧取得のレスポンスエンティティ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE        VER.        DESCRIPTION                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25   2                                          SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示画面の一覧取得のレスポンスエンティティクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResGetRouteInfoListEntity extends AjaxDataTablesResBaseEntity {

    // ------------------------------------
    // データ本文
    // ------------------------------------
    public List<RouteListEntity> body = new ArrayList<RouteListEntity>();

}
