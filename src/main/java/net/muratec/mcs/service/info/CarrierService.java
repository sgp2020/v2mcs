//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierService.java
 * @brief       キャリア情報表示画面のサービス
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
 * 2018/10/23 MACS4#0021  LotID,ProductID表示                         T.Iga/CSC
 * 2018/11/08 MACS4#0034  PriorityChange(GUI)対応                     T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.info;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.entity.info.CarrierDispListEntity;
import net.muratec.mcs.entity.info.CarrierListEntity;
import net.muratec.mcs.entity.info.GroupListEntity;
import net.muratec.mcs.entity.info.GroupPortListEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierDelEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierDelTaskEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddBinEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddOhbEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierJobAddTransferEntity;
import net.muratec.mcs.entity.info.ReqGetCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetGroupListEntit;
import net.muratec.mcs.entity.info.ResGetCarrierListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.mapper.CarrierMapper;
import net.muratec.mcs.mapper.DualMapper;
import net.muratec.mcs.mapper.GuiColorMapper;
import net.muratec.mcs.mapper.OhbPortRltMapper;
import net.muratec.mcs.mapper.PortConnMapper;
import net.muratec.mcs.mapper.PortMapper;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.OhbPortRlt;
import net.muratec.mcs.model.OhbPortRltExample;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.PortExample;
import net.muratec.mcs.model.CarrierModel;
import net.muratec.mcs.model.DeleteCarrier;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.MsgUtilService;
import net.muratec.mcs.service.forupgrade.AddCarrierAction;
import net.muratec.mcs.service.forupgrade.DeleteCarrierAction;
import net.muratec.mcs.service.forupgrade.TransportAction;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア情報表示画面のサービスクラス
 * @par       機能:
 *              getCount（キャリア情報一覧の全レコード数を取得）
 *              getCarrierList（キャリア情報一覧の取得）
 *              getCarrierDispList（キャリア情報一覧の取得）機能
 *              getCarrier（キャリア情報の取得）
 *              modCarrier（キャリア情報の修正）
 *              exeDeleteCarrier（キャリア情報の削除）
 *              exeDeleteCarrierList（キャリア情報の強制削除）
 *              exeAddTransferCarrier（キャリア情報の搬送Job登録）
 *              exeAddCarrier（キャリア情報の登録）
 *              getDispRowList（画面表示レコード情報取得）
 *              creRgbArray（キャリア画面の色情報を取得する）
 *              rowColorJud（文字色条件判定）
 *              replaceRowData（レコード情報置き換え）
 *              getPortList(ポートリスト取得)
 *              checkAddCarrierDuplicate（キャリアID、ポートIDの重複チェック）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0034  PriorityChange(GUI)対応                                T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Service
public class CarrierService extends BaseService {

    
    @Autowired private CarrierMapper carrierMapper;
    @Autowired private PortConnMapper portConnMapper;
    @Autowired private TscMapper tscMapper;
    @Autowired private PortMapper portMapper;
    @Autowired private DualMapper dualMapper;
    @Autowired private OhbPortRltMapper ohbPortRltMapper;
    // ! 設定ファイル
    @Autowired private Properties m_msgsvrProperties;
    
    // ------------------------------------
    // GUI_COLORマッパー
    // ------------------------------------
    @Autowired private GuiColorMapper guiColorMapper;

    // ------------------------------------
    // メッセージリソース
    // ------------------------------------
    @Autowired private MessageSource messageSource;

    // ------------------------------------
    // メッセージ送受信サービス
    // ------------------------------------
    @Autowired private MsgUtilService msgUtilService;

    // ------------------------------------
    // メッセージフォーマットプロパティ
    // ------------------------------------
    @Autowired private Properties m_formatProperties;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount（キャリア情報一覧の全レコード数を取得）機能
     * @param     reqEntity      リクエスト（検索条件）
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致したキャリア情報一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetCarrierListEntity reqEntity) {

        return carrierMapper.getCount(reqEntity);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCarrierList（キャリア情報一覧の取得）機能
     * @param     reqEntity      リクエスト（検索条件）
     * @return    キャリア情報一覧
     * @retval    キャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したキャリア情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<CarrierListEntity> getCarrierList(ReqGetCarrierListEntity reqEntity) {

        List<CarrierModel> recList = null;
        // ------------------------------------
        // あいまい検索用に特殊文字をエスケープ
        // ------------------------------------
        reqEntity.carrierId = ComFunction.escapeForLike(reqEntity.carrierId);
        recList = carrierMapper.selectList(reqEntity);

        if (recList == null) {
            return null;
        }

        List<CarrierListEntity> retRecList = new ArrayList<CarrierListEntity>();
        for (CarrierModel carrier : recList) {
            CarrierListEntity entity = new CarrierListEntity();

            entity.carrierId = carrier.getCarrierId();
            entity.status = carrier.getCommState();
            entity.carrierState = carrier.getCarrierState();
            entity.currentTscId = carrier.getCurrentTscId();
            entity.currentLocation = carrier.getCurrentLoc();
            entity.currentZone = carrier.getCurrentZoneId();
            entity.lastStoredTime = carrier.getStoredTime();
            entity.block = carrier.getBlock();
            entity.insystemCarrierID = carrier.getInsystemCarrierId();
            entity.lastInsystemTime = carrier.getInsystemTime();
            entity.insystemLocation = carrier.getInsystemLocation();
            entity.nextDestination = carrier.getNextDst();
            entity.priority = carrier.getPriority().toString();
            entity.offline = carrier.getOffline();

            retRecList.add(entity);
        }

        return retRecList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCarrierDispList（キャリア情報一覧の取得）機能
     * @param     reqEntity      リクエスト（検索条件）
     * @return    キャリア情報一覧
     * @retval    キャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したキャリア情報一覧(色条件判定用のデータも含む)の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<CarrierDispListEntity> getCarrierDispList(ReqGetCarrierListEntity reqEntity) {

        List<CarrierModel> recList = null;
        // ------------------------------------
        // あいまい検索用に特殊文字をエスケープ
        // ------------------------------------
        reqEntity.carrierId = ComFunction.escapeForLike(reqEntity.carrierId);
        recList = carrierMapper.selectList(reqEntity);

        if (recList == null) {
            return null;
        }

        List<CarrierDispListEntity> retRecList = new ArrayList<CarrierDispListEntity>();
        for (CarrierModel carrier : recList) {
            CarrierDispListEntity entity = new CarrierDispListEntity();
            
            entity.carrierId = carrier.getCarrierId();
            entity.status = carrier.getCommState();
            entity.carrierState = carrier.getCarrierState();
            entity.currentTscId = carrier.getCurrentTscId();
            entity.currentLocation = carrier.getCurrentLoc();
            entity.currentZone = carrier.getCurrentZoneId();
            entity.lastStoredTime = carrier.getStoredTime();
            entity.block = carrier.getBlock();
            entity.insystemCarrierID = carrier.getInsystemCarrierId();
            entity.lastInsystemTime = carrier.getInsystemTime();
            entity.insystemLocation = carrier.getInsystemLocation();
            entity.nextDestination = carrier.getNextDst();
            entity.priority = carrier.getPriority().toString();
            entity.offline = carrier.getOffline();
            
            retRecList.add(entity);
        }

        return retRecList;
    }
    
    //
    public boolean beforExeDeleteCarrier(ReqGetCarrierDelTaskEntity reqValidate) throws McsException {

        //
        int sndCarDelCount = 0;
        int sndTraDelCount = 0;
        
        for (ReqGetCarrierDelEntity reqEntity : reqValidate.delList) {
            if( reqEntity.getCarrierState() != null && reqEntity.getCarrierState().equals("Installed/Stored")) {
                /** Carrier削除要求 */
                sndCarDelCount++;
            } else {
                /** Job削除要求 */
                sndTraDelCount++;
            }
        }
        
        int sendType;
        if( sndCarDelCount > 0 && sndTraDelCount > 0 )          sendType = 0;
        else if( sndCarDelCount > 0 && sndTraDelCount == 0 )    sendType = 1;
        else if( sndCarDelCount == 0 && sndTraDelCount > 0 )    sendType = 2;
        else                                                    sendType = 9; // これになることはないけど一応ね.

        if(sendType == 0) {
            // 先にMovingデータの削除を行うから後でもう一度Storedデータの削除もしてねというメッセージを表示.
            return true;
        } else {
            return false;
        }
    }
    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeDeleteCarrier（キャリア情報の削除）機能
     * @param     reqEntity リクエスト(キャリア情報の削除内容)
     * @return    戻り値なし
     * @attention
     * @note      キャリア情報の削除処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<DeleteCarrier> exeDeleteCarrier(ReqGetCarrierDelTaskEntity reqValidate, String strUserName) throws McsException {

        //
        int sndCarDelCount = 0;
        int sndTraDelCount = 0;
        
        for (ReqGetCarrierDelEntity reqEntity : reqValidate.delList) {
            if( reqEntity.getCarrierState() != null && reqEntity.getCarrierState().equals("Installed/Stored")) {
                /** Carrier削除要求 */
                sndCarDelCount++;
            } else {
                /** Job削除要求 */
                sndTraDelCount++;
            }
        }
        
        int sendType;
        if( sndCarDelCount > 0 && sndTraDelCount > 0 )          sendType = 0;
        else if( sndCarDelCount > 0 && sndTraDelCount == 0 )    sendType = 1;
        else if( sndCarDelCount == 0 && sndTraDelCount > 0 )    sendType = 2;
        else                                                    sendType = 9; // これになることはないけど一応ね.
        
        ListIterator<ReqGetCarrierDelEntity> it = reqValidate.delList.listIterator();    
        if(sendType == 1) {
            // RemoveCmdReqを送る際の処理.
            while(it.hasNext()) {
                ReqGetCarrierDelEntity sR = (ReqGetCarrierDelEntity) it.next();
                // RemoveCmdReqはStoredのCarrierのみ行う.
                if(sR.getCarrierState() != null && sR.getCarrierState().equals("Installed/Stored"))
                {
                    // 選択されたデータの内Storedとなっている行を保持する.
                    ;
                } else {
                    it.remove();
                }
            }
        } else {
            // JobDeleteReqを送る際の処理.
            while(it.hasNext()) {
                ReqGetCarrierDelEntity sR = (ReqGetCarrierDelEntity) it.next();
                // JobDeleteReqはStored以外のCarrierで行う(らしい).
                if(!(sR.getCarrierState() != null && sR.getCarrierState().equals("Installed/Stored"))) {
                    // 選択されたデータの内Stored以外となっている行を保持する.
                    ;
                } else {
                    it.remove();
                }
            }
        }
        
        List<DeleteCarrier> sendRows = new ArrayList<DeleteCarrier>();
        List<DeleteCarrier> rcvRows = new ArrayList<DeleteCarrier>();
        String hostName = m_msgsvrProperties.getProperty("connectIP");
        
        for (ReqGetCarrierDelEntity reqEntity : reqValidate.delList) {
            
            DeleteCarrier dC = new DeleteCarrier();
            
            dC.setCarrierId(reqEntity.getCarrierId());
            
            Integer execSid = tscMapper.getExecSidByCarrier(dC.getCarrierId());
            
            if(execSid == null) {
                // とりあえず、ExecSidを返す
                execSid = 301;
            } 
            dC.setExecSid(execSid);
            //发送之前共通的验证，在中写在共通发送前，数据库类操作移至这里。
            String procState = tscMapper.getProcState( sendType == 1 ? execSid + 2000 : execSid + 1000);
            if( procState == null || !"Running".equals(procState)) {
                dC.setAns("-232");
                rcvRows.add(dC);
            } else {
                sendRows.add(dC);
            }
        }   
 
        int guiId = dualMapper.getGuiId();
        DeleteCarrierAction action = new DeleteCarrierAction(guiId);
        if(sendType == 0) {
            // 先にMovingデータの削除を行うから後でもう一度Storedデータの削除もしてねというメッセージを表示.
            rcvRows.addAll(action.sendRemoveCmdReq(sendRows, strUserName, hostName, 2));
        } else if(sendType != 9) {
            rcvRows.addAll(action.sendRemoveCmdReq(sendRows, strUserName, hostName, sendType));
        } else {
            // 未選択の時のみNONEで返ってくるが,そんなことはありえないので今回は処理を書かない.
            ;
        }
        
        return rcvRows;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeAddTransferCarrier（キャリア情報の搬送Job登録）機能
     * @param     reqEntity リクエスト(キャリア情報の搬送Job登録内容)
     * @return    戻り値なし
     * @attention
     * @note      キャリア情報の搬送Job登録処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0034  PriorityChange(GUI)対応                                T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public String exeAddTransferCarrier(ReqGetCarrierJobAddTransferEntity reqEntity, String strUserName)
            throws McsException {
        
          //原发送时有用的验证 
          String srcTsc = portConnMapper.getSrcTsc(reqEntity.getFromCzone(), reqEntity.getFromPort());
          if(srcTsc == null) srcTsc = "";
          String dstTsc = portConnMapper.getDstTsc(reqEntity.getController(), reqEntity.getPort());
          if(dstTsc == null) dstTsc = "";
          Integer execSid = 0;
          if(!"".equals(srcTsc) && Integer.parseInt(srcTsc) > 0) execSid = tscMapper.getExecSid(Integer.parseInt(srcTsc));
          if(execSid == null) execSid = 0;
          if(execSid == 0 && !"".equals(dstTsc) && Integer.parseInt(dstTsc) > 0) execSid = tscMapper.getExecSid(Integer.parseInt(dstTsc));
          if(execSid == null) execSid = 0;
          if(execSid == 0) execSid = tscMapper.getAllExecSid();
          if(execSid == null) execSid = 0;
          if(execSid == 0) return "-230";
        
          String hostName = m_msgsvrProperties.getProperty("connectIP");
          int guiId = dualMapper.getGuiId();

          //发送之前共通的验证，在中写在共通发送前，数据库类操作移至这里。
          String procState = tscMapper.getProcState(execSid < 1000 ? execSid + 1000 : execSid);
          if( procState == null || !"Running".equals(procState)) return "-232";
          
          TransportAction action = new TransportAction(guiId);
          return action.sendTransferCmdReq(reqEntity, strUserName, execSid, hostName);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     exeAddCarrier（キャリア情報の登録）機能
     * @param     reqEntity      リクエスト（キャリア情報の追加内容）
     * @return    戻り値なし
     * @attention
     * @note      キャリア情報の登録処理を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public String exeAddCarrierBin(ReqGetCarrierJobAddBinEntity reqEntity, String strUserName) throws McsException {

        //
        Integer execSid = tscMapper.getExecSid(Integer.parseInt(reqEntity.getStkId()));
        
        String hostName = m_msgsvrProperties.getProperty("connectIP");
        int guiId = dualMapper.getGuiId();
        
        //发送之前共通的验证，在中写在共通发送前，数据库类操作移至这里。
        String procState = tscMapper.getProcState(execSid + 2000);
        if( procState == null || !"Running".equals(procState)) return "-232";
        
        AddCarrierAction action = new AddCarrierAction(guiId);
        return action.sendAddCarrierBinCmdReq(reqEntity, strUserName, execSid, hostName);
        
    }
    
    //
    public String exeAddCarrierOhb(ReqGetCarrierJobAddOhbEntity reqEntity, String strUserName) throws McsException {

        //
        PortExample portExample = new PortExample();
        portExample.createCriteria().andPortIdEqualTo(reqEntity.getPortId());
        List<Port> portList = portMapper.selectByExample(portExample);

        Integer tscId = null;
        for (Port port : portList) {
            tscId = port.getManagementTscId();
        }
        
        Integer execSid;
        if(0 > tscId) {
            execSid = 999;
        } else {
            execSid = tscMapper.getExecSidByPort(reqEntity.getPortId());
        }        
      
        String hostName = m_msgsvrProperties.getProperty("connectIP");
        int guiId = dualMapper.getGuiId();
        
        //发送之前共通的验证，在中写在共通发送前，数据库类操作移至这里。
        String procState = tscMapper.getProcState(execSid + 2000);
        if( procState == null || !"Running".equals(procState)) return "-232";
        
        AddCarrierAction action = new AddCarrierAction(guiId);
        return action.sendAddCarrierOhbCmdReq(reqEntity, strUserName, execSid, hostName);
        
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getDispRowList（画面表示レコード情報取得）機能
     * @param     reqEntity     画面表示(色情報含む)データ
     * @return    画面表示レコード情報と色情報データを取得する
     * @retval
     * @attention
     * @note      画面表示レコード情報と色情報データを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public ResGetCarrierListEntity getDispRowList(List<CarrierDispListEntity> reqEntity) {

        ResGetCarrierListEntity retList = new ResGetCarrierListEntity();

        List<CarrierListEntity> body = new ArrayList<CarrierListEntity>();
        String[] colorList = creRgbArray();
        List<String> rowColorList = new ArrayList<String>();

        // レコード毎にレコードの詰め替えと文字色を取得
        for (CarrierDispListEntity record : reqEntity) {
            CarrierListEntity rowData = null;
            String color = null;

            // レコードの詰め替え
            rowData = replaceRowData(record);
            // 文字色条件の判定
            color = rowColorJud(record, colorList);

            body.add(rowData);
            rowColorList.add(color);
        }

        retList.body = body;
        retList.rowColorList = rowColorList;

        return retList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     creRgbArray（キャリア画面の色情報を取得する）機能
     * @param     なし
     * @return    色情報一覧
     * @retval    String[]形式で返却
     * @attention
     * @note      GUI_COLORテーブルの色情報を使用する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    private String[] creRgbArray() {

        String[] rgbColorArray = new String[1000];

        // 色情報を取得
        GuiColorExample example = new GuiColorExample();
        example.createCriteria().andSectionEqualTo("CARRIER");
        example.setOrderByClause("KEY asc");
        List<GuiColor> guiColorList = guiColorMapper.selectByExample(example);

        // 取得した設定色をRGBに変換し、配列化
        for (GuiColor guiColor : guiColorList) {
            Short index = new Short(guiColor.getKey());
            if (index != null && 0 <= index && index < 1000) {
                rgbColorArray[index] = ComFunction.rgbToHex(guiColor.getRgbRed(), guiColor.getRgbGreen(),
                        guiColor.getRgbBlue());
            }
        }

        return rgbColorArray;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     rowColorJud（文字色条件判定）機能
     * @param     record レコード情報
     * @param     rgbColor 文字色情報
     * @return    レコード文字色情報
     * @retval    String[]形式で返却
     * @attention
     * @note      レコード文字色情報を返却する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    private String rowColorJud(CarrierDispListEntity record, String[] rgbColor) {
        String ret = null;

        if (record.carrierState != null && record.block != null) {
            if ("NotAvailable".equals(record.status)) {
                ret = rgbColor[ComConst.Carrier.KEY_OFFLINE];
            } else {
                if ("Installed/Stored".equals(record.carrierState)) {
                    ret = rgbColor[7];  //白
                } else {
                    ret = rgbColor[5];  //黄
                }
                if ("Block".equals(record.block)) {
                    ret = rgbColor[2]; //红色
                }
            }
        }
        return ret;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     replaceRowData（レコード情報置き換え）機能
     * @param     record レコード情報
     * @return    レコード情報
     * @retval
     * @attention
     * @note      画面表示に使用する情報のみへ置き換えを実施
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0021  LotID,ProductID表示                                    T.Iga/CSC
     ******************************************************************************
     */
    private CarrierListEntity replaceRowData(CarrierDispListEntity record) {
        CarrierListEntity ret = new CarrierListEntity();
        ret.carrierId = record.carrierId;
        ret.status = record.status;
        ret.carrierState = record.carrierState;
        ret.currentTscId = record.currentTscId;
        ret.currentLocation = record.currentLocation;
        ret.currentZone = record.currentZone;
        ret.lastStoredTime = record.lastStoredTime;
        ret.block = record.block;
        ret.insystemCarrierID = record.insystemCarrierID;
        ret.lastInsystemTime = record.lastInsystemTime;
        ret.insystemLocation = record.insystemLocation;
        ret.nextDestination = record.nextDestination;
        ret.priority = record.priority;
        ret.offline = record.offline;

        return ret;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getPortList（ポートリスト取得）機能
     * @param     ohbId     OHB_ID
     * @return    ポートリスト（セレクトボックスのリスト形式で返す）
     * @retval
     * @attention
     * @note      OHB_IDをもとにOHB_PORT_RLTテーブルからポートリストを取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<String[]> getPortList(String ohbId) {

        List<String[]> retList = new ArrayList<String[]>();

        if (ohbId == null || "".equals(ohbId)) {
            PortExample portExample = new PortExample();
            portExample.createCriteria().andPortTypeEqualTo((short) -10);
            portExample.setOrderByClause("PORT_ID ASC");
            List<Port> portList = portMapper.selectByExample(portExample);

            for (Port port : portList) {
                String[] data = new String[2];
                data[0] = port.getPortId();
                data[1] = port.getPortId();
                retList.add(data);
            }
        } else {
            OhbPortRltExample example = new OhbPortRltExample();
            example.createCriteria().andOhbIdEqualTo(ohbId);
            example.setOrderByClause("PORT_ID ASC");
            List<OhbPortRlt> ohbPortRltList = ohbPortRltMapper.selectByExample(example);
    
//            if (ohbPortRltList == null || ohbPortRltList.isEmpty()) {
//                String[] data = new String[2];
//                data[0] = "";
//                data[1] = "";
//                retList.add(data);
//            } else {
                for (OhbPortRlt ohbPortRlt : ohbPortRltList) {
                    String[] data = new String[2];
                    data[0] = ohbPortRlt.getPortId();
                    data[1] = ohbPortRlt.getPortId();
                    retList.add(data);
//                }
            }
        }
        return retList;
    }

    
    //
    public GroupListEntity getGroupAvailable(ReqGetGroupListEntit reqEntity) throws McsException {
        
        GroupListEntity res = new GroupListEntity();
        Port port = portMapper.selectByPrimaryKey(reqEntity.getGroupPortId());
        if (port == null) {
            return res;
        }
        String strIbsemAvail  = port.getIbsemAvail();
        String strAvailable  = port.getAvailable();

        if("Avail".equals(strIbsemAvail) && "Available".equals(strAvailable)) {
            res.strAvailable = strAvailable;
            res.strColor = "#00FF00";   //Color.green
        } else if("Available".equals(strAvailable)) {
            res.strAvailable = strIbsemAvail;
            if(strIbsemAvail !=null && !"".equals(strIbsemAvail)) {
                res.strColor = "#FF0000";   // Color.red
            }
        } else {
            res.strAvailable = strAvailable;
            if(strAvailable != null && !"".equals(strAvailable)) {
                res.strColor = "#FF0000";   // Color.red
            }
        }
        
        return res;        
    }        

    //
    public List<GroupPortListEntity> getGroupPort(ReqGetGroupListEntit reqEntity) throws McsException {
        
        List<GroupPortListEntity> resList = new ArrayList<GroupPortListEntity>();
        resList = carrierMapper.selectgetGroupIdList(reqEntity.getGroupPortId());
        if (resList == null) {
            return new ArrayList<GroupPortListEntity>();
        }
        
        return resList;        
    } 
        
}
