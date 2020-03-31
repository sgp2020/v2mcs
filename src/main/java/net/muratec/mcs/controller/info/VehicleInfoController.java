//@formatter:off
/**
 ******************************************************************************
 * @file        VehicleInfoController.java
 * @brief       ビックル情報表示関連のコントローラ
 * @par
 * @author      天津／張東江
 * $Id:         $
 * @attention
 *
 * Copyright (c) 2020 MURATA MACHINERY,LTD. All rights reserved.
 *
 * @note        Tabstop=4
 * ----------------------------------------------------------------------------
 * DATE       VER.        DESCRIPTION               AUTHOR
 * ----------------------------------------------------------------------------
 * 2020/03/12  2                                     天津／張東江     
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.controller.info;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.muratec.mcs.annotation.OpLog;
import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.common.ComCsvItem;
import net.muratec.mcs.common.ComCsvOut;
import net.muratec.mcs.common.ComFunction;
import net.muratec.mcs.controller.common.BaseController;
import net.muratec.mcs.entity.info.ReqGetVehicleInfoListEntity;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.common.AutoReloadTimerManagerService;
import net.muratec.mcs.service.info.VehicleInfoService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     ビックル情報表示関連のコントローラクラス
 * @par       機能:
 *              VehicleInfoInfo（初期状態表示）
 *              getCsvFile（CSV出力）
 *              createCsvHeaderRecords（CSVヘッダー情報生成）
 *              createCsvTitleRecords（CSVTitle情報生成）
 *              createCsvItem（CSV項目オブジェクト生成）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Controller
public class VehicleInfoController extends BaseController {

	/** メッセージリソース */
    @Autowired private MessageSource messageSource;

    @Autowired private VehicleInfoService vehicleInfoService;

    private static final Logger logger = LoggerFactory.getLogger(VehicleInfoController.class);

    public static Logger getLogger() {

        return logger;
    }

    // 自動更新機能
    @Autowired private AutoReloadTimerManagerService autoReload;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     VehicleInfo（初期状態表示）機能
     * @param     session        セッション情報（Frameworkより付加）
     * @param     locale         ロケーション情報（Frameworkより付加）
     * @param     model          モデル情報（Frameworkより付加）
     * @return    遷移先URL(アラーム情報表示)
     * @retval    jspファイルのパスを返却
     * @attention
     * @note      初期表示を行う
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    @RequestMapping(value = "/vehicleInfo", method = RequestMethod.GET)
    @OpLog(screenInfo = ComConst.ScreenInfo.INFO_VEHICLE, logOperationType = ComConst.LogOperationType.GET, number = 1L)
    public String vehicleInfoList(HttpSession session, Locale locale, Model model) throws McsException {

        // ----------------------------------------------
        // アクセス権情報等
        // ----------------------------------------------
        super.setUserInfo(session, model, locale, ComConst.ScreenInfo.INFO_VEHICLE.getRefAuthFuncId());

        // ----------------------------------------------
        // 自動更新機能の有効化
        // ----------------------------------------------
        autoReload.setInterval(model);

        // セレクトボックスの全件要素作成
        String[] allTerms = new String[2];
        allTerms[0] = ComConst.StringSelectboxAll.VALUE;
        allTerms[1] = ComConst.StringSelectboxAll.TEXT;

        // ----------------------------------------------
        // セレクトボックスの初期値を追加
        // ----------------------------------------------
       
        List<String[]> currentTscIdBoxList = vehicleInfoService.getCurrentTscIdBox();
      
        currentTscIdBoxList.add(0, allTerms);
        String currentTscIdJson = super.objectToJson(currentTscIdBoxList);
        model.addAttribute("II_004_01_001", currentTscIdJson);

        // バージョン情報付与
        ComFunction.setVersion(model);

        return "info/VehicleInfo";
    }
}
