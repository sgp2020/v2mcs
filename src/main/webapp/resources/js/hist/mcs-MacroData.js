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
/*$(function() {
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
 
  *//**
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
   *//*
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
});*/
function openMacroDataDialog(commandId) {
	  //**********************************************
	  //**********************************************
	  var macroDataDialogComp = new McsComponentManager();

	  //**********************************************
	  //コンポーネントマネージャーの生成
	  //**********************************************
	  var macroDataDialogDelTaskManager = new McsDeleteTaskManager();
//	  var commandId = $('#commandId').val();

	  // 初回検索
	  var cond = {
	      	commandId: commandId
	      };
	  //**********************************************
	  //ダイアログ内要素の生成
	  //**********************************************
	  var macroDataDialogBtnText1 = macroDataDialogText.confirm;
	  var macroDataDialogBtnText2 = macroDataDialogText.ret;
	  var macroDataDialog = new McsDialog($('<div></div>'), macroDataDialogText.macroDataList);
	  var macroDataDialogDiv = $('<div></div>');

	 // var macroDataDialogTypeSelBox;
	 // var macroDataDialogCtrlSelBox;
	  var macroDataDialogPortListTable;
	 // var macroDataDialogLogicalSelBox;

	  macroDataDialogDiv.append(initmacroDataDialog());

	  //**********************************************
	  //ダイアログオープン
	  //**********************************************
	  //macroDataDialog.openCommonPartsDialog(macroDataDialogDiv, 450, 630, macroDataDialogBtnText1, macroDataDialogBtnText2, macroDataDialogFunc, true);
	  macroDataDialog.openCommonPartsDialog(macroDataDialogDiv, 450, 630, "", macroDataDialogBtnText2, macroDataDialogFunc, true);

	  /**
	   ******************************************************************************
	   * @brief     ダイアログ ボタン処理
	   * @param     {int} buttonNum ボタン番号
	   * @return
	   * @retval
	   * @attention
	   * @note
	   * ----------------------------------------------------------------------------
	   * VER.        DESCRIPTION                                               AUTHOR
	   * ----------------------------------------------------------------------------
	   ******************************************************************************
	   */
	  function macroDataDialogFunc(buttonNum) {
	    if (buttonNum == 0) {
	      macroDataDialogConfirm();
	    } else {
	      // キャンセルボタン
	      macroDataDialog.closeDialog();
	    }
	  }

	  /**
	   ******************************************************************************
	   * @brief   Port論理状態変更ダイアログを生成
	   * @param
	   * @return    {div} ダイアログdiv
	   * @retval
	   * @attention
	   * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
	   *           他jsファイルで呼び出されているため無視する。
	   * ----------------------------------------------------------------------------
	   * VER.        DESCRIPTION                                               AUTHOR
	   * ----------------------------------------------------------------------------
	   ******************************************************************************
	   */
	  function initmacroDataDialog() {
	    //macroDataDialogTypeSelBox = macroDataDialogCreateSelBoxDiv(macroDataDialogText.controllerType, true);
	    //macroDataDialogCtrlSelBox = macroDataDialogCreateSelBoxDiv(macroDataDialogText.controller, true);
	    macroDataDialogPortListTable = macroDataDialogCreateTableDiv(macroDataDialogText.macroDataList, true);
	    //macroDataDialogLogicalSelBox = macroDataDialogCreateSelBoxDiv(macroDataDialogText.logicalStateAfter, false);

	    // **********************************************
	    // コンポーネントマネージャーに登録
	    // **********************************************
	   // macroDataDialogComp.add('controllerType', macroDataDialogTypeSelBox);
	    //macroDataDialogComp.add('controller', macroDataDialogCtrlSelBox);
	    macroDataDialogComp.add('portList', macroDataDialogPortListTable);
	   // macroDataDialogComp.add('logicalStateAfter', macroDataDialogLogicalSelBox);

	    // **********************************************
	    // 各種設定
	    // **********************************************
	    // コントローラ種別
//	    macroDataDialogTypeSelBox.setList(screenValue.macroDataDialogValue.amhsType);
//
//	    // イベント設定
//	    macroDataDialogTypeSelBox.onChange(function() {
//	      macroDataDialogSearchController();
//	    });
//
//	    // コントローラ
//	    macroDataDialogCtrlSelBox.onChange(function() {
//	      macroDataDialogSearchPortList();
//	    });

	    // ポートリスト
	    // ヘッダ設定
	    macroDataDialogPortListTable.setHeader([{
	    	name: 'rum',
		    text: screenText.macroData.rum,
		    display: true
		  }, {
		    name: 'carrierId',
		    text: screenText.macroData.carrierId,
		    display: true
		  }, {
		    name: 'rcvTime',
		    text: screenText.macroData.rcvTime,
		    display: true
		  }, {
		    name: 'startTime',
		    text: screenText.macroData.startTime,
		    display: true
		  }, {
		    name: 'cmpTime',
		    text: screenText.macroData.cmpTime,
		    display: true
		  },{
		    name: 'srcTscId',
		    text: screenText.macroData.srcTscId,
		    display: true
		  }, {
		    name: 'srcLoc',
		    text: screenText.macroData.srcLoc,
		    display: true
		  }, {
		    name: 'dstTscId',
		    text: screenText.macroData.dstTscId,
		    display: true
		  }, {
		    name: 'dstLoc',
		    text: screenText.macroData.dstLoc,
		    display: true
		  },{
		    name: 'dstGroup',
		    text: screenText.macroData.dstGroup,
		    display: true
		  }, {
		    name: 'altTscId',
		    text: screenText.macroData.altTscId,
		    display: true
		  }, {
			  name: 'altLoc',
			  text: screenText.macroData.altLoc,
			  display: true
		  }, {
		    name: 'status',
		    text: screenText.macroData.status,
		    display: true
		  }, {
		    name: 'priority',
		    text: screenText.macroData.priority,
		    display: true
		  },{
		    name: 'cancelReq',
		    text: screenText.macroData.cancelReq,
		    display: true
		  },{
		    name: 'time',
		    text: screenText.macroData.time,
		    display: true
		  },{
		    name: 'hostCommand',
		    text: screenText.macroData.hostCommand,
		    display: true
		  },{
		    name: 'commandId',
		    text: screenText.macroData.commandId,
		    display: true
		  },{
		    name: 'originator',
		    text: screenText.macroData.originator,
		    display: true
		  },{
		    name: 'rerouteReq',
		    text: screenText.macroData.rerouteReq,
		    display: true
	    }]);

	    // 論理状態(変更後)
//	    macroDataDialogLogicalSelBox.setList(screenValue.macroDataDialogValue.logicalState);

	    // コントローラの検索
//	    macroDataDialogSearchController();

	    return macroDataDialogDiv;
	  }

	  /**
	   ******************************************************************************
	   * @brief   ダイアログの実行ボタン押下時の処理
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
	  function macroDataDialogConfirm() {
	    var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
	    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
	    // チェックが入っているデータを取得
	    var portIdList = [];
	    var datas = macroDataDialogPortListTable.getValue();
	    var logicalState = macroDataDialogLogicalSelBox.getValue();
	    for (var i = 0; i < datas.length; i++) {
	      if (datas[i].target === true) {
	        portIdList.push({
	          port: datas[i].port,
	          logicalState: logicalState
	        });
	      }
	    };

	    if (portIdList.length == 0) {
	      // ポートが選択されていない場合、エラー
	      errorDialog.openAlert(macroDataDialogText.dialogText.errorMsg, macroDataDialogText.dialogText.ret, 'error');
	    } else {
	      // 確認ダイアログ表示
	      confirmDialog.openConfirm(macroDataDialogText.dialogText.confirmMsg, macroDataDialogText.dialogText.confirm,
	                macroDataDialogText.dialogText.ret, 'confirm', function(result) {
	        if (result) {
	          // エラー情報初期化
	          macroDataDialogComp.clearErrors();

	          var options = {
	            url: getUrl('/macroData/ExemacroData'),
	            whereMapList: portIdList,
	            onError: null,
	            onComplete: function () {
	              // ポートリスト更新
	              macroDataDialogSearchPortList()
	            }
	          };
	          macroDataDialogDelTaskManager.start(options);
	        }
	      });
	    }
	  }

	  /**
	   ******************************************************************************
	   * @brief   コントローラの検索を行う
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
	  function macroDataDialogSearchController() {
	    var url = getUrl('/AtomicActivityHist/GetMacroData');
	    /*var cond = {
	      controllerType: macroDataDialogTypeSelBox.getValue()
	    };*/
	    var flag = false;
	    var success = function(retObj) {
	      macroDataDialogCtrlSelBox.setList(retObj.body);
	      macroDataDialogSearchPortList();
	    };
	    callAjax(url, JSON.stringify(cond), flag, success);
	  }

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
	  function macroDataDialogSearchPortList() {
	      var url = getUrl('/macroData/GetPortList');
	      var cond = {
	              controller: macroDataDialogCtrlSelBox.getValue()
	      };
	      var flag = false;
	      var success = function(retObj) {
	        macroDataDialogPortListTable.clear();
	        macroDataDialogPortListTable.addDataList(retObj.portList);
	      };
	      callAjax(url, JSON.stringify(cond), flag, success);
	  }

	  /**
	   ******************************************************************************
	   * @brief   ダイアログ内セレクトボックスのdiv要素生成
	   * @param     {String} label セレクトボックスのラベル
	   * @param     {boolean} require 必須マークの要(true)／不要(false)
	   * @return    {McsSelectBox} 生成したセレクトボックス
	   * @retval
	   * @attention
	   * @note
	   * ----------------------------------------------------------------------------
	   * VER.        DESCRIPTION                                               AUTHOR
	   * ----------------------------------------------------------------------------
	   ******************************************************************************
	   */
	  function macroDataDialogCreateSelBoxDiv(label, require) {
	    var elementdiv = $('<div class="mcs-listTable-element"></div>');

	    // セレクトボックス生成
	    var selectBox = new McsSelectBox(elementdiv);

	    // ラベル生成
	    var selectLabel = $('<label class="mcs-select-label"></label>');

	    // 必須マークを付与
	    if (require) {
	      selectLabel.addClass('mcs-required');
	    }

	    selectLabel.append(label);
	    elementdiv.prepend(selectLabel);

	    // 生成した要素をメンバ追加画面のdiv要素に追加
	    macroDataDialogDiv.append(elementdiv);

	    return selectBox;
	  }

	  /**
	   ******************************************************************************
	   * @brief   ダイアログ内テーブルのdiv要素生成
	   * @param     {String} label テーブルのラベル
	   * @param     {boolean} require 必須マークの要(true)／不要(false)
	   * @return    {McsSelectBox} 生成したテーブル
	   * @retval
	   * @attention
	   * @note
	   * ----------------------------------------------------------------------------
	   * VER.        DESCRIPTION                                               AUTHOR
	   * ----------------------------------------------------------------------------
	   ******************************************************************************
	   */
	  function macroDataDialogCreateTableDiv(label, require) {
	    var elementdiv = $('<div class="mcs-dialogTable-element"></div>');

	    // セレクトボックス生成
	    var table = new McsTable(elementdiv);

	    // ラベル生成
	    var tableLabel = $('<label class="mcs-table-label"></label>');

	    // 必須マークを付与
	    if (require) {
	      tableLabel.addClass('mcs-required');
	    }

	    tableLabel.append(label);
	    elementdiv.prepend(tableLabel);

	    // 生成した要素をメンバ追加画面のdiv要素に追加
	    macroDataDialogDiv.append(elementdiv);

	    return table;
	  }

	  /**
	   ******************************************************************************
	   * @brief   ダイアログ内テキストボックスのdiv要素生成
	   * @param     {String} label テキストボックスのラベル
	   * @param     {String} type  テキストボックスのタイプ
	   * @param     {boolean} require 必須マークの要(true)／不要(false)
	   * @return    {McsSelectBox} 生成したテキストボックス
	   * @retval
	   * @attention
	   * @note
	   * ----------------------------------------------------------------------------
	   * VER.        DESCRIPTION                                               AUTHOR
	   * ----------------------------------------------------------------------------
	   ******************************************************************************
	   */
	  function macroDataDialogCreateTextDiv(label, type, require) {
	    var elementdiv = $('<div class="mcs-listTable-element"></div>');

	    // テキストボックス生成
	    var textBox = new McsTextBox(elementdiv, type);

	    // ラベル生成
	    var textLabel = $('<label class="mcs-text-label"></label>');

	    // 必須マークを付与
	    if (require) {
	      textLabel.addClass('mcs-required');
	    }

	    textLabel.append(label);
	    elementdiv.prepend(textLabel);

	    // 生成した要素をメンバ追加画面のdiv要素に追加
	    macroDataDialogDiv.append(elementdiv);

	    return textBox;
	  }
	}

