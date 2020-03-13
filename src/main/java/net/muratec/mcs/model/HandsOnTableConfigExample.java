﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class HandsOnTableConfigExample {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public HandsOnTableConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public String getOrderByClause() {

        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public boolean isDistinct() {

        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
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
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table HANDS_ON_TABLE_CONFIG
     * @mbg.generated
     */
    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table HANDS_ON_TABLE_CONFIG
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

        public Criteria andSectionIsNull() {

            addCriterion("SECTION is null");
            return (Criteria) this;
        }

        public Criteria andSectionIsNotNull() {

            addCriterion("SECTION is not null");
            return (Criteria) this;
        }

        public Criteria andSectionEqualTo(String value) {

            addCriterion("SECTION =", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotEqualTo(String value) {

            addCriterion("SECTION <>", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionGreaterThan(String value) {

            addCriterion("SECTION >", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionGreaterThanOrEqualTo(String value) {

            addCriterion("SECTION >=", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLessThan(String value) {

            addCriterion("SECTION <", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLessThanOrEqualTo(String value) {

            addCriterion("SECTION <=", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLike(String value) {

            addCriterion("SECTION like", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotLike(String value) {

            addCriterion("SECTION not like", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionIn(List<String> values) {

            addCriterion("SECTION in", values, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotIn(List<String> values) {

            addCriterion("SECTION not in", values, "section");
            return (Criteria) this;
        }

        public Criteria andSectionBetween(String value1, String value2) {

            addCriterion("SECTION between", value1, value2, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotBetween(String value1, String value2) {

            addCriterion("SECTION not between", value1, value2, "section");
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

        public Criteria andValue1IsNull() {

            addCriterion("VALUE1 is null");
            return (Criteria) this;
        }

        public Criteria andValue1IsNotNull() {

            addCriterion("VALUE1 is not null");
            return (Criteria) this;
        }

        public Criteria andValue1EqualTo(String value) {

            addCriterion("VALUE1 =", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1NotEqualTo(String value) {

            addCriterion("VALUE1 <>", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1GreaterThan(String value) {

            addCriterion("VALUE1 >", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1GreaterThanOrEqualTo(String value) {

            addCriterion("VALUE1 >=", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1LessThan(String value) {

            addCriterion("VALUE1 <", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1LessThanOrEqualTo(String value) {

            addCriterion("VALUE1 <=", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1Like(String value) {

            addCriterion("VALUE1 like", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1NotLike(String value) {

            addCriterion("VALUE1 not like", value, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1In(List<String> values) {

            addCriterion("VALUE1 in", values, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1NotIn(List<String> values) {

            addCriterion("VALUE1 not in", values, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1Between(String value1, String value2) {

            addCriterion("VALUE1 between", value1, value2, "value1");
            return (Criteria) this;
        }

        public Criteria andValue1NotBetween(String value1, String value2) {

            addCriterion("VALUE1 not between", value1, value2, "value1");
            return (Criteria) this;
        }

        public Criteria andValue2IsNull() {

            addCriterion("VALUE2 is null");
            return (Criteria) this;
        }

        public Criteria andValue2IsNotNull() {

            addCriterion("VALUE2 is not null");
            return (Criteria) this;
        }

        public Criteria andValue2EqualTo(String value) {

            addCriterion("VALUE2 =", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2NotEqualTo(String value) {

            addCriterion("VALUE2 <>", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2GreaterThan(String value) {

            addCriterion("VALUE2 >", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2GreaterThanOrEqualTo(String value) {

            addCriterion("VALUE2 >=", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2LessThan(String value) {

            addCriterion("VALUE2 <", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2LessThanOrEqualTo(String value) {

            addCriterion("VALUE2 <=", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2Like(String value) {

            addCriterion("VALUE2 like", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2NotLike(String value) {

            addCriterion("VALUE2 not like", value, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2In(List<String> values) {

            addCriterion("VALUE2 in", values, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2NotIn(List<String> values) {

            addCriterion("VALUE2 not in", values, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2Between(String value1, String value2) {

            addCriterion("VALUE2 between", value1, value2, "value2");
            return (Criteria) this;
        }

        public Criteria andValue2NotBetween(String value1, String value2) {

            addCriterion("VALUE2 not between", value1, value2, "value2");
            return (Criteria) this;
        }

        public Criteria andValue3IsNull() {

            addCriterion("VALUE3 is null");
            return (Criteria) this;
        }

        public Criteria andValue3IsNotNull() {

            addCriterion("VALUE3 is not null");
            return (Criteria) this;
        }

        public Criteria andValue3EqualTo(String value) {

            addCriterion("VALUE3 =", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3NotEqualTo(String value) {

            addCriterion("VALUE3 <>", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3GreaterThan(String value) {

            addCriterion("VALUE3 >", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3GreaterThanOrEqualTo(String value) {

            addCriterion("VALUE3 >=", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3LessThan(String value) {

            addCriterion("VALUE3 <", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3LessThanOrEqualTo(String value) {

            addCriterion("VALUE3 <=", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3Like(String value) {

            addCriterion("VALUE3 like", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3NotLike(String value) {

            addCriterion("VALUE3 not like", value, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3In(List<String> values) {

            addCriterion("VALUE3 in", values, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3NotIn(List<String> values) {

            addCriterion("VALUE3 not in", values, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3Between(String value1, String value2) {

            addCriterion("VALUE3 between", value1, value2, "value3");
            return (Criteria) this;
        }

        public Criteria andValue3NotBetween(String value1, String value2) {

            addCriterion("VALUE3 not between", value1, value2, "value3");
            return (Criteria) this;
        }

        public Criteria andValue4IsNull() {

            addCriterion("VALUE4 is null");
            return (Criteria) this;
        }

        public Criteria andValue4IsNotNull() {

            addCriterion("VALUE4 is not null");
            return (Criteria) this;
        }

        public Criteria andValue4EqualTo(String value) {

            addCriterion("VALUE4 =", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4NotEqualTo(String value) {

            addCriterion("VALUE4 <>", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4GreaterThan(String value) {

            addCriterion("VALUE4 >", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4GreaterThanOrEqualTo(String value) {

            addCriterion("VALUE4 >=", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4LessThan(String value) {

            addCriterion("VALUE4 <", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4LessThanOrEqualTo(String value) {

            addCriterion("VALUE4 <=", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4Like(String value) {

            addCriterion("VALUE4 like", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4NotLike(String value) {

            addCriterion("VALUE4 not like", value, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4In(List<String> values) {

            addCriterion("VALUE4 in", values, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4NotIn(List<String> values) {

            addCriterion("VALUE4 not in", values, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4Between(String value1, String value2) {

            addCriterion("VALUE4 between", value1, value2, "value4");
            return (Criteria) this;
        }

        public Criteria andValue4NotBetween(String value1, String value2) {

            addCriterion("VALUE4 not between", value1, value2, "value4");
            return (Criteria) this;
        }

        public Criteria andValue5IsNull() {

            addCriterion("VALUE5 is null");
            return (Criteria) this;
        }

        public Criteria andValue5IsNotNull() {

            addCriterion("VALUE5 is not null");
            return (Criteria) this;
        }

        public Criteria andValue5EqualTo(String value) {

            addCriterion("VALUE5 =", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5NotEqualTo(String value) {

            addCriterion("VALUE5 <>", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5GreaterThan(String value) {

            addCriterion("VALUE5 >", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5GreaterThanOrEqualTo(String value) {

            addCriterion("VALUE5 >=", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5LessThan(String value) {

            addCriterion("VALUE5 <", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5LessThanOrEqualTo(String value) {

            addCriterion("VALUE5 <=", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5Like(String value) {

            addCriterion("VALUE5 like", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5NotLike(String value) {

            addCriterion("VALUE5 not like", value, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5In(List<String> values) {

            addCriterion("VALUE5 in", values, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5NotIn(List<String> values) {

            addCriterion("VALUE5 not in", values, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5Between(String value1, String value2) {

            addCriterion("VALUE5 between", value1, value2, "value5");
            return (Criteria) this;
        }

        public Criteria andValue5NotBetween(String value1, String value2) {

            addCriterion("VALUE5 not between", value1, value2, "value5");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table HANDS_ON_TABLE_CONFIG
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
     * This class was generated by MyBatis Generator. This class corresponds to the database table HANDS_ON_TABLE_CONFIG
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
