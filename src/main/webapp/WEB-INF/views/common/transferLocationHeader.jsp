<%--
 ******************************************************************************
 * @file        transferLocationHeader.jsp
 * @brief       搬送ロケーション選択部品用JSP
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
 * 2018/01/31 0.8         Step4リリース                               T.Iga/CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Location利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-TransferLocation.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/component/mcs-TransferLocation.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  McsTransferLocation.language = {
    select: '<spring:message code="TransferLocation.language.select"/>',
    selectTitle: '<spring:message code="TransferLocation.language.selectTitle"/>',
    ok: '<spring:message code="TransferLocation.language.ok"/>',
    cancel: '<spring:message code="TransferLocation.language.cancel"/>'
  };
  McsTransferLocationSelectBox.language = {
    transferLocationCommon: {
      labels: {
        location1: '<spring:message code="TransferLocationSelectBox_Common.language.labels.location1"/>',
        location2: '<spring:message code="TransferLocationSelectBox_Common.language.labels.location2"/>',
        from: '<spring:message code="TransferLocationSelectBox_Common.language.labels.From"/>',
        to: '<spring:message code="TransferLocationSelectBox_Common.language.labels.To"/>'
      }
    },
    transferLocationSelect: {
      labels: {
        location: '<spring:message code="TransferLocationSelectBox_Select.language.labels.location"/>',
        controller: '<spring:message code="TransferLocationSelectBox_Select.language.labels.controller"/>',
        stockerGroup: '<spring:message code="TransferLocationSelectBox_Select.language.labels.stockerGroup"/>',
        ohbGroup: '<spring:message code="TransferLocationSelectBox_Select.language.labels.ohbGroup"/>',
        machine1: '<spring:message code="TransferLocationSelectBox_Select.language.labels.machine1"/>',
        machine2: '<spring:message code="TransferLocationSelectBox_Select.language.labels.machine2"/>',
        port: ''
      },
      selects: {
        stocker: '<spring:message code="TransferLocationSelectBox_Select.language.selects.stocker"/>',
  		stockerGroup: '<spring:message code="TransferLocationSelectBox_Select.language.selects.stockerGroup"/>',
        ohbGroup: '<spring:message code="TransferLocationSelectBox_Select.language.selects.ohbGroup"/>',
        machine: '<spring:message code="TransferLocationSelectBox_Select.language.selects.machine"/>',
        lifter: '<spring:message code="TransferLocationSelectBox_Select.language.selects.lifter"/>',
        operatorPort: '<spring:message code="TransferLocationSelectBox_Select.language.selects.operatorPort"/>'
      }
    },
    btnText: {
      select: '<spring:message code="TransferLocationSelectBox.btn.select"/>',
      ok: '<spring:message code="TransferLocationSelectBox.btn.ok"/>',
      clear: '<spring:message code="TransferLocationSelectBox.btn.clear"/>'
    },
    dialogText: {
      errMsg: '<spring:message code="TransferLocationSelectBox.errMsg"/>'
    }
  };
</script>
<!-- Location利用ヘッダー部 end  -->