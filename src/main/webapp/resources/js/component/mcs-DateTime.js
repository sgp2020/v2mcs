﻿/**
 ******************************************************************************
 * @file        mcs-DateTime.js
 * @brief       日時入力に関する部品
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
 * @brief     日時入力コンポーネント
 * @param     {jQuery}  containerDiv 格納先のdiv要素
 * @param     {String}  parLblname   ラベル設定時のラベル名（未セットの場合はカレンダーアイコン表示）
 * @param     {Number}  parLblsize   ラベル設定時のラベル幅（pxで設定。未セットの場合は幅は自動調整）
 * @param     {Boolean} manualInput  手入力可否設定(デフォルト：false)
 * @param     {Object}  attr         inputに追加適用する属性
 * @param     {Object}  prop         inputに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note      日時入力コンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsDateTime = function(containerDiv, parLblname, parLblsize, manualInput, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, parLblname, parLblsize, manualInput, attr, prop);
};

// メソッド類
McsDateTime.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {String} parLblname   ラベル設定時のラベル名（未セットの場合はカレンダーアイコン表示）
   * @param     {Number} parLblsize   ラベル設定時のラベル幅（pxで設定。未セットの場合は幅は自動調整）
   * @param     {Object} attr         inputに追加適用する属性
   * @param     {Object} prop         inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note      初期化処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, parLblname, parLblsize, manualInput, attr, prop) {
    this.containerDiv = containerDiv;
    // 実際の要素を生成
    var div = $('<div class="form-group mcs-datetimepicker-wrapper">'
        + '<div class="input-group date mcs-datetimepicker">' + '</div>' + '</div>');
    var datePicker = div.find('.mcs-datetimepicker');
    var input = $('<input type="text" class="form-control mcs-datetimepicker-text" />');
    var label = $('<span class="input-group-addon mcs-datetimepicker-label"></span>');
    var clearLabel = $('<span class="input-group-addon mcs-datetimepicker-clear">'
        + '<span class="glyphicon glyphicon-trash"></span></span>');
    if (parLblname) {
      // ラベル名が指定されているときの設定
      label.text(parLblname);
      if (parLblsize) {
        label.css({
          'width': parLblsize + 'px',
          'max-width': parLblsize + 'px'
        });
      }
      // datePickerのdivに追加。
      datePicker.append(label).append(input).append(clearLabel);
    } else {
      var glyph = $('<span class="glyphicon glyphicon-calendar"></span>');
      label.append(glyph);
      // datePickerのdivに追加。
      datePicker.append(label).append(input).append(clearLabel);
    }

    // ブラウザの言語タイプ判定によるカレンダー表示言語切替え
    var langtype = window.mcsComponentLanguage;
    var locale;
    var format;
    if (langtype == 'ja') {
      // 日本語
      locale = 'ja';
      format = 'YYYY/MM/DD HH:mm:ss';
    } else {
      // 英語
      locale = 'en';
      format = 'YYYY/MM/DD HH:mm:ss';
    }

    // DatetimePicker化
    window.McsDateTimeMarginTop = $('.mcs-content').offset().top;
    datePicker.datetimepicker({
      locale: locale,
      format: format,
      widgetPositioning: {
        vertical: 'auto',
        horizontal: 'left'
      },
      ignoreReadonly: true
    // input要素がreadonlyでも表示できるようにするオプション
    });

    // テキストボックスのクリック時にも表示するように設定
    if (manualInput) {
        input.prop('readonly', false);
    } else {
        input.prop('readonly', true);
        input.on('click', function(e) {
          e.preventDefault();
          datePicker.data('DateTimePicker').toggle();
        });
    }

    // クリアボタンの動作を設定
    var self = this;
    clearLabel.off('click').on('click', function(e) {
      e.preventDefault();
      if (self.getEnabled()) {
        self.clear();
      }
    });

    if (attr)
      div.attr(attr);
    if (prop)
      div.prop(prop);
    this.div = div;
    this.input = input;
    this.clearLabel = clearLabel;
    this.datePicker = datePicker;
    this.datePickerText = input;

    // containerDivに追加する
    containerDiv.append(div);
  },

  /**
   ******************************************************************************
   * @brief     日付入力フォーマット指定
   * @param     {String} format 日付入力フォーマット文字列（例：'YYYY/MM/DD HH:mm:ss'）
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      日付入力のフォーマットを設定する。
   *             時間までしか入力できないようにする場合は、'YYYY/MM/DD HH:00:00'とすれば良い。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setFormat: function(format) {
    this.clear();
    this.datePicker.data('DateTimePicker').format(format);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     値設定
   * @param     {String} value 日付形式の文字列（yyyy/MM/dd HH:mm:ss）
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      値を設定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setValue: function(value) {
    this.datePicker.data('DateTimePicker').date(value);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     日時取得処理
   * @return    {String} 選択されていた日時の文字列
   * @retval
   * @attention
   * @note      選択された日時を文字列で返却する。何も選択されていないときはnullを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValue: function() {
    return this.getSelectedDateTime();
  },

  /**
   ******************************************************************************
   * @brief     日時取得処理
   * @return    {String} 選択されていた日時の文字列
   * @retval
   * @attention
   * @note      選択された日時を文字列で返却する。何も選択されていないときはnullを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getSelectedDateTime: function() {
    var inputText = this.datePickerText.val();
    if (inputText.length == 0) {
      return null;
    }
    return inputText;
  },

  /**
   ******************************************************************************
   * @brief     クリア処理
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      日時をクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.datePicker.data('DateTimePicker').clear();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     読み取り専用設定処理
   * @param     {Boolean} readonly true：読み取り専用、false：読み取り専用以外
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      読み取り専用の設定をする。
   *             読み取り専用にした場合、クリックしてもカレンダーは表示されない。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setReadonly: function(readonly) {
    if (readonly) {
      this.datePicker.data('DateTimePicker').ignoreReadonly(false);
      this.input.addClass('mcs-readonly');
    } else {
      this.datePicker.data('DateTimePicker').ignoreReadonly(true);
      this.input.removeClass('mcs-readonly');
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     分の加減ステップ量設定処理
   * @param     {int} value 加減ステップ量
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      分の加減ステップ量の設定をする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setStepping: function(value) {
    this.datePicker.data('DateTimePicker').stepping(value);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     時の加減ステップ量設定処理
   * @param     {int} value 加減ステップ量
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      分の加減ステップ量の設定をする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setSteppingHour: function(value) {
    this.datePicker.data('DateTimePicker').steppingHour(value);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     読み取り専用情報取得処理
   * @return    {Boolean} true：読み取り専用、false：読み取り専用以外
   * @retval
   * @attention
   * @note      読み取り専用かどうかを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getReadonly: function() {
    return this.input.hasClass('mcs-readonly');
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性設定処理
   * @param     {Boolean} enabled true：活性、false：非活性
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      活性・非活性を設定する。 setReadOnlyと同じ挙動となる。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnabled: function(enabled) {
    this.input.prop('disabled', !enabled);
    // Step4 2017_08_30
    if (enabled) {
      this.datePicker.children('span').removeClass('inactive');
    } else {
      this.datePicker.children('span').addClass('inactive');
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性取得処理
   * @return    {Boolean} true：活性、false：非活性
   * @retval
   * @attention
   * @note      活性・非活性を取得する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getEnabled: function() {
    return !this.input.prop('disabled');
  },

  /**
   ******************************************************************************
   * @brief     エラー追加
   * @param     {String} errorMessage エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note      エラーメッセージを追加する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addError: function(errorMessage) {
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    this.input.addClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief     エラー表示クリア
   * @retval
   * @attention
   * @note      エラー表示をクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearError: function() {
    this.containerDiv.find('.mcs-component-errorText').remove();
    this.input.removeClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief DateTime表示処理
   * @return {DateTime} このコンポーネント
   * @retval
   * @attention
   * @note DateTimeを表示する。
   * ----------------------------------------------------------------------------
   *  VER.        DESCRIPTION                                               AUTHOR
   *   ----------------------------------------------------------------------------
   ******************************************************************************
   */
  show: function() {
    this.containerDiv.show();
    return this;
  },

  /**
   ******************************************************************************
   * @brief DateTime非表示処理
   * @return {DateTime} このコンポーネント
   * @retval
   * @attention
   * @note DateTimeを非表示する。
   * ----------------------------------------------------------------------------
   *  VER.        DESCRIPTION                                               AUTHOR
   *   ----------------------------------------------------------------------------
   ******************************************************************************
   */
  hide: function() {
    this.containerDiv.hide();
    return this;
  },

  /**
   ******************************************************************************
   * @brief         jQueryオブジェクト取得
   * @return        {jQueryObj} input要素
   * @retval
   * @attention    なるべく使用しないこと
   * @note          input要素のjQueryオブジェクトを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getJq: function() {
    return this.div;
  },

  /**
   ******************************************************************************
   * @brief     指定要素に追加
   * @param     {jQueryObj} jQueryObj jQueryオブジェクト
   * @return    {McsDateTime} このコンポーネント
   * @retval
   * @attention
   * @note      指定された要素の配下に、本コンポーネントを追加する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  appendTo: function(jQueryObj) {
    jQueryObj.append(this.containerDiv);
    return this;
  },

  end: 'end' // 終端ダミー
};
