//@formatter:off
/**
 ******************************************************************************
 * @file        ActivityHistService.java
 * @brief       個別モニタ(SCモニタ)関連のサービス
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE            VER.        DESCRIPTION                AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/2007    0.5         Step4リリース                            SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.hist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.hist.ActivityHistoryListEntity;
import net.muratec.mcs.entity.hist.AtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.MacroDataListEntity;
import net.muratec.mcs.entity.hist.ReqGetActivityHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AtomicTransferLogMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.MacroTransferLogMapper;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.OhbPortRltModel;
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
 ******************************************************************************
 */
//@formatter:on
@Service
public class ActivityHistoryService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    
    @Autowired private MacroTransferLogMapper macroTransferLogMapper;

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
     * VER.        DESCRIPTION                                 AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311		Host情報										SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<ActivityHistoryListEntity> getActivityHistoryList(ReqGetActivityHistoryEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<ActivityHistoryListEntity> retRecList = new ArrayList<ActivityHistoryListEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
//        List<Host> host = hostMapper.selectHostCommInfoList(reqEntity);
        List<MacroTransferLog> atomicTransferLog = macroTransferLogMapper.selectActivityHistoryList(reqEntity);
        
        if (atomicTransferLog == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (MacroTransferLog macroTransferLogRec : atomicTransferLog) {
	 		ActivityHistoryListEntity retRec = new ActivityHistoryListEntity();

	 		retRec.rum = rowNum;
	 		retRec.rcvTime = macroTransferLogRec.getOrgRcvTime();
	 		retRec.carrierId = macroTransferLogRec.getOrgCarrierId();
	 		String totalTime = macroTransferLogRec.getTotalTime();
	 		retRec.totalTime = timeFormat(new Integer(totalTime));  ///????
	 		retRec.srcTscId = macroTransferLogRec.getOrgSrcTscId().toString();
	 		retRec.srcLoc = macroTransferLogRec.getOrgSrcLoc();
	 		retRec.compTscId = macroTransferLogRec.getTscId();///????????
	 		retRec.compLoc = macroTransferLogRec.getLoc();  /////???????
	 		retRec.status = macroTransferLogRec.getStatusStr();  //????
	 		retRec.startTime = macroTransferLogRec.getOrgStartTime();
	 		retRec.cmpTime = macroTransferLogRec.getOrgCmpTime();
	 		retRec.dstGroup = macroTransferLogRec.getOrgDstGroup();
	 		retRec.dstTscId = macroTransferLogRec.getOrgDstTscId().toString();
	 		retRec.dstLoc = macroTransferLogRec.getOrgDstLoc();
	 		retRec.altTscId = macroTransferLogRec.getAltTscId().toString();
	 		retRec.altLoc = macroTransferLogRec.getAltLoc();
	 		retRec.priority = macroTransferLogRec.getOrgPriority();
	 		retRec.nextDestination = macroTransferLogRec.getOrgNextDst();
	 		retRec.cancelReq = macroTransferLogRec.getCancelFlgStr(); //????
	 		retRec.hostCommandId = macroTransferLogRec.getOrgHostCommandId();
	 		retRec.commandId = macroTransferLogRec.getOrgCommandId();
	 		retRec.originator = macroTransferLogRec.getOrgOriginator();
	 		retRec.rerouteReq = macroTransferLogRec.getRerouteFlgStr();///???

	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    
    
    /**
   	 * 時間を'HH:MM:SS'フォーマットの文字列に変換します。
   	 *
   	 * @param millis 時間を表すlong値
   	 * @return 'HH:MM:SS'フォーマットの文字列
   	 */
   	public static String timeFormat( int millis )
   	{
   		/*
   		int abs = java.lang.Math.abs( millis );
   		int hh  = abs / 3600;
   		int mm  = ( abs / 60 ) % 60;
   		int ss  = abs % 60;
   		return ( ( millis < 0 )?  "-" : "" ) +
   			   ( ( hh     < 10 )?  "0" : "" ) + String.valueOf( hh ) + ":" +
   			   ( ( mm     < 10 )?  "0" : "" ) + String.valueOf( mm ) + ":" +
   			   ( ( ss     < 10 )?  "0" : "" ) + String.valueOf( ss );
   	    */
   		long hours = millis / 3600;
   		millis = millis % 3600;
        long minutes = millis / 60;
        millis = millis % 60;
        
        return String.format("%02d:%02d:%02d",hours,minutes,millis);
   	}
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCountActivityHistory（ほＳＴ_IDを指定し、合致する空FOUPレコード件数を取得する）機能
     * @param     controllerId    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                     AUTHOR
     * ----------------------------------------------------------------------------
     * 20200407   getCountActivityHistory						SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountActivityHistory(ReqGetActivityHistoryEntity record) {

        int ret = 0;
        ret = (int) macroTransferLogMapper.getCountActivityHistory(record);
        return ret;
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
     * VER.        DESCRIPTION                                  AUTHOR
     * ----------------------------------------------------------------------------
     * 20200407   getStkData									SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getStkData() {
    	
    	List<Tsc> tscIdList = macroTransferLogMapper.selectStkData();

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
     * VER.        DESCRIPTION                                     AUTHOR
     * ----------------------------------------------------------------------------
     * 20200407    getOhbData										SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getOhbData() {
    	
    	List<Ohb> ohbIdList = macroTransferLogMapper.selectOhbData();

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
     * VER.        DESCRIPTION                                   AUTHOR
     * ----------------------------------------------------------------------------
     * 20200407    getPortData									SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getPortData() {
    	
    	List<Port> porTIdList = macroTransferLogMapper.selectPortData();

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
     * @brief     getAtomicTransferLogList(アラーム情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    アラーム情報一覧
     * @retval    アラーム情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<AtomicActivityHistListEntity> getAtomicTransferLogList(String commandId) {

    	List<AtomicActivityHistListEntity> atomicTransferLogList = macroTransferLogMapper.selectAtomicTransferLogByCommandId(commandId);
    	for (AtomicActivityHistListEntity atomicActivityHistListEntity : atomicTransferLogList) {
    		String atomicTime = atomicActivityHistListEntity.atomicTime;
    		if(atomicTime != null && !"".equals(atomicTime)) {
    			atomicActivityHistListEntity.atomicTime =  timeFormat( new Integer(atomicTime) );
    		}
    		//if(atomicTime != null && !"".equals(atomicTime)) {
        	//	String storedTime1 = atomicTime.substring(0,4) + "/" + atomicTime.substring(4,6) + "/" + atomicTime.substring(6,8) + " " + atomicTime.substring(8,10) + ":" + atomicTime.substring(10,12) + ":" + atomicTime.substring(12,14);
        	//	atomicTransferLogList.setAtomicTime(storedTime1);
    		//}
		}  
        return atomicTransferLogList;
    }
}
