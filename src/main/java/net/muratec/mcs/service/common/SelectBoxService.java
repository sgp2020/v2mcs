/**
 ******************************************************************************
 * @file        SelectBoxService.java
 * @brief       画面部品SelectBoxで扱うサービス
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
 * 2018/10/18 MACS4#0016  GUI客先テストNG項目対応                           CSC
 * 2019/02/19 MACS4#0099  iFoup設定画面変更                           T.Iga/CSC
 * 2019/03/14 MACS4#0119  GUI Controller Type修正                     T.Iga/CSC
 * 2019/04/19 MACS4#0159  代替元設定変更対応                          T.Iga/CSC
 * 2019/04/22 MACS4#0160  IFOHB表示非表示対応                         T.Iga/CSC
 * 2019/12/17 MACS4#0225  MACSV2→MACSV4対応                         天津村研　董
 ******************************************************************************
 */
package net.muratec.mcs.service.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.mapper.AmhsMapper;
import net.muratec.mcs.mapper.OhbMapper;
import net.muratec.mcs.mapper.PortMapper;
import net.muratec.mcs.mapper.ScreenMonitorMemberMapper;
import net.muratec.mcs.mapper.StringMasterMapper;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.model.Amhs;
import net.muratec.mcs.model.AmhsExample;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.OhbExample;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.PortExample;
import net.muratec.mcs.model.ScreenMonitorMember;
import net.muratec.mcs.model.ScreenMonitorMemberExample;
//import net.muratec.mcs.model.PurposeType;             // MACS4#0099 Del
//import net.muratec.mcs.model.PurposeTypeExample;      // MACS4#0099 Del
import net.muratec.mcs.model.StringMaster;
import net.muratec.mcs.model.StringMasterExample;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;

//@formatter:off
/**
 ******************************************************************************
 * @brief     画面部品SelectBoxで扱うサービスクラス
 * @par       機能:
 *              getZoneBox（ZONEテーブル取得処理）
 *              getAmhsIdBox（AMHSテーブル取得処理(AMHS_IDのみ)）
 *              getAmhsIdBoxByTypeList（指定したAMHSタイプのリストからAMHSId取得しSelectBox用配列で返す(AMHS_IDのみ)）
 *              getAmhsIdBoxByType（指定したAMHSタイプのAMHSId取得しSelectBox用配列で返す(AMHS_IDのみ)）
 *              getcarrierActionBox（CARRIERテーブル取得処理(アクションフラグ)）
 *              getStringMasterList（ストリングマスターテーブル取得処理）
 *              getStrMstBoxOrderByCode（ストリングマスターテーブル取得処理（コード順））
 *              getStrMstBoxByString（ストリングマスターテーブル取得処理（ストリング順））
 *              getHostNameList（HOSTテーブル取得処理）
 *              getCarrierShapeId（CARRIER_SHAPE_ID一覧取得処理）
 *              getCarrierTypeId（CARRIER_TYPE_ID一覧取得処理）
 *              getController（Controller一覧取得処理）
 *              getControllerByType（指定したAMHSタイプのControllerの一覧を取得しSelectBox用配列で返す）
 *              getControllerByType（(複数指定可)指定したAMHSタイプのControllerの一覧を取得しSelectBox用配列で返す）
 *              getAmhsString（AMHS_TYPEのSTRING_MASTER一覧取得処理）
 *              getOhbId（OHB_IDの一覧を取得しSelectBox用配列で返す）
 *              getOhbIdByAmhsId(指定したAMHS_IDのOHB_IDの一覧を取得しSelectBox用配列で返す)
 *              getAmhsByType（指定したAMHS_TYPEのAMHS_IDの一覧を取得しSelectBox用配列で返す）
 *              getDefaultTransGrpId（defaultTransGrpId一覧取得処理）
 *              getAmhsGrpId（amhsGrpId一覧取得処理）
 *              getZoneNameBox（ZONE一覧取得処理）
 *              getGroupIdSelBox（GUI_GROUP_IDの一覧を取得しSelectBox用配列で返す）
 *              getStrMstRemoveCode（指定したキーのSTRING_MASTERから指定したコードを除いた一覧を取得しSelectBox用配列で返す）
 *              getUnitIdBox（UNITテーブル取得処理(UNIT_IDのみ)）
 *              getHostNameOnlyList（HOST_NAME取得処理(HOST_NAMEのみ)）
 *              getServiceIdBox（PROCESS.SERVICE_ID取得処理）
 *              getNodeIdBox（NODEテーブル取得処理）
 *              getVipIdBox（VIPテーブル取得処理）
 *              getSystemIdBox（PROCESS.SYSTEM_ID取得処理）
 *              getSystemIdBoxByServiceId（指定したサービスIDに紐付くPROCESS.SYSTEM_ID取得処理）
 *              getOhbIdForLocation（OHB_IDの一覧を取得しSelectBox用配列で返す）
 *              getStockerGrpIdBox（GRP.GROUP_ID取得処理）
 *              getPortIdBoxByController（指定したコントローラに紐付くPORT.PORT_ID取得処理）
 *              getPortIdBoxByOhbId（指定したOHB_IDに紐付くOHB_PORT_RLT.OHB_ID取得処理）
 *              getPortIdByAmhsIdAndType(指定したAMHS_ID、ポートタイプに紐づくPORT.PORT_ID取得処理)
 *              getStrMstGetCodeList(指定したキーのSTRING_MASTERから指定されたコードのみ取得処理)
 *              getEmptyCarrierControllerControllIdBox(空FOUPコントローラテーブルのCONTROLLER_IDの一覧を取得しSelectBox用配列で返す)
 *              getEmptyCarrierContamiContamiIdBox(空FOUPコンタミテーブルのCONTAMI_IDを全件取得しSelectBox用配列で返す)
 *              getPurposeTypePurposeTypeBox(空FOUP目的種別テーブルのPURPOSE_TYPEを全件取得しSelectBox用配列で返す)
 *              getModuleIdBoxByAmhsId（指定したAMHS IDに紐付くMODULE.MODULE_ID取得処理）
 *              getAltSrcTypeBox（代替元タイプ一覧を作成しSelectBox用配列で返す）
 *              getEqpControllerIdList(装置コントローラIDリストを取得しSelectBox用配列で返す)
 *              getEqpPortIdList(装置ポートIDリストを取得しSelectBox用配列で返す)
 *              getOhbIdBoxByAmhsType(指定したAMHSタイプのAMHS_Idを取得し、そのAMHS_IDと一致するOHB_IDのSelectBox用配列を返す)
 *              getDefaultProcessTypeBox(DEFAULT_PROCESSテーブルからProcessTypeを全件取得しSelectBox用配列で返す)
 *              getRoutePatternIdSelBox(ROUTEテーブルからPATTERN_ID(distinct)を全件取得し、selectBox用配列で返す)
 *              getAmhsAndOhbControllerIdBox(AMHS.AMHS_ID（STCのみ）、OHB.OHB_ID（全件）を取得し、selectBox用配列で返す)
 *              getEqpTypeBox（EQP_TYPEテーブルを全件取得しSelectBox用配列で返す）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Service
public class SelectBoxService extends BaseService {

    // ----------------------------------------------
    // ログ出力
    // ----------------------------------------------
    private static final Logger logger = LoggerFactory.getLogger(SelectBoxService.class);

    // ----------------------------------------------
    // ゾーンマッパー
    // ----------------------------------------------
//    @Autowired private ZoneMapper zoneMapper;

    // ----------------------------------------------
    // AMHSマッパー
    // ----------------------------------------------
    @Autowired private AmhsMapper amhsMapper;

    // ----------------------------------------------
    // キャリアマッパー
    // ----------------------------------------------
//    @Autowired private CarrierMapper carrierMapper;

    // ----------------------------------------------
    // StringMasterマッパー
    // ----------------------------------------------
    @Autowired private StringMasterMapper stringMasterMapper;

    // ----------------------------------------------
    // Hostマッパー
    // ----------------------------------------------
//    @Autowired private HostMapper hostMapper;

    // ----------------------------------------------
    // キャリアシェイプマッパー
    // ----------------------------------------------
//    @Autowired private CarrierShapeMapper carrierShapeMapper;

    // ----------------------------------------------
    // キャリアタイプマッパー
    // ----------------------------------------------
//    @Autowired private CarrierTypeMapper carrierTypeMapper;

    // ----------------------------------------------
    // テイクオーバールールリレーションマッパー
    // ----------------------------------------------
//    @Autowired private TakeoverRuleRltMapper trrMapper;


    // ----------------------------------------------
    // GuiFuncGroupマッパー
    // ----------------------------------------------
//    @Autowired private GuiFuncGroupMapper guiFuncGroupMapper;

    // ----------------------------------------------
    // Unitマッパー
    // ----------------------------------------------
//    @Autowired private UnitMapper unitMapper;

    // ----------------------------------------------
    // Processマッパー
    // ----------------------------------------------
//    @Autowired private ProcessMapper processMapper;

    // ----------------------------------------------
    // Nodeマッパー
    // ----------------------------------------------
//    @Autowired private NodeMapper nodeMapper;

    // ----------------------------------------------
    // Vipマッパー
    // ----------------------------------------------
//    @Autowired private VipMapper vipMapper;

    // ----------------------------------------------
    // GRPマッパー
    // ----------------------------------------------
//    @Autowired private GrpMapper grpMapper;
//  @Autowired private HostGrpMapper hostGrpMapper;  // Hostポートグループ対応 - 制御側未対応のため、未使用


    // ----------------------------------------------
    // OHB_PORT_RLTマッパー
    // ----------------------------------------------
//    @Autowired private OhbPortRltMapper ohbPortRltMapper;

    // ----------------------------------------------
    // EMPTY_CARRIER_CONTROLLERマッパー
    // ----------------------------------------------
//    @Autowired private EmptyCarrierControllerMapper emptyCarrierControllerMapper;

    // ----------------------------------------------
    // EMPTY_CARRIER_CONTAMIマッパー
    // ----------------------------------------------
//    @Autowired private EmptyCarrierContamiMapper emptyCarrierContamiMapper;

    // ----------------------------------------------
    // PURPOSE_TYPEマッパー
    // ----------------------------------------------
//  @Autowired private PurposeTypeMapper purposeTypeMapper;     // MACS4#0099 Del

    // ----------------------------------------------
    // MODULEマッパー
    // ----------------------------------------------
//    @Autowired private ModuleMapper moduleMapper;

    // ----------------------------------------------
    // HOST_ALIASマッパー
    // ----------------------------------------------
//    @Autowired private HostAliasMapper hostAliasMapper;

    // ----------------------------------------------
    // DEFAULT_PROCESSマッパー
    // ----------------------------------------------
//    @Autowired private DefaultProcessMapper defaultProcessMapper;  // プロセスデフォルト対応 - 制御側未対応のため、未使用

    // ----------------------------------------------
    // ROUTEマッパー
    // ----------------------------------------------
//    @Autowired private RouteMapper routeMapper;

    // ----------------------------------------------
    // EQP_TYPEマッパー
    // ----------------------------------------------
//    @Autowired private EqpTypeMapper eqpTypeMapper;

    @Autowired private ScreenMonitorMemberMapper screenMonitorMemberMapper;// MACSV2→MACSV4対応                         天津村研　董
    
    @Autowired private TscMapper tscMapper;     //zhangyi
    @Autowired private OhbMapper ohbMapper;     //zhangyi
    @Autowired private PortMapper portMapper;   //zhangyi

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ZONEテーブルを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[ZONE_ID][ZONE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getZoneBox() {
//
//        ZoneExample zoneExample = new ZoneExample();
//        zoneExample.createCriteria().andZoneTypeEqualTo(ComConst.ZoneType.CODE_LOGICAL);
//        zoneExample.setOrderByClause("ZONE_ID ASC");
//
//        List<Zone> zoneList = zoneMapper.selectByExample(zoneExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Zone zone : zoneList) {
//            String[] data = new String[2];
//            data[0] = zone.getZoneId();
//            data[1] = zone.getZoneId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AMHSテーブルを全件取得しSelectBox用配列で返す(AMHS_IDのみ)
     * @param
     * @return    [[AMHS_ID][AMHS_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getAmhsIdBox() {
//
//        AmhsExample amhsExample = new AmhsExample();
//        amhsExample.createCriteria();
//        amhsExample.setOrderByClause("AMHS_ID ASC");
//
//        List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Amhs amhs : amhsList) {
//            String[] data = new String[2];
//            data[0] = amhs.getAmhsId();
//            data[1] = amhs.getAmhsId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したAMHSタイプのAMHSId取得しSelectBox用配列で返す(AMHS_IDのみ)
     * @param     amhsTypeList   取得対象のAMHSタイプのリスト
     * @return    [[AMHS_ID][AMHS_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getAmhsIdBoxByTypeList(List<Short> amhsTypeList) {
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for(Short amhsType : amhsTypeList) {
//            // 指定したAMHSタイプのAMHSIdの一覧を取得
//            AmhsExample amhsExample = new AmhsExample();
//            amhsExample.createCriteria().andAmhsTypeEqualTo(amhsType);
//            amhsExample.setOrderByClause("AMHS_ID asc");
//            List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);
//
//            for (Amhs amhs : amhsList) {
//                String[] data = new String[2];
//                data[0] = amhs.getAmhsId();
//                data[1] = amhs.getAmhsId();
//                selBoxList.add(data);
//            }
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したAMHSタイプのAMHSId取得しSelectBox用配列で返す(AMHS_IDのみ)
     * @param     amhsType    取得対象のAMHSタイプ
     * @return    [[AMHS_ID][AMHS_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getAmhsIdBoxByType(Short amhsType) {
//
//        // 指定したAMHSタイプのAMHSIdの一覧を取得
//        AmhsExample amhsExample = new AmhsExample();
//        amhsExample.createCriteria().andAmhsTypeEqualTo(amhsType);
//        amhsExample.setOrderByClause("AMHS_ID asc");
//        List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Amhs amhs : amhsList) {
//            String[] data = new String[2];
//            data[0] = amhs.getAmhsId();
//            data[1] = amhs.getAmhsId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターをKEYで取得する
     * @param     key            キー
     * @param     order          並び順
     * @return    [[CODE][STRING]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    private List<String[]> getStringMasterList(String key, String order) {

        logger.debug("key=[" + key + "] order by=[" + order + "]");

        StringMasterExample strMstExample = new StringMasterExample();
        strMstExample.createCriteria().andKeyEqualTo(key);
        strMstExample.setOrderByClause(order);

        List<StringMaster> strMstList = stringMasterMapper.selectByExample(strMstExample);

        List<String[]> selBoxList = new ArrayList<String[]>();
        for (StringMaster strMst : strMstList) {
            String[] data = new String[2];
            data[0] = strMst.getCode().toString();
            data[1] = strMst.getString();
            selBoxList.add(data);
        }

        return selBoxList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターをKEYで取得しSelectBox用配列で返す(CODE昇順)
     * @param     key            キー
     * @return    [[CODE][STRING]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public List<String[]> getStrMstBoxOrderByCode(String key) {

        return getStringMasterList(key, "CODE ASC");
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ストリングマスターをKEYで取得しSelectBox用配列で返す(STRING昇順)
     * @param     key            キー
     * @return    [[CODE][STRING]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getStrMstBoxByString(String key) {
//
//        return getStringMasterList(key, "STRING ASC");
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     HOSTの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[HOST_DRV_SRV_ID][HostName(HOST_DRV_SRV_ID)]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getHostNameList() {
//
//        HostExample hostExample = new HostExample();
//        hostExample.createCriteria();
//        hostExample.setOrderByClause("HOST_DRV_SRV_ID ASC");
//
//        List<Host> hostList = hostMapper.selectByExample(hostExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Host host : hostList) {
//            String[] data = new String[2];
//
//            // TODO Host名をValueに設定
//            // data[0] = host.getHostDrvSrvId().toString();
//            // data[1] = host.getHostName().toString() + "(" + data[0] + ")";
//            data[0] = host.getHostName().toString();
//            data[1] = host.getHostName().toString() + "(" + host.getHostDrvSrvId().toString() + ")";
//
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CARRIER_SHAPE_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[CARRIER_SHAPE_ID][CARRIER_SHAPE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getCarrierShapeId() {
//
//        List<String> carrierShapeId = carrierShapeMapper.getCarrierShapeId();
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (String selItem : carrierShapeId) {
//            String[] data = new String[2];
//            data[0] = selItem;
//            data[1] = selItem;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     CARRIER_TYPE_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[CARRIER_TYPE_ID][CARRIER_TYPE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getCarrierTypeId() {
//
//        List<String> carrierTypeId = carrierTypeMapper.getCarrierTypeId();
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (String selItem : carrierTypeId) {
//            String[] data = new String[2];
//            data[0] = selItem;
//            data[1] = selItem;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     Controllerの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[AMHS_ID][AMHS_NAME(AMHS_ID)]]
     * @retval
     * @attention
     * @note      AMHS_IDの昇順ソートでController一覧を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getController() {
//
//        AmhsExample amhsExample = new AmhsExample();
//        amhsExample.createCriteria().andDummyFlagEqualTo((short) 0);
//        amhsExample.setOrderByClause("AMHS_ID ASC");
//
//        List<Amhs> controller = amhsMapper.selectByExample(amhsExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Amhs selItem : controller) {
//            String[] data = new String[2];
//            data[0] = selItem.getAmhsId();
//            StringBuffer sb = new StringBuffer(selItem.getAmhsName());
//            sb.append("(");
//            sb.append(selItem.getAmhsId());
//            sb.append(")");
//            data[1] = sb.toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したAMHSタイプのControllerの一覧を取得しSelectBox用配列で返す
     * @param     amhsType    取得対象のAMHSタイプ
     * @return    [[AMHS_ID][AMHS_NAME(AMHS_ID)]]
     * @retval
     * @attention
     * @note      AMHS_IDの昇順ソートでController一覧を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<String[]> getControllerByType(Short amhsType) {

        // 指定したAMHSタイプのAMHSIdの一覧を取得
        AmhsExample amhsExample = new AmhsExample();
        amhsExample.createCriteria().andAmhsTypeEqualTo(amhsType);
        amhsExample.setOrderByClause("AMHS_ID asc");
        List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);

        // セレクトボックス用の配列に変換
        List<String[]> amhsNameSelList = new ArrayList<String[]>();
        for (Amhs amhs : amhsList) {
            amhsNameSelList.add(new String[] { amhs.getAmhsId(), amhs.getAmhsName() + "(" + amhs.getAmhsId() + ")" });
        }

        return amhsNameSelList;
    }


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     (複数指定可)指定したAMHSタイプのControllerの一覧を取得しSelectBox用配列で返す
     * @param     amhsTypeList    取得対象のAMHSタイプ(List形式)
     * @return    [[AMHS_ID][AMHS_NAME(AMHS_ID)]]
     * @retval
     * @attention
     * @note      AMHS_IDの昇順ソートでController一覧を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    /*public List<String[]> getControllerByType(List<Short> amhsTypeList) {

        // 指定したAMHSタイプのAMHSIdの一覧を取得
        AmhsExample amhsExample = new AmhsExample();
        amhsExample.createCriteria();
        for (Short amhsType : amhsTypeList) {
            amhsExample.or().andAmhsTypeEqualTo(amhsType);
        }
        amhsExample.setOrderByClause("AMHS_TYPE asc, AMHS_ID asc");
        List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);

        // セレクトボックス用の配列に変換
        List<String[]> amhsNameSelList = new ArrayList<String[]>();
        for (Amhs amhs : amhsList) {
            amhsNameSelList.add(new String[] { amhs.getAmhsId(), amhs.getAmhsName() + "(" + amhs.getAmhsId() + ")",
                    String.valueOf(amhs.getAmhsType()) });
        }

        return amhsNameSelList;
    }*/
    public List<String[]> getControllerByType(List<String> tscTypeList) {
    	
    	// 指定したAMHSタイプのAMHSIdの一覧を取得
    	ScreenMonitorMemberExample screenMonitorMemberExample = new ScreenMonitorMemberExample();
    	
    	for (String tscType : tscTypeList) {
    		screenMonitorMemberExample.or().andMemberGroupEqualTo(tscType);
        }
    	screenMonitorMemberExample.setOrderByClause("DISPLAY_ID asc");
    	List<ScreenMonitorMember> tscList = screenMonitorMemberMapper.selectByExample(screenMonitorMemberExample);
    	
    	// セレクトボックス用の配列に変換
    	List<String[]> amhsNameSelList = new ArrayList<String[]>();
    	for (ScreenMonitorMember tscs : tscList) {
    		amhsNameSelList.add(new String[] { String.valueOf(tscs.getDisplayId()), tscs.getDisplayName() + "(" + String.valueOf(tscs.getDisplayId()) + ")" ,
                    String.valueOf(tscs.getMemberGroup()) });
    	}
    	
    	return amhsNameSelList;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AMHS_TYPEのSTRING_MASTERの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[AMHS_TYPE][STRING]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     * MACS4#0119  GUI Controller Type修正                                T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    public List<String[]> getAmhsString() {

        StringMasterExample stringMasterExample = new StringMasterExample();
//      stringMasterExample.createCriteria().andKeyEqualTo("AMHS_TYPE_STRING").andCodeGreaterThan((long) 0);
        // MACS4#0119 Add Start
        stringMasterExample.createCriteria().andKeyEqualTo(ComConst.StringMaster.KEY_AMHS_TYPE)
                                            .andCodeGreaterThan((long) 0)
                                            .andCodeNotEqualTo((long) ComConst.AmhsType.CODE_MCS);
        // MACS4#0119 Add End
        stringMasterExample.setOrderByClause("CODE ASC");

        List<StringMaster> stringMaster = stringMasterMapper.selectByExample(stringMasterExample);
        List<String[]> selBoxList = new ArrayList<String[]>();
        for (StringMaster selItem : stringMaster) {
            String[] data = new String[2];
            data[0] = String.valueOf(selItem.getCode());
            data[1] = selItem.getString();
            selBoxList.add(data);
        }

        return selBoxList;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     OHB_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[OHB_ID][OHB_ID(AMHS_TYPE)][フィルター用の連番]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     ******************************************************************************
     */
    /*//@formatter:on
    public List<String[]> getOhbId() {

        List<OhbMCS> ohbList = ohbMapper.getOhbForType();
        List<String[]> selBoxList = new ArrayList<String[]>();
        for (OhbMCS ohb : ohbList) {
            String[] data = new String[3];
            data[0] = ohb.getOhbId();
            data[1] = ohb.getAmhsType();
            data[2] = "1";
            selBoxList.add(data);
        }

        return selBoxList;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     OHB_IDの一覧を取得しSelectBox用配列で返す
     * @param     amhsId    取得対象のAMHS ID
     * @return    [[OHB_ID][OHB_ID]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     *  MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
     ******************************************************************************
     */
    //@formatter:on
     public List<String[]> getOhbIdByAmhsId(String amhsId) {

        // 検索条件の生成
        OhbExample example = new OhbExample();
//        example.createCriteria().andAmhsIdEqualTo(amhsId);
        example.setOrderByClause("OHB_ID asc");

        // OHB ID要素の取得
        List<Ohb> ohbList = ohbMapper.selectByExample(example);
        List<String[]> selBoxList = new ArrayList<String[]>();
        for (Ohb ohb : ohbList) {
            String[] data = new String[2];
            data[0] = ohb.getOhbId();
            data[1] = ohb.getOhbId();
            selBoxList.add(data);
        }

        return selBoxList;
    }
    /*public List<String[]> getOhbIdByAmhsId(String amhsId) {
    	
    	// 検索条件の生成
    	OhbExample example = new OhbExample();
    	example.createCriteria().andAmhsIdEqualTo(amhsId);
    	example.setOrderByClause("OHB_ID asc");
    	
    	// OHB ID要素の取得
    	List<Ohb> ohbList = ohbMapper.selectByExample(example);
    	List<String[]> selBoxList = new ArrayList<String[]>();
    	for (Ohb ohb : ohbList) {
    		String[] data = new String[2];
    		data[0] = ohb.getOhbId();
    		data[1] = ohb.getOhbId();
    		selBoxList.add(data);
    	}
    	
    	return selBoxList;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     OHBGrの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[OHB_ID][OHB_NAME(OHB_ID)]]
     * @retval
     * @attention
     * @note      OHB_IDの昇順ソートでOHBGr一覧を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getOHBGrSel() {
//
//        OhbExample ohbExample = new OhbExample();
//        ohbExample.createCriteria();                                                    // MACS4#0160 Del
////        ohbExample.createCriteria().andOhbTypeNotEqualTo(ComConst.OhbType.CODE_IFOHB);  // MACS4#0160 Add
//        ohbExample.setOrderByClause("OHB_ID ASC");
//
//        List<Ohb> ohbGrList = ohbMapper.selectByExample(ohbExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Ohb selItem : ohbGrList) {
//            String[] data = new String[2];
//            data[0] = selItem.getOhbId();
//            StringBuffer sb = new StringBuffer(selItem.getOhbName());
//            sb.append("(");
//            sb.append(selItem.getOhbId());
//            sb.append(")");
//            data[1] = sb.toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したAMHS_TYPEのAMHS_IDの一覧を取得しSelectBox用配列で返す
     * @param     amhsType    取得対象のAMHSタイプ
     * @return    [[AMHS_ID][AMHS_ID(AMHS_TYPE)][フィルター用の連番]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getAmhsByType(Short amhsType) {
//
//        List<AmhsModel> amhsList = amhsMapper.getAmhsByType(amhsType);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (AmhsModel amhs : amhsList) {
//            String[] data = new String[3];
//            data[0] = amhs.getAmhsId();
//            data[1] = amhs.getAmhsType();
//            data[2] = "0";
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     defaultTransGrpIdのセレクトボックス用の一覧を取得する
     * @param
     * @return    [[SERVICE_ID][SERVICE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getDefaultTransGrpId() {
//
//        List<String> defaultTransGrpIdList = trrMapper.selectByDefaultTransGrpId();
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (String selItem : defaultTransGrpIdList) {
//            String[] data = new String[2];
//            data[0] = selItem;
//            data[1] = selItem;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     amhsGrpIdのセレクトボックス用の一覧を取得する
     * @param
     * @return    [[SERVICE_ID][SERVICE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * 0.2         Step2_1リリース                                              CSC
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getAmhsGrpId() {
//
//        List<String> amhsGrpIdList = trrMapper.selectByAMHSGrpId();
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (String selItem : amhsGrpIdList) {
//            String[] data = new String[2];
//            data[0] = selItem;
//            data[1] = selItem;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ZONEテーブルを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[ZONE_ID][NAME(ZONE_ID)]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getZoneNameBox() {
//
//        ZoneExample zoneExample = new ZoneExample();
//        zoneExample.createCriteria();
//        zoneExample.setOrderByClause("ZONE_ID ASC");
//
//        List<Zone> zoneList = zoneMapper.selectByExample(zoneExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Zone zone : zoneList) {
//            String[] data = new String[2];
//            data[0] = zone.getZoneId();
//            data[1] = zone.getName() + "(" + zone.getZoneId() + ")";
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GUI_FUNC_GROUP.GROUP_IDを全件取得し、selectBox用配列で返す
     * @param
     * @return    [[GROUP_ID][GROUP_ID]]
     * @retval
     * @attention
     * @note      GROUP_IDのみを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getGroupIdSelBox() throws McsException {
//
//        GuiFuncGroupExample example = new GuiFuncGroupExample();
//        example.createCriteria();
//        example.setDistinct(true);
//        example.setOrderByClause("GUI_GROUP_ID asc");
//        List<GuiFuncGroup> groupIdList = guiFuncGroupMapper.selectByExample(example);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (GuiFuncGroup groupId : groupIdList) {
//            String[] data = new String[2];
//            data[0] = groupId.getGuiGroupId();
//            data[1] = groupId.getGuiGroupId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したキーのSTRING_MASTERから指定したコードより大きい一覧を取得しSelectBox用配列で返す
     * @param     key         キー
     * @param     removeCode  コード
     * @return    [[CODE][STRING]]
     * @retval
     * @attention
     * @note      Unknownを要素から除くときに使用
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getStrMstRemoveCode(String key, long removeCode) {
//
//        StringMasterExample stringMasterExample = new StringMasterExample();
//        stringMasterExample.createCriteria().andKeyEqualTo(key).andCodeGreaterThan(removeCode);
//        stringMasterExample.setOrderByClause("CODE ASC");
//
//        List<StringMaster> stringMaster = stringMasterMapper.selectByExample(stringMasterExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (StringMaster selItem : stringMaster) {
//            String[] data = new String[2];
//            data[0] = String.valueOf(selItem.getCode());
//            data[1] = selItem.getString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     UNITテーブルを全件取得しSelectBox用配列で返す(UNIT_IDのみ)
     * @param
     * @return    [[UNIT_ID][UNIT_NAME(UNIT_ID)]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getUnitBox() {
//
//        UnitExample unitExample = new UnitExample();
//        unitExample.createCriteria();
//        unitExample.setOrderByClause("UNIT_ID ASC");
//
//        List<Unit> unitList = unitMapper.selectByExample(unitExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Unit unit : unitList) {
//            String[] data = new String[2];
//            data[0] = unit.getUnitId();
//            data[1] = unit.getUnitName() + "(" + unit.getUnitId() + ")";
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     HOST_NAMEの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[HOST_NAME][HOST_NAME]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getHostNameOnlyList() {
//
//        List<String> hostNameList = hostMapper.selectHostNameList();
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (String hostName : hostNameList) {
//
//            String[] data = new String[] { hostName, hostName };
//
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     PROCESS.SERVICE_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[SERVICE_ID][SERVICE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getServiceIdBox() {
//
//        List<String> serviceIdList = processMapper.getServiceId();
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (String serviceId : serviceIdList) {
//            String[] data = new String[2];
//            data[0] = serviceId;
//            data[1] = serviceId;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     NODE.NODE_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[NODE_ID][NODE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getNodeIdBox() {
//
//        NodeExample nodeExample = new NodeExample();
//        nodeExample.createCriteria();
//        nodeExample.setOrderByClause("NODE_ID ASC");
//
//        List<Node> nodeList = nodeMapper.selectByExample(nodeExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Node node : nodeList) {
//            String[] data = new String[2];
//            data[0] = node.getNodeId().toString();
//            data[1] = node.getNodeId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     VIP.VIP_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[VIP_ID][VIP_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getVipIdBox() {
//
//        VipExample vipExample = new VipExample();
//        vipExample.createCriteria();
//        vipExample.setOrderByClause("VIP_ID ASC");
//
//        List<Vip> vipList = vipMapper.selectByExample(vipExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Vip vip : vipList) {
//            String[] data = new String[2];
//            data[0] = vip.getVipId();
//            data[1] = vip.getVipId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     PROCESS.SYSTEM_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[SYSTEM_ID][SYSTEM_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getSystemIdBox() {
//
//        ProcessExample processExample = new ProcessExample();
//        processExample.createCriteria();
//        processExample.setDistinct(true);
//        processExample.setOrderByClause("SYSTEM_ID ASC");
//
//        List<Process> processList = processMapper.selectByExample(processExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Process process : processList) {
//            String[] data = new String[2];
//            data[0] = process.getSystemId().toString();
//            data[1] = process.getSystemId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     SYSTEM_IDの一覧を取得しSelectBox用配列で返す
     * @param     serviceId    取得対象のSERVICE_ID
     * @return    [[SYSTEM_ID][SYSTEM_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getSystemIdBoxByServiceId(String serviceId) {
//
//        // 検索条件の生成
//        ProcessExample processExample = new ProcessExample();
//        processExample.createCriteria().andServiceIdEqualTo(serviceId);
//        processExample.setDistinct(true);
//        processExample.setOrderByClause("SYSTEM_ID ASC");
//
//        List<Process> processList = processMapper.selectByExample(processExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Process process : processList) {
//            String[] data = new String[2];
//            data[0] = process.getSystemId().toString();
//            data[1] = process.getSystemId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     OHB_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[OHB_ID][OHB_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
     /*@Transactional(readOnly = true)
    public List<String[]> getOhbIdForLocation() {

        // OHBの検索
        List<Ohb> ohbList = null;
//      ohbList = ohbMapper.selectOhbForLocation();         // MACS4#0160 Del
        ohbList = ohbMapper.selectOhbForLocation(false);    // MACS4#0160 Add

        List<String[]> selBoxList = new ArrayList<String[]>();
        for (Ohb ohb : ohbList) {

            String[] retRec = new String[2];

            String ohbId = ohb.getOhbId();
            retRec[0] = ohbId;
            retRec[1] = ohbId;

            selBoxList.add(retRec);
        }

        return selBoxList;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GROUP_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[GROUP_ID][GROUP_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getStockerGrpIdBox() {
//
//        // 検索条件の生成
//        GrpExample stockerGrpExample = new GrpExample();
////      HostGrpExample stockerGrpExample = new HostGrpExample();  // Hostポートグループ対応 - 制御側未対応のため、未使用
//        stockerGrpExample.createCriteria().andGroupTypeEqualTo(ComConst.GroupType.CODE_STK_GRP);
//        stockerGrpExample.setDistinct(true);
//        stockerGrpExample.setOrderByClause("GROUP_ID ASC");
//
//        List<Grp> stockerGrpList = grpMapper.selectByExample(stockerGrpExample);
////      List<HostGrp> stockerGrpList = hostGrpMapper.selectByExample(stockerGrpExample);  // Hostポートグループ対応 - 制御側未対応のため、未使用
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Grp stockerGrp : stockerGrpList) {
////      for (HostGrp stockerGrp : stockerGrpList) {  // Hostポートグループ対応 - 制御側未対応のため、未使用
//            String[] data = new String[2];
//            data[0] = stockerGrp.getGroupId().toString();
//            data[1] = stockerGrp.getGroupId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     PORT_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[PORT_ID][PORT_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getTransferTestPortIdBoxByController(String controller) {
//
//        // 検索条件の生成
////        List<String> eqpPortList = portMapper.selectTransferTestEqpPortList(controller);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        Amhs amhs = amhsMapper.selectByPrimaryKey(controller);
//
//        if (amhs.getAmhsType() == ComConst.AmhsType.CODE_STC || amhs.getAmhsType() == ComConst.AmhsType.CODE_TSTC
//                || amhs.getAmhsType() == ComConst.AmhsType.CODE_RSBC) {
//            String[] data = new String[2];
//            data[0] = ComConst.STR_SHELF;
//            data[1] = ComConst.STR_SHELF;
//            selBoxList.add(data);
//        }
//
////        for (String eqpPort : eqpPortList) {
////            String[] data = new String[2];
////            data[0] = eqpPort;
////            data[1] = eqpPort;
////            selBoxList.add(data);
////        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     PORT_IDの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[PORT_ID][PORT_ID]]
     * @retval
     * @attention
     * @note      OHB_IDを指定し、OHB_PORT_RLTテーブルからPORT_IDを取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getPortIdBoxByOhbId(String ohbId) {
//
//        /* Step4 2017_08_23 */
//        // 検索条件の生成
//        OhbPortRltExample ohbPortRltExample = new OhbPortRltExample();
//        ohbPortRltExample.createCriteria().andOhbIdEqualTo(ohbId);
//        ohbPortRltExample.setOrderByClause("PORT_ID ASC");
//
//        List<OhbPortRlt> ohbPortRltList = ohbPortRltMapper.selectByExample(ohbPortRltExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        String[] shelf = new String[2];
//        shelf[0] = ComConst.STR_SHELF;
//        shelf[1] = ComConst.STR_SHELF;
//        selBoxList.add(shelf);
//
//        for (OhbPortRlt ohbPortRlt : ohbPortRltList) {
//            String[] data = new String[2];
//            data[0] = ohbPortRlt.getPortId().toString();
//            data[1] = ohbPortRlt.getPortId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したポートタイプおよびAMHS IDに紐づくポートIDの一覧を取得する。
     * @param
     * @return    [[PORT_ID][PORT_ID]]
     * @retval
     * @attention
     * @note      PORT_TYPEおよびAMHS_IDを指定し、PORTテーブルからPORT_IDを取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0159  代替元設定変更対応                                     T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
////  public List<String[]> getPortIdByAmhsIdAndType(List<Short> portType, String amhsId) {                                       // MACS4#0159 Del
//    public List<String[]> getPortIdByAmhsIdAndPortTypeListAndIoMode(List<Short> portType, String amhsId, List<Short> ioMode) {  // MACS4#0159 Add
//
//        // 検索条件の生成
//        PortExample portExample = new PortExample();
////      portExample.createCriteria().andAmhsIdEqualTo(amhsId).andPortTypeIn(portType);                      // MACS4#0159 Del
////        portExample.createCriteria().andAmhsIdEqualTo(amhsId).andPortTypeIn(portType).andIoModeIn(ioMode);  // MACS4#0159 Add
//        portExample.setOrderByClause("PORT_ID ASC");
//
//        List<Port> portIdList = portMapper.selectByExample(portExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        // 検索結果を成形しリストに挿入する
//        for (Port port : portIdList) {
//            String[] data = new String[2];
//            data[0] = port.getPortId();
//            data[1] = port.getPortId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したコントローラに紐づくポートIDの一覧を取得する。
     * @param
     * @return    [[PORT_ID][PORT_ID]]
     * @retval
     * @attention
     * @note      AMHS_IDを指定し、PORTテーブルからPORT_IDを取得する。
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getPortIdBoxByController(String amhsId) {
//
//        // 検索条件の生成
//        PortExample portExample = new PortExample();
////        portExample.createCriteria().andAmhsIdEqualTo(amhsId);
//        portExample.setOrderByClause("PORT_ID ASC");
//
//        List<Port> portIdList = portMapper.selectByExample(portExample);
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        // 検索結果を成形しリストに挿入する
//        for (Port port : portIdList) {
//            String[] data = new String[2];
//            data[0] = port.getPortId();
//            data[1] = port.getPortId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したキーのSTRING_MASTERから指定されたコードのみを取得
     * @param     key         キー
     * @param     getCode     取得コードリスト
     * @return    [[CODE][STRING]]
     * @retval
     * @attention
     * @note      特定のコードのみを取得する場合に使用(CODE値でソート[昇順])
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getStrMstGetCodeList(String key, List<Long> getCode) {
//
//        StringMasterExample stringMasterExample = new StringMasterExample();
//        stringMasterExample.createCriteria().andKeyEqualTo(key).andCodeIn(getCode);
//        stringMasterExample.setOrderByClause("CODE ASC");
//
//        List<StringMaster> stringMaster = stringMasterMapper.selectByExample(stringMasterExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//        for (StringMaster selItem : stringMaster) {
//            String[] data = new String[2];
//            data[0] = String.valueOf(selItem.getCode());
//            data[1] = selItem.getString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     装置ポートを持つコントローラのみを取得
     * @param
     * @return    [[AMHS_ID][AMHS_ID]]
     * @retval
     * @attention
     * @note      装置ポートを持つコントローラのみを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getStcEqpList() {
//
//        List<String[]> retList = new ArrayList<String[]>();
//
//        List<Short> amhsTypeList = new ArrayList<Short>();
//        amhsTypeList.add(ComConst.AmhsType.CODE_STC);
//        amhsTypeList.add(ComConst.AmhsType.CODE_TSTC);
//        amhsTypeList.add(ComConst.AmhsType.CODE_RSBC);
//
//        AmhsExample amhsExample = new AmhsExample();
//        amhsExample.createCriteria().andAmhsTypeIn(amhsTypeList);
//        amhsExample.setOrderByClause("AMHS_ID asc");
//
//        List<Amhs> amhsList = amhsMapper.selectByExample(amhsExample);
//
//        for (Amhs amhs : amhsList) {
//            String[] data = new String[2];
//            data[0] = amhs.getAmhsId();
//            data[1] = amhs.getAmhsId();
//            retList.add(data);
//        }
//
////        List<String> eqpCtrlList = portMapper.selectTransferTestEqpCtrlList();
//
////        for (String record : eqpCtrlList) {
////            String[] data = new String[2];
////            data[0] = record;
////            data[1] = record;
////            retList.add(data);
////        }
//
//        return retList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ポートタイプリスト(ポート構成画面用)を取得
     * @param
     * @return    [PortType(Code)][PortType(String)][AMHSType]
     * @retval
     * @attention
     * @note      ポート構成画面で使用するポートリストを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getPortTypeList() {
//
//        List<String[]> retList = new ArrayList<String[]>();
//
//        List<String[]> stcPortType = new ArrayList<String[]>();
//        List<String[]> ohvcPortType = new ArrayList<String[]>();
//        List<String[]> lfcPortType = new ArrayList<String[]>();
//        List<String[]> dwccPortType = new ArrayList<String[]>();
//        List<String[]> ohbcPortType = new ArrayList<String[]>();
//        List<String[]> tstcPortType = new ArrayList<String[]>();
//        List<String[]> rsbcPortType = new ArrayList<String[]>();
//        List<String[]> vlfcPortType = new ArrayList<String[]>();
//        List<String[]> opcPortType = new ArrayList<String[]>();
//        List<String[]> vohbcPortType = new ArrayList<String[]>();
//        List<String[]> virtualPortType = new ArrayList<String[]>();
//
//        // STC - EQP, PGV, LINK, DCT
//        short[] stcPortTypeList = { ComConst.PortType.CODE_EQP, ComConst.PortType.CODE_PGV, ComConst.PortType.CODE_LINK,
//                ComConst.PortType.CODE_DCT };
//
//        // OHVC - EQP
//        short[] ohvcPortTypeList = { ComConst.PortType.CODE_EQP };
//
//        // LFC - PGV, LINK, DCT
//        short[] lfcPortTypeList = { ComConst.PortType.CODE_PGV, ComConst.PortType.CODE_LINK,
//                ComConst.PortType.CODE_DCT };
//
//        // DWCC - PGV, LINK
//        short[] dwccPortTypeList = { ComConst.PortType.CODE_PGV, ComConst.PortType.CODE_LINK };
//
//        // OHBC - OHB
//        short[] ohbcPortTypeList = { ComConst.PortType.CODE_OHB };
//
//        // TSTC - EQP, LINK
//        short[] tstcPortTypeList = { ComConst.PortType.CODE_EQP, ComConst.PortType.CODE_LINK };
//
//        // RSBC - EQP, LINK, TB
//        short[] rsbcPortTypeList = { ComConst.PortType.CODE_EQP, ComConst.PortType.CODE_LINK,
//                ComConst.PortType.CODE_TOOL_BUFFER };
//
//        // VecLFC - LINK
//        short[] vlfcPortTypeList = { ComConst.PortType.CODE_LINK };
//
//        // OPC - OPC
//        short[] opcPortTypeList = { ComConst.PortType.CODE_OPE };
//
//        // VOHBC - OHB
//        short[] vohbcPortTypeList = { ComConst.PortType.CODE_OHB };
//
//        // Virtual - Virtual
//        short[] virtualPortTypeList = { ComConst.PortType.CODE_VIRTUAL };
//
//        StringMasterExample example = new StringMasterExample();
//        example.createCriteria().andKeyEqualTo(ComConst.StringMaster.KEY_PORT_TYPE);
//        example.setOrderByClause("CODE asc");
//
//        List<StringMaster> strMasterList = stringMasterMapper.selectByExample(example);
//
//        for (StringMaster record : strMasterList) {
//
//            // STC
//            for (short stc : stcPortTypeList) {
//                if (record.getCode() == stc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_STC).toString();
//
//                    stcPortType.add(data);
//                    break;
//                }
//            }
//
//            // OHVC
//            for (short ohvc : ohvcPortTypeList) {
//                if (record.getCode() == ohvc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_OHVC).toString();
//
//                    ohvcPortType.add(data);
//                    break;
//                }
//            }
//
//            // LFC
//            for (short lfc : lfcPortTypeList) {
//                if (record.getCode() == lfc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_LFC).toString();
//
//                    lfcPortType.add(data);
//                    break;
//                }
//            }
//
//            // DWCC
//            for (short dwcc : dwccPortTypeList) {
//                if (record.getCode() == dwcc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_DWCC).toString();
//
//                    dwccPortType.add(data);
//                    break;
//                }
//            }
//
//            // OHBC
//            for (short ohbc : ohbcPortTypeList) {
//                if (record.getCode() == ohbc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_OHBC).toString();
//
//                    ohbcPortType.add(data);
//                    break;
//                }
//            }
//
//            // TSTC
//            for (short tstc : tstcPortTypeList) {
//                if (record.getCode() == tstc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_TSTC).toString();
//
//                    tstcPortType.add(data);
//                    break;
//                }
//            }
//
//            // RSBC
//            for (short rsbc : rsbcPortTypeList) {
//                if (record.getCode() == rsbc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_RSBC).toString();
//
//                    rsbcPortType.add(data);
//                    break;
//                }
//            }
//
//            // VLFC
//            for (short vlfc : vlfcPortTypeList) {
//                if (record.getCode() == vlfc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_VLFT).toString();
//
//                    vlfcPortType.add(data);
//                    break;
//                }
//            }
//
//            // OPC
//            for (short opc : opcPortTypeList) {
//                if (record.getCode() == opc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_OPC).toString();
//
//                    opcPortType.add(data);
//                }
//            }
//
//            // VOHBC
//            for (short vohbc : vohbcPortTypeList) {
//                if (record.getCode() == vohbc) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_VOHBC).toString();
//
//                    vohbcPortType.add(data);
//                }
//            }
//
//            // Virtual
//            for (short virtual : virtualPortTypeList) {
//                if (record.getCode() == virtual) {
//                    String[] data = new String[3];
//
//                    data[0] = record.getCode().toString();
//                    data[1] = record.getString();
//                    data[2] = new Short(ComConst.AmhsType.CODE_VIRTUAL).toString();
//
//                    virtualPortType.add(data);
//                }
//            }
//        }
//
//        retList.addAll(stcPortType);
//        retList.addAll(ohvcPortType);
//        retList.addAll(lfcPortType);
//        retList.addAll(dwccPortType);
//        retList.addAll(ohbcPortType);
//        retList.addAll(tstcPortType);
//        retList.addAll(rsbcPortType);
//        retList.addAll(vlfcPortType);
//        retList.addAll(opcPortType);
//        retList.addAll(vohbcPortType);
//        retList.addAll(virtualPortType);
//
//        return retList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     接続ポートリストを取得
     * @param
     * @return    [PORT_ID][PORT_ID]
     * @retval
     * @attention
     * @note      接続ポートリストを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getSamePortIdList() {
//
//        List<String[]> retList = new ArrayList<String[]>();
//
//        // OHVCかつPortType:EQPのポートを取得
////        List<String> samePortIdList = portMapper.selectSamePortList();
//
//        String[] blank = new String[2];
//        blank[0] = ComConst.StringSelectboxAll.VALUE;
//        blank[1] = ComConst.StringSelectboxAll.TEXT;
//
//        retList.add(blank);
//
////        for (String samePort : samePortIdList) {
////            String[] data = new String[2];
////
////            data[0] = samePort;
////            data[1] = samePort;
////
////            retList.add(data);
////        }
//
//        return retList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     接続ポートリスト(修正時)を取得
     * @param
     * @return    [PORT_ID][PORT_ID]
     * @retval
     * @attention
     * @note      接続ポートリスト(修正時)を取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getModSamePortIdList(String portId) {
//
//        List<String[]> retList = new ArrayList<String[]>();
//
//        // OHVCかつPortType:EQPのポートを取得
////        List<String> samePortIdList = portMapper.selectModSamePortList(portId);
//
//        String[] blank = new String[2];
//        blank[0] = ComConst.StringSelectboxAll.VALUE;
//        blank[1] = ComConst.StringSelectboxAll.TEXT;
//
//        retList.add(blank);
//
////        for (String samePort : samePortIdList) {
////            String[] data = new String[2];
////
////            data[0] = samePort;
////            data[1] = samePort;
////
////            retList.add(data);
////        }
//
//        return retList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     EMPTY_CARRIER_CONTROLLER.CONTROLLER_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[CONTROLLER_ID][CONTROLLER_ID]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getEmptyCarrierControllerControllIdBox() {
//
//        EmptyCarrierControllerExample emptyCarrierControllerExample = new EmptyCarrierControllerExample();
//        emptyCarrierControllerExample.createCriteria();
//        emptyCarrierControllerExample.setOrderByClause("CONTROLLER_ID ASC");
//
//        List<EmptyCarrierController> emptyCarrierControllerList = emptyCarrierControllerMapper
//                .selectByExample(emptyCarrierControllerExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (EmptyCarrierController emptyCarrierController : emptyCarrierControllerList) {
//            String[] data = new String[2];
//            data[0] = emptyCarrierController.getControllerId();
//            data[1] = emptyCarrierController.getControllerId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     EMPTY_CARRIER_CONTAMI.CONTAMI_IDを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[CONTAMI_ID][CONTAMI_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getEmptyCarrierContamiContamiIdBox() {
//
//        EmptyCarrierContamiExample emptyCarrierContamiExample = new EmptyCarrierContamiExample();
//        emptyCarrierContamiExample.createCriteria();
//        emptyCarrierContamiExample.setOrderByClause("CONTAMI_ID ASC");
//
//        List<EmptyCarrierContami> emptyCarrierContamiList = emptyCarrierContamiMapper
//                .selectByExample(emptyCarrierContamiExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (EmptyCarrierContami emptyCarrierContami : emptyCarrierContamiList) {
//            String[] data = new String[2];
//            data[0] = emptyCarrierContami.getContamiId();
//            data[1] = emptyCarrierContami.getContamiId();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     PURPOSE_TYPE.PURPOSE_TYPEを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[PURPOSE_TYPE][PURPOSE_TYPE]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0099  iFoup設定画面変更                                      T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    // MACS4#0099 Del Start
//  @Transactional(readOnly = true)
//  public List<String[]> getPurposeTypePurposeTypeBox() {
//
//      PurposeTypeExample purposeTypeExample = new PurposeTypeExample();
//      purposeTypeExample.createCriteria();
//      purposeTypeExample.setOrderByClause("PURPOSE_TYPE ASC");
//
//      List<PurposeType> purposeTypeList = purposeTypeMapper.selectByExample(purposeTypeExample);
//      List<String[]> selBoxList = new ArrayList<String[]>();
//
//      for (PurposeType purposeType : purposeTypeList) {
//          String[] data = new String[2];
//          data[0] = purposeType.getPurposeType();
//          data[1] = purposeType.getPurposeType();
//          selBoxList.add(data);
//      }
//
//      return selBoxList;
//  }
    // MACS4#0099 Del Start

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     MODULE_IDの一覧を取得しSelectBox用配列で返す
     * @param     amhsId    取得対象のAMHS_ID
     * @return    [[MODULE_ID][MODULE_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getModuleIdBoxByAmhsId(String amhsId) {
//
//        // 検索条件の生成
//        ModuleExample moduleExample = new ModuleExample();
//        moduleExample.createCriteria().andAmhsIdEqualTo(amhsId);
//        moduleExample.setDistinct(true);
//        moduleExample.setOrderByClause("MODULE_ID ASC");
//
//        List<Module> moduleList = moduleMapper.selectByExample(moduleExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (Module module : moduleList) {
//            String[] data = new String[2];
//            data[0] = module.getModuleId().toString();
//            data[1] = module.getModuleId().toString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     代替元コントローラタイプの一覧を取得しSelectBox用配列で返す
     * @param
     * @return    [[Type][Name]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0016  GUI客先テストNG項目対応                                      CSC
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getAltSrcTypeBox() {
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        // タイプリスト取得
//        String[][] srcTypeList = {
//          // タイプコード取得
//            { ComConst.AmhsType.STR_CODE_STC, ComConst.AmhsType.STR_NAME_STC },
////          { ComConst.AmhsType.STR_CODE_TSTC, ComConst.AmhsType.STR_NAME_TSTC },  // MACS4#0016 Del
////          { ComConst.AmhsType.STR_CODE_RSBC,ComConst.AmhsType.STR_NAME_RSBC },   // MACS4#0016 Del
////          { ComConst.AmhsType.STR_CODE_LFC,  ComConst.AmhsType.STR_NAME_LFC },   // MACS4#0016 Del
////          { ComConst.AmhsType.STR_CODE_DWCC,  ComConst.AmhsType.STR_NAME_DWCC }, // MACS4#0016 Del
//            { ComConst.AmhsType.STR_CODE_OPC,ComConst.AmhsType.STR_NAME_OPC },
//            { ComConst.AmhsType.STR_CODE_OHB_GROUP, ComConst.AmhsType.STR_NAME_OHB_GROUP },
//            { ComConst.AmhsType.STR_CODE_MACHINE,  ComConst.AmhsType.STR_NAME_MACHINE }
//        };
//        // リスト編集
//        for (int i = 0; i < srcTypeList.length; i++) {
//            String[] data = new String[2];
//            data[0] = srcTypeList[i][0];
//            data[1] = srcTypeList[i][1];
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     装置コントローラIDリストを取得
     * @param
     * @return    [ALIAS][ALIAS]
     * @retval
     * @attention
     * @note      HOST_ALIASテーブル、AMHSテーブル、PORTテーブルから装置コントローラIDリストを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getEqpControllerIdList() {
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        //  リストデータを取得
//        List<String> eqpControllerIdList = hostAliasMapper.selectIoEQControllerList();
//
//        for (String controllerId : eqpControllerIdList) {
//            String[] data = new String[2];
//
//            data[0] = controllerId;
//            data[1] = controllerId;
//
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     装置ポートIDリストを取得
     * @param
     * @return    [ALIAS][ALIAS]
     * @retval
     * @attention
     * @note      HOST_ALIASテーブル、PORTテーブルから装置ポートIDリストを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    public List<String[]> getEqpPortIdList(String key) {
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        //  リストデータを取得
//        List<String> eqpPortIdList = hostAliasMapper.selectIoEQPortList(key);
//
//        for (String portId : eqpPortIdList) {
//            String[] data = new String[2];
//
//            data[0] = portId;
//            data[1] = portId;
//
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     指定したAMHSタイプのAMHS_Idを取得し、そのAMHS_IDと一致するOHB_IDのSelectBox用配列を返す
     * @param     amhsType    取得対象のAMHSタイプ
     * @return    [[OHB_ID][OHB_ID]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#MACSV2  MACSV2→MACSV4対応                                  天津村研　董
     ******************************************************************************
     */
    //@formatter:on
    /*//20191219 dqy del
    @Transactional(readOnly = true)
    public List<String[]> getOhbIdBoxByAmhsType(Short amhsType) {

        // 指定したAMHSタイプのAMHSIdの一覧から一致するOHB_IDのリストを取得
        List<String> ohbIdList = ohbMapper.selectOhbIdByAmhsType(amhsType);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (String ohbId : ohbIdList) {
            String[] data = new String[2];
            data[0] = ohbId;
            data[1] = ohbId;
            selBoxList.add(data);
        }

        return selBoxList;
    }*/

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     DEFAULT_PROCESSテーブルからProcessTypeを全件取得しSelectBox用配列で返す
     * @param
     * @return    [[PROC_TYPE][PROC_TYPE_STRING]]
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    // プロセスデフォルト対応 - 制御側未対応のため、未使用
//    public List<String[]> getDefaultProcessTypeBox() {
//
//        DefaultProcessExample defaultProcessExample = new DefaultProcessExample();
//        defaultProcessExample.createCriteria();
//        defaultProcessExample.setOrderByClause("PROC_TYPE ASC");
//
//        List<DefaultProcess> defaultProcessesList = defaultProcessMapper
//                .selectByExample(defaultProcessExample);
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (DefaultProcess defaultProcesses : defaultProcessesList) {
//            String[] data = new String[2];
//            data[0] = defaultProcesses.getProcType().toString();
//            data[1] = defaultProcesses.getProcTypeString();
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     ROUTE.PATTERN_IDを全件取得し、selectBox用配列で返す
     * @param
     * @return    [[PATTERN_ID][PATTERN_ID]]
     * @retval
     * @attention
     * @note      PATTERN_IDのみを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getRoutePatternIdSelBox() throws McsException {
//
//        List<String> patternIdList = routeMapper.getRoutePatternId();
//
//        List<String[]> selBoxList = new ArrayList<String[]>();
//
//        for (String patternId : patternIdList) {
//            String[] data = new String[2];
//            data[0] = patternId;
//            data[1] = patternId;
//            selBoxList.add(data);
//        }
//
//        return selBoxList;
//    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     AMHS.AMHS_ID（STCのみ）、OHB.OHB_ID（OHB_TYPE!=IFOHB）を取得し、selectBox用配列で返す
     * @param
     * @return    [[CONTROLLER_ID][CONTROLLER_ID]]
     * @retval
     * @attention
     * @note      CONTROLLER_IDのみを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0160  IFOHB表示非表示対応                                    T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
//    @Transactional(readOnly = true)
//    public List<String[]> getAmhsAndOhbControllerIdBox() throws McsException {
//
//        // AMHSテーブルからストッカ(AMHS_TYPE = 1)のみリスト取得
//        List<AmhsModel> amhsList = amhsMapper.getAmhsByType(ComConst.AmhsType.CODE_STC);
//        List<String[]> amhsSelBoxList = new ArrayList<String[]>();
//
//        for (AmhsModel amhs : amhsList) {
//            String[] data = new String[2];
//            data[0] = amhs.getAmhsId();
//            data[1] = amhs.getAmhsId();
//            amhsSelBoxList.add(data);
//        }
//
//        // OHBテーブルからリスト取得
//        OhbExample ohbExample = new OhbExample();
////      ohbExample.createCriteria();        // MACS4#0160 Del
//        // MACS4#0160 Add Start
////        ohbExample.createCriteria().andOhbTypeNotEqualTo(ComConst.OhbType.CODE_IFOHB);//20191219 dqy del
//        ohbExample.setOrderByClause("OHB_ID ASC");
//        // MACS4#0160 Add End
//
//        List<Ohb> ohbList = ohbMapper.selectByExample(ohbExample);
//        List<String[]> OhbSelBoxList = new ArrayList<String[]>();
//
//        for (Ohb amhs : ohbList) {
//            String[] data = new String[2];
//            data[0] = amhs.getOhbId();
//            data[1] = amhs.getOhbId();
//            OhbSelBoxList.add(data);
//        }
//
//        // AMHS、OHBテーブルを結合して返却
//        List<String[]> supplySrcControllerId = new ArrayList<String[]>();
//        supplySrcControllerId.addAll(amhsSelBoxList);
//        supplySrcControllerId.addAll(OhbSelBoxList);
//
//        return supplySrcControllerId;
//    }
    
    @Transactional(readOnly = true)
    public List<String[]> getTscIdBox() {

        TscExample tscExample = new TscExample();
        tscExample.createCriteria().andTscIdGreaterThan(0);
        tscExample.setOrderByClause("TSC_ABBREVIATION ASC");

        List<Tsc> tscList = tscMapper.selectByExample(tscExample);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Tsc tsc : tscList) {
            String[] data = new String[2];
            data[0] = String.valueOf(tsc.getTscId());
            data[1] = tsc.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    public List<String[]> getCarrierStateBox() {

        List<String[]> selBoxList = new ArrayList<String[]>();
        String[] data0 = new String[2];
            data0[0] = "Installed/Moving";
            data0[1] = "Moving";
        selBoxList.add(data0);
        String[] data1 = new String[2];
            data1[0] = "Installed/Stored";
            data1[1] = "Stored";
        selBoxList.add(data1);
        String[] data2 = new String[2];
            data2[0] = "Installed/StoredAlt";
            data2[1] = "StoredAlt";
        selBoxList.add(data2);   

        return selBoxList;
    }
    
    @Transactional(readOnly = true)
    public List<String[]> getOhbIdBox() {

        OhbExample ohbExample = new OhbExample();
        ohbExample.setOrderByClause("ohb_id ASC");

        List<Ohb> ohbList = ohbMapper.selectByExample(ohbExample);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Ohb ohb : ohbList) {
            String[] data = new String[2];
            data[0] = ohb.getOhbId();
            data[1] = ohb.getOhbId();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    @Transactional(readOnly = true)
    public List<String[]> getPortIdBox() {

        PortExample portExample = new PortExample();
        portExample.createCriteria().andPortTypeEqualTo((short) -10);
        portExample.setOrderByClause("port_id ASC");

        List<Port> portList = portMapper.selectByExample(portExample);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Port port : portList) {
            String[] data = new String[2];
            data[0] = port.getPortId();
            data[1] = port.getPortId();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    @Transactional(readOnly = true)
    public List<String[]> getStkIdBox() {

        TscExample tscExample = new TscExample();
        tscExample.createCriteria().andTscTypeIn(new ArrayList<String>(Arrays.asList("CDC", "STC", "OCDC", "XCDC")));
        tscExample.setOrderByClause("TSC_ABBREVIATION ASC");

        List<Tsc> tscList = tscMapper.selectByExample(tscExample);

        List<String[]> selBoxList = new ArrayList<String[]>();

        for (Tsc tsc : tscList) {
            String[] data = new String[2];
            data[0] = String.valueOf(tsc.getTscId());
            data[1] = tsc.getTscAbbreviation();
            
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
    

}
