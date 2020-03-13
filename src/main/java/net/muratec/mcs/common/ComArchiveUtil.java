//@formatter:off
/**
 ******************************************************************************
 * @file        ComArchiveUtil.java
 * @brief       アーカイブ処理ユーティリティ
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アーカイブ処理ユーティリティ
 * @par       機能:
 *              byteToZipArchive（zipファイル生成）
 * @attention アーカイブデータはメモリ上(byte[]）で生成される
 * @note      CSVファイルを圧縮する際などに利用する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComArchiveUtil {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     データをzipファイルに圧縮
     * @param     dataList      圧縮するデータのリスト
     * @param     fileNameList  zipファイル内のデータのファイル名
     * @return    zipファイル
     * @retval    zipファイルのバイトデータ
     * @attention 圧縮失敗時はIOExceptionをスローする。
     * @note      CSVファイルを圧縮する際などに利用する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public static byte[] byteToZipArchive(List<byte[]> dataList, List<String> fileNameList) throws IOException {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ZipOutputStream out = new ZipOutputStream(baos)) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                byte[] data = dataList.get(i);
                String fileName = fileNameList.get(i);

                ZipEntry entry = new ZipEntry(fileName);
                entry.setSize(data.length);
                out.putNextEntry(entry);
                out.write(data);
            }
            out.close();
            baos.close();
            return baos.toByteArray();
        }
    }
}
