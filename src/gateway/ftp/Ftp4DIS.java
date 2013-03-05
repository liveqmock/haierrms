package gateway.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * 全面预算系统所需企业余额表.
 * User: zhanrui
 * Date: 2011-4-8
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class Ftp4DIS {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private FtpClient disclient = new FtpClient("disftpconf.properties");


    /**
     * ！！
     * @param strDate
     * @throws IOException
     */
    public void putFileToDIS(String strDate) throws IOException {
        disclient.openHandSwitch();
        if (!disclient.ready()) {
            disclient.close();
        } else {
            String ftpPath = PropertyManager.getProperty("FTP_DIS_ROOTPATH");

            try {
                disclient.removeFile("/", strDate + ".xls");
                disclient.upload(ftpPath, strDate + "_actbal.xls", strDate + "_actbal.xls");
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        disclient.close();
        disclient.closeHandSwitch();
    }
}
