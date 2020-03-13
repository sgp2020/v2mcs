//@formatter:off
/**
 ******************************************************************************
 * @file        SiteMapEntity.java
 * @brief       サイトマップ用のエンティティ
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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     サイトマップ用のエンティティ
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
public class SiteMapEntity {

    /**
     * カテゴリリスト
     */
    public List<SiteMapInfoEntity> label = new ArrayList<SiteMapInfoEntity>();

    /**
     * ボタンリスト
     */
    public List<List<SiteMapInfoEntity>> button = new ArrayList<List<SiteMapInfoEntity>>();
}
