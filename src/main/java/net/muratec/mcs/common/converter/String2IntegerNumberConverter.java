//@formatter:off
/**
 ******************************************************************************
 * @file        String2IntegerNumberConverter.java
 * @brief       文字列から整数への変換
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
 * @brief     文字列から整数への変換クラス
 * @par       機能:
 *              convert（文字列から整数へ変換）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class String2IntegerNumberConverter implements Converter {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列から整数へ変換を行う
     * @param     type           変換後のClass
     * @param     value          変換前の値
     * @return    整数への変換した結果
     * @retval    変換失敗時はNULLを返却
     * @attention
     * @note      typeへは、Long,Integer,Short,Byteが指定可能
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

            Long lng = null;
            try {
                lng = new Long(strValue);
            } catch (NumberFormatException e) {
                return (T) null;
            }

            try {
                if (Long.class.equals(type)) {
                    return (T) new Long(lng.longValue());
                } else if (Integer.class.equals(type)) {
                    return (T) new Integer(lng.intValue());
                } else if (Short.class.equals(type)) {
                    return (T) new Short(lng.shortValue());
                } else if (Byte.class.equals(type)) {
                    return (T) new Byte(lng.byteValue());
                } else {
                    return (T) null;
                }
            } catch (NumberFormatException e) {
                return (T) null;
            }
        }
    }
}
