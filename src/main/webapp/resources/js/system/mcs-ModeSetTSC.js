﻿/**
 ******************************************************************************
 * @file        mcs-ModeSetTSC.js
 * @brief       ModeSetTSC 画面用JavaScript
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
 * 2020/05/08 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　                   天津村研　董
 ******************************************************************************
 */
$(function() {
	
  focus();

  //STD 2020.03.15 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.enable);
  $('#color2').css('background-color', screenText.colorText.disable);
  $('#color3').css('background-color', screenText.colorText.test);
  $('#color4').css('background-color', screenText.colorText.PM);
  $('#color5').css('background-color', screenText.colorText.notReady);
  /*var ctrl1 = new McsTextBox($('#ctrl1'));
  var ctrl2 = new McsTextBox($('#ctrl2'));
  var ctrl3 = new McsTextBox($('#ctrl3'));
  ctrl1.setReadonly(true);
  ctrl2.setReadonly(true);
  ctrl3.setReadonly(true);
  ctrl1.setValue(screenText.ctrlText.Normal);
  ctrl2.setValue(screenText.ctrlText.Low);
  ctrl3.setValue(screenText.ctrlText.High);*/

  //END 2020.03.15 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
  
  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
	  MODESET: 0,
	  MODESETHISTORY: 1,
	  COLOR  : 2,
	  RELOAD : 3,
	  RETURN :4
  };

  // 現在表示している画面の番号
  var screenIndex;

  // tscType選択用セレクトボックス生成
  var ctrlSelBox = new McsSelectBox($('#sel-ctrl'));
  var tscTypeList = screenValue.tscType;
  ctrlSelBox.setList(tscTypeList);

  ctrlSelBox.onChange(function() {
    // エラー表示をクリア
    selComp.clearErrors();
//    getData(ctrlSelBox.getValue(), true);
    showListScreen(false);
  });

  // コンポーネントマネージャ
  var selComp = new McsComponentManager();
  selComp.add('tscId', ctrlSelBox);

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  creTopMenu();

  // テーブル
  //STD 2020.03.17 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
  //var dataTables = new McsDataTables($('#list-table-target'), true);
  //20200318 DQY MOD
  var dataTables = new McsDataTablesBgColor($('#list-table-target'), true);
  //END 2020.03.17 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 

  // 初期表示処理
  // ---------------------------------------
  ctrlSelBox.setList(screenValue.tscType);
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
    var modeSetBtn = new McsButton($('#list-btn-modeSet'), screenText.btnText.modeSet);
    var modeSetHistoryBtn = new McsButton($('#list-btn-modeSetHistory'), screenText.btnText.modeSetHistory);
    var colorBtn = new McsButton($('#list-btn-color'), screenText.btnText.color);
    var reloadBtn = new McsButton($('#list-btn-reload'), screenText.btnText.reload);
    var rtnBtn = new McsButton($('#list-btn-ret'), screenText.btnText.cancel);

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
   * @brief  一覧画面表示処理
   * @param {Boolean}  reloadFlg true:直前のデータで更新 false:現在のデータで更新
   * @return {Boolean} true
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
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
    ctrlSelBox.setEnabled(true);

    //画面モードセット
//    currentDisp = DISP_MODE.EMPTY_CARRIER_LIST;

    if (reloadFlg) {
      dataTables.reload();
    } else {
      // AJax呼び出し
      var values = {};
//      values.controllerId = ctrlSelBox.getValue();
      values.tscType = ctrlSelBox.getValue();//20200313 DQY DEL

      // 表示をクリア
      clearState();
      
      dataTables.getDataAjax({
        url: getUrl('/ModeSetTSC/GetModeSetTSCList'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'S-001-ModeSetTSCList',
        success: function(data) {
          // 成功時
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
  /*function changeSelectBoxList() {
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

  }*/
  
  
});
