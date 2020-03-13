﻿/**
 ******************************************************************************
 * @file        mcs-TextBox.js
 * @brief       テキストボックスに関する部品
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
 * @brief     テキストボックスコンポーネント
 * @param     {jQuery} containerDiv 格納先のdiv要素
 * @param     {String} type         inputタグのtype。省略時はtext。
 * @param     {Object} attr         inputに追加適用する属性
 * @param     {Object} prop         inputに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note      テキストボックスコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsTextBox = function(containerDiv, type, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, type, attr, prop);
};
// メソッド類
McsTextBox.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {String} type         inputタグのtype。省略時はtext。
   * @param     {Object} attr         inputに追加適用する属性
   * @param     {Object} prop         inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note      初期化する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, type, attr, prop) {
    this.containerDiv = containerDiv;
    // 実際の要素を生成
    //var input = $('<input type="text" class="form-control mcs-textbox">');   
    var input = $('<input type="text" name="colorText" class="form-control mcs-textbox">');  //20191224 Song ADD
    if (type) {
      if (type == 'number')
        input.addClass('mcs-textbox-number');
      else
        input.attr('type', type);
    }
    if (attr)
      input.attr(attr);
    if (prop)
      input.prop(prop);
    this.input = input;
    // containerDivに追加する
    containerDiv.append(input);
  },

  /**
   ******************************************************************************
   * @brief     クリック時イベントの設定
   * @param     {Function} callback クリックしたときのコールバック
   * @return    {McsTextBox}  このコンポーネント
   * @retval
   * @attention
   * @note      クリック時イベントを設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onClick: function(callback) {
    this.input.on('click', callback);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     値を設定
   * @param     {String} val  値（テキスト）
   * @return    {McsTextBox}  このコンポーネント
   * @retval
   * @attention
   * @note      値を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setValue: function(val) {
    this.input.val(val);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     入力値を取得
   * @param
   * @return    {String} 入力されているテキスト
   * @retval
   * @attention
   * @note      入力値を取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValue: function() {
    return this.input.val();
  },

  /**
   ******************************************************************************
   * @brief     入力値のクリア
   * @param
   * @return    {McsTextBox} このコンポーネント
   * @retval
   * @attention
   * @note      入力値をクリアして空にする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.input.val('');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性を設定
   * @param     {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return    {McsTextBox} このコンポーネント
   * @retval
   * @attention
   * @note      活性・非活性を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnabled: function(enabled) {
    this.setReadonly(!enabled);
    this.input.prop('disabled', !enabled);
    return this;
  },
  
  
  /**
   ******************************************************************************
   * @brief     disabledを設定
   * @param     {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return    {McsTextBox} このコンポーネント
   * @retval
   * @attention
   * @note      disabledを設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * 20191224 Song Add
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
   setDisabled: function(enabled) {
	  if(enabled == true){
		  this.input.attr("disabled","disabled");
		  return this;
	  }
   },

  /**
   ******************************************************************************
   * @brief     活性・非活性を取得
   * @param
   * @return    {Boolean} 活性ならtrue、非活性ならfalse
   * @retval
   * @attention
   * @note      活性・非活性を取得する
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
   * @brief     読み取り専用を設定
   * @param     {Boolean} readonly 読み取り専用ならtrue
   * @return    {McsTextBox} このコンポーネント
   * @retval
   * @attention
   * @note      読み取り専用を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setReadonly: function(readonly) {
    this.input.prop('readonly', readonly);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     読み取り専用かどうかを取得
   * @param
   * @return    {Boolean} 読み取り専用ならtrue
   * @retval
   * @attention
   * @note      読み取り専用かどうかを取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getReadonly: function() {
    return this.input.prop('readonly');
  },

  /**
   ******************************************************************************
   * @brief     maxlength（最大長）を設定
   * @param     {Number} maxlength 最大長
   * @return    {McsTextBox} このコンポーネント
   * @retval
   * @attention
   * @note      maxlength（最大長）を設定する。 type=numberの場合、maxlengthは効かないため注意。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setMaxLength: function(maxlength) {
    if (maxlength)
      this.input.attr('maxlength', maxlength);
    else
      this.input.removeAttr('maxlength');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     エラーを追加
   * @param     {String} errorMessage エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note      エラーを追加する
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
   * @brief     エラー表示をクリア
   * @param
   * @return
   * @retval
   * @attention
   * @note      エラー表示をクリアする
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
   * @brief     貼り付け禁止を設定
   * @param     {Boolean} pasteBan 貼り付け禁止ならtrue、貼り付け可ならfalse
   * @return
   * @retval
   * @attention
   * @note      貼り付けを禁止する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setPasteBan: function(pasteBan) {
      this.input.attr('onpaste', 'return ' + !pasteBan);
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスに付随するチェックボックス追加
   * @param     {jQuery} compId       格納先に付与するid
   * @param     {String} text         ラベルのテキスト
   * @param     {Object} attr         inputに追加適用する属性
   * @param     {Object} prop         inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note      テキストボックスに付随するチェックボックスを追加する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addCheckBox: function(compId, text, attr, prop) {
      var divId = $('<div id=' + compId + '></div>');
      this.containerDiv.append(divId);
      var checkBox = new McsCheckBox(divId, text, attr, prop);
      this.checkBox = checkBox;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスに付随するチェックボックス取得
   * @param
   * @return    {McsCheckBox}         付随するチェックボックス
   * @retval
   * @attention
   * @note      テキストボックスに付随するチェックボックスを取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getCheckBox: function() {
      var checkBox = this.checkBox;
      return checkBox;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスのタイプ設定
   * @param     {String} type テキストボックスのタイプを指定
   * @return
   * @retval
   * @attention
   * @note      貼り付けを禁止する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setTextBoxType: function(type) {
      this.input.attr('type', type);
  },

  /**
   ******************************************************************************
   * @brief     jQueryオブジェクトを取得
   * @param
   * @return    {jQueryObj} jQueryオブジェクト
   * @retval
   * @attention
   * @note      jQueryオブジェクトを返す。 （注意：なるべく使用しないこと）
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getJq: function() {
    return this.input;
  },
  
  
  
  /**
   ******************************************************************************
   * @brief     changeBgColorを設定
   * @param     Id
   * @param     color
   * @retval
   * @attention
   * @note      changeBgColorを設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * 20191224 Song Add
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  changeBgColor:function(Id,color){ 
	   $("#" + Id).find('input').css('background-color', color);
	   //$("#" + Id).find("#colorText").css('background-color', color); //这行语句没成功
  },

  end: 'end'
};
