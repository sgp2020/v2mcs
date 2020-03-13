<%--
 ******************************************************************************
 * @file        OhbPortGroupLStateChange.jsp
 * @brief       OHBポートグループ論理状態変更画面用JSP
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
var ohbPortGroupLStateChangeText = {
        ohbId: '<spring:message code="IM-905-01-001"/>',
        logicalStateBefore: '<spring:message code="IM-905-01-002"/>',
        logicalStateAfter: '<spring:message code="IM-905-01-003"/>',
        confirm: '<spring:message code="IM-905-01-004" />',
        ret: '<spring:message code="IM-905-01-005" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-905-01.001" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var ohbPortGroupLStateChangeValue = {
        logicalState: JSON.parse('${IM_905_01_001}')
    };
    $.extend(true, screenText, {
        ohbPortGroupLStateChangeText: ohbPortGroupLStateChangeText
    });
    $.extend(true, screenValue, {
        ohbPortGroupLStateChangeValue: ohbPortGroupLStateChangeValue
    });
</script>
<div id="mcs-ohbPortGroupLStateChange" class="mcs-ohbPortGroupLStateChangeMenu">
    <span id="mcs-ohbPortGroupLStateChange-ohbIdLabel" class="mcs-required"></span>
    <div id="mcs-ohbPortGroupLStateChange-ohbId"></div>
    <span id="mcs-ohbPortGroupLStateChange-logicalStateBeforeLabel"></span>
    <div id="mcs-ohbPortGroupLStateChange-logicalStateBefore"></div>
    <span id="mcs-ohbPortGroupLStateChange-logicalStateAfterLabel" class="mcs-required"></span>
    <div id="mcs-ohbPortGroupLStateChange-logicalStateAfter"></div>
    <div id="mcs-ohbPortGroupLStateChange-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-ohbPortGroupLStateChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-confirm-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-OhbPortGroupLStateChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-OhbPortGroupLStateChange.js?Ver=${version}" />"></script>
