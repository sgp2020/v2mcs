﻿package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class RelationalPortExample {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public RelationalPortExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {

        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public String getOrderByClause() {

        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {

        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public boolean isDistinct() {

        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {

        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public void or(Criteria criteria) {

        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public Criteria or() {

        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
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
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {

        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table RELATIONAL_PORT
     * @mbg.generated
     */
    public void clear() {

        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table RELATIONAL_PORT
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

        public Criteria andPortIdIsNull() {

            addCriterion("PORT_ID is null");
            return (Criteria) this;
        }

        public Criteria andPortIdIsNotNull() {

            addCriterion("PORT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPortIdEqualTo(String value) {

            addCriterion("PORT_ID =", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdNotEqualTo(String value) {

            addCriterion("PORT_ID <>", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdGreaterThan(String value) {

            addCriterion("PORT_ID >", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdGreaterThanOrEqualTo(String value) {

            addCriterion("PORT_ID >=", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdLessThan(String value) {

            addCriterion("PORT_ID <", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdLessThanOrEqualTo(String value) {

            addCriterion("PORT_ID <=", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdLike(String value) {

            addCriterion("PORT_ID like", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdNotLike(String value) {

            addCriterion("PORT_ID not like", value, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdIn(List<String> values) {

            addCriterion("PORT_ID in", values, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdNotIn(List<String> values) {

            addCriterion("PORT_ID not in", values, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdBetween(String value1, String value2) {

            addCriterion("PORT_ID between", value1, value2, "portId");
            return (Criteria) this;
        }

        public Criteria andPortIdNotBetween(String value1, String value2) {

            addCriterion("PORT_ID not between", value1, value2, "portId");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {

            addCriterion("SEQ is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {

            addCriterion("SEQ is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Short value) {

            addCriterion("SEQ =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Short value) {

            addCriterion("SEQ <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Short value) {

            addCriterion("SEQ >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Short value) {

            addCriterion("SEQ >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Short value) {

            addCriterion("SEQ <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Short value) {

            addCriterion("SEQ <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Short> values) {

            addCriterion("SEQ in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Short> values) {

            addCriterion("SEQ not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Short value1, Short value2) {

            addCriterion("SEQ between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Short value1, Short value2) {

            addCriterion("SEQ not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdIsNull() {

            addCriterion("RELATIONAL_PORT_ID is null");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdIsNotNull() {

            addCriterion("RELATIONAL_PORT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdEqualTo(String value) {

            addCriterion("RELATIONAL_PORT_ID =", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdNotEqualTo(String value) {

            addCriterion("RELATIONAL_PORT_ID <>", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdGreaterThan(String value) {

            addCriterion("RELATIONAL_PORT_ID >", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdGreaterThanOrEqualTo(String value) {

            addCriterion("RELATIONAL_PORT_ID >=", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdLessThan(String value) {

            addCriterion("RELATIONAL_PORT_ID <", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdLessThanOrEqualTo(String value) {

            addCriterion("RELATIONAL_PORT_ID <=", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdLike(String value) {

            addCriterion("RELATIONAL_PORT_ID like", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdNotLike(String value) {

            addCriterion("RELATIONAL_PORT_ID not like", value, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdIn(List<String> values) {

            addCriterion("RELATIONAL_PORT_ID in", values, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdNotIn(List<String> values) {

            addCriterion("RELATIONAL_PORT_ID not in", values, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdBetween(String value1, String value2) {

            addCriterion("RELATIONAL_PORT_ID between", value1, value2, "relationalPortId");
            return (Criteria) this;
        }

        public Criteria andRelationalPortIdNotBetween(String value1, String value2) {

            addCriterion("RELATIONAL_PORT_ID not between", value1, value2, "relationalPortId");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table RELATIONAL_PORT
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
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table RELATIONAL_PORT
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}
