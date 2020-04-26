//@formatter:off
/**
 ******************************************************************************
 * @file        StockerInformationAjaxController.java
 * @brief       StockerInformation画面関連のajaxコントローラ
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 * 2020.03.11 			StockerInformationAjaxController			          董 天津村研
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.hist;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComBeanConv;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;

import net.muratec.mcs.controller.common.BaseAjaxController;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityListValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataValidateEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.entity.hist.ResGetAtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.ResGetMacroDataListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListValidateEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorValidateEntity;
import net.muratec.mcs.entity.top.ResScMonitorPortListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.common.SelectBoxService;
import net.muratec.mcs.service.hist.AtomicActivityHistService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     StockerInformation画面関連のajaxコントローラクラス
 * @par       機能:
 *              getStockerInfoList (StockerInformation一覧の取得)
 *              getStockerInfoSelectBoxList (StockerInformation一覧TSCIDセレクトボックスリストの取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020.03.11 			StockerInformationAjaxController			          董 天津村研
 ******************************************************************************
 */
//@formatter:on
@Controller
public class AtomicActivityHistAjaxController extends BaseAjaxController {

    public static final Logger logger = LoggerFactory.getLogger(AtomicActivityHistAjaxController.class);

    public static Logger getLogger() {

        return logger;
    }

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private AtomicActivityHistService atomicActivityHistService;

    /** グリッド用サービス */
    @Autowired private McsDataTablesService mcsDataTablesService;

    /** セレクトボックス用サービス */
    @Autowired private SelectBoxService selBoxService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getStockerInfoList（StockerInfo一覧検索）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      一覧検索条件
     * @param     errors         エラー情報（Framworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      EMPTY_CARRIERテーブルより、指定された開始～終了行の一覧を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/AtomicActivityHist/GetAtomicActivityHistList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.GET,
            number = 2L)
    public ResGetAtomicActivityHistListEntity getAtomicActivityHistList(HttpSession session,
            @Valid @RequestBody ReqGetAtomicActivityListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getRefAuthFuncId());
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
       
        ReqGetAtomicActivityHistEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetAtomicActivityHistEntity.class);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetAtomicActivityHistListEntity resEntity = mcsDataTablesService.createResEntity(ResGetAtomicActivityHistListEntity.class,
        		reqEntity, sessionUserInfo.userName, locale);

        // 検索処理実装判定
        if (reqValidate.searchDataFlag) {
            // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
           /* ReqGetStockerInfoListValidateEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                    ReqGetStockerInfoListValidateEntity.class);*/
        	// 日付の大小関係を確認（修正）
            if (!ComFunction.checkFromTo(reqEntity.dateFrom, reqEntity.dateTo)) {
                // 大小関係が入れ替わっている場合
                // 現在の値を格納
            	Timestamp sbeforeFrom = reqEntity.dateFrom;
            	Timestamp sbeforeTo = reqEntity.dateTo;
            	
                // FromとToを入れ替え
                reqEntity.dateFrom = sbeforeTo;
                reqEntity.dateTo = sbeforeFrom;
             
            }
            
            // データ取得、設定
            resEntity.body = atomicActivityHistService.getAtomicActivityHistList(reqEntity);
            
            // 全体レコード数取得、設定
            resEntity.pageInfo.totalRecords = atomicActivityHistService.getAtomicActivityHistCount(reqEntity);
            
            /*//異常Rowを色へ変更する
            List<String> color = new ArrayList<String>();
            int rowSize = resEntity.body.size();
            for(int i = 0;i<rowSize; i++) {
            	String commState = resEntity.body.get(i).commState; 
            	if(commState!=null && !State.HOST_STATE_COMMUNICATING.equals(commState) ) 
        		{
            		// Selected/Communicating以外は異常とする.
            		color.add("#FF0000");
        		}
            	resEntity.rowColorList = color;
            }*/

        }
        return resEntity;
    }

  //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvAtomicActivityHistList（CSV保存）機能
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
    @RequestMapping(value = "/AtomicActivityHist/SetCsvAtomicActivityHistList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity SetCsvAtomicActivityHistList(@Valid @RequestBody ReqGetAtomicActivityListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetAtomicActivityHistEntity reqEntity = bc.convert(reqStrEntity, ReqGetAtomicActivityHistEntity.class);

        // 日付の大小関係を確認（修正）
        if (!ComFunction.checkFromTo(reqEntity.dateFrom, reqEntity.dateTo)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp sbeforeFrom = reqEntity.dateFrom;
            Timestamp sbeforeTo = reqEntity.dateTo;
            // FromとToを入れ替え
            reqEntity.dateFrom = sbeforeTo;
            reqEntity.dateTo = sbeforeFrom;
        }

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     macroData情報
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      検索条件
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      macroData画面に表示する各データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    /*@RequestMapping(value = "/AtomicActivityHist/GetMacroData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetMacroDataListEntity getMacroData(HttpSession session,
            @Valid @RequestBody ReqGetMacroDataValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.HIST_ATOMICACTIVITYHISTORY.getRefAuthFuncId());

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqGetMacroDataEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
        		ReqGetMacroDataEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        // ------------------------------------
        ResGetMacroDataListEntity resEntity = new ResGetMacroDataListEntity();

        // ------------------------------------
        // SCモニタ ポート情報の取得
        // ------------------------------------
        resEntity.body = atomicActivityHistService.getMacroDataList(reqEntity);

        // ------------------------------------
        // 色情報取得、設定
        // ------------------------------------
//        resEntity.rowColorList = scMonitorService.getPortColorInfoList(resEntity.body);

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
    }*/

}
