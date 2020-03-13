//@formatter:off
/**
 ******************************************************************************
 * @file        FieldNameKey.java
 * @brief       フィールド名から項目名（英）変換用アノテーション
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
package net.muratec.mcs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.muratec.mcs.common.ComConst;

//@formatter:off
/**
 ******************************************************************************
 * @brief     フィールド名から項目名（英）変換用インターフェイス
 * @par       機能:
 *              アノテーション用の変数宣言のみ
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNameKey {

    /**
     * メッセージキー
     */
    String key() default ComConst.FIELD_NAME_KEY_NONE;

    /**
     * マスクフラグ（ture:隠す false:隠さない）
     *
     * @return
     */
    boolean mask() default false;

    /**
     * 非出力フラグ（ture:ログ出力しない false:ログ出力）
     *
     * @return
     */
    boolean hidden() default false;

}
