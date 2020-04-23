<%--
 ******************************************************************************
 * @file        MacroDataDialog.jsp
 * @brief       MacroDataDialog
 * @par
 * @author      Dong
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE         VER.        DESCRIPTION                         AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/22                                                   Dong
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="<c:url value='/resources/css/hist/mcs-MacroDataDialog.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/hist/mcs-MacroDataDialog.js?Ver=${version}" />"></script>
<script>
// メッセージ読み込み
var macroDataDialogText = {
	macroDataList: '<spring:message code="IH-002-05-022" />',
	rum:		'<spring:message code="IH-002-05-001" />',
	carrierId:	'<spring:message code="IH-002-05-002" />',
	rcvTime:	'<spring:message code="IH-002-05-003" />',
	startTime:	'<spring:message code="IH-002-05-004" />',
	cmpTime:	'<spring:message code="IH-002-05-005" />',
	srcTscId:	'<spring:message code="IH-002-05-006" />',
	srcLoc:		'<spring:message code="IH-002-05-007" />',
	dstTscId:	'<spring:message code="IH-002-05-008" />',
	dstLoc:		'<spring:message code="IH-002-05-009" />',
    dstGroup:	'<spring:message code="IH-002-05-010" />',
    altTscId:	'<spring:message code="IH-002-05-011" />',
    altLoc:		'<spring:message code="IH-002-05-012" />',
    status:		'<spring:message code="IH-002-05-013" />',
	priority:	'<spring:message code="IH-002-05-014" />',
	cancelReq:	'<spring:message code="IH-002-05-015" />',
	time:		'<spring:message code="IH-002-05-016" />',
	hostCommand:'<spring:message code="IH-002-05-017" />',
	commandId:	'<spring:message code="IH-002-05-018" />',
	originator:	'<spring:message code="IH-002-05-019" />',
	rerouteReq: '<spring:message code="IH-002-05-020" />',
    ret: '<spring:message code="IH-002-05-021" />',
    dialogText: {
      errorMsg: '<spring:message code="COMMON.MESSAGE.NO_SELECT" />',
      ret: '<spring:message code="COMMON.BTN.RETURN" />'
    }
};

</script>

<%--
$.extend(true, screenText, {
    macroDataDialogText: macroDataDialogText
});
--%>





<%--
    // メッセージ読み込み
    var portLStateChangeDialogText = {
        controllerType: '<spring:message code="IM-903-01-001" />',
        controller: '<spring:message code="IM-903-01-002" />',
        portList: '<spring:message code="IM-903-01-003" />',
        target: '<spring:message code="IM-903-01-004" />',
        port: '<spring:message code="IM-903-01-005" />',
        logicalState: '<spring:message code="IM-903-01-006" />',
        logicalStateAfter: '<spring:message code="IM-903-01-007" />',
        confirm: '<spring:message code="IM-903-01-008" />',
        ret: '<spring:message code="IM-903-01-009" />',
        dialogText: {
          confirmMsg: '<spring:message code="IM-903-01.001" />',
          errorMsg: '<spring:message code="COMMON.MESSAGE.NO_SELECT" />',
          confirm: '<spring:message code="COMMON.BTN.CONFIRM" />',
          ret: '<spring:message code="COMMON.BTN.RETURN" />'
        }
    };
    
    var portLStateChangeDialogValue = {
        amhsType: JSON.parse('${IM_903_01_001}'),
        logicalState: JSON.parse('${IM_903_01_002}')
    };
    $.extend(true, screenText, {
        portLStateChangeDialogText: portLStateChangeDialogText
    });
    $.extend(true, screenValue, {
        portLStateChangeDialogValue: portLStateChangeDialogValue
    });
    
     --%>