/**
 ******************************************************************************
 * @file        mcs-Location.js
 * @brief       ロケーション選択に関する部品
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
 * 2018/10/18 MACS4#0016  GUI客先テストNG項目対応                           CSC
 * 2018/11/26 MACS4#0047  GUI要望分                                   T.Iga/CSC
 * 2019/04/22 MACS4#0160  IFOHB表示非表示対応                         T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief     ロケーションコンポーネント（ボタン＋テキストボックス）
 * @param     {jQuery} containerDiv 格納先のdiv要素
 * @param     {Boolean} mode ロケーションのモード。false:選択、true:入力
 * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
 * @param     {Boolean} macroMode macroのみ使用するかのモード。false:Portセレクトボックス有効、true:Portセレクトボックス無効
 * @param     {Boolean} findAmhsId OHBグループ表示情報の取得種別モード。false:OHBテーブル、true:AMHSテーブル
 * @param     {Boolean} dispIFOhb IFOHB表示フラグ。false:非表示、true:表示
 * @return
 * @retval
 * @attention
 * @note      実際にロケーションを選択するセレクトボックスの部分については、 McsLocationSelectBox が担当する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0047  GUI要望分                                              T.Iga/CSC
 * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
 ******************************************************************************
 */
//var McsLocation = function(containerDiv, mode, filter) {                      // MACS4#0047 Del
//var McsLocation = function(containerDiv, mode, filter, macroMode, findAmhsId) { // MACS4#0047 Add // MACS4#0160 Del
var McsLocation = function(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb) {          // MACS4#0160 Add
  // コンストラクタ
//this.init(containerDiv, mode, filter);                          // MACS4#0047 Del
//this.init(containerDiv, mode, filter, macroMode, findAmhsId);   // MACS4#0047 Add // MACS4#0160 Del
  this.init(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb);          // MACS4#0160 Add
};

// メソッド類
McsLocation.prototype = {

  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {Boolean} mode ロケーションのモード。false:選択、true:入力
   * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
   * @param     {Boolean} macroMode macroのみ使用するかのモード。false:Portセレクトボックス有効、true:Portセレクトボックス無効
   * @param     {Boolean} findAmhsId OHBグループ表示情報の取得種別モード。false:OHBテーブル、true:AMHSテーブル
   * @param     {Boolean} dispIFOhb IFOHB表示フラグ。false:非表示、true:表示
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
   ******************************************************************************
  */
//init: function(containerDiv, mode, filter) {                          // MACS4#0047 Del
//init: function(containerDiv, mode, filter, macroMode, findAmhsId) {   // MACS4#0047 Add   // MACS4#0160 Del
  init: function(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb) {            // MACS4#0160 Add
    var self = this;

    this.containerDiv = containerDiv;
    this.containerDiv.addClass('mcs-location');

    this.wrapperDiv = $('<div class="mcs-location-wrapper"></div>');
    containerDiv.append(this.wrapperDiv);

    // ロケーション選択部品生成
    this.locationWrapper = $('<div class="mcs-location-location-wrapper"></div>');
//  this.location = new McsLocationSelectBox(this.locationWrapper, mode, filter);                           // MACS4#0047 Del
//  this.location = new McsLocationSelectBox(this.locationWrapper, mode, filter, macroMode, findAmhsId);    // MACS4#0047 Add   // MACS4#0160 Del
    this.location = new McsLocationSelectBox(this.locationWrapper, mode, filter, macroMode, findAmhsId, dispIFOhb);             // MACS4#0160 Add
    this.location.setLabelVisible(true);

    // ダイアログ生成
    var title;
    if (mode) {
      title = McsLocation.language.inputTitle;
    } else {
      title = McsLocation.language.selectTitle;
    }
    this.dialogWrapper = $('<div></div>');
    this.dialog = new McsDialog(this.dialogWrapper, title);

    // ボタン生成
    this.buttonWrapper = $('<div class="mcs-location-button-wrapper"></div>');
    this.button = new McsButton(this.buttonWrapper, McsLocation.language.select);
    this.wrapperDiv.append(this.buttonWrapper);
    this.button.onClick(function() {
      self.showDialog();
    });

    // テキストボックス生成
    this.textBoxList = [];
    this.textBoxWrapperList = [];
    // テキストボックス①
    this.textBoxWrapperList[0] = $('<div class="mcs-location-text-wrapper1"></div>');
    this.textBoxList[0] = new McsTextBox(this.textBoxWrapperList[0]);
    this.textBoxList[0].setMaxLength(64);
    this.wrapperDiv.append(this.textBoxWrapperList[0]);
    // テキストボックス②
    this.textBoxWrapperList[1] = $('<div class="mcs-location-text-wrapper2"></div>');
    this.textBoxList[1] = new McsTextBox(this.textBoxWrapperList[1]);
    this.textBoxList[1].setMaxLength(64);
    this.wrapperDiv.append(this.textBoxWrapperList[0]);
    this.wrapperDiv.append(this.textBoxWrapperList[1]);

    // 特に設定していない状態では、編集可能状態。
    this.setEditable(true);
  },

  /**
   ******************************************************************************
   * @brief     ロケーション選択ダイアログを表示
   * @param
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  showDialog: function() {
    // ダイアログを開く
    var buttonText1 = McsLocation.language.ok;
    var buttonText2 = McsLocation.language.cancel;
    var self = this;
    self.location.clear();
    this.dialog.openCommonPartsDialog(self.location, 450, 350, buttonText1, buttonText2, function(buttonNum) {
      if (buttonNum == 0) {
        // OKボタン押下時
        var locationValues = self.location.getValue();
        var val1 = '';
        var val2 = '';
        val1 = '';
        if (locationValues[0] != null) {
          val1 = locationValues[0].value;
        }
        if (locationValues[1] != null) {
          val2 = locationValues[1].value;
        }
        self.setValue({
          value1: val1,
          value2: val2
        });
      } else {
        // キャンセルボタン
        // 何もしない
      }
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックス部品の取得
   * @param
   * @return    {Array.McsTextBox} テキストボックス部品の配列
   * @retval
   * @attention
   * @note      テキストボックスの部品を配列形式で返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getTextBoxList: function() {
    return [this.textBoxList[0], this.textBoxList[1]];
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスに値を設定
   * @param     {Object} valueObj 値のオブジェクト
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * 値の形式は以下の通り。
   *
   * <pre>
   * {
   *   value1: テキストボックス①の値
   *   value2: テキストボックス②の値
   * }
   * </pre>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setValue: function(valueObj) {
    if (valueObj === null || valueObj === undefined) {
      return this;
    }
    this.textBoxList[0].setValue(valueObj.value1);
    this.textBoxList[1].setValue(valueObj.value2);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスの値を配列で取得
   * @param
   * @return    {Object} 値のオブジェクト
   * @retval
   * @attention
   * @note
   * 値の形式は以下の通り。
   *
   * <pre>
   * {
   *   value1: テキストボックス①の値
   *   value2: テキストボックス②の値
   * }
   * </pre>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValue: function() {
    var v = {
      value1: this.textBoxList[0].getValue(),
      value2: this.textBoxList[1].getValue()
    };
    return v;
  },

  /**
   ******************************************************************************
   * @brief     テキストボックスの値をクリア
   * @param
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clear: function() {
    for (var i = 0; i < this.textBoxList.length; i++) {
      this.textBoxList[i].clear();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性を設定
   * @param     {Boolean} enabled 活性ならtrue、非活性ならfalse
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setEnabled: function(enabled) {
    for (var i = 0; i < this.textBoxList.length; i++) {
      this.textBoxList[i].setEnabled(enabled);
    }
    this.button.setEnabled(enabled);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     活性・非活性を取得
   * @param
   * @return    {Boolean} 活性ならtrue、非活性ならfalse
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getEnabled: function() {
    // テキストボックス、ボタンの活性非活性は同じはずなので、
    // 1つめのテキストボックスの活性状態を返す。
    return this.textBoxList[0].getEnabled();
  },

  /**
   ******************************************************************************
   * @brief     読み取り専用を設定
   * @param     {Boolean} readonly 読み取り専用ならtrue
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setReadonly: function(readonly) {
    for (var i = 0; i < this.textBoxList.length; i++) {
      this.textBoxList[i].setReadonly(readonly);
    }
    this.button.setEnabled(!readonly);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     読み取り専用状態を取得
   * @param
   * @return    {Boolean} 読み取り専用ならtrue
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getReadonly: function() {
    return this.textBoxList[0].getReadonly();
  },

  /**
   ******************************************************************************
   * @brief     編集可能状態を設定
   * @param     {Boolean} editable 編集可能状態ならtrue、不可ならfalse
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setEditable: function(editable) {
    var i;
    this.editable = editable;
    if (editable) {
      // ボタンを活性、テキスト部分のreadonlyを解除する
      for (i = 0; i < this.textBoxList.length; i++) {
        this.textBoxList[i].setReadonly(false);
      }
      this.button.setEnabled(true);
    } else {
      // ボタンを活性、テキスト部分をreadonlyにする
      for (i = 0; i < this.textBoxList.length; i++) {
        this.textBoxList[i].setReadonly(true);
      }
      this.button.setEnabled(true);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     編集可能状態を取得
   * @param
   * @return    {Boolean} 編集可能状態ならtrue、不可ならfalse
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getEditable: function() {
    return this.editable;
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
    for (var i = 0; i < this.textBoxList.length; i++) {
      this.textBoxList[i].addError('');
    }
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
    for (var i = 0; i < this.textBoxList.length; i++) {
      this.textBoxList[i].clearError();
    }
  },

  /**
   ******************************************************************************
   * @brief     指定のjQueryObjの配下にこの部品を追加
   * @param     {jQueryObj} jQueryObj jQueryオブジェクト
   * @param     {jQueryObj} containerDiv 格納先のdiv要素
   * @return    {McsLocation} このコンポーネント
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

  /**
   ******************************************************************************
   * @brief     指定されたテキストボックスを表示する
   * @param     {Number} index 対象のテキストボックスのインデックス
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  showText: function(index) {
    this.textBoxWrapperList[index].show();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     指定されたテキストボックスを非表示にする
   * @param     {Number} index 対象のテキストボックスのインデックス
   * @return    {McsLocation} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  hideText: function(index) {
    this.textBoxWrapperList[index].hide();
    return this;
  }

};

/**
 ******************************************************************************
 * @brief     ロケーションコンポーネント（ラベル＋セレクトボックス）
 * @param     {jQuery} containerDiv 格納先のdiv要素
 * @param     {Boolean} mode ロケーションのモード。false:選択、true:入力
 * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
 * @param     {Boolean} macroMode macroのみ使用するかのモード。false:Portセレクトボックス有効、true:Portセレクトボックス無効
 * @param     {Boolean} findAmhsId OHBグループ表示情報の取得種別モード。false:OHBテーブル、true:AMHSテーブル
 * @param     {Boolean} dispIFOhb IFOHB表示フラグ。false:非表示、true:表示
 * @param     {Object.<string, string>} attr inputに追加適用する属性
 * @param     {Object.<string, string>} prop inputに追加適用する属性
 * 
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0047  GUI要望分                                              T.Iga/CSC
 * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
 ******************************************************************************
 */
//var McsLocationSelectBox = function(containerDiv, mode, filter, attr, prop) {                         // MACS4#0047 Del
//var McsLocationSelectBox = function(containerDiv, mode, filter, macroMode, findAmhsId, attr, prop) {    // MACS4#0047 Add // MACS4#0160 Del
var McsLocationSelectBox = function(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb, attr, prop) {             // MACS4#0160 Add
  // コンストラクタ
//this.init(containerDiv, mode, filter, attr, prop);                          // MACS4#0047 Del
//this.init(containerDiv, mode, filter, macroMode, findAmhsId, attr, prop);   // MACS4#0047 Add // MACS4#0160 Del
  this.init(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb, attr, prop);          // MACS4#0160 Add
};

// メソッド類
McsLocationSelectBox.prototype = {

  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {Boolean} mode ロケーションのモード。false:選択、true:入力
   * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
   * @param     {Boolean} macroMode macroのみ使用するかのモード。false:Portセレクトボックス有効、true:Portセレクトボックス無効
   * @param     {Boolean} findAmhsId OHBグループ表示情報の取得種別モード。false:OHBテーブル、true:AMHSテーブル
   * @param     {Boolean} dispIFOhb IFOHB表示フラグ。false:非表示、true:表示
   * @param     {Object.<string, string>} attr inputに追加適用する属性
   * @param     {Object.<string, string>} prop inputに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
  */
//init: function(containerDiv, mode, filter, attr, prop) {                          // MACS4#0047 Del
//init: function(containerDiv, mode, filter, macroMode, findAmhsId, attr, prop) {   // MACS4#0047 Add   // MACS4#0160 Del
  init: function(containerDiv, mode, filter, macroMode, findAmhsId, dispIFOhb, attr, prop) {            // MACS4#0160 Add
    this.attr = attr;
    this.prop = prop;
    this.containerDiv = containerDiv;
    this.containerDiv.addClass('mcs-locationselectbox');
    // セレクトボックスを管理するための配列
    this.selectComponent = [];
    // ラベルを管理するための配列
    this.labelComponent = [];

    // 実際の要素を生成
    // データ選択用のDiv(セレクトボックスは最大3個)
    var dataDiv = $('<div class="mcs-locationselectbox-data"></div>');
    this.dataDiv = dataDiv;
    // セレクトボックスの生成
    this._createSelectBox();// ロケーション
    this._createSelectBox();// data1
    this._createSelectBox();// data2

    // this内に格納
    this.mode = mode;
    this.filter = filter;
    // MACS4#0047 Add Start
    if (macroMode === null || macroMode === undefined) {
      macroMode = false;
    }
    this.macroMode = macroMode;
    if (findAmhsId === null || findAmhsId === undefined) {
      findAmhsId = false;
    }
    this.findAmhsId = findAmhsId;
    // MACS4#0047 Add End
    // MACS4#0160 Add Start
    if (dispIFOhb === null || dispIFOhb === undefined) {
        dispIFOhb = false;
    }
    this.dispIFOhb = dispIFOhb;
    // MACS4#0160 Add End

    // ロケーションを設定
//  this.setLocation(mode, filter);             // MACS4#0047 Del
    this.setLocation(mode, filter, macroMode);  // MACS4#0047 Add

    // それぞれの部品を指定Div内に設置
    this.containerDiv.append(dataDiv);

    // イベントの設定
    var self = this;
    this.initChangeLocation(function() {
      self.clearList(2);
      self.clearList(1);
    }, function() {
      self.clearList(2);
      self.clearList(1);
    });
    this.initChangeSelect(function() {
      self.clearList(2);
    }, function() {
      self.clearList(2);
    });
    this.setLabelVisible(false);
  },

  /**
   ******************************************************************************
   * @brief     ロケーションセレクトボックスの要素を生成
   * @param     {Boolean} mode ロケーションのモード。false:選択、true:入力
   * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
   * @param     {Boolean} macroMode macroのみ使用するかのモード。false:Portセレクトボックス有効、true:Portセレクトボックス無効
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0016  GUI客先テストNG項目対応                                      CSC
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
  */
//setLocation: function(mode, filter) {             // MACS4#0047 Del
  setLocation: function(mode, filter, macroMode) {  // MACS4#0047 Add
    this.selectComponent[0].clear();
    var language = McsLocationSelectBox.language;
    if (mode) {
      // ロケーション入力
      var inputController = language.locationInput.labels.controller;
      var inputPort = language.locationInput.labels.port;
      // MACS4#0016 Del Start
//    var baseLocationList = [[101, language.locationInput.selects.stocker],
//        [102, language.locationInput.selects.vehicle], [103, language.locationInput.selects.ohbGroup],
//        [104, language.locationInput.selects.machine], [105, language.locationInput.selects.lifter],
//        [106, language.locationInput.selects.convayor], [107, language.locationInput.selects.operatorPort]];
      // MACS4#0016 Del End
      // MACS4#0016 Add Start
      var baseLocationList = [
        [101, language.locationInput.selects.stocker],
        [102, language.locationInput.selects.vehicle],
        [103, language.locationInput.selects.ohbGroup],
        [104, language.locationInput.selects.machine],
        [107, language.locationInput.selects.operatorPort]
      ];
      // MACS4#0016 Add End
      // MACS4#0047 Del Start
//    var baseLabelList = {
//      101: [inputController, inputPort],
//      102: [inputController, language.locationInput.labels.vehicle],
//      103: [language.locationInput.labels.ohbGroup, inputPort],
//      104: [language.locationInput.labels.machine, inputPort],
////    105: [inputController, inputPort], // MACS4#0016 Del
////    106: [inputController, inputPort], // MACS4#0016 Del
//      107: [inputController, inputPort]
//    };
      // MACS4#0047 Del End
      // MACS4#0047 Add Start
      if (!macroMode) {
        var baseLabelList = {
          101: [inputController, inputPort],
          102: [inputController, language.locationInput.labels.vehicle],
          103: [language.locationInput.labels.ohbGroup, inputPort],
          104: [language.locationInput.labels.machine, inputPort],
          107: [inputController, inputPort]
        };
      } else {
        var baseLabelList = {
          101: [inputController],
          102: [inputController],
          103: [language.locationInput.labels.ohbGroup],
          104: [language.locationInput.labels.machine],
          107: [inputController]
        };
      }
      // MACS4#0047 Add End
    } else {
      // ロケーション選択
      var selectController = language.locationSelect.labels.controller;
      var selectPort = language.locationSelect.labels.port;
      // MACS4#0016 Del Start
//    var baseLocationList = [[1, language.locationSelect.selects.stocker],
//        [2, language.locationSelect.selects.stockerGroup], [3, language.locationSelect.selects.ohbGroup],
//        [4, language.locationSelect.selects.machine], [5, language.locationSelect.selects.lifter],
//        [6, language.locationSelect.selects.operatorPort]];
      // MACS4#0016 Del End
      // MACS4#0016 Add Start
      var baseLocationList = [
        [1, language.locationSelect.selects.stocker],
        [2, language.locationSelect.selects.stockerGroup],
        [3, language.locationSelect.selects.ohbGroup],
        [4, language.locationSelect.selects.machine],
        [6, language.locationSelect.selects.operatorPort]
      ];
      // MACS4#0016 Add End
      // MACS4#0047 Del Start
//    var baseLabelList = {
//      1: [selectController, selectPort],
//      2: [language.locationSelect.labels.stockerGroup],
//      3: [language.locationSelect.labels.ohbGroup, selectPort],
//      4: [language.locationSelect.labels.machine, selectPort],
////    5: [selectController, selectPort], // MACS4#0016 Del
//      6: [selectController, selectPort]
//    };
      // MACS4#0047 Del End
      // MACS4#0047 Add Start
      if (!macroMode) {
        var baseLabelList = {
          1: [selectController, selectPort],
          2: [language.locationSelect.labels.stockerGroup],
          3: [language.locationSelect.labels.ohbGroup, selectPort],
          4: [language.locationSelect.labels.machine, selectPort],
          6: [selectController, selectPort]
        };
      } else {
        var baseLabelList = {
          1: [selectController],
          2: [language.locationSelect.labels.stockerGroup],
          3: [language.locationSelect.labels.ohbGroup],
          4: [language.locationSelect.labels.machine],
          6: [selectController]
        };
      }
      // MACS4#0047 Add End
    }

    var initLocationList = this._filterLocationList(baseLocationList, filter);
    this.initLabelList = this._filterLabelList(baseLabelList, filter);

    this.mode = mode;
    this.labelComponent[0].text(language.locationCommon.labels.location);
    this.selectComponent[0].setList(initLocationList);
    var self = this;
    this._locationAjax(this, function() {
      self.clearList(2);
      self.clearList(1);
    }, function() {
      self.clearList(2);
      self.clearList(1);
    });
  },

  /**
   ******************************************************************************
   * @brief     ロケーションセレクトボックス要素にフィルタをかける
   * @param     {Array} locationList ロケーションセレクトボックス要素
   * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
   * @return    {Array} フィルタをかけた後のロケーションセレクトボックス要素
   * @retval
   * @attention
   * @note      filterが配列でない場合、フィルタをかけない
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _filterLocationList: function(locationList, filter) {
    // filterが配列でない場合、フィルタをかけない
    if (!Array.isArray(filter)) {
      return locationList;
    }

    // フィルタをかける
    var retList = locationList.filter(function(location) {
      return filter.includes(location[0]);
    });
    return retList;
  },

  /**
   ******************************************************************************
   * @brief     ロケーションラベルにフィルタをかける
   * @param     {Array} labelList ラベルリスト
   * @param     {Array} filter 表示するロケーションをセレクトボックスのvalue値で指定。指定なしは全表示
   * @return    {Object} フィルタをかけた後のラベルリスト
   * @retval
   * @attention
   * @note      filterが配列でない場合、フィルタをかけない
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _filterLabelList: function(labelList, filter) {
    // filterが配列でない場合、フィルタをかけない
    if (!Array.isArray(filter)) {
      return labelList;
    }

    // フィルタをかける
    var retList = {};
    for (var i = 0, len = filter.length; i < len; i++) {
      if (labelList.hasOwnProperty(filter[i])) {
        retList[filter[i]] = labelList[filter[i]];
      }
    }
    return retList;
  },

  /**
   ******************************************************************************
   * @brief     指定したjQuery要素にセレクトボックスを生成
   * @param
   * @return    {McsSelectBox} 生成したセレクトボックス
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createSelectBox: function() {
    var cssMargin = {
      'margin-bottom': '10px'
    };// 各要素にかけるcss
    var dataSelectDiv = $('<div class="mcs-locationselectbox-data"></div>').css(cssMargin);
    var selectBox = new McsSelectBox(dataSelectDiv, this.attr, this.prop);
    var selectLabel = $('<label class="mcs-select-label"></label>');
    this.selectComponent.push(selectBox);
    this.labelComponent.push(selectLabel);
    dataSelectDiv.prepend(selectLabel);
    this.dataDiv.append(dataSelectDiv);
    return selectBox;
  },

  /**
   ******************************************************************************
   * @brief     すべてのセレクト要素、エラーメッセージを初期化
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
  */
  clear: function() {
//  this.setLocation(this.mode, this.filter);                   // MACS4#0047 Del
    this.setLocation(this.mode, this.filter, this.macroMode);   // MACS4#0047 Add
    this.clearError();
  },

  /**
   ******************************************************************************
   * @brief     選択されているセレクト要素を配列で返す
   * @param
   * @return    {Array.<Object>} セレクトボックスのvalue値及びtext値
   * @retval
   * @attention
   * @note
   * データ形式：<br>
   *
   * <pre>
   *  [
   *    {value: 2段目の値, text: 2段目のテキスト},
   *    {value: 3段目の値, text: 3段目のテキスト}
   *  ]
   * </pre>
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValues: function() {
    var arrayData = [];
    for (var i = 1; i < this.selectComponent.length; i++) {
      if (this.selectComponent[i].getValue() !== null) {
        arrayData.push({
          value: this.selectComponent[i].getValue(),
          text: this.selectComponent[i].getText()
        });
      }
    }
    return arrayData;
  },

  /**
   ******************************************************************************
   * @brief     選択されているセレクト要素を配列で返す
   * @param
   * @return    {Array.<Object>} セレクトボックスのvalue値及びtext値
   * @retval
   * @attention
   * @note      getValuesを呼ぶ
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getValue: function() {
    return this.getValues();
  },

  /**
   ******************************************************************************
   * @brief     ロケーション選択時のAjax
   * @param     {McsLocationSelectBox} self このコンポーネント自身
   * @param     {Function} innerError サーバエラー時のコールバック
   * @param     {Function} ajaxError Ajaxエラー時コールバック
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
   ******************************************************************************
  */
  _locationAjax: function(self, innerError, ajaxError) {
    var url = getUrl('Com/McsLocation/GetLocationData');
    self.location = self.selectComponent[0].getValue();

    var data = {
      location: self.location,
      macroMode: self.macroMode,    // MACS4#0047 Add
      findAmhsId: self.findAmhsId,  // MACS4#0047 Add
      dispIFOhb: self.dispIFOhb     // MACS4#0160 Add
    };
    self.clearError();

    callAjax(url, JSON.stringify(data), false, function(retObj) {
      // 成功時
      // 各ラベルをセット
      self._setLabel(self.labelComponent, self.initLabelList, self.location);
      // ラベルが設定されていない部分を非活性に
      self._setSelectEnable(self.selectComponent, self.initLabelList[self.location].length);
      // セレクトボックスをセット
      for (var i = 1; i < self.selectComponent.length; i++) {
        if (retObj.body && retObj.body.length > 0 && retObj.body[i - 1]) {
          var selectArray = self._createSelectArray(retObj.body[i - 1].data);
          self.selectComponent[i].setList(selectArray);
        } else {
          self.selectComponent[i].setList([]);
        }
      }
    },
    // 失敗時
    innerError,
    // Ajaxエラー時
    ajaxError);
  },

  /**
   ******************************************************************************
   * @brief     ラベルをセット
   * @param     {Array.<jQuery>} labelComponent ラベルを集めた配列
   * @param     {Array.<string, Array.<number, string>>} labelList ラベルに入れるテキストを集めた配列
   * @param     {Number} location 選択されたロケーション
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _setLabel: function(labelComponent, labelList, location) {
    for (var j = 1; j < labelComponent.length; j++) {
      if (j > labelList[location].length) {
        labelComponent[j].html('&nbsp;');
      }
      labelComponent[j].text(labelList[location][j - 1]);
    }
  },

  /**
   ******************************************************************************
   * @brief     ラベルの表示・非表示を設定
   * @param     {Boolean} visible 表示状態。表示ならtrue、非表示ならfalse
   * @return    {McsLocationSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setLabelVisible: function(visible) {
    var i;
    if (visible) {
      for (i = 0; i < this.labelComponent.length; i++) {
        this.labelComponent[i].show();
      }
    } else {
      for (i = 0; i < this.labelComponent.length; i++) {
        this.labelComponent[i].hide();
      }
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     ラベルが設定されていない部分を非活性にする
   * @param     {Array.<McsSelectBox>} selectComponent セレクトボックスを集めた配列
   * @param     {Number} setLabelLength ラベルリストの長さ
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _setSelectEnable: function(selectComponent, setLabelLength) {
    for (var i = 0; i < selectComponent.length; i++) {
      selectComponent[i].setEnabled(true);
      if (i > setLabelLength) {
        selectComponent[i].setEnabled(false);
      }
    }
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックスの要素を生成
   * @param     {Array.<Object>} retData セットするセレクト要素のデータ。{value: 値, text: テキスト}の形式
   * @return    {Array} セレクトボックスにセットする配列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  _createSelectArray: function(retData) {
    var selectData = [];
    for (var i = 0; i < retData.length; i++) {
      var data = [retData[i].value, retData[i].text];
      selectData[i] = data;
    }
    return selectData;
  },

  /**
   ******************************************************************************
   * @brief     ロケーションセレクトボックスのチェンジイベントを設定
   * @param     {Function} innerError サーバエラー時のコールバック
   * @param     {Function} ajaxError Ajaxエラー時コールバック
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  initChangeLocation: function(innerError, ajaxError) {
    var self = this;
    self.selectComponent[0].onChange(function() {
      self._locationAjax(self, innerError, ajaxError);
    });
  },

  /**
   ******************************************************************************
   * @brief     １つ目のセレクトボックスのチェンジイベントを設定
   * @param     {Function} innerError サーバエラー時のコールバック
   * @param     {Function} ajaxError Ajaxエラー時コールバック
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
  */
  initChangeSelect: function(innerError, ajaxError) {
    var self = this;
    self.selectComponent[1].onChange(function() {
      var url = window.mcsComponentContextRoot + 'Com/McsLocation/GetLocationPortData';
      var data = {
        location: self.location,
        id: $(this).val(),
        mode: self.mode,
        macroMode: self.macroMode,    // MACS4#0047 Add
        findAmhsId: self.findAmhsId   // MACS4#0047 Add
      };
      self.clearError();
      callAjax(url, JSON.stringify(data, null, 4), true, function(retObj) {
        self.selectComponent[2].setList(self._createSelectArray(retObj.body[0].data));
      }, innerError, ajaxError);
    });
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックスの活性・非活性を設定
   * @param     {Number} index セレクトボックスのインデックス
   * @param     {Boolean} enabled 活性：true,非活性：false
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setSelectEnabled: function(index, enabled) {
    this.selectComponent[index].setEnabled(enabled);
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックスの活性・非活性を取得
   * @param     {Number} index セレクトボックスのインデックス
   * @return    {Boolean} 活性：true,非活性：false
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getSelectEnabled: function(index) {
    return this.selectComponent[index].getEnabled();
  },

  /**
   ******************************************************************************
   * @brief     このコンポーネントすべての活性・非活性を設定
   * @param     {Boolean} enabled 非表示:false,表示：true
   * @return    {McsLocationSelectBox} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  setEnabled: function(enabled) {
    for (var i = 0; i < this.selectComponent.length; i++) {
      this.setSelectEnabled(i, enabled);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     選択項目をすべて選択しているかどうかを調べる
   * @param
   * @return    {Boolean} 調査結果 すべて選択：true,一部未選択：false
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  checkAll: function() {
    var selectArray = this.getValues();
    var result = false;
    if (selectArray.length == this.countEnabled()) {
      return true;
    }

    return result;
  },

  /**
   ******************************************************************************
   * @brief     インデックス番号のセレクトボックスが選択されているかどうか調べる
   * @param     {Number} index セレクトボックスのインデックス番号
   * @param     {String} errorMsg エラーメッセージ
   * @return    {Boolean} 調査結果 選択済み：true,未選択：false,そもそも非活性：null
   * @retval
   * @attention
   * @note      エラーメッセージが引数にある場合はそのメッセージをセット
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  check: function(index, errorMsg) {
    // 非活性になっているセレクトボックスをチェックしようとした場合
    if (!this.getSelectEnabled(index)) {
      return null;
    }
    var data = this.getValues();
    var result = (data[index] != undefined);
    // エラーメッセージが引数に設定されている場合はそのメッセージをセット
    if (!result && errorMsg != undefined) {
      this.addCheckError(index, errorMsg);
    }
    return result;
  },

  /**
   ******************************************************************************
   * @brief     指定したインデックス番号のセレクトボックスにエラーメッセージを設定
   * @param     {Number} index メッセージを表示するセレクトボックスのインデックス
   * @param     {String} errorMsg エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  addCheckError: function(index, errorMsg) {
    this.selectComponent[index].addError(errorMsg);
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックス全体にエラーを設定
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
    // 全体的に追加
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    this.containerDiv.append(elm);
    this.containerDiv.find('.dropdown-toggle').addClass('mcs-component-errorBack');
  },

  /**
   ******************************************************************************
   * @brief     すべてのエラーメッセージを削除
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
    for (var i = 0; i < this.selectComponent.length; i++) {
      this.selectComponent[i].clearError();
    }
    this.containerDiv.find('.mcs-component-errorText').remove();
  },

  /**
   ******************************************************************************
   * @brief     セレクトボックスの活性要素の数を数える
   * @param
   * @return    {Number} 活性になっているセレクトボックスの数
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  countEnabled: function() {
    var count = 0;
    for (var i = 0; i < this.selectComponent.length; i++) {
      if (this.selectComponent[i].getEnabled()) {
        count++;
      }
    }
    return count;
  },

  /**
   ******************************************************************************
   * @brief     現在のモード(ロケーション入力:true,ロケーション選択：false)を取得
   * @param
   * @return    {Boolean} 現在のモード
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  getMode: function() {
    return this.mode;
  },

  /**
   ******************************************************************************
   * @brief     このコンポーネントを表示
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
  show: function() {
    this.containerDiv.show();
  },

  /**
   ******************************************************************************
   * @brief     このコンポーネントを非表示にする
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
  hide: function() {
    this.containerDiv.hide();
  },

  /**
   ******************************************************************************
   * @brief     指定したセレクトボックスの中身を空にする
   * @param     index 空にするセレクトボックスの番号
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
  */
  clearList: function(index) {
    this.selectComponent[index].clearList();
  },

  /**
   ******************************************************************************
   * @brief     指定のjQueryObjの配下にこの部品を追加
   * @param     {jQueryObj} jQueryObj jQueryオブジェクト
   * @return    {McsDateTime} このコンポーネント
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
  }
};
