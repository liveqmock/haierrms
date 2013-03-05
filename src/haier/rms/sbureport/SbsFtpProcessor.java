package haier.rms.sbureport;

import gateway.ftp.FtpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * 通过FTP 获取SBS文件.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtpProcessor {

//private static FtpClient client = FtpClient.getInstance("D:/rms/ftp/ftpconf.properties");
    private static FtpClient client = FtpClient.getInstance("ftpconf.properties");
    private static final Log logger = LogFactory.getLog(SbsFtpProcessor.class);

    public static void getFileList() throws IOException {
        String[] res = client.listFiles();
        for (int i = 0; i < res.length; i++) {
            System.out.println("---------------" + i + "--------------" + res[i]);

        }
    }

    public static void download(String path, String filename) throws IOException {
        client.download(path, filename);
    }


    /**
     * @param strDate yyyy-MM-dd
     * @throws IOException
     */
    public static void processOneDay(String strDate) throws IOException {
        logger.info("FTP SBS Beginning...");
        client.openHandSwitch();
        if (!client.ready()) {
            client.close();
        } else {
            //SBS FTP ????????     d:/rms/ftp/sbs/
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                download(ftpPath + strDate + "_dpbcptpiv1.010", strDate + "/dpbcptpiv1.010");
                download(ftpPath + strDate + "_dpbcptpiv2.010", strDate + "/dpbcptpiv2.010");
                download(ftpPath + strDate + "_dpbcptpiv3.010", strDate + "/dpbcptpiv3.010");

            } catch (IOException e) {
                logger.error("SBS FTP文件传输出现问题", e);
            }
            download(ftpPath + strDate + "_dpbcptpiv2.010", strDate + "/dpbcptpiv2.010");
            download(ftpPath + strDate + "_dpbcptpiv3.010", strDate + "/dpbcptpiv3.010");
        }

        client.close();
        client.closeHandSwitch();
    }

}