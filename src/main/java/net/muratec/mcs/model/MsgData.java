//@formatter:off
/**
 ******************************************************************************
 * @file        MsgData.java
 * @brief       メッセージデータクラス
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
package net.muratec.mcs.model;

import java.util.Properties;

import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.common.ComMsgUtil;

//@formatter:off
/**
 ******************************************************************************
 * @brief  メッセージデータクラス
 * @par    制御プロセスとのメッセージデータを格納
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
public class MsgData {

    // 定数
    // ! データ型:Byte
    private static final String k_strTypeByte = "Byte";
    // ! データ型:Short
    private static final String k_strTypeShort = "Short";
    // ! データ型:Integer
    private static final String k_strTypeInteger = "Integer";
    // ! データ型:Long
    private static final String k_strTypeLong = "Long";
    // ! データ型:String
    private static final String k_strTypeString = "String";
    // ! データ型:List
    private static final String k_strTypeList = "List";

    // ! メッセージ構造体インデックス：キー値
    private static final int k_nIndexKey = 0;
    // ! メッセージ構造体インデックス：型
    private static final int k_nIndexType = 1;
    // ! メッセージ構造体インデックス:データサイズ
    private static final int k_nIndexSize = 2;
    // ! メッセージ構造体インデックス:実データ
    private static final int k_nIndexValue = 3;
    // ! メッセージ構造体サイズ
    private static final int k_nMsgSize = 4;

    // メンバ変数

    // ! メッセージ構造体保持配列
    private Object[][] m_aryMsgStruct = null;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  コンストラクタ
     * @par    制御プロセスへ送信するメッセージ構造体を作成
     * @param  formatProperties フォーマットプロパティ
     * @param  strProcName プロセス名
     * @param  strDeliveryType 送信タイプ
     * @param  strEventName　イベント名
     * @note   メッセージ構造体作成初期化処理を実施し、メッセージ構造体を作成
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public MsgData(Properties formatProperties, String strProcName, String strDeliveryType, String strEventName) {
        // 共通ヘッダ情報取得
        String[] strHeaderArray = ComMsgUtil.splitByDelim(formatProperties.getProperty(ComMsgDef.k_strHeader),
                ComMsgDef.k_strFromatLineDelimiter);
        // プロセス固有ヘッダ取得
        String[] strProcHeaderArray = ComMsgUtil.splitByDelim(
                formatProperties.getProperty(ComMsgDef.k_strHeader + "." + strProcName),
                ComMsgDef.k_strFromatLineDelimiter);
        // メッセージデータ配列取得
        String[] strDataArray = ComMsgUtil.splitByDelim(formatProperties.getProperty(strDeliveryType + strEventName),
                ComMsgDef.k_strFromatLineDelimiter);

        // メッセージ配列作成
        String[] strMsgStruct = new String[strHeaderArray.length + strProcHeaderArray.length + strDataArray.length];
        System.arraycopy(strHeaderArray, 0, strMsgStruct, 0, strHeaderArray.length);
        System.arraycopy(strProcHeaderArray, 0, strMsgStruct, strHeaderArray.length, strProcHeaderArray.length);
        System.arraycopy(strDataArray, 0, strMsgStruct, strHeaderArray.length + strProcHeaderArray.length,
                strDataArray.length);

        init(strMsgStruct);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ格納(キー)
     * @par    メッセージ構造体へデータを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へデータを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void setVal(String strKeyName, Object data) {

        if (m_aryMsgStruct != null) {
            for (int i = 0; i < m_aryMsgStruct.length; i++) {
                if (strKeyName.equals(m_aryMsgStruct[i][k_nIndexKey])) {
                    // 指定されたキーと一致するデータを更新
                    m_aryMsgStruct[i][k_nIndexValue] = data;
                    break;
                }
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ格納(インデックス)
     * @par    メッセージ構造体へデータを格納
     * @param  nIndex インデックス
     * @param  data データ
     * @return 戻り値なし
     * @note   インデックスを元に、メッセージ構造体へデータを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setVal(int nIndex, Object data) {

        if (m_aryMsgStruct != null) {
            m_aryMsgStruct[nIndex][k_nIndexValue] = data;
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  byte型データ格納
     * @par    メッセージ構造体へbyte型データを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へbyte型データを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setValByte(String strKeyName, byte data) {

        this.setVal(strKeyName, new Byte(data));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  short型データ格納
     * @par    メッセージ構造体へshort型データを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へshort型データを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setValShort(String strKeyName, short data) {

        this.setVal(strKeyName, new Short(data));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  int型データ格納
     * @par    メッセージ構造体へint型データを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へint型データを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setValInteger(String strKeyName, int data) {

        this.setVal(strKeyName, new Integer(data));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  long型データ格納
     * @par    メッセージ構造体へlong型データを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へlong型データを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setValLong(String strKeyName, long data) {

        this.setVal(strKeyName, new Long(data));
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  String型データ格納
     * @par    メッセージ構造体へString型データを格納
     * @param  strKeyName キー
     * @param  data データ
     * @return 戻り値なし
     * @note   キーを元に、メッセージ構造体へString型データを格納
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setValString(String strKeyName, String data) {

        if (data != null) {
            this.setVal(strKeyName, data);
        } else {
            this.setVal(strKeyName, new String());
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ取得(キー)
     * @par    メッセージ構造体からデータを取得
     * @param  strKeyName キー
     * @param  data データ
     * @return 格納データ
     * @note   キーを元に、メッセージ構造体からデータを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Object getVal(String strKeyName) {

        for (int i = 0; i < m_aryMsgStruct.length; i++) {
            if (strKeyName.equals(m_aryMsgStruct[i][k_nIndexKey])) {
                Object value = m_aryMsgStruct[i][k_nIndexValue];
                if (value instanceof String) {
                    value = ((String) value).trim();
                }
                return value;
            }
        }

        return null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ取得(キー)
     * @par    メッセージ構造体からデータを取得
     * @param  strKeyName キー
     * @param  data データ
     * @return 格納データ
     * @note   キーを元に、メッセージ構造体からデータを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public int getIndex(String strKeyName) {

        for (int i = 0; i < m_aryMsgStruct.length; i++) {
            if (strKeyName.equals(m_aryMsgStruct[i][k_nIndexKey])) {
                return i;
            }
        }

        return -1;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ取得(キー)
     * @par    メッセージ構造体からデータを取得
     * @param  strKeyName キー
     * @param  data データ
     * @return 格納データ
     * @note   キーを元に、メッセージ構造体からデータを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public int getSize(String strKeyName) {

        for (int i = 0; i < m_aryMsgStruct.length; i++) {
            if (strKeyName.equals(m_aryMsgStruct[i][k_nIndexKey])) {
                return Integer.parseInt((String) m_aryMsgStruct[i][k_nIndexSize]);
            }
        }

        return -1;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ取得(インデックス)
     * @par    メッセージ構造体からデータを取得
     * @param  nIndex インデックス
     * @param  data データ
     * @return 格納データ
     * @note   インデックスを元に、メッセージ構造体からデータを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public Object getVal(int nIndex) {

        if (nIndex > 0 && nIndex < m_aryMsgStruct.length) {
            Object value = m_aryMsgStruct[nIndex][k_nIndexValue];
            if (value instanceof String) {
                value = ((String) value).trim();
            }
            return value;
        }

        return null;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  データ構造体一部削除
     * @par        キー情報を元に、該当するデータ構造以外の構造体を作成
     * @param  strKeyName
     * @return 戻り値なし
     * @note   キー情報を元に、該当するデータ構造以外の構造体を作成
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void delMsgStruct(String strKeyName) {

        Object[][] objMsgStruct = new Object[m_aryMsgStruct.length - 1][k_nMsgSize];

        int nCopyIndex = 0;

        for (int i = 0; i < m_aryMsgStruct.length || nCopyIndex < m_aryMsgStruct.length - 1; i++) {
            if (!strKeyName.equals(m_aryMsgStruct[i][k_nIndexKey])) {
                objMsgStruct[nCopyIndex] = m_aryMsgStruct[i].clone();
                nCopyIndex++;
            }
        }

        m_aryMsgStruct = objMsgStruct;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  バイト配列取得
     * @par    メッセージ構造体からバイト配列を取得
     * @param  なし
     * @return バイト配列
     * @note   メッセージ構造体からバイト配列を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public byte[] getByteArray() {

        byte[] btTemp = new byte[20000];
        byte[] btArray = null;
        int nIndex = 0;

        for (int i = 0; i < m_aryMsgStruct.length; i++) {
//			System.out.println(m_aryMsgStruct[i][k_nIndexKey] + " : " + m_aryMsgStruct[i][k_nIndexValue]);
            System.out.println(m_aryMsgStruct[i][k_nIndexKey] + " : " + m_aryMsgStruct[i][k_nIndexType]  + "(" + m_aryMsgStruct[i][k_nIndexSize] + ")"  + " : " + m_aryMsgStruct[i][k_nIndexValue]);
        }

        try {
            for (int i = 0; i < m_aryMsgStruct.length; i++) {
                String strType = (String) m_aryMsgStruct[i][k_nIndexType];
                int nLength = Integer.parseInt((String) m_aryMsgStruct[i][k_nIndexSize]);

                switch (strType) {
                    case k_strTypeLong:
                        long lVal;

                        // long型へデータを変換
                        if (m_aryMsgStruct[i][k_nIndexValue] instanceof Long) {
                            lVal = ((Long) m_aryMsgStruct[i][k_nIndexValue]).longValue();
                        } else {
                            lVal = (Long.valueOf((String) m_aryMsgStruct[i][k_nIndexValue])).longValue();
                        }

                        // バイト配列のコピー
                        System.arraycopy(ComMsgUtil.convertLongToByteArray(lVal), 0, btTemp, nIndex, nLength);

                        break;

                    case k_strTypeInteger:
                        int nVal;

                        // int型へデータを変換
                        if (m_aryMsgStruct[i][k_nIndexValue] instanceof Integer) {
                            nVal = ((Integer) m_aryMsgStruct[i][k_nIndexValue]).intValue();
                        } else {
                            nVal = (Integer.valueOf((String) m_aryMsgStruct[i][k_nIndexValue])).intValue();
                        }

                        // バイト配列のコピー
                        System.arraycopy(ComMsgUtil.convertIntToByteArray(nVal), 0, btTemp, nIndex, nLength);

                        break;

                    case k_strTypeShort:
                        short sVal;

                        // short型へデータを変換
                        if (m_aryMsgStruct[i][k_nIndexValue] instanceof Short) {
                            sVal = ((Short) m_aryMsgStruct[i][k_nIndexValue]).shortValue();
                        } else {
                            sVal = (Short.valueOf((String) m_aryMsgStruct[i][k_nIndexValue])).shortValue();
                        }

                        // バイト配列のコピー
                        System.arraycopy(ComMsgUtil.convertShortToByteArray(sVal), 0, btTemp, nIndex, nLength);

                        break;

                    case k_strTypeByte:
                        byte[] btVal = new byte[1];

                        if (m_aryMsgStruct[i][k_nIndexValue] instanceof Byte) {
                            btVal[0] = ((Byte) m_aryMsgStruct[i][k_nIndexValue]).byteValue();
                        } else {
                            btVal[0] = (Byte.valueOf((String) m_aryMsgStruct[i][k_nIndexValue])).byteValue();
                        }

                        // バイト配列のコピー
                        System.arraycopy(btVal, 0, btTemp, nIndex, nLength);

                        break;

                    case k_strTypeString:
                        String strVal = (String) m_aryMsgStruct[i][k_nIndexValue];

                        // フォーマットに指定されている文字数以上の内容は切り捨て
                        if (strVal.length() < nLength) {
                            System.arraycopy(strVal.getBytes(), 0, btTemp, nIndex, strVal.length());
                        } else {
                            // 最終バイトは改行コードのため
                            System.arraycopy(strVal.getBytes(), 0, btTemp, nIndex, nLength - 1);
                        }

                        break;

                    case k_strTypeList:
                        nLength = 0;
                        break;

                    default:
                        break;
                }

                nIndex += nLength;
            }

            btArray = new byte[nIndex];
            System.arraycopy(btTemp, 0, btArray, 0, nIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return btArray;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ構造体変換処理
     * @par    バイト配列からメッセージ構造体への変換を実施
     * @param  data データ
     * @return 戻り値なし
     * @note   バイト配列からメッセージ構造体への変換を実施
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void convertByteArrayToMsgData(byte[] data) {

        try {
            int nIndex = 0;
            for (int i = 0; i < m_aryMsgStruct.length; i++) {
                String strType = (String) m_aryMsgStruct[i][k_nIndexType];
                int nLength = Integer.parseInt((String) m_aryMsgStruct[i][k_nIndexSize]);
                byte[] buff = new byte[nLength];

                switch (strType) {
                    case k_strTypeLong:
                        System.arraycopy(data, nIndex, buff, 0, nLength);
                        m_aryMsgStruct[i][k_nIndexValue] = new Long(ComMsgUtil.convertByteArrayToLong(buff));
                        break;

                    case k_strTypeInteger:
                        System.arraycopy(data, nIndex, buff, 0, nLength);
                        m_aryMsgStruct[i][k_nIndexValue] = new Integer(ComMsgUtil.convertByteArrayToInt(buff));
                        break;

                    case k_strTypeShort:
                        System.arraycopy(data, nIndex, buff, 0, nLength);
                        m_aryMsgStruct[i][k_nIndexValue] = new Short(ComMsgUtil.convertByteArrayToShort(buff));
                        break;

                    case k_strTypeByte:
                        System.arraycopy(data, nIndex, buff, 0, nLength);
                        m_aryMsgStruct[i][k_nIndexValue] = new Byte(buff[0]);
                        break;

                    case k_strTypeString:
                        System.arraycopy(data, nIndex, buff, 0, nLength);
                        m_aryMsgStruct[i][k_nIndexValue] = new String(buff).trim();
                        break;

                    case k_strTypeList:
                        nLength = 0;
                        break;

                    default:
                        break;
                }
                nIndex += nLength;

                // TODO デバッグ 取得データ表示
                System.out.println(m_aryMsgStruct[i][k_nIndexKey] + " : " + m_aryMsgStruct[i][k_nIndexValue]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ構造体初期化
     * @par    メッセージ構造体の初期化処理を実施
     * @param  strArray メッセージ構造体ひな形
     * @return 戻り値なし
     * @note   渡されたString配列からメッセージ構造体を作成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private void init(String[] strArray) {

        m_aryMsgStruct = new Object[strArray.length][k_nMsgSize];

        for (int i = 0; i < m_aryMsgStruct.length; i++) {
            String[] params = ComMsgUtil.splitByDelim(strArray[i], ",");

            // 各パラメータの値を設定する
            m_aryMsgStruct[i][k_nIndexKey] = params[k_nIndexKey]; // キー
            m_aryMsgStruct[i][k_nIndexType] = params[k_nIndexType]; // 型
            m_aryMsgStruct[i][k_nIndexSize] = params[k_nIndexSize]; // データ長
            // 初期値設定
            if (k_strTypeString.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = "";
            } else if (k_strTypeLong.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = new Long(0);
            } else if (k_strTypeInteger.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = new Integer(0);
            } else if (k_strTypeShort.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = new Short((short) 0);
            } else if (k_strTypeByte.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = new Byte((byte) 0);
            } else if (k_strTypeList.equals(m_aryMsgStruct[i][k_nIndexType])) {
                m_aryMsgStruct[i][k_nIndexValue] = new Integer(0);
            }
        }
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief  メッセージ構造体の更新
     * @par    メッセージ構造体の初期化処理を実施
     * @param  nArrayNum メッセージのリスト数
     * @param  nItemNum リスト化されるアイテム数
     * @param  nListPos Listタイプが設定されている配列のポジション
     * @return 戻り値なし
     * @note   渡されたString配列からメッセージ構造体を作成する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void updateList(int nArrayNum, int nItemNum, int nListPos) {

        try {

            // 元となる配列が存在しない、または、配列数が0以下の場合はメッセージ構造体の更新は行わない
            if (m_aryMsgStruct != null && nArrayNum > 0) {
                Object[][] objMsgStruct = new Object[m_aryMsgStruct.length + (nArrayNum - 1) * nItemNum][k_nMsgSize];

                // 先頭からリストポジションまでの値をコピー
                System.arraycopy(m_aryMsgStruct, 0, objMsgStruct, 0, nListPos + 1);

                int nOrgIndex = nListPos + 1;
                int nCopyIndex = nListPos + 1;
                // 配列の数 * アイテム数分コピー
                for (int i = 0; i < nArrayNum; i++) {
                    for (int j = 0; j < nItemNum; j++) {
                        // 配列のディープコピー
                        objMsgStruct[nCopyIndex + j] = m_aryMsgStruct[nOrgIndex + j].clone();
                        // キーを一意にするため、リスト番号を付与
                        objMsgStruct[nCopyIndex + j][k_nIndexKey] = (String) m_aryMsgStruct[nOrgIndex + j][k_nIndexKey]
                                + String.valueOf(i + 1);
                    }

                    nCopyIndex += nItemNum;
                }
                nOrgIndex += nItemNum;

                // 残りのリストをコピー
                System.arraycopy(m_aryMsgStruct, nOrgIndex, objMsgStruct, nCopyIndex,
                        m_aryMsgStruct.length - nOrgIndex);

                m_aryMsgStruct = objMsgStruct;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
