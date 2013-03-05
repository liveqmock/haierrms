package haier.repository.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SbsActcxrExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public SbsActcxrExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
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
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
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

        public Criteria andTxndateIsNull() {
            addCriterion("TXNDATE is null");
            return (Criteria) this;
        }

        public Criteria andTxndateIsNotNull() {
            addCriterion("TXNDATE is not null");
            return (Criteria) this;
        }

        public Criteria andTxndateEqualTo(String value) {
            addCriterion("TXNDATE =", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateNotEqualTo(String value) {
            addCriterion("TXNDATE <>", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateGreaterThan(String value) {
            addCriterion("TXNDATE >", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateGreaterThanOrEqualTo(String value) {
            addCriterion("TXNDATE >=", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateLessThan(String value) {
            addCriterion("TXNDATE <", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateLessThanOrEqualTo(String value) {
            addCriterion("TXNDATE <=", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateLike(String value) {
            addCriterion("TXNDATE like", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateNotLike(String value) {
            addCriterion("TXNDATE not like", value, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateIn(List<String> values) {
            addCriterion("TXNDATE in", values, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateNotIn(List<String> values) {
            addCriterion("TXNDATE not in", values, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateBetween(String value1, String value2) {
            addCriterion("TXNDATE between", value1, value2, "txndate");
            return (Criteria) this;
        }

        public Criteria andTxndateNotBetween(String value1, String value2) {
            addCriterion("TXNDATE not between", value1, value2, "txndate");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNull() {
            addCriterion("CURCDE is null");
            return (Criteria) this;
        }

        public Criteria andCurcdeIsNotNull() {
            addCriterion("CURCDE is not null");
            return (Criteria) this;
        }

        public Criteria andCurcdeEqualTo(String value) {
            addCriterion("CURCDE =", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotEqualTo(String value) {
            addCriterion("CURCDE <>", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThan(String value) {
            addCriterion("CURCDE >", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeGreaterThanOrEqualTo(String value) {
            addCriterion("CURCDE >=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThan(String value) {
            addCriterion("CURCDE <", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLessThanOrEqualTo(String value) {
            addCriterion("CURCDE <=", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeLike(String value) {
            addCriterion("CURCDE like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotLike(String value) {
            addCriterion("CURCDE not like", value, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeIn(List<String> values) {
            addCriterion("CURCDE in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotIn(List<String> values) {
            addCriterion("CURCDE not in", values, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeBetween(String value1, String value2) {
            addCriterion("CURCDE between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andCurcdeNotBetween(String value1, String value2) {
            addCriterion("CURCDE not between", value1, value2, "curcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeIsNull() {
            addCriterion("XRTCDE is null");
            return (Criteria) this;
        }

        public Criteria andXrtcdeIsNotNull() {
            addCriterion("XRTCDE is not null");
            return (Criteria) this;
        }

        public Criteria andXrtcdeEqualTo(String value) {
            addCriterion("XRTCDE =", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeNotEqualTo(String value) {
            addCriterion("XRTCDE <>", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeGreaterThan(String value) {
            addCriterion("XRTCDE >", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeGreaterThanOrEqualTo(String value) {
            addCriterion("XRTCDE >=", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeLessThan(String value) {
            addCriterion("XRTCDE <", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeLessThanOrEqualTo(String value) {
            addCriterion("XRTCDE <=", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeLike(String value) {
            addCriterion("XRTCDE like", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeNotLike(String value) {
            addCriterion("XRTCDE not like", value, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeIn(List<String> values) {
            addCriterion("XRTCDE in", values, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeNotIn(List<String> values) {
            addCriterion("XRTCDE not in", values, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeBetween(String value1, String value2) {
            addCriterion("XRTCDE between", value1, value2, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andXrtcdeNotBetween(String value1, String value2) {
            addCriterion("XRTCDE not between", value1, value2, "xrtcde");
            return (Criteria) this;
        }

        public Criteria andSecccyIsNull() {
            addCriterion("SECCCY is null");
            return (Criteria) this;
        }

        public Criteria andSecccyIsNotNull() {
            addCriterion("SECCCY is not null");
            return (Criteria) this;
        }

        public Criteria andSecccyEqualTo(String value) {
            addCriterion("SECCCY =", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyNotEqualTo(String value) {
            addCriterion("SECCCY <>", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyGreaterThan(String value) {
            addCriterion("SECCCY >", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyGreaterThanOrEqualTo(String value) {
            addCriterion("SECCCY >=", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyLessThan(String value) {
            addCriterion("SECCCY <", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyLessThanOrEqualTo(String value) {
            addCriterion("SECCCY <=", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyLike(String value) {
            addCriterion("SECCCY like", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyNotLike(String value) {
            addCriterion("SECCCY not like", value, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyIn(List<String> values) {
            addCriterion("SECCCY in", values, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyNotIn(List<String> values) {
            addCriterion("SECCCY not in", values, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyBetween(String value1, String value2) {
            addCriterion("SECCCY between", value1, value2, "secccy");
            return (Criteria) this;
        }

        public Criteria andSecccyNotBetween(String value1, String value2) {
            addCriterion("SECCCY not between", value1, value2, "secccy");
            return (Criteria) this;
        }

        public Criteria andCurratIsNull() {
            addCriterion("CURRAT is null");
            return (Criteria) this;
        }

        public Criteria andCurratIsNotNull() {
            addCriterion("CURRAT is not null");
            return (Criteria) this;
        }

        public Criteria andCurratEqualTo(BigDecimal value) {
            addCriterion("CURRAT =", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratNotEqualTo(BigDecimal value) {
            addCriterion("CURRAT <>", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratGreaterThan(BigDecimal value) {
            addCriterion("CURRAT >", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CURRAT >=", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratLessThan(BigDecimal value) {
            addCriterion("CURRAT <", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CURRAT <=", value, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratIn(List<BigDecimal> values) {
            addCriterion("CURRAT in", values, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratNotIn(List<BigDecimal> values) {
            addCriterion("CURRAT not in", values, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURRAT between", value1, value2, "currat");
            return (Criteria) this;
        }

        public Criteria andCurratNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CURRAT not between", value1, value2, "currat");
            return (Criteria) this;
        }

        public Criteria andRatvalIsNull() {
            addCriterion("RATVAL is null");
            return (Criteria) this;
        }

        public Criteria andRatvalIsNotNull() {
            addCriterion("RATVAL is not null");
            return (Criteria) this;
        }

        public Criteria andRatvalEqualTo(BigDecimal value) {
            addCriterion("RATVAL =", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalNotEqualTo(BigDecimal value) {
            addCriterion("RATVAL <>", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalGreaterThan(BigDecimal value) {
            addCriterion("RATVAL >", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("RATVAL >=", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalLessThan(BigDecimal value) {
            addCriterion("RATVAL <", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalLessThanOrEqualTo(BigDecimal value) {
            addCriterion("RATVAL <=", value, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalIn(List<BigDecimal> values) {
            addCriterion("RATVAL in", values, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalNotIn(List<BigDecimal> values) {
            addCriterion("RATVAL not in", values, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RATVAL between", value1, value2, "ratval");
            return (Criteria) this;
        }

        public Criteria andRatvalNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("RATVAL not between", value1, value2, "ratval");
            return (Criteria) this;
        }

        public Criteria andOperidIsNull() {
            addCriterion("OPERID is null");
            return (Criteria) this;
        }

        public Criteria andOperidIsNotNull() {
            addCriterion("OPERID is not null");
            return (Criteria) this;
        }

        public Criteria andOperidEqualTo(String value) {
            addCriterion("OPERID =", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotEqualTo(String value) {
            addCriterion("OPERID <>", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThan(String value) {
            addCriterion("OPERID >", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidGreaterThanOrEqualTo(String value) {
            addCriterion("OPERID >=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThan(String value) {
            addCriterion("OPERID <", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLessThanOrEqualTo(String value) {
            addCriterion("OPERID <=", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidLike(String value) {
            addCriterion("OPERID like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotLike(String value) {
            addCriterion("OPERID not like", value, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidIn(List<String> values) {
            addCriterion("OPERID in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotIn(List<String> values) {
            addCriterion("OPERID not in", values, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidBetween(String value1, String value2) {
            addCriterion("OPERID between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperidNotBetween(String value1, String value2) {
            addCriterion("OPERID not between", value1, value2, "operid");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNull() {
            addCriterion("OPERDATE is null");
            return (Criteria) this;
        }

        public Criteria andOperdateIsNotNull() {
            addCriterion("OPERDATE is not null");
            return (Criteria) this;
        }

        public Criteria andOperdateEqualTo(Date value) {
            addCriterion("OPERDATE =", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotEqualTo(Date value) {
            addCriterion("OPERDATE <>", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThan(Date value) {
            addCriterion("OPERDATE >", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateGreaterThanOrEqualTo(Date value) {
            addCriterion("OPERDATE >=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThan(Date value) {
            addCriterion("OPERDATE <", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateLessThanOrEqualTo(Date value) {
            addCriterion("OPERDATE <=", value, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateIn(List<Date> values) {
            addCriterion("OPERDATE in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotIn(List<Date> values) {
            addCriterion("OPERDATE not in", values, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateBetween(Date value1, Date value2) {
            addCriterion("OPERDATE between", value1, value2, "operdate");
            return (Criteria) this;
        }

        public Criteria andOperdateNotBetween(Date value1, Date value2) {
            addCriterion("OPERDATE not between", value1, value2, "operdate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated do_not_delete_during_merge Sat Apr 09 20:06:45 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table SBS_ACTCXR
     *
     * @mbggenerated Sat Apr 09 20:06:45 CST 2011
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