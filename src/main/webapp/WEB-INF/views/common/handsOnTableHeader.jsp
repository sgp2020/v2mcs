<%--
 ******************************************************************************
 * @file        handsOnTableHeader.jsp
 * @brief       handsOnTableTable部品用JSP
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Handsontable利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value="/resources/lib/handsontable/handsontable.full.css" />">
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-HandsOnTable.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/lib/webfontloader/webfontloader.js" />"></script>
<script src="<c:url value="/resources/lib/handsontable/handsontable.full.js" />"></script>
<script src="<c:url value="/resources/js/component/mcs-HandsOnTable.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  McsHandsOnTable.language = {
    saveSuccess: '<spring:message code="HandsOnTable.language.saveSuccess"/>'
  };
</script>
<!-- Handsontable利用ヘッダー部 end  -->
