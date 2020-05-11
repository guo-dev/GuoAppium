package com.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.guo.appium.action.LoginActions;
import com.guo.appium.datadriver.ExcelUtil;
import com.guo.appium.testcases.TestBase;

import io.appium.java_client.functions.ExpectedCondition;

public class Tests extends TestBase {
//	@DataProvider
//	public Object[][] getData1() throws Exception{
//		ExcelUtil excel = new ExcelUtil("configs/liecai.xlsx");
//		return excel.getTestData("login");
//	}
	
	
	@Test(dataProvider = "getData")
	public void test001_login(String linenum,String casename,String username,String pwd,String assertValue,String filename) throws Exception {
		LoginActions loginActions=new LoginActions(driver, platform);
		ExcelUtil excel=new ExcelUtil("configs/liecai.xlsx");
		try {
			
			loginActions.login(username, pwd);
			boolean flag=new WebDriverWait(driver.driver, 10).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return driver.getPageSource().contains(assertValue);
				}
			});
			Assert.assertTrue(flag);
			excel.setCellData(Integer.valueOf(linenum), 7, "成功", "login", true);
		} catch (Exception|Error e) {
			// TODO: handle exception
			try {
				driver.takeScreen("images/", filename);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			excel.setCellData(Integer.valueOf(linenum), 7, "失败", "login", false);
			Assert.fail("casename"+e.getMessage());
		}

	}
}
