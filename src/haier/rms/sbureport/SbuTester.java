package haier.rms.sbureport;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * �����������Ŵ�CUBE���ݴ���
 * User: zhanrui
 * Date: 2010-4-18
 * Time: 18:17:24
 * To change this template use File | Settings | File Templates.
 */
public class SbuTester {
    private Logger log = Logger.getLogger(this.getClass());
    
    public static void main(String[] argv){
          SbuTester t = new  SbuTester();
//          t.processOneDay();
//          t.processDataInit();

    }

    /**
     * ÿ�ն�ʱ��������
     */
    public void processOneDay(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String strdate = df.format(date);

        IMainETL etl = new PLoanHandler();
        etl.processCurrYearData(strdate,strdate,"5014");
        etl.processCurrYearData(strdate,strdate,"5037");
    }

    /**
     * ��ʱ��γ�ʼ�����ݣ����ظ�����
     */
    public void processDataInit(){

        IMainETL etl = new PLoanHandler();
        etl.processCurrYearData("20100501","20100511","5014");
//        etl.process("20090101","20100510","5037");
    }
}
