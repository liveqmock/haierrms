package haier.rms.sbureport;

import haier.rms.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.tools.Airth;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 9:20:54
 * To change this template use File | Settings | File Templates.
 */
public class LastYearDataETL extends AbstractMainETL {
    private static final Log logger = LogFactory.getLog(LastYearDataETL.class);

    public  void processCurrYearData(String strStartDate, String strEndDate, String bussItem){

    }

    /*
    一般贷款类
     */

    //@Override
    public double getLastYearTopLevelData_loan(String strCurrDate, String bussItem) {

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_lastyear_monday = Calendar.getInstance();

        Date monday_lastyear = getLastYearWeekMonday(strCurrDate);
        cal_lastyear_monday.setTime(monday_lastyear);
        Calendar cal_lastyear_sunday = (Calendar) cal_lastyear_monday.clone();
        cal_lastyear_sunday.add(Calendar.DATE, 6);


        InterestKey key = new InterestKey();

        key.setStartDate(sdf_yyyymmdd.format(monday_lastyear));

        //业务种类：放款
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //业务种类：还本
        key.setYwzl("2");

        int days = 0;
        double intrAmt = 0.00;

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //计算本年度当前进度实际
                Date ywrq = sdf_yyyymmdd.parse(intrInfo.getYwrq());
                Date dqrq = sdf_yyyymmdd.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_lastyear_sunday, cal_lastyear_monday);

                double rate = intrInfo.getLl();

                //查找最新利率
                ratekey.setHtnm(intrInfo.getHtnm());
                ratekey.setSxrq(strCurrDate);
                Double newrate = (Double) session.selectOne("InterestInfo.queryRate", ratekey);
                if (newrate != null) {
                    rate = newrate;
                }
                double intr_day = Airth.mul(intrInfo.getJe(), rate / 30 / 1000);
                intrAmt += Airth.mul(intr_day, days);

                //查找还本记录
                key.setFkid(intrInfo.getId());
                List<InterestInfo> list_ywzl_2 = session.selectList("InterestInfo.queryYwzl2", key);
                if (list_ywzl_2.size() > 0) {
                    for (InterestInfo info : list_ywzl_2) {
                        ywrq = sdf_yyyymmdd.parse(info.getYwrq());
                        dqrq = sdf_yyyymmdd.parse(info.getDqrq());
                        cal_ywrq.setTime(ywrq);
                        cal_dqrq.setTime(dqrq);

                        days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_lastyear_sunday, cal_lastyear_monday);

                        double huankuanRate = info.getLl();
                        if (newrate != null) {
                            huankuanRate = newrate;
                        }
                        intr_day = Airth.mul(info.getJe(), huankuanRate / 30 / 1000);
                        intrAmt = intrAmt - Airth.mul(intr_day, days);
                    }
                }
            }

        } catch (ParseException e) {
            logger.error("数据计算错误", e);
            throw new RuntimeException(e);
        }

        return Airth.round(intrAmt/10000,2);
    }
    /*
    贴现台帐
     */

    //@Override
    public double getLastYearTopLevelData_txtz(String strCurrDate, String bussItem) {


        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_lastyear_monday = Calendar.getInstance();

        Date monday_lastyear = getLastYearWeekMonday(strCurrDate);
        cal_lastyear_monday.setTime(monday_lastyear);
        Calendar cal_lastyear_sunday = (Calendar) cal_lastyear_monday.clone();
        cal_lastyear_sunday.add(Calendar.DATE, 6);


        String neiwaiFlag = "";
        if (bussItem.equals("5011")) {
            neiwaiFlag = "N";
        } else if (bussItem.equals("5212")) {
            neiwaiFlag = "W";
        } else {
            logger.error("参数有误");
            throw new RuntimeException("参数有误");
        }

        TxtzKey key = new TxtzKey();

        key.setStartDate(sdf_yyyymmdd.format(monday_lastyear));
        key.setNeiWaiFlag(neiwaiFlag);
        List<TxtzInfo> list = session.selectList("InterestInfo.selectTxtz", key);

        int days = 0;
        double intrAmt = 0.00;

        try {
            for (TxtzInfo info : list) {
                Date ywrq = sdf_yyyymmdd.parse(info.getRq());
                Date dqrq = sdf_yyyymmdd.parse(info.getHpdqr());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_lastyear_sunday, cal_lastyear_monday);

                double intr_day = info.getDay_lx();

                intrAmt += Airth.mul(intr_day, days);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("数据计算错误", e);
            throw new RuntimeException(e);
        }

        return Airth.round(intrAmt/10000,2);
    }
}
