<%--
 ******************************************************************************
 * @file        CarrierSynchronize.jsp
 * @brief       キャリア同期画面用JSP
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- キャリア同期ボディ部 start -->
<script>
  // メッセージ読み込み
  var carrierSynchronizeText = {
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
  var carrierSynchronizeValue = {
    amhsType: JSON.parse('${IM_011_01_001}')
  }
  $.extend(true, screenText, {
    carrierSynchronizeText: carrierSynchronizeText
  });
  $.extend(true, screenValue, {
    carrierSynchronizeValue: carrierSynchronizeValue
  });
</script>
<!-- 子スライドメニュー(キャリア同期) -->
<div id="mcs-synchroCarrier" class="mcs-synchroMenu-carrier">
    <span id="mcs-synchroCarrier-controllerGroupLabel" class="mcs-required"></span>
    <div id="mcs-synchroCarrier-controllerGroup"></div>
    <span id="mcs-synchroCarrier-controllerLabel" class="mcs-required"></span>
    <div id="mcs-synchroCarrier-controller"></div>
    <div id="mcs-synchroCarrier-btn-confirm" class="btn-mcs-slide"></div>
    <div id="mcs-synchroCarrier-btn-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
    <div id="mcs-synchroCarrier-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
</div>

<!-- 確認用ダイアログ -->
<div id="mcs-confirm-dialog"></div>
<div id="mcs-carrierSync-confirm-dialog"></div> <!-- MACS4#0036 Add -->
<div id="mcs-carrierSync-info-dialog"></div> <!-- MACS4#0036 Add -->
<link rel="stylesheet" href="<c:url value='/resources/css/maint/mcs-CarrierSynchronize.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/maint/mcs-CarrierSynchronize.js?Ver=${version}" />"></script>
<!-- キャリア同期ボディ部 end  -->