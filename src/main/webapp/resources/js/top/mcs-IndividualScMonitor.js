/**
 ******************************************************************************
 * @file        mcs-IndividualScMonitor.js
 * @brief       個別モニタ(SCモニタ)関連のJavaScript
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
$(function() {
  /* Step4 2017_08_10：ウィンドウ生成時にフォーカスを当てるよう修正 */
  focus();

  // Step4 2017_09_06
  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
    STATE: 0,
    PORT: 1,
    //MICRO_CMD: 2,  
    //TRN_JOB: 3
  };

  // 現在表示している画面の番号
  var screenIndex;

  // 直近の検索成功時の検索条件(amhsId)
  var latestAmhsId;

  // AMHS選択用セレクトボックス生成
  var amhsSelBox = new McsSelectBox($('#sel-amhs'));
  //var amhsNameList = screenValue.amhsName;  //20191223 Song Del
  var amhsNameList = screenValue.displayNames;  //20191223 Song Add
  amhsSelBox.setList(amhsNameList);
  //amhsSelBox.setValue(screenValue.amhsId);  //20191223 Song Del
  amhsSelBox.setValue(screenValue.displayId); //20191223 Song Add

  amhsSelBox.onChange(function() {
    /* Step4 2017_08_16 */
    // エラー表示をクリア
    selComp.clearErrors();
    getData(amhsSelBox.getValue(), true);
  });

  // コンポーネントマネージャ
  var selComp = new McsComponentManager();
  /* Step4 2017_08_16 */
  //selComp.add('amhsId', amhsSelBox);  //20191223 Song Del
  selComp.add('displayId', amhsSelBox);  //20191223 Song Add

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  creTopMenu();

  // ---------------------------------------
  // 状態画面コンポーネント生成
  // ---------------------------------------
  // テキストボックス
  var stateCommState = new McsTextBox($('#state-comm-state'));
  var stateControlState = new McsTextBox($('#state-control-state'));
  var stateSystemState = new McsTextBox($('#state-system-state'));
  var stateAvailable = new McsTextBox($('#state-available'));
  
  //20191225 Song Del Start
  //var stateZoneOccupied = new McsTextBox($('#state-zone-occupied'), 'number');
  //var stateZoneCapacity = new McsTextBox($('#state-zone-capacity'), 'number');
  //var stateZoneUtility = new McsTextBox($('#state-zone-utility'), 'number');
  //var stateAmhsLState = new McsTextBox($('#state-amhs-l-state'));
  //20191225 Song Del End
  
  // テキストボックス読み取り専用化
  stateCommState.setReadonly(true);
  stateControlState.setReadonly(true);
  stateSystemState.setReadonly(true);
  stateAvailable.setReadonly(true);
  
  //20191225 Song Del Start
  //stateZoneOccupied.setReadonly(true);
  //stateZoneCapacity.setReadonly(true);
  //stateZoneUtility.setReadonly(true);
  //stateAmhsLState.setReadonly(true);
  //20191225 Song Del End
  
  //20191225 Song ADD START
  var alarmState = new McsTextBox($('#alarm-state'));
  var ocdcMode = new McsTextBox($('#OCDC-mode'));
  var pieceMode = new McsTextBox($('#piece-mode'));
  var pieceAvailable = new McsTextBox($('#piece-available'));
  
  alarmState.setReadonly(true);
  ocdcMode.setReadonly(true);
  pieceMode.setReadonly(true);
  pieceAvailable.setReadonly(true);
  //20191225 Song ADD END

  // テーブル
  var stateTable = new McsTable($('#state-table-target'));
  stateTable.setNotRowSelect(true);

  // 状態テーブルヘッダ(状態テーブル)
  var stateHeader = [{
    name: 'setTime',
    text: screenText.state.alarmDateTime,
    display: true
  }, {
    name: 'alarmId',
    text: screenText.state.alarmId,
    display: true
  }, {
    name: 'alarmText',
    text: screenText.state.alarmText,
    display: true
  }, {
    name: 'alarmLoc',
    text: screenText.state.alarmLoc,
    display: true
  }, {
    name: 'vehicleId',
    text: screenText.state.vehicleId,
    display: true
  }];

  // ヘッダ設定(状態テーブル)
  stateTable.setHeader(stateHeader);
  stateTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // ポート画面コンポーネント生成
  // ---------------------------------------
  // ポートテーブル生成
  var portTable = new McsTable($('#port-table-target'));
  portTable.setNotRowSelect(true);

  /*
  //20191231 Song Del Start For v4 
  // ポートテーブルヘッダ
  var portHeader = [{
    name: 'portId',
    text: screenText.port.portId,
    display: true
  }, {
    name: 'portType',
    text: screenText.port.type,
    display: true
  }, {
    name: 'portIo',
    text: screenText.port.ioMode,
    display: true
  }, {
    name: 'portAvail',
    text: screenText.port.available,
    display: true
  }, {
    name: 'portLState',
    text: screenText.port.logicalState,
    display: true
  }];
  //20191231 Song Del End For v4 
  */
  //20191231 Song Add Start For v2 
  var portHeader = [{
	    name: 'portAbbreviation',
	    text: screenText.port.portId,
	    display: true
	  },  {
	    name: 'portMode',
	    text: screenText.port.ioMode,
	    display: true
	  }, {
	    name: 'Available',
	    text: screenText.port.available,
	    display: true
	  }];
  //20191231 Song Add End For v2 
  
  // ヘッダ設定(ポートテーブル)
  portTable.setHeader(portHeader);
  portTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // Microコマンド画面コンポーネント生成
  // ---------------------------------------
  // Microコマンドテーブル生成
  var microCmdTable = new McsTable($('#microCmd-table-target'));
  microCmdTable.setNotRowSelect(true);

  // Step4 2017/09/04
  // Microコマンドテーブルヘッダ
  var microCmdHeader = [{
    name: 'commandId',
    text: screenText.microCmd.commandId,
    display: true
  }, {
    name: 'priority',
    align: 'right',
    text: screenText.microCmd.priority,
    display: true
  }, {
    name: 'carrierId',
    text: screenText.microCmd.carrierId,
    display: true
  }, {
    name: 'srcLoc',
    text: screenText.microCmd.microFrom,
    display: true
  }, {
    name: 'dstLoc',
    text: screenText.microCmd.microTo,
    display: true
  }, {
    name: 'carrierJobState',
    text: screenText.microCmd.jobState,
    display: true
  }, {
    name: 'waitInTime',
    text: screenText.microCmd.waitInTime,
    display: true
  }];

  // ヘッダ設定(Microコマンドテーブル)
  microCmdTable.setHeader(microCmdHeader);
  microCmdTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // 搬送Job画面コンポーネント生成
  // ---------------------------------------
  // 搬送Jobテーブル生成
  var trnJobTable = new McsTable($('#trnJob-table-target'));
  trnJobTable.setNotRowSelect(true);

  // Step4 2017/09/04
  // 搬送Jobテーブルヘッダ
  var trnJobHeader = [{
    name: 'receivedTime',
    text: screenText.transferJob.waitTime,
    display: true
  }, {
    name: 'jobOwner',
    text: screenText.transferJob.jobOwner,
    display: true
  }, {
    name: 'carrierId',
    text: screenText.transferJob.carrierId,
    display: true
  }, {
    name: 'priority',
    align: 'right',
    text: screenText.transferJob.priority,
    display: true
  }, {
    name: 'carrierJobState',
    text: screenText.transferJob.carrierJobState,
    display: true
  }, {
    name: 'currentLoc',
    text: screenText.transferJob.location,
    display: true
  }, {
    name: 'srcAmhsId',
    text: screenText.transferJob.fromAmhsId,
    display: true
  }, {
    name: 'srcLoc',
    text: screenText.transferJob.fromPort,
    display: true
  }, {
    name: 'dstAmhsId',
    text: screenText.transferJob.toAmhsId,
    display: true
  }, {
    name: 'dstLoc',
    text: screenText.transferJob.toPort,
    display: true
  }, {
    name: 'microFrom',
    text: screenText.transferJob.microFrom,
    display: true
  }, {
    name: 'microTo',
    text: screenText.transferJob.microTo,
    display: true
  }, {
    name: 'amhsId',
    text: screenText.transferJob.controller,
    display: true
  }, {
    name: 'waitInTime',
    text: screenText.transferJob.waitInTime,
    display: true
  }];

  // ヘッダ設定(搬送Jobテーブル)
  trnJobTable.setHeader(trnJobHeader);
  trnJobTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // 初期表示処理
  // ---------------------------------------
  // 状態画面の表示
  showStateScreen();
  // 状態画面のデータ取得、表示
  getState(amhsSelBox.getValue(), true);
  // 自動更新有効化
  AutoReloadTimerManager.addTimeoutCallback(function() {
    if (latestAmhsId !== undefined) {
      getData(latestAmhsId, false, true, true);
    }
    AutoReloadTimerManager.start();
  });
  AutoReloadTimerManager.start();

  /**
   ******************************************************************************
   * @brief   右スライドメニュー生成メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    ボタン等のコンポーネント生成を行い、各処理を設定。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function creTopMenu() {
    // ボタン生成
    var reloadBtn = new McsButton($('#btn-reload'), screenText.slideMenuBtn.reload);
    var stateBtn = new McsButton($('#btn-state'), screenText.slideMenuBtn.state);
    var portBtn = new McsButton($('#btn-port'), screenText.slideMenuBtn.port);
    //var microCmdBtn = new McsButton($('#btn-micro-cmd'), screenText.slideMenuBtn.microCmd);
    //var trnJobBtn = new McsButton($('#btn-trn-job'), screenText.slideMenuBtn.transferJob);
    var rtnBtn = new McsButton($('#btn-return'), screenText.slideMenuBtn.ret);

    // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
      if (latestAmhsId !== undefined) {
        /* Step4 2017_08_16 */
        // エラー表示をクリア
        selComp.clearErrors();

        getData(latestAmhsId, false, true);
      }
    });

    // 状態ボタン押下処理
    stateBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // 状態画面のデータ取得、表示
      getState(amhsSelBox.getValue(), false);
    });

    // ポートボタン押下処理
    portBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // ポート画面のデータ取得、表示
      getPort(amhsSelBox.getValue(), false);
    });

    
    // Microコマンドボタン押下処理
//    microCmdBtn.onClick(function() {
//      /* Step4 2017_08_16 */
//      // エラー表示をクリア
//      selComp.clearErrors();
//      // Microコマンド画面のデータ取得、表示
//      getMicroCmd(amhsSelBox.getValue(), false);
//    });

    // 搬送Jobボタン押下処理
//    trnJobBtn.onClick(function() {
//      /* Step4 2017_08_16 */
//      // エラー表示をクリア
//      selComp.clearErrors();
//      // 搬送Job画面のデータ取得、表示
//      getTrnJob(amhsSelBox.getValue(), false);
//    });

    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
  }

  /**
   ******************************************************************************
   * @brief   状態画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    状態画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showStateScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.STATE;

    // 状態ボタン非表示
    $('#btn-state').hide();
    $('#btn-port').show();
    $('#btn-micro-cmd').show();
    $('#btn-trn-job').show();

    // 各画面の表示切替
    $('#state-screen').show();
    $('#port-screen').hide();
    $('#microCmd-screen').hide();
    $('#trnJob-screen').hide();

    // テーブルのヘッダ幅調整
    stateTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   ポート画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    ポート画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showPortScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.PORT;

    // ポートボタン非表示
    $('#btn-state').show();
    $('#btn-port').hide();
    //$('#btn-micro-cmd').show();
    //$('#btn-trn-job').show();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').show();
    //$('#microCmd-screen').hide();
    //$('#trnJob-screen').hide();

    // テーブルのヘッダ幅調整
    portTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   Microコマンド画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    Microコマンド画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showMicroCmdScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.MICRO_CMD;

    // Micoroコマンドボタン非表示
    $('#btn-state').show();
    $('#btn-port').show();
    $('#btn-micro-cmd').hide();
    $('#btn-trn-job').show();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').hide();
    $('#microCmd-screen').show();
    $('#trnJob-screen').hide();

    // テーブルのヘッダ幅調整
    microCmdTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   搬送Job画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    搬送Job画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showTrnJobScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.TRN_JOB;

    // 搬送Jobボタン非表示
    $('#btn-state').show();
    $('#btn-port').show();
    $('#btn-micro-cmd').show();
    $('#btn-trn-job').hide();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').hide();
    $('#microCmd-screen').hide();
    $('#trnJob-screen').show();

    // テーブルのヘッダ幅調整
    trnJobTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   表示画面のデータ取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    現在表示している画面のデータを取得する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getData(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    switch (screenIndex) {
      case SCREEN.STATE:
        getState(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      case SCREEN.PORT:
        getPort(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
//      case SCREEN.MICRO_CMD:
//        getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
//        break;
//      case SCREEN.TRN_JOB:
//        getTrnJob(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
//        break;
    }
  }

  /**
   ******************************************************************************
   * @brief   状態取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    状態の取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
    //function getState(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) { //20191225 Song DEL
   function getState(displayId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191225 Song ADD   
    var url = getUrl('/Individual/GetScStateInfo');
    var cond = {
      //amhsId: amhsId,      //20191223 Song Del
      displayId: displayId,	 //20191223 Song Add
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      //latestAmhsId = amhsId;   //20191223 Song Del
      latestAmhsId = displayId;  //20191223 Song Add

      // スクロール位置を保持
      var top = stateTable.getScrollTop();
      var left = stateTable.getScrollLeft();

      // 表示をクリア
      clearState();

      if (retObj.body) {
        // テキストボックスのデータ
        var textValue = retObj.body.state;
        // テーブルのデータ
        var tableValue = retObj.body.alarmList;

        // データをテキストボックスにセット
        stateCommState.setValue(textValue.commState);
        stateControlState.setValue(textValue.controlState);
        stateSystemState.setValue(textValue.systemState);
        stateAvailable.setValue(textValue.available);
        
        //20191225 Song Del Start
        //stateZoneOccupied.setValue(textValue.zoneOccupied);
        //stateZoneCapacity.setValue(textValue.zoneCapacity);
        //stateZoneUtility.setValue(textValue.zoneUtility);
        //stateAmhsLState.setValue(textValue.amhsLState);
        //20191225 Song Del End
        
        //20191225 Song ADD START FOR MCSV2 STATE
        alarmState.setValue(textValue.alarmState);
        ocdcMode.setValue(textValue.ocdcMode);
        pieceMode.setValue(textValue.pieceMode);
        pieceAvailable.setValue(textValue.pieceAvailable);
        
        //20191225 Song ADD START FOR COLORS 
        //CONTROL_STATE
        var stateControlStateValue = stateControlState.getValue();
        if(stateControlState.getValue()!="Online/Remote"){
        	$("#state-control-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#state-control-state input[name='colorText']").css('background-color','#38FF61');
        }
        //SYSTEM_STATE(OCDC State)
        if(stateSystemState.getValue()!="Auto"){
        	$("#state-system-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#state-system-state input[name='colorText']").css('background-color','#38FF61');
        }
        //ALARM_STATE
        if(alarmState.getValue()!="NoAlarms"){
        	$("#alarm-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#alarm-state input[name='colorText']").css('background-color','#38FF61');
        }
        //COMM_STATE
        if(stateCommState.getValue()!="Communicating"){
        	$("#state-comm-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#state-comm-state input[name='colorText']").css('background-color','#38FF61');
        }
        // TSC_MODE
        if(ocdcMode.getValue()!="Up"){
        	$("#OCDC-mode input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#OCDC-mode input[name='colorText']").css('background-color','#38FF61');
        }
        // TSC_AVAILABLE
        if(stateAvailable.getValue()!="Available"){
        	$("#state-available input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	$("#state-available input[name='colorText']").css('background-color','#38FF61');
        }
        //PIECE_MODE
        if(textValue.pieceMode==null || textValue.pieceMode == "")
        {
        	pieceMode.setDisabled(true);
        }
        else{
        	pieceMode.setDisabled(false);
        	if(pieceMode.getValue()!="Up"){
        		$("#piece-mode input[name='colorText']").css('background-color','#ff0000');
            }
            else{
            	$("#piece-mode input[name='colorText']").css('background-color','#38FF61');
            }
        	
        }
        //PIECE_AVAILABLE
        if(textValue.pieceAvailable==null || textValue.pieceAvailable == "")
        {
        	pieceAvailable.setDisabled(true);
        }
        else{
        	pieceAvailable.setDisabled(false);
        	if(pieceAvailable.getValue()!="Available"){
        		$("#piece-available input[name='colorText']").css('background-color','#ff0000');
        	}
        	else{
        		$("#piece-available input[name='colorText']").css('background-color','#38FF61');
        	}
        	
        }
        //20191225 Song ADD END FOR COLORS 
        //20191225 Song ADD END FOR MCSV2 STATE
        
        // データをテーブルにセット
        stateTable.addDataList(tableValue);
      }

      // 選択したコントローラのAMHSタイプによる状態の項目表示切替
      //toggleStockerZoneRlt(amhsId);     //20191225 Song Del

      // 状態画面の表示
      showStateScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        stateTable.setScrollTop(top);
        stateTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   ポート取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    ポートの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  //function getPort(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) { //20191223 Song Del
   function getPort(displayId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191223 Song Add
    var url = getUrl('/Individual/GetScPortInfo');
    var cond = {
      //amhsId: amhsId,      //20191223 Song Del
      displayId: displayId,  //20191223 Song Add
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      //latestAmhsId = amhsId;   //20191223 Song Del
      latestAmhsId = displayId;  //20191223 Song Add

      // テーブルのスクロール位置を保持
      var top = portTable.getScrollTop();
      var left = portTable.getScrollLeft();

      // テーブルのクリア
      portTable.clear();
      // データをテーブルにセット
      //portTable.addDataList(retObj.body, retObj.rowColorList); //20200102 Song Del
      portTable.addPortDataList(retObj.body, retObj.rowColorList); //20200102 Song Add

      // ポート画面の表示
      showPortScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        portTable.setScrollTop(top);
        portTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   Microコマンド取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    Microコマンドの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetScMicroCmdInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = microCmdTable.getScrollTop();
      var left = microCmdTable.getScrollLeft();

      // テーブルのクリア
      microCmdTable.clear();
      // データをテーブルにセット
      microCmdTable.addDataList(retObj.body, retObj.rowColorList);

      // Microコマンド画面の表示
      showMicroCmdScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        microCmdTable.setScrollTop(top);
        microCmdTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   搬送Job取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    搬送Jobの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getTrnJob(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetScTransferJobInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = trnJobTable.getScrollTop();
      var left = trnJobTable.getScrollLeft();

      // テーブルのクリア
      trnJobTable.clear();
      // データをテーブルにセット
      trnJobTable.addDataList(retObj.body, retObj.rowColorList);

      // 搬送Job画面の表示
      showTrnJobScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        trnJobTable.setScrollTop(top ? top : 0);
        trnJobTable.setScrollLeft(left ? left : 0);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   StockerZoneRlt表示切替メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @return
   * @retval
   * @attention
   * @note     引数のAMHSIDに紐付くAMHSタイプが3なら非表示、それ以外は表示
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function toggleStockerZoneRlt(amhsId) {
    if (_getAmhsTypeByAmhsId(amhsId) == AMHS_TYPE.LFC) {
      $('.state-stocker-zone-rlt').hide();
    } else {
      $('.state-stocker-zone-rlt').show();
    }
  }

  /**
   ******************************************************************************
   * @brief   AMHSタイプ取得メソッド
   * @param   {String} amhsId AMHSタイプ取得元のAMHSID
   * @return  {String} AMHSタイプ
   * @retval
   * @attention
   * @note     対象のAMHSIDが存在しなければnullを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function _getAmhsTypeByAmhsId(amhsId) {
    for (var i = 0; i < amhsNameList.length; i++) {
      if (amhsNameList[i][0] == amhsId) {
        return amhsNameList[i][2];
      }
    }
    return null;
  }

  /**
   ******************************************************************************
   * @brief   状態画面の表示クリア機能
   * @param
   * @return
   * @retval
   * @attention
   * @note     状態画面のコンポーネントをクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function clearState() {
    // 状態画面のテキストボックスをクリア
    stateCommState.clear();
    stateControlState.clear();
    stateSystemState.clear();
    stateAvailable.clear();
    //20191225 Song Mod Start
    //stateZoneOccupied.clear();
    //stateZoneCapacity.clear();
    //stateZoneUtility.clear();
    //stateAmhsLState.clear();
    alarmState.clear();
    ocdcMode.clear();
    pieceMode.clear();
    pieceAvailable.clear();
    //20191225 Song Mod End
    // 状態画面のテーブルをクリア
    stateTable.clear();
  }
});
