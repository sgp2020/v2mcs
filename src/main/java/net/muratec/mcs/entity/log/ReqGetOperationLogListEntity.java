//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetOperationLogListEntity.java
 * @brief       操作ログ表示画面の一覧取得リクエストエンティティ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.log;

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
public class ReqGetOperationLogListEntity extends AjaxDataTablesReqBaseEntity {

    // ------------------------------------
    // 日時（開始）
    // ------------------------------------
    @FieldNameKey(key = "IL-006-02-001, IL-006-02-002") public Timestamp from;

    // ------------------------------------
    // 日時（終了）
    // ------------------------------------
    @FieldNameKey(key = "IL-006-02-001, IL-006-02-003") public Timestamp to;

    // ------------------------------------
    // ユーザ
    // ------------------------------------
    @FieldNameKey(key = "IL-006-02-004") public String userId;

    // ------------------------------------
    // コード
    // ------------------------------------
    @FieldNameKey(key = "IL-006-02-006") public String logCode;

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
