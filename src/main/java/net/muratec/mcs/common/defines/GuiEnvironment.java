/*
 * $Header: /home/CVS/CVS_DB/macs-infi2/gui-source/src/macs/common/GuiEnvironment.java,v 1.3 2018/02/08 09:24:49 kuwahara Exp $
 * $Revision: 1.3 $
 * $Date: 2018/02/08 09:24:49 $
 *
 * ====================================================================
 *
 * GuiEnvironment.java
 *
 * Copyright 2000-2003 by Murata Machinary,LTD
 *
 * ====================================================================
 *
 *
 */
package net.muratec.mcs.common.defines;

import net.muratec.mcs.common.defines.*;


/**
 * MACS-GUI共通属性クラス
 *
 * @version $Revision: 1.3 $
 * @author M.Takashima(NISP)
 */
public class GuiEnvironment
{
	/** 日付チェックエラー */
	public static final int ERROR_DATE_CHECK = 0;

	/** メモリリークエラー */
	public static final int ERROR_OUT_OF_MEMORY = 1;

	/** GUIがIdle状態であることを表す */
	public static final int GUI_STATE_IDLE = 0;

	/** GUIが処理中であることを表す */
	public static final int GUI_STATE_BUSY = 1;

	/** 
	 * GUI共通のID。個々の画面のIDはこの値にURLのパラメータで渡された値を加えたものとなる｡ 
	 */
	public static final int GUI_COMMON_ID = 50000;


//	/** TSCのタイプがICTであることを表す */
//	public static final String TSC_TYPE_ICT = "ICT";

//	/** TSCのタイプがICSであることを表す */
//	public static final String TSC_TYPE_ICS = "ICS";

	/** TSCのタイプがICCであることを表す */
	public static final String TSC_TYPE_ICC = "ICC";

	/** TSCのタイプがAGVCであることを表す */
	public static final String TSC_TYPE_AGVC = "AGVC";

	/** TSCのタイプがLIMCであることを表す */
	public static final String TSC_TYPE_LIMC = "LIMC";

	/** TSCのタイプがCDCであることを表す */
	public static final String TSC_TYPE_CDC = "CDC";
	public static final String TSC_TYPE_OHTC = "OHTC";
	public static final String TSC_TYPE_OHS = "OHS";
	public static final String TSC_TYPE_STC = "STC";
	public static final String TSC_TYPE_SRC320 = "SRC320";
	public static final String TSC_TYPE_SRC350 = "SRC350";
	/** TSCのタイプがRGCVであることを表す */
	public static final String TSC_TYPE_RGVC = "RGVC";

	/** TSCのタイプがLIFTERCであることを表す */
//	public static final String TSC_TYPE_LIFTERC = "LIFTERC";
	public static final String TSC_TYPE_LIFTERC = "LFTC";
	public static final String TSC_TYPE_LFC = "LFC";
    public static final String TSC_TYPE_XLFT = "XLFT";
	

	/** TSCのタイプがPORTCであることを表す */
	public static final String TSC_TYPE_PORTC = "PORTC";

	/** TSCのタイプがSORTCであることを表す */
	public static final String TSC_TYPE_SORTC = "SORTC";

	/** TSCのタイプがSORTCであることを表す */
	public static final String TSC_TYPE_OHBC = "OHBC";

	public static final String TSC_TYPE_OCDC = "OCDC";
	public static final String TSC_TYPE_XCDC = "XCDC";
	
	/** ホストポートタイプ */
	public static final String STC_PORT = "STC_PORT";
	public static final String EQP_PORT = "EQP_PORT";
	public static final String AGV_PORT = "AGV_PORT";
	public static final String OHS_PORT = "OHS_PORT";
	public static final String CON_PORT = "CON_PORT";
	public static final String LFT_PORT = "LFT_PORT";
	public static final String RGV_PORT = "RGV_PORT";
	public static final String PRT_PORT = "PRT_PORT";
	public static final String OTHER = "OTHER";

	/** TSC ModelがSTKSEMであることを表す */
	public static final String TSC_MODEL_STKSEM = "STKSEM";

	/** TSC ModelがIBSEMであることを表す */
	public static final String TSC_MODEL_IBSEM = "IBSEM";

	/** TSC ModelがOHBSEMであることを表す */
	public static final String TSC_MODEL_OHBSEM = "OHBSEM";

	/** ホットロット */
	private static int hotLot = Integer.MIN_VALUE;

	/** MACS-GUI固有のID */
	private static int guiId = Integer.MIN_VALUE;

	/** オートログアウトするまでのインターバル */
	private static int logOutInterval = Integer.MIN_VALUE;

	/** MACS-GUIに最後にアクセスした時間 */
	private static long lastAccessedTime = Long.MIN_VALUE;

	/** GUIからの入力文字を大文字に変換するか否かの情報を保持 */
	private static boolean isToUpper;

	/** 
	 * 制御との通信に使用するサブジェクトのプロジェクト名。<br>
	 * (サブジェクトのフォーマット(*.*.*.*.*)の一番左のアスタリスク部分に相当)
	 */
	private static String projectName;

	/** 日付チェック処理のエラーメッセージ */
	private static String dateChkErrMsg;

	/** メモリリーク時のエラーメッセージ */
	private static String[] outOfMemErrMsg;

	/** 接続エラー(Oracle)時に表示するエラーメッセージ */
	private static String dbConnErrMsg;

	/** 通信エラー(Oracle)時に表示するエラーメッセージ */
	private static String dbCommErrMsg;

	/** 通信エラー(TIB)時に表示するエラーメッセージ */
	private static String tibCommErrMsg;

	/** 検索データなし時に表示するメッセージ */
	private static String noSearchDataMsg;

	/** ダウンロードエラーメッセージ */
	private static String downloadErrMsg;

	/** MACSシャットダウン時メッセージ */
	private static String macsStateDownMsg;

	/** PROCESSダウン時メッセージ */
	private static String processStateDownMsg;

	/** 設定ファイル名 */
	private static String cfgFileName;

	/** 接続先ホスト名 */
	private static String hostName;
	
	private static int port;

	/** SystemMonitor用の設定ファイルがあるディレクトリ名 */
	private static String directoryName;

	/** Tomcatの使用するポート 2005.09.26 */
	private static String tomcatPort;

	/**  */
	private static int dbCount;

	/**  */
	private static String dbDriver;
	
	/**  */
	private static String[] dbURL;

	/**  */
	private static String dbUser;

	/**  */
	private static String dbPassword;

	/** */
	private static String myMacsName;

	/** 
	 * 現在のGUIの状態を保持する。<br>
	 * trueなら処理中、falseなら待機中であることを表す｡
	 */
	private static boolean isGuiStateBusy;
	public static final String CDC_FROM = "300";
	public static final String CDC_TO   = "400";
	/** TSCのタイプがCDCCのAlarmID 範囲 */
	public static final int CDC_ALARM_FROM = 400000;
	public static final int CDC_ALARM_TO   = 409999;

	/** TSCのタイプがOHTCの範囲 */
	public static final String OHT_FROM = "200";
	public static final String OHT_TO   = "210";
	/** TSCのタイプがOHTC TSCのAlarmID 範囲 */
	public static final int OHT_ALARM_FROM1 = 851000;
	public static final int OHT_ALARM_TO1   = 852999;
	/** TSCのタイプがOHTC VEHICLEのAlarmID 範囲 */
	public static final int OHT_ALARM_FROM2 = 853000;
	public static final int OHT_ALARM_TO2   = 859999;

	/** TSCのタイプがRGVCの範囲 */
	public static final String RGV_FROM = "400";
	public static final String RGV_TO   = "500";
	/** TSCのタイプがRGVC TSCのAlarmID 範囲 */
	public static final int RGV_ALARM_FROM1 = 502000;
	public static final int RGV_ALARM_TO1   = 502999;
	/** TSCのタイプがRGVC VEHICLEのAlarmID 範囲 */
	public static final int RGV_ALARM_FROM2 = 501000;
	public static final int RGV_ALARM_TO2   = 501999;
	public static final int RGV_ALARM_FROM3 = 503000;
	public static final int RGV_ALARM_TO3   = 509999;
	/** ALARM_LOGの固有エラーコード */
	//2004.03.05 MOD YAMANA
	public static final int    ALARM_LOG_KEY = 999999;

	public static final String SETALL = "SetAll";

	private static int dayOfWeek = 2;
	/** ALARM_LEVELが装置のみ使用 */
	//2004.03.05 MOD HIRAYAMA
	public static final int ALARM_LEVEL_MIN = 1;
	public static final int ALARM_LEVEL_MAX = 5;

	/**
	 * 制御との通信タイムアウトのデフォルト時間を保持する。<br>
	 * タイムアウトインターバルを変更したい画面は個々で対応可能｡
	 */
	private static int commTimeOutInterval = Integer.MIN_VALUE;

	/** サーブレットにアクセスするURLを保持する。 */
	private static String servletAccessUrl;

	/** 大文字変換フラグ */
	private static final String UPPER_CASE = "1";

	/** Tomcatのデフォルトポート2005.09.26 */
	private static final String DEFAULT_TOMCAT_PORT = "8080";
	private static String gui_jar_dir = "cr2-gui";

	/** ブラウザ名称 */
	private static String browserType;

//********************************************************************************
// Public Methods
//********************************************************************************

	public static String getBrowserType() {
		return browserType;
	}

	public static void setBrowserType(String browserType) {
		GuiEnvironment.browserType = browserType;
	}

	/**
	 * ホットロットを返します。
	 */
	public static final int getHotLot()
	{
		return hotLot;
	}

	/**
	 * ホットロットを設定します｡
	 */
	public static final void setHotLot( int hotLot )
	{
		if( GuiEnvironment.hotLot == Integer.MIN_VALUE )
			GuiEnvironment.hotLot = hotLot;
	}

	/**
	 * MACS-GUI固有のIDを返します。
	 */
	public static final int getGuiId()
	{
		return guiId;
	}

	/**
	 * MACS-GUI固有のIDを設定します｡
	 */
	public static final void setGuiId( int guiId )
	{
		if( GuiEnvironment.guiId == Integer.MIN_VALUE )
			GuiEnvironment.guiId = guiId;
	}

	/**
	 * オートログアウトのインターバルを返します。
	 */
	public static final int getLogOutInterval()
	{
		return logOutInterval;
	}

	/**
	 * オートログアウトのインターバルを設定します｡
	 */
	public static final void setLogOutInterval( int logOutInterval )
	{
// For Duncan Request 20101012 ---------------------------------
//System.out.println("GuiEnvironment.logOutInterval : ["+GuiEnvironment.logOutInterval+"]");
//		if( GuiEnvironment.logOutInterval == Integer.MIN_VALUE )
			GuiEnvironment.logOutInterval = logOutInterval;
//System.out.println("Seted GuiEnvironment.logOutInterval : ["+GuiEnvironment.logOutInterval+"]");
// -------------------------------------
//		// if( GuiEnvironment.logOutInterval == Integer.MIN_VALUE )
//			GuiEnvironment.logOutInterval = logOutInterval;
// --------------------------------------------------------------
	}

	/**
	 * ユーザが入力した英字を大文字に変換するか否かを返します｡
	 */
	public static final boolean isToUpper()
	{
		return isToUpper;
	}

	/**
	 * ユーザが入力した英字を大文字に変換するか否かを設定します｡
	 */
	public static final void setToUpper( boolean isToUpper )
	{
		GuiEnvironment.isToUpper = isToUpper;
	}

	/**
	 * ユーザが入力した英字を大文字に変換するか否かを設定します｡
	 */
	public static final void setToUpper( String isToUpper )
	{
		if( UPPER_CASE.equals( isToUpper ) )
		{
			GuiEnvironment.isToUpper = true;
		}
		else
		{
			GuiEnvironment.isToUpper = false;
		}
	}

	/**
	 * GUIに最後にアクセスした時間を返します。
	 */
	public synchronized static final long getLastAccessedTime()
	{
//System.out.println("getLastAccessedTime : ["+lastAccessedTime+"]");
		return lastAccessedTime;
	}

	/**
	 * GUIに最後にアクセスした時間を設定します｡
	 */
	public synchronized static final void setLastAccessedTime()
	{
//System.out.println("setLastAccessedTime : Set Last Accessed Time["+System.currentTimeMillis()+"]");
		GuiEnvironment.lastAccessedTime = System.currentTimeMillis();
	}

	/**
	 * プロジェクト名を返します。
	 */
	public static final String getProjectName()
	{
		return projectName;
	}

	/**
	 * プロジェクト名を設定します｡
	 */
	public static final void setProjectName( String projectName )
	{
		if( GuiEnvironment.projectName == null )
			GuiEnvironment.projectName = projectName;
	}

	/**
	 * GUIの設定ファイル名を返します。
	 */
	public static final String getCfgFileName()
	{
		return cfgFileName;
	}

	/**
	 * GUIの設定ファイル名を設定します｡
	 */
	public static final void setCfgFileName( String cfgFileName )
	{
		if( GuiEnvironment.cfgFileName == null )
			GuiEnvironment.cfgFileName = cfgFileName;
	}

	/**
	 * 接続先ホスト名を返します。
	 */
	public static final String getHostName()
	{
		return hostName;
	}

	/**
	 * 接続先ホスト名を設定します｡
	 */
	public static final void setHostName( String hostName )
	{
		if( GuiEnvironment.hostName == null )
			GuiEnvironment.hostName = hostName;
	}
	


	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		if( port == -1 ) port=80;
		
		GuiEnvironment.port = port;
	}

	public static final String getHostPath()
	{

		StringBuffer buffer = new StringBuffer();
		buffer.append(getHostName());
		if (port > 0) {
			buffer.append(":");
			buffer.append(getPort());
		}

		return buffer.toString();
	}

	/**
	 * SystemMonitor用の設定ファイルがあるディレクトリを返します。
	 */
	public static final String getCSVFileDirectory()
	{
		return directoryName;
	}

	/**
	 * SystemMonitor用の設定ファイルがあるディレクトリを設定します。
	 */
	public static final void setCSVFileDirectory( String directoryName )
	{
		GuiEnvironment.directoryName = directoryName;
	}

	/**
	 * JDBCドライバ名を返します。
	 */
	public static final String getDbDriver()
	{
		return dbDriver;
	}

	/**
	 * JDBCドライバ名を設定します。
	 */
	public static final void setDbDriver( String driverName )
	{
		GuiEnvironment.dbDriver = driverName;
	}

	/**
	 * DBのURLを返します。
	 */
	public static final String[] getDbURL()
	{
		return dbURL;
	}

	/**
	 * DBのURLを設定します。
	 */
	public static final void setDbURL( String[] dbURL )
	{
		GuiEnvironment.dbURL = dbURL;
	}

	/**
	 * DBのUserIDを返します。
	 */
	public static final String getDbUser()
	{
		return dbUser;
	}

	/**
	 * DBのUserIDを設定します。
	 */
	public static final void setDbUser( String dbUser )
	{
		GuiEnvironment.dbUser = dbUser;
	}

	/**
	 * DBのPasswordを返します。
	 */
	public static final String getDbPassword()
	{
		return dbPassword;
	}

	/**
	 * DBのPasswordを設定します。
	 */
	public static final void setDbPassword( String dbPassword )
	{
		GuiEnvironment.dbPassword = dbPassword;
	}

	/**
	 * MyMacsNameを返します。
	 */
	public static final String getMyMacsName()
	{
		return myMacsName;
	}

	/**
	 * MyMacsNameを設定します。
	 */
	public static final void setMyMacsName( String myMacsName )
	{
		GuiEnvironment.myMacsName = myMacsName;
	}

	/**
	 * 日付チェック処理のエラーメッセージを返します。
	 */
	public static final String getDateChkErrMsg()
	{
		return dateChkErrMsg;
	}

	/**
	 * 日付チェック処理のエラーメッセージを設定します｡
	 */
	public static final void setDateChkErrMsg( String dateChkErrMsg )
	{
		if( GuiEnvironment.dateChkErrMsg == null )
			GuiEnvironment.dateChkErrMsg = dateChkErrMsg;
	}

	/**
	 * 日付チェック処理のエラーメッセージを返します。
	 */
	public static final String[] getOutOfMemErrMsg()
	{
		return outOfMemErrMsg;
	}

	/**
	 * 日付チェック処理のエラーメッセージを設定します｡
	 */
	public static final void setOutOfMemErrMsg( String[] outOfMemErrMsg )
	{
		if( GuiEnvironment.outOfMemErrMsg == null )
			GuiEnvironment.outOfMemErrMsg = outOfMemErrMsg;
	}

	/**
	 * 日付チェック処理のエラーメッセージを返します。
	 */
	public static final boolean isGuiStateBusy()
	{
		return isGuiStateBusy;
	}

	/**
	 * 日付チェック処理のエラーメッセージを設定します｡
	 */
	public static final void setGuiStateBusy( boolean isGuiStateBusy )
	{
		GuiEnvironment.isGuiStateBusy = isGuiStateBusy;
	}

	/**
	 * 通信タイムアウトのデフォルトインターバルを返します。
	 */
	public static final int getCommTimeOutInterval()
	{
		return commTimeOutInterval;
	}

	/**
	 * 通信タイムアウトのデフォルトインターバルを設定します｡
	 */
	public static final void setCommTimeOutInterval( int commTimeOutInterval )
	{
		if( GuiEnvironment.commTimeOutInterval == Integer.MIN_VALUE )
			GuiEnvironment.commTimeOutInterval = commTimeOutInterval;
	}

	/**
	 * DBの接続エラー時に表示するメッセージを返します。
	 */
	public static final String getDbConnErrMsg()
	{
		return dbConnErrMsg;
	}

	/**
	 * DBの接続エラー時に表示するメッセージを設定します。
	 */
	public static final void setDbConnErrMsg( String dbConnErrMsg )
	{
		GuiEnvironment.dbConnErrMsg = dbConnErrMsg;
	}

	/**
	 * DBとの通信エラー時に表示するメッセージを返します｡
	 */
	public static final String getDbCommErrMsg()
	{
		return dbCommErrMsg;
	}

	/**
	 * DBとの通信エラー時に表示するメッセージを設定します｡
	 */
	public static void setDbCommErrMsg( String dbCommErrMsg )
	{
		GuiEnvironment.dbCommErrMsg = dbCommErrMsg;
	}

	/**
	 * TIBとの通信エラー時に表示するメッセージを返します｡
	 */
	public static final String getTibCommErrMsg()
	{
		return tibCommErrMsg;
	}

	/**
	 * TIBとの通信エラー時に表示するメッセージを設定します｡
	 */
	public static void setTibCommErrMsg( String tibCommErrMsg )
	{
		GuiEnvironment.tibCommErrMsg = tibCommErrMsg;
	}

	/**
	 * 検索データなし時に表示するメッセージを返します｡
	 */
	public static final String getNoSearchDataMsg()
	{
		return noSearchDataMsg;
	}

	/**
	 * 検索データなし時に表示するメッセージを設定します｡
	 */
	public static void setNoSearchDataMsg( String noSearchDataMsg )
	{
		GuiEnvironment.noSearchDataMsg = noSearchDataMsg;
	}

	/**
	 * ダウンロードエラーメッセージを返します｡
	 */
	public static final String getDownloadErrMsg()
	{
		return downloadErrMsg;
	}

	/**
	 * ダウンロードエラーメッセージを設定します｡
	 */
	public static void setDownloadErrMsg( String downloadErrMsg )
	{
		GuiEnvironment.downloadErrMsg = downloadErrMsg;
	}

	/**
	 * MACSシャットダウン時に表示するメッセージを返します｡
	 */
	public static final String getMacsStateDownMsg()
	{
		return macsStateDownMsg;
	}

	/**
	 * MACSシャットダウン時に表示するメッセージを設定します｡
	 */
	public static void setMacsStateDownMsg( String macsStateDownMsg )
	{
		GuiEnvironment.macsStateDownMsg = macsStateDownMsg;
	}

	/**
	 * PROCESSダウン時に表示するメッセージを返します｡
	 */
	/*//20200311 song Del
	public static final String getProcessStateDownMsg( String tibmsg )
	{
		String[] params = GuiUtilities.splitByDelim( tibmsg, "." );
		
		return processStateDownMsg.replaceAll( "%1", ( params[1] + " ( SID : " + params[2] + " )" ) );
	}
   */
	/**
	 * PROCESSダウン時に表示するメッセージを設定します｡
	 */
	public static void setProcessStateDownMsg( String processStateDownMsg )
	{
		GuiEnvironment.processStateDownMsg = processStateDownMsg;
	}

	/**
	 * DebugLog用サブジェクトを返します｡
	 */
	public static final String getDebugLogSubject()
	{
//		return getProjectName() + "." + Subject.DEBUGLOG_WRITER_NAME + ".7." + Subject.EVT_DEBUGINFO + ".ON";
		return getProjectName() + ".Gui.*." + Subject.EVT_DEBUGINFO + ".ON";
	}

	/**
	 * Tomcatの使用するポートを設定します｡ 2005.09.26
	 */
	public static void setTomcatPort( String tomcatPort )
	{
		if( tomcatPort == null || "".equals( tomcatPort ) )
		{
			tomcatPort = DEFAULT_TOMCAT_PORT;
		}
//System.out.println("tomcatPort [" + tomcatPort + "] ");

		GuiEnvironment.tomcatPort = tomcatPort;
	}

	/**
	 * ダウンロードサーブレットにアクセスするURLを設定します｡
	 */
	public static void setServletAccessUrl( String url )
	{
//		servletAccessUrl = "http://" + Common.macs.getCodeBase().getHost() + url;
//		servletAccessUrl = "http://" + Common.macs.getCodeBase().getHost() + ":8081" + url;
//		servletAccessUrl = "http://" + Common.macs.getCodeBase().getHost() + ":" + tomcatPort + url;
		servletAccessUrl = "http://" + GuiEnvironment.getHostName() + ":" + tomcatPort + url;
	}

	/**
	 * ダウンロードサーブレットにアクセスするURLを返します｡
	 */
	public static final String getServletAccessUrl()
	{
//System.out.println("GuiEnvironment#getServletAccessUrl : URL["+servletAccessUrl+"]");
		return servletAccessUrl;
	}
	public static final int getDayOfWeek() {
		return dayOfWeek;
	}
		// 2008.07.11 BONN: Added for GUI macs.jar version update notification
	public static void setGuiJarDir( String dir_name )
	{
		if (dir_name != null)
		{
			gui_jar_dir = dir_name;
		}
	}

	// 2008.07.11 BONN: Added for GUI macs.jar version update notification
	public static final String getGuiJarDir()
	{
		return gui_jar_dir;
	}
}
