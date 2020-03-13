//@formatter:off
/**
 ******************************************************************************
 * @file        AjaxAurgumentException.java
 * @brief       入力値関連のエクセプション
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

import java.util.List;

import net.muratec.mcs.entity.common.AjaxResultEntity;

//@formatter:off
/**
 ******************************************************************************
 * @brief     入力値関連のエクセプションクラス
 * @par       機能:
 *              AjaxAurgumentException（コンストラクタ）
 *              getCode（エラーコードの返却を行う）
 *              getErrorInfoList（エラー情報の返却を行う）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class AjaxAurgumentException extends Exception {

    private static final long serialVersionUID = 1L;
    private int code;
    private List<AjaxResultEntity.ErrorInfo> errorInfoList;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AjaxAurgumentException（コンストラクタ）機能
     * @param     code          エラーコード
     * @param     message       エラーメッセージ
     * @param     errorInfoList エラー情報リスト
     * @return
     * @retval
     * @attention
     * @note      AjaxAurgumentExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public AjaxAurgumentException(int code, String message, List<AjaxResultEntity.ErrorInfo> errorInfoList) {
        super(message);
        this.code = code;
        this.errorInfoList = errorInfoList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AjaxAurgumentException（コンストラクタ）機能
     * @param     code          エラーコード
     * @param     message       エラーメッセージ
     * @return
     * @retval
     * @attention
     * @note      AjaxAurgumentExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public AjaxAurgumentException(int code, String message) {
        super(message);
        this.code = code;
        this.errorInfoList = null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AjaxAurgumentException（コンストラクタ）機能
     * @param     message       エラーメッセージ
     * @return
     * @retval
     * @attention
     * @note      AjaxAurgumentExceptionのコンストラクタ
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public AjaxAurgumentException(String message) {
        super(message);
        this.code = 0;
        this.errorInfoList = null;
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
     * @brief     getErrorInfoList（エラー情報の返却を行う）機能
     * @param
     * @return    エラー情報リスト
     * @retval
     * @attention
     * @note      エラー情報リストを返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<AjaxResultEntity.ErrorInfo> getErrorInfoList() {

        return errorInfoList;
    }
}
