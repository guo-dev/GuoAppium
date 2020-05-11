package com.guo.appium.page;

import java.util.List;

import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.utils.GetLocatorUtils;

import io.appium.java_client.MobileElement;

public class BasePage {

	public CrazyMobileDriver driver;
	public GetLocatorUtils locator;
	public String platform;
	public String pagePath;
	String configfile;

	public BasePage(CrazyMobileDriver driver, String platform,String pagePath) {
		this.driver = driver;
		this.platform = platform;
		this.pagePath=pagePath;
		//pages/loginpage.properties,  pages/homepage.properties
		this.configfile = pagePath + this.getClass().getSimpleName().toLowerCase() + ".properties";
		System.out.println(this.configfile);
		this.locator = new GetLocatorUtils(configfile);
	}
	
	
	public MobileElement getElement(String locatorValue){
		//menu_mine
		if(platform.equalsIgnoreCase("android")){
			//driver.findElement(By.xpath("com.example.tiantang:id/iv_bookshelf_header"))
			//locator.getByLocator("menu_mine")==By.xpath("//*[@text='我的']/preceding-sibling::*[1]")
			return driver.findElement(locator.getByLocator(locatorValue));
		}else{
			return driver.findElement(locator.getByLocator("ios_"+locatorValue));
		}
	}
	
	public List<MobileElement> getElements(String locatorValue){
		if(platform.equalsIgnoreCase("android")){
			return driver.findElements(locator.getByLocator(locatorValue));
		}else{
			return driver.findElements(locator.getByLocator("ios_"+locatorValue));
		}
	}


	public static void main(String[] args) {

	}

}
