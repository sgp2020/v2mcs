//@formatter:off
/**
 ******************************************************************************
 * @file        NumRangeValidator.java
 * @brief       文字数チェック用バリデーションエンティティ
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

import net.muratec.mcs.entity.validator.annotation.NumRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     文字数チェック用バリデーションクラス
 * @par       機能:
 *              入力された文字列の文字数が最小値～最大値の範囲内であるかをチェック
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class NumRangeValidator implements ConstraintValidator<NumRange, String> {

    private long minValue;
    private long maxValue;

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     初期化処理
     * @param     numRange  文字列の最大値、最小値
     * @return
     * @retval
     * @attention
     * @note      文字列の最大値、最小値を変数へセット
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void initialize(NumRange numRange) {

        this.minValue = numRange.min();
        this.maxValue = numRange.max();
    }

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字数チェック
     * @param     input  画面より入力された文字列
     *             Con    バリデーターコンテキスト
     * @return    チェック結果
     * @retval    Boolean形式で返却
     * @attention
     * @note      入力された文字列の文字数が最小値～最大値の範囲内であるかをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isValid(String input, ConstraintValidatorContext con) {

        if (input != null && input.length() > 0) {
            // 半角整数チェック
            Pattern p = Pattern.compile("^[+-]?[0-9]+$|^[0-9]*$");
            Matcher m = p.matcher(input);
            if (!m.find()) {
                return false;
            }

            // 数値範囲チェック
            try {
                long value = Long.parseLong(input);
                if (value < minValue) {
                    return false;
                }
                if (value > maxValue) {
                    return false;
                }
            } catch (NumberFormatException nfex) {
                return false;
            }
        }
        return true;
    }
}
