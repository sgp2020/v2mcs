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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.info.ReqGetStockerInfoEntity;
import net.muratec.mcs.entity.info.StockerInfoListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.JobPriorityMapper;
import net.muratec.mcs.mapper.StockerMapper;
import net.muratec.mcs.mapper.StockerZoneRltMapper;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.JobPriority;
import net.muratec.mcs.model.Stocker;
import net.muratec.mcs.model.StockerExample;
import net.muratec.mcs.model.StockerInfoModel;
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
 *              getHotlotCategoryByPriority（ジョブ状態のチェック機能）
 *              creRgbArray（GUI_COLORレコードからRGB配列を生成する機能）
 *              stringToShortNumber（String型の数値からShort型の数値へ変換する機能）
 *              decimalToPerNumString（小数値をString型の百分率形式に変換する機能）
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
//    	StockerInfoListEntity resEntity = new StockerInfoListEntity();
        List<StockerInfoListEntity> retRecList = new ArrayList<StockerInfoListEntity>();

        // -----------------------------------------
        // stockerZoneRltデータ取得
        // -----------------------------------------
//        
//        StockerZoneRltExample configExample = new StockerZoneRltExample();
//        configExample.createCriteria().andTscIdEqualTo(Integer.valueOf(reqEntity.controllerId));
//        List<StockerInfoModel> stockerZoneRlt = stockerZoneRltMapper.selectStockerInfoList(reqEntity);
        List<StockerZoneRlt> stockerZoneRlt = stockerZoneRltMapper.selectStockerInfoList(reqEntity);
        
        if (stockerZoneRlt == null ) {
        	return retRecList;
        }
	 	
//	 		for (StockerInfoModel stockerZoneRltRec : stockerZoneRlt) {
        int rowNum = 1;
	 	for (StockerZoneRlt stockerZoneRltRec : stockerZoneRlt) {
	 		StockerInfoListEntity retRec = new StockerInfoListEntity();

//	 		retRec.rum = stockerZoneRltRec.getNum();
	 		retRec.rum = rowNum;
	 		retRec.tscId = stockerZoneRltRec.getTscId();
	 		retRec.zoneId = stockerZoneRltRec.getZoneId();
	 		retRec.lOccupancy = stockerZoneRltRec.getlOccupancy();
//	 		retRec.lOccupancy = stockerZoneRltRec.getLOccupancy();
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
     ******************************************************************************
     */
    //@formatter:on
   /* @Transactional(readOnly = true)
    public List<String[]> getStockerInfoIdBox() {
        List<Stocker> stockerInfoIdList = stockerMapper.selectStockerIdList(null);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Stocker stocker : stockerInfoIdList) {
            String[] data = new String[2];
            data[0] = String.valueOf(stocker.getTscId());
            data[1] = stocker.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    */
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
     * @brief     getStockerInfoCount（コントローラIDを指定し、合致する空FOUPレコード件数を取得する）機能
     * @param     controllerId    検索条件
     * @return    検索条件に該当するレコード数
     * @retval    int形式で返却
     * @attention
     * @note      コントローラIDを指定し、合致する件数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public int getStockerInfoCount(String tscId) {
//    	public int getStockerInfoCount(String tscId) {

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

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送Job画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    搬送Job情報
     * @retval    List形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
   /* @Transactional(readOnly = true)
    public List<IndividualMonitorTransferJobEntity> getTransferJobInfoList(ReqIndividualMonitorEntity reqEntity)
            throws McsException {

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        List<IndividualMonitorTransferJob> recList = iMonitorMapper.getScMonitorTransferJobList(reqEntity);

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorTransferJobEntity> resEntity = new ArrayList<IndividualMonitorTransferJobEntity>();

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        for (IndividualMonitorTransferJob rec : recList) {
            IndividualMonitorTransferJobEntity res = new IndividualMonitorTransferJobEntity();

            res.receivedTime = ComFunction.timestampToStringSmall(rec.getReceivedTime());
            res.jobOwner = rec.getJobOwner();
            res.carrierId = rec.getCarrierId();
            res.priority = rec.getPriority();
            res.carrierJobState = rec.getCarrierJobState();
            res.currentLoc = rec.getCurrentLoc();
            res.srcAmhsId = rec.getSrcAmhsId();
            res.srcLoc = rec.getSrcLoc();
            res.dstAmhsId = rec.getDstAmhsId();
            res.dstLoc = rec.getDstLoc();
            res.microFrom = rec.getMicroFrom();
            res.microTo = rec.getMicroTo();
            res.amhsId = rec.getAmhsId();
            res.hideCarrierJobState = rec.getHideCarrierJobState();
            res.waitInTime = rec.getWaitInTime();

            resEntity.add(res);
        }

        return resEntity;
    }
*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送Job画面の色情報を取得する機能
     * @param     recList    Tableに表示するデータ
     * @return    色情報一覧
     * @retval    List形式で返却
     * @attention
     * @note      GUI_COLORテーブルの色情報を使用する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
   /* @Transactional(readOnly = true)
    public List<String> getTransferJobColorInfoList(List<IndividualMonitorTransferJobEntity> recList) {

        // 色情報を取得
        String[] rgbColorArray = creRgbArray("CARRIER_JOB", 1000);

        // 優先順位による状態(Normal, Hot, SuperHot)の情報を取得する
        List<JobPriority> jobPriorityList = jobPriorityMapper.selectMinPriority();

        // 色情報のリスト
        List<String> retList = new ArrayList<String>();

        // 色情報を取得し、リストに追加
        for (IndividualMonitorTransferJobEntity record : recList) {
            String color = null;

            // 対応する状態色付与(Normal, Hot, SuperHot)
            Short hotCategory = getHotlotCategoryByPriority(jobPriorityList, record.priority);

            // RGB情報にアクセスするindex番号を設定 (Normal：0～60、Hot&SuperHot：61～69)
            if (hotCategory == 0 && 0 <= record.hideCarrierJobState && record.hideCarrierJobState <= 60) {
                color = rgbColorArray[record.hideCarrierJobState];
            } else if (0 <= hotCategory && hotCategory < 10) {
                color = rgbColorArray[hotCategory + 60];
            }

            retList.add(color);
        }

        // RGB配列の解放
        rgbColorArray = null;

        return retList;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief      ジョブの状態(Normal, Hot, SuperHot)を取得する機能
     * @param      jobPriorityList    ジョブの優先順位情報
     * @param      recPriority        レコードの優先順位
     * @return     ジョブの状態(Normal, Hot, SuperHot)
     * @retval
     * @attention 対応するジョブがなければ0(normal)を返す
     * @note       0:Normal, 1:Hot, 2:SuperHot
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Short getHotlotCategoryByPriority(List<JobPriority> jobPriorityList, Short recPriority) {

        for (JobPriority jobPriority : jobPriorityList) {
            if (jobPriority.getThresholdPriority() <= recPriority) {
                return jobPriority.getHotlotCategory();
            }
        }
        return 0;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief      GUI_COLOR.SECTIONを指定し、String配列で色情報を取得
     * @param      section         取得する色情報のGUI_COLOR.SECTION
     * @param      maxIndex        返却配列のサイズ
     * @return     色情報
     * @retval     String配列形式で返却
     * @note       色情報は#000000の形式で返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String[] creRgbArray(String section, int maxIndex) {

        String[] rgbColorArray = new String[maxIndex];

        // 色情報を取得
        GuiColorExample example = new GuiColorExample();
        example.createCriteria().andSectionEqualTo(section);
        example.setOrderByClause("KEY asc");
        List<GuiColor> guiColorList = guiColorMapper.selectByExample(example);

        // 取得した設定色をRGBに変換し、配列化
        for (GuiColor guiColor : guiColorList) {
            Short index = stringToShortNumber(guiColor.getKey());
            if (index != null && 0 <= index && index < maxIndex) {
                rgbColorArray[index] = ComFunction.rgbToHex(guiColor.getRgbRed(), guiColor.getRgbGreen(),
                        guiColor.getRgbBlue());
            }
        }

        return rgbColorArray;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列からShort型数値への変換メソッド
     * @param     value              Short型数値に変換する文字列
     * @return    Short型へ変換された値
     * @retval    Short型で返却
     * @attention
     * @note      Short型へ変換可能な文字列のみ変換を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Short stringToShortNumber(String value) {

        Short convValue = null;
        try {
            convValue = Short.parseShort(value);
        } catch (Exception e) {
            return null;
        }

        return convValue;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     小数値からパーセント表記への変換メソッド
     * @param     obj              パーセント表記に変換する小数値
     * @return    パーセント表記へ変換された値
     * @retval    String形式で返却
     * @attention
     * @note      小数値をパーセント表記への変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String decimalToPerNumString(Object obj) throws McsException {

        String perNumStr = "";
        int perNum = 0;

        if (obj == null) {
            // 変換する値が設定されていない場合
            return perNumStr;
        } else if (obj.getClass().equals(java.math.BigDecimal.class)) {
            // 変換する値がBigDecimal型の場合
            BigDecimal decNum = (BigDecimal) obj;
            perNum = decNum.intValue();

            // パーセント(%)の付与
            perNumStr = String.valueOf(perNum) + "%";
        }

        return perNumStr;
    }

}
