/**
 ******************************************************************************
 * @file        mcs-SlideMenu.js
 * @brief       スライドメニューに関する部品
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
 * @brief     スライドメニューコンポーネント
 * @param     {Object} options オプション(詳細は下記参照）
 *         depth : スライドの深さ。1段階目は0、子スライドは1、
 *          孫スライドは2、という風になる。
 *        parent :親スライドのインスタンス。
 *       親スライドがいない場合、nullを指定する。
 *       slideDiv : スライド化する要素のjQueryオブジェクト。
 * @return
 * @retval
 * @attention
 *   生成時のオプションは以下のような形式となる。
 *     var slideMenu = new McsSlideMenu({
 *           depth: 1,
 *           parent: slideMenuTop,
 *           slideDiv: $('#mcs-slideMenu01')
 *     });
 *
 *   depthは、スライドメニューの深さを指定する。
 *   初めのスライドは0、子スライドの場合は1、孫スライドの場合は2となる。
 *   parentは親のスライドを指定する。初めのスライドの場合はnullを指定する。
 *   slideDivは、スライドメニュー化する対象のdiv要素を指定する。
 * @note      スライドメニューコンポーネント
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsSlideMenu = function(options) {
  // コンストラクタ
  this.init(options);
};

// 定数
/** {Number} z-index。子スライドについては+1ずつ増えていく。 **/
McsSlideMenu.Z_INDEX = 5000;
/** {Number} アニメーション時間 */
McsSlideMenu.ANIMATION_TIME = 250;
/** {Number} 子スライドと親スライドの重なり幅 */
McsSlideMenu.OVERLAPING_WIDTH = 20;
/** {McsSlideMenu} トップメニューのスライド。すべてのスライドの親となる。 */
McsSlideMenu.primaryMenuSlide = null;

// メソッド類
McsSlideMenu.prototype = {
  /**
   ******************************************************************************
   * @brief     初期化処理
   * @param    {Object} options オプション
   *              {
   *              depth : スライドの深さ。1段階目は0、子スライドは1、
   *                            孫スライドは2、という風になる。
   *                    parent : 親スライドのインスタンス。
   *                             親スライドがいない場合、nullを指定する。
   *                    slideDiv : スライド化する要素のjQueryオブジェクト。
   *             }
   * @return
   * @retval
   * @attention
   * @note      スライドインを初期化する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  init: function(options) {
    if (!options)
      options = {};
    this.options = options;

    // 親のスライドメニューを記憶
    if (options.depth == 1) {
      this.parent = McsSlideMenu.primaryMenuSlide;
      this.parent.children.push(this);
      this.children = [];
    } else if (options.parent) {
      this.parent = options.parent;
      this.parent.children.push(this);
      this.children = [];
    } else {
      this.parent = null;
      this.children = [];
    }

    // このスライド深さ
    var depth = options.depth;
    this.depth = depth;

    // スライドメニューのdivを調整
    this.mainDiv = options.slideDiv;
    this.mainDiv.addClass('mcs-slidemenu').css({
      'z-index': McsSlideMenu.Z_INDEX + depth
    });

    // オーバーレイを生成、追加しておく
    this.overlay = $('<div class="mcs-slidemenu-overlay"></div>');
    this.overlay.css({
      'position': 'absolute',
      'top': '0',
      'left': '0',
      'right': '0',
      'bottom': '0',
      'margin': '0',
      'padding': '0',
      'z-index': '2100000000'
    }).hide().appendTo(this.mainDiv);

    // 自動更新の初期化
    this._initAutoReload();
  },

  /**
   ******************************************************************************
   * @brief     自動更新の初期化
   * @param
   * @return
   * @retval
   * @attention
   * @note      画面の自動更新設定を初期化する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _initAutoReload: function() {
    var self = this;
    if (self.depth == 0) {
      AutoReloadTimerManager.addCheckCallback(function() {
        return !self.isVisible();
      });
    }
  },

  /**
   ******************************************************************************
   * @brief     表示状態取得
   * @param
   * @return  {Boolean} 表示されているならtrue、非表示ならfalse
   * @retval
   * @attention
   * @note      スライドインが表示されているかどうかを返す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  isVisible: function() {
    return this.mainDiv.is(':visible');
  },

  /**
   ******************************************************************************
   * @brief     表示状態切替
   * @param
   * @return
   * @retval
   * @attention
   * @note      スライドインの表示状態をトグルする。
   *             スライドインが表示されていない場合は表示し
   *             すでに表示されている場合は閉じる。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  toggle: function() {
    if (this.isVisible()) {
      this.hide();
    } else {
      this.show();
    }
  },

  /**
   ******************************************************************************
   * @brief     スライドイン表示
   * @param  {Boolean} initStartPosFlag 表示開始位置を初期化するかどうか。
   *             省略時true
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      スライドインを表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  show: function(initStartPosFlag) {
    var mainDiv = this.mainDiv;
    var width = mainDiv.outerWidth();
    var startRight = -width;
    var self = this;

    initStartPosFlag = (initStartPosFlag === undefined || initStartPosFlag);

    mainDiv.show();

    // トップレベルのスライドの初期表示の場合
    if (initStartPosFlag && this.depth == 0) {
      // McsDataTablesの自動更新を一時停止する
      AutoReloadTimerManager.stop();
    }

    // 初期表示の時
    if (initStartPosFlag) {
      // スタート位置を初期化する
      mainDiv.css({
        right: startRight + 'px'
      });
      // スクロール位置を初期化する
      mainDiv.scrollTop(0);
      // コールバックを呼ぶ
      if (self.onShowCallback) {
        self.onShowCallback();
      }
    }

    // このスライドの入力欄を非活性にする
    self.toDisabled();
    // アニメーションで表示する
    mainDiv.animate({
      right: 0
    }, McsSlideMenu.ANIMATION_TIME, 'swing', function() {
      // このスライドの入力欄を活性にする
      self.toEnabled();
    });

    // 親スライドがいる場合、親スライドを自分の後ろに隠す
    if (self.parent) {
      self.parent.moveToBack(width);
    }
    return this;
  },

  /**
   ******************************************************************************
   * @brief     子スライドイン背後へ移動
   * @param  {Number}  childWidth 子スライドの幅
   * @return
   * @retval
   * @attention
   * @note      自スライドインを子スライドインの後ろへ移動する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  moveToBack: function(childWidth) {
    var mainDiv = this.mainDiv;
    // このスライドの入力欄を非活性にする
    this.toDisabled();
    // アニメーションで移動
    var width = mainDiv.outerWidth();
    var endRight = McsSlideMenu.OVERLAPING_WIDTH - (width - childWidth);
    var self = this;
    mainDiv.animate({
      right: endRight + 'px'
    }, McsSlideMenu.ANIMATION_TIME, 'swing', function() {
      // アニメーション完了時
      // MEMO toDisabledで非活性になっているが、非表示なので非活性のままにしておく。
    });
    if (this.parent) {
      this.parent.moveToBack(childWidth + McsSlideMenu.OVERLAPING_WIDTH);
    }
    if (self.onMoveBackCallback) {
      self.onMoveBackCallback(self);
    }
  },

  /**
   ******************************************************************************
   * @brief     スライドインを隠す
   * @param
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      自スライドインを隠す。
   *     親スライドが存在する場合は、親スライドをもとの場所に戻す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  hide: function() {
    return this._hide(true);
  },

  /**
   ******************************************************************************
   * @brief     スライドインを隠す（内部処理用）
   * @param  {Boolean} moveParent 親スライドを移動させる場合（元の場所に戻す）
   *            はtrue
   * @return
   * @retval
   * @attention
   * @note      自スライドインを隠す。
   *     親スライドが存在する場合は、親スライドをもとの場所に戻す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _hide: function(moveParent) {
    if (!this.isVisible())
      return;

    var div = this.mainDiv;
    var width = div.outerWidth();
    var endRight = -width;
    var self = this;

    // このスライド自身を閉じる
    self.toDisabled();
    div.animate({
      right: endRight + 'px'
    }, McsSlideMenu.ANIMATION_TIME, 'swing', function() {
      // アニメーション完了時
      // MEMO toDisabledで非活性になっているが、非表示なので非活性のままにしておく。
      div.hide();
      if (self.depth == 0) {
        AutoReloadTimerManager.start();
      }
      if (self.onHideCallback) {
        self.onHideCallback(self);
      }
    });
    // 親スライドを元の場所に戻す
    if ((moveParent === undefined || moveParent) && self.parent) {
      self.parent.moveToFore();
    }
    // 子スライドを隠す
    if (self.children) {
      for (var i = 0; i < self.children.length; i++) {
        // 子スライドを隠すときは、親スライドは動かさない
        // （子スライドの親スライド＝自分、のため。）
        self.children[i]._hide(false);
      }
    }
  },

  /**
   ******************************************************************************
   * @brief     自スライドイン表示位置移動
   * @param
   * @return
   * @retval
   * @attention
   * @note      子スライドが非表示になった際に自スライドを前に出す。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  moveToFore: function() {
    this.show(false);
    if (this.onMoveForeCallback) {
      this.onMoveForeCallback(this);
    }
  },

  /**
   ******************************************************************************
   * @brief     スライドイン操作不能設定
   * @param
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      自スライドインの入力要素を一時的に操作不能にする。
   *            実際には上に透明なdivを重ねて操作不能にする。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  toDisabled: function() {
    // フォーカスを外す
    document.activeElement.blur();
    // オーバーレイを表示
    this.overlay.show();
    // TABキー操作を無効化
    var mainDiv = this.mainDiv;
    var _existAttr = this._existAttr;
    mainDiv.find('input, button').each(function() {
      var $this = $(this);
      var tabindex = $this.attr('tabindex');
      if (_existAttr(tabindex) && tabindex != '-1') {
        // tabindexの値をバッファに保持
        $this.attr('mcs-slidemenu-tabindex-buf', tabindex);
      }
      $this.prop('tabindex', -1);
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     スライドイン操作不能設定解除
   * @param
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      toDisabledによる一時非活性を解除する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  toEnabled: function() {
    // オーバーレイを隠す
    this.overlay.hide();
    // TABキー操作を有効化
    var mainDiv = this.mainDiv;
    var _existAttr = this._existAttr;
    mainDiv.find('input, button').each(function() {
      var $this = $(this);
      var tabindexBuf = $this.attr('mcs-slidemenu-tabindex-buf');
      if (_existAttr(tabindexBuf)) {
        // バッファからtabindexを復元する
        $this.attr('tabindex', tabindexBuf);
        $this.removeAttr('mcs-slidemenu-tabindex-buf');
      } else {
        // バッファが無い場合、もともとtabindexが設定されていなかった場合なので、
        // tabindex属性自体を削除する
        $this.removeAttr('tabindex');
      }
    });
    return this;
  },

  /**
   ******************************************************************************
   * @brief     属性値存在チェック
   * @param  {String} attr 属性値
   * @return  {Boolean} 存在するならtrue
   * @retval
   * @attention
   * @note      属性値(attr)が存在するかどうかをチェックする
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  _existAttr: function(attr) {
    return attr !== undefined && attr !== null && attr !== '';
  },

  /**
   ******************************************************************************
   * @brief     スライドイン表示コールバック設定
   * @param  {Function} callback コールバック
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      スライドインを表示するときのコールバックを設定
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onShow: function(callback) {
    this.onShowCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     スライドイン非表示コールバック設定
   * @param  {Function} callback コールバック
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      スライドインを非表示にするときのコールバックを設定
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onHide: function(callback) {
    this.onHideCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     スライドイン背面表示コールバック設定
   * @param  {Function} callback コールバック
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      自スライドインが子スライドの裏に隠れるときのコールバックを設定
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onMoveBack: function(callback) {
    this.onMoveBackCallback = callback;
    return this;
  },

  /**
   ******************************************************************************
   * @brief     スライドイン前面表示コールバック設定
   * @param  {Function} callback コールバック
   * @return  {McsSlideMenu} このコンポーネント
   * @retval
   * @attention
   * @note      自スライドインが子スライドの裏から表に出るときのコールバックを設定
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  onMoveFore: function(callback) {
    this.onMoveForeCallback = callback;
    return this;
  },

  end: 'end' // 終端用ダミー
};

/**
 ******************************************************************************
 * @brief     操作部スライドイン生成
 * @param
 * @return
 * @retval
 * @attention
 * @note      画面初期化時に、右メニュー（第0階層のスライド）を生成しておく
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
$(function() {
  McsSlideMenu.primaryMenuSlide = new McsSlideMenu({
    depth: 0,
    parent: null,
    slideDiv: $('#mcs-right-menu')
  });
  var mcsHeaderMenu = $('#mcs-icon-menu');
  mcsHeaderMenu.click(function() {
    McsSlideMenu.primaryMenuSlide.toggle();
  });
});
