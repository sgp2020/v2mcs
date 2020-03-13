﻿package net.muratec.mcs.model;

public class Module extends ModuleKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.MODULE_NAME
     * @mbg.generated
     */
    private String moduleName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.AVAILABLE
     * @mbg.generated
     */
    private Short available;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MODULE.DISPLAY_FLAG
     * @mbg.generated
     */
    private Short displayFlag;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.MODULE_NAME
     * @return  the value of MODULE.MODULE_NAME
     * @mbg.generated
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.MODULE_NAME
     * @param moduleName  the value for MODULE.MODULE_NAME
     * @mbg.generated
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.AVAILABLE
     * @return  the value of MODULE.AVAILABLE
     * @mbg.generated
     */
    public Short getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.AVAILABLE
     * @param available  the value for MODULE.AVAILABLE
     * @mbg.generated
     */
    public void setAvailable(Short available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MODULE.DISPLAY_FLAG
     * @return  the value of MODULE.DISPLAY_FLAG
     * @mbg.generated
     */
    public Short getDisplayFlag() {
        return displayFlag;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MODULE.DISPLAY_FLAG
     * @param displayFlag  the value for MODULE.DISPLAY_FLAG
     * @mbg.generated
     */
    public void setDisplayFlag(Short displayFlag) {
        this.displayFlag = displayFlag;
    }
}
