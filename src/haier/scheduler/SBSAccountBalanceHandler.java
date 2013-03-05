package haier.scheduler;

import gateway.ftp.Ftp4SBS;
import haier.repository.model.SbsActaha;
import haier.repository.model.SbsActbal;
import haier.repository.model.SbsActcxr;
import haier.service.rms.sbsbatch.ActbalService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import pub.platform.advance.utils.PropertyManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ͨ��FTP��ʽ��ȡSBS�Թ��ʻ���������ļ��������ļ���д�뱾�����ݿ����
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 18:17:24
 * <p/>
 * ÿ���˻���������������
 * �ļ����·��:
 * ftp://192.168.91.5/pub/print/yyyy-mm-dd/
 * �ļ���ʽ˵��:
 * 1. actbal.lst �Թ��˻����
 * ����|�˺�|����|���
 * 2. actcxr.lst �����Ƽ�
 * �ұ�|��������(4-�м��)|�ڶ�����|��ǰ����ֵ|����ֵ
 * ���������� = ��� * ��ǰ����ֵ
 * 3. actaha.lst ���й�˾�˻��嵥
 * ����(A,H)|�˺�|����
 */
//@Service
public class SBSAccountBalanceHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ActbalService actbalService;

    public ActbalService getActbalService() {
        return actbalService;
    }

    public void setActbalService(ActbalService actbalService) {
        this.actbalService = actbalService;
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
            Ftp4SBS sbs = new Ftp4SBS();
            sbs.getActbalFile(strdate);
        } catch (Exception e) {
            logger.error("��SBSϵͳ��FTP�ӿڳ������⡣" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("��SBSϵͳ��FTP�ӿڳ������⡣");
        }

        try {
            extractActbalTxtToDb(strdate);
            extractActcxrTxtToDb(strdate);
            extractActahaTxtToDb(strdate);
        } catch (IOException e) {
            logger.error("ת��SBS����ļ��������⡣" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("ת��SBS����ļ��������⡣��");
        }

    }

    /**
     * ת���˻�����ļ������ݿ�
     *
     * @param strdate
     * @throws IOException
     */
    private void extractActbalTxtToDb(String strdate) throws IOException {

        String filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

        File file = new File(filepath + strdate + "_actbal-bk.lst");

        List<String> lines = FileUtils.readLines(file);
        if (lines.size() == 0) {
            return;
        }

        actbalService.deleteActbalSameDateRecord(strdate);

        SbsActbal actbal = new SbsActbal();
        Date date = new Date();
        for (String line : lines) {
            String[] fileds = StringUtils.split(line, "|");
            actbal.setTxndate(fileds[0].trim());
            actbal.setActno(fileds[1].trim());
            actbal.setActname(fileds[2].trim());
            actbal.setBalamt(new BigDecimal(fileds[3].trim()));
            actbal.setActtype(fileds[4].trim());
            actbal.setOperid("AUTO");
            actbal.setOperdate(date);
            actbalService.insertActbal(actbal);
        }

    }


    private void extractActcxrTxtToDb(String strdate) throws IOException {
        String filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
        File file = new File(filepath + strdate + "_actcxr-bk.lst");

        List<String> lines = FileUtils.readLines(file);
        if (lines.size() == 0) {
            return;
        }
        actbalService.deleteActcxrSameDateRecord(strdate);
        SbsActcxr actcxr = new SbsActcxr();
        Date date = new Date();
        for (String line : lines) {
            String[] fileds = StringUtils.split(line, "|");
            actcxr.setTxndate(fileds[0].trim());
            actcxr.setCurcde(fileds[1].trim());
            actcxr.setXrtcde(fileds[2].trim());
            actcxr.setSecccy(fileds[3].trim());
            actcxr.setCurrat(new BigDecimal(fileds[4].trim()));
            actcxr.setRatval(new BigDecimal(fileds[5].trim()));
            actcxr.setOperid("AUTO");
            actcxr.setOperdate(date);
            if ("8".equals(actcxr.getXrtcde())) {
                actbalService.insertActcxr(actcxr);
            }
        }
    }

    /**
     * A��H�ɶ����ļ�
     * @param strdate
     * @throws IOException
     */
    private void extractActahaTxtToDb(String strdate) throws IOException {
        String filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
        File file = new File(filepath + strdate + "_actaha-bk.lst");

        List<String> lines = FileUtils.readLines(file);
        if (lines.size() == 0) {
            return;
        }
        actbalService.deleteActahaAllRecord();
        SbsActaha actaha = new SbsActaha();
        Date date = new Date();
        for (String line : lines) {
            String[] fileds = StringUtils.split(line, "|");
            actaha.setActtype(fileds[0].trim());
            actaha.setActno(fileds[1].trim());
            actaha.setActname(fileds[2].trim());
            actaha.setOperid("AUTO");
            actaha.setOperdate(date);
            actbalService.insertActaha(actaha);
        }
    }
}
