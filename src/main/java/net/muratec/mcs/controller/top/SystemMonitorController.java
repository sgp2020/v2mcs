//@formatter:off
/**
 ******************************************************************************
 * @file        SystemMonitorController.java
 * @brief       システムモニタ表示関連のコントローラ
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
 * 2018/11/30 MACS4#0059  M17対応                                     T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.top;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.ScreenColorMasterMapper;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.maint.SystemParameterService;
import net.muratec.mcs.service.top.SystemMonitorService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     システムモニタ表示関連のコントローラクラス
 * @par       機能:
 *              systemMonitor（システムモニタ表示画面を表示する）
 *              remarks（凡例画面を表示する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class SystemMonitorController extends BaseController {

    // セレクトボックスサービス
    @Autowired private SelectBoxService selBoxService;

    // セレクトボックスサービス
    @Autowired private SystemMonitorService sysMonitorService;

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;

    // システムパラメータサービス
    @Autowired private SystemParameterService systemParameterService;
    
    /** 色設定テーブルマッパー */
    @Autowired private ScreenColorMasterMapper screenColorMasterMapper;   //MACS4#MACSV2  20200106 DQY ADD
    
    //20200110 DQY ADD START
  	/**  */
  	private String[][] TSCMaster;
  	
  	/**  */
  	private String[][] monitorData;
  	
  	/**  */
  	private String[][] colorMaster;
  	
  	/**  */
  	private final String SCREEN_MONITOR = "SCREEN_MONITOR.csv";
  	
  	/**  */
  	private final String SCREEN_MONITOR_MEMBER = "SCREEN_MONITOR_MEMBER.csv";
  	
  	/**  */
  	private final String SCREEN_COLOR_MASTER = "SCREEN_COLOR_MASTER.csv";
  	
  	/**  */
  	private final String HOST = "HOST.csv";
  	
  	/**  */
  	private final String MACS = "MACS.csv";
  	
  	
  	//20200110 DQY ADD END
    
    //20200107 Song Add Start
    private static List<ScreenMonitor> screenMonitorList1  = new ArrayList<ScreenMonitor>();
    private static List<ScreenMonitorMember> screenMonitorMemberList1  = new ArrayList<ScreenMonitorMember>();
    private static List<ScreenColorMaster> screenColorMasterList1  = new ArrayList<ScreenColorMaster>();
    private static String[][] hostData = null;
    private static String[][] macsData = null;
    //20200107 Song Add End

    //@formatter:off
    @SuppressWarnings("deprecation")
	/**
     ******************************************************************************
     * @brief     システムモニタ表示画面を表示する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先パス
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/", method = RequestMethod.GET)
    //public String systemMonitor(HttpSession session, Locale locale, Model model) throws McsException {  //20200111 Song Del
    public String systemMonitor(HttpServletRequest  request,HttpSession session, Locale locale, Model model) throws McsException {//20200111 Song Add    
	
    	//20200107 Song Add Start FOR DB→CSV
    	screenMonitorList1.clear();
    	screenMonitorMemberList1.clear();
    	screenColorMasterList1.clear();
    	
        // ファイルからデータを取得
        String contextPath = session.getServletContext().getContextPath();//get project name
        //String localIp=request.getLocalAddr();//get local ip
        String url="http://" + request.getServerName() //Server Ip  
                   + ":"   
                   + request.getServerPort()           //port Number
                   + contextPath
                   + "/resources/";
                   //+ request.getRequestURI();
        
        //if(screenMonitorList1 == null || screenMonitorList1.isEmpty()) {
        
    	setData(url,SCREEN_MONITOR);
    	setData(url,SCREEN_MONITOR_MEMBER);
    	setData(url,SCREEN_COLOR_MASTER);
    	
    	ServletContext servletContext = session.getServletContext();
    	servletContext.setAttribute("screenMonitorList1", screenMonitorList1);
    	servletContext.setAttribute("screenMonitorMemberList1", screenMonitorMemberList1);
    	servletContext.setAttribute("screenColorMasterList1", screenColorMasterList1);
    	
    	 String userName = ComConst.ConstUserId.NOLOGIN;
         String sessionAttribute = (String) session.getAttribute(ComConst.SessionKey.LOGIN_USER_INFO);
         AuthenticationEntity authEntity = (AuthenticationEntity) gson.fromJson(sessionAttribute,
                 AuthenticationEntity.class);
        
	    if( authEntity!= null ) { 
		    userName = authEntity.userName; 
		    if(userName != null && userName.length() != 0 && !"NOLOGIN".equals(userName)) { 
			    setData(url,HOST); setData(url,MACS);
		  	    servletContext.setAttribute("hostData", hostData);
		  	    servletContext.setAttribute("macsData", macsData); 
		    }else {
		        servletContext.setAttribute("hostData", null);
	            servletContext.setAttribute("macsData", null); 
	        } 
	    }else { //For onClick Logout
	    	servletContext.setAttribute("hostData", null);
            servletContext.setAttribute("macsData", null);
	    }
		 
	    
	    //20200121 Song Add For Judge pieceId Start
	    String[] type = null;
		
		if (TSCMaster != null)
		{
			type = getTscTypes();
		}
		
		String type1 = null;
		String[][] values;
		for( int i=0; i<type.length; i++ )
		{
			type1 = type[i];
			if( ( values = getTscData( type1 ) ) != null )
			{
				String[] aoValue = null;
				for( int j=0; j<values.length; j++ )
				{
					aoValue =  values[j];
					String caption = aoValue[1];
					//System.out.println(caption);
					Hashtable aliasToIdTbl = new Hashtable();
					String pieceId = (String)aliasToIdTbl.get( caption );
					//System.out.println(pieceId);
				}
			}
		}
		//20200121 Song Add For Judge pieceId End
		
        //}
        
        //20200107 Song Add End
        

        // ------------------------------------
        // ユーザ情報のチェック＆セット
        // ------------------------------------
        super.setUserInfoTop(session, model, locale, null);

        // ------------------------------------
        // 自動更新機能の有効化
        // ------------------------------------
        autoReload.setIntervalByKey(model, ComConst.SystemParameter.Key.AUTO_RELOAD_INTERVAL_SYSTEM_MONITOR);

        // ----------------------------------------------
        // AMHS_TYPE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> amhsBoxList = selBoxService.getAmhsString();
        model.addAttribute("IM_010_01_001", super.objectToJson(amhsBoxList));
        // OHBとVOHBタイプを除く
        List<String[]> amhsTypeByAmhsLStateChg = new ArrayList<String[]>();
        for (String[] amhsType: amhsBoxList) {
            short amhsTypeCode = new Short(amhsType[0]).shortValue();
            if (amhsTypeCode != ComConst.AmhsType.CODE_OHBC && amhsTypeCode != ComConst.AmhsType.CODE_VOHBC) {
                amhsTypeByAmhsLStateChg.add(amhsType);
            }
        }
        model.addAttribute("IM_902_01_001", super.objectToJson(amhsTypeByAmhsLStateChg));
        model.addAttribute("IM_903_01_001", super.objectToJson(amhsBoxList));
        List<String[]> amhsSelBox = new ArrayList<String[]>();
        String allAmhsTypeSel[] = { "ALL", "ALL" };
        amhsSelBox.add(allAmhsTypeSel);
        amhsBoxList.addAll(amhsSelBox);
        String amhsBoxJson = super.objectToJson(amhsBoxList);
        model.addAttribute("IM_011_01_001", amhsBoxJson);
        // ----------------------------------------------
        // AMHS_LOGICAL_STATE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> amhsLState = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_AMHS_LOGICAL_STATE);
        // 一部内容をリストから削除
        List<String[]> amhsLStateDispList = new ArrayList<String[]>();
        for (String[] strAmhsLState : amhsLState) {
            if (!strAmhsLState[0].equals(ComConst.AmhsLogicalState.STR_CODE_PRE_PM)) {
                if (!strAmhsLState[0].equals(ComConst.AmhsLogicalState.STR_CODE_PM)) {
                    amhsLStateDispList.add(strAmhsLState);
                } else {
                    // PMの場合は制御にPrePMの値で要求するため、valueの書き換えを行う
                    strAmhsLState[0] = ComConst.AmhsLogicalState.STR_CODE_PRE_PM;
                    amhsLStateDispList.add(strAmhsLState);
                }
            }
        }
        model.addAttribute("IM_902_01_002", super.objectToJson(amhsLStateDispList));
        // ----------------------------------------------
        // PORT_LOGICAL_STATE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> portLState = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_PORT_LOGICAL_STATE);
        model.addAttribute("IM_903_01_002", super.objectToJson(portLState));
        // ----------------------------------------------
        // UNIT_LOGICAL_STATE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> unitLState = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_UNIT_LOGICAL_STATE);
        model.addAttribute("IM_904_01_001", super.objectToJson(unitLState));
        // ----------------------------------------------
        // OHB_LOGICAL_STATE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> ohbLState = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_OHB_LOGICAL_STATE);
        // 一部内容をリストから削除
        List<String[]> ohbLStateDispList = new ArrayList<String[]>();
        for (String[] strOhbLState : ohbLState) {
            if (!ComConst.OhbLogicalState.STR_CODE_PRE_PM.equals(strOhbLState[0])) {
                if (!ComConst.OhbLogicalState.STR_CODE_PM.equals(strOhbLState[0])) {
                    ohbLStateDispList.add(strOhbLState);
                } else {
                    // PMの場合は制御にPrePMの値で要求するため、valueの書き換えを行う
                    strOhbLState[0] = ComConst.OhbLogicalState.STR_CODE_PRE_PM;
                    ohbLStateDispList.add(strOhbLState);
                }
            }
        }
        model.addAttribute("IM_905_01_001", super.objectToJson(ohbLStateDispList));
        // ----------------------------------------------
        // MCS_LOGICAL_STATE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> mcsLState = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_MCS_LOGICAL_STATE);
        model.addAttribute("IM_906_01_001", super.objectToJson(mcsLState));
        // ----------------------------------------------
        // TRANS_MODE_STRING用セレクトボックスを生成
        // ----------------------------------------------
        List<String[]> transModeList = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_TRASN_MODE);
        List<String[]> transModeDispList = new ArrayList<String[]>();
        for (String[] strTransMode: transModeList) {
            if (ComConst.TransMode.STR_ENABLE_TOOL_BUFFER.equals(strTransMode[0]) ||
                ComConst.TransMode.STR_DISABLE_TOOL_BUFFER.equals(strTransMode[0])) {
                transModeDispList.add(strTransMode);
            }
        }
        model.addAttribute("IM_907_01_001", super.objectToJson(transModeDispList));
        // MACS4#0059 Add Start
        List<String[]> selectTargetList = selBoxService.getStrMstBoxOrderByCode(ComConst.StringMaster.KEY_M17_SELSECT_TARGET_STRING);
        model.addAttribute("IM_046_01_001", super.objectToJson(selectTargetList));
        // MACS4#0059 Add End

        // ----------------------------------------------
        // 個別モニタのウィンドウ数制限
        // ----------------------------------------------
        Integer limit = systemParameterService.getInt(ComConst.SystemParameter.Category.GUI,
                ComConst.SystemParameter.Section.SYSTEM_MONITOR, ComConst.SystemParameter.Key.LIMIT_INDIVIDUAL_MONITOR);
        model.addAttribute("LimitIndividualMonitorNum", limit);

        // バージョン情報付与
        ComFunction.setVersion(model);

        return "/top/SystemMonitor";
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     凡例画面を表示する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先パス
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Remarks", method = RequestMethod.POST)
    public String remarks(HttpSession session, Locale locale, Model model) throws McsException {
//    	String[] stateColors = new String[1000];
//    	stateColors = getAllColors();
        // ------------------------------------
        // ユーザ情報のチェック＆セット
        // ------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ----------------------------------------------
        // DBより凡例の閾値を取得
        // ----------------------------------------------
        Map<String, String> occuRateMap = sysMonitorService.getOccupiedRate();
        model.addAttribute("IT_001_27_009", super.objectToJson(occuRateMap.get("lowRate")));
        model.addAttribute("IT_001_27_010", super.objectToJson(occuRateMap.get("middleRate")));
        model.addAttribute("IT_001_27_011", super.objectToJson(occuRateMap.get("highRate")));
        
        String[] colors = new String[1000]; 
		String[] states = new String[1000]; 
		List<ScreenColorMaster> screenColorMaster = screenColorMasterMapper.selectAllColor(); //SCREEN_COLOR_MASTER
		int i = 0 ;
		int j = 0 ;
		for(ScreenColorMaster stateColor:screenColorMaster) 
		{
			  colors[i] =  ComFunction.rgbToHex(stateColor.getRedParam(),stateColor.getGreenParam(), stateColor.getBlueParam());
			  states[i] = stateColor.getColorName().substring(3);
			  if(i<10) {
				  model.addAttribute("IT_001_27_02"+i,states[i]);
				  model.addAttribute("IT_001_27_04"+i,colors[i]);
			  }
			  else {
				  j = i-10;
				  model.addAttribute("IT_001_27_03"+j,states[i]);
				  model.addAttribute("IT_001_27_05"+j,colors[i]);
			  }
			  i++;				  
		 }
        // バージョン情報付与
        ComFunction.setVersion(model);

        return "/top/Remarks";
    }
    
  //20200106 DQY ADD START
    // MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
    private String[] getAllColors() {
    	
		  String[] colors = new String[1000]; 
		  String[] states = new String[1000]; 
//		  ScreenColorMaster screenColorMaster = (ScreenColorMaster) screenColorMasterMapper.selectAllColor(); //查询某个颜色的3个色素
		  List<ScreenColorMaster> screenColorMaster = screenColorMasterMapper.selectAllColor(); //查询某个颜色的3个色素
		  for(ScreenColorMaster stateColor:screenColorMaster) 
		  {
			  int i = 0 ;
			  colors[i] =  ComFunction.rgbToHex(stateColor.getRedParam(),stateColor.getGreenParam(), stateColor.getBlueParam());
			  states[i] = stateColor.getColorName().substring(3);
			  i++;				  
		  }
		  return colors;
    }
    
    
    
    
    //20200106 Song Add start
    
  	/**
  	 * 指定された、CSVのあるディレクトリとファイルから必要なデータの取得。オリジナル版。
  	 * @throws IOException
  	 * @throws HttpException
  	*/
  	private String getCsvContents(String dir, String fileKind){
  		
  		HttpClient client  = new HttpClient();
  		
  		String retStr = null; //Get file contents 
  		
  		// ないならば、作成して登録しておく。
          //System.out.println("http://" + GuiEnvironment.getHostName() + "/" + dir + "/" + fileKind);
  		//HttpMethod 	method = new GetMethod("http://127.0.0.1:8080/v2mcs/resources/SCREEN_MONITOR.csv");
  		HttpMethod 	method = new GetMethod(dir + fileKind);
  		try {
  			  client.executeMethod(method);
  			  if(method.getStatusCode( ) == HttpStatus.SC_NOT_MODIFIED ) {
  			    	//System.out.println( "Content not modified since last request" );
  			  } else if(method.getStatusCode() == HttpStatus.SC_OK) { 
  			    	retStr =  method.getResponseBodyAsString(); // Get contents...
  			  }
  			  if( retStr != null ) {
  				  // 改行コードの削除
  				  retStr = retStr.replaceAll( "\n" , "" );
  			  }
  		} catch (HttpException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch ( Exception e){
  			e.printStackTrace();
  		} finally{
  			method.releaseConnection( );
  		}
  		return retStr;
  	}
  	
  	/**
  	 * 指定の文字列内にある全てのbeforeをreplacementに置換した結果
  	 * 生成される新しい文字列を返します｡
  	 *
  	 * @param base 置換対象となる文字列
  	 * @param before 置換したい文字列
  	 * @param replacement 置換後の文字列
  	 * @return 置換後の文字列
  	 */
  	public static String replaceAll( String base, String before, String replacement )
  	{
  		StringBuffer regex = new StringBuffer();
  		char[] upperChars = before.toUpperCase().toCharArray();
  		char[] lowerChars = before.toLowerCase().toCharArray();
  		for( int i=0; i<upperChars.length; i++ )
  		{
  			regex.append( "[" );
  			regex.append( upperChars[i] );
  			regex.append( lowerChars[i] );
  			regex.append( "]" );
  		}

  		return base.replaceAll( regex.toString(), replacement );
  	}
  	
  	/**
  	 * 
  	 */
  	private String[] mySplit( String array )
  	{
  		int cnt = 0;
  		int count = 0;
  		String[] strArray;
  		StringBuffer strb = new StringBuffer();
  		
  		for( int i=0; i<array.length(); i++ )
  		{
  			if( !",".equals( String.valueOf( array.charAt( i ) ) ) ) continue;
  			
  			count++;
  		}
  		
  		strArray = new String[count+1];
  		
  		for( int i=0; i<strArray.length; i++ )
  		{
  			int tmp = array.indexOf( ",", cnt );
  			
  			if( tmp == -1 )
  			{
  				strArray[i] = array.substring( cnt, array.length() );
  			}
  			else if( tmp == 1 )
  			{
  				strArray[i] = "";
  			}
  			else
  			{
  				strArray[i] = array.substring( cnt, tmp );
  			}
  			cnt = tmp + 1;
  		}
  		
  		return strArray;
  	}
  	//20200106 Song Add end
    
    //20200110 DQY ADD START
	/**
	 * 各種データを取得します
	 * 
	 * SCREEN_MONITOR.csvより、各TSCIDとTSC_NAME、ZONE情報等を取得
	 * SCREEN_MONITOR_MEMBER.csvより、各TSCの状態を取得
	 * COLOR_MASTER.csvより、色の情報を取得
	 * 
	 * SCREEN_MONITOR_MEMBER : 
	 *   DISPLAY_ID, DISPLAY_NAME, TABLE_NAME, MEMBER_GROUP, GROUP_NUMBER, SEPARATE, CONTROL_STATE, 
	 *   SYSTEM_STATE, ALARM_STATE, COMM_STATE, TSC_MODE, PIECE_MODE, TSC_ABBREVIATION, PIECE_ABREVIATION, PORT_STATE
	 * SCREEN_MONITOR : 
	 *   DISPLAY_ID, DISPLAY_NUMBER, COLOR, TEXT
	 * SCREEN_COLOR_MASTER : 
	 *   COLOR, COLOR_NAME, RED_PARAM, GREEN_PARAM, BLUE_PARAM
	 */
	private void setData( String dir, String fileKind )
	{
		String strBuf = getCsvContents(dir,fileKind);
		if( strBuf == null || strBuf.length()==0){
			//if( fileKind.equals( HOST ) ) {
			//	hostData = null;
			//}
			/* 読んでも何もなかった。*/
			//return ;
		}
		
		// 取得した値を配列に変換する
		String[] array = replaceAll( strBuf, "'", "" ).split( ";" );
		
		// 配列を","区切りで更に細分化する
		if( fileKind.equals( SCREEN_MONITOR_MEMBER ) ) 
		{	
    		TSCMaster = new String[array.length][];
    		
    		for (int i = 0; i < array.length; i++) {
    			TSCMaster[i] = mySplit( array[i] );
    		}
    		
    		for( int i=0; i<TSCMaster.length; i++ )
    		{
				ScreenMonitorMember smm = new ScreenMonitorMember();
				smm.setDisplayId(new Integer(TSCMaster[i][0])); 
				smm.setDisplayName(TSCMaster[i][1]);
				smm.setTableName(TSCMaster[i][2]);
				smm.setMemberGroup(TSCMaster[i][3]);
				smm.setGroupNumber(new Short(TSCMaster[i][4]));
				smm.setSeparate(new Short(TSCMaster[i][5]));
				smm.setControlState(TSCMaster[i][6]);
				smm.setSystemState(TSCMaster[i][7]);
				smm.setAlarmState(TSCMaster[i][8]);
				smm.setCommState(TSCMaster[i][9]);
				smm.setTscMode(TSCMaster[i][10]);
				smm.setPieceMode(TSCMaster[i][11]);
				smm.setTscAvailable(TSCMaster[i][12]);
				smm.setPieceAvailable(TSCMaster[i][13]);
				smm.setPortState(TSCMaster[i][14]);
				screenMonitorMemberList1.add(smm);
    		}
		 }
		 else if( fileKind.equals( SCREEN_MONITOR ) ) 
		 {	
			monitorData = new String[array.length][];
			
			for (int i = 0; i < array.length; i++) {
				monitorData[i] = mySplit( array[i] );
			}
			
			for( int i=0; i<monitorData.length; i++ )
			{
				ScreenMonitor sm = new ScreenMonitor();
				sm.setDisplayId(new Integer(monitorData[i][0])); 
				sm.setDisplayNumber(new Short(monitorData[i][1]));
				sm.setColor(monitorData[i][2]);
				sm.setText(monitorData[i][3]);
				screenMonitorList1.add(sm);
			}
		 }
		 else if( fileKind.equals( SCREEN_COLOR_MASTER ) ) 
		 {	
			colorMaster = new String[array.length][];
			
			for (int i = 0; i < array.length; i++) {
				colorMaster[i] = mySplit( array[i] );
			}
			
			for( int i=0; i<colorMaster.length; i++ )
			{
				ScreenColorMaster scm = new ScreenColorMaster();
				scm.setColor(colorMaster[i][0]);
				scm.setColorName(colorMaster[i][1]);
				scm.setRedParam(new Short(colorMaster[i][2]));
				scm.setGreenParam(new Short(colorMaster[i][3]));
				scm.setBlueParam(new Short(colorMaster[i][4]));
				screenColorMasterList1.add(scm);
			}
    	  } else if( fileKind.equals( HOST ) ) 
 		  {	
    		  String[][] data = new String[array.length][];
      		
	      	  for(int i = 0; i < array.length; i++) {
	      		  String[] tmp = array[i].split( "," );
				  data[i] = tmp;
	      	  }
	      	  
	      	  hostData = data;
 		  } else if( fileKind.equals( MACS ) ) 
 		  {	
    		  String[][] data = new String[array.length][];
      		
	      	  for(int i = 0; i < array.length; i++) {
	      		  String[] tmp = array[i].split( "," );
				  data[i] = tmp;
	      	  }
	      	  
	      	  macsData = data;
 		  }
    	
	}
	
	
	/**
	 * TSCタイプのリストを取得する
	 * 
	 * リストが整列されているため、これで取得できる
	 */
	public String[] getTscTypes()
	{
		if( TSCMaster == null )
		{
			return null;
		}
		
		String befType = "";
		
		StringBuffer strBuf = new StringBuffer();
		
		for( int i=0; i<TSCMaster.length; i++ )
		{
			if( i == 0 )
			{
				strBuf.append( TSCMaster[i][3] );
				strBuf.append( "," );
			}
			else
			{
				if( !befType.equals( TSCMaster[i][3] )  )
				{
					strBuf.append( TSCMaster[i][3] );
					strBuf.append( "," );
				}
			}
			befType = TSCMaster[i][3];
		}
		
		return strBuf.toString().split( "," );
	}
	
	/**
	 * TSCの情報を返します
	 * 
	 * tscType : 取得対象データとなるTSCのType
	 */
	public String[][] getTscData( String tscType )
	{
		int index = 0;
		int count = getTscTypeCount( tscType );
		
		if( count == 0 )
		{
			return null;
		}
		
		String[][] tmp = new String[count][];
		
		for( int i=0; i<TSCMaster.length; i++ )
		{
			if( tscType.equals( TSCMaster[i][3] ) )
			{
				tmp[index++] = TSCMaster[i];
			}
		}
		
		return tmp;
	}
	

	/**
	 * 指定されたTypeのTSCデータが何件あるかを返します
	 * 
	 * tscType : データ件数を調べたいTSCのType
	 */
	private int getTscTypeCount( String tscType )
	{
		int count = 0;
		
		if( TSCMaster != null )
		{
			for( int i=0; i<TSCMaster.length; i++ )
			{
				if( tscType.equals( TSCMaster[i][3] ) )
				{
					count++;
				}
			}
		}
		
		return count;
	}
	
    //20200110 DQY ADD END

}
