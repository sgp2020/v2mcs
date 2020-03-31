//@formatter:off
/**
 ******************************************************************************
 * @file        RouteInfoController.java
 * @brief       アラーム情報表示関連のコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                     SGP                          
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.Macs;
import net.muratec.mcs.model.Piece;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.info.RouteInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示関連のコントローラクラス
 * @par       機能:
 *              RouteInformation（初期状態表示）
 *              getCsvFile（CSV出力）
 *              createCsvHeaderRecords（CSVヘッダー情報生成）
 *              createCsvTitleRecords（CSVTitle情報生成）
 *              createCsvItem（CSV項目オブジェクト生成）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class RouteInfoController extends BaseController {

    /** メッセージリソース */
    //@Autowired private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(RouteInfoController.class);

    public static Logger getLogger() {

        return logger;
    }

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;
    
    // ------------------------------------
    // アラーム情報画面用サービス
    // ------------------------------------
    @Autowired private RouteInfoService routeInfoService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     RouteInfo（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(アラーム情報表示)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/RouteInfo", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_ROUTE, logOperationType = ComConst.LogOperationType.GET, number = 1L)
    public String routeInfo(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_ROUTE.getRefAuthFuncId());

        // ----------------------------------------------
        // 自動更新機能の有効化
        // ----------------------------------------------
        autoReload.setInterval(model);

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        /*
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;
        */
        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> pieceList = routeInfoService.getSourceDestinationPieceSelectBox();
        List<String[]> tabelNoList = routeInfoService.getTableNoSelectBox();
        Macs macs= routeInfoService.getTableNo();
        
        Short routeState = macs.getRouteState();
        List<String[]> routeStateList = RouteState(routeState);
        Short tableNo = macs.getTableNo();
        
        //tscIdBoxはAllを初期化表示する
        //hostNameBoxList.add(0, allTerms);
        //commStateBoxList.add(0, allTerms);

        // セレクトボックス要素をJSON化
       String pieceListJson = super.objectToJson(pieceList);
       String tabelNoJson = super.objectToJson(tabelNoList);
       String routeStateJson = super.objectToJson(routeStateList);
       
       model.addAttribute("II_009_00_001", pieceListJson);
       model.addAttribute("II_009_00_002", tabelNoJson);
       model.addAttribute("II_009_00_003", routeStateJson);
       model.addAttribute("II_009_00_004", tableNo.toString());
       
       // バージョン情報付与
       ComFunction.setVersion(model);

       return "info/RouteInfo";
    }
    
    
    
	/**
	 * Route Stateの色とキャプションを更新します
	 * 
	 * State : 0 -> None
	 *         1 -> Creating
	 *         2 -> Calculating
	 *         3 -> Completed
	 *         9 -> Failed
	 */
    private List<String[]> RouteState(Short routeState)
	{
		/** ROUTE_STATEの状態を表します */
		String STATE_NONE        = "0";
		String STATE_CREATING    = "1";
		String STATE_CALCULATING = "2";
		String STATE_COMPLETED   = "3";
		String STATE_FAILED      = "9";
		
		String[] routeState1 = new String[2];
		
		if( routeState != null  )
		{
			String routeState2 = routeState.toString();
			
			if( STATE_NONE.equals( routeState2 ) )
			{
				routeState1[0] = "#00FFFF";  //java.awt.Color.cyan
				routeState1[1] = "None" ;
			}
			else if( STATE_CREATING.equals( routeState2 ) )
			{
				routeState1[0] = "#FFC800";  //Color.orange
				routeState1[1] = "Creating" ;
			}
			else if( STATE_CALCULATING.equals( routeState2 ) )
			{
				routeState1[0] = "#FFAFAF";  //Color.pink
				routeState1[1] = "Calculating" ;
			}
			else if( STATE_COMPLETED.equals( routeState2 ) )
			{
				routeState1[0] = "#00FF00";  //Color.green
				routeState1[1] = "Completed" ;
			}
			else if( STATE_FAILED.equals( routeState2 ) )
			{
				routeState1[0] = "#FF0000";  //Color.red
				routeState1[1] = "Failed" ;
			}
			else
			{
				routeState1[0] = "#FF0000"; //Color.red
				routeState1[1] = routeState2 ;
			}
		}
		else
		{
			routeState1[0] = "";
			routeState1[1] = "";
		}
		
		List<String[]> selBoxrouteStateList = new ArrayList<String[]>();

	    selBoxrouteStateList.add(routeState1);

	    return selBoxrouteStateList;
	}    
}
