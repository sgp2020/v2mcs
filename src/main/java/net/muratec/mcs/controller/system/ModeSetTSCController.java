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
 * 2020/03/11  v1.0.0  	       初版作成                                       								天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.system;

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
import net.muratec.mcs.service.info.StockerInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     空FOUP管理　画面関連のコントローラクラス
 * @par       機能:
 *              emptyCarrier (空FOUP管理画面を表示)
 *              saveCsvEmptyCarrier  (空FOUP管理一覧CSV出力)
 *              saveCsvEmptyCarrierContami （コンタミ一覧CSV出力機能）
 *              saveCsvEmptyCarrierPurposeType （目的種別一覧CSV出力機能）
 *              saveCsvEmptyCarrierController （コントローラ一覧CSV出力機能）
 *              saveCsvEmptyCarrierSupplySrc（供給元一覧CSV出力機能）
 *              saveCsvEmptyCarrierDistributionDst（配給先一覧CSV出力機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * v1.0.0  	          初版作成                                       											天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Controller
public class ModeSetTSCController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private StockerInfoService stockerInfoService;
    
    @Autowired private SelectBoxService selBoxService;

/*    public static final Logger logger = LoggerFactory.getLogger(AMHSConfController.class);

    public static Logger getLogger() {

        return logger;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     emptyCarrier   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(空FOUP管理一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      空FOUP管理画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/ModeSetTSC", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.SYST_MODESETTSC, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String StockerInfo(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.SYST_MODESETTSC.getRefAuthFuncId());

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
        List<String[]> tscTypeBoxList = stockerInfoService.getStockerInfoIdBox();
       
	   // STD APL 2020.03.13 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 
       //tscIdBoxはAllを初期化表示する
       tscTypeBoxList.add(0, allTerms);
       // END APL 2020.03.13 天津村研　董  MCSV4　GUI開発  Ver2.0 Rev.000 


        // セレクトボックス要素をJSON化
       String tscTypeJson = super.objectToJson(tscTypeBoxList);
       model.addAttribute("IS_001_01_001", tscTypeJson);

        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "system/ModeSetTSC";
    }

}
