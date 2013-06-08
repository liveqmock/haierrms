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
     * ÿ�ն�ʱ��������
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
            log.info("��������EXCEL ת����ɣ�" + strdate);
            sbsftp.putFileToPSI(strdate);
            log.info("��������EXCEL �ϴ���ɣ�" + strdate);
        } catch (Exception e) {
            log.error("��������EXCE�����������" + strdate, e);
            throw new RuntimeException("��������EXCE�����������", e);
        }
    }

}
