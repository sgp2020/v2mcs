<%--
 ******************************************************************************
 * @file        AmhsLStateChangeDialog.jsp
 * @brief       AMHS論理状態変更画面ダイアログ用JSP
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-AmhsLStateChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-AmhsLStateChangeDialog.js?Ver=${version}" />"></script>
<script>
    // メッセージ読み込み
    var amhsLStateChangeDialogText = {
        controllerType: '<spring:message code="IM-902-01-001"/>',
        controller: '<spring:message code="IM-902-01-002"/>',
        logicalStateBefore: '<spring:message code="IM-902-01-003"/>',
        logicalStateAfter: '<spring:message code="IM-902-01-004"/>',
        confirm: '<spring:message code="IM-902-01-005" />',
        ret: '<spring:message code="IM-902-01-006" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-902-01.001" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var amhsLStateChangeDialogValue = {
        amhsType: JSON.parse('${IM_902_01_001}'),
        logicalState: JSON.parse('${IM_902_01_002}')
    };
    $.extend(true, screenText, {
        amhsLStateChangeDialogText: amhsLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        amhsLStateChangeDialogValue: amhsLStateChangeDialogValue
    });
</script>
