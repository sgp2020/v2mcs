/**
 ******************************************************************************
 * @file        mcs-Remarks.js
 * @brief       凡例用JavaScript
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 ******************************************************************************
 */

$(function() {
  /* Step4 2017_08_10：ウィンドウ生成時にフォーカスを当てるよう修正 */
  focus();

  // アイコン生成
  createRemarksIcon($('#container1'));
  createRemarksIcon2($('#container2'));

  // ステータス色一覧
  /*$('#color1').css('background-color', '#38FF61');
  //$('#color2').css('background-color', '#ffff00');// MACS4#MACSV2 Del
  $('#color2').css('background-color', '#FAFA00');// MACS4#MACSV2 Add
  $('#color3').css('background-color', '#ff0000');
  $('#color4').css('background-color', '#0000ff');
  //$('#color5').css('background-color', '#9fa0a0');// MACS4#MACSV2 Del
  $('#color5').css('background-color', ' #646464');// MACS4#MACSV2 Add
  $('#color6').css('background-color', ' #FF00FA');// MACS4#MACSV2 Add
*/ 
  //20200107 DQY ADD START FOR STATE COLORS
  $('#color1').css('background-color', screenText.colorText.allUp);
  $('#color2').css('background-color', screenText.colorText.error);
  $('#color3').css('background-color', screenText.colorText.disable);
  $('#color4').css('background-color', screenText.colorText.test);
  $('#color5').css('background-color', screenText.colorText.pmmode);
  $('#color6').css('background-color', screenText.colorText.hold);
  $('#color7').css('background-color', screenText.colorText.manual);
  $('#color8').css('background-color', screenText.colorText.pausing);
  $('#color9').css('background-color', screenText.colorText.alarm);
  $('#color10').css('background-color', screenText.colorText.offline);
  $('#color11').css('background-color', screenText.colorText.dummy);
  $('#color12').css('background-color', screenText.colorText.block);
  $('#color13').css('background-color', screenText.colorText.timeout);
  $('#color14').css('background-color', screenText.colorText.other);
  
  /*$('#colorC1').css('background-color', screenText.colorText.allUp);
  $('#colorC2').css('background-color', screenText.colorText.error);
  $('#colorC3').css('background-color', screenText.colorText.disable);
  $('#colorC4').css('background-color', screenText.colorText.test);*/
  //20200107 DQY ADD START FOR END COLORS
 
//  function RGB2Color(r,g,b)
//  {
//    return '#' + byte2Hex(r) + byte2Hex(g) + byte2Hex(b);
//  }
//  alert(RGB2Color(0,255,0));

  // 棚使用率
  /*var shelf1 = new McsTextBox($('#shelf1'), 'number');
  var shelf2 = new McsTextBox($('#shelf2'), 'number');
  var shelf3 = new McsTextBox($('#shelf3'), 'number');
  var shelf4 = new McsTextBox($('#shelf4'), 'number');
  var shelf5 = new McsTextBox($('#shelf5'), 'number');
  var shelf6 = new McsTextBox($('#shelf6'), 'number');// MACS4#MACSV2 Add
  //20200107 DQY ADD START FOR STATE COLORS
  var shelf7 = new McsTextBox($('#shelf7'), 'number');
  var shelf8 = new McsTextBox($('#shelf8'), 'number');
  var shelf9 = new McsTextBox($('#shelf9'), 'number');
  var shelf10 = new McsTextBox($('#shelf10'), 'number');
  var shelf11 = new McsTextBox($('#shelf11'), 'number');
  var shelf12 = new McsTextBox($('#shelf12'), 'number');
  var shelf13 = new McsTextBox($('#shelf13'), 'number');
  var shelf14 = new McsTextBox($('#shelf14'), 'number');
  //20200107 DQY ADD START FOR END COLORS
*/
 /* 20200107 DQY DEL
  * shelf1.setReadonly(true);
  shelf2.setReadonly(true);
  shelf3.setReadonly(true);
  shelf4.setReadonly(true);
  shelf5.setReadonly(true);
  shelf6.setReadonly(true);// MACS4#MACSV2 Add
  //20200107 DQY ADD START FOR STATE COLORS
  shelf7.setReadonly(true);
  shelf8.setReadonly(true);
  shelf9.setReadonly(true);
  shelf10.setReadonly(true);
  shelf11.setReadonly(true);
  shelf12.setReadonly(true);
  shelf13.setReadonly(true);
  shelf14.setReadonly(true);
  //20200107 DQY ADD START FOR END COLORS

  // 棚使用率閾値セット
  shelf1.setValue(screenValue.occuLowRate);
  shelf2.setValue(screenValue.occuMiddleRate);
  shelf3.setValue(screenValue.occuHighRate);
*/
  // 通信状態
  /*
  var comm1 = new McsTextBox($('#comm1'));
  var comm2 = new McsTextBox($('#comm2'));
  var comm3 = new McsTextBox($('#comm3'));
  var comm4 = new McsTextBox($('#comm4'));
  var comm5 = new McsTextBox($('#comm5'));
  var comm6 = new McsTextBox($('#comm6'));// MACS4#MACSV2 Add

  comm1.setReadonly(true);
  comm2.setReadonly(true);
  comm3.setReadonly(true);
  comm4.setReadonly(true);
  comm5.setReadonly(true);
  comm6.setReadonly(true);

  // 通信状態 対象メッセージセット
  comm1.setValue(screenText.commText.onLineRemote);
  comm2.setValue(screenText.commText.offLine);
  comm3.setValue(screenText.commText.notComm);
  comm4.setValue(screenText.commText.onLineLocal);
  */

  // コントローラ状態
  var ctrl1 = new McsTextBox($('#ctrl1'));
  var ctrl2 = new McsTextBox($('#ctrl2'));
  var ctrl3 = new McsTextBox($('#ctrl3'));
  var ctrl4 = new McsTextBox($('#ctrl4'));
  var ctrl5 = new McsTextBox($('#ctrl5'));
  var ctrl6 = new McsTextBox($('#ctrl6'));// MACS4#MACSV2 Add
  //20200106 DQY ADD START
  var ctrl7 = new McsTextBox($('#ctrl7'));// MACS4#MACSV2 Add
  var ctrl8 = new McsTextBox($('#ctrl8'));// MACS4#MACSV2 Add
  var ctrl9 = new McsTextBox($('#ctrl9'));// MACS4#MACSV2 Add
  var ctrl10 = new McsTextBox($('#ctrl10'));// MACS4#MACSV2 Add
  var ctrl11 = new McsTextBox($('#ctrl11'));// MACS4#MACSV2 Add
  var ctrl12 = new McsTextBox($('#ctrl12'));// MACS4#MACSV2 Add
  var ctrl13 = new McsTextBox($('#ctrl13'));// MACS4#MACSV2 Add
  var ctrl14 = new McsTextBox($('#ctrl14'));// MACS4#MACSV2 Add
  //20200106 DQY ADD END

  ctrl1.setReadonly(true);
  ctrl2.setReadonly(true);
  ctrl3.setReadonly(true);
  ctrl4.setReadonly(true);
  ctrl5.setReadonly(true);
  ctrl6.setReadonly(true);// MACS4#MACSV2 Add
  //20200106 DQY ADD START
  ctrl7.setReadonly(true);// MACS4#MACSV2 Add
  ctrl8.setReadonly(true);// MACS4#MACSV2 Add
  ctrl9.setReadonly(true);// MACS4#MACSV2 Add
  ctrl10.setReadonly(true);// MACS4#MACSV2 Add
  ctrl11.setReadonly(true);// MACS4#MACSV2 Add
  ctrl12.setReadonly(true);// MACS4#MACSV2 Add
  ctrl13.setReadonly(true);// MACS4#MACSV2 Add
  ctrl14.setReadonly(true);// MACS4#MACSV2 Add
  //20200106 DQY ADD END
  
  
  
  //20200107 DQY START FOR COLOR STATE 
  //MACS4#MACSV2 Add
  // コントローラ状態 対象メッセージセット
  /*ctrl1.setValue(screenText.ctrlText.noAlarmsAuto);
  ctrl2.setValue(screenText.ctrlText.noAlarmsOther);
  ctrl3.setValue(screenText.ctrlText.alarms);
  ctrl4.setValue(screenText.ctrlText.pm);
  ctrl5.setValue(screenText.ctrlText.unknown);
  ctrl6.setValue(screenText.ctrlText.offline);// MACS4#MACSV2 Add
*/
  ctrl1.setValue(screenText.ctrlText.allUp);
  ctrl2.setValue(screenText.ctrlText.error);
  ctrl3.setValue(screenText.ctrlText.disable);
  ctrl4.setValue(screenText.ctrlText.test);
  ctrl5.setValue(screenText.ctrlText.pmmode);
  ctrl6.setValue(screenText.ctrlText.hold);
  ctrl7.setValue(screenText.ctrlText.manual);
  ctrl8.setValue(screenText.ctrlText.pausing);
  ctrl9.setValue(screenText.ctrlText.alarm);
  ctrl10.setValue(screenText.ctrlText.offline);
  ctrl11.setValue(screenText.ctrlText.dummy);
  ctrl12.setValue(screenText.ctrlText.block);
  ctrl13.setValue(screenText.ctrlText.timeout);
  ctrl14.setValue(screenText.ctrlText.other);
  //20200107 DQY END FOR COLOR STATE
  
  // ポート状態
 /* //20200107 DQY DEL
  var port1 = new McsTextBox($('#port1'));
  var port2 = new McsTextBox($('#port2'));
  var port3 = new McsTextBox($('#port3'));
  var port4 = new McsTextBox($('#port4'));
  var port5 = new McsTextBox($('#port5'));
  var port6 = new McsTextBox($('#port6'));// MACS4#MACSV2 Add
  //20200107 DQY START FOR COLOR STATE
  var port7 = new McsTextBox($('#port7'));
  var port8 = new McsTextBox($('#port8'));
  var port9 = new McsTextBox($('#port9'));
  var port10 = new McsTextBox($('#port10'));
  var port11 = new McsTextBox($('#port11'));
  var port12 = new McsTextBox($('#port12'));
  var port13 = new McsTextBox($('#port13'));
  var port14 = new McsTextBox($('#port14'));
  //20200107 DQY END FOR COLOR STATE

  port1.setReadonly(true);
  port2.setReadonly(true);
  port3.setReadonly(true);
  port4.setReadonly(true);
  port5.setReadonly(true);
  port6.setReadonly(true);// MACS4#MACSV2 Add
  //20200107 DQY START FOR COLOR STATE
  port7.setReadonly(true);
  port8.setReadonly(true);
  port9.setReadonly(true);
  port10.setReadonly(true);
  port11.setReadonly(true);
  port12.setReadonly(true);
  port13.setReadonly(true);
  port14.setReadonly(true);
  //20200107 DQY END FOR COLOR STATE
  

  // ポート状態 対象メッセージセット
  port1.setValue(screenText.portText.noAlarms);
  port2.setValue(screenText.portText.someAlarms);
  port3.setValue(screenText.portText.alarms);

  // 出庫状態
  var carrier1 = new McsTextBox($('#carrier1'));
  var carrier2 = new McsTextBox($('#carrier2'));
  var carrier3 = new McsTextBox($('#carrier3'));
  var carrier4 = new McsTextBox($('#carrier4'));
  var carrier5 = new McsTextBox($('#carrier5'));
  var carrier6 = new McsTextBox($('#carrier6'));// MACS4#MACSV2 Add
  //20200107 DQY START FOR COLOR STATE
  var carrier7 = new McsTextBox($('#carrier7'));
  var carrier8 = new McsTextBox($('#carrier8'));
  var carrier9 = new McsTextBox($('#carrier9'));
  var carrier10 = new McsTextBox($('#carrier10'));
  var carrier11 = new McsTextBox($('#carrier11'));
  var carrier12 = new McsTextBox($('#carrier12'));
  var carrier13 = new McsTextBox($('#carrier13'));
  var carrier14 = new McsTextBox($('#carrier14'));
  //20200107 DQY END FOR COLOR STATE
  
  carrier1.setReadonly(true);
  carrier2.setReadonly(true);
  carrier3.setReadonly(true);
  carrier4.setReadonly(true);
  carrier5.setReadonly(true);
  carrier6.setReadonly(true);// MACS4#MACSV2 Add
  //20200107 DQY START FOR COLOR STATE
  carrier7.setReadonly(true);
  carrier8.setReadonly(true);
  carrier9.setReadonly(true);
  carrier10.setReadonly(true);
  carrier11.setReadonly(true);
  carrier12.setReadonly(true);
  carrier13.setReadonly(true);
  carrier14.setReadonly(true);
  //20200107 DQY END FOR COLOR STATE

  // 出庫状態 対象メッセージセット
  carrier1.setValue(screenText.carrierText.noCarrier);
  carrier2.setValue(screenText.carrierText.carrierExist);*/

  /**
   ******************************************************************************
   * @brief    アイコン生成
   * @param    {jQuery} containerDiv アイコン格納先のdiv要素
   * @return
   * @retval
   * @attention
   * @note     凡例画面用のアイコンを生成する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  /*  //20200104 Song Mod Start
  function createRemarksIcon(containerDiv) {
    // SVGのDOM生成
    var parentDiv = $('<div />');
    var i = $('<i class="icon-set status-block" />');
    var svg = $('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52" />');
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var polygon = $('<polygon points="10.5 42.52 10.5 18.21 18.21 10.5 33.79 10.5 41.5 18.21 41.5 42.52 10.5 42.52" />');
    var path = $('<path d="M33.59,11,41,18.51V42H11V18.51L18.51,11H33.69M34,10H18L10,18V43H42V18L34,10Z" />');
   */
    /* Step4 2017_08_10：foreignObject,rectタグに対して、width・heightを指定 */
    /* Step4 2017_08_21 */
    /* Step4 2017_08_25 */
  /*
    var rect1 = $('<rect class="stat-01 status-box" x="11.37" y="35.16" width="14" height="6" />');
    var rect2 = $('<rect class="stat-02 status-box" x="26.2" y="35.16" width="14" height="6" />');
    var rect3 = $('<rect class="stat-03 status-box" x="11.37" y="27.01" width="14" height="6" />');
    var rect4 = $('<rect class="stat-04 status-box" x="26.2" y="27.01" width="14" height="6" />');
    var text1 = $('<text class="stat-num" x="23" y="20">(1)</text>');
    var text2 = $('<text class="stat-num" x="16" y="31">(2)</text>');
    var text3 = $('<text class="stat-num" x="30.5" y="31">(3)</text>');
    var text4 = $('<text class="stat-num" x="16" y="39.5">(4)</text>');
    var text5 = $('<text class="stat-num" x="30.5" y="39.5">(5)</text>');
    parentDiv.append(i);
    i.append(svg);
    svg.append(polygon, path, rect1, rect2, rect3, rect4, text1, text2, text3, text4, text5);

    // アイコンを反映する
    containerDiv.append($(parentDiv.html()));
  }
  */
  function createRemarksIcon(containerDiv) {
	    // SVGのDOM生成
	    var parentDiv = $('<div />');
	    var i = $('<i class="icon-set status-block" />');
	    var svg = $('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52" />');
	    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
	    var polygon = $('<polygon points="10.5 42.52 10.5 18.21 18.21 10.5 33.79 10.5 41.5 18.21 41.5 42.52 10.5 42.52" />');
	    var path = $('<path d="M33.59,11,41,18.51V42H11V18.51L18.51,11H33.69M34,10H18L10,18V43H42V18L34,10Z" />');
//	    var rect1 = $('<rect class="stat-01 status-box" x="16" y="20" width="20" height="6" />');
	    var rect1 = $('<rect class="stat-01 status-box" x="11" y="20" width="30" height="6" />');//20200116 DQY MOD
	    var rect2 = $('<rect class="stat-02 status-box" x="11" y="28" width="30" height="6" />');//20200116 DQY MOD
	    var rect3 = $('<rect class="stat-03 status-box" x="11" y="36" width="30" height="6" />');//20200116 DQY MOD
//	    var text1 = $('<text class="stat-num" x="23" y="17">(1)TSCState</text>');
	    var text1 = $('<text class="stat-num" x="17" y="17">TSCState</text>');//20200116 DQY MOD
	    var text2 = $('<text class="stat-num" x="11" y="24.5">TSCName</text>');//20200116 DQY MOD
	    var text3 = $('<text class="stat-num" x="11" y="32.5">OccupiedRate</text>');//20200116 DQY MOD
	    var text4 = $('<text class="stat-num" x="11" y="40.5">PortState/Carrier</text>');//20200116 DQY MOD
	    parentDiv.append(i);
	    i.append(svg);
	    svg.append(polygon, path, rect1, rect2, rect3, text1, text2, text3, text4);

	    // アイコンを反映する
	    containerDiv.append($(parentDiv.html()));
  }
  //20200104 Song Mod End
  
  //20200104 Song Mod Start
  /**
   ******************************************************************************
   * @brief    アイコン2生成
   * @param    {jQuery} containerDiv アイコン格納先のdiv要素
   * @return
   * @retval
   * @attention
   * @note     凡例画面用のアイコンを生成する
   * ----------------------------------------------------------------------------
   * VER.        DESCRIPTION                                               AUTHOR
   * ----------------------------------------------------------------------------
   ******************************************************************************
   */
  /*
  function createRemarksIcon2(containerDiv) {
    // SVGのDOM生成
    var parentDiv = $('<div />');
    var i = $('<i class="icon-set status-block" />');
    var svg = $('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52" />');
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var polygon = $('<polygon points="10.5 42.52 10.5 18.28 12.09 18.28 12.09 13.87 10.5 13.87 10.5 10.5 41.5 10.5 41.5 13.87 40 13.87 40 18.28 41.5 18.28 41.5 42.52 10.5 42.52" />');
    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
    var path = $('<path d="M41,11V13.36H39.5V18.78H41V42H11V18.78H12.59V13.36H11V11H41m1-1H10V14.36H11.59V17.78H10V43H42V17.78H40.5V14.36H42V10Z" />');

    var rect1 = $('<rect class="stat-01 status-box" x="11.37" y="35.16" width="14" height="6" />');
    var rect2 = $('<rect class="stat-02 status-box" x="26.2" y="35.16" width="14" height="6" />');
    var text1 = $('<text class="stat-num" x="23" y="20">(1)</text>');
    var text2 = $('<text class="stat-num" x="16" y="39.5">(4)</text>');
    var text3 = $('<text class="stat-num" x="30.5" y="39.5">(5)</text>');
    parentDiv.append(i);
    i.append(svg);
    svg.append(polygon, path, rect1, rect2, text1, text2, text3);

    // アイコンを反映する
    containerDiv.append($(parentDiv.html()));
  }
  */
  function createRemarksIcon2(containerDiv) {
	    // SVGのDOM生成
	    var parentDiv = $('<div />');
	    var i = $('<i class="icon-set status-block" />');
	    var svg = $('<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52" />');
	    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
	    var polygon = $('<polygon points="10.5 42.52 10.5 18.28 12.09 18.28 12.09 13.87 10.5 13.87 10.5 10.5 41.5 10.5 41.5 13.87 40 13.87 40 18.28 41.5 18.28 41.5 42.52 10.5 42.52" />');
	    // eslintで警告が出るが、ソースコードの可読性を考慮し無視する。
	    var path = $('<path d="M41,11V13.36H39.5V18.78H41V42H11V18.78H12.59V13.36H11V11H41m1-1H10V14.36H11.59V17.78H10V43H42V17.78H40.5V14.36H42V10Z" />');
//20200108 DQY MOD START
//	    var rect1 = $('<rect class="stat-01 status-box" x="16" y="23" width="20" height="6" />');
//	    var rect2 = $('<rect class="stat-02 status-box" x="16" y="32" width="20" height="6" />');
//	    var text1 = $('<text class="stat-num" x="23" y="27.5">(1)</text>');
//	    var text2 = $('<text class="stat-num" x="23" y="36.5">(4)</text>');
	    var rect1 = $('<rect class="stat-01 status-box" x="11" y="23" width="30" height="6" />');
	    var rect2 = $('<rect class="stat-02 status-box" x="11" y="32" width="30" height="6" />');
	    var text1 = $('<text class="stat-num" x="17" y="18.5">TSCState</text>');//20200116 DQY ADD
	    var text2 = $('<text class="stat-num" x="11" y="27.5">TSCName</text>'); //20200116 DQY MOD
	    var text3 = $('<text class="stat-num" x="11" y="36.5">PortState/Carrier</text>');//20200116 DQY MOD
//20200108 DQY MOD END	    
	    parentDiv.append(i);
	    i.append(svg);
	    svg.append(polygon, path, rect1, rect2, text1, text2, text3);//20200116 DQY MOD

	    // アイコンを反映する
	    containerDiv.append($(parentDiv.html()));
   }
   //20200104 Song Mod End
});
