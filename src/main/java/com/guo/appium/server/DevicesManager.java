package com.guo.appium.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guo.appium.utils.Command;
import com.guo.appium.utils.CommandLineExec;
import com.guo.appium.utils.Log;

public class DevicesManager {

	private Log logger = Log.getLogger(DevicesManager.class);
	public Map<String, String> devicesMap;
	public int devicesCounts;

	public DevicesManager(int deviceType) {
		this.devicesMap = getDevices(deviceType);
		this.devicesCounts = devicesMap.size();
	}

	/**
	 * 根据传入的测试设备类型获取当前可用的设备udid
	 * {"udid1":"android","udid2":"android","udid3":"ios"}
	 * @param deviceType
	 *            0:android and ios;1:android;2:ios
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getDevices(int deviceType) {
		Map<String, String> deviceMap = new HashMap<String, String>();
		if (deviceType == 0 && Command.isMac()) {
			List<String> androidList = getAndroidDevices();
			// {key:value} {"hdgt12ff":"android"}
			for (String s : androidList) {
				deviceMap.put(s, "android");
			}

			List<String> iosList = getIOSDevices();
			for (String s : iosList) {
				deviceMap.put(s, "ios");
			}

		} else if (deviceType == 1) {
			List<String> androidList = getAndroidDevices();
			for (String s : androidList) {
				deviceMap.put(s, "android");
			}
		} else if (deviceType == 2 && Command.isMac()) {
			List<String> iosList = getIOSDevices();
			for (String s : iosList) {
				deviceMap.put(s, "ios");
			}

		} else {
			logger.error("deviceType is error or deviceType and os compatible");
			throw new RuntimeException("deviceType is error or deviceType and os compatible");
		}
		return deviceMap;
	}

	public List<String> getIOSDevices() {
		String iosResult;
		try {
			iosResult = CommandLineExec.executor(Command.getIOSDevices());
			if(iosResult.equals("")){
				logger.error("No ios device");
				throw new RuntimeException("No ios device");
			}
		} catch (Exception e) {
			logger.error(("No ios device-->" + e.getMessage()));
			throw new RuntimeException("No ios device-->" + e.getMessage());
		}
		String[] result = iosResult.split("\n");
		List<String> deviceRes = new ArrayList<String>();
		if (result.length == 1) {
			for (String s : result) {
				deviceRes.add(s);
			}
		} else {
			logger.error("No ios device");
			throw new RuntimeException("No ios device or device more than one ");
		}
		return deviceRes;
	}

	public List<String> getAndroidDevices() {
		String androidResult;
		try {
			androidResult = CommandLineExec.executor(Command.getAndroidDevices());
			
		} catch (Exception e) {
			logger.error(("No android device-->" + e.getMessage()));
			throw new RuntimeException("No android device-->" + e.getMessage());
		}
		String[] result = androidResult.split("\n");
		List<String> deviceRes = new ArrayList<String>();
		int total = 2;
		if (Command.isMac()) {
			total = 1;
		}
		if (result.length >2) {
			for (int i = 1; i < result.length - total + 1; i++) {
				String deviceInfo[] = result[i].split("\t");
				// System.out.println(deviceInfo[0]);127.0.0.1:62001,device
				if (deviceInfo[1].trim().equals("device")) {
					deviceRes.add(deviceInfo[0].trim());
					logger.info("get android device " + deviceInfo[0].trim());
				}
			}
		} else {
			logger.error("No android device  ");
			throw new RuntimeException("No android device ");
		}
		return deviceRes;
	}

	public static void main(String[] args) throws Exception {
		// String commandResult
		// =CommandLineExec.executor(Command.getAndroidDevices());
		// System.out.println(commandResult.split("\n")[0]);
		DevicesManager dm = new DevicesManager(1);
		List<String> deviceList=dm.getAndroidDevices();
		for(String s:deviceList){
			System.out.println(s);
		}
		System.out.println(deviceList.size());
	}

}
