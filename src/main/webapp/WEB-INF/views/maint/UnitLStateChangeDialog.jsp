<%--
 ******************************************************************************
 * @file        UnitLStateChangeDialog.jsp
 * @brief       ユニット論理状態変更画面ダイアログ用JSP
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
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-UnitLStateChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-UnitLStateChangeDialog.js?Ver=${version}" />"></script>
<script>
    // メッセージ読み込み
    var unitLStateChangeDialogText = {
        unitId: '<spring:message code="IM-904-01-001"/>',
        logicalStateBefore: '<spring:message code="IM-904-01-002"/>',
        logicalStateAfter: '<spring:message code="IM-904-01-003"/>',
        confirm: '<spring:message code="IM-904-01-004" />',
        ret: '<spring:message code="IM-904-01-005" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-904-01.001" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var unitLStateChangeDialogValue = {
        logicalState: JSON.parse('${IM_904_01_001}')
    };
    $.extend(true, screenText, {
        unitLStateChangeDialogText: unitLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        unitLStateChangeDialogValue: unitLStateChangeDialogValue
    });
</script>
