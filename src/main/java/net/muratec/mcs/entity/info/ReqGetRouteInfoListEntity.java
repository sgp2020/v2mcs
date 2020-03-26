//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetRouteInfoListEntity.java
 * @brief       アラーム情報表示画面の一覧取得リクエストエンティティ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                           SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     一覧取得リクエストエンティティクラス
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
public class ReqGetRouteInfoListEntity extends AjaxDataTablesReqBaseEntity {
	
	 public String srcPieceId;
     public String dstPieceId;
     public String tableNo;
}
