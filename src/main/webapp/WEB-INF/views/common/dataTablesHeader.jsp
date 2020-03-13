<%--
 ******************************************************************************
 * @file        dataTablesHeader.jsp
 * @brief       dataTables部品用JSP
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- DataTables利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/lib/datatables/datatables.min.css'/>" media="all">
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-DataTables.css?Ver=${version}'/>" media="all">
<script src="<c:url value='/resources/lib/datatables/datatables.mod.js'/>"></script>
<script src="<c:url value='/resources/js/component/mcs-DataTables.js?Ver=${version}'/>"></script>
<script>
  // メッセージ読み込み
  McsDataTables.language = {
    'emptyTable': '<spring:message code="DataTables.language.emptyTable"/>',
    'info': '<spring:message code="DataTables.language.info"/>',
    'infoEmpty': '<spring:message code="DataTables.language.infoEmpty"/>',
    'infoFiltered': '<spring:message code="DataTables.language.infoFiltered"/>',
    'infoPostFix': '<spring:message code="DataTables.language.infoPostFix"/>',
    'thousands': '<spring:message code="DataTables.language.thousands"/>',
    'lengthMenu': '<spring:message code="DataTables.language.lengthMenu"/>',
    'loadingRecords': '<spring:message code="DataTables.language.loadingRecords"/>',
    'processing': '<spring:message code="DataTables.language.processing"/>',
    'search': '<spring:message code="DataTables.language.search"/>',
    'zeroRecords': '<spring:message code="DataTables.language.zeroRecords"/>',
    'paginate': {
      'first': '<spring:message code="DataTables.language.paginate.first"/>',
      'last': '<spring:message code="DataTables.language.paginate.last"/>',
      'next': '<spring:message code="DataTables.language.paginate.next"/>',
      'previous': '<spring:message code="DataTables.language.paginate.previous"/>',
    },
    'aria': {
      'sortAscending': '<spring:message code="DataTables.language.aria.sortAscending"/>',
      'sortDescending': '<spring:message code="DataTables.language.aria.sortDescending"/>',
    },
    'buttons': {
      'colvis': '<spring:message code="DataTables.language.buttons.colvis"/>',
      'colsave': {
        'text': '<spring:message code="DataTables.language.buttons.colsave.text"/>',
        'success': '<spring:message code="DataTables.language.buttons.colsave.success"/>',
      }
    },
    'mypaginate': {
      'prev': '<spring:message code="DataTables.language.mypaginate.prev"/>',
      'next': '<spring:message code="DataTables.language.mypaginate.next"/>',
      'total': '<spring:message code="DataTables.language.mypaginate.total"/>',
    },
    'mylengthMenu': {
      'before': '<spring:message code="DataTables.language.mylengthMenu.before"/>',
      'after': '<spring:message code="DataTables.language.mylengthMenu.after"/>',
    },
    // Step4 2017_08_18
    'notCreatedTable': '<spring:message code="DataTables.language.notCreatedTable"/>'
  };
</script>
<!-- DataTables利用ヘッダー部 end  -->