package haier.rms.sbureport;

import haier.rms.model.PLoanKey;
import haier.rms.model.Sburpt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  消费信贷系统数据处理
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 9:20:54
 * To change this template use File | Settings | File Templates.
 */
public class XFXDDataETL extends AbstractXFXDETL {
    private static final Log logger = LogFactory.getLog(XFXDDataETL.class);

    /*
    消费信贷 （住房按揭）
    将CUBE中的数据汇总到sburpt中
     */

    @Override
    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {

        Calendar cal_curr = Calendar.getInstance();
        Calendar cal_base = Calendar.getInstance();

        cal_curr.clear();
        cal_base.clear();

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
        cal_curr.setFirstDayOfWeek(Calendar.MONDAY);
        cal_curr.setMinimalDaysInFirstWeek(7);

        cal_base.setTime(basedate);
        cal_base.setFirstDayOfWeek(Calendar.MONDAY);
        cal_base.setMinimalDaysInFirstWeek(7);

        java.text.DecimalFormat formater = new java.text.DecimalFormat("00");


        //本周周数
        String weekT0 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));


        //本周第一天
        cal_curr.setTime(currdate);
        cal_curr.set(Calendar.DAY_OF_WEEK, cal_curr.getFirstDayOfWeek());
        String strWeekStartDate = sdf_yyyymmdd.format(cal_curr.getTime());

        //下周一日期
        cal_curr.add(Calendar.DATE, 7);
        String weekT1 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));
        cal_curr.add(Calendar.DATE, 7);
        String weekT2 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));
        cal_curr.add(Calendar.DATE, 7);
        String weekT3 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));
        cal_curr.add(Calendar.DATE, 7);
        String weekT4 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));
        cal_curr.add(Calendar.DATE, 7);
        String weekT5 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));
        cal_curr.add(Calendar.DATE, 7);
        String weekT6 = String.valueOf(cal_curr.get(Calendar.YEAR)) + "W" + formater.format(cal_curr.get(Calendar.WEEK_OF_YEAR));


        //IBATIS 参数
        PLoanKey key = new PLoanKey();

        key.setStartdate(strStartDate);
        key.setEnddate(strEndDate);
        key.setCurrdate(strEndDate);
        key.setWeekenddate(strEndDate);
        
        key.setWeekstartdate(strWeekStartDate);

        key.setWeekt0(weekT0);
        key.setWeekt1(weekT1);
        key.setWeekt2(weekT2);
        key.setWeekt3(weekT3);
        key.setWeekt4(weekT4);
        key.setWeekt5(weekT5);
        key.setWeekt6(weekT6);

        //区分房贷(5014)与消费信贷（5037）
        key.setBussItem(bussItem);

        //获取数据
        Sburpt sburpt = new Sburpt();

        List<Sburpt> list = null;
        if ("5014".equals(bussItem)) {  //房贷
            list = session.selectList("CubeInfo.selectPLoanSbuData", key);
        }else if ("5037".equals(bussItem)) {//消费信贷
            list = session.selectList("CubeInfo.selectXFSbuData", key);
        } else {
            logger.error("BUSSITEM有误。");
            throw new RuntimeException("BUSSITEM有误。");
        }

//        List<Sburpt> list = session.selectList("CubeInfo.selectPLoanSbuData", key);

        //更新数据库
        for (Sburpt info: list){
            sburpt.setBussItem(bussItem);
            sburpt.setCustNo(info.getCustNo());
            sburpt.setCustName(info.getCustName());
            sburpt.setDataDate(strEndDate);
            sburpt.setYearActr(info.getYearActr());
            sburpt.setWeekActr(info.getWeekActr());
            sburpt.setWeekActrT1(info.getWeekActrT1());
            sburpt.setWeekActrT2(info.getWeekActrT2());
            sburpt.setWeekActrT3(info.getWeekActrT3());
            sburpt.setWeekActrT4(info.getWeekActrT4());
            sburpt.setWeekActrT5(info.getWeekActrT5());
            sburpt.setWeekActrT6(info.getWeekActrT6());
            session.insert("InterestInfo.insertSburpt", sburpt);
        }
        session.commit();
    }
}