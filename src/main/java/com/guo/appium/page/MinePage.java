package com.guo.appium.page;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class MinePage extends BasePage{
	
	public static final String pagePath="pages/";

	public MinePage(CrazyMobileDriver driver, String platform,String pagePath) {
		super(driver, platform,pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getLoginBtn() {
		return getElement("loginBtn");
	}
	
	public MobileElement getName() {
		return getElement("name");
	}
	public MobileElement getSettings() {
		return getElement("settingBtn");
	}
	
	public MobileElement getAvatar(){
		return getElement("avatar");
	}
	
	public MobileElement getPersonal() {
		return getElement("personal");
	}
	
	public void clickAvatar(){
		getAvatar().click();
	}
	public void clickLoginBtn(){
		getLoginBtn().click();
	}
	public void clickSettings(){
		getSettings().click();
	}
	
	public void clickPersonal() {
		getPersonal().click();
	}
	
}
