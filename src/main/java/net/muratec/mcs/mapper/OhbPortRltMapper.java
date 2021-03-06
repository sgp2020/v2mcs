package net.muratec.mcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.muratec.mcs.model.OhbPortRlt;
import net.muratec.mcs.model.OhbPortRltExample;
import net.muratec.mcs.model.OhbPortRltModel;

public interface OhbPortRltMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    long countByExample(OhbPortRltExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    int deleteByExample(OhbPortRltExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    int insert(OhbPortRlt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    int insertSelective(OhbPortRlt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    List<OhbPortRlt> selectByExample(OhbPortRltExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") OhbPortRlt record, @Param("example") OhbPortRltExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table OHB_PORT_RLT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") OhbPortRlt record, @Param("example") OhbPortRltExample example);
    

    /**
     ******************************************************************************
     * @brief       Get OhbPortRlt
     * @param
     * @return      OhbPortRlt(List)
     * @retval      List
     * @attention
     * @note        Get OhbPortRlt
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    List<OhbPortRltModel> selectListByOhbId(String ohbId);
}