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
	
	  // 初回検索
	  var cond = {
	      	commandId: commandId
	      };
	 
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
//  macroDataDialog.openCommonPartsDialog(macroDataDialogDiv, 900, 630, null, retBtnText, portLStateChangeDialogFunc, true);
  macroDataDialog.openMacroDataDialog(macroDataDialogDiv, 900, 450, retBtnText, macroDataDialogFunc, true);

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
      // キャンセルボタン
    	macroDataDialog.closeDialog();
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
    macroDataDialogListTable = macroDataDialogCreateTableDiv(macroDataDialogText.macroDataList, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    macroDataDialogComp.add('macroDataList', macroDataDialogListTable);

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

    return macroDataDialogDiv;
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
}

