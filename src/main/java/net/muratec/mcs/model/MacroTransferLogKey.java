package net.muratec.mcs.model;

public class MacroTransferLogKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MACRO_TRANSFER_LOG.TIME
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    private String time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MACRO_TRANSFER_LOG.STATUS
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    private Short status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MACRO_TRANSFER_LOG.ORG_COMMAND_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    private String orgCommandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MACRO_TRANSFER_LOG.ORG_CARRIER_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    private String orgCarrierId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MACRO_TRANSFER_LOG.TIME
     *
     * @return the value of MACRO_TRANSFER_LOG.TIME
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public String getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MACRO_TRANSFER_LOG.TIME
     *
     * @param time the value for MACRO_TRANSFER_LOG.TIME
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MACRO_TRANSFER_LOG.STATUS
     *
     * @return the value of MACRO_TRANSFER_LOG.STATUS
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public Short getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MACRO_TRANSFER_LOG.STATUS
     *
     * @param status the value for MACRO_TRANSFER_LOG.STATUS
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MACRO_TRANSFER_LOG.ORG_COMMAND_ID
     *
     * @return the value of MACRO_TRANSFER_LOG.ORG_COMMAND_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public String getOrgCommandId() {
        return orgCommandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MACRO_TRANSFER_LOG.ORG_COMMAND_ID
     *
     * @param orgCommandId the value for MACRO_TRANSFER_LOG.ORG_COMMAND_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public void setOrgCommandId(String orgCommandId) {
        this.orgCommandId = orgCommandId == null ? null : orgCommandId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MACRO_TRANSFER_LOG.ORG_CARRIER_ID
     *
     * @return the value of MACRO_TRANSFER_LOG.ORG_CARRIER_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public String getOrgCarrierId() {
        return orgCarrierId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MACRO_TRANSFER_LOG.ORG_CARRIER_ID
     *
     * @param orgCarrierId the value for MACRO_TRANSFER_LOG.ORG_CARRIER_ID
     *
     * @mbggenerated Thu Mar 26 09:25:19 CST 2020
     */
    public void setOrgCarrierId(String orgCarrierId) {
        this.orgCarrierId = orgCarrierId == null ? null : orgCarrierId.trim();
    }
}