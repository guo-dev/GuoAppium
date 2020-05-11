package com.guo.appium.server;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.guo.appium.utils.Log;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class TestngXmlConfig {

	// public DevicesManager deviceManager;
	private Log logger = Log.getLogger(TestngXmlConfig.class);
	public int devicesCounts;
	public Map<String, String> devicesMap;

	public TestngXmlConfig(DevicesManager deviceManager, int deviceType) {
		this.devicesMap = deviceManager.getDevices(deviceType);
		this.devicesCounts = deviceManager.devicesCounts;

	}

	/**
	 * 
	 * @param classname
	 * @param portList
	 * @param testType
	 *            0：均分测试用例每个设备；1：每个设备都执行所有用例
	 */
	public void createConfig(String[] classnames, List<Integer> portList, List<Integer> sysPortList,int testType) {
//		if (testType == 0) {
//			//System.out.println(classnames.length + "======" + devicesCounts);
//			if (classnames.length % devicesCounts != 0) {
//				throw new RuntimeException("classnames'totals%devicesCounts!=0,Not average");
//			}
//		}
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(devicesCounts));
//		Element listeners = root.addElement("listeners");
//		Element listener1 = listeners.addElement("listener");
//		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
//		Element listener2 = listeners.addElement("listener");
//		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
//		Element listener3 = listeners.addElement("listener");
//		listener3.addAttribute("class-name", "com.crazy.appium.testng.RetryListener");
//		Element listener4 = listeners.addElement("listener");
//		listener4.addAttribute("class-name", "com.crazy.appium.testng.TestngListener");
		
		//{"udid1":"android","udid2":"android","udid3":"ios"}
		Iterator iter = devicesMap.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Object key = entry.getKey();//udid
			Object val = entry.getValue();//platform
			Element test = root.addElement("test");
			test.addAttribute("name", key.toString());
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", key.toString());
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", String.valueOf(portList.get(i)));
			Element paramPlatform = test.addElement("parameter");
			paramPlatform.addAttribute("name", "platform");
			paramPlatform.addAttribute("value", val.toString());
			
			Element sysPort = test.addElement("parameter");
			sysPort.addAttribute("name", "sysPort");
			sysPort.addAttribute("value", String.valueOf(sysPortList.get(i)));
			Element classes = test.addElement("classes");
			//假设我们有6个用例，两台设备，这时每台3个用例{1,2,3,4,5,6,7,8,9}，{a，b,c}
			
			if (testType == 0) {
				int j = classnames.length / devicesCounts;// 62/8=7  62-56=6
				int y=classnames.length % devicesCounts;//y=6   6*8=48  2*7=14
				//每个设备分配测试用例的起始索引（j*i）+每台应该分配的个数j+余数的y==整个测试用例的个数，说明当前是最后一台设备
				if((j*i)+j+y==classnames.length){
					for (int k = 0; k < j+y; k++) {
						Element classNode = classes.addElement("class");
						classNode.addAttribute("name", classnames[j * i + k]);
					}
				}else{
					for (int k = 0; k < j; k++) {
						Element classNode = classes.addElement("class");
						classNode.addAttribute("name", classnames[j * i + k]);
					}
				}
			} else {
				for (int k = 0; k < classnames.length; k++) {
					Element classNode = classes.addElement("class");
					classNode.addAttribute("name", classnames[k]);
				}
			}
			i++;
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testngMain.xml"), format);
			xmlWrite2.write(document);
			xmlWrite2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("creat testng config file failure-->" + e.getMessage());
			throw new RuntimeException("creat testng config file failure-->" + e.getMessage());
		}
	}
	/**
	 * 
	 * @param portList
	 * @param deviceCases  设备与测试用例之间对应关系的map对象
	 */
	public void createConfig(List<Integer> portList,Map<String,String[]> deviceCases) {

		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(devicesCounts));
//		Element listeners = root.addElement("listeners");
//		Element listener1 = listeners.addElement("listener");
//		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
//		Element listener2 = listeners.addElement("listener");
//		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
//		Element listener3 = listeners.addElement("listener");
//		listener3.addAttribute("class-name", "com.crazy.appium.testng.RetryListener");
//		Element listener4 = listeners.addElement("listener");
//		listener4.addAttribute("class-name", "com.crazy.appium.testng.TestngListener");
		//{"udid1":"android","udid2":"android","udid3":"ios"}
		Iterator iter = devicesMap.entrySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Object key = entry.getKey();//udid
			Object val = entry.getValue();//platform
			Element test = root.addElement("test");
			test.addAttribute("name", key.toString());
			Element paramUuid = test.addElement("parameter");
			paramUuid.addAttribute("name", "udid");
			paramUuid.addAttribute("value", key.toString());
			Element paramPort = test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value", String.valueOf(portList.get(i)));
			Element paramPlatform = test.addElement("parameter");
			paramPlatform.addAttribute("name", "platform");
			paramPlatform.addAttribute("value", val.toString());
			Element classes = test.addElement("classes");
			
			String[] cases=deviceCases.get(key);
			for (int k = 0; k < cases.length; k++) {
				Element classNode = classes.addElement("class");
				classNode.addAttribute("name", cases[k]);
			}
			
			i++;
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testngMain.xml"), format);
			xmlWrite2.write(document);
			xmlWrite2.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("creat testng config file failure-->" + e.getMessage());
			throw new RuntimeException("creat testng config file failure-->" + e.getMessage());
		}
	}
}
