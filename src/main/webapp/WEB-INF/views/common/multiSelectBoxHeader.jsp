<%--
 ******************************************************************************
 * @file        multiSelectBoxHeader.jsp
 * @brief       複数選択式セレクトボックス部品用JSP
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
<!-- MultiSekectBox利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-ModifyTable.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/component/mcs-MultiSelectBox.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  McsMultiSelectBox.language = {
    menu: {
      checkAll: '<spring:message code="MultiSelect.OPTION.CheckAllText"/>',
      uncheckAll: '<spring:message code="MultiSelect.OPTION.UncheckAllText"/>',
      noneSelected: '<spring:message code="MultiSelect.OPTION.NoneSelectedText"/>',
      isSelected: '<spring:message code="MultiSelect.OPTION.SelectedText"/>',
      ListNumber: 5
    }
  };
</script>
<!-- MultiSekectBoxend -->