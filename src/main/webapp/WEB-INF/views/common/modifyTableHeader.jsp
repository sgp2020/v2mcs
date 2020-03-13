<%--
 ******************************************************************************
 * @file        modifyTableHeader.jsp
 * @brief       modifyTable部品用JSP
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- ModifyTable利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-ModifyTable.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/component/mcs-ModifyTable.js?Ver=${version}'/>"></script>
<script>
  // メッセージ読み込み
  McsModifyTable.language = {
    buttons: {
      colvis: '<spring:message code="ModifyTable.language.buttons.colvis"/>',
      colsave: '<spring:message code="ModifyTable.language.buttons.colsave.text"/>',
      colsaveSuccess: '<spring:message code="ModifyTable.language.buttons.colsave.success"/>'
    }
  };
</script>
<!-- ModifyTable利用ヘッダー部 end -->