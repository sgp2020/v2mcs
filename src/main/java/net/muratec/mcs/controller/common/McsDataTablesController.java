//@formatter:off
/**
 ******************************************************************************
 * @file        McsDataTablesController.java
 * @brief       McsDataTables用コントローラ
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
import net.muratec.mcs.entity.common.McsDataTablesColInfoReqEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.McsDataTablesService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     McsDataTables用コントローラクラス
 * @par       機能:
 *              saveColumnInfo（画面情報保存処理）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
@RequestMapping(value = "/Com/McsDataTables", method = RequestMethod.POST)
public class McsDataTablesController extends BaseAjaxController {

    /**
     * メッセージリソース
     */
    @Autowired private MessageSource messageSource;

    /**
     * ログ出力
     */
    private static final Logger logger = LoggerFactory.getLogger(McsDataTablesController.class);

    /**
     * DataTablesサービス
     */
    @Autowired private McsDataTablesService mcsDataTablesService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     画面情報保存処理
     * @param     reqEntity      カラム情報
     * @param     session        セッション情報
     * @param     errors         エラー情報
     * @param     locale         ロケール情報
     * @param     model          モデル情報
     * @return    保存処理結果
     * @retval    JSON形式で返却
     * @attention
     * @note      カラム表示の情報を全て（表示非表示・表示順・表示件数）を保存
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/SaveColumnInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @OpLog(screenInfo = ComConst.ScreenInfo.SYS_DATA_TABLES_MOD, logOperationType = ComConst.LogOperationType.SYS,
            number = 1L)
    public AjaxResBaseEntity saveColumnInfo(@RequestBody McsDataTablesColInfoReqEntity reqEntity, HttpSession session,
            Errors errors, Locale locale, Model model) throws McsException {

        logger.info("savecolorder. tableCompId=" + reqEntity.tableCompId);

        setUserInfo(session, model, locale, null);
        AuthenticationEntity sessionUserInfo = super.getUserInfo(session);

        // NOLOGINの場合エラー
        if (ComConst.ConstUserId.NOLOGIN.equals(sessionUserInfo.userName)) {
            throw new McsException(messageSource.getMessage("ERR0008", null, "ERR0008", locale));
        }

        int updateCount = mcsDataTablesService.updateColumnInfo(sessionUserInfo.userName, reqEntity.tableCompId,
                reqEntity.pageRecords, reqEntity.columnOrderList, reqEntity.columnDisplayList);

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
