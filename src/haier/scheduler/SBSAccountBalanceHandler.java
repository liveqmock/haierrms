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
 * 通过FTP方式获取SBS对公帐户当日余额文件，解析文件后写入本地数据库表中
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 18:17:24
 * <p/>
 * 每日账户余额相关下载数据
 * 文件存放路径:
 * ftp://192.168.91.5/pub/print/yyyy-mm-dd/
 * 文件格式说明:
 * 1. actbal.lst 对公账户余额
 * 日期|账号|户名|余额
 * 2. actcxr.lst 当日牌价
 * 币别|汇率种类(4-中间价)|第二货币|当前汇率值|汇率值
 * 折人民币余额 = 余额 * 当前汇率值
 * 3. actaha.lst 上市公司账户清单
 * 种类(A,H)|账号|户名
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
            logger.error("与SBS系统的FTP接口出现问题。" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("与SBS系统的FTP接口出现问题。");
        }

        try {
            extractActbalTxtToDb(strdate);
            extractActcxrTxtToDb(strdate);
            extractActahaTxtToDb(strdate);
        } catch (IOException e) {
            logger.error("转换SBS余额文件出现问题。" + strdate, e);
            //TODO DBLOG
            throw new RuntimeException("转换SBS余额文件出现问题。。");
        }

    }

    /**
     * 转换账户余额文件到数据库
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
     * A股H股定义文件
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
