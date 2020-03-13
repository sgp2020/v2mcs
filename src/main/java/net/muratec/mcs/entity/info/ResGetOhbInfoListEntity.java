//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetOhbInfoListEntity.java
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
 * 2020/03/12   2                                          SGP
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
public class ResGetOhbInfoListEntity extends AjaxDataTablesResBaseEntity {

    // ------------------------------------
    // データ本文
    // ------------------------------------
    public List<OhbInfoListEntity> body = new ArrayList<OhbInfoListEntity>();

}
