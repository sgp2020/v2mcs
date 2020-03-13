/**
 ******************************************************************************
 * @file        mcs-OhbPortGroupLStateChangeDialog.js
 * @brief       OHBポートグループ論理状態変更ダイアログ用JavaScript
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
 * @brief   OHBポートグループ論理状態変更ダイアログ
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
function openOhbPortGroupLStateChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var ohbPortGroupLStateChangeDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var ohbPortGroupLStateChangeDialogBtnText1 = ohbPortGroupLStateChangeDialogText.confirm;
  var ohbPortGroupLStateChangeDialogBtnText2 = ohbPortGroupLStateChangeDialogText.ret;
  var ohbPortGroupLStateChangeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.ohbPortGroupLState);
  var ohbPortGroupLStateChangeDialogDiv = $('<div></div>');

  var ohbPortGroupLStateChangeDialogOhbIdSelBox;
  var ohbPortGroupLStateChangeDialogLogicalBeforeTextBox;
  var ohbPortGroupLStateChangeDialogLogicalAfterSelBox;

  ohbPortGroupLStateChangeDialogDiv.append(initOhbPortGroupLStateChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  ohbPortGroupLStateChangeDialog.openCommonPartsDialog(ohbPortGroupLStateChangeDialogDiv, 450, 360, ohbPortGroupLStateChangeDialogBtnText1, ohbPortGroupLStateChangeDialogBtnText2, ohbPortGroupLStateChangeDialogFunc, true);

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
  function ohbPortGroupLStateChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      ohbPortGroupLStateChangeDialogConfirm();
    } else {
      // キャンセルボタン
      ohbPortGroupLStateChangeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   OHBポートグループ論理状態変更ダイアログを生成
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
  function initOhbPortGroupLStateChangeDialog() {
    ohbPortGroupLStateChangeDialogOhbIdSelBox = ohbPortGroupLStateChangeDialogCreateSelBoxDiv(ohbPortGroupLStateChangeDialogText.ohbId, true);
    ohbPortGroupLStateChangeDialogLogicalBeforeTextBox = ohbPortGroupLStateChangeDialogCreateTextDiv(ohbPortGroupLStateChangeDialogText.logicalStateBefore, 'string', false, false);
    ohbPortGroupLStateChangeDialogLogicalAfterSelBox = ohbPortGroupLStateChangeDialogCreateSelBoxDiv(ohbPortGroupLStateChangeDialogText.logicalStateAfter, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    ohbPortGroupLStateChangeDialogComp.add('ohbId', ohbPortGroupLStateChangeDialogOhbIdSelBox);
    ohbPortGroupLStateChangeDialogComp.add('logicalStateBefore', ohbPortGroupLStateChangeDialogLogicalBeforeTextBox);
    ohbPortGroupLStateChangeDialogComp.add('logicalStateAfter', ohbPortGroupLStateChangeDialogLogicalAfterSelBox);
    
    // **********************************************
    // 各種設定
    // **********************************************
    // OHBID
    ohbPortGroupLStateChangeDialogOhbIdSelBox.onChange(function() {
      ohbPortGroupLStateChangeDialogSearchLogicalState();
    });

    // 論理状態(変更後)
    ohbPortGroupLStateChangeDialogLogicalAfterSelBox.setList(screenValue.ohbPortGroupLStateChangeDialogValue.logicalState);

    // OHBポートグループの検索
    ohbPortGroupLStateChangeDialogSearchOhbPortGroup();

    return ohbPortGroupLStateChangeDialogDiv;
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
  function ohbPortGroupLStateChangeDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    // 確認ダイアログ表示
    confirmDialog.openConfirm(screenText.ohbPortGroupLStateChangeDialogText.dialogText.confirmMsg, screenText.ohbPortGroupLStateChangeDialogText.dialogText.confirm,
            screenText.ohbPortGroupLStateChangeDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
          // エラー情報初期化
        ohbPortGroupLStateChangeDialogComp.clearErrors();
        var url = getUrl('/OhbPortGroupLStateChange/ExeOhbPortGroupLStateChange');
        var cond = {
          ohbId: ohbPortGroupLStateChangeDialogOhbIdSelBox.getValue(),
          logicalStateAfter: ohbPortGroupLStateChangeDialogLogicalAfterSelBox.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          // 何もしない
        };
        var error = function(retObj) {
          ohbPortGroupLStateChangeDialogComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
  }

  /**
   ******************************************************************************
   * @brief   OHBポートグループの検索を行う
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
  function ohbPortGroupLStateChangeDialogSearchOhbPortGroup() {
    var url = getUrl('/OhbPortGroupLStateChange/GetOhbPortGroupList');
    var cond = {};
    var flag = false;
    var success = function(retObj) {
      ohbPortGroupLStateChangeDialogOhbIdSelBox.setList(retObj.body);
      ohbPortGroupLStateChangeDialogSearchLogicalState();
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
  function ohbPortGroupLStateChangeDialogSearchLogicalState() {
    var url = getUrl('/OhbPortGroupLStateChange/GetLogicalState');
    var cond = {
      ohbId: ohbPortGroupLStateChangeDialogOhbIdSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      ohbPortGroupLStateChangeDialogLogicalBeforeTextBox.setValue(retObj.ohbLState);
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
  function ohbPortGroupLStateChangeDialogCreateSelBoxDiv(label, require) {
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
    ohbPortGroupLStateChangeDialogDiv.append(elementdiv);

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
  function ohbPortGroupLStateChangeDialogCreateTextDiv(label, type, require, enable) {
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
    ohbPortGroupLStateChangeDialogDiv.append(elementdiv);

    return textBox;
  }
}

