//@formatter:off
/**
 ******************************************************************************
 * @file        OperationLogAjaxController.java
 * @brief       操作ログ表示関連のajaxコントローラ
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
package net.muratec.mcs.controller.log;

import java.sql.Timestamp;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.log.ReqGetOperationLogListEntity;
import net.muratec.mcs.entity.log.ReqGetOperationLogListValidateEntity;
import net.muratec.mcs.entity.log.ResGetOperationLogListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.log.OperationLogService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     操作ログ表示関連のajaxコントローラクラス
 * @par       機能:
 *              getOperationLogList（操作ログ一覧の取得）
 *              setCsvOperationLogList（検索条件の保存）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class OperationLogAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogAjaxController.class);

    // ------------------------------------
    // 操作ログ表示画面用サービス
    // ------------------------------------
    @Autowired private OperationLogService operationlogService;

    // ------------------------------------
    // グリッド用サービス
    // ------------------------------------
    @Autowired private McsDataTablesService mcsDataTablesService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOperationLog（操作ログ表示一覧の取得）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    操作ログ表示一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      操作ログ表示一覧の検索処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/OperationLog/GetOperationLog", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_OPERATIONLOG, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetOperationLogListEntity getOperationLog(HttpSession session,
            @Valid @RequestBody ReqGetOperationLogListValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_OPERATIONLOG.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetOperationLogListEntity resEntity = mcsDataTablesService
                .createResEntity(ResGetOperationLogListEntity.class, reqValidate, sessionUserInfo.userName, locale);

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqValidate.searchDataFlag) {

            // ------------------------------------
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
            // ------------------------------------
            ReqGetOperationLogListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetOperationLogListEntity.class);

            // ------------------------------------
            // 日付の大小関係を確認（修正）
            // ------------------------------------
            if (!ComFunction.checkFromTo(reqEntity.from, reqEntity.to)) {
                // 大小関係が入れ替わっている場合

                // 現在の値を格納
                Timestamp beforeFrom = reqEntity.from;
                Timestamp beforeTo = reqEntity.to;

                // FromとToを入れ替え
                reqEntity.from = beforeTo;
                reqEntity.to = beforeFrom;
            }

            // あいまい検索用に特殊文字をエスケープ
            operationlogService.escapeSearchCond(reqEntity);

            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = operationlogService.getOperationLogList(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = operationlogService.getCount(reqEntity);

            resEntity.caption = operationlogService.createCaption(resEntity.body);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     setCsvOperationLog（CSV保存）機能
     * @param     reqValidate    画面より入力された情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      CSV出力のための検索条件を保存する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/OperationLog/SetCsvOperationLog", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.LOG_OPERATIONLOG, logOperationType = ComConst.LogOperationType.CSV_SET,
            number = 3L)
    public AjaxResBaseEntity setCsvOperationLog(HttpSession session,
            @Valid @RequestBody ReqGetOperationLogListValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.LOG_OPERATIONLOG.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqGetOperationLogListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetOperationLogListEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew

        // ------------------------------------
        // 日付の大小関係を確認（修正）
        // ------------------------------------
        if (!ComFunction.checkFromTo(reqEntity.from, reqEntity.to)) {
            // 大小関係が入れ替わっている場合

            // 現在の値を格納
            Timestamp beforeFrom = reqEntity.from;
            Timestamp beforeTo = reqEntity.to;

            // FromとToを入れ替え
            reqEntity.from = beforeTo;
            reqEntity.to = beforeFrom;
        }

        // ------------------------------------
        // 戻り値宣言
        // ------------------------------------
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.LOG_OPERATIONLOG.getFunctionId() + ComConst.SessionKey.CSV_INFO;
        super.setSessionAttribute(session, sessionKey, reqEntity);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}
