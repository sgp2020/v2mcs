<%--
 ******************************************************************************
 * @file        HostCommInfo.jsp
 * @brief       HostCommInformation　画面用JSP
 * @par
 * @author      天津村研　董
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/10 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　    天津村研　董
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
<%-- グリッドヘッダ --%>
<%@ include file="../common/dataTablesHeader.jsp"%>
<%-- 更新テーブルヘッダ --%>
<%@ include file="../common/modifyTableHeader.jsp"%>
<%-- IdListSelectヘッダ --%>
<%@ include file="../common/idListSelectHeader.jsp"%>
<%-- マルチセレクトボックスヘッダ --%>
<%@ include file="../common/multiSelectBoxHeader.jsp"%>

<%-- 画面固有ヘッダ --%>
<title><spring:message code="II-009-01-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/info/mcs-HostCommInfo.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/info/mcs-HostCommInfo.js?Ver=${version}'/>"></script>
<!-- 2020.03.17 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 -->
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor-RowClick.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    list: {
      btnText: {
        reload: '<spring:message code="II-009-02-001"/>',
        search: '<spring:message code="II-009-02-002"/>',
        color:  '<spring:message code="II-009-02-003"/>',
        cancel: '<spring:message code="II-009-02-004"/>'
      }
    },
    slideSearch: {
        hostName: '<spring:message code="II-009-04-001" />',
        commState: '<spring:message code="II-009-04-002" />',
        extract:   '<spring:message code="II-009-04-003" />',
        clear:     '<spring:message code="II-009-04-004" />',
        ret: 	   '<spring:message code="II-009-04-005" />'
        
    },
    ctrlText: {
        CommError:    'CommError'
    },
    colorText: {	
    	CommError:    '#08336B'
    }
  };

  var screenValue = {
		  hostName: JSON.parse('${II_009_01_001}'),
		  commState: JSON.parse('${II_009_01_002}')
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-009-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <!-- 一覧画面ヘッダー -->
    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td><spring:message code="II-009-01-003" /></td>
                <!-- <td class="mcs-td-selectbox">
                    <div id="sel-ctrl"></div>
                </td> -->
            </tr>
        </table>
    </div>

    <div class="mcs-host-comm-information-content">
        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <spring:message code="II-009-01-002" />
            </div>
            <!--<div id="list-table-target" style="width: 100%; height: 100%;"> </div>-->
            <div id="list-table-target"> </div>
            <div id="ColorDiv" >
			         <div id="colorColumn" class="remarks-content-color">
			         	 <div id="mcs-content-CommState">
			         	 	<spring:message code="II-009-03-001" />
			         	 </div>	
			             <div id="color1"></div>
			         </div>
			         <div id="ctrlColumn" >  
		             	  <div class="mcs-content-CommError">
			         	 	<spring:message code="II-009-03-002" />
			         	 </div>	
			         </div>
        	 </div>
        </div>
        <!-- 一覧画面 end -->
    </div>
    <!-- スライドメニュー 検索用 -->
    <div id="mcs-slideMenu-search">
        <spring:message code="II-009-04-001" />
        <div id="mcs-search-hostName"></div>
        
        <spring:message code="II-009-04-002" />
        <div id="mcs-search-commState"></div>
        
        <!-- ボタン類 -->
        <div id="mcs-search-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
        <div id="mcs-search-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
        <div id="mcs-search-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <!-- 一覧 -->
        <div id="list-btn-reload" class="btn-mcs-slide" data-auth="${I009_REF}"></div>
        <div id="list-btn-search" class="btn-mcs-slide" data-auth="${I009_REF}"></div>
        <div id="list-btn-color"  class="btn-mcs-slide" data-auth="${I009_REF}"></div>
        <div id="list-btn-ret"    class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>


</body>
</html>
