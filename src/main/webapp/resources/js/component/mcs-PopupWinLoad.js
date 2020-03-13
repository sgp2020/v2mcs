/**
 ******************************************************************************
 * @file        mcs-PopupWinLoad.js
 * @brief       POSTによるポップアップウィンドウに関する部品
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
/**
 ******************************************************************************
 * @brief   コンストラクタ
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
var McsPopupWinLoad = function() {
  this._init();
};

McsPopupWinLoad.prototype = {
  /**
   ******************************************************************************
   * @brief   初期化処理
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
  _init: function() {
    this.popupWin = {};
  },
  /**
   ******************************************************************************
   * @brief   ポップアップウィンドウ表示処理
   * @param   {Object}  options  ポップアップウィンドウ生成時のオプション
   * @return
   * @retval
   * @attention
   * @note    引数optionsに以下の通り値を設定する。
   *          {
   *              url：POST通信を行うURL,
   *              winName：ウィンドウに与える名前,
   *              sendValue：サーバに送るデータオブジェクト,
   *              limitWindow：生成するウィンドウ数の上限,
   *              width：ウィンドウの横幅,
   *              height：ウィンドウの縦幅
   *          }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  open: function(options) {
    // ポップアップウィンドウ表示用formを事前削除
    $('#postPopupWin').remove();

    // ウィンドウ名が設定されていない場合は現在の時間をウィンドウ名とする
    var winName = options.winName ? options.winName : Date.now().toString();

    // すでにウィンドウが存在していればそれを閉じる
    if (this.popupWin[winName] != null && !this.popupWin[winName].closed) {
      this.popupWin[winName].close();
    }

    // 閉じられているウィンドウが存在する場合は変数から削除
    for ( var key in this.popupWin) {
      if (this.popupWin[key] != null && this.popupWin[key].closed) {
        delete this.popupWin[key];
      }
    }

    // ウィンドウが制限数未満の場合はウィンドウを生成
    if (!options.limitWindow || Object.keys(this.popupWin).length < options.limitWindow) {
      // ウィンドウの外枠を考慮し、ウィンドウサイズを計算する
      // Step4 2017_08_25
      var widthPerScreen = screen.availWidth / options.width;
      var heightPerScreen = screen.availHeight / options.height;
      var reductionRatio = Math.min(widthPerScreen, heightPerScreen, 1.0);
      var width = Math.floor(options.width * reductionRatio);
      var height = Math.floor(options.height * reductionRatio);
      var userAgent = window.navigator.userAgent.toLowerCase();
      if (userAgent.indexOf('edge') != -1) {
        width -= 16;
        height -= 84;
      } else if (userAgent.indexOf('chrome') != -1) {
        width -= 10;
        height -= 57;
      }

      // ブランクでポップアップウィンドウを生成し、管理変数に格納
      // Step4 2017_08_29
      var openWinName = winName + Date.now().toString();
      this.popupWin[winName] = window.open('about:blank', openWinName, 'width=' + width + ',height=' + height +
          ',scrollbars=no,resizable=no');

      // POST送信用のformを作成し上記で生成したポップアップウィンドウに追加～実行
      // Step4 2017_08_29
      var html = '<form method="POST" action="' + options.url + '" id="postPopupWin" target="' + openWinName +
          '" style="display: none;">';
      for ( var key in options.sendValue) {
        if (options.sendValue.hasOwnProperty(key)) {
          html += '<input type="hidden" name="' + key + '" value="' + options.sendValue[key] + '" >';
        }
      }
      html += '</form>';
      $('body').append(html);
      $('#postPopupWin').submit();

      // 実行完了後は追加したformを削除
      $('#postPopupWin').remove();
    }
  }
};
