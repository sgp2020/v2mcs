package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class MacsExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public MacsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
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
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
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

        public Criteria andMacsNameIsNull() {
            addCriterion("MACS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andMacsNameIsNotNull() {
            addCriterion("MACS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andMacsNameEqualTo(String value) {
            addCriterion("MACS_NAME =", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameNotEqualTo(String value) {
            addCriterion("MACS_NAME <>", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameGreaterThan(String value) {
            addCriterion("MACS_NAME >", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameGreaterThanOrEqualTo(String value) {
            addCriterion("MACS_NAME >=", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameLessThan(String value) {
            addCriterion("MACS_NAME <", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameLessThanOrEqualTo(String value) {
            addCriterion("MACS_NAME <=", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameLike(String value) {
            addCriterion("MACS_NAME like", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameNotLike(String value) {
            addCriterion("MACS_NAME not like", value, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameIn(List<String> values) {
            addCriterion("MACS_NAME in", values, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameNotIn(List<String> values) {
            addCriterion("MACS_NAME not in", values, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameBetween(String value1, String value2) {
            addCriterion("MACS_NAME between", value1, value2, "macsName");
            return (Criteria) this;
        }

        public Criteria andMacsNameNotBetween(String value1, String value2) {
            addCriterion("MACS_NAME not between", value1, value2, "macsName");
            return (Criteria) this;
        }

        public Criteria andSoftRevIsNull() {
            addCriterion("SOFT_REV is null");
            return (Criteria) this;
        }

        public Criteria andSoftRevIsNotNull() {
            addCriterion("SOFT_REV is not null");
            return (Criteria) this;
        }

        public Criteria andSoftRevEqualTo(String value) {
            addCriterion("SOFT_REV =", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevNotEqualTo(String value) {
            addCriterion("SOFT_REV <>", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevGreaterThan(String value) {
            addCriterion("SOFT_REV >", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevGreaterThanOrEqualTo(String value) {
            addCriterion("SOFT_REV >=", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevLessThan(String value) {
            addCriterion("SOFT_REV <", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevLessThanOrEqualTo(String value) {
            addCriterion("SOFT_REV <=", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevLike(String value) {
            addCriterion("SOFT_REV like", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevNotLike(String value) {
            addCriterion("SOFT_REV not like", value, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevIn(List<String> values) {
            addCriterion("SOFT_REV in", values, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevNotIn(List<String> values) {
            addCriterion("SOFT_REV not in", values, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevBetween(String value1, String value2) {
            addCriterion("SOFT_REV between", value1, value2, "softRev");
            return (Criteria) this;
        }

        public Criteria andSoftRevNotBetween(String value1, String value2) {
            addCriterion("SOFT_REV not between", value1, value2, "softRev");
            return (Criteria) this;
        }

        public Criteria andControlStateIsNull() {
            addCriterion("CONTROL_STATE is null");
            return (Criteria) this;
        }

        public Criteria andControlStateIsNotNull() {
            addCriterion("CONTROL_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andControlStateEqualTo(String value) {
            addCriterion("CONTROL_STATE =", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateNotEqualTo(String value) {
            addCriterion("CONTROL_STATE <>", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateGreaterThan(String value) {
            addCriterion("CONTROL_STATE >", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateGreaterThanOrEqualTo(String value) {
            addCriterion("CONTROL_STATE >=", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateLessThan(String value) {
            addCriterion("CONTROL_STATE <", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateLessThanOrEqualTo(String value) {
            addCriterion("CONTROL_STATE <=", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateLike(String value) {
            addCriterion("CONTROL_STATE like", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateNotLike(String value) {
            addCriterion("CONTROL_STATE not like", value, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateIn(List<String> values) {
            addCriterion("CONTROL_STATE in", values, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateNotIn(List<String> values) {
            addCriterion("CONTROL_STATE not in", values, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateBetween(String value1, String value2) {
            addCriterion("CONTROL_STATE between", value1, value2, "controlState");
            return (Criteria) this;
        }

        public Criteria andControlStateNotBetween(String value1, String value2) {
            addCriterion("CONTROL_STATE not between", value1, value2, "controlState");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgIsNull() {
            addCriterion("INHIBIT_FLG is null");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgIsNotNull() {
            addCriterion("INHIBIT_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgEqualTo(Short value) {
            addCriterion("INHIBIT_FLG =", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgNotEqualTo(Short value) {
            addCriterion("INHIBIT_FLG <>", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgGreaterThan(Short value) {
            addCriterion("INHIBIT_FLG >", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgGreaterThanOrEqualTo(Short value) {
            addCriterion("INHIBIT_FLG >=", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgLessThan(Short value) {
            addCriterion("INHIBIT_FLG <", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgLessThanOrEqualTo(Short value) {
            addCriterion("INHIBIT_FLG <=", value, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgIn(List<Short> values) {
            addCriterion("INHIBIT_FLG in", values, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgNotIn(List<Short> values) {
            addCriterion("INHIBIT_FLG not in", values, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgBetween(Short value1, Short value2) {
            addCriterion("INHIBIT_FLG between", value1, value2, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andInhibitFlgNotBetween(Short value1, Short value2) {
            addCriterion("INHIBIT_FLG not between", value1, value2, "inhibitFlg");
            return (Criteria) this;
        }

        public Criteria andCaptureModeIsNull() {
            addCriterion("CAPTURE_MODE is null");
            return (Criteria) this;
        }

        public Criteria andCaptureModeIsNotNull() {
            addCriterion("CAPTURE_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andCaptureModeEqualTo(Short value) {
            addCriterion("CAPTURE_MODE =", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeNotEqualTo(Short value) {
            addCriterion("CAPTURE_MODE <>", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeGreaterThan(Short value) {
            addCriterion("CAPTURE_MODE >", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeGreaterThanOrEqualTo(Short value) {
            addCriterion("CAPTURE_MODE >=", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeLessThan(Short value) {
            addCriterion("CAPTURE_MODE <", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeLessThanOrEqualTo(Short value) {
            addCriterion("CAPTURE_MODE <=", value, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeIn(List<Short> values) {
            addCriterion("CAPTURE_MODE in", values, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeNotIn(List<Short> values) {
            addCriterion("CAPTURE_MODE not in", values, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeBetween(Short value1, Short value2) {
            addCriterion("CAPTURE_MODE between", value1, value2, "captureMode");
            return (Criteria) this;
        }

        public Criteria andCaptureModeNotBetween(Short value1, Short value2) {
            addCriterion("CAPTURE_MODE not between", value1, value2, "captureMode");
            return (Criteria) this;
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

        public Criteria andRouteStateIsNull() {
            addCriterion("ROUTE_STATE is null");
            return (Criteria) this;
        }

        public Criteria andRouteStateIsNotNull() {
            addCriterion("ROUTE_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andRouteStateEqualTo(Short value) {
            addCriterion("ROUTE_STATE =", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateNotEqualTo(Short value) {
            addCriterion("ROUTE_STATE <>", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateGreaterThan(Short value) {
            addCriterion("ROUTE_STATE >", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateGreaterThanOrEqualTo(Short value) {
            addCriterion("ROUTE_STATE >=", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateLessThan(Short value) {
            addCriterion("ROUTE_STATE <", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateLessThanOrEqualTo(Short value) {
            addCriterion("ROUTE_STATE <=", value, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateIn(List<Short> values) {
            addCriterion("ROUTE_STATE in", values, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateNotIn(List<Short> values) {
            addCriterion("ROUTE_STATE not in", values, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateBetween(Short value1, Short value2) {
            addCriterion("ROUTE_STATE between", value1, value2, "routeState");
            return (Criteria) this;
        }

        public Criteria andRouteStateNotBetween(Short value1, Short value2) {
            addCriterion("ROUTE_STATE not between", value1, value2, "routeState");
            return (Criteria) this;
        }

        public Criteria andNodeNoIsNull() {
            addCriterion("NODE_NO is null");
            return (Criteria) this;
        }

        public Criteria andNodeNoIsNotNull() {
            addCriterion("NODE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andNodeNoEqualTo(Short value) {
            addCriterion("NODE_NO =", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoNotEqualTo(Short value) {
            addCriterion("NODE_NO <>", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoGreaterThan(Short value) {
            addCriterion("NODE_NO >", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoGreaterThanOrEqualTo(Short value) {
            addCriterion("NODE_NO >=", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoLessThan(Short value) {
            addCriterion("NODE_NO <", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoLessThanOrEqualTo(Short value) {
            addCriterion("NODE_NO <=", value, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoIn(List<Short> values) {
            addCriterion("NODE_NO in", values, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoNotIn(List<Short> values) {
            addCriterion("NODE_NO not in", values, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoBetween(Short value1, Short value2) {
            addCriterion("NODE_NO between", value1, value2, "nodeNo");
            return (Criteria) this;
        }

        public Criteria andNodeNoNotBetween(Short value1, Short value2) {
            addCriterion("NODE_NO not between", value1, value2, "nodeNo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MACS
     *
     * @mbggenerated do_not_delete_during_merge Fri Mar 27 19:52:43 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MACS
     *
     * @mbggenerated Fri Mar 27 19:52:43 CST 2020
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