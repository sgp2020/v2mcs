/**
 ******************************************************************************
 * @file        mcs-AmhsLStateChangeDialog.js
 * @brief       AMHS論理状態変更ダイアログ用JavaScript
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
 * @brief   AMHS論理状態変更ダイアログ
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
function openAmhsLStateChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var amhsLStateChangeDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var amhsLStateChangeDialogBtnText1 = amhsLStateChangeDialogText.confirm;
  var amhsLStateChangeDialogBtnText2 = amhsLStateChangeDialogText.ret;
  var amhsLStateChangeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.amhsLState);
  var amhsLStateChangeDialogDiv = $('<div></div>');

  var amhsLStateChangeDialogTypeSelBox;
  var amhsLStateChangeDialogCtrlSelBox;
  var amhsLStateChangeDialogLogicalBeforeTextBox;
  var amhsLStateChangeDialogLogicalAfterSelBox;

  amhsLStateChangeDialogDiv.append(initAmhsLStateChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  amhsLStateChangeDialog.openCommonPartsDialog(amhsLStateChangeDialogDiv, 450, 420, amhsLStateChangeDialogBtnText1, amhsLStateChangeDialogBtnText2, amhsLStateChangeDialogFunc, true);

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
  function amhsLStateChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      amhsLStateChangeDialogConfirm();
    } else {
      // キャンセルボタン
      amhsLStateChangeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   AMHS論理状態変更ダイアログを生成
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
  function initAmhsLStateChangeDialog() {
    amhsLStateChangeDialogTypeSelBox = amhsLStateChangeDialogCreateSelBoxDiv(amhsLStateChangeDialogText.controllerType, true);
    amhsLStateChangeDialogCtrlSelBox = amhsLStateChangeDialogCreateSelBoxDiv(amhsLStateChangeDialogText.controller, true);
    amhsLStateChangeDialogLogicalBeforeTextBox = amhsLStateChangeDialogCreateTextDiv(amhsLStateChangeDialogText.logicalStateBefore, 'string', false, false);
    amhsLStateChangeDialogLogicalAfterSelBox = amhsLStateChangeDialogCreateSelBoxDiv(amhsLStateChangeDialogText.logicalStateAfter, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    amhsLStateChangeDialogComp.add('controller', amhsLStateChangeDialogCtrlSelBox);
    amhsLStateChangeDialogComp.add('logicalStateBefore', amhsLStateChangeDialogLogicalBeforeTextBox);
    amhsLStateChangeDialogComp.add('logicalStateAfter', amhsLStateChangeDialogLogicalAfterSelBox);

    // **********************************************
    // 各種設定
    // **********************************************
    // コントローラ種別
    amhsLStateChangeDialogTypeSelBox.setList(amhsLStateChangeDialogValue.amhsType);

    // イベント設定
    amhsLStateChangeDialogTypeSelBox.onChange(function() {
      amhsLStateChangeDialogSearchController();
    });

    // コントローラ
    amhsLStateChangeDialogCtrlSelBox.onChange(function() {
      amhsLStateChangeDialogSearchLogicalState();
    });

    // 論理状態(変更後)
    amhsLStateChangeDialogLogicalAfterSelBox.setList(amhsLStateChangeDialogValue.logicalState);

    // コントローラの検索
    amhsLStateChangeDialogSearchController();

    return amhsLStateChangeDialogDiv;
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
  function amhsLStateChangeDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    // 確認ダイアログ表示
    confirmDialog.openConfirm(amhsLStateChangeDialogText.dialogText.confirmMsg, amhsLStateChangeDialogText.dialogText.confirm,
            amhsLStateChangeDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報初期化
        amhsLStateChangeDialogComp.clearErrors();
        var url = getUrl('/AmhsLStateChange/ExeAmhsLStateChange');
        var cond = {
          controller: amhsLStateChangeDialogCtrlSelBox.getValue(),
          logicalStateAfter: amhsLStateChangeDialogLogicalAfterSelBox.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          // 何もしない
        };
        var error = function(retObj) {
          amhsLStateChangeDialogComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
  };

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
  function amhsLStateChangeDialogSearchController() {
    var url = getUrl('/AmhsLStateChange/GetControllerList');
    var cond = {
      controllerType: amhsLStateChangeDialogTypeSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      amhsLStateChangeDialogCtrlSelBox.setList(retObj.body);
      amhsLStateChangeDialogSearchLogicalState();
    };
    callAjax(url, JSON.stringify(cond), flag, success);
  }

  /**
   ******************************************************************************
   * @brief   論理状態(変更前)の検索を行う
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
  function amhsLStateChangeDialogSearchLogicalState() {
    var url = getUrl('/AmhsLStateChange/GetLogicalState');
    var cond = {
      controller: amhsLStateChangeDialogCtrlSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      amhsLStateChangeDialogLogicalBeforeTextBox.setValue(retObj.amhsLState);
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
  function amhsLStateChangeDialogCreateSelBoxDiv(label, require) {
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
    amhsLStateChangeDialogDiv.append(elementdiv);

    return selectBox;
  }

  /**
   ******************************************************************************
   * @brief   ダイアログ内テキストボックスのdiv要素生成
   * @param     {String} label テキストボックスのラベル
   * @param     {String} type  テキストボックスのタイプ
   * @param     {boolean} require 必須マークの要(true)／不要(false)
   * @param     {boolean} enable 活性(true)／非活性(false)
   * @return    {McsSelectBox} 生成したテキストボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function amhsLStateChangeDialogCreateTextDiv(label, type, require, enable) {
    var elementdiv = $('<div class="mcs-listTable-element"></div>');

    // テキストボックス生成
    var textBox = new McsTextBox(elementdiv, type);
    textBox.setEnabled(enable);

    // ラベル生成
    var textLabel = $('<label class="mcs-text-label"></label>');

    // 必須マークを付与
    if (require) {
      textLabel.addClass('mcs-required');
    }

    textLabel.append(label);
    elementdiv.prepend(textLabel);

    // 生成した要素をメンバ追加画面のdiv要素に追加
    amhsLStateChangeDialogDiv.append(elementdiv);

    return textBox;
  }
}

