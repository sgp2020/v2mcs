/**
 ******************************************************************************
 * @file        mcs-ButtonGroup.js
 * @brief       ボタンイベントを管理する部品
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
 * @brief ボタンコンポーネント機能
 * @param {jQuery} containerDiv 格納先のdiv要素
 * @param {String} text ボタンのテキスト
 * @param {Object} attr inputに追加適用する属性
 * @param {Object} prop inputに追加適用する属性
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 *******************************************************************************
 */
var McsButtonGroup = function(containerDiv, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, attr, prop);
};

// メソッド類
McsButtonGroup.prototype = {

  /**
   ******************************************************************************
   * @brief 初期化処理
   * @param {jQuery} containerDiv 格納先のdiv要素
   * @param {Object} attr inputに追加適用する属性
   * @param {Object} prop inputに追加適用する属性
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, attr, prop) {
    this.containerDiv = containerDiv;
    if (attr)
      this.containerDiv.attr(attr);
    if (prop)
      this.containerDiv.prop(prop);
    this.buttonMap = [];
    this.addDataCount = 0;
  },

  /**
   ******************************************************************************
   * @brief クリックイベントの初期化処理
   * @param {jQueryObj} input ボタン
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initClickEvent: function(input) {
    input.input.on('click', function() {
      var $this = $(this);
      $this.prop('disabled', true);
      $this.nextAll().prop('disabled', false);
      $this.prevAll().prop('disabled', false);
    });
  },

  /**
   ******************************************************************************
   * @brief ボタン追加処理
   * @param {String} text ボタンのテキスト
   * @param {boolan}isRadio ラジオボタンのような挙動を行うなら true、しないなら false
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  add: function(text, isRadio) {
    // 実際の要素を生成
    var attr = {
      class: 'btn btn-default mcs-button button-group'
    };
    var mcsButton = new McsButton(this.containerDiv, text, attr);
    this.buttonMap.push(mcsButton);
    if (isRadio)
      this._initClickEvent(mcsButton);
    this.addDataCount++;
    return this;
  },

  /**
   ******************************************************************************
   * @brief クリック時イベント設定処理
   * @param {Number} index 番号に対応するボタン
   * @param {Function} callback クリック時のコールバック
   * @return {Object} indexで指定したボタンにクリックイベントの付与
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onClick: function(index, callback) {
    var button = this.buttonMap[index].input;
    return button.on('click', callback);
  },

  /**
   ******************************************************************************
   * @brief クリック時イベント設定クリア処理
   * @param {Number} index 番号に対応するボタン
   * @return {Object} indexで指定したボタンからクリックイベントを除去
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearOnClick: function(index) {
    var button = this.buttonMap[index].input;
    return button.off('click');
  },

  /**
   ******************************************************************************
   * @brief 活性・非活性設定処理
   * @param {Number} index 番号に対応するボタン
   * @param {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return {McsButtonGroup} このコンポーネント
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnabled: function(index, enabled) {
    this.buttonMap[index].input.prop('disabled', !enabled);
    return this;
  },

  /**
   ******************************************************************************
   * @brief ボタン表示処理
   * @param {Number} index 番号に対応するボタン
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  show: function(index) {
    this.buttonMap[index].input.show();
    return this;
  },

  /**
   ******************************************************************************
   * @brief ボタン非表示処理
   * @param {Number} index 番号に対応するボタン
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note
   * ---------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  hide: function(index) {
    this.buttonMap[index].input.hide();
    return this;
  },

  /**
   ******************************************************************************
   * @brief ボタン表示機能
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  allShow: function() {
    for (var i = 0; i < this.buttonMap.length; i++)
      this.buttonMap[i].input.show();
    return this;
  },

  /**
   ******************************************************************************
   * @brief ボタン非表示機能
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  allHide: function() {
    for (var i = 0; i < this.buttonMap.length; i++)
      this.buttonMap[i].input.hide();
    return this;
  },

  /**
   ******************************************************************************
   * @brief input要素のjQueryオブジェクトを返す。
   * @return {McsButtonGroup} このコンポーネント
   * @retval
   * @attention
   * @note なるべく使用しないこと
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getJq: function() {
    return this.input;
  }
};
