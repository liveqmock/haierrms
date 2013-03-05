package haier.activemq.service.ftp.txn.cmsrpt4pbc;

import haier.activemq.service.ftp.core.Ftp4SBS;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-6-30
 * Time: 上午7:29
 * To change this template use File | Settings | File Templates.
 */
public class SbsFileHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public List<String> processSbsFile(String strDate) {
        List<String> resultList = null;
        try {
            getActbalFile(strDate);
            resultList = extractActbalData(strDate);
        } catch (IOException e) {
            throw new RuntimeException("文件处理失败。", e);
        }
        return resultList;
    }


    private void getActbalFile(String strDate) {
        Ftp4SBS ftp4SBS = new Ftp4SBS();
        String localFtpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
        try {
            ftp4SBS.sbsdownload(localFtpPath + strDate + "_bal-pl.010", strDate + "/bal-pl.010");

        } catch (Exception e) {
            logger.error("SBS文件获取失败，请检查网络链接。");
            throw new RuntimeException("SBS文件获取失败，请检查网络链接。", e);
        }
    }

    private List<String> extractActbalData(String strdate) throws IOException {

        String filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

        String pathname = filepath + strdate + "_bal-pl.010";
        File file = new File(pathname);

        List<String> lines = FileUtils.readLines(file);
        if (lines.size() == 0) {
            throw new RuntimeException("文件为空:" + pathname);
        }

        List<String> resultList = new ArrayList<String>();
        for (String line : lines) {
            if (line.indexOf("总帐码") == 1) {
                resultList.add(assembleResultLineBuffer(line, "总帐码"));
            } else if (line.indexOf("核算码") == 1) {
                String apcode = assembleResultLineBuffer(line, "核算码");
                resultList.add(apcode);
            }
        }
        return  resultList;
    }

    private String assembleResultLineBuffer(String line, String code) {
        StringBuilder result = new StringBuilder();
        String[] fileds = StringUtils.split(line, " ");
        result.append(code);
        result.append("|");
        result.append(fileds[1]);
        result.append("|");
        result.append(fileds[2]);
        result.append("|");
        result.append(fileds[4]);
        result.append("|");
        String[] currcodeFileds=StringUtils.split(fileds[5], ":");
        if (currcodeFileds.length == 2) {
            result.append(currcodeFileds[1]);
        }
        return result.toString();
    }
}

