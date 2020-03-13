//@formatter:off
/**
 ******************************************************************************
 * @file        GuiColorCsvEntity.java
 * @brief       システムパラメータマスタメンテ GuiカラーCSV連携用エンティティ
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
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムパラメータマスタメンテ GuiカラーCSV連携用エンティティクラス
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
public class GuiColorCsvEntity {

    public String section = "";
    public String key = "";

    public String color = "";
    public String object = "";
    public String description = "";

}
