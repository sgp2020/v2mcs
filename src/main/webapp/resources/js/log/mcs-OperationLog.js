/**
 ******************************************************************************
 * @file        mcs-OperationLog.js
 * @brief       操作ログ表示画面用JavaScript
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
  // ******************************************************
  // データテーブル生成
  // ******************************************************
  var dataTables = new McsDataTables($('#lst-table-target'), true);
  // ******************************************************
  // スライド生成
  // ******************************************************
  // Top
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  // 検索用スライドの初期化
  var searchMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-searchMenu')
  });
  // CSV保存用スライドの初期化
  var saveMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-saveMenu')
  });

  // CSV保存用のdatetimePicker生成
  // 開始の項目
  var saveStart = new McsDateTime($('#mcs-saveStartDatetime'), screenText.saveText.start, 75);
  // 終了の項目
  var saveEnd = new McsDateTime($('#mcs-saveEndDatetime'), screenText.saveText.end, 75);

  // コンポーネントマネージャー生成
  var searchComp = new McsComponentManager();
  // コンポーネントマネージャー生成
  var saveComp = new McsComponentManager();
  // CSV保存用スライドの生成
  saveCsvSlide();
  // 検索用スライドの生成
  searchSlide();
  // // 検索スライドのキャリアジョブ状態のデフォルト値を保持
  // var defCarrierJobState = searchComp.get('carrierJobState').getValue();
  // トップのスライド生成
  createTopSlide();

  /**
   ******************************************************************************
   * @brief  トップスライドのコンポーネントを生成する機能
   * @param
   * @return
   * @retval
   * @attention
   * @note    トップスライドのコンポーネントを生成する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createTopSlide() {
    // ******************************************************
    // ボタン生成
    // ******************************************************
    // searchボタン
    var searchButton = new McsButton($('#btn-search'), screenText.btnText.search);

    // reloadボタン
    var reloadButton = new McsButton($('#btn-reload'), screenText.btnText.update);

    // saveボタン
    var saveButton = new McsButton($('#btn-save'), screenText.btnText.save);

    // returnボタン
    var returnButton = new McsButton($('#btn-return'), screenText.btnText.cancel);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 検索ボタン押下
    searchButton.onClick(function() {
      // 検索フラグを取得
      var searchDataFlag = dataTables.getLatestSearchDataFlag();

      if (searchDataFlag) {
        // 画面の内容を消去
        searchComp.get('from').clear();
        searchComp.get('to').clear();
        searchComp.get('userId').clear();
        searchComp.get('logCode').clear();
        searchComp.clearErrors();

        // 前回の条件を復元
        var datas = dataTables.getLatestCond();

        searchComp.get('from').setValue(datas.from);
        searchComp.get('to').setValue(datas.to);
        searchComp.get('userId').setValue(datas.userId);
        searchComp.get('logCode').setValue(datas.logCode);
      } else {
        // 空の場合
        searchComp.get('from').clear();
        searchComp.get('to').clear();
        searchComp.get('userId').clear();
        searchComp.get('logCode').clear();
        searchComp.clearErrors();
      }
      searchMenu.show();
    });
    // 再表示ボタン押下
    reloadButton.onClick(function() {
      slideHideFlag = false;
      dataTables.reload();
    });
    // 保存ボタン押下
    saveButton.onClick(function() {
      // 日時入力部品を初期化
      saveComp.get('from').clear();
      saveComp.get('to').clear();
      // エラー表示をクリア
      saveComp.clearErrors();
      // 検索の有無を判定
      var searchDataFlag = dataTables.getLatestSearchDataFlag();
      if (searchDataFlag) {
        // 前回の検索条件を取得
        var datas = dataTables.getLatestCond();
        saveComp.get('from').setValue(datas.from);
        saveComp.get('to').setValue(datas.to);
      }
      saveMenu.show();
    });
    // 戻るボタン押下
    returnButton.onClick(function() {
      // McsSlideMenu.primaryMenuSlide.hide();
      slideMenuTop.toggle();
    });
  }

  /**
   ******************************************************************************
   * @brief  検索用スライドのコンポーネントを生成する機能
   * @param
   * @return
   * @retval
   * @attention
   * @note    検索用スライドのコンポーネントを生成する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function searchSlide() {
    // ******************************************************
    // 検索項目生成
    // ******************************************************
    // datetimePicker生成
    // 開始の項目
    var from = new McsDateTime($('#mcs-fromDatetime'), screenText.searchText.from, 75);
    // 終了の項目
    var to = new McsDateTime($('#mcs-toDatetime'), screenText.searchText.to, 75);

    // ユーザのテキストボックス
    var userId = new McsTextBox($('#mcs-userIdTextBox'));

    // コードのテキストボックス
    var logCode = new McsTextBox($('#mcs-logCodeTextBox'));

    // ******************************************************
    // ボタン生成
    // ******************************************************
    // 抽出ボタン
    var extractButton = new McsButton($('#btn-extract'), screenText.btnText.extract);

    // クリアボタン
    var clearButton = new McsButton($('#btn-clear'), screenText.btnText.clear);

    // 検索の戻るボタン
    var searchReturnButton = new McsButton($('#btn-searchReturn'), screenText.btnText.searchReturn);

    // ******************************************************
    // コンポ―ネットマネージャーに登録
    // ******************************************************
    searchComp.add('from', from);
    searchComp.add('to', to);
    searchComp.add('userId', userId);
    searchComp.add('logCode', logCode);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // Step4 2017_08_09：userId.setMaxLength(16);をuserId.setMaxLength(64); に修正
    // ユーザ
    userId.setMaxLength(64);
    // コード
    logCode.setMaxLength(10);
    // 抽出ボタン押下
    extractButton.onClick(function() {
      // エラーメッセージを消去
      searchComp.clearErrors();
      // スライドを閉じるように設定
      slideHideFlag = true;
      var url = getUrl('/OperationLog/GetOperationLog');
      var cond = {
        from: from.getValue(),
        to: to.getValue(),
        userId: userId.getValue(),
        logCode: logCode.getValue()
      };
      var tableCompId = 'L-006-dataTables';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function(retObj) {
          // 成功時
          // キャプションをセット
          if (retObj.caption) {
            dataTables.setCaption(retObj.caption);
          }
        },
        serverError: function(result) {
          // 検索失敗時
          searchComp.setErrors(result.result.errorInfoList);
        },
        ajaxError: function() {
          // Ajaxエラー時
          // 何もしない
        }
      };
      dataTables.getDataAjax(options);
    });

    // クリアボタン押下
    clearButton.onClick(function() {
      from.clear();
      to.clear();
      userId.clear();
      logCode.clear();
      searchComp.clearErrors();
    });

    // 戻るボタン押下
    searchReturnButton.onClick(function() {
      searchMenu.hide();
    });
  }

  /**
   ******************************************************************************
   * @brief  CSV保存用スライドのコンポーネントを生成する機能
   * @param
   * @return
   * @retval
   * @attention
   * @note    CSV保存用スライドのコンポーネントを生成する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function saveCsvSlide() {
    // ******************************************************
    // ボタン生成
    // ******************************************************
    // 決定ボタン
    var confirmButton = new McsButton($('#btn-confirm'), screenText.btnText.confirm);
    // CSV保存の戻るボタン
    var saveReturnButton = new McsButton($('#btn-saveReturn'), screenText.btnText.saveReturn);

    // ******************************************************
    // コンポ―ネットマネージャーに登録
    // ******************************************************
    saveComp.add('from', saveStart);
    saveComp.add('to', saveEnd);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 決定ボタン押下
    confirmButton.onClick(function() {
      // エラーメッセージを消去
      saveComp.clearErrors();
      // CSV出力
      var datas = dataTables.getLatestCond();

      datas.from = saveStart.getValue();
      datas.to = saveEnd.getValue();

      callAjax(getUrl('/OperationLog/SetCsvOperationLog'), JSON.stringify(datas, null, '   '), false, function(retObj) {
        window.location.href = getUrl('/OperationLog/SaveCsvOperationLog');
      }, function(retObj) {
        saveComp.setErrors(retObj.result.errorInfoList);
        // 失敗時
        // なにもしない
      }, function(status, error) {
        // Ajax通信失敗
        // なにもしない
      });
    });

    // 戻るボタン押下
    saveReturnButton.onClick(function() {
      saveMenu.hide();
    });
  }

  // ******************************************************
  // 初期表示表示
  // ******************************************************
  var url = getUrl('/OperationLog/GetOperationLog');
  var cond = {
    from: '',
    to: '',
    userId: '',
    logCode: ''
  };
  var searchDataFlag = false;
  var tableCompId = 'L-006-dataTables';
  var options = {
    url: url,
    cond: cond,
    searchDataFlag: searchDataFlag,
    tableCompId: tableCompId,
    success: function() {
      // 成功時
      // 何もしない
    },
    serverError: function() {
      // サーバエラー時
      // 何もしない
    },
    ajaxError: function() {
      // ajaxError時
      // 何もしない
    }
  };
  // 検索開始
  dataTables.getDataAjax(options);
});
