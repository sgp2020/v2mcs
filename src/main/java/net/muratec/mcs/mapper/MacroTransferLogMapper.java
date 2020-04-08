package net.muratec.mcs.mapper;

import java.util.List;

import net.muratec.mcs.entity.hist.AtomicActivityHistListEntity;
import net.muratec.mcs.entity.hist.ReqGetActivityHistoryEntity;
import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetMacroDataEntity;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.MacroTransferLogExample;
import net.muratec.mcs.model.MacroTransferLogKey;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.OhbPortRltModel;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.Tsc;

import org.apache.ibatis.annotations.Param;

public interface MacroTransferLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int countByExample(MacroTransferLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int deleteByExample(MacroTransferLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int deleteByPrimaryKey(MacroTransferLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int insert(MacroTransferLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int insertSelective(MacroTransferLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    List<MacroTransferLog> selectByExample(MacroTransferLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    MacroTransferLog selectByPrimaryKey(MacroTransferLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int updateByExampleSelective(@Param("record") MacroTransferLog record, @Param("example") MacroTransferLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int updateByExample(@Param("record") MacroTransferLog record, @Param("example") MacroTransferLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int updateByPrimaryKeySelective(MacroTransferLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACRO_TRANSFER_LOG
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    int updateByPrimaryKey(MacroTransferLog record);
    
    // =============================
    // v2MCS Unique Function From Here
    // =============================
    //@formatter:off
    /**
     ******************************************************************************
     * @brief       selectMacroDataList
     * @param       reqEntity   (search criteria)
     * @return      MacroDataList
     * @retval      List of MacroDataList Model Style
     * @attention
     * @note        Select MacroDataList that matches search criteria.
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
	// STD 2020.04.02 DONG  ADD 
    List<MacroTransferLog> selectMacroDataList(ReqGetMacroDataEntity reqEntity);
    int getCount(ReqGetMacroDataEntity record);
	// END 2020.04.02 DONG ADD 
    // STD 2020.04.07 SGP ADD
    List<Tsc> selectStkData();
    List<Ohb> selectOhbData();
    List<Port> selectPortData();
    List<MacroTransferLog> selectActivityHistoryList(ReqGetActivityHistoryEntity reqEntity);
    int getCountActivityHistory(ReqGetActivityHistoryEntity record);
    /**
     ******************************************************************************
     * @brief       Get AtomicTransferLog
     * @param
     * @return      AtomicTransferLog(List)
     * @retval      List
     * @attention
     * @note        Get AtomicTransferLog
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    List<AtomicActivityHistListEntity> selectAtomicTransferLogByCommandId(String commandId);
    // END 2020.04.07 SGP ADD
}