<%--
 ******************************************************************************
 * @file        StockerStatisticsHist.jsp
 * @brief       StockerStatisticsHistory　画面用JSP
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/10 v1.0.0      初版作成                                 								  天津／張東江
 ******************************************************************************
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- 共通ヘッダ --%>
<%@ include file="../common/commonHeader.jsp"%>
<%-- グリッドヘッダ --%>
<%@ include file="../common/dataTablesHeader.jsp"%>
<%-- 更新テーブルヘッダ --%>
<%@ include file="../common/modifyTableHeader.jsp"%>
<%-- IdListSelectヘッダ --%>
<%@ include file="../common/idListSelectHeader.jsp"%>
<%-- マルチセレクトボックスヘッダ --%>
<%@ include file="../common/multiSelectBoxHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="IH-003-01-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/hist/mcs-StockerStatisticsHist.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/hist/mcs-StockerStatisticsHist.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
      btnText: {
    	  search: 	'<spring:message code="IH-003-02-001"/>',
      	downLoad:   '<spring:message code="IH-003-02-002"/>',
          cancel: 	'<spring:message code="IH-003-02-003"/>'
    },
    slideSearch: {
    	tscId:	   	'<spring:message code="IH-003-03-001" />',
    	unit: 		'<spring:message code="IH-003-03-002" />',
    	dateTime: 	'<spring:message code="IH-003-03-003" />',
    	dateFrom: 	'<spring:message code="IH-003-03-006" />',
    	dateTo: 	'<spring:message code="IH-003-03-007" />',
        extract:    '<spring:message code="IH-003-03-008" />',
        clear:      '<spring:message code="IH-003-03-009" />',
        ret: 	    '<spring:message code="IH-003-03-010" />'
        
    },
    
    downLoadText: {
        saveStart: '<spring:message code="IH-003-04-002"/>',
        saveEnd: '<spring:message code="IH-003-04-003"/>'
      },
    downLoadBtn: {
    	 saveConfirm: '<spring:message code="IH-003-04-004"/>',
         saveReturn: '<spring:message code="IH-003-04-005"/>'
      },
    dialog: {
        listNotSelect: '<spring:message code="IH-003-01.001" />',
        listRet: '<spring:message code="IH-003-01.002" />'
    }
  };

  var screenValue = {
	  tscId: JSON.parse('${IH_003_01_001}'),
	  unit: JSON.parse('${IH_003_01_002}')
  };
</script>
<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>


<body>

    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IH-003-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>
    

    <!-- 一覧画面ヘッダー -->
    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td style="color:#6495ED;"><spring:message code="IH-003-01-020" /> &nbsp &nbsp</td>
                <td style="color:#6495ED;"><div id="searchInfo"></div></td>
            </tr>
        </table>
    </div>
    
     <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <!-- 一覧 -->
        <div id="list-btn-search" class="btn-mcs-slide" data-auth="${H003_REF}"></div>
        <div id="list-btn-downLoad"  class="btn-mcs-slide" data-auth="${H003_REF}"></div>
        <div id="list-btn-ret"    class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <div class="mcs-stocker-statistics-history-content">
        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <spring:message code="IH-003-01-002" />
            </div>
            <div id="list-table-target" style="width: 100%; height: 100%;"> </div>
        </div>
        <!-- 一覧画面 end -->
    </div>
    <!-- スライドメニュー 検索用 -->
    <div id="mcs-slideMenu-search">
        <spring:message code="IH-003-03-001" />
        <div id="mcs-search-tscId"></div>
        
        <spring:message code="IH-003-03-002" />
        <div id="mcs-search-unit"></div>    

        <spring:message code="IH-003-03-003" />
        <div id="mcs-search-dFrom"></div>
        <div id="mcs-search-dTo"></div>
        
        <!-- ボタン類 -->
        <div id="mcs-search-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-search-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
        <div id="mcs-search-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    
	    
	<!-- スライドメニュー(CSV保存) start-->
    <div id="mcs-saveMenu">
        <div id="mcs-saveStartDatetime"></div>
        <div id="mcs-saveEndDatetime"></div>
        <div id="btn-saveConfirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-saveReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    <!-- スライドメニュー(CSV保存) end -->
    
    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>
    <!-- ダイアログ -->
    <div id="mcs-error-dialog"></div>
</body>
</html>
