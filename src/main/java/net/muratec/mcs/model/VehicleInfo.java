package net.muratec.mcs.model;

public class VehicleInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.VEHICLE_ID
     *
     * @mbg.generated
     */
    private String vehicleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.TSC_ID
     *
     * @mbg.generated
     */
    private Integer tscId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.VEHICLE_STATE
     *
     * @mbg.generated
     */
    private String vehicleState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.VEHICLE_TYPE
     *
     * @mbg.generated
     */
    private String vehicleType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.CURRENT_PORT_ID
     *
     * @mbg.generated
     */
    private String currentPortId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.REQ_TIME
     *
     * @mbg.generated
     */
    private String reqTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.ASSIGNED_TIME
     *
     * @mbg.generated
     */
    private String assignedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.ACQUIRE_START_TIME
     *
     * @mbg.generated
     */
    private String acquireStartTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.ACQUIRE_CMP_TIME
     *
     * @mbg.generated
     */
    private String acquireCmpTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.DEPOSIT_START_TIME
     *
     * @mbg.generated
     */
    private String depositStartTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.DEPOSIT_CMP_TIME
     *
     * @mbg.generated
     */
    private String depositCmpTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.UNASSIGNED_TIME
     *
     * @mbg.generated
     */
    private String unassignedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.TOTAL_OPE
     *
     * @mbg.generated
     */
    private Integer totalOpe;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.TOTAL_ERR
     *
     * @mbg.generated
     */
    private Integer totalErr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.INSTALLED_TIME
     *
     * @mbg.generated
     */
    private String installedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.REMOVED_TIME
     *
     * @mbg.generated
     */
    private String removedTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.VEHICLE_KIND
     *
     * @mbg.generated
     */
    private Short vehicleKind;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.RTM_TSC_ID
     *
     * @mbg.generated
     */
    private Integer rtmTscId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column VEHICLE.CURRENT_BCU
     *
     * @mbg.generated
     */
    private String currentBcu;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.VEHICLE_ID
     *
     * @return the value of VEHICLE.VEHICLE_ID
     *
     * @mbg.generated
     */
    public String getVehicleInfoId() {
        return vehicleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.VEHICLE_ID
     *
     * @param vehicleId the value for VEHICLE.VEHICLE_ID
     *
     * @mbg.generated
     */
    public void setVehicleInfoId(String vehicleId) {
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.TSC_ID
     *
     * @return the value of VEHICLE.TSC_ID
     *
     * @mbg.generated
     */
    public Integer getTscId() {
        return tscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.TSC_ID
     *
     * @param tscId the value for VEHICLE.TSC_ID
     *
     * @mbg.generated
     */
    public void setTscId(Integer tscId) {
        this.tscId = tscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.VEHICLE_STATE
     *
     * @return the value of VEHICLE.VEHICLE_STATE
     *
     * @mbg.generated
     */
    public String getVehicleInfoState() {
        return vehicleState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.VEHICLE_STATE
     *
     * @param vehicleState the value for VEHICLE.VEHICLE_STATE
     *
     * @mbg.generated
     */
    public void setVehicleInfoState(String vehicleState) {
        this.vehicleState = vehicleState == null ? null : vehicleState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.VEHICLE_TYPE
     *
     * @return the value of VEHICLE.VEHICLE_TYPE
     *
     * @mbg.generated
     */
    public String getVehicleInfoType() {
        return vehicleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.VEHICLE_TYPE
     *
     * @param vehicleType the value for VEHICLE.VEHICLE_TYPE
     *
     * @mbg.generated
     */
    public void setVehicleInfoType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.CURRENT_PORT_ID
     *
     * @return the value of VEHICLE.CURRENT_PORT_ID
     *
     * @mbg.generated
     */
    public String getCurrentPortId() {
        return currentPortId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.CURRENT_PORT_ID
     *
     * @param currentPortId the value for VEHICLE.CURRENT_PORT_ID
     *
     * @mbg.generated
     */
    public void setCurrentPortId(String currentPortId) {
        this.currentPortId = currentPortId == null ? null : currentPortId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.REQ_TIME
     *
     * @return the value of VEHICLE.REQ_TIME
     *
     * @mbg.generated
     */
    public String getReqTime() {
        return reqTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.REQ_TIME
     *
     * @param reqTime the value for VEHICLE.REQ_TIME
     *
     * @mbg.generated
     */
    public void setReqTime(String reqTime) {
        this.reqTime = reqTime == null ? null : reqTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.ASSIGNED_TIME
     *
     * @return the value of VEHICLE.ASSIGNED_TIME
     *
     * @mbg.generated
     */
    public String getAssignedTime() {
        return assignedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.ASSIGNED_TIME
     *
     * @param assignedTime the value for VEHICLE.ASSIGNED_TIME
     *
     * @mbg.generated
     */
    public void setAssignedTime(String assignedTime) {
        this.assignedTime = assignedTime == null ? null : assignedTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.ACQUIRE_START_TIME
     *
     * @return the value of VEHICLE.ACQUIRE_START_TIME
     *
     * @mbg.generated
     */
    public String getAcquireStartTime() {
        return acquireStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.ACQUIRE_START_TIME
     *
     * @param acquireStartTime the value for VEHICLE.ACQUIRE_START_TIME
     *
     * @mbg.generated
     */
    public void setAcquireStartTime(String acquireStartTime) {
        this.acquireStartTime = acquireStartTime == null ? null : acquireStartTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.ACQUIRE_CMP_TIME
     *
     * @return the value of VEHICLE.ACQUIRE_CMP_TIME
     *
     * @mbg.generated
     */
    public String getAcquireCmpTime() {
        return acquireCmpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.ACQUIRE_CMP_TIME
     *
     * @param acquireCmpTime the value for VEHICLE.ACQUIRE_CMP_TIME
     *
     * @mbg.generated
     */
    public void setAcquireCmpTime(String acquireCmpTime) {
        this.acquireCmpTime = acquireCmpTime == null ? null : acquireCmpTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.DEPOSIT_START_TIME
     *
     * @return the value of VEHICLE.DEPOSIT_START_TIME
     *
     * @mbg.generated
     */
    public String getDepositStartTime() {
        return depositStartTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.DEPOSIT_START_TIME
     *
     * @param depositStartTime the value for VEHICLE.DEPOSIT_START_TIME
     *
     * @mbg.generated
     */
    public void setDepositStartTime(String depositStartTime) {
        this.depositStartTime = depositStartTime == null ? null : depositStartTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.DEPOSIT_CMP_TIME
     *
     * @return the value of VEHICLE.DEPOSIT_CMP_TIME
     *
     * @mbg.generated
     */
    public String getDepositCmpTime() {
        return depositCmpTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.DEPOSIT_CMP_TIME
     *
     * @param depositCmpTime the value for VEHICLE.DEPOSIT_CMP_TIME
     *
     * @mbg.generated
     */
    public void setDepositCmpTime(String depositCmpTime) {
        this.depositCmpTime = depositCmpTime == null ? null : depositCmpTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.UNASSIGNED_TIME
     *
     * @return the value of VEHICLE.UNASSIGNED_TIME
     *
     * @mbg.generated
     */
    public String getUnassignedTime() {
        return unassignedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.UNASSIGNED_TIME
     *
     * @param unassignedTime the value for VEHICLE.UNASSIGNED_TIME
     *
     * @mbg.generated
     */
    public void setUnassignedTime(String unassignedTime) {
        this.unassignedTime = unassignedTime == null ? null : unassignedTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.TOTAL_OPE
     *
     * @return the value of VEHICLE.TOTAL_OPE
     *
     * @mbg.generated
     */
    public Integer getTotalOpe() {
        return totalOpe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.TOTAL_OPE
     *
     * @param totalOpe the value for VEHICLE.TOTAL_OPE
     *
     * @mbg.generated
     */
    public void setTotalOpe(Integer totalOpe) {
        this.totalOpe = totalOpe;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.TOTAL_ERR
     *
     * @return the value of VEHICLE.TOTAL_ERR
     *
     * @mbg.generated
     */
    public Integer getTotalErr() {
        return totalErr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.TOTAL_ERR
     *
     * @param totalErr the value for VEHICLE.TOTAL_ERR
     *
     * @mbg.generated
     */
    public void setTotalErr(Integer totalErr) {
        this.totalErr = totalErr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.INSTALLED_TIME
     *
     * @return the value of VEHICLE.INSTALLED_TIME
     *
     * @mbg.generated
     */
    public String getInstalledTime() {
        return installedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.INSTALLED_TIME
     *
     * @param installedTime the value for VEHICLE.INSTALLED_TIME
     *
     * @mbg.generated
     */
    public void setInstalledTime(String installedTime) {
        this.installedTime = installedTime == null ? null : installedTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.REMOVED_TIME
     *
     * @return the value of VEHICLE.REMOVED_TIME
     *
     * @mbg.generated
     */
    public String getRemovedTime() {
        return removedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.REMOVED_TIME
     *
     * @param removedTime the value for VEHICLE.REMOVED_TIME
     *
     * @mbg.generated
     */
    public void setRemovedTime(String removedTime) {
        this.removedTime = removedTime == null ? null : removedTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.VEHICLE_KIND
     *
     * @return the value of VEHICLE.VEHICLE_KIND
     *
     * @mbg.generated
     */
    public Short getVehicleInfoKind() {
        return vehicleKind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.VEHICLE_KIND
     *
     * @param vehicleKind the value for VEHICLE.VEHICLE_KIND
     *
     * @mbg.generated
     */
    public void setVehicleInfoKind(Short vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.RTM_TSC_ID
     *
     * @return the value of VEHICLE.RTM_TSC_ID
     *
     * @mbg.generated
     */
    public Integer getRtmTscId() {
        return rtmTscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.RTM_TSC_ID
     *
     * @param rtmTscId the value for VEHICLE.RTM_TSC_ID
     *
     * @mbg.generated
     */
    public void setRtmTscId(Integer rtmTscId) {
        this.rtmTscId = rtmTscId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column VEHICLE.CURRENT_BCU
     *
     * @return the value of VEHICLE.CURRENT_BCU
     *
     * @mbg.generated
     */
    public String getCurrentBcu() {
        return currentBcu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column VEHICLE.CURRENT_BCU
     *
     * @param currentBcu the value for VEHICLE.CURRENT_BCU
     *
     * @mbg.generated
     */
    public void setCurrentBcu(String currentBcu) {
        this.currentBcu = currentBcu == null ? null : currentBcu.trim();
    }
}