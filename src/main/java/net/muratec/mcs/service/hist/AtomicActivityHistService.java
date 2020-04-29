//@formatter:off
/**
 ******************************************************************************
 * @file        AtomicActivityHistService.java
 * @brief       
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/3/18 v1.0.0  	       初版作成                                       								天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.hist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 *              getStockerInfoIdBox（HostのTscIDセレクトボックスリスト取得）
 *              getStockerInfoList（StokerInformation一覧取得（LIST））
 *              getStockerInfoCount（コントローラIDを指定し、合致するStockerレコード件数を取得する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0  	   AtomicActivityHistService      							天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Service
public class AtomicActivityHistService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    // STD APL 2020.03.26 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
    @Autowired private AtomicTransferLogMapper atomicTransferLogMapper;
    // END APL 2020.03.26 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
    /** 時間単位(時間) */
    @Autowired private static final int UNIT_BY_HOUR = 3600;
	
	/** 時間単位(日) */
    @Autowired private static final int UNIT_BY_DAY = 24*3600;
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
     * 2020/3/11		Host情報										天津村研　董
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
	 		if(atomicTransferLogRec.getQueuedTime()!=null && atomicTransferLogRec.getQueuedTime()!="") 
	 		{
	 			retRec.queuedTime = secondToTime(Integer.valueOf(atomicTransferLogRec.getQueuedTime()));
	 		}
	 		else {
	 			retRec.queuedTime = atomicTransferLogRec.getQueuedTime();
	 		}
	 		if(atomicTransferLogRec.getLeadTime()!=null && atomicTransferLogRec.getLeadTime()!="") 
	 		{
	 			retRec.leadTime = secondToTime(Integer.valueOf(atomicTransferLogRec.getLeadTime()));
	 		}
	 		else {
	 			retRec.leadTime = atomicTransferLogRec.getLeadTime();
	 		}
	 		if(atomicTransferLogRec.getTotalTime()!=null && atomicTransferLogRec.getTotalTime()!="") 
	 		{
	 			retRec.totalTime = secondToTime(Integer.valueOf(atomicTransferLogRec.getTotalTime()));
	 		}
	 		else {
	 			retRec.totalTime = atomicTransferLogRec.getTotalTime();
	 		}
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
     * 返回日时分秒
     * @param second
     * @return
     */
    //@formatter:on
    private String secondToTime(long second) {

    	String time ;
    	if ( second > UNIT_BY_DAY)
    	{
    		second = UNIT_BY_DAY;
    	}
    	
    	long hours = second / 3600;
        second = second % 3600;
        long minutes = second / 60;
        second = second % 60;
        
        return String.format("%02d:%02d:%02d",hours,minutes,second);
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
     * 2020/3/26   getTscIdBox										天津村研　董
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
     * 2020/3/26   getStkData										天津村研　董
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
     * 2020/3/26   getOhbData										天津村研　董
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
     * 2020/3/26   getPortData										天津村研　董
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
     * 2020/3/11   getStockerInfoCount										天津村研　董
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
     * 2020/4/2				MacroData情報										DONG
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
