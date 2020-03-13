/**
 ******************************************************************************
 * @file        mcs-ComConst.js
 * @brief       定数管理クラス
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 * 2019/01/25 MACS4#0092  IFOHB追加機能(GUI)                          T.Iga/CSC
 ******************************************************************************
 */

// 定数
// AMHSタイプ
const AMHS_TYPE = {
    STC:    1,
    TSTC:  21,
    RSBC:  25,
    LFC:   31,
    DWCC:  41,
    OPC:   51,
    OHBC:  61,
    VOHBC: 65,
    OHVC:  81,
    VLFT:  85
};

// ポートタイプ
const PORT_TYPE = {
    PGV:      1,
    OPE:      2,
    EQP:     11,
    LINK:    21,
    DCT:     22,
    OHB:     71,
    TBP:     75,
    VEH:     95,
    VIRTUAL: 99
};

// フロア
const FLOOR = {
    THIRD_FLOOR: 3
};

// MACS4#0092 Add Start
// OHBGrタイプ
const OHB_TYPE = {
    OHBGR   : 0,
    IFOHBGR : 1
};
// MACS4#0092 Add End
