package haier.rms.sbs.balance;

import haier.repository.model.Ptenudetail;
import haier.service.common.PlatformService;
import haier.service.common.ToolsService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存款结构分析
 * Created by zhanrui on 14-4-9.
 */
@ManagedBean
@ViewScoped
public class DepositStructAnalysisAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String startDate;
    private String endDate;
    private List<ReportBean> detlList = new ArrayList<ReportBean>();
    private List<RecordSetBean> recordSet = new ArrayList<RecordSetBean>();
    private ReportBean selectedRecord;
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

    //对应数据库查询结果BEAN
    public static class RecordSetBean{
        private String txndate;
        private String category;   //分类 A H
        private String acttype;    //账户类别 1：活期  2：定期
        private BigDecimal rmbbal;

        public String getTxndate() {
            return txndate;
        }

        public void setTxndate(String txndate) {
            this.txndate = txndate;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getActtype() {
            return acttype;
        }

        public void setActtype(String acttype) {
            this.acttype = acttype;
        }

        public BigDecimal getRmbbal() {
            return rmbbal;
        }

        public void setRmbbal(BigDecimal rmbbal) {
            this.rmbbal = rmbbal;
        }
    }

    //对应页面展示BEAN
    public static class ReportBean {
        private String corpClass;      //类别
        private BigDecimal rmbBalAverage;
        private BigDecimal rmbCdpBal;  //活期存款余额
        private BigDecimal rmbTdpBal;  //定期存款余额
        private BigDecimal rmbMarginBal;  //保证金存款余额
        private BigDecimal rmbBalTotal;  //存款小计
        private BigDecimal rmbCdpRatio;  //活期存款占比
        private BigDecimal rmbTdpRatio;  //定期存款占比
        private BigDecimal rmbMarginRatio;  //定期存款占比
        private BigDecimal rmbRatioTotal;  //存款占比小计

        public String getCorpClass() {
            return corpClass;
        }

        public void setCorpClass(String corpClass) {
            this.corpClass = corpClass;
        }

        public BigDecimal getRmbBalAverage() {
            return rmbBalAverage;
        }

        public void setRmbBalAverage(BigDecimal rmbBalAverage) {
            this.rmbBalAverage = rmbBalAverage;
        }

        public BigDecimal getRmbCdpBal() {
            return rmbCdpBal;
        }

        public void setRmbCdpBal(BigDecimal rmbCdpBal) {
            this.rmbCdpBal = rmbCdpBal;
        }

        public BigDecimal getRmbTdpBal() {
            return rmbTdpBal;
        }

        public void setRmbTdpBal(BigDecimal rmbTdpBal) {
            this.rmbTdpBal = rmbTdpBal;
        }

        public BigDecimal getRmbBalTotal() {
            return rmbBalTotal;
        }

        public void setRmbBalTotal(BigDecimal rmbBalTotal) {
            this.rmbBalTotal = rmbBalTotal;
        }

        public BigDecimal getRmbCdpRatio() {
            return rmbCdpRatio;
        }

        public void setRmbCdpRatio(BigDecimal rmbCdpRatio) {
            this.rmbCdpRatio = rmbCdpRatio;
        }

        public BigDecimal getRmbTdpRatio() {
            return rmbTdpRatio;
        }

        public void setRmbTdpRatio(BigDecimal rmbTdpRatio) {
            this.rmbTdpRatio = rmbTdpRatio;
        }

        public BigDecimal getRmbRatioTotal() {
            return rmbRatioTotal;
        }

        public void setRmbRatioTotal(BigDecimal rmbRatioTotal) {
            this.rmbRatioTotal = rmbRatioTotal;
        }

        public BigDecimal getRmbMarginBal() {
            return rmbMarginBal;
        }

        public void setRmbMarginBal(BigDecimal rmbMarginBal) {
            this.rmbMarginBal = rmbMarginBal;
        }

        public BigDecimal getRmbMarginRatio() {
            return rmbMarginRatio;
        }

        public void setRmbMarginRatio(BigDecimal rmbMarginRatio) {
            this.rmbMarginRatio = rmbMarginRatio;
        }
    }

    @PostConstruct
    public void init() {
        this.startDate = new DateTime().minusYears(1).withDayOfYear(1).toString("yyyy-MM-dd");
        this.endDate = new DateTime().minusYears(1).monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");

        this.title = "财务公司企业存款结构分析";
 //       this.corpTypeList = selectCorpTypeMenus();
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
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        recordSet = rmsJdbc.query(assembleSql(), paramMap, new BeanPropertyRowMapper<RecordSetBean>(RecordSetBean.class));

        BigDecimal product = new BigDecimal("0.00");
        int day = 0;
        for (ReportBean reportBean : detlList) {
            day++;
            product = product.add(reportBean.getRmbBalTotal());
            BigDecimal average = product.divide(new BigDecimal(Integer.toString(day)),2, BigDecimal.ROUND_HALF_UP);
            reportBean.setRmbBalAverage(average);
        }

    }

    private BigDecimal statisticsData(String corpClass){
        BigDecimal product = new BigDecimal("0.00");
        int day = 0;
        String curDate = "";
        BigDecimal average = new BigDecimal("0");
        for (RecordSetBean record  : recordSet) {
            if (corpClass.equals(record.getCategory())) {
                if (!curDate.equals(record.getTxndate())) {
                    day++;
                    curDate = record.getTxndate();
                } else {
                    product = product.add(record.getRmbbal());
                    average = product.divide(new BigDecimal(Integer.toString(day)), 2, BigDecimal.ROUND_HALF_UP);
                }
            }
        }

        //TODO 20140514
        return average;
    }

    private String assembleSql() {
        String sql = "with actbal as\n" +
                " (select a.*, b.bankcd, b.category, c.ratval\n" +
                "    from sbs_actbal a, mt_acttype b, sbs_actcxr c\n" +
                "   where a.actno = b.actno(+)\n" +
                "     and a.acttype != '3'\n" +
                "     and a.txndate = c.txndate\n" +
                "     and substr(a.actno, 16, 3) = c.curcde(+)\n" +
                "     and c.xrtcde = '8'\n" +
                "     and c.secccy = '001')\n" +
                "select t.txndate,\n" +
                "       t.category,\n" +
                "       t.acttype,\n" +
                "       sum(round((case\n" +
                "                   when substr(actno, 16, 3) = '001' then\n" +
                "                    balamt\n" +
                "                   else\n" +
                "                    balamt / 100\n" +
                "                 end) * ratval,\n" +
                "                 2)) as rmbbal\n" +
                "  from actbal t\n" +
                " where (t.bankcd = '999' or (t.bankcd is null and t.acttype != '4'))\n" +
                "   and t.txndate between :startDate and :endDate\n" +
                " group by t.txndate, t.category, t.acttype\n" +
                " order by t.txndate, t.category, t.acttype\n";

        logger.info("SQL:" + sql);
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

    public List<ReportBean> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<ReportBean> detlList) {
        this.detlList = detlList;
    }

    public NamedParameterJdbcTemplate getRmsJdbc() {
        return rmsJdbc;
    }

    public void setRmsJdbc(NamedParameterJdbcTemplate rmsJdbc) {
        this.rmsJdbc = rmsJdbc;
    }

    public ReportBean getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(ReportBean selectedRecord) {
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
