/**
 ******************************************************************************
 * @file        mcs-UnitLStateChange.js
 * @brief       ユニット論理状態変更用JavaScript
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
 * 2018/01/31 0.8         Step4                                       T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   ユニット論理状態変更スライドを生成
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
function initUnitLStateChange(parentSlide) {
    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var unitLStateComp = new McsComponentManager();
    
    // **********************************************
    // スライドの生成
    // **********************************************
    // スライドの要素を生成
    
    // ユニットID
    var unitIdLabel = $('#mcs-unitLStateChange-unitIdLabel');
    var unitId = new McsSelectBox($('#mcs-unitLStateChange-unitId'));
    
    // 論理状態(変更前)
    var logicalStateBeforeLabel = $('#mcs-unitLStateChange-logicalStateBeforeLabel');
    var logicalStateBefore = new McsTextBox($('#mcs-unitLStateChange-logicalStateBefore'));
    
    // 論理状態(変更前)
    var logicalStateAfterLabel = $('#mcs-unitLStateChange-logicalStateAfterLabel');
    var logicalStateAfter = new McsSelectBox($('#mcs-unitLStateChange-logicalStateAfter'));
    
    // 実行ボタン
    var confirm = new McsButton($('#mcs-unitLStateChange-btn-confirm'), screenText.unitLStateChangeText.confirm);
    
    // 戻るボタン
    var ret = new McsButton($('#mcs-unitLStateChange-btn-ret'), screenText.unitLStateChangeText.ret);
    
    // スライド
    var unitLStateChangeSlideDiv = $('#mcs-unitLStateChange');
    
    // ダイアログ
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    
    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    unitLStateComp.add('unitId', unitId);
    unitLStateComp.add('logicalStateBefore', logicalStateBefore);
    unitLStateComp.add('logicalStateAfter', logicalStateAfter);
    
    // **********************************************
    // 各種設定
    // **********************************************
    // ユニットID
    unitIdLabel.text(screenText.unitLStateChangeText.unitId);
    
    unitId.onChange(function() {
        searchLogicalState();
    });
    
    // 論理状態(変更前)
    logicalStateBeforeLabel.text(screenText.unitLStateChangeText.logicalStateBefore);
    logicalStateBefore.setEnabled(false);
    
    // 論理状態(変更後)
    logicalStateAfterLabel.text(screenText.unitLStateChangeText.logicalStateAfter);
    logicalStateAfter.setList(screenValue.unitLStateChangeValue.logicalState);
    
    // 実行ボタン
    confirm.onClick(function() {
        // 確認ダイアログ表示
        confirmDialog.openConfirm(screenText.unitLStateChangeText.dialogText.confirmMsg, screenText.unitLStateChangeText.dialogText.confirm,
                screenText.unitLStateChangeText.dialogText.ret, 'confirm', function(result) {
            if (result) {
                // エラー情報初期化
                unitLStateComp.clearErrors();
                var url = getUrl('/UnitLStateChange/ExeUnitLStateChange');
                var cond = {
                    unitId: unitId.getValue(),
                    logicalStateAfter: logicalStateAfter.getValue()
                };
                var flag = false;
                var success = function(retObj) {
                    // 何もしない
                };
                var error = function(retObj) {
                    unitLStateComp.setErrors(retObj.result.errorInfoList);
                };
                
                callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
            }
        });
    });
    
    // 戻るボタン
    ret.onClick(function() {
        unitLStateChangeSlide.hide();
    });
    
    // スライドの生成
    var unitLStateChangeSlide = new McsSlideMenu({
        depth: 3,
        parent: parentSlide,
        slideDiv: unitLStateChangeSlideDiv
    });
    
    // スライド表示時のコールバック
    unitLStateChangeSlide.onShow(function() {
        // 各項目をクリアする
        unitLStateComp.clearErrors();
        searchUnit();
    });
    
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
    function searchUnit() {
      var url = getUrl('/UnitLStateChange/GetUnitList');
      var cond = {};
      var flag = false;
      var success = function(retObj) {
        unitId.setList(retObj.body);
        searchLogicalState();
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
    function searchLogicalState() {
        var url = getUrl('/UnitLStateChange/GetLogicalState');
        var cond = {
                unitId: unitId.getValue()
        };
        var flag = false;
        var success = function(retObj) {
            logicalStateBefore.setValue(retObj.unitLState);
        };
        callAjax(url, JSON.stringify(cond), flag, success);
    }
    
    return unitLStateChangeSlide;
}