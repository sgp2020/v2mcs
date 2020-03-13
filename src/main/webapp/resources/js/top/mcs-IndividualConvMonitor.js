/**
 ******************************************************************************
 * @file        mcs-IndividualConvMonitor.js
 * @brief       個別モニタ(コンベアモニタ)関連のJavaScript
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */
$(function() {
  /* Step4 2017_08_10：ウィンドウ生成時にフォーカスを当てるよう修正 */
  focus();

  // Step4 2017_09_06
  // 非アクティブ状態でも自動更新を行う
  AutoReloadTimerManager.setEnableBlurExecute();

  // 画面の番号定義
  const
  SCREEN = {
    STATE: 0,
    PORT: 1,
    MICRO_CMD: 2
  };

  // 現在表示している画面の番号
  var screenIndex;

  // 直近の検索成功時の検索条件(amhsId)
  var latestAmhsId;

  // AMHS選択用セレクトボックス生成
  var amhsSelBox = new McsSelectBox($('#sel-amhs'));
  var amhsNameList = screenValue.amhsName;
  amhsSelBox.setList(amhsNameList);
  amhsSelBox.setValue(screenValue.amhsId);

  amhsSelBox.onChange(function() {
    /* Step4 2017_08_16 */
    // エラー表示をクリア
    selComp.clearErrors();

    getData(amhsSelBox.getValue(), true);
  });

  // コンポーネントマネージャ
  var selComp = new McsComponentManager();
  /* Step4 2017_08_16 */
  selComp.add('amhsId', amhsSelBox);

  // スライドメニュー生成
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  creTopMenu();

  // ---------------------------------------
  // 状態画面コンポーネント生成
  // ---------------------------------------
  // テキストボックス
  var stateCommState = new McsTextBox($('#state-comm-state'));
  var stateControlState = new McsTextBox($('#state-control-state'));
  var stateSystemState = new McsTextBox($('#state-system-state'));
  var stateAvailable = new McsTextBox($('#state-available'));
  // テキストボックス読み取り専用化
  stateCommState.setReadonly(true);
  stateControlState.setReadonly(true);
  stateSystemState.setReadonly(true);
  stateAvailable.setReadonly(true);

  // テーブル
  var stateTable = new McsTable($('#state-table-target'));
  // 行選択不可設定
  stateTable.setNotRowSelect(true);

  // 状態テーブルヘッダ(状態テーブル)
  var stateHeader = [{
    name: 'setTime',
    text: screenText.state.alarmDateTime,
    display: true
  }, {
    name: 'alarmId',
    text: screenText.state.alarmId,
    display: true
  }, {
    name: 'alarmText',
    text: screenText.state.alarmText,
    display: true
  }];

  // ヘッダ設定(状態テーブル)
  stateTable.setHeader(stateHeader);
  stateTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // ポート画面コンポーネント生成
  // ---------------------------------------
  // ポートテーブル生成
  var portTable = new McsTable($('#port-table-target'));
  // 行選択不可設定
  portTable.setNotRowSelect(true);

  // ポートテーブルヘッダ
  var portHeader = [{
    name: 'portId',
    text: screenText.port.portId,
    display: true
  }, {
    name: 'portType',
    text: screenText.port.type,
    display: true
  }, {
    name: 'portIo',
    text: screenText.port.ioMode,
    display: true
  }, {
    name: 'portAvail',
    text: screenText.port.available,
    display: true
  }, {
    name: 'portLState',
    text: screenText.port.logicalState,
    display: true
  }];

  // ヘッダ設定(ポートテーブル)
  portTable.setHeader(portHeader);
  portTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // Microコマンド画面コンポーネント生成
  // ---------------------------------------
  // Microコマンドテーブル生成
  var microCmdTable = new McsTable($('#microCmd-table-target'));
  // 行選択不可設定
  microCmdTable.setNotRowSelect(true);

  // Step4 2017/09/04
  // Microコマンドテーブルヘッダ
  var microCmdHeader = [{
    name: 'commandId',
    text: screenText.microCmd.commandId,
    display: true
  }, {
    name: 'priority',
    align: 'right',
    text: screenText.microCmd.priority,
    display: true
  }, {
    name: 'carrierId',
    text: screenText.microCmd.carrierId,
    display: true
  }, {
    name: 'srcLoc',
    text: screenText.microCmd.microFrom,
    display: true
  }, {
    name: 'dstLoc',
    text: screenText.microCmd.microTo,
    display: true
  }, {
    name: 'carrierJobState',
    text: screenText.microCmd.jobState,
    display: true
  }, {
    name: 'waitInTime',
    text: screenText.microCmd.waitInTime,
    display: true
  }];

  // ヘッダ設定(Microコマンドテーブル)
  microCmdTable.setHeader(microCmdHeader);
  microCmdTable.setBodyHeight($('.mcs-content.mcs-with-subheader.mcs-with-subtitle').outerHeight() - 40);

  // ---------------------------------------
  // 初期表示処理
  // ---------------------------------------
  // 状態画面の表示
  showStateScreen();
  // 状態画面のデータ取得、表示
  getState(amhsSelBox.getValue(), true);
  // 自動更新有効化
  AutoReloadTimerManager.addTimeoutCallback(function() {
    if (latestAmhsId !== undefined) {
      getData(latestAmhsId, false, true, true);
    }
    AutoReloadTimerManager.start();
  });
  AutoReloadTimerManager.start();

  /**
   ******************************************************************************
   * @brief   右スライドメニュー生成メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    ボタン等のコンポーネント生成を行い、各処理を設定。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function creTopMenu() {
    // ボタン生成
    var reloadBtn = new McsButton($('#btn-reload'), screenText.slideMenuBtn.reload);
    var stateBtn = new McsButton($('#btn-state'), screenText.slideMenuBtn.state);
    var portBtn = new McsButton($('#btn-port'), screenText.slideMenuBtn.port);
    var microCmdBtn = new McsButton($('#btn-micro-cmd'), screenText.slideMenuBtn.microCmd);
    var rtnBtn = new McsButton($('#btn-return'), screenText.slideMenuBtn.ret);

    // 再表示ボタン押下処理
    reloadBtn.onClick(function() {
      if (latestAmhsId !== undefined) {
        /* Step4 2017_08_16 */
        // エラー表示をクリア
        selComp.clearErrors();

        getData(latestAmhsId, false, true);
      }
    });

    // 状態ボタン押下処理
    stateBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // 状態画面のデータ取得、表示
      getState(amhsSelBox.getValue(), false);
    });

    // ポートボタン押下処理
    portBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // ポート画面のデータ取得、表示
      getPort(amhsSelBox.getValue(), false);
    });

    // Microコマンドボタン押下処理
    microCmdBtn.onClick(function() {
      /* Step4 2017_08_16 */
      // エラー表示をクリア
      selComp.clearErrors();
      // Microコマンド画面のデータ取得、表示
      getMicroCmd(amhsSelBox.getValue(), false);
    });

    // 戻るボタン押下処理
    rtnBtn.onClick(function() {
      slideMenuTop.toggle();
    });
  }

  /**
   ******************************************************************************
   * @brief   状態画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    状態画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showStateScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.STATE;

    // 状態ボタン非表示
    $('#btn-state').hide();
    $('#btn-port').show();
    $('#btn-micro-cmd').show();

    // 各画面の表示切替
    $('#state-screen').show();
    $('#port-screen').hide();
    $('#microCmd-screen').hide();

    // テーブルのヘッダ幅調整
    stateTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   ポート画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    ポート画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showPortScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.PORT;

    // ポートボタン非表示
    $('#btn-state').show();
    $('#btn-port').hide();
    $('#btn-micro-cmd').show();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').show();
    $('#microCmd-screen').hide();

    // テーブルのヘッダ幅調整
    portTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   Microコマンド画面表示メソッド
   * @param
   * @return
   * @retval
   * @attention
   * @note    Microコマンド画面の表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function showMicroCmdScreen() {
    // 表示画面番号の更新
    screenIndex = SCREEN.MICRO_CMD;

    // Micoroコマンドボタン非表示
    $('#btn-state').show();
    $('#btn-port').show();
    $('#btn-micro-cmd').hide();

    // 各画面の表示切替
    $('#state-screen').hide();
    $('#port-screen').hide();
    $('#microCmd-screen').show();

    // テーブルのヘッダ幅調整
    microCmdTable.resizeColWidth();
  }

  /**
   ******************************************************************************
   * @brief   表示画面のデータ取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    現在表示している画面のデータを取得する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getData(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    switch (screenIndex) {
      case SCREEN.STATE:
        getState(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      case SCREEN.PORT:
        getPort(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
      case SCREEN.MICRO_CMD:
        getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag);
        break;
    }
  }

  /**
   ******************************************************************************
   * @brief   状態取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    状態の取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getState(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetConvStateInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = stateTable.getScrollTop();
      var left = stateTable.getScrollLeft();

      // 表示をクリア
      clearState();

      if (retObj.body) {
        // テキストボックスのデータ
        var textValue = retObj.body.state;
        var tableValue = retObj.body.alarmList;

        // データをテキストボックスにセット
        stateCommState.setValue(textValue.commState);
        stateControlState.setValue(textValue.controlState);
        stateSystemState.setValue(textValue.systemState);
        stateAvailable.setValue(textValue.available);

        // データをテーブルにセット
        stateTable.addDataList(tableValue);
      }

      // 状態画面の表示
      showStateScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        stateTable.setScrollTop(top);
        stateTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   ポート取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    ポートの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getPort(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetConvPortInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = portTable.getScrollTop();
      var left = portTable.getScrollLeft();

      // テーブルをクリア
      portTable.clear();
      // データをテーブルにセット
      portTable.addDataList(retObj.body, retObj.rowColorList);

      // ポート画面の表示
      showPortScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        portTable.setScrollTop(top);
        portTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   Microコマンド取得メソッド
   * @param   {String} amhsId 検索条件のAMHSID
   * @param   {boolean} ctrlChgFlag(true:コントローラ変更時 / false:画面遷移時)
   * @param   {boolean} scrollFixFlag
   *                    (true:スクロール位置保持 / false:スクロール位置初期化)
   * @return
   * @retval
   * @attention
   * @note    Microコマンドの取得を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function getMicroCmd(amhsId, ctrlChgFlag, scrollFixFlag, autoReloadFlag) {
    var url = getUrl('/Individual/GetConvMicroCmdInfo');
    var cond = {
      amhsId: amhsId,
      ctrlChgFlag: ctrlChgFlag
    };

    // 成功時処理
    var onSuccess = function(retObj) {
      // 直近の検索成功時のAMHSIDを更新
      latestAmhsId = amhsId;

      // テーブルのスクロール位置を保持
      var top = microCmdTable.getScrollTop();
      var left = microCmdTable.getScrollLeft();

      // テーブルをクリア
      microCmdTable.clear();
      // データをテーブルにセット
      microCmdTable.addDataList(retObj.body, retObj.rowColorList);

      // Microコマンド画面の表示
      showMicroCmdScreen();

      // テーブルのスクロール位置を設定
      if (scrollFixFlag !== undefined && scrollFixFlag) {
        microCmdTable.setScrollTop(top);
        microCmdTable.setScrollLeft(left);
      }
    };

    // エラー時処理
    var onError = function(retObj) {
      // 検索失敗時はエラーを反映
      selComp.setErrors(retObj.result.errorInfoList);
    };

    // 取得結果0件時処理
    var onEmpty = onSuccess;

    // 検索を実行
    callAjax(url, cond, true, onSuccess, onError, null, true, onEmpty, 0, autoReloadFlag);
  }

  /**
   ******************************************************************************
   * @brief   状態画面の表示クリア機能
   * @param
   * @return
   * @retval
   * @attention
   * @note     状態画面のコンポーネントをクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function clearState() {
    // 状態画面のテキストボックスをクリア
    stateCommState.clear();
    stateControlState.clear();
    stateSystemState.clear();
    stateAvailable.clear();
    // 状態画面のテーブルをクリア
    stateTable.clear();
  }
});
