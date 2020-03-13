/**
 ******************************************************************************
 * @file        mcs-PortLStateChangeDialog.js
 * @brief       Port論理状態変更ダイアログ用JavaScript
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */

/**
 ******************************************************************************
 * @brief   Port論理状態変更ダイアログ
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
function openPortLStateChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var portLStateChangeDialogComp = new McsComponentManager();

  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var portLStateChangeDialogDelTaskManager = new McsDeleteTaskManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var portLStateChangeDialogBtnText1 = portLStateChangeDialogText.confirm;
  var portLStateChangeDialogBtnText2 = portLStateChangeDialogText.ret;
  var portLStateChangeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.portLState);
  var portLStateChangeDialogDiv = $('<div></div>');

  var portLStateChangeDialogTypeSelBox;
  var portLStateChangeDialogCtrlSelBox;
  var portLStateChangeDialogPortListTable;
  var portLStateChangeDialogLogicalSelBox;

  portLStateChangeDialogDiv.append(initPortLStateChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  portLStateChangeDialog.openCommonPartsDialog(portLStateChangeDialogDiv, 450, 630, portLStateChangeDialogBtnText1, portLStateChangeDialogBtnText2, portLStateChangeDialogFunc, true);

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
      portLStateChangeDialogConfirm();
    } else {
      // キャンセルボタン
      portLStateChangeDialog.closeDialog();
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
  function initPortLStateChangeDialog() {
    portLStateChangeDialogTypeSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.controllerType, true);
    portLStateChangeDialogCtrlSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.controller, true);
    portLStateChangeDialogPortListTable = portLStateChangeDialogCreateTableDiv(portLStateChangeDialogText.portList, true);
    portLStateChangeDialogLogicalSelBox = portLStateChangeDialogCreateSelBoxDiv(portLStateChangeDialogText.logicalStateAfter, false);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    portLStateChangeDialogComp.add('controllerType', portLStateChangeDialogTypeSelBox);
    portLStateChangeDialogComp.add('controller', portLStateChangeDialogCtrlSelBox);
    portLStateChangeDialogComp.add('portList', portLStateChangeDialogPortListTable);
    portLStateChangeDialogComp.add('logicalStateAfter', portLStateChangeDialogLogicalSelBox);

    // **********************************************
    // 各種設定
    // **********************************************
    // コントローラ種別
    portLStateChangeDialogTypeSelBox.setList(screenValue.portLStateChangeDialogValue.amhsType);

    // イベント設定
    portLStateChangeDialogTypeSelBox.onChange(function() {
      portLStateChangeDialogSearchController();
    });

    // コントローラ
    portLStateChangeDialogCtrlSelBox.onChange(function() {
      portLStateChangeDialogSearchPortList();
    });

    // ポートリスト
    // ヘッダ設定
    portLStateChangeDialogPortListTable.setHeader([{
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

    // 論理状態(変更後)
    portLStateChangeDialogLogicalSelBox.setList(screenValue.portLStateChangeDialogValue.logicalState);

    // コントローラの検索
    portLStateChangeDialogSearchController();

    return portLStateChangeDialogDiv;
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
  function portLStateChangeDialogSearchPortList() {
      var url = getUrl('/PortLStateChange/GetPortList');
      var cond = {
              controller: portLStateChangeDialogCtrlSelBox.getValue()
      };
      var flag = false;
      var success = function(retObj) {
        portLStateChangeDialogPortListTable.clear();
        portLStateChangeDialogPortListTable.addDataList(retObj.portList);
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
    portLStateChangeDialogDiv.append(elementdiv);

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
  function portLStateChangeDialogCreateTableDiv(label, require) {
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
    portLStateChangeDialogDiv.append(elementdiv);

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
    portLStateChangeDialogDiv.append(elementdiv);

    return textBox;
  }
}

