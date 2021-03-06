package net.muratec.mcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.muratec.mcs.model.Dual;
import net.muratec.mcs.model.DualExample;

public interface DualMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    long countByExample(DualExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    int deleteByExample(DualExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    int insert(Dual record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    int insertSelective(Dual record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    List<Dual> selectByExample(DualExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Dual record, @Param("example") DualExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DUAL
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Dual record, @Param("example") DualExample example);
    
    Integer getGuiId();
}