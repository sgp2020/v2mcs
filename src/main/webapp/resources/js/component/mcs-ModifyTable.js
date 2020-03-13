/**
 ******************************************************************************
 * @file        mcs-ModifyTable.js
 * @brief       追加・更新画面用の編集テーブルに関する部品
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
 * @brief     追加・更新画面用の編集テーブル
 * @param     {String} containerDiv 格納先のdiv要素
 * @param     {String} tableCompId テーブルコンポーネントID
 * @param     {String} optAttr テーブルに追加適用する属性
 * @param     {String} optProp テーブルに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsModifyTable = function(containerDiv, tableCompId, optAttr, optProp) {
  this.init(containerDiv, tableCompId, optAttr, optProp);
};
McsModifyTable.prototype = {

  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param     {String} containerDiv 格納先のdiv要素
   * @param     {String} tableCompId テーブルコンポーネントID
   * @param     {String} optAttr テーブルに追加適用する属性
   * @param     {String} optProp テーブルに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  init: function(containerDiv, tableCompId, optAttr, optProp) {
    this.containerDiv = containerDiv;
    this.tableCompId = tableCompId;
    this.optAttr = optAttr;
    this.optProp = optProp;

    // メインのテーブル、ヘッダ部、ボディ部を作っておく
    this.wrapperDiv = $('<div></div>');

    this.clearDiv = $('<div style="clear:both"></div>');
    this.containerDiv.append(this.wrapperDiv);

    this.wrapperTable = $('<table class="mcs-modifytable-wrapper"><tr></tr></table>');
    this.containerDiv.append(this.wrapperTable);

    // ヘッダ部に表示切替メニューを配置する
    this.dispMenuDiv = this._createDispMenu();
    this.wrapperDiv.append(this.dispMenuDiv);

    this.tableList = [];
    this._newTable();
    // テーブル中のコンポーネントのリスト
    this.componentList = [];
  },

  /**
   ******************************************************************************
   * @brief     新しいテーブル要素を生成し、現在のテーブルとする
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _newTable: function() {
    this.currentTable = $('<table class="table mcs-modifytable"></table>');
    if (this.tableList.length > 0) {
      // すでにテーブルがある場合は、ヘッダー部分を複製する
      var oldTable = this.tableList[0];
      // Step4 2017_08_18
      this.currentHeaderNode = oldTable.find('thead.mcs-modifytable-header').clone();
    } else {
      // 最初のテーブルの場合は空のヘッダを生成（あとでsetHeaderで設定される）
      // Step4 2017_08_18
      this.currentHeaderNode = $('<thead class="mcs-modifytable-header"></thead>');
    }
    this.currentBodyNode = $('<tbody></tbody>');
    this.currentTable.append(this.currentHeaderNode).append(this.currentBodyNode);

    this.tableList.push(this.currentTable);

    if (this.optAttr)
      this.currentTable.attr(this.optAttr);
    if (this.optProp)
      this.currentTable.prop(this.optProp);

    var td = $('<td></td>').append(this.currentTable);
    this.wrapperTable.find('tr:first').append(td);

    return this.currentTable;
  },

  /**
   ******************************************************************************
   * @brief     ヘッダを設定
   * @param     {Array} headerData ヘッダの名前の配列
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setHeader: function(headerData) {
    // ヘッダー
    var tr = $('<tr></tr>');
    for (var i = 0, length = headerData.length; i < length; i++) {
      var th = $('<th></th>').text(headerData[i]);
      tr.append(th);
    }
    this.currentHeaderNode.append(tr);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     データを1行追加
   * @param     {Object} data 列情報
   * @param     {Object} dispInfo データの表示情報
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * 列情報はオブジェクト形式で、以下のような情報が格納されているものとする。
   *
   * <pre>
   * {
   *   name     : 列の名前(ID)。値を取得・設定するときなどはこの名前を用いて操作する。
   *              また、この名前はTABLE_COMP_CONFIGテーブルのCOL_IDに対応する。
   *   type     : 入力欄の種類。以下の種類がある。
   *                text ... テキストボックス（デフォルト）
   *                number ... 数値テキストボックス
   *                boolean ... チェックボックス
   *                select ... セレクトボックス
   *                comp ... その他の部品
   *                password ... パスワード入力テキストボックス
   *                br ... 改行を挿入。改行して次のテーブルへ移行する。
   *   enabled  : 入力欄の活性非活性。入力欄の部品がsetEnabledを実装している場合のみ有効。
   *   value    : 入力欄の初期値。入力欄の部品がsetValueを実装している場合のみ有効。
   *   maxLength: 入力欄のmaxlength。入力欄の部品がsetMaxLengthを実装している場合のみ有効。
   *   readonly : 入力欄のreadonly。入力欄の部品がsetReadonlyを実装している場合のみ有効。
   *   options  : typeがselectの場合のみ有効。セレクトボックスの選択肢の配列。
   *              McsSelectBox.setListにそのまま受け渡すため、形式についてはそちらを参照。
   *   comp     : typeがcompの場合のみ有効。表示する部品のインスタンスを設定。
   *              appendToメソッドが実装されていない部品は設定できないため注意。
   *   password : typeがpasswordの場合のみ有効。表示する部品のインスタンスを設定。
   *              アスタリスク表記するテキストボックスを生成する。
   *   required : 必須入力表示フラグ。必須入力欄の場合はtrue。
   *              falseまたは未指定の場合は何も表示されない。
   * }
   * </pre>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _addData: function(data, dispInfo) {
    if (data.type == 'br') {
      // 改行指示
      this._newTable();
    } else {
      // その他の場合は入力行を追加する
      var tr = $('<tr></tr>');
      tr.attr('name', data.name);

      // 非表示行の場合は非表示に
      if (dispInfo.itemDisplay != 1) {
        tr.hide();
      }

      // 名前（列１）のラベルを設定
      var nameNode = $('<th class="mcs-modifytable-name"></th>');
      var nameSpan = $('<span></span>').text(dispInfo.itemName).appendTo(nameNode);
      // 必須入力欄の場合はアスタリスクを表示
      if (data.required) {
        nameSpan.addClass('mcs-required');
      }

      // 値（列２）の要素を生成。type指定により生成する部品を切り替える
      var valueNode = $('<td class="mcs-modifytable-value"></td>');
      var input;
      switch (data.type) {
        case 'number':
          input = this._createNumberBox(data, valueNode);
          break;
        case 'boolean':
          input = this._createCheckBox(data, valueNode);
          break;
        case 'select':
          input = this._createSelectBox(data, valueNode);
          break;
        case 'comp':
          input = this._createOtherComp(data, valueNode);
          break;
        case 'password':
          input = this._createPassText(data, valueNode);
          break;
        default:
          input = this._createTextBox(data, valueNode);
          break;
      }

      // テーブル部品をもつvalueにはクラスを付加
      if (valueNode.find('#mcs-table').length > 0) {
        valueNode.addClass('has-table');
      }

      // 表示切替メニューに項目を追加する
      this._addDispMenuList(tr, data.name, dispInfo);

      this.componentList.push({
        name: data.name,
        component: input
      });
      tr.append(nameNode).append(valueNode);
      this.currentBodyNode.append(tr);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスの要素を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {McsTextBox} テキストボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createTextBox: function(data, valueNode) {
    var textBox = new McsTextBox(valueNode, 'text', {
      name: data.name
    });
    textBox.setValue(data.value);
    if (data.maxLength)
      textBox.setMaxLength(data.maxLength);
    textBox.setEnabled(data.enabled);
    valueNode.addClass('mcs-modifytable-input-text');
    return textBox;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスの要素（パスワード）を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {McsTextBox} テキストボックス
   * @attention
   * @note      パスワード入力用テキストボックス要素を生成する。
   * ----------------------------------------------------------------------------
   *  VER.        DESCRIPTION                                               AUTHOR
   *   ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _createPassText: function(data, valueNode) {
    var passText = new McsTextBox(valueNode, 'password', {
      name: data.name
    });
    passText.setValue(data.value);
    if (data.maxLength)
      passText.setMaxLength(data.maxLength);
    passText.setEnabled(data.enabled);
    valueNode.addClass('mcs-modifytable-input-text');
    return passText;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスの要素（数値）を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {McsTextBox} テキストボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createNumberBox: function(data, valueNode) {
    var textBox = new McsTextBox(valueNode, 'number', {
      name: data.name
    });
    this._setOptions(textBox, data);
    valueNode.addClass('mcs-modifytable-input-number');
    return textBox;
  },

  /**
   ******************************************************************************
   * @brief     チェックボックスの要素を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {McsCheckBox} チェックボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createCheckBox: function(data, valueNode) {
    var checkBox = new McsCheckBox(valueNode, '', {
      name: data.name
    });
    this._setOptions(checkBox, data);
    valueNode.addClass('mcs-modifytable-input-checkbox');
    return checkBox;
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックスの要素を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {McsSelectBox} セレクトボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createSelectBox: function(data, valueNode) {
    var selectBox = new McsSelectBox(valueNode);
    selectBox.setList(data.options);
    this._setOptions(selectBox, data);
    return selectBox;
  },

  /**
   ******************************************************************************
   * @brief     その他のコンポーネントの要素を生成
   * @param     {Object} data 列情報
   * @param     {jQueryObj} valueNode コンポーネント配置先
   * @return    {Object} その他のコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createOtherComp: function(data, valueNode) {
    var comp = data.comp;
    this._setOptions(comp, data);
    if (comp.appendTo) {
      comp.appendTo(valueNode);
    } else {
      // MEMO このアラートが表示される場合は実装誤りであり、
      // 正常に実装されている場合は表示されない。そのため、日本語英語切替など不要。
      alert('appendToメソッドが実装されていないため、ModifyTableに設定できません。' + 'ModifyTableに表示する部品には appendTo メソッドが必要です。'
          + '※このアラートが表示される場合、実装誤りです。実行時エラーではありません。');
    }
    return comp;
  },

  /**
   ******************************************************************************
   * @brief     列情報に設定されているオプション類をコンポーネントに設定
   * @param     {Object} comp コンポーネント
   * @param     {Object} data 列情報
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _setOptions: function(comp, data) {
    if (comp.setValue && data.value !== null && data.value !== undefined) {
      comp.setValue(data.value);
    }
    if (comp.setEnabled && data.enabled !== null && data.enabled !== undefined) {
      comp.setEnabled(data.enabled);
    }
    if (comp.setReadonly && data.readonly !== null && data.readonly !== undefined) {
      comp.setReadonly(data.readonly);
    }
    if (comp.setEditable && data.editable !== null && data.editable !== undefined) {
      comp.setEditable(data.editable);
    }
    if (comp.setMaxLength && data.maxLength !== null && data.maxLength !== undefined) {
      comp.setMaxLength(data.maxLength);
    }
  },

  /**
   ******************************************************************************
   * @brief     データ行をまとめて設定
   * @param     {Array} dataList 列情報のリスト。列情報の詳細については_addDataを参照。
   * @param     {McsModifyTable} このコンポーネント
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setData: function(dataList) {
    this._getDispState(dataList);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     表示情報を取得
   * @param     {Array} dataList 複数行分のデータのリスト
   * @return
   * @retval
   * @attention
   * @note      取得に成功した際にデータを追加する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getDispState: function(dataList) {
    var self = this;
    var i;
    var length;
    callAjax(getUrl('/Com/McsModifyTable/GetDispState'), this.tableCompId, false, function(jsonObj) {
      self.dispInfoList = jsonObj.body;
      for (i = 0, length = dataList.length; i < length; i++) {
        var dispInfo = self._getMatchDispState(dataList[i].name);
        self._addData(dataList[i], dispInfo);
      }
      for (i = 0, length = self.tableList.length; i < length; i++) {
        self._hideEmptyTable(self.tableList[i]);
      }
      // Modifyテーブルセッティング後のコールバックを実行する
      if (self.onAfterSettingCallback) {
        self.onAfterSettingCallback();
      }
    });
  },

  /**
   ******************************************************************************
   * @brief     与えられた名前に対応するデータの表示情報を取得
   * @param     {String} key データの名前
   * @return    {Object} 表示情報
   * @retval
   * @attention
   * @note      keyに対するデータが見つからない場合はnullを返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _getMatchDispState: function(key) {
    for (var i = 0, length = this.dispInfoList.length; i < length; i++) {
      if (this.dispInfoList[i].itemId == key) {
        return this.dispInfoList[i];
      }
    }
    return null;
  },

  /**
   ******************************************************************************
   * @brief     値、エラーメッセージ等をクリア
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clearAll: function() {
    this.clearValues();
    this.clearErrors();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     値をすべてクリアする。ただし、非活性の項目はクリアしない
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note       clearValueを呼ぶ
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clear: function() {
    return this.clearValues();
  },

  /**
   ******************************************************************************
   * @brief     値をすべてクリアする。ただし、非活性の項目はクリアしない
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clearValues: function() {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var comp = list[i].component;
      if (!comp.getEnabled || comp.getEnabled())
        comp.clear();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     値、エラーメッセージ等をクリアする。 非活性の項目もクリアする。
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  forceClearAll: function() {
    // エラーメッセージクリア
    this.clearErrors();
    // 値クリア
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var comp = list[i].component;
      comp.clear();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     入力されている値をオブジェクトにして返す
   * @param
   * @return    {Object} {名前:値} のオブジェクト
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValues: function() {
    var values = {};
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var name = list[i].name;
      var comp = list[i].component;
      values[name] = comp.getValue();
    }
    return values;
  },

  /**
   ******************************************************************************
   * @brief     値をまとめて設定する
   * @param     {Object} values {名前:値} のオブジェクト
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setValues: function(values) {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var name = list[i].name;
      var comp = list[i].component;
      if (values[name] !== undefined)
        comp.setValue(values[name]);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     入力されている値を返す。
   * @param     {String} name 値の名前
   * @return    {?} 入力されている値
   * @retval
   * @attention
   * @note      指定された名前の欄が存在しない場合はundefinedを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValue: function(name) {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      if (list[i].name == name)
        return list[i].component.getValue();
    }
    return undefined;
  },

  /**
   ******************************************************************************
   * @brief     値を設定
   * @param     {String} name 値の名前
   * @param     {?} value 値
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setValue: function(name, value) {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      if (list[i].name == name) {
        list[i].component.setValue(value);
        break;
      }
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     値を入力するコンポーネント自体を返す。
   * @param     {String} name 値の名前
   * @return    {?} コンポーネント。存在しない場合はnull
   * @retval
   * @attention
   * @note      指定された名前の欄が存在しない場合はnullを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getComponent: function(name) {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      if (list[i].name == name)
        return list[i].component;
    }
    return null;
  },

  /**
   ******************************************************************************
   * @brief     全コンポーネントでオブジェクトで返す。
   * @param
   * @return    {?} 全コンポーネント。{ 名前: コンポーネント, ... }
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getAllComponent: function() {
    var obj = {};
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var name = list[i].name;
      var comp = list[i].component;
      obj[name] = comp;
    }
    return obj;
  },

  /**
   ******************************************************************************
   * @brief     エラーをまとめて追加
   * @param     {Array} errorInfoList エラー情報リスト
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  addErrors: function(errorInfoList) {
    this.clearErrors(); // 一旦クリア
    for (var i = 0; i < errorInfoList.length; i++) {
      var id = errorInfoList[i].id;
      var errorMessage = errorInfoList[i].errorMessage;
      var component = this.getComponent(id);
      if (component && component.addError) {
        component.addError(errorMessage);
        this._showData(id);
      }
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     エラーをまとめてクリア
   * @param
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clearErrors: function() {
    var list = this.componentList;
    for (var i = 0; i < list.length; i++) {
      var comp = list[i].component;
      if (comp.clearError) {
        comp.clearError();
      }
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     表示切替メニューを配置
   * @param
   * @return    {Object} dispMenuDiv 表示切替メニューのオブジェクト
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createDispMenu: function() {
    // 表示切替メニューを配置するdiv要素
    var dispMenuDiv = $('<div class="mcs-modifytable-buttons-wrapper"></div>');
    // 表示切替メニューのul要素
    var dispMenuUl = $('<ul class="dt-button-collection dropdown-menu" style="display:none;"/>');
    // 表示切替ボタン
    var dispMenuBtnA = $('<a class="btn btn-default" />');
    // 表示状態保存ボタン
    var dispMenuSaveDiv = $('<div class="mcs-modifytable-save-button"></div>');
    var dispMenuSaveBtn = new McsButton(dispMenuSaveDiv, McsModifyTable.language.buttons.colsave);
    // オーバーレイ用のdiv要素
    var dispMenuCoverDiv = $('<div class="dt-button-background"></div>');

    var self = this;

    dispMenuBtnA.text(McsModifyTable.language.buttons.colvis);

    // 表示切替ボタン押下時の処理を設定
    // 押下時に表示切替メニューの表示・非表示を行う。
    // 表示時に画面全体にdiv要素を被せる
    dispMenuBtnA.on('click', function() {
      if (dispMenuUl.is(':visible')) {
        dispMenuUl.fadeOut();
        // 表示切替が閉じられた時のコールバックを呼ぶ
        if (self.onHiddenColvisCallback) {
          self.onHiddenColvisCallback();
        }
      } else {
        dispMenuUl.fadeIn();
        dispMenuDiv.append(dispMenuCoverDiv);
        // 表示切替が開かれた時のコールバックを呼ぶ
        if (self.onShownColvisCallback) {
          self.onShownColvisCallback();
        }
      }

      // 表示切替メニューの表示位置を調整する。
      dispMenuUl.offset({
        top: dispMenuBtnA.offset().top + dispMenuBtnA.outerHeight(true) + parseInt(dispMenuUl.css('margin-top'), 10),
        left: dispMenuBtnA.offset().left
      });
    });

    // 表示状態保存ボタン押下時の処理を設定
    dispMenuSaveBtn.onClick(function() {
      var dispArray = [];
      dispMenuUl.find('li').each(function() {
        var dispInfo = self._getMatchDispState($(this).attr('name'));
        dispInfo.itemDisplay = $(this).hasClass('active') ? 1 : 0;
      });

      for (var i = 0, length = self.dispInfoList.length; i < length; i++) {
        dispArray.push(self.dispInfoList[i].itemDisplay);
      }

      var data = {
        tableCompId: self.tableCompId,
        columnDisplayList: dispArray
      };

      callAjax(getUrl('Com/McsModifyTable/SaveColVis'), data, false, function(retObj) {
        self._showSaveColumnInfoDialog();
      });
    });

    dispMenuSaveDiv.append(dispMenuSaveBtn);
    dispMenuDiv.append(dispMenuUl).append(dispMenuBtnA).append(dispMenuSaveDiv);

    // 表示切替メニューからフォーカスが外れた際に、表示切替メニューを閉じる
    $(document).click(function(event) {
      if (!$(event.target).closest(dispMenuDiv).length || event.target == dispMenuCoverDiv[0]) {
        dispMenuUl.fadeOut();
        dispMenuCoverDiv.remove();
        // 表示切替が閉じられた時のコールバックを呼ぶ
        if (self.onHiddenColvisCallback) {
          self.onHiddenColvisCallback();
        }
      }
    });
    return dispMenuDiv;
  },

  /**
   ******************************************************************************
   * @brief     表示情報保存の完了ダイアログを表示
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
  _showSaveColumnInfoDialog: function() {
    if (!this._showSaveColumnInfoDialogFlag) {
      this._showSaveColumnInfoDialogFlag = true;
      var div = $('<div></div>').appendTo('body');
      this._saveColumnInfoDialog = new McsDialog(div, window.mcsDialogTitleInfo);
    }
    var dialog = this._saveColumnInfoDialog;
    dialog.openAlert(McsModifyTable.language.buttons.colsaveSuccess, window.mcsDialogBtnReturn, 'info');
  },

  /**
   ******************************************************************************
   * @brief     表示切替メニューに項目を加える
   * @param     {Object} tr 対応するModifyTableの項目
   * @param     {String} name 項目名
   * @param     {Object} dispInfo 表示情報
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _addDispMenuList: function(tr, name, dispInfo) {
    var dispMenuLi = $('<li class="dt-button active"/>');
    dispMenuLi.attr('name', name);
    if (dispInfo.itemDisplay != 1) {
      dispMenuLi.removeClass('active');
    }

    var dispMenuA = $('<a href="#"/>');
    dispMenuA.text(dispInfo.itemName);
    var self = this;
    dispMenuA.on('click', function() {
      self._toggleDisp(tr, dispMenuLi);
      return false;
    });
    dispMenuLi.append(dispMenuA);
    this.dispMenuDiv.find('ul').append(dispMenuLi);
  },

  /**
   ******************************************************************************
   * @brief     表示状態を反転させる
   * @param     {Object} tr 対象のtr要素
   * @param     {Object} dispMenuLi 対象の表示切替メニューの要素
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _toggleDisp: function(tr, dispMenuLi) {
    if (dispMenuLi.hasClass('active')) {
      dispMenuLi.removeClass('active');
      tr.hide();
      this._hideEmptyTable(tr.closest('table'));
    } else {
      tr.closest('table').show();
      dispMenuLi.addClass('active');
      tr.show();
    }
  },

  /**
   ******************************************************************************
   * @brief     表示切替のウィンドウが表示されているかどうかチェック
   * @param
   * @return    {Booelan} 開かれているならtrue
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  isOpeningDispMenu: function() {
    var back = $('div.dt-button-background');
    return back.length != 0 && back.is(':visible');
  },

  /**
   ******************************************************************************
   * @brief     データを表示する
   * @param     {String} name 表示対象の名前
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _showData: function(name) {
    var tr = this.wrapperTable.find('tr[name="' + name + '"]');
    var dispMenuLi = this.dispMenuDiv.find('li[name="' + name + '"]');
    tr.closest('table').show();
    tr.show();
    dispMenuLi.addClass('active');
  },

  /**
   ******************************************************************************
   * @brief     空のテーブルを非表示にする
   * @param     {Object} table 対象のtable要素
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _hideEmptyTable: function(table) {
    table.show();
    var isEmpty = true;
    table.find('tbody tr').each(function(i, elem) {
      if ($(elem).css('display') != 'none') {
        isEmpty = false;
        return false;
      }
    });
    if (isEmpty) {
      table.hide();
    } else {
      table.show();
    }
  },

  /**
   ******************************************************************************
   * @brief     表示切替が開いたときのイベントコールバックを設定
   * @param     {Function} callback コールバック
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onShownColvis: function(callback) {
    this.onShownColvisCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     表示切替が閉じられたときのイベントコールバックを設定
   * @param     {Function} callback コールバック
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onHiddenColvis: function(callback) {
    this.onHiddenColvisCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     Modifyテーブルセッティング後のコールバック
   * @param     {Function} callback コールバック
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  onAfterSetting: function(callback) {
    this.onAfterSettingCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     必須項目のマークの表示状態を設定する
   * @param     {String} name 設定対象の名前
   * @param     {Boolean} required 表示状態。true:表示、false:非表示
   * @return    {McsModifyTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setRequired: function(name, required) {
    $targetSpan = this.containerDiv.find('tr[name="' + name + '"] th.mcs-modifytable-name span');
    if (required) {
      $targetSpan.addClass('mcs-required');
    } else {
      $targetSpan.removeClass('mcs-required');
    }
    return this;
  },

  end: 'end' // 終端ダミー
};
