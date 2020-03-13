//@formatter:off
/**
 ******************************************************************************
 * @file        Num.java
 * @brief       数値型チェック用アノテーション
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

import net.muratec.mcs.entity.validator.NumValidator;

//@formatter:off
/**
 ******************************************************************************
 * @brief     数値型チェック用インターフェース
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
@Constraint(validatedBy = NumValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Num {

    String message() default "{mcs.validator.Num.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
