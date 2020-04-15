//@formatter:off
/**
 ******************************************************************************
 * @file        SystemLogService.java
 * @brief       個別モニタ(SCモニタ)関連のサービス
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
package net.muratec.mcs.service.hist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.hist.AtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.MacroDataListEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AtomicTransferLogMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のサービス
 * @par       機能:
 *              getSystemLog（Systemlog一覧取得（LIST））
 *              getSystemLogCount（）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class SystemLogService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    // STD APL 2020.03.26 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    @Autowired private AtomicTransferLogMapper atomicTransferLogMapper;
    // END APL 2020.03.26 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     メイン画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    Host情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311		Host情報										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<AtomicActivityHistListEntity> getAtomicActivityHistList(ReqGetAtomicActivityHistEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<AtomicActivityHistListEntity> retRecList = new ArrayList<AtomicActivityHistListEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
//        List<Host> host = hostMapper.selectHostCommInfoList(reqEntity);
        List<AtomicTransferLog> atomicTransferLog = atomicTransferLogMapper.selectAtomicActivityHistList(reqEntity);
        
        if (atomicTransferLog == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (AtomicTransferLog atomicTransferLogRec : atomicTransferLog) {
	 		AtomicActivityHistListEntity retRec = new AtomicActivityHistListEntity();

	 		retRec.rum = rowNum;
	 		retRec.time = atomicTransferLogRec.getTime();
	 		retRec.carrierId = atomicTransferLogRec.getCarrierId();
	 		retRec.tscAbbreviation = atomicTransferLogRec.getTscAbbreviation();
	 		retRec.source = atomicTransferLogRec.getSrcLoc();
	 		retRec.destination = atomicTransferLogRec.getDstLoc();
	 		retRec.statusStr = atomicTransferLogRec.getStatusStr();
	 		retRec.priority = atomicTransferLogRec.getPriority();
	 		retRec.routeNo = atomicTransferLogRec.getRouteNo();
	 		retRec.queuedTime = atomicTransferLogRec.getQueuedTime();
	 		retRec.leadTime = atomicTransferLogRec.getLeadTime();
	 		retRec.totalTime = atomicTransferLogRec.getTotalTime();
	 		retRec.vehicleId = atomicTransferLogRec.getVehicleId();
	 		retRec.commandId = atomicTransferLogRec.getCommandId();
	 		retRec.atomicRequestTime = atomicTransferLogRec.getAtomicReqTime();
	 		retRec.atomicAnswerTime = atomicTransferLogRec.getAtomicAnsTime();
	 		retRec.atomicInitiateTime = atomicTransferLogRec.getAtomicIniTime();
	 		retRec.atomicAcquiredTime = atomicTransferLogRec.getAtomicAcqTime();
	 		retRec.atomicCompleteTime = atomicTransferLogRec.getAtomicCmpTime();
	 		retRec.abortRequestTime = atomicTransferLogRec.getAbortReqTime();
	 		retRec.abortAnswerTime = atomicTransferLogRec.getAbortAnsTime();
	 		retRec.abortInitiateTime = atomicTransferLogRec.getAbortIniTime();
	 		retRec.abortCompleteTime = atomicTransferLogRec.getAbortCmpTime();
	 		retRec.abortReason = atomicTransferLogRec.getAbortReason();
	 		
	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTscIdBox
     *            （TSCのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200326   getTscIdBox										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getTscIdBox() {
    	
    	List<Tsc> tscIdList = atomicTransferLogMapper.selectTscNameList();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Tsc tscIds : tscIdList) {
            String[] data = new String[2];
            data[0] = String.valueOf(tscIds.getTscId());
            data[1] = tscIds.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStkData
     *            （tscのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200326   getStkData										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getStkData() {
    	
    	List<Tsc> tscIdList = atomicTransferLogMapper.selectStkData();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Tsc tscIds : tscIdList) {
            String[] data = new String[2];
            data[0] = String.valueOf(tscIds.getTscId());
            data[1] = tscIds.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
 
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOhbData
     *            （OHBのOHB_IDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200326   getOhbData										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getOhbData() {
    	
    	List<Ohb> ohbIdList = atomicTransferLogMapper.selectOhbData();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Ohb ohbIds : ohbIdList) {
            String[] data = new String[2];
            data[0] = ohbIds.getOhbId();
            data[1] = ohbIds.getOhbId();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     getPortData
     *            （PORTのOPORT_IDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200326   getPortData										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getPortData() {
    	
    	List<Port> porTIdList = atomicTransferLogMapper.selectPortData();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Port porIds : porTIdList) {
            String[] data = new String[2];
            data[0] = porIds.getPortId();
            data[1] = porIds.getPortAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     getHostommInfoCount（ほＳＴ_IDを指定し、合致する空FOUPレコード件数を取得する）機能
     * @param     controllerId    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      TSC_IDを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311   getStockerInfoCount										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getAtomicActivityHistCount(ReqGetAtomicActivityHistEntity record) {

        int ret = 0;
        ret = (int) atomicTransferLogMapper.getCount(record);
        return ret;
    }
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     MacroData画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    MacroData情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200402				MacroData情報										DONG
     ******************************************************************************
     */
    //@formatter:on
    /*@Transactional(readOnly = true)
    public List<MacroDataListEntity> getMacroDataList(ReqGetMacroDataEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<MacroDataListEntity> retRecList = new ArrayList<MacroDataListEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
        List<MacroTransferLog> macroTransferLog = atomicTransferLogMapper.selectMacroDataList(reqEntity);
        
        if (macroTransferLog == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (MacroTransferLog macroTransferLogRec : macroTransferLog) {
	 		MacroDataListEntity retRec = new MacroDataListEntity();

	 		retRec.rum = rowNum;
	 		retRec.time = macroTransferLogRec.getTime();
	 		retRec.orgCarrierId = macroTransferLogRec.getOrgCarrierId();
	 		retRec.orgRcvTime = macroTransferLogRec.getOrgRcvTime();
	 		retRec.orgStartTime = macroTransferLogRec.getOrgStartTime() ;
            retRec.orgCmpTime = macroTransferLogRec.getOrgCmpTime() ;
            retRec.orgSrcTscId = macroTransferLogRec.getOrgSrcTscId() ;
            retRec.orgSrcLoc = macroTransferLogRec.getOrgSrcLoc() ;
            retRec.orgDstTscId = macroTransferLogRec.getOrgDstTscId() ;
            retRec.orgDstLoc = macroTransferLogRec.getOrgDstLoc() ;
            retRec.altTscId = macroTransferLogRec.getAltTscId() ;
            retRec.altLoc = macroTransferLogRec.getAltLoc() ;
            retRec.status = macroTransferLogRec.getStatus() ;
            retRec.orgPriority = macroTransferLogRec.getOrgPriority() ;
            retRec.cancelFlg = macroTransferLogRec.getCancelFlg() ;
            retRec.orgDstGroup = macroTransferLogRec.getOrgDstGroup() ;
            retRec.time = macroTransferLogRec.getTime() ;
            retRec.orgHostCommandId = macroTransferLogRec.getOrgHostCommandId() ;
            retRec.orgCommandId = macroTransferLogRec.getOrgCommandId() ;
            retRec.orgOriginator = macroTransferLogRec.getOrgOriginator() ;
            retRec.rerouteReq = macroTransferLogRec.getRerouteFlg() ;
	 		
	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

		return retRecList;
    }*/
}
