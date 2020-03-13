<%--
 ******************************************************************************
 * @file        CarrierSynchronizeDialog.jsp
 * @brief       キャリア同期画面ダイアログ用JSP
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
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-CarrierSynchronizeDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-CarrierSynchronizeDialog.js?Ver=${version}" />"></script>
<!-- キャリア同期ボディ部 start -->
<script>
  // メッセージ読み込み
  var carrierSynchronizeDialogText = {
    controllerGroup: '<spring:message code="IM-011-01-001"/>',
    controller: '<spring:message code="IM-011-01-008"/>',
    apply: '<spring:message code="IM-011-01-012"/>',
    clear: '<spring:message code="IM-011-01-013"/>',
    ret: '<spring:message code="IM-011-01-014"/>',
    resultMessage: {
      success: '<spring:message code="IM-011-01.001"/>',
      failed: '<spring:message code="IM-011-01.002"/>',
    },
    dialogText: {
      confirmMessage: '<spring:message code="IM-011-01.003"/>',
      confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
      ret: '<spring:message code="COMMON.BTN.RETURN" />'
    }
  };
  var carrierSynchronizeDialogValue = {
    amhsType: JSON.parse('${IM_011_01_001}')
  }
  $.extend(true, screenText, {
    carrierSynchronizeDialogText: carrierSynchronizeDialogText
  });
  $.extend(true, screenValue, {
    carrierSynchronizeDialogValue: carrierSynchronizeDialogValue
  });
</script>
<!-- キャリア同期ボディ部 end  -->