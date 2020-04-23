/**
 ******************************************************************************
 * @file        mcs-MacroDataDialog.js
 * @brief       MacroDataDialog JavaScript
 * @par
 * @author      Dong
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE           VER.        DESCRIPTION                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/22     v1.0.0      初版作成                        Dong
 ******************************************************************************
 */

/**
 ******************************************************************************
 * @brief   MacroDataDialog
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
function MacroDataDialog(commandId) {
	
//	  var commandId = $('#commandId').val();

	  // 初回検索
	  var cond = {
	      	commandId: commandId
	      };
	  
	  
	  //extract(cond);
	 
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
	  /*
	  function extract(cond) {
	    dataTables.getDataAjax({
	      url: getUrl('/AtomicActivityHist/GetMacroData'),
	      cond: cond,
	      searchDataFlag: true,
	      tableCompId: 'H-002-macroData-dataTables', // テーブルコンポーネントID
	      success: function(data) {
	    	  macroDataDialogListTable.clear();
	          macroDataDialogListTable.addDataList(retObj.portList);
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
	  */
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var macroDataDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var retBtnText = macroDataDialogText.ret;
  var macroDataDialog = new McsDialog($('<div></div>'), macroDataDialogText.macroDataList);
  var macroDataDialogDiv = $('<div></div>');

  var macroDataDialogListTable;
 
  macroDataDialogDiv.append(initMacroDataDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  macroDataDialog.openCommonPartsDialog(macroDataDialogDiv, 900, 630, null, retBtnText, portLStateChangeDialogFunc, true);

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
  function portLStateChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      //portLStateChangeDialogConfirm();
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
  function initMacroDataDialog() {
    //portLStateChangeDialogTypeSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.controllerType, true);
    //portLStateChangeDialogCtrlSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.controller, true);
    macroDataDialogListTable = macroDataDialogCreateTableDiv(macroDataDialogText.macroDataList, true);
    //portLStateChangeDialogLogicalSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.logicalStateAfter, false);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    //portLStateChangeDialogComp.add('controllerType', portLStateChangeDialogTypeSelBox);
    //portLStateChangeDialogComp.add('controller', portLStateChangeDialogCtrlSelBox);
    macroDataDialogComp.add('macroDataList', macroDataDialogListTable);
    //portLStateChangeDialogComp.add('logicalStateAfter', portLStateChangeDialogLogicalSelBox);

    // **********************************************
    // 各種設定
    // **********************************************
    // コントローラ種別
    //portLStateChangeDialogTypeSelBox.setList(screenValue.portLStateChangeDialogValue.amhsType);

    /*
    // イベント設定
    portLStateChangeDialogTypeSelBox.onChange(function() {
      portLStateChangeDialogSearchController();
    });

    // コントローラ
    portLStateChangeDialogCtrlSelBox.onChange(function() {
      portLStateChangeDialogSearchPortList();
    });
    */
    // ポートリスト
    // ヘッダ設定
    /*
    macroDataDialogListTable.setHeader([{
        name: 'target',
        text: screenText.portLStateChangeDialogText.target,
        display: true,
        editable: true,
        align: 'center'
    }, {
        name: 'port',
        text: screenText.portLStateChangeDialogText.port,
        display: true
    }, {
        name: 'logicalState',
        text: screenText.portLStateChangeDialogText.logicalState,
        display: true
    }]);
    */
    macroDataDialogListTable.setHeader([{
    	name: 'rum',
	    text: macroDataDialogText.rum,
	    display: true
	  }, {
	    name: 'orgCarrierId',
	    text: macroDataDialogText.orgCarrierId,
	    display: true
	  }, {
	    name: 'orgRcvTime',
	    text: macroDataDialogText.orgRcvTime,
	    display: true
	  }, {
	    name: 'orgStartTime',
	    text: macroDataDialogText.orgStartTime,
	    display: true
	  }, {
	    name: 'orgCmpTime',
	    text: macroDataDialogText.orgCmpTime,
	    display: true
	  },{
	    name: 'orgSrcTscId',
	    text: macroDataDialogText.orgSrcTscId,
	    display: true
	  }, {
	    name: 'orgSrcLoc',
	    text: macroDataDialogText.orgSrcLoc,
	    display: true
	  }, {
	    name: 'orgDstTscId',
	    text: macroDataDialogText.orgDstTscId,
	    display: true
	  }, {
	    name: 'orgDstLoc',
	    text: macroDataDialogText.orgDstLoc,
	    display: true
	  },{
	    name: 'orgDstGroup',
	    text: macroDataDialogText.orgDstGroup,
	    display: true
	  }, {
	    name: 'altTscId',
	    text: macroDataDialogText.altTscId,
	    display: true
	  }, {
		  name: 'altLoc',
		  text: macroDataDialogText.altLoc,
		  display: true
	  }, {
	    name: 'status',
	    text: macroDataDialogText.status,
	    display: true
	  }, {
	    name: 'orgPriority',
	    text: macroDataDialogText.orgPriority,
	    display: true
	  },{
	    name: 'cancelFlg',
	    text: macroDataDialogText.cancelFlg,
	    display: true
	  },{
	    name: 'time',
	    text: macroDataDialogText.time,
	    display: true
	  },{
	    name: 'orgHostCommandId',
	    text: macroDataDialogText.orgHostCommandId,
	    display: true
	  },{
	    name: 'orgCommandId',
	    text: macroDataDialogText.orgCommandId,
	    display: true
	  },{
	    name: 'orgOriginator',
	    text: macroDataDialogText.orgOriginator,
	    display: true
	  },{
	    name: 'rerouteReq',
	    text: macroDataDialogText.rerouteReq,
	    display: true
    }]);
    
    var url = getUrl('/AtomicActivityHist/GetMacroData');
    var cond = {
    		commandId: commandId
    };
    var flag = false;
    var success = function(retObj) {
      macroDataDialogListTable.clear();
      macroDataDialogListTable.addDataList(retObj.body);
    };
    callAjax(url, JSON.stringify(cond), flag, success);
    // 論理状態(変更後)
    //portLStateChangeDialogLogicalSelBox.setList(screenValue.portLStateChangeDialogValue.logicalState);

    // コントローラの検索
    //portLStateChangeDialogSearchController();

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
  
  /*
  function portLStateChangeDialogConfirm() {
    var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    // チェックが入っているデータを取得
    var portIdList = [];
    var datas = portLStateChangeDialogPortListTable.getValue();
    var logicalState = portLStateChangeDialogLogicalSelBox.getValue();
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
      errorDialog.openAlert(portLStateChangeDialogText.dialogText.errorMsg, portLStateChangeDialogText.dialogText.ret, 'error');
    } else {
      // 確認ダイアログ表示
      confirmDialog.openConfirm(portLStateChangeDialogText.dialogText.confirmMsg, portLStateChangeDialogText.dialogText.confirm,
                portLStateChangeDialogText.dialogText.ret, 'confirm', function(result) {
        if (result) {
          // エラー情報初期化
          portLStateChangeDialogComp.clearErrors();

          var options = {
            url: getUrl('/PortLStateChange/ExePortLStateChange'),
            whereMapList: portIdList,
            onError: null,
            onComplete: function () {
              // ポートリスト更新
              portLStateChangeDialogSearchPortList()
            }
          };
          portLStateChangeDialogDelTaskManager.start(options);
        }
      });
    }
  }
  */
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
  /*
  function portLStateChangeDialogSearchController() {
    var url = getUrl('/PortLStateChange/GetControllerList');
    var cond = {
      controllerType: portLStateChangeDialogTypeSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      portLStateChangeDialogCtrlSelBox.setList(retObj.body);
      portLStateChangeDialogSearchPortList();
    };
    callAjax(url, JSON.stringify(cond), flag, success);
  }
  */
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
  function portLStateChangeDialogSearchPortList() {
      var url = getUrl('/PortLStateChange/GetPortList');
      var cond = {
              controller: portLStateChangeDialogCtrlSelBox.getValue()
      };
      var flag = false;
      var success = function(retObj) {
        macroDataDialogListTable.clear();
        macroDataDialogListTable.addDataList(retObj.portList);
      };
      callAjax(url, JSON.stringify(cond), flag, success);
  }
  */
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
  /*
  function portLStateChangeDialogCreateSelBoxDiv(label, require) {
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
  */
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
  /*
  function portLStateChangeDialogCreateTextDiv(label, type, require) {
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
  */
}

