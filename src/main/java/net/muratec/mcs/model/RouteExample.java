﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class RouteExample {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table ROUTE
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table ROUTE
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table ROUTE
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public RouteExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public String getOrderByClause() {

        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public boolean isDistinct() {

        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
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
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table ROUTE
     * @mbg.generated
     */
    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table ROUTE
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

        public Criteria andTableNoIsNull() {

            addCriterion("TABLE_NO is null");
            return (Criteria) this;
        }

        public Criteria andTableNoIsNotNull() {

            addCriterion("TABLE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTableNoEqualTo(Short value) {

            addCriterion("TABLE_NO =", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotEqualTo(Short value) {

            addCriterion("TABLE_NO <>", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoGreaterThan(Short value) {

            addCriterion("TABLE_NO >", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoGreaterThanOrEqualTo(Short value) {

            addCriterion("TABLE_NO >=", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoLessThan(Short value) {

            addCriterion("TABLE_NO <", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoLessThanOrEqualTo(Short value) {

            addCriterion("TABLE_NO <=", value, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoIn(List<Short> values) {

            addCriterion("TABLE_NO in", values, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotIn(List<Short> values) {

            addCriterion("TABLE_NO not in", values, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoBetween(Short value1, Short value2) {

            addCriterion("TABLE_NO between", value1, value2, "tableNo");
            return (Criteria) this;
        }

        public Criteria andTableNoNotBetween(Short value1, Short value2) {

            addCriterion("TABLE_NO not between", value1, value2, "tableNo");
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

        public Criteria andRouteNoIsNull() {

            addCriterion("ROUTE_NO is null");
            return (Criteria) this;
        }

        public Criteria andRouteNoIsNotNull() {

            addCriterion("ROUTE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andRouteNoEqualTo(Long value) {

            addCriterion("ROUTE_NO =", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotEqualTo(Long value) {

            addCriterion("ROUTE_NO <>", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoGreaterThan(Long value) {

            addCriterion("ROUTE_NO >", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoGreaterThanOrEqualTo(Long value) {

            addCriterion("ROUTE_NO >=", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoLessThan(Long value) {

            addCriterion("ROUTE_NO <", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoLessThanOrEqualTo(Long value) {

            addCriterion("ROUTE_NO <=", value, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoIn(List<Long> values) {

            addCriterion("ROUTE_NO in", values, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotIn(List<Long> values) {

            addCriterion("ROUTE_NO not in", values, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoBetween(Long value1, Long value2) {

            addCriterion("ROUTE_NO between", value1, value2, "routeNo");
            return (Criteria) this;
        }

        public Criteria andRouteNoNotBetween(Long value1, Long value2) {

            addCriterion("ROUTE_NO not between", value1, value2, "routeNo");
            return (Criteria) this;
        }

        public Criteria andFuncOptIsNull() {

            addCriterion("FUNC_OPT is null");
            return (Criteria) this;
        }

        public Criteria andFuncOptIsNotNull() {

            addCriterion("FUNC_OPT is not null");
            return (Criteria) this;
        }

        public Criteria andFuncOptEqualTo(Short value) {

            addCriterion("FUNC_OPT =", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptNotEqualTo(Short value) {

            addCriterion("FUNC_OPT <>", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptGreaterThan(Short value) {

            addCriterion("FUNC_OPT >", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptGreaterThanOrEqualTo(Short value) {

            addCriterion("FUNC_OPT >=", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptLessThan(Short value) {

            addCriterion("FUNC_OPT <", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptLessThanOrEqualTo(Short value) {

            addCriterion("FUNC_OPT <=", value, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptIn(List<Short> values) {

            addCriterion("FUNC_OPT in", values, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptNotIn(List<Short> values) {

            addCriterion("FUNC_OPT not in", values, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptBetween(Short value1, Short value2) {

            addCriterion("FUNC_OPT between", value1, value2, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andFuncOptNotBetween(Short value1, Short value2) {

            addCriterion("FUNC_OPT not between", value1, value2, "funcOpt");
            return (Criteria) this;
        }

        public Criteria andRouteCountIsNull() {

            addCriterion("ROUTE_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andRouteCountIsNotNull() {

            addCriterion("ROUTE_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRouteCountEqualTo(Short value) {

            addCriterion("ROUTE_COUNT =", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountNotEqualTo(Short value) {

            addCriterion("ROUTE_COUNT <>", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountGreaterThan(Short value) {

            addCriterion("ROUTE_COUNT >", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountGreaterThanOrEqualTo(Short value) {

            addCriterion("ROUTE_COUNT >=", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountLessThan(Short value) {

            addCriterion("ROUTE_COUNT <", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountLessThanOrEqualTo(Short value) {

            addCriterion("ROUTE_COUNT <=", value, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountIn(List<Short> values) {

            addCriterion("ROUTE_COUNT in", values, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountNotIn(List<Short> values) {

            addCriterion("ROUTE_COUNT not in", values, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountBetween(Short value1, Short value2) {

            addCriterion("ROUTE_COUNT between", value1, value2, "routeCount");
            return (Criteria) this;
        }

        public Criteria andRouteCountNotBetween(Short value1, Short value2) {

            addCriterion("ROUTE_COUNT not between", value1, value2, "routeCount");
            return (Criteria) this;
        }

        public Criteria andEnableFlagIsNull() {

            addCriterion("ENABLE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andEnableFlagIsNotNull() {

            addCriterion("ENABLE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andEnableFlagEqualTo(Short value) {

            addCriterion("ENABLE_FLAG =", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagNotEqualTo(Short value) {

            addCriterion("ENABLE_FLAG <>", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagGreaterThan(Short value) {

            addCriterion("ENABLE_FLAG >", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagGreaterThanOrEqualTo(Short value) {

            addCriterion("ENABLE_FLAG >=", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagLessThan(Short value) {

            addCriterion("ENABLE_FLAG <", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagLessThanOrEqualTo(Short value) {

            addCriterion("ENABLE_FLAG <=", value, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagIn(List<Short> values) {

            addCriterion("ENABLE_FLAG in", values, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagNotIn(List<Short> values) {

            addCriterion("ENABLE_FLAG not in", values, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagBetween(Short value1, Short value2) {

            addCriterion("ENABLE_FLAG between", value1, value2, "enableFlag");
            return (Criteria) this;
        }

        public Criteria andEnableFlagNotBetween(Short value1, Short value2) {

            addCriterion("ENABLE_FLAG not between", value1, value2, "enableFlag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table ROUTE
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
     * This class was generated by MyBatis Generator. This class corresponds to the database table ROUTE
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 07 15:13:24 JST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
