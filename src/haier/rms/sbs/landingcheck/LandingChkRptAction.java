package haier.rms.sbs.landingcheck;

import haier.activemq.service.sbs.core.SOFDataDetail;
import haier.activemq.service.sbs.txn.Tn116.Tn116SOFDataDetail;
import haier.service.rms.DepService;
import haier.view.JxlsManager;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 关联交易限额月控报表.
 * User: zhanrui
 * Date: 11-1-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class LandingChkRptAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(LandingChkRptAction.class);
    private static final long serialVersionUID = -6478572032885828880L;

    private List<Tn116SOFDataDetail> detlList = new ArrayList<Tn116SOFDataDetail>();
    private Tn116SOFDataDetail detlRecord = new Tn116SOFDataDetail();

    private String startdate;
    private String startamt;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @ManagedProperty(value = "#{depService}")
    private DepService depService;

    private DecimalFormat df = new DecimalFormat("0.00");

    //==============================
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate=dt.toString("yyyy-MM-dd");
        startamt = "5000000.00";
    }

    public String onExport() throws InvocationTargetException, IllegalAccessException {
        List<SOFDataDetail> toaList =  depService.processLandingChkTxn(this.startdate);
        List<LandingChkVO> records = new ArrayList<LandingChkVO>();
        if (toaList.size() == 0) {
            MessageUtil.addWarn("未查询到落地审核记录。");
            return null;
        }
        double startamt = Double.parseDouble(this.startamt);
        for (SOFDataDetail sofDataDetail : toaList) {
            double doubleAmt = transAmt(((Tn116SOFDataDetail)sofDataDetail).getTxnamt());
            String ctlrsn = ((Tn116SOFDataDetail) sofDataDetail).getCtlrsn().trim();
            //过滤
            if (StringUtils.isEmpty(ctlrsn)) {
                continue;
            }
            if (ctlrsn.startsWith("大额")) {
                if (doubleAmt < startamt) {
                    continue;
                }
            } else if (!ctlrsn.startsWith("同户名")) {
                continue;
            }

            ((Tn116SOFDataDetail)sofDataDetail).setRetnam(((Tn116SOFDataDetail) sofDataDetail).getRetnam().trim());
            ((Tn116SOFDataDetail)sofDataDetail).setBennam(((Tn116SOFDataDetail) sofDataDetail).getBennam().trim());
            ((Tn116SOFDataDetail)sofDataDetail).setBenbnk(((Tn116SOFDataDetail) sofDataDetail).getBenbnk().trim());
            ((Tn116SOFDataDetail)sofDataDetail).setTxnamt(df.format(doubleAmt));
            ((Tn116SOFDataDetail)sofDataDetail).setRetaux(((Tn116SOFDataDetail) sofDataDetail).getRetaux().trim());
            ((Tn116SOFDataDetail)sofDataDetail).setCtlrsn(ctlrsn);
            ((Tn116SOFDataDetail)sofDataDetail).setTermnm(((Tn116SOFDataDetail) sofDataDetail).getTermnm().trim());
            LandingChkVO vo = new LandingChkVO();
            BeanUtils.copyProperties(vo, (Tn116SOFDataDetail)sofDataDetail);
            vo.setV_txnamt(new BigDecimal(df.format(doubleAmt)));
            records.add(vo);
        }

        sortList(records);
        printRpt(records);
        return null;
    }

    private void printRpt(List<LandingChkVO> records) {
        DateTime dateTime = new DateTime(this.startdate);
        String mmdd = dateTime.toString("MM.dd");
        String strDate8 = dateTime.toString("yyyyMMdd");
        String fileName = "落地审核明细表" + strDate8 + ".xls";

        Map beans = new HashMap();
        beans.put("rptDate", mmdd);
        beans.put("records", records);
        JxlsManager jxlsManager = new JxlsManager();
        jxlsManager.export(fileName, "dep/landingChkModel.xls", beans);
    }

    private void sortList(List<LandingChkVO> records) {
        Collections.sort(records, new Comparator<LandingChkVO>() {
            @Override
            public int compare(LandingChkVO r1, LandingChkVO r2) {
                LandingChkVO toa1 = (LandingChkVO) r1;
                LandingChkVO toa2 = (LandingChkVO) r2;
                return compareTermnm(toa1, toa2);
            }

            //渠道比较
            private int compareTermnm(LandingChkVO r1, LandingChkVO r2) {
                String s1 = r1.getTermnm();
                String s2 = r2.getTermnm();
                if (s1.startsWith("MPC")) {
                    if (s2.startsWith("MPC")) return compareCtlrsn(r1, r2);
                    else return -1;
                } else if (s1.startsWith("电票")) {
                    if (s2.startsWith("电票")) return compareCtlrsn(r1, r2);
                    else if (s2.startsWith("MPC")) return 1;
                    else return -1;
                } else if (s1.startsWith("网银")) {
                    if (s2.startsWith("网银")) return compareCtlrsn(r1, r2);
                    else if (s2.startsWith("MPC")) return 1;
                    else if (s2.startsWith("电票")) return 1;
                    else return -1;
                } else {
                    return 1;
                }
            }

            //落地原因
            private int compareCtlrsn(LandingChkVO r1, LandingChkVO r2) {
                String s1 = r1.getCtlrsn();
                String s2 = r2.getCtlrsn();
                if (s1.startsWith("大额")) {
                    if (s2.startsWith("大额")) return -r1.getV_txnamt().compareTo(r2.getV_txnamt());
                    else return -1;
                } else if (s1.startsWith("同户名")) {
                    if (s2.startsWith("同户名")) return -r1.getV_txnamt().compareTo(r2.getV_txnamt());
                    else if (s2.startsWith("大额")) return 1;
                    else return -1;
                } else {
                    return 1;
                }
            }
        });
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

    public List<Tn116SOFDataDetail> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<Tn116SOFDataDetail> detlList) {
        this.detlList = detlList;
    }

    public Tn116SOFDataDetail getDetlRecord() {
        return detlRecord;
    }

    public void setDetlRecord(Tn116SOFDataDetail detlRecord) {
        this.detlRecord = detlRecord;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static void setSdf(SimpleDateFormat sdf) {
        LandingChkRptAction.sdf = sdf;
    }

    public DepService getDepService() {
        return depService;
    }

    public void setDepService(DepService depService) {
        this.depService = depService;
    }

    public String getStartamt() {
        return startamt;
    }

    public void setStartamt(String startamt) {
        this.startamt = startamt;
    }
}
