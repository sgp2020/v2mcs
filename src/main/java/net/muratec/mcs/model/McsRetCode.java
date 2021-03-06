﻿package net.muratec.mcs.model;

public class McsRetCode extends McsRetCodeKey {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MCS_RET_CODE.HOST_CODE
     * @mbg.generated
     */
    private String hostCode;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MCS_RET_CODE.MESSAGE_TEXT
     * @mbg.generated
     */
    private String messageText;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column MCS_RET_CODE.DESCRIPTION
     * @mbg.generated
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MCS_RET_CODE.HOST_CODE
     * @return  the value of MCS_RET_CODE.HOST_CODE
     * @mbg.generated
     */
    public String getHostCode() {
        return hostCode;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MCS_RET_CODE.HOST_CODE
     * @param hostCode  the value for MCS_RET_CODE.HOST_CODE
     * @mbg.generated
     */
    public void setHostCode(String hostCode) {
        this.hostCode = hostCode;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MCS_RET_CODE.MESSAGE_TEXT
     * @return  the value of MCS_RET_CODE.MESSAGE_TEXT
     * @mbg.generated
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MCS_RET_CODE.MESSAGE_TEXT
     * @param messageText  the value for MCS_RET_CODE.MESSAGE_TEXT
     * @mbg.generated
     */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column MCS_RET_CODE.DESCRIPTION
     * @return  the value of MCS_RET_CODE.DESCRIPTION
     * @mbg.generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column MCS_RET_CODE.DESCRIPTION
     * @param description  the value for MCS_RET_CODE.DESCRIPTION
     * @mbg.generated
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
