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
package net.muratec.mcs.service.info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.info.ReqGetStockerInfoEntity;
import net.muratec.mcs.entity.info.HostCommInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetHostCommInfoEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.HostMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.StockerMapper;
import net.muratec.mcs.mapper.HostMapper;
import net.muratec.mcs.model.Host;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.model.ScreenMonitorMemberExample;
import net.muratec.mcs.model.Stocker;
import net.muratec.mcs.model.StockerExample;
import net.muratec.mcs.model.Host;
import net.muratec.mcs.model.HostExample;
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
public class HostCommInfoService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    // STD APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    @Autowired private HostMapper hostMapper;
    // END APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

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
    public List<HostCommInfoListEntity> getHostCommInfoList(ReqGetHostCommInfoEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<HostCommInfoListEntity> retRecList = new ArrayList<HostCommInfoListEntity>();

        // -----------------------------------------
        // Hostデータ取得
        // -----------------------------------------
//        
        List<Host> host = hostMapper.selectHostCommInfoList(reqEntity);
        
        if (host == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (Host hostRec : host) {
	 		HostCommInfoListEntity retRec = new HostCommInfoListEntity();

	 		retRec.rum = rowNum;
	 		retRec.hostIP = hostRec.getHostIp();
	 		retRec.hostName = hostRec.getHostName();
	 		retRec.commState = hostRec.getCommState();
	 		retRec.passageTime = hostRec.getPassageTime();
	 		
	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoIdBox
     *            （HostのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200318   getStockerInfoIdBox										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getHostNameBox() {
    	
    	List<Host> hostNameList = hostMapper.selectHostNameList();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Host hostName : hostNameList) {
            String[] data = new String[2];
            data[0] = hostName.getHostName();
            data[1] = hostName.getHostName();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoIdBox
     *            （HostのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200318   getStockerInfoIdBox										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getCommStateBox() {
    	
        List<String[]> selBoxList = new ArrayList<String[]>();
        	
            String[] data1 = new String[2];
            data1[0] = "Selected";
            data1[1] = "Selected";
            
            String[] data2 = new String[2];
            data2[0] = "Selected/NotCommuncating";
            data2[1] = "Selected/NotCommuncating";
            
            String[] data3 = new String[2];
            data3[0] = "Selected/Communicating";
            data3[1] = "Selected/Communicating";
            
            selBoxList.add(data1);
            selBoxList.add(data2);
            selBoxList.add(data3);

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
    public int getHostommInfoCount(ReqGetHostCommInfoEntity record) {

        int ret = 0;
        ret = (int) hostMapper.getCount(record);
        return ret;
    }
    

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getSearchInfo（HOST_NAME,COMM_STATEを指定し、合致するHOSTレコード件数を取得する）機能
     * @param     HOST_NAME,COMM_STATE    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    String形式で返却
     * @attention
     * @note      HOST_NAME,COMM_STATEを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311   getSearchSelectData										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public String getSearchSelectData(ReqGetHostCommInfoEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        String ret = " " ;
        
        // -----------------------------------------
        // 検索ボタン選択データ取得
        // -----------------------------------------
        String hostName = reqEntity.hostName;
        String commState = reqEntity.commState;
        
        if (hostName != null && !"".equals(hostName)) {
            ret = "Host Name " + "[" + hostName + "] ";
        }
        else if(commState!=null && !"".equals(commState)){
        	ret = "Comm State " + "[" + commState + "]";
        }

		return ret;
    }

}
