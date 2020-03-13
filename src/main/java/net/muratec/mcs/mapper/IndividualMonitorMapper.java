//@formatter:off
/**
 ******************************************************************************
 * @file        IndividualMonitorMapper.java
 * @brief       Individual monitor related mapper
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

package net.muratec.mcs.mapper;

import java.util.List;

import net.muratec.mcs.entity.top.ReqIndividualMonitorEntity;
import net.muratec.mcs.entity.top.ReqIndividualMonitorOhbEntity;
import net.muratec.mcs.model.IndividualMonitorStateInfo;
import net.muratec.mcs.model.IndividualMonitorTransferJob;
import net.muratec.mcs.entity.top.IndividualMonitorPortEntity;
import net.muratec.mcs.entity.top.IndividualMonitorVehicleEntity;
import net.muratec.mcs.entity.top.IndividualMonitorMicroCmdEntity;

public interface IndividualMonitorMapper {

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getScMonitorState
     * @param     reqEntity Request from client(search condition)
     * @return    State information
     * @retval    State information of SC Monitor
     * @attention
     * @note      Returns State information(for SC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    IndividualMonitorStateInfo getScMonitorState(ReqIndividualMonitorEntity reqEntity);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getConvAndTscMonitorState
     * @param     reqEntity Request from client(search condition)
     * @return    State information
     * @retval    State information of Conveyor Monitor and TSC Monitor
     * @attention
     * @note      Returns State information(for Conveyor Monitor and TSC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    IndividualMonitorStateInfo getConvAndTscMonitorState(ReqIndividualMonitorEntity reqEntity);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOhbMonitorState
     * @param     reqEntity Request from client(search condition)
     * @return    State information
     * @retval    State information of OHB Monitor
     * @attention
     * @note      Returns State information(for OHB Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    IndividualMonitorStateInfo getOhbMonitorState(ReqIndividualMonitorOhbEntity reqEntity);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getIndividualMonitorPortList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Port information
     * @retval    Retrun the list of Port information
     * @attention
     * @note      Returns a list of Port information(for SC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorPortEntity> getIndividualMonitorPortList(ReqIndividualMonitorEntity reqEntity);
    
    //20200120 Song add Start
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getIndividualMonitorPortPieceList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Port information
     * @retval    Retrun the list of Port information
     * @attention
     * @note      Returns a list of Piece Port information(for SC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorPortEntity> getIndividualMonitorPortPieceList(ReqIndividualMonitorEntity reqEntity);
    //20200120 Song add End

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getOhbMonitorPortList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Port information
     * @retval    Retrun the list of Port information
     * @attention
     * @note      Returns a list of Port information(for OHB Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorPortEntity> getOhbMonitorPortList(ReqIndividualMonitorOhbEntity reqEntity);
    
    //@formatter:off
    //20200103 DQY ADD FOR OHB PORT
    /**
     ******************************************************************************
     * @brief     getOhbMonitorOHBPortList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Port information
     * @retval    Retrun the list of Port information
     * @attention
     * @note      Returns a list of Port information(for OHB Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorPortEntity> getOhbMonitorOHBPortList(ReqIndividualMonitorOhbEntity reqEntity);

    
    //@formatter:off
    //20200121 Song Add FOR OHB PORT
    /**
     ******************************************************************************
     * @brief     getOhbMonitorOHBPortList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Port information
     * @retval    Retrun the list of Port information
     * @attention
     * @note      Returns a list of Port information(for OHB Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorPortEntity> getOhbMonitorOHBPortPieceList(ReqIndividualMonitorOhbEntity reqEntity);

    
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getIndividualMonitorMicroCmdList
     * @param     reqEntity Request from client(search condition)
     * @return    List of MicroCmd information
     * @retval    Retrun the list of MicroCmd information
     * @attention
     * @note      Returns a list of MicroCmd information
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorMicroCmdEntity> getIndividualMonitorMicroCmdList(ReqIndividualMonitorEntity reqEntity);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getScMonitorTransferJobList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Transfer Job
     * @retval    Retrun the list of Transfer Job
     * @attention
     * @note      Returns a list of Transfer Job(for SC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorTransferJob> getScMonitorTransferJobList(ReqIndividualMonitorEntity reqEntity);

    //@formatter:off
    /**
     ******************************************************************************
     * @brief     getTscMonitorVehicleList
     * @param     reqEntity Request from client(search condition)
     * @return    List of Vehicle
     * @retval    Retrun the list of Vehicle
     * @attention
     * @note      Returns a list of Vehicle(for TSC Monitor)
     * ----------------------------------------------------------------------------
     * VER.        DESCRIPTION                                               AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<IndividualMonitorVehicleEntity> getTscMonitorVehicleList(ReqIndividualMonitorEntity reqEntity);

}
