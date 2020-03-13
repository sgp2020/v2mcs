//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetTransferRoutePatternEntity.java
 * @brief       搬送パターンマスタメンテナンス
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
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief    搬送パターンマスタメンテナンス
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
public class ReqGetTransferRoutePatternEntity extends AjaxReqBaseEntity {

    @FieldNameKey(key = "IM-029-05-003") public Short tableNo;
    @FieldNameKey(key = "IM-029-05-004") public String patternId;
    @FieldNameKey(key = "IM-029-05-005") public Long routeNo;
}
