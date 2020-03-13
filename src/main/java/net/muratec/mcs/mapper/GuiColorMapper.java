﻿package net.muratec.mcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.muratec.mcs.entity.common.AjaxDataTablesReqBaseEntity;
import net.muratec.mcs.model.GuiColor;
import net.muratec.mcs.model.GuiColorExample;
import net.muratec.mcs.model.GuiColorKey;

public interface GuiColorMapper {

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    long countByExample(GuiColorExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int deleteByExample(GuiColorExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int deleteByPrimaryKey(GuiColorKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int insert(GuiColor record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int insertSelective(GuiColor record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    List<GuiColor> selectByExample(GuiColorExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    GuiColor selectByPrimaryKey(GuiColorKey key);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GuiColor record, @Param("example") GuiColorExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int updateByExample(@Param("record") GuiColor record, @Param("example") GuiColorExample example);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GuiColor record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table GUI_COLOR
     * @mbg.generated
     */
    int updateByPrimaryKey(GuiColor record);

    // =============================
    // MCS Unique Function From Here
    // =============================
    //@formatter:off
    /**
     ******************************************************************************
     * @brief     GuiColor rownum select
     * @param     reqEntity     (search criteria)
     * @return    GuiColor List
     * @retval
     * @attention
     * @note
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<GuiColor> selectGuiColorRowNum(AjaxDataTablesReqBaseEntity reqEntity);
}
