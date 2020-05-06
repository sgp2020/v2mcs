/**
 ******************************************************************************
 * @file        mcs-TestCarrierList.js
 * @brief       テストキャリア情報表示画面用JavaScript
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  1                                          天津／張東江
 ******************************************************************************
 */
$(function() {
  // 画面初期化時の処理

  // データテーブル
  var dataTables = new McsDataTablesBgColor($('#list-table-target'), true);
  var save = new McsButton($('#menu-btn-save'), screenText.list.save);
  var enableFlag = false;
  // コンポーネントマネージャ（検索用）
  var searchComp = new McsComponentManager();

  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.Future);
  $('#color2').css('background-color', screenText.colorText.Past);

  // 画面の番号定義
  const
  SCREEN = {
    SEARCH: 0,
    DOWNLOAD: 1,
    RELOAD: 2,
    COLOR:  3,
    RETURN: 4
  };

   var screenIndex;

  // AMHS選択用セレクトボックス生成
  var ctrlSelBox = new McsSelectBox($('#sel-ctrl'));
  var carrierStateList = screenValue.carrierState;
  ctrlSelBox.setList(carrierStateList);
  ctrlSelBox.setValue(screenValue.carrierState);

  ctrlSelBox.onChange(function() {
    // エラー表示をクリア
 //   selComp.clearErrors();
    showListScreen(false);
  });



  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;
  // 検索実行前か後かを判定するためのフラグ
  var firstSearchFlag = true;

  // 初回検索
  extract({
	  currentTscId: '',
	  currentTscName: '',
	  carrierId: ''
  });

  /**
   ******************************************************************************
   * @brief       検索処理を実行する機能
   * @param       {Object} cond 抽出条件（そのままサーバへ送信されるJSON）
   * @return
   * @retval
   * @attention
   * @note        検索処理を実行し、返却されたデータをDataTablesに表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function extract(cond) {
    dataTables.getDataAjax({
      url: getUrl('/TestCarrierList/GetTestCarrierList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'I-007-dataTables', // テーブルコンポーネントID
      success: function(data) {
    	 
    	  if(data.body.length != 0){
			  enableFlag = true;
	      }else{
	    	  
			  enableFlag = false;
		  }
	    
	      save.setEnabled(enableFlag);
        // 特にすることなし
        if (retFlag) {
          // 戻るボタンが押されたときは閉じない
          // 戻るボタン用フラグを下す
          retFlag = false;
          return;
        }
        enableFlag = false;
      },
      serverError: function(data) {
        // 特にすることなし
      },
      ajaxError: function(status, error) {
        // 特にすることなし
      }
    });
  }

  
  // 各ボタンの生成
  // 一覧
  var search = new McsButton($('#menu-btn-search'), screenText.list.search);
  var reLoad = new McsButton($('#menu-btn-update'), screenText.list.reLoad);
  var color = new McsButton($('#menu-btn-color'), screenText.list.color);
  var ret = new McsButton($('#menu-btn-cancel'), screenText.list.ret);
  
  /**
   * 各スライドを生成
   */
  // Top
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  
  // 検索
  var slideMenuSearch = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-search')
  });
  // CSV保存用スライドの初期化
  var saveMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-saveMenu')
  });


  /**
   * ダイアログの生成
   */
  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
  var warningDialog = new McsDialog($('#mcs-warning-dialog'), window.mcsDialogTitleError);
  var informationDialog = new McsDialog($('#mcs-information-dialog'), window.mcsDialogTitleInfo);
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);


  // 操作部のスライド生成
  createList();
  // 詳細部のスライド生成
  createSearchSlide();
  // CSV保存用スライドの生成
  saveCsvSlide();


  /**
   ******************************************************************************
   * @brief       一覧画面及びスライドのコンポーネント生成関数
   * @param
   * @return
   * @retval
   * @attention
   * @note        一覧画面及びスライドのコンポーネントを生成する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  function createList() {
    // 各ボタンのイベント登録
     // 検索ボタン押下
    search.onClick(function() {
      // 画面の内容を消去
      searchComp.get('currentTscId').clear();
      searchComp.get('carrierId').clear();
      searchComp.clearErrors();

      // 前回の条件を復元
      var datas = dataTables.getLatestCond();

      searchComp.get('currentTscId').setValue(datas.currentTscId);
      searchComp.get('carrierId').setValue(datas.testCarrierId);


      slideMenuSearch.show();
    });
    // 再表示ボタン押下
    reLoad.onClick(function() {
      dataTables.reload();
    });
    // 保存ボタン押下
    save.onClick(function() {
      saveMenu.show();
    });
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuTop.hide();
    });

        // 色ボタン押下処理
    color.onClick(function() {
    	 var colorFlg = $('#ColorDiv').is(":hidden");
         if(colorFlg){
         	$('#ColorDiv').show();
         }
         else{
         $('#ColorDiv').hide();
         }
    });
  }

  /**
   ******************************************************************************
   * @brief       CSV保存用スライドのコンポーネントを生成する機能
   * @param
   * @return
   * @retval
   * @attention
   * @note        CSV保存用スライドのコンポーネントを生成する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function saveCsvSlide() {
    // ******************************************************
    // 検索項目生成
    // ******************************************************

    // ******************************************************
    // ボタン生成
    // ******************************************************
    // 決定ボタン
    var confirmButton = new McsButton($('#btn-confirm'), screenText.btnText.confirm);

    // CSV保存の戻るボタン
    var saveReturnButton = new McsButton($('#btn-saveReturn'), screenText.btnText.saveReturn);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 決定ボタン押下
    confirmButton.onClick(function() {
      var datas = dataTables.getLatestCond();
      callAjax(getUrl('/TestCarrierList/SetCsvTestCarrierList'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/TestCarrierList/SaveCsvTestCarrierList');
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
    // 検索項目の生成
    var carrierId = new McsTextBox($('#mcs-search-carrierID'));
	var currentTscId = new McsSelectBox($('#mcs-search-currentTscId'));
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clean'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);

  	var currentTscIdList = screenValue.currentTscId;

    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('currentTscId', currentTscId);
    searchComp.add('carrierId', carrierId);
    // 各イベントの設定
    // キャリアのテキスト
    carrierId.setMaxLength(64);
    currentTscId.setList(currentTscIdList);

    // 抽出ボタン押下
    extract.onClick(function() {
      // エラー解除
      searchComp.clearErrors();
      // 検索処理
      var url = getUrl('/TestCarrierList/GetTestCarrierList');
      var cond = {
    	currentTscId: currentTscId.getValue(),
    	currentTscName: currentTscId.getText(),
        carrierId: carrierId.getValue()
      };
      
      var searchInformationH = "";
      var searchInformationC = "";
      var searchInformation = "";
      var space = "&nbsp&nbsp"; 
      var searchCurrentTscId = currentTscId.getText();
      var searchCarrierID = carrierId.getValue();
      
      if(searchCurrentTscId != null && searchCurrentTscId != "")
      {
    	  searchInformationH = " Current TscID ["+searchCurrentTscId+"]	";
      }
      if(searchCarrierID != null && searchCarrierID != "")
      {
    	  searchInformationC = " Test CarrierID " + "[" + searchCarrierID + "]";
      }
      
      $('#searchInfo').html(searchInformationH + space + searchInformationC);//space is not valid.
      
      
      var tableCompId = 'I-007-dataTables';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function() {
          
          if(data.body.length != 0){
  			  enableFlag = true;
  	      }else{
	    	  
			  enableFlag = false;
		  }
  	    
  	      save.setEnabled(enableFlag);
          // 検索成功時
          if (retFlag) {
            // 戻るボタン押下時
            // 戻るボタン用フラグを下す
            retFlag = false;
            return;
          }
          firstSearchFlag = false;
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
    // クリアボタン押下
    clear.onClick(function() {
      // 各項目を初期化する
    	currentTscId.clear();
    	currentTscId.clearError();
		carrierId.clear();
		carrierId.clearError();
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
   * @brief       一覧再表示処理
   * @param
   * @return
   * @retval
   * @attention
   * @note        一覧再表示処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分(新規作成)                                    T.Iga/CSC
   ******************************************************************************
   */
  function listReload() {
     // dataTablesのオートリロード再開
    dataTables.setAutoReloadEnabled(true);
    // スクリーンの表示・非表示
    $('#mcs-subtitle-list').show();
    $('#list-screen').show();

    // スライドのボタンの表示・非表示
    search.show();
    update.show();
    save.show();
    ret.show();

    // dataTablesのリロード
    dataTables.reload();
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

//    reloadButton.show();
//    cancelButton.show();

    ctrlSelBox.setEnabled(true);

    //画面モードセット

    if (reloadFlg) {
      dataTables.reload();
    } else {
      // AJax呼び出し
      var values = {};
      values.status = ctrlSelBox.getValue();

      // 表示をクリア
      clearState();
      
      dataTables.getDataAjax({
        url: getUrl('/TestCarrierList/GetTestCarrierList'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'I-007-dataTables',
        success: function(data) {
          // 成功時
          if(data.body.length != 0){
			  enableFlag = true;
	      }else{
	    	  
			  enableFlag = false;
		  }
          
	    
	      save.setEnabled(enableFlag);
	      enableFlag = false;
        },
        serverError: function(retObj) {
          // エラーメッセージクリア
          //ctrlSelBox.clearErrors();
          // エラーメッセージを付与
          ctrlSelBox.setErrors(retObj.result.errorInfoList);
        },
        ajaxError: function(message, status) {
          // 何もすることなし
        }
        
      });
    }

    return true;
  }
  
  function clearState() {
	    // 状態画面のテーブルをクリア
		dataTables.clear();
  }
  
});
