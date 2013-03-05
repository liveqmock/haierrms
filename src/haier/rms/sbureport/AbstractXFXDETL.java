package haier.rms.sbureport;

/**
 * 消费信贷系统处理.
 * User: zhanrui
 * Date: 2010-7-6
 * Time: 14:40:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractXFXDETL extends AbstractMainETL {

    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {
        super.processCurrYearData(strStartDate, strEndDate, bussItem);
    }

    public double getLastYearTopLevelData(String strCurrDate, String bussItem) {
        return super.getLastYearTopLevelData(strCurrDate, bussItem);
    }
}