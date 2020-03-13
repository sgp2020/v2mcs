//@formatter:off
/**
 ******************************************************************************
 * @file        McsMsgViewReqEntity.java
 * @brief       メッセージ表示のリクエストエンティティ
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
package net.muratec.mcs.entity.common;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ表示のリクエストエンティティ
 * @par    メッセージ表示のリクエストエンティティ
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class McsMsgViewReqEntity {

    // ! 受信者ID
    public String recvId;
}
