package haier.view.rms.externalcustomer;

import haier.repository.model.SbsActbal;
import haier.service.rms.externalcustomer.ExternalCustService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: haiyuhuang
 * Date: 11-9-20
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@RequestScoped
public class LtdratioRptAction {
    private static final Logger logger = LoggerFactory.getLogger(LtdratioRptAction.class);
    private static DecimalFormat df = new DecimalFormat("###,##0.00");
    private List<LtdratioBean> ltdratioBeans = new ArrayList<LtdratioBean>();
    private List<LtdratioBean> ltdDistActname = new ArrayList<LtdratioBean>();
    private String yearmonth;
    HSSFCellStyle cellStyle_center;
    HSSFCellStyle cellStyle_right;
    HSSFFont fontStype;
    HSSFCellStyle cellStyle_head;
    HSSFDataFormat dataFormat;
    @ManagedProperty(value = "#{externalCustService}")
    private ExternalCustService externalCustService;

    private String startdate;
    private String enddate;
    private String companyname;
    private String deptname;

    @PostConstruct
    public void init() {
        DateTime dt = new DateTime();
        this.yearmonth = dt.minusMonths(1).toString("yyyy-MM");
    }

    public String onExport() {
        FacesContext context = FacesContext.getCurrentInstance();

        String reportPath = PropertyManager.getProperty("REPORT_ROOTPATH");
        String templateFileName = reportPath + "ltdratioModel.xls";
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
            String fileName = "ltdratioReport_" + startdate + "~" + enddate + ".xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");

            int daycnt = getMonthDays();
            if (daycnt < 1) {
                MessageUtil.addError("日期选择有误.");
                return null;
            } else if (daycnt > 62) {
                MessageUtil.addError("日期区间过大，请重新选择.");
                return null;
            }
            String company = "%" + this.companyname + "%";
            String dept = "%" + this.deptname + "%";
            ltdDistActname = externalCustService.selectedDistActname(this.startdate, this.enddate, company, dept);
            ltdratioBeans = externalCustService.seletectedLtdrRecords(this.startdate, this.enddate, company, dept);
            HSSFSheet sheet = workbook.getSheetAt(0);   //读取第一个工作簿
            fontStype = workbook.createFont();
            cellStyle_head = workbook.createCellStyle();
            cellStyle_head.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle_head.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle_head.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle_head.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle_head.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle_head.setAlignment(HSSFCellStyle.ALIGN_CENTER);

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
//            cellStyle_right.set

            dataFormat = workbook.createDataFormat();
            int rowIndex = 3;
            int cellIndex = 2;
//            int i = 1;
            String prevActname = "";
            String prevApcode = "";
            int colWidth1 = 18 * 300;
            for (int day = 1; day <= daycnt; day++) {
                setHeadCell(sheet, 2, day + cellIndex, String.valueOf(day));
                sheet.setColumnWidth(day + cellIndex, colWidth1);
            }
            setHeadCell(sheet, 2, daycnt + cellIndex + 1, "日平均");
            setHeadCell(sheet, 2, daycnt + cellIndex + 2, "日平均存贷比(%)");
            sheet.setColumnWidth(daycnt + cellIndex + 1, colWidth1);
            sheet.setColumnWidth(daycnt + cellIndex + 2, colWidth1);
            sheet.createRow(rowIndex);
            int i = 0;
            double avgD = 0.00; //存款日均
            double avgL = 0.00; //贷款日均
            double avgR = 0.00; //存贷日均比率
            for (LtdratioBean rcd : ltdDistActname) {
                HSSFRow hssfRow = sheet.createRow(rowIndex);
                HSSFRow hssfRow1 = sheet.createRow(rowIndex + 1);
                hssfRow.createCell(0);
                hssfRow1.createCell(1);
                setCell(sheet, rowIndex, 0, rcd.getActname());
                setCell(sheet, rowIndex, 1, rcd.getDeptname());
                setCell(sheet, rowIndex + 1, 0, "");
                setCell(sheet, rowIndex + 1, 1, "");
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 1, 1));
                setCell(sheet, rowIndex, 2, "存款余额");
                setCell(sheet, rowIndex + 1, 2, "贷款余额");
                for (int day = 1; day <= daycnt; day++) {
                    hssfRow.createCell(day + cellIndex);
                    hssfRow1.createCell(day + cellIndex);
                    setCell(sheet, rowIndex, cellIndex + day, (double) 0);
                    setCell(sheet, rowIndex + 1, cellIndex + day, (double) 0);
                    for (LtdratioBean record : ltdratioBeans) {
                        if (record.getActname().equals(rcd.getActname())) {
                            if (record.getApcode().equals("2")) {
                                if (Integer.parseInt(record.getDays()) == day) {
                                    setCell(sheet, rowIndex, cellIndex + day, record.getBalamt().doubleValue());
                                    avgD += record.getBalamt().doubleValue();
                                }
                            } else {
                                if (Integer.parseInt(record.getDays()) == day) {
                                    double dbbalamt = record.getBalamt().doubleValue() == 0 ? 0 : -record.getBalamt().doubleValue();
                                    setCell(sheet, rowIndex + 1, cellIndex + day, dbbalamt);
                                    avgL += dbbalamt;
                                }
                            }

                        }
                    }
                    if (day == daycnt) {
                        hssfRow.createCell(day + cellIndex + 1);
                        hssfRow1.createCell(day + cellIndex + 1);
                        hssfRow.createCell(day + cellIndex + 2);
                        hssfRow1.createCell(day + cellIndex + 2);
                        setCell(sheet, rowIndex, cellIndex + day + 1, avgD / daycnt);
                        setCell(sheet, rowIndex + 1, cellIndex + day + 1, avgL / daycnt);
//                        avgR = (avgD / daycnt) / (avgL / daycnt) * 100;
                        if (avgD == 0) {
                            setCell(sheet, rowIndex, cellIndex + day + 2, "");
                        } else {
                            avgR = (avgD / daycnt) / (avgL / daycnt)  * 100;
                            setCell(sheet, rowIndex, cellIndex + day + 2, avgR);
                        }
                        setCell(sheet, rowIndex + 1, cellIndex + day + 2, (double) 0);
                        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex + day + 2, cellIndex + day + 2));
                        avgD = 0.00;
                        avgL = 0.00;
                    }
                }
                rowIndex += 2;
            }

            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
        return null;
    }

    /**
     * 创建cell
     * 表头
     */
    public void setHeadCell(HSSFSheet sheet, int row, int index, String value) {
        HSSFRow hssfRow;
        hssfRow = sheet.getRow(row);
        HSSFCell cell = hssfRow.createCell(index);

        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        fontStype.setFontName("宋体");
        //设置字体大小;
        fontStype.setFontHeightInPoints((short) 16);
        //设置字体加粗
        fontStype.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle_head.setFont(fontStype);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle_head);
    }

    /**
     * 创建cell
     * 内容
     */
    public void setCell(HSSFSheet sheet, int row, int index, String value) {
        HSSFRow hssfRow;
        hssfRow = sheet.getRow(row);
        HSSFCell cell = hssfRow.createCell(index);

        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//           cell.setEncoding(XLS_ENCODING);
        //cellStyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle_center);
    }

    /**
     * 创建cell
     * 内容
     */
    public void setCell(HSSFSheet sheet, int row, int index, double value) {
        HSSFRow hssfRow;
        hssfRow = sheet.getRow(row);
        HSSFCell cell = hssfRow.getCell(index);

        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//           cell.setEncoding(XLS_ENCODING);
        //cellStyle_center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellValue(df.format(value));
        cell.setCellStyle(cellStyle_right);
    }

    private int getMonthDays() throws ParseException {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//        String datestr = yearmonth + "-01";
        Date startdt = dateformat.parse(startdate);
        c.clear();
        c.setTime(startdt);
        Calendar endC = Calendar.getInstance();
        Date enddt = dateformat.parse(enddate);
        endC.clear();
        endC.setTime(enddt);
        long days = TimeUnit.MILLISECONDS.toDays(endC.getTimeInMillis() - c.getTimeInMillis()) + 1;
        return (int) days;
    }


    public ExternalCustService getExternalCustService() {
        return externalCustService;
    }

    public void setExternalCustService(ExternalCustService externalCustService) {
        this.externalCustService = externalCustService;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
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

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}
