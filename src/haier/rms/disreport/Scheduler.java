package haier.rms.disreport;

import gateway.ftp.Ftp4DIS;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ȫ��Ԥ��ϵͳ�ӿ���������
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
     * ÿ�ն�ʱ��������
     */
    public void start() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = df.format(date);

        Ftp4DIS dis = new Ftp4DIS();

        //������
        //strdate = "2011-01-11";
        
        try {
//            dis.getFileFromSBS(strdate);
//
//            dis.lst2Excel(strdate);
//            log.info("��������EXCEL ת����ɣ�" + strdate);
//
//            dis.putFileToPSI(strdate);
            log.info("��������EXCEL �ϴ���ɣ�" + strdate);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            log.error("��������EXCE�����������" + strdate, e);
            //TODO
        }
    }

}
