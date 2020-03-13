//@formatter:off
/**
 ******************************************************************************
 * @file        AutoReloadTimerManagerService.java
 * @brief       自動更新機能のサービス
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
 ******************************************************************************
 */
//@formatter:on
package net.muratec.mcs.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import net.muratec.mcs.common.ComConst;
import net.muratec.mcs.exception.McsException;
import net.muratec.mcs.service.maint.SystemParameterService;

//@formatter:off
/**
 ******************************************************************************
 * @brief     自動更新機能のサービスクラス
 * @par       機能:
 *              setInterval（自動更新有効化処理）
 *              setIntervalByKey（自動更新有効化処理【キー指定】）
 * @attention
 * @note
 * ----------------------------------------------------------------------------
 * VER.        DESCRIPTION                                               AUTHOR
 * ----------------------------------------------------------------------------
 ******************************************************************************
 */
//@formatter:on
@Service
public class AutoReloadTimerManagerService {

    /** システムパラメータサービス */
    @Autowired private SystemParameterService systemParameterService;

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     自動更新有効化処理
     * @param     model           モデル
     * @return
     * @retval
     * @attention
     * @note      自動更新のインターバル時間をシステムパラメータから取得し、モデルに追加する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setInterval(Model model) throws McsException {

        Integer interval = systemParameterService.getInt(ComConst.SystemParameter.Category.GUI,
                ComConst.SystemParameter.Section.AUTO_RELOAD, ComConst.SystemParameter.Key.AUTO_RELOAD_INTERVAL);
        model.addAttribute("AutoReloadTimerManagerDelay", interval);
    }

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     自動更新有効化処理(キー指定)
     * @param     model           モデル
     * @param     key             キー
     * @return
     * @retval
     * @attention
     * @note      自動更新のインターバル時間をシステムパラメータからキー指定で取得し、モデルに追加する
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    public void setIntervalByKey(Model model, String key) throws McsException {

        Integer interval = systemParameterService.getInt(ComConst.SystemParameter.Category.GUI,
                ComConst.SystemParameter.Section.AUTO_RELOAD, key);
        model.addAttribute("AutoReloadTimerManagerDelay", interval);
    }

}
