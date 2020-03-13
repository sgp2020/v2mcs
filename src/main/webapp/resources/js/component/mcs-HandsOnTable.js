﻿/**
 ******************************************************************************
 * @file        mcs-HandsOnTable.js
 * @brief       アイコン等のグリッド表示に関する部品
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 * 2019/12/06 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief    コンストラクタ
 * @param    {jQuery} containerDiv 格納先のdiv要素
 * @param    {Boolean} enabled 編集可能かどうか true:編集可、false:編集不可
 * @attention
 * @note     アイコン等をグリッド表示するコンポーネント
 * ----------------------------------------------------------------------------
 *  VER.        DESCRIPTION                                              AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsHandsOnTable = function(containerDiv, enabled) {
  this._init(containerDiv, enabled);
};

// メソッド類
McsHandsOnTable.prototype = {
  /**
   ******************************************************************************
   * @brief   初期化処理
   * @param   {jQuery}  containerDiv         格納先のdiv要素
   * @param   {Boolean} enabled 編集可能かどうか true:編集可、false:編集不可
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _init: function(containerDiv, enabled) {
    this.containerDiv = containerDiv;
    this.isEnabled = enabled;

    // ラッパー生成
    this.wrapperDiv = $('<div class="hands-on-table-wrapper"/>');
    this.wrapperDiv.height(this.containerDiv.height()).width(this.containerDiv.width());
    this.wrapperDiv.appendTo(this.containerDiv);

    // ラベルの横幅計算用
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    this.calcWidthSpan = $('<span id="widthcheck" style="visibility:hidden;position:absolute;white-space:nowrap;"></span>');
    this.calcWidthSpan.appendTo(this.containerDiv);
  },

  /**
   ******************************************************************************
   * @brief   データ取得、テーブル生成処理
   * @param   {Object} options オプション類
   * @param   {Boolean} reloadFlag 再表示かどうか true:再表示、false:初期表示
   * @return
   * @retval
   * @attention
   * @note    引数ajaxOptionsに以下の通り値を設定する
   *          {
   *              url  : Ajax呼び出し先のURL,
   *              cond : 検索条件としてサーバに投げるオブジェクト,
   *              success : データ取得成功時のコールバック,
   *              serverError : サーバエラー時のコールバック,
   *              ajaxError : Ajaxエラー時のコールバック,
   *              judgeOnIconClick : アイコンクリック時のコールバック付与判定関数,
   *              onIconClick : アイコンクリック時のコールバック
   *          }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getDataAjax: function(ajaxOptions, reloadFlag) {
    var self = this;
    self.ajaxOptions = ajaxOptions;

    var onSuccess = function(resObj) {
      var createTable = (function(resObj) {
        self.data = resObj.body.data;
        var mergeCells = resObj.body.mergeCells;
        var customBorders = resObj.body.customBorders;
        var rowHeights = resObj.body.heights;
        var colWidths = resObj.body.widths;

        // 一番左のセルが縦に結合された時に、高さ指定が反映されないので
        // ヘッダのない編集不可時は、一番左にダミー列を追加する。
        if (!self.isEnabled) {
          for (var i = 0; i < self.data.length; i++) {
            self.data[i].col += 1;
          }
          for (var i = 0; i < mergeCells.length; i++) {
            mergeCells[i].col += 1;
          }
          for (var i = 0; i < customBorders.length; i++) {
            customBorders[i].col += 1;
          }
          colWidths.unshift(1);
        }

        // ダミーデータを生成
        var dummyData = [];
        for (var i = 0; i < rowHeights.length; i++) {
          dummyData[i] = [];
          for (var j = 0; j < colWidths.length; j++) {
            dummyData[i][j] = '';
          }
        }

        // 行列のサイズを計算
        self._calculateSize(self.data, rowHeights, colWidths);

        // 各種オプション設定
        var options = {
          // セル内のデータ（ダミーデータでテーブル生成を行い、生成後に本データを設定）
          data: dummyData,
          // セルのマージ情報
          mergeCells: mergeCells,
          // ボーダー情報
          customBorders: customBorders,
          // テーブル全体の高さ設定
          height: self.containerDiv.height(),
          // テーブル全体の幅設定
          width: self.containerDiv.width(),
          // 読み取り専用
          readOnly: true,
          // セル選択時のスクロールを無効化
          afterSelectionByProp: function(r, c, r2, c2, preventScrolling) {
            preventScrolling.value = true;
          },
          // 各行の高さ
          rowHeights: rowHeights,
          // 各列の幅
          colWidths: colWidths,
          // 行数分はレンダリングするように設定
          viewportRowRenderingOffset: rowHeights.length,
          // 列数分はレンダリングするように設定
          viewportColumnRenderingOffset: colWidths.length,
          // セル選択の無効（編集可：OFF、編集不可：ON）
          disableVisualSelection: !self.isEnabled,
          // 右クリックメニューの表示（編集可：ON、編集不可：OFF）
          contextMenu: self.isEnabled,
          // 行のヘッダー表示（編集可：ON、編集不可：OFF）
          rowHeaders: self.isEnabled,
          // 列のヘッダー表示（編集可：ON、編集不可：OFF）
          colHeaders: self.isEnabled,
          // 行の高さリサイズ機能（編集可：ON、編集不可：OFF）
          manualColumnResize: self.isEnabled,
          // 列の幅リサイズ機能（編集可：ON、編集不可：OFF）
          manualRowResize: self.isEnabled
        };

        // オプション設定を保持
        self.options = options;

        // スクロールバーの位置
        var scrollLeft = 0;
        var scrollTop = 0;

        // テーブル生成前：再表示時はスクロールバーの位置を記憶し、テーブルを削除する
        if (reloadFlag) {
          scrollLeft = self.getScrollLeft();
          scrollTop = self.getScrollTop();
          self.table.destroy();
        }

        // テーブルの生成
        self.table = new Handsontable(self.wrapperDiv.get(0), self.options);
        self.table.render();

        // テーブル生成後：再表示時はスクロールバーの位置を復元
        // 初期生成時は自動リサイズ処理を設定
        if (reloadFlag) {
          self.setScrollLeft(scrollLeft);
          self.setScrollTop(scrollTop);
        } else {
          self._setAutoResize();
        }

        // 本データの設定
        self._setData(self.data);

        // アイコンのクリックイベント設定
        self._onIconClick(self.ajaxOptions.onIconClick, self.ajaxOptions.judgeOnIconClick);

        // ボーダーのスタイル設定
        self._setBorderStyle(self.options.customBorders);

        // 検索処理成功時のコールバック実行
        if (self.ajaxOptions.success) {
          self.ajaxOptions.success(resObj);
        }
      });

      // フォントの読み込みが完了してからテーブル生成を行う
      if (self.fontLoaded) {
        createTable(resObj);
      } else {
        WebFont.load({
          custom: {
            families: [self.calcWidthSpan.css('font-family')]
          },
          active: function() {
            self.fontLoaded = true;
            createTable(resObj);
          }
        });
      }
    };

    var onServerError = function(resObj) {
      // 検索処理失敗時のコールバック実行
      if (self.ajaxOptions.serverError) {
        self.ajaxOptions.serverError(resObj);
      }
    };

    var onAjaxError = function(status, error) {
      // Ajax失敗時のコールバック実行
      if (self.ajaxOptions.ajaxError) {
        self.ajaxOptions.ajaxError(status, error);
      }
    };

    // Ajax通信実行
    // Step4 2017_08_24
    callAjax(self.ajaxOptions.url, self.ajaxOptions.cond, false, onSuccess, onServerError, onAjaxError, true,
        onSuccess, 0);
  },

  /**
   ******************************************************************************
   * @brief    行列のサイズ計算処理
   * @param    {Array} data セルデータリスト
   * @param    {Array} rowHeights サーバから送られた行の高さ設定
   * @param    {Array} colWidths サーバから送られた列の幅設定
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   *  MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
   ******************************************************************************
   */
  _calculateSize: function(data, rowHeights, colWidths) {
    // サイズの設定値
    var sizeSettings = {
      // HandsOnTable内での行高さのデフォルト
      minRowHeight: 23,
      // HandsOnTable内での列幅のデフォルト
//      minColWidth: 50,// MACS4#MACSV2 Del
      minColWidth: 150,// MACS4#MACSV2 Add
      // 最終の高さに足すマージン
      rowHeightMargin: 1,
      // 最終の幅に足すマージン
      colWidthMargin: 10,
      // ラベルの高さ
      labelHeight: 18,
      // 普通アイコンの高さ
      normalIconHeight: 62,
      // 普通アイコンの幅
//      normalIconWidth: 55,// MACS4#MACSV2 Del
      normalIconWidth: 155,// MACS4#MACSV2 Add
      // 横長アイコンの高さ
      wideIconHeight: 62,
      // 横長アイコンの幅
//      wideIconWidth: 75// MACS4#MACSV2 Del
      wideIconWidth: 175// MACS4#MACSV2 Add
    };

    // 行の計算用配列をHandsOnTableの行高さデフォルト値で初期化する
    var calcRowHeights = [];
    for (var i = 0; i < rowHeights.length; i++) {
      calcRowHeights.push(sizeSettings.minRowHeight);
    }

    // 列の計算用配列を列のマージン値で初期化する
    var calcColWidths = [];
    for (var i = 0; i < colWidths.length; i++) {
      calcColWidths[i] = [];
      for (var j = 0; j < rowHeights.length; j++) {
        calcColWidths[i].push(sizeSettings.colWidthMargin);
      }
    }

    for (var i = 0; i < data.length; i++) {
      // 各行において一番大きい要素の高さを配列に格納する
      calcRowHeights[data[i].row] = Math.max(calcRowHeights[data[i].row], sizeSettings.rowHeightMargin +
          this._calculateHeight(data[i], sizeSettings));

      // 各行毎の列内要素の幅を列の計算用配列に加算する
      calcColWidths[data[i].col][data[i].row] += this._calculateWidth(data[i], sizeSettings);
    }

    // 各列において一番大きい要素の幅を配列に格納する
    var maxCalcColWidths = [];
    for (var i = 0; i < calcColWidths.length; i++) {
      maxCalcColWidths[i] = sizeSettings.minColWidth;
      for (var j = 0; j < calcColWidths[i].length; j++) {
        maxCalcColWidths[i] = Math.max(maxCalcColWidths[i], calcColWidths[i][j]);
      }
    }

    // サーバからの高さ設定値と、計算した高さの内大きい方を実際の行の高さとする
    for (var i = 0; i < rowHeights.length; i++) {
      rowHeights[i] = Math.max(rowHeights[i], calcRowHeights[i]);
    }

    // サーバからの幅設定値と、計算した幅の内大きい方を実際の列の幅とする
    for (var i = 0; i < colWidths.length; i++) {
      colWidths[i] = Math.max(colWidths[i], maxCalcColWidths[i]);
    }

    // ダミー列の幅は1pxのままにしておく
    if (!this.isEnabled) {
      colWidths[0] = 1;
    }
  },

  /**
   ******************************************************************************
   * @brief    行の高さ計算処理
   * @param    {Object} cellData セル内データ要素
   * @param    {Object} sizeSettings サイズの設定値
   * @return   {Number} 行の高さ
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calculateHeight: function(cellData, sizeSettings) {
    if (cellData.label) {
      return sizeSettings.labelHeight;
    } else if (cellData.icon) {
      // アイコンの場合は、アイコンの種類から高さを求める
      return (cellData.icon.iconType == 6 || cellData.icon.iconType == 8) ? sizeSettings.wideIconHeight
          : sizeSettings.normalIconHeight;
    } else {
      return 0;
    }
  },

  /**
   ******************************************************************************
   * @brief    列の幅計算処理
   * @param    {Object} cellData セル内データ要素
   * @param    {Object} sizeSettings サイズの設定値
   * @return   {Number} 列の幅
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calculateWidth: function(cellData, sizeSettings) {
    if (cellData.label) {
      // ラベルの場合は、文字列の横幅を求める
      return Math.ceil(this._calculateStrWidth(cellData.label));
    } else if (cellData.icon) {
      // アイコンの場合は、アイコンの種類から幅を求める
      return (cellData.icon.iconType == 6 || cellData.icon.iconType == 8) ? sizeSettings.wideIconWidth
          : sizeSettings.normalIconWidth;
    } else {
      return 0;
    }
  },

  /**
   ******************************************************************************
   * @brief    文字列の幅計算処理
   * @param    {String} str 文字列
   * @return   {Number} 文字列の幅
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calculateStrWidth: function(str) {
    var width = this.calcWidthSpan.text(str).get(0).offsetWidth;
    this.calcWidthSpan.empty();
    return width;
  },

  /**
   ******************************************************************************
   * @brief    セルデータ設定処理
   * @param    {Array} セルデータリスト
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setData: function(data) {
    for (var i = 0; i < data.length; i++) {
      if (data[i].label) {
        $('.ht_master .htCore tbody tr:eq(' + data[i].row + ') td:eq(' + data[i].col + ')').append(
            $('<span class="hands-on-table-label"/>').text(data[i].label));
      } else if (data[i].icon) {
        $('.ht_master .htCore tbody tr:eq(' + data[i].row + ') td:eq(' + data[i].col + ')').append(
            $(data[i].icon.iconDomStr));
      }
    }
  },

  /**
   ******************************************************************************
   * @brief    自動リサイズ設定処理
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
  _setAutoResize: function() {
    var self = this;
    var timer = false;
    $(window).on('resize', function() {
      if (timer !== false) {
        // resizeイベントのたびに処理すると負荷が高いので、
        // setTimeoutをうまく使って300msの様子見時間を作る
        window.clearTimeout(timer);
      }
      timer = window.setTimeout(function() {
        self.updateSettings();
      }, 300);
    });
  },

  /**
   ******************************************************************************
   * @brief   テーブル設定更新処理
   * @param
   * @return
   * @retval
   * @attention
   * @note    リサイズ時等に使用する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  updateSettings: function() {
    // テーブルの表示領域を設定
    this.options.height = this.containerDiv.height();
    this.options.width = this.containerDiv.width();

    // テーブルの更新
    this.table.updateSettings(this.options);

    // データの設定
    this._setData(this.data);

    // アイコンのクリックイベント設定
    this._onIconClick(this.ajaxOptions.onIconClick, this.ajaxOptions.judgeOnIconClick);
  },

  /**
   ******************************************************************************
   * @brief    テーブル再表示処理
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
  reload: function() {
    this.getDataAjax(this.ajaxOptions, true);
  },

  /**
   ******************************************************************************
   * @brief     横スクロール位置取得処理
   * @param
   * @return    {Number} 横スクロール位置
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollLeft: function() {
    return this.wrapperDiv.find('.ht_master .wtHolder').scrollLeft();
  },

  /**
   ******************************************************************************
   * @brief    横スクロール位置設定処理
   * @param    {Number} value 横スクロール位置
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollLeft: function(value) {
    this.wrapperDiv.find('.ht_master .wtHolder').scrollLeft(value);
  },

  /**
   ******************************************************************************
   * @brief     縦スクロール位置取得処理
   * @param
   * @return    {Number} 縦スクロール位置
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollTop: function() {
    return this.wrapperDiv.find('.ht_master .wtHolder').scrollTop();
  },

  /**
   ******************************************************************************
   * @brief    縦スクロール位置設定処理
   * @param    {Number} value 縦スクロール位置
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollTop: function(value) {
    this.wrapperDiv.find('.ht_master .wtHolder').scrollTop(value);
  },

  /**
   ******************************************************************************
   * @brief    ボーダースタイル変更処理
   * @param    {Array} borders ボーダー設定
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setBorderStyle: function(borders) {
    for (var i = 0; i < borders.length; i++) {
      var targetBorder = $('.wtBorder.border_row' + borders[i].row + 'col' + borders[i].col);

      // top
      if (borders[i].top && borders[i].top.type != 0) {
        $(targetBorder.get(0)).addClass('change-border top type-' + ('00' + borders[i].top.type).slice(-2)).css({
          'border-width': borders[i].top.width + 'px',
          'height': '0px'
        });
      }

      // left
      if (borders[i].left && borders[i].left.type != 0) {
        $(targetBorder.get(1)).addClass('change-border left type-' + ('00' + borders[i].left.type).slice(-2)).css({
          'border-width': borders[i].left.width + 'px',
          'width': '0px'
        });
      }

      // bottom
      if (borders[i].bottom && borders[i].bottom.type != 0) {
        $(targetBorder.get(2)).addClass('change-border bottom type-' + ('00' + borders[i].bottom.type).slice(-2)).css({
          'border-width': borders[i].bottom.width + 'px',
          'height': '0px'
        });
      }

      // right
      if (borders[i].right && borders[i].right.type != 0) {
        $(targetBorder.get(3)).addClass('change-border right type-' + ('00' + borders[i].right.type).slice(-2)).css({
          'border-width': borders[i].right.width + 'px',
          'width': '0px'
        });
      }
    }
  },

  /**
   ******************************************************************************
   * @brief    jQueryオブジェクト→DOM文字列変換処理
   * @param    {jQuery} jQueryオブジェクト
   * @return   {String} DOM文字列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getDomStr: function(elm) {
    var parent = $('<div/>');
    parent.append(elm);
    return parent.html();
  },

  /**
   ******************************************************************************
   * @brief    アイコンクリック時のコールバック設定処理
   * @param    {Function} callback コールバック
   * @param    {Function} judgeTargetIcon 対象アイコン判定関数
   * @return
   * @retval
   * @attention
   * @note     コールバックに渡される引数：引数1=amhsId, 引数2=amhsType
   * @note     コールバックに渡される引数：引数1=displayName, 引数2=memberGroup
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
   ******************************************************************************
   */
  _onIconClick: function(callback, judgeTargetIcon) {
    // コールバックが設定されていれば、対象のアイコンにコールバックを設定
    if (callback) {
	
      var icon = $(this.containerDiv).find('div.container').filter(function(index) {
        return !judgeTargetIcon || judgeTargetIcon($(this));
      }).find('svg');
      icon.css('cursor', 'pointer').on('click', function(elem) {
//        var amhsId = $(elem.currentTarget).closest('div.container').find('input[name="amhsId"]').val();	  // MACS4#MACSV2 Del
//        var amhsType = $(elem.currentTarget).closest('div.container').find('input[name="amhsType"]').val(); // MACS4#MACSV2 Del
        var displayId = $(elem.currentTarget).closest('div.container').find('input[name="displayId"]').val();    // 20191225 DQY ADD
        var displayName = $(elem.currentTarget).closest('div.container').find('input[name="displayName"]').val();// MACS4#MACSV2 Add
        var memberGroup = $(elem.currentTarget).closest('div.container').find('input[name="memberGroup"]').val();// MACS4#MACSV2 Add
//        callback(displayId,displayName, memberGroup);// 20191225 DQY DEL
        callback(displayId,displayName, memberGroup);// 20191225 DQY ADD
      });
      
      //20191223 Song Add Start 
      var icon1 = $(this.containerDiv).find('div.container-cdc').filter(function(index) {
	        return !judgeTargetIcon || judgeTargetIcon($(this));
	      }).find('svg');
      icon1.css('cursor', 'pointer').on('click', function(elem) {
    	  var displayId = $(elem.currentTarget).closest('div.container-cdc').find('input[name="displayId"]').val();    // 20191225 DQY ADD
    	var displayName = $(elem.currentTarget).closest('div.container-cdc').find('input[name="displayName"]').val();  
    	var memberGroup = $(elem.currentTarget).closest('div.container-cdc').find('input[name="memberGroup"]').val();
//    	callback(displayName, memberGroup);// 20191225 DQY DEL
    	callback(displayId,displayName, memberGroup);// 20191225 DQY ADD
      });
      //20191223 Song Add End
    }
  }

};
