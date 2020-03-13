//@formatter:off
/**
 ******************************************************************************
 * @file        IFOhbSelectModel.java
 * @brief       IFOHB搬送詳細 セレクトボックスモデル
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
 * 2019/01/28 MACS4#0092  IFOHB追加機能(初版作成)                     T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.model;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     IFOHB搬送詳細 セレクトボックスモデルクラス
 * @par
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
public class IFOhbSelectBoxModel {

    private String ohbId;
    private String ioMode;
}
