/**
 ******************************************************************************
 * @file        mcs-AMHSModeChange.js
 * @brief       AMHSモード変更画面用JavaScript
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */

// MACS4#0036 Add Start
// 定数
// AMHSタイプ
const EXE_TYPE = {
    ONLINE:  1,
    OFFLINE: 2,
    PASUED:  3,
    AUTO:    4
};
//MACS4#0036 Add End

/**
 ******************************************************************************
 * @brief     AMHSモード変更スライドを生成する
 * @param     {Object} parentSlide 呼び出し元のスライド
 * @return    {McsSlideMenu} AMHSモード変更スライド
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
function initAMHSModeChange(parentSlide) {
  // **********************************************
  // スライド生成
  // **********************************************
  var AMHSModeChangeSlide = new McsSlideMenu({
    depth: 3,
    parent: parentSlide,
    slideDiv: $('#mcs-AMHSModeChange-slide')
  });

  // スライドを開くときのコールバックを設定する
  AMHSModeChangeSlide.onShow(function() {
    clearComps();
  });

  var infoDialog = new McsDialog($('#mcs-amhsMode-info-dialog'), window.mcsDialogTitleInfo);   // MACS4#0036 Add

  // **********************************************
  // スライドの要素生成
  // **********************************************
  // コントローラタイプ
  $('#mcs-AMHSModeChange-controllerType-label').text(screenText.AMHSModeChangeText.controllerType);
  var controllerType = new McsSelectBox($('#mcs-AMHSModeChange-controllerType'));
  // コントローラ
  $('#mcs-AMHSModeChange-controller-label').text(screenText.AMHSModeChangeText.controller);
  var controller = new McsSelectBox($('#mcs-AMHSModeChange-controller'));
  // 制御モード
  $('#mcs-AMHSModeChange-controlMode-label').text(screenText.AMHSModeChangeText.controlMode);
  var online = new McsButton($('#mcs-AMHSModeChange-btn-online'), screenText.AMHSModeChangeText.online);
  var offline = new McsButton($('#mcs-AMHSModeChange-btn-offline'), screenText.AMHSModeChangeText.offline);
  // SEM状態
  $('#mcs-AMHSModeChange-semState-label').text(screenText.AMHSModeChangeText.semState);
  var paused = new McsButton($('#mcs-AMHSModeChange-btn-paused'), screenText.AMHSModeChangeText.paused);
  var auto = new McsButton($('#mcs-AMHSModeChange-btn-auto'), screenText.AMHSModeChangeText.auto);
  // クリアボタン
  var clear = new McsButton($('#mcs-AMHSModeChange-btn-clear'), screenText.AMHSModeChangeText.clear);
  // 戻るボタン
  var ret = new McsButton($('#mcs-AMHSModeChange-btn-ret'), screenText.AMHSModeChangeText.ret);

  // **********************************************
  // コンポーネントマネージャーに登録
  // **********************************************
  var AMHSModeChangeComp = new McsComponentManager();
  AMHSModeChangeComp.add('controllerType', controllerType);
  AMHSModeChangeComp.add('controller', controller);

  // **********************************************
  // 各イベント設定
  // **********************************************
  // コントローラタイプ
  controllerType.setList(screenValue.AMHSModeChangeValue.controllerType);
  controllerType.onChange(function() {
    setControllerList();
  });

  // オンラインボタン
  online.onClick(function() {
    AMHSModeChangeComp.clearErrors();
//  exeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOnline'));                   // MACS4#0036 Del
    exeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOnline'), EXE_TYPE.ONLINE);  // MACS4#0036 Add
  });

  // オフラインボタン
  offline.onClick(function() {
    AMHSModeChangeComp.clearErrors();
//  exeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOffline'));                    // MACS4#0036 Del
    exeExternalProcess(getUrl('/AMHSModeChange/ExeControlModeOffline'), EXE_TYPE.OFFLINE);  // MACS4#0036 Add
  });

  // ポーズボタン
  paused.onClick(function() {
    AMHSModeChangeComp.clearErrors();
//  exeExternalProcess(getUrl('/AMHSModeChange/ExeSemStatePaused'));                  // MACS4#0036 Del
    exeExternalProcess(getUrl('/AMHSModeChange/ExeSemStatePaused'), EXE_TYPE.PASUED); // MACS4#0036 Add
  });

  // オートボタン
  auto.onClick(function() {
    AMHSModeChangeComp.clearErrors();
//  exeExternalProcess(getUrl('/AMHSModeChange/ExeSemStateAuto'));                // MACS4#0036 Del
    exeExternalProcess(getUrl('/AMHSModeChange/ExeSemStateAuto'), EXE_TYPE.AUTO); // MACS4#0036 Add
  });

  // クリアボタン
  clear.onClick(function() {
    clearComps();
  });

  // 戻るボタン
  ret.onClick(function() {
    AMHSModeChangeSlide.hide();
  });

  // **********************************************
  // コントローラセレクトボックスの要素を取得
  // **********************************************
  setControllerList();

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
  function setControllerList() {
    var cond = {
      controllerType: controllerType.getValue()
    };
    callAjax(getUrl('/AMHSModeChange/GetController'), cond, false, function(resObj) {
      controller.setList(resObj.body);
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
//function exeExternalProcess(url) {          // MACS4#0036 Del
  function exeExternalProcess(url, type) {    // MACS4#0036 Add
    var cond = {
      controllerType: controllerType.getValue(),
      controller: controller.getValue()
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
      AMHSModeChangeComp.setErrors(resObj.result.errorInfoList);
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
  function clearComps() {
    controllerType.clear();
    AMHSModeChangeComp.clearErrors();
    setControllerList();
  }

  return AMHSModeChangeSlide;
}
