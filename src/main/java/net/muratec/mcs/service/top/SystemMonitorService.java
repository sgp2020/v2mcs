//@formatter:off
/**
 ******************************************************************************
 * @file        SystemMonitorService.java
 * @brief       システムモニタ表示関連のサービス
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.top;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.top.SiteMapInfoEntity;
import net.muratec.mcs.entity.top.SystemMonitorEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiFuncGroupRltMapper;
import net.muratec.mcs.mapper.McsConstsMapper;
import net.muratec.mcs.mapper.McsMapper;
import net.muratec.mcs.mapper.ScreenColorMasterMapper;
import net.muratec.mcs.mapper.SitemapTableConfigMapper;
import net.muratec.mcs.mapper.UserAccountMapper;
import net.muratec.mcs.model.GuiFuncGroupRltExample;
import net.muratec.mcs.model.GuiFuncGroupRltKey;
import net.muratec.mcs.model.McsConsts;
import net.muratec.mcs.model.McsState;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.SitemapTableConfig;
import net.muratec.mcs.model.SitemapTableConfigExample;
import net.muratec.mcs.model.UserAccount;
import net.muratec.mcs.model.UserAccountExample;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムモニタ表示関連のサービスクラス
 * @par       機能:
 *              getState（通信/制御ステータス、ソフトウェア/GUIバージョンの取得を行う）
 *              getOccupiedRate（凡例画面の閾値リストの取得を行う）
 *              chkStringToInteger（Stringからint型への変換チェック機能）
 *              betweenPerString（凡例画面の閾値表示フォーマットへの変換機能）
 *              getSiteMapLabel(サイトマップ用カテゴリ情報の取得を行う機能)
 *              getSiteMapButton(サイトマップ用ボタン情報の取得を行う機能)
 *              sortSitemapData(サイトマップデータのリストをソートする処理)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class SystemMonitorService extends BaseService {

    // Mcsマッパー生成
    @Autowired private McsMapper mcsMapper;

    // McsConstsマッパー生成
    @Autowired private McsConstsMapper mcsConstsMapper;

    // SitemapTableConfigマッパー生成
    @Autowired private SitemapTableConfigMapper sitemapTableConfigMapper;

    // UserAccountマッパー生成
    @Autowired private UserAccountMapper userAccountMapper;

    // GuiFuncGroupRltマッパー生成
    @Autowired private GuiFuncGroupRltMapper guiFuncGroupRltMapper;

    // メッセージリソース
    @Autowired private MessageSource messageSource;

    // サイトマップ用テーブル(SITEMAP_TABLE_CONFIG)のKEYカラムの分割番号
    private static final int SITEMAP_KEY1 = 0;
    private static final int SITEMAP_KEY2 = 1;
    private static final int SITEMAP_KEY3 = 2;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     通信/制御ステータス、ソフトウェア/GUIバージョンの取得を行う機能
     * @param
     * @return    ステータス、バージョンの取得結果
     * @retval
     * @attention
     * @note      通信/制御ステータス、ソフトウェア/GUIバージョンの取得処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public SystemMonitorEntity getState() throws McsException {

        // ------------------------------------
        // ステータスの取得
        // ------------------------------------
        List<McsState> mcsStateList = mcsMapper.selectState();
        // MCSテーブルのレコード数が1つ以外ならば例外を投げる
        if (mcsStateList == null || mcsStateList.size() != 1) {
            String errorMessage = messageSource.getMessage("ERR0034", null, "ERR0034", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // ------------------------------------
        // エンティティに格納
        // ------------------------------------
        McsState mcsState = mcsStateList.get(0);
        SystemMonitorEntity sysmonEntity = new SystemMonitorEntity();
        sysmonEntity.mcsVer = mcsState.getMcsVersion();
        sysmonEntity.guiVer = mcsState.getGuiVersion();
        sysmonEntity.controlState = mcsState.getSystemState();
        sysmonEntity.commState = mcsState.getCommState();

        return sysmonEntity;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     凡例画面-棚占有率の閾値取得機能
     * @param
     * @return    閾値の範囲リスト
     * @retval
     * @attention
     * @note      棚占有率の閾値を取得し、範囲リストとして返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public Map<String, String> getOccupiedRate() throws McsException {

        int lowNum = ComConst.OccupiedRate.DEFAULT_OCCUPIED_RATE_CAUTION;
        int highNum = ComConst.OccupiedRate.DEFAULT_OCCUPIED_RATE_ALERT;

        Map<String, String> occuRateMap = new HashMap<>();

        McsConsts lowRateRec = mcsConstsMapper.selectByPrimaryKey("OCCUPIED_RATE_CAUTION_LEVEL");

        // レコードの存在チェック＆整数値への変換チェック
        if (lowRateRec != null && chkStringToInteger(lowRateRec.getValue())) {
            lowNum = Integer.parseInt(lowRateRec.getValue());
        }

        McsConsts highRateRec = mcsConstsMapper.selectByPrimaryKey("OCCUPIED_RATE_ALERT_LEVEL");

        // レコードの存在チェック＆整数値への変換チェック
        if (highRateRec != null && chkStringToInteger(highRateRec.getValue())) {
            highNum = Integer.parseInt(highRateRec.getValue());
        }

        // HashMap形式に変換する
        occuRateMap.put("lowRate", betweenPerString(0, lowNum));
        occuRateMap.put("middleRate", betweenPerString(lowNum + 1, highNum));
        occuRateMap.put("highRate", betweenPerString(highNum + 1, 100));

        return occuRateMap;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     整数値へ変換チェック
     * @param     str  チェック対象の文字列
     * @return    変換の可否
     * @retval    true 変換可能 / false 変換不可
     * @attention
     * @note      整数値への変換可否結果を返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean chkStringToInteger(String str) {

        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     引数から指定のString型文字列へ変換する機能
     * @param     lowNum    文字列内の下限値
     * @param     highNum   文字列内の上限値
     * @return    閾値の範囲文字列
     * @retval    「(lowNum) - (highNum)%」の形式
     * @attention
     * @note      入力された引数から、「(lowNum) - (highNum)%」の文字列を出力する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String betweenPerString(int lowNum, int highNum) {

        StringBuilder sb = new StringBuilder();
        sb.append(lowNum);
        sb.append(" - ");
        sb.append(highNum);
        sb.append("%");

        return sb.toString();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     サイトマップ用カテゴリ情報の取得を行う機能
     * @param
     * @return    カテゴリ情報取得結果
     * @retval
     * @attention
     * @note      サイトマップのカテゴリ情報の取得処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<SiteMapInfoEntity> getSiteMapLabel() throws McsException {

        // ------------------------------------
        // SECTIONが"LABEL"のレコードを取得
        // ------------------------------------
        SitemapTableConfigExample example = new SitemapTableConfigExample();
        example.createCriteria().andSectionEqualTo("LABEL");
        example.setOrderByClause("KEY ASC");
        List<SitemapTableConfig> sitemapList = sitemapTableConfigMapper.selectByExample(example);

        // 存在しないなら例外を投げる
        if (sitemapList == null ) {
            String errorMessage = messageSource.getMessage("ERR0034", null, "ERR0034", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // ------------------------------------
        // エンティティに格納
        // ------------------------------------
        List<SiteMapInfoEntity> retEntity = new ArrayList<SiteMapInfoEntity>();

        for(SitemapTableConfig sitemap: sitemapList) {
            SiteMapInfoEntity entity = new SiteMapInfoEntity();

            String[] keys = sitemap.getKey().split(",");
            entity.setKey1(Integer.parseInt(keys[SITEMAP_KEY1], 10));
            entity.setKey2(Integer.parseInt(keys[SITEMAP_KEY2], 10));
            entity.setKey3(Integer.parseInt(keys[SITEMAP_KEY3], 10));

            // カテゴリ文字列を取得
            String labelText = messageSource.getMessage(sitemap.getValue1(), null, sitemap.getValue1(), LocaleContextHolder.getLocale());
            entity.setValue1(labelText);

            entity.setValue2(sitemap.getValue2());
            entity.setValue3(sitemap.getValue3());
            entity.setValue4(sitemap.getValue4());
            entity.setValue5(sitemap.getValue5());
            retEntity.add(entity);
        }

        // ------------------------------------
        // ソート
        // ------------------------------------
        sortSitemapData(retEntity);

        return retEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     サイトマップ用ボタン情報の取得を行う機能
     * @param
     * @return    ボタン情報取得結果
     * @retval
     * @attention
     * @note      サイトマップのボタン情報の取得処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<List<SiteMapInfoEntity>> getSiteMapButton(Short userId) throws McsException {

        // ------------------------------------
        // SECTIONが"BUTTON"のレコードを取得
        // ------------------------------------
        SitemapTableConfigExample example = new SitemapTableConfigExample();
        example.createCriteria().andSectionEqualTo("BUTTON");
        example.setOrderByClause("KEY ASC");
        List<SitemapTableConfig> sitemapList = sitemapTableConfigMapper.selectByExample(example);

        // 存在しないなら例外を投げる
        if (sitemapList == null ) {
            String errorMessage = messageSource.getMessage("ERR0034", null, "ERR0034", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // ------------------------------------
        // ユーザーIDからGUI_GROUP_IDを取得
        // ------------------------------------
        UserAccountExample userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andUserIdEqualTo(userId);
        List<UserAccount> userAccountList = userAccountMapper.selectByExample(userAccountExample);

        // 存在しないなら例外を投げる
        if (userAccountList == null ) {
            String errorMessage = messageSource.getMessage("ERR0034", null, "ERR0034", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // グループIDを取得
        String groupId = null;
        for (UserAccount account:userAccountList) {
            groupId = account.getGuiGroupId();
        }

        // ------------------------------------
        // GUI_FUNC_GROUP_RLTから有効機能の一覧を取得
        // ------------------------------------
        GuiFuncGroupRltExample guiFuncGroupRltExample = new GuiFuncGroupRltExample();
        guiFuncGroupRltExample.createCriteria().andGuiGroupIdEqualTo(groupId);
        guiFuncGroupRltExample.setOrderByClause("FUNC_ID ASC");
        List<GuiFuncGroupRltKey> funcGroupList = guiFuncGroupRltMapper.selectByExample(guiFuncGroupRltExample);

        // 存在しないなら例外を投げる
        if (funcGroupList == null ) {
            String errorMessage = messageSource.getMessage("ERR0034", null, "ERR0034", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // ------------------------------------
        // エンティティに格納
        // ------------------------------------
        List<SiteMapInfoEntity> entities = new ArrayList<SiteMapInfoEntity>();

        for(SitemapTableConfig sitemap: sitemapList) {
            SiteMapInfoEntity entity = new SiteMapInfoEntity();

            String[] keys = sitemap.getKey().split(",");
            entity.setKey1(Integer.parseInt(keys[SITEMAP_KEY1], 10));
            entity.setKey2(Integer.parseInt(keys[SITEMAP_KEY2], 10));
            entity.setKey3(Integer.parseInt(keys[SITEMAP_KEY3], 10));

            // ボタンのテキストを設定
            String labelText = messageSource.getMessage(sitemap.getValue1(), null, sitemap.getValue1(), LocaleContextHolder.getLocale());
            entity.setValue1(labelText);

            // ボタンの有効／無効を設定
            String value2 = "false";
            for (GuiFuncGroupRltKey key:funcGroupList) {
                // GUI_FUNC_GROUP_RLTに登録されているなら有効
                if (sitemap.getValue2().equals(key.getFuncId())) {
                    value2 = "true";
                }
            }
            entity.setValue2(value2);

            entity.setValue3(sitemap.getValue3());
            entity.setValue4(sitemap.getValue4());
            entity.setValue5(sitemap.getValue5());
            entities.add(entity);
        }

        // ------------------------------------
        // カテゴリごとに分割して格納
        // ------------------------------------
        List<List<SiteMapInfoEntity>> retEntity = new ArrayList<List<SiteMapInfoEntity>>();
        int from = 0;
        for (int i = 0; i < entities.size(); i++) {
            // 対象リストの末尾もしくはKey1が切り替わるタイミングで、データを切り出し結果のリストに加える
            if ((entities.size() - 1 == i) || (entities.get(i).getKey1() != entities.get(i + 1).getKey1())) {
                retEntity.add(new ArrayList<SiteMapInfoEntity>(entities.subList(from, i + 1)));
                from = i + 1;
            }
        }

        // ------------------------------------
        // カテゴリごとにソート
        // ------------------------------------
        for(int j=0; j<retEntity.size();j++) {
            sortSitemapData(retEntity.get(j));
        }

        return retEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     サイトマップデータのリストをソートする処理
     * @param     サイトマップデータ要素リスト
     * @return
     * @retval
     * @attention
     * @note      優先順位の昇順でソート
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void sortSitemapData(List<SiteMapInfoEntity> sitemapDataList) {

        Collections.sort(sitemapDataList, new Comparator<SiteMapInfoEntity>() {

            @Override
            public int compare(SiteMapInfoEntity a, SiteMapInfoEntity b) {

                return a.key3 - b.key3;
            }
        });
    }
}
