//@formatter:off
/**
 ******************************************************************************
 * @file        DefineLLCComController.java
 * @brief       
 * @par
 * @author       天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/01 v1.0.0      初版作成                                          天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.define;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComBeanConv;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComCsvItem;
import net.muratec.mcs.common.ComCsvOut;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.define.DefineLLCComEntity;
import net.muratec.mcs.entity.define.ReqGetDefineLLCComValidateEntity;
import net.muratec.mcs.entity.define.ReqGetDefineLLCComEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ErrorLog;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.define.DefineLLCComService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     DefineLLCComController画面
 * @par       機能:
 *              
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class DefineLLCComController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private DefineLLCComService defineLLCComService;
    
    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DefineLLCCom   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(DefineLLCCom一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      DefineLLCCom画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/DefineLLCCom", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.DEF_LLCCOM, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String DefineLLCCom(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.DEF_LLCCOM.getRefAuthFuncId());

        
        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "define/DefineLLCCom";
    }
    
}
