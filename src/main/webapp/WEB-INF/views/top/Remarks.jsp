<%--
 ******************************************************************************
 * @file        Remarks.jsp
 * @brief       凡例画面用JSP
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
<title><spring:message code="IT-001-27-001" /></title>

<link rel="stylesheet" href="<c:url value='/resources/css/top/mcs-Remarks.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/top/mcs-Remarks.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    commText: {
      onLineRemote: '<spring:message code="IT-001-27-021" />',
      offLine: '<spring:message code="IT-001-27-022" />',
      notComm: '<spring:message code="IT-001-27-023" />',
      onLineLocal: '<spring:message code="IT-001-27-024" />'
    },
    ctrlText: {
      <%-- 20200106 DQY DEL
      noAlarmsAuto: '<spring:message code="IT-001-27-003"/>',
      noAlarmsOther: '<spring:message code="IT-001-27-004"/>',
      alarms: '<spring:message code="IT-001-27-005"/>',
      pm: '<spring:message code="IT-001-27-006"/>',
      unknown: '<spring:message code="IT-001-27-007"/>',
      offline: '<spring:message code="IT-001-27-030"/>'
      --%>
      <%-- 20200107 DQY START--%>
   	  allUp:   '${IT_001_27_020}',
   	  error:   '${IT_001_27_021}',
      disable: '${IT_001_27_022}',
   	  test:    '${IT_001_27_023}',
   	  pmmode:  '${IT_001_27_024}',
      hold:    '${IT_001_27_025}',
   	  manual:  '${IT_001_27_026}',
   	  pausing: '${IT_001_27_027}',
   	  alarm:   '${IT_001_27_028}',
   	  offline: '${IT_001_27_029}',
   	  dummy:   '${IT_001_27_030}',
   	  block:   '${IT_001_27_031}',
   	  timeout: '${IT_001_27_032}',
   	  other:   '${IT_001_27_033}'
      <!-- 20200107 DQY END-->
    },
    portText: {
      noAlarms: '<spring:message code="IT-001-27-027"/>',
      someAlarms: '<spring:message code="IT-001-27-028"/>',
      alarms: '<spring:message code="IT-001-27-029"/>'
    },
    carrierText: {
      noCarrier: '<spring:message code="IT-001-27-015"/>',
      carrierExist: '<spring:message code="IT-001-27-016"/>'
    },
    colorText: {
    	allUp:   '${IT_001_27_040}',
    	error:   '${IT_001_27_041}',
    	disable: '${IT_001_27_042}',
    	test:    '${IT_001_27_043}',
    	pmmode:	 '${IT_001_27_044}',
    	hold: 	 '${IT_001_27_045}',
    	manual:	 '${IT_001_27_046}',
    	pausing: '${IT_001_27_047}',
    	alarm: 	 '${IT_001_27_048}',
    	offline: '${IT_001_27_049}',
    	dummy: 	 '${IT_001_27_050}',
    	block: 	 '${IT_001_27_051}',
    	timeout: '${IT_001_27_052}',
    	other: 	 '${IT_001_27_053}'
      }
  };
  var screenValue = {
    occuLowRate: JSON.parse('${IT_001_27_009}'),
    occuMiddleRate: JSON.parse('${IT_001_27_010}'),
    occuHighRate: JSON.parse('${IT_001_27_011}')
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IT-001-27-001" />
        </h1>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <div id="mcs-content" class="mcs-content">
        <!-- アイコン表示部 start -->
        <table id="container-screen">
            <tr>
                <td><div class="container" id="container1"></div></td>
                <td><div class="container" id="container2"></div></td>
            </tr>
        </table>
        <!-- アイコン表示部 end -->

        <!-- ステータス一覧 start 
        <span id="ctrlCol" class="remarks-condition"><spring:message code="IT-001-27-002" /></span>-->
        <span id="condition" style="font-size:18px" class=""><spring:message code="IT-001-27-002" /></span>
        <div>
            <!-- ステータス色 start -->
            <div id="color" class="remarks-content-color">
                <div id="color1"></div>
                <div id="color2"></div>
                <div id="color3"></div>
                <div id="color4"></div>
                <!-- 20200107 DQY DEL START -->
                <!-- 
                <div id="color5"></div>
                <div id="color6"></div>
                <div id="color7"></div>
                <div id="color8"></div>
                <div id="color9"></div>
                <div id="color10"></div>
                <div id="color11"></div>
                <div id="color12"></div>
                <div id="color13"></div>
                <div id="color14"></div>
                -->
                <!-- 20200106 DQY ADD END -->
            </div>
            <!-- ステータス色 end -->

            <!-- コントローラ状態 start -->
            <div id="ctrl" class="remarks-content">
                <div id="ctrl1"></div>
                <div id="ctrl2"></div>
                <div id="ctrl3"></div>
                <div id="ctrl4"></div>
                <!-- 20200107 DQY ADD START -->
                <!--
                <div id="ctrl5"></div>
                <div id="ctrl6"></div>
                <div id="ctrl7"></div>
                <div id="ctrl8"></div>
                <div id="ctrl9"></div>
                <div id="ctrl10"></div>
                <div id="ctrl11"></div>
                <div id="ctrl12"></div>
                <div id="ctrl13"></div>
                <div id="ctrl14"></div> -->
                <!-- 20200107 DQY ADD END -->
            </div>
            <!-- コントローラ状態 end -->
			<!-- 20200107 DQY ADD START -->
			<div id="color1" class="remarks-content-color">
                <div id="color5"></div>
                <div id="color6"></div>
                <div id="color7"></div>
                <div id="color8"></div>
            </div>
            
            
            <!-- 20200106 DQY ADD END -->

            <!-- 20200104 Song Mod Start -->
            <!-- 出庫状態 start -->
            <div id="carrier" class="remarks-content">
                 <!--20200108 DQY DEL
                <span id="carrierCol"><spring:message code="IT-001-27-014" /></span>
                -->
                <div id="ctrl5"></div>
                <div id="ctrl6"></div>
                <div id="ctrl7"></div>
                <div id="ctrl8"></div>
                
                <!--20200107 DQY DEL
                <div id="carrier1"></div>
                <div id="carrier2"></div>
                <div id="carrier3"></div>
                <div id="carrier4"></div>
                <div id="carrier5"></div>
                <div id="carrier6"></div> -->
                <!-- 20200107 DQY ADD START -->
                <!-- <div id="carrier7"></div>
                <div id="carrier8"></div>
                <div id="carrier9"></div>
                <div id="carrier10"></div>
                <div id="carrier11"></div>
                <div id="carrier12"></div>
                <div id="carrier13"></div>
                <div id="carrier14"></div> -->
                <!-- 20200107 DQY ADD END -->
            </div>
            <!-- 出庫状態 end -->
            
            <div id="color2" class="remarks-content-color">
                <div id="color9"></div>
                <div id="color10"></div>
                <div id="color11"></div>
                <div id="color12"></div>
            </div>
            
            <!-- 棚使用率 start -->
            <div id="shelf" class="remarks-content">
                <!--20200108 DQY DEL
                <span id="shelfCol"><spring:message code="IT-001-27-008" /></span>
                -->
                <div id="ctrl9"></div>
                <div id="ctrl10"></div>
                <div id="ctrl11"></div>
                <div id="ctrl12"></div>
                <!-- 20200107 DQY DEL
                <div id="shelf1"></div>
                <div id="shelf2"></div>
                <div id="shelf3"></div>
                <div id="shelf4"></div>
                <div id="shelf5"></div>
                <div id="shelf6"></div> -->
                <!-- 20200107 DQY ADD START -->
                <!-- <div id="shelf7"></div>
                <div id="shelf8"></div>
                <div id="shelf9"></div>
                <div id="shelf10"></div>
                <div id="shelf11"></div>
                <div id="shelf12"></div>
                <div id="shelf13"></div>
                <div id="shelf14"></div> -->
                <!-- 20200107 DQY ADD END -->
            </div>
            <!-- 棚使用率 end -->

			

            <!-- 通信状態 start 
            <!--
            <div id="comm" class="remarks-content">
                <span id="commCol"><spring:message code="IT-001-27-020" /></span>
                <div id="comm1"></div>
                <div id="comm2"></div>
                <div id="comm3"></div>
                <div id="comm4"></div>
                <div id="comm5"></div>
                <div id="comm6"></div>
            </div>
            -->
            
            <!-- 通信状態 end -->
			<div id="color2" class="remarks-content-color">
                <div id="color13"></div>
                <div id="color14"></div>
            </div>
            <!-- ポート状態 start -->
            <div id="port" class="remarks-content">
                <!--20200108 DQY DEL
                <span id="portCol">
                <spring:message code="IT-001-27-026" />
                </span>
                -->
                <div id="ctrl13"></div>
                <div id="ctrl14"></div>
                <!--20200107 DQY DEL
                <div id="port1"></div>
                <div id="port2"></div>
                <div id="port3"></div>
                <div id="port4"></div>
                <div id="port5"></div>
                <div id="port6"></div> -->
                <!-- 20200107 DQY ADD START -->
                <!-- <div id="port7"></div>
                <div id="port8"></div>
                <div id="port9"></div>
                <div id="port10"></div>
                <div id="port11"></div>
                <div id="port12"></div>
                <div id="port13"></div>
                <div id="port14"></div> -->
                <!-- 20200107 DQY ADD END -->
            </div>
            <!-- ポート状態 end -->
            
            <!-- 20200104 Song Mod End -->
            
        </div>
        <!-- ステータス一覧 end -->
    </div>
</body>
</html>
