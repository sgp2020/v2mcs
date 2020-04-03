<%--
 ******************************************************************************
 * @file        Port.jsp
 * @brief       アラーム情報表示画面用JSP
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

<%-- 画面固有ヘッダ --%>
<title><spring:message code="IH-002-05-022" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/hist/mcs-MacroData.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/hist/mcs-MacroData.js?Ver=${version}'/>"></script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>

</head>

<body>

<%
  String commandId = (String)request.getParameter("commandId");
  System.out.println(request.toString());
  System.out.println(commandId);
%>
<input type="hidden" id="commandId" value=<%=commandId%>>
    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IH-002-05-022" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <!-- 一覧画面 start -->
    <div id="list-screen" class="mcs-content mcs-with-subtitle">
        <div id="mcs-subtitle-list" class="mcs-content-subtitle">
            <spring:message code="IH-002-01-002" />
        </div>
        <div id="macroData-table-target" style="width: 100%; height: 100%;"></div>
    </div>
    <!-- 一覧画面 end -->

    <!-- ダイアログ -->
    <div id="mcs-error-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
</body>
</html>