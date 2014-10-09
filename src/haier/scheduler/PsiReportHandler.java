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
 * PSI共享中心报表
 * 1、企业发生额明细
 * 2、企业余额
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
            logger.error("定时任务出现错误.", e);
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

    //txt 企业余额报表
    private boolean processOneDay_balRpt(String strdate) {
        boolean isOK = true;
        try {
            generateTxtReport(strdate);
        } catch (IOException e) {
            isOK = false;
            logger.error("生成SBS余额文件出现问题。" + strdate, e);
            //throw new RuntimeException("生成SBS余额文件出现问题。。");
        }

        try {
            SbsFtp4PSI ftp = new SbsFtp4PSI();
            String fileName = strdate + "_actbal_psi.txt";
            ftp.putFileToPSIByFilename(fileName);
        } catch (Exception e) {
            isOK = false;
            logger.error("FTP接口出现问题。" + strdate, e);
            //throw new RuntimeException("FTP接口出现问题。", e);
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
                logger.error("生成PSI余额文件错误。" + strdate, e);
                throw new RuntimeException("生成PSI余额文件错误。", e);
            } finally {
                if (fw != null) {
                    fw.close();
                }
            }
        }
    }

    //excel:交易明细报表
    private boolean processOneDay_detlRpt(String strdate) {
        boolean isOK = true;
        SbsFtp4PSI sbsftp = new SbsFtp4PSI();
        try {
            sbsftp.getFileFromSBS(strdate);
            lst2Excel(strdate);
            logger.info("共享中心EXCEL 转换完成：" + strdate);
            sbsftp.putFileToPSI(strdate);
            logger.info("共享中心EXCEL 上传完成：" + strdate);
        } catch (Exception e) {
            isOK = false;
            logger.error("共享中心EXCE报表出现问题" + strdate, e);
            //throw new RuntimeException("共享中心EXCE报表出现问题", e);
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

    //业务过滤规则：判断帐号是否属于规定的总账码 (所有存款类)
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
