//@formatter:off
/**
 ******************************************************************************
 * @file        ReqLocationEntity.java
 * @brief      Locationリストリクエストエンティティ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 * 2018/11/26 MACS4#0047  GUI要望分                                   T.Iga/CSC
 * 2019/04/22 MACS4#0160  IFOHB表示非表示対応                         T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.entity.common;

import lombok.Getter;
import lombok.Setter;

//@formatter:off
/**
 ******************************************************************************
 * @brief     Locationリストリクエストエンティティ
 * @par       Locationリストリクエストエンティティクラス。
 * @attention 
 * @note      
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 0.1         Step1リリース                                                CSC
 * MACS4#0047  GUI要望分                                              T.Iga/CSC
 * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Getter
@Setter
public class ReqLocationEntity extends AjaxReqBaseEntity {

    public Short location = 0;
    public String id = null;
    public Boolean mode = false;
    public Boolean macroMode = false;   // MACS4#0047 Add
    public Boolean findAmhsId = false;  // MACS4#0047 Add
    public Boolean dispIFOhb = false;   // MACS4#0160 Add
}
