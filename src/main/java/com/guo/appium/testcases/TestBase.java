package com.guo.appium.testcases;

import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.server.ServersManager;
import com.guo.appium.utils.SendMail;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestBase {
	public CrazyMobileDriver driver;
	// AppiumDriverLocalService service;
	String udid;
	ServersManager server;
	public String platform;
	boolean loginStatus=false;

	public CrazyMobileDriver getDriver() {
		return driver;
	}

	public String getUdid() {
		return udid;
	}
	public String getPlatform() {
		return platform;
	}
	@Parameters({"port","platform"})
	@BeforeTest
	public void startServer(String port,String platform) {
		if(platform.equalsIgnoreCase("ios")) {
			System.out.println("临时性修改");
		}else {
			server=new ServersManager();
			server.startServer(port);//启动appium server
		}

	}
	@Parameters({ "udid", "port", "platform"})
	@BeforeClass
	public void beforeClass(String udid, String port, String platform) throws Exception {
		this.udid = udid;
		this.platform = platform;
		System.out.println(udid + port);
//		server=new ServersManager();
//		//server.killServers();
//		server.startServer(port);
		try {
			driver = new CrazyMobileDriver(platform, udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			SendMail se=new SendMail();
			se.send("driver init failure", "driver init failure-->"+udid+"-->"+e.getMessage());
		}
		driver.implicitlyWaitDefault();
		driver.sleep(10000);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		//server.stop();
	}
//	
//	@DataProvider
//	public Object[][] getData(Method method) throws Exception{
//		ExcelUtil excel=new ExcelUtil("configs/liecai.xlsx");
//        Object[][] result = null;
//        if(method.getName().equals("test001_login")){
//            result = excel.getTestData("login");
//        }else if(method.getName().equals("test004_search")){
//        	result = excel.getTestData("search");
//        }else{
//            result = new Object[][]{new Object[]{3}};
//        }
//        return result;
//	}
	@AfterMethod
	public void backHome() throws Exception{
		if(platform.equalsIgnoreCase("android")){
			driver.startActivity((String)driver.getCaps().getCapability("appPackage"), (String)driver.getCaps().getCapability("appActivity"));
			driver.sleep(6000);
		}else{
//			//ios的话需要用到不断返回直到首页元素出现为止
//			while(driver.isElementExist(By.xpath("//*[resource-id='com.toobei.lcds:id/mainActivity_RadioGroup']"))){
//				driver.findElement(MobileBy.AccessibilityId("back")).click();
//			}
			driver.activateApp((String)driver.getCaps().getCapability("bundleId"));
		}
//		driver.closeApp();
//		driver.launchApp();
		
		
	}
	@AfterTest
	public  void stopServer() {
		if(platform.equalsIgnoreCase("ios")) {
			System.out.println("临时性修改");
		}else {
			server.killServers();
		}
		
	}


}
