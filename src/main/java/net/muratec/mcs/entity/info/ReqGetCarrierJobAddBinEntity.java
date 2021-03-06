//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetCarrierJobAddEntity.java
 * @brief       キャリア情報追加画面連携用エンティティ
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

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.validator.annotation.ByteRange;
import net.muratec.mcs.entity.validator.annotation.NumRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報追加画面連携用エンティティクラス
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
public class ReqGetCarrierJobAddBinEntity {

    @NotBlank @ByteRange(min = 0, max = 64) @FieldNameKey(key = "v2II-002-05-005") public String carrierId;
    @NotBlank @FieldNameKey(key = "v2II-002-05-008") public String stkId;
    @NumRange(min = 0, max = 99) @FieldNameKey(key = "v2II-002-05-007") public String bin1;
    @NumRange(min = 0, max = 99) @FieldNameKey(key = "v2II-002-05-009") public String bin2;
    @NumRange(min = 0, max = 99) @FieldNameKey(key = "v2II-002-05-010") public String bin3;

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
