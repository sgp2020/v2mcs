/**
 ******************************************************************************
 * @file        mcs-ComboBox.js
 * @brief       コンボボックスを管理する部品
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
 * @brief    コンボボックスコンポーネント
 * @param    {jQuery} containerDiv 格納先のdiv要素
 * @param    {Object} attr inputに追加適用する属性
 * @param    {Object} prop inputに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note     コンボボックスコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsComboBox = function(containerDiv, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, attr, prop);
};
// メソッド類
McsComboBox.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param {jQuery} containerDiv 格納先のdiv要素
   * @param {Object} attr inputに追加適用する属性
   * @param {Object} prop inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note    初期化処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, attr, prop) {
    this.containerDiv = containerDiv;
    // 実際の要素を生成
    var input = $('<select class="input-large form-control combobox mcs-combobox"></select>');
    // input.selectpicker('render');
    if (attr)
      input.attr(attr);
    input.attr({
      name: 'hiddenItem'
    });

    if (prop)
      input.prop(prop);
    this.input = input;
    // containerDivに追加する
    containerDiv.append(input);
    // select要素を横幅100%で表示するために、containerDivにクラスを追加する
    containerDiv.addClass('mcs-combobox-container');

    // 保存用
    this.valueMap = [];
    this.addDataCount = 0;
  },

  /**
   ******************************************************************************
   * @brief      プルダウンリストの設定処理
   * @param {Array} comboList 追加するデータのリスト[{'text:テキスト', 'value:value値'}]
   * [[テキスト, value値]]に変更（矢野）
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note    設定する前に、一度リストをクリアする。末尾に追加したい場合は、addListを使用すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setList: function(comboList) {
    this.clearList();
    var textList = [];
    var valueList = [];
    for (var i = 0; i < comboList.length; i++) {
      textList.push(comboList[i].text);
      valueList.push(comboList[i].value);
    }
    this._setText(textList);
    this._setValue(valueList);
    return this;
  },

  /**
   ******************************************************************************
   * @brief      プルダウンリストを設定する。
   * @param {Array} getTextList 追加するデータのリスト['テキスト']
   * @return {Function} このコンポーネントのAddListの実行結果
   * @retval
   * @attention
   * @note    設定する前に、一度リストをクリアする。末尾に追加したい場合は、addListを使用すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setText: function(getTextList) {
    var textList = [''].concat(getTextList);
    return this._addText(textList);
  },

  /**
   ******************************************************************************
   * @brief    プルダウンリストに設定されたテキストにvalueを付与する機能
   * @param {Array} valueList 追加するデータのリスト['value値']
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setValue: function(valueList) {
    for (var i = 0; i < valueList.length; i++) {
      this.valueMap.push(valueList[i]);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief    プルダウンリストに追加する。
   * @param {Array} textList 追加するデータのリスト['テキスト']
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _addText: function(textList) {
    for (var i = 0; i < textList.length; i++) {
      var option = $('<option />');
      if (textList[i] != '') {
        option.val(this.addDataCount);
        this.addDataCount++;
        option.text(textList[i]);
      }
      this.input.append(option);
    }
    this.input.combobox('refresh');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    リストの内容クリア処理
   * @note     リストの内容をクリアする。
   * @return
   * @retval
   * @attention
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearList: function() {
    this.input.find('option').remove();
    this.valueMap = [];
    this.input.combobox('refresh');
  },

  /**
   ******************************************************************************
   * @brief    選択中データに設定したValue値返却処理
   * @return {*} Value値
   * @retval
   * @attention
   * @note     選択中データに設定したValue値を返す。
   *            自由入力（セレクトボックスに無いデータ）の場合はテキストを返却
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValue: function() {
    // テキストフィールドの設定値を取得
    var name = 'input[name="hiddenItem"]';
    var hiddenText = this.containerDiv.find(name).val();

    // ドロップダウンのリストから、入力値を検索
    var findFlg = false;
    var findValue = 0;
    this.input.find('option').each(function() {
      var option = $(this);
      if (option.text() === hiddenText) {
        findFlg = true;
        findValue = option.val();
      }
    });

    // 入力値が存在する場合、valueを返却。無い場合textを返却
    if(findFlg){
      return this.valueMap[findValue];
    }else{
      return hiddenText;
    }
  },

  /**
   ******************************************************************************
   * @brief    テキストボックスに値をセットする
   * @param {String} value セットする値
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note     テキストボックスに値をセットする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setValue: function(value) {
    // hiddenフィールド（実際の送信されるデータ）に設定
    var name = 'input[name="hiddenItem"]';
    var comboText = this.containerDiv.find(name).val(value);

    // ドロップダウンのリストから、入力値を検索
    var findFlg = false;
    var findValue = 0;
    this.input.find('option').each(function() {
      var option = $(this);
      if (option.text() === value) {
        findFlg = true;
        findValue = option.val();
      }
    });

    if(findFlg){
      // ドロップダウンのリストに要素が見つかった場合、該当の項目を選択状態にする
      this.input.val(findValue).combobox('refresh');
    }else{
      // ドロップダウンのリストに要素が見つからなかった場合、自由入力として設定
      this.containerDiv.find('input[type=text]').val(value);
      this.containerDiv.find('.combobox-container').addClass('combobox-selected');
    }

    return this;
  },

  /**
   ******************************************************************************
   * @brief    選択中データのテキスト返却処理
   * @return {String} comboTextテキスト
   * @retval
   * @attention
   * @note     選択中データのテキストを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getText: function() {
    var name = 'input[name="hiddenItem"]';
    var comboText = this.containerDiv.find(name).val();
    return comboText;
  },

  /**
   ******************************************************************************
   * @brief    初期選択状態決定処理
   *
   * @return {McsComboBox} このコンポーネント@param {Number} index 登録リストのうち、何番目かを指定（0～）
   * @retval
   * @attention
   * @note     初期選択状態を決定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setSelected: function(index) {
    this.input.val(index).combobox('refresh');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    初期化処理
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note     コンボボックスを初期化する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.input.combobox('clearElement');
    this.input.combobox('clearTarget');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    選択値変更イベント設定クリア処理
   * @param {Function} callback コールバック
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note    コンボボックスの選択値変更イベントを設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onChange: function(callback) {
    this.input.on('change.mcscombobox', callback);
    return this;
  },

  /**
   ******************************************************************************
   * @brief    選択値変更イベント設定クリア処理
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note    選択値変更イベントの設定をクリアする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearOnChange: function() {
    this.input.off('change.mcscombobox');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    活性・非活性設定処理
   * @param {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note    活性・非活性を設定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnabled: function(enabled) {
    if (enabled)
      this.input.combobox('enable');
    else
      this.input.combobox('disable');
    this.input.prop('disabled', !enabled);
    this.input.combobox('refresh');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    活性・非活性取得処理
   * @return {Boolean} 活性ならtrue、非活性ならfalse
   * @retval
   * @attention
   * @note    活性・非活性を取得する。
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
   * @brief    最大長を設定する
   * @param {Number} maxLength 最大文字列長
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note    最大長を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setMaxLength: function(maxlength) {
    if (maxlength)
      this.containerDiv.find('.input-group > input').attr('maxlength', maxlength);
    else
      this.input.find('.input-group > input').removeAttr('maxlength');
    return this;
  },

  /**
   ******************************************************************************
   * @brief    エラー追加処理
   * @param {String} errorMessage エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note    エラーを追加する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addError: function(errorMessage) {
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    this.containerDiv.find('.combobox').addClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief    エラー表示クリア処理
   * @return
   * @retval
   * @attention
   * @note    エラー表示をクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearError: function() {
    this.containerDiv.find('.mcs-component-errorText').remove();
    this.containerDiv.find('.combobox').removeClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief    jQueryオブジェクトを返却する処理
   * @return {jQueryObj} table要素
   * @retval
   * @attention
   * @note    jQueryオブジェクトを返す （注意：なるべく使用しないこと）
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
   * @brief    指定のjQueryObjの配下にこの部品を追加する。
   * @param {jQueryObj} jQueryObj jQueryオブジェクト
   * @return {McsComboBox} このコンポーネント
   * @retval
   * @attention
   * @note
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
