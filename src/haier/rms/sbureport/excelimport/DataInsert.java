package haier.rms.sbureport.excelimport;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-7-6
 * Time: 15:10:01
 * To change this template use File | Settings | File Templates.
 */

import haier.rms.xls.SbuintbizBean;
import haier.rms.xls.SbuinvestbizBean;
import haier.rms.xls.SbuotherincomeBean;
import haier.rms.xls.XLSDataList;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;
import pub.platform.db.RecordSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-7-5
 * Time: 17:54:58
 * To change this template use File | Settings | File Templates.
 */
public class DataInsert {

    DatabaseConnection dbconn = null;
    RecordSet rs = new RecordSet();

    public String runDataInsert(String path, String dataDate) {
        XLSDataList dtlst = new XLSDataList();
        ArrayList arraylst1 = dtlst.getList(path, "Ͷ������ҵ��");
        ArrayList arraylst2 = dtlst.getList(path, "����ҵ��");
        ArrayList arraylst3 = dtlst.getList(path, "��������");
        try {
            dbconn = ConnectionManager.getInstance().get();  //ת��ʱ��
            dbconn.begin();
            //delete datadate dada
            String strSql1 = "";
            //delete ��������=����dataDate��Ԫ����  table: 1 sbu_intbiz ; 2
            strSql1 = "delete sbu_intbiz where datadate = '" + dataDate + "'";
            int rtn = 0;
            rtn = dbconn.executeUpdate(strSql1);
            if (rtn < 0) throw new Exception();

            strSql1 = "delete sbu_investbiz where datadate = '" + dataDate + "'";
            rtn = dbconn.executeUpdate(strSql1);
            if (rtn < 0) throw new Exception();

            strSql1 = "delete sbu_otherincome where datadate = '" + dataDate + "'";
            rtn = dbconn.executeUpdate(strSql1);
            if (rtn < 0) throw new Exception();

            insertInvestData(arraylst1, dataDate);
            insertIntData(arraylst2, dataDate);
            InsertOtherData(arraylst3, dataDate);

            dbconn.commit();
            return "0";
//            if (rs != null) {
//                rs.close();
//                return "0";
//            } else {
//                return "-1";
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
            dbconn.rollback();
            return "-1";
        } finally {
            dbconn.close();
        }
    }
    //Ͷ�����б��������

    public void insertInvestData(ArrayList dtlst, String dataDate) throws Exception {
        String strSql = " insert into sbu_investbiz (productid, productname, biztype, investamt, " +
                "currtype, startdate, enddate, rate, dailyincome, operdate, opertime, remark,datadate) values (";
        String strSql1 = "";
        Iterator it = dtlst.iterator();
        String productID = ""; //����
        String productName = "";  //��Ʒ���� or ��λ����
        String biztype = "";   //ҵ������
        Double investamt;
        String currtype = "";  //����
        String startdate = "";
        String enddate = "";
        Double rate;       //��ͬ������
        Double dailyincome;   //riyilv
        Date date = new Date();
        SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operdateAll = formattxt.format(date);
        String operdate = operdateAll.substring(0, 10).replace("-", "");
        String opertime = operdateAll.substring(11).replace("-", "");
        String remark = null;
        while (it.hasNext()) {
            SbuinvestbizBean siv = (SbuinvestbizBean) it.next();
            productID = siv.getProductID();
            productName = siv.getProductName();
            biztype = siv.getBiztype();
            investamt = siv.getInvestamt();
            currtype = siv.getCurrtype();
            startdate = siv.getStartdate();
            enddate = siv.getEnddate();
            rate = siv.getRate();
            dailyincome = siv.getDailyincome();
            StringBuilder strBSql = new StringBuilder();
            strSql1 = "'" + productID + "','" + productName + "','" + biztype + "'," +
                    investamt + ",'" + currtype + "','" + startdate + "','" + enddate + "'," + rate + "," +
                    dailyincome + ",'" + operdate + "','" + opertime + "'," + remark + ",'" + dataDate + "') ";
            strBSql.append(strSql + strSql1);
            String runStrSql = strBSql.toString();
            int rtn = dbconn.executeUpdate(runStrSql);
            if (rtn <= 0) {
                throw new Exception();
            }
        }
    }
    //����ҵ������

    public void insertIntData(ArrayList dtlst, String dataDate) throws Exception {
        String strSql = " insert into sbu_intbiz (PRODUCTID, PRODUCTNAME, loantype, outboundflag, loanamt, " +
                "currtype, startdate, enddate, rate, dailyincome, operdate, opertime, remark,datadate) values (";
        String strSql1 = "";
        Iterator it = dtlst.iterator();
        String productID = ""; //����
        String productName = "";  //��Ʒ���� or ��λ����
        String loantype = "";   //ҵ������
        String outboundflag = "0";       //���������־ default:0
        Double loanamt;
        String currtype = "";  //����
        String startdate = "";
        String enddate = "";
        Double rate;       //��ͬ������
        Double dailyincome;   //riyilv
        Date date = new Date();
        SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operdateAll = formattxt.format(date);
        String operdate = operdateAll.substring(0, 10).replace("-", "");
        String opertime = operdateAll.substring(11).replace("-", "");
        String remark = null;
        while (it.hasNext()) {
            SbuintbizBean sbb = (SbuintbizBean) it.next();
            productID = sbb.getProductID();
            productName = sbb.getProductName();
            loantype = sbb.getLoantype();
            outboundflag = sbb.getOutboundflag();
            loanamt = sbb.getLoanamt();
            currtype = sbb.getCurrtype();
            startdate = sbb.getStartdate();
            enddate = sbb.getEnddate();
            rate = sbb.getRate();
            dailyincome = sbb.getDailyincome();
            StringBuilder strBSql = new StringBuilder();
            strSql1 = "'" + productID + "','" + productName + "','" + loantype + "'," + outboundflag + "," +
                    loanamt + ",'" + currtype + "','" + startdate + "','" + enddate + "'," + rate + "," +
                    dailyincome + ",'" + operdate + "','" + opertime + "'," + remark + ",'" + dataDate + "') ";
            strBSql.append(strSql + strSql1);
            String runStrSql = strBSql.toString();
            int rtn = dbconn.executeUpdate(runStrSql);
            if (rtn <= 0) {
                throw new Exception();
            }
        }
    }
    //������������

    public void InsertOtherData(ArrayList dtlst, String dataDate) throws Exception {
        String strSql = " insert into sbu_otherincome (productid, productname, biztype, bizamt, currtype, " +
                "startdate, enddate, rate, dailyincome, operdate, opertime, remark,datadate) values (";

        String strSql1 = "";
        Iterator it = dtlst.iterator();
        String productID = ""; //����
        String biztype = "";   //ҵ������
        Double investamt;            //���
        String currtype = "";  //����
        String startdate = "";
        String enddate = "";
        Double rate;       //��ͬ������
        Double dailyincome;   //riyilv
        Date date = new Date();
        SimpleDateFormat formattxt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String operdateAll = formattxt.format(date);
        String operdate = operdateAll.substring(0, 10).replace("-", "");
        String opertime = operdateAll.substring(11).replace("-", "");
        String remark = null;
        String productName = null;
//        try{
        while (it.hasNext()) {
            SbuotherincomeBean soi = (SbuotherincomeBean) it.next();
            productID = soi.getProductID();
            biztype = soi.getBiztype();
            investamt = soi.getInvestamt();
            currtype = soi.getCurrtype();
            startdate = soi.getStartdate();
            enddate = soi.getEnddate();
            rate = soi.getRate();
            dailyincome = soi.getDailyincome();
            StringBuilder strBSql = new StringBuilder();
            strSql1 = "'" + productID + "'," + productName + ",'" + biztype + "'," +
                    investamt + ",'" + currtype + "','" + startdate + "','" + enddate + "'," + rate + "," +
                    dailyincome + ",'" + operdate + "','" + opertime + "'," + remark + ",'" + dataDate + "') ";
            strBSql.append(strSql + strSql1);
            String runStrSql = strBSql.toString();
            int rtn = dbconn.executeUpdate(runStrSql);
            if (rtn <= 0) {
                throw new Exception();
            }
        }
    }
}
