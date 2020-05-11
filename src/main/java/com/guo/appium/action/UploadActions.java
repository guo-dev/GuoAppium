package com.guo.appium.action;

import com.guo.appium.driver.CrazyMobileDriver;
import com.guo.appium.page.HomePage;
import com.guo.appium.page.MinePage;
import com.guo.appium.page.UserinfoPage;
import com.guo.appium.utils.ImageUtil;
import com.guo.appium.utils.RandomUtil;

public class UploadActions {
	private CrazyMobileDriver driver;
	private String platform;
	public HomePage homePage;
	public UserinfoPage userinfoPage;
	public MinePage minePage;
	
	public UploadActions(CrazyMobileDriver driver,String platform) {
		this.driver=driver;
		this.platform=platform;
		this.homePage=new HomePage(driver, platform, HomePage.pagePath);
		this.userinfoPage=new UserinfoPage(driver, platform, UserinfoPage.pagePath);
		this.minePage=new MinePage(driver, platform, MinePage.pagePath);
	}
	/**
	 * 1. 先准备测试数据，就是相册里的几张图片
	 * 2. 遍历相册里的图片，进行上传，上传以后采集上传成功的头像作为期望图片
	 * @throws Exception 
	 */
	public boolean upload() throws Exception {
		homePage.driver.sleep(1000);
		homePage.getMine_menu().click();
		//采集期望图片
//		for(int i=1;i<4;i++) {
//			minePage.driver.sleep(5000);
//			minePage.clickAvatar();
//			minePage.driver.sleep(2000);
//			userinfoPage.clickAvatar();
//			userinfoPage.clickCamara();
//			userinfoPage.driver.sleep(2000);
//			userinfoPage.clickIcon(i);
//			userinfoPage.clickConfirm();
//			userinfoPage.点击图片上传的完成();
//			userinfoPage.driver.sleep(6000);
//			userinfoPage.clickBack();
//			userinfoPage.driver.sleep(5000);
//			userinfoPage.driver.takeScreenForElement(userinfoPage.getAvatar(), "images/0311/", String.valueOf(i));
//		}
		
		//测试思路
		//1. 截图头像，和期望图片1/2/3逐个对比，和谁一样就说明当前头像是谁，那么本次上传不选择改图
		//2.记录当前图片的索引，随机选择时，随机生成一个和当前图片索引不一样的索引，并且记住这个新索引
		//3. 根据随机生成的索引选择图片
		//4.上传完成以后，再次针对新头像进行截图，并且和期望图片进行对比
		minePage.driver.sleep(5000);
		userinfoPage.driver.takeScreenForElement(userinfoPage.getAvatar(), "images/0311/", "current");
		int cur_index=1;
		for(int i=1;i<4;i++) {
		
			if(ImageUtil.compareImg("images/0311/current.png", "images/0311/"+i+".png", 100f)) {
				cur_index=i;
				break;
			}
		}
		
		minePage.clickAvatar();
		minePage.driver.sleep(2000);
		userinfoPage.clickAvatar();
		userinfoPage.clickCamara();
		userinfoPage.driver.sleep(2000);
		int new_index=RandomUtil.randomInt(1, 4);
		while(cur_index==new_index) {
			new_index=RandomUtil.randomInt(1, 4);
		}
		userinfoPage.clickIcon(new_index);
		userinfoPage.clickConfirm();
		userinfoPage.点击图片上传的完成();
		userinfoPage.driver.sleep(6000);
		userinfoPage.clickBack();
		userinfoPage.driver.sleep(5000);
		userinfoPage.driver.takeScreenForElement(userinfoPage.getAvatar(), "images/0311/","new");
		boolean compareImg = ImageUtil.compareImg("images/0311/new.png", "images/0311/"+new_index+".png", 100f);

		return compareImg;
	}
}
