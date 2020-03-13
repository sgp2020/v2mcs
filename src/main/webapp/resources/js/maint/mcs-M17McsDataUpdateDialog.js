/**
 ******************************************************************************
 * @file        mcs-M17McsDataUpdateDialog.js
 * @brief       M17更新要求ダイアログ用JavaScript
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/11/28 MACS4#0059  M17対応(初版作成)                           T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   M17要求用スライドを生成
 * @param   {Object} parentSlide  呼び出し元のスライド
 * @return  {Object}              スライドオブジェクト
 * @retval
 * @attention
 * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
 *           他jsファイルで呼び出されているため無視する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
function openM17McsDataUpdateDialog() {
  // コンポーネントマネージャーの生成
  var m17McsDataUpdateDialogComp = new McsComponentManager();

  // ダイアログの要素を生成
  var m17McsDataUpdateDialogConfirmBtnText = screenText.m17McsDataUpdateDialogText.confirm;
  var m17McsDataUpdateDialogReturnBtnText = screenText.m17McsDataUpdateDialogText.ret;
  var m17McsDataUpdateDialog = new McsDialog($('<div></div>'), screenText.systemOpe.m17McsDataUpdate);
  var m17McsDataUpdateDialogDiv = $('<div></div>');

  var m17McsDataUpdateDialogSelectTargetSelBox;
  var m17McsDataUpdateDialogTargetIdTextBox;

  initM17McsDataUpdateDialog();

  // ダイアログオープン
  m17McsDataUpdateDialog.openCommonPartsDialog(m17McsDataUpdateDialogDiv, 450, 300, m17McsDataUpdateDialogConfirmBtnText, m17McsDataUpdateDialogReturnBtnText, m17McsDataUpdateDialogFunc, true);

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
  function m17McsDataUpdateDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      m17McsDataUpdateDialogConfirm();
    } else {
      // キャンセルボタン
      m17McsDataUpdateDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   M17更新要求ダイアログを生成
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
  function initM17McsDataUpdateDialog() {
    m17McsDataUpdateDialogSelectTargetSelBox = m17McsDataUpdateDialogCreateSelBoxDiv(screenText.m17McsDataUpdateDialogText.selectTarget, true);
    m17McsDataUpdateDialogTargetIdTextBox = m17McsDataUpdateDialogCreateTextBoxDiv(screenText.m17McsDataUpdateDialogText.targetId, 'string', true, true);

    m17McsDataUpdateDialogComp.add('selectTarget', m17McsDataUpdateDialogSelectTargetSelBox);
    m17McsDataUpdateDialogComp.add('targetId', m17McsDataUpdateDialogTargetIdTextBox);

    m17McsDataUpdateDialogSelectTargetSelBox.setList(m17McsDataUpdateDialogValue.selectTarget);
    m17McsDataUpdateDialogTargetIdTextBox.setMaxLength(64);

    return m17McsDataUpdateDialogDiv;
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
  function m17McsDataUpdateDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-m17McsDataUpdate-confirm-dialog'), window.mcsDialogTitleConfirm);
    var infoDialog = new McsDialog($('#mcs-m17McsDataUpdate-info-dialog'), window.mcsDialogTitleInfo);

    // 確認ダイアログ表示
    confirmDialog.openConfirm(screenText.m17McsDataUpdateDialogText.dialogText.confirmMsg, screenText.m17McsDataUpdateDialogText.dialogText.confirm,
        screenText.m17McsDataUpdateDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報初期化
        m17McsDataUpdateDialogComp.clearErrors();
        var url = getUrl('/M17McsDataUpdate/ExeM17McsDataUpdate');
        var cond = {
            selectTarget: m17McsDataUpdateDialogSelectTargetSelBox.getValue(),
            targetId: m17McsDataUpdateDialogTargetIdTextBox.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          infoDialog.openAlertProc(screenText.m17McsDataUpdateDialogText.dialogText.resultMsg, screenText.m17McsDataUpdateDialogText.dialogText.ret, 'info', function() {
            // 特になし
          });
        };
        var error = function(retObj) {
          m17McsDataUpdateDialogComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
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
  function m17McsDataUpdateDialogCreateSelBoxDiv(label, require) {
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
    m17McsDataUpdateDialogDiv.append(elementdiv);

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
  function m17McsDataUpdateDialogCreateTextBoxDiv(label, type, require, enable) {
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
    m17McsDataUpdateDialogDiv.append(elementdiv);

    return textBox;
  }
}