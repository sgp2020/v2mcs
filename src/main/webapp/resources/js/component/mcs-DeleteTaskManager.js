/**
 ******************************************************************************
 * @file        mcs-DeleteTaskManager.js
 * @brief       レコードの削除処理を管理するクラス
 * @par
 * @author      CSC
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2016 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=2
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                                    AUTHOR
 * ----------------------------------------------------------------------------
 * 2016/12/26 0.1         Step1リリース                                     CSC
 ******************************************************************************
 */
/**
 ******************************************************************************
 * @brief    レコードの削除処理を管理する。
 * @return
 * @retval
 * @attention
 * @note     レコードの削除処理を管理する。
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
var McsDeleteTaskManager = function() {
};

// メソッド類
McsDeleteTaskManager.prototype = {
  /**
   ******************************************************************************
   *
   * @brief タスクを開始する。
   * @param {Object} options オプション
   * @return
   * @retval
   * @attention
   * @note タスクを開始する。
   * optionsには下記の通りにオプションを指定する。
   * <pre>
   *   {
   *     url: {String} Ajaxする先のURL
   *     successMessage: {String} 成功時のダイアログメッセージ。
   *                              メッセージ中の%1と%2は、タスクのインデックスと総タスク数に
   *                              置換される。
   *                              未指定の場合はデフォルトのメッセージが表示される。
   *     completeMessage: {String} 全タスク完了時のダイアログメッセージ。
   *                               未指定の場合はデフォルトのメッセージが表示される。
   *     whereMapList: {Array.Object} 削除対象のwhere句の配列。
   *     onError: {Function} エラーになった時のコールバック
   *     onComplete: {Function} タスク完了時のコールバック。
   *                            指定された数のタスクが完了したときに呼ばれる。
   *                            その際、引数にエラーが発生した個数が渡される。
   *   }
   * </pre>
   * whereMapListについては、オブジェクトのキー名がテーブルのカラム名、<br>
   * オブジェクトの値が対象の値となる。<br>
   * たとえば CAR テーブルの主キーが CATEGORY と CAR_NAME の時、<br>
   * 以下のように指定するとSQLで where CATEGORY='Minivan' and CAR_NAME='PRESAGE' となる。
   * <pre>
   *   whereMapList: [{CATEGORY: 'Minivan', CAR_NAME: 'PRESAGE'}]
   * </pre>
   *
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  start: function(options) {
    var taskCount = options.whereMapList.length;
    var whereMapList = options.whereMapList;
    var successMessage = options.successMessage;
    var completeMessage = options.completeMessage;

    var dialogManagerOptions = {
      url: options.url, // Ajax先URL
      taskCount: taskCount, // タスクの数
      successMessage: successMessage, // 成功時のメッセージ
      completeMessage: completeMessage, // 全完了時のメッセージ
      whereMapList: whereMapList, // 削除リスト（キャリア一覧の削除対象キャリアID表示にのみ使用）
      onTask: function(index) {
        // タスク時。ここでAjaxする値を生成する
        return {
          'delete': [whereMapList[index]]
        // 一つずつ渡す（ただし、配列）
        };
      },
      onBulkTask: function(startIndex, endIndex) {
        // 一括実行時。
        // まとめて渡すための配列を作成
        var list = [];
        for (var i = startIndex; i <= endIndex; i++) {
          list.push(whereMapList[i]);
        }
        return {
          'delete': list
        };
      },
      onSuccess: function(data) {
        // 成功時
        // 何もしない
      },
      //onError: function() {
      // キャリア削除の全処理完了ダイアログへ処理結果件数表示のため引数へオブジェクト追加
      onError: function(data) {
        // 失敗時
        if (options.onError) {
          options.onError();
        }
      },
      onComplete: function() {
        // 全タスク完了時
        if (options.onComplete) {
          options.onComplete();
        }
      }
    };
    // 一括ダイアログ管理で実行
    var bulkDialogManager = new McsBulkDialogManager();
    bulkDialogManager.start(dialogManagerOptions);
  }
};
