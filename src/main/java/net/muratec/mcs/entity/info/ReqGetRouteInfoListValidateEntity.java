//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetRouteInfoListValidateEntity.java
 * @brief       アラーム情報表示画面の一覧リクエストエンティティ（検証）
 * @par
 * @author      CSC
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

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     一覧リクエストエンティティ（検証）クラス
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
public class ReqGetRouteInfoListValidateEntity extends AjaxDataTablesReqBaseEntity {
	 public String srcPieceId;
     public String dstPieceId;
     public String tableNo;
}
