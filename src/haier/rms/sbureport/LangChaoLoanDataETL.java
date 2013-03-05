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
 * �˳�ϵͳ���ݴ���  for  һ�����.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 9:20:54
 * To change this template use File | Settings | File Templates.
 */
public class LangChaoLoanDataETL extends AbstractLangChaoETL {
    private static final Log logger = LogFactory.getLog(LangChaoLoanDataETL.class);


    @Override
    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {

        Sburpt sburpt = new Sburpt();

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        //������һ����
        Calendar cal_base_t0 = Calendar.getInstance();

        cal_ywrq.clear();
        cal_dqrq.clear();
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
            logger.error("�������������");
        }

        cal_curr.setTime(currdate);
        cal_base.setTime(basedate);

        //���㱾����һ����
        cal_base_t0.setFirstDayOfWeek(Calendar.MONDAY);
        cal_base_t0.setTime(currdate);
        cal_base_t0.set(Calendar.DAY_OF_WEEK, cal_base_t0.getFirstDayOfWeek()); //MONDAY
/*
         int dayofweek = cal_base_t0.get(cal_base_t0.DAY_OF_WEEK) - cal_base_t0.getFirstDayOfWeek();
         cal_base_t0.add(cal_base.DATE, 0 - dayofweek + 1);
*/


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


        InterestKey key = new InterestKey();

        key.setStartDate(strEndDate);
        //ҵ�����ࣺ�ſ�
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //ҵ�����ࣺ����
        key.setYwzl("2");


        int[] days = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        double[] intrAmt = new double[8];

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //���㱾��ȵ�ǰ����ʵ��
                Date ywrq = sdf_yyyymmdd.parse(intrInfo.getYwrq());
                Date dqrq = sdf_yyyymmdd.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                //�����ʵ��
                days[7] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                //���ܽ���ʵ��
                days[0] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base_t0);
                //T+1�ܽ���ʵ��
                days[1] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t11, cal_base_t1);
                //T+2�ܽ���ʵ��
                days[2] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t22, cal_base_t2);
                //T+3�ܽ���ʵ��
                days[3] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t33, cal_base_t3);
                //T+2�ܽ���ʵ��
                days[4] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t44, cal_base_t4);
                //T+2�ܽ���ʵ��
                days[5] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t55, cal_base_t5);
                //T+2�ܽ���ʵ��
                days[6] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t66, cal_base_t6);

                double rate = intrInfo.getLl();

                //������������
                ratekey.setHtnm(intrInfo.getHtnm());
                ratekey.setSxrq(strEndDate);
                Double newrate = (Double) session.selectOne("InterestInfo.queryRate", ratekey);
                if (newrate != null) {
                    rate = newrate;
                }
                double intr_day = Airth.mul(intrInfo.getJe(), rate / 30 / 1000);
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


                //���һ�����¼
                key.setFkid(intrInfo.getId());
                List<InterestInfo> list_ywzl_2 = session.selectList("InterestInfo.queryYwzl2", key);
                if (list_ywzl_2.size() > 0) {
                    for (InterestInfo info : list_ywzl_2) {
                        ywrq = sdf_yyyymmdd.parse(info.getYwrq());
                        dqrq = sdf_yyyymmdd.parse(info.getDqrq());
                        cal_ywrq.setTime(ywrq);
                        cal_dqrq.setTime(dqrq);

                        //�����ʵ��
                        days[7] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                        //���ܽ���ʵ��
                        days[0] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base_t0);
                        //T+1�ܽ���ʵ��
                        days[1] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t11, cal_base_t1);
                        //T+2�ܽ���ʵ��
                        days[2] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t22, cal_base_t2);
                        //T+3�ܽ���ʵ��
                        days[3] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t33, cal_base_t3);
                        //T+2�ܽ���ʵ��
                        days[4] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t44, cal_base_t4);
                        //T+2�ܽ���ʵ��
                        days[5] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t55, cal_base_t5);
                        //T+2�ܽ���ʵ��
                        days[6] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t66, cal_base_t6);

                        double huankuanRate = info.getLl();
                        if (newrate != null) {
                            huankuanRate = newrate;
                        }
                        intr_day = Airth.mul(info.getJe(), huankuanRate / 30 / 1000);
//                        double amtTemp = Airth.mul(intr_day, days[0]);
//
//                        intrAmt = intrAmt - Airth.round(amtTemp, 2);

                        intrAmt[7] = Airth.round(intrAmt[7] - Airth.mul(intr_day, days[7]), 2);
                        intrAmt[0] = Airth.round(intrAmt[0] - Airth.mul(intr_day, days[0]), 2);
                        intrAmt[1] = Airth.round(intrAmt[1] - Airth.mul(intr_day, days[1]), 2);
                        intrAmt[2] = Airth.round(intrAmt[2] - Airth.mul(intr_day, days[2]), 2);
                        intrAmt[3] = Airth.round(intrAmt[3] - Airth.mul(intr_day, days[3]), 2);
                        intrAmt[4] = Airth.round(intrAmt[4] - Airth.mul(intr_day, days[4]), 2);
                        intrAmt[5] = Airth.round(intrAmt[5] - Airth.mul(intr_day, days[5]), 2);
                        intrAmt[6] = Airth.round(intrAmt[6] - Airth.mul(intr_day, days[6]), 2);
                    }
                }

                //���д��RMS_SBURPT��
                if (intrAmt[7] > 0) {
                    sburpt.setBussItem(bussItem);
                    sburpt.setCustNo(intrInfo.getKhbh());
//                    sburpt.setCustName(intrInfo.getKhmc()+" "+intrInfo.getHtbh()+" "+rate);
                    sburpt.setCustName(intrInfo.getKhmc());

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
            }

            session.commit();

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("���ݴ������", e);
            throw new RuntimeException(e);
        }

    }

    /*
   һ�������
    */

    @Override
    public double getLastYearTopLevelData(String strCurrDate, String bussItem) {

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_lastyear_monday = Calendar.getInstance();

        Date monday_lastyear = getLastYearWeekMonday(strCurrDate);
        cal_lastyear_monday.setTime(monday_lastyear);
        Calendar cal_lastyear_sunday = (Calendar) cal_lastyear_monday.clone();
        cal_lastyear_sunday.add(Calendar.DATE, 6);


        InterestKey key = new InterestKey();

        key.setStartDate(sdf_yyyymmdd.format(monday_lastyear));

        //ҵ�����ࣺ�ſ�
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //ҵ�����ࣺ����
        key.setYwzl("2");

        int days = 0;
        double intrAmt = 0.00;

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //���㱾��ȵ�ǰ����ʵ��
                Date ywrq = sdf_yyyymmdd.parse(intrInfo.getYwrq());
                Date dqrq = sdf_yyyymmdd.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_lastyear_sunday, cal_lastyear_monday);

                double rate = intrInfo.getLl();

                //������������
                ratekey.setHtnm(intrInfo.getHtnm());
                ratekey.setSxrq(strCurrDate);
                Double newrate = (Double) session.selectOne("InterestInfo.queryRate", ratekey);
                if (newrate != null) {
                    rate = newrate;
                }
                double intr_day = Airth.mul(intrInfo.getJe(), rate / 30 / 1000);
                intrAmt += Airth.mul(intr_day, days);

                //���һ�����¼
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
            logger.error("���ݼ������", e);
            throw new RuntimeException(e);
        }

        return Airth.round(intrAmt / 10000, 2);
    }
}