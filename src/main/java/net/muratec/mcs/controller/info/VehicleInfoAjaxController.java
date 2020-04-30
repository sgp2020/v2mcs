//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleInfoAjaxController.java
 * @brief       ビックル情報表示関連のコントローラ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  2                                          天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.util.ArrayList;
import java.util.List;
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
import net.muratec.mcs.common.ComBeanConv;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.info.ReqGetVehicleInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetVehicleInfoListValidateEntity;
import net.muratec.mcs.entity.info.ResGetVehicleInfoListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.info.VehicleInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ビックル情報表示関連のコントローラクラス
 * @par       機能:
 *              getVehicleInfo(ビックル情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class VehicleInfoAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleInfoAjaxController.class);

    // ------------------------------------
    // ビックル情報画面用サービス
    // ------------------------------------
    @Autowired private VehicleInfoService vehicleInfoService;

    // ------------------------------------
    // グリッド用サービス
    // ------------------------------------
    @Autowired private McsDataTablesService mcsDataTablesService;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GetVehicleInfo(ビックル情報表示一覧の取得)機能
     * @param     session        セッション情報(Frameworkより付加)
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報(Frameworkより付加)
     * @param     locale         ロケーション情報(Frameworkより付加)
     * @param     model          モデル情報(Frameworkより付加)
     * @return    テストキャリア情報表示一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      ビックル情報表示一覧の検索処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/VehicleInfo/GetVehicleInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_VEHICLE, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetVehicleInfoListEntity getVehicleInfoList(HttpSession session,
            @Valid @RequestBody ReqGetVehicleInfoListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_VEHICLE.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック(エラー時はAjaxAurgumentExceptionをthrow)
        // ------------------------------------
        ReqGetVehicleInfoListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetVehicleInfoListEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetVehicleInfoListEntity resEntity = mcsDataTablesService.createResEntity(ResGetVehicleInfoListEntity.class, reqEntity,
                sessionUserInfo.userName, locale);

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqEntity.searchDataFlag) {

            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = vehicleInfoService.getVehicleInfo(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = vehicleInfoService.getCount(reqEntity);
            
            resEntity.rowColorList = vehicleInfoService.getVehicleColor(reqEntity);
        
        }

        return resEntity;
    }

   
  //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvVehicleInfoList（CSV保存）機能
     * @param     reqValidate    画面より入力された情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    成功、または失敗
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリアのCSV出力を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 20200331		DownLoad												DONG
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/VehicleInfo/SetVehicleInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_VEHICLE, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvVehicleInfoList(@Valid @RequestBody ReqGetVehicleInfoListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_VEHICLE.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetVehicleInfoListEntity reqEntity = bc.convert(reqStrEntity, ReqGetVehicleInfoListEntity.class);

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.INFO_VEHICLE.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}