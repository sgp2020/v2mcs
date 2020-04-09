/**
 ******************************************************************************
 * @file        mcs-SystemMonitor.js
 * @brief       システムモニタ表示画面用JavaScript
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 * 2018/11/09 MACS4#0016  GUI客先テストNG項目対応                     T.Iga/CSC
 * 2018/11/27 MACS4#0049  StageCmd対応(GUI)                           T.Iga/CSC
 * 2018/11/29 MACS4#0059  M17対応                                     T.Iga/CSC
 ******************************************************************************
 */
$(function() {
  var sTable = {};
  var categoryList;
  var buttonListAll;

  // ステータス表示データ取得
  getDataAjax();

  // サイトマップの作成
  createSiteMap();

  // サイトマップ非表示設定
  $('#siteMap-largeTable').hide();

  // メインスクリーン
  var slideMenuTop = createMainScreen();

  // 来歴スクリーン
  var slideMenuHistory = createHistorySlideMenu();

  // ログスクリーン
  var slideMenuLog = createLogSlideMenu();

  // 統計スクリーン
  var slideMenuStatistics = createStatisticsSlideMenu();

  // 保守スクリーン
  var slideMenuMainte = createMainteSlideMenu();

  // amhs構成
  var slideMenuAmhsCons = createAmhsConsSlideMenu();

  // amhs操作
  var slideMenuAmhsOpe = createAmhsOpeSlideMenu();

  // システム操作
  var slideMenuSystemOpe = createSystemOpeSlideMenu();

  // Has
  var slideMenuHas = createHasSlideMenu();

  // AMHSモード変更スライド生成
  var AMHSModeChangeSlide = initAMHSModeChange(slideMenuAmhsOpe);

  // キャリア同期スライド生成
  var carrierSynchronizeSlide = initCarrierSynchronize(slideMenuAmhsOpe);

  // AMHS論理状態変更スライド生成
  var amhsLStateChangeSlide = initAmhsLStateChange(slideMenuAmhsOpe);

  // Port論理状態変更スライド生成
  var portLStateChangeSlide = initPortLStateChange(slideMenuAmhsOpe);

  // ユニット論理状態変更スライド作成
  var unitLStateChangeSlide = initUnitLStateChange(slideMenuAmhsOpe);

  // OHBポートグループ論理状態変更スライド作成
  var ohbPortGroupLStateChangeSlide = initOhbPortGroupLStateChange(slideMenuAmhsOpe);

  // 運用モード変更スライド作成
//  var transferModeChangeSlide = initTransferModeChange(slideMenuAmhsOpe);

  // リカバリーコンプレッションスライド生成
//  var recoveryCompletionSlide = initRecoveryCompletion(slideMenuSystemOpe);

  // MCS論理状態変更スライド作成
  var mcsLStateChangeSlide = initMcsLStateChange(slideMenuSystemOpe);

  // M17更新要求スライド作成 - MACS4#0059 Add
  var m17McsDataUpdateSlide = initM17McsDataUpdate(slideMenuSystemOpe);

  var callSessionUrl = getUrl('/SessionCheck');

  /**
   ******************************************************************************
   * @brief     サイトマップの作成
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
  function createSiteMap() {
    // サイトマップデータを取得する
    callAjax(getUrl('/GetSiteMap'), null, false, function(retObj) {
      // -------------------------
      // onSuccess(データ取得OK)
      // -------------------------
      var btnUrl = {};
      var btnDisp = {};
      var btnId = 0;
      categoryList = retObj.body.label;
      buttonListAll = retObj.body.button;

      // -------------------------
      // テーブルのdivクラスを作成する
      // -------------------------
      // サイトマップ全体を一つのテーブルにする
      var ltr = $('<div class="sitemap-tr"></div>');
      // カテゴリ数分の小テーブルを作成
      for (var i=0;i<categoryList.length;i++) {
        var ltd = $('<div class="sitemap-td"></div>');
        var tbldiv = $('<div id="table-target'+i+'"></div>');
        ltd.append(tbldiv);
        ltr.append(ltd);
      }
      // divクラスに登録
      $('#siteMap-largeTable').append(ltr);

      // -------------------------
      // McsTableおよびボタンの登録
      // -------------------------
      for (var i=0;i<categoryList.length;i++) {
        // テーブル作成
        sTable[i] = new McsTable($('#table-target'+i));
        // 行選択無し
        sTable[i].setNotRowSelect(true);
        // カテゴリをヘッダにする
        sTable[i].setHeader([{
          name: 'sitemapButton',
          text: categoryList[i].value1,
          display: true,
          align: 'center'
        }]);

        // 現カテゴリのボタンリストを取得
        for (var k=0;k<buttonListAll.length;k++) {
          var buttonList = buttonListAll[k];
          if (categoryList[i].key1==buttonList[0].key1) {
            break;
          }
        }
        // カテゴリ内の機能数分ボタンを作成
        for (var j=0;j<buttonList.length;j++) {
          // テーブルのカラムにボタンのためのdivクラスを作成しておく
          sTable[i].addData({
            sitemapButton: $('<div id="'+buttonList[j].value3+'" data-auth="'+buttonList[j].value2+'"></div>')
          });

          // ボタンの遷移先を保持しておく （ボタンクリックされたときに参照する）
          btnUrl[btnId] = buttonList[j].value4;
          btnDisp[btnId] = buttonList[j].value5;

          // ボタン作成
          var btn = new McsButton($('#'+buttonList[j].value3), buttonList[j].value1);
          // ボタンにIDをつけておく （ボタンクリックされたときにthis.idでボタン識別ができるように）
          btn.input[0].id = btnId;

          // クリックイベント
          btn.onClick(function() {
            // ダイアログを表示する機能
            if (btnUrl[this.id] == "slideMenu") {
              openFunctionDialog(btnDisp[this.id]);
            // 画面遷移する機能
            } else {
              openScreen(getUrl(btnUrl[this.id]), btnDisp[this.id]);
            }
          });
          btnId++;
        }
        // カラム幅調整
        sTable[i].resizeColWidth();
      }
    }, function(retObj) {
      // -------------------------
      // onError(データ取得失敗（特に処理なし）)
      // -------------------------
    }, function() {
      // -------------------------
      // onFail(Ajax通信エラー（特に処理なし）)
      // -------------------------
    });
  }

  /**
   ******************************************************************************
   * @brief     メインスクリーンの作成
   * @param
   * @return    {McsSlideMenu} トップメニュースライド
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0049  StageCmd対応(GUI)                                      T.Iga/CSC
   ******************************************************************************
   */
  function createMainScreen() {
    var slideMenuTop = McsSlideMenu.primaryMenuSlide;

    var alarm = new McsButton($('#menu-btn-alarm'), screenText.menuText.alarm);
    var carrier = new McsButton($('#menu-btn-carrier'), screenText.menuText.carrier);
    
    //var transfer = new McsButton($('#menu-btn-transferJob'), screenText.menuText.transferJob); //DEL SGP 20200312
    var ohbInfo = new McsButton($('#menu-btn-ohbInfo'), screenText.menuText.ohbInfo); 
    var processMonitor = new McsButton($('#menu-btn-processMonitor'), screenText.menuText.processMonitor); 
    var testCarrierList = new McsButton($('#menu-btn-testCarrierList'), screenText.menuText.testCarrierList); 
    
    var stageInfo = new McsButton($('#menu-btn-stageInfo'), screenText.menuText.stageInfo);   // MACS4#0049 Add
    var history = new McsButton($('#menu-btn-history'), screenText.menuText.history);
    var log = new McsButton($('#menu-btn-log'), screenText.menuText.log);
    var statistics = new McsButton($('#menu-btn-statis'), screenText.menuText.statis);
    var maintenance = new McsButton($('#menu-btn-maint'), screenText.menuText.maint);
    var cancel = new McsButton($('#menu-btn-cancel'), screenText.menuText.cancel);

    // STD APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    var stockerInfo = new McsButton($('#menu-btn-stockerInfo'), screenText.menuText.stockerInfo);
    // END APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    
    var routeInfo = new McsButton($('#menu-btn-routeInfo'), screenText.menuText.routeInfo); //20200325 Song Add
    
    // STD APL 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    var hostCommInfo = new McsButton($('#menu-btn-hostCommInfo'), screenText.menuText.hostCommInfo);
    // END APL 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    // アラーム情報表示、ページ遷移
    alarm.onClick(function() {
      openScreen(getUrl('Alarm'), 'Alarm');
    });

    // キャリア情報、ページ遷移
    carrier.onClick(function() {
      openScreen(getUrl('Carrier'), 'Carrier');
    });

    //DEL SGP 20200312  START
    // 搬送ジョブ情報、ページ遷移   
    /*
    transfer.onClick(function() {
      openScreen(getUrl('TransferJob'), 'TransferJob');
    });
    */
    //DEL SGP 20200312 END
    
    // OHB情報、ページ遷移   
    ohbInfo.onClick(function() {
      openScreen(getUrl('OhbInfo'), 'OhbInfo');
    });
    
    // Process Monitor情報、ページ遷移   
    processMonitor.onClick(function() {
      openScreen(getUrl('processMonitor'), 'processMonitor');
    });
    
    // Test Carrier List情報、ページ遷移   
    testCarrierList.onClick(function() {
      openScreen(getUrl('testCarrierList'), 'testCarrierList');
    });
    
    // MACS4#0049 Add Start
    stageInfo.onClick(function() {
      openScreen(getUrl('StageInfo'), 'StageInfo');
    });
    // MACS4#0049 Add End

    // STD APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    stockerInfo.onClick(function() {
    	openScreen(getUrl('StockerInfo'), 'StockerInfo');
    });
    // END APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    
    routeInfo.onClick(function() {
    	openScreen(getUrl('RouteInfo'), 'RouteInfo');
    });
    
    // STD APL 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    hostCommInfo.onClick(function() {
    	openScreen(getUrl('HostCommInfo'), 'HostCommInfo');
    });
    // END APL 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    
    // 来歴ボタン、子スクリーン表示
    history.onClick(function() {
      slideMenuHistory.toggle();
    });

    // ログボタン、子スクリーン表示
    log.onClick(function() {
      slideMenuLog.toggle();
    });

    // 統計ボタン、子スクリーン表示
    statistics.onClick(function() {
      slideMenuStatistics.toggle();
    });

    // 保守ボタン、子スクリーン表示
    maintenance.onClick(function() {
      slideMenuMainte.toggle();
    });

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuTop.hide();
    });

    return slideMenuTop;
  }

  /**
   ******************************************************************************
   * @brief     来歴スクリーンの作成
   * @param
   * @return    {McsSlideMenu} 来歴スクリーンスライド
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0049  StageCmd対応(GUI)                                      T.Iga/CSC
   ******************************************************************************
   */
  function createHistorySlideMenu() {
    var slideMenuHistory = new McsSlideMenu({
      depth: 1,
      parent: null,
      slideDiv: $('#mcs-top-slideMenu-his')
    });
    //var transJobHist = new McsButton($('#mcs-top-slideMenu-his .his-job'), screenText.slideHistoryText.trJobHis);
    //var alarmHistory = new McsButton($('#mcs-top-slideMenu-his .his-alarm'), screenText.slideHistoryText.AlarmHis);
    //var carrierRmHist = new McsButton($('#mcs-top-slideMenu-his .his-carrierRm'),screenText.slideHistoryText.carrierRmHis);
    //var stTimeHistory = new McsButton($('#mcs-top-slideMenu-his .his-stTime'), screenText.slideHistoryText.stTimeHis);
    //var carrierMtnTimeHistory = new McsButton($('#mcs-top-slideMenu-his .his-carrierMtnTime'),screenText.slideHistoryText.carrierMtnTimeHis);
    //var alarmSystemHistory = new McsButton($('#mcs-top-slideMenu-his .his-alarmSystem'),screenText.slideHistoryText.alarmSystemHis);
    //var stageHistory = new McsButton($('#mcs-top-slideMenu-his .his-stage'), screenText.slideHistoryText.stageHis);   // MACS4#0049 Add
    var cancel = new McsButton($('#mcs-top-slideMenu-his .his-cancel'), screenText.slideHistoryText.cancel);

    var activityHistory = new McsButton($('#mcs-top-slideMenu-his .his-activity'), screenText.slideHistoryText.activityHist);
    // STD 2020.03.24 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    var atomicActivityHist = new McsButton($('#mcs-top-slideMenu-his .his-atomicActivity'), screenText.slideHistoryText.atomicActivityHist);
    var statisticsHistoryJob = new McsButton($('#mcs-top-slideMenu-his .his-statisticsHistoryJob'), screenText.slideHistoryText.statisticsHistoryJob);
    
    
    // ActivityHistory来歴表示、ページ遷移
    activityHistory.onClick(function() {
    	openScreen(getUrl('ActivityHistory'), 'ActivityHistory');
    });
    
    // AtomicActivity来歴表示、ページ遷移
    atomicActivityHist.onClick(function() {
    	openScreen(getUrl('AtomicActivityHist'), 'AtomicActivityHist');
    });
    
    // AtomicActivity来歴表示、ページ遷移
    statisticsHistoryJob.onClick(function() {
    	openScreen(getUrl('StatisticsHistoryJob'), 'StatisticsHistoryJob');
    });
    // END 2020.03.24 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    
    
    /*
    // 搬送ジョブ来歴表示、ページ遷移
    transJobHist.onClick(function() {
      openScreen(getUrl('TransferJobHistory'), 'TransferJobHistory');
    });

    // アラーム来歴表示、ページ遷移
    alarmHistory.onClick(function() {
      openScreen(getUrl('AlarmHistory'), 'AlarmHistory');
    });

    // キャリア削除来歴表示、ページ遷移
    carrierRmHist.onClick(function() {
      openScreen(getUrl('CarrierRemoveHistory'), 'CarrierRemoveHistory');
    });

    // 在籍時間来歴表示、ページ遷移
    stTimeHistory.onClick(function() {
      openScreen(getUrl('StorageTimeHistory'), 'StorageTimeHistory');
    });

    // キャリア監視来歴表示、ページ遷移
    carrierMtnTimeHistory.onClick(function() {
      openScreen(getUrl('CarrierMotionTimeHistory'), 'CarrierMotionTimeHistory');
    });

//    // アラームレポート来歴表示、ページ遷移
//    alarmReportHistory.onClick(function() {
//      openScreen(getUrl('AlarmReportHistory'), 'AlarmReportHistory');
//    });

    // アラームシステム来歴表示、ページ遷移
    alarmSystemHistory.onClick(function() {
      openScreen(getUrl('AlarmSystemHistory'), 'AlarmSystemHistory');
    });

    // MACS4#0049 Add Start
    // ステージ来歴表示、ページ遷移
    stageHistory.onClick(function() {
      openScreen(getUrl('StageHistory'), 'StageHistory');
    });
    // MACS4#0049 Add End
     */
    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuHistory.hide();
    });

    return slideMenuHistory;
  }

  /**
   ******************************************************************************
   * @brief     ログスクリーンの作成
   * @param
   * @return    {McsSlideMenu} ログスクリーンスライド
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0016  GUI客先テストNG項目対応                                T.Iga/CSC
   ******************************************************************************
   */
  function createLogSlideMenu() {
    var slideMenuLog = new McsSlideMenu({
      depth: 1,
      parent: null,
      slideDiv: $('#mcs-top-slideMenu-log')
    });

    var systemLog = new McsButton($('#mcs-top-slideMenu-log .log-system'), screenText.slideLogText.sysLog);
    var opeLog = new McsButton($('#mcs-top-slideMenu-log .log-ope'), screenText.slideLogText.opeLog);
    var hostComLog = new McsButton($('#mcs-top-slideMenu-log .log-host'), screenText.slideLogText.hostLog);
    var amhsComLog = new McsButton($('#mcs-top-slideMenu-log .log-amhs'), screenText.slideLogText.amhsLog);
//  var transferLog = new McsButton($('#mcs-top-slideMenu-log .log-transfer'), screenText.slideLogText.transferLog);
    var transDisLog = new McsButton($('#mcs-top-slideMenu-log .log-dis'), screenText.slideLogText.transferDisLog);
//  var performDtLog = new McsButton($('#mcs-top-slideMenu-log .log-performDt'), screenText.slideLogText.performDtLog);   // MACS4#0016 Del
    var cancel = new McsButton($('#mcs-top-slideMenu-log .log-cancel'), screenText.slideLogText.cancel);

    // システムログ表示ボタン、ページ遷移
    systemLog.onClick(function() {
      openScreen(getUrl('SystemLog'), 'SystemLog');
    });

    // 操作ログ表示ボタン、ページ遷移
    opeLog.onClick(function() {
      openScreen(getUrl('OperationLog'), 'OperationLog');
    });

    // ホスト通信ログ表示ボタン、ページ遷移
    hostComLog.onClick(function() {
      openScreen(getUrl('HostCommunicationLog'), 'HostCommunicationLog');
    });

    // AMHS通信ログ表示ボタン、ページ遷移
    amhsComLog.onClick(function() {
      openScreen(getUrl('AmhsCommunicationLog'), 'AmhsCommunicationLog');
    });

    // 搬送ログ表示ボタン、ページ遷移
//    transferLog.onClick(function() {
//      openScreen(getUrl('TransferLog'), 'TransferLog');
//    });

    // 搬送障害ログ表示ボタン、ページ遷移
    transDisLog.onClick(function() {
      openScreen(getUrl('TransferDisturbLog'), 'TransferDisturbLog');
    });

    // パフォーマンスデータログ表示ボタン、ページ遷移
    // MACS4#0016 Del Start
//  performDtLog.onClick(function() {
//    openScreen(getUrl('PerformanceDataLog'), 'PerformanceDataLog');
//  });
    // MACS4#0016 Del End

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuLog.hide();
    });

    return slideMenuLog;
  }

  /**
   ******************************************************************************
   * @brief     統計スクリーンの作成
   * @param
   * @return    {McsSlideMenu} 統計スクリーンスライド
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createStatisticsSlideMenu() {
    var slideMenuStatistics = new McsSlideMenu({
      depth: 1,
      parent: null,
      slideDiv: $('#mcs-top-slideMenu-st')
    });
    var mttrMtbf = new McsButton($('#mcs-top-slideMenu-st .st-mttrMtbf'), screenText.slideStatistics.mttrMtbf);
    var binUtil = new McsButton($('#mcs-top-slideMenu-st .st-binUtil'), screenText.slideStatistics.binUtil);
    var avgBinUtil = new McsButton($('#mcs-top-slideMenu-st .st-avg-binUtil'), screenText.slideStatistics.avgBinUtil);
    var avgNumCarrier = new McsButton($('#mcs-top-slideMenu-st .st-avg-numCarrier'),
        screenText.slideStatistics.avgNumCarrier);
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var avgUnitUtil = new McsButton($('#mcs-top-slideMenu-st .st-avg-unitUtil'), screenText.slideStatistics.avgUnitUtil);
    var transferTime = new McsButton($('#mcs-top-slideMenu-st .st-transferTime'),
        screenText.slideStatistics.transferTime);
    var tranTimeMicro = new McsButton($('#mcs-top-slideMenu-st .st-transferTime-micro'),
        screenText.slideStatistics.tranTimeMicro);
    var aveTrnTimeCarrier = new McsButton($('#mcs-top-slideMenu-st .st-aveTrnTime-carrier'),
        screenText.slideStatistics.aveTranTimeCarrier);
    var aveTrnTimeMicro = new McsButton($('#mcs-top-slideMenu-st .st-aveTrnTime-micro'),
        screenText.slideStatistics.aveTranTimeMicro);
    var hostCommResTime = new McsButton($('#mcs-top-slideMenu-st .st-HostComResTime'),
        screenText.slideStatistics.hostComResTime);
    var cancel = new McsButton($('#mcs-top-slideMenu-st .st-cancel'), screenText.slideStatistics.cancel);

    // MTTR/MTBF統計表示ボタン、ページ遷移
    mttrMtbf.onClick(function() {
      openScreen(getUrl('MttrMtbf'), 'MttrMtbf');
    });

    // 棚占有率統計表示ボタン、ページ遷移
    binUtil.onClick(function() {
      openScreen(getUrl('BinUtilization'), 'BinUtilization');
    });

    // 平均棚占有率統計表示ボタン、ページ遷移
    avgBinUtil.onClick(function() {
      openScreen(getUrl('AverageBinUtilization'), 'AverageBinUtilization');
    });

    // 平均キャリア数統計表示ボタン、ページ遷移
    avgNumCarrier.onClick(function() {
      openScreen(getUrl('AverageNumberCarrier'), 'AverageNumberCarrier');
    });

    // 機器平均稼働率統計表示ボタン、ページ遷移
    avgUnitUtil.onClick(function() {
      openScreen(getUrl('AverageUnitUtilization'), 'AverageUnitUtilization');
    });

    // 搬送回数（キャリアジョブ）統計表示ボタン、ページ遷移
    transferTime.onClick(function() {
      openScreen(getUrl('TransferTimesCarrierJob'), 'TransferTimesCarrierJob');
    });

    // 搬送回数(Micro)統計表示ボタン、ページ遷移
    tranTimeMicro.onClick(function() {
      openScreen(getUrl('TransferTimesMicro'), 'TransferTimesMicro');
    });

    // 平均搬送時間(キャリアJob)統計表示ボタン、ページ遷移
    aveTrnTimeCarrier.onClick(function() {
      openScreen(getUrl('AverageTransferTimeCarrierJob'), 'AverageTransferTimeCarrierJob');
    });

    // 平均搬送時間(Micro)統計表示ボタン、ページ遷移
    aveTrnTimeMicro.onClick(function() {
      openScreen(getUrl('AverageTransferTimeMicro'), 'AverageTransferTimeMicro');
    });

    // HOST通信応答時間統計表示ボタン、ページ遷移
    hostCommResTime.onClick(function() {
      openScreen(getUrl('HostCommunicationResTime'), 'HostCommunicationResTime');
    });

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuStatistics.hide();
    });
    return slideMenuStatistics;
  }

  /**
   ******************************************************************************
   * @brief     保守スクリーンの作成
   * @param
   * @return    {McsSlideMenu} 保守スクリーンスライド
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0016  GUI客先テストNG項目対応                                T.Iga/CSC
   ******************************************************************************
   */
  function createMainteSlideMenu() {
    var slideMenuMainte = new McsSlideMenu({
      depth: 1,
      parent: null,
      slideDiv: $('#mcs-top-slideMenu-mainte')
    });

    var stocker = new McsButton($('#mcs-top-slideMenu-mainte .mainte-stockerGroup'), screenText.slideMainte.stocker);
    var portGrp = new McsButton($('#mcs-top-slideMenu-mainte .mainte-portGrp'), screenText.slideMainte.portGrp);
//  var hostPortGrp = new McsButton($('#mcs-top-slideMenu-mainte .mainte-hostPortGrp'), screenText.slideMainte.hostPortGrp);
    var alDevice = new McsButton($('#mcs-top-slideMenu-mainte .mainte-alDevice'), screenText.slideMainte.alDevice);
    var nearTranConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-nearTranConf'),
        screenText.slideMainte.nearTranConf);
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var userManage = new McsButton($('#mcs-top-slideMenu-mainte .mainte-userManage'), screenText.slideMainte.userManage);
    var hostConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-hostConf'), screenText.slideMainte.hostConf);
    var amhsConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-amhsConf'), screenText.slideMainte.amhsConf);
    var zoneConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-zoneConf'), screenText.slideMainte.zoneConf);
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var ohbPortGrp = new McsButton($('#mcs-top-slideMenu-mainte .mainte-ohbPortGrp'), screenText.slideMainte.ohbPortGrp);
    var ifOhb = new McsButton($('#mcs-top-slideMenu-mainte .mainte-ifOhb'), screenText.slideMainte.ifOhb);
    var systemParam = new McsButton($('#mcs-top-slideMenu-mainte .mainte-sysParam'), screenText.slideMainte.sysParam);
    var amhsOpe = new McsButton($('#mcs-top-slideMenu-mainte .mainte-amhsOpe'), screenText.slideMainte.amhsOpe);
    var systemOpe = new McsButton($('#mcs-top-slideMenu-mainte .mainte-systemOpe'), screenText.slideMainte.systemOpe);
    var routeSearch = new McsButton($('#mcs-top-slideMenu-mainte .mainte-routeSearch'),
        screenText.slideMainte.routeSearch);
    //song20191011
    var tranRoute = new McsButton($('#mcs-top-slideMenu-mainte .mainte-tranRoute'), screenText.slideMainte.tranRoute);    // MACS4#0016 Del
    
    var zoneRel = new McsButton($('#mcs-top-slideMenu-mainte .mainte-zoneRel'), screenText.slideMainte.zoneRel);
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var unitConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-unitConf'), screenText.slideMainte.unitConf);
    var unitGrp = new McsButton($('#mcs-top-slideMenu-mainte .mainte-unitGroup'), screenText.slideMainte.unitGrp);
    var floorTrans = new McsButton($('#mcs-top-slideMenu-mainte .mainte-floorTransfer'), screenText.slideMainte.floorTransfer);
    var vehicleConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-vehicleConf'),
        screenText.slideMainte.vehicleConf);
    var testCarrier = new McsButton($('#mcs-top-slideMenu-mainte .mainte-testCarrier'),
        screenText.slideMainte.testCarrier);
    var carrierTypeConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-carrierTypeConf'),
        screenText.slideMainte.carrierTypeConf);
    var carrierShape = new McsButton($('#mcs-top-slideMenu-mainte .mainte-carrierShape'),
        screenText.slideMainte.carrierShape);
    var transferTest = new McsButton($('#mcs-top-slideMenu-mainte .mainte-transferTest'),
        screenText.slideMainte.transferTest);
    // MACS4#0016 Del Start
//  var jobPriThreshold = new McsButton($('#mcs-top-slideMenu-mainte .mainte-jobPriThr'),
//      screenText.slideMainte.jobPriThreshold);
    // MACS4#0016 Del End
    var alarmSystemSetting = new McsButton($('#mcs-top-slideMenu-mainte .mainte-alarmSystemSetting'),
            screenText.slideMainte.alarmSystemSetting);
    var has = new McsButton($('#mcs-top-slideMenu-mainte .mainte-has'), screenText.slideMainte.has);
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var vrIPManage = new McsButton($('#mcs-top-slideMenu-mainte .mainte-vrIPManage'), screenText.slideMainte.vrIPManage);
    var vrIPRelProc = new McsButton($('#mcs-top-slideMenu-mainte .mainte-vrIPRelProc'),
        screenText.slideMainte.vrIPRelProc);
    // MACS4#0016 Del Start
//  var traceLogManage = new McsButton($('#mcs-top-slideMenu-mainte .mainte-traceLogMng'),
//      screenText.slideMainte.traceLogManage);
    // MACS4#0016 Del End
    var hostAliasConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-hostAliasConf'),
            screenText.slideMainte.hostAliasConf);    
    var emptyCarrier = new McsButton($('#mcs-top-slideMenu-mainte .mainte-emptyCarrier'),
        screenText.slideMainte.emptyCarrier);
    var moduleConf = new McsButton($('#mcs-top-slideMenu-mainte .mainte-moduleConf'), screenText.slideMainte.moduleConf);
    var cancel = new McsButton($('#mcs-top-slideMenu-mainte .mainte-cancel'), screenText.slideMainte.cancel);

    // ストッカグループマスタメンテナンスボタン、ページ遷移
    stocker.onClick(function() {
      openScreen(getUrl('StockerGroup'), 'StockerGroup');
    });

    // ポートグループマスタメンテナンスボタン、ページ遷移
    portGrp.onClick(function() {
      openScreen(getUrl('PortGroup'), 'PortGroup');
    });

    // HOSTポートグループマスタメンテナンスボタン、ページ遷移
//  hostPortGrp.onClick(function() {
//    openScreen(getUrl('HostPortGroup'), 'HostPortGroup');
//  });

    // 代替マスタメンテナスボタン、ページ遷移
    alDevice.onClick(function() {
      openScreen(getUrl('AlternateDevice'), 'AlternateDevice');
    });

    // 最寄搬送先許可設定ボタン、ページ遷移
    nearTranConf.onClick(function() {
      openScreen(getUrl('NearTransferConf'), 'NearTransferConf');
    });

    // ユーザ管理マスタメンテナンスボタン、ページ遷移
    userManage.onClick(function() {
      openScreen(getUrl('UserManagement'), 'UserManagement');
    });

    // ホスト構成マスタメンテナンスボタン、ページ遷移
    hostConf.onClick(function() {
      openScreen(getUrl('HostConf'), 'HostConf');
    });

    // AMHS構成ボタン、さらに子スクリーン表示
    amhsConf.onClick(function() {
      slideMenuAmhsCons.toggle();
    });

    // ゾーンマスタメンテナンスボタン、ページ遷移
    zoneConf.onClick(function() {
      openScreen(getUrl('ZoneConf'), 'ZoneConf');
    });

    // OHBポートグループマスタメンテナンスボタン、ページ遷移
    ohbPortGrp.onClick(function() {
      openScreen(getUrl('OhbPortGroup'), 'OhbPortGroup');
    });

    // IFOHB搬送設定ボタン、ページ背に
    ifOhb.onClick(function() {
      openScreen(getUrl('IFOhbSetting'), 'IFOhbSetting');
    });

    // システムパラメータマスタメンテナンスボタン、ページ遷移
    systemParam.onClick(function() {
      openScreen(getUrl('SystemParameter'), 'SystemParameter');
    });

    // AMHS操作ボタン、さらに子スクリーン表示
    amhsOpe.onClick(function() {
      slideMenuAmhsOpe.toggle();
    });

    // システム操作ボタン、さらに子スクリーン表示
    systemOpe.onClick(function() {
      slideMenuSystemOpe.toggle();
    });

    // ルート検索マスタメンテナンスボタン、ページ遷移
    routeSearch.onClick(function() {
      openScreen(getUrl('RouteSearch'), 'RouteSearch');
    });

    //song20191011
    // 搬送経路マスタメンテナンスボタン、ページ遷移
    // MACS4#0016 Del Start
  tranRoute.onClick(function() {
    openScreen(getUrl('TransferRoute'), 'TransferRoute');
  });
    // MACS4#0016 Del End

    // ゾーンリレーショナルマスタメンテナンスボタン、ページ遷移
    zoneRel.onClick(function() {
      openScreen(getUrl('ZoneRelationalConf'), 'ZoneRelationalConf');
    });

    // ユニットマスタメンテナンスボタン、ページ遷移
    unitConf.onClick(function() {
      openScreen(getUrl('UnitConf'), 'UnitConf');
    });

    // ユニットグループマスタメンテナンスボタン、ページ遷移
    unitGrp.onClick(function() {
      openScreen(getUrl('UnitGroup'), 'UnitGroup');
    });

    floorTrans.onClick(function() {
      openScreen(getUrl('FloorTransfer'), 'FloorTransfer');
    });

    // ビークルメンテナンスボタン、ページ遷移
    vehicleConf.onClick(function() {
      openScreen(getUrl('VehicleConf'), 'VehicleConf');
    });

    // テストキャリアマスタメンテナンスボタン、ページ遷移
    testCarrier.onClick(function() {
      openScreen(getUrl('TestCarrier'), 'TestCarrier');
    });

    // キャリアタイプマスタメンテナンスボタン、ページ遷移
    carrierTypeConf.onClick(function() {
      openScreen(getUrl('CarrierTypeConf'), 'CarrierTypeConf');
    });

    // キャリアシェイプボタン、ページ遷移
    carrierShape.onClick(function() {
      openScreen(getUrl('CarrierShape'), 'CarrierShape');
    });

    // 搬送テストマスタメンテナンスボタン、ページ遷移
    transferTest.onClick(function() {
      openScreen(getUrl('TransferTest'), 'TransferTest');
    });

    // 搬送Job優先順位しきい値設定ボタン、ページ遷移
    // MACS4#0016 Del Start
//  jobPriThreshold.onClick(function() {
//    openScreen(getUrl('JobPriorityThreshold'), 'JobPriorityThreshold');
//  });
    // MACS4#0016 Del End

    // アラーム報告設定ボタン、ページ遷移
    alarmSystemSetting.onClick(function() {
      openScreen(getUrl('AlarmSystemSetting'), 'AlarmSystemSetting');
    });

    // Hasボタン、Hasメニューを表示
    has.onClick(function() {
      slideMenuHas.toggle();
    });

    // Virtual IP構成マスタメンテナンスボタン、ページ遷移
    vrIPManage.onClick(function() {
      openScreen(getUrl('VirtualIPConf'), 'VirtualIPConf');
    });

    // Virtual IPリレーショナル構成管理マスタメンテナンスボタン、ページ遷移
    vrIPRelProc.onClick(function() {
      openScreen(getUrl('VirtualIPRelationalConf'), 'VirtualIPRelationalConf');
    });

    // トレースログ管理マスタメンテナンスボタン、ページ遷移
    // MACS4#0016 Del Start
//  traceLogManage.onClick(function() {
//    openScreen(getUrl('TraceLogManagement'), 'TraceLogManagement');
//  });
    // MACS4#0016 Del End

    // HOSTエイリアスマスタメンテナンスボタン、ページ遷移
    hostAliasConf.onClick(function() {
      openScreen(getUrl('HostAlias'), 'HostAlias');
    });

    // 空FOUPボタン、ページ遷移
    emptyCarrier.onClick(function() {
      openScreen(getUrl('EmptyCarrier'), 'EmptyCarrier');
    });

    // モジュールマスタメンテナンスボタン、ページ遷移
    moduleConf.onClick(function() {
      openScreen(getUrl('ModuleConf'), 'ModuleConf');
    });

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuMainte.hide();
    });

    return slideMenuMainte;
  }

  /**
   ******************************************************************************
   * @brief     AMHS構成スクリーンの作成
   * @param
   * @return    {McsSlideMenu} AMHS構成スクリーンスライド
   * @retval
   * @attention
   * @note      保守スクリーンの子スライド
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createAmhsConsSlideMenu() {
    var slideMenuAmhsCons = new McsSlideMenu({
      depth: 2,
      parent: slideMenuMainte,
      slideDiv: $('#mcs-top-slideMenu-amhsCons')
    });

    var amhsMaster = new McsButton($('#mcs-top-slideMenu-amhsCons .amhsCons-master'), screenText.amhsCons.master);
    var portMaster = new McsButton($('#mcs-top-slideMenu-amhsCons .amhsCons-port'), screenText.amhsCons.port);
    var cancel = new McsButton($('#mcs-top-slideMenu-amhsCons .amhsCons-cancel'), screenText.amhsCons.cancel);

    // AMHSマスタメンテナンスボタン、ページ遷移
    amhsMaster.onClick(function() {
      openScreen(getUrl('AMHSConf'), 'AMHSConf');
    });

    // ポートマスタメンテナンスボタン、ページ遷移
    portMaster.onClick(function() {
      openScreen(getUrl('PortConf'), 'PortConf');
    });

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuAmhsCons.hide();
    });

    return slideMenuAmhsCons;
  }

  /**
   ******************************************************************************
   * @brief     AMHS操作スクリーンの作成
   * @param
   * @return    {McsSlideMenu} AMHS操作スクリーンスライド
   * @retval
   * @attention
   * @note      保守スクリーンの子スライド
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createAmhsOpeSlideMenu() {
    var slideMenuAmhsOpe = new McsSlideMenu({
      depth: 2,
      parent: slideMenuMainte,
      slideDiv: $('#mcs-top-slideMenu-amhsOpe')
    });

    var amhsMode = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-mode'), screenText.amhsOpe.amhsMode);
    var carrierSynchro = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-carrier'),
        screenText.amhsOpe.carrierSynchro);
    var amhsLState = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-amhsLState'),
            screenText.amhsOpe.amhsLState);
    var portLState = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-portLState'),
            screenText.amhsOpe.portLState);
    var unitLState = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-unitLState'),
            screenText.amhsOpe.unitLState);
    var ohbPortGroupLState = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-ohbPortGroupLState'),
            screenText.amhsOpe.ohbPortGroupLState);
    var transferModeChange = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-transferModeChange'),
            screenText.amhsOpe.transferModeChange);
    var cancel = new McsButton($('#mcs-top-slideMenu-amhsOpe .amhsOpe-cancel'), screenText.amhsOpe.cancel);

    // AMHSモード変更ボタン、AMHSモード変更スライド表示
    amhsMode.onClick(function() {
      AMHSModeChangeSlide.show();
    });

    // キャリア同期ボタン、キャリア同期スライド表示
    carrierSynchro.onClick(function() {
      carrierSynchronizeSlide.show();
    });

    // AMHS論理状態変更ボタン、AMHS論理状態変更スライド表示
    amhsLState.onClick(function() {
      amhsLStateChangeSlide.show();
    });
    
    // Port論理状態変更ボタン、Port論理状態変更スライド表示
    portLState.onClick(function() {
        portLStateChangeSlide.show();
    });

    // ユニット論理状態変更ボタン、ユニット論理状態変更スライド表示
    unitLState.onClick(function() {
        unitLStateChangeSlide.show();
    });
    
    // OHBポートグループ論理状態変更ボタン、OHBポートグループ論理状態変更スライド表示
    ohbPortGroupLState.onClick(function() {
        ohbPortGroupLStateChangeSlide.show();
    });
    
    // 運用モード変更ボタン、運用モード変更スライド表示
    transferModeChange.onClick(function() {
      transferModeChangeSlide.show();
    });
    
    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuAmhsOpe.hide();
    });

    return slideMenuAmhsOpe;
  }

  /**
   ******************************************************************************
   * @brief     システム操作スクリーンの作成
   * @param
   * @return    {McsSlideMenu} システム操作スクリーンスライド
   * @retval
   * @attention
   * @note      保守スクリーンの子スライド
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0059  M17対応                                                T.Iga/CSC
   ******************************************************************************
   */
  function createSystemOpeSlideMenu() {
    var slideMenuSystemOpe = new McsSlideMenu({
      depth: 2,
      parent: slideMenuMainte,
      slideDiv: $('#mcs-top-slideMenu-systemOpe')
    });

//  var recoveryComp = new McsButton($('#mcs-top-slideMenu-systemOpe .systemOpe-recoveryComp'),
//      screenText.systemOpe.recoveryComp);
    var layoutUpdate = new McsButton($('#mcs-top-slideMenu-systemOpe .systemOpe-layoutUpdate'), screenText.systemOpe.layoutUpdate);
    var mcsLState = new McsButton($('#mcs-top-slideMenu-systemOpe .systemOpe-mcsLState'),
            screenText.systemOpe.mcsLState);
    var m17McsDataUpadte = new McsButton($('#mcs-top-slideMenu-systemOpe .systemOp-m17McsDataUpdate'), screenText.systemOpe.m17McsDataUpdate);    // MACS4#0059 Add
    var cancel = new McsButton($('#mcs-top-slideMenu-systemOpe .systemOpe-cancel'), screenText.systemOpe.cancel);

    // リカバリコンプレッションボタン、リカバリコンプレッションスライド表示
//  recoveryComp.onClick(function() {
//    recoveryCompletionSlide.show();
//  });

    layoutUpdate.onClick(function() {
      openLayoutUpdateDialog();
    });

    // MCS論理状態変更ボタン、MCS論理状態変更スライド表示
    mcsLState.onClick(function() {
      mcsLStateChangeSlide.show();
    });

    // MACS4#0059 Add Start
    // M17更新要求ボタン、M17更新要求スライド表示
    m17McsDataUpadte.onClick(function() {
      m17McsDataUpdateSlide.show();
    });
    // MACS4#0059 Add End

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuSystemOpe.hide();
    });

    return slideMenuSystemOpe;
  }

  /**
   ******************************************************************************
   * @brief     Hasスクリーンの作成
   * @param
   * @return    {McsSlideMenu} Hasスクリーンスライド
   * @retval
   * @attention
   * @note      保守スクリーンの子スライド
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createHasSlideMenu() {
    var slideMenuHas = new McsSlideMenu({
      depth: 2,
      parent: slideMenuMainte,
      slideDiv: $('#mcs-top-slideMenu-has')
    });

    var node = new McsButton($('#mcs-top-slideMenu-has .has-node'), screenText.has.node);
    var process = new McsButton($('#mcs-top-slideMenu-has .has-process'), screenText.has.process);
    var procState = new McsButton($('#mcs-top-slideMenu-has .has-procState'), screenText.has.procState);
    var failover = new McsButton($('#mcs-top-slideMenu-has .has-failover'), screenText.has.failover);
    var takeover = new McsButton($('#mcs-top-slideMenu-has .has-takeover'), screenText.has.takeover);
    var message = new McsButton($('#mcs-top-slideMenu-has .has-message'), screenText.has.message);
    var cancel = new McsButton($('#mcs-top-slideMenu-has .has-cancel'), screenText.has.cancel);

    // ノード構成マスタメンテナンスボタン、ページ遷移
    node.onClick(function() {
      openScreen(getUrl('NodeConf'), 'NodeConf');
    });

    // プロセス構成マスタメンテナンスボタン、ページ遷移
    process.onClick(function() {
      openScreen(getUrl('ProcessConf'), 'ProcessConf');
    });

    // プロセス状態管理マスタメンテナンスボタン、ページ遷移
    procState.onClick(function() {
      openScreen(getUrl('ProcStateManagement'), 'ProcStateManagement');
    });

    // フェイルオーバーマスタメンテナンスボタン、ページ遷移
    failover.onClick(function() {
      openScreen(getUrl('FailoverRuleConf'), 'FailoverRuleConf');
    });

    // テイクオーバーマスタメンテナンスボタン、ページ遷移
    takeover.onClick(function() {
      openScreen(getUrl('TakeoverRuleConf'), 'TakeoverRuleConf');
    });

    // メッセージ保持設定ボタン、ページ遷移
    message.onClick(function() {
      openScreen(getUrl('MessageRetentionSetting'), 'MessageRetentionSetting');
    });

    // 戻るボタン、スクリーン非表示
    cancel.onClick(function() {
      slideMenuHas.hide();
    });

    return slideMenuHas;
  }

  /**
   ******************************************************************************
   * @brief     ステータス取得
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
  function getDataAjax() {
    var callUrl = getUrl('/GetTopAjax');
    var onError = function() {
    };
    callAjax(callUrl, null, true, setStates, onError, onError);
  }

  /**
   ******************************************************************************
   * @brief     ステータスの反映
   * @param     {Object} data データベースから取得したステータス情報
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function setStates(data) {
	//20200115 Song mod Start  
    //communicationText.text(data.body.commState);
    //controlText.text(data.body.controlState);
    //softVerText.text(data.body.mcsVer);
    //schmVerText.text(data.body.guiVer);
	  macsText.text(data.body.macsText);
	  hostText.text(data.body.hostText);
	  softwareVersionText.text(data.body.softwareVersionText);
	  $("#macs").css("background-color",data.body.macsColor);
	  $("#host").css("background-color",data.body.hostColor);
	  $("#macs").css("color","#000000");
	  $("#host").css("color","#000000");
    //20200115 Song mod End  
  }

  /**
   ******************************************************************************
   * @brief     画面表示
   * @param     {String} url     表示するURL
   * @param     {String} winName 表示する画面名
   * @return
   * @retval
   * @attention
   * @note      画面を表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function openScreen(url, winName) {
    winLoad(url, winName);
  }

  /**
   ******************************************************************************
   * @brief     システムモニタ／サイトマップ切替
   * @param     {String} value   セレクトボックスの値
   * @return
   * @retval
   * @attention
   * @note      画面を表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function changeDisplayContents(value) {
    $('#hands-on-table').hide();
    $('#siteMap-largeTable').hide();
    $('#remarks-btn').hide();

    switch(value){
    case "1": // サイトマップ
      $('#siteMap-largeTable').show();
      // サイトマップテーブルのカラム幅再調整
      for (var i=0;i<categoryList.length;i++) {
        sTable[i].resizeColWidth();
      }
      break;
    case "0": // 全体モニタ画面
    default:
      $('#hands-on-table').show();
      $('#remarks-btn').show();
      handsOnTable.reload();
      break;
    }
  }

  /**
   ******************************************************************************
   * @brief     サイトマップから機能ダイアログをオープンする
   * @param     {String} value   機能番号
   * @return
   * @retval
   * @attention
   * @note      画面を表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0059  M17対応                                                T.Iga/CSC
   ******************************************************************************
   */
  function openFunctionDialog(value) {

    // すべてのスライドをいったん非表示にする
    slideMenuTop.hide();
    slideMenuHistory.hide();
    slideMenuLog.hide();
    slideMenuStatistics.hide();
    slideMenuMainte.hide()
    slideMenuAmhsCons.hide();
    slideMenuAmhsOpe.hide();
    slideMenuSystemOpe.hide();
    slideMenuHas.hide();

    switch(value){
    case "1":
      // [保守]-[AMHS操作]-[AMHSモード変更]
      openAmhsModeChangeDialog();
      break;
    case "2":
      // [保守]-[AMHS操作]-[キャリア同期]
      openCarrierSynchronizeDialog();
      break;
    case "3":
      // [保守]-[AMHS操作]-[AMHS論理状態変更]
      openAmhsLStateChangeDialog();
      break;
    case "4":
      // [保守]-[AMHS操作]-[Port論理状態変更]
      openPortLStateChangeDialog();
      break;
    case "5":
      // [保守]-[AMHS操作]-[Unit論理状態変更]
      openUnitLStateChangeDialog();
      break;
    case "6":
      // [保守]-[AMHS操作]-[OHBポートグループ論理状態変更]
      openOhbPortGroupLStateChangeDialog();
      break;
    case "7":
      // [保守]-[AMHS操作]-[運用モード変更]
      openTransferModeChangeDialog();
      break;
    case "8":
      // [保守]-[システム操作]-[MCS論理状態変更]
      openMcsLStateChangeDialog();
      break;
    case "9":
      // [保守]-[システム操作]-[レイアウト更新]
      openLayoutUpdateDialog();
      break;
    // MACS4#0059 Add Start
    case "10":
      // [保守]-[システム操作]-[M17更新要求]
      openM17McsDataUpdateDialog();
      break;
    // MACS4#0059 Add End
    default:
      // 何もしない
      break;
    }
  }

  /**
   ******************************************************************************
   * @brief     レイアウト更新ダイアログをオープンする
   * @param     {String} value   機能番号
   * @return
   * @retval
   * @attention
   * @note      画面を表示する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   *  2019/12/06 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
   ******************************************************************************
   */
  function openLayoutUpdateDialog() {
    var confDialog = new McsDialog($('#mcs-conf-dialog'), window.mcsDialogTitleConfirm);
    var compDialog = new McsDialog($('#mcs-comp-dialog'), window.mcsDialogTitleInfo);

    confDialog.openConfirm(screenText.systemOpe.msg.layoutUpdateConf, window.mcsDialogBtnOk, window.mcsDialogBtnReturn, 'confirm', function(resObj) {
      if (resObj) {
        // 決定ボタン押下時
        var url = getUrl('/LayoutUpdate/ExeLayoutUpdate');
        var cond = {};
        callAjax(url, cond, false, function(retObj) {
          compDialog.openAlertProc(screenText.dialogText.layoutUpdateSuccess, window.mcsDialogBtnOk, 'info', function() {
            // 何もしない
          });
        })
      }
    });
  }

  // セッションチェック（5min=300000ms）
  var sessionCheck = function() {
    callAjax(callSessionUrl, null, false);
  };
  setInterval(sessionCheck, 300000);

  //20200115 Song Mod Start
  // MACSテキスト
  var macsText = $('#macs');
  
  //HOST状態テキスト
  var hostText = $('#host');
  
  //Software Version状態テキスト
  var softwareVersionText = $('#softwareVersion');
  
  
  // 通信テキスト
  //var communicationText = $('#mcs-info-com-value');

  // 制御状態テキスト
  //var controlText = $('#mcs-info-cntrl-value');

  // ソフトバージョン表示テキスト
  //var softVerText = $('#mcs-info-soft-value');

  // DBスキーマバージョン表示
  //var schmVerText = $('#mcs-info-schm-value');
  
  //20200115 Song Mod End

  // ログインボタン
  var login = new McsButton($('#mcs-btn-login'), screenText.headerText.loginBtn);

  // ログアウトボタン
  var logout = new McsButton($('#mcs-btn-logout'), screenText.headerText.logoutBtn);

  // ログイン状態によるボタン表示切替
  if (comLoginUserName !== NOLOGIN_USER) {
    // ログイン中
    logout.show();
    login.hide();
  } else {
    // 未ログイン
    login.show();
    logout.hide();
  }

  // ログイン処理 ダイアログ表示
  login.onClick(function() {
    var loginDialog = new McsDialog($('#login-dialog'), screenText.login.title);
    loginDialog.openLoginDialog(screenText.login.message, screenText.login.userName, screenText.login.password,
        screenText.login.loginBtn, screenText.login.cancelBtn);
  });
  // ログアウト処理
  logout.onClick(function() {
    var values = {};
    values['userName'] = comLoginUserName;
    values['password'] = '';
    var str = JSON.stringify(values, null, '   ');
    callAjax(window.mcsComponentContextRoot + 'Logout', str, false, function(retObj) {
      // ログアウトOK（画面リロードし未ログインユーザ情報反映させる）
      location.reload();
      
    }, function(retObj) {
      // ログアウト失敗（特に処理なし）
     
    }, function() {
      // Ajax通信エラー（特に処理なし）
      
    });
  });

  // 全体モニタ画面生成
  var handsOnTable = new McsHandsOnTable($('#hands-on-table'), false);
  var handsOnTableOption = {
    url: getUrl('/GetSystemMonitor'),
    cond: {}
  };
  // アイコンのクリックイベント設定
  if ($('#hands-on-table').attr('data-auth') == 'true') {
    handsOnTableOption.judgeOnIconClick = function(icon) {
    //return icon.find('i.sample,i.undefined').length == 0 && !isNaN(icon.find('input[name="amhsType"]').val()); // MACS4#MACSV2 Del
    return icon.find('i.sample,i.undefined').length == 0 && isNaN(icon.find('input[name="memberGroup"]').val());// MACS4#MACSV2 Add  //v2系统中input[name="amhsType"]取值是数值型，v4系统中input[name="memberGroup"]取值是字符串类型，所以本条语句把isNaN前面的！(非)去掉了
    };
    var iconPopup = new McsPopupWinLoad();
    //handsOnTableOption.onIconClick = function(amhsId, amhsType) {// MACS4#MACSV2 Del
//    handsOnTableOption.onIconClick = function(displayName, memberGroup) {	// MACS4#MACSV2 Add
      handsOnTableOption.onIconClick = function(displayId,displayName, memberGroup) {	// MACS4#MACSV2 Add
      var options = {
        url: getUrl('Individual'),
        sendValue: {
          //amhsId: amhsId,			// MACS4#MACSV2 Del
          //amhsType: amhsType		// MACS4#MACSV2 Del
        	displayId:displayId,	// 20191225 DQY ADD
        	displayName:displayName,// MACS4#MACSV2 Add
        	memberGroup:memberGroup	// MACS4#MACSV2 Add
        },
        limitWindow: screenValue.LimitIndividualMonitorNum,
        width: 1280,
        height: 800
      };
      iconPopup.open(options);
    };
  }
  // データの取得
  handsOnTable.getDataAjax(handsOnTableOption);

  // 凡例ボタン生成
  var remarksBtn = new McsButton($('#remarks-btn'), screenText.headerText.remarksBtn);
  var remarksPopup = new McsPopupWinLoad();
  remarksBtn.onClick(function() {
    var options = {
      url: getUrl('Remarks'),
      winName: 'remarks',
      width: 1280,
      height: 800
    };
    remarksPopup.open(options);
  });

  // システムモニタ／サイトマップ切替セレクトボックス
  var selbDisplay = new McsSelectBox($('#mcs-selb-content'));
  // セレクトボックス要素セット([value, text])
  selbDisplay.setList([['0', screenText.selectBox.systemMonitor],
    ['1', screenText.selectBox.siteMap]]);

  // セレクトボックス 選択時
  selbDisplay.onChange(function() {
    changeDisplayContents(selbDisplay.getValue());
  });

  // 自動更新処理を設定
  AutoReloadTimerManager.addTimeoutCallback(function() {
    getDataAjax();
    handsOnTable.reload();
    AutoReloadTimerManager.start();
  });

  AutoReloadTimerManager.start();
});
