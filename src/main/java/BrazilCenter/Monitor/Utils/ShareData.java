package BrazilCenter.Monitor.Utils;

import java.util.LinkedList;
import java.util.Queue;

public class ShareData {
	
	private Queue<String> receivedMsgList = new LinkedList<String>();
	
	public  synchronized void AddMsg(String msg){
		this.receivedMsgList.add(msg);
	}
	
	public synchronized String GetMsg(){
		return this.receivedMsgList.poll();
	}
	
}
