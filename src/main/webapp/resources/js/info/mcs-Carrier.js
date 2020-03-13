/**
 ******************************************************************************
 * @file        mcs-Carrier.js
 * @brief       キャリア情報表示画面用JavaScript
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
 * 2018/11/26 MACS4#0056  EQP In/Out専用の設定対応                    T.Iga/CSC
 * 2018/11/28 MACS4#0047  GUI要望分                                   T.Iga/CSC
 ******************************************************************************
 */
$(function() {
  // 画面初期化時の処理

  // コンポーネントマネージャ（検索用）
  var searchComp = new McsComponentManager();
  // コンポーネントマネージャ（搬送ジョブ用）
  var jobComp = new McsComponentManager();
  // コンポーネントマネージャー（搬送ジョブ追加用）
  var jobAddComp = new McsComponentManager();
  // 削除マネージャー
  var deleteTaskManager = new McsDeleteTaskManager();
  // データテーブル
  var dataTables = new McsDataTables($('#lst-table-target'), true);
  // 追加テーブル
  var addbinComp = new McsComponentManager();
  var addohbComp = new McsComponentManager();

  /**
   * 修正用ID配列
   */
  var modifyArrayId = [];

  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;

  // 検索実行前か後かを判定するためのフラグ
  var firstSearchFlag = true;

  // 初回検索
  extract({
	  currentTscId: '',
	  carrierId: '',
	  state: '',
	  sfrom: null,
	  sto: null,
	  ifrom: null,
	  ito: null
  });

  /**
   ******************************************************************************
   * @brief   抽出して画面へ表示する
   * @param   {Object} cond 抽出条件（そのままサーバへ送信されるJSON）
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function extract(cond) {
    searchComp.clearErrors();
    dataTables.getDataAjax({
      url: getUrl('/Carrier/GetCarrierList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'v2I-002-dataTables', // テーブルコンポーネントID
      success: function(data) {
        // 特にすることなし
        if (retFlag) {
          // 戻るボタンが押されたときは閉じない
          // 戻るボタン用フラグを下す
          retFlag = false;
          return;
        }
      },
      serverError: function(data) {
        // 特にすることなし
        searchComp.setErrors(data.result.errorInfoList);
      },
      ajaxError: function(status, error) {
        // 特にすることなし
      }
    });
  }

  // 各ボタンの生成
  // 一覧
  var search = new McsButton($('#menu-btn-search'), screenText.list.search);
  var update = new McsButton($('#menu-btn-update'), screenText.list.update);
  var createJob = new McsButton($('#menu-btn-createJob'), screenText.list.createTransferJob);
  var addbin = new McsButton($('#menu-btn-addbin'), screenText.list.addbin);
  var addohb = new McsButton($('#menu-btn-addohb'), screenText.list.addohb);
  var del = new McsButton($('#menu-btn-delete'), screenText.list.del);
  var save = new McsButton($('#menu-btn-save'), screenText.list.save);
  var group = new McsButton($('#menu-btn-group'), screenText.list.group);
  var ret = new McsButton($('#menu-btn-cancel'), screenText.list.ret);

  // 搬送Job作成テーブル
  var carrierInformation = new McsTable($('#mcs-createjob-entrydata'));
  var groupInfor = new McsTable($('#mcs-groupInfor'));

  /**
   * 各スライドを生成
   */
  // Top
  var slideMenuTop = McsSlideMenu.primaryMenuSlide;
  // 検索
  var slideMenuSearch = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-search')
  });
  // 搬送Job作成用 親メニュー
  var slideMenuCreateJob = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-slideMenu-createjob')
  });
  // 搬送Job作成用 子メニュー
  var slideMenuTransportCarrier = new McsSlideMenu({
    depth: 2,
    parent: slideMenuCreateJob,
    slideDiv: $('#mcs-slideMenu-transportCarrier')
  });
  //Group
  var groupMenu = new McsSlideMenu({
    depth: 1,
    parent: slideMenuTop,
    slideDiv: $('#mcs-groupMenu')
  });
  // CSV保存用スライドの初期化
  var saveMenu = new McsSlideMenu({
    depth: 1,
    parent: null,
    slideDiv: $('#mcs-saveMenu')
  });
  var addbinMenu = new McsSlideMenu({
	    depth: 1,
	    parent: slideMenuTop,
	    slideDiv: $('#mcs-addbinMenu')
	  });
  var addohbMenu = new McsSlideMenu({
	    depth: 1,
	    parent: slideMenuTop,
	    slideDiv: $('#mcs-addohbMenu')
	  });
  
  /**
   * ダイアログの生成
   */
  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);
  var warningDialog = new McsDialog($('#mcs-warning-dialog'), window.mcsDialogTitleError);
  var informationDialog = new McsDialog($('#mcs-information-dialog'), window.mcsDialogTitleInfo);
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);

  // 操作部のスライド生成
  createList();
  // 詳細部のスライド生成
  createSearchSlide();
  createJobSlide();
  createAddJob();
  // CSV保存用スライドの生成
  saveCsvSlide();
  groupSlide();
  addbinSlide();
  addohbSlide();

  /**
   ******************************************************************************
   * @brief   一覧画面及びスライド生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createList() {
    // 各ボタンの表示・非表示を設定
//    addConfirm.hide();
//    addRet.hide();

    // 各ボタンのイベント登録
    // 検索ボタン押下
    search.onClick(function() {
      // 画面の内容を消去
      searchComp.get('currentTscId').clear();
      searchComp.get('carrierId').clear();
      searchComp.get('state').clear();
      searchComp.get('sfrom').clear();
      searchComp.get('sto').clear();
      searchComp.get('ifrom').clear();
      searchComp.get('ito').clear();
      searchComp.clearErrors();

      // 前回の条件を復元
      var datas = dataTables.getLatestCond();

      searchComp.get('currentTscId').setValue(datas.currentTscId);
      searchComp.get('carrierId').setValue(datas.carrierId);
      searchComp.get('state').setValue(datas.state);
      searchComp.get('sfrom').setValue(datas.sfrom);
      searchComp.get('sto').setValue(datas.sto);
      searchComp.get('ifrom').setValue(datas.ifrom);
      searchComp.get('ito').setValue(datas.ito);

      slideMenuSearch.show();
    });
    // 再表示ボタン押下
    update.onClick(function() {
      dataTables.reload();
      retFlag = true;
    });
    // 搬送ジョブ作成押下
    createJob.onClick(function() {
      jobComp.get('jobPriority').clear();
      jobComp.get('jobPriority').setValue(1);

      jobComp.get('controller').clear();
      jobComp.get('port').clear();
      jobComp.clearErrors();
      carrierInformation.clearErrors();
      carrierInformation.clear();

      // データの準備
      var datas = dataTables.getSelectedRowData();
      var carrierList = [];
      // スクリーンの表示・非表示
      if (datas !== null) {
//        selectDataLength = datas.length;
//        for (var i = 0; i < selectDataLength; i++) {
//          // キャリアJobID情報が存在するか判定
//          if (datas[i].carrierJobId === undefined  || datas[i].carrierJobId.length == 0) {
//            carrierInformation.addData({
//                carrierId: datas[i].carrierId,
//                fromAmhsId: datas[i].currentTscId,
//                fromPort: datas[i].currentLocation,
//                fromCzone: datas[i].currentZone,
//                carrierIdHidden: datas[i].carrierId,
//                fromAmhsIdHidden: datas[i].currentTscId,
//                fromPortHidden: datas[i].currentLocation,
//                fromCzoneHidden: datas[i].currentZone,
//                inputTypeHidden: "1"
//            });
//          } else {
//            carrierList.push(datas[i].carrierId);
//          }
//        }
	        carrierInformation.addData({
	        carrierId: datas[0].carrierId,
	        fromAmhsId: datas[0].currentTscId,
	        fromPort: datas[0].currentLocation,
	        fromCzone: datas[0].currentZone,
	        carrierIdHidden: datas[0].carrierId,
	        fromAmhsIdHidden: datas[0].currentTscId,
	        fromPortHidden: datas[0].currentLocation,
	        fromCzoneHidden: datas[0].currentZone,
	        inputTypeHidden: "1"
	    });
      }
      if (carrierList.length > 0) {
        var message = screenText.dialog.carrierTableAddError;
        for (var i = 0; i < carrierList.length; i++) {
          message = message + "\n *" + carrierList[i];
        }
        warningDialog.openAlertProc(message, window.mcsDialogBtnOk, 'alert', function () {
          slideMenuCreateJob.show();
          carrierInformation.resizeColWidth();
        });
      } else {
        slideMenuCreateJob.show();
        carrierInformation.resizeColWidth();
      }

    });

    // 追加ボタン押下
	addbin.onClick(function() {
	      // 画面の内容を消去
	      addbinComp.get('carrierId').clear();
	      addbinComp.get('stk').clear();
//	      addbinComp.get('stk').setList(screenValue.addbinStkId);
	      addbinComp.get('bin1').clear();
	      addbinComp.get('bin2').clear();
	      addbinComp.get('bin3').clear();
	      addbinComp.clearErrors();
	      addbinMenu.show();
	  });
	addohb.onClick(function() {
	      // 画面の内容を消去
	      addohbComp.get('carrierId').clear();
	      addohbComp.get('ohb').clear();
	      addohbComp.get('bin').clear();
	      addohbComp.get('bin').setList(screenValue.addohbPortId);
	      addohbComp.clearErrors();
	      addohbMenu.show();
	  });

	
    // 削除ボタン押下
    del.onClick(function() {
      var datas = dataTables.getSelectedRowData();
      if (datas === null) {
        errorDialog.openAlert(screenText.dialog.listNotSelect, screenText.dialog.listRet, 'alert');
        return;
      }
      // 確認ダイアログ
      // 先頭の削除対象レコード確認メッセージ組み立て
      var delCfmMsg3 = screenText.dialog.delCfmMsg3;
      var delId = "\n" + datas[0].carrierId;
      for (var i = 1; i < datas.length; i++) {
    	  delId = delId + "\n" + datas[i].carrierId;
      }
      
//      delCfmMsg3 = delCfmMsg3.replace(/%3/, datas[0].carrierId);
      delCfmMsg3 = delCfmMsg3.replace(/%3/, delId);
//      confirmDialog.openConfirm(screenText.dialog.delCfmMsg1 + '\n' + screenText.dialog.delCfmMsg2 + '\n\n' + delCfmMsg3,
      confirmDialog.openConfirm(delCfmMsg3,screenText.dialog.delCfm, screenText.dialog.delRet,
          'confirm', function(result) {
            if (result) {
              // 決定ボタン押下時
              // 選択されているCARRIER IDを取得
              var delrows = [];
              for (var i = 0; i < datas.length; i++) {
            	  delrows.push({
            		  carrierId: datas[i].carrierId,
            		  carrierState: datas[i].carrierState
                });
              }
              var cond = { delList: delrows };
              var url1 = getUrl('/Carrier/BeforExeDeleteCarrierList');
              var url2 = getUrl('/Carrier/ExeDeleteCarrierList');
              var complete = function() {
                  // 全完了時の動作
                  listReload();
              };
              var success1 = function(result1s) {
            	  if (result1s.result.message === ''){
            		  callAjax(url2, JSON.stringify(cond), false, success2, error2);
            	  } else {
            		  confirmDialog.openConfirm(result1s.result.message,screenText.dialog.delCfm,screenText.dialog.delRet, 'confirm',
                          function(result) {
    	                      if (result) {
    	                      	callAjax(url2, JSON.stringify(cond), false, success2, error2);
    	                      }
                          });
            	  }
              };
              var success2 = function(result2s) {
                    // 成功時の処理
                    informationDialog.openAlertProc(result2s.result.message, screenText.dialog.createjobRet, 'info',
                        function() {
                            complete();
                        });
              };
              var error1 = function(result1e) {

              };
              var error2 = function(result2e) {
                    // 失敗時の処理
                    //jobComp.setErrors(result2e.result.errorInfoList);
              };
              
              callAjax(url1, JSON.stringify(cond), false, success1, error1);             
            } else {
              // 戻るボタン押下時
              // 何もしない
            }
          });
    });

    // 保存ボタン押下
    save.onClick(function() {
      if (!firstSearchFlag) {
        saveMenu.show();
      } else {
        errorDialog.openAlert(screenText.dialog.listSave, screenText.dialog.listRet, 'alert');
      }
    });
    
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuTop.hide();
    });

  }

  /**
   ******************************************************************************
   * @brief   一覧再表示処理
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function listReload() {
    // dataTablesのオートリロード再開
    dataTables.setAutoReloadEnabled(true);
    // スクリーンの表示・非表示
    $('#mcs-subtitle-list').show();
    $('#list-screen').show();

    // スライドのボタンの表示・非表示
    search.show();
    update.show();
    createJob.show();
    addbin.show();
    addohb.show();
    del.show();
    save.show();
    group.show();
    ret.show();

    // dataTablesのリロード
    dataTables.reload();
  }

  /**
   ******************************************************************************
   * @brief   検索メニュー生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分                                              T.Iga/CSC
   ******************************************************************************
   */
  function createSearchSlide() {
    // 検索項目の生成
	var currentTscId = new McsSelectBox($('#mcs-search-currentTscId'));
    var carrierId = new McsTextBox($('#mcs-search-CarrierID'));
    var state = new McsSelectBox($('#mcs-search-State'));
    var stockInFrom = new McsDateTime($('#mcs-search-sFrom'), screenText.slideSearch.sstart, 75);
    var stockInTo = new McsDateTime($('#mcs-search-sTo'), screenText.slideSearch.send, 75);
    var insystemFrom = new McsDateTime($('#mcs-search-iFrom'), screenText.slideSearch.istart, 75);
    var insystemTo = new McsDateTime($('#mcs-search-iTo'), screenText.slideSearch.iend, 75);    
    var extract = new McsButton($('#mcs-search-extract'), screenText.slideSearch.extract);
    var clear = new McsButton($('#mcs-search-clean'), screenText.slideSearch.clear);
    var ret = new McsButton($('#mcs-search-cancel'), screenText.slideSearch.ret);

    // コンポーネントマネージャーに各検索項目を入れる
    searchComp.add('currentTscId', currentTscId);
    searchComp.add('carrierId', carrierId);
    searchComp.add('state', state);
    searchComp.add('sfrom', stockInFrom);
    searchComp.add('sto', stockInTo);
    searchComp.add('ifrom', insystemFrom);
    searchComp.add('ito', insystemTo);
    // 各イベントの設定
    // キャリアのテキスト
    carrierId.setMaxLength(64);
    currentTscId.setList(screenValue.searchCurrentTscId);
    state.setList(screenValue.searchCarrierState);
    // ロケーション
    // 何もしない
    // ストックインタイム
    // 何もしない
    // 抽出ボタン押下
    extract.onClick(function() {
      // エラー解除
      searchComp.clearErrors();
      // 検索処理
      var url = getUrl('/Carrier/GetCarrierList');
      var cond = {
    	currentTscId: currentTscId.getValue(),
        carrierId: carrierId.getValue(),
        state: state.getValue(),
        sfrom: stockInFrom.getValue(),
        sto: stockInTo.getValue(),
        ifrom: insystemFrom.getValue(),
        ito: insystemTo.getValue()
      };
      var tableCompId = 'v2I-002-dataTables';
      var options = {
        url: url,
        cond: cond,
        searchDataFlag: true,
        tableCompId: tableCompId,
        success: function() {
          // 検索成功時
          if (retFlag) {
            // 戻るボタン押下時
            // 戻るボタン用フラグを下す
            retFlag = false;
            return;
          }
          firstSearchFlag = false;
        },
        serverError: function(result) {
          // 検索失敗時
          searchComp.setErrors(result.result.errorInfoList);
        },
        ajaxError: function() {
          // Ajaxエラー時
        }

      };
      dataTables.getDataAjax(options);
    });

    // クリアボタン押下
    clear.onClick(function() {
      // 各項目を初期化する
    	currentTscId.clear();
    	currentTscId.clearError();
		carrierId.clear();
		carrierId.clearError();
		state.clear();
		state.clearError();
		stockInFrom.clear();
		stockInFrom.clearError();
		stockInTo.clear();
		stockInTo.clearError();
		insystemFrom.clear();
		insystemFrom.clearError();
		insystemTo.clear();
		insystemTo.clearError();
    });

    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuSearch.hide();
    });

    /**
     * 検索メニュー生成終了
     */
  }

  /**
   ******************************************************************************
   * @brief   搬送ジョブ作成スライド生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function createJobSlide() {
    // 登録項目の生成
    var add = new McsButton($('#mcs-createjob-add'), screenText.slideCreatejob.add);
    var del = new McsButton($('#mcs-createjob-del'), screenText.slideCreatejob.del);
    var jobPriority = new McsTextBox($('#mcs-createjob-jobpriority'), 'number');
    var to = new McsTransferLocation($('#mcs-createjob-to-location'), 2);
    var confirm = new McsButton($('#mcs-createjob-entry'), screenText.slideCreatejob.confirm);
    var ret = new McsButton($('#mcs-createjob-cancel'), screenText.slideCreatejob.ret);

    console.log(to.getValue().value1);
    console.log(to.getValue().value2);
    
    // 搬送ジョブ作成用のコンポーネントマネージャーに設定する
    jobComp.add('jobPriority', jobPriority);
    jobComp.add('controller', to.getTextBoxList()[0]);
    jobComp.add('port', to.getTextBoxList()[1]);

    // 各イベントの設定
    // キャリアインフォメーションテーブル
    carrierInformation.setHeader([{
      name: 'carrierId',
      text: screenText.slideCreatejob.carrierId,
      display: true
    }, {
        name: 'fromAmhsId',
        text: screenText.slideCreatejob.fromAmhsId,
        display: true
    }, {
      name: 'fromPort',
      text: screenText.slideCreatejob.fromPort,
      display: true
    }, { 	
      name: 'fromCzone',
      text: screenText.slideCreatejob.fromCzone,
      display: true
    }, {
      name: 'carrierIdHidden',
      text: screenText.slideCreatejob.carrierId,
      display: false
    }, {
        name: 'fromAmhsIdHidden',
        text: screenText.slideCreatejob.fromAmhsId,
        display: false
    }, {
      name: 'fromPortHidden',
      text: screenText.slideCreatejob.fromPort,
      display: false
    }, {
        name: 'fromCzoneHidden',
        text: screenText.slideCreatejob.fromCzone,
        display: false
    }, {
      name: 'inputTypeHidden',
      text: "",
      display: false
    }]);
    
    // 追加ボタン押下
    add.onClick(function() {
      jobAddComp.get('carrierId').clear();
      jobAddComp.get('controller').clear();
      jobAddComp.get('port').clear();
      jobAddComp.clearErrors();
      carrierInformation.clearErrors();
      slideMenuTransportCarrier.show();
    });
    // 削除ボタン押下
    del.onClick(function() {
      var delNum = carrierInformation.delSelectedRowData();
      if (delNum == 0) {
        warningDialog.openAlert(screenText.dialog.createjobNotSelect, screenText.dialog.createjobRet, 'alert');
      }
    });
    // ジョブプライオリティ
    jobPriority.setMaxLength(2);
    // Toロケーション
    to.setEditable(false);
    // 決定ボタン
    confirm.onClick(function() {
      // エラー解除
      jobComp.clearErrors();
      carrierInformation.clearErrors();
      // 登録処理
      confirmDialog.openConfirm(screenText.dialog.createjobSaveData, screenText.dialog.createjobConfirm,
          screenText.dialog.createjobRet, 'confirm', function(flag, dialog) {
            if (flag) {
              // OKボタン押下時
              // 処理続行
              register();
            } else {
              // キャンセルボタン押下時
              // 処理中断
              return;
            }
          });

      /**
       * 登録処理
       */
      function register() {
        // テーブルのレコードを取得
        var carrierList = carrierInformation.getValues();
        if (carrierList.length === 0) {
          // キャリアが存在しない場合
          carrierInformation.addError();
          warningDialog.openAlert(screenText.dialog.createjobNotData, McsBulkDialogManager.language.DLGBTN.BTNOK,
              'alert');
          return;
        }

        var url = getUrl('/Carrier/ExeAddTransferCarrier');
        var cond = [];
        for (var i = 0; i < carrierList.length; i++) {
          var data = {
            carrierId: carrierList[i].carrierId,
            fromAmhsId: carrierList[i].fromAmhsId,
            fromPort: carrierList[i].fromPort,
            fromCzone: carrierList[i].fromCzone,
            jobPriority: jobPriority.getValue(),
            controller: to.getValue().value1,
            port: to.getValue().value2,
            inputType: carrierList[i].inputTypeHidden,
            codeBrowser: 1
          };
          cond.push(data);
        }

        // 登録処理
        var currentIndex = 0;
        var complete = function() {
          // 全完了時の動作
          listReload();
        };
        var success;
        var error;
        success = function(resultj) {
          // 成功時の処理
          informationDialog.openAlertProc(screenText.dialog.createjobComplete, screenText.dialog.createjobRet, 'info',
              function() {
                // OK押下時
                // 対象行をテーブルから削除
                carrierInformation.delRow(0);
                // 次の行の登録を実施
                currentIndex++;
                if (cond.length > currentIndex) {
                  callAjax(url, JSON.stringify(cond[currentIndex]), false, success, error);
                } else {
                  complete();
                }
              });
        };
        error = function(result) {
          // 失敗時の処理
          jobComp.setErrors(result.result.errorInfoList);
        };
        // 初回（1行目）の登録を実施
        callAjax(url, JSON.stringify(cond[currentIndex], null, 4), false, success, error);
      }
    });
    // 戻るボタン押下
    ret.onClick(function() {
      slideMenuCreateJob.hide();
    });
  }

  /**
   ******************************************************************************
   * @brief   搬送ジョブ追加スライド生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0056  EQP In/Out専用の設定対応                               T.Iga/CSC
   ******************************************************************************
   */
  function createAddJob() {
    // ボタンの生成
    var carrierId = new McsTextBox($('#mcs-transportCarrier-carrierId'));
    var fromLocation = new McsTransferLocation($('#mcs-transportCarrier-from-location'), 1, [4]);   // MACS4#0056 Mod [ioMode] 2 → 1

    var confirm = new McsButton($('#mcs-transportCarrier-ok'), screenText.slideAddCarrier.confirm);
    var ret = new McsButton($('#mcs-transportCarrier-cancel'), screenText.slideAddCarrier.ret);

    // コンポーネントマネージャーに各項目を入れる
    jobAddComp.add('carrierId', carrierId);
    jobAddComp.add('controller', fromLocation.getTextBoxList()[0]);
    jobAddComp.add('port', fromLocation.getTextBoxList()[1]);

    // 各イベントの設定
    // キャリアID
    carrierId.setMaxLength(64);
    // ロケーション
    fromLocation.setEditable(false);
    // ゾーンのセレクトボックス
    // confirmボタン
    confirm.onClick(function() {
      // エラーを解除
      jobAddComp.clearErrors();

      var errorInfoList = [];
      // carreirIdのテキストボックスのブランクをチェック
      var carrierText = carrierId.getValue();
      if (carrierText.replace(/ /g, '') == '') {
        var carrierErrorInfo = {
          id: 'carrierId',
          errorMessage: screenText.validate.carrierId,
          errorValue: carrierId.getValue()
        };

        errorInfoList.push(carrierErrorInfo);
      }
      // carreirIdのテキストボックスのバイト長をチェック
      var byteLength = encodeURI(carrierText).replace(/%[0-9A-F]{2}/g, '*').length;
      if (byteLength > 64) {
        // エラーメッセージ生成
        var errorMessage = screenText.validate.carrierIdByteRange;
        errorMessage = errorMessage.replace(/{min}/g, '0');
        errorMessage = errorMessage.replace(/{max}/g, '64');
        var carrierErrorInfo = {
          id: 'carrierId',
          errorMessage: errorMessage,
          errorValue: carrierId.getValue()
        };

        errorInfoList.push(carrierErrorInfo);
      }
      // 追加済みデータのcarreirIdとの重複をチェック
      var addCarrierInfoList = carrierInformation.getValues();
      for (var i = 0; i < addCarrierInfoList.length; i++) {
        if (carrierText == addCarrierInfoList[i].carrierId) {
          var errorMessage = screenText.validate.Duplication;
          errorMessage = errorMessage.replace(/{.}/g, screenText.validate.carrierIdDuplicationMes);
          var carrierErrorInfo = {
            id: 'carrierId',
            errorMessage: errorMessage,
            errorValue: carrierId.getValue()
          };
          errorInfoList.push(carrierErrorInfo);
        }
      }
      
      // fromLocationをチェック
//      if (!fromLocation.getValue().value1) {
//        // エラー情報をセット
//        var fromErrorInfo = {
//          id: 'controller',
//          errorMessage: screenText.validate.fromLocation,
//          errorValue: fromLocation.getValue().value1
//        };
//        errorInfoList.push(fromErrorInfo);
//      }

      // エラーがあった場合
      if (errorInfoList.length !== 0) {
        warningDialog.openAlertProc(screenText.dialog.carrierAddError, screenText.dialog.carrierAddRet, 'alert',
            function() {
              jobAddComp.setErrors(errorInfoList);
            });
        return;
      }

      // テーブルにデータセット
      carrierInformation.addData({
        carrierId: carrierId.getValue(),
        fromAmhsId: "",     
        fromPort: fromLocation.getValue().value2,
        fromCzone: fromLocation.getValue().value1,
        carrierIdHidden: carrierId.getValue(),
        fromAmhsIdHidden: fromLocation.getValue().value,
        fromPortHidden: fromLocation.getValue().value,
        fromCzoneHidden: fromLocation.getValue().value,

        inputTypeHidden: "0"
      });

      //追加スライドを閉じる
      slideMenuTransportCarrier.hide();
    });
    // 戻るボタン押下時
    ret.onClick(function() {
      slideMenuTransportCarrier.hide();
    });
  }

  /**
   ******************************************************************************
   * @brief   CSV保存用スライドを生成
   * @param
   * @return
   * @retval
   * @attention
   * @note
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function saveCsvSlide() {
    // ******************************************************
    // 検索項目生成
    // ******************************************************
    // datetimePicker生成
    // 開始の項目
    var saveStart = new McsDateTime($('#mcs-saveStartDatetime'), screenText.saveText.saveStart, 75);
    // 終了の項目
    var saveEnd = new McsDateTime($('#mcs-saveEndDatetime'), screenText.saveText.saveEnd, 75);

    // 非活性項目の設定
    saveStart.setEnabled(false);
    saveEnd.setEnabled(false);

    // 物件対応
    saveStart.hide();
    saveEnd.hide();

    // ******************************************************
    // ボタン生成
    // ******************************************************
    // 決定ボタン
    var confirmButton = new McsButton($('#btn-confirm'), screenText.btnText.confirm);

    // CSV保存の戻るボタン
    var saveReturnButton = new McsButton($('#btn-saveReturn'), screenText.btnText.saveReturn);

    // ******************************************************
    // 各イベント
    // ******************************************************
    // 決定ボタン押下
    confirmButton.onClick(function() {
      var datas = dataTables.getLatestCond();
      callAjax(getUrl('/Carrier/SetCsvCarrierList'), datas, false,
      // 成功
      function(retObj) {
        window.location.href = getUrl('/Carrier/SaveCsvCarrierList');
      },
      // エラー
      function(retObj) {
        // 特にすることなし
      });
    });

    // 戻るボタン押下
    saveReturnButton.onClick(function() {
      saveMenu.hide();
    });
  }
  
  //GROUP用
  function groupSlide() {
	  //
	  groupInfor.setHeader([{
	    name: 'groupNo',
	    text: screenText.slideGroupInfo.groupNo,
	    display: true
	  }, {
		name: 'groupId',
		text: screenText.slideGroupInfo.groupId,
		display: true
	  }]);
	var groupPortId = new McsTextBox($('#mcs-groupPortId'));
		groupPortId.setReadonly(true);
		groupPortId.setEnabled(false);
	var groupAvailable = new McsTextBox($('#mcs-groupAvailable'));
		groupAvailable.setReadonly(true);
//		groupAvailable.setEnabled(false);
    //group ボタン押下
    group.onClick(function() {
    	groupInfor.clearErrors();
    	groupInfor.clear();
        $("#mcs-groupAvailable").find('input').css('background-color', "#FAFAFA");     //标准灰白
    	
	    var datas = dataTables.getSelectedRowData();
	    if (datas === null) {
	      errorDialog.openAlert(screenText.dialog.listNotSelect, screenText.dialog.listRet, 'alert');
	      return;
	    }

	    if (datas !== null) {
	    	groupPortId.setValue(datas[0].currentLocation);

            var url = getUrl('/Carrier/GetGroupInfo');
            var cond = { groupPortId: groupPortId.getValue() };
            callAjax(url, JSON.stringify(cond), false, 
        		function(result) {
	            	// 成功時の処理
	            	groupAvailable.setValue(result.body1.strAvailable);
	    	        $("#mcs-groupAvailable").find('input').css('background-color', result.body1.strColor);
	    	        
	    		    selectDataLength = result.body2.length;
	    		    for (var i = 0; i < selectDataLength; i++) {
	    		    	groupInfor.addData({
	    		    		groupNo: result.body2[i].strNo,
	    		    		groupId: result.body2[i].strGroupId
	    			        });
	    		    }
		        },  function(result) {
		            // 失敗時の処理
		            //jobComp.setErrors(result.result.errorInfoList);
		        });
	    }

	    groupMenu.show();
    });
    var groupReturnButton = new McsButton($('#btn-groupReturn'), screenText.btnText.groupReturn);

    // 戻るボタン押下
    groupReturnButton.onClick(function() {
      groupMenu.hide();
    });
  }
  
  //ADD
  function addbinSlide() {
	    // 検索項目の生成
	    var carrierId = new McsTextBox($('#mcs-addbin-carrierId'));
		var stk = new McsSelectBox($('#mcs-addbin-stk'));
		var bin1 = new McsTextBox($('#mcs-addbin-binno1'), 'number');
		var bin2 = new McsTextBox($('#mcs-addbin-binno2'), 'number');
		var bin3 = new McsTextBox($('#mcs-addbin-binno3'), 'number');
	    
	    var extract = new McsButton($('#mcs-addbin-extract'), screenText.addohb.confirm);
	    var ret = new McsButton($('#mcs-addbin-cancel'), screenText.addohb.ret);

	    // コンポーネントマネージャーに各検索項目を入れる
	    addbinComp.add('carrierId', carrierId);
	    addbinComp.add('stk', stk);
	    addbinComp.add('bin1', bin1);
	    addbinComp.add('bin2', bin2);
	    addbinComp.add('bin3', bin3);
	    // 各イベントの設定
	    // キャリアのテキスト
	    carrierId.setMaxLength(64);
	    stk.setList(screenValue.addbinStkId);
	    bin1.setMaxLength(2);
	    bin2.setMaxLength(2);
	    bin3.setMaxLength(2);

	    extract.onClick(function() {
	      // エラー解除
	      addbinComp.clearErrors();
	      //
	      var url = getUrl('/Carrier/ExeAddCarrierBin');
	      var cond = {
	        carrierId: carrierId.getValue(),
	        stkId: stk.getValue(),
	        bin1: bin1.getValue(),
			bin2: bin2.getValue(),
			bin3: bin3.getValue()
	      };
	      
			var complete = function() {
			    // 全完了時の動作
			    listReload();
			  };
			var success = function(resultj) {
			      // 成功時の処理
				  informationDialog.openAlertProc(screenText.dialog.createjobComplete, screenText.dialog.createjobRet, 'info',
			        function() {
			            complete();
			        });
			  };
			var error = function(result) {
			    // 失敗時の処理
				addbinComp.setErrors(result.result.errorInfoList);
			  };
			  // 初回（1行目）の登録を実施
			  callAjax(url, JSON.stringify(cond), false, success, error);
	      
	    });

	    // 戻るボタン押下
	    ret.onClick(function() {
	    	addbinMenu.hide();
	    });

	  }
  
  function addohbSlide() {
	    // 検索項目の生成
	    var carrierId = new McsTextBox($('#mcs-addohb-carrierId'));
		var ohb = new McsSelectBox($('#mcs-addohb-ohb'));
		var bin = new McsSelectBox($('#mcs-addohb-binno'));
	    
	    var extract = new McsButton($('#mcs-addohb-extract'), screenText.addohb.confirm);
	    var ret = new McsButton($('#mcs-addohb-cancel'), screenText.addohb.ret);

	    // コンポーネントマネージャーに各検索項目を入れる
	    addohbComp.add('carrierId', carrierId);
	    addohbComp.add('ohb', ohb);
	    addohbComp.add('bin', bin);
	    // 各イベントの設定
	    // キャリアのテキスト
	    carrierId.setMaxLength(64);
	    ohb.setList(screenValue.addohbId);
	    bin.setList(screenValue.addohbPortId);

	    ohb.onChange(function() {
            var url = getUrl('/Carrier/GetPortList');
            var cond = { ohbId: ohb.getValue() };
            callAjax(url, JSON.stringify(cond), false, 
        		function(result) {
	            	// 成功時の処理
            	    bin.setList(result.body);
		        },  function(result) {
		            // 失敗時の処理
		            //jobComp.setErrors(result.result.errorInfoList);
		        });
	    });
	    
	    extract.onClick(function() {
	      // エラー解除
	      addohbComp.clearErrors();
	      //
	      var url = getUrl('/Carrier/ExeAddCarrierOhb');
	      var cond = {
	        carrierId: carrierId.getValue(),
	        portId: bin.getValue()
	      };
	      
			var complete = function() {
			    // 全完了時の動作
			    listReload();
			  };
			var success = function(resultj) {
			      // 成功時の処理
				  informationDialog.openAlertProc(screenText.dialog.createjobComplete, screenText.dialog.createjobRet, 'info',
			        function() {
			            complete();
			        });
			  };
			var error = function(result) {
			    // 失敗時の処理
				addohbComp.setErrors(result.result.errorInfoList);
			  };
			  // 初回（1行目）の登録を実施
			  callAjax(url, JSON.stringify(cond), false, success, error);
	      
	    });

	    // 戻るボタン押下
	    ret.onClick(function() {
	    	addohbMenu.hide();
	    });

	  }
  
});
