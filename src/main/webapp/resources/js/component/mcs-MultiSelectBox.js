/**
 ******************************************************************************
 * @file        mcs-MultiSelectBox.js
 * @brief       マルチセレクトボックスに関する部品
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
 * @brief     複数選択式セレクトボックスコンポーネント
 * @param     {jQuery} containerDiv 格納先のdiv要素
 * @param     {Object} attr inputに追加適用する属性
 * @param     {Object} prop inputに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsMultiSelectBox = function(containerDiv, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, attr, prop);
};

// メソッド類
McsMultiSelectBox.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {Object} attr inputに追加適用する属性
   * @param     {Object} prop inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  init: function(containerDiv, attr, prop) {
    this.containerDiv = containerDiv;
    // 実際の要素を生成
    var inputWrapper = $('<div class="mcs-multiselectpicker-input-wrapper"></div>');
    var input = $('<select class="multiselectpicker mcs-multiselectpicker" multiple="multiple" ></select>');
    var output = $('<div class="multiselect-container"></div>');
    if (attr)
      input.attr(attr);
    if (prop)
      input.prop(prop);
    this.inputWrapper = inputWrapper;
    this.input = input;
    this.output = output;
    // containerDivに追加する
    inputWrapper.append(input);
    containerDiv.append(inputWrapper);
    containerDiv.append(output);
    // select要素を横幅100%で表示するために、containerDivにクラスを追加する
    containerDiv.addClass('mcs-multiselectbox-container');

    this.initButtonEventFlag = false;
    this.refreshMulti();
    this.checkedList = [];
    this.selectList = [];
  },

  /**
   ******************************************************************************
   * @brief     プルダウンリストを設定
   * @param     {Array} selectList 追加するデータのリスト['表示フラグ','選択済みフラグ','コンテンツ']
   * @param     {Array} opt 追加時に表示するテキストのキー
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note      設定する前に、一度リストをクリアする。末尾に追加したい場合は、addListを使用すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setList: function(selectList, opt) {
    this.clearList();
    this.addList(selectList, opt);
    this._setValue(selectList);
    this.checkedList = this._getNewCheckedList();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     プルダウンリストに追加する
   * @param     {Object} selectList 追加するデータのオブジェクト配列['コンテンツ']
   * @param     {Array} opt 追加時に表示するテキストのキー
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  addList: function(selectList, opt) {
    for (var i = 0; i < selectList.length; i++) {
      if (!selectList[i].disp)
        continue;

      var option = $('<option />');
      // インデックス番号としてval に通し番号を登録
      option.val(i);
      var text = this._getListText(selectList[i].data, opt);
      option.text(text);
      option.attr('title', text.replace(/"/g, '&quot;'));
      option.attr('mcs-list-id', i);
      if (selectList[i].selected)
        option.attr('selected', true);

      this.input.append(option);
    }
    this.refreshMulti();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     プルダウンリストに追加するテキストを取得
   * @param     {Object} data 追加するデータのオブジェクト['コンテンツ']
   * @param     {Array} opt 追加時に表示するテキストのキー
   * @return    {String} 表示用の文字列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getListText: function(data, opt) {
    var text = '';
    for (var i = 0; i < opt.length; i++) {
      if (text == '')
        text += data[opt[i]];
      else
        text += '[' + data[opt[i]] + ']';
    }
    return text;
  },

  /**
   ******************************************************************************
   * @brief     リストの内容をクリア
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
  clearList: function() {
    this.input.find('option').remove();
    this.selectList = [];
    this.checkedList = [];
    this.refreshMulti();
  },

  /**
   ******************************************************************************
   * @brief     選択中データのValueを配列で返す
   * @param
   * @return    {Array} 登録したリストのうち、選択されているオブジェクトのdataプロパティ
   * @retval
   * @attention
   * @note      選択されていないときは空配列を返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValue: function() {
    var selectVal = this.input.val();
    var valList = [];
    if (selectVal == null) {
      return valList;
    }
    for (var i = 0; i < selectVal.length; i++) {
      valList.push(this.selectList[selectVal[i]]);
    }
    return valList;
  },

  /**
   ******************************************************************************
   * @brief     選択中データのテキストを配列で返す
   * @param
   * @return    {Array} テキスト
   * @retval
   * @attention
   * @note      選択されていないときは空配列を返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getText: function() {
    var selectText = [];
    this.input.find('option:selected').each(function() {
      selectText.push($(this).text());
    });
    return selectText;
  },

  /**
   ******************************************************************************
   * @brief     選択中データのValue属性値とテキストのセットを配列で返す
   * @param
   * @return    {Array} ['テキスト', 'Value属性値']
   * @retval
   * @attention
   * @note      選択されていないときは空配列を返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getAll: function() {
    var selectedVal = this.getValue();
    var selectedText = this.getText();
    var array = [];
    for (var i = 0; i < selectedText.length; i++) {
      array.push({
        text: selectedText[i],
        value: selectedVal[i]
      });
    }
    return array;
  },

  /**
   ******************************************************************************
   * @brief     値を設定する。 指定の値をチェック状態にする
   * @param     {Object} value 値
   * @param     {Boolean} refreshFlag ライブラリに反映するか
   *                       true, undefined: 反映する、 false: 反映しない
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setValue: function(value, refreshFlag) {
    var self = this;
    var selectList = this.selectList;
    this.inputWrapper.find('option').each(function() {
      var $this = $(this);
      var listId = $this.attr('mcs-list-id');
      var v = selectList[parseInt(listId, 10)];
      if (self._setValueCompare(v, value)) {
        $this.prop('selected', true);
      }
    });
    // ライブラリに反映
    if (refreshFlag === undefined || refreshFlag) {
      this.inputWrapper.multiselect('refresh');
    }

    return this;
  },

  /**
   ******************************************************************************
   * @brief     二つの値を比較する
   * @param     {Object} v1 値１
   * @param     {Object} v2 値２
   * @return    {Boolean} 同じ値ならture、違う値ならfalse
   * @retval
   * @attention
   * @note      setValueの内部処理用
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _setValueCompare: function(v1, v2) {
    var key;
    for (key in v2) {
      if (v1[key] !== v2[key]) {
        return false;
        break;
      }
    }
    return true;
  },

  /**
   ******************************************************************************
   * @brief     Value属性値を同時に複数設定する
   * @param     {Array.<Object>} values 値のリスト
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setValues: function(values) {
    for (var i = 0; i < values.length; i++) {
      this.setValue(values[i], false);
    }
    this.inputWrapper.multiselect('refresh');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     Value属性値を設定する。同時に複数設定したい場合は配列で渡す。
   * @param     {Array} selectList Value属性値
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note      内部処理用
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _setValue: function(selectList) {
    for (var i = 0; i < selectList.length; i++) {
      this.selectList.push(selectList[i].data);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     選択を初期化
   * @param
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clear: function() {
    this.inputWrapper.multiselect('uncheckAll');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     全項目を選択
   * @param
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  checkAll: function() {
    this.inputWrapper.multiselect('checkAll');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     全項目を選択のコールバックを設定
   * @param
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onCheckAll: function(callback) {
    this.inputWrapper.bind('multiselectcheckall', function() {
      callback();
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     全項目を選択解除のコールバック設定
   * @param
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onUnCheckAll: function(callback) {
    this.inputWrapper.bind('multiselectuncheckall', function() {
      callback();
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     マルチセレクトボックスの選択値変更イベントを設定
   * @param     {Function} callback コールバック
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onChange: function(callback) {
    this.inputWrapper.change(callback);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     マルチセレクトボックスのクリックイベントを設定
   * @param     {Function} callback コールバック
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onClick: function(callback) {
    this.inputWrapper.on('multiselectclick', function() {
      callback();
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性を設定
   * @param     {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return    {McsMultiSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setEnabled: function(enabled) {
    if (enabled)
      this.inputWrapper.multiselect('enable');
    else
      this.inputWrapper.multiselect('disable');
    return this;
  },

  /**
   ******************************************************************************
   * @brief     マルチセレクトボックスの更新を行う
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
  refreshMulti: function() {
    this.inputWrapper.multiselect({
      checkAllText: McsMultiSelectBox.language.menu.checkAll,
      uncheckAllText: McsMultiSelectBox.language.menu.uncheckAll,
      noneSelectedText: McsMultiSelectBox.language.menu.noneSelected,
      selectedText: '# ' + McsMultiSelectBox.language.menu.isSelected,
      selectedList: 0,
      minWidth: 'auto',
      appendTo: this.output,
      position: {
        my: 'left top',
        at: 'left bottom'
      }
    });
    this.inputWrapper.multiselect('refresh');
    // マルチセレクトボックスの横幅指定
    if (this.initButtonEventFlag === false) {
      this.initButtonEventFlag = true;
      var self = this;
      var button = self.inputWrapper.multiselect('getButton');
      button.on('click', function() {
        var width = $(this).outerWidth();
        self.inputWrapper.multiselect('option', {
          menuWidth: width + 'px'
        });
      });
    }
  },

  /**
   ******************************************************************************
   * @brief     変更があったインデックス番号を取得
   * @param
   * @return    {Array} changedIndex インデックス番号
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getChangedIndex: function() {
    var newCheckedList = this._getNewCheckedList();
    var changedIndex;
    if (this.checkedList.length < newCheckedList.length)
      changedIndex = this._getDifference(this.checkedList, newCheckedList);
    else
      changedIndex = this._getDifference(newCheckedList, this.checkedList);

    this.checkedList = newCheckedList;
    return changedIndex;
  },

  /**
   ******************************************************************************
   * @brief     マルチセレクトボックス操作前のchecked項目のインデックス番号の取得
   * @param
   * @return    {Array} this.checkedList checked項目のvalue値配列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getCheckedList: function() {
    return this.checkedList;
  },

  /**
   ******************************************************************************
   * @brief     マルチセレクトボックス操作後のchecked項目のインデックス番号の取得
   * @param
   * @return    {Array} this.checkedList checked項目のvalue値配列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getNewCheckedList: function() {
    var newCheckedList = this.inputWrapper.multiselect('getChecked').map(function() {
      return this.value;
    }).get();
    return newCheckedList;
  },

  /**
   ******************************************************************************
   * @brief     インデックス番号を格納した配列を比較し、状態の変化した番号を取得
   * @param     {Array} small インデックス番号を格納した配列
   * @param     {Array} large インデックス番号を格納した配列
   * @return    {String} インデックス番号
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getDifference: function(small, large) {
    var checked = [];
    for (var i = 0; i < large.length; i++) {
      if ($.inArray(large[i], small) == -1) {
        checked.push(large[i]);
      }
    }
    return checked;
  },

  /**
   ******************************************************************************
   * @brief     エラーを追加
   * @param     {String} errorMessage エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  addError: function(errorMessage) {
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    this.containerDiv.find('button.ui-multiselect').addClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief     エラー表示をクリア
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
  clearError: function() {
    this.containerDiv.find('.mcs-component-errorText').remove();
    this.containerDiv.find('button.ui-multiselect').removeClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief     jQueryオブジェクトを返す
   * @param
   * @return    {jQueryObj} table要素
   * @retval
   * @attention なるべく使用しないこと
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getJq: function() {
    return this.input;
  }

};
