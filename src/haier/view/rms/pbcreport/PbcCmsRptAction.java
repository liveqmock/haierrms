package haier.view.rms.pbcreport;

import haier.repository.model.sbsreport.ActbalHistory;
import haier.service.rms.pbcreport.CmsQryService;
import haier.service.rms.pbcreport.model.SbsItemBalBean;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 人行信贷报表.
 * User: zhanrui
 * Date: 11-6-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class PbcCmsRptAction implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PbcCmsRptAction.class);
    private static final long serialVersionUID = 2266984947611564735L;

    private List<ActbalHistory> detlList = new ArrayList<ActbalHistory>();
    private ActbalHistory detlRecord = new ActbalHistory();

    private String actnum;
    private String startdate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @ManagedProperty(value = "#{cmsQryService}")
    private CmsQryService cmsQryService;

    private List<SbsItemBalBean> sbsItemBalBeanList;

    private BigDecimal totalCreditAmt = new BigDecimal(0);
    private BigDecimal totalDiscountAmt = new BigDecimal(0);

    public List<ActbalHistory> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<ActbalHistory> detlList) {
        this.detlList = detlList;
    }

    public ActbalHistory getDetlRecord() {
        return detlRecord;
    }

    public void setDetlRecord(ActbalHistory detlRecord) {
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
/*
        try {
            Date date = new SimpleDateFormat("yyyy年MM月").parse(startdate);
            this.startdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
        }
*/
    }

    public void setCmsQryService(CmsQryService cmsQryService) {
        this.cmsQryService = cmsQryService;
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

    //==============================
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate = dt.minusMonths(1).toString("yyyy年MM月");
    }

    public String onSearchSbsData() {

        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        try {
            DateTime dateTime = (new DateTime(strdate)).dayOfMonth().withMaximumValue();
            strdate = dateTime.toString("yyyy-MM-dd");
            this.sbsItemBalBeanList = cmsQryService.getSbsItemDataList(strdate);
            this.totalCreditAmt = cmsQryService.getCreditTotalBalance(this.sbsItemBalBeanList);
            this.totalDiscountAmt = cmsQryService.getDiscountTotalBalance(this.sbsItemBalBeanList);
        } catch (Exception e) {
            logger.error("获取SBS总帐码核算码数据错误",e);
            MessageUtil.addError("获取SBS总帐码核算码数据错误：" + e.getMessage());
        }
        return null;
    }

    private String checkAndTransInputDate() {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy年MM月").parse(startdate);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
            return null;
        }
        return (new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    public String onExportExcel() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }

        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream(); //获得输出流
            response.reset();

            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String lastyyyymm = dateTime.minusMonths(1).toString("yyyyMM");


            String fileName = "C001JC5007437" + yyyymm + ".xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");

            //模板
            String modelPath = PropertyManager.getProperty("REPORT_ROOTPATH") + "pbc/C001JC5007437YYYYMM.xls";
            Map beans = new HashMap();

            beans.put("yyyymm", yyyymm);
            beans.put("lastyyyymm", lastyyyymm);

            Map reporpDataMap = cmsQryService.getReporpDataMap(yyyymm, lastyyyymm);
            beans.put("CNY_D", reporpDataMap.get("CNY_D"));
            beans.put("CNY_T", reporpDataMap.get("CNY_T"));
            beans.put("USD_D", reporpDataMap.get("USD_D"));
            beans.put("HKD_D", reporpDataMap.get("HKD_D"));

            beans.put("CNY_D_CS01", reporpDataMap.get("CNY_D_CS01"));
            beans.put("CNY_D_CS02", reporpDataMap.get("CNY_D_CS02"));
            beans.put("CNY_D_CS03", reporpDataMap.get("CNY_D_CS03"));

            beans.put("CNY_D_CS02_A", reporpDataMap.get("CNY_D_CS02_A"));
            beans.put("CNY_D_CS02_B", reporpDataMap.get("CNY_D_CS02_B"));
            beans.put("CNY_D_CS02_C", reporpDataMap.get("CNY_D_CS02_C"));
            beans.put("CNY_D_CS02_D", reporpDataMap.get("CNY_D_CS02_D"));
            beans.put("CNY_D_CS02_E", reporpDataMap.get("CNY_D_CS02_E"));
            beans.put("CNY_D_CS02_Z", reporpDataMap.get("CNY_D_CS02_Z"));

            beans.put("CNY_D_CS03_A", reporpDataMap.get("CNY_D_CS03_A"));
            beans.put("CNY_D_CS03_B", reporpDataMap.get("CNY_D_CS03_B"));
            beans.put("CNY_D_CS03_C", reporpDataMap.get("CNY_D_CS03_C"));
            beans.put("CNY_D_CS03_D", reporpDataMap.get("CNY_D_CS03_D"));
            beans.put("CNY_D_CS03_E", reporpDataMap.get("CNY_D_CS03_E"));
            beans.put("CNY_D_CS03_Z", reporpDataMap.get("CNY_D_CS03_Z"));

            //上月数据
            beans.put("LAST_CNY_D", reporpDataMap.get("LAST_CNY_D"));
            beans.put("LAST_CNY_T", reporpDataMap.get("LAST_CNY_T"));
            beans.put("LAST_USD_D", reporpDataMap.get("LAST_USD_D"));
            beans.put("LAST_HKD_D", reporpDataMap.get("LAST_HKD_D"));

            beans.put("LAST_CNY_D_CS01", reporpDataMap.get("LAST_CNY_D_CS01"));
            beans.put("LAST_CNY_D_CS02", reporpDataMap.get("LAST_CNY_D_CS02"));
            beans.put("LAST_CNY_D_CS03", reporpDataMap.get("LAST_CNY_D_CS03"));

            beans.put("LAST_CNY_D_CS02_A", reporpDataMap.get("LAST_CNY_D_CS02_A"));
            beans.put("LAST_CNY_D_CS02_B", reporpDataMap.get("LAST_CNY_D_CS02_B"));
            beans.put("LAST_CNY_D_CS02_C", reporpDataMap.get("LAST_CNY_D_CS02_C"));
            beans.put("LAST_CNY_D_CS02_D", reporpDataMap.get("LAST_CNY_D_CS02_D"));
            beans.put("LAST_CNY_D_CS02_E", reporpDataMap.get("LAST_CNY_D_CS02_E"));
            beans.put("LAST_CNY_D_CS02_Z", reporpDataMap.get("LAST_CNY_D_CS02_Z"));

            beans.put("LAST_CNY_D_CS03_A", reporpDataMap.get("LAST_CNY_D_CS03_A"));
            beans.put("LAST_CNY_D_CS03_B", reporpDataMap.get("LAST_CNY_D_CS03_B"));
            beans.put("LAST_CNY_D_CS03_C", reporpDataMap.get("LAST_CNY_D_CS03_C"));
            beans.put("LAST_CNY_D_CS03_D", reporpDataMap.get("LAST_CNY_D_CS03_D"));
            beans.put("LAST_CNY_D_CS03_E", reporpDataMap.get("LAST_CNY_D_CS03_E"));
            beans.put("LAST_CNY_D_CS03_Z", reporpDataMap.get("LAST_CNY_D_CS03_Z"));

            //本月发生额
            beans.put("FS_CNY_D", reporpDataMap.get("FS_CNY_D"));
            beans.put("FS_CNY_T", reporpDataMap.get("FS_CNY_T"));
            beans.put("FS_USD_D", reporpDataMap.get("FS_USD_D"));
            beans.put("FS_HKD_D", reporpDataMap.get("FS_HKD_D"));

            beans.put("FS_CNY_D_CS01", reporpDataMap.get("FS_CNY_D_CS01"));
            beans.put("FS_CNY_D_CS02", reporpDataMap.get("FS_CNY_D_CS02"));
            beans.put("FS_CNY_D_CS03", reporpDataMap.get("FS_CNY_D_CS03"));

            beans.put("FS_CNY_D_CS02_A", reporpDataMap.get("FS_CNY_D_CS02_A"));
            beans.put("FS_CNY_D_CS02_B", reporpDataMap.get("FS_CNY_D_CS02_B"));
            beans.put("FS_CNY_D_CS02_C", reporpDataMap.get("FS_CNY_D_CS02_C"));
            beans.put("FS_CNY_D_CS02_D", reporpDataMap.get("FS_CNY_D_CS02_D"));
            beans.put("FS_CNY_D_CS02_E", reporpDataMap.get("FS_CNY_D_CS02_E"));
            beans.put("FS_CNY_D_CS02_Z", reporpDataMap.get("FS_CNY_D_CS02_Z"));

            beans.put("FS_CNY_D_CS03_A", reporpDataMap.get("FS_CNY_D_CS03_A"));
            beans.put("FS_CNY_D_CS03_B", reporpDataMap.get("FS_CNY_D_CS03_B"));
            beans.put("FS_CNY_D_CS03_C", reporpDataMap.get("FS_CNY_D_CS03_C"));
            beans.put("FS_CNY_D_CS03_D", reporpDataMap.get("FS_CNY_D_CS03_D"));
            beans.put("FS_CNY_D_CS03_E", reporpDataMap.get("FS_CNY_D_CS03_E"));
            beans.put("FS_CNY_D_CS03_Z", reporpDataMap.get("FS_CNY_D_CS03_Z"));

            XLSTransformer transformer = new XLSTransformer();
            InputStream is = new BufferedInputStream(new FileInputStream(modelPath));
            Workbook wb = transformer.transformXLS(is, beans);
            wb.write(os);
            is.close();
            os.flush();
            os.close();

        } catch (Exception ex) {
            logger.error("生成报表时出现错误。", ex);
            MessageUtil.addWarn("生成报表时出现错误。");
        } finally {
        }

        return null;
    }

    public String onExportTxt_Y() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C001YC5007437" + yyyymm + ".txt";
            result = onExportTxt(fileName, cmsQryService.getDetailBalanceBuffer(yyyymm));
        } catch (SQLException e) {
            logger.error("生成报表时出现错误。", e);
            MessageUtil.addWarn("生成报表时出现错误。");
        }
        return result;
    }

    public String onExportTxt_F() {
        String strdate = checkAndTransInputDate();
        if (strdate == null) {
            return null;
        }
        String result = null;
        try {
            DateTime dateTime = new DateTime(strdate);
            String yyyymm = dateTime.toString("yyyyMM");
            String fileName = "C001FC5007437" + yyyymm + ".txt";
            result = onExportTxt(fileName, cmsQryService.getDetailActualAmtBuffer(yyyymm));
        } catch (SQLException e) {
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
}
