//@formatter:off
/**
 ******************************************************************************
 * @file        ComMsgUtil.java
 * @brief       メッセージ共通関数クラス
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

import java.nio.ByteBuffer;
import java.util.StringTokenizer;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ共通関数定義
 * @par    制御側へのメッセージ送受信時に使用する共通関数を定義
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
//@formatter:off
/**
 ******************************************************************************
 * @brief     メッセージ共通関数クラス
 * @par       機能:
 *              splitByDelim (文字列分割処理)
 *              convertLongToByteArray (バイトオーダー変換処理:long)
 *              convertByteArrayToLong (long型変換処理)
 *              convertIntToByteArray (バイトオーダー変換処理:int)
 *              convertByteArrayToInt (int型変換処理)
 *              convertShortToByteArray (バイトオーダー変換処理:short)
 *              convertByteArrayToShort (short型変換処理)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComMsgUtil {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  文字列分割処理
     * @par    文字列の分割処理
     * @param  strVal 対象文字列
     * @param  strDelim 分割文字
     * @return 分割文字列
     * @note   指定された分割文字に従って、文字列の分割処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static String[] splitByDelim(String strVal, String strDelim) {

        try {
            StringTokenizer token = new StringTokenizer(strVal, strDelim);
            int nLength = token.countTokens();
            String[] strArray = new String[nLength];
            for (int i = 0; i < nLength; i++) {
                strArray[i] = token.nextToken();
            }
            return strArray;
        } catch (Exception ex) {
            return null;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  バイトオーダー変換処理:long
     * @par    long型のバイトオーダー変換を実施
     * @param  lVal 変換対象
     * @return バイト配列
     * @note   long型をbyte配列へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static byte[] convertLongToByteArray(long lVal) {

        ByteBuffer buf = ByteBuffer.allocate(8);
        buf.putLong(lVal);

        return buf.array();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  long型変換処理
     * @par    byte配列をlong型に変換
     * @param  bVal 変換対象
     * @return long型
     * @note   byte配列をlong型へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static long convertByteArrayToLong(byte[] bVal) {

        ByteBuffer buf = ByteBuffer.wrap(bVal);
        return buf.getLong();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  バイトオーダー変換処理:int
     * @par    int型のバイトオーダー変換を実施
     * @param  nVal 変換対象
     * @return バイト配列
     * @note   int型をbyte配列へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static byte[] convertIntToByteArray(int nVal) {

        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(nVal);

        return buf.array();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  int型変換処理
     * @par    byte配列をint型に変換
     * @param  bVal 変換対象
     * @return int型
     * @note   byte配列をint型へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static int convertByteArrayToInt(byte[] bVal) {

        ByteBuffer buf = ByteBuffer.wrap(bVal);
        return buf.getInt();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  バイトオーダー変換処理:short
     * @par    short型のバイトオーダー変換を実施
     * @param  nVal 変換対象
     * @return バイト配列
     * @note   short型をbyte配列へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static byte[] convertShortToByteArray(short sVal) {

        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.putShort(sVal);

        return buf.array();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  short型変換処理
     * @par    byte配列をshort型に変換
     * @param  bVal 変換対象
     * @return short型
     * @note   byte配列をshort型へ変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static short convertByteArrayToShort(byte[] bVal) {

        ByteBuffer buf = ByteBuffer.wrap(bVal);
        return buf.getShort();
    }
}
