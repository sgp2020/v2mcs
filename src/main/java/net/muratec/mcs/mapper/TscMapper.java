package net.muratec.mcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.muratec.mcs.model.Tsc;
import net.muratec.mcs.model.TscExample;
import net.muratec.mcs.model.EqpType;

public interface TscMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    long countByExample(TscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int deleteByExample(TscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer tscId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int insert(Tsc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int insertSelective(Tsc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    List<Tsc> selectByExample(TscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    Tsc selectByPrimaryKey(Integer tscId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Tsc record, @Param("example") TscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Tsc record, @Param("example") TscExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Tsc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TSC
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Tsc record);
    
    List<EqpType> selectSrcEqpTypeList();
    List<EqpType> selectDstEqpTypeList();
    
    List<EqpType> selectSrcControllerList(String con);
    List<EqpType> selectDstControllerList(String con);
    
    List<EqpType> selectSrcPortList(String con);
    List<EqpType> selectDstPortList(String con);
    
    Integer getExecSid(@Param("tscId")int tscId);
    Integer getAllExecSid();
    String  getProcState(@Param("sid")int sid);
    Integer getExecSidByCarrier(@Param("carrierId")String carrierId);
    Integer getExecSidByPort(@Param("portId")String portId);
    
}