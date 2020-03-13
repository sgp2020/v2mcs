//@formatter:off
/**
 ******************************************************************************
 * @file        ModuleIdEntity.java
 * @brief       モジュールマスタメンテ モジュールテーブルPKエンティティ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.maint;

import lombok.Getter;
import lombok.Setter;
import net.muratec.mcs.annotation.FieldNameKey;

//@formatter:off
/**
 ******************************************************************************
 * @brief     モジュールマスタメンテ モジュールテーブルPKエンティティクラス
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
public class ModuleIdEntity {

    @FieldNameKey(key = "IM-041-01-003") public String amhsId = "";
    @FieldNameKey(key = "IM-041-01-004") public String moduleId = "";

}
