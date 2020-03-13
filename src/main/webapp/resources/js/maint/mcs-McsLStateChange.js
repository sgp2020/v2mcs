/**
 ******************************************************************************
 * @file        mcs-McsLStateChange.js
 * @brief       MCS論理状態変更用JavaScript
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
 * @brief   MCS論理状態変更スライドを生成
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
function initMcsLStateChange(parentSlide) {
    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var mcsLStateComp = new McsComponentManager();
    
    // **********************************************
    // スライドの生成
    // **********************************************
    // スライドの要素を生成
    
    // 論理状態(変更前)
    var logicalStateBeforeLabel = $('#mcs-mcsLStateChange-logicalStateBeforeLabel');
    var logicalStateBefore = new McsTextBox($('#mcs-mcsLStateChange-logicalStateBefore'));
    
    // 論理状態(変更前)
    var logicalStateAfterLabel = $('#mcs-mcsLStateChange-logicalStateAfterLabel');
    var logicalStateAfter = new McsSelectBox($('#mcs-mcsLStateChange-logicalStateAfter'));
    
    // 実行ボタン
    var confirm = new McsButton($('#mcs-mcsLStateChange-btn-confirm'), screenText.mcsLStateChangeText.confirm);
    
    // 戻るボタン
    var ret = new McsButton($('#mcs-mcsLStateChange-btn-ret'), screenText.mcsLStateChangeText.ret);
    
    // スライド
    var mcsLStateChangeSlideDiv = $('#mcs-mcsLStateChange');
    
    // ダイアログ
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    
    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    mcsLStateComp.add('logicalStateBefore', logicalStateBefore);
    mcsLStateComp.add('logicalStateAfter', logicalStateAfter);
    
    // **********************************************
    // 各種設定
    // **********************************************
    
    // 論理状態(変更前)
    logicalStateBeforeLabel.text(screenText.mcsLStateChangeText.logicalStateBefore);
    logicalStateBefore.setEnabled(false);
    
    // 論理状態(変更後)
    logicalStateAfterLabel.text(screenText.mcsLStateChangeText.logicalStateAfter);
    logicalStateAfter.setList(screenValue.mcsLStateChangeValue.logicalState);
    
    // 実行ボタン
    confirm.onClick(function() {
        // 確認ダイアログ表示
        confirmDialog.openConfirm(screenText.mcsLStateChangeText.dialogText.confirmMsg, screenText.mcsLStateChangeText.dialogText.confirm,
                screenText.mcsLStateChangeText.dialogText.ret, 'confirm', function(result) {
            if (result) {
                // エラー情報初期化
                mcsLStateComp.clearErrors();
                var url = getUrl('/McsLStateChange/ExeMcsLStateChange');
                var cond = {
                    logicalStateAfter: logicalStateAfter.getValue()
                };
                var flag = false;
                var success = function(retObj) {
                    // 何もしない
                };
                var error = function(retObj) {
                    mcsLStateComp.setErrors(retObj.result.errorInfoList);
                };
                
                callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
            }
        });
    });
    
    // 戻るボタン
    ret.onClick(function() {
        mcsLStateChangeSlide.hide();
    });
    
    // スライドの生成
    var mcsLStateChangeSlide = new McsSlideMenu({
        depth: 3,
        parent: parentSlide,
        slideDiv: mcsLStateChangeSlideDiv
    });
    
    // スライド表示時のコールバック
    mcsLStateChangeSlide.onShow(function() {
        // 各項目をクリアする
        mcsLStateComp.clearErrors();
        searchLogicalState();
    });
    
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
        var url = getUrl('/McsLStateChange/GetLogicalState');
        var cond = {};
        var flag = false;
        var success = function(retObj) {
            logicalStateBefore.setValue(retObj.mcsLState);
        };
        callAjax(url, JSON.stringify(cond), flag, success);
    }
    
    return mcsLStateChangeSlide;
}