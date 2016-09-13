package BrazilCenter.Monitor.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import junit.framework.TestCase;

public class MonitorServerHandlerTest extends TestCase {

	public void testChannelReadChannelHandlerContextObject() {
		MonitorServerHandler handler = new MonitorServerHandler();
		String msg = "<?xml version=\"1.0\" " + "encoding=\"UTF-8\" standalone=\"no\"?><info><MessageType>"
				+ "TaskInfo</MessageType><SoftwareId>client_1</SoftwareId><StartTime>"
				+ "2016-09-08 15:02:44.942</StartTime><EndTime>2016-09-08 15:02:44.952"
				+ "</EndTime><TargetSoftwareId>transfer</TargetSoftwareId><FileName>FKT_DPS01_IIG_L31_STP_20150312020000.PNG</FileName>"
				+ "<FileSize>51961</FileSize><SourceAddress>"
				+ "BrazilTestlientSourceData\\</SourceAddress><Result>success</Result><FailReason/></info> "
				+ "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><info><MessageType>TaskInfo</MessageType><SoftwareId>client_1</SoftwareId><StartTime>2016-09-08 15:02:44.982</StartTime><EndTime>2016-09-08 15:02:44.982</EndTime><TargetSoftwareId>transfer</TargetSoftwareId><FileName>FKT_DPS01_IIG_L31_STP_20150312020500.PNG</FileName><FileSize>47471</FileS";
		String msg2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><info><MessageType>HeartBeat</MessageType><SoftwareId>client_1</SoftwareId><LocalIp>10.66.153.186</LocalIp><CurrentTime>2016-09-08 16:36:30</CurrentTime><Duration>0d 0h 3m 15s</Duration><HostName>bpcw02</HostName><CpuPercent>0</CpuPercent><MemoryPercent>0</MemoryPercent><DiskPercent>0</DiskPercent></info>";
		ByteBuf firstMessage  = null;
		byte[] data = msg2.getBytes();
		firstMessage = Unpooled.buffer();
		firstMessage.writeBytes(data);
		handler.channelRead(null, firstMessage);
	}

}
