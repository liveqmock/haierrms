package pub.tools;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-6
 * Time: 21:10:18
 * To change this template use File | Settings | File Templates.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* ���ڹ��ô�����
*
* @author SongJun
* @version 1.3
*/
public class DateUtil {

      public static final int MILLISECONDS_PER_SECOND = 1000;
    public static final int MILLISECONDS_PER_DAY = 86400000;
    public static final int MILLISECONDS_PER_HOUR = 3600000;
    public static final int MILLISECONDS_PER_MINUTE = 60000;
      /*
     * ���ڰ�ָ����ʽת�����ַ���
     */
    public static String dateToStr(String aMask,Date date){
        SimpleDateFormat df = new SimpleDateFormat(aMask);
        String dateAsString = df.format(date);
        return dateAsString;
    }
    public static String getCurrentTime(){
        return getToday("HH:mm:ss");
    }
    /*
     * ��ָ����ʽ���ص������ڵ��ַ�����ʽ
     */
    public static String getToday(String aMask){
        Date today = new Date();
        String todayAsString = dateToStr(aMask,today);
        return todayAsString;
    }
    /*
     * ��Ĭ�ϸ�ʽ���ص������ڵ��ַ�����ʽ
     */
    public static String getToday(){
        return getToday("yyyy-MM-dd");
    }
    /*
     * ���ַ�����ָ����ʽת����Date
     */
    public static Date strToDate(String aMask,String strDate) {
        SimpleDateFormat format = new SimpleDateFormat(aMask);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /*
     * ���ַ�����Ĭ�ϸ�ʽת����Date
     */
    public static Date strToDate(String strDate) {
        return strToDate("yyyy-MM-dd",strDate);
    }
    /*
     * ��������õ��ĺ�����
     */
    public static long sub(String aMask,String strBeginDate, String strEndDate) {
        long dateRange=0;
        int num = 0;
        Date beginDate = strToDate(aMask,strBeginDate);
        Date endDate = strToDate(aMask,strEndDate);
        dateRange = endDate.getTime() - beginDate.getTime();
        return dateRange;
    }
    /*
     * ��������õ��ĺ�����
     */
    public static long sub(String strBeginDate, String strEndDate) {
        long dateRange=0;
        int num = 0;
        Date beginDate = strToDate(strBeginDate);
        Date endDate = strToDate(strEndDate);
        dateRange = endDate.getTime() - beginDate.getTime();
        return dateRange;
    }
    /*
     * ��������õ�������
     */
    public static String subToDay(String strBeginDate, String strEndDate){
        String dayNum = "";
        long dateRange = sub(strBeginDate,strEndDate);
        dayNum = ""+(dateRange/MILLISECONDS_PER_DAY);
        return dayNum;
    }
    /*
     * ��������õ�������
     */
    public static String subToSecond(String aMask,String strBeginDate, String strEndDate){
        String secNum = "";
        long dateRange = sub(aMask,strBeginDate,strEndDate);
        secNum = ""+(dateRange/MILLISECONDS_PER_SECOND);
        return secNum;
    }
    public static String subToSecond(String strBeginDate, String strEndDate){
        String secNum = "";
        long dateRange = sub("yyyy-MM-dd HH:mm:ss",strBeginDate,strEndDate);
        secNum = ""+(dateRange/MILLISECONDS_PER_SECOND);
        return secNum;
    }

    //====================================================================

    /**
     * ����һ������֮��������·�
     *
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static ArrayList getMonthList(String beginDateStr, String endDateStr) {
        // ָ��Ҫ������ʱ���ʽ
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        // ���ص��·��б�
        String sRet = "";

        // ����һЩ����
        Date beginDate = null;
        Date endDate = null;

        GregorianCalendar beginGC = null;
        GregorianCalendar endGC = null;
        ArrayList list = new ArrayList();

        try {
            // ���ַ���parse������
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // ��������
            beginGC = new GregorianCalendar();
            beginGC.setTime(beginDate);

            endGC = new GregorianCalendar();
            endGC.setTime(endDate);

            // ֱ������ʱ����ͬ
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {
                sRet = beginGC.get(Calendar.YEAR) + "-"
                        + (beginGC.get(Calendar.MONTH) + 1);
                list.add(sRet);
                // ����Ϊ��λ������ʱ��
                beginGC.add(Calendar.MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ����һ�����ڶ�֮�����������
     *
     * @param beginDateStr
     *            ��ʼ����
     * @param endDateStr
     *            ��������
     * @return
     */
    public static ArrayList getDayList(String beginDateStr, String endDateStr) {
        // ָ��Ҫ������ʱ���ʽ
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

        // ����һЩ����
        Date beginDate = null;
        Date endDate = null;

        Calendar beginGC = null;
        Calendar endGC = null;
        ArrayList list = new ArrayList();

        try {
            // ���ַ���parse������
            beginDate = f.parse(beginDateStr);
            endDate = f.parse(endDateStr);

            // ��������
            beginGC = Calendar.getInstance();
            beginGC.setTime(beginDate);

            endGC = Calendar.getInstance();
            endGC.setTime(endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // ֱ������ʱ����ͬ
            while (beginGC.getTime().compareTo(endGC.getTime()) <= 0) {

                list.add(sdf.format(beginGC.getTime()));
                // ����Ϊ��λ������ʱ��
                beginGC.add(Calendar.DAY_OF_MONTH, 1);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList getYearList() {
        ArrayList list = new ArrayList();
        Calendar c = null;
        c = Calendar.getInstance();
        c.setTime(new Date());
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        int startYear = currYear - 5;
        int endYear = currYear + 10;
        for (int i = startYear; i < endYear; i++) {
            list.add(new Integer(i));
        }
        return list;
    }

    public static int getCurrYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * �õ�ĳһ���ܵ�����
     *
     * @param year
     * @return
     */
    public static LinkedHashMap getWeekList(int year) {
        LinkedHashMap map = new LinkedHashMap();
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int count = getWeekOfYear(c.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayOfWeekStart = "";
        String dayOfWeekEnd = "";
        for (int i = 1; i <= count; i++) {
            dayOfWeekStart = sdf.format(getFirstDayOfWeek(year, i));
            dayOfWeekEnd = sdf.format(getLastDayOfWeek(year, i));
            map.put(new Integer(i), "��"+i+"��(��"+dayOfWeekStart + "��" + dayOfWeekEnd+")");
        }
        return map;

    }

    /**
     * �õ�һ���������
     * @param year
     * @return
     */
    public static int getWeekCountInYear(int year){
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        int count = getWeekOfYear(c.getTime());
        return count;
    }

    /**
     * ȡ�õ�ǰ�����Ƕ�����
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
     * �õ�ĳ��ĳ�ܵĵ�һ��
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
     * �õ�ĳ��ĳ�ܵ����һ��
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
     * �õ�ĳ��ĳ�µĵ�һ��
     * @param year
     * @param month
     * @return
     */
    public static Date getFirestDayOfMonth(int year,int month){
        month = month-1;
        Calendar   c   =   Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);

        int day = c.getActualMinimum(c.DAY_OF_MONTH);

        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();

    }

    /**
     * �ᵽĳ��ĳ�µ����һ��
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year,int month){
        month = month-1;
        Calendar   c   =   Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        int day = c.getActualMaximum(c.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * ȡ�õ�ǰ���������ܵĵ�һ��
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
     * ȡ�õ�ǰ���������ܵ����һ��
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


    //==========
      public int getDaysBetween (Calendar d1, Calendar d2) {
        if (d1.after(d2)) {  // swap dates so that d1 is start and d2 is end
             Calendar swap = d1;
             d1 = d2;
             d2 = swap;
         }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
             d1 = (Calendar) d1.clone();
            do {
                 days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);//�õ������ʵ������
                 d1.add(Calendar.YEAR, 1);
             } while (d1.get(Calendar.YEAR) != y2);
         }
        return days;
     }


    //====================
     // ��õ�ǰ�����뱾������������
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // ��ý�����һ�ܵĵڼ��죬�������ǵ�һ�죬���ڶ��ǵڶ���......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1;   //��Ϊ���й����һ��Ϊ��һ�����������1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }
// ��ñ��������յ�����
    public String getCurrentWeekday() {
        int weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus+6);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

     public static void main(String[] args) {
        System.out.println("��Ĭ�ϸ�ʽ���ؽ�������:"+getToday());
        System.out.println("��ָ����ʽ���ؽ�������:"+getToday("yyyy-MM-dd HH:mm:ss"));
        System.out.println("�����������:"+subToDay("2006-08-02 23:02:01", "2006-08-03 01:02:01"));
        System.out.println("�����������:"+subToSecond("2006-08-02 23:02:01", "2006-08-03 01:02:01"));
        System.out.println("��ǰʱ��:"+getCurrentTime());
    }

} 