//@formatter:off
/**
 ******************************************************************************
 * @file        CarrierSynchronizeService.java
 * @brief       キャリア同期のサービス
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
 * 2017/01/31 0.2         Step2_1リリース                                   CSC
 * 2018/11/09 MACS4#0036  GUIオペレーション制御不具合対応             T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.maint;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.muratec.mcs.common.ComMsgDef;
import net.muratec.mcs.entity.maint.ReqExeCarrierSynchronizeEntity;
import net.muratec.mcs.entity.maint.ReqGetCarrierSynchronizeEntity;
import net.muratec.mcs.mapper.AmhsMapper;
import net.muratec.mcs.model.Amhs;
import net.muratec.mcs.model.AmhsExample;
import net.muratec.mcs.model.MsgData;
import net.muratec.mcs.model.MsgResult;
import net.muratec.mcs.service.common.BaseService;
import net.muratec.mcs.service.common.MsgUtilService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     キャリア同期のサービスクラス
 * @par       機能:
 *              getCarrierSynchronize（キャリア同期のコントローラのレコードを取得）
 *              exeCarrierSynchronize（外部の同期処理を実行）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
 ******************************************************************************
 */
//@formatter:on
@Service
public class CarrierSynchronizeService extends BaseService {

    @Autowired private AmhsMapper amhsMapper;

    @Autowired private MsgUtilService msgUtilService;

    @Autowired private Properties m_formatProperties;

//  private static final Short k_sActiveCarriers = (short) 51;      // MACS4#0036 Del

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     キャリア同期のコントローラのレコードを取得
     * @param     reqEntity    検索条件
     * @return    コントローラのレコード
     * @retval    List形式で返却
     * @attention
     * @note       キャリア同期のコントローラの検索レコードを取得
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<String[]> getCarrierSynchronize(ReqGetCarrierSynchronizeEntity reqEntity) {

        List<String[]> carrierSynchronizeListEntity = new ArrayList<String[]>();
        List<Amhs> amhsList = new ArrayList<Amhs>();
        AmhsExample amhsExample = new AmhsExample();

        // 検索
        amhsExample.createCriteria().andAmhsTypeEqualTo(reqEntity.controllerGroup);
        amhsExample.setOrderByClause("AMHS_ID ASC");
        amhsList = amhsMapper.selectByExample(amhsExample);

        for (Amhs amhs : amhsList) {
            String[] select = new String[2];
            select[0] = amhs.getAmhsId();
            select[1] = amhs.getAmhsName() + "(" + amhs.getAmhsId() + ")";
            carrierSynchronizeListEntity.add(select);
        }

        return carrierSynchronizeListEntity;
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     キャリア同期実行
     * @param     reqEntity        キャリア同期を実行する対象情報
     * @return    同期成功可否
     * @retval
     * @attention
     * @note       キャリア同期実行
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     * MACS4#0036  GUIオペレーション制御不具合対応                        T.Iga/CSC
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public boolean exeCarrierSynchronize(ReqExeCarrierSynchronizeEntity reqEntity) {

        // MACS4#0036 Mod Start
//      MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strStatusServer, ComMsgDef.k_strSubSend,
//              ComMsgDef.k_strCarrierSync);
        MsgData msgData = new MsgData(m_formatProperties, ComMsgDef.k_strDataSyncServer, ComMsgDef.k_strSubSend,
                ComMsgDef.k_strCarrierSync);
        // MACS4#0036 Mod End

        // ヘッダ情報設定
        msgData.setValByte(ComMsgDef.k_strKeyReply, ComMsgDef.k_btNoReply);
        msgData.setValByte(ComMsgDef.k_strKeySrcProcType, ComMsgDef.k_btSrcProcTypeGUI);

        // メッセージ本文設定
        msgData.setValString(ComMsgDef.k_strKeyAmhsID, reqEntity.getController());
//      msgData.setValShort(ComMsgDef.k_strKeySyncType, k_sActiveCarriers);     // MACS4#0036 Del

        // メッセージの送受信
        // MACS4#0036 Mod Start
//      MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strStatusServer,
//              ComMsgDef.k_strCarrierSync, true, ComMsgDef.k_strApplicationMessageItem);
        MsgResult msgResult = msgUtilService.communicationMsgSvr(msgData, ComMsgDef.k_strDataSyncServer,
                ComMsgDef.k_strCarrierSync, true, ComMsgDef.k_strApplicationMessageItem);
        // MACS4#0036 Mod End

        if (msgResult.getResult() != MsgResult.k_nResultOK) {
            return false;
        }

        return true;
    }
}
