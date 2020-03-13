//@formatter:off
/**
 ******************************************************************************
 * @file        NumAsterValidator.java
 * @brief       数値型またはアスタリスクチェック（あいまい検索）用バリデーションエンティティ
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
package net.muratec.mcs.entity.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.muratec.mcs.entity.validator.annotation.NumAster;

//@formatter:off
/**
 ******************************************************************************
 * @brief     数値型またはアスタリスクチェック（あいまい検索）用バリデーションクラス
 * @par       機能:
 *              入力された値が数値型(1-9)またはアスタリスク(*)であるかをチェック
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class NumAsterValidator implements ConstraintValidator<NumAster, String> {

    @Override
    public void initialize(NumAster num) {

    }

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     数値型またはアスタリスクチェック（あいまい検索）
     * @param     input  画面より入力された文字列
     *             Con    バリデーターコンテキスト
     * @return    チェック結果
     * @retval    Boolean形式で返却
     * @attention
     * @note      入力された値が数値型(1-9)またはアスタリスク(*)であるかをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isValid(String input, ConstraintValidatorContext con) {

        if (input != null) {
            Pattern p = Pattern.compile("^[0-9*]*$");
            Matcher m = p.matcher(input);
            return m.find();
        } else {
            return true;
        }
    }

}
