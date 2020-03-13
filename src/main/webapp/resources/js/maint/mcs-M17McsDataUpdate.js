/**
 ******************************************************************************
 * @file        mcs-M17McsDataUpdate.js
 * @brief       M17要求用JavaScript
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
function initM17McsDataUpdate(parentSlide) {
  // コンポーネントマネージャーの生成
  var m17McsDataUpdateComp = new McsComponentManager();

  // スライドの生成

  // スライドの要素を生成

  // 対象選択
  var selectTargetLabel = $('#mcs-m17McsDataUpdate-selectTargetLabel');
  var selectTarget = new McsSelectBox($('#mcs-m17McsDataUpdate-selectTarget'));

  // 対象ID
  var targetIdLabel = $('#mcs-m17McsDataUpdate-targetIdLabel');
  var targetId = new McsTextBox($('#mcs-m17McsDataUpdate-targetId'));

  // 実行ボタン
  var confirmBtn = new McsButton($('#mcs-m17McsDataUpdate-btn-confirm'), screenText.m17McsDataUpdateText.confirm);

  // 戻るボタン
  var retBtn = new McsButton($('#mcs-m17McsDataUpdate-btn-ret'), screenText.m17McsDataUpdateText.ret);

  // スライド
  var m17McsDataUpdateSlideDiv = $('#mcs-m17McsDataUpdate');

  // ダイアログ
  var confirmDialog = new McsDialog($('#mcs-m17McsDataUpdate-confirm-dialog'), window.mcsDialogTitleConfirm);
  var infoDialog = new McsDialog($('#mcs-m17McsDataUpdate-info-dialog'), window.mcsDialogTitleInfo);

  // コンポーネントマネージャーに登録
  m17McsDataUpdateComp.add('selectTarget', selectTarget);
  m17McsDataUpdateComp.add('targetId', targetId);

  // 各種設定

  // 対象選択
  selectTargetLabel.text(screenText.m17McsDataUpdateText.selectTarget);
  selectTarget.setList(screenValue.m17McsDataUpdateValue.selectTarget);

  // イベント設定

  // 対象ID
  targetIdLabel.text(screenText.m17McsDataUpdateText.targetId);
  targetId.setMaxLength(64);

  // 実行ボタン
  confirmBtn.onClick(function() {
    // 確認ダイアログ表示
    confirmDialog.openConfirm(screenText.m17McsDataUpdateText.dialogText.confirmMsg, screenText.m17McsDataUpdateText.dialogText.confirm,
        screenText.m17McsDataUpdateText.dialogText.ret, 'confirm', function(result) {
      if (result) {
        // エラー情報初期化
        m17McsDataUpdateComp.clearErrors();
        var url = getUrl('/M17McsDataUpdate/ExeM17McsDataUpdate');
        var cond = {
            selectTarget: selectTarget.getValue(),
            targetId: targetId.getValue()
        };
        var flag = false;
        var success = function(retObj) {
          infoDialog.openAlertProc(screenText.m17McsDataUpdateText.dialogText.resultMsg, screenText.m17McsDataUpdateText.dialogText.ret, 'info', function() {
            // 特になし
          });
        };
        var error = function(retObj) {
          m17McsDataUpdateComp.setErrors(retObj.result.errorInfoList);
        };

        callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
      }
    });
  });

  // 戻るボタン
  retBtn.onClick(function() {
    m17McsDataUpdateSlide.hide();
  });

  // スライドの生成
  var m17McsDataUpdateSlide = new McsSlideMenu({
    depth: 3,
    parent: parentSlide,
    slideDiv: m17McsDataUpdateSlideDiv
  });

  // スライド表示時のコールバック
  m17McsDataUpdateSlide.onShow(function() {
    // 各項目をクリアする
    selectTarget.clear();
    targetId.clear();
    m17McsDataUpdateComp.clearErrors();
  });

  return m17McsDataUpdateSlide;
}