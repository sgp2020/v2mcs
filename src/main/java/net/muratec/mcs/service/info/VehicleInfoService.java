//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleInfoService.java
 * @brief       ビックル情報表示画面のサービス
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note       
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  1                                        天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.entity.info.VehicleInfoListEntity;
import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.entity.info.ReqGetVehicleInfoListEntity;
import net.muratec.mcs.mapper.VehicleInfoMapper;
import net.muratec.mcs.model.VehicleInfoModel;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ビックル情報表示画面のサービスクラス
 * @par       機能:
 *              getCount(ビックル情報一覧の全レコード数を取得)
 *              getVehicleInfoList(ビックル情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class VehicleInfoService extends BaseService {

    // ------------------------------------
    // ビックル情報マッパー
    // ------------------------------------
    @Autowired private VehicleInfoMapper vehicleInfoMapper;
    @Autowired private TscMapper tscMapper;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount(ビックル情報一覧の全レコード数を取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致したビックル情報一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetVehicleInfoListEntity reqEntity) {

        return vehicleInfoMapper.getCount(reqEntity);
    }


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getVehicleColor(ビックル色の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    ビックル色一覧
     * @retval    ビックル色のLIST形式で返却
     * @attention
     * @note      検索条件に一致したビックル情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional

    public List<String> getVehicleColor(ReqGetVehicleInfoListEntity reqEntity) {
    
    	List<VehicleInfoModel> recList = null;

        recList = vehicleInfoMapper.selectVehicleInfoList(reqEntity);

        if (recList == null) {
            return null;
        }
   
        List<String> color = new ArrayList<String>();

        for (VehicleInfoModel VehicleInfoModel : recList) {
          
            if(VehicleInfoModel.getVehicleState() != null && !State.VEHICLE_UNASSIGNED.equals(VehicleInfoModel.getVehicleState().toString())){
            	
            	// Notassigned.
            	color.add("#FFFF80");
        	}
            else {
        		// Black
        		color.add("");           		
        	}
        }
        
        return color;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getVehicleInfo(ビックル情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    ビックル情報一覧
     * @retval    ビックル情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したビックル情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional    
    public List<VehicleInfoListEntity> getVehicleInfo(ReqGetVehicleInfoListEntity reqEntity) {

        List<VehicleInfoModel> recList = null;

        recList = vehicleInfoMapper.selectVehicleInfoList(reqEntity);

        if (recList == null) {
            return null;
        }

        List<VehicleInfoListEntity> retRecList = new ArrayList<VehicleInfoListEntity>();
        int num = 1;
        List<String> color = new ArrayList<String>();
        for (VehicleInfoModel VehicleInfoModel : recList) {
            VehicleInfoListEntity entity = new VehicleInfoListEntity();
            entity.vehicleId = VehicleInfoModel.getVehicleId();
 
            entity.rn = num;
            if(VehicleInfoModel.getVehicleState()==null){
            	entity.vehicleState = "";
            }else {
            	entity.vehicleState = VehicleInfoModel.getVehicleState().toString();
            	if(!State.VEHICLE_UNASSIGNED.equals(entity.vehicleState) ) 
        		{
            		// assigned.
            		color.add("#FFFF80");
        		}
            	else {
            		// Black
            		color.add("");           		
            	}

            }
            if(VehicleInfoModel.getCurrentTscId()==null){
            	entity.currentTscId = "";
            }else {
            	entity.currentTscId = VehicleInfoModel.getCurrentTscId().toString();
            }
            if(VehicleInfoModel.getCurrentLoc()==null){
            	entity.currentLoc = "";
            }else {
            	entity.currentLoc = VehicleInfoModel.getCurrentLoc().toString();
            }
            if(VehicleInfoModel.getAssignedCarrierId()==null){
            	entity.assignedCarrierId = "";
            }else {
            	entity.assignedCarrierId = VehicleInfoModel.getAssignedCarrierId().toString();
            }
            if(VehicleInfoModel.getAssignedCommandId()==null){
            	entity.assignedCommandId = "";
            }else {
            	entity.assignedCommandId = VehicleInfoModel.getAssignedCarrierId().toString();
            }            
            if(VehicleInfoModel.getSourceLoc()==null){
            	entity.sourceLoc = "";
            }else {
            	entity.sourceLoc = VehicleInfoModel.getSourceLoc().toString();
            }            
            if(VehicleInfoModel.getDestLoc()==null){
            	entity.destLoc = "";
            }else {
            	entity.destLoc = VehicleInfoModel.getDestLoc().toString();
            } 
            
            if(VehicleInfoModel.getTotalCount()==null){
            	entity.totalCount = "";
            }else {
            	entity.totalCount = VehicleInfoModel.getTotalCount().toString();
            } 
            num++;
            retRecList.add(entity);
        }
        return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCurrentTscIdBox
     * @param     reqEntity      リクエスト(検索条件)
     * @return    ビックル情報一覧
     * @retval    ビックル情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したビックル情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getCurrentTscIdBox() {
    	
        TscExample tscExample = new TscExample();
        List<String[]> selBoxList = new ArrayList<String[]>();
        List<Tsc> tscList = tscMapper.selectOhbSemtTscID(tscExample);

        for (Tsc tsc : tscList) {
        	
            String[] data = new String[2];
            data[0] = tsc.getTscId().toString();
            data[1] = tsc.getTscAbbreviation();
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
}
