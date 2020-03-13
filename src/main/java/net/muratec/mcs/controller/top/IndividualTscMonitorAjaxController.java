//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualTscMonitorAjaxController.java
 * @brief       個別モニタ(TSCモニタ)関連のajaxコントローラ
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
import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorValidateEntity;
import net.muratec.mcs.entity.top.ResTscMonitorMicroCmdListEntity;
import net.muratec.mcs.entity.top.ResTscMonitorModuleListEntity;
import net.muratec.mcs.entity.top.ResTscMonitorStateInfoEntity;
import net.muratec.mcs.entity.top.ResTscMonitorVehicleListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.top.IndividualTscMonitorService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(TSCモニタ)関連のajaxコントローラクラス
 * @par       機能:
 *               getTscStateInfo（状態画面の情報を取得する機能）
 *               getTscVehicleInfo（台車画面の情報を取得する機能）
 *               getTscMicroCmdInfo（Microコマンド画面の情報を取得する機能）
 *               getTscModuleInfo（モジュール画面の情報を取得する機能）
 *               getTscStateInfoForeignFile（【外部ファイル参照】状態画面の情報を取得する機能）
 *               getTscVehicleInfoForeignFile（【外部ファイル参照】台車画面の情報を取得する機能）
 *               getTscMicroCmdInfoForeignFile（【外部ファイル参照】Microコマンド画面の情報を取得する機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class IndividualTscMonitorAjaxController extends BaseAjaxController {

    /** 操作ログサービス */
    @Autowired private OpeLogService opeLogService;

    /** TSCモニタ用サービス */
    @Autowired private IndividualTscMonitorService tscMonitorService;

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
     * @note      TSCモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscStateInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    /*//20200113 DQY DEL
     public ResTscMonitorStateInfoEntity getTscStateInfo(HttpSession session,
            @Valid @RequestBody ReqIndividualMonitorValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {*/
    	public ResTscMonitorStateInfoEntity getTscStateInfo(
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
        tscMonitorService.setScreenMonitorList1(screenMonitorList1);
        tscMonitorService.setScreenMonitorMemberList1(screenMonitorMemberList1);
        tscMonitorService.setScreenColorMasterList1(screenColorMasterList1);
        //20200113 DQY ADD END
        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResTscMonitorStateInfoEntity resEntity = new ResTscMonitorStateInfoEntity();

        // ------------------------------------
        // TSCモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getStateInfo(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
       /* if (reqEntity.ctrlChgFlag) {//20191220 DQY DEL OPERATIONLOG
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 8L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }*/

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     台車情報検索機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate   検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      TSCモニタの台車画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscVehicleInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorVehicleListEntity getTscVehicleInfo(HttpSession session,
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
        ResTscMonitorVehicleListEntity resEntity = new ResTscMonitorVehicleListEntity();

        // ------------------------------------
        // TSCモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getVehicleInfoList(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        /*if (reqEntity.ctrlChgFlag) {//20191220 DQY DEL FOR OPERATION_LOG
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 8L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }*/

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
     * @note      TSCモニタのMicroコマンド画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscMicroCmdInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorMicroCmdListEntity getTscMicroCmdInfo(HttpSession session,
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
        ResTscMonitorMicroCmdListEntity resEntity = new ResTscMonitorMicroCmdListEntity();

        // ------------------------------------
        // TSCモニタ Microコマンド情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getMicroCmdInfoList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = tscMonitorService.getMicroCmdColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 8L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     モジュール情報を検索する機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      TSCモニタのモジュール画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscModuleInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorModuleListEntity getTscModuleInfo(HttpSession session,
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
        ResTscMonitorModuleListEntity resEntity = new ResTscMonitorModuleListEntity();

        // ------------------------------------
        // TSCモニタ モジュール情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getModuleInfo(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = tscMonitorService.getModuleColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.GET, 8L);

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
     * @note      TSCモニタの状態画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscStateInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorStateInfoEntity getTscStateInfoForeignFile(HttpSession session,
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
        ResTscMonitorStateInfoEntity resEntity = new ResTscMonitorStateInfoEntity();

        // ------------------------------------
        // TSCモニタ 状態情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getStateInfoForeignFile(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 8L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     台車情報検索機能（外部ファイル参照）
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate   検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      TSCモニタの台車画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscVehicleInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorVehicleListEntity getTscVehicleInfoForeignFile(HttpSession session,
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
        ResTscMonitorVehicleListEntity resEntity = new ResTscMonitorVehicleListEntity();

        // ------------------------------------
        // TSCモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getVehicleInfoListForeignFile(reqEntity);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 8L);

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
     * @note      TSCモニタのMicroコマンド画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Individual/GetTscMicroCmdInfoForeignFile", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResTscMonitorMicroCmdListEntity getTscMicroCmdInfoForeignFile(HttpSession session,
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
        ResTscMonitorMicroCmdListEntity resEntity = new ResTscMonitorMicroCmdListEntity();

        // ------------------------------------
        // TSCモニタ Microコマンド情報の取得
        // ------------------------------------
        resEntity.body = tscMonitorService.getMicroCmdInfoListForeignFile(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
        resEntity.rowColorList = tscMonitorService.getMicroCmdColorInfoList(resEntity.body);

        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        // コントローラ変更時のみ操作ログを出力
        if (reqEntity.ctrlChgFlag) {
            // ------------------------------------
            // // 操作ログの情報設定（アノテーション記載情報を転記）
            // ------------------------------------
            OpeLogInfoEntity opeLogInfo = ComFunction.createOpeLogInfo(session, ComConst.ScreenInfo.TOP_SYSTEMMONITOR,
                    ComConst.LogOperationType.EXT_FILE, 8L);

            // 正常時に操作ログ出力
            opeLogService.getOpeLog(opeLogInfo.logCode, ComFunction.toStringMcs(reqEntity), opeLogInfo.userName,
                    opeLogInfo.ipAddress);
        }

        return resEntity;
    }
}
