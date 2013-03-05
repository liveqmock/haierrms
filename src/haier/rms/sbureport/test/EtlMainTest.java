package haier.rms.sbureport.test;

import haier.rms.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pub.tools.Airth;

import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-5
 * Time: 16:53:23
 * To change this template use File | Settings | File Templates.
 */
public class EtlMainTest {
    private static final Log logger = LogFactory.getLog(EtlMainTest.class);

    static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");


    public static void main(String[] args) throws Exception {

        Reader reader = Resources.getResourceAsReader("Configuration.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        processLoan(session, "20100101", "20100331", "5015");
    }

    /**
     * 测试用
     * @param startDate
     * @param endDate
     * @throws Exception
     */
    public static void run(String startDate, String endDate) throws Exception {

        Reader reader = Resources.getResourceAsReader("Configuration.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        session.delete("InterestInfo.deletecurrdate_sburpt", endDate);
//        session.delete("InterestInfo.deleteall_sburpt");

        processLoan(session, startDate, endDate, "5013");
        processLoan(session, startDate, endDate, "5015");
        processLoan(session, startDate, endDate, "1501");
        processLoan(session, startDate, endDate, "1502");
        processLoan(session, startDate, endDate, "5211");

        //贴现 集团内部
        processTxtz(session, startDate, endDate, "5011");
        //贴现 集团外部
        processTxtz(session, startDate, endDate, "5212");


    }

    /*

    flag: 1->处理年度实际进度
    flag: 2->处理本周实际进度
     NWFlag : 集团内外标志
     */

    private static void processLoan(SqlSession session, String strStartDate, String strEndDate, String bussItem, int flag) {

        InterestInfo interestinfo = new InterestInfo();
        Sburpt sburpt = new Sburpt();

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        cal_ywrq.clear();
        cal_dqrq.clear();
        cal_curr.clear();
        cal_base.clear();
        Date currdate = null;
        Date basedate = null;

        try {
            currdate = df.parse(strEndDate);
            cal_curr.setTime(currdate);
            if (flag == 1) {
                basedate = df.parse(strStartDate);
                cal_base.setTime(basedate);
            } else {
                cal_base.setTime(currdate);
                int dayofweek = cal_base.get(cal_base.DAY_OF_WEEK) - cal_base.getFirstDayOfWeek();
                cal_base.add(cal_base.DATE, 0 - dayofweek);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }

        InterestKey key = new InterestKey();

        key.setStartDate(strEndDate);
        //业务种类：放款
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //业务种类：还本
        key.setYwzl("2");


        int days = 0;

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //计算本年度当前进度实际
                Date ywrq = df.parse(intrInfo.getYwrq());
                Date dqrq = df.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                double rate = intrInfo.getLl();

                //查找最新利率
                ratekey.setHtnm(intrInfo.getHtnm());
                ratekey.setSxrq(strEndDate);
                Double newrate = (Double) session.selectOne("InterestInfo.queryRate", ratekey);
                if (newrate != null) {
                    rate = newrate;
                }
                double intr_day = Airth.mul(intrInfo.getJe(), rate / 30 / 1000);
                double intrAmt = Airth.mul(intr_day, days);
                intrAmt = Airth.round(intrAmt, 2);


                //查找还本记录
                key.setFkid(intrInfo.getId());
                List<InterestInfo> list_ywzl_2 = session.selectList("InterestInfo.queryYwzl2", key);
                if (list_ywzl_2.size() > 0) {
                    for (InterestInfo info : list_ywzl_2) {
                        ywrq = df.parse(info.getYwrq());
                        dqrq = df.parse(info.getDqrq());
                        cal_ywrq.setTime(ywrq);
                        cal_dqrq.setTime(dqrq);

                        days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                        double huankuanRate = info.getLl();
                        if (newrate != null) {
                            huankuanRate = newrate;
                        }
                        intr_day = Airth.mul(info.getJe(), huankuanRate / 30 / 1000);
                        double amtTemp = Airth.mul(intr_day, days);
                        intrAmt = intrAmt - Airth.round(amtTemp, 2);
                    }
                }
                System.out.printf("je=%f\n", intrAmt);

                //结果写入RMS_SBURPT表
                sburpt.setBussItem(bussItem);
                sburpt.setCustNo(intrInfo.getKhbh());
                sburpt.setCustName(intrInfo.getKhmc());
                if (flag == 1) {
                    sburpt.setYearActr(intrAmt / 10000);
                    sburpt.setWeekActr(0.00);
                    sburpt.setDataFlag("1"); //年度当前实际
                } else if (flag == 2) {
                    sburpt.setYearActr(0.00);
                    sburpt.setWeekActr(intrAmt / 10000);
                    sburpt.setDataFlag("2"); //年度当前实际
                } else {
                    throw new RuntimeException("FLAG ERROR");
                }
                sburpt.setDataDate(strEndDate);
                session.insert("InterestInfo.insertSburpt", sburpt);
            }

            session.commit();

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("日期转换错误，请检查数据库中日期字段的格式。", e);
        }

    }

    private static void processLoan(SqlSession session, String strStartDate, String strEndDate, String bussItem) {

        InterestInfo interestinfo = new InterestInfo();
        Sburpt sburpt = new Sburpt();

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        //本周周一日期
        Calendar cal_base_t0 = Calendar.getInstance();

        cal_ywrq.clear();
        cal_dqrq.clear();
        cal_curr.clear();
        cal_base.clear();
        cal_base_t0.clear();

        Date currdate = null;
        Date basedate = null;

        try {
            basedate = df.parse(strStartDate);
            currdate = df.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }

        cal_curr.setTime(currdate);
        cal_base.setTime(basedate);
        //计算本周周一日期
        cal_base_t0.setTime(currdate);
        int dayofweek = cal_base_t0.get(cal_base_t0.DAY_OF_WEEK) - cal_base_t0.getFirstDayOfWeek();
        cal_base_t0.add(cal_base.DATE, 0 - dayofweek + 1);
        //
        //当前周 在本年中的周数
        Calendar cal_curr_lastyear = (Calendar) cal_curr.clone();
        cal_curr_lastyear.setFirstDayOfWeek(Calendar.MONDAY);
        cal_curr_lastyear.setMinimalDaysInFirstWeek(7);
        int weeks = cal_curr_lastyear.get(Calendar.WEEK_OF_YEAR);

        //上年度本周周一日期
        cal_curr_lastyear.add(Calendar.YEAR, -1);
        cal_curr_lastyear.set(Calendar.MONTH, Calendar.JANUARY);
        cal_curr_lastyear.set(Calendar.DATE, 1);
        cal_curr_lastyear.add(Calendar.DATE, weeks * 7);
        cal_curr_lastyear.set(Calendar.DAY_OF_WEEK, cal_curr_lastyear.getFirstDayOfWeek()); // Monday

        Calendar cal_base_t0_lastyear = (Calendar) cal_curr_lastyear.clone();
        //上年度本周周日日期
        Calendar cal_base_t00_lastyear = (Calendar) cal_curr_lastyear.clone();
        cal_base_t00_lastyear.add(Calendar.DATE, 6);

/*


        //上年度本周周一日期
        Calendar cal_base_t0_lastyear = (Calendar) cal_base_t0.clone();
        int year = cal_base_t0_lastyear.get(Calendar.YEAR) - 1;
//        cal_base_t0_lastyear.set(Calendar.YEAR,year);
        cal_base_t0_lastyear.add(Calendar.YEAR, -1);
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
        //业务种类：放款
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //业务种类：还本
        key.setYwzl("2");


        int[] days = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        double[] intrAmt = new double[8];

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //计算本年度当前进度实际
                Date ywrq = df.parse(intrInfo.getYwrq());
                Date dqrq = df.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                //年进度实际
                days[7] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                //本周进度实际
                days[0] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base_t0);
                //T+1周进度实际
                days[1] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t11, cal_base_t1);
                //T+2周进度实际
                days[2] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t22, cal_base_t2);
                //T+3周进度实际
                days[3] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t33, cal_base_t3);
                //T+2周进度实际
                days[4] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t44, cal_base_t4);
                //T+2周进度实际
                days[5] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t55, cal_base_t5);
                //T+2周进度实际
                days[6] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t66, cal_base_t6);

                double rate = intrInfo.getLl();

                //查找最新利率
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


                //查找还本记录
                key.setFkid(intrInfo.getId());
                List<InterestInfo> list_ywzl_2 = session.selectList("InterestInfo.queryYwzl2", key);
                if (list_ywzl_2.size() > 0) {
                    for (InterestInfo info : list_ywzl_2) {
                        ywrq = df.parse(info.getYwrq());
                        dqrq = df.parse(info.getDqrq());
                        cal_ywrq.setTime(ywrq);
                        cal_dqrq.setTime(dqrq);

                        //年进度实际
                        days[7] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                        //本周进度实际
                        days[0] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base_t0);
                        //T+1周进度实际
                        days[1] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t11, cal_base_t1);
                        //T+2周进度实际
                        days[2] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t22, cal_base_t2);
                        //T+3周进度实际
                        days[3] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t33, cal_base_t3);
                        //T+2周进度实际
                        days[4] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t44, cal_base_t4);
                        //T+2周进度实际
                        days[5] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t55, cal_base_t5);
                        //T+2周进度实际
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

                //结果写入RMS_SBURPT表
                if (intrAmt[7] > 0) {
                    sburpt.setBussItem(bussItem);
                    sburpt.setCustNo(intrInfo.getKhbh());
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
            logger.error("日期转换错误，请检查数据库中日期字段的格式。", e);
        }

    }


    /*
   贴现台帐处理
    */

    private static void processTxtz(SqlSession session, String strStartDate, String strEndDate, String bussItem, int flag) {

        Sburpt sburpt = new Sburpt();

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        cal_ywrq.clear();
        cal_dqrq.clear();
        cal_curr.clear();
        cal_base.clear();
        Date currdate = null;
        Date basedate = null;

        try {
            currdate = df.parse(strEndDate);
            cal_curr.setTime(currdate);
            if (flag == 1) {
                basedate = df.parse(strStartDate);
                cal_base.setTime(basedate);
            } else {
                cal_base.setTime(currdate);
                int dayofweek = cal_base.get(cal_base.DAY_OF_WEEK) - cal_base.getFirstDayOfWeek();
                cal_base.add(cal_base.DATE, 0 - dayofweek);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }


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
        key.setStartDate(strStartDate);
        key.setNeiWaiFlag(neiwaiFlag);
        List<TxtzInfo> list = session.selectList("InterestInfo.selectTxtz", key);

        int days = 0;

        try {
            for (TxtzInfo info : list) {
                //计算本年度当前进度实际
                Date ywrq = df.parse(info.getRq());
                Date dqrq = df.parse(info.getHpdqr());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                if (info.getKhbh().equals("W0006")) {
                    int ii = 0;
                }
                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);

                double intr_day = info.getDay_lx();
                double intrAmt = Airth.mul(intr_day, days);
                intrAmt = Airth.round(intrAmt, 2);

                //结果写入RMS_SBURPT表
                sburpt.setBussItem(bussItem);
                sburpt.setCustNo(info.getKhbh());
                sburpt.setCustName(info.getKhmc());
                if (flag == 1) {
                    sburpt.setYearActr(intrAmt / 10000);
                    sburpt.setWeekActr(0.00);
                    sburpt.setDataFlag("1"); //年度当前实际
                } else if (flag == 2) {
                    sburpt.setYearActr(0.00);
                    sburpt.setWeekActr(intrAmt / 10000);
                    sburpt.setDataFlag("2"); //年度当前实际
                } else {
                    throw new RuntimeException("FLAG ERROR");
                }
                sburpt.setDataDate(strEndDate);
                session.insert("InterestInfo.insertSburpt", sburpt);
            }

            session.commit();

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("日期转换错误，请检查数据库中日期字段的格式。", e);
        }

    }


    /*
   贴现台帐处理   new
    */

    private static void processTxtz(SqlSession session, String strStartDate, String strEndDate, String bussItem) {

        Sburpt sburpt = new Sburpt();

        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        //本周周一日期
        Calendar cal_base_t0 = Calendar.getInstance();

        cal_ywrq.clear();
        cal_dqrq.clear();
        cal_curr.clear();
        cal_base.clear();
        cal_base_t0.clear();

        Date currdate = null;
        Date basedate = null;

        try {
            basedate = df.parse(strStartDate);
            currdate = df.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }

        cal_curr.setTime(currdate);
        cal_base.setTime(basedate);
        //计算本周周一日期
        cal_base_t0.setTime(currdate);
        int dayofweek = cal_base_t0.get(cal_base_t0.DAY_OF_WEEK) - cal_base_t0.getFirstDayOfWeek();
        cal_base_t0.add(cal_base.DATE, 0 - dayofweek + 1);
        //
        //上年度当前日期
        Calendar cal_curr_lastyear = (Calendar) cal_curr.clone();
        cal_curr_lastyear.add(Calendar.YEAR, -1);
        //上年度本周周一日期
        Calendar cal_base_t0_lastyear = (Calendar) cal_base_t0.clone();
        int year = cal_base_t0_lastyear.get(Calendar.YEAR) - 1;
//        cal_base_t0_lastyear.set(Calendar.YEAR,year);
        cal_base_t0_lastyear.add(Calendar.YEAR, -1);

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
        key.setStartDate(strStartDate);
        key.setNeiWaiFlag(neiwaiFlag);
        List<TxtzInfo> list = session.selectList("InterestInfo.selectTxtz", key);

        int[] days = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        double[] intrAmt = new double[8];

        try {
            for (TxtzInfo info : list) {
                //计算本年度当前进度实际
                Date ywrq = df.parse(info.getRq());
                Date dqrq = df.parse(info.getHpdqr());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                if (info.getKhbh().equals("W0006")) {
                    int ii = 0;
                }
//                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);

                //年进度实际
                days[7] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                //本周进度实际
                days[0] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base_t0);
                //T+1周进度实际
                days[1] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t11, cal_base_t1);
                //T+2周进度实际
                days[2] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t22, cal_base_t2);
                //T+3周进度实际
                days[3] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t33, cal_base_t3);
                //T+2周进度实际
                days[4] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t44, cal_base_t4);
                //T+2周进度实际
                days[5] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t55, cal_base_t5);
                //T+2周进度实际
                days[6] = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_base_t66, cal_base_t6);


                double intr_day = info.getDay_lx();

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


//
//                double intrAmt = Airth.mul(intr_day, days);
//                intrAmt = Airth.round(intrAmt, 2);

                //结果写入RMS_SBURPT表
                sburpt.setBussItem(bussItem);
                sburpt.setCustNo(info.getKhbh());
                sburpt.setCustName(info.getKhmc());
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
            logger.error("日期转换错误，请检查数据库中日期字段的格式。", e);
        }

    }

    //=========================================================================

    private static void processLoanMain(SqlSession session, String bussItem,
                                        String strStartDate, String strEndDate) {


        Calendar cal_ywrq = Calendar.getInstance();
        Calendar cal_dqrq = Calendar.getInstance();
        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();
        cal_ywrq.clear();
        cal_dqrq.clear();
        cal_curr.clear();
        cal_base.clear();
        Date currdate = null;
        Date basedate = null;

/*
        try {
            currdate = df.parse(strEndDate);
            cal_curr.setTime(currdate);
            if (flag == 1) {
                basedate = df.parse(strStartDate);
                cal_base.setTime(basedate);
            } else {
                cal_base.setTime(currdate);
                int dayofweek = cal_base.get(cal_base.DAY_OF_WEEK) - cal_base.getFirstDayOfWeek();
                cal_base.add(cal_base.DATE, 0 - dayofweek);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }
*/

        try {
            currdate = df.parse(strEndDate);
            basedate = df.parse(strStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
            throw new RuntimeException("输入的日期有误。");
        }


        InterestInfo interestinfo = new InterestInfo();
        Sburpt sburpt = new Sburpt();

        InterestKey key = new InterestKey();

        key.setStartDate(strEndDate);
        //业务种类：放款
        key.setYwzl("1");
        key.setBussItem(bussItem);
        List<InterestInfo> list = session.selectList("InterestInfo.queryInterest", key);

        //业务种类：还本
        key.setYwzl("2");


        int days = 0;

        try {
            rateKey ratekey = new rateKey();
            for (InterestInfo intrInfo : list) {
                //计算本年度当前进度实际
                Date ywrq = df.parse(intrInfo.getYwrq());
                Date dqrq = df.parse(intrInfo.getDqrq());
                cal_ywrq.setTime(ywrq);
                cal_dqrq.setTime(dqrq);

                days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                double rate = intrInfo.getLl();

                //查找最新利率
                ratekey.setHtnm(intrInfo.getHtnm());
                ratekey.setSxrq(strEndDate);
                Double newrate = (Double) session.selectOne("InterestInfo.queryRate", ratekey);
                if (newrate != null) {
                    rate = newrate;
                }
                double intr_day = Airth.mul(intrInfo.getJe(), rate / 30 / 1000);
                double intrAmt = Airth.mul(intr_day, days);
                intrAmt = Airth.round(intrAmt, 2);


                //查找还本记录
                key.setFkid(intrInfo.getId());
                List<InterestInfo> list_ywzl_2 = session.selectList("InterestInfo.queryYwzl2", key);
                if (list_ywzl_2.size() > 0) {
                    for (InterestInfo info : list_ywzl_2) {
                        ywrq = df.parse(info.getYwrq());
                        dqrq = df.parse(info.getDqrq());
                        cal_ywrq.setTime(ywrq);
                        cal_dqrq.setTime(dqrq);

                        days = getCurrentDateDays(cal_ywrq, cal_dqrq, cal_curr, cal_base);
                        double huankuanRate = info.getLl();
                        if (newrate != null) {
                            huankuanRate = newrate;
                        }
                        intr_day = Airth.mul(info.getJe(), huankuanRate / 30 / 1000);
                        double amtTemp = Airth.mul(intr_day, days);
                        intrAmt = intrAmt - Airth.round(amtTemp, 2);
                    }
                }
                System.out.printf("je=%f\n", intrAmt);

                //结果写入RMS_SBURPT表
                sburpt.setBussItem(bussItem);
                sburpt.setCustNo(intrInfo.getKhbh());
                sburpt.setCustName(intrInfo.getKhmc());

                int flag = 1;
                if (flag == 1) {
                    sburpt.setYearActr(intrAmt / 10000);
                    sburpt.setWeekActr(0.00);
                    sburpt.setDataFlag("1"); //年度当前实际
                } else if (flag == 2) {
                    sburpt.setYearActr(0.00);
                    sburpt.setWeekActr(intrAmt / 10000);
                    sburpt.setDataFlag("2"); //年度当前实际
                } else {
                    throw new RuntimeException("FLAG ERROR");
                }
                sburpt.setDataDate(strEndDate);
                session.insert("InterestInfo.insertSburpt", sburpt);
            }

            session.commit();

        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("日期转换错误，请检查数据库中日期字段的格式。", e);
        }

    }


    public static void assertTrue(boolean v) {
        if (!v) {
            throw new IllegalStateException("test expression must be true");
        }
    }

    /*
    根据业务起始日期及到期日期计算在本年度截止到当前日期的天数
     */

    private static int getCurrentDateDays(Calendar cal_ywrq, Calendar cal_dqrq, Calendar cal_curr, Calendar cal_base) {
        int rtnDays = 0;

        //区分日期区间四种可能的情形
        if (cal_ywrq.before(cal_base) && cal_dqrq.after(cal_curr)) { //两头在外
            rtnDays = getDaysBetween(cal_base, cal_curr);
        }
        if (cal_ywrq.after(cal_base) && cal_dqrq.before(cal_curr)) { //两头在里
            rtnDays = getDaysBetween(cal_ywrq, cal_dqrq);
        }
        if (cal_ywrq.before(cal_base) && (cal_dqrq.after(cal_base) && cal_dqrq.before(cal_curr))) { //左外右内
            rtnDays = getDaysBetween(cal_base, cal_dqrq);
        }
        if ((cal_ywrq.after(cal_base) && cal_ywrq.before(cal_curr)) && cal_dqrq.after(cal_curr)) { //左内右外
            rtnDays = getDaysBetween(cal_ywrq, cal_curr);
        }

/*
        //区分日期区间四种可能的情形
        if (cal_ywrq.before(cal_base) && cal_dqrq.after(cal_curr)) { //两头在外
            rtnDays = cal_curr.get(Calendar.DAY_OF_YEAR) - cal_base.get(Calendar.DAY_OF_YEAR);
        } else if (cal_ywrq.after(cal_base) && cal_dqrq.before(cal_curr)) { //两头在里
            rtnDays = cal_dqrq.get(Calendar.DAY_OF_YEAR) - cal_ywrq.get(Calendar.DAY_OF_YEAR);
        } else if (cal_ywrq.before(cal_base) && cal_dqrq.before(cal_curr)) { //左外右内
            rtnDays = cal_dqrq.get(Calendar.DAY_OF_YEAR) - cal_base.get(Calendar.DAY_OF_YEAR);
        } else if (cal_ywrq.after(cal_base) && cal_dqrq.after(cal_curr)) { //右外左内
            rtnDays = cal_curr.get(Calendar.DAY_OF_YEAR) - cal_ywrq.get(Calendar.DAY_OF_YEAR);
        } else { //不在区间内
            rtnDays = 0;
        }
*/

        if (rtnDays < 0) {
            int i = 0;
        }
        return rtnDays;

    }

    private static int getDaysBetween(Calendar start, Calendar end) {
/*
        if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
             java.util.Calendar swap = d1;
             d1 = d2;
             d2 = swap;
         }
*/
        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int y2 = end.get(Calendar.YEAR);
        if (start.get(Calendar.YEAR) != y2) {
            start = (Calendar) start.clone();
            do {
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                start.add(Calendar.YEAR, 1);
            } while (start.get(Calendar.YEAR) != y2);
        }
        return days+1;
    }

    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);

        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 得到某年某周的第一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getFirstDayOfWeek(cal.getTime());
    }

    /**
     * 得到某年某周的最后一天
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE, week * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


}
