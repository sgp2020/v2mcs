//@formatter:off
/**
 ******************************************************************************
 * @file        PortsService.java
 * @brief       アラーム情報表示画面のサービス
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

 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.hist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.hist.MacroDataListEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.mapper.MacroTransferLogMapper;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     MacroData情報表示画面のサービスクラス
 * @par       機能:
 *              getCount(MacroData情報一覧の全レコード数を取得)
 *              getMacroDataList(MacroData情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 20200402      MacroData                                              DONG
 ******************************************************************************
 */
//@formatter:on
@Service
public class MacroDataService extends BaseService {

    // ------------------------------------
    // アラーム情報マッパー
    // ------------------------------------
    @Autowired private MacroTransferLogMapper macroTransferLogMapper;

    // ------------------------------------
    // メッセージ送受信サービス - MACS4#0047 Add
    // ------------------------------------
    // @Autowired private MsgUtilService msgUtilService;

    // ------------------------------------
    // メッセージフォーマットプロパティ - MACS4#0047 Add
    // ------------------------------------
    // @Autowired private Properties m_formatProperties;
 
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount(アラーム情報一覧の全レコード数を取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetMacroDataEntity reqEntity) {

        return macroTransferLogMapper.getCount(reqEntity);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getPortsList(アラーム情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    アラーム情報一覧
     * @retval    アラーム情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0114  GUI MCSPortsクリア対応                                 T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<MacroDataListEntity> getMacroDataList(ReqGetMacroDataEntity reqEntity) {

    	// -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<MacroDataListEntity> retRecList = new ArrayList<MacroDataListEntity>();
        
        List<MacroTransferLog> macroTransferLog = macroTransferLogMapper.selectMacroDataList(reqEntity);
        
        if (macroTransferLog == null ) {
        	return retRecList;
        }
        
        int rowNum = 1;
	 	for (MacroTransferLog macroTransferLogRec : macroTransferLog) {
	 		MacroDataListEntity retRec = new MacroDataListEntity();
	 		
	 		String orgSrcTscId = String.valueOf(macroTransferLogRec.getOrgSrcTscId());
	 		List<Tsc> getSrcTscName = macroTransferLogMapper.setTscName(orgSrcTscId);
	 		
	 		String orgDstTscId = String.valueOf(macroTransferLogRec.getOrgDstTscId());
	 		List<Tsc> getDstTscName = macroTransferLogMapper.setTscName(orgDstTscId);
	 		
	 		String altTscId = String.valueOf(macroTransferLogRec.getAltTscId());
	 		List<Tsc> getAltTscName = macroTransferLogMapper.setTscName(altTscId);
	 		
	 		retRec.rum = rowNum;
	 		retRec.time = macroTransferLogRec.getTime();
	 		retRec.orgCarrierId = macroTransferLogRec.getOrgCarrierId();
	 		retRec.orgRcvTime = macroTransferLogRec.getOrgRcvTime();
	 		retRec.orgStartTime = macroTransferLogRec.getOrgStartTime() ;
            retRec.orgCmpTime = macroTransferLogRec.getOrgCmpTime() ;
//            retRec.orgSrcTscId = macroTransferLogRec.getOrgSrcTscId() ;
            retRec.orgSrcTscId = getSrcTscName.get(0).getTscAbbreviation();
            retRec.orgSrcLoc = macroTransferLogRec.getOrgSrcLoc() ;
//            retRec.orgDstTscId = macroTransferLogRec.getOrgDstTscId() ;
            retRec.orgDstTscId = getDstTscName.get(0).getTscAbbreviation();
            retRec.orgDstLoc = macroTransferLogRec.getOrgDstLoc() ;
//            retRec.altTscId = macroTransferLogRec.getAltTscId() ;
            retRec.altTscId = getAltTscName.get(0).getTscAbbreviation();
            retRec.altLoc = macroTransferLogRec.getAltLoc() ;
            retRec.status = macroTransferLogRec.getStatusStr() ;
            retRec.orgPriority = macroTransferLogRec.getOrgPriority() ;
            retRec.cancelFlg = macroTransferLogRec.getCancelFlg() ;
            retRec.orgDstGroup = macroTransferLogRec.getOrgDstGroup() ;
            retRec.orgHostCommandId = macroTransferLogRec.getOrgHostCommandId() ;
            retRec.orgCommandId = macroTransferLogRec.getOrgCommandId() ;
            retRec.orgOriginator = macroTransferLogRec.getOrgOriginator() ;
            retRec.rerouteReq = macroTransferLogRec.getRerouteFlg() ;
	 		
	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

        return retRecList;
    }

}
