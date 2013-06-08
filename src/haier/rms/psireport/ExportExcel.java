package haier.rms.psireport;
//HSSFRichTextString
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: huangzunxiu
 * Date: 2010-9-28
 * Time: 5:48:23
 * To change this template use File | Settings | File Templates.
 */
public class ExportExcel {
    private HSSFWorkbook wb = null;
    private HSSFSheet sheet = null;
    private HSSFCellStyle headStyle = null;
    private HSSFCellStyle cstyle1 = null;
    private HSSFCellStyle cstyle2 = null;
    private HSSFCellStyle cstyle = null;
    private HSSFCellStyle headStyle1 = null;
    private int num=2;
    public ExportExcel() {
        this.wb=new HSSFWorkbook();
        this.sheet=wb.createSheet();
        headFont();
        bodyFont();
    }
    public ExportExcel(HSSFWorkbook wb, HSSFSheet sheet) {
        this.wb = wb;
        this.sheet = sheet;
        headFont();
        bodyFont();
    }

    /**
     * ����head��ʽ
     *
     */
    private void headFont() {

        HSSFFont headFont = wb.createFont();
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headFont.setFontName("����");
        headFont.setFontHeightInPoints((short) 11);
        headStyle = wb.createCellStyle();
        headStyle.setFont(headFont);
        HSSFFont headFont1 = wb.createFont();
        headFont1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        headFont1.setFontName("����");
        headFont1.setColor(HSSFColor.DARK_RED.index);
        headFont1.setFontHeightInPoints((short) 9);
        headStyle1=wb.createCellStyle();
        headStyle1.setFont(headFont1);
        sheet.setColumnWidth(0,4000);
        sheet.setColumnWidth(1,10000);
        sheet.setColumnWidth(2,5000);
        sheet.setColumnWidth(3,10000);
        sheet.setColumnWidth(4,3000);
        sheet.setColumnWidth(5,4000);
        sheet.setColumnWidth(6,4000);
        sheet.setColumnWidth(7,4000);
    }
    /**
     * ����body��ʽ
     *
     */
    private void bodyFont() {
        cstyle1=wb.createCellStyle();
        cstyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
         cstyle2=wb.createCellStyle();
        HSSFDataFormat format =wb.createDataFormat();
        cstyle2.setDataFormat(format.getFormat("yyyy-M-d"));
         cstyle=wb.createCellStyle();
        cstyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));

        
    }
    //������ͷ��������
    public void createHead(boolean fit) {
        HSSFRow row=sheet.createRow(0);
        this.createHeadCell(row,0,headStyle,"ϵͳ��ˮ��");
        this.createHeadCell(row,1,headStyle,"��ҵ����");
        this.createHeadCell(row,2,headStyle,"�����˺�");
        this.createHeadCell(row,3,headStyle,"���ݺ�");
        this.createHeadCell(row,4,headStyle,"��������");
        this.createHeadCell(row,5,headStyle,"�跽���");
        this.createHeadCell(row,6,headStyle,"�������");
        this.createHeadCell(row,7,headStyle,"���");
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short)500);
        this.createHeadCell(row1,0,headStyle1,"����ֵ��ʽ��");
        this.createHeadCell(row1,1,headStyle1,"(�ı���ʽ)");
        this.createHeadCell(row1,2,headStyle1,"(�ı���ʽ)");
        this.createHeadCell(row1,3,headStyle1,"(�ı���ʽ)");
        this.createHeadCell(row1,4,headStyle1,"(���ڸ�ʽ ��2008-01-01)");
        this.createHeadCell(row1,5,headStyle1,"(��ֵ��ʽ���뱣����λС��)");
        this.createHeadCell(row1,6,headStyle1,"(��ֵ��ʽ���뱣����λС��)");
        this.createHeadCell(row1,7,headStyle1,"(��ֵ��ʽ���뱣����λС��)");

    }
    //����body
    public void createBody(boolean fit,nsmbean n) throws ParseException {
        HSSFRow row=sheet.createRow(num++);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        Date dt = sdf.parse(n.getComdate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
                
//        String dd = sdf.format(dt);
        double br = Double.parseDouble(n.getBorrowamt());
//        DecimalFormat df = new DecimalFormat("0.00");
//        String de=df.format(br);
        double loan = Double.parseDouble(n.getLoanamt());
        double sum = Double.parseDouble(n.getSumamt());
        this.createBodyCell(row,0,cstyle1,n.getSyslscode());
        this.createBodyCell(row,1,cstyle1,n.getCompanyName());
        this.createBodyCell(row,2,cstyle1,n.getBankacct());
        this.createBodyCell(row,3,cstyle1,n.getCommerce());
        this.createBodyCell(row,4,cstyle2, cal);
        this.createBodyCell(row,5,cstyle,br);
        this.createBodyCell(row,6,cstyle,loan);
        this.createBodyCell(row,7,cstyle,sum);
    }
    /**
    * �������ݵ�Ԫ�� 
    *@param row ��
    * @param col short�͵�������
    * @param style ���
    * @param val ��ֵ
    */
    public void createHeadCell(HSSFRow row,int col,HSSFCellStyle style,String val) {
    HSSFCell cell = row.createCell(col);
    cell.setCellValue(new HSSFRichTextString(val));
    cell.setCellType(HSSFCell.ENCODING_UTF_16);
    cell.setCellStyle(style);
    }
    public void createBodyCell(HSSFRow row,int col,HSSFCellStyle style,String val) {
    HSSFCell cell = row.createCell(col);
    cell.setCellValue(val);
    cell.setCellStyle(style);
    }
    public void createBodyCell(HSSFRow row,int col,HSSFCellStyle style,double val) {
        HSSFCell cell = row.createCell(col);

        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell.setCellStyle(style);
        cell.setCellValue(val);
    }
    public void createBodyCell(HSSFRow row,int col,HSSFCellStyle style,Calendar val) {
        HSSFCell cell = row.createCell(col);

        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
         
        cell.setCellValue(val.getTime());
        cell.setCellStyle(style);
    }
    /**
    * ����EXCEL�ļ� 
    *
    * @param fileName �ļ���
    */
    public void outputExcel(String fileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(fileName));
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
