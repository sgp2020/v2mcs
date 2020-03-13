//@formatter:off
/**
 ******************************************************************************
 * @file        OpLog.java
 * @brief       操作ログ指定用アノテーション
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
 * @brief     操作ログ指定用インターフェイス
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
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {

    /**
     * ログタイプ 1000000000
     */
    long logType() default ComConst.LogType.OPERATION_LOG;

    /**
     * 画面情報（画面ID,画面ログNo）
     */
    ComConst.ScreenInfo screenInfo();

    /**
     * 操作種別
     */
    long logOperationType();

    /**
     * 画面内連番
     */
    long number() default 1L;

}
