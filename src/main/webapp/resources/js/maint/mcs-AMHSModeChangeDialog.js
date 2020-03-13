/**
 ******************************************************************************
 * @file        mcs-AMHSModeChangeDialog.js
 * @brief       AMHSモード変更画面ダイアログ用JavaScript
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
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief    AMHSモード変更画面ダイアログ
 * @param
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
 ******************************************************************************
 */
function openAmhsModeChangeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var amhsModeChangeDialogComp = new McsComponentManager();

  var infoDialog = new McsDialog($('#mcs-amhsMode-info-dialog'), window.mcsDialogTitleInfo);   // MACS4#0036 Add

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var amhsModeChangeDialogBtnText1 = amhsModeChangeDialogText.clear;
  var amhsModeChangeDialogBtnText2 = amhsModeChangeDialogText.ret;
  var amhsModeChangeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.amhsMode);
  var amhsModeChangeDialogDiv = $('<div></div>');

  var amhsModeChangeDialogTypeSelBox;
  var amhsModeChangeDialogCtrlSelBox;

  amhsModeChangeDialogDiv.append(initAMHSModeChangeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  amhsModeChangeDialog.openCommonPartsDialog(amhsModeChangeDialogDiv, 450, 500, amhsModeChangeDialogBtnText1, amhsModeChangeDialogBtnText2, amhsModeChangeDialogFunc, true);

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
  function amhsModeChangeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      amhsModeChangeDialogClearComps();
    } else {
      // キャンセルボタン
      amhsModeChangeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief     AMHSモード変更ダイアログを生成する
   * @param
   * @return    {div} ダイアログdiv
   * @retval
   * @attention
   * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
   *           他jsファイルで呼び出されているため無視する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
   ******************************************************************************
   */
  function initAMHSModeChangeDialog() {
    amhsModeChangeDialogTypeSelBox = amhsModeChangeDialogCreateSelBoxDiv(amhsModeChangeDialogText.controllerType, true);
    amhsModeChangeDialogCtrlSelBox = amhsModeChangeDialogCreateSelBoxDiv(amhsModeChangeDialogText.controller, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    amhsModeChangeDialogComp.add('controllerType', amhsModeChangeDialogTypeSelBox);
    amhsModeChangeDialogComp.add('controller', amhsModeChangeDialogCtrlSelBox);

    // **********************************************
    // スライドの要素生成
    // **********************************************
    // 制御モード
    var amhsModeChangeDialogControlModeLabel = $('<span id="amhsModeChangeDialog-controlMode-label" class="mcs-amhsModeChangeDialog-label"></div>');
    var amhsModeChangeDialogBtnOnLine = $('<div id="mcs-amhsModeChangeDialog-btn-online" class="mcs-amhsModeChangeDialog-btn"></div>');
    var amhsModeChangeDialogBtnOffLine = $('<div id="mcs-amhsModeChangeDialog-btn-offline" class="mcs-amhsModeChangeDialog-btn"></div>');
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogControlModeLabel);
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogBtnOnLine);
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogBtnOffLine);
    amhsModeChangeDialogControlModeLabel.text(amhsModeChangeDialogText.controlMode);
    var online = new McsButton(amhsModeChangeDialogBtnOnLine, amhsModeChangeDialogText.online);
    var offline = new McsButton(amhsModeChangeDialogBtnOffLine, amhsModeChangeDialogText.offline);
    // SEM状態
    var amhsModeChangeDialogSemStateLabel = $('<span id="amhsModeChangeDialog-semState-label" class="mcs-amhsModeChangeDialog-label"></div>');
    var amhsModeChangeDialogBtnPaused = $('<div id="mcs-amhsModeChangeDialog-btn-paused" class="mcs-amhsModeChangeDialog-btn"></div>');
    var amhsModeChangeDialogBtnAuto = $('<div id="mcs-amhsModeChangeDialog-btn-auto" class="mcs-amhsModeChangeDialog-btn"></div>');
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogSemStateLabel);
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogBtnPaused);
    amhsModeChangeDialogDiv.append(amhsModeChangeDialogBtnAuto);
    amhsModeChangeDialogSemStateLabel.text(amhsModeChangeDialogText.semState);
    var paused = new McsButton(amhsModeChangeDialogBtnPaused, amhsModeChangeDialogText.paused);
    var auto = new McsButton(amhsModeChangeDialogBtnAuto, amhsModeChangeDialogText.auto);

    // **********************************************
    // 各イベント設定
    // **********************************************
    // コントローラタイプ
    amhsModeChangeDialogTypeSelBox.setList(screenValue.amhsModeChangeDialogValue.controllerType);
    amhsModeChangeDialogTypeSelBox.onChange(function() {
      amhsModeChangeDialogSetControllerList();
    });

    // オンラインボタン
    online.onClick(function() {
      amhsModeChangeDialogComp.clearErrors();
//    amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOnline'));                   // MACS4#0036 Del
      amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOnline'), EXE_TYPE.ONLINE);  // MACS4#0036 Add
    });

    // オフラインボタン
    offline.onClick(function() {
      amhsModeChangeDialogComp.clearErrors();
//    amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOffline'));                    // MACS4#0036 Del
      amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOffline'), EXE_TYPE.OFFLINE);  // MACS4#0036 Add
    });

    // ポーズボタン
    paused.onClick(function() {
      amhsModeChangeDialogComp.clearErrors();
//    amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeSemStatePaused'));                  // MACS4#0036 Del
      amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeSemStatePaused'), EXE_TYPE.PASUED); // MACS4#0036 Add
    });

    // オートボタン
    auto.onClick(function() {
      amhsModeChangeDialogComp.clearErrors();
//    amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeSemStateAuto'));                // MACS4#0036 Del
      amhsModeChangeDialogExeExternalProcess(getUrl('/AMHSModeChange/ExeSemStateAuto'), EXE_TYPE.AUTO); // MACS4#0036 Add
    });

    // **********************************************
    // コントローラセレクトボックスの要素を取得
    // **********************************************
    amhsModeChangeDialogSetControllerList();

    return amhsModeChangeDialogDiv;
  }

  /**
   ******************************************************************************
   * @brief    コントローラセレクトボックスの要素を取得する
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
  function amhsModeChangeDialogSetControllerList() {
    var cond = {
      controllerType: amhsModeChangeDialogTypeSelBox.getValue()
    };
    callAjax(getUrl('/AMHSModeChange/GetController'), cond, false, function(resObj) {
      amhsModeChangeDialogCtrlSelBox.setList(resObj.body);
    });
  }

  /**
   ******************************************************************************
   * @brief    外部プロセス連携処理を行う
   * @param    {String} url 対象コントローラのURL
   * @param    {int} type 実行処理種別
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
   ******************************************************************************
   */
//function amhsModeChangeDialogExeExternalProcess(url) {          // MACS4#0036 Del
  function amhsModeChangeDialogExeExternalProcess(url, type) {    // MACS4#0036 Add
    var cond = {
      controllerType: amhsModeChangeDialogTypeSelBox.getValue(),
      controller: amhsModeChangeDialogCtrlSelBox.getValue()
    };
    callAjax(url, cond, false, function(resObj) {
      // MACS4#0036 Add Start
      switch (type) {
        case EXE_TYPE.ONLINE:
          infoDialog.openAlertProc(screenText.AMHSModeChangeText.dialog.onlineCmpMsg, screenText.AMHSModeChangeText.ret, 'info', function() {
            // 特になし
          });
          break;

        case EXE_TYPE.OFFLINE:
          infoDialog.openAlertProc(screenText.AMHSModeChangeText.dialog.offlineCmpMsg, screenText.AMHSModeChangeText.ret, 'info', function() {
            // 特になし
          });
          break;

        case EXE_TYPE.PASUED:
          infoDialog.openAlertProc(screenText.AMHSModeChangeText.dialog.pasuedCmpMsg, screenText.AMHSModeChangeText.ret, 'info', function() {
            // 特になし
          });
          break;

        case EXE_TYPE.AUTO:
          infoDialog.openAlertProc(screenText.AMHSModeChangeText.dialog.autoCmpMsg, screenText.AMHSModeChangeText.ret, 'info', function() {
            // 特になし
          });
          break;
      }
      // MACS4#0036 Add End
    }, function(resObj) {
      // 入力値エラーがあれば反映
      amhsModeChangeDialogComp.setErrors(resObj.result.errorInfoList);
    }, function(resObj) {
      // Ajax通信エラー時は何もしない
    });
  }

  /**
   ******************************************************************************
   * @brief    要素をクリアする
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
  function amhsModeChangeDialogClearComps() {
    amhsModeChangeDialogTypeSelBox.clear();
    amhsModeChangeDialogComp.clearErrors();
    amhsModeChangeDialogSetControllerList();
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
  function amhsModeChangeDialogCreateSelBoxDiv(label, require) {
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
    amhsModeChangeDialogDiv.append(elementdiv);

    return selectBox;
  }
}

