//@formatter:off
/**
 ******************************************************************************
 * @file        ComFunction.java
 * @brief       本システムで利用する共通関数群を登録
 * @par
 * @author      CSC
 * $Id:         $
 * @attention   インスタンス生成不要なstaticな関数を登録すること
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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.google.gson.Gson;

import net.muratec.mcs.annotation.FieldNameKey;
import net.muratec.mcs.entity.common.AjaxResultEntity;
import net.muratec.mcs.entity.common.AjaxResultEntity.ErrorInfo;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     共通関数群クラス
 * @par       機能:
 *              timestampToString（Timestampを文字列に変換）
 *              timestampToString（oracle.sql.TIMESTAMPを文字列に変換）
 *              timestampToString（java.sql.Timestampを文字列に変換）
 *              timestampToStringSmall（Timestampを日付文字列（yyyy/MM/dd HH:mm:ss）に変換）
 *              timestampToStringSmall（oracle.sql.TIMESTAMPを日付文字列（yyyy/MM/dd HH:mm:ss）に変換）
 *              timestampToStringSmall（java.sql.Timestampを日付文字列（yyyy/MM/dd HH:mm:ss）に変換）
 *              timestampToDate（TimestampをDateに変換）
 *              timestampToDate（oracle.sql.TIMESTAMPをDateに変換）
 *              timestampToDate（java.sql.TimestampをDateに変換）
 *              stringToTimestamp（文字列をTimestampに変換）
 *              stringToDate（文字列をDateに変換）
 *              stringToDateByMicro（文字列をDateに変換）
 *              dateToString（Dateを文字列に変換）
 *              dateToTimestamp（DateをTimestampに変換）
 *              ajaxAurgumentCheck（入力値エラーチェック（エラー情報を文字列で連結））
 *              ajaxAurgumentCheck（入力値エラーチェック（エラー情報をErrorInfoListで格納））
 *              getFromRecordNum（開始レコードNoを計算）
 *              getToRecordNum（終了レコードNoを計算）
 *              escapeForLike（LIKE演算子用のエスケープ）
 *              toStringMcs（付与されたオブジェクトの各変数を操作ログ形式で出力）
 *              toStringJavaObj（Javaのオブジェクトを文字列に変換）
 *              getStringByte（文字列のByte長を返す）
 *              makeLogStringList（操作ログ出力文字列の分割）
 *              createOpeLogInfo（操作ログ情報エンティティを構築）
 *              checkFromTo（日付の大小関係を比較(Timestamp)）
 *              checkFromTo（日付の大小関係を比較(Date)）
 *              checkFromTo（日付の大小関係を比較(String)）
 *              cutListForIn（与えられたListを1000個ずつで区切る）
 *              rgbToHex（RGB値を16進数表記に変換）
 *              checkNotBlank（アノテーションが使用できないときのNotBlankチェック）
 *              checkNumRange（アノテーションが使用できないときのNumRangeチェック）
 *              checkByteRange（アノテーションが使用できないときのByteRangeチェック）
 *              sortErrorInfoList（ErrorInfoリストをerrorValueでソート）
 *              sortIdErrorInfoList（ErrorInfoのリストをidでソート）
 *              splitErrorId（ErrorInfoのidを項目名とリストのIndexで分割し、String型配列で返す）
 *              formatSecond（秒数をHH:mm:ssに変換）
 *              createCsvItem(CSV項目オブジェクト生成)
 *              isMatch(文字列一致判定処理)
 *              setVersion（JS/CSSバージョン情報付与関数）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComFunction {

    static private ApplicationContext appContext = SpringContext.getApplicationContext();

    public static final Logger logger = LoggerFactory.getLogger(ComFunction.class);

    /**
     * GSONインスタンス
     */
    protected static final Gson gson = new Gson();

    //! 検索ワイルドカード
    private static final String SEARCH_WILDCARD = "*";

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Timestampを文字列に変換する。
     * @param     obj           変換元オブジェクト
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToString(Object obj) {

        if (obj == null) {
            return "";
        } else if (obj.getClass().equals(oracle.sql.TIMESTAMP.class)) {
            return timestampToString((oracle.sql.TIMESTAMP) obj);
        } else if (obj.getClass().equals(java.sql.Timestamp.class)) {
            return timestampToString((java.sql.Timestamp) obj);
        } else {
            return obj.toString();
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     oracle.sql.TIMESTAMPを文字列に変換する。
     * @param     time          変換元oracle.sql.TIMESTAMP
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToString(oracle.sql.TIMESTAMP time) {

        return (time == null) ? "" : time.toString().replaceAll("-", "/");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     java.sql.Timestampを文字列に変換する。
     * @param     time          変換元java.sql.Timestamp
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToString(java.sql.Timestamp time) {

        return (time == null) ? "" : time.toString().replaceAll("-", "/");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Timestampを日付文字列（yyyy/MM/dd HH:mm:ss）に変換する。
     * @param     obj           変換元オブジェクト
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToStringSmall(Object obj) {

        if (obj == null) {
            return "";
        } else if (obj.getClass().equals(oracle.sql.TIMESTAMP.class)) {
            return timestampToStringSmall((oracle.sql.TIMESTAMP) obj);
        } else if (obj.getClass().equals(java.sql.Timestamp.class)) {
            return timestampToStringSmall((java.sql.Timestamp) obj);
        } else {
            return obj.toString();
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     oracle.sql.TIMESTAMPを日付文字列（yyyy/MM/dd HH:mm:ss）に変換する。
     * @param     time          変換元oracle.sql.TIMESTAMP
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToStringSmall(oracle.sql.TIMESTAMP time) {

        return (time == null) ? "" : time.toString().replaceAll("-", "/").substring(0, 19);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     java.sql.Timestampを日付文字列（yyyy/MM/dd HH:mm:ss）に変換する。
     * @param     time          変換元java.sql.Timestamp
     * @return    変換結果
     * @retval    変換失敗時は空文字を返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String timestampToStringSmall(java.sql.Timestamp time) {

        return (time == null) ? "" : time.toString().replaceAll("-", "/").substring(0, 19);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     TimestampをDateに変換する。
     * @param     obj           変換元オブジェクト
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public Date timestampToDate(Object obj) {

        if (obj == null) {
            return null;
        } else if (obj.getClass().equals(oracle.sql.TIMESTAMP.class)) {
            return timestampToDate((oracle.sql.TIMESTAMP) obj);
        } else if (obj.getClass().equals(java.sql.Timestamp.class)) {
            return timestampToDate((java.sql.Timestamp) obj);
        } else {
            return null;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     oracle.sql.TIMESTAMPをDateに変換する。
     * @param     time          変換元oracle.sql.TIMESTAMP
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public Date timestampToDate(oracle.sql.TIMESTAMP time) {

        try {
            return time.dateValue();
        } catch (SQLException e) {
            return null;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     java.sql.TimestampをDateに変換する。
     * @param     time          変換元java.sql.Timestamp
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public Date timestampToDate(java.sql.Timestamp time) {

        return (time == null) ? null : new java.util.Date(time.getTime());
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付文字列（yyyy/MM/dd HH:mm:ss）をTimestampに変換する
     * @param     time          変換元日付文字列（yyyy/MM/dd HH:mm:ss）
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public java.sql.Timestamp stringToTimestamp(String time) {

        if (time == null || time.equals("")) {
            return null;
        } else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ComConst.TIMESTAMP_FORMAT);
            Date date;
            try {
                date = simpleDateFormat.parse(time);
                java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                return timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付文字列（yyyy/MM/dd HH:mm:ss）をdateに変換する
     * @param     dt            変換元日付文字列（yyyy/MM/dd HH:mm:ss）
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public Date stringToDate(String dt) {

        Date retDt = null;
        if (dt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_SEPARATOR);
            try {
                retDt = sdf.parse(dt);
            } catch (ParseException e) {
                retDt = null;
                e.printStackTrace();
            }
        }
        return retDt;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     dateを日付文字列（yyyy/MM/dd HH:mm:ss）に変換する
     * @param     dt            変換元Date
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String dateToString(Date dt) {

        String retDt = null;
        if (dt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_SEPARATOR);
            retDt = sdf.format(dt);
        }
        return retDt;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Date型をTimestamp型に変換する
     * @param     date          変換元Date
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public java.sql.Timestamp dateToTimestamp(Date date) {

        if (date == null) {
            return null;
        } else {
            java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
            return timestamp;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付文字列（yyyyMMddHHmmssFFFFFF）をDateに変換する
     * @param     date          変換元日付文字列（yyyyMMddHHmmssFFFFFF）
     * @return    変換結果
     * @retval    変換失敗時はnullを返す
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public Date stringToDateByMicro(String date) {

        Date retDt = null;
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_FF6);
            try {
                retDt = sdf.parse(date);
            } catch (ParseException e) {
                retDt = null;
                e.printStackTrace();
            }
        }
        return retDt;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力値エラーチェック（エラー情報を文字列で連結する）
     * @param     errors        エラー情報
     * @param     logger        ロガー（呼び出し元で生成したロガー）
     * @param     locale        ロケーション情報
     * @return
     * @retval
     * @attention
     * @note      errorsの中身をチェックする。
     *            エラーが存在すればエラー情報を文字列で連結し
     *            AjaxAurgumentExceptionをスロー
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public void ajaxAurgumentCheck(Errors errors, Logger logger, Locale locale) throws AjaxAurgumentException {

        if (errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                String tmp = err.toString();
                logger.error(tmp, locale);
            }

            List<AjaxResultEntity.ErrorInfo> errorInfoList = new ArrayList<AjaxResultEntity.ErrorInfo>();
            AjaxResultEntity ajaxResult = new AjaxResultEntity();

            // Step4P2 2017_11_14：入力チェックエラーの表示順を修正
            List<FieldError> fieldError = errors.getFieldErrors();

            for (FieldError err : fieldError) {
                AjaxResultEntity.ErrorInfo errorInfo = ajaxResult.new ErrorInfo();
                errorInfo.id = err.getField();
                errorInfo.errorMessage = err.getDefaultMessage();
                errorInfo.errorValue = "null";
                if (err.getRejectedValue() != null) {
                    errorInfo.errorValue = err.getRejectedValue().toString();
                }
                errorInfoList.add(errorInfo);
            }
            sortIdErrorInfoList(errorInfoList, true);

            String errInfo = "";
            for (ErrorInfo err : errorInfoList) {
                if (errInfo.isEmpty()) {
                    errInfo = "(";
                } else {
                    errInfo = errInfo + ", ";
                }
                errInfo = errInfo + err.id + ":" + err.errorMessage;
            }
            errInfo = errInfo + ")";
            throw new AjaxAurgumentException(errors.getErrorCount(), errInfo);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力値エラーチェック（エラー情報をErrorInfoListで格納する）＆変換
     * @param     errors        エラー情報
     * @param     logger        ロガー（呼び出し元で生成したロガー）
     * @param     locale        ロケーション情報
     * @param     source        変換元オブジェクト
     * @param     clazz         変換後のクラス
     * @return    変換後のオブジェクト
     * @retval
     * @attention
     * @note      errorsの中身をチェックする。
     *            エラーが存在すればErrorInfoListを生成し
     *            AjaxAurgumentExceptionをスロー。
     *            正常終了時には、変換後のクラスを生成し変換元オブジェクトを
     *            変換し戻り値に設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public <T> T ajaxAurgumentCheck(Errors errors, Logger logger, Locale locale, Object source, Class<T> clazz)
            throws AjaxAurgumentException {

        if (errors.hasErrors()) {
            for (ObjectError err : errors.getAllErrors()) {
                String tmp = err.toString();
                logger.error(tmp, locale);
            }

            List<AjaxResultEntity.ErrorInfo> errorInfoList = new ArrayList<AjaxResultEntity.ErrorInfo>();
            AjaxResultEntity ajaxResult = new AjaxResultEntity();

            // Step4P2 2017_11_14：入力チェックエラーの表示順を修正
            List<FieldError> fieldError = errors.getFieldErrors();

            for (FieldError err : fieldError) {
                AjaxResultEntity.ErrorInfo errorInfo = ajaxResult.new ErrorInfo();
                errorInfo.id = err.getField();
                errorInfo.errorMessage = err.getDefaultMessage();
                errorInfo.errorValue = "null";
                if (err.getRejectedValue() != null) {
                    errorInfo.errorValue = err.getRejectedValue().toString();
                }
                errorInfoList.add(errorInfo);
            }
            sortIdErrorInfoList(errorInfoList, true);
            throw new AjaxAurgumentException(errors.getErrorCount(), "", errorInfoList);
        }

        ComBeanConv bc = new ComBeanConv();
        T retTarget = bc.convert(source, clazz);

        return retTarget;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     開始レコードNoを計算
     * @param     pageRecords   １ページあたりの表示件数
     * @param     pageNum       表示したいページ
     * @return    開始レコードNo
     * @retval
     * @attention
     * @note      DataTablesのページング機能にて利用
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public int getFromRecordNum(int pageRecords, int pageNum) {

        int ret = 0;
        ret = pageRecords * (pageNum - 1) + 1;
        return ret;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     終了レコードNoを計算
     * @param     pageRecords   １ページあたりの表示件数
     * @param     pageNum       表示したいページ
     * @return    終了レコードNo
     * @retval
     * @attention
     * @note      DataTablesのページング機能にて利用
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public int getToRecordNum(int pageRecords, int pageNum) {

        int ret = 0;
        ret = getFromRecordNum(pageRecords, pageNum) + (pageRecords) - 1;
        return ret;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     LIKE演算子用にエスケープを行う
     * @param     input         変換元文字列
     * @return    変換後文字列
     * @retval
     * @attention
     * @note      引数の文字列をLike検索可能なように特定記号をエスケープする
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String escapeForLike(String input) {

        return input == null ? null
                : input.replace(ComConst.SQL_ESCAPE, ComConst.SQL_ESCAPE + ComConst.SQL_ESCAPE)
                        .replace("_", ComConst.SQL_ESCAPE + "_").replace("%", ComConst.SQL_ESCAPE + "%")
                        .replaceAll("[*]{1,}", "%");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     引数のオブジェクトにある各変数を操作ログ形式で出力する
     * @param     target        変換元文字列
     * @return    操作ログ形式の文字列
     * @retval    値が取得できない場合は「???」を出力
     * @attention
     * @note      FieldNameKeyアノテーションにより
     *              変数名をメッセージプロパティで変換
     *              パスワードなど秘匿項目はマスク
     *            を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String toStringMcs(Object target) {

        String retStr = "";
        Field[] fields = target.getClass().getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldStr = "";
            boolean mask = false;
            boolean hidden = false;

            // フィールド名指定のアノテーションがあれば、そちらを元にフィールド名を取得する。
            FieldNameKey fnKeyAnt = field.getAnnotation(FieldNameKey.class);
            if (fnKeyAnt != null) {
                String key = fnKeyAnt.key();
                mask = fnKeyAnt.mask();
                hidden = fnKeyAnt.hidden();
                // アノテーションのフィールドキーが設定されていれば、メッセージファイルより取得する。
                if (!ComConst.FIELD_NAME_KEY_NONE.equals(key)) {

                    String[] keys = key.split(",", 0);

                    if (keys.length == 1) {
                        // メッセージファイル（英）より取得。なければ変数名を設定
                        fieldName = appContext.getMessage(keys[0].trim(), null, fieldName, Locale.US);
                    } else {
                        fieldName = "";
                        for (String oneKey : keys) {
                            // メッセージファイル（英）より取得。なければ変数名を設定
                            if (!"".equals(fieldName)) {
                                fieldName = fieldName + " ";
                            }
                            fieldName = fieldName
                                    + appContext.getMessage(oneKey.trim(), null, oneKey.trim(), Locale.US);
                        }
                    }

                }
            }

            // 値の取得
            if (mask) {
                // maskが有効な場合はマスク文字列をいれる
                try {
                    if (field.get(target) != null && field.get(target).toString().length() != 0) {
                        fieldStr = ComConst.MASK_STRING;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    fieldStr = "???";
                }
            } else {
                // maskが無効な場合は、値を取得しセットする
                if (field.getType().isArray()) {
                    // 配列の場合
                    try {
                        for (Object obj : (Object[]) field.get(target)) {
                            // 配列の２つ目以降の場合、カンマを付ける
                            if (fieldStr.length() != 0) {
                                fieldStr = fieldStr + ",";
                            }
                            // オブジェクトがEntityパッケージなら本メソッドを再帰呼び出し。
                            // それ以外は、toStringJavaObjにて文字列変換。
                            String pkg = obj.getClass().getPackage().getName();
                            if (pkg.startsWith(ComConst.ENTITY_PACKAGE)) {
                                fieldStr = fieldStr + toStringMcs(obj);
                            } else {
                                fieldStr = fieldStr + toStringJavaObj(obj);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        fieldStr = "???";
                    }
                } else if (List.class.equals(field.getType())) {
                    // Listの場合
                    try {
                        for (Object obj : (List<?>) field.get(target)) {
                            // 配列の２つ目以降の場合、カンマを付ける
                            if (fieldStr.length() != 0) {
                                fieldStr = fieldStr + ",";
                            }
                            // オブジェクトがEntityパッケージなら本メソッドを再帰呼び出し。
                            // それ以外は、toStringJavaObjにて文字列変換。
                            String pkg = obj.getClass().getPackage().getName();
                            if (pkg.startsWith(ComConst.ENTITY_PACKAGE)) {
                                fieldStr = fieldStr + toStringMcs(obj);
                            } else {
                                fieldStr = fieldStr + toStringJavaObj(obj);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        fieldStr = "???";
                    }
                } else {
                    // 単一項目（配列、List以外）の場合
                    try {
                        if (field.get(target) != null) {
                            // オブジェクトがEntityパッケージなら本メソッドを再帰呼び出し。
                            // それ以外は、toStringJavaObjにて文字列変換。
                            String pkg = field.get(target).getClass().getPackage().getName();
                            if (pkg.startsWith(ComConst.ENTITY_PACKAGE)) {
                                fieldStr = toStringMcs(field.get(target));
                            } else {
                                fieldStr = toStringJavaObj(field.get(target));
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        fieldStr = "???";
                    }
                }
            }
            // 非表示フラグがOFFの場合、出力文字列に追加
            if (!hidden) {
                retStr = retStr + "[" + fieldName + "=" + fieldStr + "]";
            }
        }
        return retStr;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Javaのオブジェクトを文字列に変換
     * @param     obj           変換元オブジェクト
     * @return    変換後の文字列
     * @retval
     * @attention
     * @note      Date、Timestamp型は所定の形式に変換する。
     *             それ以外の型は、そのオブジェクトが持っているtoString関数を利用
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static private String toStringJavaObj(Object obj) {

        String retStr = "";

        if (Date.class.equals(obj.getClass())) {
            // Date型の変換
            retStr = dateToString((Date) obj);
        } else if (java.sql.Timestamp.class.equals(obj.getClass())) {
            // Timestamp型の変換
            retStr = obj.toString().replaceAll("-", "/");
        } else {
            retStr = obj.toString();
        }

        return retStr;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列のバイト数を返す
     * @param     text          文字列
     * @return    引数の文字列のバイト数
     * @retval
     * @attention バイトはUTF-8で換算
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public int getStringByte(String text) {

        int len = 0;
        // 出力対象がnullなら空Listをそのまま返す
        if (text == null) {
            return len;
        }
        try {
            len = text.getBytes(ComConst.CHAR_SET).length;
        } catch (UnsupportedEncodingException e) {
            // サポート外のエンコードの場合は文字長をそのまま返す。
            logger.warn("Unsupported Encoding [" + ComConst.CHAR_SET + "].", e);
            len = text.length();
        }

        return len;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     操作ログ出力文字列の分割を行う
     * @param     logText       操作ログ出力文字列
     * @return    分割時の操作ログ出力フォーマットに分割された文字列リスト
     * @retval    分割不要な場合は入力値がそのまま設定される
     * @attention 分割はバイト(UTF-8)で実施。
     * @attention 引数は"操作内容部. [パラメータ部]"であること。
     * @note      操作ログが上限サイズを超過する場合、分割して操作ログリストを作成。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public List<String> makeLogStringList(String logText) {

        // -----------------------------------------------------------
        // 戻り値
        // -----------------------------------------------------------
        List<String> logTextList = new ArrayList<String>();

        // -----------------------------------------------------------
        // 出力対象nullチェック
        // -----------------------------------------------------------
        if (logText == null) {
            // nullなら空Listをそのまま返す
            return logTextList;
        }

        // -----------------------------------------------------------
        // 出力対象の上限サイズチェック
        // -----------------------------------------------------------
        int logByte = getStringByte(logText);
        if (logByte <= ComConst.OPE_LOG_MAX_LEN) {
            // 出力対象が上限サイズ以内なら、そのままListにつめて返却
            logTextList.add(logText);
            return logTextList;
        }

        // -----------------------------------------------------------
        // 操作部判定（パラメータのセパレーターがあるかどうか）
        // -----------------------------------------------------------
        int sepalatePosition = logText.indexOf("[");
        if (sepalatePosition <= -1) {
            // セパレータが無い場合は、エラーメッセージをADDして返却
            logger.error(ComConst.OPE_LOG_ERROR + "[" + logText + "]");
            logTextList.add(ComConst.OPE_LOG_ERROR);
            return logTextList;
        }

        // -----------------------------------------------------------
        // 操作内容部/パラメータ部を分割
        // -----------------------------------------------------------
        String opeLog = logText.substring(0, sepalatePosition); // 操作内容部
        String paramLog = logText.substring(sepalatePosition); // パラメータ部

        // -----------------------------------------------------------
        // パラメータ部上限Byte取得
        // -----------------------------------------------------------
        int paramMaxByte = ComConst.OPE_LOG_MAX_LEN - getStringByte(opeLog)
                - (ComConst.RECORD_INFO_LEN + ComConst.PARAM_PAREN_LEN);

        // -----------------------------------------------------------
        // パラメータ部の領域チェック
        // -----------------------------------------------------------
        if (paramMaxByte <= 0) {
            // ゼロ以下（操作内容部のみで領域オーバー時）の場合
            // エラーログを出力し操作内容部のみ返却
            logger.error("OPERATION_LOG MAX SIZE OVER.[" + logText + "]");
            logTextList.add(opeLog);
            return logTextList;
        }

        // -----------------------------------------------------------
        // パラメータ部分割
        // -----------------------------------------------------------
        List<String> paramLogList = new ArrayList<String>();

        // -----------------------------------------------------------
        // 分割処理
        // -----------------------------------------------------------
        // 1文字づつ抜出ながらparamMaxByte越えるかチェック
        // それまでに分割文字（ "[" か "," ）があればその場所を覚えておく
        // 分割文字がある場合、そこまでの文字を抜出ADD
        // 分割文字がない場合、paramMaxByte越えたとこまで抜出ADD
        int paramByteSum = 0; // パラメータ部のバイト数合計
        int startPosition = 0; // パラメータ部開始位置
        int splitPosition = 0; // パラメータ部分割位置
        int index = 0; // ループindex
        String splitString = ""; // 分割文字
        while (index < paramLog.length()) {

            // **************************************************
            // パラメータ部を１文字抜粋
            // **************************************************
            String singleChar = paramLog.substring(index, index + 1);

            // **************************************************
            // 文字が分割対象文字チェック（最初の１文字目以降）
            // **************************************************
            if (paramByteSum != 0 && ("[".equals(singleChar) || ",".equals(singleChar))) {
                splitPosition = index;
                splitString = singleChar;
            }

            // **************************************************
            // 文字のバイト数取得し加算
            // **************************************************
            paramByteSum = paramByteSum + getStringByte(singleChar);

            // **************************************************
            // パラメータ部上限Byteを超えているかチェック
            // **************************************************
            if (paramByteSum <= paramMaxByte) {
                // 上限以内であれば、index+1 を行い次文字処理する
                index = index + 1;
            } else {
                // 上限を超えている場合、それまでの文字を取得し次文字を処理する。
                // バイト数合計をゼロクリア
                paramByteSum = 0;

                // 分割～格納
                if (splitPosition == 0) {
                    // 分割対象が無かった場合、スタート～Index前まで抜出す。
                    paramLogList.add(paramLog.substring(startPosition, index));

                    // 開始位置をindexにする
                    startPosition = index;
                } else {
                    // 分割対象が有った場合、スタート～splitPosition前まで抜出す。
                    paramLogList.add(paramLog.substring(startPosition, splitPosition));

                    // 開始位置、indexをsplitPositionに移動
                    startPosition = splitPosition;
                    index = splitPosition;

                    // 分割文字がカンマの場合はそのカンマは出力させない
                    if (",".equals(splitString)) {
                        startPosition = startPosition + 1;
                        index = index + 1;
                    }

                    // splitPositionをゼロクリア
                    splitPosition = 0;
                }
            }

        }

        // -----------------------------------------------------------
        // 残りのParam分をADD("]"のみの場合は、以降の処理で追加されるためSKIP)
        // -----------------------------------------------------------
        if (!"]".equals(paramLog.substring(startPosition))) {
            paramLogList.add(paramLog.substring(startPosition));
        }

        // -----------------------------------------------------------
        // 分割されたパラメータ部を元に操作ログフォーマットに生成しListへADD
        // -----------------------------------------------------------
        int count = 0;
        int totalNum = paramLogList.size();

        for (String param : paramLogList) {
            count = count + 1;
            StringBuffer sbParamLog = new StringBuffer(param);

            // 先頭が"["でない場合、先頭に"["を追加
            if (sbParamLog.indexOf("[") != 0) {
                sbParamLog.insert(0, "[");
            }

            // 末尾が"]"でない場合、末尾に"]"を追加
            if (sbParamLog.lastIndexOf("]") + 1 != sbParamLog.length()) {
                sbParamLog.append("]");
            }
            // ログの結合を行う(操作内容部 + レコード情報 + 分割後パラメータ部)
            String log = String.format("%1$s[%2$02d/%3$02d] %4$s", opeLog, count, totalNum, sbParamLog.toString());
            logTextList.add(log);
        }

        return logTextList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     操作ログ情報エンティティを構築
     * @param     logText       操作ログ出力文字列
     * @param     session       セッション情報（Frameworkより付加）
     * @param     screenInfo    画面情報
     * @param     logOperationType 操作タイプ
     * @param     number        連番
     * @return    操作ログ情報エンティティ
     * @retval
     * @attention セッションへのアクセスあり
     * @note      ログコードを生成しIPアドレスやユーザ情報はセッションより取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public OpeLogInfoEntity createOpeLogInfo(HttpSession session, ComConst.ScreenInfo screenInfo,
            long logOperationType, long number) {

        OpeLogInfoEntity opeLogInfo = new OpeLogInfoEntity();

        // ログコードを取得
        long logType = ComConst.LogType.OPERATION_LOG;
        long logCode = screenInfo.getLogCode();
        long logOpType = logOperationType;
        long logNumber = number * 10L;
        long logReserv = ComConst.OP_LOG_RESERV;
        opeLogInfo.logCode = logType + logCode + logOpType + logNumber + logReserv;

        // セッションよりIPアドレス取得
        opeLogInfo.ipAddress = (String) session.getAttribute(ComConst.SessionKey.REMOTE_ADDRESS);

        // セッションよりユーザ名取得
        String userName = ComConst.ConstUserId.NOLOGIN;
        String sessionAttribute = (String) session.getAttribute(ComConst.SessionKey.LOGIN_USER_INFO);
        AuthenticationEntity authEntity = (AuthenticationEntity) gson.fromJson(sessionAttribute,
                AuthenticationEntity.class);
        // AuthenticationEntity authEntity = (AuthenticationEntity) session
        // .getAttribute(ComConst.SessionKey.LOGIN_USER_INFO);
        if (authEntity != null) {
            userName = authEntity.userName;
        }
        opeLogInfo.userName = userName;

        return opeLogInfo;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付の大小関係を比較(Timestamp)
     * @param     from          開始日付
     * @param     to            終了日付
     * @return    判定結果（True/False）
     * @retval
     * @attention 「開始日付 < 終了日付」でない場合、False
     * @note      引数の日付の大小関係が正しいか比較します
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public boolean checkFromTo(Timestamp from, Timestamp to) {

        if (from == null || to == null) {
            // nullの場合は何もしない
            return true;
        }

        // 日付を比較
        int checkResult = from.compareTo(to);

        // 結果を返却
        if (checkResult > 0) {
            // 開始日付 > 終了日付
            return false;
        } else {
            // 開始日付 <= 終了日付
            return true;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付の大小関係を比較(Date)
     * @param     from          開始日付
     * @param     to            終了日付
     * @return    判定結果（True/False）
     * @retval
     * @attention 「開始日付 < 終了日付」でない場合、False
     * @note      引数の日付の大小関係が正しいか比較します
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public boolean checkFromTo(Date from, Date to) {

        if (from == null || to == null) {
            // nullの場合は何もしない
            return true;
        }

        // 日付を比較
        int checkResult = from.compareTo(to);

        // 結果を返却
        if (checkResult > 0) {
            // 開始日付 > 終了日付
            return false;
        } else {
            // 開始日付 <= 終了日付
            return true;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日付の大小関係を比較(String)
     * @param     from          開始日付
     * @param     to            終了日付
     * @return    判定結果（True/False）
     * @retval
     * @attention 「開始日付 < 終了日付」でない場合、False
     * @note      引数の日付の大小関係が正しいか比較します
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public boolean checkFromTo(String from, String to) {

        if (from == null || to == null) {
            // nullの場合は何もしない
            return true;
        }

        int checkResult = 0;

        // 日付を変換
        try {
            Timestamp fromTimestamp = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(from).getTime());
            Timestamp toTimestamp = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(to).getTime());

            // 日付を比較
            checkResult = fromTimestamp.compareTo(toTimestamp);

        } catch (ParseException e) {
            // 変換できない形式の場合は何もしない
            return true;
        }

        // 結果を返却
        if (checkResult > 0) {
            // 開始日付 > 終了日付
            return false;
        } else {
            // 開始日付 <= 終了日付
            return true;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     与えられたListを1000個ずつで区切る
     * @param     targetList    対象リスト
     * @return    与えられたリストを1000個ずつで区切って格納したリスト
     * @retval
     * @attention 引数がnullの場合はnullを返す
     * @note      SQLでIN句の要素が1000個を超える際に使用
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public <T> List<List<T>> cutListForIn(List<T> targetList) {

        final int SQL_IN_MAX = 1000;

        // 引数がnullの場合、nullを返す。
        if (targetList == null) {
            return null;
        }
        List<List<T>> retList = new ArrayList<List<T>>();

        // 1000個毎に要素を区切る。
        for (int i = 0;; i += SQL_IN_MAX) {

            // 残要素が1000個より多い場合、1000個をリストに加える。
            if (i + SQL_IN_MAX < targetList.size()) {
                retList.add(targetList.subList(i, i + SQL_IN_MAX));

                // 残要素が1000個以下の多い場合、全てリストに加えてリストを返す。
            } else {
                retList.add(targetList.subList(i, targetList.size()));
                return retList;
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     RGB値を16進数表記に変換
     * @param     r    Red
     * @param     g    Green
     * @param     b    Blue
     * @return    変換結果
     * @retval
     * @attention RGB値が不正の場合はnullを返す
     * @note      #000000の形式を返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String rgbToHex(Short r, Short g, Short b) {

        return (r == null || r < 0 || 255 < r || g == null || g < 0 || 255 < g || b == null || b < 0 || 255 < b) ? null
                : "#" + String.format("%02x", r) + String.format("%02x", g) + String.format("%02x", b);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     NotBlankチェック
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     input         画面より入力された文字列
     * @param     errId         ErrorInfoのidに格納する値
     * @param     errValue      ErrorInfoのerrorValueに格納する値
     * @return    エラー情報
     * @retval    ErrInfo形式で返却(NotBlankであればnull)
     * @attention
     * @note      入力された文字列の文字数が最小値～最大値の範囲内であるかをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public ErrorInfo checkNotBlank(MessageSource messageSource, Locale locale, String input, String errId,
            String errValue) {

        if (input != null && input.length() > 0) {
            String replace = input.replaceAll(" ", "");

            if (replace.length() > 0) {
                return null;
            }
        }

        // エラー情報作成
        ErrorInfo errorInfo = (new AjaxResultEntity()).new ErrorInfo();
        errorInfo.id = errId;
        errorInfo.errorMessage = messageSource.getMessage("org.hibernate.validator.constraints.NotBlank.message", null,
                "org.hibernate.validator.constraints.NotBlank.message", locale);
        errorInfo.errorValue = errValue;

        return errorInfo;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字数チェック
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     input         画面より入力された文字列
     * @param     minValue      最小値
     * @param     maxValue      最大値
     * @param     errId         ErrorInfoのidに格納する値
     * @param     errValue      ErrorInfoのerrorValueに格納する値
     * @return    エラー情報
     * @retval    ErrInfo形式で返却(範囲内であればnull)
     * @attention
     * @note      入力された文字列の数値チェック＆数値が最小値～最大値の範囲内であるかをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public ErrorInfo checkNumRange(MessageSource messageSource, Locale locale, String input, long minValue,
            long maxValue, String errId, String errValue) {

        boolean isValid = true;

        if (input != null && input.length() > 0) {
            // 半角整数チェック
            Pattern p = Pattern.compile("^[+-]?[0-9]+$|^[0-9]*$");
            Matcher m = p.matcher(input);
            if (!m.find()) {
                isValid = false;
            } else {
                // 数値範囲チェック
                try {
                    long value = Long.parseLong(input);
                    if (value < minValue) {
                        isValid = false;
                    }
                    if (value > maxValue) {
                        isValid = false;
                    }
                } catch (NumberFormatException nfex) {
                    isValid = false;
                }
            }

        }

        if (isValid) {
            return null;
        } else {
            // エラー情報作成
            ErrorInfo errorInfo = (new AjaxResultEntity()).new ErrorInfo();
            errorInfo.id = errId;
            errorInfo.errorMessage = messageSource.getMessage("ERR0047",
                    new String[] { String.valueOf(minValue), String.valueOf(maxValue) }, "ERR0047", locale);
            errorInfo.errorValue = errValue;

            return errorInfo;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力文字Byte数バリデーションチェック
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     input         画面より入力された文字列
     * @param     minValue      最小値
     * @param     maxValue      最大値
     * @param     errId         ErrorInfoのidに格納する値
     * @param     errValue      ErrorInfoのerrorValueに格納する値
     * @return    エラー情報
     * @retval    ErrInfo形式で返却(範囲内であればnull)
     * @attention
     * @note      入力文字のByte数について最小値～最大値の範囲内かをチェック
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public ErrorInfo checkByteRange(MessageSource messageSource, Locale locale, String input, long minValue,
            long maxValue, String errId, String errValue) {

        boolean isValid = true;

        // 入力がnullの場合はnullを返却する（必須チェックは別途実施しているため）
        if (input != null) {
            // Byte数の取得
            long byteSize = ComFunction.getStringByte(input);

            // 最少より小さいor最大より大きい場合エラー
            if ((byteSize < minValue) || (byteSize > maxValue)) {
                isValid = false;
            }
        }

        if (isValid) {
            return null;
        } else {
            // エラー情報作成
            ErrorInfo errorInfo = (new AjaxResultEntity()).new ErrorInfo();
            errorInfo.id = errId;
            errorInfo.errorMessage = messageSource.getMessage("ERR0049",
                    new String[] { String.valueOf(minValue), String.valueOf(maxValue) }, "ERR0049", locale);
            errorInfo.errorValue = errValue;

            return errorInfo;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ErrorInfoのリストをerrorValueでソート
     * @param     errorInfoList     ErrorInfoのリスト
     * @param     sortOrder         errorValueのソート順を指定(true：昇順、false：降順)
     * @param     sortColumnType    errorValueを文字列 or 数値で比較するかを指定(true：数値、false：文字列)
     * @return
     * @retval
     * @attention
     * @note      ErrorInfoのリストを指定した要素でソートする
     *             List#sort, Collection#sortはException発生で使用できないため独自実装
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public void sortErrorInfoList(List<ErrorInfo> errorInfoList, boolean sortOrder, boolean sortColumnType) {

        // リストのソート条件(エラーリスト)
        Collections.sort(errorInfoList, new Comparator<ErrorInfo>() {

            public static final int ASC = 1; // 昇順 (1.2.3....)
            public static final int DESC = -1; // 降順 (3.2.1....)

            @Override
            public int compare(ErrorInfo errInfo1, ErrorInfo errInfo2) {

                int sortType;

                if (sortOrder) {
                    sortType = ASC;
                } else {
                    sortType = DESC;
                }

                String errValue1 = (errInfo1 == null || errInfo1.errorValue == null ? null : errInfo1.errorValue);
                String errValue2 = (errInfo2 == null || errInfo2.errorValue == null ? null : errInfo2.errorValue);
                if (errValue1 == null && errValue2 == null) {
                    return 0;
                } else if (errValue1 == null) {
                    return 1 * sortType;
                } else if (errValue2 == null) {
                    return -1 * sortType;
                }

                if (sortColumnType) {
                    int nErrValue1 = Integer.valueOf(errValue1);
                    int nErrValue2 = Integer.valueOf(errValue2);
                    return (nErrValue1 - nErrValue2) * sortType;
                } else {
                    return errValue1.compareTo(errValue2) * sortType;
                }
            }
        });
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ErrorInfoのリストをidでソート
     * @param     errorInfoList     ErrorInfoのリスト
     * @param     sortOrder         errorIdのソート順を指定(true：昇順、false：降順)
     * @return
     * @retval
     * @attention
     * @note      アノテーションで検出したエラーを項目の変数名、
     *             アノテーションのアルファベット順でソートする。
     *             テーブル等のリスト内のエラーは、リストのindex順にソートされる。
     *             FieldError、ErrorではCollections.sortでException or
     *             ビルドエラーが起きるのでErrorInfoでソート
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public void sortIdErrorInfoList(List<ErrorInfo> errorInfoList, boolean sortOrder) {

        // リストのソート条件(エラーリスト)
        Collections.sort(errorInfoList, new Comparator<ErrorInfo>() {

            public static final int ASC = 1; // 昇順 (1.2.3....)
            public static final int DESC = -1; // 降順 (3.2.1....)

            @Override
            public int compare(ErrorInfo errInfo1, ErrorInfo errInfo2) {

                int sortType;

                // ソート順の設定
                if (sortOrder) {
                    sortType = ASC;
                } else {
                    sortType = DESC;
                }

                // 項目名とリストのIndexを分割
                String errStr1[] = splitErrorId(errInfo1);
                String errStr2[] = splitErrorId(errInfo2);

                if (errStr1[0] == null && errStr2[0] == null) {
                    return 0;
                } else if (errStr1[0] == null) {
                    return 1 * sortType;
                } else if (errStr2[0] == null) {
                    return -1 * sortType;
                }

                // 項目名⇒リストのIndexの順で比較
                if (errStr1[0].equals(errStr2[0])) {
                    if (errStr1[1] == null && errStr2[1] == null) {
                        return 0;
                    } else if (errStr1[1] == null) {
                        return 1 * sortType;
                    } else if (errStr2[1] == null) {
                        return -1 * sortType;
                    }
                    return (Integer.valueOf(errStr1[1]) - Integer.valueOf(errStr2[1])) * sortType;
                } else {
                    return errStr1[0].compareTo(errStr2[0]) * sortType;
                }
            }
        });
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ErrorInfoのidを項目名とリストのIndexで分割し、String型配列で返す
     * @param     errInfo           分割するidのErrorInfo情報
     * @return    [[項目名][リストのIndex]]
     * @retval
     * @attention
     * @note      ErrorInfoの情報がなければ、全てnullで返す
     *             項目がリストでなければ要素1の箇所をnullで返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    // Step4P2 2017_11_14：入力チェックエラーの表示順を修正
    private static String[] splitErrorId(ErrorInfo errInfo) {

        String[] res = new String[2];

        Pattern p = Pattern.compile("(.+)\\[([0-9]+)\\]");

        res[0] = (errInfo == null ? null : errInfo.id);
        res[1] = null;
        Matcher m = p.matcher(res[0]);
        if (m.find()) {
            res[0] = m.group(1);
            res[1] = m.group(2);
        }

        return res;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     秒数をHH:mm:ssに変換
     * @param     sumSecond    秒数
     * @return
     * @retval
     * @attention
     * @note      秒数が負ならば-HH:mm:ss
     *             時間(H)が3桁以上ならばHHH(桁数分):mm:ss
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public String formatSecond(Long sumSecond) {

        Long h = sumSecond / 3600;
        Long m = (sumSecond % 3600) / 60;
        Long s = sumSecond % 60;

        DecimalFormat df = new DecimalFormat("00");

        return df.format(h) + ":" + df.format(Math.abs(m)) + ":" + df.format(Math.abs(s));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CSV項目オブジェクト生成機能
     * @param     csvOutName       CSV出力される項目名
     * @param     entityName       DBより返されるEntityの変数名
     * @param     addSingleQuotes  項目データの先頭にシングルクォーテーションを付与
     * @return    CSV項目オブジェクト
     * @retval
     * @attention
     * @note      CSV項目オブジェクトを生成し、返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public ComCsvItem createCsvItem(String csvOutName, String entityName, boolean addSingleQuotes) {

        ComCsvItem csvItem = new ComCsvItem();
        csvItem.csvOutName = csvOutName;
        csvItem.entityName = entityName;
        csvItem.addSingleQuotes = addSingleQuotes;
        return csvItem;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列一致判定処理
     * @param     strText        対象文字列
     * @param     strCondition   検索文字列
     * @return
     * @retval
     * @attention
     * @note      ワイルドカード指定(*)の文字列一致判定処理
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public boolean isMatch(String strText, String strCondition) {
        
        boolean bRet = true;
        boolean bPrefix = false;
        boolean bSuffix = false;
        
        // 検索方法を判断
        if (strCondition.endsWith(SEARCH_WILDCARD)) {
            bPrefix = true;
            strCondition = strCondition.substring(0, strCondition.length() - SEARCH_WILDCARD.length());
        }
        if (strCondition.startsWith(SEARCH_WILDCARD)) {
            bSuffix = true;
            strCondition = strCondition.substring(SEARCH_WILDCARD.length());
        }
        
        // 部分一致
        if (bPrefix && bSuffix) {
            if (strText.indexOf(strCondition) < 0) {
                bRet = false;
            }
        // 前方一致
        } else if (bPrefix) {
            if (!strText.startsWith(strCondition)) {
                bRet = false;
            }
        // 後方一致
        } else if (bSuffix) {
            if (!strText.endsWith(strCondition)) {
                bRet = false;
            }
        // 完全一致
        } else {
            if (!strText.equals(strCondition)) {
                bRet = false;
            }
        }
        return bRet;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     バージョン情報設定関数
     * @param     model       対象モデル
     * @return
     * @retval
     * @attention
     * @note      Javascriptファイルなどのキャッシュ更新のために使用する
     *             バージョン情報を設定を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    static public void setVersion(Model model) {

        model.addAttribute(ComConst.VAR_NAME, ComConst.VARSION);
    }
}
