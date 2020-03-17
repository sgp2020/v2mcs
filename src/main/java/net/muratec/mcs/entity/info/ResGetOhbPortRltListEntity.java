//@formatter:off
/**
 ******************************************************************************
 * @file        ResGetOhbPortRltListEntity.java
 * @brief       OhbPortRlt
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                       AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/17 0.8                                            SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.model.OhbPortRltModel;

//@formatter:off
/**
 ******************************************************************************
 * @brief     OhbPortRltの一覧エンティティクラス
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
public class ResGetOhbPortRltListEntity extends AjaxResBaseEntity {

    public List<OhbPortRltModel> ohbPortRltList = new ArrayList<OhbPortRltModel>();
}
