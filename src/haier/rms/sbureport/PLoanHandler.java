package haier.rms.sbureport;

import haier.rms.model.CubeFactActrInfo;
import haier.rms.model.CubeFactFcstInfo;
import haier.rms.model.PLoanInfo;
import haier.rms.model.PLoanKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ��ȡ�Ŵ�ϵͳ�ķ������ݺ������Ŵ�ϵͳ�е����� ��������CUBE��
 * User: zhanrui
 * Date: 2010-4-18
 * Time: 17:10:39
 * To change this template use File | Settings | File Templates.
 */
public class PLoanHandler extends AbstractMainETL {
    private final Log logger = LogFactory.getLog(this.getClass().getName());

    private String bussItem;

    @Override
    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {
        this.bussItem = bussItem;

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        Date startdate = null;
        Date enddate = null;

        try {
            startdate = sdf_yyyymmdd.parse(strStartDate);
            enddate = sdf_yyyymmdd.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�������������");
            throw new RuntimeException(e);
        }
        cal_start.setTime(startdate);
        cal_end.setTime(enddate);

        //TODO ���жϲ����Ƿ���ȷ

        do{
            String strCurrDate = sdf_yyyymmdd.format(cal_start.getTime());
            processOneDay(strCurrDate);
            cal_start.add(Calendar.DATE, 1);
            logger.info("�������ݣ�" + strCurrDate + "��������ɡ�");
        }while(!cal_start.after(cal_end));

    }

    private void deleteOneDayRecord(String strCurrdate) {

        session.delete("deleteCubeFactActrForDate", strCurrdate+this.bussItem);
        session.delete("deleteCubeFactPlanForDate", strCurrdate+this.bussItem);
        session.delete("deleteCubeFactFcstForDate", strCurrdate+this.bussItem);
        session.commit();
    }

    public List<PLoanInfo> getOnedayIntrList(String strCurrdate) {
        PLoanKey key = new PLoanKey();

        key.setCurrdate(strCurrdate);
        key.setCurrmonth(strCurrdate.substring(0, 6));

        Date currdate = new Date();
        try {
            currdate = sdf_yyyymmdd.parse(strCurrdate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�������������");
            throw new RuntimeException(e);
        }
        Calendar currcal = Calendar.getInstance();
        currcal.setTime(currdate);
        //��һ��
        currcal.add(Calendar.MONTH, 1);
        Date nextmonth = currcal.getTime();
        String strNextMonth = sdf_yyyymmdd.format(nextmonth).substring(0, 6);

        key.setNextmonth(strNextMonth);
        List<PLoanInfo> list=null;
        if ("5014".equals(this.bussItem)) {  //����
            list = session.selectList("PLoanInfo.selectPLoanDayIntr", key);
        }else if ("5037".equals(this.bussItem)) {//�����Ŵ�
            list = session.selectList("PLoanInfo.selectXFDayIntr", key);
        } else {
            logger.error("BUSSITEM����");
            throw new RuntimeException("BUSSITEM����");
        }

        return list;
    }

    //�������ݵ���ʵ����

    private void insertOneDayRecord2Actr(List<PLoanInfo> infos, String strCurrdate) {

        CubeFactActrInfo actrinfo = new CubeFactActrInfo();

        actrinfo.setDim_buss_item(this.bussItem);
        actrinfo.setDim_currency("001");
        actrinfo.setDim_oprt_dept("000");
        actrinfo.setDim_cust_mngr("00000000");
        actrinfo.setDim_data_date(strCurrdate);

        Date currdate = new Date();
        try {
            currdate = sdf_yyyymmdd.parse(strCurrdate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�������������");
            throw new RuntimeException(e);
        }

        int weeks = getWeekOfYear(currdate);

        java.text.DecimalFormat formater = new java.text.DecimalFormat("00");
        actrinfo.setDim_buss_date(strCurrdate.substring(0, 4) + "W" + formater.format(weeks));

        for (PLoanInfo loaninfo : infos) {
            actrinfo.setDim_cust_no(loaninfo.getLbbh());
            actrinfo.setFact_value(loaninfo.getLxje());
            session.insert("PLoanInfo.insertCubeFactActr", actrinfo);
        }
        session.commit();

    }


    //�������ݵ�Ԥ�����

    private void insertOneDayRecord2Fcst(List<PLoanInfo> infos, String strDatadate, String strBussDate) {

        CubeFactFcstInfo fcstinfo = new CubeFactFcstInfo();

        fcstinfo.setDim_buss_item(this.bussItem);
        fcstinfo.setDim_currency("001");
        fcstinfo.setDim_oprt_dept("000");
        fcstinfo.setDim_cust_mngr("00000000");
        fcstinfo.setDim_data_date(strDatadate);

        fcstinfo.setTag_fcst_type("B"); //����ΪԤ��

        Date bussdate = new Date();
        try {
            bussdate = sdf_yyyymmdd.parse(strBussDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�������������");
            throw new RuntimeException(e);
        }

        int weeks = getWeekOfYear(bussdate);

        java.text.DecimalFormat formater = new java.text.DecimalFormat("00");
        fcstinfo.setDim_buss_date(strDatadate.substring(0, 4) + "W" + formater.format(weeks));

        for (PLoanInfo loaninfo : infos) {
            fcstinfo.setDim_cust_no(loaninfo.getLbbh());
            fcstinfo.setFact_value(loaninfo.getLxje());
            session.insert("PLoanInfo.insertCubeFactFcst", fcstinfo);
        }
        session.commit();

    }

    private void processFcstData(String strCurrdate) {

        Calendar cal_curr = Calendar.getInstance();
        Date currdate = null;

        try {
            currdate = sdf_yyyymmdd.parse(strCurrdate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("�������������");
            throw new RuntimeException(e);
        }


        //�������һ��
        cal_curr.setTime(currdate);
        cal_curr.setFirstDayOfWeek(Calendar.MONDAY);
        cal_curr.set(Calendar.DAY_OF_WEEK, cal_curr.getFirstDayOfWeek() + 6); //SUNDAY
        //����һ����
        cal_curr.add(Calendar.DATE, 1);

        for (int k = 0; k < 6; k++) {
            List<PLoanInfo> baseinfos = new LinkedList();
            String strDate = null;
            for (int i = 0; i < 7; i++) {
                Date date = cal_curr.getTime();
                strDate = sdf_yyyymmdd.format(date);
                //��ȡĳ������
                List<PLoanInfo> dbinfos = getOnedayIntrList(strDate);
                if (baseinfos.size() == 0) {
                    for (PLoanInfo dbinfo : dbinfos) {
                        baseinfos.add(dbinfo);
                    }
                } else {
                    for (PLoanInfo dbinfo : dbinfos) {
                        String db_lbbh = dbinfo.getLbbh();
                        double db_lxje = dbinfo.getLxje();
                        for (PLoanInfo baseinfo : baseinfos) {
                            String base_lbbh = baseinfo.getLbbh();
                            if (db_lbbh.equals(base_lbbh)) {
                                baseinfo.setLxje(baseinfo.getLxje() + db_lxje);
                            }
                        }
                    }
                }
                cal_curr.add(Calendar.DATE, 1);
                System.out.println("===================" + sdf_yyyymmdd.format(cal_curr.getTime()) + " ===== k: " + k + " ===== i: " + i);
            }
            //����CUBE fcst����
            String strBussDate = strDate;
            String strDataDate = strCurrdate;
            insertOneDayRecord2Fcst(baseinfos, strDataDate, strBussDate);
        }
    }

    private void processOneDay(String strCurrdate) {
        try {
            //ɾ����������
            deleteOneDayRecord(strCurrdate);

            //��ȡ��������
            List<PLoanInfo> infos = getOnedayIntrList(strCurrdate);

            //����CUBE����
            insertOneDayRecord2Actr(infos, strCurrdate);

            //����Ԥ���T1-T6�����ݣ�
            processFcstData(strCurrdate);
        } catch (Exception e) {
            logger.error("������/�����Ŵ����ݳ������⡣");
            e.printStackTrace();
            throw new RuntimeException("������/�����Ŵ����ݳ������⡣");
        }
    }


}
