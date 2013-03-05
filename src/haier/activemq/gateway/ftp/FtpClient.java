package haier.activemq.gateway.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;

/**
 * FTP�ͻ���
 */
public class FtpClient {
    private FTPClient client = null;

    private static FtpClient instance = null;

    private FtpClientConfig config = null;

    private Logger log = null;

    /**
     * CURRENT����Ŀ¼��ÿ�ιر�����Ҫ��null����Ϊ��ǰ���ǵ�����
     */
    private String workDirectory = null;

    /**
     * �Ƿ��ֹ���������
     */
    private boolean handSwitch = false;

    private boolean ready = false;

    /**
     * ��ʼ���������ü�����commons.net.ftp�Ŀͻ���
     * @param configFile
     */
//    private FtpClient(String configFile) {
     public FtpClient(String configFile) {
        log = Logger.getLogger(getClass());
        client = new FTPClient();
        //config = FtpClientConfig.getInstance(configFile);
        config = new FtpClientConfig(configFile);

        /** ��־��� */
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        client.setControlEncoding(config.getRemoteEncoding());

        // ���õ�ǰ����Ŀ¼
        workDirectory = config.getRootPath();
    }

    /**
     * ��ȡftp�ͻ��˵�ʵ��
     *
     * @return
     */
    public static FtpClient getInstance(String configFile) {
        if (instance == null) instance = new FtpClient(configFile);
        return instance;
    }

    /**
     * ����ftp
     *
     * @return
     * @throws java.net.SocketException
     * @throws java.io.IOException
     */
    private boolean connect() throws SocketException, IOException {
        client.connect(config.getServer(), Integer.valueOf(config.getPort()));
        int reply;
        reply = client.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            client.disconnect();
            log.info("FTP �������Ѿܾ���������.");
            return false;
        }
        return true;
    }

    /**
     * ����ftp
     *
     * @return
     * @throws java.io.IOException
     */
    private boolean login() throws IOException {
        if (!client.login(config.getUsername(), config.getPassword())) {
            client.logout();
            log.info("FTP ��������¼ʧ��.");
            return false;
        }
        return true;
    }

    /**
     * ����Ȼ�����ͳһ���
     *
     * @return
     * @throws java.net.SocketException
     * @throws java.io.IOException
     */
    public boolean ready() throws SocketException, IOException {
        if (connect() && login()) {
            setConfig();
            ready = true;
            return true;
        }
        return false;
    }

    /**
     * ftp���л�����������
     *
     * @throws java.io.IOException
     */
    private void setConfig() throws IOException {
        FTPClientConfig conf = new FTPClientConfig(config.getFTPStyle());
        client.configure(conf);

        // ��������ģʽ
        if (config.getPassiveMode())
            client.enterLocalPassiveMode();

        // �����ƴ���ģʽ
        if (config.getBinaryFileType())
            client.setFileType(FTP.BINARY_FILE_TYPE);

        // ���õ�ǰ����Ŀ¼
        client.changeWorkingDirectory(getWorkDirectory());
    }

    /**
     * �ر�����
     *
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        if (client.isConnected()) {
            client.logout();
            client.disconnect();

            // Ҳ������Ϊnull
            workDirectory = config.getRootPath();
        }
        ready = false;
    }

    /**
     * ��ȡ��ǰ����Ŀ¼���ļ��б�
     *
     * @return
     * @throws java.io.IOException
     */
    public String[] listFiles() throws IOException {
        if (!setReady()) {
            return null;
        }
        FTPFile[] files = client.listFiles();
        int filesLength = files.length;
        String[] fileNameArr = new String[filesLength];
        for (int i = 0; i < filesLength; i++) {
            fileNameArr[i] = files[i].getName();
        }
        setClose();
        return fileNameArr;
    }

    /**
     * �ϴ��ļ����ļ�����ʽ
     *
     * @param path
     * @param name
     * @return
     * @throws java.io.IOException
     */
    public boolean upload(String path, String name, String remoteName) throws IOException {
        if (!setReady()) {
            return false;
        }
        FileInputStream fis = new FileInputStream(path + name);
        if (client.storeFile(getWorkDirectory() + remoteName, fis)) {
            log.info(" �ϴ��ɹ�! ");
            fis.close();
            setClose();
            return true;
        }
        fis.close();
        setClose();
        log.info(" �ϴ�ʧ��! ");
        return false;
    }

    /**
     * �ϴ��ļ�,����ʽ
     *
     * @param name
     * @return
     * @throws java.io.IOException
     */
    public boolean upload(InputStream stream, String name, String remoteName) throws IOException {
        if (!setReady()) {
            return false;
        }
        if (client.storeFile(getWorkDirectory() + remoteName, stream)) {
            log.info(" �ϴ��ɹ�! ");
            stream.close();
            setClose();
            return true;
        }
        stream.close();
        setClose();
        log.info("�ϴ�ʧ��!  ");
        return false;
    }

    /**
     * �����ļ�
     *
     * @param localfile
     * @param remotefile
     * @return
     * @throws java.io.UnsupportedEncodingException
     * @throws java.io.IOException
     */
    public boolean download(String localfile, String remotefile) throws UnsupportedEncodingException, IOException {
        if (!setReady()) {
            return false;
        }
        FileOutputStream fos = new FileOutputStream(localfile);

        if (client.retrieveFile(new String(remotefile.getBytes(config.getLocalEncoding()), config.getRemoteEncoding()), fos)) {
            log.info("�����ļ��ɹ�! ");
            fos.close();
            setClose();
            return true;
        }
        fos.close();
        setClose();
        log.info(" �����ļ�ʧ��! ");
        return false;
    }

    /**
     * ɾ���ļ�
     *
     * @param path
     * @param name
     * @return
     * @throws java.io.IOException
     */
    public boolean removeFile(String path, String name) throws IOException {
        if (!setReady()) {
            return false;
        }
        client.changeWorkingDirectory(config.getRootPath() + path);
        if (client.deleteFile(name)) {
            log.info("ɾ���ļ��ɹ�! ");
            setClose();
            return true;
        }
        setClose();
        log.info(" ɾ���ļ�ʧ��! ");
        return false;
    }

    /**
     * �ı乤��Ŀ¼
     *
     * @param path
     * @throws java.io.IOException
     */
    public void setWorkDirectory(String path) throws IOException {
        workDirectory = (config.getRootPath() + path);

        // ������ֶ����ƿ������øı乤��Ŀ¼
        if (handSwitch) {
            client.changeWorkingDirectory(workDirectory);
        }
    }

    /**
     * ����Ŀ¼
     *
     * @param pathname
     * @return
     * @throws java.io.IOException
     */
    public boolean createDirectory(String pathname) throws IOException {
        if (!setReady()) {
            return false;
        }
        boolean okFlag = client.makeDirectory(pathname);
        setClose();
        return okFlag;
    }

    /**
     * ��ȡ��ǰ����Ŀ¼
     *
     * @return
     */
    public String getWorkDirectory() {
        return workDirectory;
    }

    /**
     * ׼��FTP���ӻ���
     *
     * @return
     * @throws java.net.SocketException
     * @throws java.io.IOException
     */
    private boolean setReady() throws SocketException, IOException {
        if (!ready) {
            if (!ready()) {
                log.error("Ftp ready fail.");
                if (client.isConnected())
                    client.disconnect();
                return false;
            }
        }
        ready = true;
        return true;
    }

    /**
     * �����Ƿ�ftp����
     *
     * @throws java.io.IOException
     */
	private void setClose() throws IOException{
		if(!handSwitch) close();
	}

	/**
     * ���ֶ�����
     */
	public void openHandSwitch() {
		handSwitch = true;
	}

	/**
     * �ر��ֶ�����
     */
	public void closeHandSwitch() {
		handSwitch = false;
	}
}
