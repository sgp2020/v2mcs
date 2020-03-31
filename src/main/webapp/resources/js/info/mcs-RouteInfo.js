/**
 ******************************************************************************
 * @file        mcs-RouteInfo.js
 * @brief       アラーム情報表示画面用JavaScript
 * @par
 * @author      SGP
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                     AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/25  2                                           SGP
 ******************************************************************************
 */
$(function() { 
	
  // ステータス色一覧
  $('#color1').css('background-color', screenText.colorText.Disable);
  
  var ctrl1 = new McsTextBox($('#ctrl1'));

  ctrl1.setReadonly(true);
 
  ctrl1.setValue(screenText.ctrlText.Disable);
 
  
		
  // 画面初期化時の処理

  // データテーブル
  
  //var dataTables = new McsDataTables($('#lst-table-target'), false);
  var dataTables = new McsDataTablesBgColor($('#lst-table-target'), false);
  
  // 行選択時のイベントをセット
  dataTables.onSelectRow(function() {
	destTable.clear();
	var srcPieceIdText = source.getText();
	var dstPieceIdText = destination.getText();
    var record = dataTables.getSelectedRowData();
    var nextDstConnId1 = record[0].nextDstConnId1;
    var destTableList = new Array();
    var i = 0;
    var j = 1;
    
    var dest = {
    	    'DestNo':j,
    	    'EQPID':srcPieceIdText,
    	    'Connection':nextDstConnId1
    	} ;
    destTableList[i]= dest;
    i++;j++;
    //var nextDstPieceId1 = record[0].nextDstPieceId1;
    if(record[0].nextDstPieceId1 == null || record[0].nextDstPieceId1 == "" || record[0].nextDstPieceId1 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
			    	    'DestNo':j,
			    	    'EQPID':record[0].nextDstPieceId1,
			    	    'Connection':record[0].nextDstConnId2
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId2 == null || record[0].nextDstPieceId2 == "" || record[0].nextDstPieceId2 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId2,
	    	    		'Connection':record[0].nextDstConnId3
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId3 == null || record[0].nextDstPieceId3 == "" || record[0].nextDstPieceId3 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId3,
	    	    		'Connection':record[0].nextDstConnId4
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId4 == null || record[0].nextDstPieceId4 == "" || record[0].nextDstPieceId4 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId4,
	    	    		'Connection':record[0].nextDstConnId5
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId5 == null || record[0].nextDstPieceId5 == "" || record[0].nextDstPieceId5 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId5,
	    	    		'Connection':record[0].nextDstConnId6
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId6 == null || record[0].nextDstPieceId6 == "" || record[0].nextDstPieceId6 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId6,
	    	    		'Connection':record[0].nextDstConnId7
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId7 == null || record[0].nextDstPieceId7 == "" || record[0].nextDstPieceId7 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId7,
	    	    		'Connection':record[0].nextDstConnId8
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId8 == null || record[0].nextDstPieceId8 == "" || record[0].nextDstPieceId8 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId8,
	    	    		'Connection':record[0].nextDstConnId9
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId9 == null || record[0].nextDstPieceId9 == "" || record[0].nextDstPieceId9 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId9,
	    	    		'Connection':record[0].nextDstConnId10
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId10 == null || record[0].nextDstPieceId10 == "" || record[0].nextDstPieceId10 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId10,
	    	    		'Connection':record[0].nextDstConnId11
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId11 == null || record[0].nextDstPieceId11 == "" || record[0].nextDstPieceId11 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId11,
	    	    		'Connection':record[0].nextDstConnId12
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId12 == null || record[0].nextDstPieceId12 == "" || record[0].nextDstPieceId12 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId12,
	    	    		'Connection':record[0].nextDstConnId13
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId13 == null || record[0].nextDstPieceId13 == "" || record[0].nextDstPieceId13 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId13,
	    	    		'Connection':record[0].nextDstConnId14
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId14 == null || record[0].nextDstPieceId14 == "" || record[0].nextDstPieceId14 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId14,
	    	    		'Connection':record[0].nextDstConnId15
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId15 == null || record[0].nextDstPieceId15 == "" || record[0].nextDstPieceId15 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId15,
	    	    		'Connection':record[0].nextDstConnId16
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId16 == null || record[0].nextDstPieceId16 == "" || record[0].nextDstPieceId16 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId16,
	    	    		'Connection':record[0].nextDstConnId17
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId17 == null || record[0].nextDstPieceId17 == "" || record[0].nextDstPieceId17 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId17,
	    	    		'Connection':record[0].nextDstConnId18
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId18 == null || record[0].nextDstPieceId18 == "" || record[0].nextDstPieceId18 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId18,
	    	    		'Connection':record[0].nextDstConnId19
	    			} ;
	    destTableList[i]= dest1;
    }
    
    i++;j++;
    if(record[0].nextDstPieceId19 == null || record[0].nextDstPieceId19 == "" || record[0].nextDstPieceId19 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId19,
	    	    		'Connection':record[0].nextDstConnId20
	    			} ;
	    destTableList[i]= dest1;
    }
    i++;j++;
    if(record[0].nextDstPieceId20 == null || record[0].nextDstPieceId20 == "" || record[0].nextDstPieceId20 == "0"){
    	var dest1 = {
		    	    	'DestNo':j,
		    	    	'EQPID':dstPieceIdText,
		    	    	'Connection':""
    	    		} ;
    	destTableList[i]= dest1;
    	destTable.addDataList(destTableList);
    	return;
    }else{
    	var dest1 = {
	    	    		'DestNo':j,
	    	    		'EQPID':record[0].nextDstPieceId20,
	    	    		'Connection':record[0].nextDstConnId21
	    			} ;
	    destTableList[i]= dest1;
    }
   
   
    //destTableList[0]= {DestNo: "32BCU024-LB007", EQPID: "Down", Connection: "Available"};
    //destTableList[1]= {DestNo: "32BCU024-LB007", EQPID: "Down", Connection: "Available"};
    destTable.addDataList(destTableList);
  });

 
  //var ohbPortRltTable = new McsTable($('#state-text-target-ohbPortRlt'));
  var destTable = new McsTableBgColor($('#state-text-target-dest'));
  destTable.setNotRowSelect(true);
  
  // 状態テーブルヘッダ(状態テーブル)
  var destHeader = [{
    name: 'DestNo',
    text: screenText.destText.DestNo,
    display: true
  }, {
    name: 'EQPID',
    text: screenText.destText.EQPID,
    display: true
  }, {
    name: 'Connection',
    text: screenText.destText.Connection,
    display: true
  }];
  	  
  // ヘッダ設定(状態テーブル)
  destTable.setHeader(destHeader);
  
  var source  = new McsSelectBox($('#source'));
  var destination  = new McsSelectBox($('#destination'));
  var tableNo  = new McsSelectBox($('#tableNo'));
  
  var searchButton = new McsButton($('#btn-search'), "Search");
  
  //routeState状態テキスト
  var routeState1 = screenValue.routeState;
  var routeState = $('#routeState');
  routeState.text(routeState1[0][1]);
  routeState.css("background-color",routeState1[0][0]);
  routeState.css("color","black");
 

  searchButton.onClick(function() {
	  destTable.clear();
	  var srcPieceIdValue = source.getValue();
	  var dstPieceIdValue = destination.getValue();
	  var tableNoValue = tableNo.getValue();
	  extract({
	      srcPieceId:source.getValue(),
		  dstPieceId:destination.getValue(),
		  tableNo:tableNo.getValue()
	  });
  });
  
  
  var pieceListJson = screenValue.pieceListJson;
  var tabelNoJson= screenValue.tabelNoJson; 
  source.setList(pieceListJson);
  destination.setList(pieceListJson);
  tableNo.setList(tabelNoJson);
  

  // 戻るボタン押下時にスライドを閉じないようにするためのフラグ
  var retFlag = false;

  // 削除マネージャー
  var deleteTaskManager = new McsDeleteTaskManager();   // MACS4#0047 Add

  var errorDialog = new McsDialog($('#mcs-error-dialog'), window.mcsDialogTitleError);        // MACS4#0047 Add
  var confirmDialog = new McsDialog($('#mcs-confirm-dialog'), window.mcsDialogTitleConfirm);  // MACS4#0047 Add

  // 初回検索
  // extract({});
   extract({
	      srcPieceId:'3006',
		  dstPieceId:'3006',
		  tableNo:'1'
   });

  /**
   ******************************************************************************
   * @brief       検索処理を実行する機能
   * @param       {Object} cond 抽出条件（そのままサーバへ送信されるJSON）
   * @return
   * @retval
   * @attention
   * @note        検索処理を実行し、返却されたデータをDataTablesに表示する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  function extract(cond) {
    dataTables.getDataAjax({
      url: getUrl('/RouteInfo/GetRouteInfoList'),
      cond: cond,
      searchDataFlag: true,
      tableCompId: 'I-008-dataTables', // テーブルコンポーネントID
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
      },
      ajaxError: function(status, error) {
        // 特にすることなし
      }
    });
    
  }
  

  /**
   ******************************************************************************
   * @brief       一覧再表示処理
   * @param
   * @return
   * @retval
   * @attention
   * @note        一覧再表示処理を実施する。
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   * MACS4#0047  GUI要望分(新規作成)                                    T.Iga/CSC
   ******************************************************************************
   */
  /*
  function listReload() {
    dataTables.reload();
  }
  */
});
