package com.guo.appium.server;

import java.io.File;
import java.util.List;

import com.guo.appium.driver.CrazyPath;
import com.guo.appium.utils.Command;
import com.guo.appium.utils.CommandLineExec;
import com.guo.appium.utils.Log;
import com.guo.appium.utils.ProUtil;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.ServerArgument;

public class ServersManager {

	public DevicesManager deviceManager;
	public TestngXmlConfig xmlConfig;
	public ProUtil proUtil;
	AppiumDriverLocalService service;
	private Log logger = Log.getLogger(ServersManager.class);

	public ServersManager(DevicesManager deviceManager) {
		this.proUtil = new ProUtil(CrazyPath.globalPath);
		this.xmlConfig = new TestngXmlConfig(deviceManager, Integer.valueOf(proUtil.getKey("deviceType")));
	}
	public ServersManager() {
	
	}

	/**
	 * 这个方法是在使用uiautomator1的引擎时使用的
	 * @param portList
	 *            -p的参数列表
	 * @param bpList
	 *            -bp的参数列表
	 */
	public void startServers(List<Integer> portList, List<Integer> bpList) {
		AppiumServiceBuilder ab = new AppiumServiceBuilder();
		AppiumDriverLocalService service;
		if ((portList.size() == bpList.size()) && !portList.isEmpty()) {
			for (int i = 0; i < portList.size(); i++) {
			
				ab.usingPort(Integer.valueOf(portList.get(i)));// 服务端端口
				ab.withArgument(new ServerArgument() {
					
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "--local-timezone";
					}
				});
				ab.withArgument(new ServerArgument() {
					public String getArgument() {
						// TODO Auto-generated method stub
						return "-bp";
					}
				}, String.valueOf(bpList.get(i)));
				ab.withLogFile(new File("logs/appium" + portList.get(i) + ".log"));// 日志存放，每次只需覆盖
				service = ab.build();
				service.start();
			}
		}else{
			logger.error("portList and bpList is not equals");
			throw new RuntimeException("portList and bpList is not equals");
		}
	}

	/**
	 * 混合app时使用的启动server的方法，安卓上
	 * @param portList
	 *            -p
	 * @param bpList
	 *            -bp
	 * @param chromedriverList
	 *            --chromedriver-port
	 */
	public void startServers(List<Integer> portList, List<Integer> bpList, List<Integer> chromedriverList) {
		AppiumServiceBuilder ab = new AppiumServiceBuilder();
		AppiumDriverLocalService service;
		if ((portList.size() == bpList.size()) && (portList.size() == chromedriverList.size()) && !portList.isEmpty()) {
			for (int i = 0; i < portList.size(); i++) {
				ab.usingPort(Integer.valueOf(portList.get(i)));// 服务端端口
				ab.withArgument(new ServerArgument() {
					
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "--local-timezone";
					}
				});
				ab.withArgument(new ServerArgument() {
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "-bp";
					}
				}, String.valueOf(bpList.get(i)));
				ab.withArgument(new ServerArgument() {
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "--chromedriver-port";
					}
				}, String.valueOf(chromedriverList.get(i)));
				ab.withLogFile(new File("logs/appium" + portList.get(i) + ".log"));// 日志存放，每次只需覆盖
				service = ab.build();
				service.start();
			}
		}else{
			logger.error("portList and bpList is not equals");
			throw new RuntimeException("portList and bpList is not equals");
		}
	}

	/**
	 * 纯H5的时候
	 * @param portList
	 *            -p
	 * @param chromedriverList
	 *            --chromedriver-port
	 */
	public void startServersWithH5(List<Integer> portList, List<Integer> chromedriverList) {
		AppiumServiceBuilder ab = new AppiumServiceBuilder();
		AppiumDriverLocalService service;
		if ((portList.size() == chromedriverList.size()) && !portList.isEmpty()) {
			for (int i = 0; i < portList.size(); i++) {
				ab.usingPort(Integer.valueOf(portList.get(i)));// 服务端端口
				ab.withArgument(new ServerArgument() {
					
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "--local-timezone";
					}
				});
				ab.withArgument(new ServerArgument() {
					@Override
					public String getArgument() {
						// TODO Auto-generated method stub
						return "--chromedriver-port";
					}
				}, String.valueOf(chromedriverList.get(i)));
				ab.withLogFile(new File("logs/appium" + portList.get(i) + ".log"));// 日志存放，每次只需覆盖
				service = ab.build();
				service.start();
			}
		}else{
			logger.error("portList and bpList is not equals");
			throw new RuntimeException("portList and bpList is not equals");
		}
	}

	public void killServers() {
		try {
			CommandLineExec.executor(Command.getKillServers());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug("kill appium servers failure-->" + e.getMessage());
		}
	}
	
	public void startServer(String port) {
		AppiumServiceBuilder ab = new AppiumServiceBuilder();
		//ab.withIPAddress("10.0.0.37");
		ab.usingPort(Integer.valueOf(port));
		ab.withArgument(new ServerArgument() {
			@Override
			public String getArgument() {
				// TODO Auto-generated method stub
				return "--local-timezone";
			}
		});
		ab.withLogFile(new File("logs/appium" + port + ".log"));// 日志存放，每次只需覆盖
		service = ab.build();
		service.start();
	}
	
	public void stop() {
		service.stop();
	}
	public static void main(String[] args) {
//		DevicesManager dm = new DevicesManager(1);
//		ServersManager sm = new ServersManager(dm);
////		List<Integer> portList = Port.generatPortList(4490, dm.devicesCounts);
////		List<Integer> bpList = Port.generatPortList(2257, dm.devicesCounts);
////		List<Integer> chromedriverList = Port.generatPortList(3389, dm.devicesCounts);
////		sm.startServers(portList, bpList, chromedriverList);
//		sm.killServers();
		ServersManager sm=new ServersManager();
		sm.startServer("7890");
		sm.killServers();
		//sm.stop();
	}
}
