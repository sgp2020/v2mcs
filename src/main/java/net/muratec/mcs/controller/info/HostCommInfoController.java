//@formatter:off
/**
 ******************************************************************************
 * @file        EmptyCarrierController.java
 * @brief       空FOUP画面関連のコントローラ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 * 2019/02/19 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseController;

import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.info.HostCommInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     HostCommInfo画面
 * @par       機能:
 *              HostCommInfo (HostCommInfo画面を表示)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 *  20200318  Ver2.0→Ver4.0 HostCommInformation画面                                     董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class HostCommInfoController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private HostCommInfoService hostCommInfoService;
    
    @Autowired private SelectBoxService selBoxService;

/*    public static final Logger logger = LoggerFactory.getLogger(AMHSConfController.class);

    public static Logger getLogger() {

        return logger;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     HostCommInformation   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(HostCommInformation一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      HostCommInformation画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200318  Ver2.0→Ver4.0 HostCommInformation画面                                     董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/HostCommInfo", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_HOSTCOMMINFO, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String HostCommInfo(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_HOSTCOMMINFO.getRefAuthFuncId());

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;

        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> hostNameBoxList = hostCommInfoService.getHostNameBox();
        List<String[]> commStateBoxList = hostCommInfoService.getCommStateBox();
       
       //tscIdBoxはAllを初期化表示する
        hostNameBoxList.add(0, allTerms);
        commStateBoxList.add(0, allTerms);

        // セレクトボックス要素をJSON化
       String hostNameJson = super.objectToJson(hostNameBoxList);
       String commStateJson = super.objectToJson(commStateBoxList);
       model.addAttribute("II_009_01_001", hostNameJson);
       model.addAttribute("II_009_01_002", commStateJson);

        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "info/HostCommInfo";
    }

}
