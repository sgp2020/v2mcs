//@formatter:off
/**
 ******************************************************************************
 * @file        ResIdListSelectGroupConfigEntity.java
 * @brief       AmhsGroupIdリストレスポンスエンティティ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

import java.util.ArrayList;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AmhsGroupIdリストレスポンスエンティティ
 * @par      AmhsGroupIdリストレスポンスエンティティクラス。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ResIdListSelectGroupConfigEntity extends AjaxResBaseEntity {

    public List<IdListSelectGroupEntity> body = new ArrayList<IdListSelectGroupEntity>();
}
