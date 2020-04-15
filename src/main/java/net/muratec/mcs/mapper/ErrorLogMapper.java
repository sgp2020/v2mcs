package net.muratec.mcs.mapper;

import java.util.List;
import net.muratec.mcs.model.ErrorLog;
import net.muratec.mcs.model.ErrorLogExample;
import net.muratec.mcs.model.ErrorLogKey;
import org.apache.ibatis.annotations.Param;

public interface ErrorLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int countByExample(ErrorLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int deleteByExample(ErrorLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int deleteByPrimaryKey(ErrorLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int insert(ErrorLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int insertSelective(ErrorLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    List<ErrorLog> selectByExample(ErrorLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    ErrorLog selectByPrimaryKey(ErrorLogKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int updateByExampleSelective(@Param("record") ErrorLog record, @Param("example") ErrorLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int updateByExample(@Param("record") ErrorLog record, @Param("example") ErrorLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int updateByPrimaryKeySelective(ErrorLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ERROR_LOG
     *
     * @mbggenerated Wed Apr 15 15:04:18 CST 2020
     */
    int updateByPrimaryKey(ErrorLog record);
}