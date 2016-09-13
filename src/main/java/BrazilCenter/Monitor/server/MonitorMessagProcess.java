package BrazilCenter.Monitor.server;

import java.sql.ResultSet;
import java.sql.SQLException;

import BrazilCenter.HeartBeat.Utils.HeartBeatUtils;
import BrazilCenter.Monitor.Main.Main;
import BrazilCenter.Monitor.Utils.LogUtils;
import BrazilCenter.Monitor.Utils.Utils;
import BrazilCenter.Monitor.Utils.XMLOperator;
import BrazilCenter.models.HeartBeatObj;
import BrazilCenter.models.RealTaskInfo;

public class MonitorMessagProcess extends Thread{

	
	public void HeartbeatInsert(HeartBeatObj beatobj) {

		String softwareInsertSql = "insert into " + Utils.softwareTableName + " values ('" + beatobj.getSoftwareid()
				+ "','" + beatobj.getHostname() + "','" + "OnLine!" + "','" + beatobj.getRecvtime() + "','"
				+ beatobj.getLastingtime() + "')";
		Main.db.insert(softwareInsertSql);
	}

	public void HeartbeatUpdate(HeartBeatObj beatobj) {
		
		String softwareUpdateSql = "update " + Utils.softwareTableName + " set name='" + beatobj.getHostname() + "',"
				+ " status='OnLine!', " + "lastheartbeat='" + beatobj.getRecvtime() + "'," + " duration='"
				+ beatobj.getLastingtime() + "'" + " where softwareid='" + beatobj.getSoftwareid() + "'";
		Main.db.insert(softwareUpdateSql);
	}

	/** check if the given softwareid already exist */
	public boolean CheckSoftwareIdExist(String table, String softwareid) {
		String sql = "select * from " + table + " where softwareid='" + softwareid + "'";
		ResultSet rs = Main.db.query(sql);
		boolean exist = false;
		try {
			if (rs.next()) {
				exist = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exist;
	}

	/**
	 * Insert the record into database;
	 * 
	 * @param taskinfo
	 */
	public void RealTaskInfoInsert(RealTaskInfo taskinfo) {
		String endTime = taskinfo.getEndTime();
		String day = endTime.substring(0, endTime.lastIndexOf(' ')).replace("-", "");
		String sql = "insert into realtaskinfo (softwareid, targetsoftwareid, filename, filesize, starttime, endtime, recordday, result, failreason) values ("
				+ "'" + taskinfo.getSoftwareId() + "'," + "'" + taskinfo.getTargetSoftwareId() + "'," + "'"
				+ taskinfo.getFileName() + "'," + "'" + Utils.formatFileSize(taskinfo.getFileSize()) + "'," + "'"
				+ taskinfo.getStartTime() + "','" + endTime + "','" + day + "','" + taskinfo.getResult() + "','"
				+ taskinfo.getFailReason() + "')";
		Main.db.insert(sql);
	}
	
	@Override
	public void run() {
		int msgCount = 0;
		// TODO Auto-generated method stub
		while(true){
			String msg = null;
			if((msg = Utils.MessageList.GetMsg()) != null){
				if (msg.contains("HeartBeat")) {
					HeartBeatObj beatobj = HeartBeatUtils.ParseHeartbeattXML(msg);
					/** Check if the current software id exist. */
					boolean isSoftwareIdExist = this.CheckSoftwareIdExist(Utils.softwareTableName, beatobj.getSoftwareid()); // false:not
																																// // exist
					if (isSoftwareIdExist) { // already exist
						HeartbeatUpdate(beatobj);
					} else { // not exist
						HeartbeatInsert(beatobj);
					}
					LogUtils.logger.info(beatobj.toString());
				} else if (msg.contains("TaskInfo")) {
					msgCount++;
					LogUtils.logger.info("Taskinfo Count: " + msgCount);
					RealTaskInfo UPTaskInfo = XMLOperator.ParseUploadTaskInfoXML(msg);
					RealTaskInfoInsert(UPTaskInfo);
				}
			}else{
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


}
