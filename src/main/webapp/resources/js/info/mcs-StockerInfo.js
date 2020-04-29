/**
 ******************************************************************************
 * @file        mcs-StockerInformation.js
 * @brief       StockerInformation 画面用JavaScript
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
 * 2020/03/10 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　                   天津村研　董
 ******************************************************************************
 */
$(function() {
	
  focus();

  //STD 2020.03.15 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.Normal);
  $('#color2').css('background-color', screenText.colorText.Low);
  $('#color3').css('background-color', screenText.colorText.High);
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
    RELOAD: 0,
    RETURN: 1
  };

  // 現在表示している画面の番号
  var screenIndex;

  // AMHS選択用セレクトボックス生成
  var ctrlSelBox = new McsSelectBox($('#sel-ctrl'));
  var tscNameList = screenValue.tscId;
  ctrlSelBox.setList(tscNameList);
//  ctrlSelBox.setValue(screenValue.tscId);

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
  ctrlSelBox.setList(screenValue.tscId);
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

    // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
        // エラー表示をクリア
       // selComp.clearErrors();
        dataTables.reload();
        retFlag = true;
//        showListScreen(retFlag);
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
    ctrlSelBox.setEnabled(true);

    //画面モードセット
//    currentDisp = DISP_MODE.EMPTY_CARRIER_LIST;

    if (reloadFlg) {
      dataTables.reload();
    } else {
      // AJax呼び出し
      var values = {};
//      values.controllerId = ctrlSelBox.getValue();
      values.tscId = ctrlSelBox.getValue();//20200313 DQY DEL

      // 表示をクリア
      clearState();
      
      dataTables.getDataAjax({
        url: getUrl('/StockerInfo/GetStockerInformationList'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'I-005-dataTables',
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
