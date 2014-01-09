package haier.rms.sbs.balance;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
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

    @ManagedProperty(value = "#{rmsJdbc}")
    private NamedParameterJdbcTemplate rmsJdbc;


    public static class AverageDailyBalBean {
        private String txnDate;
        private String rmbBal;
        private String accts;

        public String getTxnDate() {
            return txnDate;
        }

        public void setTxnDate(String txnDate) {
            this.txnDate = txnDate;
        }

        public String getRmbBal() {
            return rmbBal;
        }

        public void setRmbBal(String rmbBal) {
            this.rmbBal = rmbBal;
        }

        public String getAccts() {
            return accts;
        }

        public void setAccts(String accts) {
            this.accts = accts;
        }
    }

    @PostConstruct
    public void init() {
//        this.startDate = new DateTime().minusYears(1).monthOfYear().withMinimumValue().toString("yyyy-MM-dd");
        this.startDate = new DateTime().minusYears(1).withDayOfYear(1).toString("yyyy-MM-dd");
        this.endDate = new DateTime().minusYears(1).monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        this.queryType = request.getQueryString();
        if (StringUtils.isEmpty(this.queryType)) {
            this.queryType = "C";
        }

        if (this.queryType.equals("A")) {
            this.title = "A股企业日均余额统计 ";
        }
        if (this.queryType.equals("H")) {
            this.title = "H股企业日均余额统计 ";
        }
        if (this.queryType.equals("C")) {
            this.title = "全部企业日均余额统计 ";
        }

    }

    public void onQuery() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("startDate", this.startDate);
        paramMap.put("endDate", this.endDate);
        paramMap.put("queryType", this.queryType);
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
                "select t.txndate, sum(round(balamt * currat, 2)) as rmbbal, count(*) as accts\n" +
                "  from actbal t\n" +
                " where t.bankcd = '999'\n" +
                "   and t.txndate between :startDate and :endDate\n";

        if (!this.queryType.equals("C")) {
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
}
