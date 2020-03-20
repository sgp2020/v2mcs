﻿/**
 ******************************************************************************
 * @file        mcs-Table-BgColor.js
 * @brief       テーブルに関する部品
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        T
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/20   0.1                                         SGP
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief     テーブルコンポーネント
 * @param     {jQuery} containerDiv 格納先のdiv要素
 * @param     {Object} attr         tableに追加適用する属性
 * @param     {Object} prop         tableに追加適用する属性
 *  ※スクロール対応にてヘッダ部とデータ部のセル幅ズレが発生しているため
 *    セル幅固定で暫定対応中
 * @return
 * @retval
 * @attention
 * @note      DataTablesを利用しない簡易機能版のテーブルコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsTableBgColor = function(containerDiv, attr, prop) {
  // コンストラクタ
  this.init(containerDiv, attr, prop);
};
// メソッド類
McsTableBgColor.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化
   * @param     {jQuery} containerDiv 格納先のdiv要素
   * @param     {Object} attr         tableに追加適用する属性
   * @param     {Object} prop         tableに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note      初期化する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(containerDiv, attr, prop) {
    this.containerDiv = containerDiv;

    // 実際の要素（ヘッダ部分）を生成
    var headerTableWrapper = $('<div class="mcs-headerTable-wrapper"></div>');
    var headerTableWrapper2 = $('<div class="mcs-headerTable-wrapper2"></div>');
    var headerTable = $('<table class="mcs-headerTable table table-bordered table-hover"></table>');
    var headerThead = $('<thead class="mcs-table-head"></thead>');
    headerTableWrapper.append(headerTableWrapper2);
    headerTableWrapper2.append(headerTable);
    headerTable.append(headerThead);
    // 実際の要素（データ部分）を生成
    var tableWrapper = $('<div class="mcs-table-wrapper"></div>');
    var tableWrapper2 = $('<div class="mcs-table-wrapper2"></div>');
    var table = $('<table class="mcs-table table table-bordered table-hover" id="mcs-table"></table>');
    var thead = $('<thead></thead>');
    var tbody = $('<tbody class="mcs-table-body"></tbody>');
    if (attr)
      table.attr(attr);
    if (prop)
      table.prop(prop);
    tableWrapper.append(tableWrapper2);
    tableWrapper2.append(table);
    table.append(thead);
    table.append(tbody);
    this.headerTableWrapper = headerTableWrapper;
    this.headerTableWrapper2 = headerTableWrapper2;
    this.headerTable = headerTable;
    this.headerThead = headerThead;
    this.tableWrapper = tableWrapper;
    this.tableWrapper2 = tableWrapper2;
    this.table = table;
    this.thead = thead;
    this.tbody = tbody;
    // containerDivに追加する
    containerDiv.append(headerTableWrapper);
    containerDiv.append(tableWrapper);

    // スクロール時の動作を初期化
    var self = this;
    this.tableWrapper.on('scroll', function() {
      self._adjustScrollLeft();
    });
    // ウィンドウリサイズ時の動作を初期化
    $(window).on('resize', function() {
      self.resizeColWidth();
    });
    // チェックボックスのクリックイベントを無効化しておく
    this.tbody.on('click', 'input[type="checkbox"]', function(e) {
      e.preventDefault();
      return false;
    });
    // 行クリック時イベントを初期化
    this.initClickEvent();

    // 内部データ
    this.headerInfo = null;
    this.dataMap = {};
    this.addDataCount = 0;
    this.multiRowSelect = true;
    this.notRowSelect = false;

    this.tableWrapper.on('inview', function() {
      self.resizeColWidth();
    });
  },

  /**
   ******************************************************************************
   * @brief     クリックイベント初期化
   * @param
   * @return
   * @retval
   * @attention
   * @note      クリックイベントを初期化する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  initClickEvent: function() {
    // var tbody = this.tbody;
    var tbody = this.tbody;
    var self = this;

    tbody.on('click', 'tr', function() {
      var $this = $(this);
      var isSelected = $this.hasClass('info');
      if (self.notRowSelect) {
        // 行選択不可モード
      } else if (self.multiRowSelect) {
        // 複数選択モード（デフォルト）
        if (isSelected) {
          $this.removeClass('info');
        } else {
          $this.addClass('info');
        }
      } else {
        // 単一選択モード
        // 全ての選択行を一旦解除
        self.deselectAll();
        // クリックされた行の選択状態を切り替える
        if (!isSelected) {
          $this.addClass('info');
        }
      }
    });
  },

  /**
   ******************************************************************************
   * @brief     選択状態全解除
   * @param
   * @return
   * @retval
   * @attention
   * @note      選択状態を全て解除する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  deselectAll: function() {
    this.tbody.find('.info').removeClass('info');
  },

  /**
   ******************************************************************************
   * @brief     複数行選択設定
   * @param  {Boolean}
   *    multiRowSelect 複数選択ならtrue、単一行選択ならfalse
   * @return  {McsTableBgColor}  このコンポーネント
   * @retval
   * @attention
   * @note      複数行選択状態を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setMultiRowSelect: function(multiRowSelect) {
    this.multiRowSelect = multiRowSelect;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     行選択可否設定
   * @param  {Boolean}
   *    notRowSelect 行選択不可ならtrue、行選択可能ならfalse
   * @return  {McsTableBgColor}  このコンポーネント
   * @retval
   * @attention
   * @note      行選択の可否を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setNotRowSelect: function(notRowSelect) {
    this.notRowSelect = notRowSelect;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     データ部高さ設定
   * @param  {Number} height 高さ（ピクセル）
   * @return  {McsTableBgColor}  このコンポーネント
   * @retval
   * @attention
   * @note      データ部分の高さを設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setBodyHeight: function(height) {
    this.tableWrapper.height(height);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テーブルヘッダ設定
   * @param  {Array} headerInfoList ヘッダ情報のリスト
   * @return  {McsTableBgColor}  このコンポーネント
   * @retval
   * @attention
   * @note      テーブルのヘッダを設定する
   *     ヘッダ情報は以下の形式。
   *   {
   *     name: カラム名。データのキー名と対応する,
   *     text: 画面に出すテキスト,
   *     display: 画面に表示する場合はtrue、非表示の場合はfalse,
   *     align: 文字寄せ位置。'left'、'center'、'right'のいずれか。
   *            未指定の場合は'left'として扱う。
   *   }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setHeader: function(headerInfoList) {
    this.headerInfoList = headerInfoList;

    var i;
    var headerInfo;
    var display;
    // ヘッダ用テーブルにヘッダを設定
    var tr = $('<tr></tr>');
    for (i = 0; i < headerInfoList.length; i++) {
      headerInfo = headerInfoList[i];
      // var name = headerInfo.name;
      var text = headerInfo.text;
      display = headerInfo.display;
      if (display) {
        var th = $('<th class="mcs-headerTable-cell"></th>');
        th.text(text);
        tr.append(th);
      }
    }
    this.headerThead.empty();
    this.headerThead.append(tr);

    // データ用テーブルに、列幅調整の為にヘッダ行を登録
    var tr2 = $('<tr class="mcs-table-hiddenHead"></tr>');
    for (i = 0; i < headerInfoList.length; i++) {
      headerInfo = headerInfoList[i];
      display = headerInfo.display;
      if (display) {
        tr2.append($('<th></th>'));
      }
    }
    this.thead.empty();
    this.thead.append(tr2);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テーブルデータ１行追加
   * @param  {Object} data 追加するデータ
   * @param  {String} color 文字色（'#RRGGBB'）
   * @param  {Boolean} resizeColWidthFlag 列幅を調整するかどうかのフラグ。
   *        未設定かtrueが指定された場合、列幅を調整する。
   * @return   {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note      テーブルのデータを一行追加する。データは以下の形式。
   *     ただし、#col_xはsetHeaderで設定したカラム名に対応する。
   *     {
   *          #col_1: 値1,
   *          #col_2: 値2,
   *          #col_3: 値3,
   *             ...
   *          #col_N: 値4,
   *     }
   *    ※値がboolean値だった場合は、チェックボックスとして表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0158  一覧表示ハイパーリンク化抑制対応                       T.Iga/CSC
   ******************************************************************************
   */
  addData: function(data, color, resizeColWidthFlag) {
    var self = this;
    // テーブルに追加
    var headerInfoList = this.headerInfoList;
    var tr = $('<tr></tr>');
    // 管理用インデックスを要素にも付与しておく。
    // （データの取得時などに、インデックス値を取得できるようにするため）
    tr.attr('mcs-table-id', this.addDataCount);
    // 対応する列にデータを設定
    for (var i = 0; i < headerInfoList.length; i++) {
      var headerInfo = headerInfoList[i];
      var name = headerInfo.name;
      var display = headerInfo.display;
      var checkEditable = headerInfo.editable;
      // 非表示列なら値は設定しない
      if (!display) {
        continue;
      }
      // 値の設定
      var value = data[name];
//    var td = $('<td></td>');                                  // MACS4#0158 Del
      // Edge対応 - 電話番号になりえる文字列へのハイパーリンク化抑制処理
      var td = $('<td x-ms-format-detection="none"></td>');     // MACS4#0158 Add
      if (value !== null && typeof value === 'boolean') {
        // 値が真偽値だった場合はチェックボックスを表示
        var cb = $('<input type="checkbox">').prop('checked', value);
        cb.attr('mcs-tableCheckbox-id', this.addDataCount);
        cb.attr('mcs-tableCheckbox-name', name);
        if (checkEditable != null && checkEditable) {
          // 編集可能チェックボックス
          cb.on('click', function(e) {
            // クリックイベントのバブリング停止
            var mapId = $(this).attr('mcs-tableCheckbox-id');
            var headerName = $(this).attr('mcs-tableCheckbox-name');
            self.dataMap[mapId][headerName] = $(this).prop('checked');
            e.stopPropagation();
          });
        }
        td.append(cb);
      } else if (value !== null && name === 'sitemapButton') {
        // Header名称が"sitemapButton"の場合はappendでテーブル内にボタンを設置
        td.append(value);
      } else {
        // 真偽値以外ならそのまま出す
        td.text(value);
      }
      // 文字寄せ位置の設定
      switch (headerInfo.align) {
        case 'left':
          td.addClass('mcs-table-align-left');
          break;
        case 'center':
          td.addClass('mcs-table-align-center');
          break;
        case 'right':
          td.addClass('mcs-table-align-right');
          break;
        default:
          // 未指定の時は、leftと同じ
          td.addClass('mcs-table-align-left');
          break;
      }
      tr.append(td);
    }
    // 文字色設定
    if (color != null) {
      //tr.css('color', color);  //20200320  Del Song
    	tr.css('background-color', color);  //20200320  Add Song
    }
    // 生成した行をテーブルに追加
    this.tbody.append(tr);

    // 管理用に独自に番号(ID)をって格納
    this.dataMap[String(this.addDataCount)] = data;
    this.addDataCount++;

    // カラムサイズ調整
    if (resizeColWidthFlag === undefined || resizeColWidthFlag) {
      this.resizeColWidth();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     テーブルデータ複数行追加
   * @param  {Array} dataList データの配列
   * @param  {Array} colorList 色の配列（['#RRGGBB', '#RRGGBB', ...]
   * @return   {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note      テーブルのデータをまとめて追加する。データは以下の形式。
   *     ただし、#col_xはsetHeaderで設定したカラム名に対応する。
   *     [
   *         {
   *           #col_1: 値1,
   *           #col_2: 値2,
   *           #col_3: 値3,
   *              ...
   *           #col_N: 値4,
   *         },
   *         ...
   *     ]
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addDataList: function(dataList, colorList) {
    var data;
    var color;
    for (var i = 0; i < dataList.length; i++) {
      data = dataList[i];
      if (colorList != null && colorList[i] != null) {
        color = colorList[i];
      } else {
        color = null;
      }
      this.addData(data, color, false);
    }
    this.resizeColWidth();
    return this;
  },
  
  //20200102 Song Add Start For IndividualXXXMonitor.js
  addPortDataList: function(dataList, colorList) {
	    var data;
	    var color;
	    for (var i = 0; i < dataList.length; i++) {
	      data = dataList[i];
	      if (colorList != null && colorList[i] != null) {
	        color = colorList[i];
	      } else {
	        color = null;
	      }
	      this.addPortData(data, color, false);
	    }
	    this.resizeColWidth();
	    return this;
	  },
  addPortData: function(data, color, resizeColWidthFlag) {
	    var self = this;
	    // テーブルに追加
	    var headerInfoList = this.headerInfoList;
	    var tr = $('<tr></tr>');
	    // 管理用インデックスを要素にも付与しておく。
	    // （データの取得時などに、インデックス値を取得できるようにするため）
	    tr.attr('mcs-table-id', this.addDataCount);
	    // 対応する列にデータを設定
	    for (var i = 0; i < headerInfoList.length; i++) {
	      var headerInfo = headerInfoList[i];
	      var name = headerInfo.name;
	      var display = headerInfo.display;
	      var checkEditable = headerInfo.editable;
	      // 非表示列なら値は設定しない
	      if (!display) {
	        continue;
	      }
	      
	      //20191231 DQY ADD START
	      // 値の設定
	      var value = data[name];
	      // Edge対応 - 電話番号になりえる文字列へのハイパーリンク化抑制処理
	      var td = $('<td x-ms-format-detection="none"></td>');     // MACS4#0158 Add
	      switch( i )
		  {
			case 0:		// PortID
				td.text(value);
				td.css('color', '#F0F8FF'); // 文字色設定
				break;
			case 1:		// Mode
				if(value == 'Up')
				{
					// Enable
					td.text('Enable');
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '#00FF00');// 拝啓色設定 green
				}
				else if(value == 'Down')
				{
					// Disable
					td.text('Disable');
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#808080');// 拝啓色設定 gray
				}
				else if(value == 'Error')
				{
					// Hold
					td.text('Hold');
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#FF8000');// 拝啓色設定
				}
				else
				{
					td.css('background', '	#FF0000');// 拝啓色設定 red
				}
				break;
			case 2:		// Status
				if(value == 'Available')
				{
					// Available
					td.text(value);
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '#00FF00');// 拝啓色設定 green
				}
				else if(value == 'Avail')
				{
					// Avail
					td.text(value);
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#00FF00');// 拝啓色設定 green
				} 
				else if(value == 'NotAvailable')
				{
					// Not Available
					td.text(value);
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#FF0000');// 拝啓色設定 red
				}
				else if(value == 'NotAvail')
				{
					// Not Avail
					td.text(value);
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#FF0000');// 拝啓色設定 red 
				}
				else if(value == 'Down')
				{
					// Down
					td.text(value);
					td.css('color', '#000000'); // 文字色設定
					td.css('background', '	#FFFF00');// 拝啓色設定 yellow
				}
				break;
		  }
	      //20191231 DQY ADD END
		
	      // 文字寄せ位置の設定
	      switch (headerInfo.align) {
	        case 'left':
	          td.addClass('mcs-table-align-left');
	          break;
	        case 'center':
	          td.addClass('mcs-table-align-center');
	          break;
	        case 'right':
	          td.addClass('mcs-table-align-right');
	          break;
	        default:
	          // 未指定の時は、leftと同じ
	          td.addClass('mcs-table-align-left');
	          break;
	      }
	      tr.append(td);
	    }
	   
	    // 生成した行をテーブルに追加
	    this.tbody.append(tr);

	    // 管理用に独自に番号(ID)をって格納
	    this.dataMap[String(this.addDataCount)] = data;
	    this.addDataCount++;

	    // カラムサイズ調整
	    if (resizeColWidthFlag === undefined || resizeColWidthFlag) {
	      this.resizeColWidth();
	    }
	    return this;
	  },
  //20200102 Song Add End For IndividualXXXMonitor.js
	  
	  
  /**
   ******************************************************************************
   * @brief     ヘッダ幅調整
   * @param
   * @return
   * @retval
   * @attention
   * @note      ヘッダの幅を調整する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  resizeColWidth: function() {
    // 調整前のスクロール位置を記憶
    var scrollLeft = this.tableWrapper.scrollLeft();

    // 一旦、設定されている幅をすべてクリアする
    // （クリアすることで幅が自動調整され、テキストを表示できるちょうどの幅になる。）
    this.headerTableWrapper2.removeAttr('style');
    this.tableWrapper2.removeAttr('style');
    this.headerThead.find('th').each(function() {
      var $this = $(this);
      $this.removeAttr('style');
    });
    this.thead.find('th').each(function() {
      var $this = $(this);
      $this.removeAttr('style');
    });

    // ヘッダ用テーブルの列幅を取得する
    var headThList = [];
    var headRowWidthList = [];
    this.headerThead.find('th').each(function() {
      var $this = $(this);
      var width = $this.outerWidth();
      headThList.push($this);
      headRowWidthList.push(width);
    });
    // データ用テーブルの列幅を取得する
    var dataThList = [];
    var dataRowWidthList = [];
    this.thead.find('th').each(function() {
      var $this = $(this);
      var width = $this.outerWidth();
      dataThList.push($this);
      dataRowWidthList.push(width);
    });
    // ヘッダの列幅とデータの列幅で、大きい方に合わせて
    // セルの幅を設定
    var sum = 0;
    for (var i = 0; i < headRowWidthList.length; i++) {
      var width1 = headRowWidthList[i];
      var width2 = dataRowWidthList[i];
      var width = Math.max(width1, width2);
      sum += width;
      headThList[i].outerWidth(width);
      dataThList[i].outerWidth(width);
    }
    // 全体のdivのサイズを設定
    this.headerTableWrapper2.innerWidth(sum + 1);
    this.tableWrapper2.innerWidth(sum + 1);

    // スクロール位置を復元して、ヘッダとボディのスクロールを揃える
    this.tableWrapper.scrollLeft(scrollLeft);
    this._adjustScrollLeft();
  },

  /**
   ******************************************************************************
   * @brief     データ部スクロール
   * @param
   * @return
   * @retval
   * @attention
   * @note      データ部分スクロール時の動作
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _adjustScrollLeft: function() {
    var left = this.tableWrapper.scrollLeft();
    this.headerTableWrapper.scrollLeft(left);
  },

  /**
   ******************************************************************************
   * @brief     データ部横スクロール位置取得
   * @param
   * @return   {Number} 横スクロール位置
   * @retval
   * @attention
   * @note      データ部の横スクロール位置を取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollLeft: function() {
    return this.tableWrapper.scrollLeft();
  },

  /**
   ******************************************************************************
   * @brief     データ部横スクロール位置設定
   * @param  {Number} scrollLeft 横スクロール位置
   * @return   {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note      データ部の横スクロール位置を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollLeft: function(scrollLeft) {
    this.tableWrapper.scrollLeft(scrollLeft);
    this._adjustScrollLeft();
    return this;
  },

  /**
   ******************************************************************************
   * @brief     データ部縦スクロール位置取得
   * @param
   * @return   {Number} 縦スクロール位置
   * @retval
   * @attention
   * @note      データ部の縦スクロール位置を取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollTop: function() {
    return this.tableWrapper.scrollTop();
  },

  /**
   ******************************************************************************
   * @brief     データ部縦スクロール位置設定
   * @param  {Number} scrollTop 縦スクロール位置
   * @return   {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note      データ部の縦スクロール位置を設定する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollTop: function(scrollTop) {
    this.tableWrapper.scrollTop(scrollTop);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     行データ取得
   * @param  {jQueryObj} tr 行
   * @return   {Object} 内部データ
   * @retval
   * @attention
   * @note      指定行データを取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getDataFromRow: function(tr) {
    var id = tr.attr('mcs-table-id');
    var data = this.dataMap[id];
    return data;
  },

  /**
   ******************************************************************************
   * @brief     表データ配列取得
   * @param
   * @return   {Array} 表の値（addDataListで与える値と同じ形式）
   * @retval
   * @attention
   * @note      表の値を配列で取得する
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
   * @brief     表データ二次元配列取得
   * @param
   * @return   {Array} 表の値（addDataListで与える値と同じ形式）
   * @retval
   * @attention
   * @note      表の値を二次元配列で取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getValues: function() {
    var list = [];
    var self = this;
    this.tbody.children('tr').each(function() {
      var tr = $(this);
      list.push(self._getDataFromRow(tr));
    });
    return list;
  },

  /**
   ******************************************************************************
   * @brief     行データ文字列取得
   * @param
   * @return   {Array} 選択行のデータ（テキスト）
   * @retval
   * @attention
   * @note      選択されている行のデータを返す。
   *    何も選択されていないときはnullを返す。
   *    データはすべて文字列となっているので注意すること。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getSelectedRowData: function() {
    var obj = this.tbody.children('tr.info');
    if (obj.length == 0) {
      return null;
    }

    var list = [];
    var self = this;
    obj.each(function() {
      var tr = $(this);
      list.push(self._getDataFromRow(tr));
    });
    return list;
  },

  /**
   ******************************************************************************
   * @brief     表行数取得
   * @param
   * @return   {Number} 表の行数
   * @retval
   * @attention
   * @note      現在の表の行数を取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getRowCount: function() {
    return this.tbody.children('tr').length;
  },

  /**
   ******************************************************************************
   * @brief     行データ削除
   * @param    {jQueryObj} tr 行
   * @param    {Boolean} resizeColWidthFlag カラムのリサイズフラグ true：リサイズを行う
   * @return   {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note      指定した行データと、それに対応する内部データを削除する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _delFromRow: function(tr, resizeColWidthFlag) {
    var id = tr.attr('mcs-table-id');
    tr.remove();
    delete this.dataMap[id];
    // エラー情報削除
    this.containerDiv.find('.mcs-component-errorText').each(function() {
      var errorMes = $(this);
      // var errorRowIndex = errorMes.attr('mcs-table-error-rowindex');
      var errorRowId = errorMes.attr('mcs-table-error-rowid');
      if (errorRowId == id) {
        errorMes.remove();
      }
    });
    // カラム幅調整
    if (resizeColWidthFlag) {
      this.resizeColWidth();
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     選択行テーブル削除
   * @param
   * @return   {Number} 削除した行数、行が未選択だった場合は0
   * @retval
   * @attention
   * @note      選択されている行のテーブルを削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  delSelectedRowData: function() {
    // 選択されている行のオブジェクト取得
    var obj = this.tbody.children('tr.info');
    if (obj.length == 0) {
      return false;
    }

    var self = this;
    // var count = 0;
    obj.each(function() {
      var tr = $(this);
      self._delFromRow(tr, true);
    });
    return obj.length;
  },

  /**
   ******************************************************************************
   * @brief     指定行削除
   * @param  {Number} rowIndex 行インデックス
   * @return   {Boolean} 削除できた場合はtrue、対象が存在しなかった場合はfalse
   * @retval
   * @attention
   * @note      指定行を削除する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  delRow: function(rowIndex) {
    var tr = this.tbody.children('tr:eq(' + rowIndex + ')');
    if (tr.length == 0) {
      return false;
    }

    this._delFromRow(tr, true);
    return true;
  },

  /**
   ******************************************************************************
   * @brief     指定行一括削除
   * @param  {Array} rowIndexList 行インデックスのリスト
   * @return   {Number} 削除した行数
   * @retval
   * @attention
   * @note      指定の行をまとめて削除する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  delRows: function(rowIndexList) {
    var targetRows = [];
    var i;

    for (i = 0; i < rowIndexList.length; i++) {
      var tr = this.tbody.find('tr:eq(' + rowIndexList[i] + ')');
      if (tr.length != 0)
        targetRows.push(tr);
    }
    for (i = 0; i < targetRows.length; i++) {
      this._delFromRow(targetRows[i], false);
    }
    // カラム幅調整
    this.resizeColWidth();

    return targetRows.length;
  },

  /**
   ******************************************************************************
   * @brief     全データ削除
   * @param
   * @return
   * @retval
   * @attention
   * @note      全データをまとめて削除する（ヘッダはクリアされない。）
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.tbody.empty();
    this.dataMap = {};
    this.setScrollTop(0);
    this.setScrollLeft(0);
    // カラム幅調整
    this.resizeColWidth();
  },

  /**
   ******************************************************************************
   * @brief 選択行を一つ上へ移動
   * @return {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note  選択した行を一つ上へ移動させる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  rowUp: function() {
    var row = this.tbody.find('tr.info');
    row.each(function() {
      if ($(this).prev('tr')[0])
        $(this).insertBefore($(this).prev('tr'));
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief 選択行を一つ下へ移動
   * @return {McsTableBgColor} このコンポーネント
   * @retval
   * @attention
   * @note  選択した行を一つ下へ移動させる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  rowDown: function() {
    var row = this.tbody.find('tr.info');
    $(row.get().reverse()).each(function() {
      if ($(this).next('tr')[0])
        $(this).insertAfter($(this).next('tr'));
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     エラー表示
   * @param  {Number} rowIndex 行インデックス
   * @param  {String} errorMessage エラーメッセージ
   * @return
   * @retval
   * @attention
   * @note      指定行のエラー表示を行う。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addError: function(rowIndex, errorMessage) {
    if (rowIndex == null || rowIndex == undefined) {
      // レコードが一つもない場合に使用
      this.containerDiv.find('.mcs-table-wrapper').addClass('mcs-component-errorBack');
    }
    // 指定行の色を設定
    var obj = this.tbody.find('tr:eq(' + rowIndex + ')');
    var rowId = obj.attr('mcs-table-id');
    obj.addClass('mcs-component-errorBack');
    // メッセージを追加
    var elm = $('<div class="mcs-component-errorText"></div>').text(errorMessage);
    elm.attr('mcs-table-error-rowindex', rowIndex);
    elm.attr('mcs-table-error-rowid', rowId);
    this.containerDiv.append(elm);
  },

  /**
   ******************************************************************************
   * @brief     エラー情報設定
   * @param  {Number} rowIndex 行インデックス
   * @param  {Array} errorInfoList エラー情報リスト
   * @param  {Array} targetIdList エラー情報リストの中で、対象とするidのリスト
   * @param  {Array} textList エラーメッセージに付属させるテキストのリスト
   * @return
   * @retval
   * @attention
   * @note      エラー情報リストからエラーを設定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addErrorFromErrorInfoList: function(rowIndex, errorInfoList, targetIdList, textList) {
    for (var i = 0; i < targetIdList.length; i++) {
      var id = targetIdList[i];
      var errorInfo = this._getErrorInfoFromId(errorInfoList, id);
      if (errorInfo !== null) {
        var errorMessage = errorInfo.errorMessage;
        if (textList) {
          errorMessage = '(' + textList[i] + ') ' + errorMessage;
        }
        this.addError(rowIndex, errorMessage);
      }
    }
  },

  /**
   ******************************************************************************
   * @brief     エラー情報取得
   * @param  {Array}  errorInfoList エラー情報リスト
   * @param  {String} id ID
   * @return   {Object} errorInfoList[] エラー情報
   * @retval
   * @attention
   * @note      エラー情報リストから、指定のIDのエラー情報を取得する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getErrorInfoFromId: function(errorInfoList, id) {
    for (var i = 0; i < errorInfoList.length; i++) {
      if (errorInfoList[i].id == id) {
        return errorInfoList[i];
      }
    }
    return null;
  },

  /**
   ******************************************************************************
   * @brief     指定行エラークリア
   * @param  {Number} rowIndex 行インデックス
   * @return
   * @retval
   * @attention
   * @note      指定行のエラーをクリアする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearError: function(rowIndex) {
    var obj = this.tbody.find('tr:eq(' + rowIndex + ')');
    obj.removeClass('mcs-component-errorBack');
    this.containerDiv.find('.mcs-component-errorText').each(function() {
      var errorMes = $(this);
      var errorRowIndex = errorMes.attr('mcs-table-error-rowindex');
      if (errorRowIndex !== null && errorRowIndex != '' && Number(errorRowIndex) == rowIndex) {
        errorMes.remove();
      }
    });
  },

  /**
   ******************************************************************************
   * @brief     全エラークリア
   * @param
   * @return
   * @retval
   * @attention
   * @note      全てのエラーをクリアする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clearErrors: function() {
    this.containerDiv.find('.mcs-component-errorBack').removeClass('mcs-component-errorBack');
    this.containerDiv.find('.mcs-component-errorText').remove();
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
   * @brief    選択行のIndex取得
   * @param
   * @return   {Array} 選択行のIndex配列
   * @retval
   * @attention
   * @note     未選択時は空の配列を返す
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getSelectedRowIndex: function() {
    var selectedIndexArr = [];
    this.tbody.children('tr').each(function(i, elem) {
      if ($(elem).hasClass('info')) {
        selectedIndexArr.push(i);
      }
    });
    return selectedIndexArr;
  },

  /**
   ******************************************************************************
   * @brief    行選択処理
   * @param    {Array} indexArr 選択対象のIndex配列
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  selectRow: function(indexArr) {
    for (var i = 0; i < indexArr.length; i++) {
      this.tbody.children('tr:eq(' + indexArr[i] + ')').addClass('info');
    }
    return this;
  },

  end: 'end'
};
