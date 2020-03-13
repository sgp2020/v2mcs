<%--
 ******************************************************************************
 * @file        McsLStateChangeDialog.jsp
 * @brief       MCS論理状態変更画面ダイアログ用JSP
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
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-McsLStateChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-McsLStateChangeDialog.js?Ver=${version}" />"></script>
<script>
    // メッセージ読み込み
    var mcsLStateChangeDialogText = {
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
    var mcsLStateChangeDialogValue = {
        logicalState: JSON.parse('${IM_906_01_001}')
    };
    $.extend(true, screenText, {
        mcsLStateChangeDialogText: mcsLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        mcsLStateChangeDialogValue: mcsLStateChangeDialogValue
    });
</script>
