package net.muratec.mcs.model;

public class StockerZoneRltKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.TSC_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer tscId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.ZONE_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private String zoneId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.TSC_ID
     *
     * @return the value of STOCKER_ZONE_RLT.TSC_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getTscId() {
        return tscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.TSC_ID
     *
     * @param tscId the value for STOCKER_ZONE_RLT.TSC_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setTscId(Integer tscId) {
        this.tscId = tscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.ZONE_ID
     *
     * @return the value of STOCKER_ZONE_RLT.ZONE_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.ZONE_ID
     *
     * @param zoneId the value for STOCKER_ZONE_RLT.ZONE_ID
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId == null ? null : zoneId.trim();
    }
}