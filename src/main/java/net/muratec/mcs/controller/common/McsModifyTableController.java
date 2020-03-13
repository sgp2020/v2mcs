//@formatter:off
/**
 ******************************************************************************
 * @file        McsModifyTableController.java
 * @brief       McsModifyTable用コントローラ
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
package net.muratec.mcs.controller.common;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

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

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.entity.common.McsDataTablesColVisReqEntity;
import net.muratec.mcs.entity.common.ModifyTableItemEntity;
import net.muratec.mcs.entity.common.ResModifyTableEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsModifyTableService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsModifyTable用コントローラクラス
 * @par       機能:
 *              getDispState（表示状態取得処理）
 *              saveColVis（表示状態保存処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
@RequestMapping(value = "/Com/McsModifyTable")
public class McsModifyTableController extends BaseAjaxController {

    /**
     * ログ出力
     */
    private static final Logger logger = LoggerFactory.getLogger(McsModifyTableController.class);

    /**
     * メッセージリソース
     */
    @Autowired private MessageSource messageSource;

    /**
     * ModifyTableService
     */
    @Autowired private McsModifyTableService mcsModifyTableService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     表示状態取得処理
     * @param     tableCompId    テーブルコンポーネントID
     * @param     session        セッション情報
     * @param     errors         エラー情報
     * @param     locale         ロケール情報
     * @param     model          モデル情報
     * @return    表示項目取得結果
     * @retval    JSON形式で返却
     * @attention
     * @note      ユーザごとにModifiTableへ表示する項目を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/GetDispState", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResModifyTableEntity getDispState(@RequestBody String tableCompId, HttpSession session, Errors errors,
            Locale locale, Model model) throws McsException {

        logger.info("Welcome getDispState! The client locale is {}.", locale);

        setUserInfo(session, model, locale, null);
        AuthenticationEntity sessionUserInfo = super.getUserInfo(session);

        List<ModifyTableItemEntity> modifyTableItemList = mcsModifyTableService.getModifyTableItemList(tableCompId,
                sessionUserInfo.userName, locale);

        ResModifyTableEntity resEntity = new ResModifyTableEntity();
        resEntity.body = modifyTableItemList;
        resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        resEntity.result.message = "";

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     表示状態保存処理
     * @param     reqEntity      カラム表示非表示保存リクエストエンティティ
     * @param     session        セッション情報
     * @param     errors         エラー情報
     * @param     locale         ロケール情報
     * @param     model          モデル情報
     * @return    保存処理結果
     * @retval    JSON形式で返却（SpringFrameworkによる自動変換）
     * @attention
     * @note      ユーザごとの表示する項目をDBへ保存
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/SaveColVis", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.SYS_MODIFY_TABLE_MOD, logOperationType = ComConst.LogOperationType.SYS,
            number = 1L)
    public AjaxResBaseEntity saveColVis(@RequestBody McsDataTablesColVisReqEntity reqEntity, HttpSession session,
            Errors errors, Locale locale, Model model) throws McsException {

        logger.info("savecolvis. tableCompId=" + reqEntity.tableCompId + " list=" + reqEntity.columnDisplayList);

        setUserInfo(session, model, locale, null);
        AuthenticationEntity sessionUserInfo = getUserInfo(session);

        // NOLOGINの場合エラー
        if (ComConst.ConstUserId.NOLOGIN.equals(sessionUserInfo.userName)) {
            throw new McsException(messageSource.getMessage("ERR0008", null, "ERR0008", locale));
        }

        int updateCount = mcsModifyTableService.updateColumnVisible(sessionUserInfo.userName, reqEntity.tableCompId,
                reqEntity.columnDisplayList);

        AjaxResBaseEntity resEntity = new AjaxResBaseEntity();
        if (updateCount == 0) {
            resEntity.result.status = ComConst.AjaxStatus.ERROR;
            // No target for update.
            String errMessage = messageSource.getMessage("ERR0020", null, "ERR0020", locale);
            resEntity.result.message = errMessage;
        } else {
            resEntity.result.status = ComConst.AjaxStatus.SUCCESS;
        }

        return resEntity;
    }

}
