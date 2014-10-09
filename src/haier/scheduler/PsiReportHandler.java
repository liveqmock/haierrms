package haier.scheduler;

import gateway.ftp.SbsFtp4PSI;
import haier.repository.model.SbsActapc;
import haier.repository.model.SbsActbal;
import haier.rms.psireport.ExportExcel;
import haier.rms.psireport.nsmbean;
import haier.service.rms.psireport.PsiReportService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import pub.platform.advance.utils.PropertyManager;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * PSI�������ı���
 * 1����ҵ��������ϸ
 * 2����ҵ���
 * User: zhanrui
 * Date: 2013-5-27
 */
public class PsiReportHandler {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private PsiReportService psiReportService;

    public void setPsiReportService(PsiReportService psiReportService) {
        this.psiReportService = psiReportService;
    }

    //====================================================================
    public void run() {
        DateTime dt = new DateTime();
        this.run(dt.minusDays(1).toDate());
    }

    public void run(Date date) {
        try {
            processOneWeek(date);
        } catch (InterruptedException e) {
            logger.error("��ʱ������ִ���.", e);
        }
    }

    private void processOneWeek(Date date) throws InterruptedException {
        boolean isOK = true;
        DateTime dateTime = new DateTime(date);
        int i = 0;
        do {
            isOK = processOneDay_balRpt(dateTime.toString("yyyy-MM-dd"));
            int failCount = 5;
            while (!isOK && failCount > 0 ) {
                Thread.sleep(10 * 1000);
                isOK = processOneDay_balRpt(dateTime.toString("yyyy-MM-dd"));
                failCount -- ;
            }

            isOK = processOneDay_detlRpt(dateTime.toString("yyyy-MM-dd"));
            failCount = 5;
            while (!isOK && failCount > 0 ) {
                Thread.sleep(10 * 1000);
                isOK = processOneDay_detlRpt(dateTime.toString("yyyy-MM-dd"));
                failCount -- ;
            }
            dateTime = dateTime.minusDays(1);
            i++;
        } while (i < 7);

    }

    //txt ��ҵ����
    private boolean processOneDay_balRpt(String strdate) {
        boolean isOK = true;
        try {
            generateTxtReport(strdate);
        } catch (IOException e) {
            isOK = false;
            logger.error("����SBS����ļ��������⡣" + strdate, e);
            //throw new RuntimeException("����SBS����ļ��������⡣��");
        }

        try {
            SbsFtp4PSI ftp = new SbsFtp4PSI();
            String fileName = strdate + "_actbal_psi.txt";
            ftp.putFileToPSIByFilename(fileName);
        } catch (Exception e) {
            isOK = false;
            logger.error("FTP�ӿڳ������⡣" + strdate, e);
            //throw new RuntimeException("FTP�ӿڳ������⡣", e);
        }

        return isOK;
    }

    private void generateTxtReport(String strdate) throws IOException {
        String destFileName = PropertyManager.getProperty("FTP_SBS_ROOTPATH") + strdate + "_actbal_psi.txt";
        List<SbsActbal> actbalList = psiReportService.selectActbal(strdate);

        if (actbalList.size() != 0) {
            FileWriter fw = null;
            try {
                fw = new FileWriter(destFileName);
                for (SbsActbal bal : actbalList) {
                    String line = bal.getTxndate() + "," + bal.getActno() + "," + bal.getActname() + "," + bal.getBalamt() + "\n";
                    fw.write(line);
                }
                fw.flush();
            } catch (Exception e) {
                logger.error("����PSI����ļ�����" + strdate, e);
                throw new RuntimeException("����PSI����ļ�����", e);
            } finally {
                if (fw != null) {
                    fw.close();
                }
            }
        }
    }

    //excel:������ϸ����
    private boolean processOneDay_detlRpt(String strdate) {
        boolean isOK = true;
        SbsFtp4PSI sbsftp = new SbsFtp4PSI();
        try {
            sbsftp.getFileFromSBS(strdate);
            lst2Excel(strdate);
            logger.info("��������EXCEL ת����ɣ�" + strdate);
            sbsftp.putFileToPSI(strdate);
            logger.info("��������EXCEL �ϴ���ɣ�" + strdate);
        } catch (Exception e) {
            isOK = false;
            logger.error("��������EXCE�����������" + strdate, e);
            //throw new RuntimeException("��������EXCE�����������", e);
        }
        return isOK;
    }

    private void lst2Excel(String strDate) throws ParseException, IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        ExportExcel ext = new ExportExcel(wb, sheet);
        ext.createHead(true);

        String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
        String strPath = ftpPath + strDate + "_" + "nsm-old.lst";

        //readData rd = new readData();
        List al = getDataList(strPath, "|");
        Iterator<nsmbean> it = al.iterator();
        while (it.hasNext()) {
            nsmbean n = it.next();
            ext.createBody(true, n);
        }
        ext.outputExcel(ftpPath + strDate + ".xls");
    }

    private List getDataList(String filepath, String splitStr) throws IOException {
        File file = new File(filepath);
        List<nsmbean> list = new ArrayList();
        nsmbean n = new nsmbean();
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String rowStr = "";

        List<SbsActapc> actapcList = psiReportService.selectActapc();
        while ((rowStr = bfr.readLine()) != null) {
            n = getBean(rowStr, splitStr);
            boolean founded = filterRecordResult(n, actapcList);
            if (founded) {
                list.add(n);
            }
        }
        return list;
    }

    //ҵ����˹����ж��ʺ��Ƿ����ڹ涨�������� (���д����)
    private boolean filterRecordResult(nsmbean n, List<SbsActapc> actapcList) {
        boolean founded = false;
        for (SbsActapc sbsActapc : actapcList) {
            if (sbsActapc.getApcode().equals(n.getBankacct().substring(7,11))) {
                if ("2010".equals(sbsActapc.getGlcode())
                        ||"2020".equals(sbsActapc.getGlcode())
                        ||"2030".equals(sbsActapc.getGlcode())
                        ||"2210".equals(sbsActapc.getGlcode())
                        ||"2220".equals(sbsActapc.getGlcode())
                        ||"2230".equals(sbsActapc.getGlcode())) {
                    return true;
                }
            }
        }
        return founded;
    }


    private nsmbean getBean(String rowStr, String splitStr) {
        nsmbean nsm = new nsmbean();
        String[] dataAry = new String[9];
        dataAry = rowStr.split("\\" + splitStr);
        nsm.setSyslscode(dataAry[0].trim());
        nsm.setCompanyName(dataAry[1].trim());
        nsm.setBankacct(dataAry[3].trim());
        nsm.setCommerce(dataAry[4].trim());
        nsm.setComdate(dataAry[5].trim());
        nsm.setBorrowamt(dataAry[6].trim());
        nsm.setLoanamt(dataAry[7].trim());
        nsm.setSumamt(dataAry[8].trim());
        return nsm;
    }

}
