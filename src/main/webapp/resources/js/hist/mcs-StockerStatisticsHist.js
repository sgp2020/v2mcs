/**
 ******************************************************************************
 * @file        mcs-StockerStatisticsHist.js
 * @brief       StockerStatisticsHistory 画面用JavaScript
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
	  tscId: '',
	  tscName:  '',
	  unit: '',
	  unitName:  '',
	  dateFrom: time,
	  dateTo: time

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
		  url: getUrl('/StockerStatisticsHist/GetStockerStatisticsHist'),
		  cond: cond,
		  searchDataFlag: false,
		  tableCompId: 'H-003-dataTables', // テーブルコンポーネントID
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
    
	
    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
    // 検索ボタン押下
    searchBtn.onClick(function() {
      // 画面の内容を消去
    	var day = new Date();
        searchComp.get('dateFrom').setValue(day);
        searchComp.get('dateTo').setValue(day);
        
        slideMenuSearch.show();
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
    var unit = new McsSelectBox($('#mcs-search-unit'));
    var dateFrom = new McsDateTime($('#mcs-search-dFrom'), screenText.slideSearch.dateFrom, 75,true);
    var dateTo = new McsDateTime($('#mcs-search-dTo'), screenText.slideSearch.dateTo, 75,true);
    
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clear'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    
    // DateTimePickerの秒指定を無効にする
    var crntFormat = 'YYYY/MM/DD';
    dateFrom.setFormat(crntFormat);
    dateTo.setFormat(crntFormat);
    dateFrom.setReadonly;
    dateTo.setReadonly;
    var tscIdList = screenValue.tscId;
    var unitList= screenValue.unit;
    
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('tscId', tscId);
    searchComp.add('unit', unit);
    searchComp.add('dateFrom', dateFrom);
    searchComp.add('dateTo', dateTo);
    

    tscId.setList(tscIdList);
    unit.setList(unitList);
    
    // 抽出ボタン押下
    extract.onClick(function() {
      
     // エラー解除
     searchComp.clearErrors();
      
      
      // 検索処理
      var url = getUrl('/StockerStatisticsHist/GetStockerStatisticsHist');
      var cond = {
    		  tscId: tscId.getValue(),
    		  tscName: tscId.getText(),
    		  unit: unit.getValue(),
    		  unitName: unit.getText(),
    		  dateFrom: dateFrom.getValue() + " 00:00:00",
    		  dateTo: dateTo.getValue() + " 00:00:00"
      };
      
      var space = "&nbsp&nbsp"; 
      var searchInformation = "";
      var searchInfoTscIds = "";
      var searchInfoUnits = "";
      var searchInfoDateFroms = "";
      var searchInfoDateTos = "";

      var searchInfoTscId = tscId.getText();
      var searchInfoUnit = unit.getText();
      var searchInfoDateFrom = dateFrom.getValue();
      var searchInfoDateTo = dateTo.getValue();
      
      if(searchInfoTscId!=null && searchInfoTscId !="")
      {
    	  searchInfoTscIds = " TSCID ["+searchInfoTscId+"]";
      }
      if(searchInfoUnit!=null && searchInfoUnit !="")
      {
    	  searchInfoUnits = " Unit " + "[" + searchInfoUnit + "]";
      }
      
      if(searchInfoDateFrom!=null && searchInfoDateFrom !="")
      {
    	  searchInfoDateFroms = " Date&Time " + "[" + searchInfoDateFrom + " 00:00:00 - ";
      }
      if(searchInfoDateTo!=null && searchInfoDateTo !="")
      {
    	  searchInfoDateTos = searchInfoDateTo + " 23:59:59 ]";
      }
           
      $('#searchInfo').html(  searchInfoTscIds+ space + 
				    		  searchInfoUnits + space +
				    		  searchInfoDateFroms + 
				    		  searchInfoDateTos );
      
      var tableCompId = 'H-003-dataTables';
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
    	unit.clear();
    	dateFrom.clear();
    	dateTo.clear();
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
        url: getUrl('/StockerStatisticsHist/GetStockerStatisticsHistList'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'H-003-dataTables',
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
      callAjax(getUrl('/StockerStatisticsHist/SetCsvStockerStatisticsHist'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/StockerStatisticsHist/SaveCsvStockerStatisticsHist');
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
