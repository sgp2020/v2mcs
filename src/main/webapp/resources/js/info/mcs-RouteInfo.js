/**
 ******************************************************************************
 * @file        mcs-RouteInfo.js
 * @brief       アラーム情報表示画面用JavaScript
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                           SGP
 ******************************************************************************
 */
$(function() { 
	
  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.Disable);
  
  var ctrl1 = new McsTextBox($('#ctrl1'));

  ctrl1.setReadonly(true);
 
  ctrl1.setValue(screenText.ctrlText.Disable);
 
  
		
  // 画面初期化時の処理

  // データテーブル
  
  //var dataTables = new McsDataTables($('#lst-table-target'), false);
  var dataTables = new McsDataTablesBgColor($('#lst-table-target'), false);
  // 行選択時のイベントをセット
  /*
  dataTables.onSelectRow(function() {
    var record = dataTables.getSelectedRowData();
    if (record) {
    	searchOhbPortRltList(record[0].ohbId);
    }
  });
  */
 
  //var ohbPortRltTable = new McsTable($('#state-text-target-ohbPortRlt'));
  var destTable = new McsTableBgColor($('#state-text-target-dest'));
  destTable.setNotRowSelect(true);
  
  // 状態テーブルヘッダ(状態テーブル)
  var destHeader = [{
    name: 'DestNo',
    text: screenText.destText.DestNo,
    display: true
  }, {
    name: 'EQPID',
    text: screenText.destText.EQPID,
    display: true
  }, {
    name: 'Connection',
    text: screenText.destText.Connection,
    display: true
  }];
	  
  // ヘッダ設定(状態テーブル)
  destTable.setHeader(destHeader);
  
  $("#routeState").css("background-color","#008000");
	 
  
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
  /*
  function searchRouteList(srcPieceId,dstPieceId,tableNo) {
      var url = getUrl('/RouteInfo/GetRouteList');
      var cond = {
    		  srcPieceId:srcPieceId,
    		  dstPieceId:dstPieceId,
    		  tableNo:tableNo
      };
      var flag = false;
      var success = function(retObj) {
    	  destTable.clear();
    	  //destTable.addDataList(retObj.ohbPortRltList,retObj.rowColor);
      };
      callAjax(url, JSON.stringify(cond), flag, success);
  }
*/
  
  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;

  // 削除マネージャー
  var deleteTaskManager = new McsDeleteTaskManager();   // MACS4#0047 Add

  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);        // MACS4#0047 Add
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);  // MACS4#0047 Add

  // 初回検索
 // extract({});
   extract({
	      srcPieceId:'4512',
		  dstPieceId:'3041',
		  tableNo:'2'
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
      url: getUrl('/RouteInfo/GetRouteInfoList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'I-008-dataTables', // テーブルコンポーネントID
      success: function(data) {
    	//var d = data.body[0].ohbId;
    	//var ohbId=d.replace("<br>","");
    	//searchOhbPortRltList(ohbId);
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
  
 


  
//DEL STD APL 2020.02.20 song 天津村研  MCSV4　GUI開発  Ver3.0 Rev.000 

  // 各ボタンの生成
  // 一覧
  
  /*
  var reLoad = new McsButton($('#menu-btn-update'), screenText.list.reLoad);
  var del = new McsButton($('#menu-btn-del'), screenText.list.del);   // MACS4#0047 Add
  var save = new McsButton($('#menu-btn-save'), screenText.list.save);
  var ret = new McsButton($('#menu-btn-cancel'), screenText.list.ret);
  */
  /**
   * 各スライドを生成
   */
  // Top
  //var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  
//DEL END APL 2020.02.20 song 天津村研  MCSV4　GUI開発  Ver3.0 Rev.000

  // CSV保存用スライドの初期化
  var saveMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-saveMenu')
  });

  // CSV保存用スライドの生成
  // saveCsvSlide();

  // 操作部のスライド生成
  //createList();

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
    // MACS4#0047 Add Start
    // 削除ボタン押下
    del.onClick(function() {
      delAlarmList();
    });
    // MACS4#0047 Add End
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
    // datetimePicker生成
    // 開始の項目
    var saveStart = new McsDateTime($('#mcs-saveStartDatetime'), screenText.saveText.saveStart, 75);
    // 終了の項目
    var saveEnd = new McsDateTime($('#mcs-saveEndDatetime'), screenText.saveText.saveEnd, 75);

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
    var confirmButton = new McsButton($('#btn-confirm'), screenText.btnText.confirm);

    // CSV保存の戻るボタン
    var saveReturnButton = new McsButton($('#btn-saveReturn'), screenText.btnText.saveReturn);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 決定ボタン押下
    confirmButton.onClick(function() {
      var datas = dataTables.getLatestCond();
      callAjax(getUrl('/Alarm/SetCsvAlarmList'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/Alarm/SaveCsvAlarmList');
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
   * @brief       アラーム削除機能
   * @param
   * @return
   * @retval
   * @attention
   * @note        選択されたアラームの削除処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分(新規作成)                                    T.Iga/CSC
   * MACS4#0114  GUI MCSAlarmクリア対応                                 T.Iga/CSC
   ******************************************************************************
   */
  function delAlarmList() {
    var datas = dataTables.getSelectedRowData();
    if (datas === null) {
      errorDialog.openAlert(screenText.dialog.noSelectMsg, screenText.dialog.ret, 'alert');
      return;
    }
    // 確認ダイアログ
    // 対象レコード用の確認メッセージ組み立て
    var delCfmMsg = screenText.dialog.delCfmMsg;
    delCfmMsg = delCfmMsg.replace(/%3/, datas[0].alarmId);
    delCfmMsg = delCfmMsg.replace(/%4/, datas[0].amhsId);

    confirmDialog.openConfirm(delCfmMsg, screenText.dialog.confirm, screenText.dialog.ret, 'confirm', function(result) {
      if (result) {
        // 決定ボタン押下時
        var arrayId = [];
        for (var i = 0; i < datas.length; i++) {
          arrayId.push({
            alarmId: datas[i].alarmId,
            alarmCode: datas[i].alarmCode,
            alarmSubCode: datas[i].alarmSubCode,
//          alarmLoc: datas[i].alarmLocation,       // MACS4#0114 Del
//          machineId: datas[i].machineId,          // MACS4#0114 Del
            amhsId: datas[i].amhsId,
            setTime: datas[i].setTime,              // MACS4#0114 Add
            amhsType: datas[i].amhsType             // MACS4#0114 Add
          });
        }

        // 削除実行
        var url = getUrl('/Alarm/ExeDeleteAlarmList');
        var whereMapList = arrayId;
        var options = {
            url: url,
            whereMapList: whereMapList,
            onError: null,
            successMessage: screenText.dialog.delCfmMsg,
            completeMessage: screenText.dialog.delCompMsg,
            onComplete: listReload
        };
        deleteTaskManager.start(options);
      } else {
        // 戻るボタン押下時
        // 動作なし
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
