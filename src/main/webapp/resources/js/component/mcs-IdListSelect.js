/**
 ******************************************************************************
 * @file        mcs-IdListSelect.js
 * @brief       IDリスト選択を実現する部品
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
 * @brief IDリストコンポーネント。
 * @param {jQuery} containerDiv 格納先のdiv要素
 * @param {Number} type 呼び出し元機能種別のモード。
 * @param {Boolean} mode 呼び出し種別。false:選択、true：グループ選択
 * @param {Boolean} textType テキストボックスのタイプを設定。false:文字列、true：数値
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsIdListSelect = function(containerDiv, type, mode, textType) {
  // コンストラクタ
  this.init(containerDiv, type, mode, textType);
};

// 呼び出し元機能種別のId。
McsIdListSelect.HOST = 'HD';
McsIdListSelect.AMHS = 'AD';

// メソッド類
McsIdListSelect.prototype = {

  /**
   ******************************************************************************
   * @brief 初期表示処理
   * @param {jQuery} containerDiv 格納先のdiv要素
   * @param {Number} type 呼び出し元機能種別のモード。
   * @param {Boolean} mode 呼び出し種別。false:選択、true：グループ選択
   * @param {Boolean} textType テキストボックスのタイプを設定。false:文字列、true：数値
   * @return
   * @retval
   * @attention
   * @note 初期表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, type, mode, textType) {
    this.containerDiv = containerDiv;
    this.mode = mode;
    this.type = type;
    this.textType = textType;
    // テキストボックス、選択ボタン、クリアボタンの生成
    var inputArea = $('<div class="mcs-idListSelect-inputArea"></div>').appendTo(containerDiv);
    var textBoxWrapper = $('<div class="mcs-idListSelect-textbox-wrapper"></div>').appendTo(inputArea);
    var textBox;
    if (this.textType) {
      textBox = new McsTextBox(textBoxWrapper, 'number').setReadonly(true).setValue('0');
    } else {
      textBox = new McsTextBox(textBoxWrapper).setReadonly(true);
    }
    var selectButtonWrapper = $('<div class="mcs-idListSelect-select-wrapper"></div>').appendTo(inputArea);
    var selectButton = new McsButton(selectButtonWrapper, McsIdListSelect.language.idListSelectCommon.btn.select);
    var clearButtonWrapper = $('<div class="mcs-idListSelect-clear-wrapper"></div>').appendTo(inputArea);
    var clearButton = new McsButton(clearButtonWrapper, McsIdListSelect.language.idListSelectCommon.btn.clear);
    this.textBox = textBox;
    this.selectButton = selectButton;
    this.clearButton = clearButton;

    // ダイアログの生成
    var dialogTitle;
    if (mode) {
      dialogTitle = McsIdListSelect.language.idListSelectGroup.label;
    } else {
      dialogTitle = McsIdListSelect.language.idListSelect.label;
    }
    var dialogWrapper = $('<div class="mcs-idListSelect-dialog-wrapper"></div>');
    var dialog = new McsDialog(dialogWrapper, dialogTitle);
    this.dialog = dialog;

    // ダイアログの中身の要素の生成（
    var dialogContent = $('<div class="mcs-idListSelect-dialog"></div>');
    $('<div class="mcs-idListSelect-table"></div>').appendTo(dialogContent);
    this.dialogContent = dialogContent;

    // 選択ボタン押下時の処理設定
    var self = this;
    selectButton.onClick(function() {
      self.setTable();
    });

    // クリアボタン押下時の処理設定
    clearButton.onClick(function() {
      self.clear();
    });
  },

  /**
   ******************************************************************************
   * @brief ダイアログを表示する
   * @return {McsIdListSelect} このコンポーネント
   * @retval
   * @attention
   * @note ID選択／IDグループ選択のダイアログを表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  showDialog: function() {
    var self = this;
    var buttonText1 = McsIdListSelect.language.idListSelectCommon.btn.ok;
    var buttonText2 = McsIdListSelect.language.idListSelectCommon.btn.cancel;
    // ダイアログを開く
    this.dialog.openCommonPartsDialog(self.dialogContent, 600, 400, buttonText1, buttonText2, function(buttonNum) {
      if (buttonNum == 0) {
        // OKボタン押下時
        var valueTmp = self.IdListTable.getSelectedRowData();
        if (valueTmp != null && valueTmp.length > 0) {
          var value = '';
          if (self.mode) {
            value = valueTmp[0].processGroupId;
          } else {
            value = valueTmp[0].serviceId;
          }
          self.textBox.setValue(value);
        } else {
          self.textBox.setValue('');
        }
      } else {
        // キャンセルボタン
        // なにもしない
      }
    });
    // カラム幅をリサイズ
    this.IdListTable.resizeColWidth();
    // 選択状態を解除
    this.IdListTable.deselectAll();

    return this;
  },

  /**
   ******************************************************************************
   * @brief IDセット機能
   * @param {Number} type 呼び出し元機能に対応するタイプ番号
   * @param {Function} innerError Ajax時の内部エラーのコールバック関数
   * @param {Function} ajaxError Ajax時のAjaxエラーのコールバック関数
   * @return
   * @retval
   * @attention
   * @note プロセスIDのテーブルセット。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setProcess: function(type, innerError, ajaxError) {
    var self = this;
    this.IdListTable.setHeader([{
      name: 'serviceId',
      text: McsIdListSelect.language.idListSelect.headers.processId,
      display: true
    }, {
      name: 'processName',
      text: McsIdListSelect.language.idListSelect.headers.processName,
      display: false
    }, {
      name: 'amhsNum',
      text: McsIdListSelect.language.idListSelect.headers.amhsNumber,
      display: true
    }, {
      name: 'amhsNameList',
      text: McsIdListSelect.language.idListSelect.headers.amhsNameList,
      display: true
    }]);
    var url = window.mcsComponentContextRoot + 'Com/McsIdListSelect/GetIdListSelect';
    var data = {
      type: type
    };
    callAjax(url, JSON.stringify(data), false, function(retObj) {
      var dataList = [];
      for (var i = 0; i < retObj.body.length; i++) {
        dataList.push({
          serviceId: retObj.body[i].serviceId,
          processName: retObj.body[i].processName,
          amhsNum: retObj.body[i].amhsNum,
          amhsNameList: retObj.body[i].amhsNameList
        });
      }
      self.IdListTable.addDataList(dataList);
      self.containerDiv.find('tbody').scrollTop(0);
      self.showDialog();
    }, innerError, ajaxError);
  },

  /**
   ******************************************************************************
   *
   * @brief グループIDセット機能
   * @param {Number} type 呼び出し元機能に対応する番号
   * @param {Function} innerError Ajax時の内部エラーのコールバック関数
   * @param {Function} ajaxError Ajax時のAjaxエラーのコールバック関数
   * @return
   * @retval
   * @attention
   * @note プロセスグループIDのテーブルセット。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setProcessGroup: function(type, innerError, ajaxError) {
    var self = this;
    this.IdListTable.setHeader([{
      name: 'processGroupId',
      text: McsIdListSelect.language.idListSelectGroup.headers.processGroupId,
      display: true
    }, {
      name: 'nodeNameList',
      text: McsIdListSelect.language.idListSelectGroup.headers.nodeNameList,
      display: true
    }]);
    var url = window.mcsComponentContextRoot + 'Com/McsIdListSelect/GetIdListSelectGroup';
    var data = {
      type: type
    };
    callAjax(url, JSON.stringify(data), false, function(retObj) {
      for (var i = 0; i < retObj.body.length; i++) {
        self.IdListTable.addData({
          processGroupId: retObj.body[i].processGroupId,
          nodeNameList: retObj.body[i].nodeNameList
        });
      }
      self.containerDiv.find('tbody').scrollTop(0);
      self.showDialog();
    }, innerError, ajaxError);
  },

  /**
   ******************************************************************************
   * @brief テーブルデータ設定処理
   * @param {Number} type 呼び出し元タイプ
   * @param {Function} innerError Ajax時の内部エラーのコールバック関数
   * @param {Function} ajaxError Ajax時のAjaxエラーのコールバック関数
   * @return
   * @retval
   * @attention
   * @note テーブルデータをセットする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setTable: function(innerError, ajaxError) {
    // 一旦、ダイアログの中身をクリア
    this.dialogContent.empty();
    // テーブル要素を生成。単一行選択モードにする。
    this.IdListTable = new McsTable(this.dialogContent);
    this.IdListTable.setBodyHeight(235);
    this.IdListTable.setMultiRowSelect(false);
    // スクロール位置を記憶
    if (this.scroll == undefined) {
      this.scroll = this.dialogContent.find('tbody').scrollTop();
    }
    // モードごとに、テーブルを設定
    if (this.mode) {
      // グループ選択
      this._setProcessGroup(this.type, innerError, ajaxError);
    } else {
      // 選択
      this._setProcess(this.type, innerError, ajaxError);
    }
  },

  /**
   ******************************************************************************
   * @brief 値取得
   * @return {String} テキストボックスの値
   * @retval
   * @attention
   * @note テキストボックスの値を取得して返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValue: function() {
    return this.textBox.getValue();
  },

  /**
   ******************************************************************************
   * @brief 値設定
   * @param {String} value 値
   * @return {McsIdListSelect} このコンポーネント
   * @retval
   * @attention
   * @note テキストボックスの値を取得して返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setValue: function(value) {
    this.textBox.setValue(value);
    return this;
  },

  /**
   ******************************************************************************
   * @brief 活性状態設定
   * @param {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return {McsIdListSelect} このコンポーネント
   * @retval
   * @attention
   * @note 活性・非活性を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setEnabled: function(enabled) {
    this.selectButton.setEnabled(enabled);
    this.clearButton.setEnabled(enabled);
    return this;
  },

  /**
   ******************************************************************************
   * @brief 活性状態取得
   * @return {Boolean} 活性ならtrue、非活性ならfalse
   * @retval
   * @attention
   * @note 活性状態を取得して返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getEnabled: function() {
    return this.selectButton.getEnabled();
  },

  /**
   ******************************************************************************
   * @brief 読取専用設定
   * @param {Boolean} readonly 読取専用ならtrue、読取専用でないならfalse
   * @return
   * @retval
   * @attention
   * @note 読取専用を設定する。本部品では、読取専用と非活性は同じ意味となる。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setReadonly: function(readonly) {
    return this.setEnabled(!readonly);
  },

  /**
   ******************************************************************************
   * @brief 読み取り専用設定
   * @return {Boolean} 読取専用ならtrue、読取専用でないならfalse
   * @return
   * @retval
   * @attention
   * @note 読取専用を返す。本部品では、読取専用と非活性は同じ意味となる。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getReadonly: function() {
    return !this.getEnabled();
  },

  /**
   ******************************************************************************
   * @brief 値をクリア
   * @return {McsIdListSelect} このコンポーネント
   * @retval
   * @attention
   * @note テキストボックスの値をクリアする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    if (this.textType) {
      this.textBox.setValue('0');
    } else {
      this.textBox.clear();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief エラーを追加する
   * @return {McsIdListSelect} このコンポーネント
   * @retval
   * @attention
   * @note エラーを追加する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addError: function(errorMessage) {
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    this.textBox.addError('');
    return this;
  },

  /**
   ******************************************************************************
   * @brief  エラー表示のクリア処理
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
    this.textBox.clearError();
  },

  /**
   ******************************************************************************
   * @brief     指定のjQueryObjの配下にこの部品を追加する
   * @param     {jQueryObj}  jQueryObj jQueryオブジェクト
   * @return    {McsIdListSelect} このコンポーネント
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

  end: 'end'
};
