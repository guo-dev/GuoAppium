package com.guo.appium.main;


import com.guo.appium.server.StartServers;

public class TestMain {
	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";  
	private static String path=System.getProperty("user.dir");
	
	public static void main(String[] args) {
		System.setProperty(ESCAPE_PROPERTY, "false"); 
		StartServers.startServers();
//        List<String> suites = new ArrayList<String>();
//        suites.add(System.getProperty("user.dir")+"/testngMain.xml");
//        TestNG tng = new TestNG();
//        tng.setTestSuites(suites);
//        tng.run();
		
	}
}
