package net.muratec.mcs.model;

public class Piece {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.PIECE_ID
     *
     * @mbg.generated
     */
    private Integer pieceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.CONN_TSC_ID
     *
     * @mbg.generated
     */
    private Integer connTscId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.PIECE_NAME
     *
     * @mbg.generated
     */
    private String pieceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.PIECE_ABBREVIATION
     *
     * @mbg.generated
     */
    private String pieceAbbreviation;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.VENDOR_PIECE_NAME
     *
     * @mbg.generated
     */
    private String vendorPieceName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.PIECE_MODE
     *
     * @mbg.generated
     */
    private String pieceMode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.AVAILABLE
     *
     * @mbg.generated
     */
    private String available;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.E10_STATE
     *
     * @mbg.generated
     */
    private String e10State;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.STATE_SET_TIME
     *
     * @mbg.generated
     */
    private String stateSetTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.MODE_SET_TIME
     *
     * @mbg.generated
     */
    private String modeSetTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.MODE_SET_SID
     *
     * @mbg.generated
     */
    private Integer modeSetSid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.MODE_SET_ORIGINATOR
     *
     * @mbg.generated
     */
    private String modeSetOriginator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PIECE.DISPLAY_FLG
     *
     * @mbg.generated
     */
    private Short displayFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.PIECE_ID
     *
     * @return the value of PIECE.PIECE_ID
     *
     * @mbg.generated
     */
    public Integer getPieceId() {
        return pieceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.PIECE_ID
     *
     * @param pieceId the value for PIECE.PIECE_ID
     *
     * @mbg.generated
     */
    public void setPieceId(Integer pieceId) {
        this.pieceId = pieceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.CONN_TSC_ID
     *
     * @return the value of PIECE.CONN_TSC_ID
     *
     * @mbg.generated
     */
    public Integer getConnTscId() {
        return connTscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.CONN_TSC_ID
     *
     * @param connTscId the value for PIECE.CONN_TSC_ID
     *
     * @mbg.generated
     */
    public void setConnTscId(Integer connTscId) {
        this.connTscId = connTscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.PIECE_NAME
     *
     * @return the value of PIECE.PIECE_NAME
     *
     * @mbg.generated
     */
    public String getPieceName() {
        return pieceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.PIECE_NAME
     *
     * @param pieceName the value for PIECE.PIECE_NAME
     *
     * @mbg.generated
     */
    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.PIECE_ABBREVIATION
     *
     * @return the value of PIECE.PIECE_ABBREVIATION
     *
     * @mbg.generated
     */
    public String getPieceAbbreviation() {
        return pieceAbbreviation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.PIECE_ABBREVIATION
     *
     * @param pieceAbbreviation the value for PIECE.PIECE_ABBREVIATION
     *
     * @mbg.generated
     */
    public void setPieceAbbreviation(String pieceAbbreviation) {
        this.pieceAbbreviation = pieceAbbreviation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.VENDOR_PIECE_NAME
     *
     * @return the value of PIECE.VENDOR_PIECE_NAME
     *
     * @mbg.generated
     */
    public String getVendorPieceName() {
        return vendorPieceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.VENDOR_PIECE_NAME
     *
     * @param vendorPieceName the value for PIECE.VENDOR_PIECE_NAME
     *
     * @mbg.generated
     */
    public void setVendorPieceName(String vendorPieceName) {
        this.vendorPieceName = vendorPieceName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.PIECE_MODE
     *
     * @return the value of PIECE.PIECE_MODE
     *
     * @mbg.generated
     */
    public String getPieceMode() {
        return pieceMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.PIECE_MODE
     *
     * @param pieceMode the value for PIECE.PIECE_MODE
     *
     * @mbg.generated
     */
    public void setPieceMode(String pieceMode) {
        this.pieceMode = pieceMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.AVAILABLE
     *
     * @return the value of PIECE.AVAILABLE
     *
     * @mbg.generated
     */
    public String getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.AVAILABLE
     *
     * @param available the value for PIECE.AVAILABLE
     *
     * @mbg.generated
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.E10_STATE
     *
     * @return the value of PIECE.E10_STATE
     *
     * @mbg.generated
     */
    public String getE10State() {
        return e10State;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.E10_STATE
     *
     * @param e10State the value for PIECE.E10_STATE
     *
     * @mbg.generated
     */
    public void setE10State(String e10State) {
        this.e10State = e10State;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.STATE_SET_TIME
     *
     * @return the value of PIECE.STATE_SET_TIME
     *
     * @mbg.generated
     */
    public String getStateSetTime() {
        return stateSetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.STATE_SET_TIME
     *
     * @param stateSetTime the value for PIECE.STATE_SET_TIME
     *
     * @mbg.generated
     */
    public void setStateSetTime(String stateSetTime) {
        this.stateSetTime = stateSetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.MODE_SET_TIME
     *
     * @return the value of PIECE.MODE_SET_TIME
     *
     * @mbg.generated
     */
    public String getModeSetTime() {
        return modeSetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.MODE_SET_TIME
     *
     * @param modeSetTime the value for PIECE.MODE_SET_TIME
     *
     * @mbg.generated
     */
    public void setModeSetTime(String modeSetTime) {
        this.modeSetTime = modeSetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.MODE_SET_SID
     *
     * @return the value of PIECE.MODE_SET_SID
     *
     * @mbg.generated
     */
    public Integer getModeSetSid() {
        return modeSetSid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.MODE_SET_SID
     *
     * @param modeSetSid the value for PIECE.MODE_SET_SID
     *
     * @mbg.generated
     */
    public void setModeSetSid(Integer modeSetSid) {
        this.modeSetSid = modeSetSid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.MODE_SET_ORIGINATOR
     *
     * @return the value of PIECE.MODE_SET_ORIGINATOR
     *
     * @mbg.generated
     */
    public String getModeSetOriginator() {
        return modeSetOriginator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.MODE_SET_ORIGINATOR
     *
     * @param modeSetOriginator the value for PIECE.MODE_SET_ORIGINATOR
     *
     * @mbg.generated
     */
    public void setModeSetOriginator(String modeSetOriginator) {
        this.modeSetOriginator = modeSetOriginator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PIECE.DISPLAY_FLG
     *
     * @return the value of PIECE.DISPLAY_FLG
     *
     * @mbg.generated
     */
    public Short getDisplayFlg() {
        return displayFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PIECE.DISPLAY_FLG
     *
     * @param displayFlg the value for PIECE.DISPLAY_FLG
     *
     * @mbg.generated
     */
    public void setDisplayFlg(Short displayFlg) {
        this.displayFlg = displayFlg;
    }
}