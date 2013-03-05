package haier.scheduler;

import gateway.ftp.Ftp4DIS;
import haier.repository.model.disreport.ActbalHistory;
import haier.service.rms.disreport.DisReportService;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ȡ���ݿ���������������EXCEl�ļ������ϴ���DIS��FTP������
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 18:17:24
 */
//@Service
public class DisReportHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private DisReportService disReportService;

    public void setDisReportService(DisReportService disReportService) {
        this.disReportService = disReportService;
    }

    //====================================================================
    public void run() {
        this.run(new Date());
    }

    public void run(Date date) {
        processOneWeek(date);
    }

    private void processOneWeek(Date date) {
        DateTime dateTime = new DateTime(date);
        int i = 0;
        do {
            processOneDay(dateTime.toString("yyyy-MM-dd"));
            dateTime = dateTime.minusDays(1);
            i++;
        } while (i < 7);

    }

    private void processOneDay(String strdate) {
        try {
            generateDisExcelReport(strdate);
        } catch (IOException e) {
            logger.error("ת��SBS����ļ��������⡣" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("ת��SBS����ļ��������⡣��");
        }

        try {
            Ftp4DIS dis = new Ftp4DIS();
            dis.putFileToDIS(strdate);
        } catch (Exception e) {
            logger.error("��DISϵͳ��FTP�ӿڳ������⡣" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("��DISϵͳ��FTP�ӿڳ������⡣");
        }

    }

    private void generateDisExcelReport(String strdate) throws IOException {

        String destFileName = PropertyManager.getProperty("FTP_DIS_ROOTPATH") + strdate + "_actbal.xls";
        String templateFileName = PropertyManager.getProperty("REPORT_ROOTPATH") + "dis/actbal.xls";

        List<ActbalHistory> historyList = disReportService.selectDisActbal(strdate);

        if (historyList.size() != 0) {
            Map beans = new HashMap();
            beans.put("actbals", historyList);
            XLSTransformer transformer = new XLSTransformer();

            try {
                transformer.transformXLS(templateFileName, beans, destFileName);
            } catch (Exception e) {
                logger.error("����DIS���EXCEL�ļ�����" + strdate, e);
                //TODO DBLOG
                throw new RuntimeException("����DIS���EXCEL�ļ�����");
            }
        }
    }
}
