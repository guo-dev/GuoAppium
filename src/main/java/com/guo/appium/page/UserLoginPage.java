package com.guo.appium.page;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class UserLoginPage extends BasePage{
	public static final String pagePath="pages/";

	public UserLoginPage(CrazyMobileDriver driver, String platform, String pagePath) {
		super(driver, platform, pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getLoginByPwd() {
		return getElement("loginbypwd");
	}
	
	public MobileElement getUsername() {
		return getElement("username");
	}
	
	public MobileElement getPassword() {
		return getElement("password");
	}
	public MobileElement getLoginBtn() {
		return getElement("loginBtn");
	}
	
	public void clickLoginByPwd() {
		getLoginByPwd().click();
	}
	public void clickLoginBtn() {
		getLoginBtn().click();
	}
	public void sendKeysUsername(String username) {
		getUsername().sendKeys(username);;
	}
	
	public void sendKeysPassword(String password) {
		getPassword().sendKeys(password);
	}

}
