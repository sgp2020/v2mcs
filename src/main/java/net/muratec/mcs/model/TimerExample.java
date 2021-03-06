﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class TimerExample {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table TIMER
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table TIMER
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table TIMER
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public TimerExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public String getOrderByClause() {

        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public boolean isDistinct() {

        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
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
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table TIMER
     * @mbg.generated
     */
    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table TIMER
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

        public Criteria andTimeIsNull() {

            addCriterion("TIME is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {

            addCriterion("TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Timestamp value) {

            addCriterion("TIME =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Timestamp value) {

            addCriterion("TIME <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Timestamp value) {

            addCriterion("TIME >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Timestamp value) {

            addCriterion("TIME >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Timestamp value) {

            addCriterion("TIME <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Timestamp value) {

            addCriterion("TIME <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Timestamp> values) {

            addCriterion("TIME in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Timestamp> values) {

            addCriterion("TIME not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Timestamp value1, Timestamp value2) {

            addCriterion("TIME between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Timestamp value1, Timestamp value2) {

            addCriterion("TIME not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNull() {

            addCriterion("SEQ_NO is null");
            return (Criteria) this;
        }

        public Criteria andSeqNoIsNotNull() {

            addCriterion("SEQ_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSeqNoEqualTo(Integer value) {

            addCriterion("SEQ_NO =", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotEqualTo(Integer value) {

            addCriterion("SEQ_NO <>", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThan(Integer value) {

            addCriterion("SEQ_NO >", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoGreaterThanOrEqualTo(Integer value) {

            addCriterion("SEQ_NO >=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThan(Integer value) {

            addCriterion("SEQ_NO <", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoLessThanOrEqualTo(Integer value) {

            addCriterion("SEQ_NO <=", value, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoIn(List<Integer> values) {

            addCriterion("SEQ_NO in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotIn(List<Integer> values) {

            addCriterion("SEQ_NO not in", values, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoBetween(Integer value1, Integer value2) {

            addCriterion("SEQ_NO between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andSeqNoNotBetween(Integer value1, Integer value2) {

            addCriterion("SEQ_NO not between", value1, value2, "seqNo");
            return (Criteria) this;
        }

        public Criteria andTimerTypeIsNull() {

            addCriterion("TIMER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTimerTypeIsNotNull() {

            addCriterion("TIMER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTimerTypeEqualTo(Short value) {

            addCriterion("TIMER_TYPE =", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeNotEqualTo(Short value) {

            addCriterion("TIMER_TYPE <>", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeGreaterThan(Short value) {

            addCriterion("TIMER_TYPE >", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeGreaterThanOrEqualTo(Short value) {

            addCriterion("TIMER_TYPE >=", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeLessThan(Short value) {

            addCriterion("TIMER_TYPE <", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeLessThanOrEqualTo(Short value) {

            addCriterion("TIMER_TYPE <=", value, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeIn(List<Short> values) {

            addCriterion("TIMER_TYPE in", values, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeNotIn(List<Short> values) {

            addCriterion("TIMER_TYPE not in", values, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeBetween(Short value1, Short value2) {

            addCriterion("TIMER_TYPE between", value1, value2, "timerType");
            return (Criteria) this;
        }

        public Criteria andTimerTypeNotBetween(Short value1, Short value2) {

            addCriterion("TIMER_TYPE not between", value1, value2, "timerType");
            return (Criteria) this;
        }

        public Criteria andSetTimeIsNull() {

            addCriterion("SET_TIME is null");
            return (Criteria) this;
        }

        public Criteria andSetTimeIsNotNull() {

            addCriterion("SET_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andSetTimeEqualTo(Timestamp value) {

            addCriterion("SET_TIME =", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeNotEqualTo(Timestamp value) {

            addCriterion("SET_TIME <>", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeGreaterThan(Timestamp value) {

            addCriterion("SET_TIME >", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeGreaterThanOrEqualTo(Timestamp value) {

            addCriterion("SET_TIME >=", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeLessThan(Timestamp value) {

            addCriterion("SET_TIME <", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeLessThanOrEqualTo(Timestamp value) {

            addCriterion("SET_TIME <=", value, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeIn(List<Timestamp> values) {

            addCriterion("SET_TIME in", values, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeNotIn(List<Timestamp> values) {

            addCriterion("SET_TIME not in", values, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeBetween(Timestamp value1, Timestamp value2) {

            addCriterion("SET_TIME between", value1, value2, "setTime");
            return (Criteria) this;
        }

        public Criteria andSetTimeNotBetween(Timestamp value1, Timestamp value2) {

            addCriterion("SET_TIME not between", value1, value2, "setTime");
            return (Criteria) this;
        }

        public Criteria andIntervalIsNull() {

            addCriterion("INTERVAL is null");
            return (Criteria) this;
        }

        public Criteria andIntervalIsNotNull() {

            addCriterion("INTERVAL is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalEqualTo(Integer value) {

            addCriterion("INTERVAL =", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotEqualTo(Integer value) {

            addCriterion("INTERVAL <>", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalGreaterThan(Integer value) {

            addCriterion("INTERVAL >", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalGreaterThanOrEqualTo(Integer value) {

            addCriterion("INTERVAL >=", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalLessThan(Integer value) {

            addCriterion("INTERVAL <", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalLessThanOrEqualTo(Integer value) {

            addCriterion("INTERVAL <=", value, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalIn(List<Integer> values) {

            addCriterion("INTERVAL in", values, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotIn(List<Integer> values) {

            addCriterion("INTERVAL not in", values, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalBetween(Integer value1, Integer value2) {

            addCriterion("INTERVAL between", value1, value2, "interval");
            return (Criteria) this;
        }

        public Criteria andIntervalNotBetween(Integer value1, Integer value2) {

            addCriterion("INTERVAL not between", value1, value2, "interval");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdIsNull() {

            addCriterion("CARRIER_JOB_ID is null");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdIsNotNull() {

            addCriterion("CARRIER_JOB_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdEqualTo(String value) {

            addCriterion("CARRIER_JOB_ID =", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdNotEqualTo(String value) {

            addCriterion("CARRIER_JOB_ID <>", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdGreaterThan(String value) {

            addCriterion("CARRIER_JOB_ID >", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdGreaterThanOrEqualTo(String value) {

            addCriterion("CARRIER_JOB_ID >=", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdLessThan(String value) {

            addCriterion("CARRIER_JOB_ID <", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdLessThanOrEqualTo(String value) {

            addCriterion("CARRIER_JOB_ID <=", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdLike(String value) {

            addCriterion("CARRIER_JOB_ID like", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdNotLike(String value) {

            addCriterion("CARRIER_JOB_ID not like", value, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdIn(List<String> values) {

            addCriterion("CARRIER_JOB_ID in", values, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdNotIn(List<String> values) {

            addCriterion("CARRIER_JOB_ID not in", values, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdBetween(String value1, String value2) {

            addCriterion("CARRIER_JOB_ID between", value1, value2, "carrierJobId");
            return (Criteria) this;
        }

        public Criteria andCarrierJobIdNotBetween(String value1, String value2) {

            addCriterion("CARRIER_JOB_ID not between", value1, value2, "carrierJobId");
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

        public Criteria andRcvSubjectIsNull() {

            addCriterion("RCV_SUBJECT is null");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectIsNotNull() {

            addCriterion("RCV_SUBJECT is not null");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectEqualTo(String value) {

            addCriterion("RCV_SUBJECT =", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectNotEqualTo(String value) {

            addCriterion("RCV_SUBJECT <>", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectGreaterThan(String value) {

            addCriterion("RCV_SUBJECT >", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectGreaterThanOrEqualTo(String value) {

            addCriterion("RCV_SUBJECT >=", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectLessThan(String value) {

            addCriterion("RCV_SUBJECT <", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectLessThanOrEqualTo(String value) {

            addCriterion("RCV_SUBJECT <=", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectLike(String value) {

            addCriterion("RCV_SUBJECT like", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectNotLike(String value) {

            addCriterion("RCV_SUBJECT not like", value, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectIn(List<String> values) {

            addCriterion("RCV_SUBJECT in", values, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectNotIn(List<String> values) {

            addCriterion("RCV_SUBJECT not in", values, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectBetween(String value1, String value2) {

            addCriterion("RCV_SUBJECT between", value1, value2, "rcvSubject");
            return (Criteria) this;
        }

        public Criteria andRcvSubjectNotBetween(String value1, String value2) {

            addCriterion("RCV_SUBJECT not between", value1, value2, "rcvSubject");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table TIMER
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
     * This class was generated by MyBatis Generator. This class corresponds to the database table TIMER
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 07 15:13:24 JST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
