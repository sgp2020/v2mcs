/*
 * $Header: /home/CVS/CVS_DB/macs-infi2/gui-source/src/macs/common/defines/Subject.java,v 1.2 2019/09/24 01:49:35 kuwahara Exp $
 * $Revision: 1.2 $
 * $Date: 2019/09/24 01:49:35 $
 *
 * ====================================================================
 *
 * Subject.java
 *
 * Copyright 2000-2003 by Murata Machinary,LTD
 *
 * ====================================================================
 *
 * $Log: Subject.java,v $
 * Revision 1.2  2019/09/24 01:49:35  kuwahara
 * Add Process Assign maintenace gui
 *
 */

package net.muratec.mcs.common.defines;


/**
 * サブジェクトクラス
 *
 * @version $Revision: 1.2 $
 * @author M.Takashima(NISP)
 */
public class Subject
{
//********************************************************************************
// メッセージの送り先(プロセス名)
//********************************************************************************

	/** Job Dispatcher */
	public static final String JOB_DISPATCHER_NAME = "JobDispatcher";
	/** Debug Log Writer */
	public static final String DEBUGLOG_WRITER_NAME = "DebugLog";
	/** Watch Dog */
	public static final String WATCH_DOG_NAME = "WatchDog";
	/** System Manager */
	public static final String SYSTEM_MANAGER_NAME = "SystemMgr";
	/** Log Manager */
	public static final String LOG_MANAGER_NAME = "LogMgr";
	/** IPC Log */
	public static final String IPCLOG_WRITER_NAME = "IpcLog";
	/** Route Manager */
	public static final String ROUTE_MANAGER_NAME = "RouteMgr";
	/** Mail Manager */
	public static final String MAIL_MANAGER_NAME = "MailMgr";
	/** Exercise */
	public static final String EXERCISE_NAME = "Exercise";
	/** Vehicle Dispacher */
	public static final String VEHICLE_DISPATCHER_NAME = "VehicleDpt";
	/** Host Event */
	public static final String HOST_EVENT_NAME = "HostEvent";
	/** Host Query */
	public static final String HOST_QUERY_NAME = "HostQuery";
	/** Time Manager */
	public static final String TIMER_MANAGER_NAME = "TimerMgr";
	/** Core Control */
	public static final String CORE_CONTROL_NAME = "CoreControl";
	/** LLC Com */
	public static final String LLC_COM_NAME = "LLCCom";
	/** Host Com */
//	public static final String HOST_COM_NAME = "HostCom";
	public static final String HOST_COM_NAME = "crbclient";
	/** GUI */
	public static final String MACS_GUI_NAME = "Gui";
	/** All */
	public static final String ALL = "*";


//********************************************************************************
// メッセージの種類
//********************************************************************************

	/**  */
	public static final String EVT_WAITIN = "WaitIn";
	/**  */
	public static final String EVT_CARRIERSTATECHANGE = "CarrierStateChange";
	/**  */
	public static final String EVT_CARRIERLOCATIONCHANGE = "CarrierLocationChange";
	/**  */
	public static final String EVT_REQINTRIGGER = "ReqInTrigger";
	/**  */
	public static final String EVT_JOBCREATEREQ = "JobCreateReq";
	/**  */
	public static final String EVT_JOBDELETEREQ = "JobDeleteReq";
	/**  */
	public static final String EVT_JOBDELETEANS = "JobDeleteAns";
	/** */
	public static final String EVT_DISPATCHERCANCELREQ = "DispatcherCancelReq";
	/**  */
	public static final String EVT_DISPATCHERCANCELANS = "DispatcherCancelAns";
	/**  */
	public static final String EVT_DISPATCHERDELETEREQ = "DispatcherDeleteReq";
	/**  */
	public static final String EVT_DISPATCHERDELETEANS = "DispatcherDeleteAns";
	/**  */
	public static final String EVT_HOSTRANSFERREQ = "HostTransferReq";
	/**  */
	public static final String EVT_DPLREQ = "DPLReq";
	/**  */
	public static final String EVT_TRANSFERCMDREQ = "TransferCmdReq";
	/**  */
	public static final String EVT_TRANSFERSTATECHANGE = "TransferStateChange";
	/**  */
	public static final String EVT_TRANSFERCMDANS = "TransferCmdAns";
	/**  */
	public static final String EVT_TRANSFERINITIATED = "TransferInitiated";
	/**  */
	public static final String EVT_CARRIERTRANSFERRING = "CarrierTransferring";
	/**  */
	public static final String EVT_CRANEIDLE = "CraneIdle";
	/**  */
	public static final String EVT_CRANEACTIVE = "CraneActive";
	/**  */
	public static final String EVT_TRANSFERCOMPLETED = "TransferCompleted";
	/**  */
	public static final String EVT_ZONECAPACITYCHANGE = "ZoneCapacityChange";
	/**  */
	public static final String EVT_HOLDRESET = "HoldReset";
	/**  */
	public static final String EVT_LOCATECOMPLETED = "LocateCompleted";
	/**  */
	public static final String EVT_STORED = "Stored";
	/**  */
	public static final String EVT_WAITOUT = "WaitOut";
	/**  */
	public static final String EVT_IDREAD = "IDRead";
	/**  */
	public static final String EVT_IDREADERROR = "IDReadError";
	/**  */
	public static final String EVT_REQOUTTRIGGER = "ReqOutTrigger";
	/**  */
	public static final String EVT_REMOVED = "Removed";
	/**  */
	public static final String EVT_VEHICLEINSTALLED = "VehicleInstalled";
	/**  */
	public static final String EVT_VEHICLEREMOVED = "VehicleRemoved";
	/**  */
	public static final String EVT_VEHICLEARRIVED = "VehicleArrived";
	/**  */
	public static final String EVT_VEHICLEDEPARTED = "VehicleDeparted";
	/**  */
	public static final String EVT_TRANSFERRING = "Transferring";
	/**  */
	public static final String EVT_ACQUIRESTARTED = "AcquireStarted";
	/**  */
	public static final String EVT_ACQUIRECOMPLETED = "AcquireCompleted";
	/**  */
	public static final String EVT_DEPOSITSTARTED = "DepositStarted";
	/**  */
	public static final String EVT_DEPOSITCOMPLETED = "DepositCompleted";
	/**  */
	public static final String EVT_IBINSTALLED = "IBInstalled";
	/**  */
	public static final String EVT_IBREMOVED = "IBRemoved";
	/**  */
	public static final String EVT_VEHICLEASSIGNED = "VehicleAssigned";
	/**  */
	public static final String EVT_VEHICLEUNASSIGNED = "VehicleUnassigned";
	/**  */
	public static final String EVT_SOREDALT = "SoredAlt";
	/**  */
	public static final String EVT_CARRIERRESUMED = "CarrierResumed";
	/**  */
	public static final String EVT_CARRIERCREATREQ = "CarrierCreateReq";
	/**  */
	public static final String EVT_REQJOBTRIGGER = "ReqJobTrigger";
	/**  */
	public static final String EVT_CANCELCMDREQ = "CancelCmdReq";
	/**  */
	public static final String EVT_ABORTCMDREQ = "AbortCmdReq";
	/**  */
	public static final String EVT_CANCELINITATED = "CancelInitiated";
	/**  */
	public static final String EVT_ABORTINITIATED = "AbortInitiated";
	/**  */
	public static final String EVT_CANCELCOMPLETED = "CancelCompleted";
	/**  */
	public static final String EVT_REQBINTRIGGER = "ReqBinTrigger";
	/**  */
	public static final String EVT_ABORTFAILED = "AbortFailed";
	/**  */
	public static final String EVT_CANCELFAILED = "CancelFailed";
	/**  */
	public static final String EVT_INSTALLCOMMAND = "InstallCommand";
	/**  */
	public static final String EVT_INSTALLCOMPLETED = "InstallCompleted";
	/**  */
	public static final String EVT_INSTALLCOMMANDREQ = "InstallCommandReq";
	/**  */
	public static final String EVT_INSTALLCOMMANDANS = "InstallCommandAns";
	/**  */
	public static final String EVT_REMOVECOMMAND = "RemoveCommand";
	/**  */
	public static final String EVT_REMOVECOMPLETED = "RemoveCompleted";
	/**  */
	public static final String EVT_REMOVECOMMANDREQ = "RemoveCommandReq";
	/**  */
	public static final String EVT_REMOVECOMMANDANS = "RemoveCommandAns";
	/**  */
	public static final String EVT_PAUSECOMPLETED = "PauseCompleted";
	/**  */
	public static final String EVT_DATASYNCREQ = "DataSyncReq";
	/**  */
	public static final String EVT_DATASYNCANS = "DataSyncAns";
	/**  */
	public static final String EVT_ACTIVETRANSFER = "ActiveTransfer";
	/**  */
	public static final String EVT_RESUME = "Resume";
	/** 搬送指示要求 */
	public static final String EVT_HOSTTRANSFERCMDREQ = "HostTransferCmdReq";
	/** 搬送指示応答 */
	public static final String EVT_HOSTTRANSFERCMDANS = "HostTransferCmdAns";
	/**  */
	public static final String EVT_REROUTECOMPLETED = "RerouteCompleted";
	/**  */
	public static final String EVT_DESTINATIONCHANGEREQ = "DestinationChangeReq";
	/** リルート要求 */
	public static final String EVT_HOSTREROUTECMDREQ = "HostRerouteCmdReq";
	/** リルート応答 */
	public static final String EVT_HOSTREROUTECMDANS = "HostRerouteCmdAns";
	/** 搬送キャンセル要求 */
	public static final String EVT_HOSTCANCELCMDREQ = "HostCancelCmdReq";
	/** 搬送キャンセル応答 */
	public static final String EVT_HOSTCANCELCMDANS = "HostCancelCmdAns";
	/**  */
	public static final String EVT_CREATEROUTEREQ = "CreateRouteReq";
	/**  */
	public static final String EVT_CREATEROUTEANS = "CreateRouteAns";
	/**  */
	public static final String EVT_CREATEROUTECOMPLETED = "CreateRouteCompleted";
	/**  */
	public static final String EVT_LOADTSCDATA = "LoadTscData";
	/**  */
	public static final String EVT_LOADMACSCONSTS = "LoadMacsConsts";
	/**  */
	public static final String EVT_LOADMACSSTATUS = "LoadMacsStatus";
	/**  */
	public static final String EVT_PROCSTARTREQ = "ProcStartReq";
	/**  */
	public static final String EVT_PROCSTARTANS = "ProcStartAns";
	/**  */
	public static final String EVT_HEARTBEATREQ = "HeartBeatReq";
	/**  */
	public static final String EVT_HEARTBEATANS = "HeartBeatAns";
	/**  */
	public static final String EVT_MACSSTATECHANGE = "MacsStateChange";
	/**  */
	public static final String EVT_SHUTDOWNREQ = "ShutDownReq";
	/**  */
	public static final String EVT_SHUTDOWNANS = "ShutDownAns";
	/**  */
	public static final String EVT_KILLPROCREQ = "KillProcReq";
	/**  */
	public static final String EVT_COMMSTATECHANGE = "CommStateChange";
	/**  */
	public static final String EVT_AUTOINITIATED = "AutoInitiated";
	/**  */
	public static final String EVT_AUTOCOMPLETED = "AutoCompleted";
	/**  */
	public static final String EVT_PAUSEINITIATED = "PauseInitiated";
	/**  */
	public static final String EVT_PAUSED = "Paused";
	/**  */
	public static final String EVT_PORTSTATECHANGE = "PortStateChange";
	/**  */
	public static final String EVT_AVAILCHANGE = "AvailChange";
	/**  */
	public static final String EVT_TSCCONTROLSTATECHANGE = "TscControlStateChange";
	/**  */
	public static final String EVT_TSCALARMCHANGE = "TscAlarmChange";
	/**  */
	public static final String EVT_ALARMREPORT = "AlarmReport";
	/**  */
	public static final String EVT_ALARMSET = "AlarmSet";
	/**  */
	public static final String EVT_ALARMCLEAR = "AlarmClear";
	/**  */
	public static final String EVT_TSCMODECHANGEREQ = "TSCModeChangeReq";
	/**  */
	public static final String EVT_TSCMODECHANGEANS = "TSCModeChangeAns";
	/**  */
	public static final String EVT_PIECEMODECHANGEREQ = "PieceModeChangeReq";
	/**  */
	public static final String EVT_PIECEMODECHANGEANS = "PieceModeChangeAns";
	/**  */
	public static final String EVT_HOSTMODECHANGEREQ = "HostModeChangeReq";
	/**  */
	public static final String EVT_HOSTMODECHANGEANS = "HostModeChangeAns";
	/**  */
	public static final String EVT_PROCMODECHANGEREQ = "ProcModeChangeReq";
	/**  */
	public static final String EVT_PROCMODECHANGEANS = "ProcModeChangeAns";
	/**  */
	public static final String EVT_PORTMODECHANGEREQ = "PortModeChangeReq";
	/**  */
	public static final String EVT_PORTMODECHANGEANS = "PortModeChangeAns";
	/**  */
	public static final String EVT_IDRMODECHANGEREQ = "IDRModeChangeReq";
	/**  */
	public static final String EVT_IDRMODECHANGEANS = "IDRModeChangeAns";
	/**  */
	public static final String EVT_BOOTPROC = "BootProc";

	/** 
	 * デバック情報 
	 * 画面のオペレーションログ送信及びアラームメッセージ受信に使用される
	 */
	public static final String EVT_DEBUGINFO = "DebugInfo";
	/**  */
	public static final String EVT_SETTIMER = "SetTimer";
	/**  */
	public static final String EVT_KILLTIMER = "KillTimer";
	/**  */
	public static final String EVT_GENERATETIMER = "GenerateTimer";
	/**  */
	public static final String EVT_ROUTECHANGECMP = "RouteChangeCmp";
	/**  */
	public static final String EVT_KILLWATCHDOGPROCREQ = "KillWatchDogProcReq";
	/**  */
	public static final String EVT_EXERCISESTARTREQ = "ExerciseStartReq";
	/**  */
	public static final String EVT_EXERCISESTARTANS = "ExerciseStartAns";
	/**  */
	public static final String EVT_EXERCISEENDREQ = "ExerciseEndReq";
	/**  */
	public static final String EVT_EXERCISEENDANS = "ExerciseEndAns";
	/**  */
	public static final String EVT_EXERCISEADDREQ = "ExerciseAddReq";
	/**  */
	public static final String EVT_EXERCISEADDANS = "ExerciseAddAns";
	/**  */
	public static final String EVT_EXERCISEDELETEREQ = "ExerciseDeleteReq";
	/**  */
	public static final String EVT_EXERCISEDELETEANS = "ExerciseDeleteAns";
	/**  */
	public static final String EVT_ALLSTATEREAD = "AllStateRead";
	/**  */
	public static final String EVT_E10STATECHANGE = "E10StateChange";
	
	// --- For ProcessAssign func. ---------------------------------
	/** */
	public static final String Evt_REQPROCESSASSIGN = "ReqProcessAssign";
	/** */
	public static final String Evt_REQPROCESSUNASSIGN = "ReqProcessUnAssign";
	// -------------------------------------------------------------

}
