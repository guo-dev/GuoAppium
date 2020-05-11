package com.guo.appium.page;

import java.util.List;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class UserinfoPage extends BasePage{
	
	public static final String pagePath="pages/";

	public UserinfoPage(CrazyMobileDriver driver, String platform,String pagePath) {
		super(driver, platform,pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getAvatar(){
		return getElement("avatar");
	}
	
	public void clickAvatar(){
		getAvatar().click();
	}
	
	public MobileElement getCamara(){
		return getElement("camara");
	}
	
	public void clickCamara(){
		getCamara().click();
	}
	
	public List<MobileElement> getsIcon(){
		return getElements("icon");
	}
	
	public void clickIcon(int index) {
		getsIcon().get(index).click();
	}
	
	public MobileElement getConfirm(){
		return getElement("confirm");
	}
	
	public void clickConfirm(){
		getConfirm().click();
	}
	
	public MobileElement getSure(){
		return getElement("sure");
	}
	
	public void 点击图片上传的完成(){
		getSure().click();
	}
	
	public MobileElement getBack(){
		return getElement("back");
	}
	
	public void clickBack(){
		getBack().click();
	}
	
	

	public MobileElement getEdit(){
		return getElement("edit");
	}


	public void clickEdit(){
		getEdit().click();
	}
	public MobileElement getIntro() {
		return getElement("intro");
	}
	public void sendkeysIntro(String introValue) {
		getIntro().sendKeys(introValue);
	}
	public MobileElement getSave() {
		return getElement("save");
	}
	public void clickSave() {
		getSave().click();
	}
	public MobileElement getUserintro() {
		return getElement("userintro");
	}
	public String getUserintroText() {
		return getUserintro().getText();
	}

	
}
