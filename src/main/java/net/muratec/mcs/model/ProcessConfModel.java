//@formatter:off
/**
 ******************************************************************************
 * @file        ProcessConfModel.java
 * @brief       プロセス構成マスタメンテナンス一覧取得用モデル
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
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     プロセス構成マスタメンテナンス一覧取得用モデルクラス
 * @par       機能:
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ProcessConfModel {

    private Integer systemId;
    private String serviceId;
    private String name;
    private String procType;
    private Integer telnetPort;
    private Short method;
    private Integer ruleNo;
    private Short useFlag;
    private String abbreviation;
    private String exePath;
    private String argument;

}
