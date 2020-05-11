package com.guo.appium.testng;

import java.io.File;
import java.io.FileInputStream;

import org.testng.Assert;

import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.driver.CrazyPath;

import io.qameta.allure.Allure;


public class Assertion  {
	  private  CrazyMobileDriver driver;
	  public Assertion(CrazyMobileDriver driver){
		  this.driver=driver;
	  }
	  //注意断言的失败不是一个exception，而是一个error
	  public  void assertEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	fail(fileName);
	        }
	  }
	  public  void assertEquals(Object actual, Object expected, String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	        	fail(fileName,message);
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	           	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public void fail(String fileName){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
			    Long date=System.currentTimeMillis();
//			  	System.out.println(date);
//  			    Reporter.log("<a href=http://localhost:8080/" + Thread.currentThread().getId()+"_"+date+fileName+".png" + " target=_blank>失败截图</a>", true);  
//  			    Reporter.log("<img src=http://localhost:8080/"+Thread.currentThread().getId()+"_"+date+fileName +".png"+" style=width:30px;height:30px img/>", true);
			    String imagefile=Thread.currentThread().getId()+"_"+date+fileName;
				driver.takeScreen("images/",imagefile);
				Allure.addAttachment("失败截图", new FileInputStream(new File("images/"+imagefile+".png")));
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail();
	  }
	  public void fail(String fileName,String message){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
			  	Long date=System.currentTimeMillis();
//			  	System.out.println(date);
//    			Reporter.log("<a href=http://localhost:8080/" + Thread.currentThread().getId()+"_"+date+fileName +".png"+ " target=_blank>失败截图</a>", true);  
//    			Reporter.log("<img src=http://localhost:8080/"+Thread.currentThread().getId()+"_"+date+fileName +".png"+" style=width:30px;height:30px img/>", true);
//				driver.takeScreen("D:/apache-tomcat-8.5.4-windows-x64/apache-tomcat-8.5.4/webapps/ROOT/",Thread.currentThread().getId()+"_"+date+fileName);
			    String imagefile=Thread.currentThread().getId()+"_"+date+fileName;
				driver.takeScreen("images/",imagefile);
				Allure.addAttachment("失败截图", new FileInputStream(new File("images/"+imagefile+".png")));
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail(message);
	  }
}
