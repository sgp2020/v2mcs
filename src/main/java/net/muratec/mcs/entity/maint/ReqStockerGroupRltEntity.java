//@formatter:off
/**
 ******************************************************************************
 * @file        ReqStockerGroupRltEntity.java
 * @brief       ストッカグループマスタメンテ 追加/修正連携用エンティティ
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ストッカグループマスタメンテ 追加/修正連携用エンティティクラス
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
public class ReqStockerGroupRltEntity {

    @FieldNameKey(key = "IM-001-02-012") public Short flag;
    @FieldNameKey(key = "IM-001-02-010") public String member = null;
}
