//@formatter:off
/**
 ******************************************************************************
 * @file        ModeSetTSCService.java
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
 * 2020/05/08 v1.0.0                     初版作成                                							          天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.info.ReqGetStockerInfoEntity;
import net.muratec.mcs.entity.info.StockerInfoListEntity;
import net.muratec.mcs.entity.system.ModeSetTSCListEntity;
import net.muratec.mcs.entity.system.ReqGetModeSetTSCEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.StockerMapper;
import net.muratec.mcs.mapper.StockerZoneRltMapper;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.model.Stocker;
import net.muratec.mcs.model.StockerExample;
import net.muratec.mcs.model.StockerZoneRlt;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のサービス
 * @par       機能:
 *              getTSCTypeBox（TSCのTscTypeセレクトボックスリスト取得）
 *              getModeSetTSCList（ModeSetTSC一覧取得（LIST））
 *              getModeSetTSCCount（TscTypeを指定し、合致するTSCレコード件数を取得する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0                     初版作成                                							  			        天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Service
public class ModeSetTSCService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** JOB_PRIORITYマッパー生成 */
    @Autowired private JobPriorityMapper jobPriorityMapper;


    /** 外部ファイル参照用サービス生成 */
    @Autowired ExeForeignFileService exeForeignFileService;
    
    @Autowired private TscMapper tscMapper;
    @Autowired private StockerMapper stockerMapper;
    @Autowired private StockerZoneRltMapper stockerZoneRltMapper;

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
     * 2020/3/11		stockerZoneRlt情報										天津村研　董
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<ModeSetTSCListEntity> getTscInfoList(ReqGetModeSetTSCEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<ModeSetTSCListEntity> retRecList = new ArrayList<ModeSetTSCListEntity>();

        // -----------------------------------------
        // tscデータ取得
        // -----------------------------------------
//        
        List<Tsc> tscRlt = tscMapper.selectModeSetTSC(reqEntity);
        
        if (tscRlt == null ) {
        	return retRecList;
        }
	 	
        int rowNum = 1;
	 	for (Tsc tscRltRec : tscRlt) {
	 		ModeSetTSCListEntity retRec = new ModeSetTSCListEntity();
	 		retRec.rum = rowNum;
	 		retRec.tscAbbreviation = tscRltRec.getTscAbbreviation();
	 		retRec.tscMode = tscRltRec.getTscMode();
	 		retRec.modeSetTime = tscRltRec.getModeSetTime();
	 		retRec.modeSetSid = tscRltRec.getModeSetSid();
	 		retRec.modeSetOriginator = tscRltRec.getModeSetOriginator();
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
     * 2020/3/11   getStockerInfoIdBox										天津村研　董
     ******************************************************************************
     */
    //@formatter:on
    // STD APL 2020.03.11 天津村研　董  MCSV4　GUI開発  Ver3.0 Rev.000 
    @Transactional(readOnly = true)
    public List<String[]> getStockerInfoIdBox() {
    	
    	// STD APL 2020.03.13 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
    	StockerExample stockerExample = new StockerExample();
    	stockerExample.createCriteria().andTscIdEqualTo(0);
    	stockerExample.setOrderByClause("TSC_ABBREVIATION ASC");
    	
//        List<Stocker> stockerInfoIdList = stockerMapper.selectStockerIdList();
    	List<Stocker> stockerInfoIdList = stockerMapper.selectStockerIdList(stockerExample);
    	// END APL 2020.03.13 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Stocker stocker : stockerInfoIdList) {
            String[] data = new String[2];
            data[0] = String.valueOf(stocker.getTscId());
            data[1] = stocker.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
  // END APL 2020.03.11 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
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
     * 2020/3/11   getStockerInfoCount										天津村研　董
     ******************************************************************************
     */
    //@formatter:on
   /* @Transactional(readOnly = true)
    public int getStockerInfoCount(ReqGetModeSetTSCEntity record) {

        int ret = 0;
        String tscId = record.tscId;
        if (tscId != null && !"".equals(tscId)) {
            ret = (int) stockerZoneRltMapper.getCount(record);
        }
        else if(tscId==null ||"".equals(tscId)){
        	ret = (int) stockerZoneRltMapper.getCount(record);
        }
        return ret;
    }*/
   // END APL 2020.03.11 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000  
   // STD 2020.03.27 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     getRowColor
     * @param     reqEntity      画面項目情報
     * @return    検索条件に該当するレコード
     * @retval    List形式で返却
     * @attention
     * @note      コントローラIDリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 2020/3/27   getRowColor										天津村研　董
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String> getRowColor(ReqGetModeSetTSCEntity reqEntity) throws McsException {
    	
    	List<String> color = new ArrayList<String>();
    	/*List<StockerZoneRlt> stockerZoneRlt = stockerZoneRltMapper.selectStockerInfoList(reqEntity);
        if (stockerZoneRlt == null ) {
        	return color;
        }
        
        for (StockerZoneRlt stockerZoneRltRec : stockerZoneRlt) {
        	
        	int fill = stockerZoneRltRec.getUsedCell();
        	int low  = stockerZoneRltRec.getLowWaterMark();
        	int high = stockerZoneRltRec.getHighWaterMark();
        	if( fill >= low && fill < high ) 
    		{
        		//LOW YELLOW
        		color.add("#FFFF00");
    		}
    		else if( fill >= high ) 
    		{
    			//HIGH RED
    			color.add("#FF5555");
    		}
    		else {
    			//NORMAL GREEN
    			color.add("#33FF00");
    		}
	 	} */
        return color;
    }
   // END 2020.03.27 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 

}
