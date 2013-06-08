package haier.rms.psireport;


import java.io.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: huangzunxiu
 * Date: 2010-9-27
 * Time: 16:34:04
 * To change this template use File | Settings | File Templates.
 */
public class readData {
    //filepath = "d:\\xx\\xx"
    public List getDataList(String filepath, String splitStr) throws IOException {
        File file = new File(filepath);
        List<nsmbean> list = new ArrayList();
        nsmbean n = null;
        n = new nsmbean();
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String rowStr = "";

        while ((rowStr = bfr.readLine()) != null) {
            n = getBean(rowStr, splitStr);
            list.add(n);
        }
        return list;
    }


    public nsmbean getBean(String rowStr, String splitStr) {
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
