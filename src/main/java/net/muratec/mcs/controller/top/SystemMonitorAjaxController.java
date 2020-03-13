//@formatter:off

/**
 ******************************************************************************
 * @file        SystemMonitorAjaxController.java
 * @brief       システムモニタ表示関連のajaxコントローラ
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.top;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.common.McsHandsOnTableCellInfoResEntity;
import net.muratec.mcs.entity.top.ResSiteMapEntity;
import net.muratec.mcs.entity.top.ResSystemMonitorEntity;
import net.muratec.mcs.entity.top.SystemMonitorEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.AuthenticationService;
import net.muratec.mcs.service.common.McsHandsOnTableService;
import net.muratec.mcs.service.top.SystemMonitorService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムモニタ表示関連のajaxコントローラクラス
 * @par       機能:
 *              getTopAjax（ステータス情報を取得する）
 *              sessionCheck（セッションのユーザ情報を取得する）
 *              getSystemMonitor（システムモニタ画面情報を取得する）
 *              getSiteMap（サイトマップ情報を取得する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class SystemMonitorAjaxController extends BaseAjaxController {

    // システムモニターサービス
    @Autowired private SystemMonitorService sysmonService;

    // 認証サービス
    @Autowired private AuthenticationService authService;

    // システムモニタ画面サービス
    @Autowired private McsHandsOnTableService handsOnTableService;
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ステータス情報を取得する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @return    取得結果
     * @retval
     * @attention
     * @note      ステータス表示に表示する各ステータスを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetTopAjax", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSystemMonitorEntity getTopAjax(HttpSession session, Locale locale)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // ステータス情報の取得
        // ------------------------------------
        ResSystemMonitorEntity resEntity = new ResSystemMonitorEntity();

        SystemMonitorEntity sysMonEntity = sysmonService.getState();
        
        //20200115 Song Add Start
        ServletContext servletContext = session.getServletContext();
        String[][] macsData = (String[][])servletContext.getAttribute("macsData");
        String[][] hostData = (String[][])servletContext.getAttribute("hostData");
        
        String[][] macsState = getState(macsData,"macs");
        String[][] hostState = getState(hostData,"host");
        
        String setMacsState = setMacsState( macsState );
        String setHostState = setHostState( hostState );
        sysMonEntity.macsText = setMacsState;
        sysMonEntity.hostText = setHostState;
        
        String macsStateColor = getMacsStateColor( setMacsState );
        String hostStateColor = getHostStateColor( setHostState );
        sysMonEntity.macsColor = macsStateColor;
        sysMonEntity.hostColor = hostStateColor;
        
        String version = getVersion(macsData);
        String myMacsName = getMyMacsName(macsData);
        sysMonEntity.softwareVersionText =  myMacsName + version; //SystemMonitor.jsp For  Software Version
        //sysMonEntity.guiVer =  myMacsName + version; //SystemMonitor.jsp For  Database Version
        

        //20200115 Song Add End
        
        resEntity.body = sysMonEntity;

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";
        return resEntity;
    }
    
    //20200115 Song Add Start
	/**
	 * MACS_DATA.csvより、MACSのバージョン情報を取得する
	 */
	public String getVersion(String[][] macsData)
	{
		if( macsData == null ) return "";
		
		//return "Ver" + macsData[0][1];
		return "  " + macsData[0][1];
	}
	
	/**
	 * MACS_DATA.csvより、MACSの名称を取得する
	 */
	public String getMyMacsName(String[][] macsData)
	{
		if( macsData == null ) return "";
		
		return macsData[0][0];
	}
	
	/**
	 * MACS or HOSTのステータスを返します
	 */
	public String[][] getState(String[][] data,String fileKind)
	{
		String state[][] = null;
		try
		{
			if( data != null )
			{
				if( "macs".equals( fileKind ) )
				{
					state = new String[1][1];
					state[0][0] = data[0][2];
				}
				else
				{
					state = new String[data.length][2];
					
					for( int i=0; i<data.length; i++ )
					{
						state[i][0] = data[i][2];
						state[i][1] = data[i][4];
					}
				}
			}
		}
		catch( Exception e )
		{
//			System.out.println( "File Read Error : " + fileKind );
			;
		}
		
		return state;
	}
	
	/**
	 * MACSの状態の表示を更新する
	 */
	private String setMacsState( String[][] state )
	{
		String tmp = State.MACS_CONTROL_NONE;
		
		if( state != null && state.length != 0 )
		{
			tmp = state[0][0];
		}
		
		return tmp;
		//stateIndicator.setText( tmp );
		//stateIndicator.setBackground( getMacsStateColor( tmp ) );
		//stateIndicator.repaint();
	}
	
	/**
	 * HOSTの状態の表示を更新する
	 */
	private String setHostState( String state[][] )
	{
		String hostState = "No HOST";
		
		if( state != null && state.length != 0 )
		{
			int commNum    = 0;
			int notCommNum = 0;
			
			for( int i=0; i<state.length; i++ )
			{
				// Downのデータは無視する
				if( State.COMM_STATE_DOWN.equals( state[i][0] ) ) continue;
				
				if( State.HOST_STATE_COMMUNICATING.equals( state[i][1] ) )
				{
					// このHOSTのstateはCommunicating
					commNum++;
				}
				else
				{
					// このHOSTのstateはNotCommunicating
					notCommNum++;
				}
			}
			
			if( commNum > 0 && notCommNum == 0 )
			{
				// CommunicatingのHOSTがいて、かつ
				// NotCommunicatingのHOSTがいない場合はCommunicating
				hostState = State.COMM_STATE_COMMUNICATING;
			}
			else if( commNum == 0 && notCommNum == 0 )
			{
				// 全部Downの場合
				hostState = "No HOST";
			}
			else
			{
				// １つでもNotCommunicatingである、もしくは
				// ホストのデータはあるが全てDownの場合はNotCommunicating
				hostState = State.COMM_STATE_NOT_COMMUNICATING;
			}
		}
		return hostState; 
		//hostStateIndicator.setText( hostState );
		//hostStateIndicator.setBackground( getHostStateColor( hostState ) );
		//hostStateIndicator.repaint();
	}
	
	/**
	 * MACSの状態をあらわすステータス色を返す
	 */
	//private Color getMacsStateColor( String macsState ) 
	private String getMacsStateColor( String macsState ) 
	{
		if( State.MACS_STATE_ONLINE.equals( macsState ) )
		{
			Short a = 0;
			Short b = 255;
			Short c = 0;
			return ComFunction.rgbToHex(a, b, c);
			//return Color.green;
		}
		else if( State.MACS_STATE_OFFLINE.equals( macsState ) ||
				 State.MACS_STATE_INHIBIT.equals( macsState ) ||
				 State.MACS_STATE_INHIBITED.equals( macsState ) )
		{
			Short a = 255;
			Short b = 255;
			Short c = 0;
			return ComFunction.rgbToHex(a, b, c);
			//return Color.yellow;
		}
		else if( State.MACS_STATE_INIT.equals( macsState ) ||
			State.MACS_STATE_INHIBITING.equals( macsState ) )
		{
			Short a = 255;
			Short b = 128;
			Short c = 64;
			return ComFunction.rgbToHex(a, b, c);
			//return new Color( 255, 128, 64 );
		}
		else if( State.MACS_STATE_DOWN.equals( macsState ) )
		{
			Short a = 255;
			Short b = 0;
			Short c = 0;
			return ComFunction.rgbToHex(a, b, c);
			//return Color.red;
		}
		else
		{
			Short a = 142;
			Short b = 2;
			Short c = 29;
			return ComFunction.rgbToHex(a, b, c);
			//return new Color( 142, 2, 29 );
		}
	}
	
	/**
	 * Hostの状態をあらわすステータス色を返す
	 */
	//private Color getHostStateColor( String hostState ) 
	private String getHostStateColor( String hostState ) 
	{
		if( State.COMM_STATE_COMMUNICATING.equals( hostState ) ) 
		{
			Short a = 0;
			Short b = 255;
			Short c = 0;
			return ComFunction.rgbToHex(a, b, c);
			//return Color.green;
		}
		else if( "No HOST".equals( hostState ) )
		{
			Short a = 222;
			Short b = 222;
			Short c = 222;
			return ComFunction.rgbToHex(a, b, c);
			//return new Color( Integer.parseInt( "DEDEDE", 16 ) );
		}
		else 
		{
			Short a = 255;
			Short b = 0;
			Short c = 0;
			return ComFunction.rgbToHex(a, b, c);
			//return Color.red;
		}
	}
	//20200115 Song Add End
	
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     セッション情報（ユーザ情報）を取得する機能
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     session        セッション情報（Frameworkより付加）
     * @return    取得結果
     * @retval
     * @attention
     * @note      セッション情報（ユーザ情報）を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/SessionCheck", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSystemMonitorEntity sessionCheck(HttpSession session, Locale locale)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // セッションよりユーザ情報を取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = this.getUserInfo(session);

        // セッション情報が取れなかった場合はNOLOGINで生成する。
        if (sessionUserInfo == null) {
            // NOLOGINユーザ情報の取得
            AuthenticationEntity noLoginUser = authService.getNoLoginUserInfo();
            // 認証情報をセッションに保存＆変数に再格納
            this.setSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO, noLoginUser);
            sessionUserInfo = this.getSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO,
                    AuthenticationEntity.class);
        }

        // ------------------------------------
        // 戻り値（正常）を返却
        // ------------------------------------
        ResSystemMonitorEntity resEntity = new ResSystemMonitorEntity();

        // ユーザ情報セット
        SystemMonitorEntity sysMonEntity = new SystemMonitorEntity();
        sysMonEntity.userId = sessionUserInfo.userId.toString();
        sysMonEntity.userName = sessionUserInfo.userName;
        sysMonEntity.description = sessionUserInfo.description;
        sysMonEntity.autoLogoutTime = Integer.toString(sessionUserInfo.autoLogoutTime);
        resEntity.body = sysMonEntity;

        // 戻り値セット
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";
        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     システムモニタ画面情報を取得する機能
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     session        セッション情報（Frameworkより付加）
     * @return    取得結果
     * @throws McsException
     * @retval
     * @attention
     * @note      システムモニタ画面情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetSystemMonitor", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    //public McsHandsOnTableCellInfoResEntity getSystemMonitor(@RequestBody AjaxReqBaseEntity reqEntity,  //20200107 Song Del
    //        HttpSession session, Locale locale, Model model) throws McsException {
    public McsHandsOnTableCellInfoResEntity getSystemMonitor(HttpServletRequest  request,@RequestBody AjaxReqBaseEntity reqEntity, //20200107 Song Add
            HttpSession session, Locale locale, Model model) throws McsException {

        McsHandsOnTableCellInfoResEntity resEntity = new McsHandsOnTableCellInfoResEntity();
        
        //20200107 Song Add Start FOR DB→CSV
        // ファイルからデータを取得
        String contextPath = session.getServletContext().getContextPath();//get project name
        //String localIp=request.getLocalAddr();//get local ip
        String url="http://" + request.getServerName() //Server Ip  
                   + ":"   
                   + request.getServerPort()           //port Number
                   + contextPath
                   + "/resources/";
                   //+ request.getRequestURI();
        
        //handsOnTableService.setIpPath(url);
        
        ServletContext servletContext = session.getServletContext();
        List<ScreenMonitor> screenMonitorList1 = (ArrayList<ScreenMonitor>)servletContext.getAttribute("screenMonitorList1");
        List<ScreenMonitorMember> screenMonitorMemberList1 = (ArrayList<ScreenMonitorMember>)servletContext.getAttribute("screenMonitorMemberList1");
        List<ScreenColorMaster> screenColorMasterList1 = (ArrayList<ScreenColorMaster>)servletContext.getAttribute("screenColorMasterList1");
        handsOnTableService.setScreenMonitorList1(screenMonitorList1);
        handsOnTableService.setScreenMonitorMemberList1(screenMonitorMemberList1);
        handsOnTableService.setScreenColorMasterList1(screenColorMasterList1);
        //20200107 Song Add End
        
        
        // セル内データ要素取得（ラベル、アイコン）
        resEntity.body.data.addAll(handsOnTableService.getLabel());
        resEntity.body.data.addAll(handsOnTableService.getIcon());

        // セル内データ要素のソート
        handsOnTableService.sortCellData(resEntity.body.data);

        // ボーダー情報取得
        resEntity.body.customBorders.addAll(handsOnTableService.getBorder());

        // セルサイズ取得
        List<List<Integer>> sizeList = handsOnTableService.getRowColSize();
        resEntity.body.widths = sizeList.get(0);
        resEntity.body.heights = sizeList.get(1);

        // マージ情報取得
//        resEntity.body.mergeCells = handsOnTableService.getMerge();//20200114 DQY DEL

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     サイトマップ情報を取得する機能
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     session        セッション情報（Frameworkより付加）
     * @return    取得結果
     * @throws McsException
     * @retval
     * @attention
     * @note      サイトマップに表示するカテゴリとボタン情報を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetSiteMap", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSiteMapEntity getSiteMap(HttpSession session, Locale locale)
            throws AjaxAurgumentException, McsException {

        ResSiteMapEntity resEntity = new ResSiteMapEntity();

        // ------------------------------------
        // セッションよりユーザ情報を取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = this.getUserInfo(session);

        // セッション情報が取れなかった場合はNOLOGINで生成する。
        if (sessionUserInfo == null) {
            // NOLOGINユーザ情報の取得
            AuthenticationEntity noLoginUser = authService.getNoLoginUserInfo();
            // 認証情報をセッションに保存＆変数に再格納
            this.setSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO, noLoginUser);
            sessionUserInfo = this.getSessionAttribute(session, ComConst.SessionKey.LOGIN_USER_INFO,
                    AuthenticationEntity.class);
        }

        // ------------------------------------
        // セル内データ要素取得（ラベル、ボタン）
        // ------------------------------------
        resEntity.body.label.addAll(sysmonService.getSiteMapLabel());
        resEntity.body.button.addAll(sysmonService.getSiteMapButton(sessionUserInfo.userId));

        return resEntity;
    }
}
