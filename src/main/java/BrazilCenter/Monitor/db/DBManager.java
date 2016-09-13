package BrazilCenter.Monitor.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import BrazilCenter.Monitor.Utils.Configuration;
import BrazilCenter.Monitor.Utils.LogUtils;
 
/**
  * @author phoenix
 * 
 * */

public class DBManager {

	private Connection conn;
	private Statement st;

	private String ip;
	private int port;
	private String instance;
	private String username;
	private String password;
	
	public DBManager(Configuration conf) {

		this.ip = conf.getDbIp();
		this.port = conf.getDbPort();
		this.instance = conf.getDbInstanceName();
		this.username = conf.getDbuserName();
		this.password = conf.getDbpassword();

		getConnection();
	}

	/** ��ȡ���ݿ����ӵĺ��� */
	private void getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����Mysql��������
			String url = "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.instance+ "?" + "autoReconnect=true";
			conn = DriverManager.getConnection(url, this.username, this.password); // ������������
			conn.setAutoCommit(true);
			st = (Statement) conn.createStatement(); // ��������ִ�о�̬sql����Statement����

			LogUtils.logger.info("Connect to Database Successfully!");
		} catch (Exception e) {
			LogUtils.logger.error("Failed to connect to Database" + e.getMessage());
		}
	}

	/** �������ݼ�¼���������������ݼ�¼�� */
	public int insert(String sql) {

		int count = 0;
		try {
			count = st.executeUpdate(sql);  
		} catch (SQLException e) {
			LogUtils.logger.error("SQL Error" + sql + ", "+ e.getMessage());
		}
		return count;
	}

	/** ��ѯ���ݿ⣬�������Ҫ��ļ�¼����� */
	public ResultSet query(String sql) {
		if(st == null){
			return null;
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql); // ִ��sql��ѯ��䣬���ز�ѯ���ݵĽ����
		} catch (SQLException e) {
			LogUtils.logger.error("SQL Error"+ sql + ", " + e.getMessage());
		}
		return rs;
	}

	/** clear the assigned table */
	public void ClearTable(String tablename){
		String delSql = "delete from " + tablename;
		try{
			st.execute(delSql);
		}catch(Exception e){
			LogUtils.logger.error("SQL Error： clear " + tablename + "failed");
		}
	}
	
	public void Close() {
		try {
			conn.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
