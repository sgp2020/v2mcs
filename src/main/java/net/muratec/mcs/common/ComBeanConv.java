//@formatter:off
/**
 ******************************************************************************
 * @file        ComBeanConv.java
 * @brief       Bean間コピーを行う
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
package net.muratec.mcs.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.muratec.mcs.common.converter.String2DateConverter;
import net.muratec.mcs.common.converter.String2IntegerNumberConverter;
import net.muratec.mcs.common.converter.String2RealNumberConverter;
import net.muratec.mcs.common.converter.String2TimestampConverter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     Beanを型変換しながら、同プロパティ名同士でコピーするクラス
 * @par       機能:
 *              ComBeanConv（コンストラクタ）
 *              convert（同名メソッド同士で型変換しながらコピー）
 *              isCopyProperty（変換可否判定）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComBeanConv {

    public static final Logger logger = LoggerFactory.getLogger(ComBeanConv.class);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コンストラクタ
     * @param
     * @return
     * @retval
     * @attention
     * @note      オリジナルの型変換がある場合は、ここに登録する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ComBeanConv() {
        ConvertUtils.register(new String2DateConverter(), Date.class);
        ConvertUtils.register(new String2TimestampConverter(), java.sql.Timestamp.class);
        ConvertUtils.register(new String2IntegerNumberConverter(), Long.class);
        ConvertUtils.register(new String2IntegerNumberConverter(), Integer.class);
        ConvertUtils.register(new String2IntegerNumberConverter(), Short.class);
        ConvertUtils.register(new String2IntegerNumberConverter(), Byte.class);
        ConvertUtils.register(new String2RealNumberConverter(), Double.class);
        ConvertUtils.register(new String2RealNumberConverter(), Float.class);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     同名メソッド同士で型変換しながらコピーする
     * @param     source         コピー元のオブジェクト
     * @param     clazz          コピー先のクラス
     * @return    コピーしたオブジェクト
     * @retval
     * @attention 変換元の各変数の型は、画面からの入力値である「String」を想定
     * @note      引数「clazz」で指定されたクラスのインスタンスを生成し、
     *            sourceからコピーする
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    // @SuppressWarnings("unchecked")
    public <T> T convert(Object source, Class<T> clazz) {

        if (source == null) {
            return null;
        }

        T target = null;
        try {
            target = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e1) {
            logger.warn("target newInstance Error.", e1);
            return null;
        }

        String propertyName = null;

        PropertyDescriptor[] sourceDescs = PropertyUtils.getPropertyDescriptors(source);
        for (PropertyDescriptor sourceDesc : sourceDescs) {
            // 対象プロパティのプロパティ名を取得
            propertyName = sourceDesc.getName();

            if (propertyName.equals("class")) {
                continue;
            }

            // 対象のプロパティ名がターゲットにない場合は次項目へ
            PropertyDescriptor targetDesc = null;
            Field targetField = null;
            try {
                targetDesc = new PropertyDescriptor(propertyName, target.getClass());
                targetField = clazz.getField(propertyName);
            } catch (IntrospectionException | NoSuchFieldException | SecurityException e1) {
                logger.info("Target Property Not Found.[Property Name = " + propertyName + "]");
                continue;
            }

            // 対象プロパティのクラスを取得し、各タイプごとに処理を振り分ける
            Class<?> sourceDescClass = sourceDesc.getPropertyType();

            if (isCopyProperty(sourceDescClass)) {
                // *******************************************************************
                // プリミティブ＆疑似プリミティブ、配列の場合は、素直にBeanUtils.copyPropertyで変換
                // ※配列時は、Targetの型関係なくSourceの型でコピーされる。
                // *******************************************************************
                try {
                    Object value = sourceDesc.getReadMethod().invoke(source, (Object[]) null);
                    BeanUtils.copyProperty(target, propertyName, value);
                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                    // 失敗時は次項目へ
                    logger.warn("BeanUtils.copyProperty Error.[Property Name = " + propertyName + "]", e);
                    continue;
                }
            } else if (List.class.equals(sourceDescClass)) {
                // *******************************************************************
                // リスト形式時は１レコードづつ本変換処理を再帰し、最後にADDする。
                // *******************************************************************
                // 変数宣言
                List<?> sourceDescList = null;
                Class<?> targetClass = null;
                Method targetSetter = targetDesc.getWriteMethod();

                // Sourceのリスト項目取得
                try {
                    sourceDescList = (List<?>) sourceDesc.getReadMethod().invoke(source, (Object[]) null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
                    logger.warn("List sourceDesc.getReadMethod Error.[Property Name = " + propertyName + "]", e1);
                    continue;
                }

                // LISTのジェネリックタイプを取得
                ParameterizedType targetFieldType = (ParameterizedType) targetField.getGenericType();
                if (targetFieldType != null && targetFieldType.getActualTypeArguments().length >= 1) {
                    targetClass = (Class<?>) targetFieldType.getActualTypeArguments()[0];
                } else {
                    logger.warn("List ActualTypeArguments Not Found.[Property Name = " + propertyName + "]");
                    continue;
                }

                // １レコードづつ変換しADD
                List<Object> objectList = new ArrayList<Object>();
                Object destSrc = null;
                for (Object src : sourceDescList) {
                    if (isCopyProperty(src.getClass())) {
                        destSrc = ConvertUtils.convert(src, targetClass);
                    } else {
                        destSrc = convert(src, targetClass);
                    }
                    objectList.add(destSrc);
                }

                // 変換したLISTを、Targetにセット
                try {
                    targetSetter.invoke(target, objectList);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    logger.warn("List targetSetter.invoke Error.[Property Name = " + propertyName + "]", e);
                    continue;
                }

            } else {
                // *******************************************************************
                // その他クラス処理
                // *******************************************************************
                // 変数宣言
                Method targetSetter = targetDesc.getWriteMethod();
                Class<?> targetDescClass = targetDesc.getPropertyType();

                try {
                    Object destSrc = convert(sourceDesc.getReadMethod().invoke(source, (Object[]) null),
                            targetDescClass);
                    targetSetter.invoke(target, destSrc);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    logger.warn("Other Class targetSetter.invoke Error.[Property Name = " + propertyName + "]", e);
                    continue;
                }
            }
        }

        return target;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     クラスの型を確認し、BeanUtilsで変換可能であればTrueを返却する。
     * @param     clazz          確認するクラス
     * @return    確認結果
     * @retval    true：変換可能
     * @retval    false：変換不可
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private boolean isCopyProperty(Class<?> clazz) {

        boolean retFlag = false;
        if (clazz.isPrimitive() || String.class.equals(clazz) || Boolean.class.equals(clazz) || Byte.class.equals(clazz)
                || Short.class.equals(clazz) || Integer.class.equals(clazz) || Long.class.equals(clazz)
                || Float.class.equals(clazz) || Double.class.equals(clazz) || Date.class.equals(clazz)
                || java.sql.Timestamp.class.equals(clazz) || clazz.isArray()) {
            retFlag = true;
        }
        return retFlag;
    }

}
