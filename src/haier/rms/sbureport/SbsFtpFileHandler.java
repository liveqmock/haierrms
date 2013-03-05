package haier.rms.sbureport;

import gateway.common.util.Message;
import gateway.common.util.ScreenCommonMessages;
import gateway.common.util.SystemErrorException;
import gateway.SBS.ExchangeRate.ExRateRecordBean;
import haier.rms.exchangerate.SBSHandler;
import haier.rms.model.Sburpt;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.regex.Pattern;


/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-3
 * Time: 17:10:41
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtpFileHandler {

    SqlSession session;
    private static final Log logger = LogFactory.getLog(SbsFtpFileHandler.class);

    public static void main(String[] args) {
        try {
            SbsFtpFileHandler h = new SbsFtpFileHandler();
            h.processOneDay("2010-06-01", "6001");
            h.processOneDay("2010-06-01", "6002");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected SbsFtpFileHandler() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("Configuration.xml");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IBATIS 参数文件读取错误。");
            throw new RuntimeException(e);
        }
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        this.session = sessionFactory.openSession();

    }

    public void processOneDay(String currdate, String item) throws IOException {
        BigDecimal totalamt = new BigDecimal("0.00");
        //同业往来
        BigDecimal amt_6002 = totalamt.add(processOneDayFileFor6002(currdate));
        //数据库易用的日期格式
        String strdbdate = currdate.substring(0, 4) + currdate.substring(5, 7) + currdate.substring(8, 10);
        if ("6001".equals(item)) {//利息支出
            totalamt = totalamt.add(processOneDayFile(currdate, "_dpbcptpiv1.010", 6, 2, 13));
            totalamt = totalamt.add(processOneDayFile(currdate, "_dpbcptpiv2.010", 7, 2, 7));
            //已付利息（正负符号反过来处理）
            totalamt = totalamt.subtract(processOneDayFile(currdate, "_dpbcptpiv3.010", 6, 2, 6));
            //减去同业往来（已改变符号）
            totalamt = totalamt.subtract(amt_6002);
            //处理数据库记录
            Sburpt sburpt = new Sburpt();
            sburpt.setBussItem("6001");
            sburpt.setYearActr(totalamt.doubleValue() / 10000);
            sburpt.setCustNo("000000");
            sburpt.setCustName("000000");
            sburpt.setDataDate(strdbdate);

            session.delete("InterestInfo.deleteByItemAndDate_sburpt", sburpt);
            session.insert("InterestInfo.insertSburpt", sburpt);
            session.commit();
        } else if ("6002".equals(item)) {
            Sburpt sburpt = new Sburpt();
            sburpt.setBussItem("6002");
            sburpt.setYearActr(amt_6002.doubleValue() / 10000);
            sburpt.setCustNo("000000");
            sburpt.setCustName("000000");
            sburpt.setDataDate(strdbdate);
            session.delete("InterestInfo.deleteByItemAndDate_sburpt", sburpt);
            session.insert("InterestInfo.insertSburpt", sburpt);
            session.commit();
        } else {
            throw new SystemErrorException("参数不正确");
        }

    }

    /**
     * 6002 同业往来支出处理
     *
     * @param currdate
     * @return
     * @throws IOException
     */
    public BigDecimal processOneDayFileFor6002(String currdate) throws IOException {
        File file = new File("d:/rms/ftp/sbs/" + currdate + "_dpbcptpiv3.010");
        if (!file.exists()) {
            throw new IOException();
        }
        LineIterator it = FileUtils.lineIterator(file, "GBK");
        try {
            int count = 0;
            BigDecimal totalamt = new BigDecimal("0.00");
            BigDecimal tmpamt, tmprate;
            while (it.hasNext()) {
                count++;
                String line = it.nextLine();
                if (count > 6) {
                    String begin = line.substring(0, 1);
                    if (!" ".equals(begin)) {
                        String[] array = StringUtils.split(line, " ");
                        String accountingcode = array[1];
                        String tmpcurrcode = array[2];
                        if ("6021".equals(accountingcode)
                                || "6022".equals(accountingcode)
                                || "6521".equals(accountingcode)
                                || "6522".equals(accountingcode)
                                || "6023".equals(accountingcode)
                                || "6024".equals(accountingcode)
                                || "6025".equals(accountingcode)
                                || "6014".equals(accountingcode)
                                || "6514".equals(accountingcode)
                                ) {//同业往来支出
                            String stramt = array[6];
                            int l = stramt.length();
                            if ("-".equals(stramt.substring(l - 1, l))) {
                                stramt = "-" + stramt.substring(0, l - 1);
                            }
                            tmpamt = new BigDecimal(stramt);
                            if (!"001".equals(tmpcurrcode)) {
                                String exchangerate = getLastExRate(currdate, tmpcurrcode);
                                BigDecimal rate = new BigDecimal(exchangerate);
                                tmpamt = tmpamt.multiply(rate).multiply(new BigDecimal("0.01"));
                            }

                            totalamt = totalamt.add(tmpamt);
                        }
                    }
                }
            }
            return new BigDecimal("0").subtract(totalamt);
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * 6001利息支出处理(处理三个文本文件) 包括 应付未付的活期利息、定期利息   以及已付的利息
     *
     * @param currdate
     * @param filename
     * @param startline
     * @param currcodecolume
     * @param amtcolume
     * @return
     * @throws IOException
     */
    public BigDecimal processOneDayFile(String currdate, String filename, int startline, int currcodecolume, int amtcolume) throws IOException {
        File file = new File("d:/rms/ftp/sbs/" + currdate + filename);
        if (!file.exists()) {
            throw new IOException();
        }
        LineIterator it = FileUtils.lineIterator(file, "GBK");
        try {
            int count = 0;
            BigDecimal totalamt = new BigDecimal("0.00");
            BigDecimal tmpamt, tmprate;
            while (it.hasNext()) {
                count++;
                String line = it.nextLine();
                if (count > startline) {
                    String begin = line.substring(0, 1);
                    if (!" ".equals(begin)) {
                        String[] array = StringUtils.split(line, " ");
                        String tmpcurrcode = array[currcodecolume];
//                        tmpamt = new BigDecimal(array[amtcolume]);
                        String stramt = array[amtcolume];
/*
                        if (isNumeric(stramt)) {
                            tmpamt = new BigDecimal(stramt);
                        } else {
                            logger.error("数字转换出现问题。");
                            throw new RuntimeException("数字转换出现问题。");
                        }
*/
                        int l = stramt.length();
                        if ("-".equals(stramt.substring(l - 1, l))) {
                            stramt = "-" + stramt.substring(0, l - 1);
                        }

                        tmpamt = new BigDecimal(stramt);

                        if (!"001".equals(tmpcurrcode)) {    //非人民币
                            String exchangerate = getLastExRate(currdate, tmpcurrcode);
/*
                            ExRateRecordBean record = new ExRateRecordBean();
                            record.setCURCDE(tmpcurrcode);
                            record.setSECCCY("001"); //人民币
                            String dbdate = currdate.substring(0, 4) + currdate.substring(5, 7) + currdate.substring(8, 10);
                            record.setEFFDAT(dbdate);
                            ExRateRecordBean resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
                            //本地表中未查询到此币种汇率信息时
                            if (resultbean == null) {
                                //TODO 实时查询SBS
                                SBSHandler handler = new SBSHandler();
                                handler.querySBS(session,dbdate, tmpcurrcode, "001");
                                resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
                                if (resultbean == null) {
                                    Message message = ScreenCommonMessages.M00001001E;
                                    message.create("未找到对应的汇率信息，币种：" + tmpcurrcode);
                                    throw new SystemErrorException(ScreenCommonMessages.M00001001E.toString());
                                }
                                // resultbean.setRATVA4("0.00");
                            }
                            String exchangerate = resultbean.getRATVA4().trim();

                            //TODO 汇率千分位处理 !!
                            exchangerate = exchangerate.replaceAll("(\\d+),(\\d)", "$1$2");
*/
                            BigDecimal rate = new BigDecimal(exchangerate);

                            //TODO
                            tmpamt = tmpamt.multiply(rate).multiply(new BigDecimal("0.01"));
                        }
                        totalamt = totalamt.add(tmpamt);
                    }
                }
            }
            return totalamt;
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * 取最新汇率 （中间价）
     *
     * @param currdate    yyyy-MM-dd
     * @param tmpcurrcode
     * @return
     */
    private String getLastExRate(String currdate, String tmpcurrcode) {
        ExRateRecordBean record = new ExRateRecordBean();
        record.setCURCDE(tmpcurrcode);
        record.setSECCCY("001"); //人民币
        String dbdate = currdate.substring(0, 4) + currdate.substring(5, 7) + currdate.substring(8, 10);
        record.setEFFDAT(dbdate);
        ExRateRecordBean resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
        //本地表中未查询到此币种汇率信息时
        if (resultbean == null) {
            //TODO 实时查询SBS
            SBSHandler handler = new SBSHandler();
            handler.querySBS(session, dbdate, tmpcurrcode, "001");
            resultbean = (ExRateRecordBean) session.selectOne("ExchangeRate.selectRate", record);
            if (resultbean == null) {
                Message message = ScreenCommonMessages.M00001001E;
                message.create("未找到对应的汇率信息，币种：" + tmpcurrcode);
                throw new SystemErrorException(ScreenCommonMessages.M00001001E.toString());
            }
        }
        String exchangerate = resultbean.getRATVA4().trim();
        //TODO 汇率千分位处理
        exchangerate = exchangerate.replaceAll("(\\d+),(\\d)", "$1$2");
        return exchangerate;
    }

    public boolean isNumeric(String str) {
        //对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
        //(.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
        Pattern pattern = Pattern.compile("[0-9]+(.[0-9]+)?");
        return pattern.matcher(str).matches();
    }
}
