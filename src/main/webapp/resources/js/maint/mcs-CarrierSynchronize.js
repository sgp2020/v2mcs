/**
 ******************************************************************************
 * @file        mcs-CarrierSynchronize.js
 * @brief       キャリア同期画面用JavaScript
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   キャリア同期スライドを生成
 * @param   {Object} parentSlide  呼び出し元のスライド
 * @return  {Object}              スライドオブジェクト
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
function initCarrierSynchronize(parentSlide) {
  // **********************************************
  // コンポーネントマネージャーの生成
  // **********************************************
  var synchroComp = new McsComponentManager();

  // **********************************************
  // スライドの生成
  // **********************************************
  // スライドの要素を生成

  // コントローラグループ
  var controllerGroupLabel = $('#mcs-synchroCarrier-controllerGroupLabel');
  var controllerGroup = new McsSelectBox($('#mcs-synchroCarrier-controllerGroup'));
  // コントローラ
  var controllerLabel = $('#mcs-synchroCarrier-controllerLabel');
  var controller = new McsSelectBox($('#mcs-synchroCarrier-controller'));
  // 適用ボタン
  var confirm = new McsButton($('#mcs-synchroCarrier-btn-confirm'), screenText.carrierSynchronizeText.apply);
  // クリアボタン
  var clear = new McsButton($('#mcs-synchroCarrier-btn-clear'), screenText.carrierSynchronizeText.clear);
  // 戻るボタン
  var ret = new McsButton($('#mcs-synchroCarrier-btn-ret'), screenText.carrierSynchronizeText.ret);
  // スライド
  var carrierSlideDiv = $('#mcs-synchroCarrier');
  // ダイアログ
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
  var infoDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);   // MACS4#0036 Add

  // **********************************************
  // コンポーネントマネージャーに登録
  // **********************************************
  synchroComp.add('controller', controller);

  // **********************************************
  // 各イベント設定
  // **********************************************
  // コントローラグループ
  controllerGroupLabel.text(screenText.carrierSynchronizeText.controllerGroup);
  controllerGroup.setList(screenValue.carrierSynchronizeValue.amhsType);
  controllerGroup.onChange(function() {
    var url = getUrl('/CarrierSynchronize/GetCarrierSynchronize');
    var cond = {
      controllerGroup: controllerGroup.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      controller.setList(retObj.body);
    };
    var error = function() {

    };
    if (cond.controllerGroup == 'ALL') {
      // 前の選択肢をALLのみにする
      controller.setList([['ALL', 'ALL']]);
      return;
    } else {
      controller.setEnabled(true);
    }
    callAjax(url, JSON.stringify(cond, null, 4), flag, success, error);
  });

  // コントローラ
  controllerLabel.text(screenText.carrierSynchronizeText.controller);
  initSearchController();

  // 適用ボタン
  confirm.onClick(function() {
    // エラー情報の初期化
    confirmDialog.openConfirm(carrierSynchronizeText.dialogText.confirmMessage, carrierSynchronizeText.dialogText.confirm,
            carrierSynchronizeText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報の初期化
        synchroComp.clearErrors();
        var url = getUrl('/CarrierSynchronize/ExeCarrierSynchronize');
        var cond = {
          controller: controller.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          // MACS4#0036 Add Start
          infoDialog.openAlertProc(screenText.carrierSynchronizeDialogText.resultMessage.success, screenText.carrierSynchronizeDialogText.dialogText.ret, 'info', function() {
            // 特になし
          });
          // MACS4#0036 Add End
        };
        var error = function(retObj) {
          synchroComp.setErrors(retObj.result.errorInfoList);
        };

        if (controller.getEnabled()) {
          // コントローラが活性ならなにもしない
        } else {
          // コントローラが非活性なら送信内容を「ALL」に変える
          cond.controller = controllerGroup.getValue();
        }
        callAjax(url, JSON.stringify(cond, null, 4), flag, success, error, null, true);
      }
    });
  });

  // クリアボタン
  clear.onClick(function() {
    controllerGroup.clear();
    controller.setEnabled(true);
    // エラー情報の初期化
    synchroComp.clearErrors();
    initSearchController();
  });

  // 戻るボタン
  ret.onClick(function() {
    synchroSlide.hide();
  });

  var synchroSlide = new McsSlideMenu({
    depth: 3,
    parent: parentSlide,
    slideDiv: carrierSlideDiv
  });

  // スライドを開くときのコールバックを設定する
  synchroSlide.onShow(function() {
    // 各項目をクリアする
    controllerGroup.clear();
    controller.setEnabled(true);
    // エラー情報の初期化
    synchroComp.clearErrors();
    initSearchController();
  });

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
  function initSearchController() {
    var url = getUrl('/CarrierSynchronize/GetCarrierSynchronize');
    var cond = {
      controllerGroup: controllerGroup.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      controller.setList(retObj.body);
    };
    callAjax(url, JSON.stringify(cond, null, 4), flag, success);
  }
  return synchroSlide;
}
