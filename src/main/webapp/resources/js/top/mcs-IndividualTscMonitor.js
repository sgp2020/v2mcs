/**
 ******************************************************************************
 * @file        mcs-IndividualTscMonitor.js
 * @brief       個別モニタ(TSCモニタ)関連のJavaScript
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
  /*//20191220 DQY DEL
   *SCREEN = {
    STATE: 0,
    VEHICLE: 1,
    MICRO_CMD: 2,
    MODULE: 3
  };*/
  //20191220 DQY MOD
  SCREEN = {
		  STATE: 0,
		  VEHICLE: 1,
//		  MICRO_CMD: 2,
//		  MODULE: 3
  };

  // 現在表示している画面の番号
  var screenIndex;

  // 直近の検索成功時の検索条件(amhsId)
  var latestAmhsId;

  // AMHS選択用セレクトボックス生成
  var amhsSelBox = new McsSelectBox($('#sel-amhs'));
//  var amhsNameList = screenValue.amhsName;	//20191219 DQY DEL
  var amhsNameList = screenValue.displayNames;  //20191219 DQY ADD
  amhsSelBox.setList(amhsNameList);
//  amhsSelBox.setValue(screenValue.amhsId);	//20191219 DQY DEL
//  amhsSelBox.setValue(screenValue.displayName); //20191219 DQY ADD //20191220 DQY DEL
  amhsSelBox.setValue(screenValue.displayId); //20191220 DQY ADD

  amhsSelBox.onChange(function() {
    /* Step4 2017_08_16 */
    // エラー表示をクリア
    selComp.clearErrors();
    getData(amhsSelBox.getValue(), true);
  });

  // コンポーネントマネージャ
  var selComp = new McsComponentManager();
  /* Step4 2017_08_16 */
//  selComp.add('amhsId', amhsSelBox);//20191219 DQY DEL
//  selComp.add('displayName', amhsSelBox);  //20191219 DQY ADD //20191220 DQY DEL
  selComp.add('displayId', amhsSelBox);  //20191220 DQY ADD

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
  //20191223 DQY ADD START
  var alarmState = new McsTextBox($('#alarm-state'));
  var tscMode = new McsTextBox($('#TSC-mode'));
  var pieceMode = new McsTextBox($('#piece-mode'));
  var pieceAvailable = new McsTextBox($('#piece-available'));
  
  alarmState.setReadonly(true);
  tscMode.setReadonly(true);
  pieceMode.setReadonly(true);
  pieceAvailable.setReadonly(true);
//20191223 DQY ADD END
  // テキストボックス読み取り専用化
  stateCommState.setReadonly(true);
  stateControlState.setReadonly(true);
  stateSystemState.setReadonly(true);
  stateAvailable.setReadonly(true);

  // テーブル
  var stateTable = new McsTable($('#state-table-target'));
  // 行選択不可設定
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
    text: screenText.state.vehicleID,
    display: true
  }];

  // ヘッダ設定(状態テーブル)
  stateTable.setHeader(stateHeader);
  stateTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // 台車画面コンポーネント生成
  // ---------------------------------------
  // 台車テーブル生成
  var vehicleTable = new McsTable($('#vehicle-table-target'));
  // 行選択不可設定
  vehicleTable.setNotRowSelect(true);

  // 台車テーブルヘッダ
  var vehicleHeader = [{
    name: 'vehicleId',
    text: screenText.vehicle.vehicleId,
    display: true
  }, {
    name: 'vehicleState',
    text: screenText.vehicle.state,
    display: true
  }];

  // ヘッダ設定(ポートテーブル)
  vehicleTable.setHeader(vehicleHeader);
  vehicleTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // Microコマンド画面コンポーネント生成
  // ---------------------------------------
  // Microコマンドテーブル生成
  var microCmdTable = new McsTable($('#microCmd-table-target'));
  // 行選択不可設定
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
  // モジュール画面コンポーネント生成
  // ---------------------------------------
  // モジュールテーブル生成
  var moduleTable = new McsTable($('#module-table-target'));
  // 行選択不可設定
  moduleTable.setNotRowSelect(true);

  // モジュールテーブルヘッダ
  var moduleHeader = [{
    name: 'moduleId',
    text: screenText.module.moduleId,
    display: true
  }, {
    name: 'moduleName',
    text: screenText.module.moduleName,
    display: true
  }, {
    name: 'available',
    text: screenText.module.available,
    display: true
  }];

  // ヘッダ設定(モジュールテーブル)
  moduleTable.setHeader(moduleHeader);
  moduleTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

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
    var vehicleBtn = new McsButton($('#btn-vehicle'), screenText.slideMenuBtn.vehicle);
    var microCmdBtn = new McsButton($('#btn-micro-cmd'), screenText.slideMenuBtn.microCmd);
    var moduleBtn = new McsButton($('#btn-module'), screenText.slideMenuBtn.module);
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

    // 台車ボタン押下処理
    vehicleBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // 台車画面のデータ取得、表示
      getVehicle(amhsSelBox.getValue(), false);
    });

    // Microコマンドボタン押下処理
    /*20191220 DQY DEL
     * microCmdBtn.onClick(function() {
       Step4 2017_08_16 
      // エラー表示をクリア
      selComp.clearErrors();
      // Microコマンド画面のデータ取得、表示
      getMicroCmd(amhsSelBox.getValue(), false);
    });
*/
    // モジュールボタン押下処理
    /*20191220 DQY DEL
     * moduleBtn.onClick(function() {
      selComp.clearErrors();
      // モジュール画面のデータ取得、表示
      getModule(amhsSelBox.getValue(), false);
    });*/
    
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
    $('#btn-vehicle').show();
//    $('#btn-micro-cmd').show();//20191220 DQY DEL
//    $('#btn-module').show();//20191220 DQY DEL

    // 各画面の表示切替
    $('#state-screen').show();
    $('#vehicle-screen').hide();
//    $('#microCmd-screen').hide();//20191220 DQY DEL
//    $('#module-screen').hide();//20191220 DQY DEL

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
  function showVehicleScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.VEHICLE;

    // ポートボタン非表示
    $('#btn-state').show();
    $('#btn-vehicle').hide();
//    $('#btn-micro-cmd').show();//20191220 DQY DEL
//    $('#btn-module').show();//20191220 DQY DEL

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#vehicle-screen').show();
//    $('#microCmd-screen').hide();//20191220 DQY DEL
//    $('#module-screen').hide();//20191220 DQY DEL

    // テーブルのヘッダ幅調整
    vehicleTable.resizeColWidth();
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
    $('#btn-vehicle').show();
    $('#btn-micro-cmd').hide();
    $('#btn-module').show();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#vehicle-screen').hide();
    $('#microCmd-screen').show();
    $('#module-screen').hide();

    // テーブルのヘッダ幅調整
    microCmdTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   モジュール画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    モジュール画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showModuleScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.MODULE;

    // モジュールボタン非表示
    $('#btn-state').show();
    $('#btn-vehicle').show();
    $('#btn-micro-cmd').show();
    $('#btn-module').hide();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#vehicle-screen').hide();
    $('#microCmd-screen').hide();
    $('#module-screen').show();

    // テーブルのヘッダ幅調整
    moduleTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   表示画面のデータ取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @param   {boolean} autoReloadFlag(true:自動更新時 / false:手動更新時)
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
      case SCREEN.VEHICLE:
        getVehicle(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      /*//20191220 DQY DEL
       * case SCREEN.MICRO_CMD:
        getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      case SCREEN.MODULE:
        getModule(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);*/
        break;
    }
  }

  /**
   ******************************************************************************
   * @brief   状態取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @param   {boolean} autoReloadFlag(true:自動更新時 / false:手動更新時)
   * @return
   * @retval
   * @attention
   * @note    状態の取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
//  function getState(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191219 DQY DEL
//	function getState(displayName, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191219 DQY ADD //20191220 DQY DEL
	function getState(displayId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191219 DQY ADD   //20191220 DQY DEL
    var url = getUrl('/Individual/GetTscStateInfo');
//    var cond = {//20191219 DQY DEL
//      amhsId: amhsId,
//      ctrlChgFlag: ctrlChgFlag
//    };
    var cond = {
//    		displayName: displayName,//20191220 DQY DEL
    		displayId: displayId,	 //20191220 DQY ADD
    		ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
//      latestAmhsId = amhsId;//20191219 DQY DEL
//      latestAmhsId = displayName;//20191219 DQY ADD //20191220 DQY DEL
      latestAmhsId = displayId;//20191219 DQY ADD	  //20191220 DQY ADD

      // テーブルのスクロール位置を保持
      var top = stateTable.getScrollTop();
      var left = stateTable.getScrollLeft();

      // 表示のクリア
      clearState();

      if (retObj.body) {
        // テキストボックスのデータ
        var textValue = retObj.body.state;
        var tableValue = retObj.body.alarmList;

        // データをテキストボックスにセット
        stateCommState.setValue(textValue.commState);
        stateControlState.setValue(textValue.controlState);
        stateSystemState.setValue(textValue.systemState);
        stateAvailable.setValue(textValue.available);
        
        //20191223 DQY ADD START FOR MCSV2 STATE
        alarmState.setValue(textValue.alarmState);
        tscMode.setValue(textValue.tscMode);
        pieceMode.setValue(textValue.pieceMode);
        pieceAvailable.setValue(textValue.pieceAvailable);
        
        //20191224 DQY ADD FOR COLORS
        //CONTROL_STATE
        var stateControlStateValue = stateControlState.getValue();
        if(stateControlState.getValue()!="Online/Remote"){
        	//stateControlState.background-color("red");
        	$("#state-control-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//stateControlState.background-color("green");
        	$("#state-control-state input[name='colorText']").css('background-color','#38FF61');
        }
        //SYSTEM_STATE(TSC State)
        if(stateSystemState.getValue()!="Auto"){
        	//stateSystemState.background-color("red");
        	$("#state-system-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//stateSystemState.background-color("green");
        	$("#state-system-state input[name='colorText']").css('background-color','#38FF61');
        }
        //ALARM_STATE
        if(alarmState.getValue()!="NoAlarms"){
        	//alarmState.background-color("red");
        	$("#alarm-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//alarmState.background-color("green");
        	$("#alarm-state input[name='colorText']").css('background-color','#38FF61');
        }
        //COMM_STATE
        if(stateCommState.getValue()!="Communicating"){
        	//stateCommState.background-color("red");
        	$("#state-comm-state input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//stateCommState.background-color("green");
        	$("#state-comm-state input[name='colorText']").css('background-color','#38FF61');
        }
        // TSC_MODE
        if(tscMode.getValue()!="Up"){
        	//tscMode.background-color("red");
        	$("#TSC-mode input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//tscMode.background-color("green");
        	$("#TSC-mode input[name='colorText']").css('background-color','#38FF61');
        }
        // TSC_AVAILABLE
        if(stateAvailable.getValue()!="Available"){
        	//stateAvailable.background-color("red");
        	$("#state-available input[name='colorText']").css('background-color','#ff0000');
        }
        else{
        	//stateAvailable.background-color("green");
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
        		//pieceMode.background-color("red");
        		$("#piece-mode input[name='colorText']").css('background-color','#ff0000');
            }
            else{
            	//pieceMode.background-color("green");
            	$("#piece-mode input[name='colorText']").css('background-color','#38FF61');
            }
        	
        }
        //PIECE_AVAILABLE
        if(textValue.pieceAvailable==null || pieceAvailable.pieceMode == "")
        {
        	pieceAvailable.setDisabled(true);
        }
        else{
        	pieceAvailable.setDisabled(false);
        	if(pieceAvailable.getValue()!="Available"){
        		//pieceAvailable.background-color("red");
        		$("#piece-available input[name='colorText']").css('background-color','#ff0000');
        	}
        	else{
        		//pieceAvailable.background-color("green");
        		$("#piece-available input[name='colorText']").css('background-color','#38FF61');
        	}
        	
        }
        //20191223 DQY ADD END FOR MCSV2 STATE
        
        // データをテーブルにセット
        stateTable.addDataList(tableValue);
      }

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
   * @brief   台車取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @param   {boolean} autoReloadFlag(true:自動更新時 / false:手動更新時)
   * @return
   * @retval
   * @attention
   * @note    台車の取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * 2019/12/20 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
   ******************************************************************************
   */
//  function getVehicle(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {	//20191220 DQY DEL
	function getVehicle(displayId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191220 DQY ADD
    var url = getUrl('/Individual/GetTscVehicleInfo');
    var cond = {
//      amhsId: amhsId,		 //20191220 DQY DEL
      displayId: displayId,  //20191220 DQY ADD
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
//      latestAmhsId = amhsId;//20191220 DQY DEL
      latestAmhsId = displayId;  //20191220 DQY DEL

      // テーブルのスクロール位置を保持
      var top = vehicleTable.getScrollTop();
      var left = vehicleTable.getScrollLeft();

      // テーブルのクリア
      vehicleTable.clear();
      // 台車をテーブルにセット
      vehicleTable.addDataList(retObj.body);

      // 台車画面の表示
      showVehicleScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        vehicleTable.setScrollTop(top);
        vehicleTable.setScrollLeft(left);
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
   * @param   {boolean} autoReloadFlag(true:自動更新時 / false:手動更新時)
   * @return
   * @retval
   * @attention
   * @note    Microコマンドの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
   ******************************************************************************
   */
//  function getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191220 DQY DEL
    function getMicroCmd(displayId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191220 DQY ADD
    var url = getUrl('/Individual/GetTscMicroCmdInfo');
    var cond = {
//      amhsId: amhsId,//20191220 DQY DEL
      displayId: displayId,  //20191220 DQY ADD
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
//    latestAmhsId = amhsId;//20191220 DQY DEL
      latestAmhsId = displayId;  //20191220 DQY DEL

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
   * @brief   モジュール取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @param   {boolean} autoReloadFlag(true:自動更新時 / false:手動更新時)
   * @return
   * @retval
   * @attention
   * @note    モジュールの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getModule(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetTscModuleInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = moduleTable.getScrollTop();
      var left = moduleTable.getScrollLeft();

      // テーブルのクリア
      moduleTable.clear();
      // データをテーブルにセット
      moduleTable.addDataList(retObj.body, retObj.rowColorList);

      // モジュール画面の表示
      showModuleScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        moduleTable.setScrollTop(top);
        moduleTable.setScrollLeft(left);
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
    // 状態画面のテーブルをクリア
    stateTable.clear();
  }
});
