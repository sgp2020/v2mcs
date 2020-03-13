/**
 ******************************************************************************
 * @file        mcs-MsgView.js
 * @brief       制御から画面へのメッセージ管理/表示する部品
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
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */

/**
 ******************************************************************************
 * @brief メッセージ一覧コンポーネント
 * @param {jQuery} containerDiv 格納先のdiv要素
 * @param
 * @return
 * @retval
 * @attention
 * @note ダイアログボックスコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsMsgView = function() {
  // コンストラクタ
  this.init();
};

// static変数
// ダイアログ情報格納変数
McsMsgView.dialog = null;
// メッセージデータ保持件数
McsMsgView.MaxMsgSize = 100;
// localStorageキー値 - データ
McsMsgView.MsgDataKey = 'msgData';
// localStorageキー値 - 受信者ID
McsMsgView.RecvIDKey = 'recvId';
// localStorageキー値 - 新規メッセージフラグ
McsMsgView.NewMsgFlagKey = 'newMsgFlag';
// localStorageキー値 - ローディングフラグ
McsMsgView.MsgLoadKey = 'loadingFlag';

// メソッド類
McsMsgView.prototype = {
  /**
   ******************************************************************************
   * @brief    初期処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     コンポーネントの組み立て等を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function() {
    // ローディングフラグをOFF
    localStorage.setItem(McsMsgView.MsgLoadKey, 'flase');
    // 一覧表示用のダイアログを生成
    this.initDialog();
    // RecvIDを設定
    this.setRecvID();
    // タブの有効・無効によるイベントを設定
    this.initWindowFocusEvent();
    // アイコンの色を更新
    this.changeIconColor();
    // 自動更新処理を開始
    this.startAutoReload();
  },

  /**
   ******************************************************************************
   * @brief    ダイアログ初期化処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     メッセージ一覧を表示するダイアログの初期化処理を実施します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  initDialog: function() {
    // タイトルの取得
    var dialogTitle = McsMsgView.language.TITLE;

    // ダイアログの生成
    var dialogWrapper = $('<div class="mcs-MsgView-dialog-wrapper"></div>');
    var dialog = new McsDialog(dialogWrapper, dialogTitle);
    this.dialog = dialog;

    // ダイアログの要素作成
    var dialogContent = $('<div id="mcs-MsgView-table"></div>');
    this.dialogContent = dialogContent;
  },

  /**
   ******************************************************************************
   * @brief    ダイアログ表示処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     メッセージ一覧のダイアログを表示します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  showMsgDialog: function() {
    var self = this;
    // 更新フラグ OFF
    localStorage.setItem(McsMsgView.NewMsgFlagKey, 'false');
    this.changeIconColor();
    // 自動更新停止
    this.stopAutoReload();
    // ダイアログの表示処理
    this.dialog.openCommonPartsDialogThreeButton(self.dialogContent, 600, 400, McsMsgView.language.BTNDEL,
        McsMsgView.language.BTNCLEAR, McsMsgView.language.BTNCLOSE, function(buttonNum) {
          if (buttonNum == 0) {
            // 削除ボタン押下時の動作
            // データリストの削除
            self.msgTable.delSelectedRowData();
            // localStorageの更新を行う
            var data = self.msgTable.getValue();
            self.updateMsgData(data);
          } else if (buttonNum == 1) {
            // クリアボタン押下時の動作
            // テーブルデータの削除
            self.msgTable.clear();
            // localStorage上のデータ削除
            self.clearMsgData();
          } else {
            // 閉じるボタン押下の動作
            // 自動更新再開
            self.startAutoReload();
          }
        });
    // 表示テーブルの設定
    this.setTable();
  },

  /**
   ******************************************************************************
   * @brief    テーブル初期化処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     メッセージ一覧ダイアログのテーブル初期化を実施します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setTable: function() {
    // 一旦、ダイアログの中身をクリア
    this.dialogContent.empty();
    // テーブル部品生成
    this.msgTable = new McsTable(this.dialogContent);
    // テーブルの縦幅を設定
    this.msgTable.setBodyHeight(235);
    // 複数選択可能
    this.msgTable.setMultiRowSelect(true);

    // スクロール位置を記憶
    if (this.scroll == undefined) {
      this.scroll = this.dialogContent.find('tbody').scrollTop();
    }

    // ヘッダ情報設定
    this.msgTable.setHeader([{
      name: 'date',
      text: McsMsgView.language.table.date,
      display: true,
      align: 'left'
    }, {
      name: 'message',
      text: McsMsgView.language.table.message,
      display: true,
      align: 'left'
    }]);

    // localStorageからデータを取得し、画面へ表示
    var dataList = this.getMsgData();
    this.msgTable.addDataList(dataList);
    this.dialogContent.find('tbody').scrollTop(0);
  },

  /**
   ******************************************************************************
   * @brief    受信者ID設定処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     画面起動時に受信者IDの設定を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setRecvID: function() {
    // 受信者IDを取得
    var recvID = this.getRecvID();
    // 受信者IDが存在しない場合は、データを更新
    if (recvID == null || recvID == '') {
      var url = window.mcsComponentContextRoot + 'Com/McsMsgView/GetRecvID';
//      callAjax(url, null, false, function(retObj) {
//        // データ保存処理を実行
//        localStorage.setItem(McsMsgView.RecvIDKey, retObj.recvId);
//      }, function(retObj) {
//        // エラー時の処理 - 特になし
//      }, function(retObj) {
//        // Ajax呼び出し失敗時に実行するファンクション - 特になし
//      }, false, function(retObj) {
//        // 検索結果0件のコールバック処理 - 特になし
//      }, 9);
    } else {
      // 特に処理なし
    }
  },

  /**
   ******************************************************************************
   * @brief    受信者ID取得処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     ブラウザに保持している受信者IDを取得します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getRecvID: function() {
    // 受信者IDを取得
    return localStorage.getItem(McsMsgView.RecvIDKey);
  },

  /**
   ******************************************************************************
   * @brief    受信者ID更新処理
   * @param    data 受信者ID
   * @return
   * @retval
   * @attention
   * @note     ブラウザに保存されている受信者IDの更新を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  updateRecvID: function(data) {
    if (data != null) {
      // 受信者IDの更新
      localStorage.setItem(McsMsgView.RecvIDKey, data);
    }
  },

  /**
   ******************************************************************************
   * @brief    メッセージデータ保存処理
   * @param    data 保存するメッセージデータ配列
   * @return
   * @retval
   * @attention
   * @note     ブラウザに取得したメッセージデータを保存します。
   *            保存されるデータは100件までで、古いデータから捨てられます。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setMsgData: function(data) {
    var dataList = [];
    var localData = this.getMsgData();
    var loopCnt = 0;
    // 現在の保持メッセージと新規追加のメッセージサイズを最大保持数と比較
    if (McsMsgView.MaxMsgSize >= localData.length + data.length) {
      loopCnt = localData.length;
    } else {
      // MaxMsgSizeを超える場合は、古いデータから削除するようループ回数を調整
      loopCnt = McsMsgView.MaxMsgSize - data.length;
    }

    // ローカルデータの取得および更新
    // 必要なデータを保持
    for (var i = 0; i < loopCnt; i++) {
      dataList.push({
        date: localData[i].date,
        message: localData[i].message
      });
    }

    // サーバ側で100件を超えるようなデータは取得しないように調整
    for (var i = 0; i < data.length; i++) {
      // 追加データは先頭に入れる
      dataList.unshift({
        date: data[i].date,
        message: data[i].message
      });
    }
    // メッセージデータの更新
    localStorage.setItem(McsMsgView.MsgDataKey, JSON.stringify(dataList));
  },

  /**
   ******************************************************************************
   * @brief    メッセージ取得処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     ブラウザに保存されているメッセージを取得します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getMsgData: function() {
    // localStorageからメッセージを取得
    var retData = JSON.parse(localStorage.getItem(McsMsgView.MsgDataKey));

    if (retData != null) {
      return retData;
    } else {
      return [];
    }
  },

  /**
   ******************************************************************************
   * @brief    メッセージ更新処理
   * @param    date メッセージデータ
   * @return
   * @retval
   * @attention
   * @note     ブラウザに保存されているメッセージの更新を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  updateMsgData: function(data) {
    if (data != null) {
      // メッセージデータの更新
      localStorage.setItem(McsMsgView.MsgDataKey, JSON.stringify(data));
    }
  },

  /**
   ******************************************************************************
   * @brief    メッセージクリア処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     ブラウザに保存されているメッセージのクリアを行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearMsgData: function() {
    // localStorage上からデータを削除
    localStorage.removeItem(McsMsgView.MsgDataKey);
  },

  /**
   ******************************************************************************
   * @brief    メッセージ要求処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     サーバに対してメッセージの要求を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  resMsgData: function() {
    var url = window.mcsComponentContextRoot + 'Com/McsMsgView/GetMsgDataList';
    // データの取得
    var data = {
      recvId: this.getRecvID()
    };
    var self = this;

    // サーバに対してデータ取得中は、再度リクエストを送らない
    if (localStorage.getItem(McsMsgView.MsgLoadKey) == null || localStorage.getItem(McsMsgView.MsgLoadKey) != 'true') {
      // データ取得中フラグをON
      localStorage.setItem(McsMsgView.MsgLoadKey, 'true');
      // サーバへメッセージの問い合わせを実施
//      callAjax(url, JSON.stringify(data), false, function(retObj) {
//        // データの更新
//        self.setMsgData(retObj.msgData);
//        if (retObj.msgData.length > 0) {
//          // 色変更フラグ ON
//          localStorage.setItem(McsMsgView.NewMsgFlagKey, 'true');
//        }
//        // 受信者ID更新
//        self.updateRecvID(retObj.recvId);
//        // アイコン色変更
//        self.changeIconColor();
//        // データ取得完了後、フラグをOFF
//        localStorage.setItem(McsMsgView.MsgLoadKey, 'false');
//      }, function(retObj) {
//        // エラー時の処理 - 特になし
//      }, function(retObj) {
//        // Ajax呼び出し失敗時に実行するファンクション - 特になし
//      }, false, function(retObj) {
//        // 検索結果0件のコールバック処理 - 特になし
//      }, 9);
    }
  },

  /**
   ******************************************************************************
   * @brief    自動更新開始処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     定期的にメッセージを取得するように、自動更新処理を開始します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  startAutoReload: function() {
    var self = this;

    // 自動更新の停止
    self.stopAutoReload();

    // 自動更新の設定
    self.timer = window.setTimeout(function() {
      // データ取得リクエスト実施
      self.resMsgData();

      // 自動更新処理を再設定
      // Ajax通信が非同期のため、データ取得の結果にかかわらず、タイマースタート
      self.startAutoReload();
    }, 30 * 1000);

    this.timer = self.timer;
  },

  /**
   ******************************************************************************
   * @brief    自動更新停止処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     自動更新処理の停止を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  stopAutoReload: function() {
    if (this.timer != null) {
      window.clearTimeout(this.timer);
    }
    this.timer = null;
  },

  /**
   ******************************************************************************
   * @brief    フォーカスイベント初期設定処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     タブ(ウィンドウ)有効時イベントの初期設定を行います。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  initWindowFocusEvent: function() {
    var self = this;
    if (!self._initWindowFocusEventDoneFlag) {
      // タブ(ウィンドウ)有効時
      $(window).focus(function() {
        // アイコン色変更処理
        self.changeIconColor();
        // 自動更新開始
        self.startAutoReload();
      });
      // タブ(ウィンドウ)無効時
      $(window).blur(function() {
        // 自動更新停止
        self.stopAutoReload();
      });
    }
  },

  /**
   ******************************************************************************
   * @brief    アイコン色変更処理
   * @param    なし
   * @return
   * @retval
   * @attention
   * @note     メッセージの取得状況に合わせて、アイコンの色を変更します。
   *            メッセージあり：赤
   *            メッセージなし：青
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  changeIconColor: function() {
    // 色変更フラグ OFF
    var newMsgFlag = localStorage.getItem(McsMsgView.NewMsgFlagKey);
    if (newMsgFlag != null && newMsgFlag == 'true') {
      // 新規メッセージあり
      $('#mcs-msg-icon-menu').css('color', 'red');
    } else {
      // 新規メッセージなし
      $('#mcs-msg-icon-menu').css('color', '#5877BA');
    }
  },

  end: 'end'
};

/**
 ******************************************************************************
 * @brief    コンポーネント作成処理
 * @param    なし
 * @return
 * @retval
 * @attention
 * @note     メッセージの一覧・状態を表示するアイコンに対して、
 *            本コンポーネントを作成します。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
$(function() {
  var msgHeaderMenu = $('#mcs-msg-icon-menu');
  McsMsgView.dialog = new McsMsgView($('#mcs-msg-icon-menu'));
  // アイコンクリック時の動作登録
  msgHeaderMenu.click(function() {
    // ダイアログ表示
    McsMsgView.dialog.showMsgDialog();
  });
});
