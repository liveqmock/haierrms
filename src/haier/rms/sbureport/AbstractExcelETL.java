package haier.rms.sbureport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 手工台帐数据处理.
 * User: zhanrui
 * Date: 2010-7-6
 * Time: 14:40:57
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractExcelETL extends AbstractMainETL {
    private static final Log logger = LogFactory.getLog(AbstractExcelETL.class);

    public void processCurrYearData(String strStartDate, String strEndDate, String bussItem) {
        super.processCurrYearData(strStartDate, strEndDate, bussItem);
    }

    public double getLastYearTopLevelData(String strCurrDate, String bussItem) {
        return super.getLastYearTopLevelData(strCurrDate, bussItem);
    }
}