package BrazilCenter.Monitor.Main;

import org.apache.log4j.PropertyConfigurator;

import BrazilCenter.Monitor.LogServer.LogServer;
import BrazilCenter.Monitor.Utils.Configuration;
import BrazilCenter.Monitor.Utils.LogUtils;
import BrazilCenter.Monitor.Utils.XMLOperator;
import BrazilCenter.Monitor.db.DBManager;
import BrazilCenter.Monitor.server.CheckOnlineStatus;
import BrazilCenter.Monitor.server.MonitorMessagProcess;
import BrazilCenter.Monitor.server.MonitorServerHandler;
import BrazilCenter.Tcp.Server.TcpServer;

public class Main {

	public static Configuration conf = null;
	public static DBManager db;
	public static XMLOperator xmloperator = new XMLOperator();

	public static void main(String args[]) {
		
		/** Parse the XML configuration*/
		if (!xmloperator.Initial()) {
			LogUtils.logger.error("Parse XML configuration failed.");
			return;
		}
		Main.conf = xmloperator.getConf();
		PropertyConfigurator.configure("log4j.properties");
		LogUtils.logger.info("Parsing XML configuration!");

		db = new DBManager(Main.conf);
		
		/** start online status check monitor */
		CheckOnlineStatus checker = new CheckOnlineStatus();
		checker.start();
		
		/** start log server*/
		LogServer logServer = new LogServer(Main.conf);
		logServer.start();
		
		/** start message process thread*/
		MonitorMessagProcess handleProcess = new MonitorMessagProcess();
		handleProcess.start();
		
		/** Start heart beat listener */
		TcpServer server = new TcpServer(conf.getPort());
		server.start(new MonitorServerHandler());
	}
}
