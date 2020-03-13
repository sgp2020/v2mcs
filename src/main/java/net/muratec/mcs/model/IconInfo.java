//@formatter:off
/**
 ******************************************************************************
 * @file        IconInfo.java
 * @brief       アイコン情報モデル
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 * 2019/12/06 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アイコン情報モデルクラス
 * @par       機能:
 *              isSampleIcon（サンプルアイコンを判定する処理）
 *              isDefinedController（コントローラが定義されているか判定する処理）
 *              isDefinedControllerState（コントローラ状態が定義されているか判定する処理）
 *              isDefinedCommState（通信状態が定義されているか判定する処理）
 *              isDefinedShelfOccupancy（棚占有率が定義されているか判定する処理）
 *
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2019/11/07 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class IconInfo {

    private String position;
    private String text;
    private String amhsId;
    private Short amhsType;
    private Short iconType;
    private Short iconState;
    private Short amhsLState;
//    private Short controlState;// MACS4#MACSV2 Del
//    private Short available;
//    private Short alarmState;
//    private Short commState;
//    private Short systemState;
    private Long notAvailableNum;
    private Long availableNum;
    private BigDecimal zoneUtility;
    private String occupiedCaution;
    private String occupiedAlert;
    private Boolean isDefinedData = true;
    
 // MACS4#MACSV2 Add
    private String displayId;//20191225 DQY ADD
    private String displayName;
    private String memberGroup;
    private Short groupNumber;
    private String tscMode;
    private Short rownum;
    private String controlState;
    private String available;
    private String alarmState;
    private String commState;
    private String systemState;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     サンプルアイコンを判定する処理
     * @param
     * @return    サンプルアイコン判定結果 true: サンプルアイコン
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isSampleIcon() {

        return iconState != null && iconState != 0;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コントローラが定義されているか判定する処理
     * @param
     * @return    定義判定結果 true: 定義されている
     * @retval
     * @attention
     * @note      対象のデータがnullでなければ定義されている
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
     ******************************************************************************
     */
    //@formatter:on
    /*public boolean isDefinedController() {

        return amhsId != null;
    }*/
    public boolean isDefinedController() {

        return displayName != null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     コントローラ状態が定義されているか判定する処理
     * @param
     * @return    定義判定結果 true: 定義されている
     * @retval
     * @attention
     * @note      対象のデータがnullでなければ定義されている
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
     ******************************************************************************
     */
    //@formatter:on
   /* public boolean isDefinedControllerState() {

        return amhsLState != null && controlState != null && available != null && alarmState != null
                && commState != null && systemState != null;
    }*/
    public boolean isDefinedControllerState() {

        /*//20200114 DQY DEL
        return tscMode != null && controlState != null && available != null && alarmState != null
                && commState != null && systemState != null;*/
    	//20200114 DQY MOD FOR DB→CSV
        return tscMode != null && controlState != null && alarmState != null
        		&& commState != null && systemState != null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     通信状態がDBに定義されているか判定する処理
     * @param
     * @return    定義判定結果 true: 定義されている
     * @retval
     * @attention
     * @note      対象のデータがnullでなければ定義されている
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isDefinedCommState() {

        return available != null && controlState != null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     通棚占有率がDBに定義されているか判定する処理
     * @param
     * @return    定義判定結果 true: 定義されている
     * @retval
     * @attention
     * @note      対象のデータがnullでなければ定義されている
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean isDefinedShelfOccupancy() {

        return zoneUtility != null;
    }
}
