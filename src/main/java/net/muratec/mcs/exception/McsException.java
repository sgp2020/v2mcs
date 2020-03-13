//@formatter:off
/**
 ******************************************************************************
 * @file        McsException.java
 * @brief       汎用エクセプション
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
package net.muratec.mcs.exception;

//@formatter:off
/**
 ******************************************************************************
 * @brief     汎用エクセプションクラス
 * @par       機能:
 *              McsException（コンストラクタ）
 *              getCode（エラーコードの返却を行う）
 *              getMessageKey（エラーメッセージキーの返却を行う）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsException extends Exception {

    private static final long serialVersionUID = 1L;
    private int code;
    private String messageKey = null;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     code          エラーコード
     * @param     message       エラーメッセージ
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(int code, String message) {
        super(message);
        this.code = code;
        this.messageKey = null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     message       エラーメッセージ
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(String message) {
        super(message);
        this.code = 0;
        this.messageKey = null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     cause         例外クラス
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(Throwable cause) {
        super(cause);
        this.code = 0;
        this.messageKey = null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     message       エラーメッセージ
     * @param     cause         例外クラス
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(String message, Throwable cause) {
        super(message, cause);
        this.code = 0;
        this.messageKey = null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     messageKey    エラーメッセージキー
     * @param     message       エラーメッセージ
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(String messageKey, String message) {
        super(message);
        this.code = 0;
        this.messageKey = messageKey;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsException（コンストラクタ）機能
     * @param     code          エラーコード
     * @param     messageKey    エラーメッセージキー
     * @param     message       エラーメッセージ
     * @param     cause         例外クラス
     * @return
     * @retval
     * @attention
     * @note      McsExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public McsException(int code, String messageKey, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.messageKey = messageKey;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCode（エラーコードの返却を行う）機能
     * @param
     * @return    エラーコード
     * @retval
     * @attention
     * @note      エラーコードを返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public int getCode() {

        return code;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getMessageKey（エラーメッセージキーの返却を行う）機能
     * @param
     * @return    エラーコード
     * @retval
     * @attention
     * @note      エラーメッセージキーを返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getMessageKey() {

        return messageKey;
    }
}
