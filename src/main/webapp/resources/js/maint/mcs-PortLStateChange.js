/**
 ******************************************************************************
 * @file        mcs-PortLStateChange.js
 * @brief       Port論理状態変更用JavaScript
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
 * @brief   Port論理状態変更スライドを生成
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
function initPortLStateChange(parentSlide) {
    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var portLStateComp = new McsComponentManager();

    // **********************************************
    // コンポーネントマネージャーの生成
    // **********************************************
    var executeTaskManager = new McsDeleteTaskManager();

    // **********************************************
    // スライドの生成
    // **********************************************
    // スライドの要素を生成

    // コントローラ種別
    var controllerTypeLabel = $('#mcs-portLStateChange-controllerTypeLabel');
    var controllerType = new McsSelectBox($('#mcs-portLStateChange-controllerType'));

    // コントローラ
    var controllerLabel = $('#mcs-portLStateChange-controllerLabel');
    var controller = new McsSelectBox($('#mcs-portLStateChange-controller'));

    // ポートリスト
    var portListLabel = $('#mcs-portLStateChange-portListLabel');
    var portList = new McsTable($('#mcs-portLStateChange-portList'));

    // 論理状態(変更前)
    var logicalStateAfterLabel = $('#mcs-portLStateChange-logicalStateAfterLabel');
    var logicalStateAfter = new McsSelectBox($('#mcs-portLStateChange-logicalStateAfter'));

    // 実行ボタン
    var confirm = new McsButton($('#mcs-portLStateChange-btn-confirm'), screenText.portLStateChangeText.confirm);

    // 戻るボタン
    var ret = new McsButton($('#mcs-portLStateChange-btn-ret'), screenText.portLStateChangeText.ret);

    // スライド
    var portLStateChangeSlideDiv = $('#mcs-portLStateChange');

    // ダイアログ
    var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
    var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);

    // **********************************************
    // コンポーネントマネージャーに登録
    // **********************************************
    portLStateComp.add('controller', controller);
    portLStateComp.add('logicalStateAfter', logicalStateAfter);

    // **********************************************
    // 各種設定
    // **********************************************
    // コントローラ種別
    controllerTypeLabel.text(screenText.portLStateChangeText.controllerType);
    controllerType.setList(screenValue.portLStateChangeValue.amhsType);

    // イベント設定
    controllerType.onChange(function() {
        searchController();
    });

    // コントローラ
    controllerLabel.text(screenText.portLStateChangeText.controller);

    controller.onChange(function() {
        searchPortList();
    });

    // ポートリスト
    portListLabel.text(screenText.portLStateChangeText.portList);

    // ヘッダ設定
    portList.setHeader([{
        name: 'target',
        text: screenText.portLStateChangeText.target,
        display: true,
        editable: true,
        align: 'center'
    }, {
        name: 'port',
        text: screenText.portLStateChangeText.port,
        display: true
    }, {
        name: 'logicalState',
        text: screenText.portLStateChangeText.logicalState,
        display: true
    }]);

    // 論理状態(変更後)
    logicalStateAfterLabel.text(screenText.portLStateChangeText.logicalStateAfter);
    logicalStateAfter.setList(screenValue.portLStateChangeValue.logicalState);

    // 実行ボタン
    confirm.onClick(function() {
        // チェックが入っているデータを取得
        var portIdList = [];
        var datas = portList.getValue();
        var logicalState = logicalStateAfter.getValue();
        for (var i = 0; i < datas.length; i++) {
            if (datas[i].target === true) {
                portIdList.push({
                    port: datas[i].port,
                    logicalState: logicalState
                });
            }
        };

        if (portIdList.length == 0) {
            // ポートが選択されていない場合、エラー
            errorDialog.openAlert(portLStateChangeText.dialogText.errorMsg, portLStateChangeText.dialogText.ret, 'error');
        } else {
            // 確認ダイアログ表示
            confirmDialog.openConfirm(portLStateChangeText.dialogText.confirmMsg, portLStateChangeText.dialogText.confirm,
                    portLStateChangeText.dialogText.ret, 'confirm', function(result) {
                if (result) {
                    // エラー情報初期化
                    portLStateComp.clearErrors();

                    var options = {
                            url: getUrl('/PortLStateChange/ExePortLStateChange'),
                            whereMapList: portIdList,
                            onError: null,
                            onComplete: function () {
                              // ポートリスト更新
                              searchPortList()
                            }
                    };
                    executeTaskManager.start(options);
                }
            });
        }
    });

    // 戻るボタン
    ret.onClick(function() {
        portLStateChangeSlide.hide();
    });

    // スライドの生成
    var portLStateChangeSlide = new McsSlideMenu({
        depth: 3,
        parent: parentSlide,
        slideDiv: portLStateChangeSlideDiv
    });

    // スライド表示時のコールバック
    portLStateChangeSlide.onShow(function() {
        // 各項目をクリアする
        controllerType.clear();
        portLStateComp.clearErrors();
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
      var url = getUrl('/PortLStateChange/GetControllerList');
      var cond = {
        controllerType: controllerType.getValue()
      };
      var flag = false;
      var success = function(retObj) {
        controller.setList(retObj.body);
        searchPortList();
      };
      callAjax(url, JSON.stringify(cond), flag, success);
    }

    /**
     ******************************************************************************
     * @brief   ポートリストの検索を行う
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
    function searchPortList() {
        var url = getUrl('/PortLStateChange/GetPortList');
        var cond = {
                controller: controller.getValue()
        };
        var flag = false;
        var success = function(retObj) {
            portList.clear();
            portList.addDataList(retObj.portList);
        };
        callAjax(url, JSON.stringify(cond), flag, success);
    }

    return portLStateChangeSlide;
}