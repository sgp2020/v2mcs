//@formatter:off
/**
 ******************************************************************************
 * @file        SystemParameterService.java
 * @brief       システムパラメーターマスタメンテナンス関連のサービス
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
package net.muratec.mcs.service.maint;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.annotation.MsgCacheUpdate;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.entity.maint.GuiColorCsvEntity;
import net.muratec.mcs.entity.maint.GuiColorEntity;
import net.muratec.mcs.entity.maint.McsConstsEntity;
import net.muratec.mcs.entity.maint.ReqGuiColorEntity;
import net.muratec.mcs.entity.maint.ReqMcsConstsEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.McsConstsMapper;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.GuiColorKey;
import net.muratec.mcs.model.McsConsts;
import net.muratec.mcs.model.McsConstsExample;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief    SystemParameterService
 * @par      機能:
 *             getMcsConsts（システムパラメータレコード取得機能）
 *             getInt（システムパラメータ(int値)取得機能）
 *             getSysList（SysPara一覧取得機能）
 *             getSysListCount（SysPara一覧件数取得機能）
 *             getSysAllList（SysPara一覧取得機能（CSV用））
 *             getGuiList（GUIカラー一覧取得機能）
 *             getGuiListCount（GUIカラー一覧件数取得機能）
 *             getGuiAllList（GUIカラー一覧取得機能（CSV用））
 *             getSysInfo（SysPara情報取得機能）
 *             getGuiInfo（GUIカラー情報取得機能）
 *             modSysInfo（SysPara情報更新機能）
 *             modGuiInfo（GUIカラー情報更新機能）
 *             chgSysModelToEntity（SysParaモデルからエンティティ変換機能）
 *             chgGuiModelToEntity（GUIカラーモデルからエンティティ変換機能）
 *             chgGuiCsvModelToEntity（GUIカラーモデルからエンティティ変換機能（CSV用））
 *             short2Hex（Short型（数値）から16進（文字列）へ変換機能）
 *             hex2Short（16進（文字列）からShort型（数値）へ変換機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class SystemParameterService extends BaseService {

    @Autowired private McsConstsMapper mcsConstsMapper;
    @Autowired private GuiColorMapper guiColorMapper;

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定されたキーのシステムパラメータレコードを取得
     * @param     category    検索条件(カテゴリ)
     * @param     section     検索条件(セクション)
     * @param     key         検索条件(キー)
     * @return    検索結果のシステムパラメータレコード
     * @retval
     * @attention
     * @note      条件を与え、該当するシステムパラメータレコードを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public McsConsts getMcsConsts(String category, String section, String key) throws McsException {

        // McsConstsKey mcsConstsKey = new McsConstsKey();
        McsConstsExample mcsConstsExample = new McsConstsExample();
        mcsConstsExample.createCriteria().andCategoryEqualTo(category).andSectionEqualTo(section).andKeyEqualTo(key);

        List<McsConsts> retMcsConstsList = mcsConstsMapper.selectByExample(mcsConstsExample);

        if (retMcsConstsList == null || retMcsConstsList.size() <= 0) {
            throw new McsException(
                    messageSource.getMessage("ERR0013", null, "ERR0013", LocaleContextHolder.getLocale()));
        }

        return retMcsConstsList.get(0);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定されたキーのシステムパラメータをint値として取得する
     * @param     category    検索条件
     * @param     section     検索条件
     * @param     key         検索条件
     * @return    値（MCS_CONSTS.VALUE）
     * @retval
     * @attention システムパラメータが存在しないとき、int値でなかったときMcsExceptionが発生
     * @note      条件を与え、該当するシステムパラメータをint値として取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public Integer getInt(String category, String section, String key) throws McsException {

        McsConsts consts = this.getMcsConsts(category, section, key);
        try {
            return Integer.valueOf(consts.getValue());
        } catch (Exception e) {
            throw new McsException(
                    messageSource.getMessage("ERR0022", null, "ERR0022", LocaleContextHolder.getLocale()));
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysPara一覧取得機能
     * @param     reqEntity      画面項目情報
     * @return    システムパラメータ一覧
     * @retval    List形式で返却
     * @attention DISPLAY_FLAG = 1 のみを取得
     * @note      MCS_CONSTSテーブルより、指定された開始～終了行の一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<McsConstsEntity> getSysList(AjaxDataTablesReqBaseEntity reqEntity) {

        // データ取得
        List<McsConsts> mcsConstsList = mcsConstsMapper.selectSysRowNum(reqEntity);

        // エンティティに変換
        List<McsConstsEntity> retEntityList = new ArrayList<McsConstsEntity>();
        for (McsConsts mcsConsts : mcsConstsList) {
            McsConstsEntity mcsConstsEntity = chgSysModelToEntity(mcsConsts);
            retEntityList.add(mcsConstsEntity);
        }

        // 返却
        return retEntityList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysPara一覧件数取得機能
     * @param
     * @return    SysPara一覧件数
     * @retval    int形式で返却
     * @attention
     * @note      MCS_CONSTSテーブルの件数を取得する(DISPLAY_FLAG=1のレコードのみ)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getSysListCount() {

        McsConstsExample example = new McsConstsExample();
        example.createCriteria().andDisplayFlagEqualTo(Short.valueOf("1"));

        int count = (int) mcsConstsMapper.countByExample(example);

        return count;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysPara一覧取得機能（CSV用）
     * @param
     * @return    システムパラメータ一覧
     * @retval    List形式で返却
     * @attention DISPLAY_FLAG = 1 のみを取得
     * @note      MCS_CONSTSテーブルより、指定された開始～終了行の一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<McsConstsEntity> getSysAllList() {

        // データ取得
        McsConstsExample example = new McsConstsExample();
        example.createCriteria().andDisplayFlagEqualTo(Short.valueOf("1"));
        example.setOrderByClause("CATEGORY, SECTION, KEY ASC");
        List<McsConsts> mcsConstsList = mcsConstsMapper.selectByExample(example);

        // エンティティに変換
        List<McsConstsEntity> retEntityList = new ArrayList<McsConstsEntity>();
        for (McsConsts mcsConsts : mcsConstsList) {
            McsConstsEntity mcsConstsEntity = chgSysModelToEntity(mcsConsts);
            retEntityList.add(mcsConstsEntity);
        }

        // 返却
        return retEntityList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラー一覧取得機能
     * @param     reqEntity      画面項目情報
     * @return    GUIカラー一覧
     * @retval    List形式で返却
     * @attention
     * @note      GUI_COLORテーブルより、指定された開始～終了行の一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<GuiColorEntity> getGuiList(AjaxDataTablesReqBaseEntity reqEntity) {

        // データ取得
        List<GuiColor> guiColorList = guiColorMapper.selectGuiColorRowNum(reqEntity);

        // エンティティに変換
        List<GuiColorEntity> retEntityList = new ArrayList<GuiColorEntity>();
        for (GuiColor guiColor : guiColorList) {
            GuiColorEntity guiColorEntity = chgGuiModelToEntity(guiColor);
            retEntityList.add(guiColorEntity);
        }

        // 返却
        return retEntityList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラー一覧件数取得機能
     * @param
     * @return    GUIカラー一覧件数
     * @retval    int形式で返却
     * @attention
     * @note      GUI_COLORテーブルの件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getGuiListCount() {

        GuiColorExample example = new GuiColorExample();
        example.createCriteria();

        int count = (int) guiColorMapper.countByExample(example);

        return count;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラー一覧取得機能（CSV用）
     * @param
     * @return    GUIカラー一覧
     * @retval    List形式で返却
     * @attention
     * @note      GUI_COLORテーブルより、全件を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<GuiColorCsvEntity> getGuiAllList() {

        // データ取得
        GuiColorExample example = new GuiColorExample();
        example.createCriteria();
        example.setOrderByClause("SECTION, KEY ASC");
        List<GuiColor> guiColorList = guiColorMapper.selectByExample(example);

        // エンティティに変換
        List<GuiColorCsvEntity> retEntityList = new ArrayList<GuiColorCsvEntity>();
        for (GuiColor guiColor : guiColorList) {
            GuiColorCsvEntity guiColorEntity = chgGuiCsvModelToEntity(guiColor);
            retEntityList.add(guiColorEntity);
        }

        // 返却
        return retEntityList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysPara情報取得機能
     * @param     key            取得したいMCS_CONSTSテーブルのKEY値
     * @return    システムパラメータエンティティ
     * @retval
     * @attention
     * @note      MCS_CONSTSテーブルより、指定されたKEYのレコードを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public McsConstsEntity getSysInfo(String key) {

        // 引数チェック（Nullか空文字ならNullを返す）
        if (key == null || key.length() <= 0) {
            return null;
        }

        // データ取得
        McsConsts mcsConsts = mcsConstsMapper.selectByPrimaryKey(key);

        // システムパラメータ情報が取得できなかった場合はNullを返す
        if (mcsConsts == null) {
            return null;
        }

        // エンティティに変換
        McsConstsEntity mcsConstsEntity = chgSysModelToEntity(mcsConsts);

        // 返却
        return mcsConstsEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラー情報取得機能
     * @param     section        取得したいGUI_COLORテーブルのSECTION値
     * @param     key            取得したいGUI_COLORテーブルのKEY値
     * @return    システムパラメータエンティティ
     * @retval
     * @attention
     * @note      GUI_COLORテーブルより、指定されたSECTION.KEYのレコードを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public GuiColorEntity getGuiInfo(String section, String key) {

        // 引数チェック（Nullか空文字ならNullを返す）
        if (section == null || section.length() <= 0 || key == null || key.length() <= 0) {
            return null;
        }
        GuiColorKey guiColorKey = new GuiColorKey();
        guiColorKey.setSection(section);
        guiColorKey.setKey(key);

        // データ取得
        GuiColor guiColor = guiColorMapper.selectByPrimaryKey(guiColorKey);

        // GUIカラー情報が取得できなかった場合はNullを返す
        if (guiColor == null) {
            return null;
        }

        // エンティティに変換
        GuiColorEntity guiColorEntity = chgGuiModelToEntity(guiColor);

        // 返却
        return guiColorEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysPara情報修正機能
     * @param     mscConstsEntity      画面項目情報
     * @return
     * @retval
     * @attention
     * @note      指定されたMCS_CONSTS情報にて、MCS_CONSTSテーブルに修正する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    @MsgCacheUpdate(m_strCacheIdList = { ComConst.CacheId.MCS_CONSTS })
    public void modSysInfo(ReqMcsConstsEntity mscConstsEntity) throws McsException {

        // PKでデータ取得
        McsConsts mcsConsts = mcsConstsMapper.selectByPrimaryKey(mscConstsEntity.key);

        // 更新対象が存在しない場合はエラーとする
        if (mcsConsts == null) {
            String errorMessage = messageSource.getMessage("ERR0015",
                    new String[] { "MCS_CONSTS.KEY=" + mscConstsEntity.key }, "ERR0015",
                    LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // 設定値チェック(UNIT種別"Text"はチェックなし)
        if (!(mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_TEXT))) {

            // チェックタイプ判定（All:上限+下限、Upper:上限のみ、Lower:下限のみ、None：上限・下限なし）
            String checkType = null;
            if ((mscConstsEntity.upperLimit != null) && (mscConstsEntity.lowerLimit != null)) {
                checkType = "All";
            } else if (mscConstsEntity.upperLimit != null) {
                checkType = "Upper";
            } else if (mscConstsEntity.lowerLimit != null) {
                checkType = "Lower";
            } else {
                checkType = "None";
            }

            // チェック処理
            Boolean valueCheckFlg = true; // 設定値チェックフラグ
            Boolean NumRangeCheckFlg = true; // 数値型妥当性チェックフラグ
            // -- UNIT種別"HH:MM:SS"のチェック（上限/下限チェックはしない。設定値のフォーマットおよび時刻妥当性チェックのみ実施）
            if (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_TIME)) {
                // --- チェック結果取得(エラー時はfalseをセット)
                valueCheckFlg = valueCheckTime(mscConstsEntity.value);
                // -- 上記以外のUNIT種別のチェック（上限/下限チェックおよび数値妥当性チェック
            } else if ((mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_SEC))
                    || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_MIN))
                    || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_DAY))
                    || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_NUMBER))) {
                // --- 上限、下限パラメータ値セット（未設定の場合は0セット）
                Long upperLimit = Long.parseLong("0");
                Long lowerLimit = Long.parseLong("0");
                if (checkType.equals("Lower")) {
                    lowerLimit = mscConstsEntity.lowerLimit;
                }
                if (checkType.equals("Upper")) {
                    upperLimit = mscConstsEntity.upperLimit;
                }
                if (checkType.equals("All")) {
                    lowerLimit = mscConstsEntity.lowerLimit;
                    upperLimit = mscConstsEntity.upperLimit;
                }

                // --- 数値型設定値のLong型妥当性チェック(エラー時はfalseをセット)
                try {
                    Long valueNum = Long.parseLong(mscConstsEntity.value);
                } catch (Exception e) {
                    valueCheckFlg = false;
                    NumRangeCheckFlg = false;
                }
                // --- 数値型設定値のマイナス値チェック（UNIT種別"number"以外はマイナス値不可）
                if (valueCheckFlg.equals(true)) {
                    if (!(mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_NUMBER))) {
                        Long valueNum = Long.parseLong(mscConstsEntity.value);
                        if (valueNum < 0) {
                            valueCheckFlg = false;
                        }
                    }
                }
                // --- チェック結果取得(エラー時はfalseをセット)
                if (valueCheckFlg.equals(true)) {
                    if (!(checkType.equals("None"))) {
                        valueCheckFlg = valueCheckNum(checkType, mscConstsEntity.unit, mscConstsEntity.value,
                                upperLimit, lowerLimit);
                    }
                }
            }
            // チェックエラー時処理
            if (valueCheckFlg == false) {
                String errMessage = null;
                // -- UNIT種別"HH:MM:SS"
                if (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_TIME)) {
                    errMessage = messageSource.getMessage("ERR0062", null, "ERR0062", LocaleContextHolder.getLocale());
                    // -- 上記以外のUNIT種別
                } else {
                    // --- 数値型設定値の不値エラー（数値型でない、Long型の最大値を超えている等）
                    if (NumRangeCheckFlg == false) {
                        errMessage = messageSource.getMessage("ERR0063", null, "ERR0063",
                                LocaleContextHolder.getLocale());
                        // --- 範囲チェックエラー
                    } else if (checkType.equals("All")) {
                        errMessage = messageSource.getMessage("ERR0047",
                                new String[] { String.valueOf(mscConstsEntity.lowerLimit),
                                        String.valueOf(mscConstsEntity.upperLimit) },
                                "ERR0047", LocaleContextHolder.getLocale());
                        // --- 上限チェックエラー
                    } else if (checkType.equals("Upper")) {
                        errMessage = messageSource.getMessage("ERR0061",
                                new String[] { String.valueOf(mscConstsEntity.upperLimit) }, "ERR0061",
                                LocaleContextHolder.getLocale());
                        // --- 下限チェックエラー
                    } else if (checkType.equals("Lower")) {
                        errMessage = messageSource.getMessage("ERR0060",
                                new String[] { String.valueOf(mscConstsEntity.lowerLimit) }, "ERR0060",
                                LocaleContextHolder.getLocale());
                        // --- 上限、下限なしでのチェックエラー
                    } else if (checkType.equals("None")) {
                        errMessage = messageSource.getMessage("ERR0062", null, "ERR0062",
                                LocaleContextHolder.getLocale());
                    }
                }
                // -- エラー出力（エラーメッセージ未設定の場合は出力しない）
                if (errMessage != null) {
                    throw new McsException(errMessage);
                }
            }
        }

        // ホストエンティティより、ホストモデルを更新しUpdateを行う。
        // Value、description以外は更新廃止
        // mcsConsts.setSection(mscConstsEntity.section);
        // 設定値のセット
        // -- 数値型の設定値は前ゼロがあれば除去してセット
        if ((mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_SEC))
                || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_MIN))
                || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_DAY))
                || (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_NUMBER))) {
            Long valueEntityNum = Long.parseLong(mscConstsEntity.value);
            mcsConsts.setValue(String.valueOf(valueEntityNum));
            // -- 時刻型の設定値は2桁で前ゼロ埋めしてセット（フォーマットおよび時刻値の妥当性についてはチェック済み前提）
        } else if (mscConstsEntity.unit.equals(ComConst.SystemParameter.Unit.UNIT_TIME)) {
            String[] strValue = new String[3];
            Short[] shortValue = new Short[3];
            strValue = mscConstsEntity.value.split(":");
            for (int i = 0; i < strValue.length; i++) {
                shortValue[i] = Short.parseShort(strValue[i]);
                strValue[i] = String.format("%02d", shortValue[i]);
            }
            mcsConsts.setValue(strValue[0] + ":" + strValue[1] + ":" + strValue[2]);
            // -- 上記以外の設定値はそのままセット
        } else {
            mcsConsts.setValue(mscConstsEntity.value);
        }
        mcsConsts.setDescription(mscConstsEntity.description);
        // Value、description以外は更新廃止
        // mcsConsts.setUnit(mscConstsEntity.unit);
        // mcsConsts.setLowerLimit(mscConstsEntity.lowerLimit);
        // mcsConsts.setUpperLimit(mscConstsEntity.upperLimit);

        int updateCount = mcsConstsMapper.updateByPrimaryKey(mcsConsts);

        // 更新件数が0件の場合はエラーとする
        if (updateCount == 0) {
            String errorMessage = messageSource.getMessage("ERR0015",
                    new String[] { "MCS_CONSTS.KEY=" + mscConstsEntity.key }, "ERR0015",
                    LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラー情報修正機能
     * @param     guiColorEntity      画面項目情報
     * @return
     * @retval
     * @attention
     * @note      指定されたGUI_COLOR情報にて、GUI_COLORテーブルに修正する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public void modGuiInfo(ReqGuiColorEntity guiColorEntity) throws McsException {

        // PKでデータ取得
        GuiColorKey guiColorKey = new GuiColorKey();
        guiColorKey.setSection(guiColorEntity.section);
        guiColorKey.setKey(guiColorEntity.key);
        GuiColor guiColor = guiColorMapper.selectByPrimaryKey(guiColorKey);

        // 更新対象が存在しない場合はエラーとする
        if (guiColor == null) {
            String errorMessage = messageSource
                    .getMessage("ERR0015",
                            new String[] { "GUI_COLOR.SECTION=" + guiColorEntity.section + ComConst.BR
                                    + "GUI_COLOR.KEY=" + guiColorEntity.key },
                            "ERR0015", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }

        // ホストエンティティより、ホストモデルを更新しUpdateを行う。
        Short[] rgb = hex2short(guiColorEntity.color);
        guiColor.setRgbRed(rgb[0]);
        guiColor.setRgbGreen(rgb[1]);
        guiColor.setRgbBlue(rgb[2]);
        guiColor.setObject(guiColorEntity.object);
        guiColor.setDescription(guiColorEntity.description);

        int updateCount = guiColorMapper.updateByPrimaryKey(guiColor);

        // 更新件数が0件の場合はエラーとする
        if (updateCount == 0) {
            String errorMessage = messageSource
                    .getMessage("ERR0015",
                            new String[] { "GUI_COLOR.SECTION=" + guiColorEntity.section + ComConst.BR
                                    + "GUI_COLOR.KEY=" + guiColorEntity.key },
                            "ERR0015", LocaleContextHolder.getLocale());
            throw new McsException(errorMessage);
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SysParaモデルからエンティティ変換機能
     * @param     mcsConsts      mcsConstsモデル
     * @return    McsConstsEntity
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private McsConstsEntity chgSysModelToEntity(McsConsts mcsConsts) {

        McsConstsEntity mcsConstsEntity = new McsConstsEntity();

        mcsConstsEntity.category = mcsConsts.getCategory();
        mcsConstsEntity.section = mcsConsts.getSection();
        mcsConstsEntity.key = mcsConsts.getKey();
        mcsConstsEntity.value = mcsConsts.getValue();
        mcsConstsEntity.description = mcsConsts.getDescription();
        mcsConstsEntity.unit = mcsConsts.getUnit();
        if (mcsConsts.getLowerLimit() == null) {
            mcsConstsEntity.lowerLimit = "";
        } else {
            mcsConstsEntity.lowerLimit = mcsConsts.getLowerLimit().toString();
        }
        if (mcsConsts.getUpperLimit() == null) {
            mcsConstsEntity.upperLimit = "";
        } else {
            mcsConstsEntity.upperLimit = mcsConsts.getUpperLimit().toString();
        }
        if (mcsConsts.getDisplayFlag().intValue() == ComConst.BooleanInt.TRUE) {
            mcsConstsEntity.displayFlag = Integer.toString(ComConst.BooleanInt.TRUE);
        } else {
            mcsConstsEntity.displayFlag = Integer.toString(ComConst.BooleanInt.FALSE);
        }

        return mcsConstsEntity;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラーモデルからエンティティ変換機能
     * @param     guiColor      guiColorモデル
     * @return    GuiColorEntity
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private GuiColorEntity chgGuiModelToEntity(GuiColor guiColor) {

        GuiColorEntity guiColorEntity = new GuiColorEntity();

        guiColorEntity.section = guiColor.getSection();
        guiColorEntity.key = guiColor.getKey();
        if (guiColor.getObject() == null) {
            guiColorEntity.object = "";
        } else {
            guiColorEntity.object = guiColor.getObject().toString();
        }
        guiColorEntity.description = guiColor.getDescription();

        String colorCode = "#" + short2Hex(guiColor.getRgbRed()) + short2Hex(guiColor.getRgbGreen())
                + short2Hex(guiColor.getRgbBlue());
        guiColorEntity.color.bgcolor = colorCode;

        return guiColorEntity;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUIカラーモデルからエンティティ変換機能（CSV用）
     * @param     guiColor      guiColorモデル
     * @return    McsConstsEntity
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private GuiColorCsvEntity chgGuiCsvModelToEntity(GuiColor guiColor) {

        GuiColorCsvEntity guiColorEntity = new GuiColorCsvEntity();

        guiColorEntity.section = guiColor.getSection();
        guiColorEntity.key = guiColor.getKey();
        if (guiColor.getObject() == null) {
            guiColorEntity.object = "";
        } else {
            guiColorEntity.object = guiColor.getObject().toString();
        }
        guiColorEntity.description = guiColor.getDescription();

        String colorCode = "#" + short2Hex(guiColor.getRgbRed()) + short2Hex(guiColor.getRgbGreen())
                + short2Hex(guiColor.getRgbBlue());
        guiColorEntity.color = colorCode;

        return guiColorEntity;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Short型より16進2桁に変換
     * @param     value      Short型
     * @return    16進（String）
     * @retval
     * @attention null,マイナス時は00を、256以上はffを返却
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String short2Hex(Short value) {

        // Nullの場合はx00を返却
        if (value == null) {
            return "00";
        }

        // マイナスの場合はx00を返却
        if (value.intValue() < 0) {
            return "00";
        }

        // 256以上の場合はxffを返却
        if (value.intValue() < 0) {
            return "ff";
        }

        // 上記以外は16進に変換する。
        String hex = String.format("%02x", value.intValue());

        return hex;

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     カラーコード(#ffffff)をShort[3]に変換
     * @param     value      カラーコード
     * @return    Short型の配列
     * @retval    Red[0] Green[1] Blue[2]で格納
     * @attention デコード失敗時にはMcsExceptionを発生させる
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Short[] hex2short(String value) throws McsException {

        Short[] rgb = new Short[3];

        // 想定しない文字列の場合はエラー
        if (value == null || value.length() != 7) {
            String[] args = { value };
            String errMessage = messageSource.getMessage("ERR0027", args, "ERR0027", LocaleContextHolder.getLocale());
            throw new McsException(errMessage);
        }

        // Red[0] Green[1] Blue[2]
        // 16進にDecodeできなければエラー
        try {
            rgb[0] = Short.decode("0x" + value.substring(1, 3));
            rgb[1] = Short.decode("0x" + value.substring(3, 5));
            rgb[2] = Short.decode("0x" + value.substring(5, 7));
        } catch (NumberFormatException e) {
            String[] args = { value };
            String errMessage = messageSource.getMessage("ERR0027", args, "ERR0027", LocaleContextHolder.getLocale());
            throw new McsException(errMessage, e);
        }

        return rgb;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     時刻型設定値のチェック
     * @param     value      設定値
     * 
     * @return    Boolean(チェックOK:true、チェックエラー:false）
     * @retval    
     * @attention チェックエラー時にはfalseを返す。
     * @note      ・規定フォーマットと時刻の妥当性のみチェックとする。（上限値、下限値はチェックしない。）
     *            ・設定値が未入力の場合はチェックは行わない。
     *            ・各時刻値の余分な前ゼロ、および1桁で前ゼロなしは更新時に対応するので考慮しない。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Boolean valueCheckTime(String value) {

        if (value != null) {
            // 設定値チェック用カレンダー型日付
            Calendar valueDate = Calendar.getInstance();

            // 設定値を時・分・秒に分割してカレンダークラスへセット
            Short[] parTimeValue = new Short[3];
            try {
                String[] tempValueStr;
                // --- 要素数が3個でなければエラー
                tempValueStr = value.split(":");
                if (tempValueStr.length != 3) {
                    return false;
                }
                for (int i = 0; i < tempValueStr.length; i++) {
                    parTimeValue[i] = Short.parseShort(tempValueStr[i]);
                }
                // 取得値をカレンダークラスへセット
                valueDate.set(valueDate.HOUR_OF_DAY, parTimeValue[0]);
                valueDate.set(valueDate.MINUTE, parTimeValue[1]);
                valueDate.set(valueDate.SECOND, parTimeValue[2]);
            } catch (Exception e) {
                return false;
            }

            // 時刻妥当性チェック
            valueDate.setLenient(false);
            try {
                valueDate.getTime();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     数値型設定値のチェック
     * @param     checkType  チェックパターン（All:上限+下限、Upper:上限のみ、Lower:下限のみ）
     * @param     value      設定値
     * @param     unit       UNIT種別（現状は未使用）
     * @param     upperLimit 上限値
     * @param     lowerLimit 下限値
     * 
     * @return    エラーフラグ
     * @retval    
     * @attention チェックエラー時にはfalseを返す。
     * @note      ・上限値、下限値は正しいフォーマットで設定されている前提のためチェックは実施しない。
     *            ・設定値が未入力の場合はチェックは行わない。
     *            ・前ゼロは別途除去するので考慮しない。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Boolean valueCheckNum(String checkType, String unit, String value, long upperLimit, long lowerLimit) {

        if (value != null) {
            // 設定値パラメータの数値変換
            Long valueNum;
            try {
                valueNum = Long.parseLong(value);
            } catch (Exception e) {
                return false;
            }
            // 上限、下限、範囲チェック
            // -- 上限チェック
            if (!(checkType.equals("Lower"))) {
                if (valueNum > upperLimit) {
                    return false;
                }
            }
            // -- 下限チェック
            if (!(checkType.equals("Upper"))) {
                if (valueNum < lowerLimit) {
                    return false;
                }
            }
        }
        return true;
    }
}
