﻿/**
 ******************************************************************************
 * @file        mcs-VehicleStatisticsHistory.js
 * @brief       StatisticsHistory(Vehicle) 画面用JavaScript
 * @par
 * @author      天津村研　SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                           AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/10 v1.0.0      初版作成                                      　　　　天津村研　SGP
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
  
  //メイン画面はデータが無ければ、DownLoadボタンを選択できないようにするためのフラグ
  var enableFlag = false;
  var downLoadBtn = new McsButton($('#list-btn-downLoad'), screenText.btnText.downLoad);
  
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
	  vehicle: '',
	  unit: '',
	  dateFrom: getFormatDate(),
	  dateTo: getFormatDate(),
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
		  url: getUrl('/VehicleStatisticsHistory/GetVehicleStatisticsHistory'),
		  cond: cond,
		  searchDataFlag: false,
		  tableCompId: 'H-004-VehicleStatisticsHistoryList', // テーブルコンポーネントID
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
  
  
   function getFormatDate() {
	   var date = new Date();
	   var month = date.getMonth() + 1;
	   var strDate = date.getDate();
	   var strHour = date.getHours();
	   var strMinute = date.getMinutes();
	   var strSecond = date.getSeconds();
	   
	   if (month >= 1 && month <= 9) {
	       month = "0" + month;
	   }
	   if (strDate >= 0 && strDate <= 9) {
	       strDate = "0" + strDate;
	   }
	   if (strHour >= 0 && strHour <= 9) {
	 	  strHour = "0" + strHour;
	   }
	   if (strMinute >= 0 && strMinute <= 9) {
	 	  strMinute = "0" + strMinute;
	   }
	   if (strSecond >= 0 && strSecond <= 9) {
	 	  strSecond = "0" + strSecond;
	   }
	   var currentDate = date.getFullYear() + "/" + month + "/" + strDate
	           + " " + strHour + ":" + strMinute + ":" + strSecond + ".0";

	    return currentDate;
	}

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
    //var downLoadBtn = new McsButton($('#list-btn-downLoad'), screenText.btnText.downLoad);
    var rtnBtn = new McsButton($('#list-btn-ret'), screenText.btnText.cancel);

    if(enableFlag){
    	//選択できる
    	downLoadBtn.setEnabled(true);
    }
    else{
    	//初回選択できない
    	downLoadBtn.setEnabled(false);
    }
    
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
    	 clearSearch();
      // 画面の内容を消去
//      searchComp.clearErrors();
    	 
      /* 
      var startTime = new Date(new Date().getTime()); // 当前时间
      var endTime = new Date(new Date().getTime() + 24 * 60 * 60 * 1000); // 当前时间的 前后 几小时
      
      //var startTime1 = new Date();
      //var endTime1 = startTime1.setDate(startTime1.getDate()+1);
      //endTime1 = new Date(endTime1);
          
	  var y = startTime.getFullYear();    //获取完整的年份(4位,1970-????)
	  var m = startTime.getMonth();       //获取当前月份(0-11,0代表1月)
	  var d = startTime.getDate();        //获取当前日(1-31)
	  m = m + 1;
      if (m.toString().length == 1) {
          m = "0" + m;
      }
      if (d.toString().length == 1) {
          d = "0" + d;
      }
      
      var y1 = endTime.getFullYear();    //获取完整的年份(4位,1970-????)
	  var m1 = endTime.getMonth();       //获取当前月份(0-11,0代表1月)
	  var d1 = endTime.getDate();        //获取当前日(1-31)
	  m1 = m1 + 1;
      if (m1.toString().length == 1) {
          m1 = "0" + m1;
      }
      if (d1.toString().length == 1) {
          d1 = "0" + d1;
      }
      //var date = y+'/'+m+'/'+d+'/'+' 00:00:00';
      var date = y+'/'+m+'/'+d;
      //var date1 = y+'/'+m+'/'+d1+'/'+' 23:59:59';
      var date1 = y1+'/'+m1+'/'+d1;
      searchComp.get('dateFrom').setValue(date);
      //searchComp.get('dateTo').setValue(date1);
      searchComp.get('dateTo').setValue(date);
      */
      //var date = getFormatDate();
      var date = new Date();
      searchComp.get('dateFrom').setValue(date);
      searchComp.get('dateTo').setValue(date);
      
      
      
      // 前回の条件を復元
      var datas = dataTables.getLatestCond();
      searchComp.get('tscId').setValue(datas.tscId);
      searchComp.get('vehicleId').setValue(datas.vehicleId);

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
    var vehicleId = new McsSelectBox($('#mcs-search-vehicleId'));
    var unit = new McsSelectBox($('#mcs-search-unit'));
    var dateFrom = new McsDateTime($('#mcs-search-dFrom'), screenText.slideSearch.dateFrom, 75,false);
    var dateTo = new McsDateTime($('#mcs-search-dTo'), screenText.slideSearch.dateTo, 75,false);
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clear'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    
    // DateTimePickerの秒指定を無効にする
    //var crntFormat = 'YYYY/MM/DD 00:00:00';
    var crntFormat = 'YYYY/MM/DD';
    dateFrom.setFormat(crntFormat);
    dateTo.setFormat(crntFormat);
    
    
    
    var tscIdList = screenValue.tscId;
    var vehicleIdList= screenValue.vehicleId;
    var unitList= screenValue.unit;
    
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('tscId', tscId);
    searchComp.add('vehicleId', vehicleId);
    searchComp.add('unit', unit);
    searchComp.add('dateFrom', dateFrom);
    searchComp.add('dateTo', dateTo);
    
    tscId.setList(tscIdList);
    vehicleId.setList(vehicleIdList);
    unit.setList(unitList);
   /* 
   // tscId変更時にsource、destinationの要素を取得し、一覧を再検索
    tscId.onChange(function() {
      setSourceList(tscId.getValue());
      setDestinationList(tscId.getValue());
    });
   */   
    // 抽出ボタン押下
    extract.onClick(function() {
      
      // エラー解除
      searchComp.clearErrors();
      
      
      // 検索処理
      var url = getUrl('/VehicleStatisticsHistory/GetVehicleStatisticsHistory');
      var cond = {
    		  tscId: tscId.getValue(),
    		  tscName: tscId.getText(),
    		  vehicleId: vehicleId.getValue(),
    		  unit: unit.getValue(),
    		  dateFrom: dateFrom.getValue() + " 00:00:00",
    		  dateTo: dateTo.getValue() + " 00:00:00"
      };
      
      var space = "&nbsp&nbsp"; 
      var searchInformation = "";
      var searchInfoTscIds = "";
      var searchInfovehicleId = "";
      var searchInfoUnits = "";
      var searchInfoDateFroms = "";
      var searchInfoDateTos = "";
      var searchInfoTscId = tscId.getText();
      var searchInfoVehicleId = vehicleId.getText();
      var searchInfoUnit = unit.getText();
      var searchInfoDateFrom = dateFrom.getValue();
      var searchInfoDateTo = dateTo.getValue();
      
      if(searchInfoTscId == "All"){
    	  searchInfoTscId = "";
      }
      if(searchInfoVehicleId == "All"){
    	  searchInfoVehicleId = "";
      }
    
      if(searchInfoTscId!=null && searchInfoTscId !="")
      {
    	  searchInfoTscIds = " TSCID ["+searchInfoTscId+"]" + space ;
      }
      if(searchInfoVehicleId!=null && searchInfoVehicleId !="")
      {
    	  searchInfoVehicleId = " VehicleId " + "[" + searchInfoVehicleId + "]" + space ;
      }
      if(searchInfoUnit!=null && searchInfoUnit !="")
      {
    	  searchInfoUnits = " Unit ["+searchInfoUnit+"]" + space ;
      }
      if(searchInfoDateFrom!=null && searchInfoDateFrom !="")
      {
    	  searchInfoDateFroms = " Date&Time " + "[" + searchInfoDateFrom + " 00:00:00  - " ;
      }
      if(searchInfoDateTo!=null && searchInfoDateTo !="")
      {
    	  searchInfoDateTos = searchInfoDateTo + " 23:59:59 ]" + space ;
      }
      
      $('#searchInfo').html(  searchInfoTscIds+
    		  				  searchInfoVehicleId + 
				    		  searchInfoUnits +
				    		  searchInfoDateFroms + 
				    		  searchInfoDateTos );
      
      var tableCompId = 'H-004-VehicleStatisticsHistoryList';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        //success: function() {
        success: function(data) {

    	   //検索したデータがあれば、DownLoadボタンを選択できるのを設定する
    	   // 検索成功時
      	   if(data.body.length!=0){
          	 enableFlag = true;
           }
           if(enableFlag){
          	 //選択できる
          	 downLoadBtn.setEnabled(true);
           }	
           else{
          	 downLoadBtn.setEnabled(false);
           }
               
          if (retFlag) {
        	// 戻るボタン押下時
            // 戻るボタン用フラグを下す
            retFlag = false;
            return;
          }
          //firstSearchFlag = false;
          enableFlag = false;//先回データを削除する。
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
    	vehicleId.clear();
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
      callAjax(getUrl('/VehicleStatisticsHistory/SetCsvVehicleStatisticsHistoryList'), datas, false,
      // 成功                  
      function(retObj) {
        window.location.href = getUrl('/VehicleStatisticsHistory/SaveCsvVehicleStatisticsHistoryList');
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
   * *****************************************************************************
   * @brief   検索スライド初期化
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * *****************************************************************************
   */
  function clearSearch() {
    // 検索条件を初期化
    searchComp.get('tscId').clear();
    searchComp.get('vehicleId').clear();
    searchComp.get('dateFrom').clear();
    searchComp.get('dateTo').clear();

    // 入力値エラーをクリア
    searchComp.clearErrors();
  }

});
