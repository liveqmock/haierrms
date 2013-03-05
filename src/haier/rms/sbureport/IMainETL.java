package haier.rms.sbureport;


/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 8:22:32
 * To change this template use File | Settings | File Templates.
 */
public interface IMainETL {

    /**
     * 处理本年度数据
     * @param strStartDate    yyyy-MM-dd
     * @param strEndDate      yyyy-MM-dd
     * @param bussItem
     */
    void processCurrYearData(String strStartDate, String strEndDate, String bussItem);

     /**
     * 处理上年度年度数据，直接返回一级表结果
     * @param strCurrDate    yyyy-MM-dd   当前日期
     * @param bussItem
     */
    double getLastYearTopLevelData(String strCurrDate, String bussItem);

}