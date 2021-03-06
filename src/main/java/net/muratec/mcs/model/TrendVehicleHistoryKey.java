package net.muratec.mcs.model;

import java.sql.Timestamp;

public class TrendVehicleHistoryKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TREND_VEHICLE_HISTORY.TIME
     * @mbg.generated
     */
    private Timestamp time;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TREND_VEHICLE_HISTORY.AMHS_ID
     * @mbg.generated
     */
    private String amhsId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TREND_VEHICLE_HISTORY.VEHICLE_TYPE
     * @mbg.generated
     */
    private Short vehicleType;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column TREND_VEHICLE_HISTORY.VEHICLE_STATE
     * @mbg.generated
     */
    private Short vehicleState;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TREND_VEHICLE_HISTORY.TIME
     * @return  the value of TREND_VEHICLE_HISTORY.TIME
     * @mbg.generated
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TREND_VEHICLE_HISTORY.TIME
     * @param time  the value for TREND_VEHICLE_HISTORY.TIME
     * @mbg.generated
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TREND_VEHICLE_HISTORY.AMHS_ID
     * @return  the value of TREND_VEHICLE_HISTORY.AMHS_ID
     * @mbg.generated
     */
    public String getAmhsId() {
        return amhsId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TREND_VEHICLE_HISTORY.AMHS_ID
     * @param amhsId  the value for TREND_VEHICLE_HISTORY.AMHS_ID
     * @mbg.generated
     */
    public void setAmhsId(String amhsId) {
        this.amhsId = amhsId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TREND_VEHICLE_HISTORY.VEHICLE_TYPE
     * @return  the value of TREND_VEHICLE_HISTORY.VEHICLE_TYPE
     * @mbg.generated
     */
    public Short getVehicleType() {
        return vehicleType;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TREND_VEHICLE_HISTORY.VEHICLE_TYPE
     * @param vehicleType  the value for TREND_VEHICLE_HISTORY.VEHICLE_TYPE
     * @mbg.generated
     */
    public void setVehicleType(Short vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column TREND_VEHICLE_HISTORY.VEHICLE_STATE
     * @return  the value of TREND_VEHICLE_HISTORY.VEHICLE_STATE
     * @mbg.generated
     */
    public Short getVehicleState() {
        return vehicleState;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column TREND_VEHICLE_HISTORY.VEHICLE_STATE
     * @param vehicleState  the value for TREND_VEHICLE_HISTORY.VEHICLE_STATE
     * @mbg.generated
     */
    public void setVehicleState(Short vehicleState) {
        this.vehicleState = vehicleState;
    }
}