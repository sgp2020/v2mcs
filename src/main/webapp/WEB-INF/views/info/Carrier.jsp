<%--
 ******************************************************************************
 * @file        Carrier.jsp
 * @brief       キャリア情報表示画面用JSP
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- 共通ヘッダ --%>
<%@ include file="../common/commonHeader.jsp"%>
<%-- グリッドヘッダ --%>
<%@ include file="../common/dataTablesHeader.jsp"%>
<%-- Modifyヘッダ --%>
<%@ include file="../common/modifyTableHeader.jsp"%>
<%-- Locationヘッダ --%>
<%--  <%@ include file="../common/locationHeader.jsp"%>  --%>
<%-- TransferLocationヘッダ --%>
<%@ include file="../common/transferLocationHeader.jsp"%>

<%-- メッセージ類取得 --%>
<script>
  var screenText = {
    // メニュー用テキスト
    list: {
      search: '<spring:message code="II-002-01-025" />',
      update: '<spring:message code="v2II-002-01-026" />',
      createTransferJob: '<spring:message code="v2II-002-01-027" />',
      del: '<spring:message code="v2II-002-01-030" />',
      save: '<spring:message code="v2II-002-01-032" />',
      group: '<spring:message code="v2II-002-01-042" />',
      ret: '<spring:message code="II-002-01-033" />',
      addbin: '<spring:message code="v2II-002-01-028" />',
      addohb: '<spring:message code="v2II-002-01-029" />'
    },
    slideSearch: {
      sstart: '<spring:message code="v2II-002-02-008" />',
      send: '<spring:message code="v2II-002-02-009" />',
      istart: '<spring:message code="v2II-002-02-012" />',
      iend: '<spring:message code="v2II-002-02-013" />',
      extract: '<spring:message code="v2II-002-02-014" />',
      clear: '<spring:message code="v2II-002-02-015" />',
      ret: '<spring:message code="v2II-002-02-016" />'
    },
    slideCreatejob: {
      carrierId: '<spring:message code="II-002-03-002" />',
      fromAmhsId: '<spring:message code="II-002-03-003" />',
      fromPort: '<spring:message code="II-002-03-004" />',
      fromCzone: '<spring:message code="II-002-03-005" />',
      add: '<spring:message code="II-002-03-007" />',
      del: '<spring:message code="II-002-03-008" />',
      confirm: '<spring:message code="II-002-03-019" />',
      ret: '<spring:message code="II-002-03-021" />'
    },
    slideGroupInfo: {
    	groupNo: '<spring:message code="v2II-002-08-003" />',
        groupId: '<spring:message code="v2II-002-08-004" />'
    },
    slideAddCarrier: {
        confirm: '<spring:message code="II-002-04-009" />',
        ret: '<spring:message code="II-002-04-011" />'
      },
    addbin: {
          confirm: '<spring:message code="v2II-002-05-053" />',
          ret: '<spring:message code="v2II-002-05-055" />'
        },
	addohb: {
	    confirm: '<spring:message code="v2II-002-05-053" />',
	    ret: '<spring:message code="v2II-002-05-055" />'
	  },
    dialog: {
      listNotSelect: '<spring:message code="SI-002-01.001" />',
      listRet: '<spring:message code="SI-002-01.002" />',
      listSave: '<spring:message code="SI-002-01.003" />',
      carrierTableAddError: '<spring:message code="SI-002-01.004" />',
      createjobNotSelect: '<spring:message code="SI-002-03.001" />',
      createjobRet: '<spring:message code="SI-002-03.002" />',
//      createjobSaveData: '<spring:message code="SI-002-03.003" />',
      createjobSaveData: '<spring:message code="v2SI-002-03.003" />',
      createjobConfirm: '<spring:message code="SI-002-03.004" />',
      createjobNotData: '<spring:message code="SI-002-03.005" />',
//      createjobComplete: '<spring:message code="SI-002-03.006" />',
      createjobComplete: '<spring:message code="v2SI-002-03.006" />',
      carrierAddError: '<spring:message code="SI-002-04.001" />',
      carrierAddRet: '<spring:message code="SI-002-04.002" />',
      delCfm: '<spring:message code="COMMON.BTN.CONFIRM" />',
      delRet: '<spring:message code="COMMON.BTN.RETURN" />',
      delCfmMsg1: '<spring:message code="II-002-01.001" />',
      delCfmMsg2: '<spring:message code="II-002-01.002" />',
//削除対象のCARRIER ID表示メッセージ組み立て用に追加 
      delCfmMsg3: '<spring:message code="v2II-002-01.003" />',
//タスク完了時の処理結果表示メッセージ組み立て用に追加 
      delCmpMsg: '<spring:message code="II-002-01.004" />',
      addRet: '<spring:message code="SI-002-05.001" />'

    },
    validate: {
      carrierId: '<spring:message code="org.hibernate.validator.constraints.NotBlank.message" />',
      carrierIdByteRange: '<spring:message code="mcs.validator.ByteRange.message" />',
      fromLocation: '<spring:message code="org.hibernate.validator.constraints.NotBlank.message" />',
      Duplication: '<spring:message code="ERR0048" />',
      carrierIdDuplicationMes: '<spring:message code="II-002-04-001" />'
    },
    saveText: {
      saveStart: '<spring:message code="II-002-07-002"/>',
      saveEnd: '<spring:message code="II-002-07-003"/>'
    },
    btnText: {
      confirm: '<spring:message code="II-002-07-004"/>',
      saveReturn: '<spring:message code="II-002-07-005"/>',
      groupReturn: '<spring:message code="II-002-07-005"/>'
    }
  };
  
  <%-- Step4P2U 2018_01_18：ゾーンセレクトボックス(検索)用のJSONデータを作成 --%>
  var screenValue = {
    searchCurrentTscId: JSON.parse('${v2II_002_02_004}'),
    searchCarrierState: JSON.parse('${v2II_002_02_005}'),
    addohbId: JSON.parse('${v2II_002_02_001}'),
    addohbPortId: JSON.parse('${v2II_002_02_002}'),
    addbinStkId: JSON.parse('${v2II_002_02_003}')
  };
  console.log(screenValue.searchCurrentTscId[1]);
</script>
<%-- 画面固有ヘッダ --%>
<title><spring:message code="v2II-002-01-001" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/info/mcs-Carrier.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/info/mcs-Carrier.js?Ver=${version}'/>"></script>
	
<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>

</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="v2II-002-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>


    <!-- 右メニューエリア -->
    <nav id="mcs-right-menu">
        <div id="menu-btn-search" class="btn-mcs-slide" data-auth="${I002_REF}"></div>

        <div id="menu-btn-group" class="btn-mcs-slide" data-auth="${I002_REF}"></div>
        <div id="menu-btn-save" class="btn-mcs-slide" data-auth="${I002_REF}"></div>
        <div id="menu-btn-update" class="btn-mcs-slide" data-auth="${I002_REF}"></div>  
             
        <div id="menu-btn-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
        <div id="menu-btn-createJob" class="btn-mcs-slide" data-auth="${I002_CHG}"></div>

		<div id="menu-btn-addbin" class="btn-mcs-slide" data-auth="${I002_CHG}"></div>
		<div id="menu-btn-addohb" class="btn-mcs-slide" data-auth="${I002_CHG}"></div>
        <div id="menu-btn-delete" class="btn-mcs-slide" data-auth="${I002_CHG}"></div>
        
        <div id="menu-btn-addconfirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="menu-btn-addreturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
        

    </nav>

    <!-- 一覧画面 start -->
    <div id="list-screen" class="mcs-content mcs-with-subtitle">
        <div id="mcs-subtitle-list" class="mcs-content-subtitle">
            <spring:message code="v2II-002-01-002" />
        </div>
        <div id="lst-table-target" style="width: 100%; height: 100%;"></div>
    </div>
    <!-- 一覧画面 end -->

    <!-- スライドメニュー 検索用 -->
    <div id="mcs-slideMenu-search">
        <spring:message code="v2II-002-02-001" />
        <div id="mcs-search-currentTscId"></div>
        
        <spring:message code="v2II-002-02-003" />
        <div id="mcs-search-CarrierID"></div>
        
        <spring:message code="v2II-002-02-005" />
        <div id="mcs-search-State"></div>

        <spring:message code="v2II-002-02-007" />
        <div id="mcs-search-sFrom"></div>
        <div id="mcs-search-sTo"></div>
        
        <spring:message code="v2II-002-02-011" />
        <div id="mcs-search-iFrom"></div>
        <div id="mcs-search-iTo"></div>
        
        <!-- ボタン類 -->
        <div id="mcs-search-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-search-clean" class="btn-mcs-slide btn-mcs-slide-clear"></div>
        <div id="mcs-search-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 搬送Job作成用 親メニュー -->
    <div id="mcs-slideMenu-createjob" class="mcs-width-middle">
        <!-- データ表示テーブル -->
        <span class="mcs-required"><spring:message code="II-002-03-001" /></span>
        <div id="mcs-createjob-entrydata"></div>
        <!-- add,deleteボタン類 -->
        <div id="mcs-createjob-add" class="btn-mcs-slide"></div>
        <div id="mcs-createjob-del" class="btn-mcs-slide"></div>
        <!-- JOB優先順位 -->
        <span class="mcs-required"><spring:message code="II-002-03-009" /></span>
        <div id="mcs-createjob-jobpriority" ></div>
        <!-- TOリスト(ロケーション選択） -->
        <span class="mcs-required"><spring:message code="II-002-03-013" /></span>
        <div id="mcs-createjob-to-location"></div>
        <div id="mcs-createjob-entry" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="mcs-createjob-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー 搬送キャリア用（搬送Job作成用子メニュー） -->
    <div id="mcs-slideMenu-transportCarrier">
        <!-- キャリアID -->
        <span class="mcs-required"><spring:message code="II-002-04-001" /></span>
        <div id="mcs-transportCarrier-carrierId"></div>
        <!-- FROMリスト(ロケーション選択） -->
        <span ><spring:message code="II-002-04-003" /></span>
        <div id="mcs-transportCarrier-from-location"></div>
        <!-- ボタン類 -->
        <div id="mcs-transportCarrier-ok" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="mcs-transportCarrier-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- スライドメニュー(CSV保存) start-->
    <div id="mcs-saveMenu">
        <%-- <spring:message code="II-002-07-001" /> --%>
        <div id="mcs-saveStartDatetime"></div>
        <div id="mcs-saveEndDatetime"></div>
        <div id="btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-saveReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    <!-- スライドメニュー(CSV保存) end -->
    
    <div id="mcs-groupMenu">
        <spring:message code="v2II-002-08-001" />
        <div id="mcs-groupPortId"></div>
        <spring:message code="v2II-002-08-002" />
        <div id="mcs-groupAvailable"></div>
        <div id="mcs-groupInfor"></div>
        <div id="btn-groupReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    
    <div id="mcs-addbinMenu">
        <span class="mcs-required"><spring:message code="v2II-002-05-005" /></span>
        <div id="mcs-addbin-carrierId"></div>
        
        <spring:message code="v2II-002-05-008" />
        <div id="mcs-addbin-stk"></div>

        <spring:message code="v2II-002-05-007" />
        <div id="mcs-addbin-binno1"></div>
        <spring:message code="v2II-002-05-009" />
        <div id="mcs-addbin-binno2"></div>
        <spring:message code="v2II-002-05-010" />
        <div id="mcs-addbin-binno3"></div>       
        <!-- ボタン類 -->
        <div id="mcs-addbin-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-addbin-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    
    <div id="mcs-addohbMenu">
        <span class="mcs-required"><spring:message code="v2II-002-05-005" /></span>
        <div id="mcs-addohb-carrierId"></div>
        
        <spring:message code="v2II-002-05-006" />
        <div id="mcs-addohb-ohb"></div>

        <span class="mcs-required"><spring:message code="v2II-002-05-007" /></span>
        <div id="mcs-addohb-binno"></div>
        
        <!-- ボタン類 -->
        <div id="mcs-addohb-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-addohb-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    

    <!-- ダイアログ -->
    <div id="mcs-error-dialog"></div>
    <div id="mcs-warning-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

</body>
</html>