package net.muratec.mcs.model;

import java.sql.Timestamp;

public class PerfDataHistory {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column PERF_DATA_HISTORY.TIME
     * @mbg.generated
     */
    private Timestamp time;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column PERF_DATA_HISTORY.KEY1
     * @mbg.generated
     */
    private String key1;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column PERF_DATA_HISTORY.KEY2
     * @mbg.generated
     */
    private String key2;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column PERF_DATA_HISTORY.KEY3
     * @mbg.generated
     */
    private String key3;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column PERF_DATA_HISTORY.VALUE
     * @mbg.generated
     */
    private Short value;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column PERF_DATA_HISTORY.TIME
     * @return  the value of PERF_DATA_HISTORY.TIME
     * @mbg.generated
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column PERF_DATA_HISTORY.TIME
     * @param time  the value for PERF_DATA_HISTORY.TIME
     * @mbg.generated
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column PERF_DATA_HISTORY.KEY1
     * @return  the value of PERF_DATA_HISTORY.KEY1
     * @mbg.generated
     */
    public String getKey1() {
        return key1;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column PERF_DATA_HISTORY.KEY1
     * @param key1  the value for PERF_DATA_HISTORY.KEY1
     * @mbg.generated
     */
    public void setKey1(String key1) {
        this.key1 = key1;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column PERF_DATA_HISTORY.KEY2
     * @return  the value of PERF_DATA_HISTORY.KEY2
     * @mbg.generated
     */
    public String getKey2() {
        return key2;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column PERF_DATA_HISTORY.KEY2
     * @param key2  the value for PERF_DATA_HISTORY.KEY2
     * @mbg.generated
     */
    public void setKey2(String key2) {
        this.key2 = key2;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column PERF_DATA_HISTORY.KEY3
     * @return  the value of PERF_DATA_HISTORY.KEY3
     * @mbg.generated
     */
    public String getKey3() {
        return key3;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column PERF_DATA_HISTORY.KEY3
     * @param key3  the value for PERF_DATA_HISTORY.KEY3
     * @mbg.generated
     */
    public void setKey3(String key3) {
        this.key3 = key3;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column PERF_DATA_HISTORY.VALUE
     * @return  the value of PERF_DATA_HISTORY.VALUE
     * @mbg.generated
     */
    public Short getValue() {
        return value;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column PERF_DATA_HISTORY.VALUE
     * @param value  the value for PERF_DATA_HISTORY.VALUE
     * @mbg.generated
     */
    public void setValue(Short value) {
        this.value = value;
    }
}