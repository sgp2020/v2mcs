/**
 ******************************************************************************
 * @file        mcs-Loading.js
 * @brief       ローディング表示部品
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
 * @brief     ローディング表示コンポーネント
 * @param
 * @return
 * @retval
 * @attention
 * @note      ローディング表示コンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsLoading = function() {
  this._init();
};
    // ローディング画像
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    McsLoading.imgSrc = 'data:image/gif;base64,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',
    McsLoading.depth = 0;
// メソッド類
McsLoading.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param
   * @return
   * @retval
   * @attention
   * @note      初期化処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _init: function() {
    var div = $('#mcs-loading-overlay');
    if (div.length == 0) {
      div = $('<div id="mcs-loading-overlay" tabindex="0"></div>');
      div.css({
        'display': 'none',
        'position': 'fixed',
        'top': 0,
        'left': 0,
        'width': '100%',
        'height': '100%',
        'background': 'rgba(0,0,0,0.75)',
        'z-index': 100000000
      });
      var img = $('<img>');
      var width = 128;
      var height = 128;
      img.attr('src', McsLoading.imgSrc);
      img.css({
        'width': width + 'px',
        'height': height + 'px',
        'position': 'fixed',
        'top': '50%',
        'left': '50%',
        'margin-top': '-' + (width / 2) + 'px',
        'margin-left': '-' + (height / 2) + 'px'
      });
      div.append(img);
      $('body').append(div);
    }
  },
  /**
   ******************************************************************************
   * @brief     ローディング表示
   * @param     {Boolean} clearBackFlag 画像を表示せずに透明で表示する場合はtrue
   * @return
   * @retval
   * @attention
   * @note      ローディングを表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  show: function(clearBackFlag) {
    // ローディングを表示（半透明背景、画像あり）
    // 透明背景、画像なしより優先される
    var div = $('#mcs-loading-overlay');
    if (clearBackFlag === undefined || !clearBackFlag) {
      div.css({
        'background': 'rgba(0,0,0,0.5)'
      }).show().focus();
      div.find('img').show();
    }

    // ローディング表示カウントが0ならば
    if (McsLoading.depth++ <= 0) {
      // ローディングを表示（透明背景、画像なし）
      if (clearBackFlag) {
        div.css({
          'background': 'rgba(0,0,0,0)'
        }).show().focus();
        div.find('img').hide();
      }

      // 操作ができないようにしておく
      var body = $('body');
      body.on('keydown.mcs-loading-keydown', function(event) {
        div.focus();
        event.preventDefault();
      });
      body.on('mousedown.mcs-loading-mousedown', function(event) {
        div.focus();
        event.preventDefault();
      });
    }
  },

  /**
   ******************************************************************************
   * @brief     ローディング非表示
   * @param
   * @return
   * @retval
   * @attention
   * @note      ローディングを非表示にする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  hide: function() {
    // ローディング表示カウントが0ならば
    if (--McsLoading.depth <= 0) {
      // ローディングを非表示に
      $('#mcs-loading-overlay').hide();

      // 操作を復元する
      var body = $('body');
      body.off('keydown.mcs-loading-keydown');
      body.off('mousedown.mcs-loading-mousedown');
    }
  }
};
