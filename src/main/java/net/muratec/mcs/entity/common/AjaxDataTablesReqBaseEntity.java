//@formatter:off
/**
 ******************************************************************************
 * @file        AjaxDataTablesReqBaseEntity.java
 * @brief       McsDataTables用のリクエストエンティティ
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
package net.muratec.mcs.entity.common;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables用のリクエストエンティティ
 * @par      McsDataTablesからのリクエストの基底クラス。
 *           McsDataTables用のリクエストエンティティはこのクラスを継承する。
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * 0.1         Step1リリース                                             CSC
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class AjaxDataTablesReqBaseEntity extends AjaxReqBaseEntity {

    // ------------------------------------
    // ページ番号
    // ------------------------------------
    public Integer pageNum = 1;
    // ------------------------------------
    // 1ページに表示するレコード数
    // ------------------------------------
    public Integer pageRecords = null;
    // ------------------------------------
    // テーブルコンポーネントID
    // ------------------------------------
    public String tableCompId = null;
    // ------------------------------------
    // 検索時の開始レコード番号
    // ------------------------------------
    public Integer fromRecordNum = 1;
    // ------------------------------------
    // 検索時の終了レコード番号
    // ------------------------------------
    public Integer toRecordNum = 100;
    // ------------------------------------
    // 検索を実施するかどうかのフラグ
    // ------------------------------------
    public boolean searchDataFlag = true;
}
