package haier.service.rms.pbcreport;

import gateway.ftp.SbsFtp4PbcRateRpt;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pub.platform.advance.utils.PropertyManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 人民银行利率水平报表
 * User: zhanrui
 * Date: 11-5-25
 * Time: 上午10:23
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SbsRateQryService {
    private static final Logger logger = LoggerFactory.getLogger(SbsRateQryService.class);



    //====================================================================
    public String getBalanceBuffer(String strDate10)  {
        SbsFtp4PbcRateRpt sbsFtp4PbcRateRpt = new SbsFtp4PbcRateRpt();
        sbsFtp4PbcRateRpt.getBalanceFileFromSBS(strDate10);
        String filename = "_savbal_2.010";
        return tranSbsTxt2PbcTxt(strDate10, filename);
    }
    public String getTxnDetailBuffer(String strDate10)  {
        SbsFtp4PbcRateRpt sbsFtp4PbcRateRpt = new SbsFtp4PbcRateRpt();
        sbsFtp4PbcRateRpt.getTxnDetlFileFromSBS(strDate10);

        String filename = "_savtxn_1.010";
        return tranSbsTxt2PbcTxt(strDate10, filename);
    }

    private String tranSbsTxt2PbcTxt(String strDate10, String filename) {
        String filepath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
        filepath = filepath + strDate10 + filename;

        File file = new File(filepath);

        List<String> lines = null;
        try {
            lines = FileUtils.readLines(file);
        } catch (IOException e) {
            throw new RuntimeException("文件错误:" + filepath);
        }
        if (lines.size() == 0) {
            throw new RuntimeException("文件为空:" + filepath);
        }

        List<String> resultList = new ArrayList<String>();
        StringBuffer buffer = new StringBuffer();
        for (String line : lines) {
            String[] fileds = line.split("\\|");
            int count=0;
            int length = fileds.length;
            for (String filed : fileds) {
                if ("11111111".equals(filed)) {
                    filed = "";
                }
                buffer.append(filed.trim());
                count++;
                if (count < length) {
                    buffer.append(";");
                }
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }


}
