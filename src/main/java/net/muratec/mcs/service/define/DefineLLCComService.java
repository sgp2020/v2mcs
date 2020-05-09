//@formatter:off
/**
 ******************************************************************************
 * @file        DefineLLCComService.java
 * @brief       個別モニタ(SCモニタ)関連のサービス
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/20                                       天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.define;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.define.DefineLLCComEntity;
import net.muratec.mcs.entity.define.ReqGetDefineLLCComEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.ComConfMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.model.ComConf;

import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のサービス
 * @par       機能:
 *              getDefineLLCCom（DefineLLCCom一覧取得（LIST））
 *              getDefineLLCComCount（）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class DefineLLCComService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    @Autowired private ComConfMapper comConfMapper;


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
    public List<DefineLLCComEntity> getDefineLLCCom(ReqGetDefineLLCComEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<DefineLLCComEntity> retRecList = new ArrayList<DefineLLCComEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
//        List<Host> host = hostMapper.selectHostCommInfoList(reqEntity);
        List<ComConf> comConf = comConfMapper.selectDefineLLCCom(reqEntity);
        
        if (comConf == null ) {
        	return retRecList;
        }
	 	
        int num = 1;
	 	for (ComConf comConfRec : comConf) {
	 		DefineLLCComEntity retRec = new DefineLLCComEntity();

	 		retRec.rn = num;

	 		retRec.sid = comConfRec.getLlcSid().toString();
	 		retRec.usedTsc = comConfRec.getTscId();
	 		retRec.portNo = comConfRec.getPortNo().toString();   
	 		retRec.connectMode = comConfRec.getConnectMode().toString();
	 		retRec.hostIP = comConfRec.getHostName();   
	 		retRec.deviceID= comConfRec.getLinkTest().toString();
	 		
	 		num ++ ;
	 		retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    
      //@formatter:off
    /**
     ******************************************************************************
     * @brief     getDefineLLCComCount（ほＳＴ_IDを指定し、合致する空FOUPレコード件数を取得する）機能
     * @param     controllerId    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      TSC_IDを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311   getDefineLLCComCount										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getDefineLLCComCount(ReqGetDefineLLCComEntity record) {

        int ret = 0;
        ret = (int) comConfMapper.getCount(record);
        return ret;
    }
  }
