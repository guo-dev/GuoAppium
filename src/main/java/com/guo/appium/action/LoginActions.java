package com.guo.appium.action;

import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.page.HomePage;
import com.guo.appium.page.LoginPage;
import com.guo.appium.page.MinePage;
import com.guo.appium.page.UserinfoPage;
import com.guo.appium.utils.RandomUtil;

public class LoginActions {
	
	private CrazyMobileDriver driver;
	private String platform;
	public HomePage homePage;
	public LoginPage loginPage;
	public MinePage minePage;
	public UserinfoPage userinfoPage;
	
	public LoginActions(CrazyMobileDriver driver,String platform) {
		this.driver=driver;
		this.platform=platform;
		this.homePage=new HomePage(driver, platform, HomePage.pagePath);
		this.loginPage=new LoginPage(driver, platform, LoginPage.pagePath);
		this.minePage=new MinePage(driver, platform, MinePage.pagePath);
		this.userinfoPage=new UserinfoPage(driver, platform, UserinfoPage.pagePath);
	}
	
	public void login(String username,String pwd) {
//		LoginPage loginPage=new LoginPage(driver, platform, LoginPage.pagePath);
		loginPage.getPwdlogin().click();
		loginPage.getUsername().sendKeys(username);
		loginPage.getPassword().sendKeys(pwd);
		loginPage.getLoginBtn().click();
//		HomePage homePage=new HomePage(driver, platform, HomePage.pagePath);
		homePage.driver.sleep(2000);

	}
	
	public void loginCorrect(String username,String pwd) {
		login(username, pwd);
		homePage.getMine_menu().click();
	}
	//修改个人简介
	public boolean modifyIntro() {
		homePage.driver.sleep(1000);
		homePage.getMine_menu().click();
		minePage.clickPersonal();
		userinfoPage.clickEdit();
		//修改简介不能上一次的值一样，采用随机生成一个字符串，判断和原来不一样就输入，如果一样就再次重新随机生成
		String curText=userinfoPage.getIntro().getText();
		String newText=RandomUtil.getRndStrZhByLen(6);
		while(curText.equals(newText)) {
			newText=RandomUtil.getRndStrZhByLen(6);
		}
		userinfoPage.sendkeysIntro(newText);
		userinfoPage.clickSave();
		userinfoPage.driver.sleep(5000);
		String userintroText = userinfoPage.getUserintroText();
		if(newText.equals(userintroText)) {
			return true;
		}else {
			return false;
		}
	}
}
