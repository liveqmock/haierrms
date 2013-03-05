package haier.activemq.gateway.ftp;


import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 *  FTP客户端的配置
 *
 */
public final class FtpClientConfig {

	/** ftp服务器地址 */
	private String server;

	/** ftp服务器端口 */
	private String port;

	/** ftp服务器用户名 */
	private String username;

	/** ftp服务器密码 */
	private String password;

	/** ftp服务器显示风格 一般为unix 或者nt*/
	private String FTPStyle;

	/** 本地编码格式 */
	private String localEncoding;

	/** 远程编码格式 */
	private String remoteEncoding;

	/** 是否设置 passiveMode模式 */
	private boolean passiveMode;

	/** 是否设置以二进制传输文件 */
	private boolean binaryFileType;

	/** ftp服务器工作根目录 */
	private String rootPath;

	/** 配置文件 */
	private String file;

	/** 单例 */
	private static FtpClientConfig instance = null;

	private Logger log;

	/**
	 *  初始化参数
	 *
	 */
//	private FtpClientConfig(String file) {
	 FtpClientConfig(String file) {
		try {
			log = Logger.getLogger(getClass());
			this.file = file;
			initConfig();
		} catch (Exception e) {
			log.error("FTP::读取配置文件错误.");
		}
	}

	/**
	 * 	获取配置实例
	 * @param file
	 * @return
	 */
	public static FtpClientConfig getInstance(String file) {
		if(null == instance)
			instance = new FtpClientConfig(file);
		return instance;
	}

	/**
	 *  读取配置文件
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
	 *  设置属性
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
	 *  读取二进制传输方式设置
	 * @return
	 */
	public boolean getBinaryFileType() {
		return binaryFileType;
	}

	/**
	 *  默认以二进制形式传输文件
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
	 *  默认编码为UTF-8
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
	 *  默认编码为UTF-8
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
	 *  默认NT风格
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
	 * 默认支持passiveMode
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
	 *  默认端口为21
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
	 * 默认根目录为"/"
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
