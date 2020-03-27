package net.muratec.mcs.mapper;

import java.util.List;

import net.muratec.mcs.entity.info.ReqGetOhbInfoListEntity;
import net.muratec.mcs.model.OhbModel;
import net.muratec.mcs.model.Piece;
import net.muratec.mcs.model.PieceExample;
import net.muratec.mcs.model.RouteList;

import org.apache.ibatis.annotations.Param;

public interface PieceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    long countByExample(PieceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int deleteByExample(PieceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer pieceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int insert(Piece record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int insertSelective(Piece record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    List<Piece> selectByExample(PieceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    Piece selectByPrimaryKey(Integer pieceId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Piece record, @Param("example") PieceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Piece record, @Param("example") PieceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Piece record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PIECE
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Piece record);
    
    // =============================
    // v2MCS Unique Function From Here
    // =============================
    //@formatter:off
    /**
     ******************************************************************************
     * @brief       Select Piece List
     * @param       reqEntity   (search criteria)
     * @return      Piece List
     * @retval      List of Piece Model Style
     * @attention
     * @note        Select Piece List For SelectBox.
     * ----------------------------------------------------------------------------
     * DATE       VER.        DESCRIPTION                                    AUTHOR
     * ----------------------------------------------------------------------------
     ******************************************************************************
     */
    //@formatter:on
    List<Piece> selectPieceListForSelectBox();
    
}