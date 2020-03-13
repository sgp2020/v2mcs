//@formatter:off
/**
 ******************************************************************************
 * @file        String2TimestampConverter.java
 * @brief       文字列からTimestampへの変換
 * @par
 * @author      CSC
 * $Id:         $
 * @attention  org.apache.commons.beanutils.ConvertUtilsのオリジナル変換で利用
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
package net.muratec.mcs.common.converter;

import org.apache.commons.beanutils.Converter;

import net.muratec.mcs.common.ComFunction;

//@formatter:off
/**
 ******************************************************************************
 * @brief     文字列からTimestampへの変換クラス
 * @par       機能:
 *              convert（文字列からTimestampへ変換）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class String2TimestampConverter implements Converter {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列からTimestampへ変換を行う
     * @param     type           Class（未使用）
     * @param     value          変換前の値
     * @return    Timestampへの変換した結果
     * @retval    変換失敗時はNULLを返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Object convert(Class type, Object value) {

        if (value == null) {
            return null;
        } else if (value instanceof java.sql.Timestamp) {
            return value;
        } else {
            String strValue = value.toString();
            if (strValue.isEmpty()) {
                return null;
            }

            return ComFunction.stringToTimestamp(strValue);
        }
    }
}
