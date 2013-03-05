package haier.rms.sbureport;

import haier.rms.model.Sburpt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * 处理 营业支出等一级表中数据.
 * User: zhanrui
 * Date: 2010-4-13
 * Time: 9:20:54
 * To change this template use File | Settings | File Templates.
 */
public class TopLevelDataETL extends AbstractMainETL {
    private static final Log logger = LogFactory.getLog(TopLevelDataETL.class);

    public  void processCurrYearData(String strStartDate, String strEndDate, String bussItem){

    }

    /**
     * 处理已经FTP完成的文本文件
     * @param strCurrDate
     * @param bussItem
     */
    private void generateDataFromFile(String strCurrDate, String bussItem){

    }
    /**
     * 直接读取SBURPT表中的数据
     * @param strCurrDate
     * @param bussItem
     * @return
     */
    public double getTopLevelData(String strCurrDate, String bussItem) {
        Sburpt param = new Sburpt();

        param.setBussItem(bussItem);
        param.setDataDate(strCurrDate);

        Double result = 0.00;
        SbsFtpProcessor processor = new SbsFtpProcessor();
        SbsFtpFileHandler handler = new SbsFtpFileHandler();
        try {
            String uifmtDate = strCurrDate.substring(0,4)+"-"+strCurrDate.substring(4,6)+"-"+strCurrDate.substring(6,8);
            processor.processOneDay(uifmtDate);
            handler.processOneDay(uifmtDate,bussItem);
            result = (Double)session.selectOne("InterestInfo.selectTopLevelData", param);
            if (result == null) {
                result = 0.00;
            }
        } catch (IOException e) {
            logger.error("处理失败",e);
        }
        return result;
    }
}