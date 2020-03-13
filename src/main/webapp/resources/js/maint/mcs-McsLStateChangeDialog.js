/**
 ******************************************************************************
 * @file        mcs-McsLStateChangeDialog.js
 * @brief       MCS論理状態変更ダイアログ用JavaScript
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
 * @brief   MCS論理状態変更ダイアログ
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
function openMcsLStateChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var mcsLStateChangeDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var mcsLStateChangeDialogBtnText1 = mcsLStateChangeDialogText.confirm;
  var mcsLStateChangeDialogBtnText2 = mcsLStateChangeDialogText.ret;
  var mcsLStateChangeDialog = new McsDialog($('<div></div>'), screenText.systemOpe.mcsLState);
  var mcsLStateChangeDialogDiv = $('<div></div>');

  var mcsLStateChangeDialogTypeSelBox;
  var mcsLStateChangeDialogCtrlSelBox;
  var mcsLStateChangeDialogLogicalBeforeTextBox;
  var mcsLStateChangeDialogLogicalAfterSelBox;

  mcsLStateChangeDialogDiv.append(initMcsLStateChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  mcsLStateChangeDialog.openCommonPartsDialog(mcsLStateChangeDialogDiv, 450, 280, mcsLStateChangeDialogBtnText1, mcsLStateChangeDialogBtnText2, mcsLStateChangeDialogFunc, true);

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
  function mcsLStateChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      mcsLStateChangeDialogConfirm();
    } else {
      // キャンセルボタン
      mcsLStateChangeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   MCS論理状態変更ダイアログを生成
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
  function initMcsLStateChangeDialog() {
    mcsLStateChangeDialogLogicalBeforeTextBox = mcsLStateChangeDialogCreateTextDiv(mcsLStateChangeDialogText.logicalStateBefore, 'string', false, false);
    mcsLStateChangeDialogLogicalAfterSelBox = mcsLStateChangeDialogCreateSelBoxDiv(mcsLStateChangeDialogText.logicalStateAfter, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    mcsLStateChangeDialogComp.add('logicalStateBefore', mcsLStateChangeDialogLogicalBeforeTextBox);
    mcsLStateChangeDialogComp.add('logicalStateAfter', mcsLStateChangeDialogLogicalAfterSelBox);

    // **********************************************
    // 各種設定
    // **********************************************
    // 論理状態(変更後)
    mcsLStateChangeDialogLogicalAfterSelBox.setList(screenValue.mcsLStateChangeDialogValue.logicalState);

    // 論理状態(変更前)の検索
    mcsLStateChangeDialogSearchLogicalState();

    return mcsLStateChangeDialogDiv;
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
  function mcsLStateChangeDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    // 確認ダイアログ表示
    confirmDialog.openConfirm(screenText.mcsLStateChangeDialogText.dialogText.confirmMsg, screenText.mcsLStateChangeDialogText.dialogText.confirm,
            screenText.mcsLStateChangeDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報初期化
        mcsLStateChangeDialogComp.clearErrors();
        var url = getUrl('/McsLStateChange/ExeMcsLStateChange');
        var cond = {
          logicalStateAfter: mcsLStateChangeDialogLogicalAfterSelBox.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          // 何もしない
        };
        var error = function(retObj) {
          mcsLStateChangeDialogComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
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
  function mcsLStateChangeDialogSearchLogicalState() {
    var url = getUrl('/McsLStateChange/GetLogicalState');
    var cond = {};
    var flag = false;
    var success = function(retObj) {
      mcsLStateChangeDialogLogicalBeforeTextBox.setValue(retObj.mcsLState);
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
  function mcsLStateChangeDialogCreateSelBoxDiv(label, require) {
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
    mcsLStateChangeDialogDiv.append(elementdiv);

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
  function mcsLStateChangeDialogCreateTextDiv(label, type, require, enable) {
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
    mcsLStateChangeDialogDiv.append(elementdiv);

    return textBox;
  }
}

