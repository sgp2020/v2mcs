package net.muratec.mcs.service.forupgrade;


/**
 * TIBメッセージに使用するキーワードを定義します。
 *
 * @version $Revision: 1.4 $
 * @author M.Takashima(NISP)
 */
public class Keyword
{
    public static int K_DELAY = 60000;
    /** Header */
    public static String K_SID      = "SID";
    public static String K_TIME     = "TIME";
    public static String K_REPLYFLG = "REPLYFLG";
    public static String K_LAPTIME = "LAPTIME";
    public static String K_WAITTIME = "WAITTIME";
    public static String K_SENDTIME = "SENDTIME";
    /** HostTransferCmdReq HostTransferCmdAns*/
    public static String K_HOSTCOMMANDID = "HOSTCOMMANDID";
    /** HostTransferCmdReq */
    public static String K_ORIGINATOR = "ORIGINATOR";
    /** HostTransferCmdReq */
    public static String K_BATCHFLG = "BATCHFLG";
    /** HostTransferCmdReq */
    public static String TOTALCOUNT = "TOTALCOUNT";
    /** 穴埋め用 */
    public static String K_SPLIT = "SPLIT";
    /** HostTransferCmdReq */
    public static String K_COMMANDID = "COMMANDID";
    /** HostTransferCmdReq */
    public static String K_CARRIERID = "CARRIERID";
    /** HostTransferCmdReq */
    public static String K_CARRIERTYPEID = "CARRIERTYPEID";
    /** HostTransferCmdReq */
    public static String K_PRIORITY = "PRIORITY";
    /** HostTransferCmdReq */
    public static String K_BATCHSEQ = "BATCHSEQ";
    /** HostTransferCmdReq */
    public static String K_MANDATORY = "MANDATORY";
    /** HostTransferCmdReq */
    public static String K_DEPARTTIME = "DEPARTTIME";
    /** HostTransferCmdReq */
    public static String K_ARRIVETIME = "ARRIVETIME";
    /** HostTransferCmdReq */
    public static String K_CARRIERSIZE = "CARRIERSIZE";
    /** HostTransferCmdReq */
    public static String K_CARRIERSHAPE = "CARRIERSHAPE";
    /** HostTransferCmdReq */
    public static String K_LOTID = "LOTID";
    /** HostTransferCmdReq */
    public static String K_NEXTDST = "NEXTDST";
    /** HostTransferCmdReq */
    public static String K_CARRIERPRIORITY = "CARRIERPRIORITY";
    /** HostTransferCmdReq */
    public static String K_RCVSRCEQP = "RCVSRCEQP";
    /** HostTransferCmdReq */
    public static String K_RCVSRCLOC = "RCVSRCLOC";
    /** HostTransferCmdReq */
    public static String K_RCVDSTEQP1 = "RCVDSTEQP1";
    /** HostTransferCmdReq */
    public static String K_RCVDSTLOC1 = "RCVDSTLOC1";
    /** HostTransferCmdReq */
    public static String K_RCVDSTEQP2 = "RCVDSTEQP2";
    /** HostTransferCmdReq */
    public static String K_RCVDSTLOC2 = "RCVDSTLOC2";
    /** HostTransferCmdReq */
    public static String K_RCVDSTEQP3 = "RCVDSTEQP3";
    /** HostTransferCmdReq */
    public static String K_RCVDSTLOC3 = "RCVDSTLOC3";
    /** HostTransferCmdReq */
    public static String K_RCVDSTEQP4 = "RCVDSTEQP4";
    /** HostTransferCmdReq */
    public static String K_RCVDSTLOC4 = "RCVDSTLOC4";
    /** HostTransferCmdReq */
    public static String K_RCVDSTEQP5 = "RCVDSTEQP5";
    /** HostTransferCmdReq */
    public static String K_RCVDSTLOC5 = "RCVDSTLOC5";
    /** HostTransferCmdAns */
    public static String K_HCACK = "HCACK";
    /** HostTransferCmdAns */
    public static String K_REASON = "REASON";
    /** HostTransferCmdAns */
    public static String K_CMDACK = "CMDACK";
    /**  */
    public static String K_TEXT = "TEXT";
    /**  */
    public static String K_TSCID = "TSCID";
    /**  */
    public static String K_CARRIERLOC = "CARRIERLOC";
    /**  */
    public static String K_ZONENAME = "ZONENAME";
    /**  */
    public static String K_CLOCK = "CLOCK";
    /**  */
    public static String K_PIECEID = "PIECEID";
    /**  */
    public static String K_MODE = "MODE";
    /**  */
    public static String K_PORTID = "PORTID";
    /**  */
    public static String K_PROCID = "PROCID";
    /**  */
    public static String K_EMPTYSHELVES = "EMPTYSHELVES";
    /**  */
    public static String K_ZONESIZE = "ZONESIZE";
    /**  */
    public static String K_ZONETYPE = "ZONETYPE";
    /**  */
    public static String K_FROMTSCID = "FROMTSCID";
    /**  */
    public static String K_TOTSCID = "TOTSCID";
    /**  */
    public static String K_ACTIVE_CARRIER_FILENAME = "ACTIVE_CARRIER_FILENAME";
    /**  */
    public static String K_ACTIVE_TRANSFER_FILENAME = "ACTIVE_TRANSFER_FILENAME";
    /**  */
    public static String K_PROCESSNAME = "PROCESSNAME";
    /**  */
    public static String K_PID = "PID";
    /**  */
    public static String K_FILENAME = "FILENAME";
    /**  */
    public static String K_LINENO = "LINENO";
    /**  */
    public static String K_LEVEL = "LEVEL";
    /**  */
    public static String K_ERRORCODE = "ERRORCODE";
    /**  */
    public static String K_DEBUGDATA = "DEBUGDATA";
    /**  */
    public static String K_MACSNAME = "MACSNAME";
    /**  */
    public static String K_SOFTREV         = "SOFTREV";
    /**  */
    public static String K_MACSSTATE       = "MACSSTATE";
    /**  */
    public static String K_INHIBITFLG      = "INHIBITFLG";
    /**  */
    public static String K_CAPTUREMODE     = "CAPTUREMODE";
    /**  */
    public static String K_TABLENO         = "TABLENO";
    /**  */
    public static String K_ROUTESTATE      = "ROUTESTATE";
    /**  */
    public static String K_RESULTCODE = "RESULTCODE";
    /**  */
    public static String K_HOSTID = "HOSTID";
    /**  */
    public static String K_COMMENT         = "COMMENT";
    /**  */
    public static String K_TIMERTYPE = "TIMERTYPE";
    /**  */
    public static String K_PROCNAME = "PROCESSNAME";
    /** HostTransferCmdReq */
    public static String K_MSGID = "MSGID";
    
    // --- For ProcessAssign func. ---------------------------------
    /**  */
    public static String K_SEQ_NO = "SEQNO";
    /**  */
    public static String K_EXECSID = "EXECSID";
    /**  */
    public static String K_ASSIGNEDTIME = "ASSIGNEDTIME";
    // -------------------------------------------------------------

}
