//@formatter:off
/**
 ******************************************************************************
 * @file        BaseService.java
 * @brief       基底サービス
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
package net.muratec.mcs.service.common;

import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     基底サービスクラス
 * @par       機能:
 *              listToCommaStr（カンマ区切り文字列変換処理（リスト））
 *              commaStrToArray（配列変換処理（カンマ区切り文字列））
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class BaseService {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     カンマ区切り文字列変換処理（リスト）
     * @param     list           リスト
     * @return    文字列（カンマ区切り）
     * @retval
     * @attention
     * @note      リストをカンマ区切りの文字列に変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected <T> String listToCommaStr(List<T> list) {

        StringBuilder sb = new StringBuilder();
        boolean commna = false;
        for (T val : list) {
            if (commna) {
                sb.append(',');
            } else {
                commna = true;
            }
            sb.append(val.toString());
        }
        return sb.toString();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     配列変換処理（カンマ区切り文字列）
     * @param     str            カンマ区切り文字列
     * @return    配列
     * @retval
     * @attention
     * @note      カンマ区切りの文字列を配列に変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    protected String[] commaStrToArray(String str) {

        if (str == null || str.length() == 0) {
            return new String[] {};
        }
        return str.split(",");
    }
}
