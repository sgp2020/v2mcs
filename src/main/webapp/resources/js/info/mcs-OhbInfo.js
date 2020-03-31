/**
 ******************************************************************************
 * @file        mcs-OhbInfo.js
 * @brief       アラーム情報表示画面用JavaScript
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  2                                           SGP
 ******************************************************************************
 */
$(function() { 
	
  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.Enable);
  $('#color2').css('background-color', screenText.colorText.Disable);
  $('#color3').css('background-color', screenText.colorText.Test);
  $('#color4').css('background-color', screenText.colorText.PM);
  $('#color5').css('background-color', screenText.colorText.Hold);
  $('#color6').css('background-color', screenText.colorText.NotReady);
  var ctrl1 = new McsTextBox($('#ctrl1'));
  var ctrl2 = new McsTextBox($('#ctrl2'));
  var ctrl3 = new McsTextBox($('#ctrl3'));
  var ctrl4 = new McsTextBox($('#ctrl4'));
  var ctrl5 = new McsTextBox($('#ctrl5'));
  var ctrl6 = new McsTextBox($('#ctrl6'));
  ctrl1.setReadonly(true);
  ctrl2.setReadonly(true);
  ctrl3.setReadonly(true);
  ctrl4.setReadonly(true);
  ctrl5.setReadonly(true);
  ctrl6.setReadonly(true);
  ctrl1.setValue(screenText.ctrlText.Enable);
  ctrl2.setValue(screenText.ctrlText.Disable);
  ctrl3.setValue(screenText.ctrlText.Test);
  ctrl4.setValue(screenText.ctrlText.PM);
  ctrl5.setValue(screenText.ctrlText.Hold);
  ctrl6.setValue(screenText.ctrlText.NotReady);
  
		
  // 画面初期化時の処理

  // データテーブル
  //var dataTables = new McsDataTables($('#lst-table-target'), false);
  var dataTables = new McsDataTablesBgColor($('#lst-table-target'), false);
  // 行選択時のイベントをセット
  dataTables.onSelectRow(function() {
    var record = dataTables.getSelectedRowData();
    if (record) {
    	searchOhbPortRltList(record[0].ohbId);
    }
  });
  
 
  //var ohbPortRltTable = new McsTable($('#state-text-target-ohbPortRlt'));
  var ohbPortRltTable = new McsTableBgColor($('#state-text-target-ohbPortRlt'));
  ohbPortRltTable.setNotRowSelect(true);
  
  // 状態テーブルヘッダ(状態テーブル)
  var ohbPortRltHeader = [{
    name: 'portId',
    text: screenText.portText.PortID,
    display: true
  }, {
    name: 'carrierId',
    text: screenText.portText.CarriedID,
    display: true
  }, {
    name: 'storedTime',
    text: screenText.portText.LastStoredTime,
    display: true
  }];
	  
  // ヘッダ設定(状態テーブル)
  ohbPortRltTable.setHeader(ohbPortRltHeader);
	 
  
  /**
   ******************************************************************************
   * @brief   ポートリストの検索を行う
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
  function searchOhbPortRltList(ohbId) {
      var url = getUrl('/OhbInfo/GetOhbPortRltList');
      var cond = {
    		  ohbId:ohbId//'32BCU13HB2'
      };
      var flag = false;
      var success = function(retObj) {
    	  ohbPortRltTable.clear();
    	  ohbPortRltTable.addDataList(retObj.ohbPortRltList,retObj.rowColor);
      };
      callAjax(url, JSON.stringify(cond), flag, success);
  }

  
  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;

  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);        // MACS4#0047 Add
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);  // MACS4#0047 Add

  // 初回検索
  extract({});

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
      url: getUrl('/OhbInfo/GetOhbInfoList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'I-003-dataTables', // テーブルコンポーネントID
      success: function(data) {
    	var d = data.body[0].ohbId;
    	var ohbId=d.replace("<br>","");
    	searchOhbPortRltList(ohbId);
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
    dataTables.reload();
  }
});
