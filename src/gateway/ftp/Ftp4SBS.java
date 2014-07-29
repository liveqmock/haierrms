package gateway.ftp;

import org.apache.log4j.Logger;
import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class Ftp4SBS {
    private Logger logger = Logger.getLogger(this.getClass());
    private FtpClient sbsclient = new FtpClient("sbsftpconf.properties");

    public void getFileList() throws IOException {
        String[] res = sbsclient.listFiles();
        for (int i = 0; i < res.length; i++) {
            System.out.println("---------------" + i + "--------------" + res[i]);

        }
    }

    public void sbsdownload(String path, String filename) throws IOException {
        sbsclient.download(path, filename);
    }

    /**
     * 共享中心
     * @param strDate
     * @throws IOException
     */

    public void getFile4PSI(String strDate) throws IOException {
        sbsclient.openHandSwitch();
        if (!sbsclient.ready()) {
            sbsclient.close();
        } else {
            //getFileList();

            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                sbsdownload(ftpPath + strDate + "_nsm-old.lst", strDate + "/nsm-old.lst");
            } catch (IOException e) {
                throw new RuntimeException("通过FTP方式获取SBS文件失败。", e);
            }
        }

        sbsclient.close();
        sbsclient.closeHandSwitch();
    }


    /**
     * 获取SBS余额、汇率、AH股分类文件
     * @param strDate
     * @throws IOException
     */
    public void getActbalFile(String strDate) throws IOException {
        sbsclient.openHandSwitch();
        if (!sbsclient.ready()) {
            sbsclient.close();
        } else {
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                sbsdownload(ftpPath + strDate + "_actbal-bk.lst", strDate + "/actbal-bk.lst");
                //A股H股名单
                sbsdownload(ftpPath + strDate + "_actcxr-bk.lst", strDate + "/actcxr-bk.lst");
                //当前汇率
                sbsdownload(ftpPath + strDate + "_actaha-bk.lst", strDate + "/actaha-bk.lst");
                //核算码总账码对照表
                sbsdownload(ftpPath + strDate + "_actapc_1.010", strDate + "/actapc_1.010");
                //总账码核算码明细表
                sbsdownload(ftpPath + strDate + "_actglc_1.010", strDate + "/actglc_1.010");
            } catch (IOException e) {
                logger.error("SBS文件获取失败，可能网络链接出现错误。");
                throw new RuntimeException("SBS文件获取失败，可能网络链接出现错误。");
                //TODO  log
            }
        }

        sbsclient.close();
        sbsclient.closeHandSwitch();
    }
}
