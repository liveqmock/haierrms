package haier.rms.sbs.detlbook;

import haier.rms.sbs.detlbook.sbs8853.T8853ResponseRecord;
import haier.rms.sbs.detlbook.sbs8853.T8853Service;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.primefaces.component.datatable.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-1-17
 * Time: ����3:39
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@ViewScoped
public class ActDetlBookHandler {
    private static final Logger logger = LoggerFactory.getLogger(ActDetlBookHandler.class);

    private List<T8853ResponseRecord> detlList = new ArrayList<T8853ResponseRecord>();
    private T8853ResponseRecord detlRecord = new T8853ResponseRecord();

    private int totalcount = 0;
    private BigDecimal totalamt = new BigDecimal(0);

    private String actnum;
    private String startdate;
    private Date uiStartdate;
    private String enddate;
    private Date uiEnddate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    HSSFCellStyle cellStyle;
    HSSFDataFormat dataFormat;


    public List<T8853ResponseRecord> getDetlList() {
        return detlList;
    }

    public void setDetlList(List<T8853ResponseRecord> detlList) {
        this.detlList = detlList;
    }

    public T8853ResponseRecord getDetlRecord() {
        return detlRecord;
    }

    public void setDetlRecord(T8853ResponseRecord detlRecord) {
        this.detlRecord = detlRecord;
    }


    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public BigDecimal getTotalamt() {
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

    public Date getUiStartdate() {
        return uiStartdate;
    }

    public void setUiStartdate(Date uiStartdate) {
        this.uiStartdate = uiStartdate;
    }

    public Date getUiEnddate() {
        return uiEnddate;
    }

    public void setUiEnddate(Date uiEnddate) {
        this.uiEnddate = uiEnddate;
    }

    //==============================
    @PostConstruct
    public void postConstruct() {

        Date date = new Date();
        String currDate = sdf.format(date);
//        this.startdate = currDate.substring(0, 6) + "01";
//        this.startdate = currDate;
//        this.enddate = currDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        this.uiStartdate = cal.getTime();
        this.uiEnddate = cal.getTime();
    }

    public String query() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {

            if (this.actnum.length() == 14) {
                this.actnum = "8010" + this.actnum;
            }
            if (this.actnum.length() != 18) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "������14λ��18λ�ʺš�", null));
                return null;
            }

            if (uiEnddate.before(uiStartdate)) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "��������˳�����", null));
                return null;
            }

            this.startdate = new SimpleDateFormat("yyyyMMdd").format(uiStartdate);
            this.enddate = new SimpleDateFormat("yyyyMMdd").format(uiEnddate);

            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:pdt");
//            dataTable.clearInitialState();
//            dataTable.
            dataTable.setFirst(0);
            dataTable.setPage(1);
            queryRecordsFromSBS();
        } catch (Exception e) {
            logger.error("��ѯʱ���ִ���", e);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "��ѯʱ���ִ���", "�������ݿ�������⡣"));
        }
        return null;
    }

    public String onExport() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (this.detlList.size() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "���Ȳ�ѯ���ݡ�", ""));
            return null;
        }

        if (this.detlList.size() >= 30000) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "��ѯ������̫�࣬�ѳ���30000��������С��ѯ��Χ��", ""));
            return null;
        }

        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream os = response.getOutputStream(); //��������
            response.reset();
            String fileName = "acctdetail.xls";
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");

            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet();
            //workbook.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);

            int i =1;
            int col = 0;

            HSSFRow row = sheet.createRow(i);
            cellStyle =  workbook.createCellStyle();
            dataFormat = workbook.createDataFormat();

            setCell(row, col, "�˺�");
            col++;
            setCell(row, col, "��������");
            col++;

            setCell(row, col, "���׹�Ա");
            col++;

            setCell(row, col, "��Ʊ�׺�");
            col++;

            setCell(row, col, "�������");
            col++;

            setCell(row, col, "������");
            col++;

            setCell(row, col, "���");
            col++;

            setCell(row, col, "ժҪ");


//            int i = 0;
            for (T8853ResponseRecord record : this.detlList) {
                row = sheet.createRow(i + 1);

                col = 0;

                setCell(row, col, record.getFormatedActnum());
                col++;
                setCell(row, col, record.getStmdat());
                col++;

                setCell(row, col, record.getTlrnum());
                col++;

                setCell(row, col, record.getVchset());
                col++;

                setCell(row, col, record.getSetseq());
                col++;

                setCell(row, col, transAmt(record.getTxnamt()));
                col++;

                setCell(row, col, transAmt(record.getLasbal()));
                col++;

                setCell(row, col, record.getFurinf());
                i++;
            }

            sheet.autoSizeColumn(0);
            sheet.setColumnWidth(1, (int) 35.6 * 80);
            sheet.setColumnWidth(2, (int) 35.6 * 80);
            sheet.setColumnWidth(3, (int) 35.6 * 80);
            sheet.setColumnWidth(4, (int) 35.6 * 80);
            sheet.setColumnWidth(5, (int) 35.6 * 150);
            sheet.setColumnWidth(6, (int)35.6*150);
            sheet.setColumnWidth(7, (int)35.6*300);

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

    private static void createCell(int cellIndex, String value, HSSFRow row, HSSFCell cell, HSSFCellStyle cellstyle) {
        cell = row.createCell(cellIndex);
        cell.setCellValue(new HSSFRichTextString(value));
        cell.setCellStyle(cellstyle);
    }


    /**
     * ���õ�Ԫ��
     *
     * @param index �к�
     * @param value ��Ԫ�����ֵ
     */
    public void setCell(HSSFRow row, int index, String value) {
        HSSFCell cell = row.createCell(index);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//           cell.setEncoding(XLS_ENCODING);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cell.setCellValue(value);
//        cell.setCellStyle(cellStyle);
    }

    public void setCell(HSSFRow row, int index, Double value) {
        HSSFCell cell = row.createCell(index);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//           cell.setEncoding(XLS_ENCODING);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyle.setDataFormat(dataFormat.getFormat(" #,##0.00 "));
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }


    public String reset() {
        this.detlRecord = new T8853ResponseRecord();
        return null;
    }

    private void queryRecordsFromSBS() {

        T8853Service t8853service = new T8853Service();

        List<String> requestList = new ArrayList<String>();

        //�������̶�ֵ
        requestList.add("111111");
        requestList.add("010");
        requestList.add("60");
        requestList.add("010");
        //�������ѯ����
        requestList.add(this.actnum);
        requestList.add(this.startdate);
        requestList.add(this.enddate);

        t8853service.setRequestParam(requestList);

        try {
            t8853service.querySBS();
            detlList = t8853service.getReponseRecords();
        } catch (Exception e) {
            List<String> msgs = t8853service.getMsgs();
            if (msgs.size() > 0) {
                FacesContext context = FacesContext.getCurrentInstance();
                for (String msg : msgs) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
                }
            }
            logger.error("SBS��ѯ����쳣", e);
        }


    }


}
