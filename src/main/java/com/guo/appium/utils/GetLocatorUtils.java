package com.guo.appium.utils;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;

public class GetLocatorUtils {
	ProUtil locator;
	private static Log logger = Log.getLogger(GetLocatorUtils.class);

	public GetLocatorUtils(String locatorFile){
		this.locator = new ProUtil(locatorFile);

	}
	/**
	 * By.id("xxx")
	 * @param locatorType
	 * @return
	 * @throws Exception
	 */
	public By getByLocator(String locatorType) {
		//locatorType==menu_mine
		String locatorKeyValue = locator.getKey(locatorType);//xpath>//*[@text='我的']/preceding-sibling::*[1]
		// 将配置对象中的定位类型存到 locatorType 变量，将定位表达式的值存入到 locatorValue 变量
		String type = locatorKeyValue.split(">")[0].trim();// xpath
		String value = locatorKeyValue.split(">")[1].trim();// //*[@text='我的']/preceding-sibling::*[1]
		// 输出 locatorType 变量值和locatorValue 变量值，验证是否赋值正确
		logger.info("Get element locator type by "+type+" with value : "+value );
		// 根据 locatorType 的变量值内容判断，返回何种定位方式的 By 对象
		if (type.equalsIgnoreCase("id"))
			return By.id(value);// By.id("com.zhihu.android:id/email_or_phone")
		else if (type.equalsIgnoreCase("xpath"))
			return By.xpath(value);
		else if(type.equalsIgnoreCase("accessibilityId"))
			return MobileBy.AccessibilityId(value);
		else if(type.equalsIgnoreCase("uiautomator"))
			return MobileBy.AndroidUIAutomator(value);
		else if ((type.toLowerCase().equalsIgnoreCase("classname")))
			return By.className(value);
		else if ((type.equalsIgnoreCase("linktext")))
			return By.linkText(value);
		else if (type.equalsIgnoreCase("partiallinktext"))
			return By.partialLinkText(value);
		else if (type.equalsIgnoreCase("cssselector"))
			return By.cssSelector(value);
		else if(type.equalsIgnoreCase("iosnspredicatestring"))
			return MobileBy.iOSNsPredicateString(value);
		else{
			logger.error("locator type not exist in this framework："+type);
			throw new RuntimeException(" locator type not exist in this framework：" + type);
		}
	}
	
	public static By getByLocatorKeyWord(String locatorKeyValue) {
		// 将配置对象中的定位类型存到 locatorType 变量，将定位表达式的值存入到 locatorValue 变量
		String type = locatorKeyValue.split(">")[0];// name
		String value = locatorKeyValue.split(">")[1];// 登录
		// 输出 locatorType 变量值和locatorValue 变量值，验证是否赋值正确
		logger.info("Get element locator type by "+type+" with value : "+value );
		// 根据 locatorType 的变量值内容判断，返回何种定位方式的 By 对象
		if (type.equalsIgnoreCase("id"))
			return By.id(value);// By.id("com.zhihu.android:id/email_or_phone")
		else if (type.equalsIgnoreCase("xpath"))
			return By.xpath(value);
		else if(type.equalsIgnoreCase("accessibilityId"))
			return MobileBy.AccessibilityId(value);
		else if(type.equalsIgnoreCase("uiautomator"))
			return MobileBy.AndroidUIAutomator(value);
		else if ((type.toLowerCase().equalsIgnoreCase("classname")))
			return By.className(value);
		else if ((type.equalsIgnoreCase("linktext")))
			return By.linkText(value);
		else if (type.equalsIgnoreCase("partiallinktext"))
			return By.partialLinkText(value);
		else if ((type.equalsIgnoreCase("cssselector")))
			return By.cssSelector(value);
		else if(type.equalsIgnoreCase("iosnspredicatestring"))
			return MobileBy.iOSNsPredicateString(value);
		else{
			throw new RuntimeException(" locator type not exist in this framework：" + type);
		}
	}

}
