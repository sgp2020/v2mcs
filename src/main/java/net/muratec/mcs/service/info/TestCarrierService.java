//@formatter:off
/**
 ******************************************************************************
 * @file        TestCarrierService.java
 * @brief       テストキャリア情報表示画面のサービス
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note       
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION                  AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  1                                        天津／張東江
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.info;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;
import net.muratec.mcs.mapper.TscMapper;
import net.muratec.mcs.entity.info.TestCarrierListEntity;
import net.muratec.mcs.entity.info.ReqGetTestCarrierListEntity;
import net.muratec.mcs.mapper.TestCarrierMapper;
import net.muratec.mcs.model.TestCarrierModel;
import net.muratec.mcs.service.common.BaseService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     テストキャリア情報表示画面のサービスクラス
 * @par       機能:
 *              getCount(テストキャリア情報一覧の全レコード数を取得)
 *              getTestCarrierInfoList(テストキャリア情報一覧の取得)
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class TestCarrierService extends BaseService {

    // ------------------------------------
    // テストキャリア情報マッパー
    // ------------------------------------
    @Autowired private TestCarrierMapper TestCarrierMapper;
    @Autowired private TscMapper tscMapper;


    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCount(テストキャリア情報一覧の全レコード数を取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    一覧の全レコード数
     * @retval    int形式で返却
     * @attention
     * @note      検索条件に一致したテストキャリア情報一覧の全レコード数を取得する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public int getCount(ReqGetTestCarrierListEntity reqEntity) {

        return TestCarrierMapper.getCount(reqEntity);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTestCarrierList(テストキャリア情報一覧の取得)機能
     * @param     reqEntity      リクエスト(検索条件)
     * @return    テストキャリア情報一覧
     * @retval    テストキャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したテストキャリア情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional
    public List<TestCarrierListEntity> getTestCarrierList(ReqGetTestCarrierListEntity reqEntity) {

        List<TestCarrierModel> recList = null;

        recList = TestCarrierMapper.selectTestCarrierList(reqEntity);

        if (recList == null) {
            return null;
        }

        List<TestCarrierListEntity> retRecList = new ArrayList<TestCarrierListEntity>();
        int num = 1;
        for (TestCarrierModel TestCarrierModel : recList) {
            TestCarrierListEntity entity = new TestCarrierListEntity();
            entity.testCarrierId = TestCarrierModel.getTestCarrierId();
            //entity.rn = TestCarrierModel.getRn();
            entity.rn = num;
            if(TestCarrierModel.getCurrentTscId()==null){
            	entity.currentTscId = "";
            }else {
            	entity.currentTscId = TestCarrierModel.getCurrentTscId().toString();
            }
            if(TestCarrierModel.getCurrentZoneId()==null){
            	entity.currentZoneId = "";
            }else {
            	entity.currentZoneId = TestCarrierModel.getCurrentZoneId().toString();
            }
            if(TestCarrierModel.getCurrentLoc()==null){
            	entity.currentLoc = "";
            }else {
            	entity.currentLoc = TestCarrierModel.getCurrentLoc().toString();
            }
            if(TestCarrierModel.getAtomicTscId()==null){
            	entity.atomicTscId = "";
            }else {
            	entity.atomicTscId = TestCarrierModel.getAtomicTscId().toString();
            }
            if(TestCarrierModel.getAtomicSource()==null){
            	entity.atomicSource = "";
            }else {
            	entity.atomicSource = TestCarrierModel.getAtomicSource().toString();
            }            
            if(TestCarrierModel.getAtomicDestination()==null){
            	entity.atomicDestination = "";
            }else {
            	entity.atomicDestination = TestCarrierModel.getAtomicDestination().toString();
            }            
            if(TestCarrierModel.getTransferDestination()==null){
            	entity.transferDestination = "";
            }else {
            	entity.transferDestination = TestCarrierModel.getTransferDestination().toString();
            } 
            if(TestCarrierModel.getTestStartTime()==null){
            	entity.testStartTime = "";
            }else {
            	entity.testStartTime = TestCarrierModel.getTestStartTime().toString();
            } 
            if(TestCarrierModel.getTestEndTime()==null){
            	entity.testEndTime = "";
            }else {
            	entity.testEndTime = TestCarrierModel.getTestEndTime().toString();
            } 
            num++;
            retRecList.add(entity);
        }
        return retRecList;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTestCarrierStateBox
     * @param     reqEntity      リクエスト(検索条件)
     * @return    テストキャリア情報一覧
     * @retval    テストキャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したテストキャリア情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getTestCarrierStateBox() {
    	
        List<String[]> selBoxList = new ArrayList<String[]>();

        	
        String[] data1 = new String[2];
        data1[0] = "1";
        data1[1] = "Testing";
        
        String[] data2 = new String[2];
        data2[0] = "3";
        data2[1] = "Future";
        
        String[] data3 = new String[2];
        data3[0] = "2";
        data3[1] = "Past";
        
        selBoxList.add(data1);
        selBoxList.add(data2);
        selBoxList.add(data3);
 
        return selBoxList;
    }
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getCurrentTscIdBox
     * @param     reqEntity      リクエスト(検索条件)
     * @return    テストキャリア情報一覧
     * @retval    テストキャリア情報のLIST形式で返却
     * @attention
     * @note      検索条件に一致したテストキャリア情報一覧の取得を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @Transactional(readOnly = true)
    public List<String[]> getCurrentTscIdBox() {
    	
        TscExample tscExample = new TscExample();
        List<String[]> selBoxList = new ArrayList<String[]>();
        List<Tsc> tscList = tscMapper.selectDefaultTscID(tscExample);

        for (Tsc tsc : tscList) {
        	
            String[] data = new String[2];
            data[0] = tsc.getTscId().toString();
            data[1] = tsc.getTscAbbreviation();
            selBoxList.add(data);
        }

        return selBoxList;
    }
    
}
