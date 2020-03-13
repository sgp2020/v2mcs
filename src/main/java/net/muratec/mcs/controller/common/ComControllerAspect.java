//@formatter:off
/**
 ******************************************************************************
 * @file        ComControllerAspect.java
 * @brief       SpringFrameworkの動作に処理を注入させる
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
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.gson.Gson;

import net.muratec.mcs.annotation.MsgCacheUpdate;
import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.entity.common.AuthenticationEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;
import net.muratec.mcs.service.common.MsgUtilService;
import net.muratec.mcs.service.common.OpeLogService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     SpringFrameworkの動作に処理を注入させるクラス
 * @par       機能:
 *              controllerLoging（Controller動作ログ取得）
 *              writeOpLogNormalyEnd（Controller正常終了時処理）
 *              writeOpLog（操作ログ書き込み）
 *              logHeadder（ログヘッダ生成）
 *              infoLog（Infoログ出力）
 *              errorLog（Errorログ出力）
 *              sendCacheUpdate(キャッシュ更新通知)
 *              ExeCacheUpdate(キャッシュ更新実行スレッドクラス)
 * @attention
 * @note      SpringFrameworkのAspect機能を用いて各Controllerに対して処理を実行
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Aspect
@Component
public class ComControllerAspect {

    public static final Logger logger = LoggerFactory.getLogger(ComControllerAspect.class);

    /**
     * GSONインスタンス
     */
    protected static final Gson gson = new Gson();

    // 操作ログを出力するサービス
    @Autowired private OpeLogService opeLogService;

    // メッセージを送信するサービス
    @Autowired private MsgUtilService msgUtilService;

    // メッセージフォーマットプロパティ
    @Autowired private Properties m_formatProperties;

    // メッセージリソース
    @Autowired private MessageSource messageSource;

    private ExeCacheUpdate m_thread = null;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Controller実行前後の動作ログ取得機能
     * @param     pjp            処理中のJoinPoint
     * @return    後続処理のオブジェクト
     * @retval    引数より取得した後続処理のオブジェクトをそのまま返却
     * @attention LoggerはLog4Jを利用
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Around("execution(* net.muratec.mcs.controller..*.*(..)) && ! execution(* net.muratec.mcs.controller.common..*.*(..))")
    public Object controllerLoging(ProceedingJoinPoint pjp) throws Throwable {

        // クラス＆メソッド名と引数を取得
        String headder = "none";
        try {
            headder = logHeadder(pjp.getTarget().getClass().getName(), pjp.getSignature().getName());
        } catch (Throwable e) {
            errorLog(headder, "controllerLoging Error.", e);
        }

        // スタートログ
        infoLog(headder, "start ########");

        try {
            // セッション取得
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes
                    .resolveReference(RequestAttributes.REFERENCE_REQUEST);
            HttpSession session = request.getSession();

            // リモートアドレスを取得しセッションに格納
            String remoteAddr = request.getRemoteAddr();
            session.setAttribute(ComConst.SessionKey.REMOTE_ADDRESS, remoteAddr);
        } catch (Throwable e) {
            errorLog(headder, "controllerLoging Error.", e);
        }

        // 引数出力
        try {
            Object[] args = pjp.getArgs();
            for (Object arg : args) {
                String strArg = arg.toString();
                infoLog(headder, "arg : " + strArg);
            }
        } catch (Throwable e) {
            errorLog(headder, "controllerLoging Error.", e);
        }

        // 対象メソッド開始（前後の時間取得し実行時間（差分）を計算）
        long beforeTime = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long afterTime = System.currentTimeMillis();
        long execTime = afterTime - beforeTime;

        // 終了ログ
        infoLog(headder, "End " + String.valueOf(execTime) + "(ms) #########");

        // 戻り値返却
        return retVal;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Controller正常終了時処理
     * @param     joinPoint      処理のJoinPoint
     * @return
     * @retval
     * @attention Controllerの基底クラスを継承し、OpLogアノテーションが設定されている
     *            Controllerの正常終了時に実行される。
     * @note      アノテーションより操作ログコードを生成
     *            joinPointより画面からの入力を取得し操作ログを出力する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @AfterReturning("( target(net.muratec.mcs.controller.common.BaseAjaxController) || target(net.muratec.mcs.controller.common.BaseController) ) && @annotation(net.muratec.mcs.annotation.OpLog)")
    public void writeOpLogNormalyEnd(JoinPoint joinPoint) {

        // アノテーションより、ログコードを取得～生成する
        OpLog opLog = (OpLog) ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(OpLog.class);

        long logType = opLog.logType();
        long logCode = opLog.screenInfo().getLogCode();
        long logOpType = opLog.logOperationType();
        long logNumber = opLog.number() * 10L;
        long logReserv = ComConst.OP_LOG_RESERV;

        long opLogCode = logType + logCode + logOpType + logNumber + logReserv;

        // メソッド情報取得
        String funcId = opLog.screenInfo().getFunctionId();
        String headder = logHeadder(joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());

        // 操作ログ出力
        infoLog(headder, "## NormalEnd #########(" + funcId + ":" + opLogCode + ") ## START");
        writeOpLog(opLogCode, headder, joinPoint.getArgs());
        infoLog(headder, "## NormalEnd #########(" + funcId + ":" + opLogCode + ") ## END");

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     操作ログ出力機能
     * @param     opLogCode      操作ログコード
     * @param     headder        ログヘッダ
     * @param     args           引数一覧
     * @return
     * @retval
     * @attention Controllerの基底クラスを継承し、OpLogアノテーションが設定されている
     *            Controllerの正常終了時に実行される。
     * @note      IPアドレス、ユーザ名をセッションより取得
     *            argsを解析し、画面からの入力を抽出
     *            それらを元に操作ログを出力する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void writeOpLog(long opLogCode, String headder, Object[] args) {

        // セッション取得
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = request.getSession();

        // セッションよりIPアドレスを取得
        String ipAddress = (String) session.getAttribute(ComConst.SessionKey.REMOTE_ADDRESS);

        // セッションよりユーザ情報を取得
        String userId = ComConst.ConstUserId.NOLOGIN;
        String sessionAttribute = (String) session.getAttribute(ComConst.SessionKey.LOGIN_USER_INFO);
        AuthenticationEntity authEntity = (AuthenticationEntity) gson.fromJson(sessionAttribute,
                AuthenticationEntity.class);
        if (authEntity != null) {
            userId = authEntity.userName;
        }

        // ログ出力
        infoLog(headder, "## IP-Address : " + ipAddress);
        infoLog(headder, "## LoginUser  : " + userId);

        // 引数出力
        String strMcsArg = null;
        for (Object arg : args) {

            String argName = arg.getClass().getName();
            if (argName.startsWith(ComConst.ENTITY_PACKAGE)) {
                strMcsArg = ComFunction.toStringMcs(arg);
                infoLog(headder, "## arg : " + strMcsArg);
            } else {
                infoLog(headder, "## arg : " + arg.toString());
            }

        }
        infoLog(headder, "## mcsarg : " + strMcsArg);

        // 操作ログを出力するサービス呼び出し
        opeLogService.getOpeLog(opLogCode, strMcsArg, userId, ipAddress);

    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログヘッダ生成機能
     * @param     className      クラス名
     * @param     methodName     メソッド名
     * @return    ログヘッダ
     * @retval
     * @attention
     * @note      classNameとmethodNameとでログヘッダの文字列を生成し返却
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String logHeadder(String className, String methodName) {

        return className + " " + methodName + "() ";
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Infoログ出力機能
     * @param     headder        ログヘッダ
     * @param     message        メッセージ
     * @return
     * @retval
     * @attention
     * @note      headderとmessageとを連結しInfoログを出力
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void infoLog(String headder, String message) {

        logger.info(headder + message);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Errorログ出力機能
     * @param     headder        ログヘッダ
     * @param     message        メッセージ
     * @param     t              例外情報
     * @return
     * @retval
     * @attention
     * @note      headderとmessageとを連結し、例外情報を元にErrorログを出力
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void errorLog(String headder, String message, Throwable t) {

        logger.error(headder + message, t);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief      キャッシュ更新通知機能
     * @param      joinPoint      処理のJoinPoint
     * @return     戻り値なし
     * @attention  MsgCacheUpdateアノテーションが設定されている
     *              メソッドの正常終了時のみ実行される。
     * @note       アノテーションよりキャッシュ更新対象のテーブル情報を取得
     *              MsgSvrに対してメッセージの送信を実施する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @AfterReturning("@annotation(net.muratec.mcs.annotation.MsgCacheUpdate)")
    public void sendCacheUpdate(JoinPoint joinPoint) throws McsException {

        // アノテーションより、引数を取得
        MsgCacheUpdate msgCacheUpdate = (MsgCacheUpdate) ((MethodSignature) joinPoint.getSignature()).getMethod()
                .getAnnotation(MsgCacheUpdate.class);

        String [] strCacheIdList = msgCacheUpdate.m_strCacheIdList();
        // トレースログファイル出力のみのため、英語固定
        String strErrMsg = messageSource.getMessage("ERR9007", null, "ERR9007", Locale.ENGLISH);

        m_thread = new ExeCacheUpdate(strCacheIdList, strErrMsg);
        m_thread.start();
    }

    //@formatter:off
    /**
    ******************************************************************************
    * @brief     キャッシュ更新実行スレッドクラス
    * @par       機能:
    *              exeCacheUpdate(キャッシュ更新実行処理)
    *              run(スレッド実行処理)
    * @attention
    * @note
    * ----------------------------------------------------------------------------
    * DATE       VER.        DESCRIPTION                                    AUTHOR
    * ----------------------------------------------------------------------------
    ******************************************************************************
    */
    //@formatter:on
    public class ExeCacheUpdate extends Thread {

        private String[] m_strCacheIdList = null;

        private String m_strErrMsg = null;

        private static final String ERR_MSG = "Cache ID = ";

        //@formatter:off
        /**
         ******************************************************************************
         * @brief     コンストラクタ
         * @param     strCacheIdList        キャッシュ更新リスト
         * @param     strErrMsg             エラーメッセージ
         * @return
         * @retval
         * @attention
         * @note       外部ツール実行スレッドコンストラクタ
         * ----------------------------------------------------------------------------
         * DATE       VER.        DESCRIPTION                                    AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public ExeCacheUpdate(String[] strCacheIdList, String strErrMsg) {
            this.m_strCacheIdList = strCacheIdList;
            this.m_strErrMsg = strErrMsg;
        }

        //@formatter:off
        /**
         ******************************************************************************
         * @brief     キャッシュ更新実行処理
         * @return
         * @retval
         * @attention
         * @note       メンバ変数に指定されているものに対してキャッシュ更新処理を実行する。
         * ----------------------------------------------------------------------------
         * DATE       VER.        DESCRIPTION                                    AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public void exeCacheUpdate() {
            // TODO デバッグログ
            System.out.println("Cache Reload Start");
            for (String tmp: m_strCacheIdList) {
                // TODO デバッグログ
                System.out.println("m_strCacheIdList : " + tmp);
            }
            try {
                for (String strCacheId: m_strCacheIdList) {
                    MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strCommonEvent, ComMsgDef.k_strSubSend, ComMsgDef.k_strCacheUpdate);

                    // ヘッダ情報設定
                    msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btNoReply);
                    msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

                    msgData.setValString(ComMsgDef.k_strKeyCacheId, strCacheId);
                    msgData.setValInteger(ComMsgDef.k_strKeyOperator, ComConst.CacheOperator.RELOAD_OPERATION);

                    MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strCommonEvent, ComMsgDef.k_strCacheUpdate, false, ComMsgDef.k_strControlMessageItem);
                    if (msgResult.getResult() != MsgResult.k_nResultOK) {
                        String strErrMsg = ERR_MSG + strCacheId;
                        throw new McsException(m_strErrMsg + strErrMsg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // TODO デバッグログ
            System.out.println("Cache Reload End");
        }

        //@formatter:off
        /**
         ******************************************************************************
         * @brief     スレッド開始処理
         * @return
         * @retval
         * @attention
         * @note       スレッド開始処理
         * ----------------------------------------------------------------------------
         * DATE       VER.        DESCRIPTION                                    AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        @Override
        public void run() {
            exeCacheUpdate();
        }
    }
}
