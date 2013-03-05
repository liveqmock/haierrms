package haier.activemq.service.ftp.core;

import haier.activemq.gateway.ftp.FtpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class Ftp4SBS {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private FtpClient sbsclient = new FtpClient("sbsftpconf.properties");


    public void sbsdownload(String localfile, String remotefile) {
        try {
            sbsclient.openHandSwitch();
            if (!sbsclient.ready()) {
                sbsclient.close();
                logger.error("FTP ���������Ӵ���");
                throw new RuntimeException("FTP ���������Ӵ���");
            } else {
                sbsclient.download(localfile, remotefile);
            }
        } catch (IOException e) {
            logger.error("FTP ������ͨѶ����");
            throw new RuntimeException("FTP ������ͨѶ����", e);
        } finally {
            try {
                sbsclient.close();
            } catch (IOException e) {
                logger.error("FTP ���������Ӵ���");
            }
            sbsclient.closeHandSwitch();
        }
    }

}
