//@formatter:off
/**
 ******************************************************************************
 * @file        StockerStatisticsHistService.java
 * @brief       StockerStatistics関連のサービス
 * @par
 * @author      ZHANGDONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/26 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.hist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.hist.StockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.ReqGetStockerStatisticsHistEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.StockerOpeLogMapper;
import net.muratec.mcs.model.StockerOpeLog;
import net.muratec.mcs.model.StockerOpeLogExample;
import net.muratec.mcs.model.StockerOpeLogModel;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     StockerStatistics関連のサービス
 * @par       機能:
 *              getTscIdIdBox（HostのTscIDセレクトボックスリスト取得）
 *              getStatisticsStockerHistList（StokerInformation一覧取得（LIST））
 *              getStockerInfoCount（コントローラIDを指定し、合致するStockerレコード件数を取得する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0                     初版作成             	                   							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Service
public class StockerStatisticsHistService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    @Autowired private StockerOpeLogMapper stockerOpeLogMapper;
    
    @Autowired private TscMapper tscMapper;
    
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
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<StockerStatisticsHistEntity> getStockerStatisticsHist(ReqGetStockerStatisticsHistEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<StockerStatisticsHistEntity> retRecList = new ArrayList<StockerStatisticsHistEntity>();
        int timeUnit = 0;
        long downTime = 0;
        long totalUpTime = 0;
        long idleTime = 0;
        long opeRate = 0;
        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
        List<StockerOpeLogModel> stockerOpeLogModel;
        
        
        if ("1".equals(reqEntity.unit)) 
        {
        	stockerOpeLogModel = stockerOpeLogMapper.selectStockerStatisticsHistListByDay(reqEntity);
        	timeUnit = UNIT_BY_DAY;
        }
        else
        {
        	stockerOpeLogModel = stockerOpeLogMapper.selectStockerStatisticsHistListByHour(reqEntity);
        	timeUnit = UNIT_BY_HOUR;
        }

        int num = 1;
	 	for (StockerOpeLogModel stockerOpeLogRec : stockerOpeLogModel) {
	 		StockerStatisticsHistEntity retRec = new StockerStatisticsHistEntity();

	 		retRec.rn = num;
	 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	 		 
	 		Date dateTime = null;
			 try {
				 dateTime = simpleDateFormat.parse(stockerOpeLogRec.getTime());
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }
	 		retRec.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateTime);
	 		retRec.tscId = stockerOpeLogRec.getTscId().toString();
	 		retRec.assignWaitMaxTime = secondToTime(stockerOpeLogRec.getAssignWaitMaxTime());
	 		retRec.assignWaitMinTime = secondToTime(stockerOpeLogRec.getAssignWaitMinTime());
	 		retRec.assignWaitAveTime = secondToTime(stockerOpeLogRec.getAssignWaitAveTime());
	 		retRec.activeWaitMaxTime = secondToTime(stockerOpeLogRec.getActiveWaitMaxTime());
	 		retRec.activeWaitMinTime = secondToTime(stockerOpeLogRec.getActiveWaitMinTime());
	 		retRec.activeWaitAveTime = secondToTime(stockerOpeLogRec.getActiveWaitAveTime());
	 		retRec.activeTotalTime = secondToTime(stockerOpeLogRec.getActiveTotalTime());
	 		retRec.transferCount = Integer.toString(stockerOpeLogRec.getTransferCount());
	 		retRec.errorCount = Integer.toString(stockerOpeLogRec.getErrorCount());
	 		retRec.mCBF = Integer.toString(stockerOpeLogRec.getMCBF());
	 			
	 		downTime = stockerOpeLogRec.getDownTime();
	 		totalUpTime = timeUnit - downTime;
	 		idleTime = totalUpTime - stockerOpeLogRec.activeTotalTime;
	 		opeRate = (long)(((float)stockerOpeLogRec.activeTotalTime/timeUnit)*100);
	 		
	 		retRec.downTime = secondToTime(downTime);
	 		retRec.totalUpTime = secondToTime(totalUpTime);
	 		retRec.idleTime = secondToTime(idleTime);
	 		retRec.opeRate = Long.toString(opeRate); 		
	 		
        	retRecList.add(retRec);
        	num ++ ;
	 	} 

		return retRecList;
    }
    //@formatter:off
    //@formatter:on
    @Transactional(readOnly = true)
    public int getStockerStatisticsHistCount(ReqGetStockerStatisticsHistEntity reqEntity) {

        int ret = 0;
        if ("1".equals(reqEntity.unit)) 
        {
        	ret = (int) stockerOpeLogMapper.getCountByDay(reqEntity);
        }
        else
        {
        	ret = (int) stockerOpeLogMapper.getCountByHour(reqEntity);
        }
        
        return ret;
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
    	
        TscExample tscExample = new TscExample();
        List<String[]> selBoxList = new ArrayList<String[]>();
        List<Tsc> tscList = tscMapper.selectCdcTscID(tscExample);

        for (Tsc tsc : tscList) {
        	
            String[] data = new String[2];
            data[0] = tsc.getTscId().toString();
            data[1] = tsc.getTscAbbreviation();
            selBoxList.add(data);
        }
        
        return selBoxList;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getDateTimeBox
     * @param     reqEntity      リクエスト(検索条件)
     * @return    テストキャリア情報一覧
     * @retval    テストキャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したテストキャリア情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getDateTimeBox() {
    	
        List<String[]> selBoxList = new ArrayList<String[]>();

        	
        String[] data1 = new String[2];
        data1[0] = "0";
        data1[1] = "Hour";
        
        String[] data2 = new String[2];
        data2[0] = "1";
        data2[1] = "Day";

        
        selBoxList.add(data1);
        selBoxList.add(data2);
 
        return selBoxList;
    }

    //@formatter:off
    //@formatter:on
    @Transactional(readOnly = true)
    public String getEndTime(String startTime, String hourOrDay) {

    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar calendar = Calendar.getInstance(); 
    	try 
    	{
    		Date date = simpleDateFormat.parse(startTime); 

        	calendar.setTime(date);
        	if ( "1".equals(hourOrDay) )
        	{
        		calendar.add(Calendar.DAY_OF_YEAR, +1);
        	}
        	else
        	{
        		calendar.add(Calendar.HOUR, +1);
        	}
    	}
    	catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return simpleDateFormat.format(calendar.getTime());    		
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

}
