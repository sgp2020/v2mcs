//@formatter:off
/**
 ******************************************************************************
 * @file        TableCompConfigService.java
 * @brief       TableCompConfig関連のサービス
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.mapper.TableCompConfigMMapper;
import net.muratec.mcs.mapper.TableCompConfigMapper;
import net.muratec.mcs.model.TableCompConfig;
import net.muratec.mcs.model.TableCompConfigExample;
import net.muratec.mcs.model.TableCompConfigM;

//@formatter:off
/**
 ******************************************************************************
 * @brief     TableCompConfig関連のサービスクラス
 * @par       機能:
 *              insertUserConfigFromMaster（ユーザー設定情報初回登録処理）
 *              getConfigMaster（マスタ情報取得処理）
 *              getConfig（ユーザー設定情報取得処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class TableCompConfigService extends BaseService {

    /** テーブルコンポーネント設定（マスタ） */
    @Autowired private TableCompConfigMMapper tableCompConfigMasterMapper;

    /** テーブルコンポーネント設定（ユーザー） */
    @Autowired private TableCompConfigMapper tableCompConfigMapper;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザー設定情報初回登録処理
     * @param     userId          ユーザID
     * @param     masterConfig    マスタ情報
     * @return    登録したユーザー設定情報
     * @retval
     * @attention
     * @note      マスタ情報と同じ値（デフォルト値）でユーザー情報を登録する。
     *            ログインしていない場合、登録は行わない。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public TableCompConfig insertUserConfigFromMaster(String userId, TableCompConfigM masterConfig) {

        TableCompConfig record = new TableCompConfig();
        record.setUserId(userId);
        record.setTableCompId(masterConfig.getTableCompId());
        record.setColDisplayFlagList(masterConfig.getColDisplayFlagList());
        record.setColOrderList(masterConfig.getColOrderList());
        record.setPageRecords(masterConfig.getPageRecords());

        if (!ComConst.ConstUserId.NOLOGIN.equals(userId)) {
            tableCompConfigMapper.insert(record);
        }

        return record;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     マスタ情報取得処理
     * @param     tableCompId     テーブルコンポーネントID
     * @return    テーブルコンポーネントマスタ情報
     * @retval
     * @attention
     * @note      DBからマスタ情報を取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public TableCompConfigM getConfigMaster(String tableCompId) {

        return tableCompConfigMasterMapper.selectByPrimaryKey(tableCompId);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ユーザー設定情報取得処理
     * @param     userId          ユーザーID
     * @param     tableCompId     テーブルコンポーネントID
     * @param     masterConfig    マスター情報
     * @return    テーブルコンポーネントユーザー設定情報
     * @retval
     * @attention
     * @note      DBからユーザー設定を取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public TableCompConfig getConfig(String userId, String tableCompId, TableCompConfigM masterConfig) {

        TableCompConfigExample example = new TableCompConfigExample();
        example.createCriteria().andUserIdEqualTo(userId).andTableCompIdEqualTo(tableCompId);

        List<TableCompConfig> list = tableCompConfigMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            TableCompConfig userConfig = this.insertUserConfigFromMaster(userId, masterConfig);
            return userConfig;
        } else {
            return list.get(0);
        }
    }

}
