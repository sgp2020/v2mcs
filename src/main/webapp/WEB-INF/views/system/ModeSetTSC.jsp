<%--
 ******************************************************************************
 * @file        ModeSetTSC.jsp
 * @brief       ModeSetTSC　画面用JSP
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
 * 2020/05/07 v1.0.0      初版作成                                      　　　　　　　　　　　　　　　　　　                    天津村研　董
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
<title><spring:message code="IS-001-01-001" /></title>
<link rel="stylesheet" href="<c:url value='/resources/css/info/mcs-ModeSetTSC.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/js/info/mcs-ModeSetTSC.js?Ver=${version}'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables-BgColor.js?Ver=${version}'/>"></script>

<script>
  var screenText = {
    btnText: {
    	modeSet: '<spring:message code="IS-001-01-020"/>',
    	modeSetHistory: '<spring:message code="IS-001-01-021"/>',
    	color: '<spring:message code="IS-001-01-022"/>',
        reload: '<spring:message code="IS-001-01-023"/>',
        cancel: '<spring:message code="IS-001-01-024"/>'
    },
    slideModeSet: {
    	currentState:'<spring:message code="IS-001-02-002" />',
    	changeState: '<spring:message code="IS-001-02-003" />',
    	Comment:	 '<spring:message code="IS-001-02-004" />',
        extract:     '<spring:message code="IS-001-02-005" />',
        clear:       '<spring:message code="IS-001-02-006" />',
        ret: 	     '<spring:message code="IS-001-02-007" />'
        
    },
    slideModeSetHistory: {
    	tscName:	 '<spring:message code="IS-001-04-002" />',
    	currentMode: '<spring:message code="IS-001-04-003" />',
        ret: 	     '<spring:message code="IS-001-04-011" />'
        
    },
    dialog: {
        listNotSelect: '<spring:message code="IH-002-01.001" />',
        listRet: '<spring:message code="IH-002-01.002" />'
    },
    <%-- 画面にはColorを表示する--%>
    ctrlText: {
        Normal:  'Normal',
        Low:     'Low',
        High:    'High'
      },
      colorText: {	
          enable :  '#00FF00',
          disable:  '#808080',
          test	 :  '#6E78FF',
          PM	 :  '#FF8C00',
          notReady:'#FF0000'
        }
  };

  var screenValue = {
		tscType: JSON.parse('${IS_001_01_001}')
  };
</script>

<%-- デザイン適用ヘッダ --%>
<%@ include file="../common/designHeader.jsp"%>
</head>

<body>

    <header id="mcs-header-menu">
        <h1>
            <spring:message code="IS-001-01-001" />
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
                <td><spring:message code="IS-001-01-003" /></td>
                <td class="mcs-td-selectbox">
                    <div id="sel-ctrl"></div>
                </td>
            </tr>
        </table>
    </div>

    <div class="mcs-modeSetTsc-system-content">

        <!-- 一覧画面 start -->
        <div id="list-screen" class="mcs-content mcs-with-subheader mcs-with-subtitle">
            <div class="mcs-content-subtitle">
                <spring:message code="IS-001-01-002" />
            </div>
            <div id="list-table-target"> </div>
            <!-- スライドメニュー 検索用 -->
		    <div id="mcs-slideMenu-modeSet">
		        <spring:message code="IS-001-02-001" />
		        <div id="mcs-search-hostName" style="width:200px"></div>
		        
		        <spring:message code="II-009-04-002" />
		        <div id="mcs-search-commState"></div>
		        
		        <!-- ボタン類 -->
		        <div id="mcs-search-extract" class="btn-mcs-slide btn-mcs-slide-extact"></div>
		        <div id="mcs-search-clear" class="btn-mcs-slide btn-mcs-slide-clear"></div>
		        <div id="mcs-search-cancel" class="btn-mcs-slide btn-mcs-slide-return"></div>
		    </div>
            <div id="ColorDiv" >
			         <div id="colorColumn" class="remarks-content-color">
			         	 <div id="mcs-content-Occupancy">
			         	 	<spring:message code="IS-001-03-001" />
			         	 </div>	
			             <div id="color1"></div>
			             <div id="color2"></div>
			             <div id="color3"></div>
			             <div id="color4"></div>
			             <div id="color5"></div>
			         </div>
			         <div id="ctrlColumn" >  
		             	  <div class="mcs-content-Enable">
			         	 	<spring:message code="IS-001-03-002" />
			         	 </div>	
		             	  <div class="mcs-content-Disable">
			         	 	<spring:message code="IS-001-03-003" />
			         	 </div>	
		             	 <div class="mcs-content-Test">
			         	 	<spring:message code="IS-001-03-004" />
			         	 </div>	
		             	 <div class="mcs-content-PM">
			         	 	<spring:message code="IS-001-03-005" />
			         	 </div>	
		             	 <div class="mcs-content-NotReady">
			         	 	<spring:message code="IS-001-03-006" />
			         	 </div>	
			         </div>
        		</div>
        </div>
        <!-- 一覧画面 end -->

    </div>

    <!--  右メニュースライド -->
    <nav id="mcs-right-menu">
        <!-- 一覧 -->
        <div id="list-btn-modeSet" class="btn-mcs-slide" data-auth="${S001_REF}"></div>
        <div id="list-btn-modeSetHistory" class="btn-mcs-slide" data-auth="${S001_REF}"></div>
        <div id="list-btn-color"  class="btn-mcs-slide" data-auth="${S001_REF}"></div>
        <div id="list-btn-reload" class="btn-mcs-slide" data-auth="${S001_REF}"></div>
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
