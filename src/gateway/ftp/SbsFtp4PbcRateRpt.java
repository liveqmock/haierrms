package gateway.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * ��ȡSBS������������ˮƽ����
 * User: zhanrui
 * Date: 2011-8-30
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtp4PbcRateRpt {
    private static final Logger logger = LoggerFactory.getLogger(SbsFtp4PbcRateRpt.class);

    private FtpClient sbsclient = new FtpClient("sbsftpconf.properties");

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            //getFileList();
            //download("C:/", "startadmin.sh");
            //uoload();
            SbsFtp4PbcRateRpt sbsftp = new SbsFtp4PbcRateRpt();

            String dataStr = "2011-08-24";
            sbsftp.getBalanceFileFromSBS(dataStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //�ص�

    public String getBalanceFileFromSBS(String strDate) {
        sbsclient.openHandSwitch();

        try {
            if (!sbsclient.ready()) {
                sbsclient.close();
            } else {

                //SBS FTP ????????     d:/rms/ftp/sbs/
                String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

                String ftpfile = ftpPath + strDate + "_savbal_2.010";
                sbsdownload(ftpfile, strDate + "/savbal_2.010");
                return ftpfile;
            }
        } catch (IOException e) {
            logger.error("��ȡSBS�ļ����ִ���", e);
            throw new RuntimeException("��ȡSBS�ļ����ִ���", e);
        } finally {
            try {
                sbsclient.close();
            } catch (IOException e) {
                logger.error("��ȡSBS�ļ����ִ���", e);
                throw new RuntimeException("��ȡSBS�ļ����ִ���", e);
            }
            sbsclient.closeHandSwitch();
        }
        return null;
    }

    public String getTxnDetlFileFromSBS(String strDate) {
        sbsclient.openHandSwitch();

        try {
            if (!sbsclient.ready()) {
                sbsclient.close();
            } else {

                //SBS FTP ????????     d:/rms/ftp/sbs/
                String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

                String ftpfile = ftpPath + strDate + "_savtxn_1.010";
                sbsdownload(ftpfile, strDate + "/savtxn_1.010");
                return ftpfile;
            }
        } catch (IOException e) {
            logger.error("��ȡSBS�ļ����ִ���", e);
            throw new RuntimeException("��ȡSBS�ļ����ִ���", e);
        } finally {
            try {
                sbsclient.close();
            } catch (IOException e) {
                logger.error("��ȡSBS�ļ����ִ���", e);
                throw new RuntimeException("��ȡSBS�ļ����ִ���", e);
            }
            sbsclient.closeHandSwitch();
        }
        return null;
    }

    public void sbsdownload(String path, String filename) throws IOException {
        sbsclient.download(path, filename);
    }

}
