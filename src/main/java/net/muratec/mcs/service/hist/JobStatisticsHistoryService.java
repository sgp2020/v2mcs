//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualScMonitorService.java
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
import net.muratec.mcs.entity.hist.ReqGetJobStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.JobStatisticsHistoryListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AtomicTransferLogMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.TransferOpeLogMapper;
import net.muratec.mcs.model.AmhsExample;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.StockerZoneRlt;
import net.muratec.mcs.model.TransferOpeLog;
import net.muratec.mcs.model.TransferOpeLogExample;
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
 * 20200409    JobStatisticsHistoryService								DONG
 ******************************************************************************
 */
//@formatter:on
@Service
public class JobStatisticsHistoryService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    @Autowired private TransferOpeLogMapper transferOpeLogMapper;
    
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
     * 20200414		StatisticsHistory(Job)情報								董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<JobStatisticsHistoryListEntity> getJobStatisticsHistoryList(ReqGetJobStatisticsHistoryEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<JobStatisticsHistoryListEntity> retRecList = new ArrayList<JobStatisticsHistoryListEntity>();

        String tscId = reqEntity.tscId;
        String source = reqEntity.source;
        String destination = reqEntity.destination;
        String unit = reqEntity.unit;
        List<TransferOpeLog> transferOpeLog = new ArrayList<TransferOpeLog>();
        
        if(unit.equals("0")) {
	        if(tscId!=null || tscId!="") {
	        	if(source==null || "".equals(source)) {
	        		transferOpeLog = transferOpeLogMapper.selectHourJobByTscIdData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination==null || "".equals(destination))) {
	        		transferOpeLog = transferOpeLogMapper.selectHourJobBySrcLocData(reqEntity);
	        	}
	        	else if((source==null || "".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		transferOpeLog = transferOpeLogMapper.selectHourJobByDstLocData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		transferOpeLog = transferOpeLogMapper.selectHourJobData(reqEntity);
	        	}
	        }
       }
        else if(unit.equals("1")) {
        	if(tscId!=null && tscId!="") {
	        	if(source==null || "".equals(source)) {
	        		transferOpeLog = transferOpeLogMapper.selectDayJobByTscIdData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination==null || "".equals(destination))) {
	        		transferOpeLog = transferOpeLogMapper.selectDayJobBySrcLocData(reqEntity);
	        	}
	        	else if((source==null || "".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		transferOpeLog = transferOpeLogMapper.selectDayJobByDstLocData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		transferOpeLog = transferOpeLogMapper.selectDayJobData(reqEntity);
	        	}
	        }
        }
        
        if (transferOpeLog == null ) {
        	return retRecList;
        }
        
        int rowNum = 1;
	 	for (TransferOpeLog transferOpeLogRec : transferOpeLog) {
	 		JobStatisticsHistoryListEntity retRec = new JobStatisticsHistoryListEntity();
	 		
	 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	 		Date dateTime = null;
	 		try {
	 			dateTime = simpleDateFormat.parse(transferOpeLogRec.getTime());
	 		} catch (ParseException e) {
	 			e.printStackTrace();
	 		}
	 		retRec.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateTime);

	 		retRec.rum 			= rowNum;
	 		retRec.time 		= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateTime);
	 		retRec.tscId 		= String.valueOf(transferOpeLogRec.getTscId());
	 		retRec.sourceLoc 	= transferOpeLogRec.getSrcLoc();
	 		retRec.destLoc		= transferOpeLogRec.getDstLoc();
	 		if(transferOpeLogRec.getMaxTime()!=null) 
	 		{
	 			retRec.maxTime	= secondToTime(transferOpeLogRec.getMaxTime());
	 		}
	 		else {
	 			retRec.maxTime	= String.valueOf(transferOpeLogRec.getMaxTime());
	 		}
	 		if(transferOpeLogRec.getMinTime()!=null) 
	 		{
	 			retRec.minTime		= secondToTime(transferOpeLogRec.getMinTime());
	 		}
	 		else {
	 			retRec.minTime	= String.valueOf(transferOpeLogRec.getMinTime());
	 		}
	 		if(transferOpeLogRec.getAvgTime()!=null) 
	 		{
	 			retRec.avgTime		= secondToTime(transferOpeLogRec.getAvgTime());
	 		}
	 		else {
	 			retRec.avgTime	= String.valueOf(transferOpeLogRec.getAvgTime());
	 		}
	 		if(transferOpeLogRec.getTotalTime()!=null) 
	 		{
	 			retRec.totalTime	= secondToTime(transferOpeLogRec.getTotalTime());
	 		}
	 		else {
	 			retRec.totalTime	= String.valueOf(transferOpeLogRec.getTotalTime());
	 		}
	 		retRec.opeCount		= transferOpeLogRec.getOpeCount();
	 		retRec.errCount		= transferOpeLogRec.getErrCount();
	 		retRec.mcbf  		= transferOpeLogRec.getMcbf();
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
     * 20200326   getTscIdBox										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getTscIdBox() {
    	
    	List<Tsc> tscIdList = transferOpeLogMapper.selectTscNameList();

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
    public List<String[]> getZoneData(String tscId) {
    	
    	List<StockerZoneRlt> zoneList = transferOpeLogMapper.selectZoneData(tscId);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (StockerZoneRlt zones : zoneList) {
            String[] data = new String[2];
            data[0] = String.valueOf(zones.getZoneId());
            data[1] = zones.getZoneId();
            
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
    public List<String[]> getPortData(String tscId) {
    	
    	List<Port> porTIdList = transferOpeLogMapper.selectPortData(tscId);

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
     * @brief     getJobStatisticsHistoryCount（ほTSC_IDを指定し、レコード件数を取得する）機能
     * @param     
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      TSC_IDを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 2020.04.09   getJobStatisticsHistoryCount							DONG
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getgetStatisticsHistoryJobCount(ReqGetJobStatisticsHistoryEntity reqEntity) {

        int ret = 0;
        String tscId = reqEntity.tscId;
        String source = reqEntity.source;
        String destination = reqEntity.destination;
        String unit = reqEntity.unit;
        
        if(unit.equals("0")) {
	        if(tscId!=null || tscId!="") {
	        	if(source==null || "".equals(source)) {
	        		ret = getCountHourJobByTscIdData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination==null || "".equals(destination))) {
	        		ret = getCountHourJobBySrcLocData(reqEntity);
	        	}
	        	else if((source==null || "".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		ret = getCountHourJobByDstLocData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		ret = getCountHourJobData(reqEntity);
	        	}
	        }
       }
        else if(unit.equals("1")) {
        	if(tscId!=null && tscId!="") {
	        	if(source==null || "".equals(source)) {
	        		ret = getCountDayJobByTscIdData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination==null || "".equals(destination))) {
	        		ret = getCountDayJobBySrcLocData(reqEntity);
	        	}
	        	else if((source==null || "".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		ret = getCountDayJobByDstLocData(reqEntity);
	        	}
	        	else if((source!=null || !"".equals(source))
	        			&&(destination!=null || !"".equals(destination))){
	        		ret = getCountDayJobData(reqEntity);
	        	}
	        }
        }
        return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountDayJobByTscIdData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountDayJobByTscIdData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountDayJobBySrcLocData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountDayJobBySrcLocData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountDayJobByDstLocData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountDayJobByDstLocData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountDayJobData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountDayJobData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountHourJobByTscIdData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountHourJobByTscIdData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountHourJobBySrcLocData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountHourJobBySrcLocData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountHourJobByDstLocData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountHourJobByDstLocData(record);
    	return ret;
    }
    //@formatter:on
    @Transactional(readOnly = true)
    public int getCountHourJobData(ReqGetJobStatisticsHistoryEntity record) {
    	
    	int ret = 0;
    	ret = (int) transferOpeLogMapper.getCountHourJobData(record);
    	return ret;
    }
}
