/**
 ******************************************************************************
 * @file        mcs-DialogBox.js
 * @brief       ダイアログボックスに関する部品
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 * 2018/04/18 MACS4#0158  一覧表示ハイパーリンク化抑制対応            T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief ダイアログボックスコンポーネント
 * @param {jQuery} containerDiv ダイアログ化するdiv要素
 * @param {Object} title ダイアログのタイトル
 * @return
 * @retval
 * @attention
 * @note ダイアログボックスコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsDialog = function(containerDiv, title) {
  // コンストラクタ
  this.init(containerDiv, title);
};
// メソッド類
McsDialog.prototype = {
  /**
   ******************************************************************************
   * @brief    初期化処理
   * @param    {jQuery} containerDiv ダイアログ化するdiv要素
   * @param    {Object} title  ダイアログのタイトル
   * @return
   * @retval
   * @attention
   * @note     初期化処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, title) {
    // ダイアログ基本情報を登録
    var autoRealodCheckCallback = function() {
      // ダイアログが開いていたときは自動更新を再開しないためのコールバック
      return !containerDiv.dialog('isOpen');
    };
    containerDiv.dialog({
      title: title,
      modal: true,
      autoOpen: false,
      resizable: false,
      width: 550,
      height: 'auto',
      closeOnEscape: false,
      open: function(event, ui) {
        // ×ボタンを隠す。
        $('.ui-dialog-titlebar-close').hide();
        // 自動更新を停止
        AutoReloadTimerManager.stop();
        AutoReloadTimerManager.addCheckCallback(autoRealodCheckCallback);
      },
      close: function(event, ui) {
        // 自動更新を再開
        AutoReloadTimerManager.deleteCheckCallback(autoRealodCheckCallback);
        AutoReloadTimerManager.start();
      }
    });

    // グローバルに退避
    this.containerDiv = containerDiv;

    // アイコンを生成しておく
    this.dlgicon = {};
    // エラー
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.dlgicon.alert = $('<span class="mcs-dialogbox-icon glyphicon glyphicon-remove-sign"style="display:inline-block"></span>');
    this.dlgicon.error = this.dlgicon.alert;
    // 警告
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.dlgicon.warn = $('<span class="mcs-dialogbox-icon glyphicon glyphicon-exclamation-sign" style="display:inline-block"></span>');
    // 情報
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.dlgicon.info = $('<span class="mcs-dialogbox-icon glyphicon glyphicon-info-sign" style="display:inline-block"></span>');
    // 確認
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.dlgicon.confirm = $('<span class="mcs-dialogbox-icon glyphicon glyphicon-question-sign" style="display:inline-block"></span>');
    this.dlgicon.help = this.dlgicon.confirm;
    // 検索
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.dlgicon.search = $('<span class="mcs-dialogbox-icon glyphicon glyphicon-search" style="display:inline-block"></span>');
  },

  /**
   ******************************************************************************
   * @brief     ひな形生成
   * @param     {String} message メッセージ
   * @param     {String} icon アイコン名
   * @param     {jQueryObj} additionalObj  メッセージの下部に追加するオブジェクト
   * @return    {jQueryObj} ダイアログ化するべきjQueryオブジェクト。実際はdivタグ。
   * @retval
   * @attention
   * @note       ダイアログのひな形を生成して返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0158  一覧表示ハイパーリンク化抑制対応                       T.Iga/CSC
   ******************************************************************************
   */
  _createDialog: function(message, icon, additionalObj) {
    // レイアウト用のテーブル
    // MACS4#0158 Mod Start
//  var table = $('<table class="mcs-dialogbox-layout-table"><tr><td class="mcs-dialogbox-td-icon"></td>'
//      + '<td class="mcs-dialogbox-td-message"></td></tr></table>');
    var table = $('<table class="mcs-dialogbox-layout-table"><tr><td class="mcs-dialogbox-td-icon"></td>'
            + '<td class="mcs-dialogbox-td-message" x-ms-format-detection="none"></td></tr></table>');
    // MACS4#0158 Mod End

    // アイコン設定
    if (icon) {
      var dlgicon = this.dlgicon[icon];
      if (dlgicon == undefined)
        dlgicon = this.dlgicon.alert;
      table.find('.mcs-dialogbox-td-icon').append(dlgicon);
    }

    // ダイアログメッセージ
    if (message) {
      var dlgmsg = $('<p></p>');
      var lines = message.split('\n');
      for (var i = 0; i < lines.length; i++) {
        var span = $('<span></span>').text(lines[i]);
        dlgmsg.append(span).append($('<br>'));
      }
      table.find('.mcs-dialogbox-td-message').append(dlgmsg);
    }

    // 要素配下を一旦空にしてからアイコン、メッセージ、追加要素を追加する。
    var dialogObj = this.containerDiv;
    dialogObj.empty();
    dialogObj.append(table);
    if (additionalObj) {
      dialogObj.append(additionalObj);
    }

    return dialogObj;
  },

  /**
   ******************************************************************************
   * @brief    ダイアログ表示（１ボタン）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} button1 ボタン名称
   * @param    {String} icon アイコン名
   * @return
   * @retval
   * @attention
   * @note     アラートダイアログ（１ボタン）を表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openAlert: function(message, button1, icon) {
    return this.openAlertProc(message, button1, icon, null);
  },

  /**
   ******************************************************************************
   * @brief    ダイアログ表示（１ボタン）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} button1 ボタン名称
   * @param    {String} icon アイコン名
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     アラートダイアログ（１ボタン）を表示（押下時処理あり）する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openAlertProc: function(message, button1, icon, callback) {
    // ダイアログのひな形生成
    var dialogObj = this._createDialog(message, icon);

    // ボタン処理の記述
    var buttonObj = [{
      'text': button1,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialog-return'
    }];

    // ダイアログにボタン埋め込み～ダイアログ表示
    dialogObj.dialog('option', {
      buttons: buttonObj
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    確認ダイアログ表示（２ボタン）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} buttonOk OKボタン名称
   * @param    {String} buttonCancel Cancelボタン名称
   * @param    {String} icon アイコン名
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     確認ダイアログ（２ボタン）を表示する。
   *            OKボタン押下時はcallbackにtrueが、Cancelボタン押下時はfalseが渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openConfirm: function(message, buttonOk, buttonCancel, icon, callback) {
    // ダイアログのひな形生成
    var dialogObj = this._createDialog(message, icon);

    // ボタン処理の記述
    var buttonObj = [{
      'text': buttonOk,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(true, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialoge-confirm'
    }, {
      'text': buttonCancel,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(false, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialog-return'
    }];

    // ダイアログにボタン埋め込み～ダイアログ表示
    dialogObj.dialog('option', {
      buttons: buttonObj
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    確認ダイアログ表示（３ボタン）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {String} button3 ３つ目のボタン名称
   * @param    {String} icon アイコン名
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     確認ダイアログ（３ボタン）を表示する。
   *            ボタン押下時はcallbackに押下したボタンの
   *            インデックス番号(0:１つ目のボタン, 1:２つ目のボタン, 2:３つ目のボタン)
   *            が渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openButtonTriple: function(message, button1, button2, button3, icon, callback) {
    // ダイアログのひな形生成
    var dialogObj = this._createDialog(message, icon);

    // ボタン処理の記述
    var buttonObj = [{
      'text': button1,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(0, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }, {
      'text': button2,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(1, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }, {
      'text': button3,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          callback(2, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }];

    // ダイアログにボタン埋め込み～ダイアログ表示
    dialogObj.dialog('option', {
      buttons: buttonObj
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    確認ダイアログ表示（チェックボックス）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} checkBoxText チェックボックスのテキスト
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     確認ダイアログ（チェックボックスがあるタイプ）を表示する。
   *            ボタン押下時はcallbackに
   *            チェックボックスの状態(true: チェックあり, false: チェックなし)が渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openCheckBoxInform: function(message, button1, checkBoxText, callback) {
    // チェックボックスの生成
    var checkBoxWrapper = $('<div></div>').css('margin', '10px 0 -5px 0');
    var checkBox = new McsCheckBox(checkBoxWrapper, checkBoxText);

    // ダイアログ初期化
    var dialogObj = this._createDialog(message, 'info', checkBoxWrapper);

    // ボタン処理の設定
    var buttonObj = [{
      'text': button1,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          var checked = checkBox.getValue();
          callback(checked, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }];

    dialogObj.dialog('option', {
      buttons: buttonObj
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    確認ダイアログ表示（チェックボックスと２ボタンがあるタイプ）処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {String} icon アイコン名
   * @param    {String} checkBoxText チェックボックスのテキスト
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     確認ダイアログ（チェックボックスと２ボタンがあるタイプ）を表示する。
   *            ボタン押下時はcallbackに
   *            チェックボックスの状態(true: チェックあり, false: チェックなし)と、
   *            押下したボタンのインデックス番号(0:１つ目のボタン, 1:２つ目のボタン)
   *            が渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openCheckBoxBtnDouble: function(message, button1, button2, icon, checkBoxText, callback) {
    // チェックボックスの生成
    var checkBoxWrapper = $('<div></div>').css('margin', '10px 0 -5px 0');
    var checkBox = new McsCheckBox(checkBoxWrapper, checkBoxText);

    // ダイアログ初期化
    var dialogObj = this._createDialog(message, icon, checkBoxWrapper);

    // ボタン処理の設定
    var buttonObj = [{
      'text': button1,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          var checked = checkBox.getValue();
          callback(0, checked, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }, {
      'text': button2,
      'click': function() {
        dialogObj.dialog('close');
        if (callback) {
          var checked = checkBox.getValue();
          callback(1, checked, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog'
    }];

    dialogObj.dialog('option', {
      buttons: buttonObj
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    ログインダイアログ表示処理
   * @param    {String} message ダイアログのメッセージ
   * @param    {String} userNameText テキストボックス横に表示するテキスト
   * @param    {String} passText テキストボックス横に表示するテキスト
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @return
   * @retval
   * @attention
   * @note     ログイン用ダイアログ表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openLoginDialog: function(message, userNameText, passText, button1, button2) {
    // ログイン用フォームの設定
    var textBoxWrapper = $('<div></div>').css('margin', '10px 10px -5px 10px');
    textBoxWrapper.append(userNameText);
    var userName = new McsTextBox(textBoxWrapper, null, userNameText);
    textBoxWrapper.append(passText);
    var password = new McsTextBox(textBoxWrapper, 'password');

    // ダイアログ初期化
    var dialogObj = this._createDialog(message, 'info', textBoxWrapper);

    // ボタン処理
    var buttonObj = [{
      'text': button1,
      'click': function() {
        var values = {};
        values.userName = userName.getValue();
        values.password = password.getValue();
        var str = JSON.stringify(values, null, '   ');
        callAjax(window.mcsComponentContextRoot + 'Login', str, false, function(retObj) {
          // ログインOK（画面リロードしユーザ情報反映させる）
          localStorage.setItem('idleTimerUserId', JSON.stringify(userName.getValue()));
          location.reload();
        }, function(retObj) {
          // ログイン失敗（特に処理なし）
        }, function() {
          // Ajax通信エラー（特に処理なし）
        });
      },
      'class': 'btn-mcs-dialog'
    }, {
      'text': button2,
      'click': function() {
        dialogObj.dialog('close');
      },
      'class': 'btn-mcs-dialog btn-dialoge-cancel'
    }];

    dialogObj.dialog('option', {
      buttons: buttonObj,
      position: {
        my: 'center top',
        at: 'center top',
        of: window
      }
    });

    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    プログレスバー表示(２ボタン)ダイアログ処理
   * @param    {String} processMessage 処理中のダイアログのメッセージ
   * @param    {String} endMessage 処理が終了した時のダイアログのメッセージ
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {Function} callback1 ボタン１押下時の処理
   * @param    {Function} callback2 ボタン２押下時の処理
   * @return
   * @retval
   * @attention
   * @note     プログレスバー用ダイアログ(２ボタンがあるタイプ)を表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openProgressDialog: function(processMessage, endMessage, button1, button2, callback1, callback2) {
    var progressWrapper = $('<div class="mcs-progressBarWrapper"></div>');
    var progressLabel = $('<div class="mcs-progressBarLabel">0%</div>');
    var self = this;
    // プログレスバーの生成
    progressWrapper.progressbar({
      value: 0,
      change: function() {
        // 値変化時
        progressLabel.text(progressWrapper.progressbar('value') + '%');
      },
      complete: function() {
        // 値MAX時
        progressLabel.text('');
        var dialogButton1 = self.containerDiv.next().find('.mcs-progressBarDialog-button1');
        var dialogButton2 = self.containerDiv.next().find('.mcs-progressBarDialog-button2');
        // ボタンを非活性&カーソルデザインを変更
        dialogButton1.prop('disabled', true);
        dialogButton1.css({
          cursor: 'not-allowed'
        });
        dialogButton2.prop('disabled', false);
        dialogButton2.css({
          cursor: 'pointer'
        });
        dialogButton2.focus();
        self.containerDiv.find('.mcs-dialogbox-td-message span').text(endMessage);
      }
    });
    progressWrapper.append(progressLabel);
    // ダイアログ初期化
    var dialogObj = this._createDialog(processMessage, 'info', progressWrapper);

    // ボタン処理の設定
    dialogObj.dialog('option', {
      buttons: [{
        'text': button1,
        'class': 'btn-mcs-dialog btn-dialog-return mcs-progressBarDialog-button1 btn-default btn',
        'click': function() {
          dialogObj.dialog('close');
          if (callback1) {
            callback1();
          }
        }
      }, {
        'text': button2,
        'class': 'btn-mcs-dialog btn-dialoge-confirm mcs-progressBarDialog-button2 btn-default btn',
        'click': function() {
          dialogObj.dialog('close');
          if (callback2) {
            callback2();
          }
        }
      }]
    });
    // ボタンを非活性&カーソルデザインを変更
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').prop('disabled', false);
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').css({
      cursor: 'pointer'
    });
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').focus();
    this.containerDiv.next().find('.mcs-progressBarDialog-button2').prop('disabled', true);
    this.containerDiv.next().find('.mcs-progressBarDialog-button2').css({
      cursor: 'not-allowed'
    });
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    プログレスバー表示(1ボタン)ダイアログ処理
   * @param    {String} processMessage 処理中のダイアログのメッセージ
   * @param    {String} endMessage 処理が終了した時のダイアログのメッセージ
   * @param    {String} button1 １つ目のボタン名称
   * @param    {Function} callback1 ボタン１押下時の処理
   * @return
   * @retval
   * @attention
   * @note     プログレスバー用ダイアログ(２ボタンがあるタイプ)を表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openProgressDialogOneButton: function(processMessage, endMessage, button1, callback1) {
    var progressWrapper = $('<div class="mcs-progressBarWrapper"></div>');
    var progressLabel = $('<div class="mcs-progressBarLabel">0%</div>');
    var self = this;
    // プログレスバーの生成
    progressWrapper.progressbar({
      value: 0,
      change: function() {
        // 値変化時
        progressLabel.text(progressWrapper.progressbar('value') + '%');
      },
      complete: function() {
        // 値MAX時
        progressLabel.text('');
        var dialogButton1 = self.containerDiv.next().find('.mcs-progressBarDialog-button1');
        // ボタンを非活性&カーソルデザインを変更
        dialogButton1.prop('disabled', true);
        dialogButton1.css({
          cursor: 'not-allowed'
        });
        self.containerDiv.find('.mcs-dialogbox-td-message span').text(endMessage);
      }
    });
    progressWrapper.append(progressLabel);
    // ダイアログ初期化
    var dialogObj = this._createDialog(processMessage, 'info', progressWrapper);

    // ボタン処理の設定
    dialogObj.dialog('option', {
      buttons: [{
        'text': button1,
        'class': 'btn-mcs-dialog btn-dialog-return mcs-progressBarDialog-button1 btn-default btn',
        'click': function() {
          dialogObj.dialog('close');
          if (callback1) {
            callback1();
          }
        }
      }]
    });
    // ボタンを非活性&カーソルデザインを変更
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').prop('disabled', false);
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').css({
      cursor: 'pointer'
    });
    this.containerDiv.next().find('.mcs-progressBarDialog-button1').focus();
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    メッセージの更新処理
   * @param    {String} text メッセージ
   * @return
   * @retval
   * @attention
   * @note     ダイアログのメッセージを更新する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setDialogText :function(text) {
      this.containerDiv.find('.mcs-dialogbox-td-message').text(text);
  },

  /**
   ******************************************************************************
   * @brief    プログレスバーの更新処理
   * @param    {Number} value プログレスバーのパーセンテージ
   * @return
   * @retval
   * @attention
   * @note     プログレスバーを更新する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setProgressBar: function(value) {
    this.containerDiv.find('.mcs-progressBarWrapper').progressbar('value', value);
  },

  /**
   ******************************************************************************
   * @brief    共通部品搭載用ダイアログ処理
   * @param    {MCS部品} parts 挿入する部品
   * @param    {Number} dialogWidth ダイアログの横幅
   * @param    {Number} dialogHeight ダイアログの縦幅
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {Function} callback ボタン押下時の処理
   * @param    {Boolean} noButtonClose ボタン押下時にダイアログを辻るかどうか true：閉じない
   * @return
   * @retval
   * @attention
   * @note     共通部品搭載用ダイアログを表示する。
   *            ボタン押下時はcallbackに
   *            押下したボタンのインデックス番号(0:１つ目のボタン, 1:２つ目のボタン)
   *            が渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openCommonPartsDialog: function(parts, dialogWidth, dialogHeight, button1, button2, callback, noButtonClose) {
    var partsWrapper = $('<div class="mcs-partsDialogWrapper"></div>');

    if (parts.containerDiv != null) {
      // 部品の場合、containerDivを追加する。
      partsWrapper.append(parts.containerDiv);
    } else {
      // 部品以外の場合、直接追加する。（jQueryオブジェクトを想定）
      partsWrapper.append(parts);
    }

    // ダイアログのスクロールをしないように変更
    this.containerDiv.css({
      overflow: 'visible'
    });

    // ダイアログの大きさ調整
    if (dialogWidth) {
      this.containerDiv.dialog({
        width: dialogWidth
      });
    }
    if (dialogHeight) {
      this.containerDiv.dialog({
        height: dialogHeight
      });
    }

    // ダイアログ初期化
    var dialogObj = this._createDialog('', null, partsWrapper);

    // ボタン処理の設定、記述
    var buttonObj = [{
      'text': button1,
      'click': function() {
        if (!noButtonClose) {
          dialogObj.dialog('close');
        }
        if (callback) {
          callback(0, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialoge-confirm'
    }, {
      'text': button2,
      'click': function() {
        if (!noButtonClose) {
          dialogObj.dialog('close');
        }
        if (callback) {
          callback(1, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialog-return'
    }];

    dialogObj.dialog('option', {
      buttons: buttonObj
    });

    // 開く
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    共通部品搭載用ダイアログ処理
   * @param    {MCS部品} parts 挿入する部品
   * @param    {Number} dialogWidth ダイアログの横幅
   * @param    {Number} dialogHeight ダイアログの縦幅
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {String} button3 ３つ目のボタン名称
   * @param    {Function} callback ボタン押下時の処理
   * @return
   * @retval
   * @attention
   * @note     共通部品搭載用ダイアログを表示する。
   *            ボタン押下時はcallbackに
   *            押下したボタンのインデックス番号(0:１つ目のボタンが渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openCommonPartsDialogThreeButton: function(parts, dialogWidth, dialogHeight, button1, button2, button3, callback) {
    var partsWrapper = $('<div class="mcs-partsDialogWrapper"></div>');

    if (parts.containerDiv != null) {
      // 部品の場合、containerDivを追加する。
      partsWrapper.append(parts.containerDiv);
    } else {
      // 部品以外の場合、直接追加する。（jQueryオブジェクトを想定）
      partsWrapper.append(parts);
    }

    // ダイアログのスクロールをしないように変更
    this.containerDiv.css({
      overflow: 'visible'
    });

    // ダイアログの大きさ調整
    if (dialogWidth) {
      this.containerDiv.dialog({
        width: dialogWidth
      });
    }
    if (dialogHeight) {
      this.containerDiv.dialog({
        height: dialogHeight
      });
    }

    // ダイアログ初期化
    var dialogObj = this._createDialog('', null, partsWrapper);

    // ボタン処理の設定、記述
    var buttonObj = {};
    buttonObj[button1] = function() {
      if (callback) {
        callback(0, dialogObj);
      }
    };
    buttonObj[button2] = function() {
      if (callback) {
        callback(1, dialogObj);
      }
    };
    buttonObj[button3] = function() {
      // 3つ目のボタンのみ、ダイアログのクローズ処理を実装
      dialogObj.dialog('close');
      if (callback) {
        callback(2, dialogObj);
      }
    };
    dialogObj.dialog('option', {
      buttons: buttonObj
    });

    // 開く
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    共通部品搭載用ダイアログ処理
   * @param    {MCS部品} parts 挿入する部品
   * @param    {Number} dialogWidth ダイアログの横幅
   * @param    {Number} dialogHeight ダイアログの縦幅
   * @param    {String} button1 １つ目のボタン名称
   * @param    {String} button2 ２つ目のボタン名称
   * @param    {Function} callback ボタン押下時の処理
   * @param    {Boolean} noButtonClose ボタン押下時にダイアログを閉じるかどうか true：閉じない
   *                                    (2つ目のボタンのみが対象)
   * @return
   * @retval
   * @attention
   * @note     共通部品搭載用ダイアログを表示する。
   *            ボタン押下時はcallbackに
   *            押下したボタンのインデックス番号(0:１つ目のボタン, 1:２つ目のボタン)
   *            が渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  openCommonPartsDialogButton1NoClose: function(parts, dialogWidth, dialogHeight, button1, button2, callback, noButtonClose) {
    var partsWrapper = $('<div class="mcs-partsDialogWrapper"></div>');

    if (parts.containerDiv != null) {
      // 部品の場合、containerDivを追加する。
      partsWrapper.append(parts.containerDiv);
    } else {
      // 部品以外の場合、直接追加する。（jQueryオブジェクトを想定）
      partsWrapper.append(parts);
    }

    // ダイアログのスクロールをしないように変更
    this.containerDiv.css({
      overflow: 'visible'
    });

    // ダイアログの大きさ調整
    if (dialogWidth) {
      this.containerDiv.dialog({
        width: dialogWidth
      });
    }
    if (dialogHeight) {
      this.containerDiv.dialog({
        height: dialogHeight
      });
    }

    // ダイアログ初期化
    var dialogObj = this._createDialog('', null, partsWrapper);

    // ボタン処理の設定、記述
    var buttonObj = [{
      'text': button1,
      'click': function() {
        if (callback) {
          callback(0, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialoge-confirm'
    }, {
      'text': button2,
      'click': function() {
        if (!noButtonClose) {
          dialogObj.dialog('close');
        }
        if (callback) {
          callback(1, dialogObj);
        }
      },
      'class': 'btn-mcs-dialog btn-dialog-return'
    }];

    dialogObj.dialog('option', {
      buttons: buttonObj
    });

    // 開く
    dialogObj.dialog('open');
  },

  /**
   ******************************************************************************
   * @brief    ダイアログのクローズ処理
   * @return
   * @retval
   * @attention
   * @note     ダイアログを閉じる。部品の戻るボタンでダイアログを閉じれるようにするためのメソッド。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  closeDialog: function() {
    this.containerDiv.dialog('close');
  },

  /**
   ******************************************************************************
   * @brief     ダイアログ要素のjQueryオブジェクトを返す。
   * @return    {jQueryObj} dialog要素
   * @retval
   * @attention
   * @note       ダイアログ要素のjQueryオブジェクトを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getJq: function() {
    return this.containerDiv;
  }
};
