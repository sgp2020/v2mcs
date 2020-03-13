/**
 ******************************************************************************
 * @file        mcs-IdleTimeout.js
 * @brief       自動ログアウトのため無操作時のタイムアウトを管理する部品
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
(function($) {
  /**
   ******************************************************************************
   * @brief   未操作タイマーを設置しタイムアウト時にログアウト処理を行う
   * @param   {Object} userRuntimeConfig 実行時の設定オブジェクト
   * @return  {Array} 初期処理結果
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  $.fn.idleTimeout = function(userRuntimeConfig) {
    // ------------------------------------
    // Public Configuration Variables
    // ------------------------------------
    var defaultConfig = {
      userId: 'NOLOGIN', // Login user name
      redirectUrl: '/logout', // redirect to this url on logout. Set to "redirectUrl: false" to disable redirect

      // idle settings
      idleTimeLimit: 20, // 'No activity' time limit in minutes. 1200 = 20 Minutes
      idleCheckHeartbeat: 2000, // Frequency to check for idle timeouts in seconds

      // window info
      winName: window.name,

      // optional custom callback to perform before logout
      customCallback: false, // set to false for no customCallback
      activityEvents: 'click keypress scroll wheel mousewheel mousemove', // separate each event with a space
      errorAlertMessage: 'Please disable "Private Mode", or upgrade to a '
          + 'modern browser.Or perhaps a dependent file missing.'
    };

    // ------------------------------------
    // Private Variables
    // ------------------------------------
    var currentConfig = $.extend(defaultConfig, userRuntimeConfig); // merge default and user runtime configuration
    var activityDetector;
    var idleTimer;
    var checkWinOpener;
    var checkIdleTimeout;
    var checkIdleTimeoutLoop;
    var startIdleTimer;
    var stopIdleTimer; // idle
    var logoutUser;
    var storeSet;
    var storeGet;
    var enableFlag = false;

    // ------------------------------------
    // Public Functions
    // ------------------------------------
    // なし

    // ------------------------------------
    // Private Functions
    // ------------------------------------

    /**
     ******************************************************************************
     * @brief   操作を感知するイベントをBodyに紐づける
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
    // ACTIVITY DETECTION FUNCTION
    activityDetector = function() {
      $('body').on(currentConfig.activityEvents, function() {
        storeSet('idleTimerLastActivity', $.now());
      });
    };

    /**
     ******************************************************************************
     * @brief   親ウィンドウを確認しクローズ判定を行う
     * @param
     * @return
     * @retval
     * @attention
     * @note 親が居なくWindosNameが設定されていればクローズ。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    // Child Tab FUNCTIONS
    checkWinOpener = function() {
      // 親が居なくWindosNameが設定されていればクローズする。
      if (currentConfig.winName != '' && (!window.opener || Object.keys(window.opener).length == 0)) {
        self.close();
      }
    };

    /**
     ******************************************************************************
     * @brief   未操作タイムアウトを感知し、タイムアウト時にはログアウト処理を行う
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
    // IDLE TIMER FUNCTIONS
    checkIdleTimeout = function() {
      // パラメータが未設定なら自動ログアウトは機能させないためreturnする
      if (currentConfig.idleTimeLimit == '' || currentConfig.userId == '') {
        return;
      }

      // 画面表示時とユーザが変わっていればReleadもしくは画面Closeを行う
      if (storeGet('idleTimerUserId') != currentConfig.userId) { // a changed user id?
        if (currentConfig.winName != '') {
          self.close();
        } else {
          location.reload();
        }
      }

      // 未操作タイムアウト感知
      // マイナスなら未ログインなのでチェック無し
      if ((currentConfig.idleTimeLimit - 0) < 0) {
        return;
      }

      var timeIdleTimeout = (storeGet('idleTimerLastActivity') + (currentConfig.idleTimeLimit * 60 * 1000));

      if ($.now() > timeIdleTimeout) {
        logoutUser(); // immediately log out user when user is idle for idleTimeLimit
      }
    };

    /**
     ******************************************************************************
     * @brief   未操作タイマーを開始する
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
    startIdleTimer = function() {
      stopIdleTimer();
      storeSet('idleTimerLastActivity', $.now());
      checkIdleTimeoutLoop();
    };

    /**
     ******************************************************************************
     * @brief   タイムアウトをチェックし、未操作タイマーを呼び出す
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
    checkIdleTimeoutLoop = function() {
      checkWinOpener();
      checkIdleTimeout();
      idleTimer = setTimeout(checkIdleTimeoutLoop, (currentConfig.idleCheckHeartbeat));
    };

    /**
     ******************************************************************************
     * @brief   未操作タイマーを停止する
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
    stopIdleTimer = function() {
      clearTimeout(idleTimer);
    };

    /**
     ******************************************************************************
     * @brief   ログアウト処理
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
    // LOGOUT USER FUNCTION
    logoutUser = function() {
      if (currentConfig.customCallback) {
        currentConfig.customCallback();
      }
      if (currentConfig.redirectUrl) {
        window.location.href = currentConfig.redirectUrl;
      }
    };

    /**
     ******************************************************************************
     * @brief   localStorage取得処理
     * @param   {String} key localStorageから値を取得するKey
     * @return  {String} 取得した値
     * @retval
     * @attention
     * @note    引数で指定されたKeyでlocalStorageから値を取得しParse結果を返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    storeGet = function(key) {
      return JSON.parse(localStorage.getItem(key));
    };

    /**
     ******************************************************************************
     * @brief   localStorage保存処理
     * @param   {String} key localStorageに保存するKey
     * @param   {Number} val localStorageに保存する値
     * @return  {Number} valをそのまま返却
     * @retval
     * @attention
     * @note    引数で指定されたKeyでlocalStorageから値を取得しParse結果を返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    storeSet = function(key, val) {
      if (val === undefined) {
        return localStorage.removeItem(key);
      }
      localStorage.setItem(key, JSON.stringify(val));
      return val;
    };

    // Build & Return the instance of the item as a plugin
    // This is your construct.
    return this.each(function() {
      // Check LocalStorage
      try {
        var testKey = '__storejs__';
        storeSet(testKey, testKey);
        if (storeGet(testKey) == testKey) {
          enableFlag = true;
        }
        localStorage.removeItem(testKey);
      } catch (e) {
        enableFlag = false;
      }

      if (enableFlag) {
        storeSet('idleTimerLastActivity', $.now());
        if (currentConfig.userId != null && currentConfig.userId != '') {
          storeSet('idleTimerUserId', currentConfig.userId);
        }
        activityDetector();
        startIdleTimer();
      } else {
        alert(currentConfig.errorAlertMessage);
      }
    });
  };
}(jQuery));
