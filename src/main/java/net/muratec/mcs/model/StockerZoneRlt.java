package net.muratec.mcs.model;

public class StockerZoneRlt extends StockerZoneRltKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.TOTAL_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer totalShelves;
    // STD APL 2020.03.12 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    private Integer num;
    private Integer lOccupancy;
    private Integer occupancy;
    private Integer usedCell;
    private String tscAbbreviation;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getlOccupancy() {
		return lOccupancy;
	}

	public void setlOccupancy(Integer lOccupancy) {
		this.lOccupancy = lOccupancy;
	}

	public Integer getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Integer occupancy) {
		this.occupancy = occupancy;
	}

	public Integer getUsedCell() {
		return usedCell;
	}

	public void setUsedCell(Integer usedCell) {
		this.usedCell = usedCell;
	}

    public String getTscAbbreviation() {
		return tscAbbreviation;
	}

	public void setTscAbbreviation(String tscAbbreviation) {
		this.tscAbbreviation = tscAbbreviation;
	}

	// END APL 2020.03.12 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer emptyShelves;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.L_EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer lEmptyShelves;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.HIGH_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer highWaterMark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.LOW_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Integer lowWaterMark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Short hwmHysteresisLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Short lwmHysteresisLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Short hwmHysteresisFlg;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    private Short lwmHysteresisFlg;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.TOTAL_SHELVES
     *
     * @return the value of STOCKER_ZONE_RLT.TOTAL_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getTotalShelves() {
        return totalShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.TOTAL_SHELVES
     *
     * @param totalShelves the value for STOCKER_ZONE_RLT.TOTAL_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setTotalShelves(Integer totalShelves) {
        this.totalShelves = totalShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.EMPTY_SHELVES
     *
     * @return the value of STOCKER_ZONE_RLT.EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getEmptyShelves() {
        return emptyShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.EMPTY_SHELVES
     *
     * @param emptyShelves the value for STOCKER_ZONE_RLT.EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setEmptyShelves(Integer emptyShelves) {
        this.emptyShelves = emptyShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.L_EMPTY_SHELVES
     *
     * @return the value of STOCKER_ZONE_RLT.L_EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getlEmptyShelves() {
        return lEmptyShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.L_EMPTY_SHELVES
     *
     * @param lEmptyShelves the value for STOCKER_ZONE_RLT.L_EMPTY_SHELVES
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setlEmptyShelves(Integer lEmptyShelves) {
        this.lEmptyShelves = lEmptyShelves;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.HIGH_WATER_MARK
     *
     * @return the value of STOCKER_ZONE_RLT.HIGH_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getHighWaterMark() {
        return highWaterMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.HIGH_WATER_MARK
     *
     * @param highWaterMark the value for STOCKER_ZONE_RLT.HIGH_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setHighWaterMark(Integer highWaterMark) {
        this.highWaterMark = highWaterMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.LOW_WATER_MARK
     *
     * @return the value of STOCKER_ZONE_RLT.LOW_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Integer getLowWaterMark() {
        return lowWaterMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.LOW_WATER_MARK
     *
     * @param lowWaterMark the value for STOCKER_ZONE_RLT.LOW_WATER_MARK
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setLowWaterMark(Integer lowWaterMark) {
        this.lowWaterMark = lowWaterMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_LEVEL
     *
     * @return the value of STOCKER_ZONE_RLT.HWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Short getHwmHysteresisLevel() {
        return hwmHysteresisLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_LEVEL
     *
     * @param hwmHysteresisLevel the value for STOCKER_ZONE_RLT.HWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setHwmHysteresisLevel(Short hwmHysteresisLevel) {
        this.hwmHysteresisLevel = hwmHysteresisLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_LEVEL
     *
     * @return the value of STOCKER_ZONE_RLT.LWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Short getLwmHysteresisLevel() {
        return lwmHysteresisLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_LEVEL
     *
     * @param lwmHysteresisLevel the value for STOCKER_ZONE_RLT.LWM_HYSTERESIS_LEVEL
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setLwmHysteresisLevel(Short lwmHysteresisLevel) {
        this.lwmHysteresisLevel = lwmHysteresisLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_FLG
     *
     * @return the value of STOCKER_ZONE_RLT.HWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Short getHwmHysteresisFlg() {
        return hwmHysteresisFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.HWM_HYSTERESIS_FLG
     *
     * @param hwmHysteresisFlg the value for STOCKER_ZONE_RLT.HWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setHwmHysteresisFlg(Short hwmHysteresisFlg) {
        this.hwmHysteresisFlg = hwmHysteresisFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_FLG
     *
     * @return the value of STOCKER_ZONE_RLT.LWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Short getLwmHysteresisFlg() {
        return lwmHysteresisFlg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column STOCKER_ZONE_RLT.LWM_HYSTERESIS_FLG
     *
     * @param lwmHysteresisFlg the value for STOCKER_ZONE_RLT.LWM_HYSTERESIS_FLG
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setLwmHysteresisFlg(Short lwmHysteresisFlg) {
        this.lwmHysteresisFlg = lwmHysteresisFlg;
    }
}