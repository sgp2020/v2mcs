//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorController.java
 * @brief       個別モニタ関連のコントローラ
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 * 2019/12/17 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.top;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.defines.GuiEnvironment;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.top.ReqOpenIndMonEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.ScreenMonitorMemberMapper;
import net.muratec.mcs.model.IconInfo;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.common.SelectBoxService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ関連のコントローラクラス
 * @par       機能:
 *              individualMonitor（個別モニタ画面を表示する）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Controller
public class IndividualMonitorController extends BaseController {

    /** セレクトボックスサービス */
    @Autowired private SelectBoxService selBoxService;

    /** 自動更新機能 */
    @Autowired private AutoReloadTimerManagerService autoReload;

    /** 操作ログサービス */
    @Autowired private OpeLogService opeLogService;

    /** メッセージプロパティ */
    @Autowired private MessageSource messageSource;
    @Autowired private ScreenMonitorMemberMapper screenMonitorMemberMapper; // MACS4#MACSV2 Add
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     個別モニタ表示用メソッド
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先パス
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    // Step4 2017/09/05
    @RequestMapping(value = "/Individual", method = RequestMethod.POST)
    public String individualMonitor(HttpSession session, Locale locale, Model model,
            @ModelAttribute("postPopupWin") ReqOpenIndMonEntity reqEntity) throws McsException {

        // ------------------------------------
        // ユーザ情報のチェック＆セット
        // ------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // 自動更新機能の有効化(個別モニタ用インターバルを設定)
        // ------------------------------------
        autoReload.setIntervalByKey(model, ComConst.SystemParameter.Key.AUTO_RELOAD_INTERVAL_INDIVIDUAL_MONITOR);

        // ------------------------------------
        // 操作ログ情報エンティティ生成
        // ------------------------------------
        OpeLogInfoEntity opeLogInfo = new OpeLogInfoEntity();

        // 遷移先JSPファイルのURL
        String destJspFile = null;
        // コントローラ要素の取得条件(AMHSタイプ)
//        List<Short> amhsTypeList = new ArrayList<Short>();// MACS4#MACSV2 Del
        List<String> tscTypeList = new ArrayList<String>(); // MACS4#MACSV2 Add

        //20191217 DQY ADD START // MACS4#MACSV2 Add
//        List<IconInfo> labelList = screenMonitorMemberMapper.selectIconType(); 
        
//        int typeNum = 0;
//        String[] tscType= new String[labelList.size()];
        /*String tscType;
        for (IconInfo iconInfo : labelList) {
//        	 tscType[typeNum] = iconInfo.getMemberGroup();
        	 tscType = iconInfo.getMemberGroup();
        	 if(tscType.equals(reqEntity.getMemberGroup())) 
        	 {
        		 
        	 }
//        	 typeNum++;
        }*/
        	
        	
        // 画面からの入力AMHSタイプのチェック
//        if (ComConst.AmhsType.STR_CODE_STC.equals(reqEntity.amhsType) || ComConst.AmhsType.STR_CODE_LFC.equals(reqEntity.amhsType) || ComConst.AmhsType.STR_CODE_TSTC.equals(reqEntity.amhsType)
//                || ComConst.AmhsType.STR_CODE_RSBC.equals(reqEntity.amhsType) || ComConst.AmhsType.STR_CODE_OPC.equals(reqEntity.amhsType)) {
        if (GuiEnvironment.TSC_TYPE_OCDC.equals(reqEntity.memberGroup) 
        	|| GuiEnvironment.TSC_TYPE_XCDC.equals(reqEntity.memberGroup) 
        	|| GuiEnvironment.TSC_TYPE_STC.equals(reqEntity.memberGroup)) 
        {
            // SCモニタ AMHS_TYPE in (1, 21, 25, 31, 51)
            destJspFile = "/top/IndividualScMonitor";

            // 取得するコントローラ要素のタイプを指定
            if(GuiEnvironment.TSC_TYPE_OCDC.equals(reqEntity.memberGroup)) {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_OCDC);
            }else if(GuiEnvironment.TSC_TYPE_XCDC.equals(reqEntity.memberGroup)) {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_XCDC);
            }else if(GuiEnvironment.TSC_TYPE_STC.equals(reqEntity.memberGroup)) {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_STC);
            }
            
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	
           
           /* // 操作ログの情報設定（SCモニタ）（アノテーション記載情報を転記）
            opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 1L);*/

        } 
        else if (GuiEnvironment.TSC_TYPE_LIMC.equals(reqEntity.memberGroup)) {
            // TSCモニタ AMHS_TYPE in (81, 85)
//            destJspFile = "/top/IndividualTscMonitor";
            destJspFile = "/top/IndividualScMonitor";

            // 取得するコントローラ要素のタイプを指定
            tscTypeList.add(GuiEnvironment.TSC_TYPE_LIMC);
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	//20200106 DQY ADD

            /*//20191225 DQY DEL FOR OPERATOROG
             * // 操作ログの情報設定（TSCモニタ）（アノテーション記載情報を転記）
            opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 7L);*/

        }
        else if (GuiEnvironment.TSC_TYPE_SRC320.equals(reqEntity.memberGroup)) {//20191224 DQY ADD
        	// TSCモニタ AMHS_TYPE in (81, 85)
//        	destJspFile = "/top/IndividualTscMonitor";//20200102 DQY DEL
            destJspFile = "/top/IndividualScMonitor";//20200102 DQY ADD
        	
        	// 取得するコントローラ要素のタイプを指定
        	tscTypeList.add(GuiEnvironment.TSC_TYPE_SRC320);
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	//20200106 DQY ADD

        	/*// 操作ログの情報設定（TSCモニタ）（アノテーション記載情報を転記）
        	opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
        			ComConst.LogOperationType.GET, 7L);*/
        	
        }
        else if (GuiEnvironment.TSC_TYPE_SRC350.equals(reqEntity.memberGroup)) {//20191224 DQY ADD
        	// TSCモニタ AMHS_TYPE in (81, 85)
//        	destJspFile = "/top/IndividualTscMonitor";//20200102 DQY DEL
            destJspFile = "/top/IndividualScMonitor";//20200102 DQY ADD
        	
        	// 取得するコントローラ要素のタイプを指定
        	tscTypeList.add(GuiEnvironment.TSC_TYPE_SRC350);
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	//20200106 DQY ADD

        	/*// 操作ログの情報設定（TSCモニタ）（アノテーション記載情報を転記）
        	opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
        			ComConst.LogOperationType.GET, 7L);*/
        	
        }
        else if (GuiEnvironment.TSC_TYPE_OHBC.equals(reqEntity.memberGroup)) {
            // OHBモニタ AMHS_TYPE = 61
            destJspFile = "/top/IndividualOhbMonitor";//20191224 DQY DEL
//        	destJspFile = "/top/IndividualTscMonitor"; //20191224 DQY ADD


            // 取得するコントローラ要素のタイプを指定
            tscTypeList.add(GuiEnvironment.TSC_TYPE_OHBC);

            // AMHS_IDに対するOHB IDリストを取得
//            List<String[]> ohbSelItem = selBoxService.getOhbIdByAmhsId(reqEntity.displayName);
            List<String[]> ohbSelItem = selBoxService.getOhbIdByAmhsId(reqEntity.displayId);
            model.addAttribute("IT_001_20_003", super.objectToJson(ohbSelItem));
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	//20200106 DQY ADD

            /*// 操作ログの情報設定（OHBモニタ）（アノテーション記載情報を転記）
            opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 5L);*/

//        } else if (ComConst.AmhsType.STR_CODE_OHVC.equals(reqEntity.amhsType) || ComConst.AmhsType.STR_CODE_VLFT.equals(reqEntity.amhsType)) {//20191218 dqy del
        } else if (GuiEnvironment.TSC_TYPE_OHTC.equals(reqEntity.memberGroup)
        		|| GuiEnvironment.TSC_TYPE_LIFTERC.equals(reqEntity.memberGroup)
        		||GuiEnvironment.TSC_TYPE_XLFT.equals(reqEntity.memberGroup)) {
            // TSCモニタ AMHS_TYPE in (81, 85)
//        	destJspFile = "/top/IndividualTscMonitor"; //20191224 DQY DEL
            destJspFile = "/top/IndividualScMonitor";//20200102 DQY ADD



            // 取得するコントローラ要素のタイプを指定
            if (GuiEnvironment.TSC_TYPE_OHTC.equals(reqEntity.memberGroup))
            {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_OHTC);
            }
            else if(GuiEnvironment.TSC_TYPE_LIFTERC.equals(reqEntity.memberGroup))
            {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_LIFTERC);
            }
            else if(GuiEnvironment.TSC_TYPE_XLFT.equals(reqEntity.memberGroup)) 
            {
            	tscTypeList.add(GuiEnvironment.TSC_TYPE_XLFT);//20200103 DQY ADD
            }
            		
            model.addAttribute("memberGroupName", reqEntity.memberGroup);	//20200106 DQY ADD

            /*// 操作ログの情報設定（TSCモニタ）（アノテーション記載情報を転記）
            opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 7L);*/

        } else {
            // 上記以外はエラー
            String mes = messageSource.getMessage("ERR0044", new String[] { reqEntity.memberGroup }, "ERR0044", locale);
            throw new McsException(mes);
        }

        // ----------------------------------------------
        // AMHS_ID用セレクトボックスを生成
        // ----------------------------------------------
//        List<String[]> amhsBoxList = selBoxService.getControllerByType(amhsTypeList);//20191218 dqy del
        List<String[]> tscBoxList = selBoxService.getControllerByType(tscTypeList);
//        model.addAttribute("AmhsNameList", super.objectToJson(amhsBoxList));//20191218 dqy del
        model.addAttribute("displayNameList", super.objectToJson(tscBoxList));

        // ----------------------------------------------
        // 画面表示用で選択されたAMHS_IDを格納
        // ----------------------------------------------
//        model.addAttribute("amhsId", reqEntity.amhsId);
//        model.addAttribute("displayName", reqEntity.displayName);//20191220 DQY DEL
        model.addAttribute("displayId", reqEntity.displayId);	   //20191220 DQY ADD

        // 正常時に操作ログ出力
        opeLogService.getOpeLog(opeLogInfo.logCode, null, opeLogInfo.userName, opeLogInfo.ipAddress);

        // バージョン情報付与
        ComFunction.setVersion(model);

        return destJspFile;
    }

}
