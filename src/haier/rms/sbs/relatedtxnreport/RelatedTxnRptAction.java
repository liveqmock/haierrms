package haier.rms.sbs.relatedtxnreport;

import haier.repository.model.sbsreport.ActbalHistory;
import haier.service.rms.sbsbatch.ActbalService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 关联交易限额月控报表.
 * User: zhanrui
 * Date: 11-1-17
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class RelatedTxnRptAction implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(RelatedTxnRptAction.class);
    private static final long serialVersionUID = -6478572032885828880L;

    private List<ActbalHistory> detlList = new ArrayList<ActbalHistory>();
    private ActbalHistory detlRecord = new ActbalHistory();

    private String actnum;
    private String startdate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    HSSFCellStyle cellStyle_center;
    HSSFCellStyle cellStyle_right;
    HSSFDataFormat dataFormat;

    @ManagedProperty(value = "#{actbalService}")
    private ActbalService actbalService;

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
        try {
            Date date = new SimpleDateFormat("yyyy年MM月").parse(startdate);
            this.startdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (ParseException e) {
            MessageUtil.addError("日期输入错误。");
        }

    }


    public void setActbalService(ActbalService actbalService) {
        this.actbalService = actbalService;
    }

    //==============================
    @PostConstruct
    public void postConstruct() {
        DateTime dt = new DateTime();
        this.startdate=dt.minusMonths(1).toString("yyyy年MM月");
    }

    public String onExport() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<RelatedTxnDataBean> dataList = queryRecordsFromDB();

        String reportPath = PropertyManager.getProperty("REPORT_ROOTPATH");
        String templateFileName = reportPath + "sbs/relatedTxnReport.xls";
        HSSFWorkbook workbook;
        try {
             workbook = new HSSFWorkbook(new FileInputStream(templateFileName));
        } catch (IOException e) {
            MessageUtil.addError("报表模板文件不存在。");
            return null;
        }

        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream(); //获得输出流
            response.reset();
            String fileName = "relatedtxnReport_" + new DateTime(this.startdate).toString("yyyyMM")+ ".xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");

             HSSFSheet sheet = workbook.getSheetAt(0);   //读取第一个工作簿
            //workbook.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);

            int i = 1;
            int col = 0;

            cellStyle_center = workbook.createCellStyle();
            cellStyle_center.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle_center.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle_center.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle_center.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle_center.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);


            cellStyle_right = workbook.createCellStyle();
            cellStyle_right.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle_right.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle_right.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle_right.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle_right.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            dataFormat = workbook.createDataFormat();

            int row = 2;
            col = 2;
            for (RelatedTxnDataBean record : dataList) {
                int r =0;
                setCell(sheet, row + r + 0, col, record.getRptdate());
                setCell(sheet, row + r + 1, col, record.getRmb1());
                setCell(sheet, row + r + 2, col, record.getForeign1());
                setCell(sheet, row + r + 3, col, record.getRmb2());
                setCell(sheet, row + r + 4, col, record.getForeign2());
                setCell(sheet, row + r + 5, col, record.getTotal1());
                setCell(sheet, row + r + 6, col, record.getTotal2());
                setCell(sheet, row + r + 7, col, record.getTotal());
                col++;
            }

            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private Double transAmt(String txnamt) {
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


    /**
     * 设置单元格
     *
     * @param index 列号
     * @param value 单元格填充值
     */
    public void setCell(HSSFSheet sheet, int row, int index, String value) {
//        HSSFCell cell = sheet.getRow(row).getCell(index);
        HSSFCell cell = sheet.getRow(row).createCell(index);

       cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//           cell.setEncoding(XLS_ENCODING);
        //cellStyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle_center);
    }

    public void setCell(HSSFSheet sheet, int row, int index, long value) {
        HSSFCell cell = sheet.getRow(row).createCell(index);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//           cell.setEncoding(XLS_ENCODING);
//        cellStyle_center.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyle_right.setDataFormat(dataFormat.getFormat("###,###,###"));
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle_right);
    }


    public String reset() {
        this.detlRecord = new ActbalHistory();
        return null;
    }

    private List<RelatedTxnDataBean> queryRecordsFromDB() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<RelatedTxnDataBean> dataList = new ArrayList<RelatedTxnDataBean>();
        DateTime dt = new DateTime(this.startdate);
        int month = dt.getMonthOfYear();
        DateTime newdt = dt;
        int newmonth = newdt.getMonthOfYear();
        while (newmonth == month) {
            String currdate = newdt.toString("yyyy-MM-dd");
            RelatedTxnDataBean dataBean = new RelatedTxnDataBean();
            dataBean.setRptdate(newdt.toString("MM/dd"));
            long rmb1 = actbalService.selectRelatedTxnReportRMBData(currdate, "A");
            dataBean.setRmb1(rmb1);
            long foreign1 = actbalService.selectRelatedTxnReportForeignData(currdate, "A");
            dataBean.setForeign1(foreign1);

            long rmb2 = actbalService.selectRelatedTxnReportRMBData(currdate, "H");
            dataBean.setRmb2(rmb2);
            long foreign2 = actbalService.selectRelatedTxnReportForeignData(currdate, "H");
            dataBean.setForeign2(foreign2);

            dataBean.setTotal1(rmb1 + rmb2);
            dataBean.setTotal2(foreign1 + foreign2);

            dataBean.setTotal(rmb1 + foreign1 + rmb2 + foreign2);

            dataList.add(dataBean);

            logger.info(newdt.toString());
            newdt = newdt.plusDays(1);
            newmonth = newdt.getMonthOfYear();
        }
        return dataList;
    }


}
