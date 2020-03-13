//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualOhbMonitorAjaxController.java
 * @brief       個別モニタ(OHBモニタ)関連のajaxコントローラ
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
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.top;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.common.ResSelectBoxEntity;
import net.muratec.mcs.entity.top.ReqGetOhbIdListEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorOhbEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorOhbValidateEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorValidateEntity;
import net.muratec.mcs.entity.top.ResOhbMonitorPortListEntity;
import net.muratec.mcs.entity.top.ResOhbMonitorStateInfoEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.top.IndividualOhbMonitorService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(OHBモニタ)関連のajaxコントローラクラス
 * @par       機能:
 *               getOhbStateInfo（状態画面の情報を取得する機能）
 *               getOhbPortInfo（ポート画面の情報を取得する機能）
 *               getOhbStateInfoForeignFile（外部ファイル：状態画面の情報を取得する機能）
 *               getOhbPortInfoForeignFile（外部ファイル：ポート画面の情報を取得する機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class IndividualOhbMonitorAjaxController extends BaseAjaxController {

    /** 操作ログサービス */
    @Autowired private OpeLogService opeLogService;

    /** OHBモニタサービス */
    @Autowired private IndividualOhbMonitorService ohbMonitorService;

    /** セレクトボックスサービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態情報検索機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      OHBモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetOhbStateInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    /*//20200113 DQY DEL
     public ResOhbMonitorStateInfoEntity getOhbStateInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorOhbValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {*/
    	public ResOhbMonitorStateInfoEntity getOhbStateInfo(
    			HttpServletRequest  request, //DQY ADD 20200113
    			HttpSession session,
    			@Valid @RequestBody ReqIndividualMonitorOhbValidateEntity reqValidate, Errors errors, Locale locale,
    			Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorOhbEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorOhbEntity.class);
        //20200113 DQY ADD START FOR DB→CSV
        // ファイルからデータを取得
        String contextPath = session.getServletContext().getContextPath();//get project name
        String url="http://" + request.getServerName() //Server Ip  
                   + ":"   
                   + request.getServerPort()           //port Number
                   + contextPath
                   + "/resources/";
                   //+ request.getRequestURI();
        
        ServletContext servletContext = session.getServletContext();
        List<ScreenMonitor> screenMonitorList1 = (ArrayList<ScreenMonitor>)servletContext.getAttribute("screenMonitorList1");
        List<ScreenMonitorMember> screenMonitorMemberList1 = (ArrayList<ScreenMonitorMember>)servletContext.getAttribute("screenMonitorMemberList1");
        List<ScreenColorMaster> screenColorMasterList1 = (ArrayList<ScreenColorMaster>)servletContext.getAttribute("screenColorMasterList1");
        ohbMonitorService.setScreenMonitorList1(screenMonitorList1);
        ohbMonitorService.setScreenMonitorMemberList1(screenMonitorMemberList1);
        ohbMonitorService.setScreenColorMasterList1(screenColorMasterList1);
        //20200113 DQY ADD END
        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResOhbMonitorStateInfoEntity resEntity = new ResOhbMonitorStateInfoEntity();

        // ------------------------------------
        // OHBモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = ohbMonitorService.getStateInfo(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // OHB ID変更時のみ操作ログを出力
        /*//20191225 DQY DEL FOR OPERATORLOG
         if (reqEntity.ohbChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 6L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }*/

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Port情報検索機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate   検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      OHBモニタのポート画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetOhbPortInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResOhbMonitorPortListEntity getOhbPortInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorOhbValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorOhbEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorOhbEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResOhbMonitorPortListEntity resEntity = new ResOhbMonitorPortListEntity();

        // ------------------------------------
        // OHBモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = ohbMonitorService.getPortInfoList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = ohbMonitorService.getPortColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // OHB ID変更時のみ操作ログを出力
        if (reqEntity.ohbChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 6L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態情報検索機能（外部ファイル参照）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      OHBモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetOhbStateInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResOhbMonitorStateInfoEntity getOhbStateInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorOhbEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorOhbEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResOhbMonitorStateInfoEntity resEntity = new ResOhbMonitorStateInfoEntity();

        // ------------------------------------
        // OHBモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = ohbMonitorService.getStateInfoForeignFile(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // OHB ID変更時のみ操作ログを出力
        if (reqEntity.ohbChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 6L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Port情報検索機能（外部ファイル参照）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate   検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      OHBモニタのポート画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetOhbPortInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResOhbMonitorPortListEntity getOhbPortInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorOhbValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorOhbEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorOhbEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResOhbMonitorPortListEntity resEntity = new ResOhbMonitorPortListEntity();

        // ------------------------------------
        // OHBモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = ohbMonitorService.getPortInfoListForeignFile(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = ohbMonitorService.getPortColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // OHB ID変更時のみ操作ログを出力
        if (reqEntity.ohbChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 6L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     OHB IDリストの取得機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      OHB IDのリストをセレクトボックス要素形式で返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetOhbIdList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResSelectBoxEntity getOhbIdList(HttpSession session, @RequestBody ReqGetOhbIdListEntity reqEntity,
            Errors errors, Locale locale, Model model) throws AjaxAurgumentException, McsException {

        // 返却データを生成
        ResSelectBoxEntity resEntity = new ResSelectBoxEntity();
        // セレクトボックス要素を取得
//        resEntity.body = selBoxService.getOhbIdByAmhsId(reqEntity.amhsId);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
