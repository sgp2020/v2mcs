/**
 ******************************************************************************
 * @file        mcs-DefineLLCCom.js
 * @brief       DefineLLCCom 画面用JavaScript
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/18 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　    天津／張東江
 ******************************************************************************
 */
$(function() {
	
  focus();
  // コンポーネントマネージャ（Add,Modify,Delete用）
  var addComp = new McsComponentManager();
  var modifyComp = new McsComponentManager();
  var deleteComp = new McsComponentManager();
  var detailComp = new McsComponentManager();

  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
	ADD:    0,
	MODIFY:  1,
	DELETE:  2,
    RETURN: 3
  };

  // 現在表示している画面の番号
  var screenIndex;

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  
  //Add
  var slideMenuAdd = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-add')
  });
  
  // 搬送Job作成用 子メニュー
  var slideMenuDetail = new McsSlideMenu({
    depth: 2,
    parent: slideMenuAdd,
    slideDiv: $('#mcs-slideMenu-detail')
  });
  
  //Modify
  var slideMenuModify = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-modify')
  });
  
  //Delete
  var slideMenuDelete = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-delete')
  });  
  
  // テーブル
  //true:複数行を選択できる；false：単数行を選択する
  var dataTables = new McsDataTables($('#list-table-target'), false);
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
  });
  
  //Addボタンを設定する
  createAddSlide();
  //Modifyボタンを設定する
  createModifySlide();
  //Deleteボタンを設定する
  createDeleteSlide();
  
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
	  
	  dataTables.getDataAjax({
		  url: getUrl('/DefineLLCCom/GetDefineLLCCom'),
		  cond: cond,
		  searchDataFlag: true,
		  tableCompId: 'D-003-dataTables', // テーブルコンポーネントID
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
    var addBtn = new McsButton($('#list-btn-add'), screenText.btnText.add);
    var modifyBtn = new McsButton($('#list-btn-modify'), screenText.btnText.modify);
    var deleteBtn = new McsButton($('#list-btn-delete'), screenText.btnText.delete);
    var rtnBtn = new McsButton($('#list-btn-ret'), screenText.btnText.cancel);
    
    // Addボタン押下
    addBtn.onClick(function() {
    	
    	slideMenuAdd.show();
    });

    // Modifyボタン押下
    modifyBtn.onClick(function() {
    	
    	slideMenuModify.show();
    });
    
    // Deleteボタン押下
    deleteBtn.onClick(function() {
    	
    	slideMenuDelete.show();
    });
    
    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
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
  function createAddSlide() {
	  
	addComp.clearErrors();
	// 表示画面番号の更新
	screenIndex = SCREEN.SEARCH;
    // 検索項目の生成
    var sid  = new McsTextBox($('#mcs-add-sid'));
    var hostname  = new McsTextBox($('#mcs-add-hostname'));
    var portno  = new McsTextBox($('#mcs-add-portno'));
    var t3  = new McsTextBox($('#mcs-add-t3'));
    
    sid.setMaxLength(6);
    hostname.setMaxLength(64); 
    portno.setMaxLength(6); 
    t3.setMaxLength(5);
    t3.setValue("45");
 
    
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    var detail = new McsButton($('#mcs-search-detail'), screenText.slideSearch.detail);

    // コンポーネントマネージャーに各Add項目を入れる
    addComp.add('sid', sid);
    addComp.add('hostname', hostname);
    addComp.add('portno', portno);    
    
    detail.onClick(function() {
    	
    	slideMenuDetail.show();
    	
    	detailComp.clearErrors();

    	detailComp.add('t3', t3);   
 	
    	
        var ret1 = new McsButton($('#mcs-search-cancel1'), screenText.slideSearch.ret);
        var extract1 = new McsButton($('#mcs-search-extract1'), screenText.slideSearch.ret);
        // 戻るボタン押下
        ret1.onClick(function() {
        	slideMenuDetail.hide();
        });
        // 戻るボタン押下
        extract1.onClick(function() {
        	slideMenuDetail.hide();
        });
    });
    

    
    // 抽出ボタン押下
    extract.onClick(function() {
      
    // エラー解除
    addComp.clearErrors();
      
      
    // 検索処理
    var url = getUrl('/DefineLLCCom/GetDefineLLCCom');
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
        success: function(data) {
        
          // 検索成功時
    	  if(data.body.length != 0){
    		  enableFlag = true;
          }else{
	    	  
			  enableFlag = false;
		  }
        
          downLoadBtn.setEnabled(enableFlag);
             
          if (retFlag) {

            retFlag = false;
            return;
          }
          //firstSearchFlag = false;
          enableFlag = false;
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
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuAdd.hide();
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
