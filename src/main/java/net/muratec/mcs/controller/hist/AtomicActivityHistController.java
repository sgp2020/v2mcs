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
package net.muratec.mcs.controller.hist;

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
import net.muratec.mcs.service.hist.AtomicActivityHistService;

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
 *  20200318  Ver2.0→Ver4.0 AtomicActivityHistory画面                                     董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class AtomicActivityHistController extends BaseController {

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private AtomicActivityHistService atomicActivityHistService;
    
    @Autowired private SelectBoxService selBoxService;

/*    public static final Logger logger = LoggerFactory.getLogger(AMHSConfController.class);

    public static Logger getLogger() {

        return logger;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AtomicActivityHistory   （画面初期表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(AtomicActivityHistory一覧)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      AtomicActivityHistory画面を表示する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200318  Ver2.0→Ver4.0 AtomicActivityHistory画面                                     董 天津村研
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AtomicActivityHist", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 1L)
    public String AtomicActivityHist(HttpSession session, Locale locale, Model model) throws McsException {

        // アクセス権情報等
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getRefAuthFuncId());

        // ---------------------------------------
        // 空FOUP 一覧画面 コントローラIDセレクトボックス
        // ---------------------------------------
        
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;

        String[] stkTerms = new String[2];
        String[] ohbTerms = new String[2];
        String[] portTerms = new String[2];
        
        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------

        // セレクトボックス要素取得
        List<String[]> tscIdBoxList = atomicActivityHistService.getTscIdBox();
        List<String[]> stkData = atomicActivityHistService.getStkData();
        List<String[]> ohbData = atomicActivityHistService.getOhbData();
        List<String[]> portData = atomicActivityHistService.getPortData();
        
        List<String[]> sourceBoxList = null;
        List<String[]> destinationBoxList =null;
        if(stkData!=null) {
        	sourceBoxList = stkData;
        }
        if(ohbData!=null) {
        	sourceBoxList.addAll(ohbData);
        }
        if(portData!=null) {
        	sourceBoxList.addAll(portData);
        }
       
        destinationBoxList = sourceBoxList;
       //tscIdBoxはAllを初期化表示する
        tscIdBoxList.add(0, allTerms);
        sourceBoxList.add(0, allTerms);
        destinationBoxList.add(0, allTerms);

        // セレクトボックス要素をJSON化
       String tscIdJson = super.objectToJson(tscIdBoxList);
       String sourceJson = super.objectToJson(sourceBoxList);
       String destinationJson = super.objectToJson(destinationBoxList);
       
       model.addAttribute("IH_002_01_001", tscIdJson);
       model.addAttribute("IH_002_01_002", sourceJson);
       model.addAttribute("IH_002_01_003", sourceJson);

        // -------------
        // バージョン情報付与
        // -------------
        ComFunction.setVersion(model);

        return "hist/AtomicActivityHist";
    }

}
