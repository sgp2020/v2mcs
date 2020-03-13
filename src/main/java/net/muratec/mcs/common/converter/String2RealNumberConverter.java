//@formatter:off
/**
 ******************************************************************************
 * @file        String2RealNumberConverter.java
 * @brief       文字列から実数への変換
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     文字列から実数への変換クラス
 * @par       機能:
 *              convert（文字列から実数へ変換）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class String2RealNumberConverter implements Converter {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列から実数へ変換を行う
     * @param     type           変換後のClass
     * @param     value          変換前の値
     * @return    実数への変換した結果
     * @retval    変換失敗時はNULLを返却
     * @attention
     * @note      typeへは、Double,Floatが指定可能
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(Class<T> type, Object value) {

        if (value == null) {
            return (T) null;
        } else {
            String strValue = value.toString();
            if (strValue.isEmpty()) {
                return (T) null;
            }

            Double dbl = null;
            try {
                dbl = new Double(strValue);
            } catch (NumberFormatException e) {
                return (T) null;
            }

            try {
                if (Double.class.equals(type)) {
                    return (T) new Double(dbl.doubleValue());
                } else if (Float.class.equals(type)) {
                    return (T) new Float(dbl.floatValue());
                } else {
                    return (T) null;
                }
            } catch (NumberFormatException e) {
                return (T) null;
            }
        }
    }
}
