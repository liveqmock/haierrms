package haier.rms.sbureport.test;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-8
 * Time: 6:57:27
 * To change this template use File | Settings | File Templates.
 */
public class DateTest {
    public static void  main(String[] argv){
           java.util.Calendar   cal   =   java.util.Calendar.getInstance();
         System.out.println("今天:   "   +   cal.getTime());
         //
         int   dayofmonth   =   cal.get(cal.DATE);
         cal.add(cal.DATE,   1   -   dayofmonth);
         System.out.println("本月第一天:   "   +   cal.getTime());
         cal.add(cal.DATE,   dayofmonth   -   1);
         //
         cal.add(cal.MONTH,   1);
         dayofmonth   =   cal.get(cal.DATE);
         cal.add(cal.DATE,   -dayofmonth);
         System.out.println("本月最后一天:   "   +   cal.getTime());
         cal.add(cal.DATE,   dayofmonth);
         cal.add(cal.MONTH,   -1);
         //
         int   dayofweek   =   cal.get(cal.DAY_OF_WEEK)   -   cal.getFirstDayOfWeek();
         cal.add(cal.DATE,   0   -   dayofweek);
         System.out.println("上周日:   "   +   cal.getTime());
         cal.add(cal.DATE,   dayofweek   -   1);
         //
         cal.add(cal.DATE,   7   -   dayofweek);
         System.out.println("本周日:   "   +   cal.getTime());
         cal.add(cal.DATE,   dayofweek   -   7);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        if (cal1.equals(cal2)) {
            System.out.println("cal1=cal2");
        }else{
            System.out.println("cal!=cal2");
        }
    }
}
