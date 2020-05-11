package com.testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.guo.appium.action.LoginActions;
import com.guo.appium.action.UploadActions;
import com.guo.appium.datadriver.ExcelUtil;
import com.guo.appium.testcases.TestBase;
import com.guo.appium.testng.Assertion;

public class DouBanTests extends TestBase{
	@DataProvider
	public Object[][] getLoginData() throws Exception{
		ExcelUtil excel = new ExcelUtil("configs/douban.xlsx");
		// eu.setCellData(2, eu.getLastColumnNum(),"测试执行失败");
		Object[][] ob = excel.getTestData("login");
		return ob;
	}
	
	@Test(dataProvider = "getLoginData")
	public void test001_login(String casenum,String casename,String username,String password,String assertvalue,String filename) throws Exception {
		ExcelUtil excel = new ExcelUtil("configs/douban.xlsx");
		try {
			LoginActions loginActions=new LoginActions(driver, platform);
			loginActions.login(username, password);
			Assert.assertEquals(driver.getPageSource().contains(assertvalue), true);
			excel.setCellData(Integer.valueOf(casenum), 7, "成功", "login", true);
		} catch (Exception |Error e) {
			// TODO: handle exception
			excel.setCellData(Integer.valueOf(casenum), 7, "失败", "login", false);
			Assertion as=new Assertion(driver);
			as.fail(filename,e.getMessage());
		}finally {
			driver.resetApp();
		}

	}
	@Test
	public void test002_loginCorrect() {
		LoginActions loginActions=new LoginActions(driver, platform);
		loginActions.login("15010463120", "ecryan");
		Assert.assertEquals(driver.getPageSource().contains("郭"), true);
	}
	@Test(dependsOnMethods = "test002_loginCorrect")
	public void test003_upload() throws Exception {
		UploadActions uploadActions=new UploadActions(driver, platform);
		boolean upload = uploadActions.upload();
		Assert.assertEquals(upload, true);
	}
	@Test(dependsOnMethods = "test002_loginCorrect")
	public void test004_modifyIntro() {
		LoginActions loginActions=new LoginActions(driver, platform);
		boolean modifyIntro = loginActions.modifyIntro();
		Assert.assertTrue(modifyIntro);
	}
}
