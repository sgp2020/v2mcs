<%--
 ******************************************************************************
 * @file        idListSelectHeader.jsp
 * @brief       IDリスト選択部品用JSP
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
<!-- IdListSelect利用ヘッダー部 start -->
<link rel="stylesheet" href="<c:url value='/resources/css/component/mcs-IdListSelect.css?Ver=${version}'/>" media="all">
<script src="<c:url value="/resources/js/component/mcs-IdListSelect.js?Ver=${version}" />"></script>
<script>
  // メッセージ読み込み
  McsIdListSelect.language = {
    idListSelectCommon: {
      label: '<spring:message code="IdListSelect_Common.language.label"/>',
      btn: {
        select: '<spring:message code="IdListSelect_Common.language.btn.select"/>',
        clear: '<spring:message code="IdListSelect_Common.language.btn.clear"/>',
        ok: '<spring:message code="IdListSelect_Common.language.btn.ok"/>',
        cancel: '<spring:message code="IdListSelect_Common.language.btn.cancel"/>'
      }
    },
    idListSelect: {
      label: '<spring:message code="IdListSelect.language.label"/>',
      headers: {
        processId: '<spring:message code="IdListSelect.language.headers.processId"/>',
        processName: '<spring:message code="IdListSelect.language.headers.processName"/>',
        amhsNumber: '<spring:message code="IdListSelect.language.headers.amhsNumber"/>',
        amhsNameList: '<spring:message code="IdListSelect.language.headers.amhsNameList"/>'
      }
    },
    idListSelectGroup: {
      label: '<spring:message code="IdListSelect_Group.language.label"/>',
      headers: {
        processGroupId: '<spring:message code="IdListSelect_Group.language.headers.processGroupId"/>',
        nodeNameList: '<spring:message code="IdListSelect_Group.language.headers.nodeNameList"/>'
      }
    }
  };
</script>
<!-- IdListSelect利用ヘッダー部 end  -->