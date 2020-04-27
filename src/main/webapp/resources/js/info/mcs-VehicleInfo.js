/**
 ******************************************************************************
 * @file        mcs-VehicleInfo.js
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
  //var dataTables = new McsDataTables($('#list-table-target'), true);
  var dataTables = new McsDataTablesBgColor($('#list-table-target'), true);
  $('#color1').css('background-color', screenText.colorText.Assigned);
  // コンポーネントマネージャ（検索用）
  var searchComp = new McsComponentManager();

  // ステータス色一覧

  // 画面の番号定義
  const
  SCREEN = {
    DOWNLOAD: 0,
    RELOAD: 1,
    RETURN: 2
  };

   var screenIndex;

  // AMHS選択用セレクトボックス生成
  var ctrlSelBox = new McsSelectBox($('#sel-ctrl'));
  var currentTscIdList = screenValue.currentTscId;
  ctrlSelBox.setList(currentTscIdList);
  ctrlSelBox.setValue(screenValue.currentTscId);

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
	  currentTscId: ''
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
      url: getUrl('/VehicleInfo/GetVehicleInfo'),
      cond: cond,
      searchDataFlag: false,
      tableCompId: 'I-004-dataTables', // テーブルコンポーネントID
      success: function(data) {
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
      },
      ajaxError: function(status, error) {
        // 特にすることなし
      }
    });
  }


  // 各ボタンの生成
  // 一覧
  var reLoad = new McsButton($('#menu-btn-update'), screenText.list.reLoad);
  var save = new McsButton($('#menu-btn-save'), screenText.list.save);
  var ret = new McsButton($('#menu-btn-cancel'), screenText.list.ret);
  
  /**
   * 各スライドを生成
   */
  // Top
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  
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
      callAjax(getUrl('/VehicleInfo/SetVehicleInfo'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/VehicleInfo/SaveCsvVehicleInfo');
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

    //対象項目表示
    $('#mcs-subheader-menu').show();
    $('#list-screen').show();


    ctrlSelBox.setEnabled(true);

    //画面モードセット

    if (reloadFlg) {
      dataTables.reload();
    } else {
      // AJax呼び出し
      var values = {};
      values.currentTscId = ctrlSelBox.getValue();

      // 表示をクリア
      clearState();
      
      dataTables.getDataAjax({
        url: getUrl('/VehicleInfo/GetVehicleInfo'), // データ取得元
        cond: values, // 検索条件
        searchDataFlag: true,
        tableCompId: 'I-004-dataTables',
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
  
  function clearState() {
	    // 状態画面のテーブルをクリア
		dataTables.clear();
  }
  
});
