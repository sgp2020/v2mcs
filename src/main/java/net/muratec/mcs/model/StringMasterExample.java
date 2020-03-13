﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class StringMasterExample {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public StringMasterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
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
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table STRING_MASTER
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table STRING_MASTER
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

        public Criteria andCodeIsNull() {
            addCriterion("CODE is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(Long value) {
            addCriterion("CODE =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(Long value) {
            addCriterion("CODE <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(Long value) {
            addCriterion("CODE >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("CODE >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(Long value) {
            addCriterion("CODE <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(Long value) {
            addCriterion("CODE <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<Long> values) {
            addCriterion("CODE in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<Long> values) {
            addCriterion("CODE not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(Long value1, Long value2) {
            addCriterion("CODE between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(Long value1, Long value2) {
            addCriterion("CODE not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andKeyIsNull() {
            addCriterion("KEY is null");
            return (Criteria) this;
        }

        public Criteria andKeyIsNotNull() {
            addCriterion("KEY is not null");
            return (Criteria) this;
        }

        public Criteria andKeyEqualTo(String value) {
            addCriterion("KEY =", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotEqualTo(String value) {
            addCriterion("KEY <>", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThan(String value) {
            addCriterion("KEY >", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyGreaterThanOrEqualTo(String value) {
            addCriterion("KEY >=", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLessThan(String value) {
            addCriterion("KEY <", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLessThanOrEqualTo(String value) {
            addCriterion("KEY <=", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyLike(String value) {
            addCriterion("KEY like", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotLike(String value) {
            addCriterion("KEY not like", value, "key");
            return (Criteria) this;
        }

        public Criteria andKeyIn(List<String> values) {
            addCriterion("KEY in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotIn(List<String> values) {
            addCriterion("KEY not in", values, "key");
            return (Criteria) this;
        }

        public Criteria andKeyBetween(String value1, String value2) {
            addCriterion("KEY between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andKeyNotBetween(String value1, String value2) {
            addCriterion("KEY not between", value1, value2, "key");
            return (Criteria) this;
        }

        public Criteria andStringIsNull() {
            addCriterion("STRING is null");
            return (Criteria) this;
        }

        public Criteria andStringIsNotNull() {
            addCriterion("STRING is not null");
            return (Criteria) this;
        }

        public Criteria andStringEqualTo(String value) {
            addCriterion("STRING =", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringNotEqualTo(String value) {
            addCriterion("STRING <>", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringGreaterThan(String value) {
            addCriterion("STRING >", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringGreaterThanOrEqualTo(String value) {
            addCriterion("STRING >=", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringLessThan(String value) {
            addCriterion("STRING <", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringLessThanOrEqualTo(String value) {
            addCriterion("STRING <=", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringLike(String value) {
            addCriterion("STRING like", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringNotLike(String value) {
            addCriterion("STRING not like", value, "string");
            return (Criteria) this;
        }

        public Criteria andStringIn(List<String> values) {
            addCriterion("STRING in", values, "string");
            return (Criteria) this;
        }

        public Criteria andStringNotIn(List<String> values) {
            addCriterion("STRING not in", values, "string");
            return (Criteria) this;
        }

        public Criteria andStringBetween(String value1, String value2) {
            addCriterion("STRING between", value1, value2, "string");
            return (Criteria) this;
        }

        public Criteria andStringNotBetween(String value1, String value2) {
            addCriterion("STRING not between", value1, value2, "string");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table STRING_MASTER
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
     * STRING_MASTER
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 07 15:13:24 JST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
