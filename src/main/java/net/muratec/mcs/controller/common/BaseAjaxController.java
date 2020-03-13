//@formatter:off
/**
 ******************************************************************************
 * @file        BaseAjaxController.java
 * @brief       Restアクセス時のコントローラ基底クラス
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

import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.entity.common.AjaxResBaseEntity;
import net.muratec.mcs.exception.AjaxAurgumentException;
import net.muratec.mcs.exception.McsException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     Restコントローラの基底クラス。
 * @par       機能:
 *              HttpMessageNotReadableExceptionHandler（httpメッセージ読込不可例外ハンドラ）
 *              AjaxAurgumentExceptionHandler（引数例外ハンドラ）
 *              DataAccessExceptionHandler（DBアクセス例外ハンドラ）
 *              McsExceptionHandler(McsException)（MCS例外ハンドラ）
 *              UndeclaredThrowableExceptionHandler（未宣言例外ハンドラ）
 *              ThrowableHandler（例外ハンドラ）
 * @attention Ajaxより呼び出されるコントローラは必ずこのクラスを継承すること。
 * @note      例外をハンドリングし、JSONでエラー情報を返却
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class BaseAjaxController extends McsBaseController {

    /** ログ出力 */
    public static final Logger logger = LoggerFactory.getLogger(BaseAjaxController.class);

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     HttpMessageNotReadableExceptionハンドラー
     * @param     ex             HttpMessageNotReadableException
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {

        AjaxResBaseEntity res = new AjaxResBaseEntity();
        res.result.status = ComConst.AjaxStatus.ERROR;
        res.result.message = messageSource.getMessage("HttpMessageNotReadableException.message", null,
                "HttpMessageNotReadableException.message", LocaleContextHolder.getLocale());
        logger.error("HttpMessageNotReadableException", ex);
        return res;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AjaxAurgumentExceptionHandlerハンドラー
     * @param     ex             AjaxAurgumentExceptionHandler
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note      Springframeworkでのバリデーションチェックエラー時の例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(AjaxAurgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity AjaxAurgumentExceptionHandler(AjaxAurgumentException ex) {

        AjaxResBaseEntity res = new AjaxResBaseEntity();
        res.result.status = ComConst.AjaxStatus.ERROR;
        res.result.message = messageSource.getMessage("ajax.varidat.error.message", null, "ajax.varidat.error.message",
                LocaleContextHolder.getLocale()) + ex.getMessage();
        res.result.errorInfoList = ex.getErrorInfoList();
        logger.error("AjaxAurgumentException", ex);
        return res;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DataAccessExceptionハンドラー
     * @param     ex             Exception
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note      データベースアクセス時（下記例外）のハンドリング
     *              DataAccessException
     *              SQLException
     *              TransactionException
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler({ DataAccessException.class, SQLException.class, TransactionException.class })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity DataAccessExceptionHandler(Exception ex) {

        AjaxResBaseEntity res = new AjaxResBaseEntity();
        res.result.status = ComConst.AjaxStatus.ERROR;
        if (ex instanceof DuplicateKeyException) {
            // 一意制約エラー
            res.result.message = messageSource.getMessage("ERR0019", null, "ERR0019", LocaleContextHolder.getLocale());
            logger.error("DuplicateKeyException", ex);
        } else {
            // その他のDBアクセスエラー
            res.result.message = messageSource.getMessage("ERR0018", null, "ERR0018", LocaleContextHolder.getLocale())
                    + ComConst.BR + ex.getMessage();
            logger.error("DataAccessException", ex);
        }
        return res;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsExceptionハンドラー
     * @param     ex             McsException
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note      SpringframeworkでのMCS例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(McsException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity McsExceptionHandler(McsException ex) {

        AjaxResBaseEntity res = new AjaxResBaseEntity();
        res.result.status = ComConst.AjaxStatus.ERROR;
        if (ex.getMessageKey() == null || ex.getMessageKey().length() == 0) {
            res.result.message = ex.getMessage();
        } else {
            String mes = messageSource.getMessage(ex.getMessageKey(), null, ex.getMessageKey(),
                    LocaleContextHolder.getLocale());
            String additional = ex.getMessage();
            if (additional != null && additional.length() > 0) {
                mes += "(" + additional + ")";
            }
            res.result.message = mes;
        }
        logger.error("McsException", ex);
        return res;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     UndeclaredThrowableExceptionハンドラー
     * @param     ex             UndeclaredThrowableException
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note      呼出しハンドラで認識しない例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity UndeclaredThrowableExceptionHandler(Throwable ex) {

        AjaxResBaseEntity res = null;

        if (ex.getCause() instanceof McsException) {
            res = McsExceptionHandler((McsException) ex.getCause());
        } else {
            res = ThrowableHandler((McsException) ex.getCause());
        }

        return res;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ThrowableHandlerハンドラー
     * @param     ex             ThrowableHandler
     * @return    例外情報
     * @retval    JSON形式で返却
     * @attention
     * @note      その他例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AjaxResBaseEntity ThrowableHandler(Throwable ex) {

        AjaxResBaseEntity res = new AjaxResBaseEntity();

        res.result.status = ComConst.AjaxStatus.ERROR;
        res.result.message = messageSource.getMessage("server.exception.message", null, "server.exception.message",
                LocaleContextHolder.getLocale()) + ComConst.BR + ex.getMessage();
        logger.error("Exception", ex);
        return res;
    }

}
