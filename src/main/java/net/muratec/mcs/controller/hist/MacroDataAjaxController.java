//@formatter:off
/**
 ******************************************************************************
 * @file        PortsAjaxController.java
 * @brief       アラーム情報表示関連のコントローラ
 * @par
 * @author      董 天津村研
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/04/10 v1.0.0  	       初版作成                                       								董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataValidateEntity;
import net.muratec.mcs.entity.hist.ResGetMacroDataListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.hist.MacroDataService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示関連のコントローラクラス
 * @par       機能:
 *              getPortsList(アラーム情報一覧の取得)
 *              setCsvPortsList(検索条件の保存を行う)
 *              exeDeletePortsList(アラームの削除を行う)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class MacroDataAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(MacroDataAjaxController.class);

    // ------------------------------------
    // アラーム情報画面用サービス
    // ------------------------------------
    @Autowired private MacroDataService macroDataService;

    // ------------------------------------
    // グリッド用サービス
    // ------------------------------------
    @Autowired private McsDataTablesService mcsDataTablesService;

    // ------------------------------------
    // 操作ログサービス - MACS4#0047 Add
    // ------------------------------------
    //@Autowired private OpeLogService opeLogService;

    // ------------------------------------
    // メッセージリソース - MACS4#0047 Add
    // ------------------------------------
    //@Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     macroData情報
     * @param     session        セッション情報(Frameworkより付加)
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報(Frameworkより付加)
     * @param     locale         ロケーション情報(Frameworkより付加)
     * @param     model          モデル情報(Frameworkより付加)
     * @return    アラーム情報表示一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      アラーム情報表示一覧の検索処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200402		選択したデータの対応のmacroData情報								DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AtomicActivityHist/GetMacroData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    //@OpLog(screenInfo = ComConst.ScreenInfo.INFO_ALARM, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetMacroDataListEntity getMacroDataList(HttpSession session,
            @Valid @RequestBody ReqGetMacroDataValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック(エラー時はAjaxAurgumentExceptionをthrow)
        // ------------------------------------
        ReqGetMacroDataEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetMacroDataEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
       /* ResGetMacroDataListEntity resEntity = mcsDataTablesService.createResEntity(ResGetMacroDataListEntity.class, reqEntity,
                sessionUserInfo.userName, locale);*/
        ResGetMacroDataListEntity resEntity = new ResGetMacroDataListEntity();

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
//        if (reqEntity.searchDataFlag) {

            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = macroDataService.getMacroDataList(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
//            resEntity.pageInfo.totalRecords = macroDataService.getCount(reqEntity);
//        }

        return resEntity;
    }

}