//@formatter:off
/**
 ******************************************************************************
 * @file        TransferLocationService.java
 * @brief       ロケーションサービス
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
 * 2019/04/22 MACS4#0160  IFOHB表示非表示対応                         T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.common.TransferLocationEntity;
import net.muratec.mcs.entity.common.TransferLocationSelectEntity;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.model.EqpType;

//@formatter:off
/**
 ******************************************************************************
 * @brief     搬送ロケーションサービスクラス
 * @par       機能:
 *              getTransferLocationList（ロケーション情報取得処理）
 *              getAliasList（エイリアス情報取得処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class TransferLocationService extends BaseService {
    
    @Autowired private TscMapper tscMapper;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ロケーション情報取得処理
     * @param     location          ロケーションId
     * @return    ロケーション情報リスト
     * @retval
     * @attention
     * @note      ロケーションIdからロケーション情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    
    @Transactional(readOnly = true)
    public List<TransferLocationEntity> getEqpTypeBox(short srcDstFlg) {
        
        List<TransferLocationEntity> body = new ArrayList<TransferLocationEntity>();
        TransferLocationEntity retLocation = new TransferLocationEntity();
        List<TransferLocationSelectEntity> retList = new ArrayList<TransferLocationSelectEntity>();
        List<EqpType> vaList = new ArrayList<EqpType>();
        if (srcDstFlg==1) {  //源
            vaList = tscMapper.selectSrcEqpTypeList();
        } else if (srcDstFlg==2){        //目的
            vaList = tscMapper.selectDstEqpTypeList();           
        } else {
            return body;
        }
        for (EqpType va : vaList) {
            TransferLocationSelectEntity retData = new TransferLocationSelectEntity();
            retData.text = va.getEqpTxt();
            retData.value = va.getEqpValue();
            retList.add(retData);
        }
        retLocation.data = retList;
        body.add(retLocation);
        return body;
    }
 
    @Transactional(readOnly = true)
    public List<TransferLocationEntity> getTransferLocationList(short srcDstFlg, String con) {

        List<TransferLocationEntity> body = new ArrayList<TransferLocationEntity>();
        TransferLocationEntity retLocation = new TransferLocationEntity();
        List<TransferLocationSelectEntity> retList = new ArrayList<TransferLocationSelectEntity>();
        List<EqpType> vaList = new ArrayList<EqpType>();
        if (srcDstFlg==1) {  //源
            vaList = tscMapper.selectSrcControllerList(con);
        } else if (srcDstFlg==2){        //目的
            vaList = tscMapper.selectDstControllerList(con);           
        } else {
            return body;
        }
        for (EqpType va : vaList) {
            TransferLocationSelectEntity retData = new TransferLocationSelectEntity();
            retData.text = va.getEqpTxt();
            retData.value = va.getEqpValue();
            retList.add(retData);
        }
        retLocation.data = retList;
        body.add(retLocation);
        return body;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     エイリアス情報取得処理
     * @param     location          ロケーションId
     * @return    エイリアス情報リスト
     * @retval
     * @attention
     * @note      IDからエイリアス情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<TransferLocationEntity> getAliasList(short srcDstFlg, String con) {

        List<TransferLocationEntity> body = new ArrayList<TransferLocationEntity>();
        TransferLocationEntity retLocation = new TransferLocationEntity();
        List<TransferLocationSelectEntity> retList = new ArrayList<TransferLocationSelectEntity>();
        List<EqpType> vaList = new ArrayList<EqpType>();
        if (srcDstFlg==1) {  //源
            vaList = tscMapper.selectSrcPortList(con);
        } else if (srcDstFlg==2){        //目的
            vaList = tscMapper.selectDstPortList(con);           
        } else {
            return body;
        }
        for (EqpType va : vaList) {
            TransferLocationSelectEntity retData = new TransferLocationSelectEntity();
            retData.text = va.getEqpTxt();
            retData.value = va.getEqpValue();
            retList.add(retData);
        }
        retLocation.data = retList;
        body.add(retLocation);
        return body;

    }
}
