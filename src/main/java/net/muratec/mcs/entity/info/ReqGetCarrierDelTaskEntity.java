//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetCarrierDelTaskEntity.java
 * @brief       キャリア削除用エンティティ
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.info;

import java.util.List;

import net.muratec.mcs.annotation.FieldNameKey;

//@formatter:off
/**
 ******************************************************************************
 * @brief      キャリア削除用エンティティクラス
 * @par
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ReqGetCarrierDelTaskEntity {

    @FieldNameKey(key = "v2II-002-01-002") public List<ReqGetCarrierDelEntity> delList;
}
