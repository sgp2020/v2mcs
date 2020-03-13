//@formatter:off
/**
 ******************************************************************************
 * @file        ComCsvItem.java
 * @brief       CSV出力用項目情報
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

//@formatter:off
/**
 ******************************************************************************
 * @brief     CSV出力用項目クラス（CSV出力に必要な情報を項目ごとに保持)
 * @par       機能:
 *              CSV出力情報を格納する変数宣言のみ
 * @attention entityNameにはメッセージプロパティにKeyを指定する
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class ComCsvItem {

    /**
     * CSV出力の項目名
     */
    public String csvOutName;

    /**
     * DBより返されるエンティティの項目名(Keyを指定)
     */
    public String entityName;

    /**
     * シングルクォーテーションを追加の有無(true:有、false:無)
     */
    public boolean addSingleQuotes = false;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コンストラクタ（引数あり）
     * @param     csvOutName       CSV出力される項目名
     * @param     entityName       DBより返されるEntityの変数名
     * @param     addSingleQuotes  項目データの先頭にシングルクォーテーションを付与
     * @return
     * @retval
     * @attention
     * @note      引数にてクラス変数を初期化
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ComCsvItem(String csvOutName, String entityName, boolean addSingleQuotes) {
        this.csvOutName = csvOutName;
        this.entityName = entityName;
        this.addSingleQuotes = addSingleQuotes;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     デフォルトコンストラクタ
     * @param
     * @return
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ComCsvItem() {
    }
}
