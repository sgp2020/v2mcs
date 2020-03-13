<%--
 ******************************************************************************
 * @file        AMHSModeChangeDialog.jsp
 * @brief       AMHSモード変更画面ダイアログ用JSP
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
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-AMHSModeChangeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-AMHSModeChangeDialog.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  var amhsModeChangeDialogText = {
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
  var amhsModeChangeDialogValue = {
    controllerType: JSON.parse('${IM_010_01_001}')
  }
  $.extend(true, screenText, {
    amhsModeChangeDialogText: amhsModeChangeDialogText
  });
  $.extend(true, screenValue, {
    amhsModeChangeDialogValue: amhsModeChangeDialogValue
  });
</script>
