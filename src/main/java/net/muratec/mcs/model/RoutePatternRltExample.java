﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class RoutePatternRltExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public RoutePatternRltExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
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
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ROUTE_PATTERN_RLT
     *
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

        public Criteria andSrcKeyIsNull() {
            addCriterion("SRC_KEY is null");
            return (Criteria) this;
        }

        public Criteria andSrcKeyIsNotNull() {
            addCriterion("SRC_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andSrcKeyEqualTo(String value) {
            addCriterion("SRC_KEY =", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyNotEqualTo(String value) {
            addCriterion("SRC_KEY <>", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyGreaterThan(String value) {
            addCriterion("SRC_KEY >", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_KEY >=", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyLessThan(String value) {
            addCriterion("SRC_KEY <", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyLessThanOrEqualTo(String value) {
            addCriterion("SRC_KEY <=", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyLike(String value) {
            addCriterion("SRC_KEY like", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyNotLike(String value) {
            addCriterion("SRC_KEY not like", value, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyIn(List<String> values) {
            addCriterion("SRC_KEY in", values, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyNotIn(List<String> values) {
            addCriterion("SRC_KEY not in", values, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyBetween(String value1, String value2) {
            addCriterion("SRC_KEY between", value1, value2, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcKeyNotBetween(String value1, String value2) {
            addCriterion("SRC_KEY not between", value1, value2, "srcKey");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdIsNull() {
            addCriterion("SRC_ELEMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdIsNotNull() {
            addCriterion("SRC_ELEMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdEqualTo(String value) {
            addCriterion("SRC_ELEMENT_ID =", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdNotEqualTo(String value) {
            addCriterion("SRC_ELEMENT_ID <>", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdGreaterThan(String value) {
            addCriterion("SRC_ELEMENT_ID >", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdGreaterThanOrEqualTo(String value) {
            addCriterion("SRC_ELEMENT_ID >=", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdLessThan(String value) {
            addCriterion("SRC_ELEMENT_ID <", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdLessThanOrEqualTo(String value) {
            addCriterion("SRC_ELEMENT_ID <=", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdLike(String value) {
            addCriterion("SRC_ELEMENT_ID like", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdNotLike(String value) {
            addCriterion("SRC_ELEMENT_ID not like", value, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdIn(List<String> values) {
            addCriterion("SRC_ELEMENT_ID in", values, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdNotIn(List<String> values) {
            addCriterion("SRC_ELEMENT_ID not in", values, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdBetween(String value1, String value2) {
            addCriterion("SRC_ELEMENT_ID between", value1, value2, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andSrcElementIdNotBetween(String value1, String value2) {
            addCriterion("SRC_ELEMENT_ID not between", value1, value2, "srcElementId");
            return (Criteria) this;
        }

        public Criteria andDstKeyIsNull() {
            addCriterion("DST_KEY is null");
            return (Criteria) this;
        }

        public Criteria andDstKeyIsNotNull() {
            addCriterion("DST_KEY is not null");
            return (Criteria) this;
        }

        public Criteria andDstKeyEqualTo(String value) {
            addCriterion("DST_KEY =", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyNotEqualTo(String value) {
            addCriterion("DST_KEY <>", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyGreaterThan(String value) {
            addCriterion("DST_KEY >", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyGreaterThanOrEqualTo(String value) {
            addCriterion("DST_KEY >=", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyLessThan(String value) {
            addCriterion("DST_KEY <", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyLessThanOrEqualTo(String value) {
            addCriterion("DST_KEY <=", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyLike(String value) {
            addCriterion("DST_KEY like", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyNotLike(String value) {
            addCriterion("DST_KEY not like", value, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyIn(List<String> values) {
            addCriterion("DST_KEY in", values, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyNotIn(List<String> values) {
            addCriterion("DST_KEY not in", values, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyBetween(String value1, String value2) {
            addCriterion("DST_KEY between", value1, value2, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstKeyNotBetween(String value1, String value2) {
            addCriterion("DST_KEY not between", value1, value2, "dstKey");
            return (Criteria) this;
        }

        public Criteria andDstElementIdIsNull() {
            addCriterion("DST_ELEMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andDstElementIdIsNotNull() {
            addCriterion("DST_ELEMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDstElementIdEqualTo(String value) {
            addCriterion("DST_ELEMENT_ID =", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdNotEqualTo(String value) {
            addCriterion("DST_ELEMENT_ID <>", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdGreaterThan(String value) {
            addCriterion("DST_ELEMENT_ID >", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdGreaterThanOrEqualTo(String value) {
            addCriterion("DST_ELEMENT_ID >=", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdLessThan(String value) {
            addCriterion("DST_ELEMENT_ID <", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdLessThanOrEqualTo(String value) {
            addCriterion("DST_ELEMENT_ID <=", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdLike(String value) {
            addCriterion("DST_ELEMENT_ID like", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdNotLike(String value) {
            addCriterion("DST_ELEMENT_ID not like", value, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdIn(List<String> values) {
            addCriterion("DST_ELEMENT_ID in", values, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdNotIn(List<String> values) {
            addCriterion("DST_ELEMENT_ID not in", values, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdBetween(String value1, String value2) {
            addCriterion("DST_ELEMENT_ID between", value1, value2, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andDstElementIdNotBetween(String value1, String value2) {
            addCriterion("DST_ELEMENT_ID not between", value1, value2, "dstElementId");
            return (Criteria) this;
        }

        public Criteria andPatternIdIsNull() {
            addCriterion("PATTERN_ID is null");
            return (Criteria) this;
        }

        public Criteria andPatternIdIsNotNull() {
            addCriterion("PATTERN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPatternIdEqualTo(String value) {
            addCriterion("PATTERN_ID =", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdNotEqualTo(String value) {
            addCriterion("PATTERN_ID <>", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdGreaterThan(String value) {
            addCriterion("PATTERN_ID >", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdGreaterThanOrEqualTo(String value) {
            addCriterion("PATTERN_ID >=", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdLessThan(String value) {
            addCriterion("PATTERN_ID <", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdLessThanOrEqualTo(String value) {
            addCriterion("PATTERN_ID <=", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdLike(String value) {
            addCriterion("PATTERN_ID like", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdNotLike(String value) {
            addCriterion("PATTERN_ID not like", value, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdIn(List<String> values) {
            addCriterion("PATTERN_ID in", values, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdNotIn(List<String> values) {
            addCriterion("PATTERN_ID not in", values, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdBetween(String value1, String value2) {
            addCriterion("PATTERN_ID between", value1, value2, "patternId");
            return (Criteria) this;
        }

        public Criteria andPatternIdNotBetween(String value1, String value2) {
            addCriterion("PATTERN_ID not between", value1, value2, "patternId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ROUTE_PATTERN_RLT
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table ROUTE_PATTERN_RLT
     *
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
}
