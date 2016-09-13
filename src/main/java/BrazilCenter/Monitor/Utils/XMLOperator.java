package BrazilCenter.Monitor.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import BrazilCenter.models.HeartBeatObj;
import BrazilCenter.models.RealTaskInfo;


 public class XMLOperator {

	private String filePath = "MonitorServerConfig.xml";
	private Configuration conf;

	public Configuration getConf() {
		return conf;
	}

	public XMLOperator() {
		this.conf = new Configuration();
	}

	/*
 	 */
	public boolean Initial() {

 		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(filePath);

			NodeList items = document.getChildNodes();
			for (int i = 0; i < items.getLength(); i++) {
				Node value = items.item(i);
				NodeList values = value.getChildNodes();
				for (int j = 0; j < values.getLength(); j++) {
					Node tmp = values.item(j);
					String strvalue = tmp.getTextContent();
					if (tmp.getNodeName().compareTo("ListenPort") == 0) {
						this.conf.setPort(Integer.parseInt(strvalue));
					} else if (tmp.getNodeName().compareTo("LogServerPort") == 0) {
						this.conf.setLogServerPort(Integer.parseInt(strvalue));
					} else if (tmp.getNodeName().compareTo("LongestInterval") == 0) {
						this.conf.setLongestInterval(Integer.parseInt(strvalue));
					} else if (tmp.getNodeName().compareTo("DatabaseInfo") == 0) {
						NodeList innernodelist = tmp.getChildNodes();
						for (int m = 0; m < innernodelist.getLength(); m++) {
							Node innernode = innernodelist.item(m);
							String inerstrvalue = innernode.getTextContent();
							if (innernode.getNodeName().compareTo("DatabaseIp") == 0) {
								this.conf.setDbIp(inerstrvalue);
							} else if (innernode.getNodeName().compareTo("DatabasePort") == 0) {
								this.conf.setDbPort(Integer.parseInt(inerstrvalue));
							} else if (innernode.getNodeName().compareTo("DatabaseInstanceName") == 0) {
								this.conf.setDbInstanceName(inerstrvalue);
							} else if (innernode.getNodeName().compareTo("DatabaseUserName") == 0) {
								this.conf.setDbuserName(inerstrvalue);
							} else if (innernode.getNodeName().compareTo("DatabaseUserPasswd") == 0) {
								this.conf.setDbpassword(inerstrvalue);
							} else {
							}
						}
					} else {

					}
				}
			}
		} catch (FileNotFoundException e) {
			LogUtils.logger.error(e.getMessage());
			return false;
		} catch (ParserConfigurationException e) {
			LogUtils.logger.error(e.getMessage());
			return false;
		} catch (SAXException e) {
			LogUtils.logger.error(e.getMessage());
			return false;
		} catch (IOException e) {
			LogUtils.logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	

	/**
	 * function: parse the heart beat obj.
	 */
	public static RealTaskInfo ParseUploadTaskInfoXML(String msg) {
		RealTaskInfo taskinfo = new RealTaskInfo();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(msg)));

			Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				for (int i = 0; i < books.getLength(); i++) {
					Node book = books.item(i);
					if (book.getFirstChild() != null) {
						String strvalue = book.getFirstChild().getNodeValue();
						if (book.getNodeName().compareTo("MessageType") == 0) {
							///////
						} else if (book.getNodeName().compareTo("SoftwareId") == 0) {
							taskinfo.setSoftwareId(strvalue);
						} else if (book.getNodeName().compareTo("TargetSoftwareId") == 0) {
							taskinfo.setTargetSoftwareId(strvalue);
						} else if (book.getNodeName().compareTo("StartTime") == 0) {
							taskinfo.setStartTime(strvalue);
						} else if (book.getNodeName().compareTo("EndTime") == 0) {
							taskinfo.setEndTime(strvalue);
						} else if (book.getNodeName().compareTo("FileName") == 0) {
							taskinfo.setFileName(strvalue);
						} else if (book.getNodeName().compareTo("FileSize") == 0) {
							taskinfo.setFileSize(Long.valueOf(strvalue));
						} else if (book.getNodeName().compareTo("SourceAddress") == 0) {
							taskinfo.setSourceAddress(strvalue);
						} else if (book.getNodeName().compareTo("Result") == 0) {
							taskinfo.setResult(strvalue);
						} else if (book.getNodeName().compareTo("FailReason") == 0) {
							taskinfo.setFailReason(strvalue);
						} else {
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtils.logger.error("ParseUploadTaskInfoXML Error :" + e.getMessage());
		}
		return taskinfo;
	}
}
