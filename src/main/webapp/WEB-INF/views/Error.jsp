<%-- --%>

<%--
 ******************************************************************************
 * @file        Error.jsp
 * @brief       エラー画面用JSP
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
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%--
************************************************************
* HTML ヘッダ部
************************************************************
 --%>
<head>
<%-- 共通ヘッダ --%>
<%@ include file="common/commonHeader.jsp"%>

<%-- グリッドヘッダ --%>
<%-- <%@ include file="common/dataTablesHeader.jsp" %> --%>

<%-- 更新テーブルヘッダ --%>
<%-- <%@ include file="common/modifyTableHeader.jsp" %> --%>

<%-- 画面タイトル --%>
<title><spring:message code="ERROR.SCREEN.TITLE" /></title>

<!-- 画面固有CSS・JS -->

<%-- メッセージ類取得（JavaScript初期値） --%>
<script>
  comLoginUserId = window.opener.comLoginUserId;
  comLoginUserName = window.opener.comLoginUserName;
  comLoginUserDescription = window.opener.comLoginUserDescription;
  comAutoLogoutTime = window.opener.comAutoLogoutTime;
  var screenText = {};
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="common/designHeader.jsp"%>
</head>

<%--
************************************************************
* HTML ボディ部
************************************************************
 --%>
<body>
    <%--
    ########################################################
     ヘッダーエリア
    ########################################################
     --%>
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="ERROR.SCREEN.TITLE" />
        </h1>
    </header>

    <%--
    ########################################################
     共通ボディエリア
    ########################################################
     --%>
    <%@ include file="common/commonBody.jsp"%>

    <%--
    ########################################################
     画面固有
    ########################################################
     --%>
    <%-- 右メニューエリア --%>
    <nav id="mcs-right-menu"></nav>

    <%-- メインコンテンツ --%>
    <div class="mcs-content">
        <P>${message}</P>
        <hr>
        <P>${detail}</P>
    </div>
</body>
</html>
