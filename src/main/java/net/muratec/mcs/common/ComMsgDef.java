//@formatter:off
/**
 ******************************************************************************
 * @file        ComMsgDef.java
 * @brief       メッセージ定数定義クラス
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 * 2018/11/12 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 * 2018/11/29 MACS4#0059  M17対応                                     T.Iga/CSC
 * 2018/12/03 MACS4#0047  GUI要望分                                   T.Iga/CSC
 * 2018/12/03 MACS4#0049  StageCmd対応(GUI)                           T.Iga/CSC
 * 2019/03/06 MACS4#0114  GUI MCSAlarmクリア対応                      T.Iga/CSC
 * 2019/10/10 MACS4#0219  GUI STAGEコマンドメンテナンス対応           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ定数定義クラス
 * @par    メッセージ送受信に使用する定数定義クラス
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
 * MACS4#0059  M17対応                                                T.Iga/CSC
 * MACS4#0047  GUI要望分                                              T.Iga/CSC
 * MACS4#0049  StageCmd対応(GUI)                                      T.Iga/CSC
 * MACS4#0114  GUI MCSAlarmクリア対応                                 T.Iga/CSC
 * MACS4#0219  GUI STAGEコマンドメンテナンス対応                      T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
public class ComMsgDef {
    // 定数

    // ! フォーマット行区切り文字
    public static final String k_strFromatLineDelimiter = ";";
    // ! フォーマット項目区切り文字
    public static final String k_strFromatColDelimiter = ",";
    // ! サブジェクト区切り文字
    public static final String k_strSubjectDelimiter = ".";
    // ! アプリケーションメッセージ
    public static final String k_strApplicationMessageItem = "A";
    // ! 制御メッセージ
    public static final String k_strControlMessageItem = "C";

    // ! イベント名:キャリア情報作成要求
    public static final String k_strCarrierAdd = "HCSendINSTALL";
    // ! イベント名:キャリア情報削除要求
    public static final String k_strCarrierRemove = "HCSendREMOVE";
    // ! イベント名:搬送Job作成要求
    public static final String k_strTransferJobCreate = "XferJobCreateReq";
    // ! イベント名:搬送Job情報搬送キャンセル
    public static final String k_strTransferJobCancel = "CancelReq";
    //! イベント名:搬送Job情報搬送上書き - 優先度のみ
    public static final String k_strTransferJobOverwritePriority = "PriorityChgReq";
    //! イベント名:搬送Job情報搬送上書き - 行先
    public static final String k_strTransferJobOverwriteDestination = "XferJobCreateReq";
    // ! イベント名:搬送Job情報再開
    public static final String k_strTransferJobResume = "XferJobStepReq";
    // ! イベント名:搬送Job情報削除
    public static final String k_strTransferJobDelete = "TransCarrierJobDelete";
    // ! イベント名:キャリア同期
    public static final String k_strCarrierSync = "SelectEqStatusReq";
    // ! イベント名:DB再読み込み通知
    public static final String k_strCacheUpdate = "MsgCacheUpdate";
    // ! イベント名:AMHSモード変更 - オンライン要求
    public static final String k_strRequestOnLine = "RequestOnLine";
    // ! イベント名:AMHSモード変更 - オフライン要求
    public static final String k_strRequestOffLine = "RequestOffLine";
    // ! イベント名:AMHSモード変更 - オート要求
    public static final String k_strSendResume = "SendResume";
    // ! イベント名:AMHSモード変更 - ポーズ要求
    public static final String k_strSendPause = "SendPause";
    // ! イベント名:AMHS論理状態変更要求
    public static final String k_strAMHSLStateChangeReq = "AmhsLStateChangeReq";
    // ! イベント名:Port論理状態変更要求
    public static final String k_strPortLStateChangeReq = "PortLStateChangeReq";
    // ! イベント名:ユニット論理状態変更要求
    public static final String k_strUnitLStateChangeReq = "UnitLStateChangeReq";
    // ! イベント名:OHBポートグループ論理状態変更要求
    public static final String k_strOhbLStateChangeReq = "OhbLStateChangeReq";
    // ! イベント名:MCS論理状態変更要求
    public static final String k_strMcsLStateChangeReq = "McsLStateChangeReq";
    // ! イベント名:搬送テスト開始要求
    public static final String k_strTransferTestStartReq = "ExerciseStartReq";
    // ! イベント名:レイアウト更新要求
    public static final String k_strLayoutUpdateReq = "MsgLocationChangeReq";
    // ! イベント名:ステージコマンドキャンセル要求 - MACS4#0049 Add
    public static final String k_strStageCancelReq = "TransHCSendStageCancelReq";
    // ! イベント名:ステージコマンド削除要求 - MACS4#0219 Add
    public static final String k_strStageDeleteReq = "TransHCSendStageDeleteReq";
    // ! イベント名:M17更新要求 - MACS4#0059 Add
    public static final String k_strM17McsDataUpdate = "CSCarrierInfoInq";
    // ! イベント名:アラーム削除要求(OHVC以外) - MACS4#0047 Add - MACS4#0114 Del
//  public static final String k_strAlarmClear = "UnitAlarmCleared";
    // ! イベント名:アラーム削除要求(OHVC) - MACS4#0047 Add - MACS4#0114 Del
//  public static final String k_strAlarmClearByOhvc = "SALRepCleared";
    // ! イベント名：アラームリセット要求(MCS) - MACS4#0114 Add
    public static final String k_strAlarmReset = "AlarmResetReq";
    // ! イベント名:メッセージリスト取得
    public static final String k_strGetMessageList = "GetMessageList";

    // ! フォーマット:共通ヘッダ
    public static final String k_strHeader = "header";

    // ! サブジェクト:共通
    public static final String k_strSubject = "subject.";
    // ! サブジェクト:プロジェクト
    public static final String k_strSubProject = "project";
    // ! サブジェクト:システムグループ
    public static final String k_strSubSysGrp = "systemGroup.";
    // ! サブジェクト:プロセスSID
    public static final String k_strSubProcSID = "SID.";
    // ! サブジェクト:イベント名
    public static final String k_strSubEventName = "eventName.";
    // ! サブジェクト:送信処理
    public static final String k_strSubSend = "send.";
    // ! サブジェクト:受信処理
    public static final String k_strSubRecv = "recv.";

    // ! プロセス名:HOST Control Dispatcher
    public static final String k_strHostCtrlDispatcher = "HCD";
    // ! プロセス名:HOST Driver
    public static final String k_strHostDriver = "HD";
    // ! プロセス名:AMHS Control Dispatcher
    public static final String k_strAmhsCtrlDispatcher = "ACD";
    // ! プロセス名:AMHS Driver
    public static final String k_strAmhsDriver = "AD";
    // ! プロセス名:AMHS Control
    public static final String k_strAmhsCtrl = "AC";
    // ! プロセス名:Trans Control
    public static final String k_strTransCtrl = "TC";
    // ! プロセス名:ALL Process
    public static final String k_strCommonEvent = "CE";
    // ! プロセス名:Data Sync Server
    public static final String k_strDataSyncServer = "DS";
    // ! プロセス名:GUI
    public static final String k_strGUI = "GUI";
    // ! プロセス名:Status Server
    public static final String k_strStatusServer = "SS";
    // ! プロセス名:Exercise Server
    public static final String k_strExerciseServer = "EXS";
    // ! プロセス名:Alarm Server - MACS4#0047 Add
    public static final String k_strAlarmServer = "AS";

    // メッセージフォーマット取得キー情報
    // ! 接続情報:接続IP
    public static final String k_strKeyConnectIP = "connectIP";
    // ! 接続情報:接続ポート
    public static final String k_strKeyConnectPort = "connectPort";
    // ! システムノードID
    public static final String k_strKeySysNodeID = "nodeID";

    // 共通ヘッダ
    // ! フォーマットキー:SystemID
    public static final String k_strKeySystemID = "SystemID";
    // ! フォーマットキー:NodeID
    public static final String k_strKeyNodeID = "NodeID";
    // ! フォーマットキー:IsReply
    public static final String k_strKeyReply = "IsReply";
    // ! フォーマットキー:SourceProcType
    public static final String k_strKeySrcProcType = "SourceProcType";
    // ! フォーマットキー:ResponseSubject
    public static final String k_strKeyResSubject = "ResponseSubject";
    // ! フォーマットキー:TimeStamp
    public static final String k_strKeyTimestamp = "TimeStamp";

    // プロセス共通ヘッダ
    // ! フォーマットキー:TransactionNo
    public static final String k_strKeyTransactionNo = "TransactionNo";
    // ! フォーマットキー:HostReceiveTime
    public static final String k_strKeyHostRecvTime = "HostReceiveTime";
    // ! フォーマットキー:AlarmTime - MACS4#0047 Add
    public static final String k_strKeyAlarmTime = "AlarmTime";

    // イベントデータ
    // ! フォーマットキー:AMHSID
    public static final String k_strKeyAmhsID = "AMHSID";
    // ! フォーマットキー:CarrierID
    public static final String k_strKeyCarrierID = "CarrierID";
    // ! フォーマットキー:CarrierLoc
    public static final String k_strKeyCarrierLoc = "CarrierLoc";

    //! フォーマットキー:HostName
    public static final String k_strKeyHostName = "HostName";
    //! フォーマットキー:JobOriginator
    public static final String k_strKeyJobOriginator = "JobOriginator";
    //! フォーマットキー:JobOwner
    public static final String k_strKeyJobOwner = "JobOwner";
    // ! フォーマットキー:JobID
    public static final String k_strKeyJobID = "JobID";
    // ! フォーマットキー:RerouteFlag
    public static final String k_strKeyRerouteFlag = "RerouteFlag";
    // ! フォーマットキー:TransportType
    public static final String k_strKeyTransportType = "TransportType";
    // ! フォーマットキー:CarrierJobSeqCount
    public static final String k_strKeyCarrierJobSeqCount = "CarrierJobSeqCount";
    // ! フォーマットキー:CarrierJobID
    public static final String k_strKeyCarrierJobID = "CarrierJobID";
    // ! フォーマットキー:ZoneID
    public static final String k_strKeyZoneID = "ZoneID";
    // ! フォーマットキー:N2PurgeFlag
    public static final String k_strKeyN2PurgeFlag = "N2PurgeFlag";
    // ! フォーマットキー:FromMachineID
    public static final String k_strKeyFromMachineID = "FromMachineID";
    // ! フォーマットキー:FromPortID
    public static final String k_strKeyFromPortID = "FromPortID";
    // ! フォーマットキー:ToStockerGroup
    public static final String k_strKeyToStockerGroup = "ToStockerGroup";
    // ! フォーマットキー:ProductID
    public static final String k_strKeyProductID = "ProductID";
    // ! フォーマットキー:LotID
    public static final String k_strKeyLotID = "LotID";
    // ! フォーマットキー:DestSeqCount
    public static final String k_strKeyDestSeqCount = "DestSeqCount";
    // ! フォーマットキー:ToMachineID
    public static final String k_strKeyToMachineID = "ToMachineID";
    // ! フォーマットキー:ToPortID
    public static final String k_strKeyToPortID = "ToPortID";
    // ! フォーマットキー:MicroCmndListCount
    public static final String k_strKeyMicroCmndListCount = "MicroCmndListCount";
    // ! フォーマットキー:MicroCmndListControllerID
    public static final String k_strKeyMicroCmndControllerID = "MCControllerID";
    // ! フォーマットキー:FromLocation
    public static final String k_strKeyFromLocation = "FromLocation";
    // ! フォーマットキー:ToLocation
    public static final String k_strToLocation = "ToLocation";
    // ! フォーマットキー:PassingPointListCount
    public static final String k_strPassingPointListCount = "PassingPointListCount";
    // ! フォーマットキー:PassingPointControllerID
    public static final String k_strKeyPassingPointControllerID = "PPControllerID";
    // ! フォーマットキー:PortID
    public static final String k_strKeyPortID = "PortID";
    // ! フォーマットキー:PassingNodeListCount
    public static final String k_strKeyPassingNodeListCount = "PassingNodeListCount";
    // ! フォーマットキー:ListNo
    public static final String k_strKeyListNo = "ListNo";
    // ! フォーマットキー:PassingNodeControllerID
    public static final String k_strKeyPAssingNodeControllerID = "PNControllerID";
    // ! フォーマットキー:StationName
    public static final String k_strKeyStationName = "StationName";
    // ! フォーマットキー:ExpectionStartTime
    public static final String k_strKeyExpStartTime = "ExpectedStartTime";
    // ! フォーマットキー:ExpectionEndTime
    public static final String k_strKeyExpEndTime = "ExpectedEndTime";
    // ! フォーマットキー:MondatoryFlag
    public static final String k_strKeyMandatoryFlag = "MandatoryFlag";
    // ! フォーマットキー:Priority
    public static final String k_strKeyPriority = "Priority";
    //! フォーマットキー:DeleteJobOperator
    public static final String k_strKeyDeleteJobOperator = "DeleteJobOperator";
    // ! フォーマットキー:SyncType
//  public static final String k_strKeySyncType = "SyncType";   // MACS4#0036 Del
    // ! フォーマットキー:ACK
    public static final String k_strKeyAck = "ACK";
    // ! フォーマットキー:CacheId
    public static final String k_strKeyCacheId = "CacheId";
    // ! フォーマットキー:Operator
    public static final String k_strKeyOperator = "Operator";
    // ! フォーマットキー:ONLACK
    public static final String k_strKeyONLACK = "ONLACK";
    // ! フォーマットキー:AMHSLogicalState
    public static final String k_strKeyAMHSLogicalState = "AMHSLogicalState";
    // ! フォーマットキー:Author
    public static final String k_strKeyAuthor = "Author";
    // ! フォーマットキー:PortLogicalState
    public static final String k_strKeyPortLogicalState = "PortLogicalState";
    // ! フォーマットキー:UnitID
    public static final String k_strKeyUnitID = "UnitID";
    // ! フォーマットキー:UnitLogicalState
    public static final String k_strKeyUnitLogicalState = "UnitLogicalState";
    // ! フォーマットキー:OHBID
    public static final String k_strKeyOhbID = "OHBID";
    // ! フォーマットキー:OHBLogicalState
    public static final String k_strKeyOhbLogicalState = "OHBLogicalState";
    // ! フォーマットキー:MCSName
    public static final String k_strKeyMcsName = "MCSName";
    // ! フォーマットキー:MCSLogicalState
    public static final String k_strKeyMcsLogicalState = "MCSLogicalState";
    // ! フォーマットキー:StageID - MACS4#0049 Add
    public static final String k_strKeyStageID = "StageID";
    // ! フォーマットキー:MachineID - MACS4#0059 Add
    public static final String k_strKeyMachineID = "MachineID";
    // ! フォーマットキー:Clock - MACS4#0047 Add
    public static final String k_strKeyClock = "Clock";
    // ! フォーマットキー: AlarmID - MACS4#0047 Add
    public static final String k_strKeyAlarmID = "AlarmID";
    // ! フォーマットキー: AlarmSubCode - MACS4#0047 Add
    public static final String k_strKeyAlarmSubCode = "AlarmSubCode";
    // ! フォーマットキー: ErrorID - MACS4#0047 Add
    public static final String k_strKeyErrorID = "ErrorID";
    // ! フォーマットキー: CommandID - MACS4#0047 Add
    public static final String k_strKeyCommandID = "CommandID";
    // ! フォーマットキー: StockerUnitID - MACS4#0047 Add
    public static final String k_strKeyStockerUnitID = "StockerUnitID";
    // ! フォーマットキー: StockerUnitState - MACS4#0047 Add
    public static final String k_strKeyStockerUnitState = "StockerUnitState";
    // ! フォーマットキー: AlarmLoc - MACS4#0047 Add
    public static final String k_strKeyAlarmLoc = "AlarmLoc";
    // ! フォーマットキー: AmhsType - MACS4#0047 Add
    public static final String k_strKeyAmhsType = "AmhsType";
    // ! フォーマットキー: AlarmCode - MACS4#0047 Add
    public static final String k_strKeyAlarmCode = "AlarmCode";
    // ! フォーマットキー: SetTime - MACS4#0047 Add
    public static final String k_strKeySetTime = "SetTime";
    // ! フォーマットキー: ClearTime - MACS4#0047 Add
    public static final String k_strKeyClearTime = "ClearTime";
    // ! フォーマットキー: EqpName - MACS4#0047 Add
    public static final String k_strKeyEqpName = "EqpName";
    // ! フォーマットキー: SALCode - MACS4#0047 Add
    public static final String k_strKeySALCode = "SALCode";
    // ! フォーマットキー: SALUnitID - MACS4#0047 Add
    public static final String k_strKeySALUnitID = "SALUnitID";
    // ! フォーマットキー: SALText - MACS4#0047 Add
    public static final String k_strKeySALText = "SALText";
    // ! フォーマットキー: ModuleControlEqpID - MACS4#0047 Add
    public static final String k_strKeyModuleControlEqpID = "ModuleControlEqpID";
    // ! フォーマットキー: ModuleControlEqpName - MACS4#0047 Add
    public static final String k_strKeyModuleControlEqpName = "ModuleControlEqpName";
    // ! フォーマットキー: VehiclePos - MACS4#0047 Add
    public static final String k_strKeyVehiclePos = "VehiclePos";
    // ! メッセージボックス用仮定義
    // ! フォーマットキー:DateTime
    public static final String k_strKeyDateTime = "DateTime";
    // ! フォーマットキー:Message
    public static final String k_strKeyMessage = "Message";

    // ! 返信種別:返信有
    public static final byte k_btIsReply = (byte) 1;
    // ! 返信種別:返信無
    public static final byte k_btNoReply = (byte) 0;

    // ! 送信元プロセス種別:GUI
    public static final byte k_btSrcProcTypeGUI = (byte) 6;
}
