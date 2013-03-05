package haier.activemq.gateway.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;

/**
 * FTP客户端
 */
public class FtpClient {
    private FTPClient client = null;

    private static FtpClient instance = null;

    private FtpClientConfig config = null;

    private Logger log = null;

    /**
     * CURRENT工作目录，每次关闭连接要回null，因为当前类是单例类
     */
    private String workDirectory = null;

    /**
     * 是否手工控制连接
     */
    private boolean handSwitch = false;

    private boolean ready = false;

    /**
     * 初始化参数配置及创建commons.net.ftp的客户端
     * @param configFile
     */
//    private FtpClient(String configFile) {
     public FtpClient(String configFile) {
        log = Logger.getLogger(getClass());
        client = new FTPClient();
        //config = FtpClientConfig.getInstance(configFile);
        config = new FtpClientConfig(configFile);

        /** 日志输出 */
        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        client.setControlEncoding(config.getRemoteEncoding());

        // 设置当前工作目录
        workDirectory = config.getRootPath();
    }

    /**
     * 获取ftp客户端的实例
     *
     * @return
     */
    public static FtpClient getInstance(String configFile) {
        if (instance == null) instance = new FtpClient(configFile);
        return instance;
    }

    /**
     * 连接ftp
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
            log.info("FTP 服务器已拒绝连接请求.");
            return false;
        }
        return true;
    }

    /**
     * 登入ftp
     *
     * @return
     * @throws java.io.IOException
     */
    private boolean login() throws IOException {
        if (!client.login(config.getUsername(), config.getPassword())) {
            client.logout();
            log.info("FTP 服务器登录失败.");
            return false;
        }
        return true;
    }

    /**
     * 连接然后登入统一入口
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
     * ftp运行环境参数配置
     *
     * @throws java.io.IOException
     */
    private void setConfig() throws IOException {
        FTPClientConfig conf = new FTPClientConfig(config.getFTPStyle());
        client.configure(conf);

        // 被动传输模式
        if (config.getPassiveMode())
            client.enterLocalPassiveMode();

        // 二进制传输模式
        if (config.getBinaryFileType())
            client.setFileType(FTP.BINARY_FILE_TYPE);

        // 设置当前工作目录
        client.changeWorkingDirectory(getWorkDirectory());
    }

    /**
     * 关闭连接
     *
     * @throws java.io.IOException
     */
    public void close() throws IOException {
        if (client.isConnected()) {
            client.logout();
            client.disconnect();

            // 也可设置为null
            workDirectory = config.getRootPath();
        }
        ready = false;
    }

    /**
     * 获取等前工作目录的文件列表
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
     * 上传文件，文件名方式
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
            log.info(" 上传成功! ");
            fis.close();
            setClose();
            return true;
        }
        fis.close();
        setClose();
        log.info(" 上传失败! ");
        return false;
    }

    /**
     * 上传文件,流方式
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
            log.info(" 上传成功! ");
            stream.close();
            setClose();
            return true;
        }
        stream.close();
        setClose();
        log.info("上传失败!  ");
        return false;
    }

    /**
     * 下载文件
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
            log.info("下载文件成功! ");
            fos.close();
            setClose();
            return true;
        }
        fos.close();
        setClose();
        log.info(" 下载文件失败! ");
        return false;
    }

    /**
     * 删除文件
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
            log.info("删除文件成功! ");
            setClose();
            return true;
        }
        setClose();
        log.info(" 删除文件失败! ");
        return false;
    }

    /**
     * 改变工作目录
     *
     * @param path
     * @throws java.io.IOException
     */
    public void setWorkDirectory(String path) throws IOException {
        workDirectory = (config.getRootPath() + path);

        // 如果是手动控制可以设置改变工作目录
        if (handSwitch) {
            client.changeWorkingDirectory(workDirectory);
        }
    }

    /**
     * 创建目录
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
     * 获取当前工作目录
     *
     * @return
     */
    public String getWorkDirectory() {
        return workDirectory;
    }

    /**
     * 准备FTP连接环境
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
     * 设置是否ftp连接
     *
     * @throws java.io.IOException
     */
	private void setClose() throws IOException{
		if(!handSwitch) close();
	}

	/**
     * 打开手动连接
     */
	public void openHandSwitch() {
		handSwitch = true;
	}

	/**
     * 关闭手动连接
     */
	public void closeHandSwitch() {
		handSwitch = false;
	}
}
