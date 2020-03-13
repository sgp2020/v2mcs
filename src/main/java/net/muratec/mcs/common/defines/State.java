/*
 * $Header: /home/CVS/CVS_DB/macs-infi2/gui-source/src/macs/common/defines/State.java,v 1.1.1.1 2017/12/28 06:09:48 kuwahara Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2017/12/28 06:09:48 $
 *
 * ====================================================================
 *
 * State.java
 *
 * Copyright 2000-2003 by Murata Machinary,LTD
 *
 * ====================================================================
 *
  *
 */

package net.muratec.mcs.common.defines;


/**
 * MACS制御側の各種ステータスを定義します。<br>
 * この定義は、macs_state.hから抜き出したものです。
 *
 * @version $Revision: 1.1.1.1 $
 * @author M.Takashima(NISP)
 */
public class State
{
//********************************************************************************
//    Define Macs Control status
//********************************************************************************

	/**  */
	public static final String MACS_STATE_INIT				= "Init";

	/**  */
	public static final String MACS_STATE_UP				= "Up";

	/**  */
	public static final String MACS_STATE_ONLINE			= "Up/Online";

	/**  */
	public static final String MACS_STATE_OFFLINE			= "Up/Offline";

	/**  */
	public static final String MACS_STATE_INHIBIT			= "Inhibit";

	/**  */
	public static final String MACS_STATE_INHIBITING		= "Inhibit/Inhibiting";

	/**  */
	public static final String MACS_STATE_INHIBITED			= "Inhibit/Inhibited";

	/**  */
	public static final String MACS_STATE_DOWN				= "Down";

	/**  */
	public static final String MACS_STATE_SHUTDOWN			= "Shutdown";

//********************************************************************************
//    Define E10 status
//********************************************************************************

	/**  */
	public static final String NON_SCHEDULED = "Non-Scheduled Time";

	/**  */
	public static final String UNSCHEDULED   = "UnScheduled DownTime";
	
	/**  */
	public static final String SCHEDULED     = "Scheduled DownTime";

	/**  */
	public static final String ENGINEER       = "Engineering Time";

	/**  */
	public static final String STANDBY       = "StandBy Time";

	/**  */
	public static final String PRODUCTIVE    = "Productive Time";

	/**  */
	public static final String E10_UP        = "Up";

	/**  */
	public static final String E10_DOWN      = "Down";

//********************************************************************************
//    Define Communication status
//********************************************************************************

	/**  */
	public static final String COMM_STATE_NOT_COMMUNICATING = "NotCommunicating";

	/**  */
	public static final String COMM_STATE_COMMUNICATING		= "Communicating";

	/**  */
	public static final String HOST_STATE_COMMUNICATING		= "Selected/Communicating";

//********************************************************************************
//    Define Host Communication status
//********************************************************************************

	/**  */
	public static final String COMM_STATE_UP				= "Up";

	/**  */
	public static final String COMM_STATE_DOWN				= "Down";

	/**  */
	public static final String COMM_STATE_ONLINE		    = "Online";

	/**  */
	public static final String COMM_STATE_OFFLINE			= "Offline";

//********************************************************************************
//    Define Job status
//********************************************************************************

	/**  */
	public static final String JOB_STATE_CREATED			= "JobCreated";

	/**  */
	public static final String JOB_STATE_QUEUED				= "JobQueued";

	/**  */
	public static final String JOB_STATE_CANCELLED			= "JobCancelled";

	/**  */
	public static final String JOB_STATE_ACTIVE				= "JobActive";

	/**  */
	public static final String JOB_STATE_ATOMIC_HOLD		= "AtomicJobHold";

	/**  */
	public static final String JOB_STATE_ATOMIC_QUEUED		= "AtomicJobQueued";

	/**  */
	public static final String JOB_STATE_ATOMIC_SELECT		= "SelectAtomicJob";

	/**  */
	public static final String JOB_STATE_ATOMIC_ACTIVE		= "AtomicJobActive";

	/**  */
	public static final String JOB_STATE_ATOMIC_COMPLETE	= "AtomicJobComplete";

	/**  */
	public static final String JOB_STATE_WAITING			= "Waiting";

	/**  */
	public static final String JOB_STATE_CONDITION			= "Waiting/Condition";

	/**  */
	public static final String JOB_STATE_DEPARTURE			= "Waiting/Departure";

	/**  */
	public static final String JOB_STATE_ABORT				= "Abort";

	/**  */
	public static final String JOB_STATE_NOT_ABORTING		= "Abort/NotAborting";

	/**  */
	public static final String JOB_STATE_ABORTING			= "Abort/Aborting";

	/**  */
	public static final String JOB_STATE_COMPLETE			= "JobComplete";


//********************************************************************************
//    Define Carrier status
//********************************************************************************

	/**  */
	public static final String CARRIER_STATE_CREATED		= "CarrierCreated";

	/**  */
	public static final String CARRIER_STATE_INSTALLED		= "Installed";

	/**  */
	public static final String CARRIER_STATE_MOVING			= "Installed/Moving";

	/**  */
	public static final String CARRIER_STATE_STORED			= "Installed/Stored";

	/**  */
	public static final String CARRIER_STATE_STORED_ALT		= "Installed/StoredAlt";

	/**  */
	public static final String CARRIER_STATE_REMOVED		= "CarrierRemoved";

	public static final String CARRIER_STATE_BLOCK		= "CarrierBlockSet";


//********************************************************************************
//    Define TSC Avail
//********************************************************************************

	/**  */
	public static final String TSC_AVAILABLE				= "Available";

	/**  */
	public static final String TSC_NOT_AVAILABLE			= "NotAvailable";


//********************************************************************************
//    Define TSC Mode
//********************************************************************************

	/**  */
	public static final String TSC_MODE_UP					= "Up";

	/**  */
	public static final String TSC_MODE_DOWN				= "Down";

	/**  */
	public static final String TSC_MODE_PM					= "PM";
	
	/**  */
	public static final String TSC_MODE_TEST				= "Test";


//********************************************************************************
//    Define TSC Mode
//********************************************************************************

	/**  */
	public static final String PIECE_MODE_UP				= "Up";

	/**  */
	public static final String PIECE_MODE_DOWN				= "Down";

	/**  */
	public static final String PIECE_MODE_PM				= "PM";
	
	/**  */
	public static final String PIECE_MODE_TEST				= "Test";
	

//********************************************************************************
//    Define IDR Mode
//********************************************************************************

	/**  */
	public static final String IDR_MODE_UP					= "Up";

	/**  */
	public static final String IDR_MODE_DOWN				= "Down";


//********************************************************************************
//    Define TSC System State
//********************************************************************************

	/**  */
	public static final String TSC_SYSTEM_NONE				= "None";

	/**  */
	public static final String TSC_SYSTEM_INIT				= "Init";

	/**  */
	public static final String TSC_SYSTEM_PAUSED			= "Paused";

	/**  */
	public static final String TSC_SYSTEM_AUTO				= "Auto";

	/**  */
	public static final String TSC_SYSTEM_PAUSING			= "Pausing";


//********************************************************************************
//    Define TSC Control State
//********************************************************************************

	/**  */
	public static final String TSC_CONTROL_NONE				= "None";

	/**  */
	public static final String TSC_CONTROL_EQP_OFFLINE		= "Offline/Equipment Offline";

	/**  */
	public static final String TSC_CONTROL_ATTEMPT_ONLINE	= "Offline/Attempt Online";

	/**  */
	public static final String TSC_CONTROL_HOST_OFFLINE		= "Offline/Host Offline";

	/**  */
	public static final String TSC_CONTROL_ONLINE_LOCAL		= "Online/Local";

	/**  */
	public static final String TSC_CONTROL_ONLINE_REMOTE	= "Online/Remote";


//********************************************************************************
//    Define TSC Alarm State
//********************************************************************************

	/**  */
	public static final String TSC_ALARMS					= "Alarms";

	/**  */
	public static final String TSC_NO_ALARMS				= "NoAlarms";


//********************************************************************************
//    Define Port Mode
//********************************************************************************

	/**  */
	public static final String PORT_MODE_UP					= "Up";

	/**  */
	public static final String PORT_MODE_DOWN				= "Down";

	/**  */
	public static final String PORT_MODE_PM					= "PM";
	
	/**  */
	public static final String PORT_MODE_TEST				= "Test";

	/**  */
	public static final String PORT_MODE_ERR				= "Error";

	/**  */
	public static final String PORT_MODE_HOLD				= "Hold";

//********************************************************************************
//    Define Port Available
//********************************************************************************

	/**  */
	public static final String PORT_AVAILABLE				= "Available";

	/**  */
	public static final String PORT_AVAIL					= "Avail";

	/**  */
	public static final String PORT_NOT_AVAILABLE			= "NotAvailable";

	/**  */
	public static final String PORT_NOT_AVAIL				= "NotAvail";

//********************************************************************************
//    Define Process status
//********************************************************************************

	/**  */
	public static final String PROC_STATE_STARTING			= "Starting";

	/**  */
	public static final String PROC_STATE_RUNNING			= "Running";

	/**  */
	public static final String PROC_STATE_STOPPING			= "Stopping";

	/**  */
	public static final String PROC_STATE_DOWN				= "Down";
	
	public static final String PROC_MODE_UP					= "Up";

	public static final String PROC_MODE_DOWN				= "Down";

//********************************************************************************
//    Define TSC Model
//********************************************************************************

	/**  */
	public static final String MODEL_STKSEM					= "STKSEM";

	/**  */
	public static final String MODEL_IBSEM					= "IBSEM";


//********************************************************************************
//    Define Crane status
//********************************************************************************

	/**  */
	public static final String CRANE_IDLE					= "Idle";

	/**  */
	public static final String CRANE_ACTIVE					= "Active";


//********************************************************************************
//    Define Vehicle status
//********************************************************************************

	/**  */
	public static final String VEHICLE_ASSIGNED				= "Assigned";

	/**  */
	public static final String VEHICLE_PARKED				= "Parked";

	/**  */
	public static final String VEHICLE_ACQUIRING			= "Acquiring";

	/**  */
	public static final String VEHICLE_DEPOSITING			= "Depositing";

	/**  */
	public static final String VEHICLE_UNASSIGNED			= "NotAssigned";

	/**  */
	public static final String VEHICLE_ENROUTE				= "Enroute";


// とりあえずMacs2のヘッダーで相当するのが無いのでMacs1からコピーしたもの
	public static final String VEHICLE_REMOVED                = "Removed";
	public static final String CONTROL_ONLINE_REMOTE          = "Online/Online-Remote";
	public static final String MACS_CONTROL_NONE              = "None";
	public static final String MACS_CONTROL_INHIBITING		  = "Inhibitting";
	public static final String MACS_CONTROL_STARTUP		  	  = "StartUp";


//********************************************************************************
//    Define Exercise Status
//********************************************************************************

	/**  */
	public static final String DRI_MODIFY					= "Modify";

	/**  */
	public static final String DRI_INIT						= "Init";

	/**  */
	public static final String DRI_ACKWAIT					= "WaitingAck";

	/**  */
	public static final String DRI_MOV						= "Moving";

	/**  */
	public static final String DRI_REJECT					= "Rejected";

	/**  */
	public static final String DRI_END						= "Stop";

	/**  */
	public static final String DRI_COMPLETE					= "Complete";

	/**  */
	public static final String DRI_ABCOMPLETE				= "AbnormalComplete";

	
//********************************************************************************
//    Alias Key
//********************************************************************************
	
	/**  */
	public static final String ALIAS_KEY = "HOST";

//********************************************************************************
//    Etc State 
//********************************************************************************
	/**  */
	public static final String ZERO = "0";
	
}
