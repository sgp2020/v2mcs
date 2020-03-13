//@formatter:off
/**
 ******************************************************************************
 * @file        ProcessConfEntity.java
 * @brief       プロセス構成マスタメンテナンス プロセス情報格納エンティティ
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
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     プロセス構成マスタメンテナンス プロセス情報格納エンティティクラス
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
public class ProcessConfEntity {

    public Integer systemId;
    public String serviceId;
    public String name;
    public String procType;
    public Integer telnetPort;
    public String method;
    public Integer ruleNo;
    public Boolean useFlag;
    public String abbreviation;
    public String exePath;
    public String argument;

}
