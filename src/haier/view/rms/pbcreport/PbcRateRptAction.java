package haier.view.rms.pbcreport;

import haier.repository.model.PbcRateLoanbal;
import haier.repository.model.pbcrateReport.A14112411Bean;
import haier.repository.model.pbcrateReport.PbcrateBean;
import haier.service.rms.pbcreport.*;
import haier.view.JxlsManager;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * �����Ŵ�����.
 * User: zhanrui
 * Date: 11-6-17
 * Time: ����3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class PbcRateRptAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PbcRateRptAction.class);
    private static final long serialVersionUID = 1366227629931959859L;

    private List<PbcRateLoanbal> detlList = new ArrayList<PbcRateLoanbal>();
    private PbcRateLoanbal detlRecord = new PbcRateLoanbal();

    private String actnum;
    private String startdate;

    @ManagedProperty(value = "#{cmsRateQryService}")
    private CmsRateQryService cmsRateQryService;
    @ManagedProperty(value = "#{sbsRateQryService}")
    private SbsRateQryService sbsRateQryService;
    @ManagedProperty(value = "#{dataEtlService}")
    private DataEtlService dataEtlService;
    @ManagedProperty(value = "#{pbcRateService}")
    private PbcRateService pbcRateService;

    @ManagedProperty(value = "#{subjectService}")
    private SubjectService subjectService;


    private BigDecimal totalCreditAmt = new BigDecimal(0);
    private BigDecimal totalDiscountAmt = new BigDecimal(0);

    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");
    }

    private void checkStartdate() {
        DateTime dt = new DateTime(this.startdate);
        String chkdate = dt.dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");

        if (!chkdate.equals(this.startdate)) {
            throw new RuntimeException("�Ǵ��·ݵ����һ��");
        }
    }

    public String onImport() {
        onImportCmsBalanceData();
        onImportCmsTxndetlData();
        onImportSbsBalanceData();
        return null;
    }

    /**
     * ��ȡ�Ŵ�ϵͳ�ķ��������� �����������ı��ļ� Ȼ���ı��ļ��е����ݵ��뵽�������ݱ���
     *
     * @return
     */
    public String onImportCmsTxndetlData() {
        String tmpcnt = null;
        try {
            checkStartdate();
            dataEtlService.deleteLocalLoanDetlData(this.startdate);
            dataEtlService.extractCmsTxndetlData(this.startdate);
            dataEtlService.extractCCmsTxndetlData(this.startdate);
            tmpcnt = pbcRateService.updateUSDAMT(this.startdate, "loandetl");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("������ϸ������ɡ�" + tmpcnt);
        MessageUtil.addInfo("������ϸ������Ԫ���±�����" + tmpcnt);
        return null;
    }

    public String onImportCmsBalanceData() {
        String tmpcnt = null;
        try {
            checkStartdate();
            dataEtlService.deleteLocalLoanBalData(this.startdate);
            dataEtlService.extractCmsBalData(this.startdate);
            dataEtlService.extractCCmsBalData(this.startdate);
            tmpcnt = pbcRateService.updateUSDAMT(this.startdate, "loanbal");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("����������ݵ�����ɡ�");
        MessageUtil.addInfo("������Ԫ���±�����" + tmpcnt);
        return null;
    }

    public String onExtractSbsTxndetlData() {
        String tmpcnt = null;
        try {
//            dataEtlService.extractDepositTxndetData(this.startdate);
            tmpcnt = pbcRateService.updateUSDAMT(this.startdate, "depositdetl");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
        }
        MessageUtil.addInfo("������Ԫ���±�����" + tmpcnt);
        return null;
    }

    public String onImportSbsBalanceData() {
        String tmpcnt = null;
        try {
            checkStartdate();
            dataEtlService.extractDepositBalanceData(this.startdate);
            tmpcnt = pbcRateService.updateUSDAMT(this.startdate, "depositbal");
        } catch (Exception e) {
            MessageUtil.addError(e.getMessage());
            return null;
        }
        MessageUtil.addInfo("���������ݵ�����ɡ�");
        MessageUtil.addInfo("������������Ԫ���±�����" + tmpcnt);
        return null;
    }

    private String checkAndTransInputDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        } catch (ParseException e) {
            MessageUtil.addError("�����������");
            return null;
        }
        return (new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public String onExportXls_Check() {
        checkStartdate();
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        DecimalFormat df = new DecimalFormat("###,##0.00");
        DateTime dateTime = new DateTime(strdate);
        String yyyymm = dateTime.toString("yyyyMM");
        //String strDate8 = dateTime.toString("yyyyMMdd");
        String fileName = "C01JC5007437000019" + yyyymm + ".xls";
        Map beans = new HashMap();
        beans.put("yyyymm", yyyymm);
        Map loanbalmap = new HashMap();         //�������map
        List<PbcrateBean> loanBal_pbcrateBeans = pbcRateService.pbcSelectbal(strdate, "loan"); //�������
        for (PbcrateBean p : loanBal_pbcrateBeans) {
            loanbalmap.put(p.getCurrencycode() + p.getProductstypecode(), p.getLbal());
        }
        beans.put("LoanBalMap", loanbalmap);
        //������
        Map depositbalmap = new HashMap();
        List<PbcrateBean> depositBal_pbcrateBeans = pbcRateService.pbcSelectbal(strdate, "deposit");
        for (PbcrateBean p : depositBal_pbcrateBeans) {
            depositbalmap.put(p.getCurrencycode() + p.getProductstypecode(), p.getLbal());
        }
        beans.put("DepositBalMap", depositbalmap);
        //�������
        Map loandetlmap = new HashMap();
        List<PbcrateBean> loanDetl_pbcrateBeans = pbcRateService.pbcSelectDetl(strdate, "loan");
        for (PbcrateBean p : loanDetl_pbcrateBeans) {
            loandetlmap.put(p.getCurrencycode() + p.getProductstypecode(), p.getLbal());
        }
        beans.put("LoanDetlMap", loandetlmap);

        //������
/*
            Map depositdetlmap = new HashMap();
            List<PbcrateBean> depositDetl_pbcrateBeans = pbcRateService.pbcSelectDetl(strdate,"deposit");
            for (PbcrateBean p:depositDetl_pbcrateBeans) {
                depositdetlmap.put(p.getCurrencycode() + p.getProductstypecode(),p.getLbal());
            }
            beans.put("DepositDetlMap",depositdetlmap);
*/
        //A1411&A2411����
        Map cm1411Map = new HashMap();
        Map cm2411Map = new HashMap();
        Map lm1411Map = new HashMap();
        Map lm2411Map = new HashMap();
        List<A14112411Bean> a14112411BeanList = subjectService.selectA1411A2411Data(this.startdate);
        for (A14112411Bean p : a14112411BeanList) {
            cm1411Map.put("BAL" + p.getProductType() + p.getProductCode(), p.getCm_1411());
            cm2411Map.put("BAL" + p.getProductType() + p.getProductCode(), p.getCm_2411());
            lm1411Map.put("BAL" + p.getProductType() + p.getProductCode(), p.getLm_1411());
            lm2411Map.put("BAL" + p.getProductType() + p.getProductCode(), p.getLm_2411());
        }
        beans.put("CM1411", cm1411Map);
        beans.put("CM2411", cm2411Map);
        beans.put("LM1411", lm1411Map);
        beans.put("LM2411", lm2411Map);


        //�Թ����ڴ��
        Map p12MG4Map = new HashMap();
        A14112411Bean p12MG4 = subjectService.get_P12MG4_Data();
        p12MG4Map.put("CM1411", p12MG4.getCm_1411());
        p12MG4Map.put("CM2411", p12MG4.getCm_2411());
        p12MG4Map.put("LM1411", p12MG4.getLm_1411());
        p12MG4Map.put("LM2411", p12MG4.getLm_2411());
        beans.put("P12MG4", p12MG4Map);


        JxlsManager jxlsManager = new JxlsManager();
        jxlsManager.exportOld(fileName, "peoplebankModel.xls", beans);

        return result;
    }

    public String onExportTxt_cmsbal() {
        checkStartdate();
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymmdd = dateTime.toString("yyyyMMdd");
            String fileName = "LOAB" + "C5007437000019" + yyyymmdd + ".dat";
            onExportTxtToWeb(fileName, pbcRateService.selectLoanbalToString(strdate));
        } catch (Exception e) {
            logger.error("���ɱ���ʱ���ִ���", e);
            MessageUtil.addWarn("���ɱ���ʱ���ִ���" + e.getMessage());
        }
        return null;
    }

    public String onExportTxt_CmsTxndetl() {
        checkStartdate();
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymmdd = dateTime.toString("yyyyMMdd");
            String fileName = "LOAF" + "C5007437000019" + yyyymmdd + ".dat";
            onExportTxtToWeb(fileName, pbcRateService.selectLoandetlToString(strdate));
        } catch (Exception e) {
            logger.error("���ɱ���ʱ���ִ���", e);
            MessageUtil.addWarn("���ɱ���ʱ���ִ���");
        }
        return null;
    }

    public String onExportTxt_sbsbal() {
        checkStartdate();
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymmdd = dateTime.toString("yyyyMMdd");
            String fileName = "DEPB" + "C5007437000019" + yyyymmdd + ".dat";
            onExportTxtToWeb(fileName, pbcRateService.selectDepositbalToString(strdate));
        } catch (Exception e) {
            logger.error("���ɱ���ʱ���ִ���", e);
            MessageUtil.addWarn("���ɱ���ʱ���ִ���");
        }
        return null;
    }

    public String onExportTxt_sbsdetl() {
        checkStartdate();
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C02F" + "C5007437000019" + yyyymm + ".dat";
            String buffer = pbcRateService.selectDepositdetlToString(strdate);
            exportTxtToDisk(fileName, buffer);
            result = onExportTxtToWeb(fileName, buffer);
        } catch (Exception e) {
            logger.error("���ɱ���ʱ���ִ���", e);
            MessageUtil.addWarn("���ɱ���ʱ���ִ���");
        }
        return result;
    }

    private void exportTxtToDisk(String fileName, String buffer) {
        checkStartdate();
        File file = new File(PropertyManager.getProperty("PRJ_ROOTPATH") + "exp/pbcrate/" + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("���ܴ����ļ�." + file.getPath() + file.getName());
            }
            OutputStream os = null;
            try {
                os = new FileOutputStream(file);
                os.write(buffer.getBytes("GBK"));
                os.flush();
            } catch (Exception e) {
                throw new RuntimeException("д�ļ�����.", e);
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        //
                    }
                }
            }
        }
    }


    private String onExportTxtToWeb(String fileName, String buffer) {
        ServletOutputStream os = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            os = response.getOutputStream();
            response.reset();

            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/text");

            os.write(buffer.getBytes());
        } catch (Exception ex) {
            logger.error("���ɱ���ʱ���ִ���", ex);
            throw new RuntimeException(ex);
        } finally {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return null;
    }

    //==========================================================


    public List<PbcRateLoanbal> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<PbcRateLoanbal> detlList) {
        this.detlList = detlList;
    }

    public PbcRateLoanbal getDetlRecord() {
        return detlRecord;
    }

    public void setDetlRecord(PbcRateLoanbal detlRecord) {
        this.detlRecord = detlRecord;
    }

    public String getActnum() {
        return actnum;
    }

    public void setActnum(String actnum) {
        this.actnum = actnum;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public PbcRateService getPbcRateService() {
        return pbcRateService;
    }

    public void setPbcRateService(PbcRateService pbcRateService) {
        this.pbcRateService = pbcRateService;
    }

    public CmsRateQryService getCmsRateQryService() {
        return cmsRateQryService;
    }

    public void setCmsRateQryService(CmsRateQryService cmsRateQryService) {
        this.cmsRateQryService = cmsRateQryService;
    }


    public BigDecimal getTotalCreditAmt() {
        return totalCreditAmt;
    }

    public BigDecimal getTotalDiscountAmt() {
        return totalDiscountAmt;
    }

    public SbsRateQryService getSbsRateQryService() {
        return sbsRateQryService;
    }

    public void setSbsRateQryService(SbsRateQryService sbsRateQryService) {
        this.sbsRateQryService = sbsRateQryService;
    }

    public DataEtlService getDataEtlService() {
        return dataEtlService;
    }

    public void setDataEtlService(DataEtlService dataEtlService) {
        this.dataEtlService = dataEtlService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
}
