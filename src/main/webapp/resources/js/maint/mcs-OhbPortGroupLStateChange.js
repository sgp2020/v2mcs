/**
 ******************************************************************************
 * @file        mcs-OhbPortGroupLStateChange.js
 * @brief       OHBポートグループ論理状態変更用JavaScript
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
 * @brief   OHBポートグループ論理状態変更スライドを生成
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
function initOhbPortGroupLStateChange(parentSlide) {
    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var ohbPortGroupLStateComp = new McsComponentManager();
    
    // **********************************************
    // スライドの生成
    // **********************************************
    // スライドの要素を生成
    
    // コントローラ
    var ohbIdLabel = $('#mcs-ohbPortGroupLStateChange-ohbIdLabel');
    var ohbId = new McsSelectBox($('#mcs-ohbPortGroupLStateChange-ohbId'));
    
    // 論理状態(変更前)
    var logicalStateBeforeLabel = $('#mcs-ohbPortGroupLStateChange-logicalStateBeforeLabel');
    var logicalStateBefore = new McsTextBox($('#mcs-ohbPortGroupLStateChange-logicalStateBefore'));
    
    // 論理状態(変更前)
    var logicalStateAfterLabel = $('#mcs-ohbPortGroupLStateChange-logicalStateAfterLabel');
    var logicalStateAfter = new McsSelectBox($('#mcs-ohbPortGroupLStateChange-logicalStateAfter'));
    
    // 実行ボタン
    var confirm = new McsButton($('#mcs-ohbPortGroupLStateChange-btn-confirm'), screenText.ohbPortGroupLStateChangeText.confirm);
    
    // 戻るボタン
    var ret = new McsButton($('#mcs-ohbPortGroupLStateChange-btn-ret'), screenText.ohbPortGroupLStateChangeText.ret);
    
    // スライド
    var ohbPortGroupLStateChangeSlideDiv = $('#mcs-ohbPortGroupLStateChange');
    
    // ダイアログ
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    
    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    ohbPortGroupLStateComp.add('ohbId', ohbId);
    ohbPortGroupLStateComp.add('logicalStateBefore', logicalStateBefore);
    ohbPortGroupLStateComp.add('logicalStateAfter', logicalStateAfter);
    
    // **********************************************
    // 各種設定
    // **********************************************
    // OHBID
    ohbIdLabel.text(screenText.ohbPortGroupLStateChangeText.ohbId);
    
    ohbId.onChange(function() {
        searchLogicalState();
    });
    
    // 論理状態(変更前)
    logicalStateBeforeLabel.text(screenText.ohbPortGroupLStateChangeText.logicalStateBefore);
    logicalStateBefore.setEnabled(false);
    
    // 論理状態(変更後)
    logicalStateAfterLabel.text(screenText.ohbPortGroupLStateChangeText.logicalStateAfter);
    logicalStateAfter.setList(screenValue.ohbPortGroupLStateChangeValue.logicalState);
    
    // 実行ボタン
    confirm.onClick(function() {
        // 確認ダイアログ表示
        confirmDialog.openConfirm(screenText.ohbPortGroupLStateChangeText.dialogText.confirmMsg, screenText.ohbPortGroupLStateChangeText.dialogText.confirm,
                screenText.ohbPortGroupLStateChangeText.dialogText.ret, 'confirm', function(result) {
            if (result) {
                // エラー情報初期化
                ohbPortGroupLStateComp.clearErrors();
                var url = getUrl('/OhbPortGroupLStateChange/ExeOhbPortGroupLStateChange');
                var cond = {
                    ohbId: ohbId.getValue(),
                    logicalStateAfter: logicalStateAfter.getValue()
                };
                var flag = false;
                var success = function(retObj) {
                    // 何もしない
                };
                var error = function(retObj) {
                    ohbPortGroupLStateComp.setErrors(retObj.result.errorInfoList);
                };
                
                callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
            }
        });
    });
    
    // 戻るボタン
    ret.onClick(function() {
        ohbPortGroupLStateChangeSlide.hide();
    });
    
    // スライドの生成
    var ohbPortGroupLStateChangeSlide = new McsSlideMenu({
        depth: 3,
        parent: parentSlide,
        slideDiv: ohbPortGroupLStateChangeSlideDiv
    });
    
    // スライド表示時のコールバック
    ohbPortGroupLStateChangeSlide.onShow(function() {
        // 各項目をクリアする
        ohbPortGroupLStateComp.clearErrors();
        searchOhbPortGroup();
    });
    
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
    function searchOhbPortGroup() {
      var url = getUrl('/OhbPortGroupLStateChange/GetOhbPortGroupList');
      var cond = {};
      var flag = false;
      var success = function(retObj) {
        ohbId.setList(retObj.body);
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
        var url = getUrl('/OhbPortGroupLStateChange/GetLogicalState');
        var cond = {
                ohbId: ohbId.getValue()
        };
        var flag = false;
        var success = function(retObj) {
            logicalStateBefore.setValue(retObj.ohbLState);
        };
        callAjax(url, JSON.stringify(cond), flag, success);
    }
    
    return ohbPortGroupLStateChangeSlide;
}