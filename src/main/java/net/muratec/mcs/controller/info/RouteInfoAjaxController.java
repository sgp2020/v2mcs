//@formatter:off
/**
 ******************************************************************************
 * @file        RouteInfoAjaxController.java
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
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                           SGP
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

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
//import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.info.ReqGetRouteInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetRouteInfoListValidateEntity;
import net.muratec.mcs.entity.info.ResGetRouteInfoListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.info.RouteInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示関連のコントローラクラス
 * @par       機能:
 *              getRouteInfoList(アラーム情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class RouteInfoAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(RouteInfoAjaxController.class);

    // ------------------------------------
    // アラーム情報画面用サービス
    // ------------------------------------
    @Autowired private RouteInfoService routeInfoService;

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
    // @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getRouteInfoList(アラーム情報表示一覧の取得)機能
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
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/RouteInfo/GetRouteInfoList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_ROUTE, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetRouteInfoListEntity getRouteInfoList(HttpSession session,
            @Valid @RequestBody ReqGetRouteInfoListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_ROUTE.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック(エラー時はAjaxAurgumentExceptionをthrow)
        // ------------------------------------
        ReqGetRouteInfoListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetRouteInfoListEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetRouteInfoListEntity resEntity = mcsDataTablesService.createResEntity(ResGetRouteInfoListEntity.class, reqEntity,
                sessionUserInfo.userName, locale);

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqEntity.searchDataFlag) {

           
            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = routeInfoService.getCount(reqEntity);
            
            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = routeInfoService.getRouteInfoList(reqEntity);

            
            // ------------------------------------
            // 画面表示情報の取得
            // ------------------------------------
            ResGetRouteInfoListEntity resGetRouteListEntity = routeInfoService.getDispRowList(resEntity.body);

            // ------------------------------------
            // 全体レコード、色情報設定
            // ------------------------------------
            //resEntity.body = resGetRouteListEntity.body;
            resEntity.rowColorList = resGetRouteListEntity.rowColorList;
            /*
            List<String> color = new ArrayList<String>();
            color.add("#FF0000");
            color.add("#00FF00");
            resEntity.rowColorList = color;
            */
            
        }

        return resEntity;
    }
}