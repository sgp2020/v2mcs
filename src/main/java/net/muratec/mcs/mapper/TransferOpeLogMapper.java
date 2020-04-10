package net.muratec.mcs.mapper;

import java.util.List;

import net.muratec.mcs.entity.hist.ReqGetAtomicActivityHistEntity;
import net.muratec.mcs.entity.hist.ReqGetStatisticsHistoryJobEntity;
import net.muratec.mcs.model.AtomicTransferLog;
import net.muratec.mcs.model.Ohb;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.StockerZoneRlt;
import net.muratec.mcs.model.TransferOpeLog;
import net.muratec.mcs.model.TransferOpeLogExample;
import net.muratec.mcs.model.TransferOpeLogKey;
import net.muratec.mcs.model.Tsc;

import org.apache.ibatis.annotations.Param;

public interface TransferOpeLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int countByExample(TransferOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int deleteByExample(TransferOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int deleteByPrimaryKey(TransferOpeLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int insert(TransferOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int insertSelective(TransferOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    List<TransferOpeLog> selectByExample(TransferOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    TransferOpeLog selectByPrimaryKey(TransferOpeLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int updateByExampleSelective(@Param("record") TransferOpeLog record, @Param("example") TransferOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int updateByExample(@Param("record") TransferOpeLog record, @Param("example") TransferOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int updateByPrimaryKeySelective(TransferOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TRANSFER_OPE_LOG
     *
     * @mbggenerated Thu Apr 09 15:17:50 CST 2020
     */
    int updateByPrimaryKey(TransferOpeLog record);
	// STD 2020.04.09 DONG  ADD 
    List<AtomicTransferLog> selectAtomicActivityHistList(ReqGetAtomicActivityHistEntity reqEntity);
    List<AtomicTransferLog> selectAtomicActivityHistList();
    List<Tsc> selectTscNameList();
    List<StockerZoneRlt> selectZoneData();
    List<Ohb> selectOhbData();
    List<Port> selectPortData();
    int getCount(ReqGetStatisticsHistoryJobEntity record);
    // END 2020.04.09 DONG  ADD
}