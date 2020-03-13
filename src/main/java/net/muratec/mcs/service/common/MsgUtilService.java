//@formatter:off
/**
 ******************************************************************************
 * @file        MsgUtilService.java
 * @brief       メッセージ送受信サービスクラス
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
 * 2019/03/13 MACS4#0114  GUI MCSAlarmクリア対応                      T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;                  // MACS4#0114 Add
import java.text.DecimalFormat;             // MACS4#0114 Add
//import java.text.SimpleDateFormat;        // MACS4#0114 Del
//import java.util.Date;                    // MACS4#0114 Del
import java.util.Properties;
import java.util.UUID;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.muratec.mcs.common.ComConst;
//import net.muratec.mcs.common.ComConst;   // MACS4#0114 Del
import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージ送受信サービス
 * @par    MsgSvrへのメッセージ送受信処理を実装
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class MsgUtilService {

    // 固定値

    // FMessageId
    // ! メッセージID - メッセージ送信要求
    private static final int k_nMsgSendReq = 1;
    // ! メッセージID - メッセージ受信要求
    private static final int k_nMsgRecvReq = 3;
    // ! メッセージID - 受信宛先リクエスト登録要求
    private static final int k_nRegisterAddressReq = 11;
    // ! メッセージID - 受信宛先リクエスト削除要求
    private static final int k_nRemoveAddressListReq = 17;

    // XQueueProcessingState
    // ! キューの処理状態 - 未読
    private static final byte k_btQueueProcessingStateUnread = (byte) 0x00;
    // ! キューの処理状態 - 処理済み
    private static final byte k_btQueueProcessingStateProcessed = (byte) 0x02;

    // ! リザルト:成功
    private static final byte k_btMsgResultOK = (byte) 0x00;

    // ! データ長:int
    private static final int k_nIntLength = 4;
    // ! メッセージリスト最大取得件数
    private static final int k_nMaxMsgSize = 100;

    // メンバ変数

    // ! 設定ファイル
    @Autowired private Properties m_msgsvrProperties;
    // ! フォーマットファイル
    @Autowired private Properties m_formatProperties;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ送受信処理
     * @par    MsgSvrへのメッセージ送受信処理を実施
     * @param  msgData 送信データ
     * @param  strProcName プロセス名
     * @param  strEventName イベント名
     * @param  bResponse 受信可否
     * @param  strMessageKind 制御メッセージ or アプリケーションメッセージ
     * @return 受信結果
     * @note   各サービスから本処理を呼び出し、MsgSvrへのメッセージ送受信要求を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0114  GUI MCSAlarmクリア対応                                 T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public MsgResult communicationMsgSvr(MsgData msgData, String strProcName, String strEventName, boolean bResponse, String strMessageKind) {

        MsgResult msgResult = new MsgResult();
        msgResult.setResult(MsgResult.k_nResultOK);

        try {
            // 宛先の取得
            String strSendAddress = m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubProject)
                                  + ComMsgDef.k_strSubjectDelimiter
                                  + strMessageKind
                                  + ComMsgDef.k_strSubjectDelimiter
                                  + m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubSysGrp + strProcName)
                                  + ComMsgDef.k_strSubjectDelimiter + strProcName + ComMsgDef.k_strSubjectDelimiter
                                  + m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubProcSID + strProcName)
                                  + ComMsgDef.k_strSubjectDelimiter +
                                  m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubEventName + ComMsgDef.k_strSubSend + strEventName);

            // TODO テスト
            System.out.println("送信宛先");
            System.out.println(strSendAddress);

            String strRecvAddress = "";
            String strRecvID = "";

            // 受信処理
            if (bResponse) {
                // 受信宛先登録の取得
                // TODO 動作確認用
//                strRecvAddress = m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubProject)
//                        + ComMsgDef.k_strSubjectDelimiter
//                        + m_msgsvrProperties
//                                .getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubSysGrp + strProcName)
//                        + ComMsgDef.k_strSubjectDelimiter + strProcName + ComMsgDef.k_strSubjectDelimiter
//                        + m_msgsvrProperties
//                                .getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubProcSID + strProcName)
//                        + ComMsgDef.k_strSubjectDelimiter + m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject
//                                + ComMsgDef.k_strSubEventName + ComMsgDef.k_strSubSend + strEventName);

                // 受信宛先登の取得
                // TODO 実際の処理
                 strRecvAddress = m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubProject)
                                + ComMsgDef.k_strSubjectDelimiter
                                + strMessageKind
                                + ComMsgDef.k_strSubjectDelimiter
                                + m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubSysGrp + ComMsgDef.k_strGUI)
                                + ComMsgDef.k_strSubjectDelimiter
                                + ComMsgDef.k_strGUI + ComMsgDef.k_strSubjectDelimiter
                                + Thread.currentThread().getId() + ComMsgDef.k_strSubjectDelimiter
                                + m_msgsvrProperties.getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubEventName + ComMsgDef.k_strSubRecv + strEventName);

                // 受信宛先登録処理
                strRecvID = "GUI."
                        + m_msgsvrProperties
                                .getProperty(ComMsgDef.k_strSubject + ComMsgDef.k_strSubSysGrp + ComMsgDef.k_strGUI)
                        + "." + Thread.currentThread().getId();
                System.out.println("受信者ID");
                System.out.println(strRecvID);
                if (!registerAddressList(strRecvID, strRecvAddress)) {
                    msgResult.setResult(MsgResult.k_nResultNG);
                }
            }

            if (msgResult.getResult() == MsgResult.k_nResultOK) {
                // TODO ヘッダ情報の設定
                // どういった情報をどこから取得して設定すればいいか方針を決める必要有
                // 共通の内容なので共通処理で定義するのが妥当と思われる
                short sNodeID = new Short(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeySysNodeID)).shortValue();
//              SimpleDateFormat dateFormat = new SimpleDateFormat(ComConst.DATE_TIME_FORMAT_FF6);      // MACS4#0114 Del
                msgData.setValInteger(ComMsgDef.k_strKeySystemID, (int) Thread.currentThread().getId());
                msgData.setValShort(ComMsgDef.k_strKeyNodeID, sNodeID);
//              msgData.setValString(ComMsgDef.k_strKeyTimestamp, dateFormat.format(new Date()));       // MACS4#0114 Del
                // MACS4#0114 Add Start
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String strTimestamp = timestamp.toString().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "");
                DecimalFormat df = new DecimalFormat(ComConst.DECIMAL_TIMESTAMP_FORMAT);
                double dTimestamp = new Double(strTimestamp).doubleValue();
                strTimestamp = df.format(dTimestamp).replaceAll("\\.", "");
                msgData.setValString(ComMsgDef.k_strKeyTimestamp, strTimestamp);
                // MACS4#0114 Add End

                // 送信待ちサブジェクトの設定
                msgData.setValString(ComMsgDef.k_strKeyResSubject, strRecvAddress);

                // メッセージ送信処理
                if (!sendMsgSvr(strSendAddress, msgData)) {
                    // メッセージ送信失敗
                    msgResult.setResult(MsgResult.k_nResultNG);
                }
            }

            if (bResponse && msgResult.getResult() == MsgResult.k_nResultOK) {
                // メッセージ受信処理
                msgResult = recvMsgSvr(strRecvID, strRecvAddress, strProcName, strEventName);
            }
            
            if (bResponse) {
                // 宛先リスト削除
                if (!removeAddressListReq(strRecvID, strRecvAddress)) {
                    msgResult.setResult(MsgResult.k_nResultNG);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            msgResult.setResult(MsgResult.k_nResultNG);
        }

        return msgResult;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ送信
     * @par    MsgSvrにメッセージ送信要求を送信
     * @param  strSendAddress 宛先
     * @param  sendMsgData 送信データ
     * @return 送信結果
     * @retval true 成功
     * @retval false 失敗
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean sendMsgSvr(String strSendAddress, MsgData sendMsgData) {

        Socket connMsgSvr = null;
        DataOutputStream outData = null;
        DataInputStream inData = null;

        boolean bResult = false;

        System.out.println("メッセージ送信処理"); // TODO

        try {
            // MsgSvrとのコネクションを確立
            connMsgSvr = MsgUtilService.openConnection(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectIP),
                    m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectPort));

            // メッセージ送信
            outData = new DataOutputStream(connMsgSvr.getOutputStream());
            // 宛先情報クラスの生成
            MsgXDestination msgXDest = new MsgXDestination(strSendAddress);
            // メッセージ本文
            byte[] bufMsg = sendMsgData.getByteArray();

            // TODO UUID
            String uuid = UUID.randomUUID().toString();

            // シリアライズ
            // FMessageSendRequest
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(2);                         // メッセージの要素数
            packer.packInt(1);                                 // プロセスNo TODO (デバッグ用)
            packer.packArrayHeader(3);                         // メッセージの要素数
            packer.addPayload(msgXDest.getMsgPackByteArray()); // 宛先情報
            packer.packRawStringHeader(bufMsg.length);         // 送信データ長
            packer.addPayload(bufMsg);                         // 送信データ
            packer.packString(uuid);                           // シーケンスNo

            byte[] bufXMsg = getXMsgPackByteArray(packer);
            packer.close();

            System.out.println("送信データ");
            System.out.println("送信サイズ(MsgBody) : " + bufXMsg.length);
            for (byte i : bufXMsg) {
                System.out.printf("%02X,", i);
            }
            System.out.println();
            // メッセージサイズ送信
            outData.writeInt(k_nIntLength + bufXMsg.length);
            // メッセージID送信
            outData.writeInt(k_nMsgSendReq);
            // メッセージ本文送信
            outData.write(bufXMsg);
            outData.flush();

            // MsgSvrからのリザルトを受信
            inData = new DataInputStream(connMsgSvr.getInputStream());
            // メッセージ全体サイズ
            int nTotalSize = inData.readInt();
            // メッセージID取得 - 画面側で使用しないため、読み捨て
            inData.readInt();

            // メッセージサイズにはメッセージID分含まれているため、INT_LENGTH分減算
            byte[] bufRecvXMsg = new byte[nTotalSize - k_nIntLength];

            // メッセージ本文受信
            inData.read(bufRecvXMsg);

            byte[] bufRecvMsg = getRecvMsgData(bufRecvXMsg);

            // デシリアライズ
            // FMessageSendResponse
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bufRecvMsg);
            unpacker.unpackArrayHeader();
            byte bufResult = unpacker.unpackByte(); // 応答コード
            unpacker.close();
            // TODO 
            System.out.println("受信コード");
            System.out.println(bufResult);

            if (bufResult == k_btMsgResultOK) {
                bResult = true;
            } else {
                bResult = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outData != null) {
                    outData.close();
                }
                if (inData != null) {
                    inData.close();
                }
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bResult;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ受信
     * @par    MsgSvrにメッセージ受信要求を送信
     * @param  strRecvID  受信者ID
     * @param  strAddress 宛先
     * @param  strProcName プロセス名
     * @param  strEventName イベント名
     * @return 受信結果
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public MsgResult recvMsgSvr(String strRecvID, String strAddress, String strProcName, String strEventName) {

        MsgResult resultMsg = new MsgResult();

        Socket connMsgSvr = null;
        DataOutputStream outData = null;
        DataInputStream inData = null;

        System.out.println("メッセージ受信処理"); // TODO

        try {
            // MsgSvrとのコネクションを確立
            connMsgSvr = MsgUtilService.openConnection(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectIP),
                    m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectPort));

            // メッセージ受信要求
            outData = new DataOutputStream(connMsgSvr.getOutputStream());

            // TODO UUID
            String uuid = UUID.randomUUID().toString();

            // シリアライズ
            // FMessageReceiveRequest
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(5);                       // メッセージデータの要素数
            packer.packInt(1);                               // 送信元プロセスNo (デバッグ用)
            packer.packArrayHeader(1);                       // 受信者IDリスト数
            packer.packArrayHeader(2);                       // XReceiverPriorityの要素数
            packer.packString(strRecvID);                    // 受信者ID
            packer.packByte((byte) 1);                      // 優先度
            packer.packLong(5000L);                          // 受信タイムアウト時間(ms) 0の時は無限待ち
            packer.packByte(k_btQueueProcessingStateUnread); // 処理状態
            packer.packArrayHeader(2);                       // アクセス情報の要素数
            packer.packString("");                           // アクセス情報 - シーケンスNo(処理完了時に使用)
            packer.packString(uuid);                         // アクセス情報 - 受信者ID(処理完了時に使用)

            byte[] bufXMsg = getXMsgPackByteArray(packer);
            packer.close();

            // メッセージサイズ(メッセージID + メッセージ本文)送信
            outData.writeInt(k_nIntLength + bufXMsg.length);
            // メッセージID送信
            outData.writeInt(k_nMsgRecvReq);
            // メッセージ本文送信
            outData.write(bufXMsg);
            outData.flush();

            // TODO
            System.out.println("送信データ");
            for (byte i : bufXMsg) {
                System.out.printf("%02X,", i);
            }
            System.out.println();

            // MsgSvrからのリザルトを受信
            inData = new DataInputStream(connMsgSvr.getInputStream());

            // メッセージサイズ取得
            int nTotalSize = inData.readInt();
            // メッセージID取得 - 画面では使用しないため、読み捨て
            inData.readInt();

            // メッセージサイズにはメッセージIDも含まれるため、INT_LENGTH分減算
            byte[] bufRecvXMsg = new byte[nTotalSize - k_nIntLength];
            // メッセージ本文取得
            inData.readFully(bufRecvXMsg);

            // TODO
            System.out.println("受信データ");
            System.out.println("受信データサイズ(MsgBody) : " + bufRecvXMsg.length);
            for (byte i : bufRecvXMsg) {
                System.out.printf("%02X,", i);
            }
            System.out.println();

            byte[] bufRecvMsg = getRecvMsgData(bufRecvXMsg);

            // デシリアライズ
            // FMessageReceiveResponse
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bufRecvMsg);
            unpacker.unpackArrayHeader();
            // メッセージ受信結果取得
            byte bufResult = unpacker.unpackByte(); // 応答コード

            if (bufResult == k_btMsgResultOK) {
                // 成功
                // 必要なデータをデシリアライズ - 不要なデータは読み飛ばす
                unpacker.skipValue(); // 送信元プロセスNo (デバッグ情報)
                unpacker.skipValue(); // メッセージサーバーへの登録日時
                unpacker.skipValue(); // 宛先
                int nMsgLength = unpacker.unpackBinaryHeader();           // 受信データ長
                byte[] bufRecvMsgData = unpacker.readPayload(nMsgLength); // 受信データ本文
                unpacker.unpackArrayHeader();                             // 要素数
                String strSeqNum = unpacker.unpackString();               // シーケンスNo
                unpacker.close();

                // メッセージ構造体への変換
                // TODO 動作確認のためコメントアウト
                MsgData msgRes = new MsgData(m_formatProperties, ComMsgDef.k_strGUI, ComMsgDef.k_strSubRecv, strEventName);
                // TODO 画面からのデータをそのま取得するために、sendを設定
//                MsgData msgRes = new MsgData(m_formatProperties, strProcName, ComMsgDef.k_strSubSend, strEventName);
                msgRes.convertByteArrayToMsgData(bufRecvMsgData);
                // 結果返却用オブジェクトにデータを格納
                resultMsg.setResult(MsgResult.k_nResultOK);
                resultMsg.setMsgData(msgRes);

                // メッセージ受信完了処理
                // 処理の完了報告を行わないと、MsgSvr上にメッセージが残り続けるため
                // シリアライズ
                // FMessageReceiveRequest
                MessageBufferPacker packerComp = MessagePack.newDefaultBufferPacker();
                packerComp.packArrayHeader(5);                          // メッセージデータの要素数
                packerComp.packInt(1);                                  // 送信元プロセスNo (デバッグ用)
                packerComp.packArrayHeader(1);                          // 受信者IDリスト数
                packerComp.packArrayHeader(2);                          // XReceiverPriorityの要素数
                packerComp.packString(strRecvID);                       // 受信者ID
                packerComp.packByte((byte) 1);                         // 優先度
                packerComp.packLong(5000L);                             // 受信タイムアウト時間(ms) 0の時は無限待ち
                packerComp.packByte(k_btQueueProcessingStateProcessed); // 処理状態
                packerComp.packArrayHeader(2);                          // アクセス情報の要素数
                packerComp.packString(strSeqNum);                       // アクセス情報 - シーケンスNo(処理完了時に使用)
                packerComp.packString(strRecvID);                       // アクセス情報 - 受信者ID(処理完了時に使用)

                byte[] bufXMsgComp = getXMsgPackByteArray(packerComp);
                packerComp.close();

                // TODO
                System.out.println("完了報告");
                System.out.println("送信データ");
                for (byte i : bufXMsgComp) {
                    System.out.printf("%02X,", i);
                }
                System.out.println();

                // メッセージサイズ(メッセージID + メッセージ本文)送信
                outData.writeInt(k_nIntLength + bufXMsgComp.length);
                // メッセージID送信
                outData.writeInt(k_nMsgRecvReq);
                // メッセージ本文送信
                outData.write(bufXMsgComp);
                outData.flush();
            } else {
                // 成功以外
                resultMsg.setResult(MsgResult.k_nResultNG);
                unpacker.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResult(MsgResult.k_nResultNG);
        } finally {
            try {
                if (outData != null) {
                    outData.close();
                }
                if (inData != null) {
                    inData.close();
                }
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return resultMsg;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  受信宛先登録
     * @par    MsgSvrに受信宛先の登録要求を送信
     * @param  strRecvID  受信者ID
     * @param  strAddress 宛先
     * @return 処理結果
     * @retval true 成功
     * @retval false 失敗
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean registerAddressList(String strRecvID, String strAddress) {

        Socket connMsgSvr = null;
        DataOutputStream outData = null;
        DataInputStream inData = null;

        boolean bResult = false;

        System.out.println("宛先リスト登録処理"); // TODO
        System.out.println("registerAddressList");
        System.out.println("strRecv : " + strRecvID);
        System.out.println("strAddress : " + strAddress);

        try {
            // MsgSvrとのコネクションを確立
            connMsgSvr = MsgUtilService.openConnection(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectIP),
                    m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectPort));

            // メッセージ送信
            outData = new DataOutputStream(connMsgSvr.getOutputStream());

            // 宛先クラスの作成
            MsgXDestination msgDest = new MsgXDestination(strAddress);
            // シリアライズ
            // FRegisterAddressListRequest
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(3);                        // メッセージの要素数
            packer.packInt(1);                                // 送信元プロセスNo (デバッグ用)
            packer.packString(strRecvID);                     // 受信者ID
            packer.packArrayHeader(1);                        // リスト
            packer.addPayload(msgDest.getMsgPackByteArray()); // 宛先

            byte[] bufXMsg = getXMsgPackByteArray(packer);
            packer.close();

            // TODO デバッグ、動作確認用
            System.out.println("送信データ");
            for (byte i : bufXMsg) {
                System.out.printf("%02X,", i);
            }
            System.out.println();

            // メッセージサイズ送信
            outData.writeInt(k_nIntLength + bufXMsg.length);
            // メッセージID送信
            outData.writeInt(MsgUtilService.k_nRegisterAddressReq);
            // メッセージ本文送信
            outData.write(bufXMsg);
            outData.flush();

            // MsgSvrからのリザルトを受信
            inData = new DataInputStream(connMsgSvr.getInputStream());
            // メッセージ全体サイズ
            int nTotalSize = inData.readInt();
            // メッセージID取得 - 画面側で使用しないため、読み捨て
            inData.readInt();

            // メッセージサイズにはメッセージID分含まれているため、INT_LENGTH分減算
            byte[] bufRecvXMsg = new byte[nTotalSize - k_nIntLength];

            inData.read(bufRecvXMsg);

            // メッセージサーバーのメッセージデータからプロセスのメッセージデータを抜き出す
            byte[] bufRecvMsg = getRecvMsgData(bufRecvXMsg);

            // デシリアライズ
            // FRegisterAddressListResponse
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bufRecvMsg);
            unpacker.unpackArrayHeader();
            byte bufResult = unpacker.unpackByte(); // 応答コード
            unpacker.close();

            System.out.println(bufResult);

            if (bufResult == k_btMsgResultOK) {
                // TODO
                bResult = true;
                System.out.println("bufResult = true");
            } else {
                bResult = false;
                System.out.println("bufResult = false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outData != null) {
                    outData.close();
                }
                if (inData != null) {
                    inData.close();
                }
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bResult;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  受信宛先削除
     * @par    MsgSvrに受信宛先の削除要求を送信
     * @param  strRecvID  受信者ID
     * @param  strAddress 宛先
     * @return 処理結果
     * @retval true 成功
     * @retval false 失敗
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean removeAddressListReq(String strRecvID, String strAddress) {

        Socket connMsgSvr = null;
        DataOutputStream outData = null;
        DataInputStream inData = null;

        boolean bResult = false;

        System.out.println("宛先リスト削除処理");
        System.out.println("removeAddressListReq");
        System.out.println("strRecvID : " + strRecvID);
        System.out.println("strAddress : " + strAddress);

        try {
            // MsgSvrとのコネクションを確立
            connMsgSvr = MsgUtilService.openConnection(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectIP),
                    m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectPort));

            // メッセージ送信
            outData = new DataOutputStream(connMsgSvr.getOutputStream());

            // 宛先クラスの作成
            MsgXDestination msgDest = new MsgXDestination(strAddress);
            // シリアライズ
            // FRemoveAddressListRequest
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(3);                          // メッセージの要素数
            packer.packInt(1);                                  // 送信元プロセスNo(デバッグ用)
            packer.packString(strRecvID);                       // 受信者ID
            packer.packArrayHeader(1);                          // リスト
            packer.addPayload(msgDest.getMsgPackByteArray());   // 宛先

            byte[] bufXMsg = getXMsgPackByteArray(packer);
            packer.close();

            // TODO デバッグログ
            System.out.println("送信データ");
            for (byte i: bufXMsg) {
                System.out.printf("%02X,", i);
            }
            System.out.println();

            // メッセージサイズ送信
            outData.writeInt(k_nIntLength + bufXMsg.length);
            // メッセージID送信
            outData.writeInt(MsgUtilService.k_nRemoveAddressListReq);
            // メッセージ本文送信
            outData.write(bufXMsg);
            outData.flush();

            // MsgSvrからのリザルトを受信
            inData = new DataInputStream(connMsgSvr.getInputStream());
            // メッセージ全体サイズ
            int nTotalSize = inData.readInt();
            // メッセージID取得 - 画面側で使用しないため、読み捨て
            inData.readInt();

            // メッセージサイズにはメッセージID分含まれているため、INT_LENGTH分減算
            byte[] bufRecvXMsg = new byte[nTotalSize - k_nIntLength];

            inData.read(bufRecvXMsg);

            // メッセージサーバーのメッセージデータからプロセスのメッセージデータを抜き出す
            byte[] bufRecvMsg = getRecvMsgData(bufRecvXMsg);

            // デシリアライズ
            // FRemoveAddressListResponse
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bufRecvMsg);
            unpacker.unpackArrayHeader();
            byte bufResult = unpacker.unpackByte();     // 応答コード
            unpacker.close();

            if (bufResult == k_btMsgResultOK) {
                // TODO
                bResult = true;
                System.out.println("bufResult = true");
            } else {
                // TODO
                bResult = false;
                System.out.println("bufResult = false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outData != null) {
                    outData.close();
                }
                if (inData != null) {
                    inData.close();
                }
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return bResult;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージリスト受信
     * @par    MsgSvr上のメッセージリストを受信
     * @param  strRecvID  受信者ID
     * @param  strAddress 宛先
     * @param  strProcName プロセス名
     * @param  strEventName イベント名
     * @return 受信結果
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public MsgResult recvMsgListMsgSvr(String strRecvID, String strAddress, String strProc, String strEventName) {

        MsgResult resultMsg = new MsgResult();

        Socket connMsgSvr = null;
        DataOutputStream outData = null;
        DataInputStream inData = null;

        try {
            // MsgSvrとのコネクションを確立
            connMsgSvr = MsgUtilService.openConnection(m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectIP),
                    m_msgsvrProperties.getProperty(ComMsgDef.k_strKeyConnectPort));

            outData = new DataOutputStream(connMsgSvr.getOutputStream());
            inData = new DataInputStream(connMsgSvr.getInputStream());

            // ループ回数は画面で保持できる100件とする
            for (int i = 0; i < k_nMaxMsgSize; i++) {

                // シリアライズ
                MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
                packer.packArrayHeader(5);                        // メッセージデータの要素数
                packer.packInt(1);                                // 送信元プロセスNo (デバッグ用)
                packer.packArrayHeader(1);                        // 受信者IDリスト数
                packer.packArrayHeader(2);                        // XReceiverPriorityの要素数
                packer.packString(strRecvID);                     // 受信者ID
                packer.packByte((byte) 1);                       // 優先度
                packer.packLong(5000L);                          // 受信タイムアウト時間(ms) 0の時は無限待ち
                packer.packByte(k_btQueueProcessingStateUnread); // 処理状態
                packer.packArrayHeader(2);                       // アクセス情報の要素数
                packer.packString("");                           // アクセス情報 - シーケンスNo(処理完了時に使用)
                packer.packString("");                           // アクセス情報 - 受信者ID(処理完了時に使用)

                byte[] bufXMsg = getXMsgPackByteArray(packer);
                packer.close();

                // メッセージサイズ送信
                outData.writeInt(k_nIntLength + bufXMsg.length);
                // メッセージID送信
                outData.writeInt(k_nMsgRecvReq);
                // メッセージ本文送信
                outData.write(bufXMsg);
                outData.flush();

                // メッセージサイズ取得
                int nTotalSize = inData.readInt();
                // メッセージID取得 - 画面では使用しないので読み捨て
                inData.readInt();

                byte[] bufRecvXMsg = new byte[nTotalSize - k_nIntLength];
                // メッセージ本文取得
                inData.readFully(bufRecvXMsg);

                byte[] bufRecvMsg = getRecvMsgData(bufRecvXMsg);

                // デシリアライズ
                MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bufRecvMsg);
                unpacker.unpackArrayHeader();
                // メッセージ受信結果取得
                byte bResult = unpacker.unpackByte();

                // 結果確認
                if (bResult == k_btMsgResultOK) {
                    // 受信成功
                    // 必要なデータをデシリアライズ - 不要なデータは読み飛ばし
                    unpacker.skipValue(); // 送信元プロセスNo (デバッグ情報)
                    unpacker.skipValue(); // メッセージサーバーへの登録日時
                    unpacker.skipValue(); // 宛先
                    int nMsgLength = unpacker.unpackBinaryHeader();           // 受信データ長
                    byte[] bufRecvMsgData = unpacker.readPayload(nMsgLength); // 受信データ本文
                    unpacker.unpackArrayHeader();                             // 要素数
                    String strSeqNo = unpacker.unpackString();                // シーケンスNo
                    unpacker.close();

                    // メッセージ構造体への変換
                    MsgData msgRes = new MsgData(m_formatProperties, ComMsgDef.k_strGUI, ComMsgDef.k_strSubRecv,
                            ComMsgDef.k_strGetMessageList);
                    msgRes.convertByteArrayToMsgData(bufRecvMsgData);

                    resultMsg.setResult(MsgResult.k_nResultOK);
                    resultMsg.addMsgDataList(msgRes);

                    // メッセージ受信完了処理
                    MessageBufferPacker packerComp = MessagePack.newDefaultBufferPacker();
                    packerComp.packArrayHeader(5);                          // メッセージデータの要素数
                    packerComp.packInt(1);                                  // 送信元プロセスNo (デバッグ用)
                    packerComp.packArrayHeader(1);                          // 受信者IDリスト数
                    packerComp.packArrayHeader(2);                          // XReceiverPriorityの要素数
                    packerComp.packString(strRecvID);                       // 受信者ID
                    packerComp.packByte((byte) 1);                         // 優先度
//                    packerComp.packByte(k_btMsgReqFlagComp); // 要求区分
                    packerComp.packLong(5000L); // 受信タイムアウト時間(ms) 0の時は無限待ち
                    packerComp.packByte(k_btQueueProcessingStateProcessed); // 処理状態
                    packerComp.packArrayHeader(2);                          // アクセス情報の要素数
                    packerComp.packString(strSeqNo);                        // アクセス情報 - シーケンスNo(処理完了時に使用)
                    packerComp.packString(strRecvID);                       // アクセス情報 - 受信者ID(処理完了時に使用)

                    byte[] bufXMsgComp = getXMsgPackByteArray(packerComp);
                    packerComp.close();

                    // メッセージサイズ
                    outData.writeInt(k_nIntLength + bufXMsgComp.length);
                    // メッセージID送信
                    outData.writeInt(k_nMsgRecvReq);
                    // メッセージ本文送信
                    outData.write(bufXMsgComp);
                    outData.flush();

                    // メッセージサイズ取得
                    int nTotalCompSize = inData.readInt();
                    // メッセージサイズ分データを読み捨て
                    inData.skipBytes(nTotalCompSize);

                } else {
                    // 成功以外
                    unpacker.close();
                    // メッセージの取得に失敗した場合はループを終了
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMsg.setResult(MsgResult.k_nResultNG);
        } finally {
            try {
                if (outData != null) {
                    outData.close();
                }
                if (inData != null) {
                    inData.close();
                }
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultMsg;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージバイト配列取得
     * @par    MsgSvrへ送信するメッセージのバイト配列を返す
     * @param  packer メッセージ(MsgPack)
     * @return メッセージバイト配列
     * @throws IOException
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private byte[] getXMsgPackByteArray(MessageBufferPacker packer) throws IOException {

        // シリアライズ
        // XMessageDataFormat
        MessageBufferPacker msgPacker = MessagePack.newDefaultBufferPacker();
        msgPacker.packArrayHeader(1);
        msgPacker.packRawStringHeader(packer.toByteArray().length);
        msgPacker.addPayload(packer.toByteArray());

        return msgPacker.toByteArray();
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  受信メッセージデータ取得
     * @par    バイト配列から受信メッセージデータを取得する
     * @param  recvMsg 受信メッセージ
     * @return 受信メッセージデータ
     * @throws IOException
     * @note   渡されたバイト配列から受信メッセージが取得できなかった場合は、失敗コードである"1"を返します。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private byte[] getRecvMsgData(byte[] recvMsg) throws IOException {

        // デシリアライズ
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(recvMsg);
        // 結果にはデフォルトでNGを設定する
        byte[] bufResult = { (byte) 0x91, (byte) 0x01 };
        unpacker.unpackArrayHeader();
        int nStringHeader = unpacker.unpackRawStringHeader();
        bufResult = unpacker.readPayload(nStringHeader);

        return bufResult;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  ソケット接続オープン
     * @par    MsgSvrへのソケット接続処理
     * @param  strHost 接続先IPアドレス
     * @param  strPort 接続先ポート
     * @return オープンソケット
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private static synchronized Socket openConnection(String strHost, String strPort) throws Exception {

        Socket connMsgSvr = null;

        try {
            // MsgSvr接続処理
            connMsgSvr = new Socket(strHost, Integer.parseInt(strPort));

            // KEEPALIVEを設定
            connMsgSvr.setKeepAlive(false);

        } catch (Exception e) {
            try {
                if (connMsgSvr != null) {
                    connMsgSvr.close();
                }
            } catch (Exception ex) {
            }
            throw e;
        }
        return connMsgSvr;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  宛先クラス
     * @par    MsgSvrの宛先クラス(XDestination.h)
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private class MsgXDestination {
        // メンバ変数

        // ! 宛先
        private String m_strAddress = "";
        // ! 宛先(区切り文字で分割した配列)
        private String[] m_strItems = { "" };

        //@formatter:off
        /**
         ******************************************************************************
         * @brief  コンストラクタ
         * @param  strAddress 宛先
         * @note
         * ----------------------------------------------------------------------------
         * VER.        DESCRIPTION                                               AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public MsgXDestination(String strAddress) {
            m_strAddress = strAddress;
            m_strItems = m_strAddress.split("\\" + ComMsgDef.k_strSubjectDelimiter);
        }

        //@formatter:off
        /**
         ******************************************************************************
         * @brief  MsgPackバイト配列取得
         * @par    MsgPackによるシリアライズ後のバイト配列を返す
         * @param  なし
         * @return MsgPackシリアライズ後のバイト配列
         * @throws IOException
         * @note
         * ----------------------------------------------------------------------------
         * VER.        DESCRIPTION                                               AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public byte[] getMsgPackByteArray() throws IOException {

            // シリアライズ
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(1);
            packer.packArrayHeader(m_strItems.length); // アイテムの要素数

            // TODO 
            System.out.println("getMsgPackByteArray Size:" + m_strItems.length);
            for (int i = 0; i < m_strItems.length; i++) {
                packer.packString(m_strItems[i]); // 宛先の要素
                System.out.println(m_strItems[i]);
            }
            System.out.println("getMsgPackByteArray End");

            packer.close();

            return packer.toByteArray();
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  宛先文字列設定クラス
     * @par    MsgSvrの宛先文字列設定クラス(XDestinationConfig.h)
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private class MsgXDestinationConfig {
        // メンバ変数

        // ! 区切り文字
        private String m_strSeparator = "";
        // ! ワイルドカード文字
        private String m_strWildCard = "";
        // ! 1項目の最大長
        private byte m_btItemLength = (byte) 0;
        // ! 項目の最大数
        private byte m_btItemMaxCount = (byte) 0;

        //@formatter:off
        /**
         ******************************************************************************
         * @brief  コンストラクタ
         * @par    MACHFrameworkで指定されているデフォルト値を設定
         * @param  なし
         * @note
         * ----------------------------------------------------------------------------
         * VER.        DESCRIPTION                                               AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public MsgXDestinationConfig() {
            m_strSeparator = ".";
            m_strWildCard = "*";
            m_btItemLength = (byte) 32;
            m_btItemMaxCount = (byte) 5;
        }

        //@formatter:off
        /**
         ******************************************************************************
         * @brief  MsgPackバイト配列取得
         * @par    MsgPackによるシリアライズ後のバイト配列を返す
         * @param  なし
         * @return MsgPackシリアライズ後のバイト配列
         * @throws IOException
         * @note
         * ----------------------------------------------------------------------------
         * VER.        DESCRIPTION                                               AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        public byte[] getMsgPackByteArray() throws IOException {

            // シリアライズ
            MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
            packer.packArrayHeader(6);         // データの要素数
            packer.packString(m_strSeparator); // 区切り文字
            packer.packString(m_strWildCard);  // ワイルドカード
            packer.packByte(m_btItemLength);   // 1項目の最大長
            packer.packByte(m_btItemMaxCount); // 項目の最大数
            packer.packArrayHeader(0);         // 宛先の各項目の比較順序(将来的に使用する可能性はあり)
            packer.close();

            return packer.toByteArray();
        }
    }
}
