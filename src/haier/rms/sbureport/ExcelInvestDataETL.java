package haier.rms.sbureport;

import haier.rms.model.InvestInfo;
import haier.rms.model.Sburpt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.tools.Airth;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 投资银行业务收入数据处理
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 9:20:54
 * To change this template use File | Settings | File Templates.
 */
public class ExcelInvestDataETL extends AbstractExcelETL {
    private static final Log logger = LogFactory.getLog(ExcelInvestDataETL.class);


    /**
     *
      * @param strStartDate   yyyyMMdd
     * @param strEndDate    yyyyMMdd
     * @param bussItem
     */
    @Override
    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {

        Sburpt sburpt = new Sburpt();

        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        //本周周一日期
        Calendar cal_base_t0 = Calendar.getInstance();

        cal_start.clear();
        cal_end.clear();
        cal_curr.clear();
        cal_base.clear();
        cal_base_t0.clear();

        Date currdate = null;
        Date basedate = null;

        try {
            basedate = sdf_yyyymmdd.parse(strStartDate);
            currdate = sdf_yyyymmdd.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }

        cal_curr.setTime(currdate);
        cal_base.setTime(basedate);

        //计算本周周一日期
        cal_base_t0.setFirstDayOfWeek(Calendar.MONDAY);
        cal_base_t0.setTime(currdate);
        cal_base_t0.set(Calendar.DAY_OF_WEEK, cal_base_t0.getFirstDayOfWeek()); //MONDAY

        Calendar cal_base_t1 = (Calendar) cal_base_t0.clone();
        cal_base_t1.add(Calendar.DATE, 7);
        Calendar cal_base_t11 = (Calendar) cal_base_t1.clone();
        cal_base_t11.add(Calendar.DATE, 6);

        Calendar cal_base_t2 = (Calendar) cal_base_t11.clone();
        cal_base_t2.add(Calendar.DATE, 1);
        Calendar cal_base_t22 = (Calendar) cal_base_t2.clone();
        cal_base_t22.add(Calendar.DATE, 6);

        Calendar cal_base_t3 = (Calendar) cal_base_t22.clone();
        cal_base_t3.add(Calendar.DATE, 1);
        Calendar cal_base_t33 = (Calendar) cal_base_t3.clone();
        cal_base_t33.add(Calendar.DATE, 6);

        Calendar cal_base_t4 = (Calendar) cal_base_t33.clone();
        cal_base_t4.add(Calendar.DATE, 1);
        Calendar cal_base_t44 = (Calendar) cal_base_t4.clone();
        cal_base_t44.add(Calendar.DATE, 6);

        Calendar cal_base_t5 = (Calendar) cal_base_t44.clone();
        cal_base_t5.add(Calendar.DATE, 1);
        Calendar cal_base_t55 = (Calendar) cal_base_t5.clone();
        cal_base_t55.add(Calendar.DATE, 6);

        Calendar cal_base_t6 = (Calendar) cal_base_t55.clone();
        cal_base_t6.add(Calendar.DATE, 1);
        Calendar cal_base_t66 = (Calendar) cal_base_t6.clone();
        cal_base_t66.add(Calendar.DATE, 6);


        InvestInfo key = new InvestInfo();
        key.setStartdate(strStartDate);
        key.setDatadate(strEndDate);
        List<InvestInfo> list = session.selectList("InterestInfo.selectInvestData", key);

        int[] days = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        double[] intrAmt = new double[8];

        try {
            for (InvestInfo info : list) {
                //计算本年度当前进度实际
                Date start = sdf_yyyymmdd.parse(info.getStartdate());
                Date end = sdf_yyyymmdd.parse(info.getEnddate());
                cal_start.setTime(start);
                cal_end.setTime(end);

                //年进度实际
                days[7] = getCurrentDateDays(cal_start, cal_end, cal_curr, cal_base);
                //本周进度实际
                days[0] = getCurrentDateDays(cal_start, cal_end, cal_curr, cal_base_t0);
                //T+1周进度实际
                days[1] = getCurrentDateDays(cal_start, cal_end, cal_base_t11, cal_base_t1);
                //T+2周进度实际
                days[2] = getCurrentDateDays(cal_start, cal_end, cal_base_t22, cal_base_t2);
                //T+3周进度实际
                days[3] = getCurrentDateDays(cal_start, cal_end, cal_base_t33, cal_base_t3);
                //T+4周进度实际
                days[4] = getCurrentDateDays(cal_start, cal_end, cal_base_t44, cal_base_t4);
                //T+5周进度实际
                days[5] = getCurrentDateDays(cal_start, cal_end, cal_base_t55, cal_base_t5);
                //T+6周进度实际
                days[6] = getCurrentDateDays(cal_start, cal_end, cal_base_t66, cal_base_t6);


                double intr_day = info.getDailyincome();
                //币种处理
                String tmpcurrcode = info.getCurrtype();
                if (tmpcurrcode == null) {
                    tmpcurrcode = "001";
                }
                if (!"001".equals(tmpcurrcode)) {
                    String exchangerate = getLastExRate(session, strEndDate, tmpcurrcode);
                    BigDecimal rate = new BigDecimal(exchangerate);
                    BigDecimal intr = new BigDecimal(intr_day).multiply(rate).multiply(new BigDecimal("0.01"));
                    intr_day = intr.doubleValue();
                }

                intrAmt[7] = Airth.mul(intr_day, days[7]);
                intrAmt[7] = Airth.round(intrAmt[7], 2);
                intrAmt[0] = Airth.mul(intr_day, days[0]);
                intrAmt[0] = Airth.round(intrAmt[0], 2);
                intrAmt[1] = Airth.mul(intr_day, days[1]);
                intrAmt[1] = Airth.round(intrAmt[1], 2);
                intrAmt[2] = Airth.mul(intr_day, days[2]);
                intrAmt[2] = Airth.round(intrAmt[2], 2);
                intrAmt[3] = Airth.mul(intr_day, days[3]);
                intrAmt[3] = Airth.round(intrAmt[3], 2);
                intrAmt[4] = Airth.mul(intr_day, days[4]);
                intrAmt[4] = Airth.round(intrAmt[4], 2);
                intrAmt[5] = Airth.mul(intr_day, days[5]);
                intrAmt[5] = Airth.round(intrAmt[5], 2);
                intrAmt[6] = Airth.mul(intr_day, days[6]);
                intrAmt[6] = Airth.round(intrAmt[6], 2);


                //结果写入RMS_SBURPT表
                sburpt.setBussItem(bussItem);
                sburpt.setCustNo(info.getProductid());
                sburpt.setCustName(info.getProductname());
                sburpt.setYearActr(intrAmt[7] / 10000);
                sburpt.setWeekActr(intrAmt[0] / 10000);
                sburpt.setWeekActrT1(intrAmt[1] / 10000);
                sburpt.setWeekActrT2(intrAmt[2] / 10000);
                sburpt.setWeekActrT3(intrAmt[3] / 10000);
                sburpt.setWeekActrT4(intrAmt[4] / 10000);
                sburpt.setWeekActrT5(intrAmt[5] / 10000);
                sburpt.setWeekActrT6(intrAmt[6] / 10000);
                sburpt.setDataDate(strEndDate);
                session.insert("InterestInfo.insertSburpt", sburpt);
            }

            session.commit();

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("投资银行数据处理错误。", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public double getLastYearTopLevelData(String strCurrDate, String bussItem) {
        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_lastyear_monday = Calendar.getInstance();

        Date monday_lastyear = getLastYearWeekMonday(strCurrDate);
        cal_lastyear_monday.setTime(monday_lastyear);
        Calendar cal_lastyear_sunday = (Calendar) cal_lastyear_monday.clone();
        cal_lastyear_sunday.add(Calendar.DATE, 6);

        InvestInfo key = new InvestInfo();
        key.setStartdate(strCurrDate);
        key.setDatadate(strCurrDate);
        List<InvestInfo> list;

        list = session.selectList("InterestInfo.selectInvestData", key);

        int days = 0;
        double intrAmt = 0.00;

        try {
            for (InvestInfo info : list) {
                Date ywrq = sdf_yyyymmdd.parse(info.getStartdate());
                Date dqrq = sdf_yyyymmdd.parse(info.getEnddate());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_lastyear_sunday, cal_lastyear_monday);

                double intr_day = info.getDailyincome();
                //币种处理
                String tmpcurrcode = info.getCurrtype();
                if (tmpcurrcode == null) {
                    tmpcurrcode = "001";
                }
                if (!"001".equals(tmpcurrcode)) {
                    String exchangerate = getLastExRate(session, strCurrDate, tmpcurrcode);
                    BigDecimal rate = new BigDecimal(exchangerate);
                    BigDecimal intr = new BigDecimal(intr_day).multiply(rate).multiply(new BigDecimal("0.01"));
                    intr_day = intr.doubleValue();
                }
                intrAmt += Airth.mul(intr_day, days);
            }

        } catch (ParseException e) {
            logger.error("投资银行业务数据计算错误", e);
            throw new RuntimeException(e);
        }

        return Airth.round(intrAmt / 10000, 2);
    }

}