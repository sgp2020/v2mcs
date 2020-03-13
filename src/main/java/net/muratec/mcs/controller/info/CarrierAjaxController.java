//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierAjaxController.java
 * @brief       キャリア情報表示関連のコントローラ
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
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import net.muratec.mcs.entity.info.CarrierDispListEntity;
import net.muratec.mcs.entity.info.GroupListEntity;
import net.muratec.mcs.entity.info.GroupPortListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierDelTaskEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddBinEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddBinValidateEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddOhbEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddTransferEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddTransferValidateEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddOhbValidateEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListValidateEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierPortListEntity;
import net.muratec.mcs.entity.info.ReqGetGroupListEntit;
import net.muratec.mcs.entity.info.ResGetCarrierExeAddEntity;
import net.muratec.mcs.entity.info.ResGetCarrierExeAddTransferEntity;
import net.muratec.mcs.entity.info.ResGetCarrierExeDelEntity;
import net.muratec.mcs.entity.info.ResGetCarrierListEntity;
import net.muratec.mcs.entity.info.ResGetCarrierPortListEntity;
import net.muratec.mcs.entity.info.ResGetGroupListEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.DeleteCarrier;
import net.muratec.mcs.service.common.McsDataTablesService;
import net.muratec.mcs.service.info.CarrierService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報表示関連のコントローラクラス
 * @par       機能:
 *              getCarrierList（キャリア情報表示一覧の取得）
 *              getCarrier（キャリア情報表示一覧以外の取得）
 *              exeDeleteCarrierList（キャリア情報表示の削除）
 *              exeForceDelCarrierList（キャリア情報表示の強制削除）
 *              exeAddTransferCarrier（搬送ジョブ作成）
 *              setCsvCarrierList（CSV出力）
 *              exeAddCarrier（キャリア追加）
 *              modCarrier（キャリア修正）
 *              getPortList(ポートリスト取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class CarrierAjaxController extends BaseAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(CarrierAjaxController.class);

    // ------------------------------------
    // メッセージリソース
    // ------------------------------------
    @Autowired private MessageSource messageSource;

    // ------------------------------------
    // キャリア情報画面用サービス
    // ------------------------------------
    @Autowired private CarrierService carrierService;

    // ------------------------------------
    // グリッド用サービス
    // ------------------------------------
    @Autowired private McsDataTablesService mcsDataTablesService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCarrierList（キャリア情報表示一覧の取得）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    キャリア情報表示一覧検索結果
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリア情報表示一覧の検索処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/GetCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.GET, number = 3L)
    public ResGetCarrierListEntity getCarrierList(HttpSession session,
            @Valid @RequestBody ReqGetCarrierListValidateEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getRefAuthFuncId());

        // ------------------------------------
        // ユーザ情報の取得
        // ------------------------------------
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // ------------------------------------
        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        // ------------------------------------
        ReqGetCarrierListEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate,
                ReqGetCarrierListEntity.class);

        // ------------------------------------
        // レスポンスエンティティ生成
        // ------------------------------------
        ResGetCarrierListEntity resEntity = mcsDataTablesService.createResEntity(ResGetCarrierListEntity.class,
                reqEntity, sessionUserInfo.userName, locale);

        // ------------------------------------
        // 日付の大小関係を確認（修正）
        // ------------------------------------
        if (!ComFunction.checkFromTo(reqEntity.sfrom, reqEntity.sto)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp sbeforeFrom = reqEntity.sfrom;
            Timestamp sbeforeTo = reqEntity.sto;
            // FromとToを入れ替え
            reqEntity.sfrom = sbeforeTo;
            reqEntity.sto = sbeforeFrom;
        }
        if (!ComFunction.checkFromTo(reqEntity.ifrom, reqEntity.ito)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp ibeforeFrom = reqEntity.ifrom;
            Timestamp ibeforeTo = reqEntity.ito;
            // FromとToを入れ替え
            reqEntity.ifrom = ibeforeTo;
            reqEntity.ito = ibeforeFrom;
        }

        // ------------------------------------
        // 検索処理実装判定
        // ------------------------------------
        if (reqEntity.searchDataFlag) {
            // ------------------------------------
            // データ取得
            // ------------------------------------
            List<CarrierDispListEntity> rowList = carrierService.getCarrierDispList(reqEntity);

            // ------------------------------------
            // 全体レコード数取得、設定
            // ------------------------------------
            resEntity.pageInfo.totalRecords = carrierService.getCount(reqEntity);

            // ------------------------------------
            // 画面表示情報の取得
            // ------------------------------------
            ResGetCarrierListEntity ResGetCarrierListEntity = carrierService.getDispRowList(rowList);

            // ------------------------------------
            // 全体レコード、色情報設定
            // ------------------------------------
            resEntity.body = ResGetCarrierListEntity.body;
            resEntity.rowColorList = ResGetCarrierListEntity.rowColorList;
        }

        return resEntity;
    }

    
    //
    @RequestMapping(value = "/Carrier/BeforExeDeleteCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.EXECUTE, number = 7L)
    public ResGetCarrierExeDelEntity beforExeDeleteCarrierList(HttpSession session,
            @RequestBody ReqGetCarrierDelTaskEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());
        
        boolean rtnB = carrierService.beforExeDeleteCarrier(reqValidate);

        // レスポンスエンティティ生成
        ResGetCarrierExeDelEntity resEntity = new ResGetCarrierExeDelEntity();
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        if(rtnB) {
            resEntity.result.message = "WARNING !\nSome selected carriers are moving.\nThis operation delete Non Stored carriers only.\nPlease delete stored carriers later.";
        } else {
            resEntity.result.message = "";
        }
        
        return resEntity;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeDeleteCarrierList（キャリア情報表示の削除）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    成功、または失敗
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリア情報表示の項目削除を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/ExeDeleteCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.EXECUTE, number = 7L)
    public ResGetCarrierExeDelEntity exeDeleteCarrierList(HttpSession session,
            @RequestBody ReqGetCarrierDelTaskEntity reqValidate, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());
        
        AuthenticationEntity sessionUserInfo = getUserInfo(session);        
        String strUserName = sessionUserInfo.userName;       
        
        List<DeleteCarrier> rcvRows = carrierService.exeDeleteCarrier(reqValidate, strUserName);

        // レスポンスエンティティ生成
        ResGetCarrierExeDelEntity resEntity = new ResGetCarrierExeDelEntity();
        if(rcvRows != null) {
            StringBuilder rtn = new StringBuilder();
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
            rtn.append("CarrierId    --   Result\n");
            for (DeleteCarrier dc : rcvRows) {
                rtn.append(dc.getCarrierId());
                rtn.append(" - ");
                rtn.append(messageSource.getMessage(""+dc.getAns(), null, ""+dc.getAns(), LocaleContextHolder.getLocale()));
                rtn.append("\n");
            }
            resEntity.result.message = rtn.toString();
        } else {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            resEntity.result.message = "";
        }
        return resEntity;
        
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeAddTransferCarrier（搬送ジョブ作成）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    成功、または失敗
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリア搬送ジョブの作成を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/ExeAddTransferCarrier", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.EXECUTE, number = 9L)
    public ResGetCarrierExeAddTransferEntity exeAddTransferCarrier(HttpSession session,
            @Valid @RequestBody ReqGetCarrierJobAddTransferValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());
        // ユーザ情報の取得
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
        String strUserName = sessionUserInfo.userName;

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ReqGetCarrierJobAddTransferEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate, ReqGetCarrierJobAddTransferEntity.class);

        // 実行結果設定
        String rtn = carrierService.exeAddTransferCarrier(reqEntity, strUserName);
        
        // レスポンスエンティティ生成
        ResGetCarrierExeAddTransferEntity resEntity = new ResGetCarrierExeAddTransferEntity();
        if("0".equals(rtn))
        {
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
            resEntity.result.message = "";
        } else {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            rtn = ""+ rtn;
            resEntity.result.message = messageSource.getMessage(rtn, null, rtn, LocaleContextHolder.getLocale());
        }
        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SetCsvCarrierList（CSV保存）機能
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
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/SetCsvCarrierList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.CSV_SET,number = 5L)
    public AjaxResBaseEntity setCsvCarrierList(@Valid @RequestBody ReqGetCarrierListValidateEntity reqStrEntity,
            HttpSession session, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getRefAuthFuncId());

        // Entityの型変換
        ComBeanConv bc = new ComBeanConv();
        ReqGetCarrierListEntity reqEntity = bc.convert(reqStrEntity, ReqGetCarrierListEntity.class);

        // 日付の大小関係を確認（修正）
        if (!ComFunction.checkFromTo(reqEntity.sfrom, reqEntity.sto)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp sbeforeFrom = reqEntity.sfrom;
            Timestamp sbeforeTo = reqEntity.sto;
            // FromとToを入れ替え
            reqEntity.sfrom = sbeforeTo;
            reqEntity.sto = sbeforeFrom;
        }
        if (!ComFunction.checkFromTo(reqEntity.ifrom, reqEntity.ito)) {
            // 大小関係が入れ替わっている場合
            // 現在の値を格納
            Timestamp ibeforeFrom = reqEntity.ifrom;
            Timestamp ibeforeTo = reqEntity.ito;
            // FromとToを入れ替え
            reqEntity.ifrom = ibeforeTo;
            reqEntity.ito = ibeforeFrom;
        }

        // 戻り値宣言
        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();

        String sessionKey = ComConst.ScreenInfo.INFO_CARRIER.getFunctionId() + ComConst.SessionKey.CSV_INFO;

        super.setSessionAttribute(session, sessionKey, reqEntity);
        // 実行結果設定
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeAddCarrier（キャリア追加）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqValidate    画面より入力された情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    成功、または失敗
     * @retval    JSON形式で返却
     * @attention
     * @note      キャリアの項目作成を行う。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/ExeAddCarrierBin", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.EXECUTE, number = 10L)
    public ResGetCarrierExeAddEntity exeAddCarrierBin(HttpSession session,
            @Valid @RequestBody ReqGetCarrierJobAddBinValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());
        // ユーザ情報の取得
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
        String strUserName = sessionUserInfo.userName;

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ReqGetCarrierJobAddBinEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate, ReqGetCarrierJobAddBinEntity.class);

        // キャリア追加処理を実行
        String rtn = carrierService.exeAddCarrierBin(reqEntity, strUserName);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetCarrierExeAddEntity resEntity = new ResGetCarrierExeAddEntity();

        if("0".equals(rtn))
        {
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
            resEntity.result.message = "";
        } else {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            rtn = ""+ rtn;
            resEntity.result.message = messageSource.getMessage(rtn, null, rtn, LocaleContextHolder.getLocale());
        }
        return resEntity;
    }
    
    //
    @RequestMapping(value = "/Carrier/ExeAddCarrierOhb", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.EXECUTE, number = 10L)
    public ResGetCarrierExeAddEntity exeAddCarrierOhb(HttpSession session,
            @Valid @RequestBody ReqGetCarrierJobAddOhbValidateEntity reqValidate, Errors errors, Locale locale,
            Model model) throws AjaxAurgumentException, McsException {

        // アクセス権チェック
        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());
        // ユーザ情報の取得
        AuthenticationEntity sessionUserInfo = getUserInfo(session);
        String strUserName = sessionUserInfo.userName;

        // エラーチェック（エラー時はAjaxAurgumentExceptionをthrow）
        ReqGetCarrierJobAddOhbEntity reqEntity = ComFunction.ajaxAurgumentCheck(errors, logger, locale, reqValidate, ReqGetCarrierJobAddOhbEntity.class);

        // キャリア追加処理を実行
        String rtn = carrierService.exeAddCarrierOhb(reqEntity, strUserName);

        // レスポンスエンティティ生成
        // 返すJSON全体のオブジェクトをnew
        ResGetCarrierExeAddEntity resEntity = new ResGetCarrierExeAddEntity();

        if("0".equals(rtn))
        {
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
            resEntity.result.message = "";
        } else {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            rtn = ""+ rtn;
            resEntity.result.message = messageSource.getMessage(rtn, null, rtn, LocaleContextHolder.getLocale());
        }
        return resEntity;
    }
  
    /**
     ******************************************************************************
     * @brief     Group
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */  
    @RequestMapping(value = "/Carrier/GetGroupInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResGetGroupListEntity getGroupInfo(HttpSession session,
            @RequestBody ReqGetGroupListEntit reqEntity, Errors errors, Locale locale, Model model)
            throws AjaxAurgumentException, McsException {

        setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_CARRIER.getRefAuthFuncId());

        GroupListEntity res = carrierService.getGroupAvailable(reqEntity);
        List<GroupPortListEntity> groupList = carrierService.getGroupPort(reqEntity);

        ResGetGroupListEntity resEntity = new ResGetGroupListEntity();
        resEntity.body1 = res;
        resEntity.body2 = groupList;
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ポートリスト取得機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     reqEntity      画面項目情報
     * @param     errors         エラー情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    ポートリスト
     * @retval    JSON形式で返却
     * @attention
     * @note      指定されたOHB_IDをもとにOHB_PORT_RLTテーブルからポートリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/Carrier/GetPortList", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_CARRIER, logOperationType = ComConst.LogOperationType.GET, number = 11L)
    public ResGetCarrierPortListEntity getPortList(HttpSession session, @RequestBody ReqGetCarrierPortListEntity reqEntity, Errors errors, Locale locale,
            Model model) throws McsException, AjaxAurgumentException {

        // ------------------------------------
        // アクセス権チェック
        // ------------------------------------
        super.setUserInfoAjax(session, locale, ComConst.ScreenInfo.INFO_CARRIER.getChgAuthFuncId());

        ResGetCarrierPortListEntity resEntity = new ResGetCarrierPortListEntity();

        // ------------------------------------
        // データ取得
        // ------------------------------------
        List<String[]> portList = carrierService.getPortList(reqEntity.ohbId);

        // ------------------------------------
        // 実行結果設定
        // ------------------------------------
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";
        resEntity.setBody(portList);

        return resEntity;

    }

}
