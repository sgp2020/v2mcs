<%--
 ******************************************************************************
 * @file        SystemMonitor.jsp
 * @brief       システムモニタ表示画面用JSP
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 * 2018/11/09 MACS4#0016  GUI客先テストNG項目対応                     T.Iga/CSC
 * 2018/11/22 MACS4#0047  GUI要望分                                   T.Iga/CSC
 * 2018/11/28 MACS4#0049  StageCmd対応(GUI)                           T.Iga/CSC
 * 2018/11/29 MACS4#0059  M17対応                                     T.Iga/CSC
 * 2018/04/18 MACS4#0158  一覧表示ハイパーリンク化抑制対応            T.Iga/CSC
 ******************************************************************************
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- 共通ヘッダ -->
<%@ include file="../common/commonHeader.jsp"%>

<!-- handsOnTableヘッダ -->
<%@ include file="../common/handsOnTableHeader.jsp"%>

<!-- ホーム画面用CSS・JS -->
<link rel="stylesheet" href="<c:url value="/resources/css/top/mcs-SystemMonitor.css?Ver=${version}" />">
<script src="<c:url value="/resources/js/top/mcs-SystemMonitor.js?Ver=${version}" />"></script>

<!-- 言語定義 -->
<script>
  var screenText = {
    headerText: {
      loginBtn: '<spring:message code="IT-001-01-009"/>',
      logoutBtn: '<spring:message code="IT-001-01-010"/>',
      remarksBtn: '<spring:message code="IT-001-10-003" />'
    },
    selectBox: {
      systemMonitor: '<spring:message code="IT-001-10-004" />',
      siteMap: '<spring:message code="IT-001-10-005" />'
    },
    menuText: {
      alarm: '<spring:message code="IT-001-02-001" />',
      carrier: '<spring:message code="IT-001-02-002" />',
      //transferJob: '<spring:message code="IT-001-02-003" />',   //DEL SGP 20200312
      ohbInfo: '<spring:message code="IT-001-02-003" />',
      processMonitor: '<spring:message code="IT-001-02-006" />',
      testCarrierList: '<spring:message code="IT-001-02-007" />',
      //stageInfo: '<spring:message code="IT-001-02-004" />',      // MACS4#0049 Add
      stockerInfo: '<spring:message code="IT-001-02-005" />',
      // STD APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000
   	  //MACSV4用なLabelを削除する
      //history: '<spring:message code="IT-001-02-004" />',
      //log: '<spring:message code="IT-001-01-006" />',
      //statis: '<spring:message code="IT-001-02-006" />',
      //maint: '<spring:message code="IT-001-02-007" />',
      //cancel: '<spring:message code="IT-001-02-010" />' 
      // END APL 2020.03.10 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
      history: '<spring:message code="IT-001-10-007" />',
      log: '<spring:message code="IT-001-10-008" />',
      statis: '<spring:message code="IT-001-10-009" />',
      maint: '<spring:message code="IT-001-10-010" />',
      cancel: '<spring:message code="IT-001-02-010" />'
    },
    slideHistoryText: {
      trJobHis: '<spring:message code="IT-001-03-001" />',
      AlarmHis: '<spring:message code="IT-001-03-002" />',
      carrierRmHis: '<spring:message code="IT-001-03-004" />',
      stTimeHis: '<spring:message code="IT-001-03-005" />',
      carrierMtnTimeHis: '<spring:message code="IT-001-03-006" />',
      alarmReportHis: '<spring:message code="IT-001-03-007" />',
      alarmSystemHis: '<spring:message code="IT-001-03-008" />',
      stageHis: '<spring:message code="IT-001-03-009" />',   // MACS4#0049 Add
      cancel: '<spring:message code="IT-001-03-003" />'
    },
    slideLogText: {
      sysLog: '<spring:message code="IT-001-04-001" />',
      opeLog: '<spring:message code="IT-001-04-007" />',
      hostLog: '<spring:message code="IT-001-04-002" />',
      amhsLog: '<spring:message code="IT-001-04-003" />',
      transferLog: '<spring:message code="IT-001-04-004" />',
      transferDisLog: '<spring:message code="IT-001-04-005" />',
      performDtLog: '<spring:message code="IT-001-04-008" />',
      cancel: '<spring:message code="IT-001-04-006" />'
    },
    slideStatistics: {
      mttrMtbf: '<spring:message code="IT-001-05-011" />',
      binUtil: '<spring:message code="IT-001-05-006" />',
      avgBinUtil: '<spring:message code="IT-001-05-007" />',
      avgNumCarrier: '<spring:message code="IT-001-05-008" />',
      avgUnitUtil: '<spring:message code="IT-001-05-009" />',
      transferTime: '<spring:message code="IT-001-05-001" />',
      tranTimeMicro: '<spring:message code="IT-001-05-010" />',
      aveTranTimeCarrier: '<spring:message code="IT-001-05-004" />',
      aveTranTimeMicro: '<spring:message code="IT-001-05-005" />',
      hostComResTime: '<spring:message code="IT-001-05-002" />',
      cancel: '<spring:message code="IT-001-05-003" />'
    },
    slideMainte: {
      stocker: '<spring:message code="IT-001-06-001" />',
      portGrp: '<spring:message code="IT-001-06-018" />',
<%--  hostPortGrp: '<spring:message code="IT-001-06-033" />', Hostポートグループ対応 - 制御側未対応のため、未使用 --%>
      alDevice: '<spring:message code="IT-001-06-002" />',
      nearTranConf: '<spring:message code="IT-001-06-028" />',
      userManage: '<spring:message code="IT-001-06-003" />',
      hostConf: '<spring:message code="IT-001-06-004" />',
      amhsConf: '<spring:message code="IT-001-06-005" />',
      zoneConf: '<spring:message code="IT-001-06-006" />',
      ohbPortGrp: '<spring:message code="IT-001-06-019" />',
      ifOhb: '<spring:message code="IT-001-06-034" />',
      sysParam: '<spring:message code="IT-001-06-007" />',
      amhsOpe: '<spring:message code="IT-001-06-008" />',
      systemOpe: '<spring:message code="IT-001-06-020" />',
      routeSearch: '<spring:message code="IT-001-06-009" />',
      tranRoute: '<spring:message code="IT-001-06-021" />',
      zoneRel: '<spring:message code="IT-001-06-010" />',
      moduleConf: '<spring:message code="IT-001-06-032" />',
      unitConf: '<spring:message code="IT-001-06-011" />',
      unitGrp: '<spring:message code="IT-001-06-029" />',
      floorTransfer: '<spring:message code="IT-001-06-030" />',
      vehicleConf: '<spring:message code="IT-001-06-012" />',
      testCarrier: '<spring:message code="IT-001-06-022" />',
      carrierTypeConf: '<spring:message code="IT-001-06-026" />',
      carrierShape: '<spring:message code="IT-001-06-027" />',
      transferTest: '<spring:message code="IT-001-06-014" />',
      jobPriThreshold: '<spring:message code="IT-001-06-023" />',
      alarmSystemSetting: '<spring:message code="IT-001-06-035" />',
      has: '<spring:message code="IT-001-06-015" />',
      vrIPManage: '<spring:message code="IT-001-06-016" />',
      vrIPRelProc: '<spring:message code="IT-001-06-017" />',
      traceLogManage: '<spring:message code="IT-001-06-024" />',
      hostAliasConf: '<spring:message code="IT-001-06-025" />',
      emptyCarrier: '<spring:message code="IT-001-06-031" />',
      cancel: '<spring:message code="IT-001-06-013" />'
    },
    amhsCons: {
      master: '<spring:message code="IT-001-07-001" />',
      port: '<spring:message code="IT-001-07-002" />',
      cancel: '<spring:message code="IT-001-07-003" />'
    },
    amhsOpe: {
      amhsMode: '<spring:message code="IT-001-08-001" />',
      carrierSynchro: '<spring:message code="IT-001-08-002" />',
      cancel: '<spring:message code="IT-001-08-003" />',
      amhsLState: '<spring:message code="IT-001-08-901" />',
      portLState: '<spring:message code="IT-001-08-902" />',
      unitLState: '<spring:message code="IT-001-08-903" />',
      ohbPortGroupLState: '<spring:message code="IT-001-08-904" />',
      transferModeChange: '<spring:message code="IT-001-08-905" />'
    },
    systemOpe: {
      recoveryComp: '<spring:message code="IT-001-29-001" />',
      layoutUpdate: '<spring:message code="IT-001-29-003"/>',
      m17McsDataUpdate: '<spring:message code="IT-001-29-004" />',       // MACS4#0059 Add
      cancel: '<spring:message code="IT-001-29-002" />',
      mcsLState: '<spring:message code="IT-001-29-901" />',
      msg: {
        layoutUpdateConf: '<spring:message code="IT-001-29.001"/>'
      }
    },
    has: {
      node: '<spring:message code="IT-001-28-001" />',
      process: '<spring:message code="IT-001-28-002" />',
      failover: '<spring:message code="IT-001-28-003" />',
      takeover: '<spring:message code="IT-001-28-004" />',
      message: '<spring:message code="IT-001-28-005" />',
      cancel: '<spring:message code="IT-001-28-006" />',
      procState: '<spring:message code="IT-001-28-007" />'
    },
    login: {
      title: '<spring:message code="IT-001-09-001" />',
      message: '<spring:message code="IT-001-09.001" />',
      userName: '<spring:message code="IT-001-09-002" />',
      password: '<spring:message code="IT-001-09-003" />',
      loginBtn: '<spring:message code="IT-001-09-004" />',
      cancelBtn: '<spring:message code="IT-001-09-005" />'
    },
    dialogText: {
      layoutUpdateSuccess: '<spring:message code="IM-045-01.001" />'
    }
  };
  var screenValue = {
    LimitIndividualMonitorNum: '${LimitIndividualMonitorNum}'
  };
</script>
<%@ include file="../maint/AMHSModeChangeDialog.jsp"%>
<%@ include file="../maint/CarrierSynchronizeDialog.jsp"%>
<%@ include file="../maint/AmhsLStateChangeDialog.jsp"%>
<%@ include file="../maint/PortLStateChangeDialog.jsp"%>
<%@ include file="../maint/UnitLStateChangeDialog.jsp"%>
<%@ include file="../maint/OhbPortGroupLStateChangeDialog.jsp"%>
<%-- 
<%@ include file="../maint/TransferModeChangeDialog.jsp"%>
--%>
<%@ include file="../maint/McsLStateChangeDialog.jsp"%>
<%@ include file="../maint/M17McsDataUpdateDialog.jsp" %>   <%-- MACS4#0059 Add --%>

<title><spring:message code="IT-001-01-001" /></title>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>
<body>
    <!-- AMHSモード変更 -->
    <%@ include file="../maint/AMHSModeChange.jsp"%>
    <!-- キャリア同期 -->
    <%@ include file="../maint/CarrierSynchronize.jsp"%>
    <!-- AMHS論理状態変更 -->
    <%@ include file="../maint/AmhsLStateChange.jsp"%>
    <!-- Port論理状態変更 -->
    <%@ include file="../maint/PortLStateChange.jsp"%>
    <!-- ユニット論理状態変更 -->
    <%@ include file="../maint/UnitLStateChange.jsp"%>
    <!-- OHBポートグループ論理状態変更 -->
    <%@ include file="../maint/OhbPortGroupLStateChange.jsp"%>
    <!-- 運用モード変更 -->
    <%-- 
    <%@ include file="../maint/TransferModeChange.jsp" %>
     --%>
    <!-- リカバリーコンプレッション -->
    <%-- 
    <%@ include file="../maint/RecoveryCompletion.jsp"%>
    --%>
    <!-- MCS論理状態変更 -->
    <%@ include file="../maint/McsLStateChange.jsp" %>
    <!-- M17更新要求 - MACS4#0059 Add -->
    <%@ include file="../maint/M17McsDataUpdate.jsp" %>
    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IT-001-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <!-- 情報表示部 -->
    <div id="mcs-top-info" class="mcs-top-info-block">
        <ul class="mcs-top-info-ul">
        
            <!-- 通信状態 -->
            <li class="mcs-info-com">
            <!-- 20200115 Song Mod Start -->
            <!-- <h2 class="mcs-info-com-ttl">
                 	<spring:message code="IT-001-01-005" />
                 </h2> <span id="mcs-info-com-value" ></span> -->  
                <!-- MACS状態 --> 
                <h2 class="macs">  
                    <spring:message code="IT-001-01-011" /> 
                </h2> <span id="macs" ></span>  <!-- 20200115 Song Mod End -->
            </li>

            <!-- 制御状態 -->
            <li class="mcs-info-cntrl">
            <!-- 20200115 Song Mod Start -->
            <!-- <h2 class="mcs-info-cntrl-ttl">
                    <spring:message code="IT-001-01-006" /> 
                 </h2> <span id="mcs-info-cntrl-value"></span>-->
                 <!-- HOST状態 -->  
                 <h2 class="host">
                	<spring:message code="IT-001-01-012" /> 
                 </h2> <span id="host" ></span> <!-- 20200115 Song Mod End -->
            </li>

            <!-- ソフトウェアバージョン情報 -->
            <li class="mcs-info-soft">
<%--            <h2 class="mcs-info-soft-ttl">                                   MACS4#0158 Del --%>
                <h2 class="mcs-info-soft-ttl" x-ms-format-detection="none"> <%-- MACS4#0158 Add --%>
                    <spring:message code="IT-001-01-007" />
                </h2> <!-- <span id="mcs-info-soft-value"></span> --> <span id="softwareVersion"></span> <%-- 20200115 Song Mod --%>
            </li>
            

            <!-- DBスキーマ情報 -->
       <!-- <li class="mcs-info-schm"> -->  <!-- 20200115 Song Del -->
<%--            <h2 class="mcs-info-schm-ttl">                                   MACS4#0158 Del --%>
           <!-- <h2 class="mcs-info-schm-ttl" x-ms-format-detection="none"> -->  <%-- 20200115 Song Del --%>   <%-- MACS4#0158 Add --%>
           <!--         <spring:message code="IT-001-01-008" />
                </h2> <span id="mcs-info-schm-value"></span>
            </li>  --> <%-- 20200115 Song Del --%>

            <!-- ログインログアウトボタン -->
            <li class="mcs-btn-login-wrapper">
                <div id="mcs-btn-login"></div>
                <div id="mcs-btn-logout"></div>
            </li>
        </ul>
    </div>
    <%-- ログインダイアログ --%>
    <div id="login-dialog"></div>
    <div id="mcs-conf-dialog"></div>
    <div id="mcs-comp-dialog"></div>

    <!-- メインスライド -->
    <nav id="mcs-right-menu">
        <!-- アラーム情報表示 -->
        <div id="menu-btn-alarm" class="btn-mcs-slide" data-auth="${I001_REF}"></div>
        <!-- キャリア情報表示 -->
        <div id="menu-btn-carrier" class="btn-mcs-slide" data-auth="${I002_REF}"></div>
       
       
        <!-- 搬送ジョブ情報表示 --> <!-- DEL SGP 2020312-->
        <!-- <div id="menu-btn-transferJob" class="btn-mcs-slide" data-auth="${I003_REF}"></div>  -->  <!-- DEL SGP 2020312-->
        <!--OHB Information情報表示 -->
        <div id="menu-btn-ohbInfo" class="btn-mcs-slide" data-auth="${I003_REF}"></div> 
        <!--Process Monitor情報表示 -->
        <div id="menu-btn-processMonitor" class="btn-mcs-slide" data-auth="${I006_REF}"></div> 
        <!--Test Carrier List情報表示 -->
        <div id="menu-btn-testCarrierList" class="btn-mcs-slide" data-auth="${I007_REF}"></div> 
       
       
        <!-- ステージ情報表示 - MACS4#0049 Add -->
        <div id="menu-btn-stageInfo" class="btn-mcs-slide" data-auth="${I004_REF}"></div>
        <!-- STOCKER情報表示 - 2020.03.10 董 天津村研  MCSV4 開発  Ver2.0 Rev.000 -->
        <div id="menu-btn-stockerInfo" class="btn-mcs-slide" data-auth="${I005_REF}"></div>
        <!-- 【来歴】 -->
        <%--<div id="menu-btn-history" class="btn-mcs-slide"></div>                                      MACS4#0047 Del --%>
        <div id="menu-btn-history" class="btn-mcs-slide" data-auth="${MENU_HISTORY_ENABLE}"></div>  <%-- MACS4#0047 Add --%>
        <!-- 【ログ】 -->
        <%--<div id="menu-btn-log" class="btn-mcs-slide"></div>                                  MACS4#0047 Del --%>
        <div id="menu-btn-log" class="btn-mcs-slide" data-auth="${MENU_LOG_ENABLE}"></div>  <%-- MACS4#0047 Add --%>
        <!-- 【統計】 -->
        <%--<div id="menu-btn-statis" class="btn-mcs-slide"></div>                                   MACS4#0047 Del --%>
        <div id="menu-btn-statis" class="btn-mcs-slide" data-auth="${MENU_STATS_ENABLE}"></div> <%-- MACS4#0047 Add --%>
        <!-- 【保守】 -->
        <%--<div id="menu-btn-maint" class="btn-mcs-slide"></div>                                    MACS4#0047 Del --%>
        <div id="menu-btn-maint" class="btn-mcs-slide" data-auth="${MENU_MAINT_ENABLE}"></div>  <%-- MACS4#0047 Add --%>
        <!-- 戻る -->
        <div id="menu-btn-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <!-- スライドメニュー 来歴 -->
    <div id="mcs-top-slideMenu-his">
        <!-- 搬送ジョブ来歴表示 -->
        <div class="his-job btn-mcs-slide" data-auth="${H001_REF}"></div>
        <!-- アラーム来歴表示 -->
        <div class="his-alarm btn-mcs-slide" data-auth="${H002_REF}"></div>
        <!-- キャリア削除来歴表示 -->
        <div class="his-carrierRm btn-mcs-slide" data-auth="${H004_REF}"></div>
        <!-- 在籍時間来歴表示 -->
        <div class="his-stTime btn-mcs-slide" data-auth="${H003_REF}"></div>
        <!-- キャリア監視来歴表示 -->
        <div class="his-carrierMtnTime btn-mcs-slide" data-auth="${H005_REF}"></div>
        <!-- アラーム報告来歴表示 -->
        <%-- <div class="his-alarmReport btn-mcs-slide" data-auth="${H006_REF}"></div> --%>
        <!-- アラームシステム来歴表示 -->
        <div class="his-alarmSystem btn-mcs-slide" data-auth="${H007_REF}"></div>
        <!-- ステージ来歴表示 - MACS4#0049 Add -->
        <div class="his-stage btn-mcs-slide" data-auth="${H008_REF}"></div>
        <!-- 戻る -->
        <div class="his-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー ログ -->
    <div id="mcs-top-slideMenu-log">
        <!-- システムログ表示 -->
        <div class="log-system btn-mcs-slide" data-auth="${L001_REF}"></div>
        <!-- 操作ログ表示 -->
        <div class="log-ope btn-mcs-slide" data-auth="${L006_REF}"></div>
        <!-- HOST通信ログ表示 -->
        <div class="log-host btn-mcs-slide" data-auth="${L002_REF}"></div>
        <!-- AMHS通信ログ表示 -->
        <div class="log-amhs btn-mcs-slide" data-auth="${L003_REF}"></div>
        <%-- 
        <!-- 搬送ログ表示 -->
        <div class="log-transfer btn-mcs-slide" data-auth="${L004_REF}"></div>
         --%>
        <!-- 搬送障害ログ表示 -->
        <div class="log-dis btn-mcs-slide" data-auth="${L005_REF}"></div>
        <%-- MACS4#0016 Del Start --%>
        <%--
        <!-- パフォーマンスデータログ表示 -->
        <div class="log-performDt btn-mcs-slide" data-auth="${L007_REF}"></div>
        --%
        <%-- MACS4#0016 Del End --%>
        <!-- 戻る -->
        <div class="log-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 統計-->
    <div id="mcs-top-slideMenu-st">
        <!-- MTTR/MTBF統計表示 -->
        <div class="st-mttrMtbf btn-mcs-slide" data-auth="${S010_REF}"></div>
        <!-- 棚占有率統計表示 -->
        <div class="st-binUtil btn-mcs-slide" data-auth="${S005_REF}"></div>
        <!-- 平均棚占有率統計表示 -->
        <div class="st-avg-binUtil btn-mcs-slide" data-auth="${S006_REF}"></div>
        <!-- 平均キャリア数統計表示 -->
        <div class="st-avg-numCarrier btn-mcs-slide" data-auth="${S007_REF}"></div>
        <!-- 機器平均稼働率統計表示 -->
        <div class="st-avg-unitUtil btn-mcs-slide" data-auth="${S009_REF}"></div>
        <!-- 搬送回数（キャリアジョブ）統計表示 -->
        <div class="st-transferTime btn-mcs-slide" data-auth="${S001_REF}"></div>
        <!-- 搬送回数(Micro)統計表示 -->
        <div class="st-transferTime-micro btn-mcs-slide" data-auth="${S008_REF}"></div>
        <!-- 平均搬送時間（キャリアジョブ）統計表示 -->
        <div class="st-aveTrnTime-carrier btn-mcs-slide" data-auth="${S003_REF}"></div>
        <!-- 平均搬送時間（Micro）統計表示 -->
        <div class="st-aveTrnTime-micro btn-mcs-slide" data-auth="${S004_REF}"></div>
        <!-- HOST通信応答時間統計表示 -->
        <div class="st-HostComResTime btn-mcs-slide" data-auth="${S002_REF}"></div>
        <!-- 戻る -->
        <div class="st-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 保守（親メニュー）-->
    <div id="mcs-top-slideMenu-mainte">
        <!-- ストッカグループマスタメンテ -->
        <div class="mainte-stockerGroup btn-mcs-slide" data-auth="${M001_REF}"></div>
        <!-- ポートグループマスタメンテナンス -->
        <div class="mainte-portGrp btn-mcs-slide" data-auth="${M028_REF}"></div>
        <%-- Hostポートグループ対応 - 制御側未対応のため、未使用
        <!-- HOSTポートグループマスタメンテナンス -->
        <div class="mainte-hostPortGrp btn-mcs-slide" data-auth="${M042_REF}"></div>
        --%>
        <!-- 代替マスタメンテ -->
        <div class="mainte-alDevice btn-mcs-slide" data-auth="${M002_REF}"></div>
        <!-- 最寄搬送先許可設定 -->
        <div class="mainte-nearTranConf btn-mcs-slide" data-auth="${M035_REF}"></div>
        <!-- ユーザ管理マスタメンテ -->
        <div class="mainte-userManage btn-mcs-slide" data-auth="${M003_REF}"></div>
        <!-- ホスト構成マスタメンテ -->
        <div class="mainte-hostConf btn-mcs-slide" data-auth="${M004_REF}"></div>
        <!-- 【AMHS構成】 -->
        <%--<div class="mainte-amhsConf btn-mcs-slide"></div>                                        MACS4#0047 Del --%>
        <div class="mainte-amhsConf btn-mcs-slide" data-auth="${MENU_AMHS_CONF_ENABLE}"></div>  <%-- MACS4#0047 Add --%>
        <!-- ゾーンマスタメンテ -->
        <div class="mainte-zoneConf btn-mcs-slide" data-auth="${M008_REF}"></div>
        <!-- OHBポートグループマスタメンテナンス -->
        <div class="mainte-ohbPortGrp btn-mcs-slide" data-auth="${M027_REF}"></div>
        <!-- IFOHB搬送設定 -->
        <div class="mainte-ifOhb btn-mcs-silde"  data-auth="${M043_REF}"></div>
        <!-- シスパラマスタメンテ -->
        <div class="mainte-sysParam btn-mcs-slide" data-auth="${M009_REF}"></div>
        <!-- 【AMHS操作】 -->
        <%--<div class="mainte-amhsOpe btn-mcs-slide"></div>                                         MACS4#0047 Del --%>
        <div class="mainte-amhsOpe btn-mcs-slide" data-auth="${MENU_AMHS_OPE_ENABLE}"></div>    <%-- MACS4#0047 Add --%>
        <!-- 【システム操作】 -->
        <%--<div class="mainte-systemOpe btn-mcs-slide"></div>                                       MACS4#0047 Del --%>
        <div class="mainte-systemOpe btn-mcs-slide" data-auth="${MENU_SYS_OPE_ENABLE}"></div>   <%-- MACS4#0047 Add --%>
        <!-- ルート検索マスタメンテ -->
        <div class="mainte-routeSearch btn-mcs-slide" data-auth="${M012_REF}"></div>
        <%-- MACS4#0016 Del Start --%>
        
        <!-- 搬送経路マスタメンテナンス -->
        <!-- song20191011 -->
        <div class="mainte-tranRoute btn-mcs-slide" data-auth="${M029_REF}"></div>
       <%-- --%>
        <%-- MACS4#0016 Del End --%>
        <!-- ゾーンリレーショナルメンテ -->
        <div class="mainte-zoneRel btn-mcs-slide" data-auth="${M013_REF}"></div>
        <!-- モジュールマスタメンテ -->
        <div class="mainte-moduleConf btn-mcs-slide" data-auth="${M041_REF}"></div>
        <!-- ユニットマスタメンテ -->
        <div class="mainte-unitConf btn-mcs-slide" data-auth="${M014_REF}"></div>
        <!-- ユニットグループマスタメンテ -->
        <div class="mainte-unitGroup btn-mcs-slide" data-auth="${M036_REF}"></div>
        <!-- 階間搬送マスタメンテ -->
        <div class="mainte-floorTransfer btn-mcs-slide" data-auth="${M037_REF}"></div>
        <!-- ビークルメンテ -->
        <div class="mainte-vehicleConf btn-mcs-slide" data-auth="${M015_REF}"></div>
        <!-- 空FOUP -->
        <div class="mainte-emptyCarrier btn-mcs-slide" data-auth="${M038_REF}"></div>
        <!-- テストキャリアマスタメンテナンス -->
        <div class="mainte-testCarrier btn-mcs-slide" data-auth="${M032_REF}"></div>
        <!-- キャリアタイプマスタメンテナンス -->
        <div class="mainte-carrierTypeConf btn-mcs-slide" data-auth="${M033_REF}"></div>
        <!-- キャリアシェイプ -->
        <div class="mainte-carrierShape btn-mcs-slide" data-auth="${M034_REF}"></div>
        <!-- 搬送テストマスタメンテ -->
        <div class="mainte-transferTest btn-mcs-slide" data-auth="${M017_REF}"></div>
        <%-- MACS4#0016 Del Start --%>
        <%--
        <!-- 搬送Job優先順位しきい値設定 -->
        <div class="mainte-jobPriThr btn-mcs-slide" data-auth="${M026_REF}"></div>
        --%>
        <%-- MACS4#0016 Del End --%>
        <!-- アラームシステム設定 -->
        <div class="mainte-alarmSystemSetting btn-mcs-slide" data-auth="${M044_REF}"></div>
        <!-- 【Has】 -->
        <%--<div class="mainte-has btn-mcs-slide"></div>                                         MACS4#0047 Del --%>
        <div class="mainte-has btn-mcs-slide" data-auth="${MENU_HAS_FUNC_ENABLE}"></div>    <%-- MACS4#0047 Add --%>
        <!-- Virtual IP構成マスタメンテ -->
        <div class="mainte-vrIPManage btn-mcs-slide" data-auth="${M023_REF}"></div>
        <!-- Virtual IPリレーショナル構成マスタメンテ -->
        <div class="mainte-vrIPRelProc btn-mcs-slide" data-auth="${M024_REF}"></div>
        <%-- MACS4#0016 Del Start --%>
        <%--
        <!-- トレースログ管理マスタメンテナンス -->
        <div class="mainte-traceLogMng btn-mcs-slide" data-auth="${M030_REF}"></div>
        --%>
        <%-- MACS4#0016 Del End --%>
        <!-- HOSTエイリアスマスタメンテナンス -->
        <div class="mainte-hostAliasConf btn-mcs-slide" data-auth="${M025_REF}"></div>
        <!-- 戻る -->
        <div class="mainte-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 保守（子メニュー AMHS構成メニュー）-->
    <div id="mcs-top-slideMenu-amhsCons">
        <!-- AMHSマスタメンテ -->
        <div class="amhsCons-master btn-mcs-slide" data-auth="${M005_REF}"></div>
        <!-- ポートマスタメンテ -->
        <div class="amhsCons-port btn-mcs-slide" data-auth="${M016_REF}"></div>
        <!-- 戻る -->
        <div class="amhsCons-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 保守（子メニュー AMHS操作メニュー）-->
    <div id="mcs-top-slideMenu-amhsOpe">
        <!-- AMHSモード変更 -->
        <div class="amhsOpe-mode btn-mcs-slide" data-auth="${M010_CHG}"></div>
        <!-- キャリア同期 -->
        <div class="amhsOpe-carrier btn-mcs-slide" data-auth="${M011_CHG}"></div>
        <!-- AMHS論理状態変更 -->
        <div class="amhsOpe-amhsLState btn-mcs-slide" data-auth="${M902_CHG}"></div>
        <!-- Port論理状態変更 -->
        <div class="amhsOpe-portLState btn-mcs-slide" data-auth="${M903_CHG}"></div>
        <!-- ユニット論理状態変更 -->
        <div class="amhsOpe-unitLState btn-mcs-slide" data-auth="${M904_CHG}"></div>
        <!-- OHBポートグループ論理状態変更 -->
        <div class="amhsOpe-ohbPortGroupLState btn-mcs-slide" data-auth="${M905_CHG}"></div>
        <!-- 運用モード変更 -->
        <%--
        <div class="amhsOpe-transferModeChange btn-mcs-slide" data-auth="${M907_CHG}"></div>
        --%>
        <!-- 戻る -->
        <div class="amhsOpe-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 保守（子メニュー システム操作メニュー）-->
    <div id="mcs-top-slideMenu-systemOpe">
        <%--
        <!-- リカバリコンプレッション -->
        <div class="systemOpe-recoveryComp btn-mcs-slide" data-auth="${M031_CHG}"></div>
        --%>
        <!-- レイアウト更新 -->
        <div class="systemOpe-layoutUpdate btn-mcs-slide" data-auth="${M045_CHG}"></div>
        <!-- MCS論理状態変更 -->
        <div class="systemOpe-mcsLState btn-mcs-slide" data-auth="${M906_CHG}"></div>
        <!-- M17更新要求 - MACS4#0059 Add -->
        <div class="systemOp-m17McsDataUpdate btn-mcs-slide" data-auth="${M046_CHG}"></div>
        <!-- 戻る -->
        <div class="systemOpe-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 保守（子メニュー HAS機能メニュー）-->
    <div id="mcs-top-slideMenu-has">
        <!-- ノード構成マスタメンテ -->
        <div class="has-node btn-mcs-slide" data-auth="${M020_REF}"></div>
        <!-- プロセス構成マスタメンテ -->
        <div class="has-process btn-mcs-slide" data-auth="${M021_REF}"></div>
        <!-- プロセス状態管理マスタメンテ -->
        <div class="has-procState btn-mcs-slide" data-auth="${M901_REF}"></div>
        <!-- フェイルオーバールール構成マスタメンテ -->
        <div class="has-failover btn-mcs-slide" data-auth="${M018_REF}"></div>
        <!-- テイクオーバールール構成マスタメンテ -->
        <div class="has-takeover btn-mcs-slide" data-auth="${M022_REF}"></div>
        <!-- メッセージ保持設定 -->
        <div class="has-message btn-mcs-slide" data-auth="${M019_REF}"></div>
        <!-- 戻る -->
        <div class="has-cancel btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- システムモニタ表示部 -->
    <div class="mcs-system-monitor-header">
        <div id="remarks-btn"></div>
        <!-- システムモニタ／サイトマップ切替セレクトボックス -->
        <div id="mcs-selb-content"></div>
    </div>
    <div class="mcs-content mcs-content-top">
        <div id="hands-on-table" style="width: 100%; height: 100%" data-auth="${T001_REF}"></div>
        <!-- サイトマップテーブル -->
        <div id="siteMap-largeTable" data-auth="${T001_REF}"></div>
    </div>
</body>
</html>
