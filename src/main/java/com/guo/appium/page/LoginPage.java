package com.guo.appium.page;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class LoginPage extends BasePage {
	
	public static final String pagePath="pages/";

	public LoginPage(CrazyMobileDriver driver, String platform, String pagePath) {
		super(driver, platform, pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getPwdlogin() {
		return getElement("pwdlogin");
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

}
