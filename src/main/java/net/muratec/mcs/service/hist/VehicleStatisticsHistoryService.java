//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleStatisticsHistoryService.java
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
 * DATE           VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/15                                                SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.hist;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.TimeUtilities;
import net.muratec.mcs.entity.hist.AtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.MacroDataListEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.entity.hist.ReqGetStockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.ReqGetVehicleStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.StockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.VehicleStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetJobStatisticsHistoryEntity;
import net.muratec.mcs.entity.hist.JobStatisticsHistoryListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AtomicTransferLogMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.TransferOpeLogMapper;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.mapper.VehicleMapper;
import net.muratec.mcs.mapper.VehicleOpeLogMapper;
import net.muratec.mcs.model.AmhsExample;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;
import net.muratec.mcs.model.UserAccount;
import net.muratec.mcs.model.UserAccountExample;
import net.muratec.mcs.model.Vehicle;
import net.muratec.mcs.model.VehicleExample;
import net.muratec.mcs.model.VehicleOpeLog;
import net.muratec.mcs.model.VehicleOpeLogModel;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.StockerOpeLogModel;
import net.muratec.mcs.model.StockerZoneRlt;
import net.muratec.mcs.model.TransferOpeLog;
import net.muratec.mcs.model.TransferOpeLogExample;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

import java.util.Calendar;
import java.util.Date;

import java.math.*;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のサービス
 * @par       機能:
 *              getTscIdBox（HostのTscIDセレクトボックスリスト取得）
 *              getStockerInfoList（StokerInformation一覧取得（LIST））
 *              getStockerInfoCount（コントローラIDを指定し、合致するStockerレコード件数を取得する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 20200415    VehicleStatisticsHistoryService				   SGP
 ******************************************************************************
 */
//@formatter:on
@Service
public class VehicleStatisticsHistoryService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;
    
    /** TscMapperマッパー生成 */
    @Autowired private TscMapper tscMapper;
    
    /** VehicleMapperマッパー生成 */
    @Autowired private VehicleMapper vehicleMapper;
    
    /** VehicleOpeLogMapperマッパー生成 */
    @Autowired private VehicleOpeLogMapper vehicleOpeLogMapper;

    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    @Autowired private TransferOpeLogMapper transferOpeLogMapper;
    
    /** 時間単位(時間) */
    private static final int UNIT_BY_HOUR = 3600;
	
	/** 時間単位(日) */
     private static final int UNIT_BY_DAY = 24*3600;

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
     * VER.        DESCRIPTION                                  AUTHOR
     * ----------------------------------------------------------------------------
     * 20200415		getVehicleStatisticsHistory情報				SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<VehicleStatisticsHistoryEntity> getVehicleStatisticsHistory(ReqGetVehicleStatisticsHistoryEntity reqEntity) throws McsException {
    	 
    	 int timeUnit = 0;
         long downTime = 0;
         long totalUpTime = 0;
         long idleTime = 0;
         long opeRate = 0;
         
         int vehicleCount = 0;
         //All == ""
 		 if( "".equals(reqEntity.vehicleId) ){
 			vehicleCount = vehicleOpeLogMapper.getVehicleCount(reqEntity);
 		 }else{
 			vehicleCount = 1;
 		}
         
        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<VehicleStatisticsHistoryEntity> retRecList = new ArrayList<VehicleStatisticsHistoryEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
        List<VehicleOpeLogModel> vehicleOpeLogModelList =  new ArrayList<VehicleOpeLogModel>();
        if ("1".equals(reqEntity.unit)) 
        {
        	vehicleOpeLogModelList = vehicleOpeLogMapper.selectVehicleStatisticsHistoryListByDay(reqEntity);
        	timeUnit = UNIT_BY_DAY;
        }
        else
        {
        	vehicleOpeLogModelList = vehicleOpeLogMapper.selectVehicleStatisticsHistoryListByHour(reqEntity);
        	timeUnit = UNIT_BY_HOUR;
        	//int total = getLoopCount( reqEntity.dateFrom.toString(), reqEntity.dateTo.toString(), Calendar.HOUR_OF_DAY );
        }
        
        int i=1;
        for (VehicleOpeLogModel vehicleOpeLogModel : vehicleOpeLogModelList) {
        	VehicleStatisticsHistoryEntity retRec = new VehicleStatisticsHistoryEntity();

	 		//retRec.rn = vehicleOpeLogModel.getRn();
        	retRec.rn = i; i++;
	 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	 		 
	 		Date dateTime = null;
			 try {
				 dateTime = simpleDateFormat.parse(vehicleOpeLogModel.getTime());
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }
	 		retRec.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dateTime);
	 		retRec.tscId = vehicleOpeLogModel.getTscId().toString();
	 		retRec.assignWaitMaxTime = secondToTime(vehicleOpeLogModel.getAssignWaitMaxTime());
	 		retRec.assignWaitMinTime = secondToTime(vehicleOpeLogModel.getAssignWaitMinTime());
	 		retRec.assignWaitAveTime = secondToTime(vehicleOpeLogModel.getAssignWaitAveTime());
	 		retRec.activeWaitMaxTime = secondToTime(vehicleOpeLogModel.getActiveWaitMaxTime());
	 		retRec.activeWaitMinTime = secondToTime(vehicleOpeLogModel.getActiveWaitMinTime());
	 		retRec.activeWaitAveTime = secondToTime(vehicleOpeLogModel.getActiveWaitAveTime());
	 		//retRec.activeTotalTime = secondToTime(vehicleOpeLogModel.getActiveTotalTime());
	 		retRec.transferCount = Integer.toString(vehicleOpeLogModel.getTransferCount());
	 		retRec.errorCount = Integer.toString(vehicleOpeLogModel.getErrorCount());
	 		retRec.mCBF = Integer.toString(vehicleOpeLogModel.getMCBF());
	 		
	 		reqEntity.dateFromForDownTime = vehicleOpeLogModel.getTime();
	 		//String dateFrom = new SimpleDateFormat("yyyyMMddHHmmss").format(reqEntity.dateFrom);

 			//if (dateFrom.compareTo(stockerOpeLogRec.getTime()) > 0 )
	 		//{
		 		//reqEntity.dateFromForDownTime = dateFrom;
 			//	dateFrom = reqEntity.dateFromForDownTime;
		 	//}

	 		reqEntity.dateToForDownTime = getEndTime(reqEntity.dateFromForDownTime,reqEntity.unit);
	 		
	 		reqEntity.dateFromForDownTime = reqEntity.dateFromForDownTime + "00";
	 		reqEntity.dateToForDownTime = reqEntity.dateToForDownTime + "00";
	 		//String dateTo = new SimpleDateFormat("yyyyMMddHHmmss").format(reqEntity.dateTo);
	 		
	 		//if (dateTo.compareTo(reqEntity.dateToForDownTime) < 0 )
	 		//{
		 	//	reqEntity.dateToForDownTime = dateTo;
		 	//}
	 		
	 		if (vehicleOpeLogMapper.getDownTime(reqEntity) != null)
	 		{
	 			downTime = Long.parseLong(vehicleOpeLogMapper.getDownTime(reqEntity));
	 		}
	 		else
	 		{
	 			downTime = 0;	 			
	 		}
	 			 	
	 		
	 		if ( vehicleCount != 1 )
			{
				vehicleCount = vehicleOpeLogModel.count;
			}
	 		
	 		if( vehicleCount != 0 )
			{
				// Active Total Time
				//row[10] = new BigDecimal( Math.round( ((BigDecimal)row[10]).doubleValue()/(double)vehicleCount ));
				int activeTotalTime = vehicleOpeLogModel.getActiveTotalTime();
				double d = activeTotalTime/(double)vehicleCount;
				long round = Math.round(d);
				retRec.activeTotalTime = new Long(round).toString();
		
			}
	 		
	 	    // Down Time
	 		retRec.downTime = new BigDecimal( downTime );
	 		
	 	    // Total Up Time
			totalUpTime = timeUnit - downTime;
			retRec.totalUpTime = secondToTime(totalUpTime);
	 		
			// Idle Time
			idleTime = totalUpTime - vehicleOpeLogModel.activeTotalTime;
			retRec.idleTime = secondToTime(idleTime);
				
			// Ope Rate
	 		opeRate = (long)(((float)vehicleOpeLogModel.activeTotalTime/timeUnit)*100);
	 		retRec.opeRate = new BigDecimal(opeRate);	 		
	 		
        	retRecList.add(retRec);
	 	} 
		return retRecList;
    }
    
    /**
	 * ループ回数を返す
	 */
	private int getLoopCount( String startTime, String endTime, int searchType )
	{
		if(startTime == null || endTime == null) {
			return 0;
		}else {
			long sTime = TimeUtilities.timeConvStrToMillis( startTime );
			long eTime = TimeUtilities.timeConvStrToMillis( endTime );
			double dbl = ( eTime - sTime ) / 1000;
			
			switch( searchType )
			{
			case Calendar.HOUR_OF_DAY:
				return (int)Math.ceil(dbl/3600);
			case Calendar.DATE:
			default:
				return (int)Math.ceil(dbl/(24*3600));
			}
		}
		
	}
	//@formatter:off
    //@formatter:on
    @Transactional(readOnly = true)
    public String getEndTime(String startTime, String hourOrDay) throws McsException{
    	
    	//String startTimeFormat = startTime.substring(0, 4) + "-" + startTime.substring(4, 6) + "-" + startTime.substring(6, 8) + " " + startTime.substring(8, 10);
    	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh");
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar calendar = Calendar.getInstance(); 
    	
    	if(startTime!=null && !"".equals(startTime)) {
    		
    		Date date = new Date();
			try {
				date = simpleDateFormat.parse(startTime);
				calendar.setTime(date);
	        	if ( "1".equals(hourOrDay) )
	        	{
	        		calendar.add(Calendar.DAY_OF_YEAR, +1);
	        	}
	        	else
	        	{
	        		calendar.add(Calendar.HOUR, +1);
	        	}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new McsException(e);
			}
    		
    	}
		//return startTime;	
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

    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     VehicleStatisticsHistory画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    Host情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                      AUTHOR
     * ----------------------------------------------------------------------------
     * 20200416		getVehicleStatisticsHistoryCount情報				SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getVehicleStatisticsHistoryCount(ReqGetVehicleStatisticsHistoryEntity reqEntity) {

        int ret = 0;
        if ("1".equals(reqEntity.unit)) 
        {
        	ret = (int) vehicleOpeLogMapper.getCountByDay(reqEntity);
        }
        else
        {
        	ret = (int) vehicleOpeLogMapper.getCountByHour(reqEntity);
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
     * VER.        DESCRIPTION                                     AUTHOR
     * ----------------------------------------------------------------------------
     * 20200415    getTscIdBox										SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getTscIdBox() {
    	
    	 TscExample tscExample = new TscExample();
    	 tscExample.createCriteria().andTscModelEqualTo("IBSEM");
         List<Tsc> tscIdList = tscMapper.selectByExample(tscExample);
         
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
     * @brief     getvehicleIdBox
         *            （tscのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                   AUTHOR
     * ----------------------------------------------------------------------------
     * 20200415   getvehicleIdBox								SGP 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getVehicleIdBox(String tscId) {
    	
    	VehicleExample vehicleExample = new VehicleExample();
    	vehicleExample.createCriteria();
        List<Vehicle> vehicleIdList = vehicleMapper.selectByExample(vehicleExample);
        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Vehicle vehicle : vehicleIdList) {
            String[] data = new String[2];
            data[0] = vehicle.getVehicleId();
            data[1] = vehicle.getVehicleId();
            
            selBoxList.add(data);
        }
        return selBoxList;
    }
  
    
}
