package net.muratec.mcs.mapper;

import java.util.List;
import net.muratec.mcs.model.Port;
import net.muratec.mcs.model.PortExample;
import org.apache.ibatis.annotations.Param;

public interface PortMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    long countByExample(PortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int deleteByExample(PortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String portId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int insert(Port record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int insertSelective(Port record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    List<Port> selectByExample(PortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    Port selectByPrimaryKey(String portId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Port record, @Param("example") PortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Port record, @Param("example") PortExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Port record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PORT
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Port record);
}