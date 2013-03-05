package gateway.ftp;

import haier.rms.psireport.ExportExcel;
import haier.rms.psireport.nsmbean;
import haier.rms.psireport.readData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtp4PSI {

    //private FtpClient sbsclient = FtpClient.getInstance("sbsftpconf.properties");
    private FtpClient sbsclient = new FtpClient("sbsftpconf.properties");
    //private FtpClient psiclient = FtpClient.getInstance("psiftpconf.properties");
    private FtpClient psiclient = new FtpClient("psiftpconf.properties");

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            //getFileList();
            //download("C:/", "startadmin.sh");
            //uoload();
            SbsFtp4PSI sbsftp = new SbsFtp4PSI();

            String dataStr = "2010-09-29";
            sbsftp.getFileFromSBS(dataStr);
            sbsftp.lst2Excel(dataStr);
            sbsftp.putFileToPSI(dataStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFileList() throws IOException {
        String[] res = sbsclient.listFiles();
        for (int i = 0; i < res.length; i++) {
            System.out.println("---------------" + i + "--------------" + res[i]);

        }
    }

    public void sbsdownload(String path, String filename) throws IOException {
        sbsclient.download(path, filename);
    }


    public void oneconnect() throws IOException {
        sbsclient.openHandSwitch();
        if (!sbsclient.ready()) {
            sbsclient.close();
        } else {
            getFileList();
            sbsdownload("d:/rms/ftp/sbs/2010-04-30/", "dpbcptpiv1.010");
//            uoload();
        }
        sbsclient.close();
        sbsclient.closeHandSwitch();
    }

    //ÖØµã

    public void getFileFromSBS(String strDate) throws IOException {
        sbsclient.openHandSwitch();
        if (!sbsclient.ready()) {
            sbsclient.close();
        } else {
            //getFileList();

            //SBS FTP ????????     d:/rms/ftp/sbs/
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                sbsdownload(ftpPath + strDate + "_nsm-old.lst", strDate + "/nsm-old.lst");

            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        sbsclient.close();
        sbsclient.closeHandSwitch();
    }

    public void putFileToPSI(String strDate) throws IOException {
        psiclient.openHandSwitch();
        if (!psiclient.ready()) {
            psiclient.close();
        } else {
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                //2012/01/04 zhanrui Î´²âÊÔ
                //psiclient.removeFile("pub/gxzx/", strDate + ".xls");
                psiclient.removeFile("", strDate + ".xls");
                psiclient.upload(ftpPath,  strDate + ".xls", strDate + ".xls");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        psiclient.close();
        psiclient.closeHandSwitch();
    }


    public void lst2Excel(String strDate) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        ExportExcel ext = new ExportExcel(wb, sheet);
        ext.createHead(true);

        String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

        String strPath = ftpPath + strDate + "_" + "nsm-old.lst";

        readData rd = new readData();
        List al = rd.getDataList(strPath, "|");
        Iterator<nsmbean> it = al.iterator();
        try {
            int count = 0;
            while (it.hasNext()) {
                nsmbean n = it.next();
                ext.createBody(true, n);
//                System.out.println(n.getBorrowamt());
                count++;
                //System.out.println("count=" + count);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ext.outputExcel(ftpPath + strDate + ".xls");
    }

}
