/**
 ******************************************************************************
 * @file        mcs-SystemLog.js
 * @brief       SystemLog 画面用JavaScript
 * @par
 * @author      ZHANGDONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/18 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　    ZHANGDONG
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
  var time = date.getFullYear() + "/" + month + "/" + strDate
          + " " + strHour + ":" + strMinute + ":" + strSecond + ".";
  //初回検索
  extract({
	  debug: '',
	  information: '',
	  warning: '',
	  error: '',
	  performance: '',
	  dateFrom: time,
	  dateTo: time,
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
		  url: getUrl('/SystemLog/GetSystemLog'),
		  cond: cond,
		  searchDataFlag: false,
		  tableCompId: 'H-007-dataTables', // テーブルコンポーネントID
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
    
    downLoadBtn.setEnabled(false);
    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
    // 検索ボタン押下
    searchBtn.onClick(function() {
    	var day = new Date();
    	var day1 = day.getFullYear()+"/-" + (day.getMonth()+1) + "/" + day.getDate() + " 00:00:00";
        searchComp.get('dateFrom').setValue(day1);
        searchComp.get('dateTo').setValue(day);
 
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
    var debug  = new McsCheckBox($('#mcs-search-debug'));
    var information = new McsCheckBox($('#mcs-search-information'));
    var warning = new McsCheckBox($('#mcs-search-warning'));
    var error = new McsCheckBox($('#mcs-search-error'));
    var performance = new McsCheckBox($('#mcs-search-performance'));
    var dateFrom = new McsDateTime($('#mcs-search-dFrom'), screenText.slideSearch.dateFrom, 75,true);
    var dateTo = new McsDateTime($('#mcs-search-dTo'), screenText.slideSearch.dateTo, 75,true);
    var maxRecords = new McsTextBox($('#mcs-search-maxRecords'));
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clear'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    
    dateFrom.setReadonly;
    dateTo.setReadonly;
    
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('debug', debug);
    searchComp.add('information', information);
    searchComp.add('warning', warning);
    searchComp.add('error', error);
    searchComp.add('performance', performance);
    searchComp.add('dateFrom', dateFrom);
    searchComp.add('dateTo', dateTo);
    searchComp.add('maxRecords', maxRecords);
    
    maxRecords.setValue("10000");
    maxRecords.setMaxLength(5);
    
    // 抽出ボタン押下
    extract.onClick(function() {
      
    // エラー解除
    searchComp.clearErrors();
      
      
    // 検索処理
    var url = getUrl('/SystemLog/GetSystemLog');
    var cond = {
    		  debug: debug.getValue(),
    		  information: information.getValue(),
    		  warning: warning.getValue(),
    		  error: error.getValue(),
    		  performance: performance.getValue(),
    		  dateFrom: dateFrom.getValue(),
    		  dateTo: dateTo.getValue(),
    		  maxRecords: maxRecords.getValue()
      };
      
      var space = "&nbsp&nbsp"; 
      var searchInformation = "";
      var searchInfoErrorLevels = "";
      var searchInfoDebugs = "";
      var searchInfoInformations = "";
      var searchInfoWarnings = "";
      var searchInfoErrors = "";
      var searchInfoPerformances = "";
      var searchInfoDateFroms = "";
      var searchInfoDateTos = "";
      var searchInfoMaxRecords = "";
	  var searchInfoDebug = "";
      var searchInfoInformation = "";
      var searchInfoWarning = "";
      var searchInfoError = "";
      var searchInfoPerformance = "";
      if ( debug.getValue() == true){
    	  searchInfoDebug = "Debug";
      }
   
      if ( information.getValue() == true){
    	  searchInfoInformation = "Information";
      }
      
      if ( warning.getValue() == true){
    	  searchInfoWarning = "Warning";
      }
      
      if ( error.getValue() == true){
    	  searchInfoError = "Error";
      }

      if ( performance.getValue() == true){
    	  searchInfoPerformance = "Performance";
      }
      var searchInfoDateFrom = dateFrom.getValue();
      var searchInfoDateTo = dateTo.getValue();
      var searchInfoMaxRecord = maxRecords.getValue();
                  
      if(searchInfoDebug!=null && searchInfoDebug !="")
      {
    	  searchInfoErrorLevels = searchInfoErrorLevels + space + searchInfoDebug ;
      }
      if(searchInfoInformation!=null && searchInfoInformation !="")
      {
    	  searchInfoErrorLevels = searchInfoErrorLevels + space + searchInfoInformation ;
      }
      if(searchInfoWarning!=null && searchInfoWarning !="")
      {
    	  searchInfoErrorLevels = searchInfoErrorLevels + space + searchInfoWarning ;
      }
      if(searchInfoError!=null && searchInfoError !="")
      {
    	  searchInfoErrorLevels = searchInfoErrorLevels + space + searchInfoError ;
      }
      if(searchInfoPerformance!=null && searchInfoPerformance !="")
      {
    	  searchInfoErrorLevels = searchInfoErrorLevels + space + searchInfoDebug ;
      }
      if(searchInfoErrorLevels!=null && searchInfoErrorLevels !="")
      {
    	  searchInfoErrorLevels = " Error Level " + "[" + searchInfoErrorLevels + "]";
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
      
      $('#searchInfo').html(  searchInfoErrorLevels+
				    		  searchInfoDateFroms + 
				    		  searchInfoDateTos +
				    		  searchInfoMaxRecords);
      
      var tableCompId = 'H-007-dataTables';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function() {
          // 検索成功時
          if (retFlag) {
        	downLoadBtn.setEnabled(true);
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
    	debug.clear();
    	information.clear();
    	warning.clear();
    	error.clear();
    	performance.clear();
    	dateFrom.clear();
    	dateTo.clear();
    	maxRecords.setValue("10000");
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
      callAjax(getUrl('/SystemLog/SetCsvSystemLog'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/SystemLog/SaveCsvSystemLog');
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
   
});
