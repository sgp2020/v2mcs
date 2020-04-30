<%--
 ******************************************************************************
 * @file        TestCarrier.jsp
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
      search: '<spring:message code="II-007-03-001" />',
      save: '<spring:message code="II-007-03-002" />',
      reLoad: '<spring:message code="II-007-03-003" />',
      color: '<spring:message code="II-007-03-004" />',
      ret: '<spring:message code="II-007-03-005" />'
    },
    slideSearch: {
      carrierId: '<spring:message code="II-007-04-001" />',
      currentTscId: '<spring:message code="II-007-04-002" />',
      extract: '<spring:message code="II-007-04-003" />',
      clear: '<spring:message code="II-007-04-004" />',
      ret: '<spring:message code="II-007-04-005" />'
    },
    btnText: {
      confirm: '<spring:message code="II-007-05-001"/>',
      saveReturn: '<spring:message code="II-007-05-002"/>'
    },
    // MACS4#0047 Add Start
    dialog: {
      confirm: '<spring:message code="DIALOG.BUTTON_NAME.OK" />',
      ret: '<spring:message code="DIALOG.BUTTON_NAME.RETURN" />'
    },
    colorText: {	
      Future:    '#80FF00',
      Past:    '#808000'
    }
  };

 var screenValue = {
		  carrierState: JSON.parse('${II_007_01_001}'),
		  currentTscId: JSON.parse('${II_007_01_002}')
  };

</script>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="II-007-01-001" /></title>
<link rel="stylesheet" href="<c:url value="/resources/css/info/mcs-TestCarrierList.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/info/mcs-TestCarrierList.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor.js?Ver=${version}'/>"></script>
<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <!-- ヘッダーエリア -->
    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-007-01-001" />
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
                <td style="color:#6495ED;"><spring:message code="II-007-01-014" />  </td>
                <td style="color:#6495ED;"><div id="searchInfo"></div></td>
            </tr>
            <tr>
                <td><spring:message code="II-007-02-001" /></td>
                <td class="mcs-td-selectbox"> <div id="sel-ctrl"></div>     </td>
            </tr>
        </table>
    </div>

    <div class="mcs-host-comm-information-content">
        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div id="mcs-content-subtitle1" class="mcs-content-subtitle">
                <spring:message code="II-007-01-002" />
            </div>

            <div id="list-table-target" style="color:#F8F8FF"> </div>
            <div id="ColorDiv" >
			         <div id="colorColumn" class="remarks-content-color">
			         	 <div id="mcs-content-Modeinfo">
			         	 	<spring:message code="II-007-06-001" />
			         	 </div>	
			             <div id="color1"></div>
			             <div id="color2"></div>
			         </div>
			         <div id="ctrlColumn" >  
		             	  <div class="mcs-content-Futrue">
			         	 	<spring:message code="II-007-06-002" />
			         	 </div>	
			         	 
			         	 <div class="mcs-content-Past">
			         	 	<spring:message code="II-007-06-003" />
			         	 </div>	
			         </div>
		       
        	 </div>
        </div>
        <!-- 一覧画面 end -->
    </div>
    
      
    <!-- 右メニューエリア -->
    <nav id="mcs-right-menu">
        <div id="menu-btn-search" class="btn-mcs-slide" data-auth="${I007_REF}"></div>
        <div id="menu-btn-save" class="btn-mcs-slide" data-auth="${I007_REF}"></div>
        <div id="menu-btn-update" class="btn-mcs-slide" data-auth="${I007_REF}"></div>  
        <div id="menu-btn-color"  class="btn-mcs-slide" data-auth="${I007_REF}"></div>
        <div id="menu-btn-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <div id="mcs-slideMenu-search">
       
        <spring:message code="II-007-04-001" />
        <div id="mcs-search-carrierID"></div>
  		
  		<spring:message code="II-007-04-002" />
        <div id="mcs-search-currentTscId"></div>
               
        <!-- ボタン類 -->
        <div id="mcs-search-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-search-clean" class="btn-mcs-slide btn-mcs-slide-clear"></div>
        <div id="mcs-search-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!-- 子スライドメニュー(CSV保存) -->
    
    <div id="mcs-saveMenu">
        <div id="btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-saveReturn" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
   
    

</body>
</html>