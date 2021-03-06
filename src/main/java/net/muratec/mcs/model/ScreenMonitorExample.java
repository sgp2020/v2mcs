package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class ScreenMonitorExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public ScreenMonitorExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
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
     * This method corresponds to the database table SCREEN_MONITOR
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
     * This method corresponds to the database table SCREEN_MONITOR
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SCREEN_MONITOR
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
     * This class corresponds to the database table SCREEN_MONITOR
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

        public Criteria andDisplayIdIsNull() {
            addCriterion("DISPLAY_ID is null");
            return (Criteria) this;
        }

        public Criteria andDisplayIdIsNotNull() {
            addCriterion("DISPLAY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayIdEqualTo(Integer value) {
            addCriterion("DISPLAY_ID =", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdNotEqualTo(Integer value) {
            addCriterion("DISPLAY_ID <>", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdGreaterThan(Integer value) {
            addCriterion("DISPLAY_ID >", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("DISPLAY_ID >=", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdLessThan(Integer value) {
            addCriterion("DISPLAY_ID <", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdLessThanOrEqualTo(Integer value) {
            addCriterion("DISPLAY_ID <=", value, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdIn(List<Integer> values) {
            addCriterion("DISPLAY_ID in", values, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdNotIn(List<Integer> values) {
            addCriterion("DISPLAY_ID not in", values, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdBetween(Integer value1, Integer value2) {
            addCriterion("DISPLAY_ID between", value1, value2, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("DISPLAY_ID not between", value1, value2, "displayId");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberIsNull() {
            addCriterion("DISPLAY_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberIsNotNull() {
            addCriterion("DISPLAY_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberEqualTo(Short value) {
            addCriterion("DISPLAY_NUMBER =", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberNotEqualTo(Short value) {
            addCriterion("DISPLAY_NUMBER <>", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberGreaterThan(Short value) {
            addCriterion("DISPLAY_NUMBER >", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberGreaterThanOrEqualTo(Short value) {
            addCriterion("DISPLAY_NUMBER >=", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberLessThan(Short value) {
            addCriterion("DISPLAY_NUMBER <", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberLessThanOrEqualTo(Short value) {
            addCriterion("DISPLAY_NUMBER <=", value, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberIn(List<Short> values) {
            addCriterion("DISPLAY_NUMBER in", values, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberNotIn(List<Short> values) {
            addCriterion("DISPLAY_NUMBER not in", values, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberBetween(Short value1, Short value2) {
            addCriterion("DISPLAY_NUMBER between", value1, value2, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andDisplayNumberNotBetween(Short value1, Short value2) {
            addCriterion("DISPLAY_NUMBER not between", value1, value2, "displayNumber");
            return (Criteria) this;
        }

        public Criteria andColorIsNull() {
            addCriterion("COLOR is null");
            return (Criteria) this;
        }

        public Criteria andColorIsNotNull() {
            addCriterion("COLOR is not null");
            return (Criteria) this;
        }

        public Criteria andColorEqualTo(String value) {
            addCriterion("COLOR =", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotEqualTo(String value) {
            addCriterion("COLOR <>", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThan(String value) {
            addCriterion("COLOR >", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorGreaterThanOrEqualTo(String value) {
            addCriterion("COLOR >=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThan(String value) {
            addCriterion("COLOR <", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLessThanOrEqualTo(String value) {
            addCriterion("COLOR <=", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorLike(String value) {
            addCriterion("COLOR like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotLike(String value) {
            addCriterion("COLOR not like", value, "color");
            return (Criteria) this;
        }

        public Criteria andColorIn(List<String> values) {
            addCriterion("COLOR in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotIn(List<String> values) {
            addCriterion("COLOR not in", values, "color");
            return (Criteria) this;
        }

        public Criteria andColorBetween(String value1, String value2) {
            addCriterion("COLOR between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andColorNotBetween(String value1, String value2) {
            addCriterion("COLOR not between", value1, value2, "color");
            return (Criteria) this;
        }

        public Criteria andTextIsNull() {
            addCriterion("TEXT is null");
            return (Criteria) this;
        }

        public Criteria andTextIsNotNull() {
            addCriterion("TEXT is not null");
            return (Criteria) this;
        }

        public Criteria andTextEqualTo(String value) {
            addCriterion("TEXT =", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotEqualTo(String value) {
            addCriterion("TEXT <>", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThan(String value) {
            addCriterion("TEXT >", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextGreaterThanOrEqualTo(String value) {
            addCriterion("TEXT >=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThan(String value) {
            addCriterion("TEXT <", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLessThanOrEqualTo(String value) {
            addCriterion("TEXT <=", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextLike(String value) {
            addCriterion("TEXT like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotLike(String value) {
            addCriterion("TEXT not like", value, "text");
            return (Criteria) this;
        }

        public Criteria andTextIn(List<String> values) {
            addCriterion("TEXT in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotIn(List<String> values) {
            addCriterion("TEXT not in", values, "text");
            return (Criteria) this;
        }

        public Criteria andTextBetween(String value1, String value2) {
            addCriterion("TEXT between", value1, value2, "text");
            return (Criteria) this;
        }

        public Criteria andTextNotBetween(String value1, String value2) {
            addCriterion("TEXT not between", value1, value2, "text");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SCREEN_MONITOR
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
     * This class corresponds to the database table SCREEN_MONITOR
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