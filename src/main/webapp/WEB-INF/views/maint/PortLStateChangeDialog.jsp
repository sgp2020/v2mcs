<%--
 ******************************************************************************
 * @file        PortLStateChange.jsp
 * @brief       Port論理状態変更画面用JSP
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
 * 2018/01/31 0.2         Step4                                       T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-PortLStateChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-PortLStateChangeDialog.js?Ver=${version}" />"></script>
<script>
    // メッセージ読み込み
    var portLStateChangeDialogText = {
        controllerType: '<spring:message code="IM-903-01-001" />',
        controller: '<spring:message code="IM-903-01-002" />',
        portList: '<spring:message code="IM-903-01-003" />',
        target: '<spring:message code="IM-903-01-004" />',
        port: '<spring:message code="IM-903-01-005" />',
        logicalState: '<spring:message code="IM-903-01-006" />',
        logicalStateAfter: '<spring:message code="IM-903-01-007" />',
        confirm: '<spring:message code="IM-903-01-008" />',
        ret: '<spring:message code="IM-903-01-009" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-903-01.001" />',
          errorMsg: '<spring:message code="COMMON.MESSAGE.NO_SELECT" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var portLStateChangeDialogValue = {
        amhsType: JSON.parse('${IM_903_01_001}'),
        logicalState: JSON.parse('${IM_903_01_002}')
    };
    $.extend(true, screenText, {
        portLStateChangeDialogText: portLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        portLStateChangeDialogValue: portLStateChangeDialogValue
    });
</script>
