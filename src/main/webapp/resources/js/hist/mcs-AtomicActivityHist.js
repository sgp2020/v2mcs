/**
 ******************************************************************************
 * @file        mcs-HostCommInfo.js
 * @brief       HostCommInformation 画面用JavaScript
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/18 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　    天津村研　董
 ******************************************************************************
 */
$(function() {
	
  focus();
  // コンポーネントマネージャ（検索用）
  var searchComp = new McsComponentManager();

  // ステータス色一覧
  //$('#color1').css('background-color', screenText.colorText.CommError);

  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
	SEARCH:    0,
	MACRODATA: 1,
	DOWNLOAD:  2,
    RETURN: 3
  };

  // 現在表示している画面の番号
  var screenIndex;

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  //検索
  var slideMenuSearch = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-search')
  });
//CSV保存用スライドの初期化
  var saveMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-saveMenu')
  });
  
  // テーブル
  var dataTables = new McsDataTables($('#list-table-target'), true);
 
  //戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;
  
  /**
   * ダイアログの生成
   */
  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
  //初期表示処理
  // ---------------------------------------
  
  //右スライドメニュー生成メソッド
  creTopMenu();
  
  //初回検索
  extract({
	  tscId: '',
	  source: '',
	  destination: '',
	  carrierId: '',
	  commandId: '',
	  dateFrom: null,
	  dateTo: null,
	  maxRecords: ''
  });
  
  //検索ボタンを設定する
  createSearchSlide();
  // CSV保存用スライドの生成
  saveCsvSlide();
  
  //MACRODATAテーブルmacroData
  var macroDataTable = new McsTable($('#macroData-table-target'));
  macroDataTable.setNotRowSelect(true);

  // 状態テーブルヘッダ(状態テーブル)
  var macroDataHeader = [{
	    name: 'rum',
	    text: screenText.macroData.rum,
	    display: true
	  }, {
	    name: 'carrierId',
	    text: screenText.macroData.carrierId,
	    display: true
	  }, {
	    name: 'rcvTime',
	    text: screenText.macroData.rcvTime,
	    display: true
	  }, {
	    name: 'startTime',
	    text: screenText.macroData.startTime,
	    display: true
	  }, {
	    name: 'cmpTime',
	    text: screenText.macroData.cmpTime,
	    display: true
	  },{
	    name: 'srcTscId',
	    text: screenText.macroData.srcTscId,
	    display: true
	  }, {
	    name: 'srcLoc',
	    text: screenText.macroData.srcLoc,
	    display: true
	  }, {
	    name: 'dstTscId',
	    text: screenText.macroData.dstTscId,
	    display: true
	  }, {
	    name: 'dstLoc',
	    text: screenText.macroData.dstLoc,
	    display: true
	  },{
	    name: 'dstGroup',
	    text: screenText.macroData.dstGroup,
	    display: true
	  }, {
	    name: 'altTscId',
	    text: screenText.macroData.altTscId,
	    display: true
	  }, {
		  name: 'altLoc',
		  text: screenText.macroData.altLoc,
		  display: true
	  }, {
	    name: 'status',
	    text: screenText.macroData.status,
	    display: true
	  }, {
	    name: 'priority',
	    text: screenText.macroData.priority,
	    display: true
	  },{
	    name: 'cancelReq',
	    text: screenText.macroData.cancelReq,
	    display: true
	  },{
	    name: 'time',
	    text: screenText.macroData.time,
	    display: true
	  },{
	    name: 'hostCommand',
	    text: screenText.macroData.hostCommand,
	    display: true
	  },{
	    name: 'commandId',
	    text: screenText.macroData.commandId,
	    display: true
	  },{
	    name: 'originator',
	    text: screenText.macroData.originator,
	    display: true
	  },{
	    name: 'rerouteReq',
	    text: screenText.macroData.rerouteReq,
	    display: true
	  }];

  // ヘッダ設定(状態テーブル)
  macroDataTable.setHeader(macroDataHeader);
  macroDataTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);
  
  /**
   ******************************************************************************
   * @brief   抽出して画面へ表示する
   * @param   {Object} cond 抽出条件（そのままサーバへ送信されるJSON）
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function extract(cond) {
	  //searchComp.clearErrors();
	  
	  dataTables.getDataAjax({
		  url: getUrl('/AtomicActivityHist/GetAtomicActivityHistList'),
		  cond: cond,
		  searchDataFlag: true,
		  tableCompId: 'H-002-dataTables', // テーブルコンポーネントID
		  success: function(data) {
			  // 成功時
			  // 特にすることなし
			  if (retFlag) {
				  // 戻るボタンが押されたときは閉じない
				  // 戻るボタン用フラグを下す
				  retFlag = false;
				  return;
			  }
		  },
		  serverError: function(data) {
			  // 特にすることなし
			  //searchComp.setErrors(data.result.errorInfoList);
		  },
		  ajaxError: function(status, error) {
			  // 特にすることなし
		  }
	  });
  }

  //showListScreen(false);
  // 一覧画面のデータ取得、表示
  // 自動更新有効化
  /*AutoReloadTimerManager.addTimeoutCallback(function() {
    	//showListScreen(false);
    	AutoReloadTimerManager.start();
  });
  AutoReloadTimerManager.start();*/

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
    var searchBtn = new McsButton($('#list-btn-search'), screenText.btnText.search);
    var macroDataBtn = new McsButton($('#list-btn-macroData'),screenText.btnText.macroData);
    var downLoadBtn = new McsButton($('#list-btn-downLoad'), screenText.btnText.downLoad);
    var rtnBtn = new McsButton($('#list-btn-ret'), screenText.btnText.cancel);

   /* // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
        // エラー表示をクリア
        dataTables.reload();
        retFlag = true;
    });*/

    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
    // 検索ボタン押下
    searchBtn.onClick(function() {
      // 画面の内容を消去
//      searchComp.get('hostName').clear();
//      searchComp.get('commState').clear();
//      searchComp.clearErrors();

      /*// 前回の条件を復元
      var datas = dataTables.getLatestCond();

      searchComp.get('currentTscId').setValue(datas.currentTscId);
      searchComp.get('carrierId').setValue(datas.carrierId);*/

      slideMenuSearch.show();
    });
    
    //macroData
    macroDataBtn.onClick(function() {
    	getMacroData();
    });
    
    //download
    downLoadBtn.onClick(function() {
    	saveMenu.show();
    	//saveCsvSlide();
    });
  }
  /**
   ******************************************************************************
   * @brief   検索メニュー生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  function createSearchSlide() {
	  
	searchComp.clearErrors();
	// 表示画面番号の更新
	   screenIndex = SCREEN.SEARCH;
    // 検索項目の生成
    var tscId  = new McsSelectBox($('#mcs-search-tscId'));
    var source = new McsSelectBox($('#mcs-search-source'));
    var destination = new McsSelectBox($('#mcs-search-destination'));
    var carrierId = new McsTextBox($('#mcs-search-carrierId'));
    var commandId = new McsTextBox($('#mcs-search-commandId'));
    var dateFrom = new McsDateTime($('#mcs-search-dFrom'), screenText.slideSearch.dateFrom, 75,true);
    var dateTo = new McsDateTime($('#mcs-search-dTo'), screenText.slideSearch.dateTo, 75,true);
    var maxRecords = new McsTextBox($('#mcs-search-maxRecords'));
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clear'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    
    var tscIdList = screenValue.tscIds;
    var sourceList= screenValue.sources;
    var destinationList= screenValue.destinations;
    
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('tscId', tscId);
    searchComp.add('source', source);
    searchComp.add('destination', destination);
    searchComp.add('carrierId', carrierId);
    searchComp.add('commandId', commandId);
    searchComp.add('dateFrom', dateFrom);
    searchComp.add('dateTo', dateTo);
    searchComp.add('maxRecords', maxRecords);
    
    carrierId.setMaxLength(64);
    tscId.setList(tscIdList);
    source.setList(sourceList);
    destination.setList(destinationList);
    
    maxRecords.setValue("10000");
    maxRecords.setMaxLength(5);
    
    // 抽出ボタン押下
    extract.onClick(function() {
      
     // エラー解除
     searchComp.clearErrors();
      
      
      // 検索処理
      var url = getUrl('/AtomicActivityHist/GetAtomicActivityHistList');
      var cond = {
    		  tscId: tscId.getValue(),
    		  source: source.getValue(),
    		  destination: destination.getValue(),
    		  carrierId: carrierId.getValue(),
    		  commandId: commandId.getValue(),
    		  dateFrom: dateFrom.getValue(),
    		  dateTo: dateTo.getValue(),
    		  maxRecords: maxRecords.getValue()
      };
      
      var space = "&nbsp&nbsp"; 
      var searchInformation = "";
      var searchInfoTscIds = "";
      var searchInfoSources = "";
      var searchInfoDestinations = "";
      var searchInfoCarrierIds = "";
      var searchInfoCommandIds = "";
      var searchInfoDateFroms = "";
      var searchInfoDateTos = "";
      var searchInfoMaxRecords = "";
      var searchInfoTscId = tscId.getValue();
      var searchInfoSource = source.getValue();
      var searchInfoDestination = destination.getValue();
      var searchInfoCarrierId = carrierId.getValue();
      var searchInfoCommandId = commandId.getValue();
      var searchInfoDateFrom = dateFrom.getValue();
      var searchInfoDateTo = dateTo.getValue();
      var searchInfoMaxRecord = maxRecords.getValue();
      
      if(searchInfoTscId!=null && searchInfoTscId !="")
      {
    	  searchInfoTscIds = " TSCID ["+searchInfoTscId+"]";
      }
      if(searchInfoSource!=null && searchInfoSource !="")
      {
    	  searchInfoSources = " Source " + "[" + searchInfoSource + "]";
      }
      if(searchInfoDestination!=null && searchInfoDestination !="")
      {
    	  searchInfoDestinations = " Destination ["+searchInfoDestination+"]";
      }
      if(searchInfoCarrierId!=null && searchInfoCarrierId !="")
      {
    	  searchInfoCarrierIds = " Carrier ID " + "[" + searchInfoCarrierId + "]";
      }
      if(searchInfoCommandId!=null && searchInfoCommandId !="")
      {
    	  searchInfoCommandIds = " Carrier ID " + "[" + searchInfoCommandId + "]";
      }
      if(searchInfoDateFrom!=null && searchInfoDateFrom !="")
      {
    	  searchInfoDateFroms = " Date&Time " + "[" + searchInfoDateFrom + " - ";
      }
      if(searchInfoDateTo!=null && searchInfoDateTo !="")
      {
    	  searchInfoDateTos = searchInfoDateTo + "]";
      }
      if(searchInfoMaxRecord!=null && searchInfoMaxRecord !="")
      {
    	  searchInfoMaxRecords = " Max Records " + "[" + searchInfoMaxRecord + "]";
      }
      
      $('#searchInfo').html(  searchInfoTscIds+ space + 
				    		  searchInfoSources + space +
				    		  searchInfoDestinations + space +
				    		  searchInfoCarrierIds + space +
				    		  searchInfoCommandIds + space +
				    		  searchInfoDateFroms + 
				    		  searchInfoDateTos + space +
				    		  searchInfoMaxRecords);
      
      var tableCompId = 'H-002-dataTables';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function() {
          // 検索成功時
          if (retFlag) {
//        	  hostName.clear();
//        	  commState.clear();
        	
            // 戻るボタン押下時
            // 戻るボタン用フラグを下す
            retFlag = false;
            return;
          }
          //firstSearchFlag = false;
        },
        serverError: function(result) {
          // 検索失敗時
          searchComp.setErrors(result.result.errorInfoList);
        },
        ajaxError: function() {
          // Ajaxエラー時
        }

      };
      dataTables.getDataAjax(options);
    });
 // クリアボタン押下
    clear.onClick(function() {
      // 各項目を初期化する
    	tscId.clear();
    	source.clear();
    	destination.clear();
    	carrierId.clear();
    	commandId.clear();
    	dateFrom.clear();
    	dateTo.clear();
    	maxRecords.setValue("10000");
    });
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuSearch.hide();
      //20200320
	  //hostName.clear();
	  //commState.clear();
    });

    /**
     * 検索メニュー生成終了
     */
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
    // 状態画面のテーブルをクリア
	dataTables.clear();
  }
  
  /**
   ******************************************************************************
   * @brief  STOCKERINFO一覧画面表示処理
   * @param {Boolean}  reloadFlg true:直前のデータで更新 false:現在のデータで更新
   * @return {Boolean} true
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
   ******************************************************************************
   */
  function showListScreen(reloadFlg) {

    // 全項目非表示
//    hideAllScreen();

    //対象項目表示
    $('#mcs-subheader-menu').show();
    $('#list-screen').show();
    //$('#mcs-subtitle-lst').show();

    //reloadButton.show();
    //cancelButton.show();

//    ctrlSelBox.setEnabled(true);//20200318 dqy del

    if (reloadFlg) {
      dataTables.reload();
    } else {
      // AJax呼び出し
      var values = {};

      // 表示をクリア
      clearState();
      
      dataTables.getDataAjax({
        url: getUrl('/HostCommInfo/GetHostCommInformationList'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'I-009-dataTables',
        success: function(data) {
          // 成功時
        },
        serverError: function(retObj) {
        },
        ajaxError: function(message, status) {
          // 何もすることなし
        }
      });
    }

    return true;
  }
  
  /**
   ******************************************************************************
   * @brief   CSV保存用スライドを生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function saveCsvSlide() {
	  
	// 表示画面番号の更新
	   screenIndex = SCREEN.DOWNLOAD;
	   
    // ******************************************************
    // 検索項目生成
    // ******************************************************
    // datetimePicker生成
    // 開始の項目
    var saveStart = new McsDateTime($('#mcs-saveStartDatetime'), screenText.downLoadText.saveStart, 75);
    // 終了の項目
    var saveEnd = new McsDateTime($('#mcs-saveEndDatetime'), screenText.downLoadText.saveEnd, 75);

    // 非活性項目の設定
    saveStart.setEnabled(false);
    saveEnd.setEnabled(false);

    // 物件対応
    saveStart.hide();
    saveEnd.hide();

    // ******************************************************
    // ボタン生成
    // ******************************************************
    // 決定ボタン
    var saveConfirmButton = new McsButton($('#btn-saveConfirm'), screenText.downLoadBtn.saveConfirm);

    // CSV保存の戻るボタン
    var saveReturnButton = new McsButton($('#btn-saveReturn'), screenText.downLoadBtn.saveReturn);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 決定ボタン押下
    saveConfirmButton.onClick(function() {
      var datas = dataTables.getLatestCond();
      callAjax(getUrl('/AtomicActivityHist/SetCsvAtomicActivityHistList'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/AtomicActivityHist/SaveCsvAtomicActivityHistList');
      },
      // エラー
      function(retObj) {
        // 特にすることなし
      });
    });

    // 戻るボタン押下
    saveReturnButton.onClick(function() {
      saveMenu.hide();
    });
  }
  
  /**
   ******************************************************************************
   * @brief   MacroData取得メソッド
   * @param   {String} commondId 検索条件
   * @param   
   * @param   
   * @return
   * @retval
   * @attention
   * @note    MacroDataの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * 20200401	MacroData													DONG
   ******************************************************************************
   */
   function getMacroData() {
	   
	// メイン画面データの選択したデータを取る 
	var datas = dataTables.getSelectedRowData();   
	if (datas == null) {
	      errorDialog.openAlert(screenText.dialog.listNotSelect, screenText.dialog.listRet, 'alert');
	      return;
	    }
	var commandId = datas[0].commandId;
    var url = getUrl('/AtomicActivityHist/GetMacroData');
    var cond = {
    	commandId: commandId
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新

      // テーブルのスクロール位置を保持
//      var top = macroDataTable.getScrollTop();
//      var left = macroDataTable.getScrollLeft();

      // テーブルのクリア
      macroDataTable.clear();
      // データをテーブルにセット
      macroDataTable.addDataList(retObj.body, retObj.rowColorList); 

      // ポート画面の表示
      showMacroDataScreen();

      // テーブルのスクロール位置を設定
//      if (scrollFixFlag !== undefined && scrollFixFlag) {
//        macroDataTable.setScrollTop(top);
//        macroDataTable.setScrollLeft(left);
//      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
//      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0,true);
  }
  
   /**
    ******************************************************************************
    * @brief   画面表示メソッド
    * @param
    * @return
    * @retval
    * @attention
    * @note    ポート画面の表示を行う。
    * ----------------------------------------------------------------------------
    * VER.        DESCRIPTION                                               AUTHOR
    * ----------------------------------------------------------------------------
    * * 20200401	MacroData SHOW											DONG
    ******************************************************************************
    */
   function showMacroDataScreen() {
     // 表示画面番号の更新
     screenIndex = SCREEN.MACRODATA;

     // 各画面の表示切替
     $('#macroData-screen').show();

     // テーブルのヘッダ幅調整
     macroDataTable.resizeColWidth();
   }
  
});
