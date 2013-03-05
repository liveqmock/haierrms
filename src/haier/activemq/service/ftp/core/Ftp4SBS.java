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
                logger.error("FTP 服务器连接错误。");
                throw new RuntimeException("FTP 服务器连接错误。");
            } else {
                sbsclient.download(localfile, remotefile);
            }
        } catch (IOException e) {
            logger.error("FTP 服务器通讯错误。");
            throw new RuntimeException("FTP 服务器通讯错误。", e);
        } finally {
            try {
                sbsclient.close();
            } catch (IOException e) {
                logger.error("FTP 服务器连接错误。");
            }
            sbsclient.closeHandSwitch();
        }
    }

}
