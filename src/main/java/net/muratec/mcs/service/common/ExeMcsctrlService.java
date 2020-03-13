//@formatter:off
/**
 ******************************************************************************
 * @file        ExeMcsctrlService.java
 * @brief       mcsctrl実行クラス
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
 * 2018/10/01 v1.0.0      初版作成                                          CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.muratec.mcs.common.ComConst;

//@formatter:off
/**
******************************************************************************
* @brief     mcsctrlをキックし、プロセス制御を行うクラス
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
public class ExeMcsctrlService {

    private static final int FIRST = 0;

    // MCS設定定義ファイル
    @Autowired Properties m_mcsProperties;

    // ツール実行スレッド
    private ExeMcsctrlCommand threadExeMcsctrl;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Threadを作成し、外部ツールの実行を行う
     * @param     commandList            実行コマンドリスト
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
    public void exeThread(List<String> commandList) {

        String strMcsctrlPath = m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_MCSCTRL_TOOL_DIR) +
                m_mcsProperties.getProperty(ComConst.MCSConfig.KEY_MCSCTRL_TOOL_FILE);

        commandList.add(FIRST, strMcsctrlPath);

        System.out.println("実行コマンドリスト");
        for (String tmp: commandList) {
            System.out.println(tmp);
        }

        threadExeMcsctrl = new ExeMcsctrlCommand(commandList);
        threadExeMcsctrl.start();
    }

    //@formatter:off
    /**
    ******************************************************************************
    * @brief     mcsctrl実行スレッドクラス
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
    public class ExeMcsctrlCommand extends Thread {

        private List<String> m_commandList = null;

        //@formatter:off
        /**
         ******************************************************************************
         * @brief     コンストラクタ
         * @param     strCommandList        コマンドリスト
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
        public ExeMcsctrlCommand(List<String> strCommandList) {
            this.m_commandList = strCommandList;
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

            try {
                // コマンド実行
                pb = new ProcessBuilder(m_commandList);
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
            } catch (Exception e) {
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
