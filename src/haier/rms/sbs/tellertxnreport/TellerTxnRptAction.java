package haier.rms.sbs.tellertxnreport;

import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.T8821.T8821SOFDataDetail;
import haier.service.rms.DepService;
import haier.view.JxlsManager;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.security.OperatorManager;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 柜员交易量统计报表.
 * User: zhanrui
 * Date: 2012-05-28
 */

@ManagedBean
@ViewScoped
public class TellerTxnRptAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(TellerTxnRptAction.class);
    private static final long serialVersionUID = -6478572032885828880L;

    private List<T8821SOFDataDetail> detlList = new ArrayList<T8821SOFDataDetail>();
    private T8821SOFDataDetail detlRecord = new T8821SOFDataDetail();

    private String startdate;
    private String enddate;
    private  String tellerId;
    private boolean leaderFlag;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @ManagedProperty(value = "#{depService}")
    private DepService depService;

    private DecimalFormat df = new DecimalFormat("0.00");

    //==============================
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate=dt.toString("yyyy-MM-dd");
        this.enddate=dt.toString("yyyy-MM-dd");
        OperatorManager om =  (OperatorManager)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute(SystemAttributeNames.USER_INFO_NAME);
        this.tellerId = String.format("%04d",om.getOperator().getFillint12());
        String issuper = om.getOperator().getIssuper();
        if (issuper == null || "0".equals(issuper)) {
            this.tellerId = String.format("%04d",om.getOperator().getFillint12());
            this.leaderFlag = false;
        }else{
            this.tellerId = "9999";
            this.leaderFlag = true;
        }
    }

    public String onExport() throws InvocationTargetException, IllegalAccessException {
        List<SOFDataDetail> toaList =  depService.processTellerTxnRpt(this.tellerId, this.startdate, this.enddate);
        if (toaList.size() == 0) {
            MessageUtil.addWarn("未查询到记录。");
            return null;
        }
        printRpt(toaList);
        return null;
    }

    private void printRpt(List<SOFDataDetail> records) {
        DateTime dateTime = new DateTime(this.startdate);
        String strDate8 = dateTime.toString("yyyyMMdd");
        String fileName = "柜员交易统计表" + strDate8 + ".xls";

        Map beans = new HashMap();
        beans.put("rptDate", "日期:" + this.startdate + " 至" + this.enddate);
        beans.put("records", records);
        JxlsManager jxlsManager = new JxlsManager();
        jxlsManager.export(fileName, "dep/tellerTxnRptModel.xls", beans);
    }


    private double transAmt(String txnamt) {
        double doubleAmt = 0.00;
        if (!StringUtils.isEmpty(txnamt)) {
            String strAmt = txnamt.trim();
            if (strAmt.indexOf("-") == 0) {
                doubleAmt = -Double.valueOf(strAmt.substring(1).trim().replace(",", ""));
            } else {
                doubleAmt = Double.valueOf(strAmt.replace(",", ""));
            }
        }
        return doubleAmt;
    }

    public List<T8821SOFDataDetail> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<T8821SOFDataDetail> detlList) {
        this.detlList = detlList;
    }

    public T8821SOFDataDetail getDetlRecord() {
        return detlRecord;
    }

    public void setDetlRecord(T8821SOFDataDetail detlRecord) {
        this.detlRecord = detlRecord;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getTellerId() {
        return tellerId;
    }

    public void setTellerId(String tellerId) {
        this.tellerId = tellerId;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        TellerTxnRptAction.sdf = sdf;
    }

    public DepService getDepService() {
        return depService;
    }

    public void setDepService(DepService depService) {
        this.depService = depService;
    }

    public DecimalFormat getDf() {
        return df;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }

    public boolean isLeaderFlag() {
        return leaderFlag;
    }

    public void setLeaderFlag(boolean leaderFlag) {
        this.leaderFlag = leaderFlag;
    }
}
