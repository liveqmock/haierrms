package gateway.ftp;

import pub.platform.advance.utils.PropertyManager;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-5-11
 * Time: 16:34:46
 * To change this template use File | Settings | File Templates.
 */
public class SbsFtpTest {

//private static FtpClient client = FtpClient.getInstance("D:/rms/ftp/ftpconf.properties");
    private static FtpClient client = FtpClient.getInstance("ftpconf.properties");

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            //getFileList();
            //download("C:/", "startadmin.sh");
            //uoload();
            oneconnect("2010-09-26");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
     * ??????????? ????¡¤?? ????????? ????????
     *
     * @throws IOException
     */
    public static void uoload() throws IOException {
        client.upload("C:/", "a.txt", "b.txt"); 
    }

    public static void oneconnect() throws IOException {
        client.openHandSwitch();
        if (!client.ready()) {
            client.close();
        } else {
            getFileList();
            download("d:/rms/ftp/sbs/2010-04-30/", "dpbcptpiv1.010");
//            uoload();
        }
        client.close();
        client.closeHandSwitch();
    }

    //ÖØµã
    public static void oneconnect(String strDate) throws IOException {
        client.openHandSwitch();
        if (!client.ready()) {
            client.close();
        } else {
            //getFileList();

            //SBS FTP ????????     d:/rms/ftp/sbs/
            String ftpPath = PropertyManager.getProperty("FTP_SBS_ROOTPATH");

            try {
                download(ftpPath + strDate + "_dpbcptpiv1.010", strDate + "/dpbcptpiv1.010");
                download(ftpPath + strDate + "_dpbcptpiv2.010", strDate + "/dpbcptpiv2.010");
                download(ftpPath + strDate + "_dpbcptpiv3.010", strDate + "/dpbcptpiv3.010");
                //download(ftpPath + strDate + "_nsm-old.lst", strDate + "/nsm-old.lst");

                //??????????
                
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            download(ftpPath + strDate + "_dpbcptpiv2.010", strDate + "/dpbcptpiv2.010");
            download(ftpPath + strDate + "_dpbcptpiv3.010", strDate + "/dpbcptpiv3.010");
        }

        client.close();
        client.closeHandSwitch();
    }

}
