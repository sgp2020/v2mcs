//@formatter:off
/**
 ******************************************************************************
 * @file        McsAlarmDisp.java
 * @brief       MCS_ALARMテーブルのモデル
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
package net.muratec.mcs.model;

//@formatter:off
/**
 ******************************************************************************
 * @brief     MCS_ALARMテーブルのモデルクラス
 * @par       機能:
 *              getAlarmId（AlarmIdゲッター機能）
 *              setAlarmId（AlarmIdセッター機能）
 *              getSubCode（SubCodeゲッター機能）
 *              setSubCode（SubCodeセッター機能）
 *              getAlarmTextForm（AlarmTextFormゲッター機能）
 *              setAlarmTextForm（AlarmTextFormセッター機能）
 *              getAlarmLevel（AlarmLevelゲッター機能）
 *              setAlarmLevel（AlarmLevelセッター機能）
 *              getAlarmType（AlarmTypeゲッター機能）
 *              setAlarmType（AlarmTypeセッター機能）
 *              getAmhsType（AmhsTypeゲッター機能）
 *              setAmhsType（AmhsTypeセッター機能）
 *              getAlarmLevelDisp（ArmLevelDispゲッター機能）
 *              setAlarmLevelDisp（AlarmLevelDispセッター機能）
 *              getAlarmTypeDisp（AlarmTypeDispゲッター機能）
 *              setAlarmTypeDisp（AlarmTypeDispセッター機能）
 *              getAmhsTypeDisp（AmhsTypeDispゲッター機能）
 *              setAmhsTypeDisp（AmhsTypeDispセッター機能）
 *              getAmhsAlarmId（AmhsAlarmIdゲッター機能）
 *              setAmhsAlarmId（AmhsAlarmIdセッター機能）
 *              getAlarmName（AlarmNameゲッター機能）
 *              setAlarmName（AlarmNameセッター機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class McsAlarmDisp {

    private Long alarmId;
    private Long subCode;
    private String alarmTextForm;
    private Short alarmLevel;
    private Short alarmType;
    private Short amhsType;
    private String alarmLevelDisp;
    private String alarmTypeDisp;
    private String amhsTypeDisp;
    private Long amhsAlarmId;
    private String alarmName;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmIdゲッター機能
     * @param
     * @return    alarmId
     * @retval
     * @attention
     * @note      alarmIdを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Long getAlarmId() {

        return alarmId;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmIdセッター機能
     * @param     alarmId
     * @return
     * @retval
     * @attention
     * @note      alarmIdを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmId(Long alarmId) {

        this.alarmId = alarmId;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SubCodeゲッター機能
     * @param
     * @return    subCode
     * @retval
     * @attention
     * @note      subCodeを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Long getSubCode() {

        return subCode;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SubCodeセッター機能
     * @param     subCode
     * @return
     * @retval
     * @attention
     * @note      subCodeを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setSubCode(Long subCode) {

        this.subCode = subCode;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTextFormゲッター機能
     * @param
     * @return    alarmTextForm
     * @retval
     * @attention
     * @note      alarmTextFormを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getAlarmTextForm() {

        return alarmTextForm;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTextFormセッター機能
     * @param     alarmTextForm
     * @return
     * @retval
     * @attention
     * @note      alarmTextFormを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmTextForm(String alarmTextForm) {

        this.alarmTextForm = alarmTextForm;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmLevelゲッター機能
     * @param
     * @return    alarmLevel
     * @retval
     * @attention
     * @note      alarmLevelを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Short getAlarmLevel() {

        return alarmLevel;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmLevelセッター機能
     * @param     alarmLevel
     * @return
     * @retval
     * @attention
     * @note      alarmLevelを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmLevel(Short alarmLevel) {

        this.alarmLevel = alarmLevel;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTypeゲッター機能
     * @param
     * @return    alarmType
     * @retval
     * @attention
     * @note      alarmTypeを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Short getAlarmType() {

        return alarmType;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTypeセッター機能
     * @param     alarmType
     * @return
     * @retval
     * @attention
     * @note      alarmTypeを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmType(Short alarmType) {

        this.alarmType = alarmType;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsTypeゲッター機能
     * @param
     * @return    amhsType
     * @retval
     * @attention
     * @note      amhsTypeを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Short getAmhsType() {

        return amhsType;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsTypeセッター機能
     * @param     amhsType
     * @return
     * @retval
     * @attention
     * @note      amhsTypeを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAmhsType(Short amhsType) {

        this.amhsType = amhsType;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ArmLevelDispゲッター機能
     * @param
     * @return    alarmLevelDisp
     * @retval
     * @attention
     * @note      alarmLevelDispを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getAlarmLevelDisp() {

        return alarmLevelDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmLevelDispセッター機能
     * @param     alarmLevelDisp
     * @return
     * @retval
     * @attention
     * @note      alarmLevelDispを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmLevelDisp(String alarmLevelDisp) {

        this.alarmLevelDisp = alarmLevelDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTypeDispゲッター機能
     * @param
     * @return    alarmTypeDisp
     * @retval
     * @attention
     * @note      alarmTypeDispを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getAlarmTypeDisp() {

        return alarmTypeDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmTypeDispセッター機能
     * @param     alarmTypeDisp
     * @return
     * @retval
     * @attention
     * @note      alarmTypeDispを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmTypeDisp(String alarmTypeDisp) {

        this.alarmTypeDisp = alarmTypeDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsTypeDispゲッター機能
     * @param
     * @return    amhsTypeDisp
     * @retval
     * @attention
     * @note      amhsTypeDispを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getAmhsTypeDisp() {

        return amhsTypeDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsTypeDispセッター機能
     * @param     amhsTypeDisp
     * @return
     * @retval
     * @attention
     * @note      amhsTypeDispを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAmhsTypeDisp(String amhsTypeDisp) {

        this.amhsTypeDisp = amhsTypeDisp;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsAlarmIdゲッター機能
     * @param
     * @return    amhsAlarmId
     * @retval
     * @attention
     * @note      amhsAlarmIdを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Long getAmhsAlarmId() {

        return amhsAlarmId;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AmhsAlarmIdセッター機能
     * @param     amhsAlarmId
     * @return
     * @retval
     * @attention
     * @note      amhsAlarmIdを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAmhsAlarmId(Long amhsAlarmId) {

        this.amhsAlarmId = amhsAlarmId;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmNameゲッター機能
     * @param
     * @return    alarmName
     * @retval
     * @attention
     * @note      alarmNameを返却する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String getAlarmName() {

        return alarmName;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AlarmNameセッター機能
     * @param     alarmName
     * @return
     * @retval
     * @attention
     * @note      alarmNameを設定する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setAlarmName(String alarmName) {

        this.alarmName = alarmName;
    }

}
