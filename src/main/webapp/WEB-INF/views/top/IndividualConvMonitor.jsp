<%--
 ******************************************************************************
 * @file        IndividualConvMonitor.jsp
 * @brief       個別モニタ(コンベアモニタ)関連のJSP
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
<title><spring:message code="IT-001-16-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/top/mcs-IndividualConvMonitor.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/top/mcs-IndividualConvMonitor.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    state: {
      alarmDateTime: '<spring:message code="IT-001-16-013"/>',
      alarmId: '<spring:message code="IT-001-16-014"/>',
      alarmText: '<spring:message code="IT-001-16-015"/>'
    },
    port: {
      portId: '<spring:message code="IT-001-17-005"/>',
      type: '<spring:message code="IT-001-17-006"/>',
      ioMode: '<spring:message code="IT-001-17-007"/>',
      available: '<spring:message code="IT-001-17-008"/>',
      logicalState: '<spring:message code="IT-001-17-009"/>'
    },
    microCmd: {
      commandId: '<spring:message code="IT-001-18-005"/>',
      priority: '<spring:message code="IT-001-18-006"/>',
      carrierId: '<spring:message code="IT-001-18-007"/>',
      microFrom: '<spring:message code="IT-001-18-008"/>',
      microTo: '<spring:message code="IT-001-18-009"/>',
      jobState: '<spring:message code="IT-001-18-010"/>',
      waitInTime: '<spring:message code="IT-001-18-011"/>'
    },
    slideMenuBtn: {
      reload: '<spring:message code="IT-001-19-001"/>',
      state: '<spring:message code="IT-001-19-002"/>',
      port: '<spring:message code="IT-001-19-003"/>',
      microCmd: '<spring:message code="IT-001-19-004"/>',
      ret: '<spring:message code="IT-001-19-005"/>'
    }
  };
  var screenValue = {
    amhsName: JSON.parse('${AmhsNameList}'),
    amhsId: '${amhsId}'
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IT-001-16-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td><span><spring:message code="IT-001-16-002" /></span></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-amhs"></div>
                </td>
            </tr>
        </table>
    </div>

    <div class="mcs-content mcs-with-subheader mcs-with-subtitle">
        <!-- 状態表示画面 start -->
        <div id="state-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-16-004" /></span>
            </div>
            <div id="state-text-target">
                <div>
                    <div>
                        <span><spring:message code="IT-001-16-005" /></span>
                    </div>
                    <div id="state-comm-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-16-007" /></span>
                    </div>
                    <div id="state-control-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-16-009" /></span>
                    </div>
                    <div id="state-system-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-16-011" /></span>
                    </div>
                    <div id="state-available"></div>
                </div>
            </div>
            <div id="state-table-target"></div>
        </div>
        <!-- 状態表示画面 end -->

        <!-- ポート表示画面 start -->
        <div id="port-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-17-004" /></span>
            </div>
            <div id="port-table-target"></div>
        </div>
        <!-- ポート表示画面 end -->

        <!-- Microコマンド表示画面 start -->
        <div id="microCmd-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-18-004" /></span>
            </div>
            <div id="microCmd-table-target"></div>
        </div>
        <!-- Microコマンド表示画面 end -->
    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <div id="btn-reload" class="btn-mcs-slide"></div>
        <div id="btn-state" class="btn-mcs-slide"></div>
        <div id="btn-port" class="btn-mcs-slide"></div>
        <div id="btn-micro-cmd" class="btn-mcs-slide"></div>
        <div id="btn-return" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

</body>
</html>
