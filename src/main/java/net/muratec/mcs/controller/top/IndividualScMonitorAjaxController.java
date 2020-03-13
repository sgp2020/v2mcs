//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualScMonitorAjaxController.java
 * @brief       個別モニタ(SCモニタ)関連のajaxコントローラ
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
import net.muratec.mcs.entity.common.AjaxReqBaseEntity;
import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorValidateEntity;
import net.muratec.mcs.entity.top.ResScMonitorMicroCmdListEntity;
import net.muratec.mcs.entity.top.ResScMonitorPortListEntity;
import net.muratec.mcs.entity.top.ResScMonitorStateInfoEntity;
import net.muratec.mcs.entity.top.ResScMonitorTransferJobListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.top.IndividualScMonitorService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(SCモニタ)関連のajaxコントローラクラス
 * @par       機能:
 *               getScStateInfo（状態画面の情報を取得する機能）
 *               getScPortInfo（ポート画面の情報を取得する機能）
 *               getScMicroCmdInfo（Microコマンド画面の情報を取得する機能）
 *               getScTransferJobInfo（搬送Job画面の情報を取得する機能）
 *               getScStateInfoForeignFile（状態画面の情報を取得する機能[外部ファイル]）
 *               getScPortInfoForeignFile（ポート画面の情報を取得する機能[外部ファイル]）
 *               getScMicroCmdInfoForeignFile（Microコマンド画面の情報を取得する機能[外部ファイル]）
 *               getScTransferJobInfoForeignFile（搬送Job画面の情報を取得する機能[外部ファイル]）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class IndividualScMonitorAjaxController extends BaseAjaxController {

    /** SCモニタサービス */
    @Autowired IndividualScMonitorService scMonitorService;

    /** 操作ログサービス */
    @Autowired private OpeLogService opeLogService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態情報およびアラーム一覧検索機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScStateInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    /*//20200113 DQY DEL
     public ResScMonitorStateInfoEntity getScStateInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {*/
	public ResScMonitorStateInfoEntity getScStateInfo(
			HttpServletRequest  request, //DQY ADD 20200113
			HttpSession session,
			@Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
			Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);
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
        scMonitorService.setScreenMonitorList1(screenMonitorList1);
        scMonitorService.setScreenMonitorMemberList1(screenMonitorMemberList1);
        scMonitorService.setScreenColorMasterList1(screenColorMasterList1);
        //20200113 DQY ADD END
        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorStateInfoEntity resEntity = new ResScMonitorStateInfoEntity();

        // ------------------------------------
        // SCモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getStateInfo(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Port情報検索機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタのポート画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScPortInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorPortListEntity getScPortInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorPortListEntity resEntity = new ResScMonitorPortListEntity();

        // ------------------------------------
        // SCモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getPortInfoList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getPortColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        /*
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }
        */
        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Microコマンド情報を検索する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタのMicroコマンド画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScMicroCmdInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorMicroCmdListEntity getScMicroCmdInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorMicroCmdListEntity resEntity = new ResScMonitorMicroCmdListEntity();

        // ------------------------------------
        // SCモニタ Microコマンド情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getMicroCmdInfoList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getMicroCmdColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送Job情報を検索する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタの搬送Job画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScTransferJobInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorTransferJobListEntity getScTransferJobInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorTransferJobListEntity resEntity = new ResScMonitorTransferJobListEntity();

        // ------------------------------------
        // SCモニタ 搬送Job情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getTransferJobInfoList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getTransferJobColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態情報およびアラーム一覧検索機能（外部ファイル参照）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScStateInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorStateInfoEntity getScStateInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorStateInfoEntity resEntity = new ResScMonitorStateInfoEntity();

        // ------------------------------------
        // SCモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getStateInfoForeignFile(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 2L);

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
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタのポート画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScPortInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorPortListEntity getScPortInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorPortListEntity resEntity = new ResScMonitorPortListEntity();

        // ------------------------------------
        // SCモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getPortInfoListForeignFile(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getPortColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Microコマンド情報を検索する機能（外部ファイル参照）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタのMicroコマンド画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScMicroCmdInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorMicroCmdListEntity getScMicroCmdInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorMicroCmdListEntity resEntity = new ResScMonitorMicroCmdListEntity();

        // ------------------------------------
        // SCモニタ Microコマンド情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getMicroCmdInfoListForeignFile(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getMicroCmdColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     搬送Job情報を検索する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      SCモニタの搬送Job画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetScTransferJobInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResScMonitorTransferJobListEntity getScTransferJobInfoForeignFile(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.TOP_SYSTEMMONITOR.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqIndividualMonitorEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqIndividualMonitorEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResScMonitorTransferJobListEntity resEntity = new ResScMonitorTransferJobListEntity();

        // ------------------------------------
        // SCモニタ 搬送Job情報の取得
        // ------------------------------------
        resEntity.body = scMonitorService.getTransferJobInfoListForeignFile(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = scMonitorService.getTransferJobColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 2L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }
}
