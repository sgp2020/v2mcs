//@formatter:off
/**
 ******************************************************************************
 * @file        BaseController.java
 * @brief       通常アクセス時のコントローラ基底クラス
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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import net.muratec.mcs.exception.McsException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     通常アクセス時のコントローラ基底クラス
 * @par       機能:
 *              DataAccessExceptionHandler（DBアクセス例外ハンドラ）
 *              McsExceptionHandler(McsException)（MCS例外ハンドラ）
 *              UndeclaredThrowableExceptionHandler（未宣言例外ハンドラ）
 *              ThrowableHandler（例外ハンドラ）
 * @attention 通常HTTPより呼び出されるコントローラは必ずこのクラスを継承すること。
 * @note      例外をハンドリングし、エラー情報をエラー画面にセットし遷移
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class BaseController extends McsBaseController {

    /** ログ出力 */
    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /** メッセージリソース */
    @Autowired private MessageSource messageSource;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DataAccessExceptionハンドラー
     * @param     ex             Exception
     * @return    遷移先情報
     * @retval    遷移先をエラー画面に設定し、エラー情報を埋め込んで返却
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
    public ModelAndView DataAccessExceptionHandler(Exception ex) {

        ModelAndView mav = new ModelAndView();

        if (ex instanceof DuplicateKeyException) {
            // エラーメッセージ生成 一意制約エラー
            mav.addObject("message",
                    messageSource.getMessage("ERR0019", null, "ERR0019", LocaleContextHolder.getLocale()));
            mav.addObject("detail", ex.getMessage());
            logger.error("DuplicateKeyException", ex);
        } else {
            // エラーメッセージ生成 その他のDBアクセスエラー
            mav.addObject("message",
                    messageSource.getMessage("ERR0018", null, "ERR0018", LocaleContextHolder.getLocale()));
            mav.addObject("detail", ex.getMessage());
            logger.error("DataAccessException", ex);
        }

        // 遷移先のJSPを指定します。(Error.jspに遷移します。)
        mav.setViewName("Error");
        return mav;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     McsExceptionHandlerハンドラー
     * @param     ex             McsExceptionHandler
     * @return    遷移先情報
     * @retval    遷移先をエラー画面に設定し、エラー情報を埋め込んで返却
     * @attention
     * @note      SpringframeworkでのMCS例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(McsException.class)
    public ModelAndView McsExceptionHandler(McsException ex) {

        ModelAndView mav = new ModelAndView();

        // エラーメッセージ生成
        mav.addObject("message", messageSource.getMessage("mcssystem.exception.message", null,
                "mcssystem.exception.message", LocaleContextHolder.getLocale()));
        String message = null;
        if (ex.getMessageKey() == null || ex.getMessageKey().length() == 0) {
            message = ex.getMessage();
        } else {
            String mes = messageSource.getMessage(ex.getMessageKey(), null, ex.getMessageKey(),
                    LocaleContextHolder.getLocale());
            message = mes + "(" + ex.getMessage() + ")";
        }
        mav.addObject("detail", message);

        // 遷移先のJSPを指定します。(Error.jspに遷移します。)
        mav.setViewName("Error");

        logger.error("McsException", ex);
        return mav;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     UndeclaredThrowableExceptionハンドラー
     * @param     ex             UndeclaredThrowableException
     * @return    遷移先情報
     * @retval    遷移先をエラー画面に設定し、エラー情報を埋め込んで返却
     * @attention
     * @note      呼出しハンドラで認識しない例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(UndeclaredThrowableException.class)
    public ModelAndView UndeclaredThrowableExceptionHandler(Throwable ex) {

        ModelAndView mav = null;

        if (ex.getCause() instanceof McsException) {
            mav = McsExceptionHandler((McsException) ex.getCause());
        } else {
            mav = ThrowableHandler((McsException) ex.getCause());
        }

        return mav;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Throwableハンドラー
     * @param     ex             Throwable
     * @return    遷移先情報
     * @retval    遷移先をエラー画面に設定し、エラー情報を埋め込んで返却
     * @attention
     * @note      その他例外ハンドリング
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @ExceptionHandler(Throwable.class)
    public ModelAndView ThrowableHandler(Throwable ex) {

        ModelAndView mav = new ModelAndView();

        // エラーメッセージ生成
        mav.addObject("message", messageSource.getMessage("server.exception.message", null, "server.exception.message",
                LocaleContextHolder.getLocale()));
        mav.addObject("detail", ex.toString() + " : " + ex.getMessage());

        // 遷移先のJSPを指定します。(Error.jspに遷移します。)
        mav.setViewName("Error");

        logger.error("Exception", ex);
        return mav;
    }

}
