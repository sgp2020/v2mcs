﻿/**
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
  $('#color1').css('background-color', screenText.colorText.CommError);

  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
    RELOAD: 0,
    SEARCH: 1,
    COLOR:  2,
    RETURN: 3
  };

  // 現在表示している画面の番号
  var screenIndex;

 /*//20200318 dqy del
   ctrlSelBox.onChange(function() {
    // エラー表示をクリア
    selComp.clearErrors();
//    getData(ctrlSelBox.getValue(), true);
    showListScreen(false);
  });*/

  // コンポーネントマネージャ
  /*var selComp = new McsComponentManager();//20200318 dqy del
  selComp.add('tscId', ctrlSelBox);*/

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  //検索
  var slideMenuSearch = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-search')
  });
  
  creTopMenu();
//初回検索
  extract({
	  hostName: '',
	  commState: ''
  });
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
      url: getUrl('/HostCommInfo/GetHostCommInfoList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'I-009-dataTables', // テーブルコンポーネントID
      success: function(data) {
    	// 成功時
      },
      serverError: function(data) {
        // 特にすることなし
        searchComp.setErrors(data.result.errorInfoList);
      },
      ajaxError: function(status, error) {
        // 特にすることなし
      }
    });
    
  }
  // テーブル
  //20200318 DQY MOD
  var dataTables = new McsDataTablesBgColorRowClick($('#list-table-target'), true);

  // 初期表示処理
  // ---------------------------------------
  showListScreen(false);
  // 一覧画面のデータ取得、表示
  // 自動更新有効化
  AutoReloadTimerManager.addTimeoutCallback(function() {
    	showListScreen(false);
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
    var reloadBtn = new McsButton($('#list-btn-reload'), screenText.list.btnText.reload);
    var rtnBtn = new McsButton($('#list-btn-ret'), screenText.list.btnText.cancel);
    var searchBtn = new McsButton($('#list-btn-search'), screenText.list.btnText.search);
    var colorBtn = new McsButton($('#list-btn-color'), screenText.list.btnText.color);

    // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
        // エラー表示をクリア
        dataTables.reload();
        retFlag = true;
    });

    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
 // 検索ボタン押下
    searchBtn.onClick(function() {
      // 画面の内容を消去
      searchComp.get('currentTscId').clear();
      searchComp.get('carrierId').clear();
      searchComp.clearErrors();

      /*// 前回の条件を復元
      var datas = dataTables.getLatestCond();

      searchComp.get('currentTscId').setValue(datas.currentTscId);
      searchComp.get('carrierId').setValue(datas.carrierId);*/

      slideMenuSearch.show();
    });
    // 色ボタン押下処理
    colorBtn.onClick(function() {
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

    var hostNameBox  = new McsSelectBox($('#mcs-search-hostName'));
    var commStateBox = new McsSelectBox($('#mcs-search-commState'));
    var hostNameList = screenValue.hostName;
    var commStateList= screenValue.commState;
    
    hostNameBox.setList(hostNameList);
    commStateBox.setList(commStateList);
    
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);
    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('hostName', hostNameBox);
    searchComp.add('commState', commStateBox);

    // 抽出ボタン押下
    extract.onClick(function() {
      // エラー解除
      searchComp.clearErrors();
      // 検索処理
      var url = getUrl('/HostCommInfo/GetHostCommInfoList');
      var cond = {
    	hostName: hostNameBox.getValue(),
    	commState: commStateBox.getValue(),
      };
      var tableCompId = 'I-009-dataTables';
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
          firstSearchFlag = false;
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

   // changeSelectBoxList();//DQY DEL 20200313
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
   *******************************************************************************
   * @brief       空FOUP一覧 コントローラIDセレクトボックス リスト更新処理
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   *******************************************************************************
   */
  function changeSelectBoxList() {
    var url = getUrl('/StockerInfo/GetStockerSelectBoxList');
    var cond = {};
    var datas = [];

    var success = function(retObj) {
      // コントローラID セレクトボックス
      var values = {};
      var selControllerId = ctrlSelBox.getValue();
      ctrlSelBox.clear();
//      ctrlSelBox.setList(retObj.controllerIdList);
      ctrlSelBox.setList(retObj.tscIdList);
      ctrlSelBox.setValue(selControllerId);
    }
    callAjax(url, JSON.stringify(cond), false, success);

  }
  
  
});
