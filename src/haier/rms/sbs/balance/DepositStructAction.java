package haier.rms.sbs.balance;

import haier.repository.model.Ptenudetail;
import haier.service.common.PlatformService;
import haier.service.common.ToolsService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanrui on 14-12-9.
 * SBS 存款结构分析
 */
@ManagedBean
@RequestScoped
public class DepositStructAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String startDate;
    private String endDate;
    private List<BalStructVO> detlList = new ArrayList<BalStructVO>();
    private AverageDailyBalBean selectedRecord;
    private String queryType;
    private String title;
    private String corpType;
    private List<SelectItem> corpTypeList;

    @ManagedProperty(value = "#{rmsJdbc}")
    private NamedParameterJdbcTemplate rmsJdbc;
    @ManagedProperty(value = "#{toolsService}")
    private ToolsService toolsService;
    @ManagedProperty(value = "#{platformService}")
    private PlatformService platformService;


    //存款结构VO
    public static class BalStructVO {
        private String txnDate;
        private String itemName;
        private BigDecimal averageBal;
        private BigDecimal currdepBal; //活期存款余额
        private BigDecimal fixdepBal;  //定期存款余额
        private BigDecimal marginBal;  //保证金存款余额
        private BigDecimal currdepBal_HF; //财务公司活期存款余额
        private BigDecimal fixdepBal_HF;  //财务公司定期存款余额
        private BigDecimal marginBal_HF;  //财务公司保证金存款余额
        private BigDecimal currdepRatio;  //占比
        private BigDecimal fixdepRatio;  //占比
        private BigDecimal marginBalRatio;  //占比

        public String getTxnDate() {
            return txnDate;
        }

        public void setTxnDate(String txnDate) {
            this.txnDate = txnDate;
        }

        public BigDecimal getAverageBal() {
            return averageBal;
        }

        public void setAverageBal(BigDecimal averageBal) {
            this.averageBal = averageBal;
        }

        public BigDecimal getCurrdepBal() {
            return currdepBal;
        }

        public void setCurrdepBal(BigDecimal currdepBal) {
            this.currdepBal = currdepBal;
        }

        public BigDecimal getFixdepBal() {
            return fixdepBal;
        }

        public void setFixdepBal(BigDecimal fixdepBal) {
            this.fixdepBal = fixdepBal;
        }

        public BigDecimal getCurrdepBal_HF() {
            return currdepBal_HF;
        }

        public void setCurrdepBal_HF(BigDecimal currdepBal_HF) {
            this.currdepBal_HF = currdepBal_HF;
        }

        public BigDecimal getFixdepBal_HF() {
            return fixdepBal_HF;
        }

        public void setFixdepBal_HF(BigDecimal fixdepBal_HF) {
            this.fixdepBal_HF = fixdepBal_HF;
        }

        public BigDecimal getCurrdepRatio() {
            return currdepRatio;
        }

        public void setCurrdepRatio(BigDecimal currdepRatio) {
            this.currdepRatio = currdepRatio;
        }

        public BigDecimal getFixdepRatio() {
            return fixdepRatio;
        }

        public void setFixdepRatio(BigDecimal fixdepRatio) {
            this.fixdepRatio = fixdepRatio;
        }

        public BigDecimal getMarginBal() {
            return marginBal;
        }

        public void setMarginBal(BigDecimal marginBal) {
            this.marginBal = marginBal;
        }

        public BigDecimal getMarginBal_HF() {
            return marginBal_HF;
        }

        public void setMarginBal_HF(BigDecimal marginBal_HF) {
            this.marginBal_HF = marginBal_HF;
        }

        public BigDecimal getMarginBalRatio() {
            return marginBalRatio;
        }

        public void setMarginBalRatio(BigDecimal marginBalRatio) {
            this.marginBalRatio = marginBalRatio;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
    }

    public static class AverageDailyBalBean {
        private String txnDate;
        private BigDecimal rmbBalTotal;
        private BigDecimal rmbBalAverage;
        private int days;
        private int accts;

        public String getTxnDate() {
            return txnDate;
        }

        public void setTxnDate(String txnDate) {
            this.txnDate = txnDate;
        }

        public BigDecimal getRmbBalTotal() {
            return rmbBalTotal;
        }

        public void setRmbBalTotal(BigDecimal rmbBalTotal) {
            this.rmbBalTotal = rmbBalTotal;
        }

        public BigDecimal getRmbBalAverage() {
            return rmbBalAverage;
        }

        public void setRmbBalAverage(BigDecimal rmbBalAverage) {
            this.rmbBalAverage = rmbBalAverage;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getAccts() {
            return accts;
        }

        public void setAccts(int accts) {
            this.accts = accts;
        }
    }

    @PostConstruct
    public void init() {
        this.startDate = new DateTime().withDayOfYear(1).toString("yyyy-MM-dd");
        this.endDate = new DateTime().minusDays(1).toString("yyyy-MM-dd");

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        this.queryType = request.getQueryString();
        if (StringUtils.isEmpty(this.queryType)) {
            this.queryType = "C";
        }

        this.title = "单位存款结构分析 ";

        this.corpTypeList = selectCorpTypeMenus();
    }

    public List<SelectItem> selectCorpTypeMenus() {
        List<Ptenudetail> ptenudetails = platformService.selectEnuDetail("CORPTYPE");
        List<SelectItem> selectItems = new ArrayList<SelectItem>();
        SelectItem item = new SelectItem("", "全部");
        selectItems.add(item);
        for (Ptenudetail enu : ptenudetails) {
            item = new SelectItem(enu.getEnuitemvalue(), enu.getEnuitemlabel());
            selectItems.add(item);
        }
        return selectItems;
    }

    public void onQuery() {
        DateTime s = new DateTime(startDate);
        DateTime e = new DateTime(endDate);
        Period p = new Period(s, e, PeriodType.days());
        int days = p.getDays() + 1;

        BigDecimal allCurrDep = getBal("ALL", "1");
        BigDecimal allFixDep = getBal("ALL", "2");
        BigDecimal allMargin = getBal("ALL", "3");

        BalStructVO vo = getBalStructVO(days, "A股单位", "A", allCurrDep, allFixDep, allMargin);
        detlList.add(vo);
        vo = getBalStructVO(days, "H股单位", "H", allCurrDep, allFixDep, allMargin);
        detlList.add(vo);
    }

    private BalStructVO getBalStructVO(int days, String itemName, String corpType,
                                       BigDecimal allCurrDep, BigDecimal allFixDep, BigDecimal allMargin) {
        BalStructVO vo = new BalStructVO();
        vo.setItemName(itemName);
        vo.setTxnDate(endDate);
        vo.setAverageBal(getDayAverageBal(days, corpType));

        vo.setCurrdepBal(getBal(corpType, "1"));
        vo.setFixdepBal(getBal(corpType, "2"));
        vo.setMarginBal(getBal(corpType, "3"));
        vo.setCurrdepBal_HF(allCurrDep);
        vo.setFixdepBal_HF(allFixDep);
        vo.setMarginBal_HF(allMargin);

        MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
        vo.setCurrdepRatio((vo.getCurrdepBal().divide(vo.getCurrdepBal_HF(), mc)));
        vo.setFixdepRatio(vo.getFixdepBal().divide(vo.getFixdepBal_HF(), mc));
        vo.setMarginBalRatio(vo.getMarginBal().divide(vo.getMarginBal_HF(), mc));
        return vo;
    }

    private BigDecimal getDayAverageBal(int days, String queryType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        //paramMap.put("queryType", this.queryType);
        paramMap.put("queryType", queryType);
        paramMap.put("corpType", this.corpType);
        paramMap.put("days", days);
        List<AverageDailyBalBean> detlList = rmsJdbc.query(assembleSql_averagebal(queryType), paramMap, new BeanPropertyRowMapper<AverageDailyBalBean>(AverageDailyBalBean.class));

        //20140410 zr  重新处理日均余额
        BigDecimal product = new BigDecimal("0.00");
        int day = 0;
        for (AverageDailyBalBean averageDailyBalBean : detlList) {
            day++;
            product = product.add(averageDailyBalBean.getRmbBalTotal());
            BigDecimal average = product.divide(new BigDecimal(Integer.toString(day)), 2, BigDecimal.ROUND_HALF_UP);
            averageDailyBalBean.setRmbBalAverage(average);
            if (averageDailyBalBean.getTxnDate().equals(this.endDate)) {
                return average;
            }
        }
        return new BigDecimal("0");
    }

    //计算日均余额的基础信息
    private String assembleSql_averagebal(String queryType) {
        String sql = "with actbal as" +
                " (select a.*, b.bankcd, b.category, c.ratval" +
                "    from sbs_actbal a, mt_acttype b, sbs_actcxr c" +
                "   where a.actno = b.actno(+)" +
                "     and a.txndate = c.txndate" +
                "     and substr(a.actno, 16, 3) = c.curcde(+)" +
                "     and c.xrtcde = '8'" +
                "     and c.secccy = '001')" +
                "select t.txndate, " +
                " sum(round((case when substr(actno, 16, 3)='001' then balamt else balamt/100 end) * ratval, 2)) as rmbBalTotal, " +
                " 0 as rmbbalAverage, :days as days, count(*) as accts" +
                "  from actbal t" +
                " where (t.bankcd = '999' or (t.bankcd is null and t.acttype !='4'))" +
                "   and t.txndate between :startDate and :endDate";

        if (queryType.equals("C")) {
            if (!StringUtils.isEmpty(this.corpType)) {
                sql += "   and t.category = :corpType";
            }
        } else {
            sql += "   and t.category = :queryType";
        }

        sql += " group by t.txndate" +
                " order by t.txndate";

        logger.info("SQL1:" + sql);
        return sql;
    }

    //===========
    private BigDecimal getBal(String corpType, String queryType) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("txnDate", this.endDate);
        paramMap.put("queryType", queryType);
        paramMap.put("corpType", corpType);
        Map<String, Object> resultMap = rmsJdbc.queryForMap(assembleSql_bal(corpType), paramMap);
        return (BigDecimal) resultMap.get("bal");
    }


    private String assembleSql_bal(String corpType) {
        String sql = "select sum(rmbbal) as bal" +
                "  from (select t.*," +
                "               (case" +
                "                 when t.currcode = '001' then" +
                "                  t.homecurbal" +
                "                 else" +
                "                  round(t.homecurbal / 100 * t.roe, 2)" +
                "               end) as rmbbal" +
                "          from (select a.txndate," +
                "                       a.actno," +
                "                       a.actname," +
                "                       a.acttype," +
                "                       a.balamt as homecurbal," +
                "                       substr(a.actno, 16, 3) as currcode," +
                "                       (select ratval" +
                "                          from sbs_actcxr" +
                "                         where curcde = substr(a.actno, 16, 3)" +
                "                           and txndate = :txnDate" +
                "                           and xrtcde = '8'" +
                "                           and secccy = '001') as roe" +
                "                  from (select *" +
                "                          from sbs_actbal" +
                "                         where txndate = :txnDate";

        if (corpType.equals("ALL")) {
            sql += "                           and acttype = :queryType ";
        } else {
            sql += "                           and acttype = :queryType " +
                    "                           and actno in (select actno from mt_acttype where category = :corpType)";
        }

        sql += ") a) t)";

        logger.info("SQL2:" + sql);
        return sql;
    }

    //====
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<BalStructVO> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<BalStructVO> detlList) {
        this.detlList = detlList;
    }

    public NamedParameterJdbcTemplate getRmsJdbc() {
        return rmsJdbc;
    }

    public void setRmsJdbc(NamedParameterJdbcTemplate rmsJdbc) {
        this.rmsJdbc = rmsJdbc;
    }

    public AverageDailyBalBean getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(AverageDailyBalBean selectedRecord) {
        this.selectedRecord = selectedRecord;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorpType() {
        return corpType;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public List<SelectItem> getCorpTypeList() {
        return corpTypeList;
    }

    public void setCorpTypeList(List<SelectItem> corpTypeList) {
        this.corpTypeList = corpTypeList;
    }

    public ToolsService getToolsService() {
        return toolsService;
    }

    public void setToolsService(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }
}
