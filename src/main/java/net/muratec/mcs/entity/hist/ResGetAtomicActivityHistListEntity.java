//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetStockerInfoListEntity.java
 * @brief       SCモニタ状態画面用レスポンスエンティティ
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;


import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    AtomicActivityHistory画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/18   ResGetAtomicActivityHistListEntity						天津村研　董
 ******************************************************************************
 */
//@formatter:on
public class ResGetAtomicActivityHistListEntity extends AjaxDataTablesResBaseEntity {
    public List<AtomicActivityHistListEntity> body = new ArrayList<AtomicActivityHistListEntity>();
}
