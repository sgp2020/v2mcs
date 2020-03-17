//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetOhbPortRltEntity.java
 * @brief      OhbPortRltの検索条件エンティティ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                         AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/17 0.8                                               SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     OhbPortRltの検索条件エンティティクラス
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
public class ReqGetOhbPortRltEntity extends AjaxReqBaseEntity {

    public String ohbId = null;
}
