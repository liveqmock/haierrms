package haier.rms.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-8
 * Time: 11:44:45
 * To change this template use File | Settings | File Templates.
 */
public class XLSDataList {
    public ArrayList getList(String strPath,String sheetName){
        ArrayList table = new ArrayList();
//        ArrayList record = new ArrayList();
        //poi����excel
        try{
            InputStream input = null;
            POIFSFileSystem fs = null;
            HSSFWorkbook wb = null;
            input = new FileInputStream(strPath);
            fs = new POIFSFileSystem(input);
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheet(sheetName);
            if (sheet != null){
                int rowCount = sheet.getLastRowNum();
                HSSFCell cell0 = null;              //
                HSSFCell cell1 = null;              //
                HSSFCell cell2 = null;              //
                HSSFCell cell3 = null;              //
                HSSFCell cell4 = null;              //
                HSSFCell cell5 = null;              //
                HSSFCell cell6 = null;              //
                HSSFCell cell7 = null;              //
                HSSFCell cell8 = null;              //
                String productID = ""; //����
                String productName = "";  //��Ʒ���� or ��λ����
                String biztype = "";   //ҵ������
                Double investamt;
                String currtype = "";  //����
                String startdate = "";
                String enddate = "";
                Double rate;       //��ͬ������
                Double dailyincome;   //riyilv
                Date dt = new Date(0); 
                if (sheetName.equals("Ͷ������ҵ��")){
                    for (int i = 4;i <= rowCount;i++){
                        ArrayList record = new ArrayList();
                        //���
                        cell0 = sheet.getRow(i).getCell(0);
                        switch (cell0.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                productID = cell0.getStringCellValue().trim();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                productID = Integer.toString((int) (cell0.getNumericCellValue()));
                                break;
                            default:
                                productID = null;
                                break;
                        }
                        record.add(productID);
                        //��Ʒ����
                        cell1 = sheet.getRow(i).getCell(1);
                        switch (cell1.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                productName = cell1.getStringCellValue().trim();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                productName = Integer.toString((int) (cell1.getNumericCellValue()));
                                break;
                            default:
                                productName = null;
                                break;
                        }
                        record.add(productName);
                        //ҵ������
                        cell2 = sheet.getRow(i).getCell(2);
                        biztype = cell2.getStringCellValue();
                        record.add(biztype);
                        //Ͷ�ʽ��   loanamt
                        cell3 = sheet.getRow(i).getCell(3);
                        investamt = cell3.getNumericCellValue();
                        record.add(investamt);
                        //����   currtype
                        cell4 = sheet.getRow(i).getCell(4);
                        currtype = cell4.getStringCellValue().substring(0, 3);
                        record.add(currtype);
                        //��ʼ��
                        cell5 = sheet.getRow(i).getCell(5);
                        dt = HSSFDateUtil.getJavaDate(new Double(cell5.getNumericCellValue()));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        startdate = sdf.format(dt).replace("-","");
                        record.add(startdate);
                        //������
                        cell6 = sheet.getRow(i).getCell(6);
                        dt = HSSFDateUtil.getJavaDate(new Double(cell6.getNumericCellValue()));
                        enddate = sdf.format(dt).replace("-","");
                        record.add(enddate);
                        //��ͬ������
                        cell7 = sheet.getRow(i).getCell(7);
                        rate = cell7.getNumericCellValue();
                        record.add(rate);
                        //rishouyi
                        cell8 = sheet.getRow(i).getCell(8);
                        dailyincome = cell8.getNumericCellValue();
                        record.add(dailyincome);
                        table.add(getSbuinvestbizBean(record));
                    }
                } else if (sheetName.equals("����ҵ��")){
                    String loantype = "";   //ҵ������
                    String outboundflag = "0";       //���������־ default:0
                    Double loanamt;
                    HSSFCell cell9 = null;              //
                    for (int i = 4;i <= rowCount;i++){
                        ArrayList record = new ArrayList();
                        //���
                        cell0 = sheet.getRow(i).getCell(0);
                        switch (cell0.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                productID = cell0.getStringCellValue().trim();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                productID = Integer.toString((int) (cell0.getNumericCellValue()));
                                break;
                            default:
                                productID = null;
                                break;
                        }
                        record.add(productID);
                        //��λ����
                        cell1 = sheet.getRow(i).getCell(1);
                        switch (cell1.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                productName = cell1.getStringCellValue().trim();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                productName = Integer.toString((int) (cell1.getNumericCellValue()));
                                break;
                            default:
                                productName = null;
                                break;
                        }
                        record.add(productName);
                        //������ʽ
                        cell2 = sheet.getRow(i).getCell(2);
                        loantype = cell2.getStringCellValue().trim();
                        record.add(loantype);
                        //�Ƿ�������
                        cell3 = sheet.getRow(i).getCell(3);
                        outboundflag = cell3.getStringCellValue();
                        outboundflag = outboundflag.equals("��") ? "1" : "0";
                        record.add(outboundflag);
                        //Ͷ�ʽ��   loanamt
                        cell4 = sheet.getRow(i).getCell(4);
                        loanamt = cell4.getNumericCellValue();
                        record.add(loanamt);
                        //����   currtype
                        cell5 = sheet.getRow(i).getCell(5);
                        currtype = cell5.getStringCellValue().substring(0, 3);
                        record.add(currtype);
                        //��ʼ��
                        cell6 = sheet.getRow(i).getCell(6);
                        dt = HSSFDateUtil.getJavaDate(new Double(cell6.getNumericCellValue()));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        startdate = sdf.format(dt).replace("-","");
                        record.add(startdate);
                        //������
                        cell7 = sheet.getRow(i).getCell(7);
                        dt = HSSFDateUtil.getJavaDate(new Double(cell7.getNumericCellValue()));
                        enddate = sdf.format(dt).replace("-","");
                        record.add(enddate);
                        //��ͬ������
                        cell8 = sheet.getRow(i).getCell(8);
                        rate = cell8.getNumericCellValue();
                        record.add(rate);
                        //rishouyi
                        cell9 = sheet.getRow(i).getCell(9);
                        dailyincome = cell9.getNumericCellValue();
                        record.add(dailyincome);
                        table.add(getSbuintbizBean(record));
                    }
                } else if (sheetName.equals("��������")){
                        Double bizamt;            //���
                        for (int i = 4;i <= rowCount;i++){
                            ArrayList record = new ArrayList();
                            //���
                            cell0 = sheet.getRow(i).getCell(0);
                            switch (cell0.getCellType()) {
                                case HSSFCell.CELL_TYPE_STRING:
                                    productID = cell0.getStringCellValue().trim();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    productID = Integer.toString((int) (cell0.getNumericCellValue()));
                                    break;
                                default:
                                    productID = null;
                                    break;
                            }
                            record.add(productID);
                            //ҵ������
                            cell1 = sheet.getRow(i).getCell(1);
                            biztype = cell1.getStringCellValue();
                            record.add(biztype);
                            //���
                            cell2 = sheet.getRow(i).getCell(2);
                            bizamt = cell2.getNumericCellValue();
                            record.add(bizamt);
                            //����   currtype
                            cell3 = sheet.getRow(i).getCell(3);
                            currtype = cell3.getStringCellValue().substring(0, 3);
                            record.add(currtype);
                            //��ʼ��
                            cell4 = sheet.getRow(i).getCell(4);
                            dt = HSSFDateUtil.getJavaDate(new Double(cell4.getNumericCellValue()));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            startdate = sdf.format(dt).replace("-","");
                            record.add(startdate);
                            //������
                            cell5 = sheet.getRow(i).getCell(5);
                            dt = HSSFDateUtil.getJavaDate(new Double(cell5.getNumericCellValue()));
                            enddate = sdf.format(dt).replace("-","");
                            record.add(enddate);
                            //��ͬ������
                            cell6 = sheet.getRow(i).getCell(6);
                            rate = cell6.getNumericCellValue();
                            record.add(rate);
                            //rishouyi
                            cell7 = sheet.getRow(i).getCell(7);
                            dailyincome = cell7.getNumericCellValue();
                            record.add(dailyincome);
                            table.add(getSbuotherincomeBean(record));
                        }
                    }
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return null;
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            }
            return table;
        }



    //�����ݸ������ҵ��bean
    public SbuintbizBean getSbuintbizBean(ArrayList contents){
        SbuintbizBean sbb = new SbuintbizBean();
        if (contents != null && !(contents.isEmpty())){
            sbb.setProductID(contents.get(0).toString());
            sbb.setProductName(contents.get(1).toString());
            sbb.setLoantype(contents.get(2).toString());
            sbb.setOutboundflag(contents.get(3).toString());
            sbb.setLoanamt(Double.valueOf(contents.get(4).toString()).doubleValue());
            sbb.setCurrtype(contents.get(5).toString());
            sbb.setStartdate(contents.get(6).toString());
            sbb.setEnddate(contents.get(7).toString());
            sbb.setRate(Double.valueOf(contents.get(8).toString()).doubleValue());
            sbb.setDailyincome(Double.valueOf(contents.get(9).toString()).doubleValue());
            sbb.setDatadate(null);
        }
        return sbb;
    }
    //�����ݸ���Ͷ������bean
    public SbuinvestbizBean getSbuinvestbizBean(ArrayList contents){
        SbuinvestbizBean sivb = new SbuinvestbizBean();
        if (contents != null && !(contents.isEmpty())){
            sivb.setProductID(contents.get(0).toString());
            sivb.setProductName(contents.get(1).toString());
            sivb.setBiztype(contents.get(2).toString());
            sivb.setInvestamt(Double.valueOf(contents.get(3).toString()).doubleValue());
            sivb.setCurrtype(contents.get(4).toString());
            sivb.setStartdate(contents.get(5).toString());
            sivb.setEnddate(contents.get(6).toString());
            sivb.setRate(Double.valueOf(contents.get(7).toString()).doubleValue());
            sivb.setDailyincome(Double.valueOf(contents.get(8).toString()).doubleValue());
            sivb.setDatadate(null);
        }
        return sivb;
    }
    //�����ݸ�������ҵ��bean
    public SbuotherincomeBean getSbuotherincomeBean(ArrayList contents){
        SbuotherincomeBean soi = new SbuotherincomeBean();
        if (contents != null && !(contents.isEmpty())){
            soi.setProductID(contents.get(0).toString());
            soi.setBiztype(contents.get(1).toString());
            soi.setInvestamt(Double.valueOf(contents.get(2).toString()).doubleValue());
            soi.setCurrtype(contents.get(3).toString());
            soi.setStartdate(contents.get(4).toString());
            soi.setEnddate(contents.get(5).toString());
            soi.setRate(Double.valueOf(contents.get(6).toString()).doubleValue());
            soi.setDailyincome(Double.valueOf(contents.get(7).toString()).doubleValue());
            //? soi.setEnddate(null);
            soi.setDatadate(null);

        }
        return soi;
    }
}
