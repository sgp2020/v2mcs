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
 * 2020/4/9   v1.0.0                     初版作成                                							          天津村研　董
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
 * @brief    JobStatisticsHistory画面用レスポンスエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/4/9 ResGetJobStatisticsHistoryListEntity						天津村研　董
 ******************************************************************************
 */
//@formatter:on
public class ResGetJobStatisticsHistoryListEntity extends AjaxDataTablesResBaseEntity {
    public List<JobStatisticsHistoryListEntity> body = new ArrayList<JobStatisticsHistoryListEntity>();
}
