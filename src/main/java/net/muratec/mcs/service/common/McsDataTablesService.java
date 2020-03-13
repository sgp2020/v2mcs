//@formatter:off
/**
 ******************************************************************************
 * @file        McsDataTablesService.java
 * @brief       画面部品DataTablesで扱うサービス
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
package net.muratec.mcs.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;
import net.muratec.mcs.entity.common.ColumnInfoEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.TableCompConfigMapper;
import net.muratec.mcs.model.TableCompConfig;
import net.muratec.mcs.model.TableCompConfigExample;
import net.muratec.mcs.model.TableCompConfigM;

//@formatter:off
/**
 ******************************************************************************
 * @brief     画面部品DataTablesで扱うサービスクラス
 * @par       機能:
 *              updatePageRecords（表示件数更新処理）
 *              updateColumnOrder（カラム表示順更新処理）
 *              updateColumnInfo（全カラム情報更新処理）
 *              createResEntity（レスポンスエンティティ生成処理）
 *              initReqEntity（リクエストエンティティ初期化処理）
 *              getColumnInfoList（カラム情報一覧取得処理）
 *              getColumnAlign（Alignテキスト取得処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class McsDataTablesService extends BaseService {

    /** ログ出力 */
    private static final Logger logger = LoggerFactory.getLogger(McsDataTablesService.class);

    /** テーブルコンポーネント設定（ユーザー） */
    @Autowired private TableCompConfigMapper tableCompConfigMapper;

    /** テーブルコンポーネント設定サービス */
    @Autowired private TableCompConfigService tableCompConfigService;

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザー設定の表示件数を更新する処理
     * @param     userId          ユーザーID
     * @param     tableCompId     テーブルコンポーネントID
     * @param     pageRecords     表示件数
     * @return    更新件数
     * @retval
     * @attention
     * @note      引数の表示件数を更新する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int updatePageRecords(String userId, String tableCompId, int pageRecords) {

        TableCompConfig record = new TableCompConfig();
        record.setPageRecords((short) pageRecords);

        TableCompConfigExample example = new TableCompConfigExample();
        example.createCriteria().andUserIdEqualTo(userId).andTableCompIdEqualTo(tableCompId);

        int updateCount = tableCompConfigMapper.updateByExampleSelective(record, example);
        return updateCount;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザー設定のカラム表示順を更新する処理
     * @param     userId          ユーザーID
     * @param     tableCompId     テーブルコンポーネントID
     * @param     columnOrderList カラム表示順のリスト
     * @return    更新件数
     * @retval
     * @attention
     * @note      引数のカラム表示順を更新する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int updateColumnOrder(String userId, String tableCompId, List<Integer> columnOrderList) {

        String colOrderList = super.listToCommaStr(columnOrderList);

        TableCompConfig record = new TableCompConfig();
        record.setColOrderList(colOrderList);

        TableCompConfigExample example = new TableCompConfigExample();
        example.createCriteria().andUserIdEqualTo(userId).andTableCompIdEqualTo(tableCompId);

        int updateCount = tableCompConfigMapper.updateByExampleSelective(record, example);
        return updateCount;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザー設定のカラム情報を全て更新する処理
     * @param     userId            ユーザーID
     * @param     tableCompId       テーブルコンポーネントID
     * @param     pageRecords       表示件数
     * @param     columnOrderList   カラム表示順のリスト
     * @param     columnDisplayList カラム表示非表示のリスト
     * @return    更新件数
     * @retval
     * @attention
     * @note      引数のカラム情報を更新する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int updateColumnInfo(String userId, String tableCompId, int pageRecords, List<Integer> columnOrderList,
            List<Integer> columnDisplayList) {

        TableCompConfig record = new TableCompConfig();
        record.setPageRecords((short) pageRecords);

        String colOrderList = super.listToCommaStr(columnOrderList);
        record.setColOrderList(colOrderList);

        String colDisplayFlagList = super.listToCommaStr(columnDisplayList);
        record.setColDisplayFlagList(colDisplayFlagList);

        TableCompConfigExample example = new TableCompConfigExample();
        example.createCriteria().andUserIdEqualTo(userId).andTableCompIdEqualTo(tableCompId);

        int updateCount = tableCompConfigMapper.updateByExampleSelective(record, example);
        return updateCount;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DataTables（グリッド）用のレスポンスエンティティを生成する処理
     * @param     resEntityClass    レスポンスエンティティのクラス
     * @param     reqEntity         リクエストエンティティ
     * @param     userId            ログインユーザーのユーザーID
     * @param     locale            ロケール
     * @return    生成したレスポンスエンティティ（初期化済み）
     * @retval
     * @attention
     * @note      DataTables（グリッド）用のレスポンスエンティティを生成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @SuppressWarnings("unchecked")
    @Transactional
    public <T> T createResEntity(Class<T> resEntityClass, AjaxDataTablesReqBaseEntity reqEntity, String userId,
            Locale locale) throws McsException {

        TableCompConfigM masterConfig = tableCompConfigService.getConfigMaster(reqEntity.tableCompId);
        TableCompConfig userConfig = tableCompConfigService.getConfig(userId, reqEntity.tableCompId, masterConfig);

        // リクエストエンティティの値を初期化
        this.initReqEntity(reqEntity, userConfig);

        // レスポンスエンティティをnewする
        T tmpObj = null;
        try {
            tmpObj = resEntityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            logger.warn("target newInstance Error.", e);
            return null;
        }
        AjaxDataTablesResBaseEntity resEntity = (AjaxDataTablesResBaseEntity) tmpObj;

        // ページ情報
        resEntity.pageInfo.currentPage = reqEntity.pageNum;
        resEntity.pageInfo.pageRecords = reqEntity.pageRecords;

        // カラム情報埋め込み
        List<ColumnInfoEntity> columnInfoList = getColumnInfoList(masterConfig, userConfig, locale);
        resEntity.header.columnInfoList = columnInfoList;

        return (T) resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DataTables（グリッド）用のリクエストエンティティを初期化する処理
     * @param     reqEntity         初期化対象のエンティティ
     * @param     userConfig        ユーザ設定情報
     * @return
     * @retval
     * @attention
     * @note      DataTables（グリッド）用のリクエストエンティティを初期化する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void initReqEntity(AjaxDataTablesReqBaseEntity reqEntity, TableCompConfig userConfig) throws McsException {

        // １ページあたりの件数セット
        Integer clientPageRecords = reqEntity.pageRecords;
        Integer serverPageRecords = userConfig.getPageRecords().intValue();

        if (clientPageRecords == null || clientPageRecords == 0) {
            // クライアントから表示件数が送られてきていないときは
            // サーバ側の情報を使用
            reqEntity.pageRecords = serverPageRecords;
        } else if (clientPageRecords.intValue() != serverPageRecords.intValue()) {
            // クライアントから送られてきたpageRecordsと、サーバ上の値が異なるとき、
            // クライアント側の値を利用。
            reqEntity.pageRecords = clientPageRecords;
        } else {
            // それ以外の場合、基本的にはクライアント側の値を利用。
            reqEntity.pageRecords = serverPageRecords;
        }
        // 開始レコード番号、終了レコード番号を算出
        reqEntity.fromRecordNum = ComFunction.getFromRecordNum(reqEntity.pageRecords, reqEntity.pageNum);
        reqEntity.toRecordNum = ComFunction.getToRecordNum(reqEntity.pageRecords, reqEntity.pageNum);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     カラム情報一覧を取得する処理
     * @param     masterConfig      マスター設定情報
     * @param     userConfig        ユーザ設定情報
     * @param     locale            ロケール
     * @return    カラム情報一覧
     * @retval
     * @attention
     * @note      カラム情報一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private List<ColumnInfoEntity> getColumnInfoList(TableCompConfigM masterConfig, TableCompConfig userConfig,
            Locale locale) throws McsException {

        String colIdListStr = masterConfig.getColIdList();
        String colNameKeyListStr = masterConfig.getColNameKeyList();
        String colAlignListStr = masterConfig.getColAlignList();
        String colUniqueFlagListStr = masterConfig.getColUniqueFlagList();
        String colDisplayFlagListStr = userConfig.getColDisplayFlagList();
        String colOrderListStr = userConfig.getColOrderList();

        String[] colIdList = super.commaStrToArray(colIdListStr);
        String[] colNameKeyList = super.commaStrToArray(colNameKeyListStr);
        String[] colAlignList = super.commaStrToArray(colAlignListStr);
        String[] colUniqueFlagList = super.commaStrToArray(colUniqueFlagListStr);
        String[] colDisplayFlagList = super.commaStrToArray(colDisplayFlagListStr);
        String[] colOrderList = super.commaStrToArray(colOrderListStr);

        int length = colIdList.length;
        if (colNameKeyList.length != length || colAlignList.length != length || colUniqueFlagList.length != length
                || colDisplayFlagList.length != length || colOrderList.length != length) {
            // DBの定義におかしな点がある場合
            logger.warn("colIdList.length = {}", colIdList.length);
            logger.warn("colNameKeyList.length = {}", colNameKeyList.length);
            logger.warn("colAlignList.length = {}", colAlignList.length);
            logger.warn("colUniqueFlagList.length = {}", colUniqueFlagList.length);
            logger.warn("colDisplayFlagList.length = {}", colDisplayFlagList.length);
            logger.warn("colOrderList.length = {}", colOrderList.length);
            // 例外発生：Illegal config values in TableCompConfig.
            throw new McsException(
                    messageSource.getMessage("ERR0017", null, "ERR0017", LocaleContextHolder.getLocale()));
        }

        List<ColumnInfoEntity> list = new ArrayList<ColumnInfoEntity>();
        for (int i = 0; i < length; i++) {
            String colId = colIdList[i];
            String colNameKey = colNameKeyList[i];
            String colAlign = colAlignList[i];
            String colUniqueFlag = colUniqueFlagList[i];
            String colDisplayFlag = colDisplayFlagList[i];
            String colOrder = colOrderList[i];

            ColumnInfoEntity entity = new ColumnInfoEntity();
            entity.columnId = colId;
            entity.columnName = messageSource.getMessage(colNameKey, null, locale);
            entity.columnDisplay = Integer.valueOf(colDisplayFlag);
            entity.columnAlign = getColumnAlign(colAlign);
            entity.isUniqueKey = Integer.valueOf(colUniqueFlag) == ComConst.BooleanInt.TRUE;
            entity.columnOrder = Integer.valueOf(colOrder);
            list.add(entity);
            logger.info(entity.toString());
        }

        return list;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字寄せ位置のテキストを取得する処理
     * @param     colAlign          文字寄せ位置（テーブルの値）
     * @return    文字寄せ位置（クライアントに渡す値）
     * @retval
     * @attention
     * @note      文字寄せ位置のテキストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String getColumnAlign(String colAlign) {

        switch (colAlign) {
            case "l":
                return "left";
            case "c":
                return "center";
            case "r":
                return "right";
            default:
                return "left";
        }
    }

}
