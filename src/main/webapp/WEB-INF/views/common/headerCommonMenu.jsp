<%--
 ******************************************************************************
 * @file        headerCommonMenu.jsp
 * @brief       ヘッダー共通メニュー埋め込み用JSP
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 * 2018/04/18 MACS4#0158  一覧表示ハイパーリンク化抑制対応            T.Iga/CSC
 ******************************************************************************
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- ヘッダー共通メニュー部分 start -->
<div class="mcs-header-menu-right">
    <div class="mcs-user-info">
        <ul class="mcs-user-info-ul">
            <li class="mcs-user-info-status"><span id="mcs-user-info-status-value">Dummy</span></li>
<%--        <li class="mcs-user-info-name"><span id="mcs-user-info-name-value">Dummy</span></li>                                     MACS4#0158 Del --%>
            <li class="mcs-user-info-name"><span id="mcs-user-info-name-value" x-ms-format-detection="none">Dummy</span></li>   <%-- MACS4#0158 Add --%>
            <li class="mcs-user-info-date"><span id="mcs-user-info-date-value">9999/12/31</span></li>
            <li class="mcs-user-info-time"><span id="mcs-user-info-time-value">23:59:59</span></li>
        </ul>
    </div>
    <!-- < --><!-- %@  include file="./headerMsgIcon.jsp" % >-->
    <%@ include file="./headerMenuIcon.jsp"%>
</div>
<!-- ヘッダー共通メニュー部分 end -->
