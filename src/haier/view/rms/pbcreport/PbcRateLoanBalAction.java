package haier.view.rms.pbcreport;

import haier.repository.model.PbcRateLoanbal;
import haier.service.rms.pbcreport.CmsRateQryService;
import haier.service.rms.pbcreport.DataEtlService;
import haier.service.rms.pbcreport.PbcRateService;
import haier.service.rms.pbcreport.SbsRateQryService;
import haier.service.rms.pbcreport.model.SbsItemBalBean;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 人行信贷报表.
 * User: zhanrui
 * Date: 11-6-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class PbcRateLoanBalAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PbcRateLoanBalAction.class);
    private static final long serialVersionUID = 1366227629931959859L;

    private List<PbcRateLoanbal> detlList = new ArrayList<PbcRateLoanbal>();
    private PbcRateLoanbal selectRecord = new PbcRateLoanbal();
    private PbcRateLoanbal[] selectRecords;

    private String actnum;
    private String startdate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @ManagedProperty(value = "#{cmsRateQryService}")
    private CmsRateQryService cmsRateQryService;
    @ManagedProperty(value = "#{sbsRateQryService}")
    private SbsRateQryService sbsRateQryService;
    @ManagedProperty(value = "#{pbcRateService}")
    private PbcRateService pbcRateService;
    @ManagedProperty(value = "#{dataEtlService}")
    private DataEtlService dataEtlService;

    private List<SbsItemBalBean> sbsItemBalBeanList;

    private BigDecimal totalCreditAmt = new BigDecimal(0);
    private BigDecimal totalDiscountAmt = new BigDecimal(0);

    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).dayOfMonth().withMaximumValue().toString("yyyy-MM-dd");

        initList();
//        dataEtlService.extractDataFromCms(this.startdate);
//        dataEtlService.extractDataFromCms("2011-08-31");
//        dataEtlService.getList();
        //logger.info(balbean.getBANKCODE());
    }
    private void initList(){
        this.detlList = pbcRateService.selectLoanbalList(this.startdate);
    }

    /**
     * 获取信贷系统的发生额数据 导出到本地文本文件 然后将文本文件中的数据导入到本地数据表中
     * @return
     */
    public  String onImportCmsTxndetlData(){
        dataEtlService.extractCmsTxndetlData(this.startdate);
        return null;
    }
    public  String onExtractCmsBalanceData(){
        dataEtlService.extractCmsBalData(this.startdate);
        return null;
    }
    public  String  onExtractSbsTxndetlData(){
        dataEtlService.extractDepositTxndetData(this.startdate);
        return null;
    }
    public  String  onExtractSbsBalanceData(){
        dataEtlService.extractDepositBalanceData(this.startdate);
        return null;
    }

    private String checkAndTransInputDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
            return null;
        }
        return (new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public String onExportTxt_cmsbal() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C01Y" + "C500743700" + yyyymm + ".txt";
            result = onExportTxt(fileName, cmsRateQryService.getDetailBalanceBuffer("cms_balance.xml"));
        } catch (SQLException e) {
            logger.error("生成报表时出现错误。", e);
            MessageUtil.addWarn("生成报表时出现错误。");
        }
        return result;
    }

    public String onExportCmsTxndetlTxt() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C01F" +  "C500743700" + yyyymm + ".txt";
            result = onExportTxt(fileName, cmsRateQryService.getDetailBalanceBuffer("cms_txndetl.xml"));
        } catch (SQLException e) {
            logger.error("生成报表时出现错误。", e);
            MessageUtil.addWarn("生成报表时出现错误。");
        }
        return result;
    }

    public String onExportTxt_sbsbal() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C02Y" + "C500743700" + yyyymm + ".txt";
            result = onExportTxt(fileName, sbsRateQryService.getBalanceBuffer(strdate));
        } catch (Exception e) {
            logger.error("生成报表时出现错误。", e);
            MessageUtil.addWarn("生成报表时出现错误。");
        }
        return result;
    }
    public String onExportTxt_sbsdetl() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C02F" + "C500743700" + yyyymm + ".txt";
            result = onExportTxt(fileName, sbsRateQryService.getTxnDetailBuffer(strdate));
        } catch (Exception e) {
            logger.error("生成报表时出现错误。", e);
            MessageUtil.addWarn("生成报表时出现错误。");
        }
        return result;
    }


    private String onExportTxt(String fileName, String buffer) {
        ServletOutputStream os = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            os = response.getOutputStream();
            response.reset();

            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/text");

            os.write(buffer.getBytes());
        } catch (Exception ex) {
            logger.error("生成报表时出现错误。", ex);
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

    public PbcRateLoanbal getSelectRecord() {
        return selectRecord;
    }

    public void setSelectRecord(PbcRateLoanbal selectRecord) {
        this.selectRecord = selectRecord;
    }

    public PbcRateLoanbal[] getSelectRecords() {
        return selectRecords;
    }

    public void setSelectRecords(PbcRateLoanbal[] selectRecords) {
        this.selectRecords = selectRecords;
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
/*
        try {
            Date date = new SimpleDateFormat("yyyy年MM月").parse(startdate);
            this.startdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
        }
*/
    }

    public CmsRateQryService getCmsRateQryService() {
        return cmsRateQryService;
    }

    public void setCmsRateQryService(CmsRateQryService cmsRateQryService) {
        this.cmsRateQryService = cmsRateQryService;
    }

    public List<SbsItemBalBean> getSbsItemBalBeanList() {
        return sbsItemBalBeanList;
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

    public PbcRateService getPbcRateService() {
        return pbcRateService;
    }

    public void setPbcRateService(PbcRateService pbcRateService) {
        this.pbcRateService = pbcRateService;
    }
}
