//@formatter:off
/**
 ******************************************************************************
 * @file        ComCsvOut.java
 * @brief       CSV出力
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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;

import net.muratec.mcs.exception.McsException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     CSV出力クラス
 * @par       機能:
 *              writeCsv（CSVファイル書込）
 *              createCsvData（CSVファイルデータ生成）
 *              zipOut（zipファイル出力）
 *              csvOut（CSVファイル出力）
 *              csvOut（CSVファイル出力、ヘッダ生成版）
 *              addDatetime（ファイル名に日時を追加）
 * @attention 文字コード[UTF-8 BOM付き] 改行コード[LF]で出力する
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComCsvOut<T> {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定のOutputStream、PrintWriterにCSVファイルを出力する。
     * @param     out           出力先のストリーム
     * @param     writer        出力先のライター（outから生成したPrintWriter）
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     headerStr     CSVヘッダー部の出力情報
     * @param     listCsvItem   CSV出力用項目情報
     * @param     listData      CSV出力データ
     * @return    出力結果
     * @retval    true:出力成功時（出力失敗時はMcsExceptionを発生させる）
     * @attention Controllerより呼び出されることを想定。
     * @note      指定のストリームへ、CSVファイルのヘッダー、データを出力する。
     *            このメソッドでは、ストリームは閉じられない。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean writeCsv(OutputStream out, PrintWriter writer, MessageSource messageSource, Locale locale,
            String headerStr, List<ComCsvItem> listCsvItem, List<T> listData) throws McsException {

        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();

        try {
            // BOMを付加します。
            byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
            out.write(bom);

            // ヘッダーを出力
            writer.write(headerStr + ComConst.BR);

            // 項目名を出力
            for (ComCsvItem item : listCsvItem) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(ComConst.CSV_SEPARATOR);
                }
                sb.append(messageSource.getMessage(item.csvOutName, null, item.csvOutName, locale));
            }
            sb.append(ComConst.BR);
            writer.write(sb.toString());

            // データを出力
            Field field;
            for (Object obj : listData) {
                sb = new StringBuilder();
                isFirst = true;
                for (ComCsvItem item : listCsvItem) {

                    // データ出力
                    field = obj.getClass().getField(item.entityName);
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append(ComConst.CSV_SEPARATOR);
                    }

                    Object objField = field.get(obj);
                    String fieldString = "";
                    // objFieldがnull以外の時に所定の変換実施
                    if (objField != null) {
                        // 日付型は変換、それ以外はtoString。
                        if (Date.class.equals(objField.getClass())) {
                            fieldString = ComFunction.dateToString((Date) objField);
                        } else {
                            fieldString = objField.toString();
                        }
                    }

                    // シングルクォーテーション/ダブルクォーテーション処理
                    if (item.addSingleQuotes) {
                        // シングルクォーテーション付与
                        sb.append("'");
                        sb.append(fieldString);
                    } else {
                        sb.append("\"");
                        fieldString = fieldString.replaceAll("\"", "\"\"");
                        sb.append(fieldString);
                        sb.append("\"");
                    }

                }
                sb.append(ComConst.BR);
                writer.write(sb.toString());
            }

            return true;
        } catch (Exception e) {
            throw new McsException(e);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CSVファイルのデータ（バイト配列）を生成する。
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     headerStr     CSVヘッダー部の出力情報
     * @param     listCsvItem   CSV出力用項目情報
     * @param     listData      CSV出力データ
     * @return    CSVファイル
     * @retval    CSVファイルの文字列をバイト配列化したもの
     * @attention Controllerより呼び出されることを想定。
     * @note      本メソッドでCSVデータを生成し、zipOutでzipファイルとして出力する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public byte[] createCsvData(MessageSource messageSource, Locale locale, String headerStr,
            List<ComCsvItem> listCsvItem, List<T> listData) throws McsException {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); PrintWriter writer = new PrintWriter(out)) {

            // CSVファイルをメモリ上のバッファ（ByteArrayOutputStream）に書きこみ
            writeCsv(out, writer, messageSource, locale, headerStr, listCsvItem, listData);
            writer.close();
            out.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new McsException(e);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     複数のファイルをzipファイルにしてブラウザへ返す
     * @param     res           HttpServletのレスポンス（SpringFrameworkより付加）
     * @param     fileName      zipファイル名
     * @param     dataList      ファイルデータのリスト
     * @param     fileNameList  各ファイルの名前のリスト
     * @return    出力結果
     * @retval    true:出力成功時（出力失敗時はMcsExceptionを発生させる）
     * @attention Controllerより呼び出されることを想定。
     * @note      HttpServletのレスポンスへzipファイルを出力し、クライアントにて
     *            ダウンロードを実施させる。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static boolean zipOut(HttpServletResponse res, String fileName, List<byte[]> dataList,
            List<String> fileNameList) throws McsException {

        Date sysDate = new Date();

        try (ServletOutputStream sos = res.getOutputStream()) {
            // zipファイルに圧縮
            List<String> csvFileNameList = new ArrayList<String>();
            for (String csvFileName : fileNameList) {
                String csvFileNameDatetime = addDatetime(csvFileName, sysDate);
                csvFileNameList.add(csvFileNameDatetime);
            }
            byte[] zipData = ComArchiveUtil.byteToZipArchive(dataList, csvFileNameList);

            // ブラウザへ返す
            // ContentTypeを指定
            res.setContentType("application/zip");

            // ファイル名を設定
            String zipFileName = addDatetime(fileName, sysDate);
            String outFileName = new String(zipFileName.getBytes("MS932"), "ISO-8859-1");

            // ResponseHeaderを設定
            res.setHeader("Content-Disposition", "attachment; filename=" + outFileName);
            // res.setCharacterEncoding(ComConst.CHAR_SET);

            // zipファイル書き込み
            sos.write(zipData);
            sos.close();

            return true;
        } catch (Exception e) {
            throw new McsException(e);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CSVファイルを出力する。
     * @param     res           HttpServletのレスポンス（SpringFrameworkより付加）
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     fileName      ファイル名
     * @param     headerStr     CSVヘッダー部の出力情報
     * @param     listCsvItem   CSV出力用項目情報
     * @param     listData      CSV出力データ
     * @return    出力結果
     * @retval    true:出力成功時（出力失敗時はMcsExceptionを発生させる）
     * @attention Controllerより呼び出されることを想定。
     * @note      HttpServletのレスポンスへCSVデータを出力し、クライアントにて
     *            ダウンロードを実施させる。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean csvOut(HttpServletResponse res, MessageSource messageSource, Locale locale, String fileName,
            String headerStr, List<ComCsvItem> listCsvItem, List<T> listData) throws McsException {

        try (ServletOutputStream sos = res.getOutputStream(); PrintWriter writer = new PrintWriter(sos)) {

            // ContentTypeを指定
            res.setContentType("text/csv;charset=" + ComConst.CHAR_SET);

            // ファイル名を設定
            String fileNameDatetime = addDatetime(fileName, new Date());
            String outFileName = new String(fileNameDatetime.getBytes("MS932"), "ISO-8859-1");

            // ResponseHeaderを設定
            res.setHeader("Content-Disposition", "attachment; filename=" + outFileName);
            res.setCharacterEncoding(ComConst.CHAR_SET);

            // CSVファイルを書きこみ
            writeCsv(sos, writer, messageSource, locale, headerStr, listCsvItem, listData);
            writer.close();
            sos.close();

            return true;
        } catch (Exception e) {
            throw new McsException(e);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CSVファイルを出力する（ヘッダ生成版）。
     * @param     res           HttpServletのレスポンス（SpringFrameworkより付加）
     * @param     messageSource メッセージリソース（SpringFrameworkより付加）
     * @param     locale        ロケーション情報（Frameworkより付加）
     * @param     fileName      ファイル名
     * @param     titleKey      CSVヘッダー部の出力用（タイトル）
     * @param     searchCond    CSVヘッダー部の出力用（検索条件）
     * @param     listCsvItem   CSV出力用項目情報
     * @param     listData      CSV出力データ
     * @return    出力結果
     * @retval    true:出力成功時（出力失敗時はMcsExceptionを発生させる）
     * @attention Controllerより呼び出されることを想定。
     * @note      CSVヘッダ部を生成しCSVファイル出力処理をCallする。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean csvOut(HttpServletResponse res, MessageSource messageSource, Locale locale, String fileName,
            String titleKey, String searchCond, List<ComCsvItem> listCsvItem, List<T> listData) throws McsException {

        StringBuilder sbHeader = new StringBuilder();
        // ######################
        // 1行目：タイトル
        // ######################
        String csvTitle = messageSource.getMessage(titleKey, null, locale);
        if (csvTitle != null && csvTitle.length() > 0) {
            sbHeader.append(ComConst.CSV_TITLE + ComConst.CSV_SEPARATOR + "\"" + csvTitle + "\"" + ComConst.BR);
        } else {
            sbHeader.append(ComConst.CSV_TITLE + ComConst.CSV_SEPARATOR + ComConst.BR);
        }

        // ######################
        // 2行目：出力日
        // ######################
        sbHeader.append(
                ComConst.CSV_DATE + ComConst.CSV_SEPARATOR + ComFunction.dateToString(new Date()) + ComConst.BR);

        // ######################
        // 3行目：検索条件
        // ######################
        if (searchCond != null && searchCond.length() > 0) {
            sbHeader.append(ComConst.CSV_SEARCH_COND + ComConst.CSV_SEPARATOR + "\"" + searchCond + "\"" + ComConst.BR);
        } else {
            sbHeader.append(ComConst.CSV_SEARCH_COND + ComConst.CSV_SEPARATOR + ComConst.BR);
        }

        // ######################
        // CSVファイル出力
        // ######################
        return csvOut(res, messageSource, locale, fileName, sbHeader.toString(), listCsvItem, listData);

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     入力されたファイル名の拡張子の前にシステム日時（yyyyMMddHHmmss）を付与する。
     * @param     fileName      ファイル名
     * @param     sysDate       付与するシステム日時
     * @return    年月日時分秒つきファイル名
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private static String addDatetime(String fileName, Date sysDate) {

        String fileNameDt = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT);
        String dateTime = dateFormat.format(sysDate);
        String fileNameBody = null;
        String fileNameExt = null;

        // 拡張子で分割
        int point = fileName.lastIndexOf(".");
        if (point != -1) {
            // 拡張子あり
            fileNameBody = fileName.substring(0, point);
            fileNameExt = "." + fileName.substring(point + 1);
        } else {
            // 拡張子なし
            fileNameBody = fileName;
            fileNameExt = "";
        }

        fileNameDt = fileNameBody + dateTime + fileNameExt;

        return fileNameDt;
    }

}
