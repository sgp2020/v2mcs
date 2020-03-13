//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetCarrierListValidateEntity.java
 * @brief       キャリア情報表示画面の一覧取得リクエストエンティティ（検証）
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

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.DateTime;

//@formatter:off
/**
 ******************************************************************************
 * @brief     一覧取得リクエストエンティティ（検証）クラス
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
public class ReqGetCarrierListValidateEntity extends AjaxDataTablesReqBaseEntity {

    @FieldNameKey(key = "v2II-002-02-001") public String currentTscId;
    @ByteRange(min = 0, max = 64) @FieldNameKey(key = "v2II-002-02-003") public String carrierId;
    @FieldNameKey(key = "v2II-002-02-005") public String state;
    @DateTime @FieldNameKey(key = "v2II-002-02-007, v2II-002-02-008") public String sfrom;
    @DateTime @FieldNameKey(key = "v2II-002-02-007, v2II-002-02-009") public String sto;
    @DateTime @FieldNameKey(key = "v2II-002-02-011, v2II-002-02-012") public String ifrom;
    @DateTime @FieldNameKey(key = "v2II-002-02-011, v2II-002-02-013") public String ito;

    
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
