package com.guo.appium.server;

import java.util.List;

import com.guo.appium.driver.CrazyPath;
import com.guo.appium.utils.Log;
import com.guo.appium.utils.ProUtil;

public class StartServers {
	private static Log logger = Log.getLogger(ServersManager.class);

	/**
	 * 
	 * @param appType
	 *            0:原生；1：混合；2：H5
	 */
	//获取global properties文件属性
	public static void startServers() {
		ProUtil proUtil = new ProUtil(CrazyPath.globalPath);
		int deviceType = Integer.valueOf(proUtil.getKey("deviceType"));
		String[] classnames = proUtil.getKey("testcases").split(",");
		int appType = Integer.valueOf(proUtil.getKey("appType"));
		int testType = Integer.valueOf(proUtil.getKey("testType"));
		DevicesManager deviceManager = new DevicesManager(deviceType);
		ServersManager service = new ServersManager(deviceManager);
		service.killServers();
		List<Integer> portList = Port.generatPortList(4490, deviceManager.devicesCounts);
		List<Integer> bpList = Port.generatPortList(2257, deviceManager.devicesCounts);
		List<Integer> chromedriverList = Port.generatPortList(3389, deviceManager.devicesCounts);
		List<Integer> sysPortList = Port.generatPortList(8200, deviceManager.devicesCounts);
		service.xmlConfig.createConfig(classnames, portList, sysPortList,testType);
		if (appType == 0) {
			service.startServers(portList, bpList);
		} else if (appType == 1) {
			service.startServers(portList, bpList, chromedriverList);
		} else if (appType == 2) {
			service.startServersWithH5(portList, chromedriverList);
		} else {
			logger.error("appType is error,appType must is 0:原生；1：混合；2：H5");
			throw new RuntimeException("appType is error,appType must is 0:原生；1：混合；2：H5");
		}
	}

	public static void main(String[] args) {
		startServers();
	}

}
