package haier.repository.model;

import java.util.ArrayList;
import java.util.List;

public class MtActtypeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public MtActtypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
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
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
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

        public Criteria andActnoIsNull() {
            addCriterion("ACTNO is null");
            return (Criteria) this;
        }

        public Criteria andActnoIsNotNull() {
            addCriterion("ACTNO is not null");
            return (Criteria) this;
        }

        public Criteria andActnoEqualTo(String value) {
            addCriterion("ACTNO =", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotEqualTo(String value) {
            addCriterion("ACTNO <>", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoGreaterThan(String value) {
            addCriterion("ACTNO >", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoGreaterThanOrEqualTo(String value) {
            addCriterion("ACTNO >=", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLessThan(String value) {
            addCriterion("ACTNO <", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLessThanOrEqualTo(String value) {
            addCriterion("ACTNO <=", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoLike(String value) {
            addCriterion("ACTNO like", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotLike(String value) {
            addCriterion("ACTNO not like", value, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoIn(List<String> values) {
            addCriterion("ACTNO in", values, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotIn(List<String> values) {
            addCriterion("ACTNO not in", values, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoBetween(String value1, String value2) {
            addCriterion("ACTNO between", value1, value2, "actno");
            return (Criteria) this;
        }

        public Criteria andActnoNotBetween(String value1, String value2) {
            addCriterion("ACTNO not between", value1, value2, "actno");
            return (Criteria) this;
        }

        public Criteria andBankcdIsNull() {
            addCriterion("BANKCD is null");
            return (Criteria) this;
        }

        public Criteria andBankcdIsNotNull() {
            addCriterion("BANKCD is not null");
            return (Criteria) this;
        }

        public Criteria andBankcdEqualTo(String value) {
            addCriterion("BANKCD =", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdNotEqualTo(String value) {
            addCriterion("BANKCD <>", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdGreaterThan(String value) {
            addCriterion("BANKCD >", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdGreaterThanOrEqualTo(String value) {
            addCriterion("BANKCD >=", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdLessThan(String value) {
            addCriterion("BANKCD <", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdLessThanOrEqualTo(String value) {
            addCriterion("BANKCD <=", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdLike(String value) {
            addCriterion("BANKCD like", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdNotLike(String value) {
            addCriterion("BANKCD not like", value, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdIn(List<String> values) {
            addCriterion("BANKCD in", values, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdNotIn(List<String> values) {
            addCriterion("BANKCD not in", values, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdBetween(String value1, String value2) {
            addCriterion("BANKCD between", value1, value2, "bankcd");
            return (Criteria) this;
        }

        public Criteria andBankcdNotBetween(String value1, String value2) {
            addCriterion("BANKCD not between", value1, value2, "bankcd");
            return (Criteria) this;
        }

        public Criteria andActnameIsNull() {
            addCriterion("ACTNAME is null");
            return (Criteria) this;
        }

        public Criteria andActnameIsNotNull() {
            addCriterion("ACTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andActnameEqualTo(String value) {
            addCriterion("ACTNAME =", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotEqualTo(String value) {
            addCriterion("ACTNAME <>", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameGreaterThan(String value) {
            addCriterion("ACTNAME >", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameGreaterThanOrEqualTo(String value) {
            addCriterion("ACTNAME >=", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLessThan(String value) {
            addCriterion("ACTNAME <", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLessThanOrEqualTo(String value) {
            addCriterion("ACTNAME <=", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameLike(String value) {
            addCriterion("ACTNAME like", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotLike(String value) {
            addCriterion("ACTNAME not like", value, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameIn(List<String> values) {
            addCriterion("ACTNAME in", values, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotIn(List<String> values) {
            addCriterion("ACTNAME not in", values, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameBetween(String value1, String value2) {
            addCriterion("ACTNAME between", value1, value2, "actname");
            return (Criteria) this;
        }

        public Criteria andActnameNotBetween(String value1, String value2) {
            addCriterion("ACTNAME not between", value1, value2, "actname");
            return (Criteria) this;
        }

        public Criteria andGroupcdIsNull() {
            addCriterion("GROUPCD is null");
            return (Criteria) this;
        }

        public Criteria andGroupcdIsNotNull() {
            addCriterion("GROUPCD is not null");
            return (Criteria) this;
        }

        public Criteria andGroupcdEqualTo(String value) {
            addCriterion("GROUPCD =", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdNotEqualTo(String value) {
            addCriterion("GROUPCD <>", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdGreaterThan(String value) {
            addCriterion("GROUPCD >", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdGreaterThanOrEqualTo(String value) {
            addCriterion("GROUPCD >=", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdLessThan(String value) {
            addCriterion("GROUPCD <", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdLessThanOrEqualTo(String value) {
            addCriterion("GROUPCD <=", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdLike(String value) {
            addCriterion("GROUPCD like", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdNotLike(String value) {
            addCriterion("GROUPCD not like", value, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdIn(List<String> values) {
            addCriterion("GROUPCD in", values, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdNotIn(List<String> values) {
            addCriterion("GROUPCD not in", values, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdBetween(String value1, String value2) {
            addCriterion("GROUPCD between", value1, value2, "groupcd");
            return (Criteria) this;
        }

        public Criteria andGroupcdNotBetween(String value1, String value2) {
            addCriterion("GROUPCD not between", value1, value2, "groupcd");
            return (Criteria) this;
        }

        public Criteria andStockcdIsNull() {
            addCriterion("STOCKCD is null");
            return (Criteria) this;
        }

        public Criteria andStockcdIsNotNull() {
            addCriterion("STOCKCD is not null");
            return (Criteria) this;
        }

        public Criteria andStockcdEqualTo(String value) {
            addCriterion("STOCKCD =", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdNotEqualTo(String value) {
            addCriterion("STOCKCD <>", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdGreaterThan(String value) {
            addCriterion("STOCKCD >", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdGreaterThanOrEqualTo(String value) {
            addCriterion("STOCKCD >=", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdLessThan(String value) {
            addCriterion("STOCKCD <", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdLessThanOrEqualTo(String value) {
            addCriterion("STOCKCD <=", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdLike(String value) {
            addCriterion("STOCKCD like", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdNotLike(String value) {
            addCriterion("STOCKCD not like", value, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdIn(List<String> values) {
            addCriterion("STOCKCD in", values, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdNotIn(List<String> values) {
            addCriterion("STOCKCD not in", values, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdBetween(String value1, String value2) {
            addCriterion("STOCKCD between", value1, value2, "stockcd");
            return (Criteria) this;
        }

        public Criteria andStockcdNotBetween(String value1, String value2) {
            addCriterion("STOCKCD not between", value1, value2, "stockcd");
            return (Criteria) this;
        }

        public Criteria andLandcdIsNull() {
            addCriterion("LANDCD is null");
            return (Criteria) this;
        }

        public Criteria andLandcdIsNotNull() {
            addCriterion("LANDCD is not null");
            return (Criteria) this;
        }

        public Criteria andLandcdEqualTo(String value) {
            addCriterion("LANDCD =", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdNotEqualTo(String value) {
            addCriterion("LANDCD <>", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdGreaterThan(String value) {
            addCriterion("LANDCD >", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdGreaterThanOrEqualTo(String value) {
            addCriterion("LANDCD >=", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdLessThan(String value) {
            addCriterion("LANDCD <", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdLessThanOrEqualTo(String value) {
            addCriterion("LANDCD <=", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdLike(String value) {
            addCriterion("LANDCD like", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdNotLike(String value) {
            addCriterion("LANDCD not like", value, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdIn(List<String> values) {
            addCriterion("LANDCD in", values, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdNotIn(List<String> values) {
            addCriterion("LANDCD not in", values, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdBetween(String value1, String value2) {
            addCriterion("LANDCD between", value1, value2, "landcd");
            return (Criteria) this;
        }

        public Criteria andLandcdNotBetween(String value1, String value2) {
            addCriterion("LANDCD not between", value1, value2, "landcd");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("CATEGORY =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("CATEGORY <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("CATEGORY >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("CATEGORY <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("CATEGORY like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("CATEGORY not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("CATEGORY in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("CATEGORY not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("CATEGORY between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("CATEGORY not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andOpeningbankIsNull() {
            addCriterion("OPENINGBANK is null");
            return (Criteria) this;
        }

        public Criteria andOpeningbankIsNotNull() {
            addCriterion("OPENINGBANK is not null");
            return (Criteria) this;
        }

        public Criteria andOpeningbankEqualTo(String value) {
            addCriterion("OPENINGBANK =", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankNotEqualTo(String value) {
            addCriterion("OPENINGBANK <>", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankGreaterThan(String value) {
            addCriterion("OPENINGBANK >", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankGreaterThanOrEqualTo(String value) {
            addCriterion("OPENINGBANK >=", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankLessThan(String value) {
            addCriterion("OPENINGBANK <", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankLessThanOrEqualTo(String value) {
            addCriterion("OPENINGBANK <=", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankLike(String value) {
            addCriterion("OPENINGBANK like", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankNotLike(String value) {
            addCriterion("OPENINGBANK not like", value, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankIn(List<String> values) {
            addCriterion("OPENINGBANK in", values, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankNotIn(List<String> values) {
            addCriterion("OPENINGBANK not in", values, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankBetween(String value1, String value2) {
            addCriterion("OPENINGBANK between", value1, value2, "openingbank");
            return (Criteria) this;
        }

        public Criteria andOpeningbankNotBetween(String value1, String value2) {
            addCriterion("OPENINGBANK not between", value1, value2, "openingbank");
            return (Criteria) this;
        }

        public Criteria andActattrIsNull() {
            addCriterion("ACTATTR is null");
            return (Criteria) this;
        }

        public Criteria andActattrIsNotNull() {
            addCriterion("ACTATTR is not null");
            return (Criteria) this;
        }

        public Criteria andActattrEqualTo(String value) {
            addCriterion("ACTATTR =", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrNotEqualTo(String value) {
            addCriterion("ACTATTR <>", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrGreaterThan(String value) {
            addCriterion("ACTATTR >", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrGreaterThanOrEqualTo(String value) {
            addCriterion("ACTATTR >=", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrLessThan(String value) {
            addCriterion("ACTATTR <", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrLessThanOrEqualTo(String value) {
            addCriterion("ACTATTR <=", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrLike(String value) {
            addCriterion("ACTATTR like", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrNotLike(String value) {
            addCriterion("ACTATTR not like", value, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrIn(List<String> values) {
            addCriterion("ACTATTR in", values, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrNotIn(List<String> values) {
            addCriterion("ACTATTR not in", values, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrBetween(String value1, String value2) {
            addCriterion("ACTATTR between", value1, value2, "actattr");
            return (Criteria) this;
        }

        public Criteria andActattrNotBetween(String value1, String value2) {
            addCriterion("ACTATTR not between", value1, value2, "actattr");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 20 16:50:10 CST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MT_ACTTYPE
     *
     * @mbggenerated Mon Jun 20 16:50:10 CST 2011
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