package haier.activemq.gateway.ftp;


import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 *  FTP�ͻ��˵�����
 *
 */
public final class FtpClientConfig {

	/** ftp��������ַ */
	private String server;

	/** ftp�������˿� */
	private String port;

	/** ftp�������û��� */
	private String username;

	/** ftp���������� */
	private String password;

	/** ftp��������ʾ��� һ��Ϊunix ����nt*/
	private String FTPStyle;

	/** ���ر����ʽ */
	private String localEncoding;

	/** Զ�̱����ʽ */
	private String remoteEncoding;

	/** �Ƿ����� passiveModeģʽ */
	private boolean passiveMode;

	/** �Ƿ������Զ����ƴ����ļ� */
	private boolean binaryFileType;

	/** ftp������������Ŀ¼ */
	private String rootPath;

	/** �����ļ� */
	private String file;

	/** ���� */
	private static FtpClientConfig instance = null;

	private Logger log;

	/**
	 *  ��ʼ������
	 *
	 */
//	private FtpClientConfig(String file) {
	 FtpClientConfig(String file) {
		try {
			log = Logger.getLogger(getClass());
			this.file = file;
			initConfig();
		} catch (Exception e) {
			log.error("FTP::��ȡ�����ļ�����.");
		}
	}

	/**
	 * 	��ȡ����ʵ��
	 * @param file
	 * @return
	 */
	public static FtpClientConfig getInstance(String file) {
		if(null == instance)
			instance = new FtpClientConfig(file);
		return instance;
	}

	/**
	 *  ��ȡ�����ļ�
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	private String getProperty(String prop) throws Exception {
		BufferedReader br = null;
		try {
//			InputStream inStream = new FileInputStream(new File(file));
            
            InputStream inStream = getClass().getResourceAsStream(file);

            Properties properties=new Properties();
            properties.load(inStream);
            return properties.getProperty(prop);

			/*
			br = new BufferedReader(new InputStreamReader(inStream));

			String temp = null;
			do{
				temp = br.readLine();
				if (temp == null)
					break;
				if (temp.startsWith("#"))
					continue;
				int index = temp.indexOf("=");
				if (index == -1)
					continue;
				String key = temp.substring(0, index).trim();
				String value = temp.substring(index + 1).trim();
				if (key.equals(prop)) {
					br.close();
					return value;
				}
			}while (temp != null);
			br.close();
			return null;
			*/
		} catch (Exception e) {
			if (br != null)
				br.close();
			throw e;
		}
	}

	/**
	 *  ��������
	 * @throws Exception
	 */
	private void initConfig() throws Exception{
		setServer(getProperty("server"));
		setPort(getProperty("port"));
		setUsername(getProperty("username"));
		setPassword(getProperty("password"));
		setFTPStyle(getProperty("ftpstyle"));
		setLocalEncoding(getProperty("localencoding"));
		setRemoteEncoding(getProperty("remoteencoding"));
		setPassiveMode(getProperty("passivemode"));
		setBinaryFileType(getProperty("binaryfiletype"));
		setRootPath(getProperty("rootpath"));
	}

	/**
	 *  ��ȡ�����ƴ��䷽ʽ����
	 * @return
	 */
	public boolean getBinaryFileType() {
		return binaryFileType;
	}

	/**
	 *  Ĭ���Զ�������ʽ�����ļ�
	 * @param binaryFileType
	 */
	public void setBinaryFileType(String binaryFileType) {
		if(null == binaryFileType){
			this.binaryFileType = true;
		}else {
			if("".equals(binaryFileType.trim())){
				this.binaryFileType = true;
			}else if ("true".equals(binaryFileType.trim())) {
				this.binaryFileType = true;
			}else if ("false".equals(binaryFileType.trim())) {
				this.binaryFileType = false;
			}
		}
	}

	public String getLocalEncoding() {
		return localEncoding;
	}

	/**
	 *  Ĭ�ϱ���ΪUTF-8
	 */
	public void setLocalEncoding(String encoding) {
		if(null == encoding){
			localEncoding = "UTF-8";
		}else {
			if("".equals(encoding.trim())) localEncoding = "UTF-8";
		}
		this.localEncoding = encoding.trim();
	}

	public String getRemoteEncoding() {
		return remoteEncoding;
	}

	/**
	 *  Ĭ�ϱ���ΪUTF-8
	 */
	public void setRemoteEncoding(String encoding) {
		if(null == encoding){
			this.remoteEncoding = "UTF-8";
		}else {
			if("".equals(encoding.trim())) remoteEncoding = "UTF-8";
		}
		this.remoteEncoding = encoding.trim();
	}

	public String getFTPStyle() {
		return FTPStyle;
	}

	/**
	 *  Ĭ��NT���
	 * @param style
	 */
	public void setFTPStyle(String style) {
		if(null == style){
			this.FTPStyle = FTPClientConfig.SYST_NT;
		}else {
			if("".equals(style.trim())){
				this.FTPStyle = FTPClientConfig.SYST_NT;
			}else if ("unix".equals(style.trim())) {
				this.FTPStyle = FTPClientConfig.SYST_UNIX;
			}else if ("nt".equals(style.trim())) {
				this.FTPStyle = FTPClientConfig.SYST_NT;
			}
		}
	}

	public boolean getPassiveMode() {
		return passiveMode;
	}

	/**
	 * Ĭ��֧��passiveMode
	 * @param passiveMode
	 */
	public void setPassiveMode(String passiveMode) {
		if(passiveMode == null){
			this.passiveMode = true;
		}else {
			if("".equals(passiveMode.trim())){
				this.passiveMode = true;
			}else if ("true".equals(passiveMode.trim())) {
				this.passiveMode = true;
			}else if ("false".equals(passiveMode.trim())) {
				this.passiveMode = false;
			}
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	/**
	 *  Ĭ�϶˿�Ϊ21
	 * @param port
	 */
	public void setPort(String port) {
		if( null == port ){
			port = "21";
		}else {
			if("".equals(port.trim())) port = "21";
		}
		this.port = port.trim();
	}

	public String getRootPath() {
		return rootPath;
	}

	/**
	 * Ĭ�ϸ�Ŀ¼Ϊ"/"
	 * @param rootPath
	 */
	public void setRootPath(String rootPath) {
		if( null == rootPath ){
			rootPath = "/";
		}else {
			if("".equals(rootPath.trim())) rootPath = "/";
		}
		this.rootPath = rootPath.trim();
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
