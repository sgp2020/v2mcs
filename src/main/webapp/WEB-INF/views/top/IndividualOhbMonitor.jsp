<%--
 ******************************************************************************
 * @file        IndividualOhbMonitor.jsp
 * @brief       個別モニタ(OHBモニタ)関連のJSP
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- 共通ヘッダ --%>
<%@ include file="../common/commonHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="IT-001-20-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/top/mcs-IndividualOhbMonitor.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/top/mcs-IndividualOhbMonitor.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    state: {
      alarmDateTime: '<spring:message code="IT-001-20-013"/>',
      alarmId: '<spring:message code="IT-001-20-014"/>',
      alarmText: '<spring:message code="IT-001-20-015"/>',
      alarmLoc: '<spring:message code="IT-001-20-017"/>',
      vehicleID: '<spring:message code="IT-001-20-018"/>'
    },
    port: {
      <%-- listNo: '<spring:message code="IT-001-21-005"/>',
      portId: '<spring:message code="IT-001-21-006"/>',
      type: '<spring:message code="IT-001-21-007"/>',
      ioMode: '<spring:message code="IT-001-21-008"/>',
      available: '<spring:message code="IT-001-21-009"/>',
      carrierId: '<spring:message code="IT-001-21-010"/>' --%>
      portId: '<spring:message code="IT-001-21-006"/>',
      ioMode: '<spring:message code="IT-001-21-008"/>',
      available: '<spring:message code="IT-001-21-009"/>'
    },
    slideMenuBtn: {
      reload: '<spring:message code="IT-001-22-001"/>',
      state: '<spring:message code="IT-001-22-002"/>',
      port: '<spring:message code="IT-001-22-003"/>',
      ret: '<spring:message code="IT-001-22-004"/>'
    }
  };
  var screenValue = {
    displayNames: JSON.parse('${displayNameList}'),
    <%--displayName: '${displayName}', 20191225 DQY MOD displayName->displayId --%>
    displayId: '${displayId}',
    ohbId: JSON.parse('${IT_001_20_003}')
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>

    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IT-001-20-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <div id="mcs-subheader-menu" class="mcs-height-large">
        <table>
            <tr>
                <td><span><spring:message code="IT-001-20-002" /></span></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-amhs"></div>
                </td>
            </tr>
            <tr>
                <td><span><spring:message code="IT-001-20-016" /></span></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-ohb"></div>
                </td>
            </tr>
        </table>
    </div>

    <div class="mcs-content mcs-with-subheader-large mcs-with-subtitle">
        <!-- 状態表示画面 start -->
        <div id="state-screen" class="mcs-content mcs-with-subheader-large mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-20-004" /></span>
            </div>
            <div id="state-text-target">
            	<!-- 20191225 DQY ADD START -->
            	<div>
                    <div>
                        <span><spring:message code="IT-001-23-007" /></span>
                    </div>
                    <div id="state-control-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-009" /></span>
                    </div>
                    <div id="state-system-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-016" /></span>
                    </div>
                    <div id="alarm-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-005" /></span>
                    </div>
                    <div id="state-comm-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-017" /></span>
                    </div>
                    <div id="TSC-mode"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-018" /></span>
                    </div>
                    <div id="piece-mode"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-011" /></span>
                    </div>
                    <div id="state-available"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-23-019" /></span>
                    </div>
                    <div id="piece-available"></div>
                </div>
                <!-- 20191225 DQY ADD END -->
                
                <div>
                    <div>
                        <span><spring:message code="IT-001-20-005" /></span>
                    </div>
                    <div id="state-used-port"></div>
                </div>
                <!-- 20191226 DQY DEL END -->
                <!--  
                <div>
                    <div>
                        <span><spring:message code="IT-001-20-007" /></span>
                    </div>
                    <div id="state-empty-port"></div>
                </div>
                -->
                <!-- 20191226 DQY DEL END -->
                <div>
                    <div>
                        <span><spring:message code="IT-001-20-009" /></span>
                    </div>
                    <div id="state-total-port"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-20-011" /></span>
                    </div>
                    <div id="state-port-occupied"></div>
                </div>
            </div>
            <div id="state-table-target"></div>
        </div>
        <!-- 状態表示画面 end -->

        <!-- ポート表示画面 start -->
        <div id="port-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-21-004" /></span>
            </div>
            <div id="port-table-target"></div>
        </div>
        <!-- ポート表示画面 end -->
    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <div id="btn-reload" class="btn-mcs-slide"></div>
        <div id="btn-state" class="btn-mcs-slide"></div>
        <div id="btn-port" class="btn-mcs-slide"></div>
        <div id="btn-return" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

</body>
</html>
