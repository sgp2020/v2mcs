﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class StorageTimeHistoryExample {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public StorageTimeHistoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public String getOrderByClause() {

        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public boolean isDistinct() {

        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public Criteria createCriteria() {

        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {

            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {

            return criteria;
        }

        public List<Criterion> getCriteria() {

            return criteria;
        }

        protected void addCriterion(String condition) {

            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {

            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {

            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAmhsIdIsNull() {

            addCriterion("AMHS_ID is null");
            return (Criteria) this;
        }

        public Criteria andAmhsIdIsNotNull() {

            addCriterion("AMHS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAmhsIdEqualTo(String value) {

            addCriterion("AMHS_ID =", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdNotEqualTo(String value) {

            addCriterion("AMHS_ID <>", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdGreaterThan(String value) {

            addCriterion("AMHS_ID >", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdGreaterThanOrEqualTo(String value) {

            addCriterion("AMHS_ID >=", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdLessThan(String value) {

            addCriterion("AMHS_ID <", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdLessThanOrEqualTo(String value) {

            addCriterion("AMHS_ID <=", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdLike(String value) {

            addCriterion("AMHS_ID like", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdNotLike(String value) {

            addCriterion("AMHS_ID not like", value, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdIn(List<String> values) {

            addCriterion("AMHS_ID in", values, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdNotIn(List<String> values) {

            addCriterion("AMHS_ID not in", values, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdBetween(String value1, String value2) {

            addCriterion("AMHS_ID between", value1, value2, "amhsId");
            return (Criteria) this;
        }

        public Criteria andAmhsIdNotBetween(String value1, String value2) {

            addCriterion("AMHS_ID not between", value1, value2, "amhsId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIsNull() {

            addCriterion("CARRIER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIsNotNull() {

            addCriterion("CARRIER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierIdEqualTo(String value) {

            addCriterion("CARRIER_ID =", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotEqualTo(String value) {

            addCriterion("CARRIER_ID <>", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdGreaterThan(String value) {

            addCriterion("CARRIER_ID >", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdGreaterThanOrEqualTo(String value) {

            addCriterion("CARRIER_ID >=", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLessThan(String value) {

            addCriterion("CARRIER_ID <", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLessThanOrEqualTo(String value) {

            addCriterion("CARRIER_ID <=", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdLike(String value) {

            addCriterion("CARRIER_ID like", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotLike(String value) {

            addCriterion("CARRIER_ID not like", value, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdIn(List<String> values) {

            addCriterion("CARRIER_ID in", values, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotIn(List<String> values) {

            addCriterion("CARRIER_ID not in", values, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdBetween(String value1, String value2) {

            addCriterion("CARRIER_ID between", value1, value2, "carrierId");
            return (Criteria) this;
        }

        public Criteria andCarrierIdNotBetween(String value1, String value2) {

            addCriterion("CARRIER_ID not between", value1, value2, "carrierId");
            return (Criteria) this;
        }

        public Criteria andStoredTimeIsNull() {

            addCriterion("STORED_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStoredTimeIsNotNull() {

            addCriterion("STORED_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStoredTimeEqualTo(Timestamp value) {

            addCriterion("STORED_TIME =", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeNotEqualTo(Timestamp value) {

            addCriterion("STORED_TIME <>", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeGreaterThan(Timestamp value) {

            addCriterion("STORED_TIME >", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeGreaterThanOrEqualTo(Timestamp value) {

            addCriterion("STORED_TIME >=", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeLessThan(Timestamp value) {

            addCriterion("STORED_TIME <", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeLessThanOrEqualTo(Timestamp value) {

            addCriterion("STORED_TIME <=", value, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeIn(List<Timestamp> values) {

            addCriterion("STORED_TIME in", values, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeNotIn(List<Timestamp> values) {

            addCriterion("STORED_TIME not in", values, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeBetween(Timestamp value1, Timestamp value2) {

            addCriterion("STORED_TIME between", value1, value2, "storedTime");
            return (Criteria) this;
        }

        public Criteria andStoredTimeNotBetween(Timestamp value1, Timestamp value2) {

            addCriterion("STORED_TIME not between", value1, value2, "storedTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeIsNull() {

            addCriterion("WAIT_OUT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeIsNotNull() {

            addCriterion("WAIT_OUT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeEqualTo(Timestamp value) {

            addCriterion("WAIT_OUT_TIME =", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeNotEqualTo(Timestamp value) {

            addCriterion("WAIT_OUT_TIME <>", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeGreaterThan(Timestamp value) {

            addCriterion("WAIT_OUT_TIME >", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeGreaterThanOrEqualTo(Timestamp value) {

            addCriterion("WAIT_OUT_TIME >=", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeLessThan(Timestamp value) {

            addCriterion("WAIT_OUT_TIME <", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeLessThanOrEqualTo(Timestamp value) {

            addCriterion("WAIT_OUT_TIME <=", value, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeIn(List<Timestamp> values) {

            addCriterion("WAIT_OUT_TIME in", values, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeNotIn(List<Timestamp> values) {

            addCriterion("WAIT_OUT_TIME not in", values, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeBetween(Timestamp value1, Timestamp value2) {

            addCriterion("WAIT_OUT_TIME between", value1, value2, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andWaitOutTimeNotBetween(Timestamp value1, Timestamp value2) {

            addCriterion("WAIT_OUT_TIME not between", value1, value2, "waitOutTime");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {

            addCriterion("RESULT is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {

            addCriterion("RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(String value) {

            addCriterion("RESULT =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(String value) {

            addCriterion("RESULT <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(String value) {

            addCriterion("RESULT >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(String value) {

            addCriterion("RESULT >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(String value) {

            addCriterion("RESULT <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(String value) {

            addCriterion("RESULT <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLike(String value) {

            addCriterion("RESULT like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotLike(String value) {

            addCriterion("RESULT not like", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<String> values) {

            addCriterion("RESULT in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<String> values) {

            addCriterion("RESULT not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(String value1, String value2) {

            addCriterion("RESULT between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(String value1, String value2) {

            addCriterion("RESULT not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {

            addCriterion("DURATION is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {

            addCriterion("DURATION is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Short value) {

            addCriterion("DURATION =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Short value) {

            addCriterion("DURATION <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Short value) {

            addCriterion("DURATION >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Short value) {

            addCriterion("DURATION >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Short value) {

            addCriterion("DURATION <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Short value) {

            addCriterion("DURATION <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Short> values) {

            addCriterion("DURATION in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Short> values) {

            addCriterion("DURATION not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Short value1, Short value2) {

            addCriterion("DURATION between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Short value1, Short value2) {

            addCriterion("DURATION not between", value1, value2, "duration");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table STORAGE_TIME_HISTORY
     * @mbg.generated
     */
    public static class Criterion {

        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        public String getCondition() {

            return condition;
        }

        public Object getValue() {

            return value;
        }

        public Object getSecondValue() {

            return secondValue;
        }

        public boolean isNoValue() {

            return noValue;
        }

        public boolean isSingleValue() {

            return singleValue;
        }

        public boolean isBetweenValue() {

            return betweenValue;
        }

        public boolean isListValue() {

            return listValue;
        }

        public String getTypeHandler() {

            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table
     * STORAGE_TIME_HISTORY
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 07 15:13:24 JST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
