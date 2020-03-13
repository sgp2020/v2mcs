//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualOhbMonitorService.java
 * @brief       個別モニタ(OHBモニタ)関連のサービス
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
 * 2017/09/20 0.5         Step4リリース                                     CSC
 * 2019/12/25 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.top;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.entity.top.IndividualMonitorAlarmInfoEntity;
import net.muratec.mcs.entity.top.IndividualMonitorPortEntity;
import net.muratec.mcs.entity.top.IndividualMonitorStateEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorOhbEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AlarmMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.OhbMapper;
import net.muratec.mcs.mapper.ScreenMonitorMemberMapper;
import net.muratec.mcs.model.Alarm;
import net.muratec.mcs.model.AlarmExample;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.IndividualMonitorStateInfo;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.model.ScreenMonitorMemberExample;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(OHBモニタ)関連のサービスクラス
 * @par       機能:
 *              getStateInfo（状態画面用データ取得機能）
 *              getPortInfoList（ポート画面用データ取得機能）
 *              getPortColorInfoList（ポート画面用色情報取得機能）
 *              creRgbArray（GUI_COLORからRGB情報の配列を生成する機能）
 *              stringToShortNumber（String型からShort型へ変換する機能）
 *              decimalToPerNumString（小数値から百分率形式の文字列へ変換する機能）
 *              getStateInfo（状態画面用データ取得機能）
 *              getPortInfoListForeignFile（ポート画面用データ取得機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
//@formatter:on
@Service
public class IndividualOhbMonitorService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;
    @Autowired private ScreenMonitorMemberMapper ScreenMonitorMemberMapper;//20191225 DQY ADD FOR STATEINFO OF THE OHBC MAIN
    @Autowired private OhbMapper ohbMapper;//20191225 DQY ADD FOR STATEINFO OF THE OHBC MAIN


    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** ALARMマッパー生成 */
    @Autowired private AlarmMapper alarmMapper;

    /** 外部ファイル参照用サービス */
    @Autowired private ExeForeignFileService exeForeignFileService;
    //20200113 DQY ADD START
    List<ScreenMonitor> screenMonitorList1  = new ArrayList<ScreenMonitor>();
    List<ScreenMonitorMember> ScreenMonitorMemberList1  = new ArrayList<ScreenMonitorMember>();
    List<ScreenColorMaster> screenColorMasterList1  = new ArrayList<ScreenColorMaster>();

    public void setScreenMonitorList1(List<ScreenMonitor> screenMonitorList1) {
    	this.screenMonitorList1 = screenMonitorList1;
    }
    public void setScreenMonitorMemberList1(List<ScreenMonitorMember> ScreenMonitorMemberList1) {
    	this.ScreenMonitorMemberList1 = ScreenMonitorMemberList1;
    }
    public void setScreenColorMasterList1(List<ScreenColorMaster> screenColorMasterList1) {
    	this.screenColorMasterList1 = screenColorMasterList1;
    }
    //20200113 DQY ADD END
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    状態情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public IndividualMonitorStateEntity getStateInfo(ReqIndividualMonitorOhbEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        IndividualMonitorStateEntity resEntity = new IndividualMonitorStateEntity();

        // -----------------------------------------
        // 状態データ取得
        // -----------------------------------------
        
        //20200113 DQY ADD START
        /*//20200113 DQY DEL DATA FROM DB
        ScreenMonitorMemberExample configExample = new ScreenMonitorMemberExample();
        configExample.createCriteria().andDisplayIdEqualTo(reqEntity.displayId);
        List<ScreenMonitorMember> stateRec = ScreenMonitorMemberMapper.selectByExample(configExample);*/
        //20200113 DQY ADD DATA FROM CSV START
        List<ScreenMonitorMember> stateRec = new ArrayList<ScreenMonitorMember>();
        
    	  for (ScreenMonitorMember screenMonitorMember : ScreenMonitorMemberList1) {
  			if(reqEntity.displayId.equals(screenMonitorMember.getDisplayId())) {
  				stateRec.add(screenMonitorMember);
  			}
  		}
        
    	/*if (stateRec == null) {
          return null;
      	}*/
  	   if (stateRec.isEmpty()) {
  		  return null;
  	   }
  	   //20200113 DQY ADD DATA FROM CSV END

        // -----------------------------------------
        // アラーム一覧取得
        // -----------------------------------------
        AlarmExample example = new AlarmExample();
//        example.createCriteria().andAmhsIdEqualTo(reqEntity.ohbId);	  //20191225 DQY DEL
        example.createCriteria().andTscIdEqualTo(reqEntity.displayId);	  //20191225 DQY ADD
        example.setOrderByClause("SET_TIME desc");						  //20191225 DQY ADD
        List<Alarm> alarmRecList = alarmMapper.selectByExample(example);  //20191225 DQY ADD

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        // 状態データ
        //20191225 DQY ADD START FOR MCSV2 OHBC START
        for (ScreenMonitorMember  tscStateRec : stateRec) {
	        // 状態データ
	        if(!State.COMM_STATE_COMMUNICATING.equals(tscStateRec.getCommState())) 
	        {
	        	
	        	resEntity.state.commState = tscStateRec.getCommState();
	        	resEntity.state.controlState = State.TSC_SYSTEM_NONE;
	        	resEntity.state.systemState = State.TSC_SYSTEM_NONE;
	        	resEntity.state.available = State.TSC_SYSTEM_NONE;
	        	resEntity.state.tscMode = State.TSC_SYSTEM_NONE;
	        	resEntity.state.alarmState = tscStateRec.getAlarmState();
	        	resEntity.state.pieceMode = tscStateRec.getPieceMode();
	        	resEntity.state.pieceAvailable = tscStateRec.getPieceAvailable();
	        }
	        else 
	        {
		        resEntity.state.commState = tscStateRec.getCommState();
		        resEntity.state.controlState = tscStateRec.getControlState();
		        resEntity.state.systemState = tscStateRec.getSystemState();
		        resEntity.state.available = tscStateRec.getTscAvailable();
		        resEntity.state.alarmState = tscStateRec.getAlarmState();
		        resEntity.state.tscMode = tscStateRec.getTscMode();
		        resEntity.state.pieceMode = tscStateRec.getPieceMode();
		        resEntity.state.pieceAvailable = tscStateRec.getPieceAvailable();
	        }
	        //20191225 DQY ADD START FOR MCSV2 OHBC END
	        
        }
        
        //20191225 DQY ADD FOR OHBID TOTAL_SHELVES
        IndividualMonitorStateInfo ohbStateRec = iMonitorMapper.getOhbMonitorState(reqEntity);
        if ( ohbStateRec == null) {
            return null;
        }
        
        resEntity.state.zoneOccupied = ohbStateRec.getZoneOccupied();
//        Short cap = (ohbStateRec.getZoneCapacity() != null) ? ohbStateRec.getZoneCapacity() : 0;
//        Short occ = (ohbStateRec.getZoneOccupied() != null) ? ohbStateRec.getZoneOccupied() : 0;
//        resEntity.state.zoneEmpty = (short) (cap - occ);
        resEntity.state.zoneCapacity = ohbStateRec.getZoneCapacity();
        resEntity.state.zoneUtility = decimalToPerNumString(ohbStateRec.getZoneUtility());
       
        // アラーム一覧
        for (Alarm alarmRec : alarmRecList) {
            IndividualMonitorAlarmInfoEntity alarmRes = new IndividualMonitorAlarmInfoEntity();

            alarmRes.setTime = ComFunction.timestampToStringSmall(alarmRec.getSetTime());
            alarmRes.alarmId = alarmRec.getAlarmId();
            alarmRes.alarmText = alarmRec.getAlarmText();
            alarmRes.alarmLoc = alarmRec.getAlarmLoc();//20200102 DQY ADD
            alarmRes.vehicleId = alarmRec.getVehicleId();//20200102 DQY ADD

            resEntity.alarmList.add(alarmRes);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ポート画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    ポート情報
     * @retval    List形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<IndividualMonitorPortEntity> getPortInfoList(ReqIndividualMonitorOhbEntity reqEntity) {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorPortEntity> resEntity = new ArrayList<IndividualMonitorPortEntity>();
   
        //20200120 Song Add Start
        Integer displayId = reqEntity.getDisplayId();
        String tableName = null;
        String pieceId = null;
    	for (ScreenMonitorMember screenMonitorMember : ScreenMonitorMemberList1) {
  			if(displayId.equals(screenMonitorMember.getDisplayId())) {
  				tableName = screenMonitorMember.getTableName();
  				pieceId = screenMonitorMember.getDisplayName();
  			}
  		}
    	
        if("TSC".equals(tableName)) {
        	// データ取得
        	 resEntity = iMonitorMapper.getOhbMonitorOHBPortList(reqEntity);
        }else {
        	reqEntity.setPieceId(new Integer(pieceId));
        	// データ取得
        	resEntity = iMonitorMapper.getOhbMonitorOHBPortPieceList(reqEntity);
        }
        //20200120 Song Add End

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        //resEntity = iMonitorMapper.getOhbMonitorOHBPortList(reqEntity); //20200121 Song Del

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ポート画面の色情報を取得する機能
     * @param     recList    Tableに表示するデータ
     * @return    色情報一覧
     * @retval    List形式で返却
     * @attention
     * @note      GUI_COLORテーブルの色情報を使用する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String> getPortColorInfoList(List<IndividualMonitorPortEntity> recList) {

        // ポート画面用色情報を取得
        String[] rgbColorArray = creRgbArray("PORT_STATE", 1000);

        // レコードに対する色情報を形成
        List<String> portColorInfoList = new ArrayList<String>();
        for (IndividualMonitorPortEntity record : recList) {
            Short index = record.getHidePortAvail();

            // PORTの可用性に対応するカラーが無い場合は
            // 固定の色情報を設定
            portColorInfoList.add((index != null && 0 <= index && index < 1000 && rgbColorArray[index] != null)
                    ? rgbColorArray[index] : rgbColorArray[999]);
        }

        return portColorInfoList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief      GUI_COLOR.SECTIONを指定し、String配列で色情報を取得
     * @param      section         取得する色情報のGUI_COLOR.SECTION
     * @param      maxIndex        返却配列のサイズ
     * @return     色情報
     * @retval     String配列形式で返却
     * @note       色情報は#000000の形式で返す
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String[] creRgbArray(String section, int maxIndex) {

        String[] rgbColorArray = new String[maxIndex];

        // 色情報を取得
        GuiColorExample example = new GuiColorExample();
        example.createCriteria().andSectionEqualTo(section);
        example.setOrderByClause("KEY asc");
        List<GuiColor> guiColorList = guiColorMapper.selectByExample(example);

        // 取得した設定色をRGBに変換し、配列化
        for (GuiColor guiColor : guiColorList) {
            Short index = stringToShortNumber(guiColor.getKey());
            if (index != null && 0 <= index && index < maxIndex) {
                rgbColorArray[index] = ComFunction.rgbToHex(guiColor.getRgbRed(), guiColor.getRgbGreen(),
                        guiColor.getRgbBlue());
            }
        }

        return rgbColorArray;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     文字列からShort型数値への変換メソッド
     * @param     value              Short型数値に変換する文字列
     * @return    Short型へ変換された値
     * @retval    Short型で返却
     * @attention
     * @note      Short型へ変換可能な文字列のみ変換を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private Short stringToShortNumber(String value) {

        Short convValue = null;
        try {
            convValue = Short.parseShort(value);
        } catch (Exception e) {
            return null;
        }

        return convValue;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     小数値からパーセント表記への変換メソッド
     * @param     obj              パーセント表記に変換する小数値
     * @return    パーセント表記へ変換された値
     * @retval    String形式で返却
     * @attention
     * @note      小数値をパーセント表記への変換する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String decimalToPerNumString(Object obj) throws McsException {

        String perNumStr = "";
        int perNum = 0;

        if (obj == null) {
            // 変換する値が設定されていない場合
            return perNumStr;
        } else if (obj.getClass().equals(java.math.BigDecimal.class)) {
            // 変換する値がBigDecimal型の場合
            BigDecimal decNum = (BigDecimal) obj;
            perNum = decNum.intValue();

            // パーセント(%)の付与
            perNumStr = String.valueOf(perNum) + "%";
        }

        return perNumStr;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     状態画面用データ取得機能（外部ファイル参照）
     * @param     reqEntity      画面項目情報
     * @return    状態情報
     * @retval    Entity形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public IndividualMonitorStateEntity getStateInfoForeignFile(ReqIndividualMonitorOhbEntity reqEntity)
            throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        IndividualMonitorStateEntity resEntity = new IndividualMonitorStateEntity();

        // -----------------------------------------
        // 状態データ取得
        // -----------------------------------------
        // TODO ダミーファイルの場所を指定
        String stateCmd = "c:\\StateInfoFormat.csv";
        List<String[]> stateColumnList = exeForeignFileService.exeForeignFile(reqEntity, stateCmd);

        // 状態情報0件チェック
        if (stateColumnList == null || stateColumnList.isEmpty()) {
            return null;
        }

        // -----------------------------------------
        // アラーム一覧取得
        // -----------------------------------------
        // TODO ダミーファイルの場所を指定
        String alarmCmd = "c:\\AlarmInfoFormat.csv";
        List<String[]> alarmColumnList = exeForeignFileService.exeForeignFile(reqEntity, alarmCmd);

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        // 状態データ
        String[] stateRec = stateColumnList.get(0);
        resEntity.state.zoneOccupied = stateRec[0] != null ? Short.parseShort(stateRec[0]) : null;
        resEntity.state.zoneCapacity = stateRec[1] != null ? Short.parseShort(stateRec[2]) : null;
//        resEntity.state.zoneEmpty = resEntity.state.zoneOccupied != null && resEntity.state.zoneCapacity != null
//                ? (short) (resEntity.state.zoneCapacity - resEntity.state.zoneOccupied) : null;
        resEntity.state.zoneUtility = decimalToPerNumString(stateRec[2]);

        // アラーム一覧
        for (String[] alarmRec : alarmColumnList) {
            IndividualMonitorAlarmInfoEntity alarmRes = new IndividualMonitorAlarmInfoEntity();

            alarmRes.setTime = ComFunction.timestampToStringSmall(alarmRec[0]);
            alarmRes.alarmId = (!alarmRec[1].isEmpty()) ? Long.parseLong(alarmRec[1]) : null;
            alarmRes.alarmText = alarmRec[2];

            resEntity.alarmList.add(alarmRes);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ポート画面用データ取得機能（外部ファイル参照）
     * @param     reqEntity      画面項目情報
     * @return    ポート情報
     * @throws    McsException
     * @retval    List形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<IndividualMonitorPortEntity> getPortInfoListForeignFile(ReqIndividualMonitorOhbEntity reqEntity)
            throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorPortEntity> resEntity = new ArrayList<IndividualMonitorPortEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        // TODO ダミーファイルの場所を指定
        //20191231 DQY MOD START
//        resEntity = iMonitorMapper.getOhbMonitorPortList(reqEntity);
        String portCmd = "c:\\PortInfoFormat.csv";
        List<String[]> portColumnList = exeForeignFileService.exeForeignFile(reqEntity, portCmd);

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        for (String[] portColumn : portColumnList) {
            IndividualMonitorPortEntity res = new IndividualMonitorPortEntity();

//            res.ohbPriority = portColumn[0];
//            res.portId = portColumn[1];
//            res.portType = portColumn[2];
//            res.portIo = portColumn[3];
//            res.portAvail = portColumn[4];
            res.portId = "TEST01";
            res.portIo = "UP";
            res.portAvail = "Available";
//            res.carrierId = portColumn[5];

            resEntity.add(res);
        }

        /*AlarmExample example = new AlarmExample();
        example.createCriteria().andTscIdEqualTo(reqEntity.displayId);	  //20191225 DQY ADD
        example.setOrderByClause("SET_TIME desc");						  //20191225 DQY ADD
        List<Alarm> alarmRecList = alarmMapper.selectByExample(example);  //20191225 DQY ADD

        // アラーム一覧
        for (Alarm alarmRec : alarmRecList) {
            IndividualMonitorAlarmInfoEntity alarmRes = new IndividualMonitorAlarmInfoEntity();

            alarmRes.setTime = ComFunction.timestampToStringSmall(alarmRec.getSetTime());
            alarmRes.alarmId = alarmRec.getAlarmId();
            alarmRes.alarmText = alarmRec.getAlarmText();

        }
        resEntity.add(alarmRes);*/


        return resEntity;
    }
}
