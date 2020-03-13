<%--
 ******************************************************************************
 * @file        IndividualTscMonitor.jsp
 * @brief       個別モニタ(TSCモニタ)関連のJSP
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
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%-- 共通ヘッダ --%>
<%@ include file="../common/commonHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="IT-001-23-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/top/mcs-IndividualTscMonitor.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/top/mcs-IndividualTscMonitor.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    state: {
      alarmDateTime: '<spring:message code="IT-001-23-013"/>',
      alarmId: '<spring:message code="IT-001-23-014"/>',
      alarmText: '<spring:message code="IT-001-23-015"/>',
      alarmLoc: '<spring:message code="IT-001-23-020"/>',
      vehicleID: '<spring:message code="IT-001-23-021"/>'
    },
    vehicle: {
      vehicleId: '<spring:message code="IT-001-24-005"/>',
      state: '<spring:message code="IT-001-24-006"/>'
    },
    microCmd: {
      commandId: '<spring:message code="IT-001-25-005"/>',
      priority: '<spring:message code="IT-001-25-006"/>',
      carrierId: '<spring:message code="IT-001-25-007"/>',
      microFrom: '<spring:message code="IT-001-25-008"/>',
      microTo: '<spring:message code="IT-001-25-009"/>',
      jobState: '<spring:message code="IT-001-25-010"/>',
      waitInTime: '<spring:message code="IT-001-25-011"/>'
    },
    module: {
      moduleId: '<spring:message code="IT-001-91-004" />',
      moduleName: '<spring:message code="IT-001-91-005" />',
      available: '<spring:message code="IT-001-91-006" />'
    },
    <%-- 20191220 DQY DEL START
    slideMenuBtn: {
      reload: '<spring:message code="IT-001-26-001"/>',
      state: '<spring:message code="IT-001-26-002"/>',
      vehicle: '<spring:message code="IT-001-26-003"/>',
      microCmd: '<spring:message code="IT-001-26-004"/>',
      ret: '<spring:message code="IT-001-26-005"/>',
      module: '<spring:message code="IT-001-26-901"/>'
    }
    --%>
    <%-- 20191220 DQY ADD slideMenuBtn --%>
    slideMenuBtn: {
      reload: '<spring:message code="IT-001-26-001"/>',
      state: '<spring:message code="IT-001-26-002"/>',
      vehicle: '<spring:message code="IT-001-26-003"/>',
      ret: '<spring:message code="IT-001-26-005"/>',
    }
  };
  var screenValue = {
    <%--
    amhsName: JSON.parse('${AmhsNameList}'),
    amhsId: '${amhsId}'
    --%>
    displayNames: JSON.parse('${displayNameList}'),
    <%--displayName: '${displayName}', 20191220 DQY MOD displayName->displayId --%>
    displayId: '${displayId}',
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IT-001-23-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td><span><spring:message code="IT-001-23-002" /></span></td>
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
                <span><spring:message code="IT-001-23-004" /></span>
            </div>
            <div id="state-text-target">
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
            </div>
            <div id="state-table-target"></div>
        </div>
        <!-- 状態表示画面 end -->

        <!-- 台車表示画面 start -->
        <div id="vehicle-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-24-004" /></span>
            </div>
            <div id="vehicle-table-target"></div>
        </div>
        <!-- 台車表示画面 end -->

        <!-- Microコマンド表示画面 start -->
        <!--20191220 DQY DEL
        <div id="microCmd-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-25-004" /></span>
            </div>
            <div id="microCmd-table-target"></div>
        </div>
        -->
        <!-- Microコマンド表示画面 end -->

        <!-- モジュール表示画面 start -->
        <!--20191220 DQY DEL
        <div id="module-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-91-003" /></span>
            </div>
            <div id="module-table-target"></div>
        </div>
        -->
        <!-- モジュール表示画面 end -->
    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <div id="btn-reload" class="btn-mcs-slide"></div>
        <div id="btn-state" class="btn-mcs-slide"></div>
        <div id="btn-vehicle" class="btn-mcs-slide"></div>
        <!--20191220 DQY DEL
        <div id="btn-micro-cmd" class="btn-mcs-slide"></div>
        <div id="btn-module" class="btn-mcs-slide"></div>
        -->
        <div id="btn-return" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>
</body>
</html>
