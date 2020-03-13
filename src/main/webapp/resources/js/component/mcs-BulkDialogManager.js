/**
 ******************************************************************************
 * @file        mcs-BulkDialogManager.js
 * @brief       一括処理のAjaxとダイアログ表示を管理する部品
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
 * 2018/12/03 MACS4#0047  GUI要望分                                   T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief Ajax処理とダイアログ表示を管理する。
 * @return
 * @retval
 * @attention
 * @note Ajax処理とダイアログ表示を管理する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsBulkDialogManager = function() {
  this._init();
};

// メソッド類
// 処理結果件数用グローバル変数定義
var successCount = 0;
var failedCount  = 0;

McsBulkDialogManager.prototype = {
  /**
   ******************************************************************************
   * @brief 初期化処理機能
   * @return
   * @retval
   * @attention
   * @note メソッドの初期化をする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _init: function() {
    // ダイアログを作成しておく
    this._initDialog();
    // 操作不能にするためのオーバーレイを生成しておく
    this._initOverlay();
  },
  /**
   ******************************************************************************
   * @brief 初期化処理機能
   * @return
   * @retval
   * @attention
   * @note 処理中にほかの処理が実施できないようにするための オーバーレイを初期化
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initOverlay: function() {
    this.overlay = $('<div></div>').css({
      'display': 'none',
      'z-index': 10000 - 1, // ダイアログのオーバーレイの一つ下
      'position': 'fixed',
      'top': 0,
      'right': 0,
      'bottom': 0,
      'left': 0,
      'border': 'none'
    }).appendTo($('body'));
  },

  /**
   ******************************************************************************
   * @brief タスクの開始
   * @param {Object}
   *          options オプション
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note * optionsには下記の通りにオプションを指定する。
   *
   * <pre>
   *   {
   *     url: {String} Ajaxする先のURL,
   *     taskCount: {Number} タスクの数。
   *     successMessage: {String} 成功時のダイアログメッセージ。
   *                              メッセージ中の%1と%2は、タスクのインデックスと総タスク数に置換される。
   *                              未指定の場合はデフォルトのメッセージが表示される。
   *     completeMessage: {String} 全タスク完了時のダイアログメッセージ。
   *                               未指定の場合はデフォルトのメッセージが表示される。
   *     onSuccess: {Function} タスク成功時のコールバック。callAjaxのonSuccess時に呼ばれる。,
   *     onError: {Function} エラー時のコールバック。callAjaxのonError時に呼ばれる。,
   *     onAjaxError: {Function} Ajax失敗時のコールバック。callAjaxのonAjaxError時に呼ばれる。,
   *     onComplete: {Function} タスク完了時のコールバック。
   *                            指定された数のタスクが完了したときに呼ばれる。,
   *     confirmMessage: {Function|String} 未処理データが残っている時のメッセージ。
   *                                       Functionならば返り値が、Stringならばその文字列がメッセージとなる。
   *   }
   * </pre>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  start: function(options) {
    this.options = options;
    this.currentIndex = 0;
    this.doBulkFlag = false;

    this.overlay.show();
    this._doTask();
  },

  /**
   ******************************************************************************
   * @brief タスクを実行する機能
   * @return
   * @retval
   * @attention
   * @note タスクを実行する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _doTask: function() {
    if (this.options.taskCount <= this.currentIndex) {
      // タスクがすべて完了している
      this._onCompleteTask();
      return;
    }

    // タスク実施
    var ajaxData = this.options.onTask(this.currentIndex);
    var self = this;
    callAjax(self.options.url, ajaxData, false, function(data) {
      // 成功時
      if (self.options.onSuccess) {
        self.options.onSuccess(data);
      }
      // ダイアログ表示
      self._showSuccessDialog(self.currentIndex);
    }, function(jsonData) {
      // 失敗時（status == "error" の時）
      if (self.options.onError) {
        self.options.onError();
      }
      // ダイアログ表示
      self._showErrorDialog(jsonData.result.message, self.currentIndex);
    }, function() {
      // Ajax失敗時
      if (self.options.onAjaxError) {
        self.options.onAjaxError();
      }
      // ダイアログ表示
      self._showErrorDialog(window.mcsConstMsgAjaxError, self.currentIndex);
    }, false);
  },

  /**
   ******************************************************************************
   * @brief タスクを実行する機能
   * @return
   * @retval
   * @attention
   * @note 一括でタスクを実行する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _doBulkTask: function() {
    if (this.options.taskCount <= this.currentIndex) {
      // タスクがすべて完了している
      this._onCompleteTask();
      return;
    }

    // タスク実施
    var startIndex = this.currentIndex;
    var endIndex = this.options.taskCount - 1;
    var ajaxData = this.options.onBulkTask(startIndex, endIndex);
    // Ajax実施
    var self = this;
    callAjax(self.options.url, ajaxData, false, function(data) {
      // 成功時
      if (self.options.onSuccess) {
        self.options.onSuccess(data);
        // 一括処理の成功回数をカウントへ加算（キャリア削除でのみ使用）
        successCount = successCount + parseInt(data.result.value);
      }
      self._onCompleteTask();
    // キャリア削除用にdataオブジェクトを引数へ追加（キャリア削除でのみ使用）
    // }, function() {
    }, function(data) {
      // 失敗時（status == "error" の時）
      if (self.options.onError) {
        self.options.onError();
        // 一括処理の各結果（成功,失敗）回数をカウントへ加算（キャリア削除でのみ使用）
        var taskCnt = data.result.value.split(",");
        var okCnt =  parseInt(taskCnt[0]);
        var ngCnt =  parseInt(taskCnt[1]);
        successCount = successCount + okCnt;
        failedCount = failedCount + ngCnt;
      }
      self._onCompleteTask();
    }, function() {
      // Ajax失敗時
      if (self.options.onAjaxError) {
        self.options.onAjaxError();
      }
      self._onCompleteTask();
    });
  },

  /**
   ******************************************************************************
   * @brief 自動更新停止機能
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新を停止する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _doNextTask: function() {
    this.currentIndex++;

    if (this.doBulkFlag) {
      // 残りをまとめて実施するとき
      this._doBulkTask();
    } else {
      // 逐一実行の時
      this._doTask();
    }
  },

  /**
   ******************************************************************************
   * @brief タスク完了時
   * @return
   * @retval
   * @attention
   * @note タスクがすべて完了した時の動作。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _onCompleteTask: function() {
    var self = this;
    if (self.doCancelFlag) {
      // 中断（キャンセル）されていた場合はそのままコールバック
      self.overlay.hide();
      if (self.options.onComplete) {
        self.options.onComplete(false);
      }
    } else {
      // そのほかの場合（一括実行、逐次実行ともに）ダイアログを出す
      self._showCompleteDialog(function() {
        self.overlay.hide();
        if (self.options.onComplete) {
          self.options.onComplete(true);
        }
      });
    }
    // 処理結果カウント初期化
    successCount = 0;
    failedCount   = 0;
  },

  /**
   ******************************************************************************
   * @brief 初期化機能
   * @return
   * @retval
   * @attention
   * @note ダイアログの初期化を行う
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initDialog: function() {
    var title = window.mcsDialogTitleInfo;
    var errTitle = window.mcsDialogTitleError;

    this.successDialogDiv = $('<div></div>').appendTo($('body'));
    this.successDialog = new McsDialog(this.successDialogDiv, title);

    this.completeDialogDiv = $('<div></div>').appendTo($('body'));
    this.completeDialog = new McsDialog(this.completeDialogDiv, title);

    this.errorDialogDiv = $('<div></div>').appendTo($('body'));
    this.errorDialog = new McsDialog(this.errorDialogDiv, errTitle);
  },

  /**
   ******************************************************************************
   * @brief タスク成功時の機能
   * @param {Number} index タスクのインデックス
   * @param {Function} onContinue 継続ボタン押下時のコールバック
   * @param {Function} onBreak 中断ボタン押下時のコールバック
   * @return
   * @retval
   * @attention
   * @note タスク成功時のダイアログを表示し、継続処理をコールバックする。 <br>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  _showSuccessDialog: function(index, onContinue, onBreak) {
    var button1 = McsBulkDialogManager.language.DLGCHKB2BTN.BTNOK;
    var button2 = McsBulkDialogManager.language.DLGCHKB2BTN.BTNCANCEL;
    var checkBoxText = McsBulkDialogManager.language.DLGCHKB2BTN.CHKBMSG;

    var mes;
    if (this.options.successMessage != null) {
      // 成功時メッセージが指定されている場合、指定メッセージを出す
      mes = this.options.successMessage;
    } else {
      // 指定がない場合はデフォルトのメッセージを表示
      mes = McsBulkDialogManager.language.DLGCHKB2BTN.INFMSG;
    }
    //キャリア削除時のみの独自処理（次削除データがあればキャリアIDと確認メッセージ表示、なければデフォルトメッセージ表示）
    if (this.options.url == '/mcs/Carrier/ExeDeleteCarrierList') {
        if (index + 1 >= this.options.taskCount) {
            mes = McsBulkDialogManager.language.DLGCHKB2BTN.INFMSG;
        } else {
        //次削除データIDを表示
            mes = McsBulkDialogManager.language.DLGCHKB2BTN.INFMSG + '\n' + this.options.successMessage;
            mes = mes.replace(/%3/, String(this.options.whereMapList[index + 1].carrierId));
        }
    }
    // MACS4#0047 Add Start
    // アラーム削除時のみの独自処理(次削除データがあれば、確認メッセージ表示、なければデフォルトメッセージ表示)
    if (this.options.url == getUrl('/Alarm/ExeDeleteAlarmList')) {
      if (index + 1 >= this.options.taskCount) {
          mes = McsBulkDialogManager.language.DLGCHKB2BTN.INFMSG;
      } else {
      //次削除データIDを表示
          mes = McsBulkDialogManager.language.DLGCHKB2BTN.INFMSG + '\n' + this.options.successMessage;
          mes = mes.replace(/%3/, String(this.options.whereMapList[index + 1].alarmId));
          mes = mes.replace(/%4/, String(this.options.whereMapList[index + 1].amhsId));
      }
    }
    // MACS4#0047 Add End
    mes = mes.replace(/%1/, String(index + 1));
    mes = mes.replace(/%2/, String(this.options.taskCount));

    var self = this;
    if (index + 1 >= this.options.taskCount) {
      // 最後の成功ダイアログだった場合は、OKボタンのみのダイアログを出す
      var ok = McsBulkDialogManager.language.DLGBTN.BTNOK;
      this.successDialog.openAlertProc(mes, ok, 'info', function() {
        // _doNextTaskにて、最終タスク判定されて、onCompleteが実行される。
        self._doNextTask();
      });
    } else {
      // 続きのタスクがある場合は、継続ボタンと中断ボタンのダイアログを出す
      // 確認メッセージがある場合はメッセージに追加する
      if (this.options.confirmMessage) {
        var confirmMessage = (typeof this.options.confirmMessage == 'function') ? this.options
            .confirmMessage(index + 1) : this.options.confirmMessage;
        if (confirmMessage != null && confirmMessage != '') {
          mes += '\n' + confirmMessage;
        }
      }
      this.successDialog.openCheckBoxBtnDouble(mes, button1, button2, 'info', checkBoxText, function(buttonNum,
          checked, dialog) {
        self.doBulkFlag = checked;
        if (buttonNum == 0) {
          // 継続ボタン押下時
          self._doNextTask();
        } else {
          // 中断ボタン押下時
          self.doCancelFlag = true;
          self._onCompleteTask();
        }
      });
    }
    // 処理成功カウント
    successCount += 1;
  },

  /**
   ******************************************************************************
   * @brief タスク失敗時の機能
   * @param {String}  message エラーメッセージ
   * @param {Number}  index タスクのインデックス
   * @return
   * @retval
   * @attention
   * @note タスク失敗時（サーバエラー、Ajaxエラー）のダイアログを表示する。<br>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  _showErrorDialog: function(message, index) {
    var button1 = McsBulkDialogManager.language.DLGCHKB2BTN.BTNOK;
    var button2 = McsBulkDialogManager.language.DLGCHKB2BTN.BTNCANCEL;
    var checkBoxText = McsBulkDialogManager.language.DLGCHKB2BTN.CHKBMSG;

    var mes = McsBulkDialogManager.language.DLG2BTN.ERRMSG;
    if (message) {
      mes += '\n' + message;
    }
    //キャリア削除時のみの独自処理（次削除データがあればキャリアIDと確認メッセージを表示、なければデフォルトメッセージ表示）
    if (this.options.url == '/mcs/Carrier/ExeDeleteCarrierList') {
        if (index + 1 >= this.options.taskCount) {
        } else {
        //次削除データIDを表示
            mes = mes + '\n' + this.options.successMessage;
            mes = mes.replace(/%3/, String(this.options.whereMapList[index + 1].carrierId));
        }
    }
    // MACS4#0047 Add Start
    // アラーム削除時のみの独自処理(次削除データがあれば、確認メッセージ表示、なければデフォルトメッセージ表示)
    if (this.options.url == getUrl('/Alarm/ExeDeleteAlarmList')) {
      if (index + 1 >= this.options.taskCount) {
      } else {
      //次削除データIDを表示
          mes = mes + '\n' + this.options.successMessage;
          mes = mes.replace(/%3/, String(this.options.whereMapList[index + 1].alarmId));
          mes = mes.replace(/%4/, String(this.options.whereMapList[index + 1].amhsId));
      }
    }
    // MACS4#0047 Add End
    mes = mes.replace(/%1/, String(index + 1));
    mes = mes.replace(/%2/, String(this.options.taskCount));

    var self = this;
    if (index + 1 >= this.options.taskCount) {
      // 最後の成功ダイアログだった場合は、OKボタンのみのダイアログを出す
      var ok = McsBulkDialogManager.language.DLGBTN.BTNOK;
      this.errorDialog.openAlertProc(mes, ok, 'error', function() {
        // _doNextTaskにて、最終タスク判定されて、onCompleteが実行される。
        self._doNextTask();
      });
    } else {
      // 続きのタスクがある場合は、継続ボタンと中断ボタンのダイアログを出す
      // 確認メッセージがある場合はメッセージに追加する
      if (this.options.confirmMessage) {
        var confirmMessage = (typeof this.options.confirmMessage == 'function') ? this.options
            .confirmMessage(index + 1) : this.options.confirmMessage;
        if (confirmMessage != null && confirmMessage != '') {
          mes += '\n' + confirmMessage;
        }
      }
      this.errorDialog.openCheckBoxBtnDouble(mes, button1, button2, 'error', checkBoxText, function(buttonNum, checked,
          dialog) {
        self.doBulkFlag = checked;
        if (buttonNum == 0) {
          // 継続ボタン押下時
          self._doNextTask();
        } else {
          // 中断ボタン押下時
          self.doCancelFlag = true;
          self._onCompleteTask();
        }
      });
    }
    // 処理失敗カウント
    failedCount += 1;
  },

  /**
   ******************************************************************************
   * @brief コールバック処理機能
   * @param {Function} callback ダイアログ完了時のコールバック
   * @return
   * @retval
   * @attention
   * @note 全てのタスクが完了した時のダイアログを表示し、継続処理をコールバックする。<br>
   *       タスク成功時に逐一確認が出ていた場合は、何も表示せずにコールバックを呼ぶ。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  _showCompleteDialog: function(callback) {
    // 確認ダイアログがスキップされていた場合は、最後に一回ダイアログを出す
    var ok = McsBulkDialogManager.language.DLGBTN.BTNOK;

    var mes;
    if (this.options.completeMessage != null) {
      // 全完了時メッセージが指定されている場合、指定メッセージを出す
      mes = this.options.completeMessage;
      // キャリア削除時のみの独自処理（デフォルトのメッセージ + タスク全体の処理結果メッセージを表示）
      if (this.options.url == '/mcs/Carrier/ExeDeleteCarrierList') {
        mes = McsBulkDialogManager.language.DLGBTN.INFMSG + '\n\n' + this.options.completeMessage;
        if (successCount > 0) {
          mes = mes.replace(/%1/, successCount);
        } else {
          mes = mes.replace(/%1/, "0");
        }
        if (failedCount > 0) {
          mes = mes.replace(/%2/, failedCount);
        } else {
          mes = mes.replace(/%2/, "0");
        }
      }
      // MACS4#0047 Add Start
      // アラーム削除時のみの独自処理(デフォルトのメッセージ + タスク全体の処理結果メッセージを表示)
      if (this.options.url == getUrl('/Alarm/ExeDeleteAlarmList')) {
        mes = McsBulkDialogManager.language.DLGBTN.INFMSG + '\n\n' + this.options.completeMessage;
        if (successCount > 0) {
          mes = mes.replace(/%1/, successCount);
        } else {
          mes = mes.replace(/%1/, "0");
        }
        if (failedCount > 0) {
          mes = mes.replace(/%2/, failedCount);
        } else {
          mes = mes.replace(/%2/, "0");
        }
      }
      // MACS4#0047 Add End
    } else {
      // 指定がない場合はデフォルトのメッセージを表示
      mes = McsBulkDialogManager.language.DLGBTN.INFMSG;
    }

    this.completeDialog.openAlertProc(mes, ok, 'info', function(dialog) {
      callback();
    });
  }
};
