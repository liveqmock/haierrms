package gateway.ftp;

import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtp4PSI {

    private FtpClient sbsclient = new FtpClient("sbsftpconf.properties");
    private FtpClient psiclient = new FtpClient("psiftpconf.properties");

    /**
     * @param args
     */
    public static void main(String[] args) throws ParseException {
        try {
            //getFileList();
            //download("C:/", "startadmin.sh");
            //uoload();
            SbsFtp4PSI sbsftp = new SbsFtp4PSI();

            String dataStr = "2010-09-29";
            sbsftp.getFileFromSBS(dataStr);
            //sbsftp.lst2Excel(dataStr);
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
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
            sbsdownload(ftpPath + strDate + "_nsm-old.lst", strDate + "/nsm-old.lst");
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
            psiclient.removeFile("", strDate + ".xls");
            psiclient.upload(ftpPath, strDate + ".xls", strDate + ".xls");
        }

        psiclient.close();
        psiclient.closeHandSwitch();
    }



    //===============
    public void putFileToPSIByFilename(String srcFile) throws IOException {
        psiclient.openHandSwitch();
        if (!psiclient.ready()) {
            psiclient.close();
        } else {
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");
            psiclient.removeFile("", srcFile);
            psiclient.upload(ftpPath, srcFile, srcFile);
        }

        psiclient.close();
        psiclient.closeHandSwitch();
    }

}
