<%--
 ******************************************************************************
 * @file        OhbPortGroupLStateChangeDialog.jsp
 * @brief       OHBポートグループ論理状態変更画面ダイアログ用JSP
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
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-OhbPortGroupLStateChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-OhbPortGroupLStateChangeDialog.js?Ver=${version}" />"></script>
<script>
    // メッセージ読み込み
    var ohbPortGroupLStateChangeDialogText = {
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
    var ohbPortGroupLStateChangeDialogValue = {
        logicalState: JSON.parse('${IM_905_01_001}')
    };
    $.extend(true, screenText, {
        ohbPortGroupLStateChangeDialogText: ohbPortGroupLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        ohbPortGroupLStateChangeDialogValue: ohbPortGroupLStateChangeDialogValue
    });
</script>
