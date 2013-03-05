package haier.rms.sbureport;

import gateway.common.util.Message;
import gateway.common.util.ScreenCommonMessages;
import gateway.common.util.SystemErrorException;
import gateway.SBS.ExchangeRate.ExRateRecordBean;
import haier.rms.exchangerate.SBSHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-5
 * Time: 16:53:23
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMainETL implements ISbuRptUtil, IMainETL, IExchangeRate {
    private static final Log logger = LogFactory.getLog(AbstractMainETL.class);

    SqlSession session;
    static SimpleDateFormat sdf_yyyymmdd = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat sdf_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");

    protected AbstractMainETL() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("Configuration.xml");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IBATIS 参数文件读取错误。");
            throw new RuntimeException(e);
        }
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        this.session = sessionFactory.openSession();

    }

    public void deleteAllData() {
        session.delete("InterestInfo.deleteall_sburpt");
        session.commit();
    }

    /**
     * 删除当亲日期的数据
     *
     * @param strCurrDate 格式为yyyyMMdd
     */
    public void deleteCurrDateData(String strCurrDate) {
        session.delete("InterestInfo.deletecurrdate_sburpt", strCurrDate);
    }


/*
    public double getLastYearTopLevelData_loan(String strDate, String item){
        return 0.00;
    }

    public double getLastYearTopLevelData_txtz(String strDate, String item){
        return 0.00;
    }
*/

    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {

    }

    public double getLastYearTopLevelData(String strCurrDate, String bussItem) {
        return 0.00;
    }


    //======================tools============================================

    /*
   根据业务起始日期及到期日期计算在本年度截止到当前日期的天数
    */

    protected static int getCurrentDateDays(Calendar cal_ywrq, Calendar cal_dqrq, Calendar cal_curr, Calendar cal_base) {
        int rtnDays = 0;

        //区分日期区间四种可能的情形
        if (!cal_ywrq.after(cal_base) && !cal_dqrq.before(cal_curr)) { //两头在外
            rtnDays = getDaysBetween(cal_base, cal_curr);
        }
        if (!cal_ywrq.before(cal_base) && !cal_dqrq.after(cal_curr)) { //两头在里
            rtnDays = getDaysBetween(cal_ywrq, cal_dqrq);
        }
        if (!cal_ywrq.after(cal_base) && (!cal_dqrq.before(cal_base) && !cal_dqrq.after(cal_curr))) { //左外右内
            rtnDays = getDaysBetween(cal_base, cal_dqrq);
        }
        if ((!cal_ywrq.before(cal_base) && !cal_ywrq.after(cal_curr)) && !cal_dqrq.before(cal_curr)) { //左内右外
            rtnDays = getDaysBetween(cal_ywrq, cal_curr);
        }


        if (rtnDays < 0) {
            int i = 0;
        }
        return rtnDays;

    }


    /*
    根据当前日期，计算本周在本年度中的周数
    然后计算上年同周的周一日期
     */

    protected Date getLastYearWeekMonday(String strCurrDate) {
        //本周周一日期
        Calendar cal_curr = Calendar.getInstance();
        cal_curr.clear();
        cal_curr.setFirstDayOfWeek(Calendar.MONDAY);

        Date currdate = null;
        try {
            currdate = sdf_yyyymmdd.parse(strCurrDate);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("输入的日期有误。");
        }

        //计算本周周一日期
        cal_curr.setTime(currdate);

        cal_curr.set(Calendar.DAY_OF_WEEK, cal_curr.getFirstDayOfWeek());

//        int dayofweek = cal_curr.get(cal_curr.DAY_OF_WEEK) - cal_curr.getFirstDayOfWeek();
//        cal_curr.add(Calendar.DATE, 0 - dayofweek + 1);

        //当前周 在本年中的周数
        Calendar cal_lastyear_monday = (Calendar) cal_curr.clone();
        cal_lastyear_monday.setFirstDayOfWeek(Calendar.MONDAY);
        cal_lastyear_monday.setMinimalDaysInFirstWeek(7);
        int weeks = cal_lastyear_monday.get(Calendar.WEEK_OF_YEAR);

        //上年度本周周一日期
        cal_lastyear_monday.add(Calendar.YEAR, -1);
        cal_lastyear_monday.set(Calendar.MONTH, Calendar.JANUARY);
        cal_lastyear_monday.set(Calendar.DATE, 1);
        cal_lastyear_monday.add(Calendar.DATE, weeks * 7);
        cal_lastyear_monday.set(Calendar.DAY_OF_WEEK, cal_lastyear_monday.getFirstDayOfWeek()); // Monday

        //上年度本周周日日期
        //Calendar cal_lastyear_sunday = (Calendar) cal_lastyear_monday.clone();
        //cal_lastyear_sunday.add(Calendar.DATE, 6);

        return cal_lastyear_monday.getTime();
    }

    protected static int getDaysBetween(Calendar start, Calendar end) {
        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int y2 = end.get(Calendar.YEAR);
        if (start.get(Calendar.YEAR) != y2) {
            start = (Calendar) start.clone();
            do {
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);//得到当年的实际天数
                start.add(Calendar.YEAR, 1);
            } while (start.get(Calendar.YEAR) != y2);
        }
        return days + 1;
    }

    /**
     * 取得当前日期是多少周
     *
     * @param date
     * @return
     */
    protected static int getWeekOfYear(Date date) {
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
    protected static Date getFirstDayOfWeek(int year, int week) {
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
    protected static Date getLastDayOfWeek(int year, int week) {
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
    protected static Date getFirstDayOfWeek(Date date) {
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
    protected static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }


    //zhan 20100705

    /**
     * 取最新汇率 （中间价）
     *
     * @param currdate    yyyy-MM-dd
     * @param tmpcurrcode
     * @return
     */
    public String getLastExRate(SqlSession session, String currdate, String tmpcurrcode) {
        ExRateRecordBean record = new ExRateRecordBean();
        record.setCURCDE(tmpcurrcode);
        record.setSECCCY("001"); //人民币

        String dbdate = null;
        if (currdate.length()==10) {
            dbdate = currdate.substring(0, 4) + currdate.substring(5, 7) + currdate.substring(8, 10);
        }else{
            dbdate = currdate;
        }

        record.setEFFDAT(dbdate);
        ExRateRecordBean resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
        //本地表中未查询到此币种汇率信息时
        if (resultbean == null) {
            //TODO 实时查询SBS
            SBSHandler handler = new SBSHandler();
            handler.querySBS(session, dbdate, tmpcurrcode, "001");
            resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
            if (resultbean == null) {
                Message message = ScreenCommonMessages.M00001001E;
                message.create("未找到对应的汇率信息，币种：" + tmpcurrcode);
                throw new SystemErrorException(ScreenCommonMessages.M00001001E.toString());
            }
        }
        String exchangerate = resultbean.getRATVA4().trim();
        //TODO 汇率千分位处理
        exchangerate = exchangerate.replaceAll("(\\d+),(\\d)", "$1$2");
        return exchangerate;
    }

}