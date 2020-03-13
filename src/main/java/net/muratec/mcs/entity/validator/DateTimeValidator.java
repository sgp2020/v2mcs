//@formatter:off
/**
 ******************************************************************************
 * @file        DateTimeValidator.java
 * @brief       日時文字列から日付型変換可否チェック用バリデーションエンティティ
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.entity.validator.annotation.DateTime;

//@formatter:off
/**
 ******************************************************************************
 * @brief     日時文字列から日付型変換可否チェック用バリデーションクラス
 * @par       機能:
 *              入力された日付文字列が日付型へ変換可能かをチェック
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class DateTimeValidator implements ConstraintValidator<DateTime, String> {

    @Override
    public void initialize(DateTime dateTime) {

    }

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日時文字列から日付型変換可否チェック
     * @param     input  画面より入力された文字列
     *             Con    バリデーターコンテキスト
     * @return    チェック結果
     * @retval    Boolean形式で返却
     * @attention
     * @note      入力された日付文字列が日付型へ変換可能かをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isValid(String input, ConstraintValidatorContext con) {

        if (input != null && input.length() > 0) {
            DateFormat format = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_SEPARATOR);
            try {
                format.parse(input);
            } catch (ParseException nfex) {
                return false;
            }
        }
        return true;
    }
}
