package com.guo.appium.page;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class HomePage extends BasePage{
	
	public static final String pagePath="pages/";

	public HomePage(CrazyMobileDriver driver, String platform, String pagePath) {
		super(driver, platform, pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getMine_menu() {
		return getElement("mine_menu");
	}
	public void clickMine_menu() {
		try {
			Thread.sleep(1000);
			getMine_menu().click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MobileElement getHome_menu() {
		return getElement("home_menu");
	}
	public MobileElement getBook_menu() {
		return getElement("book_menu");
	}
	public MobileElement getMarket_menu() {
		return getElement("market_menu");
	}
	public MobileElement getGroup_menu() {
		return getElement("market_menu");
	}

}
