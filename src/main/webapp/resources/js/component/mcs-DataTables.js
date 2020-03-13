/**
 ******************************************************************************
 * @file        mcs-DataTables.js
 * @brief       グリッド表示に関する部品
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
 * 2018/04/18 MACS4#0158  一覧表示ハイパーリンク化抑制対応            T.Iga/CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief   コンストラクタ
 * @param   {jQuery}  containerDiv         格納先のdiv要素
 * @param   {Boolean} multiRowSelect       複数行選択できるかどうか
 * @param   {Object}  attr                 tableに追加適用する属性
 * @param   {Object}  prop                 tableに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note    グリッド表示を実現するコンポーネント 利用する場合、
 *           以下のような手順で使う。
 *             ・newする。第二引数にtrueを渡すと、複数行選択が可能になる。
 *             ・getDataAjaxに各オプションを指定して呼び出すと、McsDataTablesは
 *               サーバからデータを取得し、表を生成する。
 *           内部的には、以下の順でメソッドを呼び出して初期化している。
 *             ・_setHeaderでヘッダ、カラム情報を設定する
 *             ・_setDataでデータ行のテキストを設定する
 *             ・_toDataTablesでDataTablesを適用する
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsDataTables = function(containerDiv, multiRowSelect, attr, prop) {
  this._init(containerDiv, multiRowSelect, attr, prop);
};

// static変数
/**
 * クラス名を一意にするためのカウント
 *
 * @type {Number}
 */
McsDataTables.count = 0;
/**
 * カラム表示状態：表示
 *
 * @type {Number}
 */
McsDataTables.COLDISP_VISIBLE = 1;
/**
 * カラム表示状態：非表示
 *
 * @type {Number}
 */
McsDataTables.COLDISP_INVISIBLE = 0;
/**
 * カラム表示状態：隠しカラム
 *
 * @type {Number}
 */
McsDataTables.COLDISP_HIDDEN = 2;
/**
 * カラムの文字列の最大文字数
 *
 * @type {Number}
 */
McsDataTables.COL_MAX_LENGTH = 100;

// メソッド類
McsDataTables.prototype = {

  /**
   ******************************************************************************
   * @brief   初期化処理
   * @param   {jQuery}  containerDiv         格納先のdiv要素
   * @param   {Boolean} multiRowSelect       複数行選択できるかどうか
   * @param   {Object}  attr                 tableに追加適用する属性
   * @param   {Object}  prop                 tableに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _init: function(containerDiv, multiRowSelect, attr, prop) {
    this.containerDiv = containerDiv;
    this.multiRowSelect = multiRowSelect;
    this.attr = attr;
    this.prop = prop;
    // メインのtableを生成しておく
    this.table = $('<table class="table table-bordered table-hover mcs-datatables nowrap" style="width:100%"></table>');
    this.thead = $('<thead class="mcs-datatables-header"></thead>').appendTo(this.table);
    this.tbody = $('<tbody></tbody>').appendTo(this.table);
    if (attr)
      this.table.attr(attr);
    if (prop)
      this.table.prop(prop);

    // 行のクリックイベント処理を初期化
    this._initRowClickEvent();
    // チェックボックスのクリックイベント処理を初期化
    this._initCbClickEvent();
    // 自動更新の初期化
    this._initAutoReload();

    // containerDivに追加する
    containerDiv.append(this.table);
    // レコード数のカウント用
    this.dataRecordCount = 0;
    // 自動更新の状態
    this.autoReloadState = 'stop';
    // 自動更新のインターバル
    this.autoReloadInterval = null;
    // サニタイジング用の一時要素
    this.tmpSpan = $('<span></span>');
    // テーブルに表示するデータの保持用
    this.cellData = null;
    // カラム入れ替え中かどうかのフラグ
    this.isChangingColumnOrder = false;
  },

  /**
   ******************************************************************************
   * @brief   行クリックイベントの初期化
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
  _initRowClickEvent: function() {
    var tbody = this.tbody;
    var self = this;
    // 行選択のクリックイベントの後に処理し、コールバックを呼ぶ。
    tbody.on('click', 'tr', function() {
      var $this = $(this);
      var isSelected = $this.hasClass('selected');
      if (isSelected && self.onSelectRowCallback) {
        self.onSelectRowCallback(self._getRowsData($this[0]));
      } else if (!isSelected && self.onDeselectRowCallback) {
        self.onDeselectRowCallback(self._getRowsData($this[0]));
      }
    });
  },

  /**
   ******************************************************************************
   * @brief   チェックボックスのクリックイベントの初期化
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
  _initCbClickEvent: function() {
    // チェックボックスのクリックイベント（無効化）
    this.table.on('click', 'input[type="checkbox"]', function(event) {
      event.preventDefault();
      return false;
    });
  },

  /**
   ******************************************************************************
   * @brief   テーブルの解放
   * @param
   * @return  {McsDataTables} このコンポーネント自身
   * @retval
   * @attention このメソッドを実行した後、すべてのメソッドは利用できなくなります。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  destroy: function() {
    // 独自で設定したイベント類をアンバインドする
    $('.' + this.colvisButtonClassName).off('click');
    $('div.dt-button-background').off('click.mcsdatatables');
    this.table.off('click');
    // コンポーネントを削除
    if (this.pageButton) {
      this.pageButton.destroy();
      this.pageButton = null;
    }
    if (this.dataTables) {
      this.dataTables.destroy(true);
      this.dataTables = null;
    }
    if (this.pageRecordsSelectBoxWrapper) {
      this.pageRecordsSelectBoxWrapper.remove();
    }
    // テーブル削除
    this.table.remove();

    return this;
  },

  /**
   ******************************************************************************
   * @brief   ヘッダ、データすべて解放
   * @param
   * @return  {McsDataTables} このコンポーネント自身
   * @retval
   * @attention
   * @note    このメソッドを実行した後、再度
   *          _setHeader > _setData > _toDataTables
   *          でテーブルを生成することができる
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  clear: function() {
    this.destroy(); // 一旦すべて破壊
    this._init(this.containerDiv, this.multiRowSelect, this.attr, this.prop);
    return this;
  },

  // ---------------------------------------------------------------------
  // DataTables初期化関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   テーブルヘッダー設定
   * @param   {Array} headerList           ヘッダーIDと表示名のリスト
   * @return  {McsDataTables} このコンポーネント自身
   * @retval
   * @attention
   * @note    引数headerListの設定内容
   *            {
   *              columnId: 'unique column id',
   *              columnName: '表示するテキスト',
   *              columnDisplay: 表示は1、非表示は0、隠しカラムは2,
   *              columnAlign: 'left' or 'center' or 'right',
   *              isUniqueKey: 主キーならtrue
   *            }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setHeader: function(headerList) {
    // ヘッダーのth内部には、ヘッダーIDと表示名を両方保持している。
    // ヘッダーIDはinput type="hidden"、表示名はspanに入っている。

    // カラムIDのリスト（オリジナルの並び順）
    this.originalColumnIdList = [];
    // カラムIDのリスト（DataTablesの初期化時にcolumnsで渡す用）
    this.columnsIdList = [];
    // 非表示カラムの番号リスト
    this.invisibleColNumList = [];
    // 主キーのカラムIDのリスト
    this.columnUniqueKeyList = [];
    // 文字列寄せ：左のカラム（のインデックス）一覧
    this.textAlignLeftColumns = [];
    // 文字列寄せ：中央のカラム（のインデックス）一覧
    this.textAlignCenterColumns = [];
    // 文字列寄せ：右のカラム（のインデックス）一覧
    this.textAlignRightColumns = [];
    // カラムの並び順（表示順で並んでいるカラムインデックス）
    this.columnOrderList = new Array(headerList.length);

    var tr = $('<tr></tr>');
    for (var i = 0; i < headerList.length; i++) {
      var th = $('<th></th>');
      var colId = headerList[i].columnId;
      var colName = headerList[i].columnName;
      var colAlign = headerList[i].columnAlign;
      var colDisplay = headerList[i].columnDisplay;
      var colOrder = headerList[i].columnOrder;
      var isUniqueKey = headerList[i].isUniqueKey;

      // DOM生成
      var _colId = $('<input type="hidden" name="columnId">').val(colId);
      var _colDisplay = $('<input type="hidden" name="originalColumnDisplay">').val(colDisplay);
      var _colName = $('<span></span>').text(colName);
      th.append(_colId);
      th.append(_colDisplay);
      th.append(_colName);

      this.originalColumnIdList.push(colId);

      // DataTables初期化時用にカラムIDのリストを生成しておく
      this.columnsIdList.push({
        data: colId
      });

      // 非表示にするカラムの番号リストを生成しておく
      if (colDisplay != McsDataTables.COLDISP_VISIBLE)
        this.invisibleColNumList.push(i);

      // 隠しカラムはクラスを設定しておく
      if (colDisplay == McsDataTables.COLDISP_HIDDEN)
        th.addClass('mcs-datatables-column-hidden');

      // 主キーのカラムIDをリストにしておく
      if (isUniqueKey) {
        this.columnUniqueKeyList.push(headerList[i].columnId);
      }

      // カラムの並び順のリストを生成しておく
      this.columnOrderList[colOrder - 1] = i;

      // 文字寄せ位置ごとに、インデックスをリストにしておく
      switch (colAlign) {
        case 'left':
          this.textAlignLeftColumns.push(i);
          break;
        case 'center':
          this.textAlignCenterColumns.push(i);
          break;
        case 'right':
          this.textAlignRightColumns.push(i);
          break;
        default:
          this.textAlignLeftColumns.push(i);
          break;
      }

      tr.append(th);
    }
    this.thead.append(tr);
    this.headerList = headerList;

    return this;
  },

  /**
   ******************************************************************************
   * @brief   テーブルのデータをまとめて追加する
   * @param   {Array} data    データの配列
   * @return  {McsDataTables} このコンポーネント自身
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _setData: function(data) {
    this.cellData = data;
    this.dataRecordCount = data.length;

    var headerList = this.headerList;

    var length = data.length;
    for (var i = 0; i < length; i++) {
      var d = data[i];
      for (var j = 0; j < headerList.length; j++) {
        var colId = headerList[j].columnId;
        var val = d[colId];
        if (val === undefined || val === null) {
          // 存在しないカラムだった場合には空文字にする
          data[i][colId] = '';
        } else if (typeof val === 'boolean') {
          // 真偽値の場合はチェックボックスを生成
          // 高速化のため、jQueryを用いずに文字列で組み立てる
          var cbHtml = '<input type="checkbox" class="mcs-datatables-checkbox" value="' + val +
              '" mcs-datatables-checked="' + val + '" ' + (val ? 'checked="checked"' : '') + '>';
          var spanHtml = '<span style="display:none">' + val + '</span>';
          data[i][colId] = spanHtml + cbHtml;
        } else if (typeof val === 'number') {
          // 数値の場合、そのまま。
          data[i][colId] = val;
        } else if (typeof val === 'object') {
          // オブジェクトの場合はそのまま設定し、
          // renderやcreatedCellコールバックの中で生成する。
          data[i][colId] = val;
        } else {
          // その他（文字列や、関数など）の場合、100文字ごとに改行する。
          // また、HTMLタグをエスケープする。
          if (typeof val != 'string') {
            val = String(val);
          }
          // _getAutoReturnStrはHTMLサニタイジング処理も含む
          data[i][colId] = this._getAutoReturnStr(val, McsDataTables.COL_MAX_LENGTH);
        }
      }
    }

    return this;
  },

  /**
   ******************************************************************************
   * @brief   文字列中のhtmlを無害化する
   * @param   {String} str    文字列
   * @return  {String}        サニタイジングされた文字列
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _htmlSanitize: function(str) {
    return this.tmpSpan.text(str).html();
  },

  /**
   ******************************************************************************
   * @brief   固定文字数で自動改行する
   * @param   {String} str      文字列
   * @param   {Number} length   改行する長さ
   * @return  {String}          変換後の文字列（html）
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getAutoReturnStr: function(str, length) {
    var newStr = '';
    var lines = str.split(/\n/);
    for (var i = 0; i < lines.length; i++) {
      var line = lines[i];
      var strArray = this._splitByLength(line, length);
      // 行ごとに、自動改行する
      for (var j = 0; j < strArray.length; j++) {
        if (j !== 0) {
          newStr += '<br class="mcs-datatables-autoreturn">';
        }
        newStr += this._htmlSanitize(strArray[j]);
      }
      // 通常の改行を挿入
      newStr += '<br>';
    }
    return newStr;
  },

  /**
   ******************************************************************************
   * @brief   文字列を指定された文字数ごとに分割し、配列にします。
   * @param   {String} str      分割対象の文字列
   * @param   {Number} length   分割単位の文字数
   * @return  {Array.<string>}  分割された文字列。
   * @retval
   * @attention 文字列が空だった場合、空配列を返す。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _splitByLength: function(str, length) {
    if (!str || !length || length < 1) {
      return [];
    }
    var strLen = str.length;
    var count = 0;
    var startPos = 0;
    var ary = [];

    for (var i = 0; i < strLen; i++) {
      var code = str.charCodeAt(i);
      if (code > 255)
        count += 2;
      else
        count++;
      if (count >= length) {
        var endPos = i + 1;
        ary.push(str.substring(startPos, endPos));
        startPos = endPos;
        count = 0;
      }
    }
    // 末尾を追加
    var tail = str.substring(startPos, strLen);
    if (tail !== '') {
      ary.push(tail);
    }
    return ary;
  },

  /**
   ******************************************************************************
   * @brief   DataTables化する。
   * @param   {Object} options  オプション
   * @return
   * @retval
   * @attention
   * @note    引数 options に設定を渡す。設定内容は以下の通り。
   *    名前          型          設定内容
   * -----------------------------------------------------------------------------
   *    currentPage   {Number}    最初に開いているページのページ番号
   *    pageRecords   {Number}    1ページに表示するレコード数
   *    totalRecords  {Number}    全体のレコード数（画面に表示されない範囲も含めて）
   *    colOrder      {Array}     ソート条件。例：[[0, 'asc'], [1, 'desc']]
   *    rowColors     {Array}     色情報。例：[&quot;&quot;, &quot;#ff0000&quot;, ...]
   *    tableState    {Object}    テーブル復元情報。復元情報の有無のみチェックし、
   *                              復元情報が存在するときは、サーバから受け取ったカラム順、
   *                              カラム表示・非表示などの情報を無視する。
   *    onComplete    {Function}  テーブル生成およびデータの設定が完了したときの
   *                              コールバック。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _toDataTables: function(options) {
    var self = this;
    McsDataTables.count++;

    // このテーブル全体のクラス名。
    // 画面内でユニークにするため、McsDataTables.countの数値を付与する。
    this.mainClassName = 'mcs-datatables-wrapper-count-' + McsDataTables.count;

    // 表示切替ボタンのクラス名。
    // 画面内でユニークにするため、McsDataTables.countの数値を付与する。
    this.colvisButtonClassName = 'mcs-datatable-colvis-count-' + McsDataTables.count;

    // domオプションの生成
    // mcs-datatables-pagebuttons-wrapperはページボタンの追加先
    var dom = '<"mcs-datatables-wrapper ' + this.mainClassName + '" ' + '<"mcs-datatables-buttons-wrapper" ' +
        '<"mcs-datatables-buttons-left-wrapper" B>' + '<"mcs-datatables-caption">' +
        '<"mcs-datatables-buttons-right-wrapper" ' + '<"mcs-datatables-pagerecords-wrapper">' +
        '<"mcs-datatables-pagebuttons-wrapper">' + '>' + '>' + 't' + '>';

    // DataTables化のオプションを生成
    var dataTablesOptions = {
      // 件数切替機能 無効
      lengthChange: false,
      // 検索機能 無効
      searching: false,
      // 情報表示 無効
      info: false,
      // ページング機能 無効
      paging: false,
      // ソート機能 有効
      ordering: true,
      // 横スクロールバーを有効にする (scrollXはtrueかfalseで有効無効を切り替え)
      scrollX: true,
      // 縦スクロールバーを有効にする (scrollYは200, "200px"など「最大の高さ」を指定)
      scrollY: this._calcScrollHeight() + 'px',
      // 1ページに表示する件数
      pageLength: options.pageRecords,
      // 遅延描画OFF
      deferRender: false,

      // カラム機能の拡張
      colReorder: {
        realtime: false,
        reorderCallback: function() {
          // 入れ替え完了時のコールバック
          if (self.dataTables) {
            self.isChangingColumnOrder = false;
            AutoReloadTimerManager.start();

            var newColumnOrderStr = JSON.stringify(self.getColumnOrderMap());
            if (newColumnOrderStr != self.currentColumnOrderStr) {
              if (self.onChangeColumnOrderCallback) {
                self.onChangeColumnOrderCallback(self.getColumnOrderMap());
              }
              self.currentColumnOrderStr = newColumnOrderStr;
            }
          }
        },
        reorderCallback2: function() {
          // 入れ替え開始時のコールバック（datatablesを改造して追加）
          if (self.dataTables) {
            self.isChangingColumnOrder = true;
            AutoReloadTimerManager.stop();
            if (self.onStartColumnOrderCallback) {
              self.onStartColumnOrderCallback(self.getColumnOrderMap());
            }
          }
        }
      },
      // カラムごとの設定
      columnDefs: [{
        // クラスを設定して、カラムの文字寄せ位置を設定(左)
        className: 'mcs-datatables-align-left',
        targets: this.textAlignLeftColumns
      }, {
        // クラスを設定して、カラムの文字寄せ位置を設定(中央)
        className: 'mcs-datatables-align-center',
        targets: this.textAlignCenterColumns
      }, {
        // クラスを設定して、カラムの文字寄せ位置を設定(右)
        className: 'mcs-datatables-align-right',
        targets: this.textAlignRightColumns
      }, {
        targets: '_all',
        render: function(data, type, row, meta) {
          // セルごとに特殊な値を表示するため、render処理をフックする。
          // 参考：https://datatables.net/reference/option/columns.render

          // ここでの処理は、セルを描画するための処理として呼ばれる。
          // 対してcreatedCellは、この処理を通過した後、DOMが生成された後に呼ばれる。
          // 主にソートに対応するための処理を記載する。

          // MEMO 現状は style のみ対応。
          // 今後、特殊なコンポーネントを表示する場合は、ここでtypeごとの処理を実施する。

          if (typeof data === 'object' && data.type === 'style') {
            // スタイル設定のあるセルの処理
            if (data.text == null) {
              // 色コードでソートできるようにするため、色コードをdisplay:none;でセル値にする。
              return '<span style="display:none;">' + data.bgcolor + '</span>';
            } else {
              // テキストがある場合は、そのままテキストで設定しておく。
              return data.text;
            }
          }

          // 特殊な処理が必要ない値の場合は、そのまま表示する。
          return data;
        },
        createdCell: function(cell, cellData, rowData, rowIndex, colIndex) {
          // セルごとに特殊な値を表示するため、セル生成後に処理を実施する。
          var data = cellData;
          if (typeof data !== 'object') {
            // オブジェクト以外の値はそのまま表示するため、何もしない。
            return;
          }

          var $cell = $(cell);
          // オブジェクトのtypeによって、それぞれの処理を実施
          // MEMO 現状は style のみ対応。
          // 今後、特殊なコンポーネントを表示する場合は、ここでtypeごとの処理を実施する。

          if (data.type === 'style') {
            // スタイルを設定しつつ表示する処理
            if (data.bgcolor != null) {
              $cell.css('background-color', data.bgcolor);
            }
          }
        }
      }],
      // カラムの設定（カラムIDを設定する）
      columns: this.columnsIdList,
      // ボタンの配置
      // 表示切替ボタンの表示
      buttons: [{
        extend: 'colvis',
        className: 'mcs-datatable-colvis ' + this.colvisButtonClassName,
        columns: ':not(.mcs-datatables-column-hidden)'
      }],
      // 各パーツの配置などの設定
      dom: dom,
      // 画面に出すテキスト類
      language: McsDataTables.language,
      // 選択機能を有効に
      select: {
        items: 'row',
        style: this.multiRowSelect ? 'multi+shift' : 'single'
      }
    };

    // 色設定機能
    if (options.rowColors && options.rowColors.length != 0) {
      dataTablesOptions.createdRow = function(row, data, dataIndex) {
        // MEMO dataIndexは渡したデータ上のインデックス
        var color = options.rowColors[dataIndex];
        if (color) {
          $(row).css('color', color);
        }
      };
    }

    // ソート順を設定
    if (options.tableState.colSortOrder) {
      // tableStateに復元情報がある場合は、復元情報から設定
      var restoreColSortOrder = this._getRestoreColSortOrderInfo(options.tableState.colSortOrder);
      dataTablesOptions.order = restoreColSortOrder;
    } else {
      // それ以外の場合は、サーバからの情報を設定
      dataTablesOptions.order = options.colOrder;
    }

    // カラム順を設定
    if (options.tableState.colOrder) {
      // tableStateに復元情報がある場合は、復元情報から設定
      var restoreColOrder = this._getRestoreColOrderInfo(options.tableState.colOrder);
      dataTablesOptions.colReorder.order = restoreColOrder;
    } else {
      // それ以外の場合は、サーバからの情報を設定
      dataTablesOptions.colReorder.order = this.columnOrderList;
    }

    // カラム表示・非表示を設定
    if (options.tableState.colDisplay) {
      // tableStateに復元情報がある場合は、復元情報から設定
      var restoreColDisplay = this._getRestoreColDisplayInfo(options.tableState.colDisplay);
      dataTablesOptions.columnDefs.push({
        visible: false,
        targets: restoreColDisplay
      });
    } else {
      // それ以外の場合は、サーバからの情報を設定
      dataTablesOptions.columnDefs.push({
        visible: false,
        targets: this.invisibleColNumList
      });
    }

    // DataTables化
    // eslintでエラーが出るが、DataTablesの仕様のため無視する。
    this.dataTables = this.table.DataTable(dataTablesOptions);

    // DataTables化以降の初期化処理
    this._toDataTablesAfterInit(options);

    // データ設定（非同期で呼ぶ）
    setTimeout(function() {
      self.addDataToDataTables(self.cellData, options.onComplete);
    }, 1);
  },

  /**
   ******************************************************************************
   * @brief   DataTablesプラグインにデータを追加し、表示する。
   * @param   {Array} cellData 追加するデータ
   * @param   {Function} onComplete 完了時コールバック
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  addDataToDataTables: function(cellData, onComplete) {
    // Edgeで「応答なし」と表示される現象の軽減のため、
    // 100行ごとに追加する。
    var self = this;
    var step = 100;
    var add = function(index) {
      var start = index;
      var end = index + step;
      if (start < cellData.length) {
        var data = cellData.slice(start, end);
        setTimeout(function() {
          self.dataTables.rows.add(data);
          add(index + step);
        }, 1);
      } else {
        // 完了
        setTimeout(function() {
          self.dataTables.draw();
          setTimeout(function() {
            if (onComplete) {
              onComplete();
            }
          }, 1);
        }, 1);
      }
    };
    add(0);
  },

  /**
   ******************************************************************************
   * @brief   DataTables化以降の初期化処理
   * @param   {Object} options  toDataTablesに渡されたオプション
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _toDataTablesAfterInit: function(options) {
    // カラム入れ替え開始時のイベントはDataTables初期化時のreorderCallbackで設定済み
    // 変更を検出するため、現状の状態を保存
    this.currentColumnOrderStr = JSON.stringify(this.getColumnOrderMap());

    // 表示切替ボタン押下時の処理
    // 変更を検出するため、現状の状態を保存
    this.currentColumnDisplayStr = JSON.stringify(this.getColumnDisplayMap());

    var self = this;
    $('body').off('click.mcsdatatables');
    $('.' + this.colvisButtonClassName).on('click', function() {
      // 表示切替ボタンが押されたときに、背景（div.dt-button-background）にイベント設定し、
      // そこで変更イベントを検出する。
      AutoReloadTimerManager.stop();
      if (self.onOpenColVisibleCallback) {
        self.onOpenColVisibleCallback(self);
      }
      $('body').one('click.mcsdatatables', 'div.dt-button-background', function() {
        var timer = window.setInterval(function() {
          // アニメーションが終わったタイミングで、
          // 表示切替が閉じられたときの処理を実施する
          if (!self._isOpeningColvis()) {
            window.clearInterval(timer);
            AutoReloadTimerManager.start();
            if (self.onCloseColVisibleCallback) {
              self.onCloseColVisibleCallback(self);
            }
          }
        }, 100);
        var newColumnDisplayStr = JSON.stringify(self.getColumnDisplayMap());
        if (newColumnDisplayStr != self.currentColumnDisplayStr) {
          // カラム表示が変更されたとき
          if (self.onChangeColumnDisplayCallback) {
            self.onChangeColumnDisplayCallback(self);
          }
          self.currentColumnDisplayStr = newColumnDisplayStr;
        }
      });
    });

    // 表示情報保存ボタンを生成
    this._initSaveButton();

    // 表示件数のセレクトボックスを生成
    this._initPageRecordsSelectBox(options.pageRecords);
    // ページ切り替えボタンを生成
    this._initPageButton(options.totalRecords, options.pageRecords, options.currentPage);
    // 自動リサイズを有効にする
    this._initAutoResizeHeight();
    this.resetScrollHeight();

    // 横スクロールを先に適用しておく
    if (options.tableState)
      this.setScrollLeft(options.tableState.scrollLeft ? options.tableState.scrollLeft : 0);
  },

  /**
   ******************************************************************************
   * @brief   表示情報保存ボタンを生成する
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
  _initSaveButton: function() {
    var buttonWrapper = $('<div class="mcs-datatables-savebutton-wrapper"></div>');
    var button = new McsButton(buttonWrapper, McsDataTables.language.buttons.colsave.text);
    // 状態保存ボタンの表示／非表示フラグ
    if (this.isSaveInfoEnabled === undefined) {
      button.setEnabled(true);
    } else {
      button.setEnabled(this.isSaveInfoEnabled);
    }
    buttonWrapper.append(button);
    this.saveButton = button;
    var wrapper = this.containerDiv.find('.mcs-datatables-buttons-left-wrapper');
    wrapper.append(buttonWrapper);
    // イベントの設定
    var self = this;
    this.saveButton.onClick(function() {
      self.saveColumnInfo();
    });
  },

  /**
   ******************************************************************************
   * @brief   表示件数のセレクトボックスを初期化する。
   * @param   {Number} pageRecords 表示件数
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initPageRecordsSelectBox: function(pageRecords) {
    var selectBoxWrapper = $('<div class="mcs-datatables-pagelength-selectbox-wrapper"></div>');
    var selectBox = new McsSelectBox(selectBoxWrapper);
    selectBox.setList([[100, '100'], [200, '200'], [500, '500'], [1000, '1000']]);
    this.pageRecordsSelectBox = selectBox;
    this.pageRecordsSelectBoxWrapper = selectBoxWrapper;

    var wrapper = this.containerDiv.find('.mcs-datatables-pagerecords-wrapper');
    var beforeText = $('<span></span>').text(McsDataTables.language.mylengthMenu.before);
    var afterText = $('<span></span>').text(McsDataTables.language.mylengthMenu.after);
    wrapper.append(beforeText);
    wrapper.append(this.pageRecordsSelectBoxWrapper);
    wrapper.append(afterText);
    // 初期値設定
    this.pageRecordsSelectBox.clear();
    this.pageRecordsSelectBox.setValue(pageRecords);
    // イベントの設定
    var self = this;
    this.pageRecordsSelectBox.onChange(function() {
      self._onChangePageRecords();
      return true;
    });
    this.pageRecordsSelectBox.onShown(function() {
      AutoReloadTimerManager.stop();
      return true;
    });
    this.pageRecordsSelectBox.onHidden(function() {
      AutoReloadTimerManager.start();
      return true;
    });
  },

  /**
   ******************************************************************************
   * @brief   ページ切り替えボタンを生成し、mcs-datatables-pagebuttons-wrapperに追加する。
   * @param   {Number} totalRecords   総件数
   * @param   {Number} pageRecords    1ページあたりの件数
   * @param   {Number} currentPage    現在ページ
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initPageButton: function(totalRecords, pageRecords, currentPage) {
    var pageMax = Math.ceil(totalRecords / pageRecords);
    if (pageMax <= 0)
      pageMax = 1;
    if (currentPage <= 0)
      currentPage = 1;
    else if (currentPage > pageMax)
      currentPage = pageMax;

    var wrapper = this.containerDiv.find('.mcs-datatables-pagebuttons-wrapper');
    var pageButtonDiv = $('<div class="mcs-datatables-pagebuttons"></div>').css({
      display: 'inline-block'
    });

    var pageButton = new window.McsDataTablesPageButton(pageButtonDiv, this.dataTables);
    pageButton.setPageMax(pageMax);
    pageButton.setPageNum(currentPage);
    pageButton.setTotalRecords(totalRecords);

    var self = this;
    pageButton.setOnChangeCallback(function(pageNum) {
      self._onChangePage(pageNum);
    });

    wrapper.append(pageButtonDiv);

    this.pageButtonDiv = pageButtonDiv;
    this.pageButton = pageButton;
  },

  /**
   ******************************************************************************
   * @brief   高さの自動リサイズの初期化
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
  _initAutoResizeHeight: function() {
    var self = this;
    var timer = false;
    $(window).on('resize', function() {
      if (timer !== false) {
        // resizeイベントのたびに処理すると負荷が高いので、
        // setTimeoutをうまく使って300msの様子見時間を作る
        window.clearTimeout(timer);
      }
      timer = window.setTimeout(function() {
        self.resetScrollHeight();
      }, 300);
    });
  },

  /**
   ******************************************************************************
   * @brief   スクロール部分の高さを再設定
   * @param
   * @return  {McsDataTables} このコンポーネント自身
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  resetScrollHeight: function() {
    // リサイズ前コールバック実行
    if (this.onBeforeResizeCallback) {
      this.onBeforeResizeCallback(height);
    }

    // リサイズ実行
    this._getScrollBody().height(0);
    var height = this._calcScrollHeight();
    this._getScrollBody().height(height);
    this.dataTables.columns.adjust();

    // リサイズ後コールバック実行
    if (this.onResizedCallback) {
      var buttonAreaHeight = this._calcButtonAreaHeight();
      var headerHeight = this._calcHeaderHeight();
      this.onResizedCallback(height, buttonAreaHeight, headerHeight);
    }

    return this;
  },

  /**
   ******************************************************************************
   * @brief   ボタン領域の高さを計算して返す
   * @param
   * @return  {Number} ボタン領域の高さ（px）
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calcButtonAreaHeight: function() {
    var buttonsDiv = this.containerDiv.find('.mcs-datatables-buttons-wrapper');
    return buttonsDiv.outerHeight();
  },

  /**
   ******************************************************************************
   * @brief   テーブルのヘッダ部分の高さを返す
   * @param
   * @return  {Number} ヘッダ部分の高さ（px）
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calcHeaderHeight: function() {
    var headerDiv = this.containerDiv.find('.dataTables_scrollHead');
    return headerDiv.outerHeight();
  },

  /**
   ******************************************************************************
   * @brief   スクロール部分に設定するべき高さを計算して返す
   * @param
   * @return  {Number} スクロール部分に設定するべき高さ
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _calcScrollHeight: function() {
    var buttonAreaHeight = this._calcButtonAreaHeight();
    var headerHeight = this._calcHeaderHeight();
    var height = this.containerDiv.innerHeight() - buttonAreaHeight - headerHeight;
    return height;
  },

  // ---------------------------------------------------------------------
  // Ajaxによるテーブル生成関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   Ajaxでデータを取得して、テーブルを生成する。
   * @param   {Object} options オプション類
   * @return
   * @retval
   * @attention
   * @note    引数optionsに以下の通り値を設定する。
   *          {
   *              type : 'GET' or 'POST'（未指定時はGET）,
   *              url  : AjaxするURL,
   *              cond : 検索条件としてサーバに投げるオブジェクト,
   *              tableCompId : このグリッドのID。DB(TABLE_COMP_CONFIG_M)と一致する必要がある。
   *              success : データ取得成功時のコールバック,
   *              serverError : サーバーエラー時のコールバック,
   *              ajaxError : Ajaxエラー時のコールバック
   *          }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getDataAjax: function(options) {
    options.pageNum = 1;
    options.tableState = {
      scrollTop: 0
    };
    this._doAjax(options);
  },

  /**
   ******************************************************************************
   * @brief   Ajaxでデータを取得して、テーブルを生成する。
   * @param   {Object} options  オプション類
   * @return  {McsDataTables}   このコンポーネント自身
   * @retval
   * @attention
   * @note    引数optionsに以下の通り値を設定する。
   *          {
   *            type : 'GET' or 'POST'（未指定時はGET）,
   *            url  : AjaxするURL,
   *            cond : 検索条件としてサーバに投げるオブジェクト,
   *            pageNum : ページ番号
   *            success : データ取得成功時,
   *            serverError : サーバーエラー時のコールバック,
   *            ajaxError : Ajaxエラー時のコールバック,
   *            tableState : 復元用のテーブル状態
   *          }
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0158  一覧表示ハイパーリンク化抑制対応                       T.Iga/CSC
   ******************************************************************************
   */
  _doAjax: function(options) {
    // サーバに送信するデータ
    var data = {};
    $.extend(true, data, options.cond);
    data.pageNum = options.pageNum;
    var pageRecords = this.getPageRecords();
    if (pageRecords != 0)
      data.pageRecords = pageRecords;
    data.tableCompId = options.tableCompId;
    data.searchDataFlag = options.searchDataFlag;
    this.searchDataFlag = options.searchDataFlag;

    var self = this;
    var onSuccess = function(data) {
      // onSuccess または onEmpty
      // 成功時
      try {
        // 先頭ページ以外でデータがない場合は末尾ページを検索する
        if (data.pageInfo.currentPage != 1 && data.body.length == 0) {
          if (data.pageInfo.totalRecords <= 0) {
            // データゼロ件なので、前ページを取りに行っても結局0件となるため、
            // つじつま合わせをして終わる。
            options.pageNum = 1;
            data.pageInfo.currentPage = 1;
          } else {
            // データがある場合は、適切なページを再取得する
            options.pageNum = Math.ceil(data.pageInfo.totalRecords / data.pageInfo.pageRecords);
            self._doAjax(options);
            return; // Ajaxするので、継続処理は実施しない
          }
        }

        // DataTablesの生成が終わるまでローディング表示
        self.modalLoading.show(false);

        // 最新の検索時のオプション類を記憶しておく
        self.latestAjaxOptions = options;
        self._applyAjaxData(data, options.tableState, function() {
          if (options.tableState) {
            // 復元情報があれば、復元する
            self._restoreTableState(options.tableState);
          }
          options.success(data);
          self.resetScrollHeight();
          self.startTime = 0;

          // Edge対応 - 電話番号になりえる文字列へのハイパーリンク化抑制処理
          $(".dataTables_scrollBody td").attr("x-ms-format-detection", "none");     // MACS4#0158 Add
          // DataTablesの生成が終わればローディング非表示
          self.modalLoading.hide();
        });
      } catch (e) {
        console.log('apply error.');
        console.log(e);
        if (e.stack) {
          console.log(e.stack);
        }
        // DataTablesの生成に失敗したらローディングを非表示にする
        self.modalLoading.hide();
        // データベースの設定に誤りが無ければここのコードに到達することはないので
        // eslintでエラー（Unexpected alert）と判定されるが、無視する。
        alert('サーバからの取得値をMcsDataTablesに設定できませんでした。フォーマットを見直してください。');
        options.ajaxError('サーバからの取得値をMcsDataTablesに設定できませんでした。フォーマットを見直してください。', '200');
      }
    };
    var onError = function(data) {
      // onError
      // Ajaxは成功したがサーバ側でエラー判定された
      console.log('ajax戻り値エラー：' + data.result.message);
      options.serverError(data);
    };
    var onAjaxError = function(status, error) {
      // onFail
      options.ajaxError(status, error);
    };

    // ローディング表示を生成（最初の一回だけ）
    if (!this.modalLoading) {
      this.modalLoading = new McsLoading();
    }

    callAjax(options.url, data, options.searchDataFlag, onSuccess, onError, onAjaxError, true, onSuccess, 1, this.autoReloadFlag);

    this.autoReloadFlag = false;

    return this;
  },

  /**
   ******************************************************************************
   * @brief   Ajaxで取得した値から、DataTablesを生成する
   * @param   {Object}   data           Ajaxで取得した値
   * @param   {Object}   tableState     復元情報
   * @param   {Function} onComplete     DataTables生成完了時のコールバック
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _applyAjaxData: function(data, tableState, onComplete) {
    var self = this;
    self.clear();
    self._setHeader(data.header.columnInfoList);
    self._setData(data.body);
    self._toDataTables({
      currentPage: data.pageInfo.currentPage,
      pageRecords: data.pageInfo.pageRecords,
      totalRecords: data.pageInfo.totalRecords,
      colOrder: [],
      rowColors: data.rowColorList,
      tableState: tableState,
      onComplete: function() {
        // 自動リロードの開始。
        AutoReloadTimerManager.start();
        if (onComplete) {
          onComplete();
        }
      }
    });
  },

  /**
   ******************************************************************************
   * @brief   現在表示中のデータの検索条件を取得する。
   * @param
   * @return  {Object} データの検索条件
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getLatestCond: function() {
    var latestOptions = this.latestAjaxOptions;
    if (!latestOptions || !latestOptions.cond) {
      return {};
    } else {
      var cond = {};
      $.extend(true, cond, latestOptions.cond);
      return cond;
    }
  },

  // ---------------------------------------------------------------------
  // データ操作関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   選択されている行のテキストをオブジェクトで返す。
   * @param
   * @return  {Array} 選択行のテキストの情報。
   * @retval
   * @attention  何も選択されていないときはnullを返す。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getSelectedRowData: function() {
    var data = this._getRowsData({
      selected: true
    });
    if (data.length == 0) {
      return null;
    } else {
      return data;
    }
  },

  /**
   ******************************************************************************
   * @brief   DataTablesのrowDataからオブジェクトへ変換する
   * @param   {Object} rowData   行のデータ
   * @return  {Object}           行のデータが入ったオブジェクト
   * @retval
   * @attention  何も選択されていないときはnullを返す。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _rowDataToObj: function(rowData) {
    var data = {};
    for ( var colId in rowData) {
      if (rowData.hasOwnProperty(colId)) {
        var val = rowData[colId];
        if (typeof val === 'string' && val.indexOf('<input type="checkbox"') != -1) {
          // チェックボックスの場合はチェック状態を取得する
          var checked = $(val).attr('mcs-datatables-checked') === 'true';
          data[colId] = checked;
        } else if (typeof val === 'number') {
          // 数値の場合はそのまま返す。
          data[colId] = val;
        } else if (typeof val === 'object') {
          // オブジェクトの場合はそのまま返す。
          data[colId] = val;
        } else {
          // そのほかは文字列として返す。
          val = String(val);
          // 自動改行で挿入された<br>を削除する。
          val = val.replace(/<br class=\"mcs-datatables-autoreturn\">/g, '');
          // 自動改行以外の<br>を改行コードにする
          val = val.replace(/<br">/g, '\n');
          // エスケープされていたものを戻す
          val = this.tmpSpan.html(val).text();
          data[colId] = val;
        }
      }
    }
    return data;
  },

  /**
   ******************************************************************************
   * @brief   指定された行のデータを配列にして返す。
   * @param   {String} rowsTargetCond  dataTables.rowsで取得する際の条件。
   * @return  {Array}                  指定された行のデータをオブジェクトにしたもの
   * @retval
   * @attention  省略時は全行返す。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getRowsData: function(rowsTargetCond) {
    var list = [];

    // DataTablesが生成されていない場合は空の配列を返す
    // Step4 2017_08_18
    if (!this.dataTables) {
      return list;
    }
    var rowDataList = this.dataTables.rows(rowsTargetCond).data();
    for (var i = 0; i < rowDataList.length; i++) {
      var rowData = rowDataList[i];
      var data = this._rowDataToObj(rowData);
      list.push(data);
    }
    return list;
  },

  /**
   ******************************************************************************
   * @brief   選択されている行を削除する。
   * @param
   * @return  {Boolean} 削除できた場合はtrue、削除しなかった場合はfalse
   * @retval
   * @attention  選択されていないときは何もしない。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  deleteSelectedRow: function() {
    var rows = this.dataTables.rows({
      selected: true
    });
    if (rows.indexes().length === 0) {
      return false;
    }
    rows.remove();
    var scrollTop = this.getScrollTop();
    var scrollLeft = this.getScrollLeft();
    this.dataTables.draw();
    this.setScrollTop(scrollTop);
    this.setScrollLeft(scrollLeft);
    return true;
  },

  /**
   ******************************************************************************
   * @brief   全行を削除する。
   * @param
   * @return  {Boolean} 削除できた場合はtrue、削除しなかった場合はfalse
   * @retval
   * @attention  全行を削除する。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  deleteAllRow: function() {
    var rows = this.dataTables.rows({});
    if (rows.indexes().length === 0) {
      return false;
    }
    rows.remove();
    var scrollTop = this.getScrollTop();
    var scrollLeft = this.getScrollLeft();
    this.dataTables.draw();
    this.setScrollTop(scrollTop);
    this.setScrollLeft(scrollLeft);
    return true;
  },

  // ---------------------------------------------------------------------
  // テーブル状態保存・復元関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   ページ切り替えや自動更新後の復元用の情報を取得。
   * @param
   * @return  {Object} テーブル状態
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _storeTableState: function() {
    // カラム表示順
    var colOrder = this.getColumnOrderMap();

    // カラム表示・非表示
    var colDisplay = this.getColumnDisplayMap();

    // ソート条件
    var colSortOrder = this.getColumnSortOrderMap();

    // 選択行の情報を取得
    var selectedRowDataList = this.getSelectedRowData();
    var selectedRows = [];
    if (selectedRowDataList) {
      for (var i = 0; i < selectedRowDataList.length; i++) {
        var rowData = selectedRowDataList[i];
        var _rowData = {};
        for (var j = 0; j < this.columnUniqueKeyList.length; j++) {
          var key = this.columnUniqueKeyList[j];
          _rowData[key] = rowData[key];
        }
        selectedRows.push(_rowData);
      }
    }

    // スクロール位置
    var scrollTop = this.getScrollTop();
    var scrollLeft = this.getScrollLeft();

    return {
      colOrder: colOrder,
      colDisplay: colDisplay,
      colSortOrder: colSortOrder,
      selectedRows: selectedRows,
      scrollTop: scrollTop,
      scrollLeft: scrollLeft
    };
  },

  /**
   ******************************************************************************
   * @brief   DataTables初期化時に復元できない情報について、 テーブル状態から復元する。
   * @param   {Object} tableState   テーブル状態
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _restoreTableState: function(tableState) {
    // 行選択復元
    if (tableState.selectedRows && tableState.selectedRows.length > 0)
      this._restoreRowSelect(tableState.selectedRows);

    // スクロール位置復元
    this.setScrollTop(tableState.scrollTop ? tableState.scrollTop : 0);
    this.setScrollLeft(tableState.scrollLeft ? tableState.scrollLeft : 0);
  },

  /**
   ******************************************************************************
   * @brief   全カラムに対して処理する。
   * @param   {Function} func  処理の内容（コールバック）。
   * @return
   * @retval
   * @attention
   * @note    引数として、カラムのオブジェクト、カラムID、インデックスが渡される。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _colEach: function(func) {
    this.dataTables.columns().every(function() {
      var col = this;
      var colId = $(col.header()).find('input[name="columnId"]').val();
      var i = col.index();
      func(col, colId, i);
    });
  },

  /**
   ******************************************************************************
   * @brief   カラムのID一覧を取得する。
   * @param
   * @return  {Array} ヘッダーの名前のリスト
   * @retval
   * @attention 並び順は、現在の画面上での並びになる。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getColumnIdList: function() {
    var list = [];
    this._colEach(function(col, colId, idx) {
      list.push(colId);
    });
    return list;
  },

  /**
   ******************************************************************************
   * @brief   現在のカラムソート順をオブジェクト形式で返す
   * @param
   * @return  {Object} カラムソート順。{ カラムID : 'asc' or 'desc' } の形式。
   * @retval
   * @attention 並び順は、現在の画面上での並びになる。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getColumnSortOrderMap: function() {
    var sortMap = {};
    // 現在表示中の並びで、カラムIDを取得する
    var colIdList = this.getColumnIdList();
    // ソート取得
    var order = this.dataTables.order();
    for (var i = 0; i < order.length; i++) {
      var idx = order[i][0];
      var o = order[i][1];
      var colId = colIdList[idx];
      sortMap[colId] = o;
    }
    return sortMap;
  },

  /**
   ******************************************************************************
   * @brief   現在のカラム表示順をオブジェクト形式で返す。
   * @param
   * @return  {Object} カラム表示順序。{ カラムID : order } の形式。
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getColumnOrderMap: function() {
    var orderMap = {};
    this._colEach(function(col, colId, idx) {
      orderMap[colId] = idx;
    });
    return orderMap;
  },

  /**
   ******************************************************************************
   * @brief   現在のカラム表示状態をオブジェクト形式で返す。
   * @param
   * @return  {Object} 現在のカラム表示状態。{ カラムID : 0 or 1 or 2 } の形式。
   * @retval
   * @attention
   * @note    隠しカラムは引き続き隠しカラムとして返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getColumnDisplayMap: function() {
    var displayMap = {};
    var self = this;
    this._colEach(function(col, colId, idx) {
      // 現在の表示・非表示状態
      var visible = col.visible();
      // もともとのカラム表示状態（表示 or 非表示 or 隠しカラム）
      var originalColDisplay = self._getOriginalColDisplay(col);
      // 二つの状態を考慮して、現在のDisplayListを生成
      if (originalColDisplay == McsDataTables.COLDISP_HIDDEN) {
        // 隠しカラムの場合は、引き続き隠しカラム
        displayMap[colId] = McsDataTables.COLDISP_HIDDEN;
      } else {
        // 隠しカラム以外は、現状の表示・非表示を設定
        displayMap[colId] = visible ? McsDataTables.COLDISP_VISIBLE : McsDataTables.COLDISP_INVISIBLE;
      }
    });
    return displayMap;
  },

  /**
   ******************************************************************************
   * @brief   DataTablesのカラムオブジェクトから、初期表示時のカラム表示状態を取得する
   * @param   {Object} col    カラムオブジェクト
   * @return  {String}        初期表示時のカラム表示状態
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getOriginalColDisplay: function(col) {
    return $(col.header()).find('input[name="originalColumnDisplay"]').val();
  },

  /**
   ******************************************************************************
   * @brief   カラムソート順の復元情報から、DataTables初期化用の情報を生成する。
   * @param   {Object} colSortOrder カラムソート順の復元情報。
   *                                  { colId: 'asc' or 'desc' } の形式。
   * @return  {Array}  DataTables用カラム順指定。[[0, 'asc']] のような配列。
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getRestoreColSortOrderInfo: function(colSortOrder) {
    var headerList = this.headerList;
    var sortOrder = [];
    for (var i = 0; i < headerList.length; i++) {
      var colId = headerList[i].columnId;
      if (colSortOrder[colId]) {
        sortOrder.push([i, colSortOrder[colId]]);
      }
    }
    return sortOrder;
  },

  /**
   ******************************************************************************
   * @brief   カラム順序の復元情報から、DataTables初期化用の情報を生成する。
   * @param   {Object} colOrder   カラム順の復元情報。
   *                               { colId: index } の形式。
   * @return  {Array}  DataTables用カラム順指定。[0,1,2,...] のような配列。
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getRestoreColOrderInfo: function(colOrder) {
    var headerList = this.headerList;
    var order = new Array(headerList.length);
    for (var i = 0; i < headerList.length; i++) {
      var colId = headerList[i].columnId;
      var orderIndex = colOrder[colId];
      order[orderIndex] = i;
    }
    return order;
  },

  /**
   ******************************************************************************
   * @brief   カラム表示・非表示の復元情報から、DataTables初期化用の情報を生成する。
   * @param   {Object} colDisplay カラム表示・非表示の復元情報。
   *                               { colId: columnDisplay } の形式。
   * @return  {Array}  DataTables用のカラム非表示指定。
   *                    [0,1,2,...] のような、非表示のカラムインデックス一覧。
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getRestoreColDisplayInfo: function(colDisplay) {
    // { 'visible': false, 'targets': this.invisibleColNumList}
    var headerList = this.headerList;
    var hiddenList = [];
    for (var i = 0; i < headerList.length; i++) {
      var colId = headerList[i].columnId;
      var disp = colDisplay[colId];
      if (disp != McsDataTables.COLDISP_VISIBLE)
        hiddenList.push(i);
    }
    return hiddenList;
  },

  /**
   ******************************************************************************
   * @brief   行選択状態を復元する
   * @param   {Array} selectedRows   行選択の情報
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _restoreRowSelect: function(selectedRows) {
    // 条件に合致する行を取得し、選択状態にする
    var self = this;
    var rows = this.dataTables.rows(function(idx, data, node) {
      data = self._rowDataToObj(data);
      for (var j = 0; j < selectedRows.length; j++) {
        var flag = true;
        for ( var key in selectedRows[j]) {
          if (selectedRows[j].hasOwnProperty(key)) {
            var val1 = selectedRows[j][key];
            var val2 = data[key];
            // オブジェクトの場合は文字列化しておく
            // （オブジェクト同士を == で比較しても、中身の比較はできないため）
            if (typeof val1 === 'object') {
              try {
                val1 = JSON.stringify(val1);
                val2 = JSON.stringify(val2);
              } catch (e) {
                flag = false;
                break;
              }
            }
            // 比較
            if (val1 != val2) {
              flag = false;
              break;
            }
          }
        }
        if (flag) {
          return true;
        }
      }
      return false;
    });
    rows.select();
  },

  /**
   ******************************************************************************
   * @brief   現在の縦スクロール位置を返す
   * @param
   * @return  {Number} 現在の縦スクロール位置
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollTop: function() {
    var bodyDiv = this._getScrollBody();
    return bodyDiv.scrollTop();
  },

  /**
   ******************************************************************************
   * @brief   現在の横スクロール位置を返す
   * @param
   * @return  {Number} 現在の横スクロール位置
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getScrollLeft: function() {
    var bodyDiv = this._getScrollBody();
    return bodyDiv.scrollLeft();
  },

  /**
   ******************************************************************************
   * @brief   縦スクロール位置を設定する
   * @param   {Number}   scrollTop  スクロール位置
   * @param   {Boolean}  animation  trueなら滑らかに移動する。デフォルトはfalse。
   * @param   {Function} onComplete アニメーション完了時のコールバック
   * @return  {McsDataTables}       このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollTop: function(scrollTop, animation, onComplete) {
    var bodyDiv = this._getScrollBody();
    if (animation) {
      bodyDiv.animate({
        scrollTop: scrollTop
      }, 200, 'swing', onComplete);
    } else {
      bodyDiv.scrollTop(scrollTop);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief   横スクロール位置を設定する
   * @param   {Number}   scrollLeft スクロール位置
   * @param   {Boolean}  animation  trueなら滑らかに移動する。デフォルトはfalse。
   * @param   {Function} onComplete アニメーション完了時のコールバック
   * @return  {McsDataTables}       このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setScrollLeft: function(scrollLeft, animation, onComplete) {
    var bodyDiv = this._getScrollBody();
    if (animation) {
      bodyDiv.animate({
        scrollLeft: scrollLeft
      }, 200, 'swing', onComplete);
    } else {
      bodyDiv.scrollLeft(scrollLeft);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief   スクロールする部分のjQueryオブジェクトを返す
   * @param
   * @return  {jQueryObj}  スクロールする部分
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _getScrollBody: function() {
    return this.containerDiv.find('.dataTables_scrollBody');
  },

  // ---------------------------------------------------------------------
  // ページ切り替え、自動リロード関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   ページが変更された時の動作（内部動作）。
   * @param   {Number}  pageNum ページ番号
   * @return
   * @retval
   * @attention ページボタン部品から呼ばれる。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _onChangePage: function(pageNum) {
    // コールバックを呼ぶ
    if (this.onChangePageCallback) {
      this.onChangePageCallback(pageNum, this);
    }

    // 直前のAjax時のオプションを取得
    var options = this.latestAjaxOptions;
    // ページ番号を新しいページ番号にする
    options.pageNum = pageNum;
    // 復元情報を取得して設定する。
    options.tableState = this._storeTableState();
    // 復元情報のうち、スクロール位置、行選択状態はクリアする
    options.tableState.scrollTop = 0;
    options.tableState.scrollLeft = 0;
    options.tableState.selectedRows = undefined;
    // Ajax実施
    this._doAjax(options);
  },

  /**
   ******************************************************************************
   * @brief   再読み込みする。
   * @param
   * @return  {McsDataTables|Boolean} 再描画実行時：このコンポーネント、再描画未実行時：false
   * @retval
   * @attention
   * @note    内部処理としては、 サーバへ現在表示しているデータを取得した時と
   *          同じリクエストを送り、 テーブルを再構成します。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  reload: function() {
    // DataTablesが生成されていない場合はfalseを返す
    // Step4 2017_08_18
    if (!this.dataTables) {
      this._showNotCreatedTableDialog();
      return false;
    }

    // コールバックを呼ぶ
    if (this.onAutoReloadCallback) {
      this.onAutoReloadCallback(this);
    }
    // 直前のAjax時のオプションを取得
    var options = this.latestAjaxOptions;
    if (!options)
      options = {};
    // 復元情報を取得して設定する。
    options.tableState = this._storeTableState();
    // Ajax実施
    this._doAjax(options);

    return this;
  },

  /**
   ******************************************************************************
   * @brief   テーブル未生成時のエラーダイアログを表示。
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
  // Step4 2017_08_18
  _showNotCreatedTableDialog: function() {
    if (!this._showNotCreatedTableDialogFlag) {
      this._showNotCreatedTableDialogFlag = true;
      var div = $('<div></div>').appendTo('body');
      this._notCreatedTableDialog = new McsDialog(div, window.mcsDialogTitleError);
    }
    var dialog = this._notCreatedTableDialog;
    dialog.openAlert(McsDataTables.language.notCreatedTable, window.mcsDialogBtnReturn, 'error');
  },

  /**
   ******************************************************************************
   * @brief   自動リロードの時間が来たときの処理
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
  _onAutoReload: function() {
    this.autoReloadFlag = true;
    this.reload();
  },

  /**
   ******************************************************************************
   * @brief   自動更新の初期化
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
  _initAutoReload: function() {
    if (!this._initAutoReloadDoneFlag) {
      var self = this;

      // 自動更新可否チェック用コールバック生成
      self.autoReloadCheckCallback = function() {
        return self._canStartAutoReload();
      };
      // 自動更新タイマー時コールバック生成
      self.autoReloadTimeoutCallback = function() {
        self._onAutoReload();
      };

      AutoReloadTimerManager.addCheckCallback(self.autoReloadCheckCallback);
      AutoReloadTimerManager.addTimeoutCallback(self.autoReloadTimeoutCallback);
      this._initAutoReloadDoneFlag = true;
    }
  },

  /**
   ******************************************************************************
   * @brief   自動更新の有効無効を設定
   * @param   {Boolean} enabled  有効ならtrue、無効ならfalse
   * @return  {McsDataTables}    このコンポーネント自身
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setAutoReloadEnabled: function(enabled) {
    if (enabled) {
      // 有効化する
      this._initAutoReload();
    } else {
      // 無効化する
      AutoReloadTimerManager.deleteCheckCallback(this.autoReloadCheckCallback);
      AutoReloadTimerManager.deleteTimeoutCallback(this.autoReloadTimeoutCallback);
      this._initAutoReloadDoneFlag = false;
      this.autoReloadCheckCallback = undefined;
      this.autoReloadTimeoutCallback = undefined;
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief   自動更新が可能かどうかを返す。
   * @param
   * @return  {Boolean} 自動更新OKならtrue、NGならfalse
   * @retval
   * @attention
   * @note    AutoReloadTimerManagerにコールバックとして設定する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _canStartAutoReload: function() {
    var canStart = this.dataTables && !this._isOpeningColvis() && !this.isChangingColumnOrder &&
        !this.pageRecordsSelectBox.isOpening() && this.searchDataFlag;
    return canStart;
  },

  /**
   ******************************************************************************
   * @brief   表示切替のウィンドウが表示されているかどうかチェックする。
   * @param
   * @return  {Booelan} 開かれているならtrue
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _isOpeningColvis: function() {
    var back = $('div.dt-button-background');
    return back.length != 0 && back.is(':visible');
  },

  // ---------------------------------------------------------------------
  // 表示件数関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   表示件数が変更されたときの処理。
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
  _onChangePageRecords: function() {
    // 直前のAjax時のオプションを取得
    var options = this.latestAjaxOptions;
    if (!options)
      options = {};
    // 復元情報を取得して設定する。
    options.tableState = this._storeTableState();
    // 復元情報のうち、スクロール位置、行選択状態はクリアする
    options.tableState.scrollTop = 0;
    options.tableState.scrollLeft = 0;
    options.tableState.selectedRows = undefined;
    // ページは1ページ目へ移動
    options.pageNum = 1;
    // Ajax実施
    this._doAjax(options);
  },

  /**
   ******************************************************************************
   * @brief   表示件数を取得する。
   * @param
   * @return  {Number} 表示件数。
   *                   画面にまだ表示件数セレクトボックスが生成されていないときは0。
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getPageRecords: function() {
    if (this.pageRecordsSelectBox) {
      return Number(this.pageRecordsSelectBox.getValue());
    }
    return 0;
  },

  /**
   ******************************************************************************
   * @brief   表示情報を保存する。
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
  saveColumnInfo: function() {
    var colOrderMap = this.getColumnOrderMap();
    var colDispMap = this.getColumnDisplayMap();
    var originalColIdList = this.originalColumnIdList;

    // カラムの表示状態と順番を取得
    var colOrderArray = [];
    var colDispArray = [];
    for (var i = 0; i < originalColIdList.length; i++) {
      var colId = originalColIdList[i];
      var order = colOrderMap[colId] + 1;
      var disp = colDispMap[colId];
      colOrderArray.push(order);
      colDispArray.push(disp);
    }

    // 表示件数を取得
    var pageRecords = this.getPageRecords();

    var data = {
      tableCompId: this.latestAjaxOptions.tableCompId,
      columnOrderList: colOrderArray,
      columnDisplayList: colDispArray,
      pageRecords: pageRecords
    };
    var self = this;
    callAjax(getUrl('Com/McsDataTables/SaveColumnInfo'), data, false, function(data) {
      self._showSaveColumnInfoDialog();
    });
  },

  /**
   ******************************************************************************
   * @brief   表示情報保存の完了ダイアログを表示。
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
    dialog.openAlert(McsDataTables.language.buttons.colsave.success, window.mcsDialogBtnReturn, 'info');
  },

  /**
   ******************************************************************************
   * @brief   状態保存ボタンの活性／非活性を設定する。
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
  setSaveInfoBtnEnabled: function(enable) {
    // 状態保存ボタンの活性／非活性フラグ
    this.isSaveInfoEnabled = enable;
  },

  // ---------------------------------------------------------------------
  // テキスト関係
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   キャプションを設定します。
   * @param   {String} text キャプションのテキスト
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setCaption: function(text) {
    var div = this.containerDiv.find('.mcs-datatables-caption');
    div.text(text);
  },

  // ---------------------------------------------------------------------
  // イベントリスナ関連
  // ---------------------------------------------------------------------

  /**
   ******************************************************************************
   * @brief   行選択時のコールバックを設定する。
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onSelectRow: function(callback) {
    this.onSelectRowCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   行選択解除時のコールバックを設定する。
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onDeselectRow: function(callback) {
    this.onDeselectRowCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   表示切替が開かれた時のイベントコールバックを設定する。
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onOpenColVisible: function(callback) {
    this.onOpenColVisibleCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   表示切替が閉じられた時のイベントコールバックを設定する。
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onCloseColVisible: function(callback) {
    this.onCloseColVisibleCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   カラム表示非表示が変更されたときのイベントコールバックを設定
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onChangeColumnDisplay: function(callback) {
    this.onChangeColumnDisplayCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   カラム順序入れ替え開始時のイベントコールバックを設定
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onStartColumnOrder: function(callback) {
    this.onStartColumnOrderCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   カラム順序が変更されたときのイベントコールバックを設定
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onChangeColumnOrder: function(callback) {
    this.onChangeColumnOrderCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   ページ番号切り替え時のコールバックを設定する
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onChangePage: function(callback) {
    this.onChangePageCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   自動リロード時のコールバックを設定する
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onAutoReload: function(callback) {
    this.onAutoReloadCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   リサイズ前イベントコールバックを設定する
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onBeforeResize: function(callback) {
    this.onBeforeResizeCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   リサイズ後イベントコールバックを設定する
   * @param   {Function} callback コールバック
   * @return  {McsDataTables}     このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onResized: function(callback) {
    this.onResizedCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief   現在表示中のデータの検索フラグの値を取得する。
   * @param
   * @return  {Boolean}           検索フラグ
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getLatestSearchDataFlag: function() {
    // 直前のAjax時のオプションを取得
    var latestOptions = this.latestAjaxOptions;

    if (!latestOptions || !latestOptions.searchDataFlag) {
      return false;
    } else {
      return latestOptions.searchDataFlag;
    }
  }

};

// ---------------------------------------------------------------------------------------------------

/**
 ******************************************************************************
 * @brief     データテーブルで使うページ切替ボタン
 * @param     {jQuery}     containerDiv 格納先のdiv要素
 * @param     {DataTables} dataTables   DataTablesのインスタンス
 * @param     {Object}     attr         tableに追加適用する属性
 * @param     {Object}     prop         tableに追加適用する属性
 * @return
 * @retval
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsDataTablesPageButton = function(containerDiv, dataTables, attr, prop) {
  this._init(containerDiv, dataTables, attr, prop);
};
// メソッド類
McsDataTablesPageButton.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化する
   * @param     {jQuery}     containerDiv 格納先のdiv要素
   * @param     {DataTables} dataTables   DataTablesのインスタンス
   * @param     {Object}     attr         tableに追加適用する属性
   * @param     {Object}     prop         tableに追加適用する属性
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _init: function(containerDiv, dataTables, attr, prop) {
    this.dataTables = dataTables;
    // メインのtableを生成しておく
    this.div = $('<div class="btn-group mcs-datatables-pagebuttons"></div>');
    this.totalRecordsSpan = $('<span class=" mcs-datatables-pagebuttons-totalRecords"></span>');
    if (attr)
      this.div.attr(attr);
    if (prop)
      this.div.prop(prop);
    // containerDivに追加する
    containerDiv.append(this.div);
    containerDiv.append(this.totalRecordsSpan);
  },

  /**
   ******************************************************************************
   * @brief     ボタンを追加する
   * @param     {String} text       ボタンのテキスト
   * @param     {String} className  クラス名
   * @param     {Object} attr       buttonに追加適用する属性
   * @param     {Object} prop       buttonに追加適用する属性
   * @return    {Button} ボタン
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  createButton: function(text, className, attr, prop) {
    var button = $('<button class="btn btn-default"></button>');
    button.addClass(className);
    button.text(text);
    if (attr)
      button.attr(attr);
    if (prop)
      button.prop(prop);
    this.div.append(button);
    return button;
  },

  /**
   ******************************************************************************
   * @brief     最大ページ数を設定する
   * @param     {Number} pageMax          最大ページ数
   * @return    {McsDataTablesPageButton} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setPageMax: function(pageMax) {
    var i = 0;
    var button = null;

    // 最大ページ数を設定
    this.pageMax = pageMax;
    // 最大ページ数に合わせて、ボタンを生成
    var prevButtonText = this.dataTables.i18n('mypaginate.prev', 'Prev');
    this.prevButton = this.createButton(prevButtonText, 'mcs-dataTables-pagebuttons-prevbutton');
    this.buttonList = [];
    if (this.pageMax <= 7) {
      // ...が発生しないパターン
      for (i = 0; i < this.pageMax; i++) {
        button = this.createButton(String(i + 1), 'mcs-dataTables-pagebuttons-button');
        this.buttonList.push(button);
      }
    } else {
      // ...が発生するパターン
      // 表示ページによってテキストが変わるので、仮文字 "-" で生成
      for (i = 0; i < 7; i++) {
        button = this.createButton('-', 'mcs-dataTables-pagebuttons-button');
        this.buttonList.push(button);
      }
    }
    var nextButtonText = this.dataTables.i18n('mypaginate.next', 'Prev');
    this.nextButton = this.createButton(nextButtonText, 'mcs-dataTables-pagebuttons-nextbutton');
    // ボタンのイベントを設定
    var self = this;
    this.prevButton.on('click', function() {
      self.onClickPrevButton(this);
    });
    this.nextButton.on('click', function() {
      self.onClickNextButton(this);
    });
    for (i = 0; i < this.buttonList.length; i++) {
      this.buttonList[i].on('click', function() {
        self.onClickPageButton(this);
      });
    }

    return this;
  },

  /**
   ******************************************************************************
   * @brief     ページ番号を設定する。
   * @param     {Number} pageNum          ページ番号
   * @return    {McsDataTablesPageButton} このコンポーネント
   * @retval
   * @attention このメソッドでは onChangeCallback は呼ばれない。
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setPageNum: function(pageNum) {
    var i = 0;
    var button = null;
    var num = 0;

    this.currentPageNum = pageNum;
    // ボタンのページ番号を再度振り直し
    if (this.pageMax <= 7) {
      // ...が発生しないパターン
      // ページ番号の振り直しは特に必要ないので、処理なし
    } else if (this.currentPageNum <= 4) {
      // 1,2,3,4,5,...,N
      for (i = 0; i < 5; i++) {
        this.buttonList[i].text(i + 1);
      }
      this.buttonList[5].text('...');
      this.buttonList[6].text(this.pageMax);
    } else if (this.pageMax - 3 <= this.currentPageNum) {
      // 1,...,N-4,N-3,N-2,N-1,N
      for (i = 0; i < 5; i++) {
        var index = this.buttonList.length - 1 - i;
        num = this.pageMax - 1 - i;
        this.buttonList[index].text(num + 1);
      }
      this.buttonList[1].text('...');
      this.buttonList[0].text('1');
    } else {
      // 1,...,n-1,n,n+1,...,N
      this.buttonList[0].text('1');
      this.buttonList[1].text('...');
      this.buttonList[2].text(this.currentPageNum - 1);
      this.buttonList[3].text(this.currentPageNum);
      this.buttonList[4].text(this.currentPageNum + 1);
      this.buttonList[5].text('...');
      this.buttonList[6].text(this.pageMax);
    }
    // 選択中のページに色を付ける
    for (i = 0; i < this.buttonList.length; i++) {
      button = this.buttonList[i];
      num = button.text();
      if (num == String(this.currentPageNum)) {
        button.addClass('btn-primary mcs-datatables-pagebuttons-selected');
      } else {
        button.removeClass('btn-primary mcs-datatables-pagebuttons-selected');
      }
    }
    // 選択中のページと ... はクリックできないようにする
    for (i = 0; i < this.buttonList.length; i++) {
      button = this.buttonList[i];
      num = button.text();
      if (num == String(this.currentPageNum) || num == '...') {
        button.prop('disabled', true);
      } else {
        button.prop('disabled', false);
      }
    }
    // 1ページ目、最終ページでのprevとnextの抑制
    this.prevButton.prop('disabled', this.currentPageNum == 1);
    this.nextButton.prop('disabled', this.currentPageNum == this.pageMax);

    return this;
  },

  /**
   ******************************************************************************
   * @brief     現在のページ番号を返す。
   * @param
   * @return    {Number} 現在のページ番号
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  getPageNum: function() {
    return this.currentPageNum;
  },

  /**
   ******************************************************************************
   * @brief     全体レコード数を設定する。
   * @param     {Number} totalRecords     全体レコード数
   * @return    {McsDataTablesPageButton} このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setTotalRecords: function(totalRecords) {
    var prefix = this.dataTables.i18n('mypaginate.total', 'Total Record:');
    this.totalRecordsSpan.text(prefix + totalRecords);
    return this;
  },

  /**
   ******************************************************************************
   * @brief     前ボタンの動作。
   * @param     {Button} btn     ボタン
   * @return
   * @retval
   * @attention
   * @note      実際にはページ番号を変更せず、onChangeCallbackに新しいページ番号を
   *            指定して呼ぶ。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onClickPrevButton: function(btn) {
    var current = this.currentPageNum;
    if (current > 1 && this.onChangeCallback) {
      this.onChangeCallback(current - 1);
    }
  },

  /**
   ******************************************************************************
   * @brief     次ボタンの動作。
   * @param     {Button} btn     ボタン
   * @return
   * @retval
   * @attention
   * @note      実際にはページ番号を変更せず、onChangeCallbackに新しいページ番号を
   *            指定して呼ぶ。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onClickNextButton: function(btn) {
    var current = this.currentPageNum;
    if (current < this.pageMax && this.onChangeCallback) {
      this.onChangeCallback(current + 1);
    }
  },

  /**
   ******************************************************************************
   * @brief     ページボタンの動作。
   * @param     {Button} btn     ボタン
   * @return
   * @retval
   * @attention
   * @note      実際にはページ番号を変更せず、onChangeCallbackに新しいページ番号を
   *            指定して呼ぶ。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onClickPageButton: function(btn) {
    var _num = $(btn).text();
    if (_num == '...') // ...ボタンは押せないように抑制しているが、念のため
      return;

    var num = parseInt(_num, 10);
    if (num == this.currentPageNum) // 同じページは開けないようにする
      return;

    if (this.onChangeCallback)
      this.onChangeCallback(num);
  },

  /**
   ******************************************************************************
   * @brief     ページが変わった時のイベントコールバックを設定。
   * @param     {Function} callback  コールバック
   * @return    {McsDataTables}      このコンポーネント
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  setOnChangeCallback: function(callback) {
    this.onChangeCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     このコンポーネントを削除する。
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
  destroy: function() {
    this.div.remove();
    this.totalRecordsSpan.remove();
  }

};
