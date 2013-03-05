package haier.rms.disreport;

import gateway.ftp.Ftp4DIS;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全面预算系统接口批量处理
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 18:17:24
 * To change this template use File | Settings | File Templates.
 */
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
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = df.format(date);

        Ftp4DIS dis = new Ftp4DIS();

        //测试用
        //strdate = "2011-01-11";
        
        try {
//            dis.getFileFromSBS(strdate);
//
//            dis.lst2Excel(strdate);
//            log.info("共享中心EXCEL 转换完成：" + strdate);
//
//            dis.putFileToPSI(strdate);
            log.info("共享中心EXCEL 上传完成：" + strdate);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            log.error("共享中心EXCE报表出现问题" + strdate, e);
            //TODO
        }
    }

}
