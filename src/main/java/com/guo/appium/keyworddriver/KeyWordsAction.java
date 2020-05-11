package com.guo.appium.keyworddriver;


import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.utils.GetLocatorUtils;
import io.appium.java_client.MobileElement;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;


public class KeyWordsAction {
	 
    
	public CrazyMobileDriver driver;
	
	Map<String,Object> param=new HashMap<String,Object>();
	public KeyWordsAction(CrazyMobileDriver driver){
		this.driver=driver;
	}

	/* 此方法的名称对应 excel 文件中“关键字”列中的 input 关键字
	 * 读取 excel 文件中“操作值”列中的字符作为输入框的输入内容，
	 * 参数 locator 表示输入框的定位表达式。
	 */
	public  void input(String locator,String inputValue) {
		
		try {
			driver.findElement(GetLocatorUtils.getByLocatorKeyWord(locator))
					.sendKeys(inputValue);
		} catch (Exception e) {
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	
	/* 此方法名称对应 excel 文件中“关键字”列中的 click 关键字，
	 * 实现点击操作，参数locator 代表被点击元素的定位表达式，
	 * 参数 String为无实际值传入的参数，仅为了通过反射机制统一地使用两个
	 * 函数参数来调用此函数。
	 */
	public  void click(String locator,String string) {
		try {
			driver.findElement(GetLocatorUtils.getByLocatorKeyWord(locator)).click();
		} catch (Exception e) {
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 通过坐标点击元素，对应关键字clickByCoordinate
	 * @param x
	 * @param y
	 */
	public  void clickByCoordinate(String locator,String coordinate){
		int x=0;
		int y=0;
		if(coordinate.contains("=")){
			x=Integer.valueOf(coordinate.split("=")[0]);
			y=Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.clickByCoordinate( x, y);
			} catch (Exception e) {
				KeywordsTests.testResult = false;
				e.printStackTrace();
			}
		}else{
			System.out.println("坐标无效");
			KeywordsTests.testResult = false;
		}
	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 WaitFor_Element 关键字，
	 * 用于显示等待页面元素出现在页面中。函数读取 excel 文件中“操作值”列中的表达
	 * 式作为函数参数，objectMap 对象的getLocator 方法会根据函数参数值在配置
	 * 文件中查找 key 值对应的定位表达式。参数 locatorExpression 表示等待出现
	 * 页面元素的定位表达式，参数 String为无实际值传入的参数，仅为了通过反射机
	 * 制统一地使用两个函数参数来调用此函数。
	 */
	public  void WaitFor_Element(String locator,String timeout) {
		try {
			//调用封装的 isElementExist 函数显示等待页面元素是否出现
			KeywordsTests.testResult=driver.isElementExist(GetLocatorUtils.getByLocatorKeyWord(locator),Integer.valueOf(timeout));
		} catch (Exception e) {
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	public  void WaitFor_Not_Element(String locator,String timeout) {
		try {
			//调用封装的 isElementExist 函数显示等待页面元素是否出现
			KeywordsTests.testResult=driver.isElementNotExist(GetLocatorUtils.getByLocatorKeyWord(locator),Integer.valueOf(timeout));
		} catch (Exception e) {
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 sleep 关键字用于等待操作，
	 * 暂停几秒，函数参数是以毫秒为单位的等待时间。参数 sleepTime 表示暂停
	 * 的毫秒数，参数 String为无实际值传入的参数，仅为了通过反射机制统一地使用
	 * 两个函数参数来调用此函数。
	 */
	public  void sleep(String string,String sleepTime){
		try {
			driver.sleep(Integer.valueOf(sleepTime));
		} catch (Exception e) {
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 Assert_String 关键字，参数 assertString
	 * 为要断言的字符串内容，参数 string 为无实际值传入的参数，仅为了通过反射机制统一地使用两
	 * 个函数参数来调用此函数。
	 */
	
	public  void  Assert_String(String string,String assertString)  {
		try{ 
			//System.out.println(driver.getPageSource());
			 Assert.assertTrue(driver.getPageSource().contains(assertString));
		} catch (AssertionError e) {
			KeywordsTests.testResult = false;
			System.out.println("断言失败");
		}
	}
	public  void  Assert_Element(String locator,String assertString)  {
		try{ 
			 Assert.assertEquals(driver.isElementExist(GetLocatorUtils.getByLocatorKeyWord(locator)), true);
		} catch (AssertionError e) {
			KeywordsTests.testResult = false;
			System.out.println("断言失败");
		}
	}
	/**
	 * 滑动屏幕
	 * @param string
	 * @param direction
	 */
	public  void swipeScreen(String swipeCounts,String direction){
		try {
			int count=Integer.valueOf(swipeCounts);
			while(count>0){
				driver.swipe(direction, 500);
				count--;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 在某元素上滑动
	 * @param locator
	 * @param direction
	 */
	public  void swipeOnElement(String locator,String direction){
		try {
			driver.swipeOnElement(GetLocatorUtils.getByLocatorKeyWord(locator), direction, 500);
		} catch (Exception e) {
			// TODO: handle exception
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 长按某元素
	 * @param locator
	 * @param direction
	 */
	public  void longPress(String locator,String direction){
		try {
			driver.longPress(GetLocatorUtils.getByLocatorKeyWord(locator));
		} catch (Exception e) {
			// TODO: handle exception
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 坐标点长按
	 * @param x
	 * @param y
	 */
	public  void longPressByCoordinate(String string,String coordinate){
		int x=0;
		int y=0;
		if(coordinate.contains("=")){
			x=Integer.valueOf(coordinate.split("=")[0]);
			y=Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.longPress(x,y);
			} catch (Exception e) {
				KeywordsTests.testResult = false;
				e.printStackTrace();
			}
		}else{
			System.out.println("坐标无效");
		}
	}
	public  void wakeByGestures(String locator,String pwd){
		MobileElement element=driver.findElement(GetLocatorUtils.getByLocatorKeyWord(locator));
		String[] indexsStr=pwd.split(",");
		int[] indexs=new int[indexsStr.length];
		for(int i=0;i<indexsStr.length;i++){
			indexs[i]=Integer.valueOf(indexsStr[i]);
		}
		driver.wakeByGestures(element, indexs);
	}
	public void clear(String locator,String str){
		try {
			driver.clearOneByOne(driver.findElement(GetLocatorUtils.getByLocatorKeyWord(locator)));
		} catch (Exception e) {
			// TODO: handle exception
			KeywordsTests.testResult = false;
			e.printStackTrace();
		}
	}
	public void getText(String locator,String str){
		try {
			String text=driver.findElement(GetLocatorUtils.getByLocatorKeyWord(locator)).getText();
			param.put(str, text);
		} catch (Exception e) {
			// TODO: handle exception
			KeywordsTests.testResult=false;
		}

	}
	public void Assert_param(String oldparam,String newparam){
		KeywordsTests.testResult=param.get(oldparam).toString().equals(newparam);
		Assert.assertTrue(KeywordsTests.testResult);
	}
}
