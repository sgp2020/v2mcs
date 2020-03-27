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
import net.muratec.mcs.mapper.PieceMapper;
import net.muratec.mcs.mapper.RouteListMapper;
import net.muratec.mcs.model.Host;
import net.muratec.mcs.model.OhbModel;
import net.muratec.mcs.model.OhbPortRltModel;
import net.muratec.mcs.model.Piece;
import net.muratec.mcs.model.RouteList;
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
    
    @Autowired private PieceMapper pieceMapper;
    
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
    	  routeListEntity.NO = new Integer (j).toString();
    	  j++;
    	  routeListEntity.routeNo = new Integer (routeListModel.routeNo).toString();
    	  if(routeListModel.selectRoute == 0) {
    		  routeListEntity.selectRoute = "Disable";
    	  }else {
    		  routeListEntity.selectRoute = "Enable";
    	  }
    	  routeListEntity.priority = new Integer (routeListModel.priority).toString();
    	  routeListEntity.transferTime = new Integer (routeListModel.transferTime).toString();
    	  routeListEntity.transferCount = new Integer (routeListModel.transferCount).toString();
    	  routeListEntity.nextDstConnId1 = routeListModel.nextDstConnId1;
    	  routeListEntity.nextDstPieceId1 = new Integer (routeListModel.nextDstPieceId1).toString();
    	  routeListEntity.nextDstConnId2 = routeListModel.nextDstConnId2;
    	  routeListEntity.nextDstPieceId2 = new Integer (routeListModel.nextDstPieceId2).toString();
    	  routeListEntity.nextDstConnId3 = routeListModel.nextDstConnId3;
    	  routeListEntity.nextDstPieceId3 = new Integer (routeListModel.nextDstPieceId3).toString();
    	  routeListEntity.nextDstConnId4 = routeListModel.nextDstConnId4;
    	  routeListEntity.nextDstPieceId4 = new Integer (routeListModel.nextDstPieceId4).toString();
    	  routeListEntity.nextDstConnId5 = routeListModel.nextDstConnId5;
    	  routeListEntity.nextDstPieceId5 = new Integer (routeListModel.nextDstPieceId5).toString();
    	  routeListEntity.nextDstConnId6 = routeListModel.nextDstConnId6;
    	  routeListEntity.nextDstPieceId6 = new Integer (routeListModel.nextDstPieceId6).toString();
    	  routeListEntity.nextDstConnId7 = routeListModel.nextDstConnId7;
    	  routeListEntity.nextDstPieceId7 = new Integer (routeListModel.nextDstPieceId7).toString();
    	  routeListEntity.nextDstConnId8 = routeListModel.nextDstConnId8;
    	  routeListEntity.nextDstPieceId8 = new Integer (routeListModel.nextDstPieceId8).toString();
    	  routeListEntity.nextDstConnId9 = routeListModel.nextDstConnId9;
    	  routeListEntity.nextDstPieceId9 = new Integer (routeListModel.nextDstPieceId9).toString();
    	  routeListEntity.nextDstConnId10 = routeListModel.nextDstConnId10;
    	  routeListEntity.nextDstPieceId10 = new Integer (routeListModel.nextDstPieceId10).toString();
    	  routeListEntity.nextDstConnId11 = routeListModel.nextDstConnId11;
    	  routeListEntity.nextDstPieceId11 = new Integer (routeListModel.nextDstPieceId11).toString();
    	  routeListEntity.nextDstConnId12 = routeListModel.nextDstConnId12;
    	  routeListEntity.nextDstPieceId12 = new Integer (routeListModel.nextDstPieceId12).toString();
    	  routeListEntity.nextDstConnId13 = routeListModel.nextDstConnId13;
    	  routeListEntity.nextDstPieceId13 = new Integer (routeListModel.nextDstPieceId13).toString();
    	  routeListEntity.nextDstConnId14 = routeListModel.nextDstConnId14;
    	  routeListEntity.nextDstPieceId14 = new Integer (routeListModel.nextDstPieceId14).toString();
    	  routeListEntity.nextDstConnId15 = routeListModel.nextDstConnId15;
    	  routeListEntity.nextDstPieceId15 = new Integer (routeListModel.nextDstPieceId15).toString();
    	  routeListEntity.nextDstConnId16 = routeListModel.nextDstConnId16;
    	  routeListEntity.nextDstPieceId16 = new Integer (routeListModel.nextDstPieceId16).toString();
    	  routeListEntity.nextDstConnId17 = routeListModel.nextDstConnId17;
    	  routeListEntity.nextDstPieceId17 = new Integer (routeListModel.nextDstPieceId17).toString();
    	  routeListEntity.nextDstConnId18 = routeListModel.nextDstConnId18;
    	  routeListEntity.nextDstPieceId18 = new Integer (routeListModel.nextDstPieceId18).toString();
    	  routeListEntity.nextDstConnId19 = routeListModel.nextDstConnId19;
    	  routeListEntity.nextDstPieceId19 = new Integer (routeListModel.nextDstPieceId19).toString();
    	  routeListEntity.nextDstConnId20 = routeListModel.nextDstConnId20;
    	  routeListEntity.nextDstPieceId20 = new Integer (routeListModel.nextDstPieceId20).toString();
    	  routeListEntity.nextDstConnId21 = routeListModel.nextDstConnId21;

    	  retRecList.add(routeListEntity);
	    }
        
        return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getSourceDestinationPieceBox(アラーム情報一覧の取得)機能
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
    @Transactional(readOnly = true)
    public List<String[]> getSourceDestinationPieceBox() {

    	List<Piece> pieceList = pieceMapper.selectPieceListForSelectBox();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Piece piece : pieceList) {
            String[] data = new String[2];
            data[0] = piece.getPieceId().toString();
            data[1] = piece.getPieceAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTableNoBox(アラーム情報一覧の取得)機能
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
    @Transactional(readOnly = true)
    public List<String[]> getTableNoBox() {

    	List<RouteList> tableNoList = routeListMapper.selectTableNoListForSelectBox();

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (RouteList routeList : tableNoList) {
            String[] data = new String[2];
            data[0] = routeList.getTableNo().toString();
            data[1] = routeList.getTableNo().toString();
            
            selBoxList.add(data);
        }

        return selBoxList;
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
