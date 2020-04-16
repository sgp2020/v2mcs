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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.hist.SystemLogEntity;
import net.muratec.mcs.entity.hist.ReqGetSystemLogEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.ErrorLogMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.model.ErrorLog;

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
    
    @Autowired private ErrorLogMapper errorLogMapper;


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
    public List<SystemLogEntity> getSystemLog(ReqGetSystemLogEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<SystemLogEntity> retRecList = new ArrayList<SystemLogEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
//        List<Host> host = hostMapper.selectHostCommInfoList(reqEntity);
        List<ErrorLog> errorLog = errorLogMapper.selectSystemLog(reqEntity);
        
        if (errorLog == null ) {
        	return retRecList;
        }
	 	
        int num = 1;
	 	for (ErrorLog errorLogRec : errorLog) {
	 		SystemLogEntity retRec = new SystemLogEntity();

	 		retRec.rn = num;
	 		
	 		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
	 		 
	 		Date dateTime = null;
			 try {
				 dateTime = simpleDateFormat.parse(errorLogRec.getTime());
			 } catch (ParseException e) {
				 e.printStackTrace();
			 }
	 		retRec.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SS").format(dateTime);
	 		retRec.description = errorLogRec.getDescription();
	 		
	 		num ++ ;
	 		retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    
      //@formatter:off
    /**
     ******************************************************************************
     * @brief     getSystemLogCount（ほＳＴ_IDを指定し、合致する空FOUPレコード件数を取得する）機能
     * @param     controllerId    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      TSC_IDを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311   getSystemLogCount										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getSystemLogCount(ReqGetSystemLogEntity record) {

        int ret = 0;
        ret = (int) errorLogMapper.getCount(record);
        return ret;
    }
  }
