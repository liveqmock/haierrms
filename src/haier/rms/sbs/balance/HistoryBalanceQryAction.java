package haier.rms.sbs.balance;

import haier.repository.model.MtActtype;
import haier.repository.model.sbsreport.ActbalHistory;
import haier.service.rms.sbsbatch.ActbalService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import pub.platform.advance.utils.PropertyManager;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 企业历史余额查询.
 * User: zhanrui
 * Date: 11-1-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class HistoryBalanceQryAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(HistoryBalanceQryAction.class);
    private static final long serialVersionUID = -7592488690417583844L;

    private List<ActbalHistory> detlList = new ArrayList<ActbalHistory>();
    private List<ActbalHistory> currList;
    private List<RoeBean> roeList = new ArrayList<RoeBean>();
    private int totalcount = 0;
    private BigDecimal totalamt = new BigDecimal(0);

    private String actnum;
    private String actname;
    private String startdate;
    private String enddate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private String[] acttypes = {"活期", "定期", "保证金"};
    private String[] currencys = {"人民币", "美元", "英镑"};
    private String[] actattrs = {"内", "外"};
    private SelectItem[] acttypeOptions;
    private SelectItem[] currencyOptions;
    private SelectItem[] actattrOptions;
    private String queryType;
    private String reportFileName;
    //UI表格标题
    private String title;

    @ManagedProperty(value = "#{actbalService}")
    private ActbalService actbalService;

    public List<ActbalHistory> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<ActbalHistory> detlList) {
        this.detlList = detlList;
    }

    public int getTotalcount() {
        return this.detlList.size();
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalamt() {
        this.totalamt = new BigDecimal("0");
        for (ActbalHistory record : this.detlList) {
            BigDecimal rmbbal = record.getRmbbal();
            System.out.println(rmbbal);
            if (rmbbal == null) {
                rmbbal = new BigDecimal(0);
            }
            totalamt = totalamt.add(rmbbal);
        }
        return totalamt;

    }

    public void setTotalamt(BigDecimal totalamt) {
        this.totalamt = totalamt;
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

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public SelectItem[] getActtypeOptions() {
        return acttypeOptions;
    }

    public SelectItem[] getCurrencyOptions() {
        return currencyOptions;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActbalService(ActbalService actbalService) {
        this.actbalService = actbalService;
    }

    public List<ActbalHistory> getCurrList() {
        return currList;
    }

    public void setCurrList(List<ActbalHistory> currList) {
        this.currList = currList;
    }

    public SelectItem[] getActattrOptions() {
        return actattrOptions;
    }

    public void setActattrOptions(SelectItem[] actattrOptions) {
        this.actattrOptions = actattrOptions;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    //==============================
    @PostConstruct
    public void postConstruct() {

        Date date = new Date();
        String currDate = sdf.format(date);

        DateTime dt = new DateTime();
        this.startdate = dt.dayOfMonth().withMinimumValue().toString("yyyy-MM-dd");
        this.enddate = dt.minusDays(1).toString("yyyy-MM-dd");

        //query();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        //    取参数列表
        this.queryType = request.getQueryString();
        if (StringUtils.isEmpty(this.queryType)) {
            this.queryType = "C";
        }
        if (this.queryType.equals("A")) {
            this.title = "A股企业历史余额查询列表 ";
        }
        if (this.queryType.equals("H")) {
            this.title = "H股企业历史余额查询列表 ";
        }
        if (this.queryType.equals("C")) {
            this.title = "全部企业历史余额查询列表 ";
        }

        this.acttypeOptions = createFilterOptions(acttypes);
        this.currencyOptions = createFilterOptions(currencys);
        this.actattrOptions = createFilterOptions(actattrs);
    }

    public String exportExcel() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (this.detlList.size() == 0) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "请先查询数据。", ""));
                return null;
            }
            this.currList = this.actbalService.selectCurrcodeList(new SimpleDateFormat("yyyy-MM-dd").format(this.startdate), this.queryType);
            this.reportFileName = createExcelTempFile();
        } catch (Exception e) {
            logger.error("报表生成失败。", e);
        }
        return null;
    }

    public String query() {
        try {
            queryRecordsFromDB();

            this.currList = this.actbalService.selectCurrcodeList(this.startdate, this.queryType);
            this.currencyOptions = createFilterOptions(createCurrencyArray());

        } catch (Exception e) {
            logger.error("查询时出现错误。", e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "查询时出现错误。", "检索数据库出现问题。"));
        }
        return null;
    }
    public String queryByCorpName() {
        try {
            queryRecordsFromDBByCorpName();

            this.currList = this.actbalService.selectCurrcodeList(this.startdate, this.queryType);
            this.currencyOptions = createFilterOptions(createCurrencyArray());

        } catch (Exception e) {
            logger.error("查询时出现错误。", e);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "查询时出现错误。", "检索数据库出现问题。"));
        }
        return null;
    }

    public String reset() {
        //this.detlRecord = new T8121ResponseRecord();
        return null;
    }

    private void queryRecordsFromDB() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.detlList = actbalService.selectHistoryActBal(this.startdate, this.queryType);
            if (this.detlList.size() == 0) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "此日期的余额记录不存在。", null));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "历史余额查询失败", null));
            logger.error("SBS查询结果异常", e);
        }
    }

    private void queryRecordsFromDBByCorpName() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            this.detlList = actbalService.selectHistoryActBalByCorpName(this.startdate, this.enddate, this.actname);
            if (this.detlList.size() == 0) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "此日期的余额记录不存在。", null));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "历史余额查询失败", null));
            logger.error("SBS查询结果异常", e);
        }
    }

    private SelectItem[] createFilterOptions(String[] acttypes) {
        SelectItem[] options = new SelectItem[acttypes.length + 1];

        options[0] = new SelectItem("", "请选择");
        for (int i = 0; i < acttypes.length; i++) {
            options[i + 1] = new SelectItem(acttypes[i], acttypes[i]);
        }
        return options;
    }

    private String[] createCurrencyArray() {
        int i = 0;
        String[] currencys = new String[this.currList.size()];
        for (ActbalHistory actbalHistory : this.currList) {
            currencys[i] = actbalHistory.getCurrname();
            i++;
        }
        return currencys;
    }

    /**
     * 已废弃 仅供参考
     *
     */
/*
    private static void downloadFile(String strfileName) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            //文件绝对路径
            String excelName = PropertyManager.getProperty("REPORT_ROOTPATH") + "temp/" + strfileName;
            //String excelName = servletContext.getRealPath("/report/temp") + "/" + strfileName;
            File exportFile = new File(excelName);
            //判断文件是否存在
            if (!exportFile.exists()) {
                logger.error("EXCEL读取错误。");
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXCEL文件不存在。", null));
            }
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + strfileName);
            httpServletResponse.setContentLength((int) exportFile.length());
            httpServletResponse.setContentType("application/x-download");
            // httpServletResponse.setContentType("application/vnd.ms-excel");

            byte[] b = new byte[1024];
            int i = 0;
            FileInputStream fis = new FileInputStream(exportFile);
            while ((i = fis.read(b)) > 0) {
                servletOutputStream.write(b, 0, i);
            }
        } catch (IOException e) {
            logger.error("EXCEL读取错误。", e);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXCEL读取错误。", null));
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

*/
    private String createExcelTempFile() throws IOException, InvalidFormatException {
        List<HistoryReportInfoBean> reportInfoBeanList = new ArrayList<HistoryReportInfoBean>();

        //获取SBS账号属性定义清单
        List<MtActtype> acttypeList =  actbalService.selectActtype4SbsActList();

        //循环遍历币别列表
        for (ActbalHistory currinfo : this.currList) {
            HistoryReportInfoBean reportInfoBean = new HistoryReportInfoBean();
            String currcode = currinfo.getCurrcode();
            reportInfoBean.setCurrcode(currcode);
            reportInfoBean.setCurrname(currinfo.getCurrname());
            reportInfoBean.setRate(currinfo.getRoe());
            int seqno = 0;
            for (ActbalHistory balanceBean : this.detlList) {
                if (balanceBean.getCurrcode().equals(currcode)) {
                    ActbalHistory newbean = new ActbalHistory();
                    BeanUtils.copyProperties(balanceBean, newbean);
                    seqno++;
                    newbean.setSeqno(seqno);
                    setBalanceBeanReportFieldValue(balanceBean, newbean);

                    //20130227 zhanrui 增加企业类别
                    for (MtActtype mtActtype : acttypeList) {
                        if (mtActtype.getActno().equals(balanceBean.getActno().trim())) {
                            newbean.setActattr(mtActtype.getCategory());
                            break;
                        }
                    }

                    reportInfoBean.addBalanceBean(newbean);
                }
            }
            reportInfoBeanList.add(reportInfoBean);
        }
        //excel
        Map beansMap = new HashMap();

        List<HistoryReportInfoBean> reportHomeCurrencyList = new ArrayList<HistoryReportInfoBean>();
        List<HistoryReportInfoBean> reportForeignCurrencyList = new ArrayList<HistoryReportInfoBean>();

        for (HistoryReportInfoBean reportInfoBean : reportInfoBeanList) {
            if (reportInfoBean.getCurrcode().equals("001")) {
                reportHomeCurrencyList.add(reportInfoBean);
            } else {
                reportForeignCurrencyList.add(reportInfoBean);
            }
        }

        //---------
        beansMap.put("renminbilist", reportHomeCurrencyList);
        beansMap.put("forecurrlist", reportForeignCurrencyList);

        String reportPath = PropertyManager.getProperty("REPORT_ROOTPATH");
        String templateFileName = reportPath + "sbsbalance.xls";

        String excelFilename = "sbsbal_" + new SimpleDateFormat("yyyyMMdd_").format(this.startdate) + this.queryType + ".xls";
        String destFileName = reportPath + "temp/" + excelFilename;


        XLSTransformer transformer = new XLSTransformer();
//        transformer.transformXLS(templateFileName, beansMap, destFileName);

        InputStream is = new BufferedInputStream(new FileInputStream(templateFileName));

        //HSSFWorkbook wb = transformer.transformXLS(is, beansMap);
        Workbook wb = transformer.transformXLS(is, beansMap);

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + excelFilename);
        response.setContentType("application/msexcel");
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
        is.close();

        return excelFilename;
    }

    private void setBalanceBeanReportFieldValue(ActbalHistory srcBalanceBean, ActbalHistory desBalnceBean) {
        String acttype = srcBalanceBean.getActtype().trim();
        if ("1".equals(acttype)) {
            desBalnceBean.setActtype1bal(desBalnceBean.getActtype1bal().add(srcBalanceBean.getHomecurbal()));
        } else if ("2".equals(acttype)) {
            desBalnceBean.setActtype2bal(desBalnceBean.getActtype2bal().add(srcBalanceBean.getHomecurbal()));
        } else if ("3".equals(acttype)) {
            desBalnceBean.setActtype3bal(desBalnceBean.getActtype3bal().add(srcBalanceBean.getHomecurbal()));
        }
    }
}
