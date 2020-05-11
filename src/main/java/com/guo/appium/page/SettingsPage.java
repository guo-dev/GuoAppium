package com.guo.appium.page;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class SettingsPage extends BasePage {
	
	public static final String pagePath="pages/";

	public SettingsPage(CrazyMobileDriver driver, String platform, String pagePath) {
		super(driver, platform, pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getLogoutBtn() {
		return getElement("logoutBtn");
	}
	public void clickLogoutBtn() {
		getLogoutBtn().click();
	}
	public MobileElement getConfirm() {
		return getElement("confirm");
	}
	public void clickConfirm() {
		getConfirm().click();
	}

}
