/**
 ******************************************************************************
 * @file        mcs-UnitLStateChangeDialog.js
 * @brief       ユニット論理状態変更ダイアログ用JavaScript
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
 * @brief   ユニット論理状態変更ダイアログ
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
function openUnitLStateChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var unitLStateChangeDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var unitLStateChangeDialogBtnText1 = unitLStateChangeDialogText.confirm;
  var unitLStateChangeDialogBtnText2 = unitLStateChangeDialogText.ret;
  var unitLStateChangeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.unitLState);
  var unitLStateChangeDialogDiv = $('<div></div>');

  var unitLStateChangeDialogUnitIdSelBox;
  var unitLStateChangeDialogLogicalBeforeTextBox;
  var unitLStateChangeDialogLogicalAfterSelBox;

  unitLStateChangeDialogDiv.append(initUnitLStateChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  unitLStateChangeDialog.openCommonPartsDialog(unitLStateChangeDialogDiv, 450, 360, unitLStateChangeDialogBtnText1, unitLStateChangeDialogBtnText2, unitLStateChangeDialogFunc, true);

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
  function unitLStateChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      unitLStateChangeDialogConfirm();
    } else {
      // キャンセルボタン
      unitLStateChangeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   ユニット論理状態変更ダイアログを生成
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
  function initUnitLStateChangeDialog() {
    unitLStateChangeDialogUnitIdSelBox = unitLStateChangeDialogCreateSelBoxDiv(unitLStateChangeDialogText.unitId, true);
    unitLStateChangeDialogLogicalBeforeTextBox = unitLStateChangeDialogCreateTextDiv(unitLStateChangeDialogText.logicalStateBefore, 'string', false, false);
    unitLStateChangeDialogLogicalAfterSelBox = unitLStateChangeDialogCreateSelBoxDiv(unitLStateChangeDialogText.logicalStateAfter, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    unitLStateChangeDialogComp.add('unitId', unitLStateChangeDialogUnitIdSelBox);
    unitLStateChangeDialogComp.add('logicalStateBefore', unitLStateChangeDialogLogicalBeforeTextBox);
    unitLStateChangeDialogComp.add('logicalStateAfter', unitLStateChangeDialogLogicalAfterSelBox);

    // **********************************************
    // 各種設定
    // **********************************************
    // ユニットID
    unitLStateChangeDialogUnitIdSelBox.onChange(function() {
      unitLStateChangeDialogSearchLogicalState();
    });

    // 論理状態(変更後)
    unitLStateChangeDialogLogicalAfterSelBox.setList(screenValue.unitLStateChangeDialogValue.logicalState);

    // ユニットの検索
    unitLStateChangeDialogSearchUnit();

    return unitLStateChangeDialogDiv;
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
  function unitLStateChangeDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    // 確認ダイアログ表示
    confirmDialog.openConfirm(screenText.unitLStateChangeDialogText.dialogText.confirmMsg, screenText.unitLStateChangeDialogText.dialogText.confirm,
            screenText.unitLStateChangeDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
          // エラー情報初期化
        unitLStateChangeDialogComp.clearErrors();
        var url = getUrl('/UnitLStateChange/ExeUnitLStateChange');
        var cond = {
          unitId: unitLStateChangeDialogUnitIdSelBox.getValue(),
          logicalStateAfter: unitLStateChangeDialogLogicalAfterSelBox.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          // 何もしない
        };
        var error = function(retObj) {
          unitLStateChangeDialogComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
  }

  /**
   ******************************************************************************
   * @brief   ユニットの検索を行う
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
  function unitLStateChangeDialogSearchUnit() {
    var url = getUrl('/UnitLStateChange/GetUnitList');
    var cond = {};
    var flag = false;
    var success = function(retObj) {
      unitLStateChangeDialogUnitIdSelBox.setList(retObj.body);
      unitLStateChangeDialogSearchLogicalState();
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
  function unitLStateChangeDialogSearchLogicalState() {
    var url = getUrl('/UnitLStateChange/GetLogicalState');
    var cond = {
      unitId: unitLStateChangeDialogUnitIdSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      unitLStateChangeDialogLogicalBeforeTextBox.setValue(retObj.unitLState);
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
  function unitLStateChangeDialogCreateSelBoxDiv(label, require) {
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
    unitLStateChangeDialogDiv.append(elementdiv);

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
  function unitLStateChangeDialogCreateTextDiv(label, type, require, enable) {
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
    unitLStateChangeDialogDiv.append(elementdiv);

    return textBox;
  }
}

