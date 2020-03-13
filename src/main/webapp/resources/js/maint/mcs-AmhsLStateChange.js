/**
 ******************************************************************************
 * @file        mcs-AmhsLStateChange.js
 * @brief       AMHS論理状態変更用JavaScript
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
 * @brief   AMHS論理状態変更スライドを生成
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
function initAmhsLStateChange(parentSlide) {
    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var amhsLStateComp = new McsComponentManager();
    
    // **********************************************
    // スライドの生成
    // **********************************************
    // スライドの要素を生成
    
    // コントローラ種別
    var controllerTypeLabel = $('#mcs-amhsLStateChange-controllerTypeLabel');
    var controllerType = new McsSelectBox($('#mcs-amhsLStateChange-controllerType'));
    
    // コントローラ
    var controllerLabel = $('#mcs-amhsLStateChange-controllerLabel');
    var controller = new McsSelectBox($('#mcs-amhsLStateChange-controller'));
    
    // 論理状態(変更前)
    var logicalStateBeforeLabel = $('#mcs-amhsLStateChange-logicalStateBeforeLabel');
    var logicalStateBefore = new McsTextBox($('#mcs-amhsLStateChange-logicalStateBefore'));
    
    // 論理状態(変更前)
    var logicalStateAfterLabel = $('#mcs-amhsLStateChange-logicalStateAfterLabel');
    var logicalStateAfter = new McsSelectBox($('#mcs-amhsLStateChange-logicalStateAfter'));
    
    // 実行ボタン
    var confirm = new McsButton($('#mcs-amhsLStateChange-btn-confirm'), screenText.amhsLStateChangeText.confirm);
    
    // 戻るボタン
    var ret = new McsButton($('#mcs-amhsLStateChange-btn-ret'), screenText.amhsLStateChangeText.ret);
    
    // スライド
    var amhsLStateChangeSlideDiv = $('#mcs-amhsLStateChange');
    
    // ダイアログ
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);
    
    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    amhsLStateComp.add('controller', controller);
    amhsLStateComp.add('logicalStateBefore', logicalStateBefore);
    amhsLStateComp.add('logicalStateAfter', logicalStateAfter);
    
    // **********************************************
    // 各種設定
    // **********************************************
    // コントローラ種別
    controllerTypeLabel.text(screenText.amhsLStateChangeText.controllerType);
    controllerType.setList(screenValue.amhsLStateChangeValue.amhsType);
    
    // イベント設定
    controllerType.onChange(function() {
        searchController();
    });
    
    // コントローラ
    controllerLabel.text(screenText.amhsLStateChangeText.controller);
    
    controller.onChange(function() {
        searchLogicalState();
    });
    
    // 論理状態(変更前)
    logicalStateBeforeLabel.text(screenText.amhsLStateChangeText.logicalStateBefore);
    logicalStateBefore.setEnabled(false);
    
    // 論理状態(変更後)
    logicalStateAfterLabel.text(screenText.amhsLStateChangeText.logicalStateAfter);
    logicalStateAfter.setList(screenValue.amhsLStateChangeValue.logicalState);
    
    // 実行ボタン
    confirm.onClick(function() {
        // 確認ダイアログ表示
        confirmDialog.openConfirm(amhsLStateChangeText.dialogText.confirmMsg, amhsLStateChangeText.dialogText.confirm,
                amhsLStateChangeText.dialogText.ret, 'confirm', function(result) {
            if (result) {
                // エラー情報初期化
                amhsLStateComp.clearErrors();
                var url = getUrl('/AmhsLStateChange/ExeAmhsLStateChange');
                var cond = {
                    controller: controller.getValue(),
                    logicalStateAfter: logicalStateAfter.getValue()
                };
                var flag = false;
                var success = function(retObj) {
                    // 何もしない
                };
                var error = function(retObj) {
                    amhsLStateComp.setErrors(retObj.result.errorInfoList);
                };
                
                callAjax(url, JSON.stringify(cond), flag, success, error, null, true);
            }
        });
    });
    
    // 戻るボタン
    ret.onClick(function() {
        amhsLStateChangeSlide.hide();
    });
    
    // スライドの生成
    var amhsLStateChangeSlide = new McsSlideMenu({
        depth: 3,
        parent: parentSlide,
        slideDiv: amhsLStateChangeSlideDiv
    });
    
    // スライド表示時のコールバック
    amhsLStateChangeSlide.onShow(function() {
        // 各項目をクリアする
        controllerType.clear();
        amhsLStateComp.clearErrors();
        searchController();
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
    function searchController() {
      var url = getUrl('/AmhsLStateChange/GetControllerList');
      var cond = {
        controllerType: controllerType.getValue()
      };
      var flag = false;
      var success = function(retObj) {
        controller.setList(retObj.body);
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
        var url = getUrl('/AmhsLStateChange/GetLogicalState');
        var cond = {
                controller: controller.getValue()
        };
        var flag = false;
        var success = function(retObj) {
            logicalStateBefore.setValue(retObj.amhsLState);
        };
        callAjax(url, JSON.stringify(cond), flag, success);
    }
    
    return amhsLStateChangeSlide;
}