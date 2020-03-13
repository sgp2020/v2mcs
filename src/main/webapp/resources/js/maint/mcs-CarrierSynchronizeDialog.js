/**
 ******************************************************************************
 * @file        mcs-CarrierSynchronizeDialog.js
 * @brief       キャリア同期画面ダイアログ用JavaScript
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
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   キャリア同期画面ダイアログ
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
function openCarrierSynchronizeDialog() {
  //**********************************************
  //コンポーネントマネージャーの生成
  //**********************************************
  var carrierSynchronizeDialogComp = new McsComponentManager();

  //**********************************************
  //ダイアログ内要素の生成
  //**********************************************
  var carrierSynchronizeDialogBtnText1 = carrierSynchronizeDialogText.apply;
  var carrierSynchronizeDialogBtnText2 = carrierSynchronizeDialogText.clear;
  var carrierSynchronizeDialogBtnText3 = carrierSynchronizeDialogText.ret;
  var carrierSynchronizeDialog = new McsDialog($('<div></div>'), screenText.amhsOpe.carrierSynchro);
  var carrierSynchronizeDialogDiv = $('<div></div>');

  var carrierSynchronizeDialogGroupSelBox;
  var carrierSynchronizeDialogCtrlSelBox;

  carrierSynchronizeDialogDiv.append(initCarrierSynchronizeDialog());

  //**********************************************
  //ダイアログオープン
  //**********************************************
  carrierSynchronizeDialog.openCommonPartsDialogThreeButton(carrierSynchronizeDialogDiv, 450, 280, carrierSynchronizeDialogBtnText1, carrierSynchronizeDialogBtnText2, carrierSynchronizeDialogBtnText3, carrierSynchronizeDialogFunc, true);

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
  function carrierSynchronizeDialogFunc(buttonNum) {
    if (buttonNum == 0) {
      carrierSynchronizeDialogConfirm();
    } else if (buttonNum == 1) {
      carrierSynchronizeDialogClear();
    } else {
      // キャンセルボタン
      carrierSynchronizeDialog.closeDialog();
    }
  }

  /**
   ******************************************************************************
   * @brief   キャリア同期ダイアログを生成
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
  function initCarrierSynchronizeDialog() {
    carrierSynchronizeDialogGroupSelBox = carrierSynchronizeDialogCreateSelBoxDiv(carrierSynchronizeDialogText.controllerGroup, true);
    carrierSynchronizeDialogCtrlSelBox = carrierSynchronizeDialogCreateSelBoxDiv(carrierSynchronizeDialogText.controller, true);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    carrierSynchronizeDialogComp.add('controller', carrierSynchronizeDialogCtrlSelBox);

    // **********************************************
    // 各イベント設定
    // **********************************************
    // コントローラグループ
    carrierSynchronizeDialogGroupSelBox.setList(screenValue.carrierSynchronizeDialogValue.amhsType);
    carrierSynchronizeDialogGroupSelBox.onChange(function() {
      var url = getUrl('/CarrierSynchronize/GetCarrierSynchronize');
      var cond = {
        controllerGroup: carrierSynchronizeDialogGroupSelBox.getValue()
      };
      var flag = false;
      var success = function(retObj) {
        carrierSynchronizeDialogCtrlSelBox.setList(retObj.body);
      };
      var error = function() {

      };
      if (cond.controllerGroup == 'ALL') {
        // 前の選択肢をALLのみにする
        carrierSynchronizeDialogCtrlSelBox.setList([['ALL', 'ALL']]);
        return;
      } else {
        carrierSynchronizeDialogCtrlSelBox.setEnabled(true);
      }
      callAjax(url, JSON.stringify(cond, null, 4), flag, success, error);
    });

    // コントローラ
    carrierSynchronizeDialogSearchController();

    return carrierSynchronizeDialogDiv;
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
  function carrierSynchronizeDialogConfirm() {
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    var infoDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);   // MACS4#0036 Add

    // エラー情報の初期化
    confirmDialog.openConfirm(carrierSynchronizeDialogText.dialogText.confirmMessage, carrierSynchronizeDialogText.dialogText.confirm,
            carrierSynchronizeDialogText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報の初期化
        carrierSynchronizeDialogComp.clearErrors();
        var url = getUrl('/CarrierSynchronize/ExeCarrierSynchronize');
        var cond = {
          controller: carrierSynchronizeDialogCtrlSelBox.getValue()
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
          carrierSynchronizeDialogComp.setErrors(retObj.result.errorInfoList);
        };

        if (carrierSynchronizeDialogCtrlSelBox.getEnabled()) {
          // コントローラが活性ならなにもしない
        } else {
          // コントローラが非活性なら送信内容を「ALL」に変える
          cond.controller = carrierSynchronizeDialogGroupSelBox.getValue();
        }
        callAjax(url, JSON.stringify(cond, null, 4), flag, success, error, null, true);
      }
    });
  }

  /**
   ******************************************************************************
   * @brief   ダイアログのクリアボタン押下時の処理
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
  function carrierSynchronizeDialogClear() {
    carrierSynchronizeDialogGroupSelBox.clear();
    carrierSynchronizeDialogCtrlSelBox.setEnabled(true);
    // エラー情報の初期化
    carrierSynchronizeDialogComp.clearErrors();
    carrierSynchronizeDialogSearchController();
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
  function carrierSynchronizeDialogSearchController() {
    var url = getUrl('/CarrierSynchronize/GetCarrierSynchronize');
    var cond = {
      controllerGroup: carrierSynchronizeDialogGroupSelBox.getValue()
    };
    var flag = false;
    var success = function(retObj) {
      carrierSynchronizeDialogCtrlSelBox.setList(retObj.body);
    };
    callAjax(url, JSON.stringify(cond, null, 4), flag, success);
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
  function carrierSynchronizeDialogCreateSelBoxDiv(label, require) {
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
    carrierSynchronizeDialogDiv.append(elementdiv);

    return selectBox;
  }
}

