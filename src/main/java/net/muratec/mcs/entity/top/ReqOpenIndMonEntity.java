//@formatter:off
/**
 ******************************************************************************
 * @file        ReqOpenIndMonEntity.java
 * @brief       個別モニタ表示用リクエストエンティティ
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
package net.muratec.mcs.entity.top;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief    個別モニタ表示用リクエストエンティティクラス
 * @par
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2019/12/18 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqOpenIndMonEntity {

//    public String amhsType;//20191218 DQY DEL
//    public String amhsId;	 //20191218 DQY DEL
    public String memberGroup;  //20191218 DQY ADD
    public String displayName;  //20191218 DQY ADD
    public String displayId;  //20191220 DQY ADD

}
