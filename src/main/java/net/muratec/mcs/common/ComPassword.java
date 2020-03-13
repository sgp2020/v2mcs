//@formatter:off
/**
 ******************************************************************************
 * @file        ComPassword.java
 * @brief       パスワードのハッシュ化＆チェック
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
package net.muratec.mcs.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.muratec.mcs.exception.McsException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     パスワードのハッシュ化＆チェックするクラス
 * @par       機能:
 *              isAuthUser（入力されたユーザID、パスワードが有効かチェック）
 *              createPassHash（入力されたユーザID、パスワードでハッシュ化）
 *              getSha256（入力された文字列を SHA-256でハッシュ化）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComPassword {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力されたユーザID、パスワードが有効かチェックする
     * @param     userId        ユーザID
     * @param     password      パスワード
     * @param     dbHash        DBに登録されているHash化されたパスワード
     * @return    チェック結果
     * @retval    true  有効
     * @retval    false 無効
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static boolean isAuthUser(String userId, String password, String dbHash) throws McsException {

        boolean ret = false;

        String hash = createPassHash(userId, password);

        if (hash != null && hash.equals(dbHash)) {
            ret = true;
        }

        return ret;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力されたユーザID、パスワードでハッシュ化
     * @param     userId        ユーザID
     * @param     password      パスワード
     * @return    ハッシュ化した文字列
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static String createPassHash(String userId, String password) throws McsException {

        String salt = getSha256(userId);
        String hash = "";

        for (int i = 0; i < ComConst.STRETCH_COUNT; i++) {
            hash = getSha256(hash + salt + password);
        }
        return hash;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力された文字列を SHA-256でハッシュ化
     * @param     target        ハッシュ化したい文字列
     * @return    ハッシュ化した文字列
     * @retval
     * @attention ハッシュ化はSHA-256を利用
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private static String getSha256(String target) throws McsException {

        MessageDigest md = null;
        StringBuffer buf = new StringBuffer();
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            byte[] digest = md.digest();

            for (int i = 0; i < digest.length; i++) {
                buf.append(String.format("%02x", digest[i]));
            }

        } catch (NoSuchAlgorithmException e) {
            throw new McsException("ERR0011", e.getMessage());
        }

        return buf.toString();
    }

}
