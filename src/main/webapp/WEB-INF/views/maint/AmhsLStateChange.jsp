<%--
 ******************************************************************************
 * @file        AmhsLStateChange.jsp
 * @brief       AMHS論理状態変更画面用JSP
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
 * 2018/01/31 0.2         Step4                                       T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
// メッセージ読み込み
var amhsLStateChangeText = {
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
    var amhsLStateChangeValue = {
        amhsType: JSON.parse('${IM_902_01_001}'),
        logicalState: JSON.parse('${IM_902_01_002}')
    };
    $.extend(true, screenText, {
        amhsLStateChangeText: amhsLStateChangeText
    });
    $.extend(true, screenValue, {
        amhsLStateChangeValue: amhsLStateChangeValue
    });
</script>
<div id="mcs-amhsLStateChange" class="mcs-amhsLStateChangeMenu">
    <span id="mcs-amhsLStateChange-controllerTypeLabel" class="mcs-required"></span>
    <div id="mcs-amhsLStateChange-controllerType"></div>
    <span id="mcs-amhsLStateChange-controllerLabel" class="mcs-required"></span>
    <div id="mcs-amhsLStateChange-controller"></div>
    <span id="mcs-amhsLStateChange-logicalStateBeforeLabel"></span>
    <div id="mcs-amhsLStateChange-logicalStateBefore"></div>
    <span id="mcs-amhsLStateChange-logicalStateAfterLabel" class="mcs-required"></span>
    <div id="mcs-amhsLStateChange-logicalStateAfter"></div>
    <div id="mcs-amhsLStateChange-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-amhsLStateChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-confirm-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-AmhsLStateChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-AmhsLStateChange.js?Ver=${version}" />"></script>
