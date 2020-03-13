//@formatter:off
/**
 ******************************************************************************
 * @file        StringMasterService.java
 * @brief       ストリングマスターテーブルにアクセスするサービス
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
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.common.MultiSelectBoxItemEntity;
import net.muratec.mcs.entity.common.StringMasterEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.StringMasterMapper;
import net.muratec.mcs.model.StringMaster;
import net.muratec.mcs.model.StringMasterExample;
import net.muratec.mcs.model.StringMasterKey;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ストリングマスターテーブルにアクセスするサービスクラス
 * @par       機能:
 *              getString（ストリングマスター取得処理）
 *              getString（ストリングマスター取得処理(short)）
 *              getStringNoException（ストリングマスター取得処理(short)）
 *              getList（ストリングマスター一覧取得処理）
 *              getListOrderByCode（ストリングマスター一覧取得処理(CODE昇順)）
 *              getListOrderByString（ストリングマスター一覧取得処理（STRING昇順））
 *              getEntityListOrderByCode（ストリングマスターエンティティ一覧取得処理(CODE昇順)）
 *              getEntityListOrderByString（ストリングマスターエンティティ一覧取得処理（STRING昇順））
 *              listToEntity（エンティティコピー処理）
 *              getMultiSelectBoxItems（マルチセレクトボックス要素生成処理）
 *              loadStringMaster（ストリングマスターロード処理）
 *              getStrMstMap（ストリングマスターMAP取得処理）
 *              getStrMstFromMap（ストリングマスター文字列取得処理）
 *              getStrMstListFromMap(ストリングマスター取得（SelectBox用）処理)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class StringMasterService extends BaseService {

    /** ログ出力 */
    private static final Logger logger = LoggerFactory.getLogger(StringMasterService.class);

    /** StringMaster保存用MAP */
    private ConcurrentHashMap<String, ConcurrentHashMap<Long, String>> stringMasterMap = new ConcurrentHashMap<String, ConcurrentHashMap<Long, String>>();

    /** テーブルコンポーネント設定（ユーザー） */
    @Autowired private StringMasterMapper stringMasterMapper;

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター取得処理
     * @param     key             検索キー
     * @param     code            検索コード(int)
     * @return    取得したストリングマスターのSTRING
     * @retval
     * @attention
     * @note      プライマリーキーでストリングマスターを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public String getString(String key, int code) throws McsException {

        logger.debug("key=[" + key + "] code=[" + code + "]");

        return getString(key, (short) code);

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター取得処理
     * @param     key             検索キー
     * @param     code            検索コード(short)
     * @return    取得したストリングマスターのSTRING
     * @retval
     * @attention
     * @note      プライマリーキーでストリングマスターを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public String getString(String key, long code) throws McsException {

        logger.debug("key=[" + key + "] code=[" + code + "]");

        StringMasterKey strMstKey = new StringMasterKey();
        strMstKey.setKey(key);
        strMstKey.setCode(code);
        StringMaster strMst = stringMasterMapper.selectByPrimaryKey(strMstKey);

        // nullチェック
        if (strMst == null || strMst.getString() == null) {
            // 検索にHITしない、取得したStringがnullなら例外
            String[] args = { key, String.valueOf(code) };
            String errMessage = messageSource.getMessage("ERR0024", args, "ERR0024", LocaleContextHolder.getLocale());
            throw new McsException(errMessage);
        }

        return strMst.getString();

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター取得処理
     * @param     key             検索キー
     * @param     code            検索コード(short)
     * @return    取得したストリングマスターのSTRING
     * @retval
     * @attention
     * @note      プライマリーキーでストリングマスターを取得する
     *            HITしない場合は空文字を返す。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public String getStringNoException(String key, long code) {

        logger.debug("key=[" + key + "] code=[" + code + "]");

        StringMasterKey strMstKey = new StringMasterKey();
        strMstKey.setKey(key);
        strMstKey.setCode(code);
        StringMaster strMst = stringMasterMapper.selectByPrimaryKey(strMstKey);

        // nullチェック
        if (strMst == null || strMst.getString() == null) {
            // 検索にHITしない、取得したStringがnullなら空文字を返す。
            return "";
        }

        return strMst.getString();

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター一覧取得処理
     * @param     key             検索キー
     * @param     order           並び順
     * @return    ストリングマスター一覧
     * @retval
     * @attention
     * @note      検索キーでストリングマスターの一覧を指定されたソート順で取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    private List<StringMaster> getList(String key, String order) {

        logger.debug("key=[" + key + "] order by=[" + order + "]");

        StringMasterExample strMstExample = new StringMasterExample();
        strMstExample.createCriteria().andKeyEqualTo(key);
        strMstExample.setOrderByClause(order);

        List<StringMaster> strMstList = stringMasterMapper.selectByExample(strMstExample);

        return strMstList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター一覧取得処理(CODE昇順)
     * @param     key             検索キー
     * @return    ストリングマスター一覧
     * @retval
     * @attention
     * @note      検索キーでストリングマスターの一覧をCODE昇順で取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<StringMaster> getListOrderByCode(String key) {

        return getList(key, "CODE ASC");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター一覧取得処理(STRING昇順)
     * @param     key             検索キー
     * @return    ストリングマスター一覧
     * @retval
     * @attention
     * @note      検索キーでストリングマスターの一覧をSTRING昇順で取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<StringMaster> getListOrderByString(String key) {

        return getList(key, "STRING ASC");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターエンティティ一覧取得処理(CODE昇順)
     * @param     key             検索キー
     * @return    ストリングマスターエンティティ一覧
     * @retval
     * @attention
     * @note      検索キーでストリングマスターの一覧をCODE昇順で取得する。
     *            取得した一覧をエンティティに詰め替える。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<StringMasterEntity> getEntityListOrderByCode(String key) {

        return listToEntity(getList(key, "CODE ASC"));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターエンティティ一覧取得処理(STRING昇順)
     * @param     key             検索キー
     * @return    ストリングマスターエンティティ一覧
     * @retval
     * @attention
     * @note      検索キーでストリングマスターの一覧をSTRING昇順で取得する。
     *            取得した一覧をエンティティに詰め替える。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<StringMasterEntity> getEntityListOrderByString(String key) {

        return listToEntity(getList(key, "STRING ASC"));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     エンティティコピー処理
     * @param     strMstList      ストリングマスター一覧
     * @return    ストリングマスターエンティティ一覧
     * @retval
     * @attention
     * @note      引数のストリングマスター一覧をエンティティに詰め替える。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private List<StringMasterEntity> listToEntity(List<StringMaster> strMstList) {

        List<StringMasterEntity> strMstEntityList = new ArrayList<StringMasterEntity>();

        // 引数がNullか0件なら、そのまま空リストを返却
        if (strMstList == null || strMstList.size() == 0) {
            return strMstEntityList;
        }

        // 全件ループさせ、Entityに詰め替える。
        for (StringMaster strMst : strMstList) {
            StringMasterEntity strMstEntity = new StringMasterEntity();
            strMstEntity.code = strMst.getCode();
            strMstEntity.string = strMst.getString();
            strMstEntityList.add(strMstEntity);
        }
        return strMstEntityList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     マルチセレクトボックス要素生成処理
     * @param     key      ストリングマスターのkey
     * @return    マルチセレクトボックスの要素リスト
     * @retval
     * @attention
     * @note      引数のストリングマスターのkeyを基にマルチセレクトボックスの要素を生成する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<MultiSelectBoxItemEntity> getMultiSelectBoxItems(String key) {

        // StringMasterからデータを取得
        List<StringMaster> stringMasterMultiSelectBox = getListOrderByCode(key);

        List<MultiSelectBoxItemEntity> retList = new ArrayList<MultiSelectBoxItemEntity>();
        for (StringMaster stringMaster : stringMasterMultiSelectBox) {
            // 要素とオプションを設定
            MultiSelectBoxItemEntity multiSelectBoxItem = new MultiSelectBoxItemEntity();
            multiSelectBoxItem.disp = true;
            multiSelectBoxItem.selected = true;
            multiSelectBoxItem.data = stringMaster;
            retList.add(multiSelectBoxItem);
        }
        return retList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターロード処理
     * @param     key      ストリングマスターのkey
     * @param     forceLoad 強制ロードフラグ
     * @return
     * @retval
     * @attention 強制ロードがfalseかつ、既にLoad済みのKey値は読み込まない。
     * @note      指定されたKeyのStringMasterを読込MAPに保存する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void loadStringMaster(String key, boolean forceLoad) {

        logger.debug("key=[" + key + "]");

        // 強制ロードがfalseかつ、既にLoad済みなら抜ける
        if (forceLoad == false && stringMasterMap.containsKey(key)) {
            return;
        }

        // 未Loadの場合は、StringMasterからデータを取得しMAPに入れる。
        // StringMasterからデータを取得
        StringMasterExample strMstExample = new StringMasterExample();
        strMstExample.createCriteria().andKeyEqualTo(key);
        strMstExample.setOrderByClause("CODE ASC");

        List<StringMaster> strMstList = stringMasterMapper.selectByExample(strMstExample);

        // MAPに格納
        ConcurrentHashMap<Long, String> strMstMap = new ConcurrentHashMap<Long, String>();
        for (StringMaster strMst : strMstList) {
            strMstMap.put(strMst.getCode(), strMst.getString());
        }
        stringMasterMap.put(key, strMstMap);

        return;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターMAP取得処理
     * @param     key      ストリングマスターのkey
     * @return    ストリングマスターMAP
     * @retval
     * @attention MAPに保存するため、新規登録時にはtomcatの再起動が必要。
     * @note      指定されたKeyのStringMasterをMAPで返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ConcurrentHashMap<Long, String> getStrMstMap(String key) {

        ConcurrentHashMap<Long, String> retStrMstMap = new ConcurrentHashMap<Long, String>();

        loadStringMaster(key, false);
        if (stringMasterMap.containsKey(key)) {
            retStrMstMap = stringMasterMap.get(key);
        }

        return retStrMstMap;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター文字列取得処理
     * @param     key      ストリングマスターのkey
     * @param     code     ストリングマスターのcode
     * @return    ストリングマスター.Strig
     * @retval
     * @attention MAPに保存するため、新規登録時にはtomcatの再起動が必要。
     * @note      存在しない場合は空文字を返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getStrMstFromMap(String key, Long code) {

        String retString = "";

        loadStringMaster(key, false);
        if (stringMasterMap.containsKey(key) && stringMasterMap.get(key).containsKey(code)) {
            retString = stringMasterMap.get(key).get(code);
        }

        return retString;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスター取得（SelectBox用）処理
     * @param     key      ストリングマスターのkey
     * @param     forceLoad 強制ロードフラグ
     * @return
     * @retval
     * @attention forceLoad=trueの場合、再度DBから取得しMAPに格納する。
     * @note      MAPよりSelectBox用データを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<String[]> getStrMstListFromMap(String key, boolean forceLoad) {

        List<String[]> strMstList = new ArrayList<String[]>();

        loadStringMaster(key, forceLoad);
        if (stringMasterMap.containsKey(key)) {
            ConcurrentHashMap<Long, String> strMstMap = stringMasterMap.get(key);
            for (ConcurrentHashMap.Entry<Long, String> e : strMstMap.entrySet()) {
                String[] data = new String[2];
                data[0] = e.getKey().toString();
                data[1] = e.getValue();
                strMstList.add(data);
            }
        }

        return strMstList;
    }

}
