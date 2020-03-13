<%--
 ******************************************************************************
 * @file        UnitLStateChange.jsp
 * @brief       ユニット論理状態変更画面用JSP
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
var unitLStateChangeText = {
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
    var unitLStateChangeValue = {
        logicalState: JSON.parse('${IM_904_01_001}')
    };
    $.extend(true, screenText, {
        unitLStateChangeText: unitLStateChangeText
    });
    $.extend(true, screenValue, {
        unitLStateChangeValue: unitLStateChangeValue
    });
</script>
<div id="mcs-unitLStateChange" class="mcs-unitLStateChangeMenu">
    <span id="mcs-unitLStateChange-unitIdLabel" class="mcs-required"></span>
    <div id="mcs-unitLStateChange-unitId"></div>
    <span id="mcs-unitLStateChange-logicalStateBeforeLabel"></span>
    <div id="mcs-unitLStateChange-logicalStateBefore"></div>
    <span id="mcs-unitLStateChange-logicalStateAfterLabel" class="mcs-required"></span>
    <div id="mcs-unitLStateChange-logicalStateAfter"></div>
    <div id="mcs-unitLStateChange-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-unitLStateChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-confirm-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-UnitLStateChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-UnitLStateChange.js?Ver=${version}" />"></script>
