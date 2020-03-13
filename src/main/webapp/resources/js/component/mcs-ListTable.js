/**
 ******************************************************************************
 * @file        mcs-ListTable.js
 * @brief       順序付きリストテーブルに関する部品
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
 * @brief コンストラクタ
 * @param {jQuery}  containerDiv 格納先のdiv要素
 * @param {Boolean} addComboBox コンボボックスを追加するかの判定
 * @param {Boolean} addButtonGroup ボタングループを追加するかの判定
 * @param {Boolean} noAddMultiSelBox マルチセレクトボックスを追加するかの判定
 * @param {Object}  attr tableに追加適用する属性
 * @param {Object}  prop tableに追加適用する属性
 * @attention
 * @note 順序付きリストテーブルコンポーネント
 *        このコンポーネントは、DataTablesを利用しない簡易機能版のコンポーネントです。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsListTable = function(containerDiv, addComboBox, addButtonGroup, noAddMultiSelBox, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, addComboBox, addButtonGroup, noAddMultiSelBox, attr, prop);
};
// メソッド類
McsListTable.prototype = {
  /**
   ******************************************************************************
   * @brief 初期化処理
   * @param {jQuery}  containerDiv 格納先のdiv要素
   * @param {Boolean} addComboBox コンボボックスを追加するかの判定
   * @param {Boolean} addButtonGroup ボタングループを追加するかの判定
   * @param {Boolean} noAddMultiSelBox マルチセレクトボックスを追加するかの判定
   * @param {Object}  attr tableに追加適用する属性
   * @param {Object}  prop tableに追加適用する属性
   * @attention
   * @note 初期化処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, addComboBox, addButtonGroup, noAddMultiSelBox, attr, prop) {
    this.containerDiv = containerDiv;
    if (addComboBox) {
      var comboBoxDiv = $('<div class="mcs-combobox-wrapper"></div>');
      var comboBox = new McsComboBox(comboBoxDiv);
      this.comboBox = comboBox;
      this.containerDiv.append(comboBoxDiv);
    }

    // マルチセレクトボックス生成
    if (!noAddMultiSelBox) {
      var multiSelectBoxDiv = $('<div></div>');
      var multiSelectBox = new McsMultiSelectBox(multiSelectBoxDiv);
      this.multiSelectBox = multiSelectBox;
      this.containerDiv.append(multiSelectBoxDiv);
    }

    // テーブル生成
    var mcsTableDiv = $('<div class="mcs-list-table"></div>');
    var mcsTable = new McsTable(mcsTableDiv, attr, prop);
    // 単一行選択にする
    mcsTable.setMultiRowSelect(false);
    this.tableDiv = mcsTableDiv;
    this.table = mcsTable;

    // containerDivに追加する
    containerDiv.append(mcsTableDiv);
    if (addButtonGroup) {
      var buttonGroupDiv = $('<div class="mcs-button-group-wrapper"></div>');
      var buttonGroup = new McsButtonGroup(buttonGroupDiv);
      this.buttonGroup = buttonGroup;
      this.containerDiv.append(buttonGroupDiv);
    }

    // 内部データ
    this.headerInfo = null;
    this.tableData = [];
    this.dataMap = {};
    this.selectListSet = [];
    this.dispKey = [];
  },

  /**
   ******************************************************************************
   * @brief クリックイベントの初期化
   * @param {jQueryObj} tr tr要素
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initClickEvent: function(tr) {
    var tbody = this.tbody;
    tr.on('click', function() {
      var $this = $(this);
      if ($this.hasClass('info')) {
        $this.removeClass('info');
      } else {
        tbody.find('tr.info').removeClass('info');
        $this.addClass('info');
      }
    });
  },

  /**
   ******************************************************************************
   * @brief プルダウンリスト表示設定
   * @param {Array} selectList Ajax通信で受け取ったdata.body
   * @param {Array} opt プルダウンリストに表示するテキストの指定
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note 受け取ったデータを成形して内部に記憶。
   *        プルダウンリストにレコードのどのデータを表示するかの設定。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setList: function(selectList, opt) {
    var tempList = [];
    for (var i = 0; i < selectList.length; i++) {
      var tempObj = {};
      tempObj.id = i;
      tempObj.disp = true;
      tempObj.selected = false;
      tempObj.data = selectList[i];
      tempList.push(tempObj);
    }
    this.selectListSet = tempList;
    this.dispKey = opt;
    this._setMultiSelectList();
    return this;
  },

  /**
   ******************************************************************************
   * @brief プルダウンリストを設定する。
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setMultiSelectList: function() {
    this.multiSelectBox.setList(this.selectListSet, this.dispKey);
    return this;
  },

  /**
   ******************************************************************************
   * @brief ヘッダ設定。
   * @param {Array} headerInfoList ヘッダー名のリスト
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note テーブルのヘッダを設定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setHeader: function(headerInfoList) {
    // マルチセレクトボックスの要素とテーブルレコードを結びつけるための
    // インデックスカラムを追加
    var newColumn = {
      name: 'mcsListTableIndexColumn',
      text: '',
      display: false
    };
    headerInfoList.push(newColumn);
    this.table.setHeader(headerInfoList);
    this.headerInfoList = headerInfoList;
    return this;
  },

  /**
   ******************************************************************************
   * @brief データ複数追加処理
   * @param {Array} indexArray プルダウンでチェックの入れられたvalue値の配列
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note テーブルのデータをまとめて追加する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addDataList: function(indexArray) {
    var dataArr = [];
    for (var i = 0; i < indexArray.length; i++) {
      // 追加する値を内部的にも外部的にも選択状態にする
      var selectSet = this.selectListSet[indexArray[i]];
      selectSet.selected = true;
      this.multiSelectBox.setValue(selectSet.data, false);

      // インデックスを調整し配列に格納
      selectSet.data.mcsListTableIndexColumn = indexArray[i];
      dataArr.push($.extend(true, {}, selectSet.data));

      // 管理用に独自にIDを振って格納
      this.dataMap[selectSet.id] = selectSet;
    }
    // テーブルに追加
    this.table.addDataList(dataArr);

    // チェンジインデックスの更新
    this.multiSelectBox.getChangedIndex();
    return this;
  },

  /**
   ******************************************************************************
   * @brief 値取得
   * @return {Array} 表の値（ [ [1, 'hoge'], [2, 'piyo'], ... ] のような形式）
   * @retval
   * @attention
   * @note 表の値を二次元配列で取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValue: function() {
    return this._getValues();
  },

  /**
   ******************************************************************************
   * @brief 値取得
   * @return {Array} 表の値（ [ [1, 'hoge'], [2, 'piyo'], ... ] のような形式）
   * @retval
   * @attention
   * @note 表の値を二次元配列で取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getValues: function() {
    return this.table.getValue();
  },

  /**
   ******************************************************************************
   * @brief 選択行データ取得
   * @return {Array} 選択行のデータ（テキスト）
   * @retval
   * @attention
   * @note  選択されている行のデータを返す。 何も選択されていないときはnullを返す。
   *          データはすべて文字列となっているので注意すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getSelectedRowData: function() {
    return this.table.getSelectedRowData();
  },

  /**
   ******************************************************************************
   * @brief 選択行データ取得
   * @param {jQueryObj} tr 行
   * @return {Array} 指定行のテキストの配列
   * @retval
   * @attention
   * @note  選択されている行のデータを返す。 何も選択されていないときはnullを返す。
   *          データはすべて文字列となっているので注意すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getRowData: function(tr) {
    var td = tr.children('td');
    var data = new Array(td.length);
    td.each(function(index) {
      if ($(this).children().length > 0) {
        // チェックボックスが前提(矢野)
        data[index] = $(this).children().prop('checked');
      } else {
        data[index] = $(this).text();
      }
    });
    return data;
  },

  /**
   ******************************************************************************
   * @brief 行数取得
   * @return {Number} 表の行数
   * @retval
   * @attention
   * @note  現在の表の行数を取得する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getRowCount: function() {
    return this.table.getRowCount();
  },

  /**
   ******************************************************************************
   * @brief 行削除
   * @param {jQueryObj} tr 行
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  指定した行データと、それに対応する内部データを削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _delFromRow: function(tr) {
    var id = tr.attr('mcs-table-id');
    tr.remove();
    delete this.dataMap[id];
    return this;
  },
  /**
   ******************************************************************************
   * @brief 行削除
   * @param {Number} index 削除対象となるthis.dataMapのプロパティ
   * @return {Boolean} 削除成功したときはtrue、行が未選択だった場合はfalse
   * @retval
   * @attention
   * @note  選択されている行のテーブルを削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _delRow: function(index) {
    // 選択されている行のオブジェクト取得
    this.selectListSet[index].selected = false;
    var tr = this.tbody.children('tr[mcs-table-id=' + index + ']');
    if (tr.length == 0) {
      return false;
    }
    this._delFromRow(tr);
    return true;
  },
  /**
   ******************************************************************************
   * @brief 行をまとめて削除する
   * @param {Array} indexArray 削除対象となるデータのindex番号配列
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  選択されている行をまとめて削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  delRows: function(indexArray) {
    var newArray = this._convertDelIndex(indexArray);
    this.table.delRows(newArray);
    for (var i = 0; i < indexArray.length; i++) {
      this.selectListSet[indexArray[i]].selected = false;
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief 削除する行のインデックスを求める
   * @param {Array} indexArray 削除対象となるデータのmultiSelectBoxのindex番号配列
   * @return {Array} 削除対象となるデータのTableのindex番号配列
   * @retval
   * @attention
   * @note  選択されている行をまとめて削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _convertDelIndex: function(indexArray) {
    // 正しい削除インデックスに変換する
    var newArray = [];
    var data = this.table.getValue();
    for (var i = 0; i < data.length; i++) {
      var multiIndex = data[i].mcsListTableIndexColumn;
      for (var j = 0; j < indexArray.length; j++) {
        if (multiIndex == indexArray[j]) {
          newArray.push(i);
        }
      }
    }
    return newArray;
  },

  /**
   ******************************************************************************
   * @brief 全データ削除
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  全データを削除する。ヘッダーはクリアされない。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.dataMap = {};
    this.table.clear();

    // マルチセレクトボックスのチェックを全てはずす
    for (var i = 0; i < this.selectListSet.length; i++) {
      this.selectListSet[i].selected = false;
    }
    this.multiSelectBox.clear();
    return this;
  },

  /**
   ******************************************************************************
   * @brief 選択行を一つ上へ移動
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  選択した行を一つ上へ移動させる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  rowUp: function() {
    this.table.rowUp();
    // var row = this.tbody.find('tr.info');
    // if ($(row).prev('tr')[0])
    // $(row).insertBefore($(row).prev('tr'));
    return this;
  },

  /**
   ******************************************************************************
   * @brief 選択行を一つ下へ移動
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  選択した行を一つ下へ移動させる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  rowDown: function() {
    this.table.rowDown();
    // var row = this.tbody.find('tr.info');
    // if ($(row).next('tr')[0])
    // $(row).insertAfter($(row).next('tr'));

    return this;
  },

  /**
   ******************************************************************************
   * @brief 表更新
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  マルチセレクトボックスの選択内容により、表を更新する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  modifyTBody: function() {
    var checkedList = this.multiSelectBox._getCheckedList();
    var newCheckedList = this.multiSelectBox._getNewCheckedList();
    var indexArray = this.multiSelectBox.getChangedIndex();
    if (checkedList.length < newCheckedList.length)
      this.addDataList(indexArray);
    else {
      this.delRows(indexArray);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief 表示項目フィルタ設定
   * @param {Object}  key 受け取ったオブジェクトのプロパティを指定
   * @param {String}  val 上記で指定したキーのどの値を表示するかを指定
   * @return {McsListTable} このコンポーネント
   * @retval
   * @note  マルチセレクトボックスの表示項目フィルタをかける。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  filter: function(key, val) {
    for (var i = 0; i < this.selectListSet.length; i++) {
      if (this.selectListSet[i].data[key] == val)
        this.selectListSet[i].disp = true;
      else
        this.selectListSet[i].disp = false;
    }
    this._setMultiSelectList();
    return this;
  },

  /**
   ******************************************************************************
   * @brief 表示項目フィルタ解除
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  マルチセレクトボックスの表示項目のフィルタを外す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  noFilter: function() {
    for (var i = 0; i < this.selectListSet.length; i++)
      this.selectListSet[i].disp = true;
    this._setMultiSelectList();
    return this;
  },

  /**
   ******************************************************************************
   * @brief エラー追加処理
   * @param {String} errorMessage エラーメッセージ
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  エラーを追加する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addError: function(errorMessage) {
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    var rowCount = this.table.getRowCount();
    if (rowCount != 0) {
      for (var i = 0; i < rowCount; i++) {
        this.table.addError(i);
      }
    }
    this.tableDiv.find('.mcs-table-wrapper').addClass('mcs-component-errorBack');
    return this;
  },

  /**
   ******************************************************************************
   * @brief エラークリア処理
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note  エラーをクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearError: function() {
    this.containerDiv.find('.mcs-component-errorText').remove();
    var rowCount = this.table.getRowCount();
    if (rowCount != 0) {
      this.table.clearErrors();
    }
    this.tableDiv.find('.mcs-table-wrapper').removeClass('mcs-component-errorBack');
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
    // this.input.prop('disabled', !enabled);
    // return this;
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
    // return !this.input.prop('disabled');
  },

  /**
   ******************************************************************************
   * @brief     行選択可否設定
   * @param     {Boolean}
   *             notRowSelect 行選択不可ならtrue、行選択可能ならfalse
   * @return    {McsTable}  このコンポーネント
   * @retval
   * @attention
   * @note      行選択の可否を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setNotRowSelect: function(notRowSelect) {
    this.table.setNotRowSelect(notRowSelect);
  },

  /**
   ******************************************************************************
   * @brief     指定のjQueryObjの配下にこの部品を追加
   * @param {jQueryObj} jQueryObj jQueryオブジェクト
   * @return {McsListTable} このコンポーネント
   * @retval
   * @attention
   * @note      指定のjQueryObjの配下にこの部品を追加する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  appendTo: function(jQueryObj) {
    jQueryObj.append(this.containerDiv);
    return this;
  },

  /**
   ******************************************************************************
   * @brief jQueryオブジェクト返却
   * @return {jQueryObj} table要素
   * @retval
   * @attention
   * @note  table要素のjQueryオブジェクトを返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getJq: function() {
    return this.input;
  }
};
