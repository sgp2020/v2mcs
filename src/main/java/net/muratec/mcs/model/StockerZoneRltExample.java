package net.muratec.mcs.model;

import java.util.ArrayList;
import java.util.List;

public class StockerZoneRltExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    protected String orderByClause;
    // STD APL 2020.03.12 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 
    protected int tscId;

    public int getTscId() {
		return tscId;
	}

	public void setTscId(int tscId) {
		this.tscId = tscId;
	}
	// END APL 2020.03.12 董 天津村研  MCSV4　GUI開発  Ver2.0 Rev.000 

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public StockerZoneRltExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
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
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
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

        public Criteria andTscIdIsNull() {
            addCriterion("TSC_ID is null");
            return (Criteria) this;
        }

        public Criteria andTscIdIsNotNull() {
            addCriterion("TSC_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTscIdEqualTo(Integer value) {
            addCriterion("TSC_ID =", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotEqualTo(Integer value) {
            addCriterion("TSC_ID <>", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdGreaterThan(Integer value) {
            addCriterion("TSC_ID >", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("TSC_ID >=", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdLessThan(Integer value) {
            addCriterion("TSC_ID <", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdLessThanOrEqualTo(Integer value) {
            addCriterion("TSC_ID <=", value, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdIn(List<Integer> values) {
            addCriterion("TSC_ID in", values, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotIn(List<Integer> values) {
            addCriterion("TSC_ID not in", values, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdBetween(Integer value1, Integer value2) {
            addCriterion("TSC_ID between", value1, value2, "tscId");
            return (Criteria) this;
        }

        public Criteria andTscIdNotBetween(Integer value1, Integer value2) {
            addCriterion("TSC_ID not between", value1, value2, "tscId");
            return (Criteria) this;
        }

        public Criteria andZoneIdIsNull() {
            addCriterion("ZONE_ID is null");
            return (Criteria) this;
        }

        public Criteria andZoneIdIsNotNull() {
            addCriterion("ZONE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andZoneIdEqualTo(String value) {
            addCriterion("ZONE_ID =", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdNotEqualTo(String value) {
            addCriterion("ZONE_ID <>", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdGreaterThan(String value) {
            addCriterion("ZONE_ID >", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdGreaterThanOrEqualTo(String value) {
            addCriterion("ZONE_ID >=", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdLessThan(String value) {
            addCriterion("ZONE_ID <", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdLessThanOrEqualTo(String value) {
            addCriterion("ZONE_ID <=", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdLike(String value) {
            addCriterion("ZONE_ID like", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdNotLike(String value) {
            addCriterion("ZONE_ID not like", value, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdIn(List<String> values) {
            addCriterion("ZONE_ID in", values, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdNotIn(List<String> values) {
            addCriterion("ZONE_ID not in", values, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdBetween(String value1, String value2) {
            addCriterion("ZONE_ID between", value1, value2, "zoneId");
            return (Criteria) this;
        }

        public Criteria andZoneIdNotBetween(String value1, String value2) {
            addCriterion("ZONE_ID not between", value1, value2, "zoneId");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesIsNull() {
            addCriterion("TOTAL_SHELVES is null");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesIsNotNull() {
            addCriterion("TOTAL_SHELVES is not null");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesEqualTo(Integer value) {
            addCriterion("TOTAL_SHELVES =", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesNotEqualTo(Integer value) {
            addCriterion("TOTAL_SHELVES <>", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesGreaterThan(Integer value) {
            addCriterion("TOTAL_SHELVES >", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesGreaterThanOrEqualTo(Integer value) {
            addCriterion("TOTAL_SHELVES >=", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesLessThan(Integer value) {
            addCriterion("TOTAL_SHELVES <", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesLessThanOrEqualTo(Integer value) {
            addCriterion("TOTAL_SHELVES <=", value, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesIn(List<Integer> values) {
            addCriterion("TOTAL_SHELVES in", values, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesNotIn(List<Integer> values) {
            addCriterion("TOTAL_SHELVES not in", values, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesBetween(Integer value1, Integer value2) {
            addCriterion("TOTAL_SHELVES between", value1, value2, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andTotalShelvesNotBetween(Integer value1, Integer value2) {
            addCriterion("TOTAL_SHELVES not between", value1, value2, "totalShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesIsNull() {
            addCriterion("EMPTY_SHELVES is null");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesIsNotNull() {
            addCriterion("EMPTY_SHELVES is not null");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesEqualTo(Integer value) {
            addCriterion("EMPTY_SHELVES =", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesNotEqualTo(Integer value) {
            addCriterion("EMPTY_SHELVES <>", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesGreaterThan(Integer value) {
            addCriterion("EMPTY_SHELVES >", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesGreaterThanOrEqualTo(Integer value) {
            addCriterion("EMPTY_SHELVES >=", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesLessThan(Integer value) {
            addCriterion("EMPTY_SHELVES <", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesLessThanOrEqualTo(Integer value) {
            addCriterion("EMPTY_SHELVES <=", value, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesIn(List<Integer> values) {
            addCriterion("EMPTY_SHELVES in", values, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesNotIn(List<Integer> values) {
            addCriterion("EMPTY_SHELVES not in", values, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesBetween(Integer value1, Integer value2) {
            addCriterion("EMPTY_SHELVES between", value1, value2, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andEmptyShelvesNotBetween(Integer value1, Integer value2) {
            addCriterion("EMPTY_SHELVES not between", value1, value2, "emptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesIsNull() {
            addCriterion("L_EMPTY_SHELVES is null");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesIsNotNull() {
            addCriterion("L_EMPTY_SHELVES is not null");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesEqualTo(Integer value) {
            addCriterion("L_EMPTY_SHELVES =", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesNotEqualTo(Integer value) {
            addCriterion("L_EMPTY_SHELVES <>", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesGreaterThan(Integer value) {
            addCriterion("L_EMPTY_SHELVES >", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesGreaterThanOrEqualTo(Integer value) {
            addCriterion("L_EMPTY_SHELVES >=", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesLessThan(Integer value) {
            addCriterion("L_EMPTY_SHELVES <", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesLessThanOrEqualTo(Integer value) {
            addCriterion("L_EMPTY_SHELVES <=", value, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesIn(List<Integer> values) {
            addCriterion("L_EMPTY_SHELVES in", values, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesNotIn(List<Integer> values) {
            addCriterion("L_EMPTY_SHELVES not in", values, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesBetween(Integer value1, Integer value2) {
            addCriterion("L_EMPTY_SHELVES between", value1, value2, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andLEmptyShelvesNotBetween(Integer value1, Integer value2) {
            addCriterion("L_EMPTY_SHELVES not between", value1, value2, "lEmptyShelves");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkIsNull() {
            addCriterion("HIGH_WATER_MARK is null");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkIsNotNull() {
            addCriterion("HIGH_WATER_MARK is not null");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkEqualTo(Integer value) {
            addCriterion("HIGH_WATER_MARK =", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkNotEqualTo(Integer value) {
            addCriterion("HIGH_WATER_MARK <>", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkGreaterThan(Integer value) {
            addCriterion("HIGH_WATER_MARK >", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkGreaterThanOrEqualTo(Integer value) {
            addCriterion("HIGH_WATER_MARK >=", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkLessThan(Integer value) {
            addCriterion("HIGH_WATER_MARK <", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkLessThanOrEqualTo(Integer value) {
            addCriterion("HIGH_WATER_MARK <=", value, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkIn(List<Integer> values) {
            addCriterion("HIGH_WATER_MARK in", values, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkNotIn(List<Integer> values) {
            addCriterion("HIGH_WATER_MARK not in", values, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkBetween(Integer value1, Integer value2) {
            addCriterion("HIGH_WATER_MARK between", value1, value2, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andHighWaterMarkNotBetween(Integer value1, Integer value2) {
            addCriterion("HIGH_WATER_MARK not between", value1, value2, "highWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkIsNull() {
            addCriterion("LOW_WATER_MARK is null");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkIsNotNull() {
            addCriterion("LOW_WATER_MARK is not null");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkEqualTo(Integer value) {
            addCriterion("LOW_WATER_MARK =", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkNotEqualTo(Integer value) {
            addCriterion("LOW_WATER_MARK <>", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkGreaterThan(Integer value) {
            addCriterion("LOW_WATER_MARK >", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkGreaterThanOrEqualTo(Integer value) {
            addCriterion("LOW_WATER_MARK >=", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkLessThan(Integer value) {
            addCriterion("LOW_WATER_MARK <", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkLessThanOrEqualTo(Integer value) {
            addCriterion("LOW_WATER_MARK <=", value, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkIn(List<Integer> values) {
            addCriterion("LOW_WATER_MARK in", values, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkNotIn(List<Integer> values) {
            addCriterion("LOW_WATER_MARK not in", values, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkBetween(Integer value1, Integer value2) {
            addCriterion("LOW_WATER_MARK between", value1, value2, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andLowWaterMarkNotBetween(Integer value1, Integer value2) {
            addCriterion("LOW_WATER_MARK not between", value1, value2, "lowWaterMark");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelIsNull() {
            addCriterion("HWM_HYSTERESIS_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelIsNotNull() {
            addCriterion("HWM_HYSTERESIS_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL =", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelNotEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL <>", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelGreaterThan(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL >", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL >=", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelLessThan(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL <", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelLessThanOrEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_LEVEL <=", value, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelIn(List<Short> values) {
            addCriterion("HWM_HYSTERESIS_LEVEL in", values, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelNotIn(List<Short> values) {
            addCriterion("HWM_HYSTERESIS_LEVEL not in", values, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelBetween(Short value1, Short value2) {
            addCriterion("HWM_HYSTERESIS_LEVEL between", value1, value2, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisLevelNotBetween(Short value1, Short value2) {
            addCriterion("HWM_HYSTERESIS_LEVEL not between", value1, value2, "hwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelIsNull() {
            addCriterion("LWM_HYSTERESIS_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelIsNotNull() {
            addCriterion("LWM_HYSTERESIS_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL =", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelNotEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL <>", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelGreaterThan(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL >", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelGreaterThanOrEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL >=", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelLessThan(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL <", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelLessThanOrEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_LEVEL <=", value, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelIn(List<Short> values) {
            addCriterion("LWM_HYSTERESIS_LEVEL in", values, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelNotIn(List<Short> values) {
            addCriterion("LWM_HYSTERESIS_LEVEL not in", values, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelBetween(Short value1, Short value2) {
            addCriterion("LWM_HYSTERESIS_LEVEL between", value1, value2, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisLevelNotBetween(Short value1, Short value2) {
            addCriterion("LWM_HYSTERESIS_LEVEL not between", value1, value2, "lwmHysteresisLevel");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgIsNull() {
            addCriterion("HWM_HYSTERESIS_FLG is null");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgIsNotNull() {
            addCriterion("HWM_HYSTERESIS_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG =", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgNotEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG <>", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgGreaterThan(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG >", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgGreaterThanOrEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG >=", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgLessThan(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG <", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgLessThanOrEqualTo(Short value) {
            addCriterion("HWM_HYSTERESIS_FLG <=", value, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgIn(List<Short> values) {
            addCriterion("HWM_HYSTERESIS_FLG in", values, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgNotIn(List<Short> values) {
            addCriterion("HWM_HYSTERESIS_FLG not in", values, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgBetween(Short value1, Short value2) {
            addCriterion("HWM_HYSTERESIS_FLG between", value1, value2, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andHwmHysteresisFlgNotBetween(Short value1, Short value2) {
            addCriterion("HWM_HYSTERESIS_FLG not between", value1, value2, "hwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgIsNull() {
            addCriterion("LWM_HYSTERESIS_FLG is null");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgIsNotNull() {
            addCriterion("LWM_HYSTERESIS_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG =", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgNotEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG <>", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgGreaterThan(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG >", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgGreaterThanOrEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG >=", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgLessThan(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG <", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgLessThanOrEqualTo(Short value) {
            addCriterion("LWM_HYSTERESIS_FLG <=", value, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgIn(List<Short> values) {
            addCriterion("LWM_HYSTERESIS_FLG in", values, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgNotIn(List<Short> values) {
            addCriterion("LWM_HYSTERESIS_FLG not in", values, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgBetween(Short value1, Short value2) {
            addCriterion("LWM_HYSTERESIS_FLG between", value1, value2, "lwmHysteresisFlg");
            return (Criteria) this;
        }

        public Criteria andLwmHysteresisFlgNotBetween(Short value1, Short value2) {
            addCriterion("LWM_HYSTERESIS_FLG not between", value1, value2, "lwmHysteresisFlg");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated do_not_delete_during_merge Mon Mar 09 13:26:43 CST 2020
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table STOCKER_ZONE_RLT
     *
     * @mbggenerated Mon Mar 09 13:26:43 CST 2020
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