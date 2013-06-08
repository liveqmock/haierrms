package haier.rms.psireport;

import gateway.ftp.SbsFtp4PSI;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Scheduler {
    private Logger log = Logger.getLogger(this.getClass());

    public static void main(String[] argv) {
        Scheduler t = new Scheduler();
        //t.processOneDay();
        //t.processDataInit();
        t.start();
    }

    /**
     * 每日定时处理数据
     */
    public void start() {
        start(new Date());
    }

    public void start(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = df.format(date);

        SbsFtp4PSI sbsftp = new SbsFtp4PSI();

        try {
            sbsftp.getFileFromSBS(strdate);
            //sbsftp.lst2Excel(strdate);
            log.info("共享中心EXCEL 转换完成：" + strdate);
            sbsftp.putFileToPSI(strdate);
            log.info("共享中心EXCEL 上传完成：" + strdate);
        } catch (Exception e) {
            log.error("共享中心EXCE报表出现问题" + strdate, e);
            throw new RuntimeException("共享中心EXCE报表出现问题", e);
        }
    }

}
