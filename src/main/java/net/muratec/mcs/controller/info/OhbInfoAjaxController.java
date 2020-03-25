//@formatter:off
/**
 ******************************************************************************
 * @file        OhbAjaxController.java
 * @brief       アラーム情報表示関連のコントローラ
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  2                                           SGP
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
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseAjaxController;
//import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
//import net.muratec.mcs.entity.common.OpeLogInfoEntity;
import net.muratec.mcs.entity.info.ReqGetOhbInfoListEntity;
import net.muratec.mcs.entity.info.ReqGetOhbInfoListValidateEntity;
import net.muratec.mcs.entity.info.ReqGetOhbPortRltEntity;
import net.muratec.mcs.entity.info.ResGetOhbInfoListEntity;
import net.muratec.mcs.entity.info.ResGetOhbPortRltListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.OhbPortRltModel;
import net.muratec.mcs.service.common.McsDataTablesService;
//import net.muratec.mcs.service.common.OpeLogService;
import net.muratec.mcs.service.info.OhbInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     アラーム情報表示関連のコントローラクラス
 * @par       機能:
 *              getOhbInfoList(アラーム情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class OhbInfoAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(OhbInfoAjaxController.class);

    // ------------------------------------
    // アラーム情報画面用サービス
    // ------------------------------------
    @Autowired private OhbInfoService ohbInfoService;

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
     * @brief     getOhbInfoList(アラーム情報表示一覧の取得)機能
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
    @RequestMapping(value = "/OhbInfo/GetOhbInfoList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_OHB, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetOhbInfoListEntity getAlarmList(HttpSession session,
            @Valid @RequestBody ReqGetOhbInfoListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_OHB.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック(エラー時はAjaxAurgumentExceptionをthrow)
        // ------------------------------------
        ReqGetOhbInfoListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetOhbInfoListEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetOhbInfoListEntity resEntity = mcsDataTablesService.createResEntity(ResGetOhbInfoListEntity.class, reqEntity,
                sessionUserInfo.userName, locale);

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqEntity.searchDataFlag) {

            // ------------------------------------
            // データ取得、設定
            // ------------------------------------
            resEntity.body = ohbInfoService.getOhbInfoList(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = ohbInfoService.getCount(reqEntity);
            /*
            List<String> color = new ArrayList<String>();
            color.add("#FF0000");
            color.add("#00FF00");
            resEntity.rowColorList = color;
            */
            
        }

        return resEntity;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOhbPortRltList（ポートリストの取得を行う）
     * @param     session     セッション情報（Frameworkより付加）
     * @param     reqEntity   検索条件
     * @param     errors      エラー情報（Frameworkより付加）
     * @param     locale      ロケーション情報（Frameworkより付加）
     * @param     model       モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      ポートリストの取得を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/OhbInfo/GetOhbPortRltList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetOhbPortRltListEntity getOhbPortRltList(HttpSession session,
            @Valid @RequestBody ReqGetOhbPortRltEntity reqEntity, Errors errors, Locale locale, Model model)
                    throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResGetOhbPortRltListEntity resEntity = new ResGetOhbPortRltListEntity();

        // ------------------------------------
        // ポートリスト取得
        // ------------------------------------
        resEntity.ohbPortRltList = ohbInfoService.getOhbPortRltList(reqEntity.ohbId);
        
        //データに応じたセルの背景色の設定を行います。
        List<String> rowColorList = new ArrayList<String>();
        List<OhbPortRltModel> ohbPortRltList = resEntity.ohbPortRltList;
        for (OhbPortRltModel ohbPortRltModel : ohbPortRltList) {
        	rowColorList.add(getRowColorList(ohbPortRltModel));
		}
        resEntity.rowColor = rowColorList;
        /*
        List<String> rowColor = new ArrayList<String>();
        rowColor.add("#00FF00");
        rowColor.add("#808080");
        rowColor.add("#6E78FF");
        resEntity.rowColor = rowColor;*/

        return resEntity;
    }
    
    /*
	  MODE や COMM_STATE に合わせて色を返す
	   ルールとして、MODEが一番強いが
	  MODEがUPの場合のみ、COMM_STATEが強くなる
	  セル描画用の拡張レンダラクラス。
	   データに応じたセルの背景色の設定を行います。
	*/
	public String getRowColorList( OhbPortRltModel ohbPortRltModel )
	{
		String color;
		
		if( "Up".equals( ohbPortRltModel.portMode ) &&
			"Available".equals( ohbPortRltModel.available ) &&
			"Avail".equals( ohbPortRltModel.ibsemAvail ) )
		{
			color = "#00FF00";
		}
		else if( "Down".equals( ohbPortRltModel.portMode ) )
		{
			color = "#808080";
		}
		else if( "Test".equals( ohbPortRltModel.portMode ) )
		{
			color = "#6E78FF";
		}
		else if( "PM".equals( ohbPortRltModel.portMode ) )
		{
			color = "#FF8C00";
		}
		else if( "Hold".equals( ohbPortRltModel.portMode ) )
		{
			color = "#FA320A";
		}
		else
		{
			color = "#FF0000";
		}

		return color;
	}
	

   
}