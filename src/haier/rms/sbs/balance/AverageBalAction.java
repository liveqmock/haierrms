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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanrui on 14-1-9.
 */
@ManagedBean
@ViewScoped
public class AverageBalAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String startDate;
    private String endDate;
    private List<AverageDailyBalBean> detlList = new ArrayList<AverageDailyBalBean>();
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
        this.startDate = new DateTime().minusYears(1).withDayOfYear(1).toString("yyyy-MM-dd");
        this.endDate = new DateTime().minusYears(1).monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        this.queryType = request.getQueryString();
        if (StringUtils.isEmpty(this.queryType)) {
            this.queryType = "C";
        }

        this.title = "企业日均余额统计 ";

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

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        paramMap.put("queryType", this.queryType);
        paramMap.put("corpType", this.corpType);
        paramMap.put("days", days);
        detlList = rmsJdbc.query(assembleSql(), paramMap, new BeanPropertyRowMapper<AverageBalAction.AverageDailyBalBean>(AverageBalAction.AverageDailyBalBean.class));

    }

    private String assembleSql() {
        String sql = "with actbal as\n" +
                " (select a.*, b.bankcd, b.category, c.currat\n" +
                "    from sbs_actbal a, mt_acttype b, sbs_actcxr c\n" +
                "   where a.actno = b.actno(+)\n" +
                "     and a.txndate = c.txndate\n" +
                "     and substr(a.actno, 16, 3) = c.curcde(+)\n" +
                "     and c.xrtcde = '8'\n" +
                "     and c.secccy = '001')\n" +
                "select t.txndate, sum(round(balamt * currat, 2)) as rmbBalTotal, round(sum(balamt * currat)/:days,2) as rmbbalAverage, :days as days, count(*) as accts\n" +
                "  from actbal t\n" +
                " where t.bankcd = '999'\n" +
                "   and t.txndate between :startDate and :endDate\n";

        if (this.queryType.equals("C")) {
            if (!StringUtils.isEmpty(this.corpType)) {
                sql += "   and t.category = :corpType\n";
            }
        } else {
            sql += "   and t.category = :queryType\n";
        }

        sql += " group by t.txndate\n" +
                " order by t.txndate";
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

    public List<AverageDailyBalBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<AverageDailyBalBean> detlList) {
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
