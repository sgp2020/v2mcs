//@formatter:off
/**
 ******************************************************************************
 * @file        NumValidator.java
 * @brief       数値型チェック用バリデーションエンティティ
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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.muratec.mcs.entity.validator.annotation.Num;

//@formatter:off
/**
 ******************************************************************************
 * @brief     数値型チェック用バリデーションクラス
 * @par       機能:
 *              入力された値が数値型であるかをチェック
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class NumValidator implements ConstraintValidator<Num, String> {

    @Override
    public void initialize(Num num) {

    }

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     数値型チェック
     * @param     input  画面より入力された文字列
     *             Con    バリデーターコンテキスト
     * @return    チェック結果
     * @retval    Boolean形式で返却
     * @attention
     * @note      入力された値が数値型であるかをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isValid(String input, ConstraintValidatorContext con) {

        if (input != null && input.length() > 0) {
            try {
                Long.parseLong(input);
            } catch (NumberFormatException nfex) {
                return false;
            }
        }
        return true;
    }
}
