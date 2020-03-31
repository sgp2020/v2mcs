<%--
 ******************************************************************************
 * @file        RouteInfo.jsp
 * @brief       アラーム情報表示画面用JSP
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note       
 * ----------------------------------------------------------------------------
 * DATE        VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25   2                                           SGP
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
<%-- グリッドヘッダ --%>
<%@ include file="../common/dataTablesHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="II-008-01-001" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/info/mcs-RouteInfo.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/info/mcs-RouteInfo.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-Table-BgColor.js?Ver=${version}'/>"></script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>


<script>
  var screenText = {
    ctrlText: {
    	Disable:    'Disable',
       
    },
    colorText: {	
    	Disable:    '#707064',
      
    },
    destText: {	
    	DestNo:     'Dest No',
    	EQPID:      'EQPID',
    	Connection: 'Connection'
   }
  };
  var screenValue = {
		  pieceListJson: JSON.parse('${II_009_00_001}'),
		  tabelNoJson:   JSON.parse('${II_009_00_002}'),
		  routeState:    JSON.parse('${II_009_00_003}')
  };

</script>


</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-008-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    
    <!-- search -->
    <div id="mcs-subheader-menu" style="font-size:18px;" >
        <table>
        	<tr>
                <td>Route State :&nbsp;<!--<spring:message code="II-005-01-003" />--></td>
                <td >
                    <span id="routeState" ><!--Completed  -->  &nbsp;&nbsp;</span>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current Table No :</td>
                <td class="">
                    <span id="currentTableNo" style="color:blue"> &nbsp;${II_009_00_004}&nbsp;&nbsp;</span>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Source :</td>
                <td class="mcs-td-selectbox">
                    <div id="source"></div>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Destination :</td>
                <td class="mcs-td-selectbox">
                    <div id="destination"></div>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Table No :</td>
                <td class="mcs-td-selectbox">
                    <div id="tableNo"></div>
                </td>
                <td class="mcs-td-searchBtn">
                    <div id="btn-search"></div>
                </td>
            </tr>
        </table>
    </div>
    <!-- 一覧画面 start -->
    <!--<div id="list-screen" class="mcs-content mcs-with-subtitle">-->
    <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
    
         <div id="mcs-subtitle-list" class="mcs-content-subtitle">
            <spring:message code="II-008-01-002" />
        </div>
        
       <!-- <div id="lst-table-target" style="width: 60%; height: 100%;"></div>  -->
       <div id="lst-table-target"></div>
       <div id="state-text-target-dest"></div> 
        
       <div id="ColorDiv" >
	       <div id="colorColumn1" class="remarks-content-color">
	           <div id="color1"></div>
	       </div>
	       <div id="ctrlColumn1" class="remarks-content">  
	           <div id="ctrl1"></div>
	       </div>
       </div>
    </div>

    <!-- 一覧画面 end -->

    <%-- MACS4#0047 Add Start --%>
    <!-- ダイアログ -->
    <div id="mcs-error-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <%-- MACS4#0047 Add End --%>
</body>
</html>