//@formatter:off
/**
 ******************************************************************************
 * @file        McsMsgViewService.java
 * @brief       メッセージ一覧表示サービスクラス
 * @par
 * @author      T.Iga/CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2017/09/20 0.5         Step4リリース                               T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ一覧表示サービス
 * @par    メッセージ一覧表示/管理する部品を実装
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class McsMsgViewService {

    // メッセージ送受信サービス
    @Autowired private MsgUtilService msgUtilService;

    // フォーマットプロパティ
    @Autowired private Properties m_formatProperties;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  受信者IDの設定
     * @par    一意なIDを生成し、MsgSvrへ登録を実施
     * @param  なし
     * @return 受信者ID
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String setRecvID() {

        // サーバで受信者IDを自動生成
        String uuid = UUID.randomUUID().toString();
        // UUIDから"-"を削除 32文字に変更
        String strRecvID = uuid.replaceAll("-", "");

        // 宛先リスト登録処理を実行
        if (msgUtilService.registerAddressList(strRecvID, "MCS.1.GUI." + strRecvID + ".GetMessageList")) // TODO アドレスの内容
        {
            // 宛先リスト登録成功
            return strRecvID;
        } else {
            // 宛先リスト登録失敗
            return "";
        }
    }

    // メッセージ一覧取得処理
    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ一覧取得
     * @par    MsgSvr上のメッセージを取得
     * @param  strRecvID  受信者ID
     * @return 取得結果
     * @note   メッセージ送受信サービスの共通処理を実行
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public MsgResult getMsgList(String strRecvID) {

        // TODO 以下の処理は動作確認用の処理
        // 受信者IDリストの登録を行い、メッセージサーバに対してメッセージの送信を行う
//        if (true) {
//            msgUtilService.registerAddressList(strRecvID, "MCS.1.GUI." + strRecvID + ".GetMessageList");

//            int rnd = new Random().nextInt(1);
//            System.out.println(rnd);
//            for (int i = 0; i < rnd; i++) {
//                MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strGUI, ComMsgDef.k_strSubRecv,
//                        ComMsgDef.k_strGetMessageList);
//                SimpleDateFormat dateFormat = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_FF6);

//                msgData.setValString(ComMsgDef.k_strKeyDateTime, dateFormat.format(new Date()));
//                msgData.setValString(ComMsgDef.k_strKeyMessage, "Message No" + (i + 1));

//                if (!msgUtilService.sendMsgSvr("MCS.1.GUI." + strRecvID + ".GetMessageList", msgData)) {
//                    break;
//                }
//            }
//        }

        // メッセージリストの取得
        MsgResult result = msgUtilService.recvMsgListMsgSvr(strRecvID, "MCS.1.GUI." + strRecvID + ".GetMessageList",
                ComMsgDef.k_strGUI, ComMsgDef.k_strGetMessageList);

        return result;
    }
}
