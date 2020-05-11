package com.guo.appium.page;

import java.util.List;

import com.guo.appium.driver.CrazyMobileDriver;

import io.appium.java_client.MobileElement;

public class UsersettingsPage extends BasePage{
	
	public static final String pagePath="pages/";

	public UsersettingsPage(CrazyMobileDriver driver, String platform,String pagePath) {
		super(driver, platform,pagePath);
		// TODO Auto-generated constructor stub
	}
	
	public MobileElement getCivAvatar(){
		return getElement("civ_avatar");
	}
	public MobileElement getModify(){
		return getElement("modify");
	}
	public MobileElement getLayoutBucket(){
		return getElement("layoutBucket");
	}

	public MobileElement getCache(){
		return getElement("cache");
	}
	public List<MobileElement> getIvMedias(){
		return getElements("ivMedias");
	}
	public MobileElement getBtnComplete(){
		return getElement("btnComplete");
	}
	
	public MobileElement getNickName(){
		return getElement("nickname");
	}
	public MobileElement getUserName(){
		return getElement("username");
	}
	
	public MobileElement getSave(){
		return getElement("save");
	}
	public MobileElement getDescription(){
		return getElement("description");
	}
	public MobileElement getFeedback(){
		return getElement("feedback");
	}
	
	public MobileElement getSex(){
		return getElement("sex");
	}
	public MobileElement getMale(){
		return getElement("male");
	}
	public MobileElement getFemale(){
		return getElement("female");
	}
	public MobileElement getBirthday(){
		return getElement("birthday");
	}
	public MobileElement getYear(){
		return getElement("year");
	}
	public MobileElement getMonth(){
		return getElement("month");
	}
	public MobileElement getDay(){
		return getElement("day");
	}
	public MobileElement getBir_save(){
		return getElement("bir_save");
	}

	public void clickCivAvatar(){
		getCivAvatar().click();
	}
	public void clickModify(){
		getModify().click();
	}
	public void clickLayoutBucket(){
		getLayoutBucket().click();
	}
	public void clickCache(){
		getCache().click();
	}
	public void clickBtnComplete(){
		getBtnComplete().click();
	}
	public void clickIvMedia(int index){
		getIvMedias().get(index).click();
	}
	public void clickNickName(){
		getNickName().click();
	}
	public void sendKeysUserName(String nickname){
		getUserName().sendKeys(nickname);
	}
	public void clickSave(){
		getSave().click();
	}
	public void clickDescription(){
		getDescription().click();
	}
	public void sendKeysFeedback(String decription){
		getFeedback().sendKeys(decription);
	}
	public void clickSex(){
		getSex().click();
	}
	public void clickMale(){
		getMale().click();
	}
	public void clickFemale(){
		getFemale().click();
	}
	public void clickBirthday(){
		getBirthday().click();
	}
	public void swipeYear(){
		driver.swipeOnElement(getYear(), "up", 1000);
	}
	public void swipeMonth(){
		driver.swipeOnElement(getMonth(), "up", 1000);
	}
	public void swipeDay(){
		driver.swipeOnElement(getDay(), "up", 1000);
	}
	public void clickBir_save(){
		getBir_save().click();
	}
}
