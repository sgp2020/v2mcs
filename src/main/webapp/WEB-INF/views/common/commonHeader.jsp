<%--
 ******************************************************************************
 * @file        commonHeader.jsp
 * @brief       共通ヘッダー用JSP
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 共通ヘッダー部 start -->
<!-- favicon -->
<link rel="shortcut icon" href="<c:url value='/resources/img/favicon.ico'/>">

<meta charset="UTF-8">
<!-- jQuery -->
<script src="<c:url value='/resources/lib/jquery/jquery-2.2.4.min.js'/>"></script>
<script src="<c:url value='/resources/lib/jquery/jquery.inview.min.js'/>"></script>

<!-- bootstrap -->
<link rel="stylesheet" href="<c:url value='/resources/lib/bootstrap/bootstrap.min.css'/>" media="all">
<link rel="stylesheet" href="<c:url value='/resources/lib/bootstrap/bootstrap-theme.min.css'/>" media="all">
<script src="<c:url value='/resources/lib/bootstrap/bootstrap.min.js'/>"></script>

<!-- bootstrap datetimepicker -->
<link rel="stylesheet" href="<c:url value="/resources/lib/datetimepicker/css/bootstrap-datetimepicker.css" />">
<script src="<c:url value="/resources/lib/datetimepicker/js/moment-with-locales.min.js" />"></script>
<script src="<c:url value="/resources/lib/datetimepicker/js/bootstrap-datetimepicker.js" />"></script>

<!-- bootstrap select -->
<link rel="stylesheet" href="<c:url value="/resources/lib/bootstrap/bootstrap-select.css" />">
<script src="<c:url value="/resources/lib/bootstrap/bootstrap-select.js" />"></script>

<!-- bootstrap combo -->
<link rel="stylesheet" href="<c:url value="/resources/lib/bootstrap/bootstrap-combobox.css" />">
<script src="<c:url value="/resources/lib/bootstrap/bootstrap-combobox.js" />"></script>

<!-- jQuery UI -->
<link rel="stylesheet" href="<c:url value="/resources/lib/jquery-ui/jquery-ui.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/lib/jquery-ui/jquery-ui.theme.min.css" />">
<script src="<c:url value="/resources/lib/jquery-ui/jquery-ui.min.js" />"></script>

<!-- カラーパレットライブラリ -->
<script type="text/javascript" src="<c:url value="/resources/lib/farbtastic/farbtastic.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/lib/farbtastic/farbtastic.css" />" type="text/css" />

<!-- マルチセレクトボックスライブラリ -->
<link rel="stylesheet" href="<c:url value="/resources/lib/jquery-ui/jquery.multiselect.css" />">
<script src="<c:url value="/resources/lib/jquery-ui/jquery.multiselect.min.js" />"></script>

<!-- ツリーライブラリ -->
<link rel="stylesheet" href="<c:url value="/resources/lib/jstree/dist/themes/default-dark/style.min.css" />">
<script src="<c:url value="/resources/lib/jstree/dist/jstree.min.js" />"></script>

<!-- コンポーネント類 -->
<link rel="stylesheet" href="<c:url value="/resources/css/component/mcs-component.css?Ver=${version}" />">
<script>
  // 共通で利用する固定値
  var contextRoot = '<c:url value="/" />';
  var jsessionidIndex = contextRoot.lastIndexOf('/;jsessionid=');
  if (jsessionidIndex != -1) {
    contextRoot = contextRoot.substring(0, jsessionidIndex);
  } else {
    contextRoot = contextRoot.substring(0, contextRoot.lastIndexOf('/'));
  }
  window.mcsComponentContextRoot = contextRoot + '/';

  window.mcsComponentLanguage = '<spring:message code="COMMON.LANGUAGE" />';
  window.mcsDialogTitleInfo = '<spring:message code="DIALOG.TITLE.INFO" />';
  window.mcsDialogTitleWarn = '<spring:message code="DIALOG.TITLE.WARN" />';
  window.mcsDialogTitleError = '<spring:message code="DIALOG.TITLE.ERROR" />';
  window.mcsDialogTitleConfirm = '<spring:message code="DIALOG.TITLE.CONFIRM" />';
  window.mcsDialogBtnOk = '<spring:message code="DIALOG.BUTTON_NAME.OK" />';
  window.mcsDialogBtnCancel = '<spring:message code="DIALOG.BUTTON_NAME.CANCEL" />';
  window.mcsDialogBtnReturn = '<spring:message code="DIALOG.BUTTON_NAME.RETURN" />';
  window.mcsConstMsgAjaxError = '<spring:message code="CONST.MESSAGE.AJAX.ERROR" />';
  window.mcsConstMsgAjaxNoData = '<spring:message code="CONST.MESSAGE.NO.DATA" />';

  // 画面用テキスト
  window.COMMON_SCREEN_TEXT = {
    MCS_DIALOG_BOX: {
      TITLE: {
        INFO: '<spring:message code="DIALOG.TITLE.INFO" />'
      },
      BUTTON_NAME: {
        OK: '<spring:message code="DIALOG.BUTTON_NAME.OK" />'
      }
    },
    LOGIN_STATUS: {
      LOGGEDIN: '<spring:message code="HOME.LOGINSTATUS.LOGGEDIN" />',
      NOLOGIN: '<spring:message code="HOME.LOGINSTATUS.NOLOGIN" />'
    }
  };

  var comLoginUserId = '${loginUserId}';
  var comLoginUserName = '${loginUserName}';
  var comLoginUserDescription = '${loginUserDescription}';
  var comAutoLogoutTime = '${autoLogoutTime}';
  // var comTopScreenFlag  = false;
  var NOLOGIN_USER = 'NOLOGIN';
</script>
<script src="<c:url value="/resources/js/component/mcs-Loading.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-AutoReloadTimerManager.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-TextBox.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-TextArea.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-Button.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-CheckBox.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-Table.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-DateTime.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-SelectBox.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-ListTable.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-DialogBox.js?Ver=${version}" />"></script>
<script src="<c:url value='/resources/js/component/mcs-ColorPicker.js?Ver=${version}'/>"></script>
<script src="<c:url value="/resources/js/component/mcs-ComponentManager.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-BulkDialogManager.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-DeleteTaskManager.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-ButtonGroup.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-ComboBox.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-Tree.js?Ver=${version}" />"></script>
<script src="<c:url value="/resources/js/component/mcs-PopupWinLoad.js?Ver=${version}" />"></script>
<!-- 
<script src="<c:url value="/resources/js/component/mcs-MsgView.js?Ver=${version}" />"></script>
 -->
<script src="<c:url value="/resources/js/common/mcs-ComConst.js?Ver=${version}" />"></script>
<script>
  // 共通部品読み込み後の処理
  AutoReloadTimerManager.delay = parseInt('${AutoReloadTimerManagerDelay}', 10);

  McsBulkDialogManager.language = {
    DLGCHKB2BTN: {
      INFMSG: '<spring:message code="DCCP0025.DLGCHKB2BTN.INFMSG" />',
      BTNOK: '<spring:message code="DCCP0025.DLGCHKB2BTN.BTNOK" />',
      BTNCANCEL: '<spring:message code="DCCP0025.DLGCHKB2BTN.BTNCANCEL" />',
      CHKBMSG: '<spring:message code="DCCP0025.DLGCHKB2BTN.CHKBMSG" />'
    },
    DLG2BTN: {
      ERRMSG: '<spring:message code="DCCP0025.DLG2BTN.ERRMSG" />'
    },
    DLGBTN: {
      INFMSG: '<spring:message code="DCCP0025.DLGBTN.INFMSG" />',
      BTNOK: '<spring:message code="DCCP0025.DLGBTN.BTNOK" />'
    }
  };

//  McsMsgView.language = {
//    'TITLE': '<spring:message code="MsgView.title" />',
//    'BTNDEL': '<spring:message code="MsgView.btn.delete" />',
//    'BTNCLEAR': '<spring:message code="MsgView.btn.clear" />',
//    'BTNCLOSE': '<spring:message code="MsgView.btn.close" />',
//    'table': {
//      'date': '<spring:message code="MsgView.table.date" />',
//      'message': '<spring:message code="MsgView.table.message" />'
//    }
//  };
</script>

<!-- 自動ログアウト -->
<script src="<c:url value="/resources/js/component/mcs-IdleTimeout.js?Ver=${version}" />"></script>

<!-- スライドメニュー -->
<link rel="stylesheet" href="<c:url value="/resources/css/component/mcs-SlideMenu.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/component/mcs-SlideMenu.js?Ver=${version}'/>"></script>

<!-- 共通css -->
<link rel="stylesheet" href="<c:url value="/resources/css/mcs-common.css?Ver=${version}" />">
<script src="<c:url value='/resources/js/mcs-common.js?Ver=${version}'/>"></script>
<!-- 共通ヘッダー部 end -->