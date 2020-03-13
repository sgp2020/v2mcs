//@formatter:off
/**
 ******************************************************************************
 * @file       TransferLocationEntity.java
 * @brief      搬送Locationリストエンティティ
 * @par
 * @author     T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/01/31 0.8         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

import java.util.ArrayList;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送Locationリストエンティティ
 * @par       搬送Locationリストエンティティクラス。
 * @attention 
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class TransferLocationEntity {

    public List<TransferLocationSelectEntity> data = new ArrayList<TransferLocationSelectEntity>();
}
