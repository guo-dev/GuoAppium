package com.guo.appium.utils;

public class Command {
	private static String osName = System.getProperty("os.name");
	private static String portCheck;
	private static String killServers;
	private static String androidDevices;
	private static String IOSDevices;
	
	public static Boolean isMac(){
		if(osName.toLowerCase().contains("mac")){
			return true;
		}
		return false;
	}
	
	public static String getPortCheck(){
		String s="netstat -an|findstr ";
		if(isMac()){
			s="configs/netstat.sh ";//当你发现在mac该文件没有执行权限时，请使用chmod 777 针对文件授权
		}
		return s;
	}
	public static String getKillServers(){
		String s="taskkill -f -pid node.exe ";
		if(isMac()){
			s="killall node ";
		}
		return s;
	}
	public static String getAndroidDevices(){
		String s="adb devices ";
		return s;
	}
	public static String getIOSDevices(){
		String s="idevice_id -l";
		return s;
	}
}
