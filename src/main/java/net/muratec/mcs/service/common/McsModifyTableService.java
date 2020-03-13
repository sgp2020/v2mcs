//@formatter:off
/**
 ******************************************************************************
 * @file        McsModifyTableService.java
 * @brief       画面部品ModifyTableで扱うサービス
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

import net.muratec.mcs.entity.common.ModifyTableItemEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.TableCompConfigMapper;
import net.muratec.mcs.model.TableCompConfig;
import net.muratec.mcs.model.TableCompConfigExample;
import net.muratec.mcs.model.TableCompConfigM;

//@formatter:off
/**
 ******************************************************************************
 * @brief     画面部品ModifyTableで扱うサービスクラス
 * @par       機能:
 *              getModifyTableItemList（項目情報一覧取得処理）
 *              updateColumnVisible（カラム表示非表示更新処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class McsModifyTableService extends BaseService {

    /** ログ出力 */
    private static final Logger logger = LoggerFactory.getLogger(McsModifyTableService.class);

    @Autowired private TableCompConfigService tableCompConfigService;

    /** テーブルコンポーネント設定（ユーザー） */
    @Autowired private TableCompConfigMapper tableCompConfigMapper;

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     項目情報一覧取得処理
     * @param     tableCompId     テーブルコンポーネントID
     * @param     userId          ユーザーID
     * @param     locale          ロケール
     * @return
     * @retval
     * @attention
     * @note      項目情報一覧を取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<ModifyTableItemEntity> getModifyTableItemList(String tableCompId, String userId, Locale locale)
            throws McsException {

        TableCompConfigM masterConfig = tableCompConfigService.getConfigMaster(tableCompId);
        TableCompConfig userConfig = tableCompConfigService.getConfig(userId, tableCompId, masterConfig);

        String itemIdListStr = masterConfig.getColIdList();
        String itemNameKeyListStr = masterConfig.getColNameKeyList();
        String itemDisplayFlagListStr = userConfig.getColDisplayFlagList();

        String[] itemIdList = super.commaStrToArray(itemIdListStr);
        String[] itemNameKeyList = super.commaStrToArray(itemNameKeyListStr);
        String[] itemDisplayFlagList = super.commaStrToArray(itemDisplayFlagListStr);

        int length = itemIdList.length;
        if (itemNameKeyList.length != length || itemDisplayFlagList.length != length) {
            // DBの定義におかしな点がある場合
            logger.warn("colIdList.length = {}", itemIdList.length);
            logger.warn("colNameKeyList.length = {}", itemNameKeyList.length);
            logger.warn("colDisplayFlagList.length = {}", itemDisplayFlagList.length);
            // Illegal config values in TableCompConfig.
            throw new McsException(
                    messageSource.getMessage("ERR0017", null, "ERR0017", LocaleContextHolder.getLocale()));
        }

        List<ModifyTableItemEntity> list = new ArrayList<ModifyTableItemEntity>();
        for (int i = 0; i < length; i++) {
            String itemId = itemIdList[i];
            String itemNameKey = itemNameKeyList[i];
            String itemDisplayFlag = itemDisplayFlagList[i];

            ModifyTableItemEntity entity = new ModifyTableItemEntity();
            entity.itemId = itemId;
            entity.itemName = messageSource.getMessage(itemNameKey, null, locale);
            entity.itemDisplay = Integer.valueOf(itemDisplayFlag);
            list.add(entity);
            logger.info(entity.toString());
        }
        return list;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     カラム表示非表示更新処理
     * @param     userId            ユーザーID
     * @param     tableCompId       テーブルコンポーネントID
     * @param     columnDisplayList カラム表示非表示のリスト
     * @return    更新件数
     * @retval
     * @attention
     * @note      ユーザー設定のカラム表示非表示を更新する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int updateColumnVisible(String userId, String tableCompId, List<Integer> columnDisplayList) {

        String colDisplayFlagList = super.listToCommaStr(columnDisplayList);

        TableCompConfig record = new TableCompConfig();
        record.setColDisplayFlagList(colDisplayFlagList);

        TableCompConfigExample example = new TableCompConfigExample();
        example.createCriteria().andUserIdEqualTo(userId).andTableCompIdEqualTo(tableCompId);

        int updateCount = tableCompConfigMapper.updateByExampleSelective(record, example);
        return updateCount;
    }

}
