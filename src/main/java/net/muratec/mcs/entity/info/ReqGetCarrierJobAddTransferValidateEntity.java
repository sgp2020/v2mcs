//@formatter:off
/**
 ******************************************************************************
 * @file        ReqGetCarrierJobAddTransferValidateEntity.java
 * @brief       搬送JOB作成入力チェック用エンティティ
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
import net.muratec.mcs.entity.validator.annotation.NumRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送JOB作成入力チェック用エンティティクラス
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
public class ReqGetCarrierJobAddTransferValidateEntity {
    
    @FieldNameKey(key = "II-002-04-001") public String carrierId;
    @FieldNameKey(key = "II-002-04-007") public String fromCzone;    
    @FieldNameKey(key = "II-002-04-003, LocationSelectBox_Select.language.labels.controller") public String fromAmhsId;
    @FieldNameKey(key = "II-002-04-003, LocationSelectBox_Select.language.labels.port") public String fromPort;
    @NotBlank @NumRange(min = 1, max = 99) @FieldNameKey(key = "II-002-03-009") public String jobPriority;
    @NotBlank @FieldNameKey(key = "II-002-03-013, LocationSelectBox_Select.language.labels.controller") public String controller;
    @FieldNameKey(key = "II-002-03-013, LocationSelectBox_Select.language.labels.port") public String port;
    @FieldNameKey(hidden = false) public String inputType;
    @FieldNameKey(hidden = false) public String codeBrowser;
    

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
