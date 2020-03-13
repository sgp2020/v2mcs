//@formatter:off
/**
 ******************************************************************************
 * @file        AMHSModeChangeService.java
 * @brief       AMHSモード変更のサービス
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
 * 2017/03/31 0.2         Step2_2リリース                                   CSC
 * 2018/11/07 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.maint;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.entity.maint.ReqExeAMHSModeChangeEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.MsgUtilService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     AMHSモード変更のサービスクラス
 * @par       機能:
 *              exeControlModeOnline（制御モード：オンライン実行）
 *              exeControlModeOffline（制御モード：オフライン実行）
 *              exeSemStatePaused（SEM状態：ポーズ実行）
 *              exeSemStateAuto（SEM状態：オート実行）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class AMHSModeChangeService extends BaseService {

    // メッセージリソース
    @Autowired MessageSource messageSource;

    // メッセージ送受信サービス
    @Autowired private MsgUtilService msgUtilService;

    // メッセージフォーマットプロパティ
    @Autowired private Properties m_formatProperties;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     制御モード：オンライン実行機能
     * @param     reqEntity    リクエストエンティティ
     * @return
     * @retval
     * @attention
     * @note       外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public void exeControlModeOnline(ReqExeAMHSModeChangeEntity reqEntity) throws McsException {

//      MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSubSend, ComMsgDef.k_strRequestOnLine);       // MACS4#0036 Del
        MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strStatusServer, ComMsgDef.k_strSubSend, ComMsgDef.k_strRequestOnLine);   // MACS4#0036 Add

        // ヘッダ情報設定
        msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btIsReply);
        msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

        // メッセージ本文設定
        msgData.setValString(ComMsgDef.k_strKeyAmhsID, reqEntity.getController());

//      MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strRequestOnLine, true, ComMsgDef.k_strApplicationMessageItem);      // MACS4#0036 Del
        MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strStatusServer, ComMsgDef.k_strRequestOnLine, true, ComMsgDef.k_strApplicationMessageItem);  // MACS4#0036 Add

        if (msgResult.getResult() != MsgResult.k_nResultOK)
        {
            throw new McsException(messageSource.getMessage("ERR9008", null, "ERR9008", LocaleContextHolder.getLocale()));
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     制御モード：オフライン実行機能
     * @param     reqEntity    リクエストエンティティ
     * @return
     * @retval
     * @attention
     * @note       外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public void exeControlModeOffline(ReqExeAMHSModeChangeEntity reqEntity) throws McsException {

//      MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSubSend, ComMsgDef.k_strRequestOffLine);      // MACS4#0036 Del
        MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strStatusServer, ComMsgDef.k_strSubSend, ComMsgDef.k_strRequestOffLine);  // MACS4#0036 Add

        // ヘッダ情報設定
        msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btIsReply);
        msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

        // メッセージ本文設定
        msgData.setValString(ComMsgDef.k_strKeyAmhsID, reqEntity.getController());

//      MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strRequestOffLine, true, ComMsgDef.k_strApplicationMessageItem);     // MACS4#0036 Del
        MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strStatusServer, ComMsgDef.k_strRequestOffLine, true, ComMsgDef.k_strApplicationMessageItem); // MACS4#0036 Add

        if (msgResult.getResult() != MsgResult.k_nResultOK)
        {
            throw new McsException(messageSource.getMessage("ERR9009", null, "ERR9009", LocaleContextHolder.getLocale()));
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SEM状態：ポーズ実行機能
     * @param     reqEntity    リクエストエンティティ
     * @return
     * @retval
     * @attention
     * @note       外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void exeSemStatePaused(ReqExeAMHSModeChangeEntity reqEntity) throws McsException {

        MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSubSend, ComMsgDef.k_strSendPause);

        // ヘッダ情報設定
        msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btIsReply);
        msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

        // メッセージ本文設定
        msgData.setValString(ComMsgDef.k_strKeyAmhsID, reqEntity.getController());

        MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSendPause, true, ComMsgDef.k_strApplicationMessageItem);

        if (msgResult.getResult() != MsgResult.k_nResultOK)
        {
            throw new McsException(messageSource.getMessage("ERR9010", null, "ERR9010", LocaleContextHolder.getLocale()));
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SEM状態：オート実行機能
     * @param     reqEntity    リクエストエンティティ
     * @return
     * @retval
     * @attention
     * @note       外部プロセス連携
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void exeSemStateAuto(ReqExeAMHSModeChangeEntity reqEntity) throws McsException {

        MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSubSend, ComMsgDef.k_strSendResume);

        // ヘッダ情報設定
        msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btIsReply);
        msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

        // メッセージ本文設定
        msgData.setValString(ComMsgDef.k_strKeyAmhsID, reqEntity.getController());

        MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strAmhsCtrl, ComMsgDef.k_strSendResume, true, ComMsgDef.k_strApplicationMessageItem);

        if (msgResult.getResult() != MsgResult.k_nResultOK)
        {
            throw new McsException(messageSource.getMessage("ERR9011", null, "ERR9011", LocaleContextHolder.getLocale()));
        }
    }
}
