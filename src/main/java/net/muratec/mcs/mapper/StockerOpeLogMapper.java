package net.muratec.mcs.mapper;

import java.util.List;

import net.muratec.mcs.model.StockerOpeLog;
import net.muratec.mcs.model.StockerOpeLogExample;
import net.muratec.mcs.model.StockerOpeLogKey;
import net.muratec.mcs.model.StockerOpeLogModel;
import net.muratec.mcs.entity.hist.ReqGetStockerStatisticsHistEntity;
import net.muratec.mcs.entity.hist.StockerStatisticsHistEntity;

import org.apache.ibatis.annotations.Param;
import java.sql.Timestamp;
public interface StockerOpeLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int countByExample(StockerOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int deleteByExample(StockerOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int deleteByPrimaryKey(StockerOpeLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int insert(StockerOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int insertSelective(StockerOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    List<StockerOpeLog> selectByExample(StockerOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    StockerOpeLog selectByPrimaryKey(StockerOpeLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int updateByExampleSelective(@Param("record") StockerOpeLog record, @Param("example") StockerOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int updateByExample(@Param("record") StockerOpeLog record, @Param("example") StockerOpeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int updateByPrimaryKeySelective(StockerOpeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_OPE_LOG
     *
     * @mbggenerated Thu Apr 02 15:23:26 CST 2020
     */
    int updateByPrimaryKey(StockerOpeLog record);
   // =============================
    // v2MCS Unique Function From Here
    // =============================
    //@formatter:on
    List<StockerOpeLogModel> selectStockerStatisticsHistListByDay(ReqGetStockerStatisticsHistEntity reqEntity);
    //@formatter:on
    List<StockerOpeLogModel> selectStockerStatisticsHistListByHour(ReqGetStockerStatisticsHistEntity reqEntity);
    
    //@formatter:on
    String getDownTime(ReqGetStockerStatisticsHistEntity reqEntity);
    int getCountByDay(ReqGetStockerStatisticsHistEntity reqEntity);
    int getCountByHour(ReqGetStockerStatisticsHistEntity reqEntity);
}