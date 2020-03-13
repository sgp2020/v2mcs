<%--
 ******************************************************************************
 * @file        M17McsDataUpdateDialog.jsp
 * @brief       M17更新要求画面用JSP
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2018/11/28 MACS4#0059  M17対応(初版作成)                           T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-M17McsDataUpdateDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-M17McsDataUpdateDialog.js?Ver=${version}" />"></script><script>
// メッセージ読み込み
var m17McsDataUpdateDialogText = {
        selectTarget: '<spring:message code="IM-046-01-001"/>',
        targetId: '<spring:message code="IM-046-01-002"/>',
        confirm: '<spring:message code="IM-046-01-003" />',
        ret: '<spring:message code="IM-046-01-004" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-046-01.001" />',
          resultMsg: '<spring:message code="IM-046-01.002" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var m17McsDataUpdateDialogValue = {
        selectTarget: JSON.parse('${IM_046_01_001}'),
    };
    $.extend(true, screenText, {
      m17McsDataUpdateDialogText: m17McsDataUpdateDialogText
    });
    $.extend(true, screenValue, {
      m17McsDataUpdateDialogValue: m17McsDataUpdateDialogValue
    });
</script>
