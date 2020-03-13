/**
 ******************************************************************************
 * @file        mcs-common.js
 * @brief       共通JavaScript
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
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   コンテキストルートからのURLを取得する
 * @param   {String} url URL
 * @return  {String}     コンテキストルートからのURL
 * @retval
 * @attention
 * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
 *          他jsファイルで呼び出されているため無視する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
function getUrl(url) {
  if (url == null || url.length == 0) {
    return window.mcsComponentContextRoot;
  }
  if (url.charAt(0) == '/') {
    return window.mcsComponentContextRoot + url.substr(1);
  } else {
    return window.mcsComponentContextRoot + url;
  }
}

/**
 ******************************************************************************
 * @brief   メッセージプロパティの内容を変更する
 * @param   {String} oldStr      置換前メッセージ
 * @param   {String} replaceStr  置換用テキスト
 * @return  {String}             置換後メッセージ
 * @retval
 * @attention 変更したい箇所はプロパティファイル内で _REPLACEMENT_ と記述
 * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
 *          他jsファイルで呼び出されているため無視する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
function replaceMessage(oldStr, replaceStr) {
  return oldStr.replace(/_REPLACEMENT_/, replaceStr);
}

/**
 ******************************************************************************
 * @brief   エラーダイアログを表示する。
 * @param   {McsDialog} ajaxResDialog  エラーダイアログ
 * @param   {String}    msg            エラーメッセージ
 * @param   {Function}  proc           エラー時のコールバック
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
function showErrorDialog(ajaxResDialog, msg, proc) {
  ajaxResDialog.openAlertProc(msg, window.mcsDialogBtnReturn, 'alert', proc);
}

/**
 ******************************************************************************
 * @brief   Ajax呼び出しを実行する。
 * @param {String}   callUrl     実行URL
 * @param {Object}   data        入力パラメータ
 * @param {Boolean}  searchFlg   検索フラグ（検索の場合true）
 * @param {Function} onSuccess   正常時に実行するファンクション
 * @param {Function} onError     異常時に実行するファンクション
 *                               （エラーダイアログClose時に実行）
 * @param {Function} onFail      Ajax呼び出し失敗（通信エラー）時に実行するファンクション
 *                               （エラーダイアログClose時に実行）
 * @param {Boolean}  showDialogOnErrorFlag
 *                               エラー時のダイアログを出すかどうか（trueか未指定の場合出す）
 * @param {Function} onEmpty     検索フラグtrueで、検索結果が0件だったときのコールバック
 * @param {Boolean}  modalFlag   Ajax中のローディング表示フラグ。
 *                               0:表示（デフォルト） 1:Loading画像 9:非表示
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
function callAjax(callUrl, data, searchFlg, onSuccess, onError, onFail, showDialogOnErrorFlag, onEmpty, modalFlag, autoReloadFlag) {
  // ダイアログを生成（最初の一回だけ）
  if (!callAjax.ajaxResDialog) {
    var dialogDiv = $('<div></div>').appendTo('body');
    callAjax.ajaxResDialog = new McsDialog(dialogDiv, window.mcsDialogTitleError);
  }
  // ローディング表示を生成（最初の一回だけ）
  var ajaxResDialog = callAjax.ajaxResDialog;
  if (!callAjax.modalLoading) {
    callAjax.modalLoading = new McsLoading();
  }

  // データ整形
  if (typeof data !== 'string') {
    data = JSON.stringify(data);
  }

  // ローディング表示
  if (modalFlag === undefined || modalFlag == 0) {
    // 透明で表示
    callAjax.modalLoading.show(true);
  } else if (modalFlag == 1) {
    // Loading画像
    callAjax.modalLoading.show(false);
  }

  $.ajax({
    type: 'POST',
    url: callUrl,
    contentType: 'application/json',
    dataType: 'text',
    data: data,
    timeout: 15000,
    async: true,
    cache: false
  }).done(function(data, status, jqxhr) {
    if (modalFlag === undefined || modalFlag == 0 || modalFlag == 1) {
      callAjax.modalLoading.hide();
    }
    var jsonObj = JSON.parse(data);
    if (jsonObj.result.status == 'error') {
      if (showDialogOnErrorFlag === undefined || showDialogOnErrorFlag) {
        showErrorDialog(ajaxResDialog, jsonObj.result.message, function() {
          if (onError) {
            onError(jsonObj);
          }
        });
      } else {
        if (onError) {
          onError(jsonObj);
        }
      }
    } else if (searchFlg && (jsonObj.body == null || jsonObj.body.length == 0)) {
      if (showDialogOnErrorFlag === undefined || showDialogOnErrorFlag) {
        if (autoReloadFlag === undefined || !autoReloadFlag) {
          showErrorDialog(ajaxResDialog, window.mcsConstMsgAjaxNoData, function() {
            if (onEmpty) {
              onEmpty(jsonObj);
            }
          });
        } else {
          if (onEmpty) {
            onEmpty(jsonObj);
          }
        }
      } else {
        if (onEmpty) {
          onEmpty(jsonObj);
        }
      }
    } else {
      if (onSuccess) {
        onSuccess(jsonObj);
      }
    }
  }).fail(function(jqxhr, status, error) {
    if (modalFlag === undefined || modalFlag == 0 || modalFlag == 1) {
      callAjax.modalLoading.hide();
    }
    showErrorDialog(ajaxResDialog, window.mcsConstMsgAjaxError, function() {
      if (onFail) {
        onFail(status, error);
      }
    });
  });
}

/**
 ******************************************************************************
 * @brief   ウィンドウ生成制御
 * @param   {String}  url       生成するウィンドウのURL
 * @param   {String}  winName   生成するウィンドウの名前
 * @return
 * @retval
 * @attention
 * @note    このjsファイルでは未使用のためESLintでエラーが出るが、
 *           他jsファイルで呼び出されているため無視する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var winLoad = (function() {
  // 子ウィンドウオブジェクトを格納する変数
  var win = {};
  return function(url, winName) {
    // すでにウィンドウが存在していればそれを閉じる
    if (win[winName] != null && !win[winName].closed) {
      win[winName].close();
    }
    // 名前に時刻情報を付与してウィンドウを生成
    var time = Date.now();
    win[winName] = window.open(url, winName + time);
  };
}());

/**
 ******************************************************************************
 * @brief   未操作時の自動ログアウトスクリプト
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
$(function() {
  $(document).idleTimeout({
    userId: comLoginUserName,
    redirectUrl: false,
    idleTimeLimit: comAutoLogoutTime,
    idleCheckHeartbeat: 1000,
    winName: window.name,
    activityEvents: 'click keypress scroll wheel mousewheel',
    customCallback: function() {
      if (comLoginUserName != NOLOGIN_USER) {
        // mcsLogout(comTopScreenFlag);
        if (window.name != '') {
          // 子画面はクローズ
          self.close();
        } else {
          // TOP画面はログアウトしてリロード
          var values = {};
          values['userName'] = comLoginUserName;
          values['password'] = '';
          var str = JSON.stringify(values, null, '   ');
          callAjax(window.mcsComponentContextRoot + 'Logout', str, false, function(retObj) {
            // ログアウトOK（画面リロードし未ログインユーザ情報反映させる）
            location.reload();
          }, function(retObj) {
            // ログアウト失敗（特に処理なし）
          }, function() {
            // Ajax通信エラー（特に処理なし）
          });
        }
      }
    }
  });
});

/**
 ******************************************************************************
 * @brief   ヘッダー共通メニューの動作
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
$(function() {
  // ログイン状態の表示
  var loginStatusText = $('#mcs-user-info-status-value');
  var userNameText = $('#mcs-user-info-name-value');
  if (comLoginUserName !== NOLOGIN_USER) {
    // ログイン中
    loginStatusText.text(window.COMMON_SCREEN_TEXT.LOGIN_STATUS.LOGGEDIN);
    userNameText.text(comLoginUserName);
    userNameText.parent().show();
  } else {
    // 未ログイン
    loginStatusText.text(window.COMMON_SCREEN_TEXT.LOGIN_STATUS.NOLOGIN);
    // userNameText.text(comLoginUserName);
    userNameText.parent().hide();
  }

  // 現在日時の表示
  var getDate = function() {
    var now = new Date();
    var year = now.getFullYear();
    var month = ('0' + (now.getMonth() + 1)).slice(-2);
    var day = ('0' + now.getDate()).slice(-2);
    var hour = ('0' + now.getHours()).slice(-2);
    var minute = ('0' + now.getMinutes()).slice(-2);
    var second = ('0' + now.getSeconds()).slice(-2);

    var dateStr = year + '/' + month + '/' + day;
    var timeStr = hour + ':' + minute + ':' + second;
    $('#mcs-user-info-date-value').text(dateStr);
    $('#mcs-user-info-time-value').text(timeStr);
  };
  setInterval(getDate, 1000);
  getDate();
});
