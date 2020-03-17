package net.muratec.mcs.mapper;

import java.util.List;
import net.muratec.mcs.model.Stocker;
import net.muratec.mcs.model.StockerExample;
import org.apache.ibatis.annotations.Param;

public interface StockerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int countByExample(StockerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int deleteByExample(StockerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int deleteByPrimaryKey(Integer tscId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int insert(Stocker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int insertSelective(Stocker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    List<Stocker> selectByExample(StockerExample example);
    
    // STD APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
//    List<Stocker> selectStockerIdList();//20200313 DQY DEL
    List<Stocker> selectStockerIdList(StockerExample example);
    // END APL 2020.03.11 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    Stocker selectByPrimaryKey(Integer tscId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int updateByExampleSelective(@Param("record") Stocker record, @Param("example") StockerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int updateByExample(@Param("record") Stocker record, @Param("example") StockerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int updateByPrimaryKeySelective(Stocker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    int updateByPrimaryKey(Stocker record);
}