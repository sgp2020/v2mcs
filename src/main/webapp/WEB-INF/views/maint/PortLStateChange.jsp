<%--
 ******************************************************************************
 * @file        PortLStateChange.jsp
 * @brief       Port論理状態変更画面用JSP
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
var portLStateChangeText = {
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
    var portLStateChangeValue = {
        amhsType: JSON.parse('${IM_903_01_001}'),
        logicalState: JSON.parse('${IM_903_01_002}')
    };
    $.extend(true, screenText, {
        portLStateChangeText: portLStateChangeText
    });
    $.extend(true, screenValue, {
        portLStateChangeValue: portLStateChangeValue
    });
</script>
<div id="mcs-portLStateChange" class="mcs-portLStateChangeMenu">
    <span id="mcs-portLStateChange-controllerTypeLabel" class="mcs-required"></span>
    <div id="mcs-portLStateChange-controllerType"></div>
    <span id="mcs-portLStateChange-controllerLabel" class="mcs-required"></span>
    <div id="mcs-portLStateChange-controller"></div>
    <span id="mcs-portLStateChange-portListLabel" class="mcs-required"></span>
    <div id="mcs-portLStateChange-portList" ></div>
    <span id="mcs-portLStateChange-logicalStateAfterLabel"></span>
    <div id="mcs-portLStateChange-logicalStateAfter"></div>
    <div id="mcs-portLStateChange-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-portLStateChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-confirm-dialog"></div>
<div id="mcs-error-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-PortLStateChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-PortLStateChange.js?Ver=${version}" />"></script>
