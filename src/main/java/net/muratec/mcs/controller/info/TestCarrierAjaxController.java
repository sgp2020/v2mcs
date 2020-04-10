//@formatter:off
/**
 ******************************************************************************
 * @file        TestCarrierAjaxController.java
 * @brief       テストキャリア情報表示関連のコントローラ
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

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.sql.Timestamp;

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
//import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
//import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.info.ReqGetTestCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetTestCarrierListValidateEntity;
import net.muratec.mcs.entity.info.ResGetTestCarrierListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;
//import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.info.TestCarrierService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     テストキャリア情報表示関連のコントローラクラス
 * @par       機能:
 *              getTestCarrier(テストキャリア情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class TestCarrierAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(TestCarrierAjaxController.class);

    // ------------------------------------
    // テストキャリア情報画面用サービス
    // ------------------------------------
    @Autowired private TestCarrierService testCarrierService;

    // ------------------------------------
    // グリッド用サービス
    // ------------------------------------
    @Autowired private McsDataTablesService mcsDataTablesService;

    // ------------------------------------
    //  操作ログサービス - MACS4#0047 Add
    // ------------------------------------
    //@Autowired private OpeLogService opeLogService;

    // ------------------------------------
    // メッセージリソース - MACS4#0047 Add
    // ------------------------------------
    // @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTestCarrierList(テストキャリア情報表示一覧の取得)機能
     * @param     session        セッション情報(Frameworkより付加)
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報(Frameworkより付加)
     * @param     locale         ロケーション情報(Frameworkより付加)
     * @param     model          モデル情報(Frameworkより付加)
     * @return    テストキャリア情報表示一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      テストキャリア情報表示一覧の検索処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/TestCarrierList/GetTestCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetTestCarrierListEntity GetTestCarrierList(HttpSession session,
            @Valid @RequestBody ReqGetTestCarrierListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック(エラー時はAjaxAurgumentExceptionをthrow)
        // ------------------------------------
        ReqGetTestCarrierListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetTestCarrierListEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetTestCarrierListEntity resEntity = mcsDataTablesService.createResEntity(ResGetTestCarrierListEntity.class, reqEntity,
                sessionUserInfo.userName, locale);

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqEntity.searchDataFlag) {

            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = testCarrierService.getTestCarrierList(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = testCarrierService.getCount(reqEntity);
            
     
            List<String> color = new ArrayList<String>();

            SimpleDateFormat f = new SimpleDateFormat("YYYYMMDDHHMMSS00");
            Date now = new Date();
            for(int i = 0; i < resEntity.body.size(); i++) {
            	
            	String startTime = resEntity.body.get(i).testStartTime; 
            	String endTime = resEntity.body.get(i).testEndTime;

            	if( startTime != null && startTime != "" &&  (startTime.compareTo(f.format(new Date())) > 0))
        		{
            		// 以前の場合は背景を黄緑にする
            		color.add("#80FF00");
        		}
            	else if( endTime != null && endTime != "" && (endTime.compareTo(f.format(new Date())) <= 0))
        		{
            		// 以降の場合は背景を黄土色にする
            		color.add("#808000");
        		}
            	else
            	{
            		color.add("");           		
            	}
            	resEntity.rowColorList = color;
      	
            	
            }
        }

        return resEntity;
    }

  //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvTestCarrierList（CSV保存）機能
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
    @RequestMapping(value = "/TestCarrierList/SetCsvTestCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvTestCarrierList(@Valid @RequestBody ReqGetTestCarrierListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetTestCarrierListEntity reqEntity = bc.convert(reqStrEntity, ReqGetTestCarrierListEntity.class);

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.INFO_TEST_CARRIER_LIST.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
}