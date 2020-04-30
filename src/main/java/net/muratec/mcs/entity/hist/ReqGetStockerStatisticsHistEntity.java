//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetStockerStatisticsHistEntity.java
 * @brief       StockerStatisticsHistのエンティティ
 * * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/26  v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.hist;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    StockerStatisticsHist関連のエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.26 	ReqGetcEntity 					董 天津村研
 ****************************************************************************** ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqGetStockerStatisticsHistEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "IH-003-03-001") public String tscId ;
    public String tscName ;
    @FieldNameKey(key = "IH-003-03-002") public String unit;
    public String unitName;
    @FieldNameKey(key = "IH-003-03-003, IH-003-03-006") public Timestamp dateFrom;
    @FieldNameKey(key = "IH-003-03-003, IH-003-03-007") public Timestamp dateTo;
    public String dateFrom1= "";
    public String dateTo1= "";
}
