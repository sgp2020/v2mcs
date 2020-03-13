<%--
 ******************************************************************************
 * @file        locationHeader.jsp
 * @brief       ロケーション選択部品用JSP
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Location利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-Location.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/component/mcs-Location.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  McsLocation.language = {
    select: '<spring:message code="Location.language.select"/>',
    selectTitle: '<spring:message code="Location.language.selectTitle"/>',
    inputTitle: '<spring:message code="Location.language.inputTitle"/>',
    ok: '<spring:message code="Location.language.ok"/>',
    cancel: '<spring:message code="Location.language.cancel"/>'
  };
  McsLocationSelectBox.language = {
    locationCommon: {
      labels: {
        location: '<spring:message code="LocationSelectBox_Common.language.labels.location"/>',
        from: '<spring:message code="LocationSelectBox_Common.language.labels.From"/>',
        to: '<spring:message code="LocationSelectBox_Common.language.labels.To"/>'
      }
    },
    locationSelect: {
      labels: {
        location: '<spring:message code="LocationSelectBox_Select.language.labels.location"/>',
        controller: '<spring:message code="LocationSelectBox_Select.language.labels.controller"/>',
        stockerGroup: '<spring:message code="LocationSelectBox_Select.language.labels.stockerGroup"/>',
        ohbGroup: '<spring:message code="LocationSelectBox_Select.language.labels.ohbGroup"/>',
        machine: '<spring:message code="LocationSelectBox_Select.language.labels.machine"/>',
        port: '<spring:message code="LocationSelectBox_Select.language.labels.port"/>'
      },
      selects: {
        stocker: '<spring:message code="LocationSelectBox_Select.language.selects.stocker"/>',
        stockerGroup: '<spring:message code="LocationSelectBox_Select.language.selects.stockerGroup"/>',
        ohbGroup: '<spring:message code="LocationSelectBox_Select.language.selects.ohbGroup"/>',
        machine: '<spring:message code="LocationSelectBox_Select.language.selects.machine"/>',
        lifter: '<spring:message code="LocationSelectBox_Select.language.selects.lifter"/>',
        virtualStocker: '<spring:message code="LocationSelectBox_Select.language.selects.virtualStocker"/>',
        ridgeIntervalConveyor: '<spring:message code="LocationSelectBox_Select.language.selects.ridgeIntervalConveyor"/>',
        operatorPort: '<spring:message code="LocationSelectBox_Select.language.selects.operatorPort"/>'
      }

    },
    locationInput: {
      labels: {
        location: '<spring:message code="LocationSelectBox_Input.language.labels.location"/>',
//        controller: '<spring:message code="LocationSelectBox_Input.language.labels.controller"/>',
        controller: 'Current TSCID',        
        vehicle: '<spring:message code="LocationSelectBox_Input.language.labels.vehicle"/>',
        machine: '<spring:message code="LocationSelectBox_Input.language.labels.machine"/>',
        ohbGroup: '<spring:message code="LocationSelectBox_Input.language.labels.ohbGroup"/>',
        port: '<spring:message code="LocationSelectBox_Input.language.labels.port"/>',
      },
      selects: {
        stocker: '<spring:message code="LocationSelectBox_Input.language.selects.stocker"/>',
        vehicle: '<spring:message code="LocationSelectBox_Input.language.selects.vehicle"/>',
        machine: '<spring:message code="LocationSelectBox_Input.language.selects.machine"/>',
        lifter: '<spring:message code="LocationSelectBox_Input.language.selects.lifter"/>',
        convayor: '<spring:message code="LocationSelectBox_Input.language.selects.convayor"/>',
        operatorPort: '<spring:message code="LocationSelectBox_Input.language.selects.operatorPort"/>',
        ohbGroup: '<spring:message code="LocationSelectBox_Input.language.selects.ohbGroup"/>',
      }
    },
    btnText: {
      select: '<spring:message code="LocationSelectBox.btn.select"/>',
      ok: '<spring:message code="LocationSelectBox.btn.ok"/>',
      clear: '<spring:message code="LocationSelectBox.btn.clear"/>'
    }
  };
</script>
<!-- Location利用ヘッダー部 end  -->