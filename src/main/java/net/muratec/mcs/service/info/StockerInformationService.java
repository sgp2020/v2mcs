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
import net.muratec.mcs.entity.info.StockerInfoListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.StockerMapper;
import net.muratec.mcs.mapper.StockerZoneRltMapper;
import net.muratec.mcs.model.Stocker;
import net.muratec.mcs.model.StockerExample;
import net.muratec.mcs.model.StockerZoneRlt;
import net.muratec.mcs.model.StockerZoneRltExample;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のサービス
 * @par       機能:
 *              getStockerInfoIdBox（StockerZoneRltのTscIDセレクトボックスリスト取得）
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
public class StockerInformationService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    // STD APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    @Autowired private StockerMapper stockerMapper;
    @Autowired private StockerZoneRltMapper stockerZoneRltMapper;
    // END APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     メイン画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    stockerZoneRlt情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311		stockerZoneRlt情報										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<StockerInfoListEntity> getStockerInfoList(ReqGetStockerInfoEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<StockerInfoListEntity> retRecList = new ArrayList<StockerInfoListEntity>();

        // -----------------------------------------
        // stockerZoneRltデータ取得
        // -----------------------------------------
//        
        List<StockerZoneRlt> stockerZoneRlt = stockerZoneRltMapper.selectStockerInfoList(reqEntity);
        
        if (stockerZoneRlt == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (StockerZoneRlt stockerZoneRltRec : stockerZoneRlt) {
	 		StockerInfoListEntity retRec = new StockerInfoListEntity();

	 		retRec.rum = rowNum;
	 		retRec.tscId = stockerZoneRltRec.getTscId();
	 		retRec.zoneId = stockerZoneRltRec.getZoneId();
	 		retRec.lOccupancy = stockerZoneRltRec.getlOccupancy();
	 		retRec.occupancy = stockerZoneRltRec.getOccupancy();
	 		retRec.usedCell = stockerZoneRltRec.getUsedCell();
	 		retRec.totalShelves = stockerZoneRltRec.getTotalShelves();
	 		retRec.lowWaterMark = stockerZoneRltRec.getLowWaterMark();
	 		retRec.highWaterMark = stockerZoneRltRec.getHighWaterMark();
	 		retRec.tscAbbreviation = stockerZoneRltRec.getTscAbbreviation();
	 		rowNum++;
	 		
        	retRecList.add(retRec);
	 	} 

		return retRecList;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoIdBox
     *            （StockerZoneRltのTscIDセレクトボックスリスト取得機能)
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200311   getStockerInfoIdBox										董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    // STD APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver3.0 Rev.000 
    @Transactional(readOnly = true)
    public List<String[]> getStockerInfoIdBox() {
    	
    	// STD APL 2020.03.13 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    	StockerExample stockerExample = new StockerExample();
    	stockerExample.createCriteria().andTscIdEqualTo(0);
    	stockerExample.setOrderByClause("TSC_ABBREVIATION ASC");
    	
//        List<Stocker> stockerInfoIdList = stockerMapper.selectStockerIdList();
    	List<Stocker> stockerInfoIdList = stockerMapper.selectStockerIdList(stockerExample);
    	// END APL 2020.03.13 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Stocker stocker : stockerInfoIdList) {
            String[] data = new String[2];
            data[0] = String.valueOf(stocker.getTscId());
            data[1] = stocker.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
  // END APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoCount（TSC_IDを指定し、合致する空FOUPレコード件数を取得する）機能
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
    public int getStockerInfoCount(String tscId) {

        int ret = 0;

        if (tscId != null && !"".equals(tscId)) {
        	StockerZoneRltExample stockerZoneRltExample = new StockerZoneRltExample();
        	stockerZoneRltExample.createCriteria().andTscIdEqualTo(Integer.valueOf(tscId));
            ret = (int) stockerZoneRltMapper.countByExample(stockerZoneRltExample);
        }
        else if(tscId==null ||"".equals(tscId)){
        	StockerZoneRltExample stockerZoneRltExample = new StockerZoneRltExample();
        	ret = (int) stockerZoneRltMapper.countByExample(stockerZoneRltExample);
        }
        return ret;
    }
  // END APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000  

}
