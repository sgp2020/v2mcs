package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class ComConfExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public ComConfExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
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
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
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

        public Criteria andLlcSidIsNull() {
            addCriterion("LLC_SID is null");
            return (Criteria) this;
        }

        public Criteria andLlcSidIsNotNull() {
            addCriterion("LLC_SID is not null");
            return (Criteria) this;
        }

        public Criteria andLlcSidEqualTo(Integer value) {
            addCriterion("LLC_SID =", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidNotEqualTo(Integer value) {
            addCriterion("LLC_SID <>", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidGreaterThan(Integer value) {
            addCriterion("LLC_SID >", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidGreaterThanOrEqualTo(Integer value) {
            addCriterion("LLC_SID >=", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidLessThan(Integer value) {
            addCriterion("LLC_SID <", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidLessThanOrEqualTo(Integer value) {
            addCriterion("LLC_SID <=", value, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidIn(List<Integer> values) {
            addCriterion("LLC_SID in", values, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidNotIn(List<Integer> values) {
            addCriterion("LLC_SID not in", values, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidBetween(Integer value1, Integer value2) {
            addCriterion("LLC_SID between", value1, value2, "llcSid");
            return (Criteria) this;
        }

        public Criteria andLlcSidNotBetween(Integer value1, Integer value2) {
            addCriterion("LLC_SID not between", value1, value2, "llcSid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andConnectModeIsNull() {
            addCriterion("CONNECT_MODE is null");
            return (Criteria) this;
        }

        public Criteria andConnectModeIsNotNull() {
            addCriterion("CONNECT_MODE is not null");
            return (Criteria) this;
        }

        public Criteria andConnectModeEqualTo(Short value) {
            addCriterion("CONNECT_MODE =", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeNotEqualTo(Short value) {
            addCriterion("CONNECT_MODE <>", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeGreaterThan(Short value) {
            addCriterion("CONNECT_MODE >", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeGreaterThanOrEqualTo(Short value) {
            addCriterion("CONNECT_MODE >=", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeLessThan(Short value) {
            addCriterion("CONNECT_MODE <", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeLessThanOrEqualTo(Short value) {
            addCriterion("CONNECT_MODE <=", value, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeIn(List<Short> values) {
            addCriterion("CONNECT_MODE in", values, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeNotIn(List<Short> values) {
            addCriterion("CONNECT_MODE not in", values, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeBetween(Short value1, Short value2) {
            addCriterion("CONNECT_MODE between", value1, value2, "connectMode");
            return (Criteria) this;
        }

        public Criteria andConnectModeNotBetween(Short value1, Short value2) {
            addCriterion("CONNECT_MODE not between", value1, value2, "connectMode");
            return (Criteria) this;
        }

        public Criteria andSessionIdIsNull() {
            addCriterion("SESSION_ID is null");
            return (Criteria) this;
        }

        public Criteria andSessionIdIsNotNull() {
            addCriterion("SESSION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSessionIdEqualTo(Integer value) {
            addCriterion("SESSION_ID =", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotEqualTo(Integer value) {
            addCriterion("SESSION_ID <>", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdGreaterThan(Integer value) {
            addCriterion("SESSION_ID >", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("SESSION_ID >=", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdLessThan(Integer value) {
            addCriterion("SESSION_ID <", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdLessThanOrEqualTo(Integer value) {
            addCriterion("SESSION_ID <=", value, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdIn(List<Integer> values) {
            addCriterion("SESSION_ID in", values, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotIn(List<Integer> values) {
            addCriterion("SESSION_ID not in", values, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdBetween(Integer value1, Integer value2) {
            addCriterion("SESSION_ID between", value1, value2, "sessionId");
            return (Criteria) this;
        }

        public Criteria andSessionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("SESSION_ID not between", value1, value2, "sessionId");
            return (Criteria) this;
        }

        public Criteria andHostNameIsNull() {
            addCriterion("HOST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andHostNameIsNotNull() {
            addCriterion("HOST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andHostNameEqualTo(String value) {
            addCriterion("HOST_NAME =", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotEqualTo(String value) {
            addCriterion("HOST_NAME <>", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameGreaterThan(String value) {
            addCriterion("HOST_NAME >", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameGreaterThanOrEqualTo(String value) {
            addCriterion("HOST_NAME >=", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLessThan(String value) {
            addCriterion("HOST_NAME <", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLessThanOrEqualTo(String value) {
            addCriterion("HOST_NAME <=", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameLike(String value) {
            addCriterion("HOST_NAME like", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotLike(String value) {
            addCriterion("HOST_NAME not like", value, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameIn(List<String> values) {
            addCriterion("HOST_NAME in", values, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotIn(List<String> values) {
            addCriterion("HOST_NAME not in", values, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameBetween(String value1, String value2) {
            addCriterion("HOST_NAME between", value1, value2, "hostName");
            return (Criteria) this;
        }

        public Criteria andHostNameNotBetween(String value1, String value2) {
            addCriterion("HOST_NAME not between", value1, value2, "hostName");
            return (Criteria) this;
        }

        public Criteria andPortNoIsNull() {
            addCriterion("PORT_NO is null");
            return (Criteria) this;
        }

        public Criteria andPortNoIsNotNull() {
            addCriterion("PORT_NO is not null");
            return (Criteria) this;
        }

        public Criteria andPortNoEqualTo(Integer value) {
            addCriterion("PORT_NO =", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotEqualTo(Integer value) {
            addCriterion("PORT_NO <>", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoGreaterThan(Integer value) {
            addCriterion("PORT_NO >", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoGreaterThanOrEqualTo(Integer value) {
            addCriterion("PORT_NO >=", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoLessThan(Integer value) {
            addCriterion("PORT_NO <", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoLessThanOrEqualTo(Integer value) {
            addCriterion("PORT_NO <=", value, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoIn(List<Integer> values) {
            addCriterion("PORT_NO in", values, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotIn(List<Integer> values) {
            addCriterion("PORT_NO not in", values, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoBetween(Integer value1, Integer value2) {
            addCriterion("PORT_NO between", value1, value2, "portNo");
            return (Criteria) this;
        }

        public Criteria andPortNoNotBetween(Integer value1, Integer value2) {
            addCriterion("PORT_NO not between", value1, value2, "portNo");
            return (Criteria) this;
        }

        public Criteria andLinkTestIsNull() {
            addCriterion("LINK_TEST is null");
            return (Criteria) this;
        }

        public Criteria andLinkTestIsNotNull() {
            addCriterion("LINK_TEST is not null");
            return (Criteria) this;
        }

        public Criteria andLinkTestEqualTo(Integer value) {
            addCriterion("LINK_TEST =", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestNotEqualTo(Integer value) {
            addCriterion("LINK_TEST <>", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestGreaterThan(Integer value) {
            addCriterion("LINK_TEST >", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestGreaterThanOrEqualTo(Integer value) {
            addCriterion("LINK_TEST >=", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestLessThan(Integer value) {
            addCriterion("LINK_TEST <", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestLessThanOrEqualTo(Integer value) {
            addCriterion("LINK_TEST <=", value, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestIn(List<Integer> values) {
            addCriterion("LINK_TEST in", values, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestNotIn(List<Integer> values) {
            addCriterion("LINK_TEST not in", values, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestBetween(Integer value1, Integer value2) {
            addCriterion("LINK_TEST between", value1, value2, "linkTest");
            return (Criteria) this;
        }

        public Criteria andLinkTestNotBetween(Integer value1, Integer value2) {
            addCriterion("LINK_TEST not between", value1, value2, "linkTest");
            return (Criteria) this;
        }

        public Criteria andT3IsNull() {
            addCriterion("T3 is null");
            return (Criteria) this;
        }

        public Criteria andT3IsNotNull() {
            addCriterion("T3 is not null");
            return (Criteria) this;
        }

        public Criteria andT3EqualTo(Integer value) {
            addCriterion("T3 =", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3NotEqualTo(Integer value) {
            addCriterion("T3 <>", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3GreaterThan(Integer value) {
            addCriterion("T3 >", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3GreaterThanOrEqualTo(Integer value) {
            addCriterion("T3 >=", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3LessThan(Integer value) {
            addCriterion("T3 <", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3LessThanOrEqualTo(Integer value) {
            addCriterion("T3 <=", value, "t3");
            return (Criteria) this;
        }

        public Criteria andT3In(List<Integer> values) {
            addCriterion("T3 in", values, "t3");
            return (Criteria) this;
        }

        public Criteria andT3NotIn(List<Integer> values) {
            addCriterion("T3 not in", values, "t3");
            return (Criteria) this;
        }

        public Criteria andT3Between(Integer value1, Integer value2) {
            addCriterion("T3 between", value1, value2, "t3");
            return (Criteria) this;
        }

        public Criteria andT3NotBetween(Integer value1, Integer value2) {
            addCriterion("T3 not between", value1, value2, "t3");
            return (Criteria) this;
        }

        public Criteria andT5IsNull() {
            addCriterion("T5 is null");
            return (Criteria) this;
        }

        public Criteria andT5IsNotNull() {
            addCriterion("T5 is not null");
            return (Criteria) this;
        }

        public Criteria andT5EqualTo(Integer value) {
            addCriterion("T5 =", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5NotEqualTo(Integer value) {
            addCriterion("T5 <>", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5GreaterThan(Integer value) {
            addCriterion("T5 >", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5GreaterThanOrEqualTo(Integer value) {
            addCriterion("T5 >=", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5LessThan(Integer value) {
            addCriterion("T5 <", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5LessThanOrEqualTo(Integer value) {
            addCriterion("T5 <=", value, "t5");
            return (Criteria) this;
        }

        public Criteria andT5In(List<Integer> values) {
            addCriterion("T5 in", values, "t5");
            return (Criteria) this;
        }

        public Criteria andT5NotIn(List<Integer> values) {
            addCriterion("T5 not in", values, "t5");
            return (Criteria) this;
        }

        public Criteria andT5Between(Integer value1, Integer value2) {
            addCriterion("T5 between", value1, value2, "t5");
            return (Criteria) this;
        }

        public Criteria andT5NotBetween(Integer value1, Integer value2) {
            addCriterion("T5 not between", value1, value2, "t5");
            return (Criteria) this;
        }

        public Criteria andT6IsNull() {
            addCriterion("T6 is null");
            return (Criteria) this;
        }

        public Criteria andT6IsNotNull() {
            addCriterion("T6 is not null");
            return (Criteria) this;
        }

        public Criteria andT6EqualTo(Integer value) {
            addCriterion("T6 =", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6NotEqualTo(Integer value) {
            addCriterion("T6 <>", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6GreaterThan(Integer value) {
            addCriterion("T6 >", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6GreaterThanOrEqualTo(Integer value) {
            addCriterion("T6 >=", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6LessThan(Integer value) {
            addCriterion("T6 <", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6LessThanOrEqualTo(Integer value) {
            addCriterion("T6 <=", value, "t6");
            return (Criteria) this;
        }

        public Criteria andT6In(List<Integer> values) {
            addCriterion("T6 in", values, "t6");
            return (Criteria) this;
        }

        public Criteria andT6NotIn(List<Integer> values) {
            addCriterion("T6 not in", values, "t6");
            return (Criteria) this;
        }

        public Criteria andT6Between(Integer value1, Integer value2) {
            addCriterion("T6 between", value1, value2, "t6");
            return (Criteria) this;
        }

        public Criteria andT6NotBetween(Integer value1, Integer value2) {
            addCriterion("T6 not between", value1, value2, "t6");
            return (Criteria) this;
        }

        public Criteria andT7IsNull() {
            addCriterion("T7 is null");
            return (Criteria) this;
        }

        public Criteria andT7IsNotNull() {
            addCriterion("T7 is not null");
            return (Criteria) this;
        }

        public Criteria andT7EqualTo(Integer value) {
            addCriterion("T7 =", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7NotEqualTo(Integer value) {
            addCriterion("T7 <>", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7GreaterThan(Integer value) {
            addCriterion("T7 >", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7GreaterThanOrEqualTo(Integer value) {
            addCriterion("T7 >=", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7LessThan(Integer value) {
            addCriterion("T7 <", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7LessThanOrEqualTo(Integer value) {
            addCriterion("T7 <=", value, "t7");
            return (Criteria) this;
        }

        public Criteria andT7In(List<Integer> values) {
            addCriterion("T7 in", values, "t7");
            return (Criteria) this;
        }

        public Criteria andT7NotIn(List<Integer> values) {
            addCriterion("T7 not in", values, "t7");
            return (Criteria) this;
        }

        public Criteria andT7Between(Integer value1, Integer value2) {
            addCriterion("T7 between", value1, value2, "t7");
            return (Criteria) this;
        }

        public Criteria andT7NotBetween(Integer value1, Integer value2) {
            addCriterion("T7 not between", value1, value2, "t7");
            return (Criteria) this;
        }

        public Criteria andT8IsNull() {
            addCriterion("T8 is null");
            return (Criteria) this;
        }

        public Criteria andT8IsNotNull() {
            addCriterion("T8 is not null");
            return (Criteria) this;
        }

        public Criteria andT8EqualTo(Integer value) {
            addCriterion("T8 =", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8NotEqualTo(Integer value) {
            addCriterion("T8 <>", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8GreaterThan(Integer value) {
            addCriterion("T8 >", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8GreaterThanOrEqualTo(Integer value) {
            addCriterion("T8 >=", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8LessThan(Integer value) {
            addCriterion("T8 <", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8LessThanOrEqualTo(Integer value) {
            addCriterion("T8 <=", value, "t8");
            return (Criteria) this;
        }

        public Criteria andT8In(List<Integer> values) {
            addCriterion("T8 in", values, "t8");
            return (Criteria) this;
        }

        public Criteria andT8NotIn(List<Integer> values) {
            addCriterion("T8 not in", values, "t8");
            return (Criteria) this;
        }

        public Criteria andT8Between(Integer value1, Integer value2) {
            addCriterion("T8 between", value1, value2, "t8");
            return (Criteria) this;
        }

        public Criteria andT8NotBetween(Integer value1, Integer value2) {
            addCriterion("T8 not between", value1, value2, "t8");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table COM_CONF
     *
     * @mbggenerated do_not_delete_during_merge Fri May 08 15:28:42 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table COM_CONF
     *
     * @mbggenerated Fri May 08 15:28:42 CST 2020
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