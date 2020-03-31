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

import net.muratec.mcs.entity.info.ReqGetRouteInfoListEntity;
import net.muratec.mcs.entity.info.ResGetRouteInfoListEntity;
import net.muratec.mcs.entity.info.RouteListEntity;
import net.muratec.mcs.mapper.MacsMapper;
import net.muratec.mcs.mapper.PieceMapper;
import net.muratec.mcs.mapper.RouteListMapper;
import net.muratec.mcs.model.Macs;
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
    
    @Autowired private MacsMapper macsMapper;

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
    	  String time = timeFormat(routeListModel.transferTime);
    	  routeListEntity.transferTime = time;
    	  routeListEntity.transferCount = new Integer (routeListModel.transferCount).toString();
    	  routeListEntity.nextDstConnId1 = routeListModel.nextDstConnId1;
    	  //routeListEntity.nextDstPieceId1 = new Integer (routeListModel.nextDstPieceId1).toString();
    	  routeListEntity.nextDstPieceId1 = getPieceName(routeListModel.nextDstPieceId1);
    	  routeListEntity.nextDstConnId2 = routeListModel.nextDstConnId2;
    	  routeListEntity.nextDstPieceId2 = getPieceName(routeListModel.nextDstPieceId2);
    	  routeListEntity.nextDstConnId3 = routeListModel.nextDstConnId3;
    	  routeListEntity.nextDstPieceId3 = getPieceName(routeListModel.nextDstPieceId3);
    	  routeListEntity.nextDstConnId4 = routeListModel.nextDstConnId4;
    	  routeListEntity.nextDstPieceId4 = getPieceName(routeListModel.nextDstPieceId4);
    	  routeListEntity.nextDstConnId5 = routeListModel.nextDstConnId5;
    	  routeListEntity.nextDstPieceId5 = getPieceName(routeListModel.nextDstPieceId5);
    	  routeListEntity.nextDstConnId6 = routeListModel.nextDstConnId6;
    	  routeListEntity.nextDstPieceId6 = getPieceName(routeListModel.nextDstPieceId6);
    	  routeListEntity.nextDstConnId7 = routeListModel.nextDstConnId7;
    	  routeListEntity.nextDstPieceId7 = getPieceName(routeListModel.nextDstPieceId7);
    	  routeListEntity.nextDstConnId8 = routeListModel.nextDstConnId8;
    	  routeListEntity.nextDstPieceId8 = getPieceName(routeListModel.nextDstPieceId8);
    	  routeListEntity.nextDstConnId9 = routeListModel.nextDstConnId9;
    	  routeListEntity.nextDstPieceId9 = getPieceName(routeListModel.nextDstPieceId9);
    	  routeListEntity.nextDstConnId10 = routeListModel.nextDstConnId10;
    	  routeListEntity.nextDstPieceId10 = getPieceName(routeListModel.nextDstPieceId10);
    	  routeListEntity.nextDstConnId11 = routeListModel.nextDstConnId11;
    	  routeListEntity.nextDstPieceId11 = getPieceName(routeListModel.nextDstPieceId11);
    	  routeListEntity.nextDstConnId12 = routeListModel.nextDstConnId12;
    	  routeListEntity.nextDstPieceId12 = getPieceName(routeListModel.nextDstPieceId12);
    	  routeListEntity.nextDstConnId13 = routeListModel.nextDstConnId13;
    	  routeListEntity.nextDstPieceId13 = getPieceName(routeListModel.nextDstPieceId13);
    	  routeListEntity.nextDstConnId14 = routeListModel.nextDstConnId14;
    	  routeListEntity.nextDstPieceId14 = getPieceName(routeListModel.nextDstPieceId14);
    	  routeListEntity.nextDstConnId15 = routeListModel.nextDstConnId15;
    	  routeListEntity.nextDstPieceId15 = getPieceName(routeListModel.nextDstPieceId15);
    	  routeListEntity.nextDstConnId16 = routeListModel.nextDstConnId16;
    	  routeListEntity.nextDstPieceId16 = getPieceName(routeListModel.nextDstPieceId16);
    	  routeListEntity.nextDstConnId17 = routeListModel.nextDstConnId17;
    	  routeListEntity.nextDstPieceId17 = getPieceName(routeListModel.nextDstPieceId17);
    	  routeListEntity.nextDstConnId18 = routeListModel.nextDstConnId18;
    	  routeListEntity.nextDstPieceId18 = getPieceName(routeListModel.nextDstPieceId18);
    	  routeListEntity.nextDstConnId19 = routeListModel.nextDstConnId19;
    	  routeListEntity.nextDstPieceId19 = getPieceName(routeListModel.nextDstPieceId19);
    	  routeListEntity.nextDstConnId20 = routeListModel.nextDstConnId20;
    	  routeListEntity.nextDstPieceId20 = getPieceName(routeListModel.nextDstPieceId20);
    	  routeListEntity.nextDstConnId21 = routeListModel.nextDstConnId21;

    	  retRecList.add(routeListEntity);
	    }
        
        return retRecList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getPieceName(アラーム情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    pieceName
     * @retval    pieceName
     * @attention
     * @note      
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public String getPieceName(int pieceId) {
    	String pieceName = null;
    	if( 0 == pieceId) {
    		pieceName = "0";
    	}else {
    		List<Piece> selectAllPieceList = pieceMapper.selectAllPieceList();
        	for (Piece piece : selectAllPieceList) {
    			if(pieceId == piece.getPieceId()) {
    				pieceName = piece.getPieceName();
    			}
    		}
    	}
    	return pieceName;
    }
    
    /**
	 * 時間を'HH:MM:SS'フォーマットの文字列に変換します。
	 *
	 * @param millis 時間を表すlong値
	 * @return 'HH:MM:SS'フォーマットの文字列
	 */
	public static String timeFormat( int millis )
	{
		int abs = java.lang.Math.abs( millis );
		int hh  = abs / 3600;
		int mm  = ( abs / 60 ) % 60;
		int ss  = abs % 60;
		return ( ( millis < 0 )?  "-" : "" ) +
			   ( ( hh     < 10 )?  "0" : "" ) + String.valueOf( hh ) + ":" +
			   ( ( mm     < 10 )?  "0" : "" ) + String.valueOf( mm ) + ":" +
			   ( ( ss     < 10 )?  "0" : "" ) + String.valueOf( ss );
	}
	
	//@formatter:off
    /**
     ******************************************************************************
     * @brief     getDispRowList（画面表示レコード情報取得）機能
     * @param     reqEntity     画面表示(色情報含む)データ
     * @return    画面表示レコード情報と色情報データを取得する
     * @retval
     * @attention
     * @note      画面表示レコード情報と色情報データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ResGetRouteInfoListEntity getDispRowList(List<RouteListEntity> reqEntity) {

    	ResGetRouteInfoListEntity retList = new ResGetRouteInfoListEntity();

        List<String> rowColorList = new ArrayList<String>();
        String color = "";
        // レコード毎にレコードの詰め替えと文字色を取得
        for (RouteListEntity record : reqEntity) {
        	String selectRoute = record.selectRoute;
        	
        	if("Disable".equals(selectRoute)) {
        		color = "#707064";
        	}else {
        		color = "";
        	}
        	
            rowColorList.add(color);
        }

        retList.rowColorList = rowColorList;

        return retList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getSourceDestinationPieceSelectBox(アラーム情報一覧の取得)機能
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
    public List<String[]> getSourceDestinationPieceSelectBox() {

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
     * @brief     getTableNoSelectBox(アラーム情報一覧の取得)機能
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
    public List<String[]> getTableNoSelectBox() {

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
     * @brief     getTableNo
     * @param     reqEntity      リクエスト(検索条件)
     * @return    TableNo
     * @retval    
     * @attention
     * @note      
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public Macs getTableNo() {

    	Macs macs = macsMapper.selectTableNo();
        return macs;
    }
}
