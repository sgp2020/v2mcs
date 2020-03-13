﻿//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetCarrierListEntity.java
 * @brief       キャリア情報表示画面の一覧取得リクエストエンティティ
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
package net.muratec.mcs.entity.info;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     一覧取得リクエストエンティティクラス
 * @par       機能:
 *              toString（インスタンスの文字列表現を返す）
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
public class ReqGetCarrierListEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "v2II-002-02-001") public String currentTscId;
    @FieldNameKey(key = "v2II-002-02-003") public String carrierId;
    @FieldNameKey(key = "v2II-002-02-005") public String state;
    @FieldNameKey(key = "v2II-002-02-007, v2II-002-02-008") public Timestamp sfrom;
    @FieldNameKey(key = "v2II-002-02-007, v2II-002-02-009") public Timestamp sto;
    @FieldNameKey(key = "v2II-002-02-011, v2II-002-02-012") public Timestamp ifrom;
    @FieldNameKey(key = "v2II-002-02-011, v2II-002-02-013") public Timestamp ito;    

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     toString（インスタンスの文字列表現を返す）機能
     * @return    インスタンスの文字列表現
     * @retval    String形式で返却
     * @attention
     * @note      インスタンスの文字列表現を返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            try {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(this);
                sb.append(' ').append(name).append('=').append(value != null ? value.toString() : "null");
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(accessible);
        }
        return sb.toString();
    }
}
