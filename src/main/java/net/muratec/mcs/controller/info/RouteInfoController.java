//@formatter:off
/**
 ******************************************************************************
 * @file        RouteInfoController.java
 * @brief       アラーム情報表示関連のコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                     SGP                          
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.info.RouteInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示関連のコントローラクラス
 * @par       機能:
 *              RouteInformation（初期状態表示）
 *              getCsvFile（CSV出力）
 *              createCsvHeaderRecords（CSVヘッダー情報生成）
 *              createCsvTitleRecords（CSVTitle情報生成）
 *              createCsvItem（CSV項目オブジェクト生成）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class RouteInfoController extends BaseController {

    /** メッセージリソース */
    //@Autowired private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(RouteInfoController.class);

    public static Logger getLogger() {

        return logger;
    }

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;
    
    // ------------------------------------
    // アラーム情報画面用サービス
    // ------------------------------------
    @Autowired private RouteInfoService routeInfoService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     RouteInfo（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(アラーム情報表示)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/RouteInfo", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_ROUTE, logOperationType = ComConst.LogOperationType.GET, number = 1L)
    public String routeInfo(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_ROUTE.getRefAuthFuncId());

        // ----------------------------------------------
        // 自動更新機能の有効化
        // ----------------------------------------------
        autoReload.setInterval(model);

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        /*
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;
        */
        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> pieceList = routeInfoService.getSourceDestinationPieceBox();
        List<String[]> tabelNoList = routeInfoService.getTableNoBox();
       
        //tscIdBoxはAllを初期化表示する
        //hostNameBoxList.add(0, allTerms);
        //commStateBoxList.add(0, allTerms);

        // セレクトボックス要素をJSON化
       String pieceListJson = super.objectToJson(pieceList);
       String tabelNoJson = super.objectToJson(tabelNoList);
       
       model.addAttribute("II_009_00_001", pieceListJson);
       model.addAttribute("II_009_00_002", tabelNoJson);
       
       // バージョン情報付与
       ComFunction.setVersion(model);

       return "info/RouteInfo";
    }
    
}
