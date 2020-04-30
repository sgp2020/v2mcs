<%--
 ******************************************************************************
 * @file        VehicleInfo.jsp
 * @brief       テストキャリア情報表示画面用JSP
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note       
 * ----------------------------------------------------------------------------
 * DATE        VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12   1                                          天津／張東江
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

<%-- メッセージ類取得 --%>

<script>

var screenText = {
    // メニュー用テキスト
    list: {
      save: '<spring:message code="II-004-03-001" />',
      reLoad: '<spring:message code="II-004-03-002" />',
      ret: '<spring:message code="II-004-03-003" />'
    },
    btnText: {
      confirm: '<spring:message code="II-004-05-001"/>',
      saveReturn: '<spring:message code="II-004-05-002"/>'
    },
    // MACS4#0047 Add Start
    dialog: {
      confirm: '<spring:message code="DIALOG.BUTTON_NAME.OK" />',
      ret: '<spring:message code="DIALOG.BUTTON_NAME.RETURN" />'
    },
    colorText: {	
        Assigned:    '#FFFF80'
      }
  };

 var screenValue = {
		  currentTscId: JSON.parse('${II_004_01_001}')
  };

</script>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="II-004-01-001" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/info/mcs-VehicleInfo.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/info/mcs-VehicleInfo.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor.js?Ver=${version}'/>"></script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-004-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <!-- ダイアログ -->
    <div id="mcs-alert-dialog"></div>
    <div id="mcs-error-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    
            
    <!-- 一覧画面ヘッダー -->
    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td><spring:message code="II-004-02-001" /></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-ctrl"></div>
                </td>
            </tr>
        </table>
    </div>
    
    <div class="mcs-host-comm-information-content">
        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">

            <div class="mcs-content-subtitle">
                <spring:message code="II-005-01-002" />
            </div>
            <div id="list-table-target" style="color:#F8F8FF"> </div>
            <div id="ColorDiv" >
			         <div id="colorColumn" class="remarks-content-color">
			         	 <div id="mcs-content-VehicleState">
			         	 	<spring:message code="II-004-06-001" />
			         	 </div>	
			             <div id="color1"></div>
			         </div>
			         <div id="ctrlColumn" >  
		             	  <div class="mcs-content-Assigned">
			         	 	<spring:message code="II-004-06-002" />
			         	 </div>	
			         </div>
        	 </div>
        </div>
        <!-- 一覧画面 end -->
    </div>
    
    <!-- 右メニューエリア -->
    <nav id="mcs-right-menu">
        <div id="menu-btn-save" class="btn-mcs-slide" data-auth="${I004_REF}"></div>
        <div id="menu-btn-update" class="btn-mcs-slide" data-auth="${I004_REF}"></div>  
        <div id="menu-btn-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <!-- 子スライドメニュー(CSV保存) -->
    
    <div id="mcs-saveMenu">
        <div id="btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-saveReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    
</body>
</html>