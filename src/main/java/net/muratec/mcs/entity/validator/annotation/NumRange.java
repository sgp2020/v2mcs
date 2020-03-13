//@formatter:off
/**
 ******************************************************************************
 * @file        NumRange.java
 * @brief       数値型チェック(範囲)用アノテーション
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
package net.muratec.mcs.entity.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.muratec.mcs.entity.validator.NumRangeValidator;

//@formatter:off
/**
 ******************************************************************************
 * @brief     数値型チェック(範囲)用インターフェース
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
@Documented
@Constraint(validatedBy = NumRangeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NumRange {

    String message() default "{mcs.validator.NumRange.message}";

    long min();

    long max();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
