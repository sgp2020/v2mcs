//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualTscMonitorService.java
 * @brief       個別モニタ(TSCモニタ)関連のサービス
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
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.top;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.common.defines.State;
import net.muratec.mcs.entity.top.IndividualMonitorAlarmInfoEntity;
import net.muratec.mcs.entity.top.IndividualMonitorMicroCmdEntity;
import net.muratec.mcs.entity.top.IndividualMonitorModuleEntity;
import net.muratec.mcs.entity.top.IndividualMonitorStateEntity;
import net.muratec.mcs.entity.top.IndividualMonitorVehicleEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.AlarmMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.IndividualMonitorMapper;
import net.muratec.mcs.mapper.ScreenMonitorMemberMapper;
import net.muratec.mcs.mapper.VehicleMapper;
import net.muratec.mcs.model.Alarm;
import net.muratec.mcs.model.AlarmExample;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.ScreenColorMaster;
import net.muratec.mcs.model.ScreenMonitor;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.ExeForeignFileService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     個別モニタ(TSCモニタ)関連のサービスクラス
 * @par       機能:
 *              getStateInfo（状態画面用データ取得機能）
 *              getVehicleInfoList（台車画面用データ取得機能）
 *              getMicroCmdInfo（Microコマンド画面用データ取得機能）
 *              getMicroCmdColorInfoList（Microコマンド画面用色情報取得機能）
 *              getModuleInfoList（モジュール画面用データ取得機能）
 *              getModuleColorInfoList（モジュール画面用色情報取得機能）
 *              creRgbArray（GUI_COLORからRGB情報の配列を生成する機能）
 *              stringToShortNumber（String型からShort型へ変換する機能）
 *              getStateInfoForeignFile（【外部ファイル参照】状態画面用データ取得機能）
 *              getVehicleInfoListForeignFile（【外部ファイル参照】台車画面用データ取得機能）
 *              getMicroCmdInfoListForeignFile（【外部ファイル参照】Microコマンド画面用データ取得機能）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class IndividualTscMonitorService extends BaseService {

    /** 個別モニタ用マッパー生成 */
    @Autowired private IndividualMonitorMapper iMonitorMapper;
    @Autowired private ScreenMonitorMemberMapper ScreenMonitorMemberMapper;//20191220 DQY ADD FOR STATEINFO OF THE LIMC MAIN
    @Autowired private VehicleMapper vehicleMapper;//20191220 DQY ADD FOR STATEINFO OF THE MAIN

    /** GUI_COLORマッパー生成 */
    @Autowired private GuiColorMapper guiColorMapper;

    /** ALARMマッパー生成 */
    @Autowired private AlarmMapper alarmMapper;

    /** Moduleマッパー生成 */
//    @Autowired private ModuleMapper moduleMapper;

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
    public IndividualMonitorStateEntity getStateInfo(ReqIndividualMonitorEntity reqEntity) throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        IndividualMonitorStateEntity resEntity = new IndividualMonitorStateEntity();

        // -----------------------------------------
        // 状態データ取得
        // -----------------------------------------
//        IndividualMonitorStateInfo stateRec = iMonitorMapper.getConvAndTscMonitorState(reqEntity);//20191220 DQY DEL FOR TABLE CHANGE TO ScreenMonitorMember 
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
//        example.createCriteria().andAmhsIdEqualTo(reqEntity.amhsId);	  //20191218 DQY DEL
        example.createCriteria().andTscIdEqualTo(reqEntity.displayId);	  //20191220 DQY ADD
        example.setOrderByClause("SET_TIME desc");
        List<Alarm> alarmRecList = alarmMapper.selectByExample(example);

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        for (ScreenMonitorMember tscStateRec : stateRec) {
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
		        //20191223 DQY ADD START FOR MCSV2 STATE
		        resEntity.state.alarmState = tscStateRec.getAlarmState();
		        resEntity.state.tscMode = tscStateRec.getTscMode();
		        resEntity.state.pieceMode = tscStateRec.getPieceMode();
		        resEntity.state.pieceAvailable = tscStateRec.getPieceAvailable();
	        }
	        //20191223 DQY ADD START FOR MCSV2 END
	        
        }
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
     * @brief     台車画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    台車情報
     * @retval    List形式で返却
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 2019/12/20 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董 
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<IndividualMonitorVehicleEntity> getVehicleInfoList(ReqIndividualMonitorEntity reqEntity) {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorVehicleEntity> resEntity = new ArrayList<IndividualMonitorVehicleEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        resEntity = iMonitorMapper.getTscMonitorVehicleList(reqEntity);//20191220 DQY DEL
//        VehicleExample configExample = new VehicleExample();
//        configExample.createCriteria().andTscIdEqualTo(reqEntity.displayId);
//        resEntity = vehicleMapper.selectByExample(configExample);	 //20191220 DQY ADD

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Microコマンド画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    Microコマンド情報
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
    public List<IndividualMonitorMicroCmdEntity> getMicroCmdInfoList(ReqIndividualMonitorEntity reqEntity) {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorMicroCmdEntity> resEntity = new ArrayList<IndividualMonitorMicroCmdEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        resEntity = iMonitorMapper.getIndividualMonitorMicroCmdList(reqEntity);

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Microコマンド画面の色情報を取得する機能
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
    public List<String> getMicroCmdColorInfoList(List<IndividualMonitorMicroCmdEntity> recList) {

        // Microコマンド画面用色情報を取得
        String[] rgbColorArray = creRgbArray("CARRIER_JOB", 1000);

        // レコードに対する色情報を形成
        List<String> retList = new ArrayList<String>();
        for (IndividualMonitorMicroCmdEntity record : recList) {
            String color = rgbColorArray[record.hideCarrierJobState];
            retList.add(color);
        }

        return retList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     モジュール画面用データ取得機能
     * @param     reqEntity      画面項目情報
     * @return    モジュール情報
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
    public List<IndividualMonitorModuleEntity> getModuleInfo(ReqIndividualMonitorEntity reqEntity) {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorModuleEntity> resEntity = new ArrayList<IndividualMonitorModuleEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        /*
         * 20191220 DQY DEL
         * List<ModuleModel> retList = moduleMapper.selectModuleInfo(reqEntity.amhsId);
        
        for (ModuleModel data : retList) {
            IndividualMonitorModuleEntity record = new IndividualMonitorModuleEntity();
            record.setModuleId(data.getModuleId());
            record.setModuleName(data.getModuleName());
            record.setAvailable(data.getAvailable());
            record.setAvailableCode(data.getAvailableCode());

            resEntity.add(record);
        }*/

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     モジュール画面の色情報を取得する機能
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
    public List<String> getModuleColorInfoList(List<IndividualMonitorModuleEntity> recList) {

        // モジュール画面用色情報を取得
        String[] rgbColorArray = creRgbArray("TSC_MONITOR_MODULE", 100);

        // レコードに対する色情報を形成
        List<String> retList = new ArrayList<String>();
        for (IndividualMonitorModuleEntity record : recList) {
            String color = rgbColorArray[record.availableCode];
            retList.add(color);
        }

        return retList;
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
    public IndividualMonitorStateEntity getStateInfoForeignFile(ReqIndividualMonitorEntity reqEntity)
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
        resEntity.state.commState = stateRec[0];
        resEntity.state.controlState = stateRec[1];
        resEntity.state.systemState = stateRec[2];
        resEntity.state.available = stateRec[3];

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
     * @brief     台車画面用データ取得機能（外部ファイル参照）
     * @param     reqEntity      画面項目情報
     * @return    台車情報
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
    public List<IndividualMonitorVehicleEntity> getVehicleInfoListForeignFile(ReqIndividualMonitorEntity reqEntity)
            throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorVehicleEntity> resEntity = new ArrayList<IndividualMonitorVehicleEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        // TODO ダミーファイルの場所を指定
        String portCmd = "c:\\VehicleInfoFormat.csv";
        List<String[]> vehicleColumnList = exeForeignFileService.exeForeignFile(reqEntity, portCmd);

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        for (String[] vehicleColumn : vehicleColumnList) {
            IndividualMonitorVehicleEntity res = new IndividualMonitorVehicleEntity();

            res.vehicleId = vehicleColumn[0];
            res.vehicleState = vehicleColumn[1];

            resEntity.add(res);
        }

        return resEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Microコマンド画面用データ取得機能（外部ファイル参照）
     * @param     reqEntity      画面項目情報
     * @return    Microコマンド情報
     * @throws McsException
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
    public List<IndividualMonitorMicroCmdEntity> getMicroCmdInfoListForeignFile(ReqIndividualMonitorEntity reqEntity)
            throws McsException {

        // -----------------------------------------
        // 返却データの生成
        // -----------------------------------------
        List<IndividualMonitorMicroCmdEntity> resEntity = new ArrayList<IndividualMonitorMicroCmdEntity>();

        // -----------------------------------------
        // データ取得
        // -----------------------------------------
        // TODO ダミーファイルの場所を指定
        String microCommandCmd = "c:\\MicroCmdInfoFormat.csv";
        List<String[]> microCmdColumnList = exeForeignFileService.exeForeignFile(reqEntity, microCommandCmd);

        // -----------------------------------------
        // エンティティ形式に変換
        // -----------------------------------------
        for (String[] microCmdColumn : microCmdColumnList) {
            IndividualMonitorMicroCmdEntity res = new IndividualMonitorMicroCmdEntity();

            res.commandId = microCmdColumn[0];
            res.priority = microCmdColumn[1];
            res.carrierId = microCmdColumn[2];
            res.srcLoc = microCmdColumn[3];
            res.dstLoc = microCmdColumn[4];
            res.carrierJobState = microCmdColumn[5];

            resEntity.add(res);
        }

        return resEntity;
    }

}
