//@formatter:off
/**
 ******************************************************************************
 * @file        RouteService.java
 * @brief       アラーム情報表示画面のサービス
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note       
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                        SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.entity.info.OhbInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetOhbInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetRouteInfoListEntity;
import net.muratec.mcs.entity.info.RouteListEntity;
import net.muratec.mcs.mapper.OhbMapper;
import net.muratec.mcs.mapper.OhbPortRltMapper;
import net.muratec.mcs.mapper.RouteListMapper;
import net.muratec.mcs.model.OhbModel;
import net.muratec.mcs.model.OhbPortRltModel;
import net.muratec.mcs.model.RouteListModel;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示画面のサービスクラス
 * @par       機能:
 *              getCount(アラーム情報一覧の全レコード数を取得)
 *              getRouteInfoList(アラーム情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class RouteInfoService extends BaseService {

    // ------------------------------------
    // アラーム情報マッパー
    // ------------------------------------
    @Autowired private RouteListMapper routeListMapper;
    
    @Autowired private OhbPortRltMapper ohbPortRltMapper;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount(アラーム情報一覧の全レコード数を取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetRouteInfoListEntity reqEntity) {

        return routeListMapper.getCount(reqEntity);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getRouteInfoList(アラーム情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    アラーム情報一覧
     * @retval    アラーム情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<RouteListEntity> getRouteInfoList(ReqGetRouteInfoListEntity reqEntity) {

        List<RouteListModel> recList = null;

        recList = routeListMapper.selectRouteInfoList(reqEntity);

        if (recList == null) {
            return null;
        }

        int j = 1;
        List<RouteListEntity>  retRecList = new ArrayList<RouteListEntity>();
        for (RouteListModel routeListModel : recList) {
    	  RouteListEntity routeListEntity = new RouteListEntity();
    	  routeListEntity.routeNo = new Integer (routeListModel.routeNo).toString();
    	  retRecList.add(routeListEntity);
	    }
        
        return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOhbPortRltList(アラーム情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    アラーム情報一覧
     * @retval    アラーム情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したアラーム情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    /*
    //@formatter:on
    @Transactional
    public List<OhbPortRltModel> getOhbPortRltList(String ohbId) {

    	List<OhbPortRltModel> ohbPortRltList = ohbPortRltMapper.selectListByOhbId(ohbId);
    	for (OhbPortRltModel ohbPortRltModel : ohbPortRltList) {
    		String storedTime = ohbPortRltModel.getStoredTime();
    		if(storedTime != null && !"".equals(storedTime)) {
        		String storedTime1 = storedTime.substring(0,4) + "/" + storedTime.substring(4,6) + "/" + storedTime.substring(6,8) + " " + storedTime.substring(8,10) + ":" + storedTime.substring(10,12) + ":" + storedTime.substring(12,14);
        		ohbPortRltModel.setStoredTime(storedTime1);
    		}
    		
		}
       
        return ohbPortRltList;
    }
    */
}
