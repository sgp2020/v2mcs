//@formatter:off
/**
 ******************************************************************************
 * @file        ComConst.java
 * @brief       各機能で利用する定数群
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
 * 2018/10/03 MACS4#0001  AlarmSystem対応(STEP2) 登録画面、ログ             CSC
 * 2018/10/25 MACS4#0016  GUI客先テストNG項目対応                     T.Iga/CSC
 * 2018/10/29 MACS4#0027  AlarmSystem対応(STEP2) 登録画面、ログ（改訂分）   CSC
 * 2018/11/21 MACS4#0047  GUI要望分                                   T.Iga/CSC
 * 2018/11/27 MACS4#0049  StageCmd対応(GUI)                           T.Iga/CSC
 * 2018/11/30 MACS4#0059  M17対応                                     T.Iga/CSC
 * 2018/12/18 MACS4#0073  Exercise画面対応                            T.Iga/CSC
 * 2019/01/08 MACS4#0084  GUI iFoup関連データ自動登録対応             T.Iga/CSC
 * 2019/03/13 MACS4#0114  GUI MCSAlarmクリア対応                      T.Iga/CSC
 * 2019/03/14 MACS4#0119  GUI Controller Type修正                     T.Iga/CSC
 * 2019/07/08 MACS4#0199  通信ログ画面表示 不具合対応                 T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.common;

import java.util.Arrays;
import java.util.List;

//@formatter:off
/**
 ******************************************************************************
 * @brief     各機能で利用する定数群を記載するクラス
 * @par       機能:
 *              各定数群
 * @attention 定数名は、Javaの一般的な規約（大文字で単語間を_で結ぶ）に則る
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0001  AlarmSystem対応(STEP2) 登録画面、ログ                        CSC
 * MACS4#0016  GUI客先テストNG項目対応                                T.Iga/CSC
 * MACS4#0027  AlarmSystem対応(STEP2) 登録画面、ログ（改訂分）              CSC
 * MACS4#0047  GUI要望分                                              T.Iga/CSC
 * MACS4#0049  StageCmd対応(GUI)                                      T.Iga/CSC
 * MACS4#0059  M17対応                                                T.Iga/CSC
 * MACS4#0073  Exercise画面対応                                       T.Iga/CSC
 * MACS4#0084  GUI iFoup関連データ自動登録対応                        T.Iga/CSC
 * MACS4#0114  GUI MCSAlarmクリア対応                                 T.Iga/CSC
 * MACS4#0119  GUI Controller Type修正                                T.Iga/CSC
 * MACS4#0199  通信ログ画面表示 不具合対応                            T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
public class ComConst {

    /**
     * バージョン情報
     */
    static public final String VAR_NAME = "version";
    static public final String VARSION = "1.0.5.00_1.0.5.00";

    /**
     * アクセスキー
     */
    static public final String API_ACCESS_KEY = "AccessKey";

    /**
     * 日付変換用フォーマット（ミリ秒あり）
     */
    static public final String DATE_TIME_FORMAT_FF3 = "yyyyMMddHHmmssSSS";

    /**
     * 日付変換用フォーマット（ミリ秒なし）
     */
    static public final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    /**
     * 日付変換用フォーマット(マイクロ秒あり)
     */
    static public final String DATE_TIME_FORMAT_FF6 = "yyyyMMddHHmmssSSSSSS";

    /**
     * 日付変換用フォーマット（ミリ秒なしセパレータあり）
     */
    static public final String DATE_TIME_FORMAT_SEPARATOR = "yyyy/MM/dd HH:mm:ss";

    /**
     * 日付変換用フォーマット（タイムスタンプ）
     */
    // static public final String TIMESTAMP_FORMAT = "yyyy/MM/dd HH:mm:ss.SS";
    static public final String TIMESTAMP_FORMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 日付変換用フォーマット（Decimal） - MACS4#0114 Add
     */
    static public final String DECIMAL_TIMESTAMP_FORMAT ="00000000000000.000000";

    /**
     * HSMSコネクトモード
     */
    static public final List<String> HSMS_MODE = Arrays.asList("passive", "active");

    /**
     * AMHSモデル
     */
    static public final List<String> AMHS_MODEL = Arrays.asList("IBSEM", "STKSEM");

    /**
     * 搬送指令送信用Host名
     */
    static public final String HOST_NAME = "MCS";

    /**
     * MCS名称
     */
    static public final String MCS_NAME = "MCS";

    /**
     * ロケーション選択 ストッカのポートセレクトボックス要素
     */
//  static public final String SELECT_STOCKER_STORAGE_VALUE = "STORAGE";    // MACS4#0047 Del
//  static public final String SELECT_STOCKER_STORAGE_TEXT = "STORAGE";     // MACS4#0047 Del
    static public final String SELECT_STOCKER_SHELF_VALUE = "SHELF";        // MACS4#0047 Add
    static public final String SELECT_STOCKER_SHELF_TEXT = "SHELF";         // MACS4#0047 Add

    /**
     * 改行コード
     */
    static public final String BR = "\n";

    /**
     * 改行コード(テキストエリア用)
     */
    static public final String CRLF = "\r\n";

    /**
     * 文字コード
     */
    static public final String CHAR_SET = "utf-8";

    /**
     * 空白
     */
    static public final String BLANK = " ";

    /**
     * SQLのエスケープ文字
     */
    static public final String SQL_ESCAPE = "\\";

    /**
     * エンティティクラスのパッケージ（操作ログ出力判定用）
     */
    static public final String ENTITY_PACKAGE = "net.muratec.mcs.entity";

    /**
     * java.langのパッケージ
     */
    static public final String JAVA_LANG_PACKAGE = "java.lang";

    /**
     * パスワードのハッシュ化ストレッチ数
     */
    static public final int STRETCH_COUNT = 9;

    /**
     * チケットのハッシュ化ストレッチ数
     */
    static public final int TICKET_STRETCH_COUNT = 3;

    /**
     * 操作ログ出力時のマスク文字列
     */
    static public final String MASK_STRING = "*****";

    /**
     * 操作ログ出力時のメッセージファイル未設定文字列
     */
    static public final String FIELD_NAME_KEY_NONE = "";

    /**
     * 操作ログ(テキスト)の最大文字列長
     */
    static public final int OPE_LOG_MAX_LEN = 256;
    /**
     * レコード情報の文字列長："[xx/xx] "
     */
    static public final int RECORD_INFO_LEN = 8;
    /**
     * パラメータ部カッコの文字列長："[]"
     */
    static public final int PARAM_PAREN_LEN = 2;

    /**
     * 操作ログのパラメータ部なし
     */
    static public final String OPE_LOG_ERROR = "OperationLog Format Error(No Parameter section).";

    /**
     * CSVヘッダ：タイトル
     */
    static public final String CSV_TITLE = "# Save Title";

    /**
     * CSVヘッダ：出力日
     */
    static public final String CSV_DATE = "# Save Date";

    /**
     * CSVヘッダ：検索条件
     */
    static public final String CSV_SEARCH_COND = "# Search Condition";

    /**
     * CSV区切り文字
     */
    static public final String CSV_SEPARATOR = ",";

    /**
     * ユーザアカウントテーブル名
     */
    static public final String USER_ACCOUNT_TABLE = "USER_ACCOUNT";

    /**
     * GUIグループテーブル名
     */
    static public final String GUI_FUNC_GROUP_TABLE = "GUI_FUNC_GROUP";

    /**
     * GUIグループRLTテーブル名
     */
    static public final String GUI_FUNC_GROUP_RLT_TABLE = "GUI_FUNC_GROUP_RLT";

    // Step4P2 2017_11_24：リレーショナルテーブルの区切り文字追加
    /**
     * リレーショナルテーブルの一覧表示区切り文字
     */
    static public final String RLT_LIST_SEPARATOR = ",";

    /**
     * リレーショナルテーブルのCSV出力区切り文字
     */
    static public final String RLT_CSV_SEPARATOR = ",'";

    /**
     * 棚 判定用文字列
     */
    static public final String STR_SHELF = "SHELF";
    static public final String STR_STORAGE = "STORAGE";		// MACS4#0199 Add

    /**
     * Ajaxの成功/失敗の文言を返す
     */
    public class AjaxStatus {

        /**
         * 処理成功
         */
        static public final String SUCCESS = "success";

        /**
         * 処理失敗
         */
        static public final String ERROR = "error";
    }

    /**
     * カラムの表示/非表示を返す
     */
    public class ColumnDisplay {

        /**
         * 表示
         */
        static public final int DISP = 1;

        /**
         * 非表示
         */
        static public final int NONE = 0;

        /**
         * 隠しカラム
         */
        static public final int HIDDEN = 2;
    }

    /**
     * カラムの文字寄せを返す
     */
    public class ColumnAlign {

        /**
         * 左寄せ
         */
        static public final String LEFT = "left";

        /**
         * 中央寄せ
         */
        static public final String CENTER = "center";

        /**
         * 右寄せ
         */
        static public final String RIGHT = "right";
    }

    /**
     * 行の文字色
     */
    public class RowColor {

        /**
         * デフォルトの色
         */
        static public final String DEFAULT = "";
    }

    /**
     *
     * キャリアJob統計表示間隔
     *
     */
    public class CarrierJobDisplayInterval {

        /**
         * 日ごとに集計
         */
        static public final int ONE_DAY = 0;

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 1;

    }

    /**
     *
     * HOST通信応答時間統計表示間隔
     *
     */
    public class HostCommunicationResTimeInterval {

        /**
         * 10分ごとに集計
         */
        static public final int TEN_MINUTES = 1;

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 2;

        /**
         * 60分ごとに集計
         */
        static public final int SIXTY_MINUTES = 3;

        /**
         * 1日ごとに集計
         */
        static public final int ONE_DAY = 4;

    }

    /**
     *
     * 平均棚占有率統計表示間隔
     *
     */
    public class AverageBinUtilizationDisplayInterval {

        /**
         * 日ごとに集計
         */
        static public final int ONE_DAY = 0;

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 1;

    }

    /**
     *
     * 平均キャリア数統計表示間隔
     *
     */
    public class AverageNumberCarrierInterval {

        /**
         * 日ごとに集計
         */
        static public final int ONE_DAY = 0;

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 1;

    }

    /**
     *
     * 搬送回数(Micro)統計表示間隔
     *
     */

    public class TransferTimesMicroInterval {

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 1;

        /**
         * 1日ごとに集計
         */
        static public final int ONE_DAY = 2;

    }

    /**
     *
     * 機器平均稼働率統計表示間隔
     *
     */
    public class AverageUnitUtilizationInterval {

        /**
         * 30分ごとに集計
         */
        static public final int THIRTY_MINUTES = 1;

        /**
         * 1日ごとに集計
         */
        static public final int ONE_DAY = 2;

    }

    /**
     * セレクトボックスの全選択要素
     *
     */
    public class StringSelectboxAll {

        /**
         * value
         */
        static public final String VALUE = "";

        /**
         * text
         */
        static public final String TEXT = "All";
    }

    /**
     * valueがString型のセレクトボックスの全選択要素(表示文字:ALL)
     *
     */
    public static class StringSelectBoxTextALL {
        
        /**
         * value
         */
        static public final String VALUE = "ALL";

        /**
         * text
         */
        static public final String TEXT = "ALL";
        
        static public final String[] SELECT_DATA = {VALUE, TEXT};
    }

    /**
     * valueがShort型のセレクトボックスの全選択要素(表示文字:ALL)
     * ※登録データはString
     *
     */
    public static class StringSelectBoxShortALL {

        /**
         * value
         */
        static public final String VALUE = "-1";
        static public final short VALUE_CODE = (short) -1;

        /**
         * text
         */
        static public final String TEXT = "ALL";

        static public final String[] SELECT_DATA = {VALUE, TEXT};
    }
    /**
     * booleanとint関連付け
     *
     */
    public class BooleanInt {

        /**
         * true
         */
        static public final int TRUE = 1;

        /**
         * false
         */
        static public final int FALSE = 0;
    }

    /**
     * 固定ユーザID
     */
    public class ConstUserId {

        /**
         * 未ログインユーザ
         */
        static public final String NOLOGIN = "NOLOGIN";

        /**
         * 管理者ユーザ１
         */
        static public final String ADMIN = "admin";

        /**
         * 管理者ユーザ２
         */
        static public final String ADMINISTRATOR = "Administrator";

    }

    /**
     * 固定グループID
     */
    public class ConstGroupId {

        /**
         * 未ログイングループ
         */
        static public final String NOLOGIN = "NOLOGIN";

        /**
         * 管理者グループ
         */
        static public final String ADMIN = "ADMIN";
    }

    /**
     * セッションに保存するキー
     */
    public class SessionKey {

        /**
         * ログインユーザ情報
         */
        static public final String LOGIN_USER_INFO = "LoginUserInfo";

        /**
         * クライアントアドレス
         */
        static public final String REMOTE_ADDRESS = "RemoteAddress";

        /**
         * CSV出力条件（画面ID＋本項目でセッションに保存する）
         */
        static public final String CSV_INFO = "_CsvInfo";

        /**
         * Logデータ(実データ)
         */
        static public final String LOG_DATA = "_LogData";

        /**
         * Keyデータ
         */
        static public final String KEY_DATA = "_KeyData";

        /**
         * AmhsLogファイル出力先
         */
        static public final String AMHS_LOG_FILE_PATH = "AmhsLogFilePath";

        /**
         * HostLogファイル出力先
         */
        static public final String HOST_LOG_FILE_PATH = "HostLogFilePath";

        /**
         * キャリアシェイプ リレーション一覧CSV保存
         */
        static public final String CARRIER_SHAPE_RLT_CSV = "-RltList";
    }

    /**
     * ログ種別（ログコード上１桁部分）1xxxxxxxxx
     */
    public class LogType {

        /**
         * 操作ログ
         */
        static public final long OPERATION_LOG = 1000000000L;

    }

    /**
     * 画面情報ENUM(画面ID+ログコード)下９桁 x1122xxxxx 参照権限、変更権限のファンクションID
     *
     */
    static public enum ScreenInfo {
        /**
         * システム_ログイン
         */
        SYS_LOGIN("SysLogin", 900100000L, "", ""),

        /**
         * システム_ログアウト
         */
        SYS_LOGOUT("SysLogout", 900200000L, "", ""),

        /**
         * システム_権限
         */
        SYS_AUTH("SysAuth", 900300000L, "", ""),

        /**
         * DataTables変更
         */
        SYS_DATA_TABLES_MOD("SysDTMod", 900400000L, "", ""),

        /**
         * ModifyTable変更
         */
        SYS_MODIFY_TABLE_MOD("SysMTMod", 900500000L, "", ""),

        /**
         * システムモニタ
         */
        TOP_SYSTEMMONITOR("T001", 10100000L, "T001_REF", ""),

        /**
         * アラーム - MACS4#0047 Mod "" → I001_CHG
         */
        INFO_ALARM("I001", 20100000L, "I001_REF", "I001_CHG"),

        /**
         * キャリア
         */
        INFO_CARRIER("I002", 20200000L, "I002_REF", "I002_CHG"),

        /**
         * 搬送Job
         */
        //INFO_TRANSFERJOB("I003", 20300000L, "I003_REF", "I003_CHG"),
        
        /**
         * OHB情報
         */
        INFO_OHB("I003", 20300000L, "I003_REF", "I003_CHG"),
        
        /**
         * ステージ情報 - MACS4#0049 Add
         */
        INFO_STAGEINFO("I004", 20400000L, "I004_REF", "I004_CHG"),

        /**
         * STOCKER情報 - 2020.03.10 DONG ADD
         */
        INFO_STOCKERINFO("I005", 20500000L, "I005_REF", "I005_CHG"),
        
        
        PROCESS_MONITOR("I006", 20600000L, "I006_REF", "I006_CHG"),
        
        TEST_CARRIER_LIST("I007", 20700000L, "I007_REF", "I007_CHG"),
        
        /**
         * Route情報
         */
        INFO_ROUTE("I008", 20800000L, "I008_REF", "I008_CHG"),
        
        /**
         * HOSTCOMM情報 - 2020.03.18 DONG ADD
         */
        INFO_HOSTCOMMINFO("I009", 20900000L, "I009_REF", "I009_CHG"),
        
        
        /**
         * 搬送Job来歴
         */
        HIST_TRANSFERJOBHISTORY("H001", 30100000L, "H001_REF", ""),

        /**
         * アラーム来歴
         */
        HIST_ALARMHISTORY("H002", 30200000L, "H002_REF", ""),

        /**
         * 在籍時間来歴
         */
        HIST_STORAGETIMEHISTORY("H003", 30300000L, "H003_REF", ""),

        /**
         * キャリア削除来歴
         */
        HIST_CARRIERREMOVEHISTORY("H004", 30400000L, "H004_REF", ""),

        /**
         * キャリア監視来歴表示
         */
        HIST_CARRIERMOTIONTIMEHISTORY("H005", 30500000L, "H005_REF", ""),

        /**
         * アラーム報告来歴表示
         */
        HIST_ALARMREPORTHISTORY("H006", 30600000L, "H006_REF", ""),

        /**
         * アラームシステム来歴表示
         */
        HIST_ALARMSYSTEMHISTORY("H007", 30700000L, "H007_REF", ""),

        /**
         * ステージ来歴表示 - MACS4#0049 Add
         */
        HIST_STAGEHISTORY("H008", 30800000L, "H008_REF", ""),

        /**
         * システムログ
         */
        LOG_SYSTEMLOG("L001", 40100000L, "L001_REF", ""),

        /**
         * HOST通信ログ
         */
        LOG_HOSTCOMMUNICATIONLOG("L002", 40200000L, "L002_REF", ""),

        /**
         * AMHS通信ログ
         */
        LOG_AMHSCOMMUNICATIONLOG("L003", 40300000L, "L003_REF", ""),

        /**
         * 搬送ログ
         */
        LOG_TRANSFERLOG("L004", 40400000L, "L004_REF", ""),

        /**
         * 搬送障害ログ
         */
        LOG_TRANSFERDISTURBLOG("L005", 40500000L, "L005_REF", ""),

        /**
         * 操作ログ
         */
        LOG_OPERATIONLOG("L006", 40600000L, "L006_REF", ""),

        /**
         * パフォーマンスデータログ
         */
        LOG_PERFORMANCEDATALOG("L007", 40700000L, "L007_REF", ""),

        /**
         * 搬送回数(キャリアJob)
         */
        STATS_TRANSFERTIMESCARRIERJOB("S001", 50100000L, "S001_REF", ""),

        /**
         * HOST通信通信応答時間
         */
        STATS_HOSTCOMMUNICATIONRESTIME("S002", 50200000L, "S002_REF", ""),

        /**
         * 平均搬送時間(キャリアJob)
         */
        STATS_AVERAGETRANSFERTIMECARRIERJOB("S003", 50300000L, "S003_REF", ""),

        /**
         * 平均搬送時間(Micro)
         */
        STATS_AVERAGETRANSFERTIMEMICRO("S004", 50400000L, "S004_REF", ""),

        /**
         * 棚占有率
         */
        STATS_BINUTILIZATION("S005", 50500000L, "S005_REF", ""),

        /**
         * 平均棚占有率
         */
        STATS_AVERAGEBINUTILIZATION("S006", 50600000L, "S006_REF", ""),

        /**
         * 平均キャリア数
         */
        STATS_AVERAGENUMBERCARRIER("S007", 50700000L, "S007_REF", ""),

        /**
         * 搬送回数(Micro)
         */
        STATS_TRANSFERTIMESMICRO("S008", 50800000L, "S008_REF", ""),

        /**
         * 機器平均稼働率
         */
        STATS_AVERAGEUNITUTILIZATION("S009", 50900000L, "S009_REF", ""),

        /**
         * MTTR/MTBF
         */
        STATS_MTTRMTBF("S010", 51000000L, "S010_REF", ""),

        /**
         * ストッカグループ
         */
        MAINT_STOCKERGROUP("M001", 60100000L, "M001_REF", "M001_CHG"),

        /**
         * 代替機器
         */
        MAINT_ALTERNATEDEVICE("M002", 60200000L, "M002_REF", "M002_CHG"),

        /**
         * ユーザ管理
         */
        MAINT_USERMANAGEMENT("M003", 60300000L, "M003_REF", "M003_CHG"),

        /**
         * Host構成
         */
        MAINT_HOSTCONF("M004", 60400000L, "M004_REF", "M004_CHG"),

        /**
         * AMHS構成
         */
        MAINT_AMHSCONF("M005", 60500000L, "M005_REF", "M005_CHG"),

        /**
         * 装置構成
         */
        MAINT_EQUIPMENTCONF("M006", 60600000L, "", ""),

        /**
         * インライン装置構成
         */
        MAINT_INLINEEQUIPMENTCONF("M007", 60700000L, "", ""),

        /**
         * ゾーン構成
         */
        MAINT_ZONECONF("M008", 60800000L, "M008_REF", "M008_CHG"),

        /**
         * システムパラメータ
         */
        MAINT_SYSTEMPARAMETER("M009", 60900000L, "M009_REF", "M009_CHG"),

        /**
         * AMHSモード変更
         */
        MAINT_AMHSMODECHANGE("M010", 61000000L, "", "M010_CHG"),

        /**
         * キャリア同期
         */
        MAINT_CARRIERSYNCHRONIZE("M011", 61100000L, "", "M011_CHG"),

        /**
         * ルート検索
         */
        MAINT_ROUTESEARCH("M012", 61200000L, "M012_REF", "M012_CHG"),

        /**
         * ゾーンリレーショナル構成
         */
        MAINT_ZONERELATIONALCONF("M013", 61300000L, "M013_REF", "M013_CHG"),

        /**
         * ユニット構成
         */
        MAINT_UNITCONF("M014", 61400000L, "M014_REF", "M014_CHG"),

        /**
         * ビークル構成
         */
        MAINT_VEHICLECONF("M015", 61500000L, "M015_REF", "M015_CHG"),

        /**
         * ポート構成
         */
        MAINT_PORTCONF("M016", 61600000L, "M016_REF", "M016_CHG"),

        /**
         * 搬送テスト
         */
        MAINT_TRANSFERTEST("M017", 61700000L, "M017_REF", "M017_CHG"),

        /**
         * フェイルオーバールール構成
         */
        MAINT_FAILOVERRULECONF("M018", 61800000L, "M018_REF", "M018_CHG"),

        /**
         * メッセージ保持設定
         */
        MAINT_MESSAGERETENTIONSETTING("M019", 61900000L, "M019_REF", "M019_CHG"),

        /**
         * ノード構成
         */
        MAINT_NODECONF("M020", 62000000L, "M020_REF", "M020_CHG"),

        /**
         * プロセス構成
         */
        MAINT_PROCESSCONF("M021", 62100000L, "M021_REF", "M021_CHG"),

        /**
         * テイクオーバールール構成
         */
        MAINT_TAKEOVERRULECONF("M022", 62200000L, "M022_REF", "M022_CHG"),

        /**
         * Virtual IP構成
         */
        MAINT_VIRTUALIPCONF("M023", 62300000L, "M023_REF", "M023_CHG"),

        /**
         * Virtual IPリレーショナル構成
         */
        MAINT_VIRTUALIPRELATIONALCONF("M024", 62400000L, "M024_REF", "M024_CHG"),

        /**
         * HOSTエイリアス
         */
        MAINT_HOSTALIAS("M025", 62500000L, "M025_REF", "M025_CHG"),

        /**
         * 搬送Job優先順位しきい値設定
         */
        MAINT_JOBPRIORITYTHRESHOLD("M026", 62600000L, "M026_REF", "M026_CHG"),

        /**
         * OHBポートグループ
         */
        MAINT_OHBPORTGROUP("M027", 62700000L, "M027_REF", "M027_CHG"),

        /**
         * ポートグループ
         */
        MAINT_PORTGROUP("M028", 62800000L, "M028_REF", "M028_CHG"),

        /**
         * 搬送経路
         */
        MAINT_TRANSFERROUTE("M029", 62900000L, "M029_REF", "M029_CHG"),

        /**
         * トレースログ管理
         */
        MAINT_TRACELOGMANAGEMENT("M030", 63000000L, "M030_REF", "M030_CHG"),

        /**
         * リカバリーコンプレッション
         */
        // Step4P2 2017_11_21：未使用権限(参照権限)の削除
        MAINT_RECOVERYCOMPLETION("M031", 63100000L, "", "M031_CHG"),

        /**
         * テストキャリア
         */
        MAINT_TESTCARRIER("M032", 63200000L, "M032_REF", "M032_CHG"),

        /**
         * キャリアタイプ
         */
        MAINT_CARRIERTYPECONF("M033", 63300000L, "M033_REF", "M033_CHG"),

        /**
         * キャリアシェイプ
         */
        MAINT_CARRIERSHAPE("M034", 63400000L, "M034_REF", "M034_CHG"),

        /**
         * 最寄搬送先許可設定
         */
        MAINT_NEARTRANSFERCONF("M035", 63500000L, "M035_REF", "M035_CHG"),

        /**
         * ユニットグループ
         */
        MAINT_UNITGROUP("M036", 63600000L, "M036_REF", "M036_CHG"),

        /**
         * 階間搬送
         */
        MAINT_FLOORTRANSFER("M037", 63700000L, "M037_REF", "M037_CHG"),

        /**
         * 空FOUP管理
         */
        MAINT_EMPTYCARRIER("M038", 63800000L, "M038_REF", "M038_CHG"),

        /**
         * Host構成 アラームレポート
         */
        MAINT_HOSTALARMREPORT("M039", 63900000L, "", "M039_CHG"),

        /**
         * Host構成 リターンコード
         */
        MAINT_HOSTRETURNCODE("M040", 64000000L, "", "M040_CHG"),

        /**
         * モジュールマスタメンテナンス
         */
        MAINT_MODULECONF("M041", 64100000L, "M041_REF", "M041_CHG"),
        
        /**
         * HOSTポートグループ
         */
        MAINT_HOSTPORTGROUP("M042", 64200000L, "M042_REF", "M042_CHG"),

        /**
         * IFOHB搬送設定
         */
        MAINT_IFOHBSETTING("M043", 64300000L, "M043_REF", "M043_CHG"),

        /**
         * アラーム報告設定
         */
        MAINT_ALARMSYSTEMSETTING("M044", 64400000L, "M044_REF", "M044_CHG"),

        /**
         * レイアウト更新
         */
        MAINT_LAYOUTUPDATE("M045", 64500000L, "", "M045_CHG"),

        /**
         * M17更新要求 - MACS4#0059 Add
         */
        MAINT_M17MCSDATAUPDATE("M046", 64600000L, "", "M046_CHG"),

        /**
         * プロセス状態管理
         */
        MAINT_PROCSTATEMANAGEMENT("M901", 69000000L, "M901_REF", "M901_CHG"),

        /**
         * AMHS論理状態変更
         */
        MAINT_AMHSLSTATECHANGE("M902", 69200000L, "", "M902_CHG"),

        /**
         * Port論理状態変更
         */
        MAINT_PORTLSTATECHANGE("M903", 69300000L, "", "M903_CHG"),

        /**
         * ユニット論理状態変更
         */
        MAINT_UNITLSTATECHANGE("M904", 69400000L, "", "M904_CHG"),

        /**
         * OHB Group論理状態変更
         */
        MAINT_OHBPORTGROUPLSTATECHANGE("M905", 69500000L, "", "M905_CHG"),

        /**
         * MCS論理状態変更
         */
        MAINT_MCSLSTATECHANGE("M906", 69600000L, "", "M906_CHG"),

        /**
         * 運用モード変更
         */
        MAINT_TRANSMODECHANGE("M907", 69700000L, "", "M907_CHG");

        /**
         * ファンクションID
         */
        private final String functionId;

        /**
         * ログコード
         */
        private final long logCode;

        /**
         * 参照権限ファンクションID
         */
        private final String refAuthFuncId;

        /**
         * 変更権限ファンクションID
         */
        private final String chgAuthFuncId;

        /**
         * ENUM生成
         * @param functiond
         * @param logCode
         * @param refAuthFuncId
         * @param chgAuthFuncId
         */
        private ScreenInfo(final String functionId, final long logCode, final String refAuthFuncId,
                final String chgAuthFuncId) {
            this.functionId = functionId;
            this.logCode = logCode;
            this.refAuthFuncId = refAuthFuncId;
            this.chgAuthFuncId = chgAuthFuncId;
        }

        /**
         * 画面IDを取得
         * @return 画面ID
         */
        public String getFunctionId() {

            return this.functionId;
        }

        /**
         * ログコード（基本部）を取得
         * @return ログコード（基本部）
         */
        public long getLogCode() {

            return this.logCode;
        }

        /**
         * 参照権限のファンクションIDを取得
         * @return
         */
        public String getRefAuthFuncId() {

            return this.refAuthFuncId;
        }

        /**
         * 変更権限のファンクションIDを取得
         * @return
         */
        public String getChgAuthFuncId() {

            return this.chgAuthFuncId;
        }

    }

    // MACS4#0047 Add Start
    public static class PariMenu {
        /**
         * 親メニュー
         */
        /** 来歴 */
        public static final String MENU_HISTORY = "MENU_HISTORY_ENABLE";
        /** ログ */
        public static final String MENU_LOG = "MENU_LOG_ENABLE";
        /** 統計 */
        public static final String MENU_STATS = "MENU_STATS_ENABLE";
        /** 保守 */
        public static final String MENU_MAINT = "MENU_MAINT_ENABLE";
        /** AMHS構成 */
        public static final String MENU_AMHS_CONF = "MENU_AMHS_CONF_ENABLE";
        /** AMHS操作 */
        public static final String MENU_AMHS_OPE = "MENU_AMHS_OPE_ENABLE";
        /** システム操作 */
        public static final String MENU_SYS_OPE = "MENU_SYS_OPE_ENABLE";
        /** Has機能 */
        public static final String MENU_HAS_FUNC = "MENU_HAS_FUNC_ENABLE";

        /**
         * 子メニュー(GUI_FUNC_RLT)
         */
        /** 来歴 */
        public static final String[] CHILD_MENU_HISTORY = {
                ScreenInfo.HIST_TRANSFERJOBHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_ALARMHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_CARRIERREMOVEHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_STORAGETIMEHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_CARRIERMOTIONTIMEHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_ALARMSYSTEMHISTORY.getRefAuthFuncId(),
                ScreenInfo.HIST_STAGEHISTORY.getRefAuthFuncId()         // MACS4#0049 Add
        };
        /** ログ */
        public static final String[] CHILD_MENU_LOG = {
                ScreenInfo.LOG_SYSTEMLOG.getRefAuthFuncId(),
                ScreenInfo.LOG_OPERATIONLOG.getRefAuthFuncId(),
                ScreenInfo.LOG_HOSTCOMMUNICATIONLOG.getRefAuthFuncId(),
                ScreenInfo.LOG_AMHSCOMMUNICATIONLOG.getRefAuthFuncId(),
                ScreenInfo.LOG_TRANSFERDISTURBLOG.getRefAuthFuncId()
        };
        /** 統計 */
        public static final String[] CHILD_MENU_STATS = {
                ScreenInfo.STATS_MTTRMTBF.getRefAuthFuncId(),
                ScreenInfo.STATS_BINUTILIZATION.getRefAuthFuncId(),
                ScreenInfo.STATS_AVERAGEBINUTILIZATION.getRefAuthFuncId(),
                ScreenInfo.STATS_AVERAGENUMBERCARRIER.getRefAuthFuncId(),
                ScreenInfo.STATS_AVERAGEUNITUTILIZATION.getRefAuthFuncId(),
                ScreenInfo.STATS_TRANSFERTIMESCARRIERJOB.getRefAuthFuncId(),
                ScreenInfo.STATS_TRANSFERTIMESMICRO.getRefAuthFuncId(),
                ScreenInfo.STATS_AVERAGETRANSFERTIMECARRIERJOB.getRefAuthFuncId(),
                ScreenInfo.STATS_AVERAGETRANSFERTIMEMICRO.getRefAuthFuncId(),
                ScreenInfo.STATS_HOSTCOMMUNICATIONRESTIME.getRefAuthFuncId()
        };
        /** 保守 */
        public static final String[] CHILD_MENU_MAINT = {
                ScreenInfo.MAINT_STOCKERGROUP.getRefAuthFuncId(),
                ScreenInfo.MAINT_PORTGROUP.getRefAuthFuncId(),
                ScreenInfo.MAINT_ALTERNATEDEVICE.getRefAuthFuncId(),
                ScreenInfo.MAINT_NEARTRANSFERCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_USERMANAGEMENT.getRefAuthFuncId(),
                ScreenInfo.MAINT_HOSTCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_AMHSCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PORTCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_ZONECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_OHBPORTGROUP.getRefAuthFuncId(),
                ScreenInfo.MAINT_IFOHBSETTING.getRefAuthFuncId(),
                ScreenInfo.MAINT_SYSTEMPARAMETER.getRefAuthFuncId(),
                ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_CARRIERSYNCHRONIZE.getChgAuthFuncId(),
                ScreenInfo.MAINT_AMHSLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_PORTLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_UNITLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_OHBPORTGROUPLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_LAYOUTUPDATE.getChgAuthFuncId(),
                ScreenInfo.MAINT_M17MCSDATAUPDATE.getChgAuthFuncId(),           // MACS4#0059 Add
                ScreenInfo.MAINT_MCSLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_ROUTESEARCH.getRefAuthFuncId(),
                ScreenInfo.MAINT_ZONERELATIONALCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_MODULECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_UNITCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_UNITGROUP.getRefAuthFuncId(),
                ScreenInfo.MAINT_FLOORTRANSFER.getRefAuthFuncId(),
                ScreenInfo.MAINT_VEHICLECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_EMPTYCARRIER.getRefAuthFuncId(),
                ScreenInfo.MAINT_TESTCARRIER.getRefAuthFuncId(),
                ScreenInfo.MAINT_CARRIERTYPECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_CARRIERSHAPE.getRefAuthFuncId(),
                ScreenInfo.MAINT_TRANSFERTEST.getRefAuthFuncId(),
                ScreenInfo.MAINT_ALARMSYSTEMSETTING.getRefAuthFuncId(),
                ScreenInfo.MAINT_NODECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PROCESSCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PROCSTATEMANAGEMENT.getRefAuthFuncId(),
                ScreenInfo.MAINT_FAILOVERRULECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_TAKEOVERRULECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_MESSAGERETENTIONSETTING.getRefAuthFuncId(),
                ScreenInfo.MAINT_VIRTUALIPCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_VIRTUALIPRELATIONALCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_HOSTALIAS.getRefAuthFuncId()
        };
        /** AMHS構成 */
        public static final String[] CHILD_MENU_AMHS_CONF = {
                ScreenInfo.MAINT_AMHSCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PORTCONF.getRefAuthFuncId()
        };
        /** AMHS操作 */
        public static final String[] CHILD_MENU_AMHS_OPE = {
                ScreenInfo.MAINT_AMHSMODECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_CARRIERSYNCHRONIZE.getChgAuthFuncId(),
                ScreenInfo.MAINT_AMHSLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_PORTLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_UNITLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_OHBPORTGROUPLSTATECHANGE.getChgAuthFuncId()
        };
        /** システム操作 */
        public static final String[] CHILD_MENU_SYS_OPE = {
                ScreenInfo.MAINT_LAYOUTUPDATE.getChgAuthFuncId(),
                ScreenInfo.MAINT_MCSLSTATECHANGE.getChgAuthFuncId(),
                ScreenInfo.MAINT_M17MCSDATAUPDATE.getChgAuthFuncId()        // MACS4#0059 Add
        };
        /** Has機能 */
        public static final String[] CHILD_MENU_HAS_FUNC = {
                ScreenInfo.MAINT_NODECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PROCESSCONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_PROCSTATEMANAGEMENT.getRefAuthFuncId(),
                ScreenInfo.MAINT_FAILOVERRULECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_TAKEOVERRULECONF.getRefAuthFuncId(),
                ScreenInfo.MAINT_MESSAGERETENTIONSETTING.getRefAuthFuncId()
        };

        public static enum PairFuncId {
            /** 来歴 */
            PAIR_MENU_HISTORY(MENU_HISTORY, CHILD_MENU_HISTORY),
            /** ログ */
            PAIR_MENU_LOG(MENU_LOG, CHILD_MENU_LOG),
            /** 統計 */
            PAIR_MENU_STATS(MENU_STATS, CHILD_MENU_STATS),
            /** 保守 */
            PAIR_MENU_MAINT(MENU_MAINT, CHILD_MENU_MAINT),
            /** AMHS構成 */
            PAIR_MENU_AMHS_CONF(MENU_AMHS_CONF, CHILD_MENU_AMHS_CONF),
            /** AMHS操作 */
            PAIR_MENU_AMHS_OPE(MENU_AMHS_OPE, CHILD_MENU_AMHS_OPE),
            /** システム操作 */
            PAIR_MENU_SYS_OPE(MENU_SYS_OPE, CHILD_MENU_SYS_OPE),
            /** Has機能 */
            PAIR_MENU_HAS_FUNC(MENU_HAS_FUNC, CHILD_MENU_HAS_FUNC);

            private final String parentFuncId;
            private final String[] childFuncIdList;

            private PairFuncId(final String parentFuncId, final String[] childFuncIdList) {
                this.parentFuncId = parentFuncId;
                this.childFuncIdList = childFuncIdList;
            }

            /**
             * 親メニュー権限の取得
             */
            public String getParentFuncId() {

                return parentFuncId;
            }

            /**
             * 子メニュー権限の取得
             */
            public String[] getChildFuncIdList() {

                return childFuncIdList;
            }
        }
    }
    // MACS4#0047 Add End

    /**
     * ログ操作種別（下５桁部分）xxxxx1122x
     */
    public class LogOperationType {

        /**
         * 登録系操作
         */
        static public final long ADD = 1000L;

        /**
         * 更新系操作
         */
        static public final long MODIFY = 2000L;

        /**
         * 削除系操作
         */
        static public final long DELETE = 3000L;

        /**
         * 強制削除系操作
         */
        static public final long FORCE_DEL = 4000L;

        /**
         * 表示系（検索～一覧、詳細などの表示） 操作
         */
        static public final long GET = 5000L;

        /**
         * CSVファイル出力系操作（抽出条件セット）
         */
        static public final long CSV_SET = 6000L;

        /**
         * CSVファイル出力系操作（CSVファイル出力）
         */
        static public final long CSV_OUT = 7000L;

        /**
         * 外部処理実行系
         */
        static public final long EXECUTE = 8000L;

        /**
         * 外部ファイル系
         */
        static public final long EXT_FILE = 9000L;
        /**
         * システム系操作（ログイン、ログアウト等）
         */
        static public final long SYS = 90000L;
    }

    /**
     * 操作ログ予約（ログコード後１桁部分）xxxxxxxxx1
     */
    static public final long OP_LOG_RESERV = 0L;

    /**
     * アカウント状態
     */
    public class AccountState {

        /**
         * 正常（アンロック）
         */
        static public final short UNLOCK = 0;

        /**
         * ロック
         */
        static public final short LOCK = 1;
    }

    /**
     * システムパラメータ（MCS_CONSTS）
     */
    public class SystemParameter {

        /**
         * カテゴリー（GUI固定）
         */
        public class Category {

            /**
             * GUI
             */
            static public final String GUI = "GUI";
        }

        /**
         * セクション
         */
        public class Section {

            /**
             * テーブルコンポーネント
             */
            static public final String TABLE_COMP = "AUTO_RELOAD";

            /**
             * 自動更新タイマー管理
             */
            static public final String AUTO_RELOAD = "AUTO_RELOAD";

            /**
             * ログイン
             */
            static public final String LOGIN = "LOGIN";

            /**
             * スクリーンコントロール
             */
            static public final String SCREEN_CONTROL = "SCREEN_CONTROL";

            /**
             * システムモニタ
             */
            static public final String SYSTEM_MONITOR = "SYSTEM_MONITOR";

        }

        /**
         * キー
         */
        public class Key {

            /**
             * 自動更新時間
             */
            static public final String AUTO_RELOAD_INTERVAL = "AUTO_RELOAD_INTERVAL";

            /**
             * 個別モニタ用自動更新時間
             */
            static public final String AUTO_RELOAD_INTERVAL_INDIVIDUAL_MONITOR = "AUTO_RELOAD_INTERVAL_INDIVIDUAL_MONITOR";

            /**
             * システムモニタ用自動更新時間
             */
            static public final String AUTO_RELOAD_INTERVAL_SYSTEM_MONITOR = "AUTO_RELOAD_INTERVAL_SYSTEM_MONITOR";

            /**
             * ログイン失敗回数
             */
            static public final String LOGIN_FAIL = "MAX_LOGIN_FAILURE";

            /**
             * ユーザがロック対象となる未ログイン日
             */
            static public final String LOGIN_LOCK = "AUTO_LOCK";

            /**
             * ユーザが削除対象となる未ログイン日
             */
            static public final String LOGIN_DELETE = "AUTO_DELETE";

            /**
             * 自動ログアウト時間（分）
             */
            static public final String LOGOUT_AUTO_LOGOUT = "AUTO_LOGOUT_TIMER";

            /**
             * 自動ロック、自動削除処理インターバル（分）
             */
            static public final String AUTO_DEL_INTERVAL = "AUTO_LOCK_TIMER";

            /**
             * 占有率注意レベル
             */
            static public final String OCCUPIED_RATE_CAUTION_LEVEL = "OCCUPIED_RATE_CAUTION_LEVEL";

            /**
             * 占有率アラートレベル
             */
            static public final String OCCUPIED_RATE_ALERT_LEVEL = "OCCUPIED_RATE_ALERT_LEVEL";

            /**
             * 個別モニタの制限数
             */
            static public final String LIMIT_INDIVIDUAL_MONITOR = "LIMIT_INDIVIDUAL_MONITOR";

        }

        /**
         * UNIT種別
         */
        public class Unit {

            /**
             * SEC
             */
            static public final String UNIT_SEC = "Sec";

            /**
             * MIN
             */
            static public final String UNIT_MIN = "Min";

            /**
             * DAY
             */
            static public final String UNIT_DAY = "Day";

            /**
             * TIME
             */
            static public final String UNIT_TIME = "HH:MM:SS";

            /**
             * NUMBER
             */
            static public final String UNIT_NUMBER = "Number";

            /**
             * TEXT
             */
            static public final String UNIT_TEXT = "Text";
        }

    }

    /**
     * システムパラメータキー群ENUM(カテゴリ、セクション、Key)
     *
     */
    static public enum McsConstsKey {
        /**
         * ログイン失敗回数
         */
        LOGIN_FAIL(SystemParameter.Category.GUI, SystemParameter.Section.LOGIN, SystemParameter.Key.LOGIN_FAIL),

        /**
         * ユーザがロック対象となる未ログイン日
         */
        LOGIN_LOCK(SystemParameter.Category.GUI, SystemParameter.Section.LOGIN, SystemParameter.Key.LOGIN_LOCK),

        /**
         * ユーザが削除対象となる未ログイン日
         */
        LOGIN_DELETE(SystemParameter.Category.GUI, SystemParameter.Section.LOGIN, SystemParameter.Key.LOGIN_DELETE),

        /**
         * 自動ログアウト時間（分）
         */
        LOGOUT_AUTO_LOGOUT(SystemParameter.Category.GUI, SystemParameter.Section.LOGIN, SystemParameter.Key.LOGOUT_AUTO_LOGOUT),

        /**
         * 自動ロック、自動削除処理インターバル（分）
         */
        AUTO_DEL_INTERVAL(SystemParameter.Category.GUI, SystemParameter.Section.LOGIN, SystemParameter.Key.AUTO_DEL_INTERVAL),;

        /**
         * カテゴリー
         */
        private final String category;

        /**
         * セクション
         */
        private final String section;

        /**
         * キー
         */
        private final String key;

        /**
         * ENUM生成
         * @param category
         * @param section
         * @param key
         */
        private McsConstsKey(final String category, final String section, final String key) {
            this.category = category;
            this.section = section;
            this.key = key;
        }

        /**
         * カテゴリーを取得
         * @return カテゴリー
         */
        public String getCategory() {

            return this.category;
        }

        /**
         * セクションを取得
         * @return セクション
         */
        public String getSection() {

            return this.section;
        }

        /**
         * キーを取得
         * @return キー
         */
        public String getKey() {

            return this.key;
        }
    }

    /**
     * 機能のカテゴリー名
     */
    public class Category {

        /**
         * TOP
         */
        public static final String TOP = "TOP";

        /**
         * INFORMATION
         */
        public static final String INFO = "INFORMATION";

        /**
         * HISTORY
         */
        public static final String HIST = "HISTORY";

        /**
         * LOG
         */
        public static final String LOG = "LOG";

        /**
         * STATISTICS
         */
        public static final String STATS = "STATISTICS";

        /**
         * MAINTENANCE
         */
        public static final String MAINT = "MAINTENANCE";
    }

    public class UserManagement {

        /**
         * Tree部品要素 親ノードID(All Grant)
         */
        public static final String ALLGRANT = "All Grant";
    }

    /**
     * HandsOnTableのConfig定義名
     */
    public class HandsOnTableConfig {

        /**
         * ラベル
         */
        public static final String LABEL = "LABEL";

        /**
         * アイコン
         */
        public static final String ICON = "ICON";

        /**
         * ボーダー
         */
        public static final String BORDER = "BORDER";

        /**
         * サイズ
         */
        public static final String SIZE = "SIZE";

        /**
         * 列の横幅
         */
        public static final String WIDTH = "WIDTH";

        /**
         * 行の縦幅
         */
        public static final String HEIGHT = "HEIGHT";

        /**
         * セルのマージ
         */
        public static final String MERGE = "MERGE";
    }

    /**
     * 棚使用率閾値
     */
    public class OccupiedRate {

        /**
         * 棚使用率閾値(警告)の初期値
         */
        public static final int DEFAULT_OCCUPIED_RATE_CAUTION = 70;

        /**
         * 棚使用率閾値(警戒)の初期値
         */
        public static final int DEFAULT_OCCUPIED_RATE_ALERT = 90;

    }

    /**
     * 搬送テストマスタメンテナンス 搬送先タイプ
     */
    public class TransferTest {

        // Step4 2017_08_16
        /** 行き先設定 ストッカ（画面から送信される値） */
        public static final short DST_TYPE_STC_EQ = 1;

        /** 行き先設定 ストッカグループ（画面から送信される値） */
        public static final short DST_TYPE_STOCKER_GROUP = 2;

        /** 行き先設定 OHBグループ（画面から送信される値） */
        public static final short DST_TYPE_OHB_GROUP = 3;
    }

    /**
     * 来歴結果 マルチセレクトボックス要素クラス
     */
    public static class historyResultMultiSelectBoxItems {

        /** Succeeded */
        public static final String SUCCEEDED = "Succeeded";

        /** Failed */
        public static final String FAILED = "Failed";

        /** Canceled */
        public static final String CANCELED = "Canceled";

        /** Aborted */
        public static final String ABORTED = "Aborted";

        /** Alternated */
        public static final String ALTERNATED = "Alternated";

        /** UnCompleted */
        public static final String UN_COMPLETED = "UnCompleted";

        /** Timeout */
        public static final String TIMEOUT = "Timeout";

        /** Deleted MACS4#0016 Add */
        public static final String DELETED = "Deleted";

        public static final String[] HISTORY_RESULT_LIST = {
                    SUCCEEDED,
                    FAILED,
                    CANCELED,
                    ABORTED,
                    ALTERNATED,
                    UN_COMPLETED,
                    TIMEOUT,
                    DELETED     // MACS4#0016 Add
                };
    }

    /**
     * 来歴結果 マルチセレクトボックス要素クラス
     */
    public static class hostAlarmHistoryResult {

        /** Succeeded */
        public static final String SUCCESS = "Success";

        /** Failed */
        public static final String FAIL = "Fail";

        public static final String[] HISTORY_RESULT_LIST = {
                    SUCCESS,
                    FAIL
                };
    }


    /**
     * キャリア GUI Colorキー情報
     */
    public class Carrier {

        /** Online Alarm */
        public static final short KEY_ONLINE_ALARM = 998;

        /** Offline */
        public static final short KEY_OFFLINE = 999;
    }

    /**
     * 搬送Job GUI Colorキー情報 / Carrier Job State
     */
    public class TransferJob {

        /** NotOverTime */
        public static final short KEY_NOT_OVER_TIME = 60;

        /** OverTime */
        public static final short KEY_OVER_TIME = 70;

        /** EndTimeViolation */
        public static final short KEY_END_TIME_VIOLATION = 80;

        /** Carrier Job State - Rescheduling */
        public static final short CARRIER_JOB_STATE_RESCHEDULING = 7;

        /** Carrier Job State - Abnormal */
        public static final short CARRIER_JOB_STATE_ABNORMAL = 8;

        /** Job Action Flag - Normal Transfer */
        public static final short JOB_ACTION_FLAG_NORMAL_TRANSFER = 0;
    }
    /**
     * MCS共通定義
     */
    public class MCSConfig {
        /** 出力ディレクトリ */
        public static final String KEY_RESULT_FILE_DIR = "RESULT_FILE_DIR";
        /** ファイル拡張子 */
        public static final String KEY_RESULT_EXT = "RESULT_EXT";
        /** mcsctrl - ツールディレクトリ */
        public static final String KEY_MCSCTRL_TOOL_DIR = "MCSCTRL_TOOL_DIR";
        /** mcsctrl - ファイル名 */
        public static final String KEY_MCSCTRL_TOOL_FILE = "MCSCTRL_TOOL_FILE";
    }

    /**
     * AMHS通信ログ定義情報
     */
    public class AmhsCommLog {

        /** ツールディレクトリ */
        public static final String KEY_DIR = "AMHS_LOG_TOOL_DIR";
        /** ツールファイル名 */
        public static final String KEY_FILE = "AMHS_LOG_TOOL_FILE";
        /** 出力結果ファイル名 */
        public static final String KEY_RESULT_FILE = "AMHS_LOG_TOOL_RESULT_FILE";
    }

    /**
     * Host通信ログ定義情報
     */
    public class HostCommLog {

        /** ツールディレクトリ */
        public static final String KEY_DIR = "HOST_LOG_TOOL_DIR";
        /** ツールファイル名 */
        public static final String KEY_FILE = "HOST_LOG_TOOL_FILE";
        /** 出力結果ファイル名 */
        public static final String KEY_RESULT_FILE = "HOST_LOG_TOOL_RESULT_FILE";
    }

    /**
     * AMHS論理状態
     */
    public class AmhsLogicalState {
        /** Down */
        public static final long CODE_DOWN = (long) 0;
        /** Up */
        public static final long CODE_UP = (long) 1;
        /** Prepared PM */
        public static final long CODE_PRE_PM = (long) 2;
        /** PM */
        public static final long CODE_PM = (long) 3;
        /** Prepared PM */
        public static final String STR_CODE_PRE_PM = "2";
        /** PM */
        public static final String STR_CODE_PM = "3";
    }

    /**
     * MCS論理状態
     */
    public class McsLogicalState {
        /** Down */
        public static final short CODE_DOWN = (short) 0;
        /** Up */
        public static final short CODE_UP = (short) 1;
    }

    /**
     * OHB論理状態
     */
    public class OhbLogicalState {
        /** Down */
        public static final short CODE_DOWN = (short) 0;
        /** Up */
        public static final short CODE_UP = (short) 1;
        /** Prepared PM */
        public static final short CODE_PRE_PM = (short) 2;
        /** PM */
        public static final short CODE_PM = (short) 3;
        /** Prepared PM */
        public static final String STR_CODE_PRE_PM = "2";
        /** PM */
        public static final String STR_CODE_PM = "3";
    }

    /**
     * Port論理状態
     */
    public class PortLogicalState {
        /** Down */
        public static final short CODE_DOWN = (short) 0;
        /** Up */
        public static final short CODE_UP = (short) 1;
    }

    /**
     * Unit論理状態
     */
    public class UnitLogicalState {
        /** Down */
        public static final short CODE_DOWN = (short) 0;
        /** Up */
        public static final short CODE_UP = (short) 1;
    }

    /**
     * キャリア通過モード
     */
    public class PassingMode {
        /** Product Carrier */
        public static final short CODE_PRODUCT_CARRIER = (short) 1;
        /** Test Carrier */
        public static final short CODE_TEST_CARRIER = (short) 2;
    }

    /**
     * AMHSタイプ
     */
    public class AmhsType {
        /** Unknown(VIRTUAL) */
        public static final short CODE_UNKNOWN = (short) 0;
        /** STC */
        public static final short CODE_STC = (short) 1;
        /** TSTC */
        public static final short CODE_TSTC = (short) 21;
        /** RSBC */
        public static final short CODE_RSBC = (short) 25;
        /** LFC */
        public static final short CODE_LFC = (short) 31;
        /** DWCC */
        public static final short CODE_DWCC = (short) 41;
        /** OPC */
        public static final short CODE_OPC = (short) 51;
        /** OHBC */
        public static final short CODE_OHBC = (short) 61;
        /** VOHBC */
        public static final short CODE_VOHBC = (short) 65;
        /** OHVC */
        public static final short CODE_OHVC = (short) 81;
        /** VecLFC */
        public static final short CODE_VLFT = (short) 85;
        /** MCS - MACS4#0119 Add */
        public static final short CODE_MCS = (short) 91;
        /** Virtual - Port画面用 */
        public static final short CODE_VIRTUAL = (short) 999;
        /** Virtual - Port画面用 */
        public static final String STR_VIRTUAL = "Virtual";
        /** Unknown(VIRTUAL) */
        public static final String STR_CODE_UNKNOWN = "0";
        /** STC */
        public static final String STR_CODE_STC = "1";
        /** TSTC */
        public static final String STR_CODE_TSTC = "21";
        /** RSBC */
        public static final String STR_CODE_RSBC = "25";
        /** LFC */
        public static final String STR_CODE_LFC = "31";
        /** DWCC */
        public static final String STR_CODE_DWCC = "41";
        /** OPC */
        public static final String STR_CODE_OPC = "51";
        /** OHBC */
        public static final String STR_CODE_OHBC = "61";
        /** VOHBC */
        public static final String STR_CODE_VOHBC = "65";
        /** OHVC */
        public static final String STR_CODE_OHVC = "81";
        /** MACHINE */
        public static final String STR_CODE_MACHINE = "81";
        /** VecLFC */
        public static final String STR_CODE_VLFT = "85";
        /** OHP GROUP */
        public static final String STR_CODE_OHB_GROUP = "99";
        /** STC */
        public static final String STR_NAME_STC = "STC";
        /** TSTC */
        public static final String STR_NAME_TSTC = "TSTC";
        /** RSBC */
        public static final String STR_NAME_RSBC = "RSBC";
        /** LFC */
        public static final String STR_NAME_LFC = "LFC";
        /** DWCC */
        public static final String STR_NAME_DWCC = "DWCC";
        /** OPC */
        public static final String STR_NAME_OPC = "OPC";
        /** OHP GROUP */
        public static final String STR_NAME_OHB_GROUP = "OHB GROUP";
        /** MACHINE */
        public static final String STR_NAME_MACHINE = "MACHINE";
    }

    /**
     * ポートタイプ
     */
    public class PortType {
        /** PGV PORT */
        public static final short CODE_PGV = (short) 1;
        /** OPERATOR PORT */
        public static final short CODE_OPE = (short) 2;
        /** EQUIPMENT PORT */
        public static final short CODE_EQP = (short) 11;
        /** LINK PORT */
        public static final short CODE_LINK = (short) 21;
        /** DCT PORT */
        public static final short CODE_DCT = (short) 22;
        /** OHB PORT */
        public static final short CODE_OHB = (short) 71;
        /** TOOL-BUFFER PORT */
        public static final short CODE_TOOL_BUFFER = (short) 75;
        /** SHELF */
        public static final short CODE_SHELF = (short) 91;
        /** OHB PORT GROUP*/
        public static final short CODE_OHB_GROUP = (short) 92;
        /** VEHICLE */
        public static final short CODE_VEH = (short) 95;
        /** VIRTUAL PORT */
        public static final short CODE_VIRTUAL = (short) 99;

        /** PGV PORT */
        public static final String STR_CODE_PGV = "01";
        /** OPERATOR PORT */
        public static final String STR_CODE_OPE = "02";
        /** EQUIPMENT PORT */
        public static final String STR_CODE_EQP = "11";
        /** LINK PORT */
        public static final String STR_CODE_LINK = "21";
        /** DCT PORT */
        public static final String STR_CODE_DCT = "22";
        /** OHB PORT */
        public static final String STR_CODE_OHB = "71";
        /** TOOL-BUFFER PORT */
        public static final String STR_CODE_TOOL_BUFFER = "75";
        /** SHELF */
        public static final String STR_CODE_SHELF = "91";
        /** OHB PORT GROUP*/
        public static final String STR_CODE_OHB_GROUP = "92";
        /** VEHICLE */
        public static final String STR_CODE_VEH = "95";
        /** VIRTUAL PORT */
        public static final String STR_CODE_VIRTUAL = "99";

    }

    /**
     * I/Oモード
     */
    public class IOMode {
        /** None */
        public static final long CODE_NONE = (long) 0;
        /** In */
        public static final long CODE_IN = (long) 1;
        /** Out */
        public static final long CODE_OUT = (long) 2;
        /** Both */
        public static final long CODE_BOTH = (long) 3;
    }

    /**
     * キャリアシェイプ
     */
    public class CarrierShape {
        /** Shape ID - FOUP */
        public static final long CODE_FOUP = (long) 1;
        /** Shape ID - RETICLE */
        public static final long CODE_RETICLE = (long) 2;
    }

    /**
     * 搬送テスト 実施状況
     */
    public class TransferTestStatus {
        /** 停止 */
        public static final short CODE_STOP = (short) 0;
        /** 搬送開始待ち */
        public static final short CODE_START_WAIT = (short) 1;
        /** 実行中 */
        public static final short CODE_RUN = (short) 2;
        /** 停止要求中 */
        public static final short CODE_REQ_STOP = (short) 3;
        /** 待機 MACS4#0073 Add */
        public static final short CODE_STAND_BY = (short) 4;
        /** 異常終了 MACS4#0073 Mod 4 → 5 */
        public static final short CODE_ABNORMAL_STOP = (short) 5;
    }

    /**
     * 運用モード
     */
    public class TransMode {
        /** Enable Tool Buffer */
        public static final short CODE_ENABLE_TOOL_BUFFER = (short) 0;
        /** Provide */
        public static final short CODE_PROVIDE = (short) 1;
        /** Collect */
        public static final short CODE_COLLECT = (short) 2;
        /** Provide/Collect */
        public static final short CODE_PROVIDE_COLLECT = (short) 3;
        /** Disable Tool Buffer */
        public static final short CODE_DISABLE_TOOL_BUFFER = (short) 4;
        /** Mode Switching */
        public static final short CODE_MODE_SWITCHING = (short) 5;
        /** Enable Tool Buffer */
        public static final String STR_ENABLE_TOOL_BUFFER = "0";
        /** Disable Tool Buffer */
        public static final String STR_DISABLE_TOOL_BUFFER = "4";
    }

    /**
     * グループタイプ
     */
    public class GroupType {
        /** ストッカグループ */
        public static final short CODE_STK_GRP = (short) 1;
        /** ポートグループ */
        public static final short CODE_PORT_GRP = (short) 2;
        /** ユニットグループ */
        public static final short CODE_UNIT_GRP = (short) 10;
    }

    /**
     * グループフラグ
     */
    public class GroupFlag {
        /** ストッカグループ - ストッカ */
        public static final short CODE_STK_FLAG = (short) 1;
        /** ストッカグループ - OHB */
        public static final short CODE_OHB_FLAG = (short) 2;
        /** ユニットフラグ */
        public static final short CODE_UNIT_FLAG = (short) 20;
    }

    /**
     * ゾーンタイプ
     */
    public class ZoneType {
        /** 物理ゾーン */
        public static final short CODE_PHYSICAL = (short) 0;
        /** 論理ゾーン */
        public static final short CODE_LOGICAL = (short) 1;
    }

    /**
     * フロア
     */
    public class Floor {
        /** 3F */
        public static final short CODE_FLOOR_3F = (short) 3;
    }

    /**
     * OHBタイプ
     */
    public class OhbType {
        /** OHB - MACS4#0084 Add */
        public static final short CODE_OHB = (short) 0;
        /** IFOHB */
        public static final short CODE_IFOHB = (short) 1;
    }

    /**
     * IFOHB搬送設定 搬送ロケーションタイプ
     */
    public class IFOhbSettingTransLocType {

        /** 行き先設定 OHBグループ (画面から送信される値) */
        public static final short TRANS_LOC_TYPE_OHB_GROUP = 1;

        /** 行き先設定 装置ポート (画面から送信される値) */
        public static final short TRANS_LOC_TYPE_EQP = 2;

        /** 行き先設定 オペレータポート (画面から送信される値) */
        public static final short TRANS_LOC_TYPE_OPC = 3;
    }

    /**
     * 空FOUPコントローラ コントローラタイプ - MACS4#0084 Add
     */
    public class EmptyCarrierControllerType {

        /** ストッカ */
        public static final short CONTROLLER_TYPE_STC = (short) 1;

        /** OHBGr */
        public static final short CONTROLLER_TYPE_OHB = (short) 2;
    }

    /**
     * 空FOUPコントローラ 供給エリア - MACS4#0084 Add
     */
    public class EmptyCarrierControllerAreaId {
        /** 初期値 */
        public static final String DEFALUT_VALUE = "Temporary";
    }

    /**
     * Jobタイプ
     */
    public class JobType {
        /** Batch */
        public static final String JOB_TYPE_BATCH = "2";
    }

    /**
     * mcsctrl実行コマンド情報
     */
    public class exeCmd {
        /** プロセス操作 */
        public static final String CMD_PROC = "proc";
        /** 切り替え操作 */
        public static final String CMD_SWITCH = "switch";
        /** Start */
        public static final String CMD_START = "start";
        /** Active */
        public static final String CMD_ACTIVE = "active";
        /** Ready */
        public static final String CMD_READY = "ready";
        /** Stop */
        public static final String CMD_STOP = "stop";
        /** フェイルオーバー */
        public static final String CMD_FAILOVER = "failover";
        /** テイクオーバー */
        public static final String CMD_TAKEOVER = "takeover";
    }

    /**
     * String Master取得Key情報
     */
    public class StringMaster {
        /** AMHS_TYPE_STRING */
        public static final String KEY_AMHS_TYPE = "AMHS_TYPE_STRING";
        /** AMHS_LOGICAL_STATE_STRING */
        public static final String KEY_AMHS_LOGICAL_STATE = "AMHS_LOGICAL_STATE_STRING";
        /** AMHS_MODEL_STRING */
        public static final String KEY_AMHS_MODEL = "AMHS_MODEL_STRING";
        /** APPLY_STRING */
        public static final String KEY_APPLY = "APPLY_STRING";
        /** AREA_STRING */
        public static final String KEY_AREA = "AREA_TYPE_STRING";
        /** CARRIER_ALARM_STRING */
        public static final String KEY_CARRIER_ALARM = "CARRIER_ALARM_STRING";
        /** CARRIER_EMPTY_STRING */
        public static final String KEY_CARRIER_EMPTY = "CARRIER_EMPTY_STRING";
        /** CARRIER_JOB_STATE_STRING */
        public static final String KEY_CARRIER_JOB_STATE = "CARRIER_JOB_STATE_STRING";
        /** CARRIER_SHAPE_STRING */
        public static final String KEY_CARRIER_SHAPE = "CARRIER_SHAPE_STRING";
        /** FLOOR_STRING */
        public static final String KEY_FLOOR = "FLOOR_TYPE_STRING";
        /** HSMS_MODE_STRING */
        public static final String KEY_HSMS_MODE = "HSMS_MODE_STRING";
        /** IDRW_TYPE_STRING */
        public static final String KEY_IDRW_TYPE = "IDRW_TYPE_STRING";
        /** IO_MODE_STRING */
        public static final String KEY_IO_MODE = "IO_MODE_STRING";
        /** JOB_CATEGORY_STRING */
        public static final String KEY_JOB_CATEGORY = "JOB_CATEGORY_STRING";
        /** JOB_STATE_STRING */
        public static final String KEY_JOB_STATE = "JOB_STATE_STRING";
        /** MESSAGE_RETENTION_TYPE_STRING */
        public static final String KEY_MESSAGE_RETENTION_TYPE = "MESSAGE_RETENTION_TYPE_STRING";
        /** MICRO_CMD_ROUTE_MODIFY_STRING */
        public static final String KEY_MICRO_CMD_ROUTE_MODIFY = "MICRO_CMD_ROUTE_MODIFY_STRING";
        /** MCS_LOGICAL_STATE_STRING */
        public static final String KEY_MCS_LOGICAL_STATE = "MCS_LOGICAL_STATE_STRING";
        /** MCS_RET_CODE_CATEGORY_STRING */
        public static final String KEY_MCS_RET_CODE_CATEGORY = "MCS_RET_CODE_CATEGORY_STRING";
        /** OHB_LOGICAL_STATE_STRING */
        public static final String KEY_OHB_LOGICAL_STATE = "OHB_LOGICAL_STATE_STRING";
        /** PGV_MODE_STRING */
        public static final String KEY_PGV_MODE = "PGV_MODE_STRING";
        /** PHASE_STRING */
        public static final String KEY_PHASE = "PHASE_TYPE_STRING";
        /** PORT_LOGICAL_STATE_STRING */
        public static final String KEY_PORT_LOGICAL_STATE = "PORT_LOGICAL_STATE_STRING";
        /** PORT_IDRW_MODE_STRING */
        public static final String KEY_PORT_IDRW_MODE = "PORT_IDRW_MODE_STRING";
        /** PORT_TYPE_STRING */
        public static final String KEY_PORT_TYPE = "PORT_TYPE_STRING";
        /** PROCESS_METHOD_TYPE_STRING */
        public static final String KEY_PROCESS_METHOD_TYPE = "PROCESS_METHOD_TYPE_STRING";
        /** PROCESS_TYPE_STRING */
        public static final String KEY_PROCESS_TYPE = "PROCESS_TYPE_STRING";
        /** TRACE_LOG_LEVEL_STRING */
        public static final String KEY_TRACE_LOG_LEVEL = "TRACE_LOG_LEVEL_STRING";
        /** TRANS_MODE_STRING */
        public static final String KEY_TRASN_MODE = "TRANS_MODE_STRING";
        /** UNIT_LOGICAL_STATE_STRING */
        public static final String KEY_UNIT_LOGICAL_STATE = "UNIT_LOGICAL_STATE_STRING";
        /** USER_ACCOUNT_STATE_STRING */
        public static final String KEY_USER_ACCOUNT_STATE = "USER_ACCOUNT_STATE_STRING";
        /** VEHICLE_KIND_STRING */
        public static final String KEY_VEHICLE_KIND = "VEHICLE_KIND_STRING";
        /** VIP_STATE_STRING */
        public static final String KEY_VIP_STATE = "VIP_STATE_STRING";
        /** ZONE_TYPE_STRING */
        public static final String KEY_ZONE_TYPE = "ZONE_TYPE_STRING";
        /** CONTROLLER_TYPE_STRING */
        public static final String KEY_CONTROLLER_TYPE = "CONTROLLER_TYPE_STRING";
        /** JOB_TYPE_STRING */
        public static final String KEY_JOB_TYPE = "JOB_TYPE_STRING";
        /** SELECT_PRIORITY_STRING **/
        public static final String KEY_SELECT_PRIORITY_STRING = "SELECT_PRIORITY_STRING";
        /** TABLE_NO_STRING **/
        public static final String KEY_TABLE_NO_STRING = "TABLE_NO_STRING";
        /** ROUTE_FUNC_OPT_STRING **/
        public static final String KEY_ROUTE_FUNC_OPT_STRING = "ROUTE_FUNC_OPT_STRING";
        /** OHB_TYPE_STRING */
        public static final String KEY_OHB_TYPE = "OHB_TYPE_STRING";
        /** RECV_TRN_ID */
        public static final String KEY_RECV_TRN_ID = "RECV_TRN_ID";
        /** SEND_TRN_ID */
        public static final String KEY_SEND_TRN_ID = "SEND_TRN_ID";
        /** ALARM_REPORT_HISTORY_RESULT_STRING */
        public static final String KEY_ALARM_REPORT_HISTORY_RESULT_STRING = "ALARM_REPORT_HISTORY_RESULT_STRING";
        /** SELECT_CONTENT_STRING */
        public static final String KEY_SELECT_CONTENT_STRING = "SELECT_CONTENT_STRING";
        /** SELECT_SUBJECT_STRING MACS4#0027 Add */
        public static final String KEY_SELECT_SUBJECT_STRING = "SELECT_SUBJECT_STRING";
        /** M17_SELECT_TARGET_STRING MACS4#0059 Add */
        public static final String KEY_M17_SELSECT_TARGET_STRING = "M17_SELECT_TARGET_STRING";
    }

    /**
     * 統計 時間種別
     */
    public class StatsIntervalType {
        /** 分 */
        public static final int INTERVAL_TYPE_MIN = 0;
        /** 時 */
        public static final int INTERVAL_TYPE_HOUR = 1;
        /** 日 */
        public static final int INTERVAL_TYPE_DAY = 2;
        /** 月 */
        public static final int INTERVAL_TYPE_MONTH = 3;
    }

    public class CacheId {
        /** ALT_DEVICE */
        public static final String ALT_DEVICE = "ALT_DEVICE";
        /** AMHS */
        public static final String AMHS = "AMHS";
        /** AMHS_ALIAS */
        public static final String AMHS_ALIAS = "AMHS_ALIAS";
        /** AMHS_STATE */
        public static final String AMHS_STATE = "AMHS_STATE";
        /** ASTAR_HEURISTIC_COST */
        public static final String ASTAR_HEURISTIC_COST = "ASTAR_HEURISTIC_COST";
        /** CARRIER_SHAPE */
        public static final String CARRIER_SHAPE = "CARRIER_SHAPE";
        /** CARRIER_SHAPE_RLT */
        public static final String CARRIER_SHAPE_RLT = "CARRIER_SHAPE_RLT";
        /** CARRIER_TYPE */
        public static final String CARRIER_TYPE = "CARRIER_TYPE";
        /** DUAL_PORT */
        public static final String DUAL_PORT = "DUAL_PORT";
        /** CONNECT_ALARM_SERVER **/
        public static final String CONNECT_ALARM_SERVER = "CONNECT_ALARM_SERVER";
        /** EJECT_DEVICE */
        public static final String EJECT_DEVICE = "EJECT_DEVICE";
        /** EXERCISE */
        public static final String EXERCISE = "EXERCISE";
        /** EXERCISE_MASTER */
        public static final String EXERCISE_MASTER = "EXERCISE_MASTER";
        /** EXERCISE_PATTERN */
        public static final String EXERCISE_PATTERN = "EXERCISE_PATTERN";
        /** FLOOR_TRANSFER_CONFIG */
        public static final String FLOOR_TRANSFER_CONFIG = "FLOOR_TRANSFER_CONFIG";
        /** FLOOR_TRANSFER_PATTERN */
        public static final String FLOOR_TRANSFER_PATTERN = "FLOOR_TRANSFER_PATTERN";
        /** GRP */
        public static final String GRP = "GRP";
        /** GRP_RLT */
        public static final String GRP_RLT = "GRP_RLT";
        /** HOST_GRP */
        public static final String HOST_GRP = "HOST_GRP";
        /** HOST_GRP_RLT */
        public static final String HOST_GRP_RLT = "HOST_GRP_RLT";
        /** HOST */
        public static final String HOST = "HOST";
        /** HOST_ALARM_DEFINE MACS4#0001 Add */
        public static final String HOST_ALARM_DEFINE = "HOST_ALARM_DEFINE";
        /** HOST_ALARM_INFO */
        public static final String HOST_ALARM_INFO = "HOST_ALARM_INFO";
        /** HOST_ALARM_MASTER MACS4#0001 Add */
        public static final String HOST_ALARM_MASTER = "HOST_ALARM_MASTER";
        /** HOST_ALIAS */
        public static final String HOST_ALIAS = "HOST_ALIAS";
        /** HOST_REPORT_INFO */
        public static final String HOST_REPORT_INFO = "HOST_REPORT_INFO";
        /** HOST_STATE */
        public static final String HOST_STATE = "HOST_STATE";
        /** LOG_CODE_MASTER */
        public static final String LOG_CODE_MASTER = "LOG_CODE_MASTER";
        /** MCS */
        public static final String MCS = "MCS";
        /** MCS_ALARM */
        public static final String MCS_ALARM = "MCS_ALARM";
        /** MCS_CONSTS */
        public static final String MCS_CONSTS = "MCS_CONSTS";
        /** MCS_RET_CODE */
        public static final String MCS_RET_CODE = "MCS_RET_CODE";
        /** MICRO_CMD_ROUTE */
        public static final String MICRO_CMD_ROUTE = "MICRO_CMD_ROUTE";
        /** MODULE */
        public static final String MODULE = "MODULE";
        /** OHB */
        public static final String OHB = "OHB";
        /** OHB_PORT_RLT */
        public static final String OHB_PORT_RLT = "OHB_PORT_RLT";
        /** OHBIF_AREA */
        public static final String OHBIF_AREA = "OHBIF_AREA";
        /** OHBIF_AREA_CONTROLLER */
        public static final String OHBIF_AREA_CONTROLLER = "OHBIF_AREA_CONTROLLER";
        /** OHBIF_AREA_IFOHB */
        public static final String OHBIF_AREA_IFOHB = "OHBIF_AREA_IFOHB";
        /** OHBIF_JDG */
        public static final String OHBIF_JDG = "OHBIF_JDG";
        /** PORT */
        public static final String PORT = "PORT";
        /** PORT_CONN_UNIT */
        public static final String PORT_CONN_UNIT = "PORT_CONN_UNIT";
        /** PORT_STATE */
        public static final String PORT_STATE = "PORT_STATE";
        /** PROC_EVENT */
        public static final String PROC_EVENT = "PROC_EVENT";
        /** ROUTE */
        public static final String ROUTE = "ROUTE";
        /** ROUTE_DETAIL */
        public static final String ROUTE_DETAIL = "ROUTE_DETAIL";
        /** ROUTE_PATTERN_RLT */
        public static final String ROUTE_PATTERN_RLT = "ROUTE_PATTERN_RLT";
        /** STOCKER */
        public static final String STOCKER = "STOCKER";
        /** STRING_MASTER */
        public static final String STRING_MASTER = "STRING_MASTER";
        /** TOOL_BUFFER_PORT */
        public static final String TOOL_BUFFER_PORT = "TOOL_BUFFER_PORT";
        /** TRACE_LOG_LEVEL */
        public static final String TRACE_LOG_LEVEL = "TRACE_LOG_LEVEL";
        /** TRANS_MODE */
        public static final String TRANS_MODE = "TRANS_MODE";
        /** UNIT */
        public static final String UNIT = "UNIT";
        /** ZONE */
        public static final String ZONE = "ZONE";
        /** ZONE_CARRIER_TYPE_RLT */
        public static final String ZONE_CARRIER_TYPE_RLT = "ZONE_CARRIER_TYPE_RLT";
    }

    public class CacheOperator {
        /** 全レコード再読み込み */
        public static final int RELOAD_OPERATION = 4;
    }
}
