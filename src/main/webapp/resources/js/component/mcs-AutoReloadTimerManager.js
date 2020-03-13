/**
 ******************************************************************************
 * @file        mcs-AutoReloadTimerManager.js
 * @brief       自動更新周りを管理する部品
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
// 自動更新のタイマー管理
// ESLintでエラーが出るが、他jsファイルで呼び出されているため無視する。
var AutoReloadTimerManager = {

  // チェック用コールバック関数リスト
  checkCallbackList: [],
  // タイマーのコールバック関数リスト
  timeoutCallbackList: [],
  // タイマーの時間。0は仮の値。本当の値はシステムパラメータから取得する。
  delay: 0,

  /**
   ******************************************************************************
   * @brief 自動更新可否判定コールバック追加
   * @param {Function} callback コールバック
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新が可能かどうかを判定する処理を、コールバックとして登録する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addCheckCallback: function(callback) {
    // 重複登録チェック
    for (var i = 0; i < this.checkCallbackList.length; i++) {
      if (this.checkCallbackList[i] === callback) {
        return this;
      }
    }
    // 登録
    this.checkCallbackList.push(callback);
    return this;
  },

  /**
   ******************************************************************************
   * @brief 自動更新時コールバック追加
   * @param {Function} callback コールバック
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新タイマーがタイムアウトした時のコールバックを登録する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addTimeoutCallback: function(callback) {
    // 重複登録チェック
    for (var i = 0; i < this.timeoutCallbackList.length; i++) {
      if (this.timeoutCallbackList[i] === callback) {
        return this;
      }
    }
    // 登録
    this.timeoutCallbackList.push(callback);
    return this;
  },

  /**
   ******************************************************************************
   * @brief 自動更新時のコールバックを削除
   * @param {Function} callback 削除するコールバック
   * @return {McsAutoReloadTimerManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新が可能かどうかチェックするコールバックを削除。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  deleteCheckCallback: function(callback) {
    var oldList = this.checkCallbackList;
    var newList = [];
    for (var i = 0; i < oldList.length; i++) {
      if (oldList[i] !== callback) {
        newList.push(oldList[i]);
      }
    }
    this.checkCallbackList = newList;
    return this;
  },

  /**
   ******************************************************************************
   * @brief タイムアウト時のコールバックを削除
   * @param {Function} callback 削除するコールバック
   * @return {McsAutoReloadTimerManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新が可能かどうかチェックするコールバックを削除。
   *  ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  deleteTimeoutCallback: function(callback) {
    var oldList = this.timeoutCallbackList;
    var newList = [];
    for (var i = 0; i < oldList.length; i++) {
      if (oldList[i] !== callback) {
        newList.push(oldList[i]);
      }
    }
    this.timeoutCallbackList = newList;
    return this;
  },

  /**
   ******************************************************************************
   * @brief 自動更新を再開
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新を開始する。 ただし、条件を満たしていない場合は開始されず、停止する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  start: function() {
    var self = this;

    // 一旦止める。
    self.stop();

    // コールバック存在チェック
    if (self.timeoutCallbackList.length == 0) {
      return self;
    }

    // 可否チェック①
    if (self.delay === undefined || self.delay === null || isNaN(self.delay) || self.delay <= 0) {
      return self;
    }

    // 可否チェック②
    for (var i = 0; i < self.checkCallbackList.length; i++) {
      var canStart = self.checkCallbackList[i]();
      if (!canStart) {
        return self;
      }
    }

    // 可否チェックをクリアしたなら、自動更新開始
    self.timer = window.setTimeout(function() {
      self.stop();
      for (var i = 0; i < self.timeoutCallbackList.length; i++) {
        console.log('AutoReload Timeout! delay=' + self.delay);
        self.timeoutCallbackList[i](self.delay);
      }
    }, self.delay * 1000);

    console.log('AutoReload start. delay=' + self.delay);
    return self;
  },

  /**
   ******************************************************************************
   * @brief 自動更新を停止する。
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note 自動更新のタイマーを停止する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  stop: function() {
    if (this.timer) {
      window.clearTimeout(this.timer);
    }
    this.timer = null;
    return this;
  },

  // Step4 2017_09_06
  /**
   ******************************************************************************
   * @brief タブ（ウィンドウ）のフォーカスによるイベントを初期化する
   * @return
   * @retval
   * @attention
   * @note フォーカスが当たっている時のみ自動更新を行う
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  initWindowFocusEvent: function() {
    // フォーカスが変化したときに再開・停止する設定
    var self = this;
    self.blurExcecuteFlag = false;
    $(window).focus(function() {
      if (!self.blurExcecuteFlag) {
        // 要素にフォーカスが当たってから更新を行う（更新処理を実行キューの後ろへ入れる）
        setTimeout(function() {
          self.start();
        }, 0);
      }
    });
    $(window).blur(function() {
      if (!self.blurExcecuteFlag) {
        self.stop();
      }
    });
    // フォーカスが当たっていない時は自動更新しない設定
    this.addCheckCallback(this._hasFocus);
  },

  // Step4 2017_09_06
  /**
   ******************************************************************************
   * @brief フォーカスが当たっているかを判定する
   * @return {Boolean} true:フォーカスが当たっている, false:当たっていない
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _hasFocus: function() {
    return document.hasFocus();
  },

  // Step4 2017_09_06
  /**
   ******************************************************************************
   * @brief フォーカスが当たっていない時も自動更新を行うようにする
   * @return {AutoReloadManager} このコンポーネント自身
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnableBlurExecute: function() {
    this.blurExcecuteFlag = true;
    return this.deleteCheckCallback(this._hasFocus);
  }
};

$(function() {
  AutoReloadTimerManager.initWindowFocusEvent();
});
