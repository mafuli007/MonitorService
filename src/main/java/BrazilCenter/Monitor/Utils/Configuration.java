package BrazilCenter.Monitor.Utils;

/** ������Ϣ */
public class Configuration {

	/**ҵ����Ϣ��������Ϣ�����˿� */
	private int port;
	
	/**��־�����������˿�*/
	private int logServerPort;
	
	/** �����ʱ���� */
	private int longestInterval;
	
	/** ���ݿ���Ϣ */
	private String dbIp;
	private int dbPort;
	private String dbInstanceName;
	private String dbuserName;
	private String dbpassword;

	/** �����˿� */
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	public int getDbPort() {
		return dbPort;
	}

	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbInstanceName() {
		return dbInstanceName;
	}

	public void setDbInstanceName(String dbInstanceName) {
		this.dbInstanceName = dbInstanceName;
	}

	public String getDbuserName() {
		return dbuserName;
	}

	public void setDbuserName(String dbuserName) {
		this.dbuserName = dbuserName;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public int getLongestInterval() {
		return longestInterval;
	}

	public void setLongestInterval(int longestInterval) {
		this.longestInterval = longestInterval;
	}

	public int getLogServerPort() {
		return logServerPort;
	}

	public void setLogServerPort(int logServerPort) {
		this.logServerPort = logServerPort;
	}
}
