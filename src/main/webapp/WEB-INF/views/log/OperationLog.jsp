<%--
 ******************************************************************************
 * @file        OperationLog.jsp
 * @brief       操作ログ表示画面用JSP
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
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
<%-- 画面固有ヘッダ --%>
<title><spring:message code="IL-006-01-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/log/mcs-OperationLog.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/log/mcs-OperationLog.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    btnText: {
      search: '<spring:message code="IL-006-01-009"/>',
      update: '<spring:message code="IL-006-01-010"/>',
      save: '<spring:message code="IL-006-01-011"/>',
      cancel: '<spring:message code="IL-006-01-012"/>',
      extract: '<spring:message code="IL-006-02-008"/>',
      clear: '<spring:message code="IL-006-02-009"/>',
      searchReturn: '<spring:message code="IL-006-02-010"/>',
      confirm: '<spring:message code="IL-006-03-004"/>',
      saveReturn: '<spring:message code="IL-006-03-005"/>'
    },
    searchText: {
      from: '<spring:message code="IL-006-02-002"/>',
      to: '<spring:message code="IL-006-02-003"/>'
    },
    saveText: {
      start: '<spring:message code="IL-006-03-002"/>',
      end: '<spring:message code="IL-006-03-003"/>'
    }
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>
<body>
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IL-006-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-dialog"></div>
    <div id="mcs-alert-dialog"></div>
    <div id="mcs-comp-notice"></div>

    <!-- 一覧画面 start -->
    <div id="list-screen" class="mcs-content mcs-with-subtitle">
        <div class="mcs-content-subtitle">
            <spring:message code="IL-006-01-002" />
        </div>
        <div class="mcs-operationLogDatatable" id="lst-table-target"></div>
    </div>

    <!--  親スライドメニュー -->
    <nav id="mcs-right-menu">
        <div id="btn-search" class="btn-mcs-slide" data-auth="${L006_REF}"></div>
        <div id="btn-reload" class="btn-mcs-slide" data-auth="${L006_REF}"></div>
        <div id="btn-save" class="btn-mcs-slide" data-auth="${L006_REF}"></div>
        <div id="btn-return" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <!-- 子スライドメニュー(検索) -->
    <div id="mcs-searchMenu">
        <spring:message code="IL-006-02-001" />
        <div id="mcs-fromDatetime" class="mcs-required"></div>
        <div id="mcs-toDatetime" class="mcs-required"></div>
        <spring:message code="IL-006-02-004" />
        <div id="mcs-userIdTextBox"></div>
        <spring:message code="IL-006-02-006" />
        <div id="mcs-logCodeTextBox"></div>
        <div id="btn-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="btn-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
        <div id="btn-searchReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- 子スライドメニュー(CSV保存) -->
    <div id="mcs-saveMenu">
        <spring:message code="IL-006-03-001" />
        <div id="mcs-saveStartDatetime" class="mcs-required"></div>
        <div id="mcs-saveEndDatetime" class="mcs-required"></div>
        <div id="btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-saveReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
</body>
</html>
