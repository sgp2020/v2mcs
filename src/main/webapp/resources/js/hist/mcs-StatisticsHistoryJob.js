/**
 ******************************************************************************
 * @file        mcs-StatisticsHistoryJob.js
 * @brief       StatisticsHistory(Job) 画面用JavaScript
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
 * 2020/04/08 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　    天津村研　董
 ******************************************************************************
 */
$(function() {
	
  focus();
  // コンポーネントマネージャ（検索用）
  var searchComp = new McsComponentManager();

  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
	SEARCH:    0,
	DOWNLOAD:  1,
    RETURN: 2
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
  //true:複数行を選択できる；false：単数行を選択する
  //  var dataTables = new McsDataTables($('#list-table-target'), true);
  var dataTables = new McsDataTables($('#list-table-target'), false);
 
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
	  unit: '',
	  dateFrom: null,
	  dateTo: null,
	  maxRecords: ''
  });
  
  //検索ボタンを設定する
  createSearchSlide();
  // CSV保存用スライドの生成
  saveCsvSlide();
  
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
		  url: getUrl('/StatisticsHistoryJob/GetStatisticsHistoryJobList'),
		  cond: cond,
		  searchDataFlag: true,
		  tableCompId: 'H-005-statisticsHistoryJobList', // テーブルコンポーネントID
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
//      searchComp.clearErrors();

      /*// 前回の条件を復元
      var datas = dataTables.getLatestCond();

      searchComp.get('currentTscId').setValue(datas.currentTscId);
      searchComp.get('carrierId').setValue(datas.carrierId);*/

      slideMenuSearch.show();
    });
    
    //download
    downLoadBtn.onClick(function() {
    	saveMenu.show();
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
    var unit = new McsSelectBox($('#mcs-search-unit'));
    var dateFrom = new McsDateTime($('#mcs-search-dFrom'), screenText.slideSearch.dateFrom, 75,true);
    var dateTo = new McsDateTime($('#mcs-search-dTo'), screenText.slideSearch.dateTo, 75,true);
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clear'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    
    var tscIdList = screenValue.tscIds;
    var sourceList= screenValue.sources;
    var destinationList= screenValue.destinations;
    var unitList= screenValue.units;
    
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('tscId', tscId);
    searchComp.add('source', source);
    searchComp.add('destination', destination);
    searchComp.add('unit', unit);
    searchComp.add('dateFrom', dateFrom);
    searchComp.add('dateTo', dateTo);
    
    tscId.setList(tscIdList);
    source.setList(sourceList);
    destination.setList(destinationList);
    unit.setList(unitList);
    
 // tscId変更時にsource、destinationの要素を取得し、一覧を再検索
    tscId.onChange(function() {
      setSourceList(tscId.getValue());
      setDestinationList(tscId.getValue());
    });
    // 抽出ボタン押下
    extract.onClick(function() {
      
     // エラー解除
     searchComp.clearErrors();
      
      
      // 検索処理
      var url = getUrl('/StatisticsHistoryJob/GetStatisticsHistoryJobList');
      var cond = {
    		  tscId: tscId.getValue(),
    		  source: source.getValue(),
    		  destination: destination.getValue(),
    		  unit: unit.getValue(),
    		  dateFrom: dateFrom.getValue(),
    		  dateTo: dateTo.getValue(),
    		  maxRecords: maxRecords.getValue()
      };
      
      var space = "&nbsp&nbsp"; 
      var searchInformation = "";
      var searchInfoTscIds = "";
      var searchInfoSources = "";
      var searchInfoDestinations = "";
      var searchInfoUnits = "";
      var searchInfoDateFroms = "";
      var searchInfoDateTos = "";
      var searchInfoMaxRecords = "";
      var searchInfoTscId = tscId.getText();
      var searchInfoSource = source.getText();
      var searchInfoDestination = destination.getText();
      var searchInfoUnit = unit.getText();
      var searchInfoDateFrom = dateFrom.getValue();
      var searchInfoDateTo = dateTo.getValue();
      var searchInfoMaxRecord = maxRecords.getValue();
      
      if(searchInfoTscId == "All"){
    	  searchInfoTscId = "";
      }
      if(searchInfoSource == "All"){
    	  searchInfoSource = "";
      }
      if(searchInfoDestination == "All"){
    	  searchInfoDestination = "";
      }
      
      if(searchInfoTscId!=null && searchInfoTscId !="")
      {
    	  searchInfoTscIds = " TSCID ["+searchInfoTscId+"]" + space ;
      }
      if(searchInfoSource!=null && searchInfoSource !="")
      {
    	  searchInfoSources = " Source " + "[" + searchInfoSource + "]" + space ;
      }
      if(searchInfoDestination!=null && searchInfoDestination !="")
      {
    	  searchInfoDestinations = " Destination ["+searchInfoDestination+"]" + space ;
      }
      if(searchInfoUnit!=null && searchInfoUnit !="")
      {
    	  searchInfoUnits = " Unit ["+searchInfoUnit+"]" + space ;
      }
      if(searchInfoDateFrom!=null && searchInfoDateFrom !="")
      {
    	  searchInfoDateFroms = " Date&Time " + "[" + searchInfoDateFrom + " - " ;
      }
      if(searchInfoDateTo!=null && searchInfoDateTo !="")
      {
    	  searchInfoDateTos = searchInfoDateTo + "]" + space ;
      }
      if(searchInfoMaxRecord!=null && searchInfoMaxRecord !="")
      {
    	  searchInfoMaxRecords = " Max Records " + "[" + searchInfoMaxRecord + "]";
      }
      
      $('#searchInfo').html(  searchInfoTscIds+
				    		  searchInfoSources + 
				    		  searchInfoDestinations +
				    		  searchInfoUnits +
				    		  searchInfoDateFroms + 
				    		  searchInfoDateTos +
				    		  searchInfoMaxRecords);
      
      var tableCompId = 'H-005-statisticsHistoryJobList';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function() {
          // 検索成功時
          if (retFlag) {
        	
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
    	unit.clear();
    	dateFrom.clear();
    	dateTo.clear();
    });
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuSearch.hide();
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
      callAjax(getUrl('/StatisticsHistoryJob/SetCsvStatisticsHistoryJobList'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/StatisticsHistoryJob/SaveCsvStatisticsHistoryJobList');
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
   * @brief     tscIdの要素をセットし、Sourceの再検索を行う
   * @param     {String}tscId
   * @return
   * @retval
   * @attention
   * @note    tscId変更時に呼ばれる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * 20200413	setSourceList												DONG
   ******************************************************************************
   */
  function setSourceList(tscId) {
    // tscIdを基にSourcの要素を取得
    var cond = {
    	tscId: tscId
    };
    callAjax(getUrl('/StatisticsHistoryJob/GetSourceSelList'), cond, false, function(resObj) {
      // 成功時、amhsIdに要素をセットし、一覧の再検索を行う
    	searchComp.get('source').setList(resObj.body);
    });
  }
  /**
   ******************************************************************************
   * @brief     tscIdの要素をセットし、Sourceの再検索を行う
   * @param     {String}tscId
   * @return
   * @retval
   * @attention
   * @note    tscId変更時に呼ばれる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * 20200413	setDestinationList												DONG
   ******************************************************************************
   */
  function setDestinationList(tscId) {
    // tscIdを基にSourcの要素を取得
    var cond = {
    	tscId: tscId
    };
    callAjax(getUrl('/StatisticsHistoryJob/GetDestinationSelList'), cond, false, function(resObj) {
      // 成功時、amhsIdに要素をセットし、一覧の再検索を行う
    	searchComp.get('destination').setList(resObj.body);
    });
  }
});
