package net.muratec.mcs.model;

public class Host extends HostKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.HOST_NAME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String hostName;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.HOST_MODE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String hostMode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.COMM_STATE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String commState;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.CONN_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String connTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.MODE_SET_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String modeSetTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.MODE_SET_SID
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private Integer modeSetSid;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column HOST.MODE_SET_ORIGINATOR
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    private String modeSetOriginator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.HOST_NAME
     *
     * @return the value of HOST.HOST_NAME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.HOST_NAME
     *
     * @param hostName the value for HOST.HOST_NAME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setHostName(String hostName) {
        this.hostName = hostName == null ? null : hostName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.HOST_MODE
     *
     * @return the value of HOST.HOST_MODE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getHostMode() {
        return hostMode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.HOST_MODE
     *
     * @param hostMode the value for HOST.HOST_MODE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setHostMode(String hostMode) {
        this.hostMode = hostMode == null ? null : hostMode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.COMM_STATE
     *
     * @return the value of HOST.COMM_STATE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getCommState() {
        return commState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.COMM_STATE
     *
     * @param commState the value for HOST.COMM_STATE
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setCommState(String commState) {
        this.commState = commState == null ? null : commState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.CONN_TIME
     *
     * @return the value of HOST.CONN_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getConnTime() {
        return connTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.CONN_TIME
     *
     * @param connTime the value for HOST.CONN_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setConnTime(String connTime) {
        this.connTime = connTime == null ? null : connTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.MODE_SET_TIME
     *
     * @return the value of HOST.MODE_SET_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getModeSetTime() {
        return modeSetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.MODE_SET_TIME
     *
     * @param modeSetTime the value for HOST.MODE_SET_TIME
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setModeSetTime(String modeSetTime) {
        this.modeSetTime = modeSetTime == null ? null : modeSetTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.MODE_SET_SID
     *
     * @return the value of HOST.MODE_SET_SID
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public Integer getModeSetSid() {
        return modeSetSid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.MODE_SET_SID
     *
     * @param modeSetSid the value for HOST.MODE_SET_SID
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setModeSetSid(Integer modeSetSid) {
        this.modeSetSid = modeSetSid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column HOST.MODE_SET_ORIGINATOR
     *
     * @return the value of HOST.MODE_SET_ORIGINATOR
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public String getModeSetOriginator() {
        return modeSetOriginator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column HOST.MODE_SET_ORIGINATOR
     *
     * @param modeSetOriginator the value for HOST.MODE_SET_ORIGINATOR
     *
     * @mbggenerated Wed Mar 18 14:17:47 CST 2020
     */
    public void setModeSetOriginator(String modeSetOriginator) {
        this.modeSetOriginator = modeSetOriginator == null ? null : modeSetOriginator.trim();
    }
    
    // STD 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    private String passageTime;
    private Integer rum;

    public String getPassageTime() {
		return passageTime;
	}

	public void setPassageTime(String passageTime) {
		this.passageTime = passageTime;
	}

    public Integer getRum() {
		return rum;
	}

	public void setRum(Integer rum) {
		this.rum = rum;
	}
	// END 2020.03.18 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
}