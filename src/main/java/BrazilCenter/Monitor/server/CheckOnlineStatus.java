package BrazilCenter.Monitor.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import BrazilCenter.Monitor.Main.Main;
import BrazilCenter.Monitor.db.DBManager;


/** Check the online status, if the heart beat is timeout,
 *  adjust the online status into 'no' */
public class CheckOnlineStatus extends Thread {
	
	int interval = Main.conf.getLongestInterval();
	int sleeptime = interval - 3;
	private String querySql = "select softwareid, lastheartbeat from softwareinfo ";
	private DBManager db;
	
	public CheckOnlineStatus(){
		this.db = new DBManager(Main.conf);
	}
	
	public void run(){
		while(true){
			/** Get the software list from db */
			ResultSet rs = Main.db.query(querySql);
			try {
				Date nowDate = new Date();
				while(rs.next()){
					String softwareid = rs.getString("softwareid");
					Timestamp lastbeat = rs.getTimestamp("lastheartbeat");
					Date lastbeattime = lastbeat;
					if((nowDate.getTime()/1000 - lastbeattime.getTime()/1000) >=interval){
						String updateSql="update softwareinfo set status='OFFLINE!' where softwareid='"+ softwareid+"'";
						this.db.insert(updateSql);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(sleeptime * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
