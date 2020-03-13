<%--
 ******************************************************************************
 * @file        M17McsDataUpdate.jsp
 * @brief       M17更新要求画面用JSP
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
 * 2018/11/28 MACS4#0059  M17対応(初版作成)                           T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
// メッセージ読み込み
var m17McsDataUpdateText = {
        selectTarget: '<spring:message code="IM-046-01-001"/>',
        targetId: '<spring:message code="IM-046-01-002"/>',
        confirm: '<spring:message code="IM-046-01-003" />',
        ret: '<spring:message code="IM-046-01-004" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-046-01.001" />',
          resultMsg: '<spring:message code="IM-046-01.002" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    var m17McsDataUpdateValue = {
        selectTarget: JSON.parse('${IM_046_01_001}'),
    };
    $.extend(true, screenText, {
      m17McsDataUpdateText: m17McsDataUpdateText
    });
    $.extend(true, screenValue, {
      m17McsDataUpdateValue: m17McsDataUpdateValue
    });
</script>
<div id="mcs-m17McsDataUpdate" class="mcs-m17McsDataUpdateMenu">
    <span id="mcs-m17McsDataUpdate-selectTargetLabel" class="mcs-required"></span>
    <div id="mcs-m17McsDataUpdate-selectTarget"></div>
    <span id="mcs-m17McsDataUpdate-targetIdLabel" class="mcs-required"></span>
    <div id="mcs-m17McsDataUpdate-targetId"></div>
    <div id="mcs-m17McsDataUpdate-btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
    <div id="mcs-m17McsDataUpdate-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>
<div id="mcs-m17McsDataUpdate-confirm-dialog"></div>
<div id="mcs-m17McsDataUpdate-info-dialog"></div>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-M17McsDataUpdate.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-M17McsDataUpdate.js?Ver=${version}" />"></script>