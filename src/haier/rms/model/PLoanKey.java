package haier.rms.model;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-18
 * Time: 16:31:08
 * To change this template use File | Settings | File Templates.
 */
public class PLoanKey {
    String currmonth;
    String nextmonth;
    String currdate;
    String startdate;     //报表起始日期
    String enddate;       //报表结束日期
    String weekstartdate; //本周起始日期
    String weekenddate;   //本周结束日期
    String weekt0;        //本周 如：2010W01
    String weekt1;        //T+1周 如：2010W02
    String weekt2;
    String weekt3;
    String weekt4;
    String weekt5;
    String weekt6;

    String bussItem;

    public String getBussItem() {
        return bussItem;
    }

    public void setBussItem(String bussItem) {
        this.bussItem = bussItem;
    }

    public String getCurrmonth() {
        return currmonth;
    }

    public void setCurrmonth(String currmonth) {
        this.currmonth = currmonth;
    }

    public String getCurrdate() {
        return currdate;
    }

    public void setCurrdate(String currdate) {
        this.currdate = currdate;
    }

    public String getNextmonth() {
        return nextmonth;
    }

    public void setNextmonth(String nextmonth) {
        this.nextmonth = nextmonth;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getWeekt1() {
        return weekt1;
    }

    public void setWeekt1(String weekt1) {
        this.weekt1 = weekt1;
    }

    public String getWeekt2() {
        return weekt2;
    }

    public void setWeekt2(String weekt2) {
        this.weekt2 = weekt2;
    }

    public String getWeekt3() {
        return weekt3;
    }

    public void setWeekt3(String weekt3) {
        this.weekt3 = weekt3;
    }

    public String getWeekt4() {
        return weekt4;
    }

    public void setWeekt4(String weekt4) {
        this.weekt4 = weekt4;
    }

    public String getWeekt5() {
        return weekt5;
    }

    public void setWeekt5(String weekt5) {
        this.weekt5 = weekt5;
    }

    public String getWeekt6() {
        return weekt6;
    }

    public void setWeekt6(String weekt6) {
        this.weekt6 = weekt6;
    }

    public String getWeekstartdate() {
        return weekstartdate;
    }

    public void setWeekstartdate(String weekstartdate) {
        this.weekstartdate = weekstartdate;
    }

    public String getWeekenddate() {
        return weekenddate;
    }

    public void setWeekenddate(String weekenddate) {
        this.weekenddate = weekenddate;
    }

    public String getWeekt0() {
        return weekt0;
    }

    public void setWeekt0(String weekt0) {
        this.weekt0 = weekt0;
    }
}