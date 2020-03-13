//@formatter:off
/**
 ******************************************************************************
 * @file        McsMsgViewResEntity.java
 * @brief       メッセージ表示のレスポンスエンティティ
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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ表示のレスポンスエンティティ
 * @par    メッセージ表示のレスポンスエンティティ
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class McsMsgViewResEntity extends AjaxResBaseEntity {

    // ! 受信者ID
    public String recvId;
    // ! メッセージリスト
    public List<MsgDataEntity> msgData = new ArrayList<MsgDataEntity>();
}
