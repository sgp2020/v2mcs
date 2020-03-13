<%--
 ******************************************************************************
 * @file        McsLStateChange.jsp
 * @brief       MCS論理状態変更画面用JSP
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
var mcsLStateChangeText = {
        logicalStateBefore: '<spring:message code="IM-906-01-001"/>',
        logicalStateAfter: '<spring:message code="IM-906-01-002"/>',
        confirm: '<spring:message code="IM-906-01-003" />',
        ret: '<spring:message code="IM-906-01-004" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-906-01.001" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var mcsLStateChangeValue = {
        logicalState: JSON.parse('${IM_906_01_001}')
    };
    $.extend(true, screenText, {
        mcsLStateChangeText: mcsLStateChangeText
    });
    $.extend(true, screenValue, {
        mcsLStateChangeValue: mcsLStateChangeValue
    });
</script>
<div id="mcs-mcsLStateChange" class="mcs-mcsLStateChangeMenu">
    <span id="mcs-mcsLStateChange-logicalStateBeforeLabel"></span>
    <div id="mcs-mcsLStateChange-logicalStateBefore"></div>
    <span id="mcs-mcsLStateChange-logicalStateAfterLabel" class="mcs-required"></span>
    <div id="mcs-mcsLStateChange-logicalStateAfter"></div>
    <div id="mcs-mcsLStateChange-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-mcsLStateChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-confirm-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-McsLStateChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-McsLStateChange.js?Ver=${version}" />"></script>
