<%--
 ******************************************************************************
 * @file        StockerInfo.jsp
 * @brief       StockerInformation　画面用JSP
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
<title><spring:message code="II-005-01-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/info/mcs-StockerInfo.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/info/mcs-StockerInfo.js?Ver=${version}'/>"></script>
<!-- 2020.03.17 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 -->
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor-RowClick.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    list: {
      btnText: {
        reload: '<spring:message code="II-005-02-001"/>',
        cancel: '<spring:message code="II-005-02-002"/>'
      }
    },
    <%-- STD 2020.03.15 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 --%>
    <%-- 画面にはColorを表示する--%>
    ctrlText: {
        Normal:  'Normal',
        Low:     'Low',
        High:    'High'
      },
      colorText: {	
          Normal:  '#33FF00',
          Low: 	   '#FFFF00',
          High:    '#FF5555'
        }
    <%-- END 2020.03.15 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 --%>
  };

  var screenValue = {
		tscId: 	  JSON.parse('${II_005_01_001}')
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<!-- <body onLoad="setTimeout('check()',100)"> -->
<body>

    <header id="mcs-header-menu">
        <h1>
            <spring:message code="II-005-01-001" />
        </h1>
        <%@ include file="../common/headerCommonMenu.jsp"%>
    </header>

    <div id="mcs-alert-dialog"></div>
    <div id="mcs-confirm-dialog"></div>
    <div id="mcs-information-dialog"></div>

    <!-- 空FOUP一覧画面ヘッダー -->
    <div id="mcs-subheader-menu">
        <table>
            <tr>
                <td><spring:message code="II-005-01-003" /></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-ctrl"></div>
                </td>
            </tr>
        </table>
    </div>

    <div class="mcs-stocker-information-content">

        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <spring:message code="II-005-01-002" />
            </div>
            <!--<div id="list-table-target" style="width: 100%; height: 100%;"> </div>-->
            <div id="list-table-target"> </div>
            <div id="ColorDiv" >
			         <div id="colorColumn" class="remarks-content-color">
			         	 <div id="mcs-content-Occupancy">
			         	 	<spring:message code="II-005-03-001" />
			         	 </div>	
			             <div id="color1"></div>
			             <div id="color2"></div>
			             <div id="color3"></div>
			         </div>
			         <!-- 
			         <div id="ctrlColumn1" class="remarks-content" >  
			             <div id="ctrl1" style="background:#000; color:#FFF"></div>
			             <div id="ctrl2" style="background:#000; color:#FFF"></div>
		             	 <div id="ctrl3" style="background:#000; color:#FFF"></div>
		             -->
			         <div id="ctrlColumn" >  
		             	  <div class="mcs-content-OccupancyN">
			         	 	<spring:message code="II-005-03-002" />
			         	 </div>	
		             	  <div class="mcs-content-OccupancyL">
			         	 	<spring:message code="II-005-03-003" />
			         	 </div>	
		             	  <div class="mcs-content-OccupancyH">
			         	 	<spring:message code="II-005-03-004" />
			         	 </div>	
			         </div>
        		</div>
        </div>
        <!-- 一覧画面 end -->

    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <!-- 一覧 -->
        <div id="list-btn-reload" class="btn-mcs-slide" data-auth="${I005_REF}"></div>
        <div id="list-btn-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </nav>

    <!-- スライドメニュー(CSV保存) start-->
    <div id="mcs-saveMenu" class="mcs-saveMenu">
        <div id="mcs-saveStartDatetime"></div>
        <div id="mcs-saveEndDatetime"></div>
        <div id="btn-confirm" class="btn-mcs-slide btn-mcs-slide-confirm"></div>
        <div id="btn-save-ret" class="btn-mcs-slide btn-mcs-slide-return"></div>
    </div>
    <!-- スライドメニュー(CSV保存) end -->

</body>
</html>
