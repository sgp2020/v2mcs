<%--
 ******************************************************************************
 * @file        IndividualScMonitor.jsp
 * @brief       個別モニタ(SCモニタ)関連のJSP
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
<%-- Modifyヘッダ --%>
<%@ include file="../common/modifyTableHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<%--  <title><spring:message code="IT-001-11-001" /></title> --%>  <%-- 20191223 Song Del --%>
<title>${memberGroupName} Detail</title>  <%-- 20191223 Song Add --%>

<link rel="stylesheet" href="<c:url value='/resources/css/top/mcs-IndividualScMonitor.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/top/mcs-IndividualScMonitor.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    state: {
      alarmDateTime: '<spring:message code="IT-001-11-021"/>',
      alarmId: '<spring:message code="IT-001-11-022"/>',
      alarmText: '<spring:message code="IT-001-11-023"/>',
      alarmLoc: '<spring:message code="IT-001-11-024"/>',
      vehicleId: '<spring:message code="IT-001-11-025"/>'
    },
    
    port: {
      portId: '<spring:message code="IT-001-12-005"/>',
      ioMode: '<spring:message code="IT-001-12-007"/>',
      available: '<spring:message code="IT-001-12-008"/>',
      <%--  type: '<spring:message code="IT-001-12-006"/>',
      logicalState: '<spring:message code="IT-001-12-009"/>' --%> <%-- 20191231 Song Del For v4  --%>
    },
    
    
    microCmd: {
      commandId: '<spring:message code="IT-001-13-005"/>',
      priority: '<spring:message code="IT-001-13-006"/>',
      carrierId: '<spring:message code="IT-001-13-007"/>',
      microFrom: '<spring:message code="IT-001-13-008"/>',
      microTo: '<spring:message code="IT-001-13-009"/>',
      jobState: '<spring:message code="IT-001-13-010"/>',
      waitInTime: '<spring:message code="IT-001-13-011"/>'
    },
    transferJob: {
      waitTime: '<spring:message code="IT-001-14-005"/>',
      jobOwner: '<spring:message code="IT-001-14-006"/>',
      carrierId: '<spring:message code="IT-001-14-007"/>',
      priority: '<spring:message code="IT-001-14-008"/>',
      carrierJobState: '<spring:message code="IT-001-14-009"/>',
      location: '<spring:message code="IT-001-14-010"/>',
      fromAmhsId: '<spring:message code="IT-001-14-011"/>',
      fromPort: '<spring:message code="IT-001-14-012"/>',
      toAmhsId: '<spring:message code="IT-001-14-013"/>',
      toPort: '<spring:message code="IT-001-14-014"/>',
      microFrom: '<spring:message code="IT-001-14-015"/>',
      microTo: '<spring:message code="IT-001-14-016"/>',
      controller: '<spring:message code="IT-001-14-017"/>',
      waitInTime: '<spring:message code="IT-001-14-018"/>'
    },
    slideMenuBtn: {
      reload: '<spring:message code="IT-001-15-001"/>',
      state: '<spring:message code="IT-001-15-002"/>',
      port: '<spring:message code="IT-001-15-003"/>',
      microCmd: '<spring:message code="IT-001-15-004"/>',
      transferJob: '<spring:message code="IT-001-15-005"/>',
      ret: '<spring:message code="IT-001-15-006"/>'
    }
  };
  var screenValue = {
    <%--  amhsName: JSON.parse('${AmhsNameList}'), --%>  <%-- 20191223 Song Del 这是v2的代码不用 AmhsNameList了 改用displayNameList--%>
    <%--amhsId: '${amhsId}'--%>  <%-- 20191223 Song Del 这是v2的代码不用 amhsId了 改用displayId--%>
    displayNames: JSON.parse('${displayNameList}'),
    displayId: '${displayId}'
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>
    <header id="mcs-header-menu">
        <h1>
            ${memberGroupName} Monitor   <%-- <spring:message code="IT-001-11-001" />  --%>
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <%-- <td><span><spring:message code="IT-001-11-002" /></span></td>  --%>   <%-- 20200103 Song Del  --%>
                <td><span>${memberGroupName}</span></td>     <%-- 20200103 Song Add  --%>
               
                <td class="mcs-td-selectbox">
                    <div id="sel-amhs"></div>
                </td>
            </tr>
        </table>
    </div>

    <div class="mcs-content mcs-with-subheader mcs-with-subtitle">
        <!-- 状態表示画面 start -->
        <!-- 20191225 Song Mod Start -->
        <%-- 
        <div id="state-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-11-004" /></span>
            </div>
            <div id="state-text-target">
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-005" /></span>
                    </div>
                    <div id="state-comm-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-007" /></span>
                    </div>
                    <div id="state-control-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-009" /></span>
                    </div>
                    <div id="state-system-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-011" /></span>
                    </div>
                    <div id="state-available"></div>
                </div>
                <div class="state-stocker-zone-rlt">
                    <div>
                        <span><spring:message code="IT-001-11-013" /></span>
                    </div>
                    <div id="state-zone-occupied"></div>
                </div>
                <div class="state-stocker-zone-rlt">
                    <div>
                        <span><spring:message code="IT-001-11-015" /></span>
                    </div>
                    <div id="state-zone-capacity"></div>
                </div>
                <div class="state-stocker-zone-rlt">
                    <div>
                        <span><spring:message code="IT-001-11-017" /></span>
                    </div>
                    <div id="state-zone-utility"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-019" /></span>
                    </div>
                    <div id="state-amhs-l-state"></div>
                </div>
            </div>
            <div id="state-table-target"></div>
        </div>
        --%>
        <div id="state-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-11-004" /></span>
            </div>
            <div id="state-text-target">
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-007" /></span>
                    </div>
                    <div id="state-control-state"></div>
                </div>
                <div>
                    <div>
                       <!-- <span><spring:message code="IT-001-11-009" /></span>  -->  <!--20200103 Song Del -->
                       <span>${memberGroupName} State</span>  <!--20200103 Song Add -->
                    </div>
                    <div id="state-system-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-016" /></span>
                    </div>
                    <div id="alarm-state"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-005" /></span>
                    </div>
                    <div id="state-comm-state"></div>
                </div>
                <div>
                    <div>
                       <!-- <span><spring:message code="IT-001-11-017" /></span> -->  <!--20200103 Song Del -->
                       <span>${memberGroupName} Mode</span>  <!--20200103 Song Add -->
                    </div>
                    <div id="OCDC-mode"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-018" /></span>
                    </div>
                    <div id="piece-mode"></div>
                </div>
                <div>
                    <div>
                         <!-- <span><spring:message code="IT-001-11-011" /></span> -->  <!--20200103 Song Del -->
                         <span>${memberGroupName} Available</span>  <!--20200103 Song Add -->
                    </div>
                    <div id="state-available"></div>
                </div>
                <div>
                    <div>
                        <span><spring:message code="IT-001-11-019" /></span>
                    </div>
                    <div id="piece-available"></div>
                </div>
            </div>
            <div id="state-table-target"></div>
        </div> 
        <!-- 20191225 Song Mod End -->
        <!-- 状態表示画面 end -->

        <!-- ポート表示画面 start -->
        <div id="port-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-12-004" /></span>
            </div>
            <div id="port-table-target"></div>
        </div>
        <!-- ポート表示画面 end -->

        <!-- Microコマンド表示画面 start -->
        <div id="microCmd-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-13-004" /></span>
            </div>
            <div id="microCmd-table-target"></div>
        </div>
        <!-- Microコマンド表示画面 end -->

        <!-- 搬送Job表示画面 start -->
        <div id="trnJob-screen">
            <div class="mcs-content-subtitle">
                <span><spring:message code="IT-001-14-004" /></span>
            </div>
            <div id="trnJob-table-target"></div>
        </div>
        <!-- 搬送Job表示画面 end -->

    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <div id="btn-reload" class="btn-mcs-slide"></div>
        <div id="btn-state" class="btn-mcs-slide"></div>
        <div id="btn-port" class="btn-mcs-slide"></div>
        <div id="btn-micro-cmd" class="btn-mcs-slide"></div>
        <div id="btn-trn-job" class="btn-mcs-slide"></div>
        <div id="btn-return" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

</body>
</html>
