<%--
 ******************************************************************************
 * @file        AMHSModeChange.jsp
 * @brief       AMHSモード変更画面用JSP
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-AMHSModeChange.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-AMHSModeChange.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  var AMHSModeChangeText = {
    controllerType: '<spring:message code="IM-010-01-001" />',
    controller: '<spring:message code="IM-010-01-003" />',
    controlMode: '<spring:message code="IM-010-01-005" />',
    online: '<spring:message code="IM-010-01-006" />',
    offline: '<spring:message code="IM-010-01-007" />',
    semState: '<spring:message code="IM-010-01-008" />',
    paused: '<spring:message code="IM-010-01-009" />',
    auto: '<spring:message code="IM-010-01-010" />',
    clear: '<spring:message code="IM-010-01-011" />',
    ret: '<spring:message code="IM-010-01-012" />',
    // MACS4#0036 Add Start
    dialog: {
      onlineCmpMsg: '<spring:message code="IM-010-01.001"/>',
      offlineCmpMsg: '<spring:message code="IM-010-01.002"/>',
      pasuedCmpMsg: '<spring:message code="IM-010-01.003"/>',
      autoCmpMsg: '<spring:message code="IM-010-01.004"/>'
    }
    // MACS4#0036 Add End
  };
  var AMHSModeChangeValue = {
    controllerType: JSON.parse('${IM_010_01_001}')
  }
  $.extend(true, screenText, {
    AMHSModeChangeText: AMHSModeChangeText
  });
  $.extend(true, screenValue, {
    AMHSModeChangeValue: AMHSModeChangeValue
  });
</script>
<div id="mcs-amhsMode-info-dialog"></div> <!-- MACS4#0036 Add -->
<!-- 子スライドメニュー(AMHSモード変更) -->
<div id="mcs-AMHSModeChange-slide">
    <span id="mcs-AMHSModeChange-controllerType-label" class="mcs-required"></span>
    <div id="mcs-AMHSModeChange-controllerType"></div>
    <span id="mcs-AMHSModeChange-controller-label" class="mcs-required"></span>
    <div id="mcs-AMHSModeChange-controller"></div>
    <span id="mcs-AMHSModeChange-controlMode-label"></span>
    <div id="mcs-AMHSModeChange-btn-online" class="btn-mcs-slide"></div>
    <div id="mcs-AMHSModeChange-btn-offline" class="btn-mcs-slide"></div>
    <span id="mcs-AMHSModeChange-semState-label"></span>
    <div id="mcs-AMHSModeChange-btn-paused" class="btn-mcs-slide"></div>
    <div id="mcs-AMHSModeChange-btn-auto" class="btn-mcs-slide"></div>
    <div id="mcs-AMHSModeChange-btn-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
    <div id="mcs-AMHSModeChange-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>