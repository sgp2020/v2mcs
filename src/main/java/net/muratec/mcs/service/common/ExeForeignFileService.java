//@formatter:off
/**
 ******************************************************************************
 * @file        ExeForeignFileService.java
 * @brief       外部ファイル連携を行う
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.exception.McsException;

//@formatter:off
/**
 ******************************************************************************
 * @brief     exeファイルをキックし、外部ファイルデータを取得するクラス
 * @par       機能:
 *              getCount（ログ一覧の全レコード数を取得）
 *              exeForeignFile（exeファイルをキックし、外部ファイルデータを取得する）
 *              csvDateTimeFormat(日時のフォーマット変換)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class ExeForeignFileService {

    private int listCount = 0;
    
    // MCS設定定義ファイル
    @Autowired Properties m_mcsProperties;

    // ツール実行スレッド
    private ExeToolCommand threadExeTool;

    // ツール実行完了ファイル プレフィックス
    private static final String PREFIX_EXE_END_FILE = "ExeEnd";
    // ファイル読み込みバイトサイズ
    private static final int READ_BYTE_SIZE = 1024;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログ一覧の全レコード数を取得
     * @return    レコード数
     * @retval
     * @attention
     * @note       ログ一覧の全レコード数を取得
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount() {

        return listCount;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeファイルをキックし、外部ファイルデータを取得する
     * @param     source            検索条件
     * @param     command           参照ファイルのパス(暫定)
     * @return    取得ログデータ
     * @retval
     * @attention
     * @note      exeファイルをキックし、出力されたCSVファイルからログデータを取得する
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<String[]> exeForeignFile(Object source, String command) throws McsException {

        // 返却データの宣言
        List<String[]> resDataList = new ArrayList<String[]>();

        try {
            // ------------------------------------
            // TODO exeプログラムキック。他クラス、メソッドに分ける?(仕様が確定していないため、作成途中)
            // ------------------------------------
            // // 外部ファイル参照exeを実装するコマンド
            // String command = "";
            // StringBuilder sb = new StringBuilder("");
            // Field[] req = reqEntity.getClass().getDeclaredFields();
            // for (Field field : req) {
            // try {
            // if (field.get(reqEntity) != null) {
            // sb.append(" " + field.get(reqEntity).toString());
            // } else {
            // continue;
            // }
            // } catch (IllegalArgumentException e) {
            // // TODO 自動生成された catch ブロック
            // e.printStackTrace();
            // } catch (IllegalAccessException e) {
            // // TODO 自動生成された catch ブロック
            // e.printStackTrace();
            // }
            // }
            // command = sb.toString();
            // Runtime runtime = Runtime.getRuntime();
            // runtime.exec(command);

            // ------------------------------------
            // TODO 外部ファイル参照で生成された？パスを設定する。(以下は、ダミー)
            // ------------------------------------
            String file = command;

            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            int count = 0;
            boolean startFlag = true;

            // -------------------------------------
            // CSVファイルから取得したデータの切り出し
            // -------------------------------------
            while ((line = bufferedReader.readLine()) != null) {
                if (startFlag) {
                    // 1行目読み飛ばし処理
                    startFlag = false;
                    continue;
                }

                String[] column = line.split(",");

                // 切り出したレコードを返却データに追加する
                resDataList.add(column);

                count++;
            }

            bufferedReader.close();
            listCount = count;

        } catch (IOException ex) {

            throw new McsException("ファイルの取得に失敗しました。");
        }

        return resDataList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     日時のフォーマット変換(YYYY/MM/DD hh;mm;ss.SSSSSS)
     * @param     dateTime     外部ファイルから切り出した日時を示す文字列
     * @return    変換後の文字列
     * @retval
     * @attention 変換元の各変数の型は、画面からの入力値である「String」を想定
     * @note      日時を表す数列(dateTime)を上記のフォーマットに変換する。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String csvDateTimeFormat(String dateTime) {

        // 形成後日時文字列
        String resDateTime = null;

        String formatString = dateTime;
        if (formatString.length() == 20) {
            String year = formatString.substring(0, 4);
            String month = formatString.substring(4, 6);
            String day = formatString.substring(6, 8);
            String hour = formatString.substring(8, 10);
            String minute = formatString.substring(10, 12);
            String seconds = formatString.substring(12, 14);
            String dateString = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + seconds;
            String milliSecondString = formatString.substring(14);
            resDateTime = dateString + "." + milliSecondString;
        } else {
            resDateTime = "";
        }

        return resDateTime;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Threadを作成し、外部ツールの実行を行う
     * @param     strCommand            実行コマンドリスト
     * @param     strOutputDir          ツール結果出力先
     * @param     strKeyName            リクエストに対するキー情報
     * @return    取得ログデータ
     * @retval
     * @attention
     * @note      Threadを作成し、外部ツールの実行を行う
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void exeThread(String[] strCommandList,String strOutputDir,String strKeyName) {
        threadExeTool = new ExeToolCommand(strCommandList, strOutputDir, strKeyName);
        threadExeTool.start();
    }
    

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     キー情報作成処理
     * @param
     * @return    キー情報
     * @retval
     * @attention
     * @note      UUIDを使用して、ユニークなキー情報を作成する
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String createKeyId() {
        // サーバで受信者IDを自動生成
        String uuid = UUID.randomUUID().toString();
        // UUIDから"-"を削除 32文字に変更
        String strKeyId = uuid.replaceAll("-", "");
        
        return strKeyId;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ツール実行ファイル名リスト作成処理
     * @param
     * @return    ファイル名リスト
     * @retval
     * @attention
     * @note      ツール実行に伴い作成される可能性のある、ファイル名のリストを作成します。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String createExeToolFile(String strOutputDir, String strKeyName) {
        // ツール実行終了ファイル
        String strEndFile = strOutputDir + PREFIX_EXE_END_FILE + strKeyName + m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_RESULT_EXT);
        
        return strEndFile;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ファイル読み込み処理
     * @param     strFilePath          読み込みファイルパス
     * @return    読み込み結果
     * @retval
     * @attention
     * @note      指定されたパスのファイル読み込みを実施する。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String readOutputFile(String strFilePath) {
        
        FileInputStream fis = null;
        StringBuffer sb = new StringBuffer();
        try {
            // ファイルオープン
            fis = new FileInputStream(strFilePath);

            // ファイル読み込み
            byte[] bufData = new byte[READ_BYTE_SIZE];
            ByteArrayOutputStream baos = new ByteArrayOutputStream(READ_BYTE_SIZE);

            int nCurCnt = 0;

            while((nCurCnt = fis.read(bufData, 0, bufData.length)) != -1) {
                baos.write(bufData, 0, nCurCnt);
                Arrays.fill(bufData, (byte)0);
            }
            sb.append(baos.toString());
        } catch (Exception e) {
            // TODO エラー処理
            System.out.println("ファイル読み取り失敗");
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (Exception ex) {
                // TODO エラー処理
                System.out.println("クローズ失敗");
            }
        }
        return sb.toString();
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列分割処理
     * @param     strSrc          対象文字列
     * @return    分割後の文字列配列
     * @retval
     * @attention
     * @note      対象文字列に対して、改行コードで分割を実施する。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ArrayList<String> divideLine(String strSrc) {
        ArrayList<String> retList = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < strSrc.length(); i++) {
            char c = strSrc.charAt(i);
            
            if (c == '\n') {
                retList.add(sb.toString());
                sb = new StringBuffer();
            } else {
                if (c != '\r') {
                    sb.append(c);
                }
            }
        }
        
        if (sb.length() > 0) {
            retList.add(sb.toString());
        }
        
        return retList;
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ログ出力完了確認処理
     * @param     strOutputDir          出力ディレクトリ
     * @param     strKeyName            キー情報
     * @return    ログ出力完了結果
     * @retval
     * @attention
     * @note       ログ取得ツールの出力が完了しているかツール実行完了ファイルの探索を実施する。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean findExeEndFile(String strOutputDir, String strKeyName) throws McsException{
        boolean bRet = false;
        File file = new File(strOutputDir + PREFIX_EXE_END_FILE + strKeyName + m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_RESULT_EXT));
        
        if (file.exists()) {
            bRet = true;
            file.delete();
        }
        return bRet;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ファイル削除処理
     * @return    削除成功可否
     * @retval
     * @attention
     * @note       指定されたファイルの削除を実施する。
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public boolean deleteFile(String strTargetFile) {
        File file = new File(strTargetFile);
        
        return file.delete();
    }

    //@formatter:off
    /**
    ******************************************************************************
    * @brief     外部ツール実行スレッドクラス
    * @par       機能:
    *              exeCommand（指定コマンド実行処理）
    * @attention
    * @note
    * ----------------------------------------------------------------------------
    * DATE       VER.        DESCRIPTION                                    AUTHOR
    * ----------------------------------------------------------------------------
    ******************************************************************************
    */
    //@formatter:on
    public class ExeToolCommand extends Thread {
        
        private String[] m_strCommandList = null;
        private String m_strOutputDir = null;
        private String m_strKeyName = null;
        
        //@formatter:off
        /**
         ******************************************************************************
         * @brief     コンストラクタ
         * @param     strCommandList        コマンドリスト
         * @param     strOutputDir          出力ディレクトリ
         * @param     strKeyName            キー情報
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
        public ExeToolCommand(String[] strCommandList, String strOutputDir, String strKeyName) {
            this.m_strCommandList = strCommandList;
            this.m_strOutputDir = strOutputDir;
            this.m_strKeyName = strKeyName;
        }
        
        //@formatter:off
        /**
         ******************************************************************************
         * @brief     コマンド実行処理
         * @return
         * @retval
         * @attention
         * @note       メンバ変数に指定されているコマンドを実行する。
         * ----------------------------------------------------------------------------
         * DATE       VER.        DESCRIPTION                                    AUTHOR
         * ----------------------------------------------------------------------------
         ******************************************************************************
         */
        //@formatter:on
        private void exeCommand() {
            // TODO デバッグ出力
            System.out.println("Thread実行");
            System.out.println("exeCommand実行開始");
            Process proc = null;
            ProcessBuilder pb = null;
            String line = new String();
            File file = null;
            
            try {
                // コマンド実行
                pb = new ProcessBuilder(m_strCommandList);
                proc = pb.start();
                
                InputStream in = proc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                // デバッグ ツール実行時の標準出力を表示
                System.out.println("Normal Output");
                while((line = br.readLine()) != null) {
                    // 特に処理なし
                    // TODO デバッグ出力
                    System.out.println(line);
                }
                
                // エラー出力の取得
                // TODO Hasからのマージコード 不要？
                InputStream iner = proc.getErrorStream();
                BufferedReader brer = new BufferedReader(new InputStreamReader(iner));
                System.out.println("Error Output");
                while ((line = brer.readLine()) != null) {
                    // 特に処理なし
                    System.out.println(line);
                }
                
                // 外部ツール実行完了報告用ファイル作成
                file = new File(m_strOutputDir + PREFIX_EXE_END_FILE + m_strKeyName + m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_RESULT_EXT));
                FileWriter filewriter = new FileWriter(file);
                filewriter.close();
            } catch (Exception e) {
                // TODO ツール実行エラー時の動作
//                try {
//                    // ツール実行エラー発生通知用のファイルを作成
//                    File errorFile = new File(m_strOutputDir + PREFIX_EXE_ERR_FILE + m_strKeyName + m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_RESULT_EXT));
//                    FileWriter filewriter = new FileWriter(errorFile);
//                    filewriter.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
                System.out.println("exeCommand Exception");
                e.printStackTrace();
            }
            // TODO デバッグ出力
            System.out.println("exeCommand実行終了");
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
            // コマンド実行処理
            exeCommand();
        }
    }
}
