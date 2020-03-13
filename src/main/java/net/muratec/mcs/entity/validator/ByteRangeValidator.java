//@formatter:off
/**
 ******************************************************************************
 * @file        ByteRangeValidator.java
 * @brief       入力文字Byte数バリデーションチェック用エンティティ
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

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.validator.annotation.ByteRange;

//@formatter:off
/**
 ******************************************************************************
 * @brief     入力文字Byte数バリデーションチェッククラス
 * @par       機能:
 *              入力文字のByte数について最小値～最大値の範囲内かをチェック
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ByteRangeValidator implements ConstraintValidator<ByteRange, String> {

    private long minValue;
    private long maxValue;

    @Override
    public void initialize(ByteRange byteRange) {

        this.minValue = byteRange.min();
        this.maxValue = byteRange.max();
    }

    @Override
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力文字Byte数バリデーションチェック
     * @param     in  画面より入力された文字列
     *             cxt バリデーターコンテキスト
     * @return    チェック結果
     * @retval    Boolean形式で返却
     * @attention
     * @note      入力文字のByte数について最小値～最大値の範囲内かをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isValid(String in, ConstraintValidatorContext cxt) {

        // 入力がnullの場合はtrueを返却する（必須チェックは別途実施しているため）
        if (in == null) {
            return true;
        }

        // Byte数の取得
        long byteSize = ComFunction.getStringByte(in);

        // 最少より小さいor最大より大きい場合エラー
        boolean retFlag = true;
        if ((byteSize < minValue) || (byteSize > maxValue)) {
            retFlag = false;
        }

        return retFlag;

    }
}
