<%--
 ******************************************************************************
 * @file        OhbInfo.jsp
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
 * 2020/03/12   2                                           SGP
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
<title><spring:message code="II-003-01-001" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/info/mcs-OhbInfo.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/info/mcs-OhbInfo.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-Table-BgColor.js?Ver=${version}'/>"></script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>


<script>
  var screenText = {
    ctrlText: {
        Enable:    'Enable',
        Disable:   'Disable',
        Test:      'Test',
        PM:        'PM',
        Hold:      'Hold',
        NotReady:  'Not Ready'
    },
    colorText: {	
        Enable:    '#00FF00',
        Disable:   '#808080',
        Test: 	   '#6E78FF',
        PM: 	   '#FF8C00',
        Hold: 	   '#FA320A',
        NotReady:  '#FF0000'
    },
    portText: {	
    	PortID:    'PortID',
    	CarriedID: 'CarriedID',
    	LastStoredTime: 'Last Stored Time'
   }
  };

</script>


</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-003-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>


    <!-- 一覧画面 start -->
    <div id="list-screen" class="mcs-content mcs-with-subtitle">
    
        <div id="mcs-subtitle-list" class="mcs-content-subtitle">
            <spring:message code="II-003-01-002" />
        </div>
        
       <!-- <div id="lst-table-target" style="width: 60%; height: 100%;"></div>  -->
       <div id="lst-table-target"></div>
       <div id="state-text-target-ohbPortRlt"></div> 
        
        <div id="ColorDiv" >
	         <div id="colorColumn1" class="remarks-content-color">
	             <div id="color1"></div>
	         </div>
	         <div id="ctrlColumn1" class="remarks-content">  
	             <div id="ctrl1"></div>
	         </div>
	         
	         <div id="colorColumn2" class="remarks-content-color">
	             <div id="color2"></div>
	         </div>
	         <div id="ctrlColumn2" class="remarks-content">  
	             <div id="ctrl2"></div>
	         </div>
	         
	         <div id="colorColumn3" class="remarks-content-color">
	             <div id="color3"></div>
	         </div>
	         <div id="ctrlColumn3" class="remarks-content">  
             	<div id="ctrl3"></div>
             </div>
             
              <div id="colorColumn4" class="remarks-content-color">
	             <div id="color4"></div>
	         </div>
	         <div id="ctrlColumn4" class="remarks-content">  
             	<div id="ctrl4"></div>
             </div>
             
              <div id="colorColumn5" class="remarks-content-color">
	             <div id="color5"></div>
	         </div>
	         <div id="ctrlColumn5" class="remarks-content">  
             	<div id="ctrl5"></div>
             </div>
             
              <div id="colorColumn6" class="remarks-content-color">
	             <div id="color6"></div>
	         </div>
	         <div id="ctrlColumn6" class="remarks-content">  
             	<div id="ctrl6"></div>
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