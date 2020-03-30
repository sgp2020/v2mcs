package net.muratec.mcs.mapper;

import java.util.List;
import net.muratec.mcs.model.MacroTransferLog;
import net.muratec.mcs.model.MacroTransferLogExample;
import net.muratec.mcs.model.MacroTransferLogKey;
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
}