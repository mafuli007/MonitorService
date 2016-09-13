package BrazilCenter.Monitor.LogServer;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.net.SocketNode;
import org.apache.log4j.xml.DOMConfigurator;

import BrazilCenter.Monitor.Utils.Configuration;
import BrazilCenter.Monitor.Utils.LogUtils;


/**
 * LogServer Used to receive log from client, tranfer and receiver. It could save the logs in
 * files and also in mysql database;
 * @author Fuli Ma
 * 
 */
public class LogServer extends Thread {

	private Configuration conf;
	private String logserverConfig = "log4j.properties";

	public LogServer(Configuration con) {
		this.conf = con;
	}

	private void init(int port, String configFile) {
		if (configFile.endsWith(".xml")) {
			DOMConfigurator.configure(configFile);
		} else {
			PropertyConfigurator.configure(configFile);
		}
	}
	
	public void run() {
		init(this.conf.getLogServerPort(), this.logserverConfig);
		try {
			LogUtils.logger.info("Log Server start Listening......");
			ServerSocket serverSocket = new ServerSocket(this.conf.getLogServerPort());
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(new SocketNode(socket, LogManager.getLoggerRepository()),
						"SimpleSocketServer-" + this.conf.getLogServerPort()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
