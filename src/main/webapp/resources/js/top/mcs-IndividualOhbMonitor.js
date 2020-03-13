/**
 ******************************************************************************
 * @file        mcs-IndividualOhbMonitor.js
 * @brief       個別モニタ(OHBモニタ)関連のJavaScript
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
    PORT: 1
  };

  // 現在表示している画面の番号
  var screenIndex;

  // 直近の検索成功時の検索条件(amhsId)
  var latestAmhsId;
  // 直近の検索成功時の検索条件(ohbId)
  var latestOhbId;

  // AMHS選択用セレクトボックス生成
  var amhsSelBox = new McsSelectBox($('#sel-amhs'));
//  var amhsNameList = screenValue.amhsName;  //20191219 dqy del
  var tscNameList = screenValue.displayNames;    //20191219 dqy add
  amhsSelBox.setList(tscNameList);
//  amhsSelBox.setValue(screenValue.amhsId);   //20191219 dqy del
//  amhsSelBox.setValue(screenValue.displayName);//20191219 dqy add //20191224 DQY DEL
  amhsSelBox.setValue(screenValue.displayId); //20191224 DQY ADD
  
  // OHB ID選択用セレクトボックス生成
  var ohbSelBox = new McsSelectBox($('#sel-ohb'));
  var ohbIdList = screenValue.ohbId;
  ohbSelBox.setList(ohbIdList);

  // コントローラのチェンジイベント
  amhsSelBox.onChange(function() {
    /* Step4 2017_08_16 */
    // エラー表示をクリア
    selComp.clearErrors();
    // OHB IDリストを取得
    setOhbIdList(amhsSelBox.getValue());
  });
  // OHB IDのチェンジイベント
  ohbSelBox.onChange(function() {
    /* Step4 2017_08_16 */
    // エラー表示をクリア
    selComp.clearErrors();
    // 検索実行
    getData(amhsSelBox.getValue(), ohbSelBox.getValue(), true);
  });

  // コンポーネントマネージャ
  var selComp = new McsComponentManager();
//  selComp.add('amhsId', amhsSelBox);
  selComp.add('displayName', amhsSelBox);
  selComp.add('ohbId', ohbSelBox);

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  creTopMenu();

  // ---------------------------------------
  // 状態画面コンポーネント生成
  // ---------------------------------------
  // テキストボックス
  //20191226 DQY ADD START FOR STATE
  var stateCommState = new McsTextBox($('#state-comm-state'));
  var stateControlState = new McsTextBox($('#state-control-state'));
  var stateSystemState = new McsTextBox($('#state-system-state'));
  var stateAvailable = new McsTextBox($('#state-available'));
  var alarmState = new McsTextBox($('#alarm-state'));
  var tscMode = new McsTextBox($('#TSC-mode'));
  var pieceMode = new McsTextBox($('#piece-mode'));
  var pieceAvailable = new McsTextBox($('#piece-available'));
  //20191226 DQY ADD END FOR STATE
  
  
  var stateUsedPort = new McsTextBox($('#state-used-port'), 'number');
 //var stateEmptyPort = new McsTextBox($('#state-empty-port'), 'number');//20191226 DQY DEL
  var stateTotalPort = new McsTextBox($('#state-total-port'), 'number');
  var statePortOccupied = new McsTextBox($('#state-port-occupied'), 'number');
  
  // テキストボックス読み取り専用化
  //20191226 DQY ADD START FOR STATE
  alarmState.setReadonly(true);
  tscMode.setReadonly(true);
  pieceMode.setReadonly(true);
  pieceAvailable.setReadonly(true);
  stateCommState.setReadonly(true);
  stateControlState.setReadonly(true);
  stateSystemState.setReadonly(true);
  stateAvailable.setReadonly(true);
  //20191226 DQY ADD END FOR STATE
  
  stateUsedPort.setReadonly(true);
  //stateEmptyPort.setReadonly(true);//20191226 DQY DEL
  stateTotalPort.setReadonly(true);
  statePortOccupied.setReadonly(true);

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
  // Step4 2017_08_09：高さの基準となる領域を修正
//  stateTable.setBodyHeight($('.mcs-content.mcs-with-subheader-large.mcs-with-subtitle').outerHeight() - 40);
  stateTable.setBodyHeight($('.mcs-content.mcs-with-subheader-large.mcs-with-subtitle').outerHeight() );

  // ---------------------------------------
  // ポート画面コンポーネント生成
  // ---------------------------------------
  // ポートテーブル生成
  var portTable = new McsTable($('#port-table-target'));
  // 行選択不可設定
  portTable.setNotRowSelect(true);

  // Step4 2017/09/04
  // ポートテーブルヘッダ
  //20191231 DQY MOD FOR MCSV2->MCSV4
  /*var portHeader = [{
    name: 'ohbPriority',
    align: 'right',
    text: screenText.port.listNo,
    display: true
  }, {
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
    name: 'carrierId',
    text: screenText.port.carrierId,
    display: true
  }];*/
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
  // ヘッダ設定(ポートテーブル)
  portTable.setHeader(portHeader);
  // Step4 2017_08_09：高さの基準となる領域を修正
  portTable.setBodyHeight($('.mcs-content.mcs-with-subheader-large.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // 初期表示処理
  // ---------------------------------------
  // 状態画面の表示
  showStateScreen();
  // 初期検索実施
  getData(amhsSelBox.getValue(), ohbSelBox.getValue(), false);
  // 自動更新有効化
  AutoReloadTimerManager.addTimeoutCallback(function() {
    if (latestAmhsId !== undefined && latestOhbId !== undefined) {
      getData(latestAmhsId, latestOhbId, false, true, true);
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
    var rtnBtn = new McsButton($('#btn-return'), screenText.slideMenuBtn.ret);

    // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
      if (latestAmhsId !== undefined && latestOhbId !== undefined) {
        /* Step4 2017_08_16 */
        // エラー表示をクリア
        selComp.clearErrors();

        getData(latestAmhsId, latestOhbId, false, true);
      }
    });

    // 状態ボタン押下処理
    stateBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // 状態画面のデータ取得、表示
      getState(amhsSelBox.getValue(), ohbSelBox.getValue(), false);
    });

    // ポートボタン押下処理
    portBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // ポート画面のデータ取得、表示
      getPort(amhsSelBox.getValue(), ohbSelBox.getValue(), false);
    });

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

    // 各画面の表示切替
    $('#state-screen').show();
    $('#port-screen').hide();

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

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').show();

    // テーブルのヘッダ幅調整
    portTable.resizeColWidth();
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
  function getData(amhsId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    switch (screenIndex) {
      case SCREEN.STATE:
        getState(amhsId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      case SCREEN.PORT:
        getPort(amhsId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
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
   * @return
   * @retval
   * @attention
   * @note    状態の取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
//  function getState(amhsId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191225 DQY DEL
	function getState(displayId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191225 DQY ADD
    var url = getUrl('/Individual/GetOhbStateInfo');
    var cond = {
//    amhsId: amhsId,	   //20191225 DQY DEL
      displayId: displayId,//20191225 DQY ADD

      ohbId: ohbId,
      ohbChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHS IDを更新
//      latestAmhsId = amhsId;//20191225 DQY DEL
      latestAmhsId = displayId;//20191225 DQY ADD
      // 直近の検索成功時のOHB IDを更新
      latestOhbId = ohbId;

      // テーブルのスクロール位置を保持
      var top = stateTable.getScrollTop();
      var left = stateTable.getScrollLeft();

      // 状態画面の表示をクリア
      clearState();

      if (retObj.body) {
        // テキストボックスのデータ
        var textValue = retObj.body.state;
        // テーブルのデータ
        var tableValue = retObj.body.alarmList;

        // データをテキストボックスにセット
        //20191226 DQY ADD START FOR MCSV2 STATE
        stateCommState.setValue(textValue.commState);
        stateControlState.setValue(textValue.controlState);
        stateSystemState.setValue(textValue.systemState);
        stateAvailable.setValue(textValue.available);
        
        alarmState.setValue(textValue.alarmState);
        tscMode.setValue(textValue.tscMode);
        pieceMode.setValue(textValue.pieceMode);
        pieceAvailable.setValue(textValue.pieceAvailable);
        
        stateUsedPort.setValue(textValue.zoneOccupied);
        //stateEmptyPort.setValue(textValue.zoneEmpty);//20191225 DQY DEL
        stateTotalPort.setValue(textValue.zoneCapacity);
        statePortOccupied.setValue(textValue.zoneUtility);
        
        //20191226 DQY ADD FOR COLORS START
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
        //20191226 DQY ADD FOR COLORS  END
        //20191226 DQY ADD END FOR MCSV2 STATE

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
//function getPort(amhsId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191225 DQY DEL
  function getPort(displayId, ohbId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {//20191225 DQY ADD
    var url = getUrl('/Individual/GetOhbPortInfo');
    var cond = {
//      amhsId: amhsId,//20191225 DQY DEL
      displayId: displayId,//20191225 DQY ADD
      ohbId: ohbId,
      ohbChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
//      latestAmhsId = amhsId;//20191225 DQY DEL
      latestAmhsId = displayId;//20191225 DQY ADD
      // 直近の検索成功時のOHB IDを更新
      latestOhbId = ohbId;

      // テーブルのスクロール位置を保持
      var top = portTable.getScrollTop();
      var left = portTable.getScrollLeft();

      // データをテーブルにセット
      portTable.clear();
//      portTable.addDataList(retObj.body, retObj.rowColorList);//20200102 DQY DEL
      portTable.addPortDataList(retObj.body, retObj.rowColorList);//20200102 DQY ADD

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

    // 取得データ0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   OHB IDリスト取得メソッド
   * @param   {String} amhsId セレクトボックスで選択されたAMHS_ID
   * @return
   * @retval
   * @attention
   * @note     検索結果をOHB ID選択用セレクトボックスにセットする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
   ******************************************************************************
   */
  /* 20191225 DQY DEL
   * function setOhbIdList(amhsId) {
    var url = getUrl('Individual/GetOhbIdList');
    var data = {
      amhsId: amhsId
    };
    callAjax(url, data, false, function(retObj) {
      // 成功時の処理
      // 検索結果をセレクトボックスにセット
      var selList = retObj.body;

      ohbSelBox.setList(selList);
      getData(amhsSelBox.getValue(), ohbSelBox.getValue(), true, true);
    });
  }*/
  function setOhbIdList(displayName) {
	  var url = getUrl('Individual/GetOhbIdList');
	  var data = {
		displayName: displayName
	  };
	  callAjax(url, data, false, function(retObj) {
		  // 成功時の処理
		  // 検索結果をセレクトボックスにセット
		  var selList = retObj.body;
		  
		  ohbSelBox.setList(selList);
		  getData(amhsSelBox.getValue(), ohbSelBox.getValue(), true, true);
	  });
  }

  /**
   ******************************************************************************
   * @brief   状態画面のクリア機能
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
    // テキストボックスをクリア
    stateUsedPort.clear();
    //stateEmptyPort.clear();//20191226 DQY DEL
    stateTotalPort.clear();
    statePortOccupied.clear();
    // テーブルデータをクリア
    stateTable.clear();
  }
});
