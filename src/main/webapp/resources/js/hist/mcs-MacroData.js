/**
 ******************************************************************************
 * @file        mcs-MacroData.js
 * @brief       アラーム情報表示画面用JavaScript
 * @par
 * @author      DONG
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 20200402					MacroData情報									DONG
 ******************************************************************************
 */
$(function() {
  // 画面初期化時の処理

  // データテーブル
  var dataTables = new McsDataTables($('#macroData-table-target'), true);

  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;

  // 削除マネージャー
  var deleteTaskManager = new McsDeleteTaskManager();  

  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
  
  var commandId = $('#commandId').val();

  // 初回検索
  var cond = {
      	commandId: commandId
      };
 extract(cond);
 
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
      url: getUrl('/AtomicActivityHist/GetMacroData'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'H-002-macroData-dataTables', // テーブルコンポーネントID
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
});
