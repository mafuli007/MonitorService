package BrazilCenter.Monitor.Utils;

import java.text.DecimalFormat;

public class Utils {
	
	public static String softwareTableName = "softwareinfo";
	public static ShareData MessageList = new ShareData();
	
	public static String formatFileSize(long size){
		DecimalFormat formater = new DecimalFormat("####.00");
		if(size<1024){
			return size+"Byte";
		}else if(size<1024*1024){
			float kbsize = size/1024f;  
			return formater.format(kbsize)+"KB";
		}else if(size<1024*1024*1024){
			float mbsize = size/1024f/1024f;  
			return formater.format(mbsize)+"MB";
		}else if(size<1024*1024*1024*1024){
			float gbsize = size/1024f/1024f/1024f;  
			return formater.format(gbsize)+"GB";
		}else{
			return "size: error";
		}	
	}
}
